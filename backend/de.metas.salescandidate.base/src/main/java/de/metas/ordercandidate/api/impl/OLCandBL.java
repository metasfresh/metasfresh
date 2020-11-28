package de.metas.ordercandidate.api.impl;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.PO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.freighcost.FreightCostRule;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.BPartnerOrderParams;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.order.BPartnerOrderParamsRepository.BPartnerOrderParamsQuery;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandOrderDefaults;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandSource;
import de.metas.ordercandidate.api.OLCandsProcessorExecutor;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.Percent;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;

@Service
public class OLCandBL implements IOLCandBL
{
	private static final Logger logger = LogManager.getLogger(OLCandBL.class);

	private final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final BPartnerOrderParamsRepository bPartnerOrderParamsRepository;

	public OLCandBL(@NonNull final BPartnerOrderParamsRepository bPartnerOrderParamsRepository)
	{
		this.bPartnerOrderParamsRepository = bPartnerOrderParamsRepository;
	}

	@Override
	public void process(@NonNull final OLCandProcessorDescriptor processor)
	{
		final SpringContextHolder springContextHolder = SpringContextHolder.instance;
		final OLCandRegistry olCandRegistry = springContextHolder.getBean(OLCandRegistry.class);
		final OLCandRepository olCandRepo = springContextHolder.getBean(OLCandRepository.class);

		final OLCandSource candidatesSource = olCandRepo.getForProcessor(processor);

		OLCandsProcessorExecutor.builder()
				.processorDescriptor(processor)
				.olCandListeners(olCandRegistry.getListeners())
				.groupingValuesProviders(olCandRegistry.getGroupingValuesProviders())
				.candidatesSource(candidatesSource)
				.build()
				.process();
	}

	@Override
	public PricingSystemId getPricingSystemId(
			@NonNull final I_C_OLCand olCand,
			@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults)
	{
		if (olCand.getM_PricingSystem_ID() > 0)
		{
			return PricingSystemId.ofRepoId(olCand.getM_PricingSystem_ID());
		}

		if (bPartnerOrderParams != null && bPartnerOrderParams.getPricingSystemId().isPresent())
		{
			return bPartnerOrderParams.getPricingSystemId().get();
		}

		if (orderDefaults != null && orderDefaults.getPricingSystemId() != null)
		{
			return orderDefaults.getPricingSystemId();
		}

		return null;
	}

	@Override
	public DeliveryRule getDeliveryRule(
			@NonNull final I_C_OLCand olCandRecord,
			@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults)
	{
		if (!Check.isEmpty(olCandRecord.getDeliveryRule(), true))
		{
			return DeliveryRule.ofCode(olCandRecord.getDeliveryRule());
		}

		if (bPartnerOrderParams != null && bPartnerOrderParams.getDeliveryRule().isPresent())
		{
			return bPartnerOrderParams.getDeliveryRule().get();
		}

		if (orderDefaults != null && orderDefaults.getDeliveryRule() != null)
		{
			return orderDefaults.getDeliveryRule();
		}

		return null;
	}

	@Override
	public DeliveryViaRule getDeliveryViaRule(
			@NonNull final I_C_OLCand olCandRecord,
			@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults)
	{
		if (!Check.isEmpty(olCandRecord.getDeliveryViaRule(), true))
		{
			return DeliveryViaRule.ofCode(olCandRecord.getDeliveryViaRule());
		}

		if (bPartnerOrderParams != null && bPartnerOrderParams.getDeliveryViaRule().isPresent())
		{
			return bPartnerOrderParams.getDeliveryViaRule().get();
		}

		if (orderDefaults != null && orderDefaults.getDeliveryViaRule() != null)
		{
			return orderDefaults.getDeliveryViaRule();
		}
		return null;
	}

	@Override
	public FreightCostRule getFreightCostRule(@Nullable final BPartnerOrderParams bPartnerOrderParams, @Nullable final OLCandOrderDefaults orderDefaults)
	{
		if (bPartnerOrderParams != null && bPartnerOrderParams.getFreightCostRule().isPresent())
		{
			return bPartnerOrderParams.getFreightCostRule().get();
		}
		if (orderDefaults != null)
		{
			return orderDefaults.getFreightCostRule();
		}
		return null;
	}

	@Override
	public InvoiceRule getInvoiceRule(@Nullable final BPartnerOrderParams bPartnerOrderParams, @Nullable final OLCandOrderDefaults orderDefaults)
	{
		if (bPartnerOrderParams != null && bPartnerOrderParams.getInvoiceRule().isPresent())
		{
			return bPartnerOrderParams.getInvoiceRule().get();
		}
		if (orderDefaults != null)
		{
			return orderDefaults.getInvoiceRule();
		}
		return null;
	}

