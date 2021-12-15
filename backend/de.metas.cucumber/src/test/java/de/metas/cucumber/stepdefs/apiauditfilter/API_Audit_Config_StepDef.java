/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.apiauditfilter;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_API_Audit_Config;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class API_Audit_Config_StepDef
{
	private final StepDefData<I_API_Audit_Config> apiAuditConfigTable;

	public API_Audit_Config_StepDef(@NonNull final StepDefData<I_API_Audit_Config> apiAuditConfigTable)
	{
		this.apiAuditConfigTable = apiAuditConfigTable;
	}

	@And("the following API_Audit_Config record is set")
	public void API_Audit_Config_insert(@NonNull final DataTable table)
	{
		final List<Map<String, String>> tableRows = table.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, "SeqNo");
			final String method = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.Method");
			final String pathPrefix = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.PathPrefix");
			final boolean isInvokerWaitsForResult = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "IsInvokerWaitsForResult", false);
			final boolean isSynchronousAuditLoggingEnabled = DataTableUtil.extractBooleanForColumnName(tableRow, I_API_Audit_Config.COLUMNNAME_IsSynchronousAuditLoggingEnabled);
			final boolean isWrapApiResponse = DataTableUtil.extractBooleanForColumnName(tableRow, I_API_Audit_Config.COLUMNNAME_IsWrapApiResponse);

			final I_API_Audit_Config auditConfig = InterfaceWrapperHelper.newInstance(I_API_Audit_Config.class);

			auditConfig.setSeqNo(seqNo);
			auditConfig.setMethod(method);
			auditConfig.setPathPrefix(pathPrefix);
			auditConfig.setIsInvokerWaitsForResult(isInvokerWaitsForResult);
			auditConfig.setIsSynchronousAuditLoggingEnabled(isSynchronousAuditLoggingEnabled);
			auditConfig.setIsWrapApiResponse(isWrapApiResponse);

			saveRecord(auditConfig);

			final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "API_Audit_Config");
			apiAuditConfigTable.put(recordIdentifier, auditConfig);
		}
	}
}
