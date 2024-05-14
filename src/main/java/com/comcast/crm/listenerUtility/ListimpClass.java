package com.comcast.crm.listenerUtility;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.IClass;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseclassTest;
import com.comcast.crm.webdriverutility.UtilityClassObject;

public class ListimpClass implements ITestListener,ISuiteListener{
	public ExtentSparkReporter spark;
	public  static ExtentReports report;
	public static ExtentTest test;
	public void onStart(ISuite suite)
	{
		System.out.println("Report configuration");
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		ExtentSparkReporter spark=new ExtentSparkReporter("./AdvanceReport/report"+time+".html");
			spark.config().setDocumentTitle("CRM Test Suite Result");
			spark.config().setReportName("CRM Report");
			spark.config().setTheme(Theme.DARK);
			
			//Add Env information& create test
			 report=new ExtentReports();
			report.attachReporter(spark);
			report.setSystemInfo("OS", "Window-10");
			report.setSystemInfo("BROWSER", "CHROME=100");
	}
	public void onFinish(ISuite suite)
	{
		System.out.println("Report backup");
		report.flush();
	}
	public void onTestStart(ITestResult result)
	{
		System.out.println("======"+result.getMethod().getMethodName()+">=====start=======");
		 test=report.createTest(result.getMethod().getMethodName());
		 UtilityClassObject.setTest(test);
		 test.log(Status.INFO, result.getMethod().getMethodName()+"====>STARTED <======");
	}
	public void onTestSucess(ITestResult result)
	{
		System.out.println("======"+result.getMethod().getMethodName()+">=======end======");
		test.log(Status.PASS, result.getMethod().getMethodName()+"====>COMPLETED <======");
	}
	public void onTestFailure(ITestResult result)
	{
		String testName = result.getMethod().getMethodName();
		TakesScreenshot edriver=(TakesScreenshot)BaseclassTest.sdriver;
		String filepath=edriver.getScreenshotAs(OutputType.BASE64);
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
	 test.addScreenCaptureFromBase64String(filepath,testName+"_"+time);
	 test.log(Status.FAIL, result.getMethod().getMethodName()+"====>FAILED <======");	
	}
	public void onTestSkipped(ITestResult result)
	{
		
	}
	}
