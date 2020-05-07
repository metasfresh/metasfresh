package de.metas.ui.web.window.datatypes.json;

import org.junit.Assert;
import org.junit.Test;

import de.metas.ui.web.test.util.EnumTestUtils;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONLookupSource;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONLookupSourceTest
{
	@Test
	public void test_fromNullable_fullyCovered()
	{
		EnumTestUtils.assertMappingFullyCovered(LookupSource.values(), JSONLookupSource::fromNullable);
	}
	
	@Test
	public void test_fromNullable()
	{
		Assert.assertNull(JSONLookupSource.fromNullable((LookupSource)null));
		Assert.assertSame(JSONLookupSource.list, JSONLookupSource.fromNullable(LookupSource.list));
		Assert.assertSame(JSONLookupSource.lookup, JSONLookupSource.fromNullable(LookupSource.lookup));
	}

}
