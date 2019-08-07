package ru.mail.kdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mail.kdog.dto.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

}
