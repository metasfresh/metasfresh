/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.finalinvoice.invoicecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyIterator;

public class FlatrateTermModular_Handler implements ConditionTypeSpecificInvoiceCandidateHandler
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	private final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);
	private final ModularContractLogDAO modularContractLogDAO = SpringContextHolder.instance.getBean(ModularContractLogDAO.class);

	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Term.TYPE_CONDITIONS_ModularContract;
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return emptyIterator();
	}

	@Override
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(@NonNull final I_C_Flatrate_Term term)
	{
		return createModularICs(term);
	}

	@Override
	public Quantity calculateQtyEntered(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return Quantitys.create(100, UomId.EACH);    // todo: get Quantity from computing method
	}

	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@NonNull final I_C_Flatrate_Term term)
	{
		icRecord.setIsSOTrx(SOTrx.PURCHASE.toBoolean());

		icRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate);
		icRecord.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_Nominal);

		icRecord.setDiscount(BigDecimal.ZERO);

		//invoice docType
		{
			setC_DocTypeInvoice(icRecord);
		}

		// final TaxId taxId = getTaxId(icRecord, TaxCategoryId.ofRepoId(1));	// todo: get tax category from contract specific prices
		icRecord.setC_Tax_ID(TaxId.ofRepoId(1000073).getRepoId());

		icRecord.setC_Currency_ID(CurrencyId.ofRepoId(318).getRepoId());    // todo: take it from contract specific price
		icRecord.setPriceActual(BigDecimal.ONE);    // todo: take it from computing method
		icRecord.setPrice_UOM_ID(UomId.EACH.getRepoId()); // todo: take it from contract specific price

		icRecord.setPriceEntered(BigDecimal.ONE); // todo: same as price actual
	}

	@Override
	public IInvoiceCandidateHandler.PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		return IInvoiceCandidateHandler.PriceAndTax.NONE; // no changes to be made
	}

	@Override
	@NonNull
	public IInvoiceCandidateHandler.CandidatesAutoCreateMode isMissingInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		return IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES_AND_INVOICES;
	}

	@NonNull
	public List<I_C_Invoice_Candidate> createModularICs(@NonNull final I_C_Flatrate_Term modularContract)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermId(FlatrateTermId.ofRepoId(modularContract.getC_Flatrate_Term_ID()));
		final YearAndCalendarId yearAndCalendarId = modularContractSettings.getYearAndCalendarId();
		final PricingSystemId pricingSystemId = modularContractSettings.getPricingSystemId();

		final ImmutableList.Builder<I_C_Invoice_Candidate> invoiceCandidatesAll = ImmutableList.builder();

		modularContractSettings.getModuleConfigs()
				.forEach(module -> invoiceCandidatesAll.add(createCandidateFor(modularContract, module, yearAndCalendarId, pricingSystemId)));

		return invoiceCandidatesAll.build();
	}

	@Override
	@NonNull
	public ImmutableList<Object> getRecordsToLock(@NonNull final I_C_Flatrate_Term term)
	{
		final ImmutableList.Builder<Object> recordsToLock = ImmutableList.builder();

		recordsToLock.add(term);

		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()))
				.processed(false)
				.billable(true)
				.build();

		recordsToLock.addAll(modularContractLogDAO.getModularContractLogRecords(query)
									 .stream()
									 .collect(ImmutableList.toImmutableList()));

		return recordsToLock.build();
	}

	@Override
	public Instant calculateDateOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return SystemTime.asInstant();
	}

	@NonNull
	private I_C_Invoice_Candidate createCandidateFor(
			@NonNull final I_C_Flatrate_Term modularContract,
			@NonNull final ModuleConfig moduleConfig,
			@NonNull final YearAndCalendarId yearAndCalendarId,
			@Nullable final PricingSystemId pricingSystemId)
	{
		final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);

		invoiceCandidate.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class));
		invoiceCandidate.setRecord_ID(modularContract.getC_Flatrate_Term_ID());
		invoiceCandidate.setAD_Org_ID(modularContract.getAD_Org_ID());
		invoiceCandidate.setC_Flatrate_Term_ID(modularContract.getC_Flatrate_Term_ID());
		invoiceCandidate.setC_Async_Batch_ID(modularContract.getC_Async_Batch_ID());

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(invoiceCandidate)
				.setFrom(ContractLocationHelper.extractBillLocation(modularContract));

		invoiceCandidate.setIsTaxIncluded(modularContract.isTaxIncluded());

		//
		invoiceCandidate.setLine(moduleConfig.getSeqNo().toInt());
		invoiceCandidate.setM_Product_ID(moduleConfig.getProductId().getRepoId());
		invoiceCandidate.setInvoicingGroup(moduleConfig.getInvoicingGroup());
		invoiceCandidate.setProductName(moduleConfig.getName());
		//
		invoiceCandidate.setHarvesting_Year_ID(yearAndCalendarId.yearId().getRepoId());
		invoiceCandidate.setC_Harvesting_Calendar_ID(yearAndCalendarId.calendarId().getRepoId());
		//
		invoiceCandidate.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSystemId));

		return invoiceCandidate;
	}

	@NonNull
	private TaxId getTaxId(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final TaxCategoryId taxCategoryId)
	{
		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = getBpartnerLocationAndCapture(invoiceCandidate);
		final OrgId orgId = OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID());
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(invoiceCandidate.isSOTrx());

		final Tax tax = taxDAO.getBy(TaxQuery.builder()
											 .orgId(orgId)
											 .bPartnerLocationId(bPartnerLocationAndCaptureId)
											 .dateOfInterest(invoiceCandidate.getDeliveryDate())
											 .soTrx(soTrx)
											 .taxCategoryId(taxCategoryId)
											 .build());

		if (tax == null)
		{
			throw TaxNotFoundException.builder()
					.taxCategoryId(taxCategoryId)
					.isSOTrx(soTrx.toBoolean())
					.billDate(invoiceCandidate.getDeliveryDate())
					.orgId(orgId)
					.build();
		}

		return tax.getTaxId();
	}

	@NonNull
	private BPartnerLocationAndCaptureId getBpartnerLocationAndCapture(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceCandidate.getBill_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, invoiceCandidate.getBill_Location_ID());

		return BPartnerLocationAndCaptureId.of(bPartnerLocationId);
	}

	private void setC_DocTypeInvoice(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		// todo: compute the total based on contract specific prices
		final BigDecimal total = BigDecimal.TEN;

		final DocTypeQuery.DocTypeQueryBuilder queryBuilder = DocTypeQuery.builder()
				.adClientId(invoiceCandidate.getAD_Client_ID())
				.adOrgId(invoiceCandidate.getAD_Org_ID())
				.isSOTrx(false);

		if (total.signum() >= 0)
		{
			queryBuilder.docBaseType(InvoiceDocBaseType.FinalInvoice.getDocBaseType());
		}
		else
		{
			queryBuilder.docBaseType(InvoiceDocBaseType.FinalCreditMemo.getDocBaseType());
		}

		final DocTypeId docTypeIdOrNull = docTypeBL.getDocTypeIdOrNull(queryBuilder.build());
		if (docTypeIdOrNull == null)
		{
			throw new AdempiereException("Fail to retrieve docType for query.")
					.appendParametersToMessage()
					.setParameter("DocTypeQuery", queryBuilder.build());
		}

		invoiceCandidate.setC_DocTypeInvoice_ID(docTypeIdOrNull.getRepoId());
	}
}
