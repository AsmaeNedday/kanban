package com.ndy.kanban;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.ndy.kanban.dao.DeveloperRepository;
import com.ndy.kanban.domain.Developer;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")

public class DeveloperRepositoryTest
{
    @Autowired
    private DeveloperRepository developerRepository;

    @Test
    public void testFindAllDeveloper() {

        Collection<Developer> developerList = this.developerRepository.findAll();

        Assert.assertEquals(2, developerList.size());
    }

}
