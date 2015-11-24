dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = false
  cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {

    development {
//        dataSource {
//            dbCreate = "update"
//            driverClassName = "com.mysql.jdbc.Driver"
//            dialect=org.hibernate.dialect.MySQL5Dialect
//            username = "k-int"
//            password = "k-int"
//            url = "jdbc:mysql://localhost/GoKBLabsDev?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
//            pooled = true
//            configClass = 'com.k_int.KIGormConfiguration'
//            properties {
//                maxActive = 500
//                minEvictableIdleTimeMillis=1800000
//                timeBetweenEvictionRunsMillis=1800000
//                numTestsPerEvictionRun=3
//                testOnBorrow=true
//                testWhileIdle=true
//                testOnReturn=true
//                validationQuery="select 1"
//                defaultTransactionIsolation = java.sql.Connection.TRANSACTION_SERIALIZABLE
//            }
//        }
        dataSource {
          dbCreate = "update"
          driverClassName = "org.postgresql.Driver"
          dialect = org.hibernate.dialect.PostgreSQLDialect
          username = "k-int"
          password = "k-int"
          url = "jdbc:postgresql://localhost:5432/GOKb"
          pooled = true
//          formatSql=true
//          logSql=true
          properties {
              maxActive = 500
              minEvictableIdleTimeMillis=1800000
              timeBetweenEvictionRunsMillis=1800000
              numTestsPerEvictionRun=3
              testOnBorrow=true
              testWhileIdle=true
              testOnReturn=true
              validationQuery="select 1"
              defaultTransactionIsolation = java.sql.Connection.TRANSACTION_SERIALIZABLE
          }
      }
    }
    test {
      dataSource {
        // dbCreate = "update"
        dbCreate = "create"
        driverClassName = "com.mysql.jdbc.Driver"
        dialect=org.hibernate.dialect.MySQL5Dialect
        username = "k-int"
        password = "k-int"
        url = "jdbc:mysql://localhost/GoKBLabsTest?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
        pooled = true
        // logSql = true
        // formatSql = true
        properties {
            maxActive = 500
            minEvictableIdleTimeMillis=1800000
            timeBetweenEvictionRunsMillis=1800000
            numTestsPerEvictionRun=3
            testOnBorrow=true
            testWhileIdle=true
            testOnReturn=true
            validationQuery="select 1"
        }
      }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            dialect=org.hibernate.dialect.MySQL5Dialect
            username = "k-int"
            password = "k-int"
            url = "jdbc:mysql://localhost/GoKBLabsProd?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
            pooled = true
            // logSql = true
            // formatSql = true
            properties {
                maxActive = 500
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="select 1"
            }
        }
    }
    functional {
        dataSource {
            dbCreate = "create"
            driverClassName = "com.mysql.jdbc.Driver"
            dialect=org.hibernate.dialect.MySQL5Dialect
            username = "k-int"
            password = "k-int"
            url = "jdbc:mysql://localhost/GoKBFunctional?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"
            pooled = true
            properties {
                maxActive = 500
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="select 1"
            }
        }
    }
    PGfunctional {
        dataSource {
            dbCreate = "create"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            username = "knowint"
            password = "knowint"
            url = "jdbc:postgresql://localhost:5432/GoKBFunctional"
            pooled = true
            formatSql=true
            logSql=true
            properties {
                maxActive = 500
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="select 1"
            }
        }
    }


}
