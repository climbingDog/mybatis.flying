package indi.mybatis.flying.test;

import java.util.Collection;

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
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

import indi.mybatis.flying.pojo.LogStatus;
import indi.mybatis.flying.pojo.LoginLog_;
import indi.mybatis.flying.pojo.condition.LoginLogSource2Condition;
import indi.mybatis.flying.pojo.condition.LoginLog_Condition;
import indi.mybatis.flying.service.LoginLogService;
import indi.mybatis.flying.service2.LoginLogSource2Service;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class, databaseConnection = { "dataSource1", "dataSource2" })
@ContextConfiguration("classpath:spring-test.xml")
public class OrTest {

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private LoginLogSource2Service loginLogSource2Service;

	@Test
	public void test1() {
		Assert.assertNotNull(loginLogService);
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/indi/mybatis/flying/test/orTest/testOr1.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/indi/mybatis/flying/test/orTest/testOr1.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/indi/mybatis/flying/test/orTest/testOr1.result.xml")
	public void testOr1() {
		LoginLog_Condition lc1 = new LoginLog_Condition();
		lc1.setLoginIPHeadLikeOr("a", "b");
		lc1.setIpLikeFilter("1");
		Collection<LoginLog_> LoginLogC = loginLogService.selectAll(lc1);
		Assert.assertEquals(2, LoginLogC.size());

		LoginLog_Condition lc2 = new LoginLog_Condition();
		lc2.setLoginIPLikeOr("b1", "c1");
		Collection<LoginLog_> LoginLogC2 = loginLogService.selectAll(lc2);
		Assert.assertEquals(2, LoginLogC2.size());

		int c2 = loginLogService.count(lc2);
		Assert.assertEquals(2, c2);

		LoginLog_Condition lc3 = new LoginLog_Condition();
		lc3.setLoginIPHeadLikeOrTailLike("a", "2");
		int c3 = loginLogService.count(lc3);
		Assert.assertEquals(2, c3);

		LoginLog_Condition lc4 = new LoginLog_Condition();
		lc4.setLoginIPEqualsOr("a1", "z3");
		int c4 = loginLogService.count(lc4);
		Assert.assertEquals(2, c4);

		LoginLog_Condition lc5 = new LoginLog_Condition();
		lc5.setLoginIPLikeOr("a1", "b1");
		lc5.setLoginIPEqualsOr("b1", "c1");
		int c5 = loginLogService.count(lc5);
		Assert.assertEquals(1, c5);

		LoginLog_Condition lc6 = new LoginLog_Condition();
		lc6.setNumEqualsOr(1, 2);
		int c6 = loginLogService.count(lc6);
		Assert.assertEquals(2, c6);

		LoginLog_Condition lc7 = new LoginLog_Condition();
		lc7.setNumEqualsOrLoginIPLike(1, "b1");
		Collection<LoginLog_> LoginLogC7 = loginLogService.selectAll(lc7);
		Assert.assertEquals(2, LoginLogC7.size());

		LoginLog_ l1 = new LoginLog_();
		l1.setLoginIP("a1");
		LoginLog_ loginLog_1 = loginLogService.selectOne(l1);

		LoginLog_ l2 = new LoginLog_();
		l2.setLoginIP("b1");
		LoginLog_ loginLog_2 = loginLogService.selectOne(l2);

		LoginLog_Condition lc8 = new LoginLog_Condition();
		lc8.setLoginTimeEqualsOr(loginLog_1.getLoginTime(), loginLog_2.getLoginTime());
		int c8 = loginLogService.count(lc8);
		Assert.assertEquals(2, c8);

		LoginLog_Condition lc9 = new LoginLog_Condition();
		lc9.setStatusEqualsOr(LogStatus.b, LogStatus.t);
		int c9 = loginLogService.count(lc9);
		Assert.assertEquals(2, c9);
	}

//	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/indi/mybatis/flying/test/orTest/testOr2.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/indi/mybatis/flying/test/orTest/testOr2.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/indi/mybatis/flying/test/orTest/testOr2.result.xml")
	public void testOr2() {
		LoginLogSource2Condition lc1 = new LoginLogSource2Condition();
		lc1.setAccountIdEqualsOr(1, 2);
		int c1 = loginLogSource2Service.count(lc1);
		Assert.assertEquals(2, c1);
	}
}
