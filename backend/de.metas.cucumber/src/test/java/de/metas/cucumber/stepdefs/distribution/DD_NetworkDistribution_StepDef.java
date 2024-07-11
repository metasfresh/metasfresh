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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_NetworkDistribution;

@RequiredArgsConstructor
public class DD_NetworkDistribution_StepDef
{
	@NonNull private final DD_NetworkDistribution_StepDefData ddNetworkTable;

	@And("metasfresh contains DD_NetworkDistribution")
	public void metasfresh_contains_DD_NetworkDistribution(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::metasfresh_contains_DD_NetworkDistribution);
	}

	private void metasfresh_contains_DD_NetworkDistribution(final DataTableRow row)
	{
		final I_DD_NetworkDistribution record = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistribution.class);

		final ValueAndName valueAndName = row.suggestValueAndName();
		record.setValue(valueAndName.getValue());
		record.setName(valueAndName.getName());

		row.getAsOptionalString(I_DD_NetworkDistribution.COLUMNNAME_DocumentNo).ifPresent(record::setDocumentNo);

		InterfaceWrapperHelper.save(record);

		row.getAsOptionalIdentifier(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID)
				.ifPresent(identifier -> ddNetworkTable.put(identifier, record));
	}
}