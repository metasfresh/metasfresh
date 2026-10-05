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
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
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
	 * If there is no such product, or its price can't be found, then the tax remains unchanged.
	 */
	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final RefundContractRepository refundContractRepository = SpringContextHolder.instance.getBean(RefundContractRepository.class);
		final RefundContract refundContract = refundContractRepository.getById(FlatrateTermId.ofRepoId(invoiceCandidateRecord.getRecord_ID()));
		final ProductId refundProductId = RefundConfigs.extractRefundProductId(refundContract.getRefundConfigs());
		if (refundProductId == null || invoiceCandidateRecord.getBill_BPartner_ID() <= 0 || invoiceCandidateRecord.getBill_Location_ID() <= 0)
		{
			return PriceAndTax.NONE;
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
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
				.setPriceDate(TimeUtil.asLocalDate(taxDate, Services.get(IOrgDAO.class).getTimeZone(orgId)));
		final BPartnerLocationAndCaptureId billLocationId = InvoiceCandidateLocationAdapterFactory.billLocationAdapter(invoiceCandidateRecord).getBPartnerLocationAndCaptureId();
		pricingContext.setCountryId(Services.get(IBPartnerDAO.class).getCountryId(billLocationId.getBpartnerLocationId()));
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(invoiceCandidateRecord.getM_PricingSystem_ID());
		if (pricingSystemId != null)
		{
			pricingContext.setPricingSystemId(pricingSystemId);
		}

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
		if (!pricingResult.isCalculated() || pricingResult.getTaxCategoryId() == null)
		{
			return PriceAndTax.NONE;
		}

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				invoiceCandidateRecord,
				pricingResult.getTaxCategoryId(),
				refundProductId.getRepoId(),
				taxDate,
				orgId,
				null, // warehouseId
				billLocationId,
				soTrx);

		return PriceAndTax.builder()
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.taxId(taxId)
				.build();
	}

	@Override
	public Consumer<I_C_Invoice_Candidate> getInvoiceScheduleSetterFunction(
			@Nullable final Consumer<I_C_Invoice_Candidate> IGNORED_defaultImplementation)
	{
		return ic -> {

			final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(ic.getRecord_ID());

			final RefundContractRepository refundContractRepository = SpringContextHolder.instance.getBean(RefundContractRepository.class);
			final RefundContract refundContract = refundContractRepository.getById(flatrateTermId);
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
