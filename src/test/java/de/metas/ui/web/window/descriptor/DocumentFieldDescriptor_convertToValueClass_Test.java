package de.metas.ui.web.window.descriptor;

import org.junit.Assert;
import org.junit.Test;

import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DocumentFieldDescriptor_convertToValueClass_Test
{
	@Test
	public void test_FromBigDecimalString_ToInteger()
	{
		final String fieldName = "MyInteger";
		final Object value = "12345.00000000000";
		final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Integer;
		final Class<?> targetType = widgetType.getValueClass();
		final LookupValueByIdSupplier lookupDataSource= null;
		
		final Object valueConverted = DocumentFieldDescriptor.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);
		Assert.assertEquals(12345, valueConverted);
	}
}
