package me.marcos.cobwebs.spring.scanner;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.marcos.core.annotations.Explorer;
import me.marcos.core.annotations.Resource;
import me.marcos.core.explorer.ExplorerDefinition;
import me.marcos.core.proxy.ExplorerProxy;
import me.marcos.core.proxy.ExplorerProxyFactory;
import me.marcos.core.resource.ResourceDefinition;
import me.marcos.core.scanner.Scanner;
import me.marcos.core.utils.ExplorerUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/6/9
 * Time:下午10:27
 * Description:
 * 用于扫描资源定义情况
 */
@Slf4j
public class ExplorerSpringScanner implements Scanner<ExplorerDefinition>, BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    @Setter
    private String excludePatterns;
    @Setter
    private String basePackage;
    @Setter
    private ExplorerProxy explorerProxy;

    private Collection<ExplorerDefinition> explorerDefinitions;

    private ApplicationContext applicationContext;


    public Collection<ExplorerDefinition> scan() {
        return this.explorerDefinitions;
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Scanner scanner = new Scanner(beanDefinitionRegistry);
        scanner.setResourceLoader(this.applicationContext);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    private final class Scanner extends ClassPathBeanDefinitionScanner {
        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        protected void registerDefaultFilters() {
            this.addIncludeFilter(new AnnotationTypeFilter(Explorer.class));
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(excludePatterns)) {
                String[] excludePackages = StringUtils.tokenizeToStringArray(excludePatterns, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
                for (String ep : excludePackages) {
                    this.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(ep)));
                }
            }
        }

        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set beanDefinitions = super.doScan(basePackages);
            if (beanDefinitions.isEmpty()) {
                this.logger.warn("未找到任何服务配置信息，请检查。");
            } else {
                Iterator iterator = beanDefinitions.iterator();
                while (iterator.hasNext()) {
                    BeanDefinitionHolder holder = (BeanDefinitionHolder) iterator.next();
                    GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                    Class beanClass = null;
                    String beanClassName = ((ScannedGenericBeanDefinition) definition).getMetadata().getClassName();
                    try {
                        beanClass = ClassUtils.forName(beanClassName, this.getClass().getClassLoader());
                    } catch (ClassNotFoundException e) {
                        //log.error("无法找到类:{}", beanClassName, e);
                    }
                    extractDoScan(beanClass, definition);
                }
            }
            return beanDefinitions;
        }

        private void extractDoScan(Class beanClass, GenericBeanDefinition definition) {
            if (beanClass != null) {
                Explorer explorer = (Explorer) beanClass.getAnnotation(Explorer.class);
                if (explorer!=null){
                    //创建代理类
                    Object proxyObject =  explorerProxy.createProxy(beanClass);
                    ExplorerProxyFactory.registedProxy(beanClass,proxyObject);

                    definition.setBeanClass(ExplorerProxyFactory.class);
                    definition.getPropertyValues().add("proxyClass",beanClass);

                    Collection<ExplorerDefinition> explorerDefinitions = ExplorerUtils.extractExplorerDefinition(beanClass);
                    explorerDefinitions.addAll(explorerDefinitions);
                }
            }
        }


        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
        }

        protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
            if (super.checkCandidate(beanName, beanDefinition)) {
                return true;
            } else {
                this.logger.warn("Skipping MapperFactoryBean with name \'" + beanName + "\' and \'" + beanDefinition.getBeanClassName() + "\' mapperInterface" + ". Bean already defined with the same name!");
                return false;
            }
        }
    }
}