	@Override
	public PaymentRule getPaymentRule(@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults,
			@Nullable I_C_OLCand orderCandidateRecord)
	{
		final PaymentRule orderCandidatePaymentRule = orderCandidateRecord == null ? null
				: PaymentRule.ofNullableCode(orderCandidateRecord.getPaymentRule());
		final PaymentRule bpartnerOrderParamsPaymentRule = bPartnerOrderParams == null ? null
				: bPartnerOrderParams.getPaymentRule();
		final PaymentRule orderDefaultsPaymentRule = orderDefaults == null ? null
				: orderDefaults.getPaymentRule();

		return coalesce(orderCandidatePaymentRule,
				bpartnerOrderParamsPaymentRule,
				orderDefaultsPaymentRule);
	}

	@Override
	public PaymentTermId getPaymentTermId(@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults,
			@Nullable I_C_OLCand orderCandidateRecord)
	{
		final PaymentTermId orderCandidatePaymenTermId = orderCandidateRecord == null ? null
				: PaymentTermId.ofRepoIdOrNull(orderCandidateRecord.getC_PaymentTerm_ID());

		final PaymentTermId bpartnerOrderParamsPaymentTermId = bPartnerOrderParams == null ? null
				: bPartnerOrderParams.getPaymentTermId().orElse(null);

		final PaymentTermId orderDefaultsPaymentTermId = orderDefaults == null ? null
				: orderDefaults.getPaymentTermId();

		return coalesce(orderCandidatePaymenTermId,
				bpartnerOrderParamsPaymentTermId,
				orderDefaultsPaymentTermId);
	}

	@Override
	public ShipperId getShipperId(
			@Nullable final BPartnerOrderParams bPartnerOrderParams,
			@Nullable final OLCandOrderDefaults orderDefaults,
			@Nullable final I_C_OLCand orderCandidateRecord)
	{
		final ShipperId orderCandiateShipperId = orderCandidateRecord == null ? null : ShipperId.ofRepoIdOrNull(orderCandidateRecord.getM_Shipper_ID());

		final ShipperId bpartnerOrderParamsShipperId = bPartnerOrderParams == null ? null
				: bPartnerOrderParams.getShipperId().orElse(null);

		final ShipperId orderDefaultsShipperId = orderDefaults == null ? null
				: orderDefaults.getShipperId();

		return coalesce(orderCandiateShipperId,
				bpartnerOrderParamsShipperId,
				orderDefaultsShipperId);
	}

