package com.github.voltorane.urlhashcodeinspectionplugin

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class UrlHashcodeInspectionTest : LightJavaCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun setUp() {
        super.setUp()
        myFixture.enableInspections(UrlHashcodeInspection())
        myFixture.addClass("package java.net; public final class URL { @Override public int hashCode() { return 0; } }")
        myFixture.addClass("package java.lang; public final class Integer { @Override public int hashCode() { return 0; } } ")
    }

    fun testJavaUrlHashcode() {
        myFixture.configureByFile("UrlHashcodeTest.java")
        myFixture.enableInspections(UrlHashcodeInspection())
        val highlightInfos = myFixture.doHighlighting();
        // there should be at least one highlight about the url hashcode
        assertTrue(highlightInfos.any { it.description == InspectionBundle.message("display.description")})
    }

    fun testIntegerHashcode() {
        myFixture.configureByFile("IntegerHashcodeTest.java")
        myFixture.enableInspections(UrlHashcodeInspection())
        val highlightInfos = myFixture.doHighlighting();
        // there shouldn't be any highlights with the url hashcode
        assertFalse(highlightInfos.any { it.description == InspectionBundle.message("display.description")})
    }
}