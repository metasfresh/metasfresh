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

package de.metas.cucumber.stepdefs.docoutbound;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_AD_Table;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Doc_Outbound_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Doc_Outbound_Config_StepDefData docOutboundConfigTable;

	public C_Doc_Outbound_Config_StepDef(@NonNull final C_Doc_Outbound_Config_StepDefData docOutboundConfigTable)
	{
		this.docOutboundConfigTable = docOutboundConfigTable;
	}

	@Given("metasfresh contains C_Doc_Outbound_Config:")
	public void metasfresh_contains_C_Doc_Outbound_Config(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::loadOrCreateC_Doc_Outbound_Config);
	}

	private void loadOrCreateC_Doc_Outbound_Config(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final String printFormatName = DataTableUtil.extractStringForColumnName(row, "PrintFormat." + I_AD_PrintFormat.COLUMNNAME_Name);
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

		final String docOutboundConfigIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		docOutboundConfigTable.putOrReplace(docOutboundConfigIdentifier, record);
	}
}
