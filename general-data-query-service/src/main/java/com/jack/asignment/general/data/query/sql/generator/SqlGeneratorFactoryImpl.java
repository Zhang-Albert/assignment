package com.jack.asignment.general.data.query.sql.generator;

import com.jack.asignment.general.data.query.annotation.Generator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SqlGeneratorFactoryImpl implements SqlGeneratorFactory, InitializingBean, ApplicationContextAware {
    private final Map<String,SqlGenerator> sqlGeneratorMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public SqlGenerator loadSqlGenerator(String type) {
        return sqlGeneratorMap.get(type.toUpperCase());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // load all the sqlGenerator into sqlGeneratorMap
        Map<String, Object> beansMap = applicationContext.getBeansWithAnnotation(Generator.class);
        beansMap.forEach((k,v)->{
            String value = v.getClass().getAnnotation(Generator.class).value();
            sqlGeneratorMap.put(value.toUpperCase(),(SqlGenerator)v);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
