package de.metas.bpartner.impexp;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.util.lang.IMutable;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.MContactInterest;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_I_BPartner;
import org.compiere.model.X_M_InOut;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPPrintFormat;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Imports {@link I_I_BPartner} records to {@link I_C_BPartner}.
 *
 * @author tsa
 *
 */
public class BPartnerImportProcess extends AbstractImportProcess<I_I_BPartner>
{
	private final BPartnerImportHelper bpartnerImporter;
	private final BPartnerLocationImportHelper bpartnerLocationImporter;
	private final BPartnerContactImportHelper bpartnerContactImporter;
	private final BPartnerBankAccountImportHelper bpartnerBankAccountImportHelper;
	private final BPCreditLimitImportHelper bpartnerCreditLimitImportHelper;

	public BPartnerImportProcess()
	{
		bpartnerImporter = BPartnerImportHelper.newInstance().setProcess(this);
		bpartnerLocationImporter = BPartnerLocationImportHelper.newInstance().setProcess(this);
		bpartnerContactImporter = BPartnerContactImportHelper.newInstance().setProcess(this);
		bpartnerBankAccountImportHelper = BPartnerBankAccountImportHelper.newInstance().setProcess(this);
		bpartnerCreditLimitImportHelper = BPCreditLimitImportHelper.newInstance();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		BPartnerImportTableSqlUpdater.updateBPartnerImportTable(whereClause);
	}

	private static final class BPartnerImportContext
	{
		// Remember Previous BP Value BP is only first one, others are contacts.
		// All contacts share BP location.
		// bp and bpl declarations before loop, we need them for data.
		private I_I_BPartner previousImportRecord = null;
		private List<I_I_BPartner> previousImportRecordsForSameBP = new ArrayList<>();

		public I_I_BPartner getPreviousImportRecord()
		{
			return previousImportRecord;
		}

		public void setPreviousImportRecord(final I_I_BPartner previousImportRecord)
		{
			this.previousImportRecord = previousImportRecord;
		}

		public int getPreviousC_BPartner_ID()
		{
			return previousImportRecord == null ? -1 : previousImportRecord.getC_BPartner_ID();
		}

		public String getPreviousBPValue()
		{
			return previousImportRecord == null ? null : previousImportRecord.getBPValue();
		}

		public List<I_I_BPartner> getPreviousImportRecordsForSameBP()
		{
			return previousImportRecordsForSameBP;
		}

		public void clearPreviousRecordsForSameBP()
		{
			previousImportRecordsForSameBP = new ArrayList<>();
		}

		public void collectImportRecordForSameBP(final I_I_BPartner importRecord)
		{
			previousImportRecordsForSameBP.add(importRecord);
		}
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_BPartner importRecord, final boolean isInsertOnly)
	{
		//
		// Get previous values
		BPartnerImportContext context = (BPartnerImportContext)state.getValue();
		if (context == null)
		{
			context = new BPartnerImportContext();
			state.setValue(context);
		}
		final I_I_BPartner previousImportRecord = context.getPreviousImportRecord();
		final int previousBPartnerId = context.getPreviousC_BPartner_ID();
		final String previousBPValue = context.getPreviousBPValue();
		context.setPreviousImportRecord(importRecord); // set it early in case this method fails

		final ImportRecordResult bpartnerImportResult;

		// First line to import or this line does NOT have the same BP value
		// => create a new BPartner or update the existing one
		final boolean firstImportRecordOrNewBPartner = previousImportRecord == null || !Objects.equals(importRecord.getBPValue(), previousBPValue);
		if (firstImportRecordOrNewBPartner)
		{
			// create a new list because we are passing to a new partner
			context.clearPreviousRecordsForSameBP();
			bpartnerImportResult = importOrUpdateBPartner(importRecord, isInsertOnly);
		}

		// Same BPValue like previous line
		else
		{
			if (previousBPartnerId <= 0)
			{
				bpartnerImportResult = importOrUpdateBPartner(importRecord, isInsertOnly);
			}
			else if (importRecord.getC_BPartner_ID() <= 0 || importRecord.getC_BPartner_ID() == previousBPartnerId)
			{
				bpartnerImportResult = doNothingAndUsePreviousPartner(importRecord, previousImportRecord);
			}
			// Our line has a C_BPartner_ID set but it's not the same like previous one, even though the BPValues are the same
			// => ERROR
			else
			{
				throw new AdempiereException("Same BPValue as previous line but not same BPartner linked");
			}
		}

		bpartnerLocationImporter.importRecord(importRecord, context.getPreviousImportRecordsForSameBP());
		bpartnerContactImporter.importRecord(importRecord);
		bpartnerBankAccountImportHelper.importRecord(importRecord);
		bpartnerCreditLimitImportHelper.importRecord(importRecord);
		createUpdateInterestArea(importRecord);
		createBPPrintFormatIfNeeded(importRecord);

		context.collectImportRecordForSameBP(importRecord);

		return bpartnerImportResult;
	}

