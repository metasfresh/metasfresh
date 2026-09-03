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
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetMandatoryType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID;

@RequiredArgsConstructor
public class M_AttributeSet_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_AttributeSet_StepDefData attributeSetTable;

	@And("load M_AttributeSet:")
	public void load_M_AttributeSet(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_AttributeSet_ID)
				.forEach(row -> {
					final String name = row.getAsString(I_M_AttributeSet.COLUMNNAME_Name);

					final I_M_AttributeSet attributeSetRecord = queryBL.createQueryBuilder(I_M_AttributeSet.class)
							.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_Name, name)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnlyNotNull(I_M_AttributeSet.class);

					attributeSetTable.putOrReplace(row.getAsIdentifier(), attributeSetRecord);
				});
	}

	@And("add M_AttributeSet:")
	public void add_M_AttributeSet(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_AttributeSet_ID)
				.forEach(row -> {
					final ValueAndName valueAndName = row.suggestValueAndName();
					final String name = valueAndName.getName();

					final I_M_AttributeSet attributeSet = CoalesceUtil.coalesceSuppliers(
							() -> queryBL.createQueryBuilder(I_M_AttributeSet.class)
									.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_Name, name)
									.addOnlyActiveRecordsFilter()
									.create()
									.firstOnly(I_M_AttributeSet.class),
							() -> {
								final I_M_AttributeSet newRecord = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
								newRecord.setMandatoryType(AttributeSetMandatoryType.NotMandatory.getCode());
								return newRecord;
							});

					assertThat(attributeSet).isNotNull();

					attributeSet.setName(name);

					row.getAsOptionalEnum(I_M_AttributeSet.COLUMNNAME_MandatoryType, AttributeSetMandatoryType.class)
							.ifPresent(mandatoryType -> attributeSet.setMandatoryType(mandatoryType.getCode()));

					InterfaceWrapperHelper.saveRecord(attributeSet);

					row.getAsOptionalIdentifier().ifPresent(identifier -> attributeSetTable.putOrReplace(identifier, attributeSet));
				});
	}
}
