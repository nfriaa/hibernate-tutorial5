# hibernate-tutorial5
Hibernate tutorial 5 : **"One To Many"** association

[![contributions welcome](https://img.shields.io/badge/contributions-welcome-orange.svg?style=flat)](https://github.com/nfriaa/hibernate-tutorial5/issues) [![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](https://github.com/nfriaa/hibernate-tutorial5) [![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/nfriaa/hibernate-tutorial5/blob/master/LICENSE)

## Description
A sample code to learn how to map **"One To Many"** relationship between two entities using the Hibernate ORM.
* JavaSE 8
* Hibernate 5 / Annotations
* Hibernate **"One To Many"** association
* Maven 4
* MySQL 5

## 1. Database
Create only database, don't create tables (tables will be created by Hibernate)
* database name : **persist_db**

## 2. Maven "pom.xml" dependencies
```
<dependencies>
    <!-- MySQL connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>6.0.6</version>
    </dependency>
    <!-- Hibernate -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.2.11.Final</version>
    </dependency>
</dependencies>
```

## 3. Hibernate configuration file "hibernate.cfg.xml"
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- MySQL -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/persist_db?useTimezone=true&amp;serverTimezone=UTC</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">root</property>

        <!-- Hibernate -->
        <property name="show_sql">true</property>
        <property name="format_sql">false</property>
        <property name="hibernate.hbm2ddl.auto">create</property>

        <!-- Entities -->
        <mapping class="net.isetjb.hibernatetutorial5.Product"/>
        <mapping class="net.isetjb.hibernatetutorial5.Category"/>
    </session-factory>
</hibernate-configuration>
```
* hibernate.hbm2ddl.auto : "create" => creates the schema necessary for defined entities, destroying any previous data
* don't forget to map the two entities in this XML config file (Product and Category)

## 4. "One To Many" association
`Destination entity : **Product.java**`
```
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 255, nullable = true)
    private String name;

    @Column(name = "price", nullable = true)
    private int price;

    // Getters and Setters here...
}
```

`Source entity : **Category.java**`
```
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 255, nullable = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    // Getters and Setters here...
}
```
* @OneToMany : In **One To Many** association, source entity has a field that stores one or more target entities
* There are two types of **One To Many** association : 
    * Unidirectional : only source entity has a relationship field that refers to the target entity
    * Bidirectional : each entity (i.e. source and target) has a relationship field that refers to each other

## 5. Main Class "Application.java"
* create main class to test the code
* example :
```
    // new product
    Product product_x = new Product();
    product_x.setName("Prod x");
    product_x.setPrice(456);
    session.save(product_x);

    // new product
    Product product_y = new Product();
    product_y.setName("Prod y");
    product_y.setPrice(123);
    session.save(product_y);

    // new product
    Product product_z = new Product();
    product_z.setName("Prod z");
    product_z.setPrice(789);
    session.save(product_z);

    // new category
    Category category_a = new Category();
    category_a.setName("Cat a");
    category_a.getProducts().add(product_x);
    category_a.getProducts().add(product_y);
    session.save(category_a);

    // new category
    Category category_b = new Category();
    category_b.setName("Cat b");
    category_b.getProducts().add(product_z);
    session.save(category_b);
```