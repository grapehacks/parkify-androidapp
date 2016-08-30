package com.grapeup.parkify.other;

import com.grapeup.parkify.BuildConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {
    protected TestUtils mUtils;

    @Before
    public void setUp() throws Exception {
        mUtils = new TestUtils();
    }
}
