/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.impexp.blockstatus;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.bpartner.blockstatus.BlockStatus;
import de.metas.bpartner.blockstatus.CreateBPartnerBlockStatusRequest;
import de.metas.bpartner.impexp.blockstatus.impl.IBPartnerBlockStatusDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.compiere.model.X_I_BPartner_BlockStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Imports {@link I_I_BPartner_BlockStatus} records to {@link org.compiere.model.I_C_BPartner_BlockStatus}.
 **/
public class BPartnerBlockStatusImportProcess extends SimpleImportProcessTemplate<I_I_BPartner_BlockStatus>
{
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);
	private final IBPartnerBlockStatusDAO ibPartnerBlockStatusDAO = SpringContextHolder.instance.getBean(IBPartnerBlockStatusDAO.class);

	@Override
	public Class<I_I_BPartner_BlockStatus> getImportModelClass()
	{
		return I_I_BPartner_BlockStatus.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BPartner_BlockStatus.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BPartner_BlockStatus.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		BPartnerBlockStatusImportTableSqlUpdater.updateAndValidateBPBlockStatusImportTable(selection);

		final int countErrorRecords = BPartnerBlockStatusImportTableSqlUpdater.countRecordsWithErrors(selection);

		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> stateHolder,
			final @NonNull I_I_BPartner_BlockStatus importRecord,
			final boolean isInsertOnly) throws Exception
	{
		bPartnerDAO.getBySAPBpartnerCode(importRecord.getSAP_BPartnerCode())
				.stream()
				.map(partner -> buildCreateBPBlockStatusRequest(importRecord, partner))
				.forEach(bPartnerBlockStatusService::createBPartnerBlockStatus);

		return ImportRecordResult.Inserted;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BPartner_BlockStatus.COLUMNNAME_I_BPartner_BlockStatus_ID;
	}

	@Override
	protected I_I_BPartner_BlockStatus retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_BPartner_BlockStatus(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void afterImport()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		ibPartnerBlockStatusDAO.forceAcknowledgementForFailedRows(selection.getSelectionId());
	}

	@NonNull
	private static CreateBPartnerBlockStatusRequest buildCreateBPBlockStatusRequest(
			@NonNull final I_I_BPartner_BlockStatus importRecord,
			@NonNull final I_C_BPartner bPartner)
	{
		if (Check.isBlank(importRecord.getBlockStatus()))
		{
			throw new AdempiereException("BlockStatus cannot be missing at this stage!");
		}

		return CreateBPartnerBlockStatusRequest.builder()
				.blockStatus(BlockStatus.ofCode(importRecord.getBlockStatus()))
				.reason(importRecord.getReason())
				.bPartnerId(BPartnerId.ofRepoId(bPartner.getC_BPartner_ID()))
				.orgId(OrgId.ofRepoId(bPartner.getAD_Org_ID()))
				.build();
	}
}
