package com.hwb.aianswerer.models

import com.hwb.aianswerer.config.AppConfig
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeepSeekVisionModelTest {

    @Before
    fun setUp() {
        mockkObject(AppConfig)
        every { AppConfig.getDynamicVisionModels() } returns emptyList()
        every { AppConfig.getDynamicVisionExcluded() } returns emptyList()
        ModelCapabilityChecker.invalidateCache()
    }

    @After
    fun tearDown() {
        ModelCapabilityChecker.invalidateCache()
        unmockkObject(AppConfig)
    }

    @Test
    fun `current Flash model supports vision including provider prefixes and uppercase IDs`() {
        assertTrue(ModelCapabilityChecker.isVisionModel("deepseek-flash"))
        assertTrue(ModelCapabilityChecker.isVisionModel("deepseek/DEEPSEEK-FLASH"))
    }

    @Test
    fun `legacy Flash aliases support vision`() {
        assertTrue(ModelCapabilityChecker.isVisionModel("deepseek-v4-flash"))
        assertTrue(ModelCapabilityChecker.isVisionModel("deepseek-v4-flash-vision-exp"))
    }

    @Test
    fun `text models and unknown Flash variants are excluded`() {
        assertFalse(ModelCapabilityChecker.isVisionModel("deepseek-v4-pro"))
        assertFalse(ModelCapabilityChecker.isVisionModel("deepseek-chat"))
        assertFalse(ModelCapabilityChecker.isVisionModel("deepseek-reasoner"))
        assertFalse(ModelCapabilityChecker.isVisionModel("deepseek-flash-unknown"))
    }
}
