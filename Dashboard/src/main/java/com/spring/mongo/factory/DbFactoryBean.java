package com.spring.mongo.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class DbFactoryBean implements FactoryBean<DB> {
   
       private Mongo mongo;
       private String name;
   
       public DB getObject() throws Exception {
    	   System.out.println("Getting Mongo Object: "+name);
           Assert.notNull(mongo);
           Assert.notNull(name);
           return mongo.getDB(name);
       }
  
     
      public Class<?> getObjectType() {
          return DB.class;
      }
  
      
      public boolean isSingleton() {
          return true;
      }
  
      @Required
      public void setMongo(Mongo mongo) {
          this.mongo = mongo;
      }

      @Required   
      public void setName(String name) {
          this.name = name;
      }
}