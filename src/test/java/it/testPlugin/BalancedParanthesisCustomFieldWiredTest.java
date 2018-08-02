package it.testPlugin;

import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testPlugin.BalancedParanthesisCustomField;

@RunWith(AtlassianPluginsTestRunner.class)
public class BalancedParanthesisCustomFieldWiredTest{

    ApplicationProperties applicationProperties;
    BalancedParanthesisCustomField balancedParanthesisCustomField;

    Logger logger = LoggerFactory.getLogger(BalancedParanthesisCustomFieldWiredTest.class);

    public BalancedParanthesisCustomFieldWiredTest(@ComponentImport ApplicationProperties applicationProperties, @ComponentImport BalancedParanthesisCustomField balancedParanthesisCustomField){
        this.applicationProperties = applicationProperties;
        this.balancedParanthesisCustomField = balancedParanthesisCustomField;
    }

    @Test
    public void test(){
    }

    @Test
    public void testGettersSingular() {
        Assert.assertEquals("(()()())", balancedParanthesisCustomField.getSingularObjectFromString("(()()())"));
        Assert.assertEquals("(()()())", balancedParanthesisCustomField.getStringFromSingularObject("(()()())"));
    }

    @Test
    public void testGettersDb() {
        Assert.assertEquals("((()))", balancedParanthesisCustomField.getDbValueFromObject("((()))"));
        Assert.assertEquals("((()))", balancedParanthesisCustomField.getDbValueFromObject("((()))"));
    }

    @Test(expected=FieldValidationException.class)
    public void test_GetSingularWithUnbalncedParanthesis() {
        balancedParanthesisCustomField.getSingularObjectFromString("((((())");
    }

    @Test
    public void test_getters_and_setters_singular() {
        Assert.assertEquals(null, balancedParanthesisCustomField.getDbValueFromObject(null));
    }
}
