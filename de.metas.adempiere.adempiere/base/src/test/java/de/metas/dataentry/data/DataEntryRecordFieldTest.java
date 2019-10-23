package de.metas.dataentry.data;

import static de.metas.dataentry.data.DataEntryRecordTestConstants.CREATED_UPDATED_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.compiere.util.TimeUtil;
import org.junit.Test;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.i18n.TranslatableStrings;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DataEntryRecordFieldTest
{
	@Test(expected = NullPointerException.class)
	public void createDataEntryRecordField_nonNull()
	{
		DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, null);
	}

	@Test
	public void createDataEntryRecordField_String()
	{
		final DataEntryRecordField<String> result1 = DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, "string");
		assertThat(result1.getValue()).isEqualTo("string");
	}

	@Test
	public void createDataEntryRecordField_Bool()
	{
		final DataEntryRecordField<Boolean> result1 = DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, true);
		assertThat(result1.getValue()).isEqualTo(true);
	}

	@Test
	public void createDataEntryRecordField_BigDecimal()
	{
		final DataEntryRecordField<BigDecimal> result1 = DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, new BigDecimal("15"));
		assertThat(result1.getValue()).isEqualTo(new BigDecimal("15"));
	}

	@Test
	public void createDataEntryRecordField_LocalDate()
	{
		final DataEntryRecordField<LocalDate> result1 = DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, DataEntryRecordTestConstants.DATE);
		assertThat(result1.getValue()).isEqualTo(DataEntryRecordTestConstants.DATE);
	}

	@Test
	public void convertValueToFieldType_MakeSureCoversPrimitiveDataTypes()
	{
		final ImmutableMultimap<Class<?>, Object> testValuesByClass = ImmutableMultimap.<Class<?>, Object> builder()
				.put(Integer.class, 1234)
				.put(Integer.class, "1234")
				.put(Integer.class, BigDecimal.valueOf(1234))
				//
				.put(String.class, "some dummy")
				.put(String.class, 111)
				.put(String.class, new BigDecimal("1234.55"))
				//
				.put(BigDecimal.class, new BigDecimal("123.456"))
				.put(BigDecimal.class, "123.456")
				.put(BigDecimal.class, 123)
				//
				.put(Boolean.class, Boolean.TRUE)
				.put(Boolean.class, "Y")
				.put(Boolean.class, "N")
				.put(Boolean.class, "true")
				.put(Boolean.class, "false")
				//
				.put(LocalDate.class, LocalDate.of(2019, Month.JULY, 3))
				.put(LocalDate.class, "2019-07-03")
				.put(LocalDate.class, TimeUtil.asTimestamp(LocalDate.of(2019, Month.JULY, 3)))
				//
				.build();

		for (final FieldType fieldType : FieldType.values())
		{
			// Skip types not handled by this test
			if (FieldType.LIST.equals(fieldType))
			{
				continue;
			}

			final DataEntryField field = DataEntryField.builder()
					.id(DataEntryFieldId.ofRepoId(123))
					.caption(TranslatableStrings.anyLanguage("caption"))
					.description(TranslatableStrings.anyLanguage("description"))
					.type(fieldType)
					.build();

			final Class<?> valueType = fieldType.getClazz();

			final ImmutableCollection<Object> testValues = testValuesByClass.get(valueType);
			assertThat(testValues)
					.as("Test values shall exist for " + valueType + " - " + field)
					.isNotEmpty();

			for (final Object value : testValues)
			{
				final Object valueConv = DataEntryRecordField.convertValueToFieldType(value, field);
				assertThat(valueConv).isInstanceOf(valueType);
			}
		}
	}

	@Test
	public void convertValueToFieldType_ListDataType()
	{
		final DataEntryField field = DataEntryField.builder()
				.id(DataEntryFieldId.ofRepoId(123))
				.caption(TranslatableStrings.anyLanguage("caption"))
				.description(TranslatableStrings.anyLanguage("description"))
				.type(FieldType.LIST)
				.listValue(DataEntryListValue.builder()
						.id(DataEntryListValueId.ofRepoId(1))
						.name(TranslatableStrings.anyLanguage("item1"))
						.build())
				.listValue(DataEntryListValue.builder()
						.id(DataEntryListValueId.ofRepoId(2))
						.name(TranslatableStrings.anyLanguage("item2"))
						.build())
				.build();

		assertThat(DataEntryRecordField.convertValueToFieldType(1, field))
				.isEqualTo(DataEntryListValueId.ofRepoId(1));
		assertThat(DataEntryRecordField.convertValueToFieldType("1", field))
				.isEqualTo(DataEntryListValueId.ofRepoId(1));
		assertThat(DataEntryRecordField.convertValueToFieldType("item1", field))
				.isEqualTo(DataEntryListValueId.ofRepoId(1));

		assertThat(DataEntryRecordField.convertValueToFieldType(2, field))
				.isEqualTo(DataEntryListValueId.ofRepoId(2));
		assertThat(DataEntryRecordField.convertValueToFieldType("2", field))
				.isEqualTo(DataEntryListValueId.ofRepoId(2));
		assertThat(DataEntryRecordField.convertValueToFieldType("item2", field))
				.isEqualTo(DataEntryListValueId.ofRepoId(2));
	}

}
