package com.atm.repository;

import com.atm.domain.Note;
import com.atm.enums.Denomination;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

	Note findByType(Denomination denomination);

	int countByType(Denomination denomination);
}
