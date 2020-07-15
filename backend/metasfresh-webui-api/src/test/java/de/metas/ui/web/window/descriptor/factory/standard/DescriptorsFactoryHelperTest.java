package de.metas.ui.web.window.descriptor.factory.standard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class DescriptorsFactoryHelperTest
{
	@Nested
	public class getValueClass
	{
		private LookupDescriptor newLookupDescritor(final Class<?> valueClass)
		{
			final LookupDescriptor lookupDescriptor = Mockito.mock(LookupDescriptor.class);
			Mockito.doReturn(valueClass).when(lookupDescriptor).getValueClass();
			return lookupDescriptor;
		}

		@Test
		public void multiValuesList()
		{
			final LookupDescriptor lookupDescriptor = newLookupDescritor(LookupValue.class);

			assertThat(DescriptorsFactoryHelper.getValueClass(DocumentFieldWidgetType.MultiValuesList, Optional.of(lookupDescriptor)))
					.isEqualTo(LookupValuesList.class);
		}
	}
}
