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
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTerm;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTermQuery;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceSettings;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceSettingsDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class FlatrateTermInterimInvoice_Handler implements ConditionTypeSpecificInvoiceCandidateHandler
{

	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInterimInvoiceSettingsDAO interimInvoiceSettingsDAO = Services.get(IInterimInvoiceSettingsDAO.class);
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceFlatrateTermDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);

	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Term.TYPE_CONDITIONS_InterimInvoice;
	}

	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return contractsDAO.createInterimInvoiceSearchCriteria(null)
				.iterate(I_C_Flatrate_Term.class);
	}

	@Override
	public Quantity calculateQtyEntered(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getInterimInvoiceFlatrateTermForIC(invoiceCandidateRecord);

		final Quantity qtyDelivered = interimInvoiceFlatrateTermBL.getQtyDelivered(interimInvoiceFlatrateTerm);

		return qtyDelivered.negateIf(!invoiceCandidateRecord.isInterimInvoice());
	}

	@NonNull
	private InterimInvoiceFlatrateTerm getInterimInvoiceFlatrateTermForIC(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final I_C_Flatrate_Term flatrateTerm = HandlerTools.retrieveTerm(invoiceCandidateRecord);
		return getInterimInvoiceFlatrateTermForTerm(flatrateTerm, invoiceCandidateRecord);
	}

	@NonNull
	private InterimInvoiceFlatrateTerm getInterimInvoiceFlatrateTermForTerm(@NonNull final I_C_Flatrate_Term flatrateTerm, @Nullable final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(flatrateTerm.getBill_BPartner_ID());
		final ProductId productId = ProductId.ofRepoId(flatrateTerm.getM_Product_ID());
		final InvoiceCandidateId id = invoiceCandidateRecord != null ? InvoiceCandidateId.ofRepoIdOrNull(invoiceCandidateRecord.getC_Invoice_Candidate_ID()) : null;
		final boolean isInterimInvoice = invoiceCandidateRecord != null && invoiceCandidateRecord.isInterimInvoice();

		final List<InterimInvoiceFlatrateTerm> interimInvoiceFlatrateTerms = interimInvoiceFlatrateTermDAO.retrieveBy(InterimInvoiceFlatrateTermQuery.builder()
						.productId(productId)
						.bpartnerId(bpartnerId)
						.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
						.startDate(TimeUtil.asInstantNonNull(flatrateTerm.getStartDate()))
						.endDate(TimeUtil.asInstantNonNull(flatrateTerm.getEndDate()))
						.build())
				.collect(Collectors.toList());

		InterimInvoiceFlatrateTerm result = null;
		if (id != null)
		{ //check if it was already associated with a InterimInvoiceFlatrateTerm
			result = interimInvoiceFlatrateTerms.stream()
					.filter(iift -> isInterimInvoice ? id.equals(iift.getInterimInvoiceCandidateId()) : id.equals(iift.getWithholdingInvoiceCandidateId()))
					.findFirst()
					.orElse(null);
		}
		if (result == null)
		{ // find the InterimInvoiceFlatrateTerm that shall be associated with this invoice candidate
			result = interimInvoiceFlatrateTerms.stream()
					.filter(iift -> iift.getInterimInvoiceCandidateId() == null || iift.getWithholdingInvoiceCandidateId() == null)
					.findFirst()
					.orElse(null);
		}

		Check.assumeNotNull(result, "Cannot identify a C_InterimInvoice_FlatrateTerm record for invoice candidate {} & flatrate term {}", id, flatrateTerm.getC_Flatrate_Term_ID());
		return result;
	}

	@Override
	@NonNull
	public IInvoiceCandidateHandler.CandidatesAutoCreateMode isMissingInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		return contractsDAO.createInterimInvoiceSearchCriteria(flatrateTerm).anyMatch()
				? IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES
				: IInvoiceCandidateHandler.CandidatesAutoCreateMode.DONT;
	}

	@Override
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(@NonNull final I_C_Flatrate_Term term)
	{
		return ImmutableList.of(createWithholdingIC(term), createInterimIC(term));
	}

	private I_C_Invoice_Candidate createInterimIC(final I_C_Flatrate_Term term)
	{
		final I_C_Invoice_Candidate invoiceCandidate = createBaseIC(term);
		invoiceCandidate.setIsInterimInvoice(true);

		final DocTypeId interimInvoiceDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.APInvoice)
				.docSubType(X_C_DocType.DOCSUBTYPE_InterimInvoice)
				.adClientId(term.getAD_Client_ID())
				.adOrgId(term.getAD_Org_ID())
				.build());
		invoiceCandidate.setC_DocTypeInvoice_ID(interimInvoiceDocTypeId.getRepoId());

		return invoiceCandidate;
	}

	private I_C_Invoice_Candidate createWithholdingIC(@NonNull final I_C_Flatrate_Term term)
	{
		final I_C_Invoice_Candidate invoiceCandidate = createBaseIC(term);

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());
		final InterimInvoiceSettings interimInvoiceSettings = interimInvoiceSettingsDAO.getForTerm(flatrateTermId);
		Check.assumeNotNull(interimInvoiceSettings, "Could not find interim invoice settings for flatrateTerm {}", flatrateTermId);
		invoiceCandidate.setM_Product_ID(interimInvoiceSettings.getWithholdingProductId().getRepoId());

		final DocTypeId interimInvoiceDocTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.APInvoice)
				.docSubType(X_C_DocType.DOCSUBTYPE_Withholding)
				.adClientId(term.getAD_Client_ID())
				.adOrgId(term.getAD_Org_ID())
				.build());
		invoiceCandidate.setC_DocTypeInvoice_ID(interimInvoiceDocTypeId.getRepoId());

		return invoiceCandidate;
	}

	private I_C_Invoice_Candidate createBaseIC(final @NonNull I_C_Flatrate_Term term)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getInterimInvoiceFlatrateTermForTerm(term, null);

		final I_C_Invoice_Candidate invoiceCandidate = HandlerTools.createIcAndSetCommonFields(term);

		// Relying on the InvoiceRule set from C_Flatrate_Conditions instead of overwriting it with one that allows for quality discount.
		// Without an invoice rule that takes into account delivered items, there will be no calculation for quality discount
		// See de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.computeToInvoiceExclOverride

		// invoiceCandidate.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());

		invoiceCandidate.setC_Order_ID(0);
		invoiceCandidate.setC_OrderLine_ID(0);
		invoiceCandidate.setIsSOTrx(false);
		invoiceCandidate.setC_UOM_ID(interimInvoiceFlatrateTerm.getUomId().getRepoId());
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
	public IInvoiceCandidateHandler.PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getInterimInvoiceFlatrateTermForIC(ic);
		final I_C_Invoice_Candidate originalIC = getInvoiceCandidateForOrderLine(interimInvoiceFlatrateTerm.getOrderLineId());

		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(ic);

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(term.getC_TaxCategory_ID());

		final TaxId taxId = TaxId.ofRepoId(originalIC.getC_Tax_ID());

		return IInvoiceCandidateHandler.PriceAndTax.builder()
				.pricingSystemId(PricingSystemId.ofRepoId(originalIC.getM_PricingSystem_ID()))
				.priceActual(originalIC.getPriceActual())
				.priceEntered(originalIC.getPriceActual()) // cg : task 04917 -- same as price actual
				.priceUOMId(UomId.ofRepoId(originalIC.getC_UOM_ID())) // 07090: when setting a priceActual, we also need to specify a PriceUOM
				.taxCategoryId(taxCategoryId)
				.taxId(taxId)
				.taxIncluded(originalIC.isTaxIncluded())
				.currencyId(CurrencyId.ofRepoIdOrNull(originalIC.getC_Currency_ID()))
				.build();
	}

	@NonNull
	private I_C_Invoice_Candidate getInvoiceCandidateForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(orderLineId);

		if (invoiceCandidates.size() > 1)
		{
			//should not happen
			throw new AdempiereException("More than one invoice candidate found for orderLineId=" + orderLineId);
		}
		return invoiceCandidates.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No invoice candidate found for orderLineId=" + orderLineId));
	}

	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@NonNull final I_C_Flatrate_Term term)
	{
		final IInvoiceCandidateHandler.PriceAndTax priceAndTax = calculatePriceAndTax(icRecord);
		IInvoiceCandInvalidUpdater.updatePriceAndTax(icRecord, priceAndTax);
	}

	@Override
	public void postSave(@NonNull final I_C_Invoice_Candidate ic)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getInterimInvoiceFlatrateTermForIC(ic);
		if (ic.isInterimInvoice())
		{
			interimInvoiceFlatrateTermDAO.save(interimInvoiceFlatrateTerm.toBuilder()
					.interimInvoiceCandidateId(InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID()))
					.build());
		}
		else
		{
			interimInvoiceFlatrateTermDAO.save(interimInvoiceFlatrateTerm.toBuilder()
					.withholdingInvoiceCandidateId(InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID()))
					.build());
		}
	}

}
