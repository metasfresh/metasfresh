/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PricingSystem;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_M_PricingSystem_ID;

@RequiredArgsConstructor
public class M_PricingSystem_StepDef
{
	@NonNull private final M_PricingSystem_StepDefData pricingSystemTable;

	@And("load M_PricingSystem:")
	public void load_bpartner(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			load_pricingSystem(tableRow);
		}
	}

	private void load_pricingSystem(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_PricingSystem_ID + ".Identifier");

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_M_PricingSystem_ID);

		if (id != null)
		{
			final I_M_PricingSystem pricingSystemRecord = InterfaceWrapperHelper.load(id, I_M_PricingSystem.class);
			assertThat(pricingSystemRecord).isNotNull();

			pricingSystemTable.putOrReplace(identifier, pricingSystemRecord);
		}
	}
}
