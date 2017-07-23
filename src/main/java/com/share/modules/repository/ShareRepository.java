package com.share.modules.repository;

import com.share.modules.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lin on 2017/7/20 16:47.
 */
public interface ShareRepository extends JpaRepository<Share, Long> {
}
