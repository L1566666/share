package com.share.modules.controller;

import com.share.modules.entity.Share;
import com.share.modules.repository.ShareRepository;
import com.share.modules.service.ShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lin on 2017/7/20 17:00.
 */
@RestController
@RequestMapping(value = "share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareRepository shareRepository;

    private static final Logger logger = Logger.getLogger(ShareController.class);

    /**
     * 2017年7月20日21:33:02
     * lin
     * @param request
     * @return
     */
    @RequestMapping(value = "save")
    public Map<String, Object> save(HttpServletRequest request, String share_describe,String share_url,
                                    Integer share_type){

        Map<String,Object> data = new HashMap<String, Object>();
        if(!(share_type==1 || share_type==2|| share_type==3)){
            data.put("resultCode",0);
            data.put("message","保存失败,share类型不正确");
            return data;
        }
        //验证url是否正确
        Pattern pattern = Pattern.compile("((http|ftp|https):\\/\\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
        Matcher matcher = pattern.matcher(share_url);
        if(matcher.matches() == false){
            data.put("resultCode",0);
            data.put("message","url地址格式错误!");
            return data;
        }

        Long userId = Long.valueOf(request.getSession().getAttribute("userId").toString());
        String email = request.getSession().getAttribute("email").toString();

        Share share = new Share(userId, email,share_describe,share_url,0L, 0L, share_type);
        Date date = new Date();
        share.setCreateDate(date);

        shareRepository.save(share);
        logger.debug("一条分享保存成功,id为:"+share.getId());
        data.put("resultCode", 1);
        data.put("message","保存成功!");

        return data;
    }

    /**
     * 2017年7月20日22:48:10
     * lin
     * @return
     */
    @RequestMapping(value = "list")
    public Map<String,Object> list(Integer share_type, Integer page_no, String search_str){

        Map<String,Object> data = new HashMap<String, Object>();

        if(page_no == null || page_no <= 0){
            data.put("resultCode", 0);
            data.put("message", "查看失败,页码格式不正确!");
            return data;
        }
        //shareType 为0时代表查看所有分类
        if(!(share_type == 0 || share_type == 1 || share_type == 2|| share_type == 3)){
            data.put("resultCode",0);
            data.put("message","查看失败,share类型不正确");
            return data;
        }

        List<Share> list = shareService.getShareList(share_type, page_no, search_str);
        Map<String,Object> shareList = new HashMap<String, Object>();
        //获取分页信息
        Long totalRow =  shareService.getTotalRow(share_type, search_str);
        Long totalPage = shareService.getTotalPage(totalRow);

        data.put("totalPage", totalPage);
        data.put("totalRow", totalRow);
        data.put("shareList", list);
        data.put("resultCode", 1);
        data.put("message", "获取成功!");
        return data;

    }

}
