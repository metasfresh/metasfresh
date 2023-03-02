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
import de.metas.bpartner.blockfile.BPartnerBlockFileId;
import de.metas.bpartner.blockfile.BPartnerBlockFileService;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.bpartner.blockstatus.BlockStatus;
import de.metas.bpartner.blockstatus.CreateBPartnerBlockStatusRequest;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.impexp.processing.ImportGroupResult;
import de.metas.impexp.processing.ImportProcessTemplate;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.compiere.model.X_I_BPartner_BlockStatus;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Imports {@link I_I_BPartner_BlockStatus} records to {@link org.compiere.model.I_C_BPartner_BlockStatus}.
 **/
public class BPartnerBlockStatusImportProcess extends ImportProcessTemplate<I_I_BPartner_BlockStatus, BPartnerBlockStatusImportProcess.BPBlockStatusGroupKey>
{
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);
	private final BPartnerBlockFileService bPartnerBlockFileService = SpringContextHolder.instance.getBean(BPartnerBlockFileService.class);

	private BPBlockStatusGroupKey groupKey = null;

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

		final BPartnerBlockFileId bPartnerBlockFileId = BPartnerBlockFileId.ofRepoIdOrNull(getParameters().getParameterAsInt(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, -1));

		BPartnerBlockStatusImportTableSqlUpdater.updateAndValidateBPBlockStatusImportTable(selection, bPartnerBlockFileId);

		final int countErrorRecords = BPartnerBlockStatusImportTableSqlUpdater.countRecordsWithErrors(selection);
		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	@Override
	protected BPBlockStatusGroupKey extractImportGroupKey(@NonNull final I_I_BPartner_BlockStatus importRecord)
	{
		return BPBlockStatusGroupKey.of(importRecord);
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
	protected ImportGroupResult importRecords(
			@NonNull final BPBlockStatusGroupKey bpBlockStatusGroupKey,
			@NonNull final List<I_I_BPartner_BlockStatus> importRecords,
			@NonNull final IMutable<Object> stateHolderNOTUSED)
	{
		importRecords.forEach(this::importRecord);

		groupKey = bpBlockStatusGroupKey;

		return ImportGroupResult.countInserted(importRecords.size());
	}

	protected void afterImport()
	{
		if (groupKey != null && groupKey.isBPBlockFileKey())
		{
			final boolean processed = BPartnerBlockStatusImportTableSqlUpdater.countRecordsWithErrors(getImportRecordsSelection()) == 0;

			Check.assumeNotNull(groupKey, "BPartnerBlockFileId cannot be missing at this stage!");
			bPartnerBlockFileService.updateImportFlags(groupKey, processed);
		}
	}

	private void importRecord(@NonNull final I_I_BPartner_BlockStatus importRecord)
	{
		bPartnerDAO.getBySAPBpartnerCode(importRecord.getSAP_BPartnerCode())
				.forEach(partner -> {
					final CreateBPartnerBlockStatusRequest request = buildCreateBPBlockStatusRequest(importRecord, partner);
					bPartnerBlockStatusService.createBPartnerBlockStatus(request);
				});
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

	@Value
	public static class BPBlockStatusGroupKey
	{
		@Nullable
		SimpleImportProcessTemplate.RecordIdGroupKey recordIdGroupKey;

		@Nullable
		BPartnerBlockFileId bPartnerBlockFileId;

		@Builder
		private BPBlockStatusGroupKey(
				@Nullable final SimpleImportProcessTemplate.RecordIdGroupKey recordIdGroupKey,
				@Nullable final BPartnerBlockFileId bPartnerBlockFileId)
		{
			Check.assume((recordIdGroupKey == null && bPartnerBlockFileId != null) || (recordIdGroupKey != null && bPartnerBlockFileId == null),
						 "Only one group key is allowed!");

			this.recordIdGroupKey = recordIdGroupKey;
			this.bPartnerBlockFileId = bPartnerBlockFileId;
		}

		@NonNull
		private static BPBlockStatusGroupKey of(@NonNull final I_I_BPartner_BlockStatus importRecord)
		{
			if (importRecord.getC_BPartner_Block_File_ID() > 0)
			{
				return BPBlockStatusGroupKey.builder()
						.bPartnerBlockFileId(BPartnerBlockFileId.ofRepoId(importRecord.getC_BPartner_Block_File_ID()))
						.build();
			}

			return BPBlockStatusGroupKey.builder()
					.recordIdGroupKey(SimpleImportProcessTemplate.RecordIdGroupKey.of(importRecord.getI_BPartner_BlockStatus_ID()))
					.build();
		}

		private boolean isBPBlockFileKey()
		{
			return this.bPartnerBlockFileId != null;
		}
	}
}
