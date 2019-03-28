# spring boot jpa 多数据源

## 前言

随着业务量发展，我们通常会进行数据库拆分或是引入其他数据库，从而我们需要配置多个数据源，如：user一个库，business一个库。那么接下来我们就要考虑怎么去在spring boot中实现多个数据源的配置。

## 配置

* 首先，创建一个Spring配置类，定义两个DataSource用来读取application.yml中的不同配置。本文中，我们user做为主数据源，主数据源配置为spring.datasource.user开头的配置，business数据源配置为spring.datasource.business开头的配置。

``` java
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "userDataSource")
    @Qualifier("userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "businessDataSource")
    @Qualifier("businessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource businessDataSource() {
        return DataSourceBuilder.create().build();
    }
}
```

* 对应的配置文件application.properties如下：

```
spring.datasource.def.jdbc-url = jdbc:mysql://10.10.10.57:3560/test1?useUnlcode=1&characterEncoding=utf-8
spring.datasource.def.username = clbuyutest
spring.datasource.def.password = clbuyutest123
spring.datasource.def.driver-class-name = com.mysql.jdbc.Driver
  
  
  
spring.datasource.second.jdbc-url = jdbc:mysql://10.10.10.57:3560/test2?useUnlcode=1&characterEncoding=utf-8
spring.datasource.second.username = clbuyutest
spring.datasource.second.password = clbuyutest123
spring.datasource.second.driver-class-name = com.mysql.jdbc.Driver
```

* 接下来我们对各数据源进行jpa的配置

* 主数据源

``` java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryUser",
        transactionManagerRef = "transactionManagerUser",
        //设置Repository所在位置
        basePackages = {"com.ppc.spring.example.jpamultidatasource.repository.user"})
public class UserConfig {
    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
    @Primary
    @Bean(name = "entityManagerUser")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryUser(builder).getObject().createEntityManager();
    }
    @Primary
    @Bean(name = "entityManagerFactoryUser")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryUser(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource)
                //设置entity所在位置
                .packages("com.ppc.spring.example.jpamultidatasource.entity.user")
                .persistenceUnit("userPersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }
    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
    @Primary
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager transactionManagerUser(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryUser(builder).getObject());
    }
}
```

* 其他数据源

``` java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBusiness",
        transactionManagerRef = "transactionManagerBusiness",
        //设置repository所在位置
        basePackages = {"com.ppc.spring.example.jpamultidatasource.repository.business"})
public class BusinessConfig {
    @Autowired
    @Qualifier("businessDataSource")
    private DataSource businessDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
    @Bean(name = "entityManagerBusiness")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryBusiness(builder).getObject().createEntityManager();
    }
    @Bean(name = "entityManagerFactoryBusiness")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBusiness(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(businessDataSource)
                .properties(getVendorProperties())
                //设置实体类所在位置
                .packages("com.ppc.spring.example.jpamultidatasource.entity.business")
                .persistenceUnit("businessPersistenceUnit")
                .build();
    }
    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
    @Bean(name = "transactionManagerBusiness")
    PlatformTransactionManager transactionManagerBusiness(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryBusiness(builder).getObject());
    }
}
```

* 配置中需要注意以下几点
* Spring Boot 1.5.x

``` 
private Map<String, String> getVendorProperties() {
  return jpaProperties.getHibernateProperties(userDataSource);
}
```

* Spring Boot 2.0.x

```
private Map<String, Object> getVendorProperties() {
  return jpaProperties.getHibernateProperties(new HibernateSettings());
}
```

*详情: https://my.oschina.net/u/3959468/blog/2875232*