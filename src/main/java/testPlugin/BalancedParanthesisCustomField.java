package testPlugin;

import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import java.util.ArrayDeque;
import java.util.Deque;

@Scanned
public class BalancedParanthesisCustomField extends AbstractSingleFieldType<String> {

    @ComponentImport
    CustomFieldValuePersister customFieldValuePersister;
    @ComponentImport
    GenericConfigManager genericConfigManager;

    private static final char OPETING_PARANTHESIS = '(';
    private static final char CLOSING_PARANTHESIS = ')';

    public BalancedParanthesisCustomField(CustomFieldValuePersister customFieldValuePersister, GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
        this.customFieldValuePersister = customFieldValuePersister;
        this.genericConfigManager = genericConfigManager;
    }

    @Override
    protected PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_LIMITED_TEXT;
    }

    @Override
    protected Object getDbValueFromObject(String s) {
        return getStringFromSingularObject(s);
    }

    @Override
    protected String getObjectFromDbValue(Object dbValue) throws FieldValidationException {
        return getSingularObjectFromString((String) dbValue);
    }

    @Override
    public String getStringFromSingularObject(String singularObject) {
        if (singularObject != null) {
            return singularObject.toString();
        }
        return singularObject;
    }

    @Override
    public String getSingularObjectFromString(String s) throws FieldValidationException {
        if (s == null) {
            return null;
        }

        if (areParenthesisBalanced(s.toCharArray())) {
            return s;
        } else {
            throw new FieldValidationException("Unbalanced patanthesis");
        }
    }

    static boolean isMatchingPair(char character1, char character2) {
        if (character1 == OPETING_PARANTHESIS && character2 == CLOSING_PARANTHESIS)
            return true;
        else
            return false;
    }

    private static boolean areParenthesisBalanced(char exp[]) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == OPETING_PARANTHESIS)
                stack.addFirst(exp[i]);
            if (exp[i] == CLOSING_PARANTHESIS) {
                if (stack.isEmpty()) {
                    return false;
                } else if (!isMatchingPair(stack.pop(), exp[i])) {
                    return false;
                }
            }
        }

        if (stack.isEmpty())
            return true; /*balanced*/
        else {   /*not balanced*/
            return false;
        }
    }
}