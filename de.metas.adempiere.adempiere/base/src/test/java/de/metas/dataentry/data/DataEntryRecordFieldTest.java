package de.metas.dataentry.data;

import static de.metas.dataentry.data.DataEntryRecordTestConstants.CREATED_UPDATED_INFO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Test;

import de.metas.dataentry.DataEntryFieldId;

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
	public void createDataEntryRecordField_ZonedDateTime()
	{
		final DataEntryRecordField<ZonedDateTime> result1 = DataEntryRecordField.createDataEntryRecordField(DataEntryFieldId.ofRepoId(1), CREATED_UPDATED_INFO, DataEntryRecordTestConstants.DATE_TIME);
		assertThat(result1.getValue()).isEqualTo(DataEntryRecordTestConstants.DATE_TIME);
	}
}
