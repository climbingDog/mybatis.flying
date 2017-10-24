package indi.mybatis.flying.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DatabaseTearDowns;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

import indi.mybatis.flying.pojo.Detail2_;
import indi.mybatis.flying.service2.Detail2Service;
import indi.mybatis.flying.service2.LoginLogSource2Service;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class, databaseConnection = { "dataSource2" })
@ContextConfiguration("classpath:spring-test.xml")
public class IgnoreInsertAndUpdateTest {
	@Autowired
	private Detail2Service detail2Service;

	@Autowired
	private LoginLogSource2Service loginLogSource2Service;

	@Test
	public void test1() {
		Assert.assertNotNull(detail2Service);
	}

	@Test
	@DatabaseSetups({
			@DatabaseSetup(connection = "dataSource2", type = DatabaseOperation.CLEAN_INSERT, value = "/indi/mybatis/flying/test/ignoreInsertAndUpdateTest/testInsert.datasource2.xml") })
	@ExpectedDatabases({
			@ExpectedDatabase(connection = "dataSource2", override = false, assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/indi/mybatis/flying/test/ignoreInsertAndUpdateTest/testInsert.datasource2.result.xml") })
	@DatabaseTearDowns({
			@DatabaseTearDown(connection = "dataSource2", type = DatabaseOperation.DELETE_ALL, value = "/indi/mybatis/flying/test/ignoreInsertAndUpdateTest/testInsert.datasource2.result.xml") })
	public void testInsert() {
		Detail2_ d = new Detail2_(), d2 = new Detail2_();
		d.setName("n");
		d.setNumber(123);
		d.setDetail("d");
		detail2Service.insertWithoutName(d);

		d2.setName("n2");
		d2.setNumber(234);
		d2.setDetail("d2");
		detail2Service.insertWithoutFoo(d2);
	}
}
