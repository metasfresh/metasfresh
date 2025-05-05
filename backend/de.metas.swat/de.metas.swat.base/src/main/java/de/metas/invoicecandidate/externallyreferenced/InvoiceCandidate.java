/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoicecandidate.externallyreferenced;

import com.google.common.collect.ImmutableList;
import de.metas.auction.AuctionId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.document.DocTypeId;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceCandidateUpsertRequest;
import de.metas.invoicecandidate.spi.ILCandHandlerId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceCandidate
{
	public static InvoiceCandidateBuilder createBuilder(@NonNull final InvoiceCandidateUpsertRequest upsertRequest)
	{
		return InvoiceCandidate
				.builder()
				.id(upsertRequest.getInvoiceCandidateId())
				.billPartnerInfo(upsertRequest.getBillPartnerInfo())
				.dateOrdered(upsertRequest.getDateOrdered())
				.discountOverride(upsertRequest.getDiscountOverride())
				.externalHeaderId(upsertRequest.getExternalHeaderId())
				.externalLineId(upsertRequest.getExternalLineId())
				.invoiceDocTypeId(upsertRequest.getInvoiceDocTypeId())
				.invoiceRuleOverride(upsertRequest.getInvoiceRuleOverride())
				.invoicingUomId(upsertRequest.getInvoicingUomId())
				.lineDescription(upsertRequest.getLineDescription())
				.orgId(upsertRequest.getOrgId())
				.poReference(upsertRequest.getPoReference())
				.presetDateInvoiced(upsertRequest.getPresetDateInvoiced())
				.priceEnteredOverride(upsertRequest.getPriceEnteredOverride())
				.productId(upsertRequest.getProductId())
				.qtyDelivered(upsertRequest.getQtyDelivered())
				.qtyOrdered(upsertRequest.getQtyOrdered())
				.soTrx(upsertRequest.getSoTrx())
				.projectId(upsertRequest.getProjectId())
				.invoiceDetailItems(upsertRequest.getInvoiceDetailItems())
				.activityId(upsertRequest.getActivityId())
				.paymentTermId(upsertRequest.getPaymentTermId())
				.harvestYearAndCalendarId(upsertRequest.getHarvestYearAndCalendarId())
				.isInterimInvoice(upsertRequest.isInterimInvoice())
				.handlerId(upsertRequest.getHandlerId())
				.isManual(upsertRequest.isManual())
				.isActive(true)
				.processed(false)
				.flatrateTermId(upsertRequest.getFlatrateTermId());
	}

	private final OrgId orgId;

	// may be null if this instance was not loaded by the repo
	private final InvoiceCandidateId id;

	@Nullable
	private ExternalId externalHeaderId;

	@Nullable
	private ExternalId externalLineId;

	@Nullable
	private String poReference;

	private final BPartnerInfo billPartnerInfo;

	private final ProductId productId;

	private final InvoiceRule invoiceRule;

	@Nullable
	private InvoiceRule invoiceRuleOverride;

	private final SOTrx soTrx;

	@Nullable
	private LocalDate dateOrdered;

	@Nullable
	private LocalDate presetDateInvoiced;

	private StockQtyAndUOMQty qtyOrdered;

	private StockQtyAndUOMQty qtyDelivered;

	private final UomId invoicingUomId;

	private final PricingSystemId pricingSystemId;

	/**
	 * when loaded from DB, come IC records can have an empty priceListVersionId.
	 */
	private final PriceListVersionId priceListVersionId;

	private final ProductPrice priceEntered;

	/**
	 * If given, then productId and currencyId have to match!
	 */
	@Nullable
	private ProductPrice priceEnteredOverride;

	private final ProductPrice priceActual;

	private final Percent discount;

	private final TaxId taxId;

	@Nullable
	private Percent discountOverride;

	@Nullable
	private DocTypeId invoiceDocTypeId;

	@Nullable
	private String lineDescription;

	@Nullable
	private String descriptionBottom;

	@Nullable
	private UserId userInChargeId;

	@Nullable
	private ProjectId projectId;

	@Nullable
	private ActivityId activityId;

	@NonNull
	PaymentTermId paymentTermId;

	@Nullable final YearAndCalendarId harvestYearAndCalendarId;

	boolean processed;
	boolean isActive;

	/**
	 * Note that an IC can **also** be referenced internally by an {@code I_Invoice_Candidate} import-record
	 */
	@Nullable
	private final TableRecordReference recordReference;

	private List<InvoiceDetailItem> invoiceDetailItems;
	private final boolean isInterimInvoice;
	private final ILCandHandlerId handlerId;
	private final boolean isManual;
	private final AuctionId auctionId;

	@Nullable
	private final FlatrateTermId flatrateTermId;

	@Builder
	private InvoiceCandidate(
			@NonNull final OrgId orgId,
			@Nullable final InvoiceCandidateId id,
			@Nullable final ExternalId externalHeaderId,
			@Nullable final ExternalId externalLineId,
			@Nullable final String poReference,
			@NonNull final BPartnerInfo billPartnerInfo,
			@NonNull final ProductId productId,
			@NonNull final InvoiceRule invoiceRule,
			@Nullable final InvoiceRule invoiceRuleOverride,
			@NonNull final SOTrx soTrx,
			@Nullable final LocalDate dateOrdered,
			@Nullable final LocalDate presetDateInvoiced,
			@NonNull final StockQtyAndUOMQty qtyOrdered,
			@NonNull final StockQtyAndUOMQty qtyDelivered,
			@NonNull final UomId invoicingUomId,
			@NonNull final PricingSystemId pricingSystemId,
			@Nullable final PriceListVersionId priceListVersionId,
			@NonNull final ProductPrice priceEntered,
			@Nullable final ProductPrice priceEnteredOverride,
			@NonNull final Percent discount,
			@Nullable final Percent discountOverride,
			@NonNull final ProductPrice priceActual,
			@NonNull final TaxId taxId,
			@Nullable final DocTypeId invoiceDocTypeId,
			@Nullable final UserId userInChargeId,
			@Nullable final ActivityId activityId,
			@Nullable final String lineDescription,
			@Nullable final ProjectId projectId,
			@Nullable final String descriptionBottom,
			@NonNull final PaymentTermId paymentTermId,
			@Nullable final YearAndCalendarId harvestYearAndCalendarId,
			@Nullable final TableRecordReference recordReference,
			@Nullable final List<InvoiceDetailItem> invoiceDetailItems,
			final boolean isInterimInvoice,
			@NonNull final ILCandHandlerId handlerId,
			@Nullable final FlatrateTermId flatrateTermId,
			final boolean isManual,
			final boolean processed,
			final boolean isActive,
			@Nullable final AuctionId auctionId)
	{
		this.orgId = orgId;
		this.id = id;
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.poReference = poReference;
		this.billPartnerInfo = billPartnerInfo;
		this.productId = productId;
		this.invoiceRule = invoiceRule;
		this.invoiceRuleOverride = invoiceRuleOverride;
		this.soTrx = soTrx;
		this.dateOrdered = dateOrdered;
		this.presetDateInvoiced = presetDateInvoiced;
		this.qtyOrdered = qtyOrdered;
		this.qtyDelivered = qtyDelivered;
		this.invoicingUomId = invoicingUomId;
		this.pricingSystemId = pricingSystemId;
		this.priceListVersionId = priceListVersionId;
		this.priceEntered = priceEntered;
		this.priceEnteredOverride = priceEnteredOverride;
		this.discount = discount;
		this.discountOverride = discountOverride;
		this.priceActual = priceActual;
		this.taxId = taxId;
		this.invoiceDocTypeId = invoiceDocTypeId;
		this.lineDescription = lineDescription;
		this.projectId = projectId;
		this.descriptionBottom = descriptionBottom;
		this.userInChargeId = userInChargeId;
		this.activityId = activityId;
		this.paymentTermId = paymentTermId;
		this.harvestYearAndCalendarId = harvestYearAndCalendarId;
		this.recordReference = recordReference;
		this.invoiceDetailItems = invoiceDetailItems != null ? ImmutableList.copyOf(invoiceDetailItems) : ImmutableList.of();
		this.isInterimInvoice = isInterimInvoice;
		this.handlerId = handlerId;
		this.isManual = isManual;
		this.auctionId = auctionId;
		this.flatrateTermId = flatrateTermId;
		this.processed = processed;
		this.isActive = isActive;

		final CurrencyId currencyId = CollectionUtils
				.extractSingleElement(
						ImmutableList.of(priceEntered, priceActual),
						ProductPrice::getCurrencyId);

		if (priceEnteredOverride != null)
		{
			if (!priceEnteredOverride.getProductId().equals(productId))
			{
				throw new AdempiereException("priceEnteredOverride.productId=" + priceEnteredOverride.getProductId() + " is inconsistent with candidate.productId=" + productId)
						.appendParametersToMessage()
						.setParameter("candidate", this);
			}
			if (!priceEnteredOverride.getUomId().equals(invoicingUomId))
			{
				throw new AdempiereException("priceEnteredOverride.uomId=" + priceEnteredOverride.getUomId() + " is inconsistent with candidate.invoicingUomId=" + invoicingUomId)
						.appendParametersToMessage()
						.setParameter("candidate", this);
			}
			if (!priceEnteredOverride.getCurrencyId().equals(currencyId))
			{
				throw new AdempiereException("priceEnteredOverride.currencyId=" + priceEnteredOverride.getCurrencyId() + " is inconsistent with candidate.currencyId=" + currencyId)
						.appendParametersToMessage()
						.setParameter("candidate", this);
			}
		}
	}
}
