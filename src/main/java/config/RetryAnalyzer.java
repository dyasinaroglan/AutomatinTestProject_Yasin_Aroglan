package config;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            System.out.println("Test tekrar çalıştırılıyor: "
                    + result.getName()
                    + " | Deneme: " + retryCount);
            return true;
        }
        return false;
    }
}
