/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.InvoiceVerificationRunId;
import de.metas.invoice.InvoiceVerificationSetId;
import de.metas.invoice.InvoiceVerificationSetLineId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceVerificationBL;
import de.metas.invoice.service.InvoiceVerificationRunStatus;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxQuery;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Verification_Run;
import org.compiere.model.I_C_Invoice_Verification_RunLine;
import org.compiere.model.I_C_Invoice_Verification_SetLine;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class InvoiceVerificationBL implements IInvoiceVerificationBL
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	@Override
	public void createVerificationSetLines(@NonNull final InvoiceVerificationSetId verificationSetId, @NonNull final Collection<InvoiceId> invoiceIds)
	{
		final Collection<InvoiceId> existingInvoiceIdsForSet = getInvoiceIdsForVerificationSet(verificationSetId);
		invoiceIds.stream()
				.filter(id -> !existingInvoiceIdsForSet.contains(id))
				.forEach(id -> createVerificationSetLine(verificationSetId, id));
	}

	@Override
	public void createVerificationRunLines(@NonNull final InvoiceVerificationRunId runId)
	{
		final ImmutableList<InvoiceVerificationRunResult> results = queryBL.createQueryBuilder(I_C_Invoice_Verification_SetLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Verification_SetLine.COLUMNNAME_C_Invoice_Verification_Set_ID, runId.getInvoiceVerificationSetId())
				.create()
				.stream()
				.map(setLine -> this.verifySetLine(runId, setLine))
				.collect(ImmutableList.toImmutableList());
		results.forEach(result -> createVerificationRunLine(runId, result));
	}

	private InvoiceVerificationRunResult verifySetLine(@NonNull final InvoiceVerificationRunId runId, @NonNull final I_C_Invoice_Verification_SetLine setLine)
	{
		final I_C_Invoice_Verification_Run run = getVerificationRunFor(runId);
		return getVerificationRunResultForSetLine(setLine, run.getMovementDate_Override());

	}

	private void createVerificationRunLine(@NonNull final InvoiceVerificationRunId runId, @NonNull final InvoiceVerificationRunResult runResult)
	{
		final Tax invoiceTax = runResult.getInvoiceTax();
		final Tax resultingTax = runResult.getResultingTax();
		final boolean noTaxFound = resultingTax == null;
		final TaxId resultingTaxId = noTaxFound ? taxDAO.retrieveNoTaxFoundId(Env.getCtx()) : resultingTax.getTaxId();
		final boolean sameTax = invoiceTax.getTaxId().equals(resultingTaxId);
		final boolean sameRate = !noTaxFound && invoiceTax.getRate().compareTo(resultingTax.getRate()) == 0;
		final boolean sameBoilerPlate = !noTaxFound && Objects.equals(invoiceTax.getBoilerPlateId(), resultingTax.getBoilerPlateId());

		final I_C_Invoice_Verification_RunLine runLine = newInstance(I_C_Invoice_Verification_RunLine.class);
		runLine.setC_Invoice_Verification_Run_ID(runId.getRepoId());
		runLine.setC_Invoice_Verification_Set_ID(runId.getInvoiceVerificationSetId().getRepoId());
		runLine.setC_Invoice_Verification_SetLine_ID(runResult.getSetLineId().getRepoId());
		runLine.setAD_Org_ID(runResult.getOrgId().getRepoId());
		runLine.setRun_Tax_ID(resultingTaxId.getRepoId());
		runLine.setRun_Tax_Lookup_Log(runResult.getLog());
		runLine.setIsTaxIdMatch(sameTax);
		runLine.setIsTaxRateMatch(sameRate);
		runLine.setIsTaxBoilerPlateMatch(sameBoilerPlate);
		save(runLine);
	}

	private I_C_Invoice_Verification_Run getVerificationRunFor(@NonNull final InvoiceVerificationRunId invoiceVerificationRunId)
	{
		return Check.assumeNotNull(queryBL.createQueryBuilder(I_C_Invoice_Verification_Run.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Verification_Run.COLUMN_C_Invoice_Verification_Run_ID, invoiceVerificationRunId)
				.create()
				.first(), "No C_Invoice_Verification_Run for ID: {}", invoiceVerificationRunId);
	}

	private InvoiceVerificationRunResult getVerificationRunResultForSetLine(@NonNull final I_C_Invoice_Verification_SetLine setLine, @Nullable final Timestamp dateOfInterestOverride)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(setLine.getC_Invoice_ID());
		final I_C_InvoiceLine line = invoiceDAO.retrieveLineById(InvoiceLineId.ofRepoId(invoiceId, setLine.getC_InvoiceLine_ID()));
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(invoice.getC_BPartner_ID());
		final BPartnerLocationAndCaptureId bpartnerLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(bpartnerId, invoice.getC_BPartner_Location_ID(), invoice.getC_BPartner_Location_Value_ID());
		final OrgId orgId = OrgId.ofRepoId(setLine.getAD_Org_ID());

		final TaxQuery query = TaxQuery.builder()
				.orgId(orgId)
				.warehouseId(WarehouseId.ofRepoIdOrNull(invoice.getM_Warehouse_ID()))
				.bPartnerLocationId(bpartnerLocationId)
				.taxCategoryId(TaxCategoryId.ofRepoIdOrNull(line.getC_TaxCategory_ID()))
				.soTrx(SOTrx.ofBoolean(invoice.isSOTrx()))
				.dateOfInterest(CoalesceUtil.coalesce(dateOfInterestOverride, setLine.getRelevantDate()))
				.build();

		final PlainStringLoggable loggable = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignore = Loggables.temporarySetLoggable(loggable))
		{
			final Tax resultingTax = taxDAO.getBy(query);
			final Tax invoiceTax = taxDAO.getTaxById(line.getC_Tax_ID());
			return InvoiceVerificationRunResult.builder()
					.setLineId(InvoiceVerificationSetLineId.ofRepoId(setLine.getC_Invoice_Verification_SetLine_ID()))
					.invoiceTax(invoiceTax)
					.resultingTax(resultingTax)
					.orgId(orgId)
					.log(loggable.getConcatenatedMessages())
					.build();
		}
	}

	private void createVerificationSetLine(@NonNull final InvoiceVerificationSetId verificationSetId, @NonNull final InvoiceId id)
	{
		final Collection<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(id);
		lines.forEach(line -> createVerificationSetLine(verificationSetId, line));
	}

	private void createVerificationSetLine(@NonNull final InvoiceVerificationSetId verificationSetId, @NonNull final I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice_Verification_SetLine line = newInstance(I_C_Invoice_Verification_SetLine.class);
		line.setC_Invoice_Verification_Set_ID(verificationSetId.getRepoId());
		line.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		line.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
		line.setC_InvoiceLine_Tax_ID(invoiceLine.getC_Tax_ID());
		line.setAD_Org_ID(invoiceLine.getAD_Org_ID());
		line.setC_DocType_ID(invoiceLine.getC_Invoice().getC_DocType_ID());
		line.setRelevantDate(invoiceLine.getC_Invoice().getDateAcct());
		save(line);
	}

	private Collection<InvoiceId> getInvoiceIdsForVerificationSet(@NonNull final InvoiceVerificationSetId verificationSetId)
	{
		return new ArrayList<>(queryBL.createQueryBuilder(I_C_Invoice_Verification_SetLine.class)
				.addEqualsFilter(I_C_Invoice_Verification_SetLine.COLUMNNAME_C_Invoice_Verification_Set_ID, verificationSetId)
				.andCollect(I_C_Invoice_Verification_SetLine.COLUMN_C_Invoice_ID)
				.create()
				.listIds(InvoiceId::ofRepoId));
	}

	public InvoiceVerificationRunStatus getStatusFor(@NonNull final InvoiceVerificationRunId runId)
	{
		final I_C_Invoice_Verification_Run run = queryBL.createQueryBuilder(I_C_Invoice_Verification_Run.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Verification_Run.COLUMN_C_Invoice_Verification_Run_ID, runId)
				.create()
				.firstOnly(I_C_Invoice_Verification_Run.class);
		return run == null ? InvoiceVerificationRunStatus.Planned : InvoiceVerificationRunStatus.ofNullableCode(run.getStatus());
	}

	@Value
	private static class InvoiceVerificationRunResult
	{
		@NonNull Tax invoiceTax;
		@Nullable Tax resultingTax;
		@NonNull InvoiceVerificationSetLineId setLineId;
		@NonNull
		OrgId orgId;
		@Nullable
		String log;

		@Builder
		public InvoiceVerificationRunResult(final @NonNull Tax invoiceTax,
				final @Nullable Tax resultingTax,
				final @NonNull InvoiceVerificationSetLineId setLineId,
				final @NonNull OrgId orgId,
				@Nullable final String log)
		{
			this.invoiceTax = invoiceTax;
			this.resultingTax = resultingTax;
			this.setLineId = setLineId;
			this.orgId = orgId;
			this.log = log;
		}
	}
}
