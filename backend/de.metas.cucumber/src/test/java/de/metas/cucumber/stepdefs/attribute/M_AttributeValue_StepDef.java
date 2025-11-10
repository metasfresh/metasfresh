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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_AttributeValue_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Attribute_StepDefData attributeTable;
	@NonNull private final M_AttributeValue_StepDefData attributeValueTable;

	@And("update all M_AttributeValue records by M_Attribute_ID")
	public void update_all_M_AttributeValue(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_AttributeValue.COLUMNNAME_M_AttributeValue_ID)
				.forEach(row -> {
					final AttributeId attributeId = row.getAsIdentifier(I_M_Attribute.COLUMNNAME_M_Attribute_ID).lookupNotNullIdIn(attributeTable);

					final IQueryUpdater<I_M_AttributeValue> updater = queryBL.createCompositeQueryUpdater(I_M_AttributeValue.class)
							.addSetColumnValue(I_M_AttributeValue.COLUMNNAME_IsNullFieldValue, row.getAsBoolean(I_M_AttributeValue.COLUMNNAME_IsNullFieldValue));

					queryBL.createQueryBuilder(I_M_AttributeValue.class)
							.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeId)
							.create()
							.update(updater);
				});
	}

	@And("update M_AttributeValue:")
	public void update_single_M_AttributeValue(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_AttributeValue.COLUMNNAME_M_AttributeValue_ID)
				.forEach(row -> {
					final StepDefDataIdentifier attributeIdentifier = row.getAsIdentifier(I_M_Attribute.COLUMNNAME_M_Attribute_ID);
					final AttributeId attributeId = attributeTable.getId(attributeIdentifier);

					final String value = row.getAsString(I_M_AttributeValue.COLUMNNAME_Value);

					final I_M_AttributeValue attributeValue = queryBL.createQueryBuilder(I_M_AttributeValue.class)
							.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeId)
							.addStringLikeFilter(I_M_AttributeValue.COLUMNNAME_Value, value, true)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnlyNotNull(I_M_AttributeValue.class);
					assertThat(attributeValue).isNotNull();

					final boolean isNullFieldValue = row.getAsBoolean(I_M_AttributeValue.COLUMNNAME_IsNullFieldValue);
					attributeValue.setIsNullFieldValue(isNullFieldValue);

					InterfaceWrapperHelper.save(attributeValue);

					row.getAsOptionalIdentifier().ifPresent(identifier -> attributeValueTable.putOrReplace(identifier, attributeValue));
				});
	}

	@And("metasfresh contains M_AttributeValues:")
	public void metasfresh_contains_M_AttributeValues(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_AttributeValue.COLUMNNAME_M_AttributeValue_ID)
				.forEach(row -> {
					final AttributeId attributeId = attributeTable.getId(row.getAsIdentifier(I_M_Attribute.COLUMNNAME_M_Attribute_ID));

					final ValueAndName valueAndName = row.suggestValueAndName();
					final String value = valueAndName.getValue();

					final I_M_AttributeValue attributeValueRecord = CoalesceUtil.coalesceSuppliersNotNull(
							() -> queryBL.createQueryBuilder(I_M_AttributeValue.class)
									.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeId)
									.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_Value, value)
									.addOnlyActiveRecordsFilter()
									.create()
									.firstOnly(I_M_AttributeValue.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class));

					attributeValueRecord.setM_Attribute_ID(attributeId.getRepoId());
					attributeValueRecord.setValue(value);
					attributeValueRecord.setName(valueAndName.getName());
					attributeValueRecord.setIsNullFieldValue(row.getAsBoolean(I_M_AttributeValue.COLUMNNAME_IsNullFieldValue));

					InterfaceWrapperHelper.saveRecord(attributeValueRecord);

					row.getAsOptionalIdentifier().ifPresent(identifier -> attributeValueTable.putOrReplace(identifier, attributeValueRecord));
				});
	}
}
