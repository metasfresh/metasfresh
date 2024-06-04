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
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ContractSpecificPriceRequest;
import de.metas.contracts.modular.ModCntrInvoiceType;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES_AND_INVOICES;
import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.DONT;
import static java.util.Collections.emptyIterator;

public class FlatrateTermModular_FinalHandler implements ConditionTypeSpecificInvoiceCandidateHandler
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private final ModularContractSettingsBL modularContractSettingsBL = SpringContextHolder.instance.getBean(ModularContractSettingsBL.class);
	private final ModularContractLogDAO modularContractLogDAO = SpringContextHolder.instance.getBean(ModularContractLogDAO.class);
	private final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);
	private final ModularContractComputingMethodHandlerRegistry modularContractComputingMethods = SpringContextHolder.instance.getBean(ModularContractComputingMethodHandlerRegistry.class);

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
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final LockOwner lockOwner)
	{
		final var modularContractSettings = modularContractSettingsBL.getByFlatrateTermId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
		final var requestTemplate = CreateInvoiceCandidateRequest.builder()
				.modularContract(term)
				.yearAndCalendarId(modularContractSettings.getYearAndCalendarId())
				.pricingSystemId(modularContractSettings.getPricingSystemId())
				.lockOwner(lockOwner);

		return modularContractSettings.getModuleConfigs(getModCntrInvoiceType().getComputingMethodTypes())
				.stream()
				.map(module -> createCandidateFor(requestTemplate.moduleConfig(module).build()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Quantity calculateQtyEntered(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final UomId uomId = UomId.ofRepoId(invoiceCandidateRecord.getC_UOM_ID());
		return Quantitys.of(invoiceCandidateRecord.getQtyEntered(), uomId);
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

		final TaxId taxId = getTaxId(icRecord, term);
		icRecord.setC_Tax_ID(taxId.getRepoId());

		//invoice docType
		{
			setC_DocTypeInvoice(icRecord);
		}
	}

	@Override
	public IInvoiceCandidateHandler.PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		return IInvoiceCandidateHandler.PriceAndTax.NONE; // no changes to be made
	}

	@Override
	@NonNull
	public IInvoiceCandidateHandler.CandidatesAutoCreateMode isMissingInvoiceCandidate(@NotNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (flatrateTerm.isReadyForDefinitiveInvoice())
		{
			return DONT;
		}

		final boolean billableLogsExist = modularContractLogDAO.anyMatch(ModularContractLogQuery.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.computingMethodTypes(getModCntrInvoiceType().getComputingMethodTypes())
				.processed(false)
				.billable(true)
				.build());

		return billableLogsExist ? CREATE_CANDIDATES_AND_INVOICES
				: DONT;
	}

	@Override
	@NonNull
	public ImmutableList<Object> getRecordsToLock(@NonNull final I_C_Flatrate_Term term)
	{
		return ImmutableList.builder()
				.add(term)
				.addAll(modularContractLogDAO.list(ModularContractLogQuery.builder()
						.flatrateTermId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()))
						.computingMethodTypes(getModCntrInvoiceType().getComputingMethodTypes())
						.processed(false)
						.billable(true)
						.build()))
				.build();
	}

	@NonNull
	private I_C_Invoice_Candidate createCandidateFor(@NonNull final CreateInvoiceCandidateRequest createInvoiceCandidateRequest)
	{
		final I_C_Flatrate_Term modularContract = createInvoiceCandidateRequest.getModularContract();
		final ModuleConfig moduleConfig = createInvoiceCandidateRequest.getModuleConfig();
		final YearAndCalendarId yearAndCalendarId = createInvoiceCandidateRequest.getYearAndCalendarId();

		final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);

		invoiceCandidate.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class));
		invoiceCandidate.setRecord_ID(modularContract.getC_Flatrate_Term_ID());
		invoiceCandidate.setAD_Org_ID(modularContract.getAD_Org_ID());
		invoiceCandidate.setC_Flatrate_Term_ID(modularContract.getC_Flatrate_Term_ID());

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(invoiceCandidate)
				.setFrom(ContractLocationHelper.extractBillLocation(modularContract));

		invoiceCandidate.setIsTaxIncluded(modularContract.isTaxIncluded());

		//
		invoiceCandidate.setLine(moduleConfig.getSeqNo().toInt());
		invoiceCandidate.setM_Product_ID(moduleConfig.getProductId().getRepoId());
		invoiceCandidate.setInvoicingGroup(moduleConfig.getInvoicingGroup().getCode());
		invoiceCandidate.setProductName(moduleConfig.getName());
		//
		invoiceCandidate.setHarvesting_Year_ID(yearAndCalendarId.yearId().getRepoId());
		invoiceCandidate.setC_Harvesting_Calendar_ID(yearAndCalendarId.calendarId().getRepoId());
		//
		invoiceCandidate.setM_PricingSystem_ID(PricingSystemId.toRepoId(createInvoiceCandidateRequest.getPricingSystemId()));
		//
		invoiceCandidate.setModCntr_Module_ID(moduleConfig.getId().getModularContractModuleId().getRepoId());

		final ComputingResponse computingResponse = getComputingMethodResponse(createInvoiceCandidateRequest);

		setPriceAndQty(invoiceCandidate, computingResponse);

		processModCntrLogs(computingResponse, invoiceCandidate);

		return invoiceCandidate;
	}

	protected void processModCntrLogs(final ComputingResponse computingResponse, final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (!computingResponse.getIds().isEmpty())
		{
			trxManager.runAfterCommit(() -> modularContractLogDAO.setICProcessed(ModularContractLogQuery.ofEntryIds(computingResponse.getIds()),
					InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID())));
		}
	}

	private void setPriceAndQty(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final ComputingResponse computingResponse)
	{
		final ProductPrice productPrice = computingResponse.getPrice();
		invoiceCandidate.setC_Currency_ID(CurrencyId.toRepoId(productPrice.getCurrencyId()));
		invoiceCandidate.setPriceActual(productPrice.toBigDecimal());
		invoiceCandidate.setPriceEntered(productPrice.toBigDecimal());
		invoiceCandidate.setPrice_UOM_ID(UomId.toRepoId(productPrice.getUomId()));

		invoiceCandidate.setQtyEntered(computingResponse.getQty().toBigDecimal());
		invoiceCandidate.setC_UOM_ID(computingResponse.getQty().getUomId().getRepoId());
	}

	@NonNull
	private ComputingResponse getComputingMethodResponse(@NonNull final CreateInvoiceCandidateRequest createInvoiceCandidateRequest)
	{
		final I_C_Flatrate_Term modularContract = createInvoiceCandidateRequest.getModularContract();
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(modularContract.getC_Flatrate_Term_ID());
		final ModuleConfig moduleConfig = createInvoiceCandidateRequest.getModuleConfig();

		final CurrencyId currencyId = CurrencyId.optionalOfRepoId(modularContract.getC_Currency_ID())
				.orElseThrow(() -> new AdempiereException("Currency must be set on the Modular Contract !")
						.appendParametersToMessage()
						.setParameter("ModularContractId", flatrateTermId.getRepoId()));

		final ComputingMethodType computingMethodType = moduleConfig.getComputingMethodType();

		final IComputingMethodHandler computingMethodHandler = modularContractComputingMethods.getApplicableHandlerFor(computingMethodType);
		final ComputingRequest request = ComputingRequest.builder()
				.flatrateTermId(flatrateTermId)
				.productId(moduleConfig.getProductId())
				.currencyId(currencyId)
				.lockOwner(createInvoiceCandidateRequest.getLockOwner())
				.moduleConfig(moduleConfig)
				.build();

		final ComputingResponse response = computingMethodHandler.compute(request);

		final UomId stockUomId;

		stockUomId = productBL.getStockUOMId(moduleConfig.getProductId());

		Check.assumeEquals(currencyId, response.getPrice().getCurrencyId());
		Check.assumeEquals(stockUomId, response.getPrice().getUomId());
		Check.assumeEquals(stockUomId, response.getQty().getUomId());

		return response;
	}

	@NonNull
	private TaxId getTaxId(
			@NonNull final I_C_Invoice_Candidate invoiceCandidate,
			@NonNull final I_C_Flatrate_Term term)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());
		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.optionalOfRepoId(invoiceCandidate.getModCntr_Module_ID())
				.orElseThrow(() -> new AdempiereException("Modular Contract Module cannot be missing at this point !")
						.appendParametersToMessage()
						.setParameter("C_Flatrate_Term_ID", flatrateTermId.getRepoId()));

		final ModularContractType moduleContractType = modularContractSettingsBL.getModuleContractType(modularContractModuleId);

		final TaxCategoryId taxCategoryId;

		if (!moduleContractType.isMatching(ComputingMethodType.INTERIM_CONTRACT))
		{
			taxCategoryId = modularContractService.getContractSpecificTaxCategoryId(ContractSpecificPriceRequest.builder()
					.modularContractModuleId(modularContractModuleId)
					.flatrateTermId(flatrateTermId)
					.build());
		}
		else
		{
			final FlatrateTermId interimContractId = flatrateBL.getInterimContractIdByModularContractIdAndDate(flatrateTermId, TimeUtil.asInstant(invoiceCandidate.getDateOrdered()));

			//interimContractId can be null, if ComputingMethodType.INTERIM_CONTRACT is present, but no interim contract was created (in this case the price will always be 0)
			final FlatrateTermId contractToUse = interimContractId != null ? interimContractId : flatrateTermId;
			taxCategoryId = modularContractService.getContractSpecificTaxCategoryId(
					ContractSpecificPriceRequest.builder()
							.flatrateTermId(contractToUse)
							.modularContractModuleId(modularContractModuleId)
							.build());
		}

		final BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId = invoiceCandBL.getBillLocationId(invoiceCandidate, true);
		final OrgId orgId = OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID());
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(invoiceCandidate.isSOTrx());

		final Tax tax = taxDAO.getBy(TaxQuery.builder()
				.orgId(orgId)
				.bPartnerLocationId(bPartnerLocationAndCaptureId)
				.dateOfInterest(invoiceCandidate.getDateOrdered())
				.soTrx(soTrx)
				.taxCategoryId(taxCategoryId)
				.build());

		if (tax == null)
		{
			throw TaxNotFoundException.builder()
					.taxCategoryId(taxCategoryId)
					.isSOTrx(soTrx.toBoolean())
					.billDate(invoiceCandidate.getDateOrdered())
					.orgId(orgId)
					.build();
		}

		return tax.getTaxId();
	}

	private void setC_DocTypeInvoice(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final BigDecimal total = invoiceCandidate.getQtyEntered().multiply(invoiceCandidate.getPriceEntered());

		final DocTypeQuery.DocTypeQueryBuilder queryBuilder = DocTypeQuery.builder()
				.adClientId(invoiceCandidate.getAD_Client_ID())
				.adOrgId(invoiceCandidate.getAD_Org_ID())
				.isSOTrx(false);

		if (total.signum() >= 0)
		{
			queryBuilder.docBaseType(InvoiceDocBaseType.VendorInvoice.getDocBaseType())
					.docSubType(getModCntrInvoiceType().getPositiveAmtDocSubType());
		}
		else
		{
			queryBuilder.docBaseType(InvoiceDocBaseType.VendorCreditMemo.getDocBaseType())
					.docSubType(getModCntrInvoiceType().getNegativAmtDocSubType());
		}

		final DocTypeId docTypeId = docTypeBL.getDocTypeId(queryBuilder.build());

		invoiceCandidate.setC_DocTypeInvoice_ID(docTypeId.getRepoId());
	}

	protected @NonNull ModCntrInvoiceType getModCntrInvoiceType()
	{
		return ModCntrInvoiceType.Final;
	}

	@Value
	@Builder
	protected static class CreateInvoiceCandidateRequest
	{
		@NonNull I_C_Flatrate_Term modularContract;
		@NonNull ModuleConfig moduleConfig;
		@NonNull YearAndCalendarId yearAndCalendarId;
		@NonNull PricingSystemId pricingSystemId;
		@NonNull LockOwner lockOwner;
	}

	public boolean isHandlerFor(@NonNull final I_C_Flatrate_Term term)
	{
		return !term.isReadyForDefinitiveInvoice();
	}

}
