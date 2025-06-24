package test.web;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class PerformanceTest {
    private static final String BASE_URL = "https://torano-clone-bluet52hz.web.app";
    private StandardJMeterEngine jmeter;
    private HashTree testPlanTree;

    @Before
    public void setUp() {
        // Set JMeter properties
        File jmeterHome = new File(System.getProperty("user.dir"));
        String slash = System.getProperty("file.separator");
        
        JMeterUtils.loadJMeterProperties(jmeterHome.getPath() + slash + "jmeter.properties");
        JMeterUtils.setJMeterHome(jmeterHome.getPath());
        JMeterUtils.initLocale();

        jmeter = new StandardJMeterEngine();
        testPlanTree = new HashTree();
    }

    @Test
    public void testHomepageLoadTime() {
        // Create Test Plan
        TestPlan testPlan = new TestPlan("Test Homepage Load Time");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        // Create HTTP Sampler
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain(BASE_URL);
        httpSampler.setPort(443);
        httpSampler.setProtocol("https");
        httpSampler.setPath("/");
        httpSampler.setMethod("GET");
        httpSampler.setName("Homepage Request");
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        // Set up Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(50);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        // Thread Group
        org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
        threadGroup.setName("Homepage Test Thread Group");
        threadGroup.setNumThreads(10);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, org.apache.jmeter.threads.ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        // Build Test Plan
        HashTree threadGroupHashTree = testPlanTree.add(testPlan)
            .add(threadGroup);
        threadGroupHashTree.add(httpSampler);

        // Add Summariser
        Summariser summer = new Summariser();
        ResultCollector logger = new ResultCollector(summer);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        // Run Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    @Test
    public void testProductPageLoadTime() {
        // Tương tự testHomepageLoadTime nhưng với đường dẫn /shop
        TestPlan testPlan = new TestPlan("Test Product Page Load Time");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setDomain(BASE_URL);
        httpSampler.setPort(443);
        httpSampler.setProtocol("https");
        httpSampler.setPath("/shop");
        httpSampler.setMethod("GET");
        httpSampler.setName("Product Page Request");
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        LoopController loopController = new LoopController();
        loopController.setLoops(50);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
        threadGroup.setName("Product Page Test Thread Group");
        threadGroup.setNumThreads(10);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, org.apache.jmeter.threads.ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        HashTree threadGroupHashTree = testPlanTree.add(testPlan)
            .add(threadGroup);
        threadGroupHashTree.add(httpSampler);

        Summariser summer = new Summariser();
        ResultCollector logger = new ResultCollector(summer);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    @Test
    public void testProductDetailLoadTime() {
        // Similar setup as homepage test but for product detail page
        // ... Implementation similar to testHomepageLoadTime but with /product/[id] path
    }

    // Nếu có kiểm thử UI bằng Selenium thì chuyển sang ChromeDriver tương tự các file khác
}
