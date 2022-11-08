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

package de.metas.cucumber.stepdefs.incoterms;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Incoterms;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Incoterms_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final C_Incoterms_StepDefData incotermsTable;

	public C_Incoterms_StepDef(@NonNull final C_Incoterms_StepDefData incotermsTable)
	{
		this.incotermsTable = incotermsTable;
	}

	@And("metasfresh contains C_Incoterms:")
	public void upsert_C_Incoterms(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::createC_Incoterm);
	}

	private void createC_Incoterm(@NonNull final Map<String, String> row)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_Value);

		final I_C_Incoterms record = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_Incoterms.class)
						.addEqualsFilter(I_C_Incoterms.COLUMNNAME_Value, value)
						.create()
						.firstOnlyOrNull(I_C_Incoterms.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_Incoterms.class));

		assertThat(record).isNotNull();

		record.setValue(value);
		record.setName(value);

		InterfaceWrapperHelper.saveRecord(record);

		final String incotermsIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_C_Incoterms_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		incotermsTable.put(incotermsIdentifier, record);
	}
}
