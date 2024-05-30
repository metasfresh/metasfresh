/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interim.invoice.invoicecandidatehandler;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.location.ContractLocationHelper;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ContractSpecificPriceRequest;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.ContractSpecificPrice;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static java.util.Collections.emptyIterator;

public class FlatrateTermInterimInvoice_Handler implements ConditionTypeSpecificInvoiceCandidateHandler
{
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final ModularContractLogService modularContractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	@NonNull private final ModularContractService modularContractService = SpringContextHolder.instance.getBean(ModularContractService.class);

	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Term.TYPE_CONDITIONS_InterimInvoice;
	}

	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return emptyIterator();
	}

	@Override
	public Quantity calculateQtyEntered(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final UomId uomId = HandlerTools.retrieveUomId(invoiceCandidateRecord);

		final Quantity quantityEntered = Quantitys.of(invoiceCandidateRecord.getQtyEntered(), UomId.ofRepoId(invoiceCandidateRecord.getC_UOM_ID()));
		final ProductId productId = ProductId.ofRepoId(invoiceCandidateRecord.getM_Product_ID());
		return uomConversionBL.convertQuantityTo(quantityEntered, productId, uomId);
	}

	@Override
	@NonNull
	public IInvoiceCandidateHandler.CandidatesAutoCreateMode isMissingInvoiceCandidate(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		return IInvoiceCandidateHandler.CandidatesAutoCreateMode.DONT;
	}

	@Override
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final LockOwner lockOwner)
	{
		return ImmutableList.of(createInterimIC(term));
	}

	private I_C_Invoice_Candidate createInterimIC(final I_C_Flatrate_Term term)
	{
		final I_C_Invoice_Candidate invoiceCandidate = createBaseIC(term);

		final DocTypeId interimInvoiceDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.APInvoice)
				.docSubType(X_C_DocType.DOCSUBTYPE_InterimInvoice)
				.adClientId(term.getAD_Client_ID())
				.adOrgId(term.getAD_Org_ID())
				.build());
		invoiceCandidate.setC_DocTypeInvoice_ID(interimInvoiceDocTypeId.getRepoId());

		return invoiceCandidate;
	}

	private I_C_Invoice_Candidate createBaseIC(final @NonNull I_C_Flatrate_Term term)
	{
		final I_C_Invoice_Candidate invoiceCandidate = HandlerTools.createIcAndSetCommonFields(term);

		// Relying on the InvoiceRule set from C_Flatrate_Conditions instead of overwriting it with one that allows for quality discount.
		// Without an invoice rule that takes into account delivered items, there will be no calculation for quality discount
		// See de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.computeToInvoiceExclOverride

		// invoiceCandidate.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());

		invoiceCandidate.setC_Order_ID(0);
		invoiceCandidate.setC_OrderLine_ID(0);
		invoiceCandidate.setIsSOTrx(false);
		invoiceCandidate.setC_UOM_ID(term.getC_UOM_ID());
		invoiceCandidate.setQtyInvoiced(BigDecimal.ZERO);
		invoiceCandidate.setQtyToInvoice_Override(null);
		invoiceCandidate.setQtyInvoicedInUOM(BigDecimal.ZERO);
		invoiceCandidate.setQtyDelivered(BigDecimal.ZERO);
		invoiceCandidate.setQtyToInvoice(BigDecimal.ZERO);
		invoiceCandidate.setQtyToInvoiceInUOM(BigDecimal.ZERO);
		invoiceCandidate.setQtyOrdered(BigDecimal.ZERO);
		invoiceCandidate.setQtyEntered(BigDecimal.ZERO);
		invoiceCandidate.setQtyDeliveredInUOM(BigDecimal.ZERO);
		return invoiceCandidate;
	}

	@Override
	public IInvoiceCandidateHandler.PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);

		final ModularContractLogEntriesList interimLogs = getInterimLogs(term, ic);
		if (interimLogs.isEmpty())
		{
			throw new AdempiereException("No logs found for the contract and invoice candidate :" + term + " , " + ic);
		}

		final ModularContractLogEntry modularContractLogEntry = interimLogs.getFirstEntry();

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());

		final TaxCategoryId taxCategoryId = modularContractService.getContractSpecificTaxCategoryId(ContractSpecificPriceRequest.builder()
				.modularContractModuleId(modularContractLogEntry.getModularContractModuleId())
				.flatrateTermId(flatrateTermId)
				.build());

		final PricingSystemId pricingSystemId = modularContractService.getPricingSystemId(flatrateTermId);

		final ProductPrice productPrice = Check.assumeNotNull(modularContractLogEntry.getPriceActual(), "productPrice shouldn't be null");
		final UomId uomId = HandlerTools.retrieveUomId(ic);
		final ProductPrice productPriceToInvoice = productPrice.convertToUom(uomId,
				currencyBL.getStdPrecision(productPrice.getCurrencyId()),
				uomConversionBL
		);

		final ContractSpecificPrice contractSpecificPrice = ContractSpecificPrice.builder()
				.productPrice(productPriceToInvoice)
				.taxCategoryId(taxCategoryId)
				.pricingSystemId(pricingSystemId)
				.build();

		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(firstGreaterThanZero(ic.getC_VAT_Code_Override_ID(), ic.getC_VAT_Code_ID()));

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				term,
				taxCategoryId,
				term.getM_Product_ID(),
				ic.getDateOrdered(), // shipDate
				OrgId.ofRepoId(term.getAD_Org_ID()),
				null,
				CoalesceUtil.coalesceSuppliersNotNull(
						() -> ContractLocationHelper.extractDropshipLocationId(term),
						() -> ContractLocationHelper.extractBillToLocationId(term)),
				SOTrx.ofBoolean(ic.isSOTrx()),
				vatCodeId);

		return IInvoiceCandidateHandler.PriceAndTax.builder()
				.pricingSystemId(contractSpecificPrice.getPricingSystemId())
				.priceActual(contractSpecificPrice.getProductPrice().toBigDecimal())
				.priceEntered(contractSpecificPrice.getProductPrice().toBigDecimal()) // cg : task 04917 -- same as price actual
				.priceUOMId(contractSpecificPrice.getProductPrice().getUomId()) // 07090: when setting a priceActual, we also need to specify a PriceUOM
				.taxCategoryId(taxCategoryId)
				.taxId(taxId)
				.taxIncluded(term.isTaxIncluded())
				.currencyId(contractSpecificPrice.getProductPrice().getCurrencyId())
				.build();
	}

	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@NonNull final I_C_Flatrate_Term term)
	{
		final IInvoiceCandidateHandler.PriceAndTax priceAndTax = calculatePriceAndTax(icRecord);
		IInvoiceCandInvalidUpdater.updatePriceAndTax(icRecord, priceAndTax);
	}

	private ModularContractLogEntriesList getInterimLogs(final @NonNull I_C_Flatrate_Term flatrateTermRecord, @NonNull final I_C_Invoice_Candidate ic)
	{

		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()))
				.computingMethodType(ComputingMethodType.INTERIM_CONTRACT)
				.contractType(LogEntryContractType.INTERIM)
				.processed(true)
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID()))
				.build();
		final ModularContractLogEntriesList modularContractLogEntries = modularContractLogService.getModularContractLogEntries(query);
		modularContractLogService.validateLogPrices(modularContractLogEntries);

		return modularContractLogEntries;
	}

}
