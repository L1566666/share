package com.share.modules.repository;

import com.share.modules.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lin on 2017/7/6.
 */
public interface UserRepository extends JpaRepository<User ,Long> {
}
