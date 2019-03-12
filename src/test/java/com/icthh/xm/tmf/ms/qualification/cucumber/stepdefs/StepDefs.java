package com.icthh.xm.tmf.ms.qualification.cucumber.stepdefs;

import com.icthh.xm.tmf.ms.qualification.QualificationApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = QualificationApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
