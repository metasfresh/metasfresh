package de.metas.dataentry.data;

import static de.metas.dataentry.data.DataEntryRecordTestConstants.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.user.UserId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubGroupId;

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

public class DataEntryRecordTest
{

	@Test
	public void clearRecordFields()
	{
		final DataEntrySubGroupId dataEntrySubGroupId = DataEntrySubGroupId.ofRepoId(10);
		final DataEntryRecord dataEntryRecord = DataEntryRecord
				.builder()
				.dataEntrySubGroupId(dataEntrySubGroupId)
				.mainRecord(TableRecordReference.of(I_M_Product.Table_Name, 41))
				.fields(DataEntryRecordTestConstants.SIMPLE_DATA_ENTRY_FIELD_DATA)
				.build();

		assertThat(dataEntryRecord.isEmpty()).isFalse(); // guard

		// invoke method under test
		dataEntryRecord.clearRecordFields();

		assertThat(dataEntryRecord.isEmpty()).isTrue();
	}

	@Test
	public void setRecordField()
	{
		final DataEntrySubGroupId dataEntrySubGroupId = DataEntrySubGroupId.ofRepoId(10);
		final DataEntryRecord dataEntryRecord = DataEntryRecord
				.builder()
				.dataEntrySubGroupId(dataEntrySubGroupId)
				.mainRecord(TableRecordReference.of(I_M_Product.Table_Name, 41))
				.fields(ImmutableList.of())
				.build();

		final DataEntryFieldId fieldId1 = DataEntryFieldId.ofRepoId(1);
		final DataEntryFieldId fieldId2 = DataEntryFieldId.ofRepoId(2);
		final DataEntryFieldId fieldId3 = DataEntryFieldId.ofRepoId(3);
		final DataEntryFieldId fieldId4 = DataEntryFieldId.ofRepoId(4);
		final DataEntryFieldId fieldId5 = DataEntryFieldId.ofRepoId(5);
		final DataEntryFieldId fieldId6 = DataEntryFieldId.ofRepoId(6);

		// invoke method under test
		dataEntryRecord.setRecordField(fieldId1, UserId.ofRepoId(20), null);
		dataEntryRecord.setRecordField(fieldId2, UserId.ofRepoId(20), "text");
		dataEntryRecord.setRecordField(fieldId3, UserId.ofRepoId(20), "longText");
		dataEntryRecord.setRecordField(fieldId4, UserId.ofRepoId(20), true);
		dataEntryRecord.setRecordField(fieldId5, UserId.ofRepoId(20), new BigDecimal("15"));
		dataEntryRecord.setRecordField(fieldId6, UserId.ofRepoId(20), DATE_TIME);

		assertThat(dataEntryRecord.getFields()).isNotEmpty();
		assertThat(dataEntryRecord.getFields()).doesNotContainNull();

		final ImmutableMap<DataEntryFieldId, DataEntryRecordField<?>> resultMap = Maps.uniqueIndex(dataEntryRecord.getFields(), DataEntryRecordField::getDataEntryFieldId);

		assertThat(resultMap.get(fieldId1)).isNull();
		assertThat(resultMap.get(fieldId2).getValue()).isEqualTo("text");
		assertThat(resultMap.get(fieldId3).getValue()).isEqualTo("longText");
		assertThat(resultMap.get(fieldId4).getValue()).isEqualTo(true);
		assertThat(resultMap.get(fieldId5).getValue()).isEqualTo(new BigDecimal("15"));
		assertThat(resultMap.get(fieldId6).getValue()).isEqualTo(DATE_TIME);
	}
}
