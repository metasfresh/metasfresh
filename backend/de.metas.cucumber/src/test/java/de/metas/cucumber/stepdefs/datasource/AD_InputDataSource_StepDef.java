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

package de.metas.cucumber.stepdefs.datasource;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.impl.InputDataSourceQuery;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

@RequiredArgsConstructor
public class AD_InputDataSource_StepDef
{
	private final @NonNull AD_InputDataSource_StepDefData dataSourceTable;

	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	/**
	 * Creates an {@code AD_InputDataSource}, or updates the one with the same generated {@code Value}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>InternalName</b> — (optional) internal name, referenced as {@code int-<InternalName>} by the order candidate REST API<br>
	 *   <b>IsEDIChannel</b> — (optional) whether orders created from this data source's order candidates are EDI orders<br>
	 * @cucumber.depends StepDefData: AD_InputDataSource_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains AD_InputDataSource:
	 *   | Identifier   | InternalName | IsEDIChannel |
	 *   | dataSource_1 | testEdi      | Y            |
	 * </pre>
	 */
	@Given("metasfresh contains AD_InputDataSource:")
	public void metasfresh_contains_ad_input_data_source(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_AD_InputDataSource.COLUMNNAME_AD_InputDataSource_ID)
				.forEach(this::createAD_InputDataSource);
	}

	private void createAD_InputDataSource(@NonNull final DataTableRow tableRow)
	{
		final ValueAndName valueAndName = tableRow.suggestValueAndName();
		final I_AD_InputDataSource inputDataSourceRecord = inputDataSourceDAO.retrieveInputDataSourceIdBy(InputDataSourceQuery.builder()
						.orgId(Env.getOrgId())
						.value(valueAndName.getValue())
						.build())
				.map(inputDataSourceDAO::getById)
				.orElseGet(() -> newInstanceOutOfTrx(I_AD_InputDataSource.class));

		inputDataSourceRecord.setValue(valueAndName.getValue());
		inputDataSourceRecord.setName(valueAndName.getName());

		tableRow.getAsOptionalString(I_AD_InputDataSource.COLUMNNAME_InternalName)
				.ifPresent(inputDataSourceRecord::setInternalName);
		tableRow.getAsOptionalBoolean(I_AD_InputDataSource.COLUMNNAME_IsEDIChannel)
				.ifPresent(inputDataSourceRecord::setIsEDIChannel);

		InterfaceWrapperHelper.saveRecord(inputDataSourceRecord);

		dataSourceTable.putOrReplace(tableRow.getAsIdentifier(), inputDataSourceRecord);
	}
}
