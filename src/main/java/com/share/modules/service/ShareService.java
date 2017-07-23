package com.share.modules.service;

import com.share.modules.configure.ShareConfigure;
import com.share.modules.entity.Share;
import com.share.modules.repository.dao.ShareDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lin on 2017/7/20 16:51.
 */
@Service
public class ShareService {

    @Autowired
    private ShareDao shareDao;

    /**
     * 2017年7月20日23:00:43
     * lin
     * @param share_type
     * @param page_no
     * @return
     */
    public List<Share> getShareList(Integer share_type, Integer page_no, String search_str){
        if(search_str == null || "".equals(search_str)){
            return shareDao.getShareList(share_type, page_no);
        }else{
            return shareDao.getShareList(share_type, page_no, search_str);
        }

    }

    /**
     * 2017年7月23日09:40:37
     * lin]
     * @param share_type
     * @param search_str
     * @return
     */
    public Long getTotalRow(Integer share_type, String search_str){
        return shareDao.getTotalRow(share_type, search_str);
    }

    /**
     * 2017年7月23日09:42:04
     * lin
     * @param totalRow
     * @return
     */
    public Long getTotalPage(Long totalRow){
        Long pageSize = ShareConfigure.pageSize;
        Long totalPage = 0L;
        if(totalRow != 0L){
            if(totalRow <= pageSize){
                totalPage = 1L;
            }else{
                if(totalRow%pageSize == 0){
                    totalPage = totalRow/pageSize;
                }else{
                    totalPage = totalRow/pageSize + 1;
                }
            }

        }
        return totalPage;
    }

}
