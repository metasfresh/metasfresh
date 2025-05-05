/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class ModCntr_Type_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ModCntr_Type_StepDefData modCntrTypeTable;

	public ModCntr_Type_StepDef(@NonNull final ModCntr_Type_StepDefData modCntrTypeTable)
	{
		this.modCntrTypeTable = modCntrTypeTable;
	}

	@Given("metasfresh contains ModCntr_Types:")
	public void metasfresh_contains_modcntr_types(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createModCntrTypes(tableRow);
		}
	}

	private void createModCntrTypes(@NonNull final Map<String, String> tableRow)
	{
		final String value = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Type.COLUMNNAME_Value);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Type.COLUMNNAME_Name);
		final ComputingMethodType handlerType = ComputingMethodType.ofCode(DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType));

		final I_ModCntr_Type modCntrTypeRecord = queryBL.createQueryBuilder(I_ModCntr_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType, handlerType.getCode())
				.create()
				.firstOnlyOptional()
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_ModCntr_Type.class));

		modCntrTypeRecord.setValue(value);
		modCntrTypeRecord.setName(name);
		modCntrTypeRecord.setModularContractHandlerType(handlerType.getCode());

		InterfaceWrapperHelper.saveRecord(modCntrTypeRecord);

		final String modCntrTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Type.COLUMNNAME_ModCntr_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrTypeTable.putOrReplace(modCntrTypeIdentifier, modCntrTypeRecord);
	}
}
