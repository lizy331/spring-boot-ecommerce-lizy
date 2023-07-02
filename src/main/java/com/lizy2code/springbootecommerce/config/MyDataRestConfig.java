package com.lizy2code.springbootecommerce.config;

import com.lizy2code.springbootecommerce.entity.Country;
import com.lizy2code.springbootecommerce.entity.Product;
import com.lizy2code.springbootecommerce.entity.ProductCategory;
import com.lizy2code.springbootecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnSupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // disable http method for PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnSupportedActions);
        disableHttpMethods(ProductCategory.class,config, theUnSupportedActions);
        disableHttpMethods(Country.class,config, theUnSupportedActions);
        disableHttpMethods(State.class,config, theUnSupportedActions);

        // expose the entity id
        expose(config);
    }

    private static void disableHttpMethods(Class theClass ,RepositoryRestConfiguration config, HttpMethod[] theUnSupportedActions) {
        config
                .getExposureConfiguration().forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions)));
    }

    private void expose(RepositoryRestConfiguration config) {

        // get the entity types from database
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for (EntityType entityType: entities){
            entityClasses.add(entityType.getJavaType());
        }

        // you can pass "0" or any other variables, it will be the same, the size will be defined by the domainType size
        Class[] domainType = entityClasses.toArray(new Class[0]);

        config.exposeIdsFor(domainType);







    }
}
