package com.share.modules.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Created by lin on 2017/7/6 10:48.
 */
@Repository
public class StaticEntityManager {

    @Autowired
    private static EntityManager entityManager;

    @Autowired
    public void setEntityManager1(EntityManager entityManager) {
        StaticEntityManager.entityManager = entityManager;
    }
    /**
     * lin
     * 2017年7月6日10:50:12
     * @return
     */

    public static EntityManager getEntityManger(){ return entityManager; }

}
