package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, String> {
}
