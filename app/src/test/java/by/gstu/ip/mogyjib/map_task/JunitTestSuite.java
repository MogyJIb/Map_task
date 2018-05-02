package by.gstu.ip.mogyjib.map_task;


import by.gstu.ip.mogyjib.map_task.tests.GooglePlacesApiUrlTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GooglePlacesApiUrlTest.class
})
public class JunitTestSuite {
    public static final double DELTA = 1e-5;
}