	private ImportRecordResult importOrUpdateBPartner(final I_I_BPartner importRecord, final boolean isInsertOnly)
	{
		final ImportRecordResult bpartnerImportResult;
		// We don't have a previous C_BPartner_ID
		// => create or update existing BPartner from this line

		final boolean bpartnerExists = importRecord.getC_BPartner_ID() > 0;

		if (isInsertOnly && bpartnerExists)
		{
			// #4994 do not update existing entries
			return ImportRecordResult.Nothing;
		}

		bpartnerImportResult = bpartnerExists ? ImportRecordResult.Updated : ImportRecordResult.Inserted;

		bpartnerImporter.importRecord(importRecord);
		return bpartnerImportResult;
	}

	/**
	 * importRecord not have a C_BPartner_ID or it has the same C_BPartner_ID like the previous line
	 * => reuse previous BPartner
	 *
	 * @param importRecord
	 * @param previousImportRecord
	 * @return
	 */
	private ImportRecordResult doNothingAndUsePreviousPartner(final I_I_BPartner importRecord, final I_I_BPartner previousImportRecord)
	{
		importRecord.setC_BPartner(previousImportRecord.getC_BPartner());
		return ImportRecordResult.Nothing;
	}

	private final void createUpdateInterestArea(final I_I_BPartner importRecord)
	{
		int interestAreaId = importRecord.getR_InterestArea_ID();
		if (interestAreaId <= 0)
		{
			return;
		}

		final int adUserId = importRecord.getAD_User_ID();
		if (adUserId <= 0)
		{
			return;
		}

		final MContactInterest ci = MContactInterest.get(getCtx(),
				interestAreaId,
				adUserId,
				true, // active
				ITrx.TRXNAME_ThreadInherited);
		ci.save();		// don't subscribe or re-activate
	}

	private final void createBPPrintFormatIfNeeded(@NonNull final I_I_BPartner importRecord)
	{
		if (importRecord.getAD_PrintFormat_ID() <= 0
				|| importRecord.getC_BP_PrintFormat_ID() > 0)
		{
			return;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final int printFormatId = importRecord.getAD_PrintFormat_ID();
		final int adTableId;
		final DocTypeId docTypeId;
		// for vendors we have print format for purchase order
		if (importRecord.isVendor())
		{
			docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder)
					.adClientId(importRecord.getAD_Client_ID())
					.adOrgId(importRecord.getAD_Org_ID())
					.build());
			adTableId = X_C_Order.Table_ID;
		}
		// for customer we have print format for delivery
		else
		{
			docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt)
					.adClientId(importRecord.getAD_Client_ID())
					.adOrgId(importRecord.getAD_Org_ID())
					.build());

			adTableId = X_M_InOut.Table_ID;
		}

		final BPartnerPrintFormatRepository repo = Adempiere.getBean(BPartnerPrintFormatRepository.class);
		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.printFormatId(printFormatId)
				.docTypeId(docTypeId.getRepoId())
				.adTableId(adTableId)
				.bpartnerId(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()))
				.build();

		BPPrintFormat bpPrintFormat = repo.getByQuery(bpPrintFormatQuery);
		if (bpPrintFormat == null)
		{
			bpPrintFormat = BPPrintFormat.builder()
					.adTableId(adTableId)
					.docTypeId(docTypeId.getRepoId())
					.printFormatId(printFormatId)
					.bpartnerId(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()))
					.build();

		}

		bpPrintFormat = repo.save(bpPrintFormat);

		importRecord.setC_BP_PrintFormat_ID(bpPrintFormat.getBpPrintFormatId());
	}

	@Override
	public Class<I_I_BPartner> getImportModelClass()
	{
		return I_I_BPartner.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BPartner.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		// gody: 20070113 - Order so the same values are consecutive.
		return I_I_BPartner.COLUMNNAME_BPValue
				+ ", " + I_I_BPartner.COLUMNNAME_I_BPartner_ID;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	protected I_I_BPartner retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_BPartner(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}
}
