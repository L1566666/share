package com.share.modules.repository;

import com.share.modules.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lin on 2017/7/8 19:41.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
}
