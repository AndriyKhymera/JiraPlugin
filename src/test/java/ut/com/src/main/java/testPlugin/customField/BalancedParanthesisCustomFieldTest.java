package ut.com.src.main.java.testPlugin.customField;

import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testPlugin.BalancedParanthesisCustomField;

import static org.mockito.Mockito.*;


public class BalancedParanthesisCustomFieldTest {

    static BalancedParanthesisCustomField balancedParanthesisCustomField;

    @BeforeClass
    public static void setup() {
        balancedParanthesisCustomField = new BalancedParanthesisCustomField(null, null);
    }

    @After
    public void tearDown() {

    }

    @Test(expected = Exception.class)
    public void testSomething() throws Exception {
        throw new Exception("BalancedParanthesisCustomField has no tests!");
    }

    @Test(expected = FieldValidationException.class)
    public void testUnbalancedParanthesis(){
        String testString = "((((())";
        Assert.assertEquals(testString, balancedParanthesisCustomField.getSingularObjectFromString(testString));
        testString = "()))(";
        Assert.assertEquals(testString, balancedParanthesisCustomField.getSingularObjectFromString(testString));
    }

    @Test
    public void testBalancedParanthesis(){
        String testString = "(()()())";
        Assert.assertEquals(testString, balancedParanthesisCustomField.getSingularObjectFromString(testString));
        testString = "((()))";
        Assert.assertEquals(testString, balancedParanthesisCustomField.getSingularObjectFromString(testString));
        testString = "(()(()()))";
        Assert.assertEquals(testString, balancedParanthesisCustomField.getSingularObjectFromString(testString));
    }
}
