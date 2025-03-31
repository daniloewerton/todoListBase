package com.daniloewerton.todolist;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

//@RunWith(Cucumber.class)
//@CucumberOptions(plugin = {"pretty", "json:build/cucumber-api-report.json"}, features = "classpath:features")
@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
class ToDoListApplicationTest {

}
