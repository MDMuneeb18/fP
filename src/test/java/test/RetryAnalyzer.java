package test;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	int retryCount = 0;
	public static final int maxRetryCount = 2;
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount < maxRetryCount ) {
			retryCount++;
			return true;
		}
		return false;
	}
}
