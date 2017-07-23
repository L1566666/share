package com.share.modules.repository.dao;

import com.share.modules.entity.Share;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by lin on 2017/7/20 16:49.
 */
@Repository
public class ShareDao {

    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(ShareDao.class);

    /**
     * 2017年7月20日23:00:07
     * lin
     * @param share_type
     * @param page_no
     * @return
     */
    public List<Share> getShareList(Integer share_type, Integer page_no){
        Query query = null;
        String sql = "";
        if(share_type == 0){
            sql = "select * from share order by create_date DESC";
            query = entityManager.createNativeQuery(sql, Share.class);
            query.setFirstResult((page_no-1)*10);
            query.setMaxResults(10);
        }else{
            sql =  "select * from share where share_type=?  order by create_date DESC";
            query = entityManager.createNativeQuery(sql, Share.class);
            query.setParameter(1,share_type);
            query.setFirstResult((page_no-1)*10);
            query.setMaxResults(10);
        }
        List<Share> list = query.getResultList();
        return list;
    }

    /**
     *2017年7月22日19:45:05
     * lin
     * 根绝检索字符进行检索
     * @param share_type
     * @param page_no
     * @param search_str
     * @return
     */
    public List<Share> getShareList(Integer share_type, Integer page_no, String search_str){
        Query query = null;
        String sql = "";
        if(share_type == 0){
            sql = "select * from share where share_subject like '%" +search_str+ "%' or share_describe like '%" +search_str+ "%'  order by create_date DESC";
            query = entityManager.createNativeQuery(sql, Share.class);

            query.setFirstResult((page_no-1)*10);
            query.setMaxResults(10);
        }else{
            sql =  "select * from share where share_type=? and share_subject like '%" +search_str+ "%' or share_describe like '%" +search_str+ "%' order by create_date DESC";
            query = entityManager.createNativeQuery(sql, Share.class);
            query.setParameter(1,share_type);

            query.setFirstResult((page_no-1)*10);
            query.setMaxResults(10);
        }
        List<Share> list = query.getResultList();
        System.out.println("最大条数"+query.getMaxResults());
        return list;
    }

    /**
     * 2017年7月22日23:37:02
     * lin
     * @param share_type
     * @param search_str
     * @return
     */
    public Long getTotalRow(Integer share_type, String search_str){
        StringBuffer sql = new StringBuffer("");
        sql.append("select count(*) from share where 1=1 ");

        if(share_type!=0){
            sql.append(" and share_type="+share_type);
        }
        if(search_str !=null && !"".equals(search_str)){
            sql.append(" and share_subject like '%" +search_str+ "%' or share_describe like '%" +search_str+ "%'");
        }
        Query query = entityManager.createNativeQuery(sql.toString());
        List list = query.getResultList();
        Long totalRow = 0L;
        if(list!=null && list.size()!=0){
            try{
                totalRow = Long.valueOf(list.get(0).toString());
            }catch (Exception e){
                logger.error("统计总行数时类型转化失败!"+e);
            }
        }

        return totalRow;
    }

}
