package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.usermodel.ContributionPanel;

public interface ContributionPanlRepository extends JpaRepository<ContributionPanel, Integer>{

}
