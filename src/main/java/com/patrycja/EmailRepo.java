package com.patrycja;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepo extends JpaRepository<EmailModel, Long> {

}
