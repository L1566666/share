package com.share.modules.service;

import com.share.modules.repository.dao.SupportOrStepDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2017年7月28日13:26:27
 */
@Service
public class SupportOrStepService {

    @Autowired
    private SupportOrStepDao supportRoStepDao;

    /**
     * 2017年7月28日14:59:47
     * lin
     * @param support_or_step
     * @param share_id
     * @param userId
     * @return
     */
    public Integer isExist(Long share_id,Long userId){
        return supportRoStepDao.isExist(share_id,userId);
    }

    /**
     * 2017年7月28日15:28:01
     * lin
     * @param support_or_step
     * @param share_id
     */
    public void shareSupportOrStep(Integer support_or_step, Long share_id){
        supportRoStepDao.shareSupportOrStep(support_or_step,share_id);
    }


}
