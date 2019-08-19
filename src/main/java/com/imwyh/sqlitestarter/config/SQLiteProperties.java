package com.imwyh.sqlitestarter.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yonghong
 * @date 2019-08-16
 **/
@Data
@ToString
@ConfigurationProperties(prefix = "imwyh.sqlite")
class SQLiteProperties {

    private String driverClassName = "org.sqlite.JDBC";

    private String url = "jdbc:sqlite:db/test.db";

    private String packageToScan = "";

    private String showSql = "true";

    private String ddlAuto = "update";

    private String dialect = "com.imwyh.sqlitestarter.config.SQLiteDialect";

    private String implicitStrategy = "org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl";

    private String physicalStrategy = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";
}
