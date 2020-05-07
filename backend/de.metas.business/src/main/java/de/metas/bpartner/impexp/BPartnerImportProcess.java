package de.metas.bpartner.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

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
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bank.BankRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MContactInterest;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_I_BPartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPPrintFormat;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Imports {@link I_I_BPartner} records to {@link I_C_BPartner}.
 *
 * @author tsa
 *
 */
public class BPartnerImportProcess extends SimpleImportProcessTemplate<I_I_BPartner>
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final BPartnerImportHelper bpartnerImporter;
	private final BPartnerLocationImportHelper bpartnerLocationImporter;
	private final BPartnerContactImportHelper bpartnerContactImporter;
	private final BPartnerBankAccountImportHelper bpartnerBankAccountImportHelper;
	private final BPCreditLimitImportHelper bpartnerCreditLimitImportHelper;

	public BPartnerImportProcess()
	{
		this(
				(BPartnerCreditLimitRepository)null,
				(BankRepository)null);
	}

	public BPartnerImportProcess(
			@Nullable BPartnerCreditLimitRepository creditLimitRepo,
			@Nullable BankRepository bankRepository)
	{
		bpartnerImporter = BPartnerImportHelper.newInstance().setProcess(this);
		bpartnerLocationImporter = BPartnerLocationImportHelper.newInstance().setProcess(this);
		bpartnerContactImporter = BPartnerContactImportHelper.newInstance().setProcess(this);

		final BankRepository bankRepositoryEffective = bankRepository != null ? bankRepository : new BankRepository();
		bpartnerBankAccountImportHelper = BPartnerBankAccountImportHelper.builder()
				.bankRepository(bankRepositoryEffective)
				.build();
		bpartnerBankAccountImportHelper.setProcess(this);

		final BPartnerCreditLimitRepository creditLimitRepoEffective = creditLimitRepo != null ? creditLimitRepo : new BPartnerCreditLimitRepository();
		bpartnerCreditLimitImportHelper = BPCreditLimitImportHelper.builder()
				.creditLimitRepo(creditLimitRepoEffective)
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();
		BPartnerImportTableSqlUpdater.updateBPartnerImportTable(selection);
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_BPartner importRecord, final boolean insertOnly)
	{
		// First line to import or this line does NOT have the same BP value
		// => create a new BPartner or update the existing one
		BPartnerImportContext context = (BPartnerImportContext)state.getValue();
		final ImportRecordResult bpartnerImportResult;
		if (context == null || !context.isSameBPartner(importRecord))
		{
			context = createNewContext(insertOnly);
			context.setCurrentImportRecord(importRecord);
			state.setValue(context);

			bpartnerImportResult = importOrUpdateBPartner(context);
		}
		//
		// Same BPValue like previous line
		else
		{
			final BPartnerId previousBPartnerId = context.getCurrentBPartnerIdOrNull();
			context.setCurrentImportRecord(importRecord);

			if (previousBPartnerId == null)
			{
				bpartnerImportResult = importOrUpdateBPartner(context);
			}
			else if (importRecord.getC_BPartner_ID() <= 0 || importRecord.getC_BPartner_ID() == previousBPartnerId.getRepoId())
			{
				// importRecord not have a C_BPartner_ID or it has the same C_BPartner_ID like the previous line
				// => reuse previous BPartner
				importRecord.setC_BPartner_ID(previousBPartnerId.getRepoId());
				bpartnerImportResult = ImportRecordResult.Nothing;
			}
			// Our line has a C_BPartner_ID set but it's not the same like previous one, even though the BPValues are the same
			// => ERROR
			else
			{
				throw new AdempiereException("Same BPValue as previous line but not same BPartner linked");
			}
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			bpartnerLocationImporter.importRecord(context);
			bpartnerContactImporter.importRecord(context);

			bpartnerBankAccountImportHelper.importRecord(importRecord);

			bpartnerCreditLimitImportHelper.importRecord(BPCreditLimitImportRequest.builder()
					.bpartnerId(bpartnerId)
					.insuranceCreditLimit(importRecord.getCreditLimit())
					.managementCreditLimit(importRecord.getCreditLimit2())
					.build());
		}

		createUpdateInterestArea(importRecord);
		createBPPrintFormatIfNeeded(importRecord);

		return bpartnerImportResult;
	}

	private BPartnerImportContext createNewContext(final boolean insertOnly)
	{
		final BPartnersCache bpartnersCache = BPartnersCache.builder()
				.bpartnersRepo(bpartnersRepo)
				.build();

		return BPartnerImportContext.builder()
				.bpartnersCache(bpartnersCache)
				.insertOnly(insertOnly)
				.build();
	}

	private ImportRecordResult importOrUpdateBPartner(final BPartnerImportContext context)
	{
		final ImportRecordResult bpartnerImportResult;
		// We don't have a previous C_BPartner_ID
		// => create or update existing BPartner from this line

		final boolean bpartnerExists = context.isCurrentBPartnerIdSet();

		if (context.isInsertOnly() && bpartnerExists)
		{
			// #4994 do not update existing entries
			return ImportRecordResult.Nothing;
		}

		bpartnerImportResult = bpartnerExists ? ImportRecordResult.Updated : ImportRecordResult.Inserted;

		bpartnerImporter.importRecord(context);
		return bpartnerImportResult;
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
			adTableId = getTableId(I_C_Order.class);
		}
		// for customer we have print format for delivery
		else
		{
			docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt)
					.adClientId(importRecord.getAD_Client_ID())
					.adOrgId(importRecord.getAD_Org_ID())
					.build());

			adTableId = getTableId(I_M_InOut.class);
		}

		final BPartnerPrintFormatRepository repo = SpringContextHolder.instance.getBean(BPartnerPrintFormatRepository.class);
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
				+ ", " + I_I_BPartner.COLUMNNAME_GlobalId
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