	@Override
	public DocTypeId getOrderDocTypeId(
			@Nullable final OLCandOrderDefaults orderDefaults,
			@Nullable final I_C_OLCand orderCandidateRecord)
	{
		final DocTypeId orderDocTypeId = orderCandidateRecord == null ? null : DocTypeId.ofRepoIdOrNull(orderCandidateRecord.getC_DocTypeOrder_ID());

		final DocTypeId orderDefaultsDocTypeId = orderDefaults == null ? null
				: orderDefaults.getDocTypeTargetId();

		return coalesce(orderDocTypeId,
				orderDefaultsDocTypeId);
	}

	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po, final IOLCandCreator olCandCreator)
	{
		Check.assumeNotNull(olCandCreator, "olCandCreator is not null");

		final I_C_OLCand olCand = olCandCreator.createFrom(po);
		if (po.set_ValueOfColumn("Processed", true))
		{
			po.saveEx();
		}

		if (olCand == null)
		{
			logger.info("{} returned null for {}; Nothing to do.", olCandCreator, po);
			return null;
		}

		olCand.setAD_Table_ID(po.get_Table_ID());
		olCand.setRecord_ID(po.get_ID());

		InterfaceWrapperHelper.save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(po, olCand); // 03745

		return olCand;
	}

	@Override
	public IPricingResult computePriceActual(
			@NonNull final I_C_OLCand olCandRecord,
			final BigDecimal qtyOverride,
			@Nullable final PricingSystemId pricingSystemIdOverride,
			final LocalDate date)
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setReferencedObject(olCandRecord);

		final IPricingResult pricingResult;

		// note that even with manual price and/or discount, we need to invoke the pricing engine, in order to get the tax category

		final BPartnerId billBPartnerId = effectiveValuesBL.getBillBPartnerEffectiveId(olCandRecord);
		final BPartnerInfo shipToPartnerInfo = effectiveValuesBL
				.getDropShipPartnerInfo(olCandRecord)
				.orElseGet(() -> effectiveValuesBL.getBuyerPartnerInfo(olCandRecord));

		final BigDecimal qty = qtyOverride != null ? qtyOverride : olCandRecord.getQtyEntered();

		final BPartnerOrderParams bPartnerOrderParams = getBPartnerOrderParams(olCandRecord);

		final PricingSystemId pricingSystemId = CoalesceUtil.coalesceSuppliers(
				() -> pricingSystemIdOverride,
				() -> getPricingSystemId(olCandRecord, bPartnerOrderParams, null/* orderDefaults */));

		if (pricingSystemId == null)
		{
			throw new AdempiereException("@M_PricingSystem@ @NotFound@")
					.appendParametersToMessage()
					.setParameter("effectiveBillPartnerId", effectiveValuesBL.getBillBPartnerEffectiveId(olCandRecord));
		}
		pricingCtx.setPricingSystemId(pricingSystemId); // set it to the context that way it will also be in the result, even if the pricing rules won't need it

		pricingCtx.setBPartnerId(billBPartnerId);
		pricingCtx.setQty(qty);
		pricingCtx.setPriceDate(date);
		pricingCtx.setSOTrx(SOTrx.SALES);

		pricingCtx.setDisallowDiscount(olCandRecord.isManualDiscount());

		final PriceListId plId = priceListDAO.retrievePriceListIdByPricingSyst(
				pricingSystemId,
				shipToPartnerInfo.getBpartnerLocationId(),
				SOTrx.SALES);
		if (plId == null)
		{
			throw new AdempiereException("@M_PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @DropShip_Location@ " + shipToPartnerInfo.getBpartnerLocationId());
		}
		pricingCtx.setPriceListId(plId);
		pricingCtx.setProductId(effectiveValuesBL.getM_Product_Effective_ID(olCandRecord));

		final CountryId countryId = bpartnerDAO.getBPartnerLocationCountryId(shipToPartnerInfo.getBpartnerLocationId());
		pricingCtx.setCountryId(countryId);

		pricingResult = pricingBL.calculatePrice(pricingCtx.setFailIfNotCalculated());

		final BigDecimal priceEntered;
		final Percent discount;
		final CurrencyId currencyId;

		if (olCandRecord.isManualPrice())
		{
			// both price and currency need to be already set in the olCand (only a price amount doesn't make sense with an unspecified currency)
			priceEntered = olCandRecord.getPriceEntered();
			currencyId = CurrencyId.ofRepoId(olCandRecord.getC_Currency_ID());
		}
		else
		{
			priceEntered = pricingResult.getPriceStd();
			currencyId = pricingResult.getCurrencyId();
		}

		if (olCandRecord.isManualDiscount())
		{
			discount = Percent.of(olCandRecord.getDiscount());
		}
		else
		{
			discount = pricingResult.getDiscount();
		}

		if (currencyId == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency@"
					+ "\n Pricing context: " + pricingCtx
					+ "\n Pricing result: " + pricingResult);
		}

		final BigDecimal priceActual = discount.subtractFromBase(priceEntered, pricingResult.getPrecision().toInt());
		pricingResult.setPriceStd(priceActual);

		pricingResult.setDisallowDiscount(false); // avoid exception
		pricingResult.setDiscount(discount);
		pricingResult.setDisallowDiscount(olCandRecord.isManualDiscount());

		return pricingResult;
	}

	@Override
	public BPartnerOrderParams getBPartnerOrderParams(@NonNull final I_C_OLCand olCandRecord)
	{
		final BPartnerId billBPartnerId = effectiveValuesBL.getBillBPartnerEffectiveId(olCandRecord);

		final BPartnerInfo shipToPartnerInfo = effectiveValuesBL
				.getDropShipPartnerInfo(olCandRecord)
				.orElseGet(() -> effectiveValuesBL.getBuyerPartnerInfo(olCandRecord));

		final BPartnerOrderParamsQuery query = BPartnerOrderParamsQuery.builder()
				.soTrx(SOTrx.SALES)
				.shipBPartnerId(shipToPartnerInfo.getBpartnerId())
				.billBPartnerId(billBPartnerId)
				.build();
		final BPartnerOrderParams params = bPartnerOrderParamsRepository.getBy(query);
		return params;
	}

	@Override
	public AttachmentEntry addAttachment(
			@NonNull final OLCandQuery olCandQuery,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final OLCandRepository olCandRepo = SpringContextHolder.instance.getBean(OLCandRepository.class);
		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		final List<TableRecordReference> olCandRefs = olCandRepo
				.getByQuery(olCandQuery)
				.stream()
				.map(olCand -> TableRecordReference.of(I_C_OLCand.Table_Name, olCand.getId()))
				.collect(ImmutableList.toImmutableList());

		if (olCandRefs.isEmpty())
		{
			throw new AdempiereException("addAttachment - Missing order line candiates for given olCandQuery")
					.appendParametersToMessage()
					.setParameter("olCandQuery", olCandQuery);
		}

		final TableRecordReference firstOLCandRef = olCandRefs.get(0);
		final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(firstOLCandRef, attachmentEntryCreateRequest);

		if (olCandRefs.size() == 1)
		{
			return attachmentEntry;
		}
		final List<TableRecordReference> remainingOLCandRefs = olCandRefs.subList(1, olCandRefs.size());
		return CollectionUtils.singleElement(attachmentEntryService.createAttachmentLinks(ImmutableList.of(attachmentEntry), remainingOLCandRefs));
	}
}
