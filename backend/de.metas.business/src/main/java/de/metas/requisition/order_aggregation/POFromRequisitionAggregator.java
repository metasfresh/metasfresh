package de.metas.requisition.order_aggregation;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MOrder;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class POFromRequisitionAggregator
{
	// services
	private static final Logger log = LogManager.getLogger(POFromRequisitionAggregator.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	//
	// Parameters
	private final boolean consolidateDocument;
	private final boolean groupByRequestor;
	private final BPGroupId onlyVendorGroupId;
	private final VendorProvider vendorProvider;

	//
	// State
	private final HashSet<BPartnerId> excludedVendorIds = new HashSet<>();
	private final HashMap<BPartnerId, I_C_BPartner> bpartnersCache = new HashMap<>();
	private OrderAggregator currentOrderAggregator = null;
	private final HashMap<OrderKey, OrderAggregator> orderAggregatorsCache = new HashMap<>();

	@Builder
	private POFromRequisitionAggregator(
			final boolean consolidateDocument,
			final boolean groupByRequestor,
			@Nullable final BPGroupId onlyVendorGroupId)
	{
		this.consolidateDocument = consolidateDocument;
		this.groupByRequestor = groupByRequestor;
		this.onlyVendorGroupId = onlyVendorGroupId;
		this.vendorProvider = new VendorProvider();
	}

	public void aggregate(@NonNull final OrderCandidate candidate)
	{
		if (!candidate.getRequisitionDocStatus().isCompleted())
		{
			log.warn("Skip candidate because requisition status is not completed: {}", candidate);
			return;
		}

		if (candidate.isAlreadyAggregated())
		{
			log.warn("Skip candidate because requisition line was already aggregated: {}", candidate);
			return;
		}

		//
		// Skip lines without product or charge
		final ProductId productId = candidate.getProductId();
		if (productId == null && candidate.getC_Charge_ID() <= 0)
		{
			log.warn("Skip candidate because no product nor charge is set: {}", candidate);
			return;
		}

		final OrderKey orderKey = extractOrderKey(candidate).orElse(null);
		if (orderKey == null)
		{
			log.debug("Skip candidate because not eligible: {}", candidate);
			return;
		}

		getCreateOrder(orderKey).aggregate(candidate);
	}

	private Optional<OrderKey> extractOrderKey(final @NotNull OrderCandidate candidate)
	{
		final BPartnerId vendorId = vendorProvider.getVendorId(candidate);
		if (!isGenerateForVendor(vendorId))
		{
			return Optional.empty();
		}

		final Instant datePromised = candidate.getDateRequired();
		PriceListId priceListId = candidate.getPriceListId();
		if (priceListId != null && !isPOPriceList(priceListId))
		{
			priceListId = null;
		}

		return Optional.of(OrderKey.builder()
				.vendorId(vendorId)
				.datePromised(datePromised)
				.priceListId(priceListId)
				.requisitionId(!consolidateDocument ? candidate.getRequisitionId() : null)
				.requisitionDocumentNo(!consolidateDocument ? candidate.getRequisitionDocumentNo() : null)
				.requestorId(groupByRequestor ? candidate.getRequestorId().orElse(null) : null)
				.build());
	}

	@NonNull
	private OrderAggregator getCreateOrder(@NonNull final OrderKey key)
	{
		if (currentOrderAggregator != null && currentOrderAggregator.isMatching(key))
		{
			return currentOrderAggregator;
		}

		if (currentOrderAggregator != null)
		{
			currentOrderAggregator.save();
			currentOrderAggregator = null;
		}

		this.currentOrderAggregator = orderAggregatorsCache.computeIfAbsent(key, this::createOrder);
		return this.currentOrderAggregator;
	}

	private OrderAggregator createOrder(@NonNull final OrderKey key)
	{
		final MOrder order = new MOrder(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		order.setDatePromised(Timestamp.from(key.getDatePromised()));
		order.setIsSOTrx(false);
		orderBL.setDefaultDocTypeTargetId(order);
		order.setBPartner(getBPartnerById(key.getVendorId()));
		order.setM_PriceList_ID(PriceListId.toRepoId(key.getPriceListId()));

		// References
		final I_M_PriceList priceList = priceListDAO.getById(key.getPriceListId());
		order.setC_Currency_ID(priceList.getC_Currency_ID()); // task 05914 : currency is mandatory

		// default po document type
		if (!Check.isBlank(key.getRequisitionDocumentNo()))
		{
			order.setDescription(msgBL.translate(Env.getCtx(), "M_Requisition_ID") + ": " + key.getRequisitionDocumentNo());
		}

		order.saveEx();

		return OrderAggregator.builder()
				.productBL(productBL)
				.key(key)
				.order(order)
				.build();
	}

	public void done()
	{
		if (this.currentOrderAggregator != null)
		{
			this.currentOrderAggregator.save();
			this.currentOrderAggregator = null;
		}
	}

	/**
	 * Do we need to generate Purchase Orders for given Vendor
	 *
	 * @return true if it's allowed
	 */
	private boolean isGenerateForVendor(@NonNull final BPartnerId vendorId)
	{
		// No filter group was set => generate for all vendors
		if (onlyVendorGroupId == null)
		{
			return true;
		}

		if (excludedVendorIds.contains(vendorId))
		{
			return false;
		}

		final I_C_BPartner vendor = getBPartnerById(vendorId);
		final boolean match = vendor.getC_BP_Group_ID() == onlyVendorGroupId.getRepoId();
		if (!match)
		{
			excludedVendorIds.add(vendorId);
		}
		return match;
	}

	private boolean isPOPriceList(@Nullable final PriceListId M_PriceList_ID)
	{
		if (M_PriceList_ID == null)
		{
			return false;
		}

		final I_M_PriceList priceList = priceListDAO.getById(M_PriceList_ID);
		return priceList != null && !priceList.isSOPriceList();
	}

	private I_C_BPartner getBPartnerById(final BPartnerId bpartnerId)
	{
		return bpartnersCache.computeIfAbsent(bpartnerId, bpartnerDAO::getById);
	}
}
