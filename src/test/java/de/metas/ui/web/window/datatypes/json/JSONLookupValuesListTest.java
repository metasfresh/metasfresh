package de.metas.ui.web.window.datatypes.json;

import org.junit.Test;

import de.metas.ui.web.window.datatypes.LookupValuesList;

public class JSONLookupValuesListTest
{
	@Test
	public void test_EMPTY_toString()
	{
		JSONLookupValuesList lookupValuesList = JSONLookupValuesList.ofLookupValuesList(LookupValuesList.EMPTY, "en_US");
		lookupValuesList.toString();
	}
}
