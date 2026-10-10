package de.metas.contracts.refund.invoicecandidatehandler;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.CandidateAssignmentService;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContract.NextInvoiceDate;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.location.CountryId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.function.Consumer;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.compiere.util.TimeUtil.asLocalDate;
import static org.compiere.util.TimeUtil.asTimestamp;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class FlatrateTermRefund_Handler
		implements ConditionTypeSpecificInvoiceCandidateHandler
{
	public static final AdMessageKey MSG_REFUND_PRODUCT_HAS_NO_PRICE_IN_CURRENCY = AdMessageKey.of("de.metas.contracts.refund.RefundProductHasNoPriceInCurrency");

	@NonNull private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	// this handler is instantiated by the invoice candidate handler framework, not by Spring
	@NonNull private final SpringContextHolder.Lazy<RefundContractRepository> refundContractRepository = SpringContextHolder.lazyBean(RefundContractRepository.class);

	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Term.TYPE_CONDITIONS_Refund;
	}

	/**
	 * @return an empty iterator; invoice candidates that need to be there are created from {@link CandidateAssignmentService}.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(@Nullable final QueryLimit limit_IGNORED)
	{
		return ImmutableList
				.<I_C_Flatrate_Term> of()
				.iterator();
	}

	@NonNull
	@Override
	public CandidatesAutoCreateMode isMissingInvoiceCandidate(@Nullable final I_C_Flatrate_Term flatrateTerm)
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * Does nothing
	 */
	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final I_C_Flatrate_Term term)
	{
		// nothing to do
	}

	/**
	 * @return always one, in the respective term's UOM
	 */
	@Override
	public Quantity calculateQtyEntered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final UomId uomId = HandlerTools.retrieveUomId(invoiceCandidateRecord);
		final I_C_UOM uomRecord = loadOutOfTrx(uomId, I_C_UOM.class);

		return Quantity.of(ONE, uomRecord);
	}

	/**
	 * The price is updated in {@link CandidateAssignmentService}.
	 * <p>
	 * The tax follows the product that the refund is booked on (the bonus product, or else the config's product):
	 * its tax category comes from the regular pricing of that product for the bill partner, and the tax from the bill location, the date and the SOTrx.
	 * If there is no such product, then the tax remains unchanged.
	 * If the product has no price, or its price has no tax category, then the candidate gets an error instead of keeping the tax of the refunded goods.
	 * The product is priced on the price list of the candidate's pricing system in the candidate's currency, which is the currency of the refund (an amount per unit is refunded in the config's currency):
	 * that price list's version goes to the candidate, so the credit memo is issued on it. Without such a price list, the candidate gets an error.
	 */
	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final RefundContract refundContract = refundContractRepository.get().getById(FlatrateTermId.ofRepoId(invoiceCandidateRecord.getRecord_ID()));

		final ProductId refundProductId = RefundConfigs.extractRefundProductId(refundContract.getRefundConfigs());
		if (refundProductId == null || invoiceCandidateRecord.getBill_BPartner_ID() <= 0 || invoiceCandidateRecord.getBill_Location_ID() <= 0)
		{
			return PriceAndTax.NONE;
		}

		final OrgId orgId = OrgId.ofRepoId(invoiceCandidateRecord.getAD_Org_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(invoiceCandidateRecord.isSOTrx());
		// a refund candidate gets its date ordered from the invoice schedule when it is created (RefundInvoiceCandidateFactory)
		final Timestamp taxDate = CoalesceUtil.coalesce(invoiceCandidateRecord.getDateOrdered(), invoiceCandidateRecord.getDateToInvoice());
		if (taxDate == null)
		{
			return PriceAndTax.NONE;
		}

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(
						orgId,
						refundProductId,
						BPartnerId.ofRepoId(invoiceCandidateRecord.getBill_BPartner_ID()),
						Quantitys.of(ONE, refundProductId),
						soTrx)
				.setReferencedObject(invoiceCandidateRecord)
				.setPriceDate(TimeUtil.asLocalDate(taxDate, orgDAO.getTimeZone(orgId)))
				.setFailIfNotCalculated();
		final BPartnerLocationAndCaptureId billLocationId = InvoiceCandidateLocationAdapterFactory.billLocationAdapter(invoiceCandidateRecord).getBPartnerLocationAndCaptureId();
		final CountryId countryId = bpartnerDAO.getCountryId(billLocationId.getBpartnerLocationId());
		pricingContext.setCountryId(countryId);
		final PricingSystemId pricingSystemId = CoalesceUtil.coalesceSuppliers(
				() -> PricingSystemId.ofRepoIdOrNull(invoiceCandidateRecord.getM_PricingSystem_ID()),
				() -> bpartnerDAO.retrievePricingSystemIdOrNullInTrx(BPartnerId.ofRepoId(invoiceCandidateRecord.getBill_BPartner_ID()), soTrx));
		if (pricingSystemId != null)
		{
			pricingContext.setPricingSystemId(pricingSystemId);
		}
		final CurrencyId refundCurrencyId = CurrencyId.ofRepoIdOrNull(invoiceCandidateRecord.getC_Currency_ID());
		if (pricingSystemId != null && refundCurrencyId != null)
		{
			pricingContext.setPriceListId(retrievePriceListIdInCurrency(pricingSystemId, countryId, soTrx, refundCurrencyId, refundProductId));
		}

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);

		// without a tax category this is the Tax-Not-Found tax, so the candidate gets an error
		final TaxId taxId = taxBL.getTaxNotNull(
				invoiceCandidateRecord,
				pricingResult.getTaxCategoryId(),
				refundProductId.getRepoId(),
				taxDate,
				orgId,
				null, // warehouseId
				billLocationId,
				soTrx);

		return PriceAndTax.builder()
				.priceListVersionId(pricingResult.getPriceListVersionId())
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.taxId(taxId)
				.build();
	}

	private PriceListId retrievePriceListIdInCurrency(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final SOTrx soTrx,
			@NonNull final CurrencyId currencyId,
			@NonNull final ProductId refundProductId)
	{
		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, countryId, soTrx, currencyId);
		if (priceListId == null)
		{
			throw new AdempiereException(
					MSG_REFUND_PRODUCT_HAS_NO_PRICE_IN_CURRENCY,
					productBL.getProductValueAndName(refundProductId),
					currencyBL.getCurrencyCodeById(currencyId).toThreeLetterCode(),
					priceListDAO.getPricingSystemName(pricingSystemId))
					.markAsUserValidationError()
					.setParameter("M_PricingSystem_ID", pricingSystemId.getRepoId())
					.setParameter("C_Country_ID", countryId.getRepoId())
					.setParameter("C_Currency_ID", currencyId.getRepoId());
		}
		return priceListId;
	}

	@Override
	public Consumer<I_C_Invoice_Candidate> getInvoiceScheduleSetterFunction(
			@Nullable final Consumer<I_C_Invoice_Candidate> IGNORED_defaultImplementation)
	{
		return ic -> {

			final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(ic.getRecord_ID());

			final RefundContract refundContract = refundContractRepository.get().getById(flatrateTermId);
			final NextInvoiceDate nextInvoiceDate = refundContract.computeNextInvoiceDate(asLocalDate(ic.getDeliveryDate()));

			ic.setC_InvoiceSchedule_ID(nextInvoiceDate.getInvoiceSchedule().getId().getRepoId());
			ic.setDateToInvoice(asTimestamp(nextInvoiceDate.getDateToInvoice()));
		};
	}

	/** Just return the record's current date */
	@Override
	public Timestamp calculateDateOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return invoiceCandidateRecord.getDateOrdered();
	}
}
