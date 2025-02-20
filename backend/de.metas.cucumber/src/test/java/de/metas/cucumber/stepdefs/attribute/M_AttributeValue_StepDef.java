/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.attribute;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_AttributeValue_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Attribute_StepDefData attributeTable;
	private final M_AttributeValue_StepDefData attributeValueTable;

	public M_AttributeValue_StepDef(
			@NonNull final M_Attribute_StepDefData attributeTable,
			@NonNull final M_AttributeValue_StepDefData attributeValueTable)
	{
		this.attributeTable = attributeTable;
		this.attributeValueTable = attributeValueTable;
	}

	@And("update all M_AttributeValue records by M_Attribute_ID")
	public void update_all_M_AttributeValue(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Attribute attribute = attributeTable.get(attributeIdentifier);

			final boolean isNullFieldValue = DataTableUtil.extractBooleanForColumnName(row, I_M_AttributeValue.COLUMNNAME_IsNullFieldValue);

			final IQueryUpdater<I_M_AttributeValue> updater = queryBL.createCompositeQueryUpdater(I_M_AttributeValue.class)
					.addSetColumnValue(I_M_AttributeValue.COLUMNNAME_IsNullFieldValue, isNullFieldValue);

			queryBL.createQueryBuilder(I_M_AttributeValue.class)
					.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID())
					.create()
					.update(updater);
		}
	}

	@And("update M_AttributeValue:")
	public void update_single_M_AttributeValue(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Attribute attribute = attributeTable.get(attributeIdentifier);

			final String value = DataTableUtil.extractStringForColumnName(row, I_M_AttributeValue.COLUMNNAME_Value);

			final I_M_AttributeValue attributeValue = queryBL.createQueryBuilder(I_M_AttributeValue.class)
					.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID())
					.addStringLikeFilter(I_M_AttributeValue.COLUMNNAME_Value, value, true)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstOnlyNotNull(I_M_AttributeValue.class);
			assertThat(attributeValue).isNotNull();

			final boolean isNullFieldValue = DataTableUtil.extractBooleanForColumnName(row, I_M_AttributeValue.COLUMNNAME_IsNullFieldValue);
			attributeValue.setIsNullFieldValue(isNullFieldValue);

			InterfaceWrapperHelper.save(attributeValue);

			final String attributeValueIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_AttributeValue.COLUMNNAME_M_AttributeValue_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			attributeValueTable.putOrReplace(attributeValueIdentifier, attributeValue);
		}
	}

	@And("metasfresh contains M_AttributeValues:")
	public void metasfresh_contains_M_AttributeValues(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String attributeValueIdentifier = DataTableUtil.extractStringForColumnName(row, StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_AttributeValue.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Attribute attributeRecord = attributeTable.get(attributeIdentifier);

			final String value = DataTableUtil.extractStringForColumnName(row, I_M_AttributeValue.COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringForColumnName(row, I_M_AttributeValue.COLUMNNAME_Name);

			final boolean isNullFieldValue = DataTableUtil.extractBooleanForColumnName(row, I_M_AttributeValue.COLUMNNAME_IsNullFieldValue);

			final I_M_AttributeValue attributeValueRecord = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_M_AttributeValue.class)
							.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeRecord.getM_Attribute_ID())
							.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_Value, value)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnly(I_M_AttributeValue.class),
					() -> InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class));

			attributeValueRecord.setM_Attribute_ID(attributeRecord.getM_Attribute_ID());
			attributeValueRecord.setValue(value);
			attributeValueRecord.setName(name);
			attributeValueRecord.setIsNullFieldValue(isNullFieldValue);

			InterfaceWrapperHelper.saveRecord(attributeValueRecord);

			attributeValueTable.putOrReplace(attributeValueIdentifier, attributeValueRecord);
		}
	}
}
