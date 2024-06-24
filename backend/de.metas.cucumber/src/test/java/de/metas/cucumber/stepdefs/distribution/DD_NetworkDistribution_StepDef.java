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

package de.metas.cucumber.stepdefs.distribution;

import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_NetworkDistribution;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class DD_NetworkDistribution_StepDef
{
	private final DD_NetworkDistribution_StepDefData ddNetworkTable;

	public DD_NetworkDistribution_StepDef(@NonNull final DD_NetworkDistribution_StepDefData ddNetworkTable)
	{
		this.ddNetworkTable = ddNetworkTable;
	}

	@And("metasfresh contains DD_NetworkDistribution")
	public void metasfresh_contains_DD_NetworkDistribution(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistribution.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistribution.COLUMNNAME_Value);
			final String documentNo = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistribution.COLUMNNAME_DocumentNo);

			final I_DD_NetworkDistribution ddNetwork = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistribution.class);

			ddNetwork.setValue(value);
			ddNetwork.setName(name);
			ddNetwork.setDocumentNo(documentNo);

			InterfaceWrapperHelper.save(ddNetwork);

			final String ddNetworkIdentifier = DataTableUtil.extractStringForColumnName(row, I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID + "." + TABLECOLUMN_IDENTIFIER);
			ddNetworkTable.put(ddNetworkIdentifier, ddNetwork);
		}
	}
}