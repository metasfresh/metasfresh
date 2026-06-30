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

package de.metas.cucumber.stepdefs.docoutbound;

import de.metas.cache.CacheMgt;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_AD_Table;

import java.util.List;

import static de.metas.document.archive.model.I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_Doc_Outbound_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@NonNull private final C_Doc_Outbound_Config_StepDefData docOutboundConfigTable;

	@Given("metasfresh contains C_Doc_Outbound_Config:")
	public void metasfresh_contains_C_Doc_Outbound_Config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_Doc_Outbound_Config_ID)
				.forEach(this::loadOrCreateC_Doc_Outbound_Config);
	}

	private void loadOrCreateC_Doc_Outbound_Config(@NonNull final DataTableRow tableRow)
	{
		final String tableName = tableRow.getAsString(I_AD_Table.COLUMNNAME_TableName);
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final String printFormatName = tableRow.getAsString("PrintFormat." + I_AD_PrintFormat.COLUMNNAME_Name);
		final I_AD_PrintFormat printFormat = queryBL.createQueryBuilder(I_AD_PrintFormat.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(I_AD_PrintFormat.COLUMNNAME_Name, printFormatName, true)
				.create()
				.firstOnlyNotNull(I_AD_PrintFormat.class);

		final I_C_Doc_Outbound_Config record = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
						.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_AD_Table_ID, tableId)
						.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_AD_PrintFormat_ID, printFormat.getAD_PrintFormat_ID())
						.create()
						.firstOnlyOrNull(I_C_Doc_Outbound_Config.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Config.class));

		record.setAD_Table_ID(tableId.getRepoId());
		record.setAD_PrintFormat_ID(printFormat.getAD_PrintFormat_ID());

		InterfaceWrapperHelper.save(record);

		tableRow.getAsIdentifier().putOrReplace(docOutboundConfigTable, record);
	}

	/**
	 * Updates the {@code IsAutoSendDocument} flag on all existing {@link I_C_Doc_Outbound_Config}
	 * rows for the given table names.  The config records are looked up by {@code AD_Table_ID} only
	 * (no print-format discriminator), which covers the typical single-config-per-table setup.
	 *
	 * <p>This step is useful in scenarios that need the outbound-mail pipeline to run automatically
	 * (e.g. {@code IsAutoSendDocument=Y}) without creating a new config from scratch.</p>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>TableName</b>  — (required) AD_Table.TableName of the document table<br>
	 *   <b>IsAutoSendDocument</b> — (required) {@code true}/{@code false}<br>
	 * @cucumber.example
	 * <pre>
	 * And update C_Doc_Outbound_Config IsAutoSendDocument:
	 *   | TableName | IsAutoSendDocument |
	 *   | C_Invoice | true               |
	 * </pre>
	 */
	@And("update C_Doc_Outbound_Config IsAutoSendDocument:")
	public void updateDocOutboundConfigAutoSend(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String tableName = row.getAsString(I_AD_Table.COLUMNNAME_TableName);
			final boolean autoSend = row.getAsBoolean(I_C_Doc_Outbound_Config.COLUMNNAME_IsAutoSendDocument);

			final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
			assertThat(tableId).as("AD_Table not found: %s", tableName).isNotNull();

			final List<I_C_Doc_Outbound_Config> configs = queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
					.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_AD_Table_ID, tableId)
					.create()
					.list();

			if (configs.isEmpty())
			{
				throw new AdempiereException("No C_Doc_Outbound_Config found for TableName=" + tableName);
			}

			for (final I_C_Doc_Outbound_Config config : configs)
			{
				config.setIsAutoSendDocument(autoSend);
				InterfaceWrapperHelper.save(config);
			}
			CacheMgt.get().reset(I_C_Doc_Outbound_Config.Table_Name);
		});
	}
}
