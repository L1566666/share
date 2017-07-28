package com.share.modules.repository.dao;

import com.share.modules.entity.SupportOrStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 2017年7月28日13:24:34
 * lin
 */
@Repository
public class SupportRoStepDao {

    @Autowired
    private EntityManager entityManager;

    /**
     * 判断该用户是否已经对该分享做过评价
     * @param support_or_step
     * @param share_id
     * @param userId
     * @return
     */
    public Integer isExist(Integer support_or_step, Long share_id,Long userId){
        String sql = "select * from support_or_step where share_id=? and user_id=? and support_or_step=?";
        Query query = entityManager.createNativeQuery(sql, SupportOrStep.class);
        query.setParameter(1,share_id).setParameter(2, userId).setParameter(3, support_or_step);
        query.setMaxResults(1);
        List<SupportOrStep> list = query.getResultList();
        if(list != null && list.size()!=0){
            return list.get(0).getSupportOrStep();
        }else {
            return null;
        }
    }
    public void shareSupportOrStep(Integer support_or_step, Long share_id){
        String sql = "";
        if(support_or_step == 1){
            sql = "update support_or_step set support_amount=support+1 where share_id=?";
        }else{
            sql = "update support_or_step set step_amount=support+1 where share_id=?";
        }

    }


}
