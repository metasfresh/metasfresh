/*
 * #%L
 * de.metas.business
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

package de.metas.order.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.costing.ChargeId;
import de.metas.costing.impl.ChargeRepository;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.DocumentLocation;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceAndDiscount;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.impl.OnConsignmentAttributeService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMBL;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static de.metas.util.Check.assume;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

public class OrderLineBL implements IOrderLineBL
{
	private static final AdMessageKey MSG_COUNTER_DOC_MISSING_MAPPED_PRODUCT = AdMessageKey.of("de.metas.order.CounterDocMissingMappedProduct");
	private static final String SYSCONFIG_SetBOMDescription = "de.metas.order.sales.line.SetBOMDescription";

	private static final Logger logger = LogManager.getLogger(OrderLineBL.class);

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private IOrderBL orderBL()
	{
		return Services.get(IOrderBL.class);
	}

	@Override
	public List<I_C_OrderLine> getByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		return orderDAO.retrieveOrderLinesByOrderIds(orderIds);
	}

	@Override
	public I_C_OrderLine getOrderLineById(@NonNull final OrderLineId orderLineId)
	{
		return orderDAO.getOrderLineById(orderLineId);
	}

	@Override
	@NonNull
	public OrderId getOrderIdByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return OrderId.ofRepoId(orderDAO.getOrderLineById(orderLineId).getC_Order_ID());
	}

	private I_C_UOM getUOM(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(orderLine.getC_UOM_ID());
		if (uomId != null)
		{
			return uomDAO.getById(uomId);
		}

		// fallback to stocking UOM
		return getStockingUOM(orderLine);
	}

	private I_C_UOM getStockingUOM(final org.compiere.model.I_C_OrderLine orderLine)
	{
		return productBL.getStockUOM(orderLine.getM_Product_ID());
	}

	@Override
	public Quantity getQtyEntered(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final BigDecimal qty = orderLine.getQtyEntered();
		final I_C_UOM uom = getUOM(orderLine);
		return Quantity.of(qty, uom);
	}

	@Override
	public Quantity getQtyOrdered(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderAndLineId);
		return getQtyOrdered(orderLine);
	}

	@Override
	public Quantity getQtyOrdered(@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyOrdered = orderLine.getQtyOrdered();
		final I_C_UOM uom = getStockingUOM(orderLine);
		return Quantity.of(qtyOrdered, uom);
	}

	@Override
	public Quantity getQtyToDeliver(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderAndLineId);
		return getQtyToDeliver(orderLine);
	}

	private Quantity getQtyToDeliver(@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyOrdered = orderLine.getQtyOrdered();
		final BigDecimal qtyDelivered = orderLine.getQtyDelivered();
		final BigDecimal qtyToDeliver = qtyOrdered.subtract(qtyDelivered);

		final I_C_UOM uom = getStockingUOM(orderLine);

		return Quantity.of(qtyToDeliver, uom);
	}

	@Override
	public Map<OrderAndLineId, Quantity> getQtyToDeliver(@NonNull final Collection<OrderAndLineId> orderAndLineIds)
	{
		return orderDAO.getOrderLinesByIds(orderAndLineIds)
				.entrySet()
				.stream()
				.map(GuavaCollectors.mapValue(this::getQtyToDeliver))
				.collect(GuavaCollectors.toImmutableMap());
	}

	@Override
	public void setTaxAmtInfo(final I_C_OrderLine ol)
	{
		final int taxId = ol.getC_Tax_ID();
		if (taxId <= 0)
		{
			ol.setTaxAmtInfo(BigDecimal.ZERO);
			return;
		}

		final boolean taxIncluded = isTaxIncluded(ol);
		final BigDecimal lineAmout = ol.getLineNetAmt();
		final CurrencyPrecision taxPrecision = getTaxPrecision(ol);

		final I_C_Tax tax = MTax.get(Env.getCtx(), taxId);

		final BigDecimal taxAmtInfo = taxBL.calculateTaxAmt(tax, lineAmout, taxIncluded, taxPrecision.toInt());
		ol.setTaxAmtInfo(taxAmtInfo);
	}

	@Override
	public void setShipper(final I_C_OrderLine ol)
	{
		final I_C_Order order = ol.getC_Order();

		final int orderShipperId = order.getM_Shipper_ID();
		if (orderShipperId > 0)
		{
			logger.debug("Setting M_Shipper_ID={} from {}", orderShipperId, order);
			ol.setM_Shipper_ID(orderShipperId);
		}
		else
		{
			logger.debug("Looking for M_Shipper_ID via ship-to-bpartner of {}", order);

			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
			if (bpartnerId == null)
			{
				logger.warn("{} has no ship-to-bpartner", order);
				return;
			}

			final ShipperId shipperId = bpartnerDAO.getShipperId(bpartnerId);
			if (shipperId == null)
			{
				// task 07034: nothing to do
				return;
			}

			logger.debug("Setting M_Shipper_ID={} from ship-to-bpartner", shipperId);
			ol.setM_Shipper_ID(shipperId.getRepoId());
		}
	}

	@Override
	public BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(final BigDecimal priceActual, final BigDecimal discount, final int precision)
	{
		return OrderLinePriceAndDiscount.calculatePriceEnteredFromPriceActualAndDiscount(priceActual, discount, precision);
	}

	@Override
	public TaxCategoryId getTaxCategoryId(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final ChargeRepository chargeRepo = SpringContextHolder.instance.getBean(ChargeRepository.class);

		final ChargeId chargeId = ChargeId.ofRepoIdOrNull(orderLine.getC_Charge_ID());
		// In case we have a charge, use the tax category from charge
		if (chargeId != null)
		{
			// TODO get rid of C_Charge from C_OrderLine alltogether
			final I_C_Charge chargeRecord = chargeRepo.getById(chargeId);
			return TaxCategoryId.ofRepoId(chargeRecord.getC_TaxCategory_ID());
		}

		final OrderLinePriceUpdateRequest request = OrderLinePriceUpdateRequest.ofOrderLine(orderLine);

		return OrderLinePriceCalculator.builder()
				.request(request)
				.orderLineBL(this)
				.build()
				.computeTaxCategoryId();
	}

	@Override
	public I_C_OrderLine createOrderLine(@NonNull final I_C_Order order)
	{
		final I_C_OrderLine ol = newInstance(I_C_OrderLine.class);
		ol.setC_Order(order);
		setOrder(ol, order);

		if (order.isSOTrx() && order.isDropShip())
		{
			final BPartnerLocationAndCaptureId deliveryLocationId = orderBL().getShipToLocationId(order);
			final int contactIdRepo = order.getDropShip_User_ID() > 0 ? order.getDropShip_User_ID() : order.getAD_User_ID();
			final BPartnerContactId contactId = BPartnerContactId.ofRepoIdOrNull(deliveryLocationId.getBpartnerId(), contactIdRepo);

			OrderLineDocumentLocationAdapterFactory
					.locationAdapter(ol)
					.setFrom(DocumentLocation.builder()
							.bpartnerId(deliveryLocationId.getBpartnerId())
							.bpartnerLocationId(deliveryLocationId.getBpartnerLocationId())
							.locationId(deliveryLocationId.getLocationCaptureId())
							.contactId(contactId)
							.build());
		}

		return ol;
	}

	@Override
	public void setOrder(final org.compiere.model.I_C_OrderLine ol, final I_C_Order order)
	{
		ol.setAD_Org_ID(order.getAD_Org_ID());

		OrderLineDocumentLocationAdapterFactory.locationAdapter(ol).setFromOrderHeader(order);

		ol.setM_PriceList_Version_ID(0); // the current PLV might be add or'd with the new order's PL.

		ol.setM_Warehouse_ID(order.getM_Warehouse_ID());
		ol.setDateOrdered(order.getDateOrdered());
		ol.setDatePromised(order.getDatePromised());
		ol.setC_Currency_ID(order.getC_Currency_ID());

		// Don't set Activity, etc as they are overwrites
	}

	@Override
	public <T extends I_C_OrderLine> T createOrderLine(final I_C_Order order, final Class<T> orderLineClass)
	{
		final I_C_OrderLine orderLine = createOrderLine(order);
		return InterfaceWrapperHelper.create(orderLine, orderLineClass);
	}

	@Override
	public void updateLineNetAmtFromQtyEntered(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final Quantity qtyInPriceUOM = convertQtyEnteredToPriceUOM(orderLine);
		updateLineNetAmtFromQtyInPriceUOM(orderLine, qtyInPriceUOM);
	}

	@Override
	public void updateLineNetAmtFromQty(
			@NonNull final Quantity qtyOverride,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final Quantity qtyInPriceUOM = convertQtyToPriceUOM(qtyOverride, orderLine);
		updateLineNetAmtFromQtyInPriceUOM(orderLine, qtyInPriceUOM);
	}

	private void updateLineNetAmtFromQtyInPriceUOM(
			@NonNull final org.compiere.model.I_C_OrderLine orderLine,
			@NonNull final Quantity qtyInPriceUOM)
	{
		final BigDecimal lineNetAmt = computeQtyNetPriceFromOrderLine(orderLine, qtyInPriceUOM);

		logger.debug("Setting LineNetAmt={} to {}", lineNetAmt, orderLine);

		orderLine.setLineNetAmt(lineNetAmt);
	}

	@Override
	public IPricingResult computePrices(final OrderLinePriceUpdateRequest request)
	{
		return OrderLinePriceCalculator.builder()
				.request(request)
				.orderLineBL(this)
				.build()
				.computePrices();
	}

	@Override
	public void updatePrices(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		updatePrices(OrderLinePriceUpdateRequest.ofOrderLine(orderLine));
	}

	@Override
	public void updatePrices(@NonNull final OrderLinePriceUpdateRequest request)
	{
		OrderLinePriceCalculator.builder()
				.request(request)
				.orderLineBL(this)
				.build()
				.updateOrderLine();
	}

	@Override
	public void updateQtyReserved(@Nullable final I_C_OrderLine orderLine)
	{
		if (orderLine == null)
		{
			logger.debug("Given orderLine is NULL; returning");
			return; // not our business
		}

		// make two simple checks that work without loading additional stuff
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			logger.debug("Given orderLine {} has M_Product_ID<=0; setting QtyReserved=0.", orderLine);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}
		if (orderLine.getQtyOrdered().signum() <= 0 || orderLine.isDeliveryClosed())
		{
			logger.debug("Given orderLine {} has QtyOrdered<=0 or delivery was closed; setting QtyReserved=0.", orderLine);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final I_C_Order order = orderLine.getC_Order();
		final DocStatus orderDocStatus = DocStatus.ofCode(order.getDocStatus());
		if (!orderDocStatus.isInProgressCompletedOrClosed())
		{
			logger.debug("C_Order {} of given orderLine {} has DocStatus {}; setting QtyReserved=0.", order, orderLine, orderDocStatus);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId != null && docTypeBL.isSalesProposal(docTypeId))
		{
			logger.debug("C_Order {} of given orderLine {} has C_DocType {} which is a proposal; setting QtyReserved=0.", order, orderLine, docTypeId);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final org.compiere.model.I_M_Product product = productBL.getById(productId);
		if (!product.isStocked())
		{
			logger.debug("Given orderLine {} has M_Product {} which is not stocked; setting QtyReserved=0.", orderLine, product);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final BigDecimal qtyReservedRaw = orderLine.getQtyOrdered().subtract(orderLine.getQtyDelivered());
		final BigDecimal qtyReserved = BigDecimal.ZERO.max(qtyReservedRaw); // not less than zero

		logger.debug("Given orderLine {} has QtyOrdered={} and QtyDelivered={}; setting QtyReserved={}.",
				orderLine, orderLine.getQtyOrdered(), orderLine.getQtyDelivered(), qtyReserved);
		orderLine.setQtyReserved(qtyReserved);
	}

	@Override
	public void updatePriceActual(final I_C_OrderLine orderLine, final CurrencyPrecision precision)
	{
		final BigDecimal priceActual = OrderLinePriceAndDiscount.of(orderLine, precision)
				.updatePriceActual()
				.getPriceActual();
		orderLine.setPriceActual(priceActual);
	}

	@Override
	public void setProductId(
			@NonNull final org.compiere.model.I_C_OrderLine orderLine,
			@NonNull final ProductId productId,
			final boolean setUOM)
	{
		orderLine.setM_Product_ID(productId.getRepoId());
		if (setUOM)
		{
			final UomId uomId = productBL.getStockUOMId(productId);
			orderLine.setC_UOM_ID(uomId.getRepoId());
		}

		orderLine.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
	}    // setM_Product_ID

	@Override
	public PriceListVersionId getPriceListVersionId(final I_C_OrderLine orderLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		if (orderLine.getM_PriceList_Version_ID() > 0)
		{
			return PriceListVersionId.ofRepoId(orderLine.getM_PriceList_Version_ID());
		}
		else
		{
			// If the line doesn't have a pricelist version, take the one from order.
			final I_C_Order order = orderLine.getC_Order();

			final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it
			return priceListDAO.retrievePriceListVersionIdOrNull(
					PriceListId.ofRepoId(order.getM_PriceList_ID()),
					getPriceDate(orderLine, order),
					processedPLVFiltering);
		}
	}

	/**
	 * task 07080
	 */
	@NonNull
	static ZonedDateTime getPriceDate(
			final org.compiere.model.I_C_OrderLine orderLine,
			final I_C_Order order)
	{
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class); // as long as this is a static method, we can't use the orgDAO field. 
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()));

		ZonedDateTime date = TimeUtil.asZonedDateTime(orderLine.getDatePromised(), timeZone);
		// if null, then get date promised from order
		if (date == null)
		{
			date = TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone);
		}
		// still null, then get date ordered from order line
		if (date == null)
		{
			date = TimeUtil.asZonedDateTime(orderLine.getDateOrdered(), timeZone);
		}
		// still null, then get date ordered from order
		if (date == null)
		{
			date = TimeUtil.asZonedDateTime(order.getDateOrdered(), timeZone);
		}
		return date;
	}

	@Override
	public Quantity convertQtyEnteredToPriceUOM(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		return convertQtyToPriceUOM(getQtyEntered(orderLine), orderLine);
	}

	@Override
	public Quantity convertQtyEnteredToStockUOM(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final BigDecimal qtyEntered = orderLine.getQtyEntered();

		final UomId uomId = UomId.ofRepoIdOrNull(orderLine.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());

		assume(uomId != null || productId != null,
				"For calling this method to make any sense, the given orderLine, needs to have at least a product *or* uom; C_OrderLine={}", orderLine);
		if (productId == null)
		{
			return Quantitys.of(qtyEntered, uomId);
		}
		if (uomId == null)
		{
			final UomId stockUOMId = productBL.getStockUOMId(productId);
			return Quantitys.of(qtyEntered, stockUOMId);
		}

		if (uomDAO.isUOMForTUs(uomId))
		{
			// we can't use any conversion rate, but need to rely on qtyItemCapacity which is coming from order line's CU-TU (M_HU_PI_Item_Product)
			return Quantitys.of(computeQtyOrderedUsingQtyItemCapacity(orderLine), productId);
		}
		else
		{
			final BigDecimal qtyOrdered = uomConversionBL.convertToProductUOM(productId, qtyEntered, uomId);
			// TODO check if null; but should have been checked by DefaultOLCandValidator
			return Quantitys.of(qtyOrdered, productId);
		}
	}

	private BigDecimal extractQtyItemCapacity(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final BigDecimal qtyItemCapacity = orderLine.getQtyItemCapacity();
		if (qtyItemCapacity.signum() <= 0 && orderLine.getQtyEntered().signum() != 0)
		{
			throw new AdempiereException(TranslatableStrings.constant("Missing QtyItemCapacity for C_ORderLine_ID="+orderLine.getC_OrderLine_ID()))
					.appendParametersToMessage()
					.setParameter("C_Order_ID", orderLine.getC_Order_ID())
					.setParameter("Line", orderLine.getLine())
					.setParameter("M_Product_ID", orderLine.getM_Product_ID());
		}
		return qtyItemCapacity;
	}

	private BigDecimal computeQtyOrderedUsingQtyItemCapacity(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final BigDecimal qtyItemCapacity = extractQtyItemCapacity(orderLine);

		return orderLine.getQtyEntered().multiply(qtyItemCapacity);
	}

	@Override
	public Quantity convertQtyToPriceUOM(
			@NonNull final Quantity sourceQuantity,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final UomId targetUomId = UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID());
		return convertToTargetUOM(sourceQuantity, orderLine, targetUomId);
	}

	@Override
	public Quantity convertQtyToUOM(
			@NonNull final Quantity sourceQuantity,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final UomId targetUomId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		return convertToTargetUOM(sourceQuantity, orderLine, targetUomId);
	}

	private Quantity convertToTargetUOM(
			final @NonNull Quantity sourceQuantity,
			final @NonNull org.compiere.model.I_C_OrderLine orderLine,
			final @Nullable UomId targetUomId)
	{
		if (targetUomId == null || targetUomId.equals(sourceQuantity.getUomId()))
		{
			return sourceQuantity;
		}

		final boolean targetIsTUUom = uomDAO.isUOMForTUs(targetUomId);
		final boolean sourceIsTUUom = uomDAO.isUOMForTUs(sourceQuantity.getUomId());
		final UOMConversionContext conversionCtx = UOMConversionContext.of(orderLine.getM_Product_ID());

		if (sourceIsTUUom && targetIsTUUom)
		{
			return sourceQuantity;
		}
		else if (!sourceIsTUUom && !targetIsTUUom)
		{
			return uomConversionBL.convertQuantityTo(sourceQuantity, conversionCtx, targetUomId);
		}

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final BigDecimal itemCapacityInStockUOM = extractQtyItemCapacity(orderLine);

		if (sourceIsTUUom)
		{
			// we can't use any conversion rate, but need to rely on qtyItemCapacity which is originally coming from order line's CU-TU (M_HU_PI_Item_Product)
			// therefore we take the detour via qtyOrdered
			// IMPORTANT: we don't use the current orderLine's getQtyOrdered from DB, because e.g. it might be 0 if the shipment-schedule was closed, but still we might have a QtyEntered>0,
			// and we don't want this to be our concern here
			final Quantity targetQtyInQtockUOM = Quantitys.of(sourceQuantity.toBigDecimal().multiply(itemCapacityInStockUOM), productId);
			assume(!uomDAO.isUOMForTUs(targetQtyInQtockUOM.getUomId()), "Our stock-Keeping is never done in a TUs-UOM; qtyInQtockUOM={}; C_OrderLine={}", targetQtyInQtockUOM, orderLine);

			return uomConversionBL.convertQuantityTo(targetQtyInQtockUOM, conversionCtx, targetUomId);
		}

		// sourceIsTUUom is false and targetIsTUUom is true at this point
		// like in above we need to take the detour via qtyOrdered; see comment there
		final Quantity sourceQtyInQtockUOM = uomConversionBL.convertToProductUOM(sourceQuantity, productId);
		assume(!uomDAO.isUOMForTUs(sourceQtyInQtockUOM.getUomId()), "Our stock-Keeping is never done in a TUs-UOM; qtyInQtockUOM={}; C_OrderLine={}", sourceQtyInQtockUOM, orderLine);

		return sourceQtyInQtockUOM.divide(itemCapacityInStockUOM);
	}

	@Override
	public boolean isTaxIncluded(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final Tax tax = taxBL.getTaxById(TaxId.ofRepoId(orderLine.getC_Tax_ID()));

		final I_C_Order order = orderLine.getC_Order();
		return orderBL().isTaxIncluded(order, tax);
	}

	@Override
	public CurrencyPrecision getPricePrecision(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderLine.getC_Order();
		return orderBL().getPricePrecision(order);
	}

	@Override
	public CurrencyPrecision getAmountPrecision(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderLine.getC_Order();
		return orderBL().getAmountPrecision(order);
	}

	@Override
	public CurrencyPrecision getTaxPrecision(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderLine.getC_Order();
		return orderBL().getTaxPrecision(order);
	}

	@Override
	public boolean isAllowedCounterLineCopy(final org.compiere.model.I_C_OrderLine fromLine)
	{
		// DO not copy the line if it's packing material. The packaging lines will be created later
		return !fromLine.isPackagingMaterial();
	}

	@Override
	public void copyOrderLineCounter(final org.compiere.model.I_C_OrderLine line, final org.compiere.model.I_C_OrderLine fromLine)
	{
		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(fromLine, de.metas.interfaces.I_C_OrderLine.class);

		if (ol.isPackagingMaterial())
		{
			// do nothing! the packaging lines will be created later
			return;
		}

		// link the line with the one from the counter document
		line.setRef_OrderLine_ID(fromLine.getC_OrderLine_ID());

		final ProductId lineProductId = ProductId.ofRepoIdOrNull(line.getM_Product_ID());
		if (lineProductId != null)      // task 09700
		{
			final I_AD_Org lineOrg = orgDAO.getById(line.getAD_Org_ID());
			final I_M_Product lineProduct = productBL.getById(lineProductId);

			final OrgId productOrgId = OrgId.ofRepoIdOrAny(lineProduct.getAD_Org_ID());
			if (!productOrgId.isAny())
			{
				// task 09700 the product from the original order is org specific, so we need to substitute it with the product from the counter-org.
				final ProductId counterProductId = productBL.retrieveMappedProductIdOrNull(lineProductId, productOrgId);
				if (counterProductId == null)
				{
					final I_AD_Org productOrg = orgDAO.getById(productOrgId);
					throw new AdempiereException(
							MSG_COUNTER_DOC_MISSING_MAPPED_PRODUCT,
							lineProduct.getValue(), productOrg.getName(), lineOrg.getName());
				}
				line.setM_Product_ID(counterProductId.getRepoId());
			}
		}
	}

	@Override
	public ProductPrice getCostPrice(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final BigDecimal poCostPrice = orderLine.getPriceCost();
		if (poCostPrice != null && poCostPrice.signum() != 0)
		{
			final UomId productUomId = productBL.getStockUOMId(productId);

			return ProductPrice.builder()
					.productId(productId)
					.uomId(productUomId)
					.money(Money.of(poCostPrice, currencyId))
					.build();
		}

		UomId priceUomId = UomId.ofRepoId(orderLine.getPrice_UOM_ID());
		if (priceUomId == null)
		{
			priceUomId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		}

		final BigDecimal priceActual = orderLine.getPriceActual();
		if (!isTaxIncluded(orderLine))
		{
			return ProductPrice.builder()
					.productId(productId)
					.uomId(priceUomId)
					.money(Money.of(priceActual, currencyId))
					.build();
		}

		final int taxId = orderLine.getC_Tax_ID();
		if (taxId <= 0)
		{
			// shall not happen
			return ProductPrice.builder()
					.productId(productId)
					.uomId(priceUomId)
					.money(Money.of(priceActual, currencyId))
					.build();
		}

		final MTax tax = MTax.get(Env.getCtx(), taxId);
		if (tax.isZeroTax() || tax.isReverseCharge())
		{
			return ProductPrice.builder()
					.productId(productId)
					.uomId(priceUomId)
					.money(Money.of(priceActual, currencyId))
					.build();
		}

		final CurrencyPrecision taxPrecision = getTaxPrecision(orderLine);
		final BigDecimal taxAmt = taxBL.calculateTaxAmt(tax, priceActual, true/* taxIncluded */, taxPrecision.toInt());
		final BigDecimal priceActualWithoutTax = priceActual.subtract(taxAmt);
		return ProductPrice.builder()
				.productId(productId)
				.uomId(priceUomId)
				.money(Money.of(priceActualWithoutTax, currencyId))
				.build();
	}

	@Override
	public ProductPrice getPriceActual(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final UomId productUomId = productBL.getStockUOMId(productId);
		final BigDecimal priceActual = orderLine.getPriceActual();

		return ProductPrice.builder()
				.productId(productId)
				.uomId(productUomId)
				.money(Money.of(priceActual, currencyId))
				.build();
	}

	@Override
	public PaymentTermId getPaymentTermId(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final PaymentTermId paymentTermOverrideId = PaymentTermId.ofRepoIdOrNull(orderLine.getC_PaymentTerm_Override_ID());
		return paymentTermOverrideId != null
				? paymentTermOverrideId
				: PaymentTermId.ofRepoId(orderLine.getC_Order().getC_PaymentTerm_ID());
	}

	@Override
	public PriceLimitRuleResult computePriceLimit(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		return OrderLinePriceCalculator.builder()
				.request(OrderLinePriceUpdateRequest.ofOrderLine(orderLine))
				.orderLineBL(this)
				.build()
				.computePriceLimit();
	}

	/**
	 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/4535">task</a>
	 */
	@Override
	public void updateProductDescriptionFromProductBOMIfConfigured(final org.compiere.model.I_C_OrderLine orderLine)
	{
		if (!isSetBOMDescription())
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final String description = productBOMBL.getBOMDescriptionForProductId(productId);
		if (Check.isEmpty(description, true))
		{
			return;
		}

		orderLine.setProductDescription(description);
	}

	private boolean isSetBOMDescription()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_SetBOMDescription, false);
	}

	@Override
	public void updateProductDocumentNote(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			orderLine.setM_Product_DocumentNote(null);
			return;
		}

		final I_C_Order order = orderLine.getC_Order();

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final org.compiere.model.I_C_BPartner partner = bpartnerDAO.getById(bpartnerId);
		final String adLanguage = partner.getAD_Language();

		final org.compiere.model.I_M_Product product = productBL.getById(productId);
		final org.compiere.model.I_M_Product translatedProduct = translate(product, org.compiere.model.I_M_Product.class, adLanguage);

		orderLine.setM_Product_DocumentNote(translatedProduct.getDocumentNote());
	}

	@Override
	public BigDecimal computeQtyNetPriceFromOrderLine(
			@NonNull final org.compiere.model.I_C_OrderLine orderLine,
			@NonNull final Quantity qty)
	{
		final Quantity qtyInPriceUOM = convertQtyToPriceUOM(qty, orderLine);

		final I_C_Order order = orderLine.getC_Order();
		final int priceListId = order.getM_PriceList_ID();
		final CurrencyPrecision netPrecision = priceListBL.getAmountPrecision(PriceListId.ofRepoId(priceListId));

		BigDecimal lineNetAmt = qtyInPriceUOM.toBigDecimal().multiply(orderLine.getPriceActual());
		lineNetAmt = netPrecision.roundIfNeeded(lineNetAmt);

		return lineNetAmt;
	}

	@Override
	public void save(final org.compiere.model.I_C_OrderLine orderLine)
	{
		orderDAO.save(orderLine);
	}

	@Override
	@NonNull
	public CurrencyPrecision extractPricePrecision(@NonNull final org.compiere.model.I_C_OrderLine olRecord)
	{
		return extractPriceList(olRecord)
				.filter(priceListRecord -> !isNull(priceListRecord, I_M_PriceList.COLUMNNAME_PricePrecision))
				.filter(priceListRecord -> priceListRecord.getPricePrecision() >= 0)
				.map(I_M_PriceList::getPricePrecision)
				.map(CurrencyPrecision::ofInt)
				.orElseGet(() -> getPrecisionFromCurrency(olRecord));
	}

	@NonNull
	private Optional<I_M_PriceList> extractPriceList(@NonNull final org.compiere.model.I_C_OrderLine olRecord)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(olRecord.getM_PriceList_Version_ID());

		if (priceListVersionId != null)
		{
			final I_M_PriceList result = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);
			logger.debug("C_OrderLine {} has M_PriceList_Version_ID={}; -> return M_PriceList={}", olRecord.getC_OrderLine_ID(), priceListVersionId, result);
			return Optional.ofNullable(result);
		}

		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(olRecord.getC_Order_ID()));
		final PricingSystemId orderPricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());

		if (orderPricingSystemId != null)
		{
			final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(olRecord.getC_BPartner_ID(), olRecord.getC_BPartner_Location_ID());
			final I_C_BPartner_Location partnerLocation = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);

			if (partnerLocation == null)
			{
				logger.debug("C_OrderLine {} has M_PricingSystem_ID={}, but no partnerLocation; -> return M_PriceList=null", olRecord.getC_OrderLine_ID(), olRecord.getBase_PricingSystem_ID());
				return Optional.empty();
			}

			final OrgId orgId = OrgId.ofRepoId(olRecord.getAD_Org_ID());
			final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);
			final ZonedDateTime orderDate = Objects.requireNonNull(TimeUtil.asZonedDateTime(olRecord.getDateOrdered(), orgZoneId));

			final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

			final I_C_Location location = locationDAO.getById(LocationId.ofRepoId(partnerLocation.getC_Location_ID()));
			final CountryId bpCountryId = CountryId.ofRepoId(location.getC_Country_ID());

			final I_M_PriceList result = priceListBL.getCurrentPricelistOrNull(orderPricingSystemId, bpCountryId, orderDate, soTrx);

			logger.debug("C_OrderLine {} has M_PricingSystem_ID={}, effective C_BPartner_Location_ID={}, DateOrdered={} and SOTrx={}; -> return M_PriceList={}",
					olRecord.getC_OrderLine_ID(), orderPricingSystemId, bPartnerLocationId, orderDate, soTrx, result);
			return Optional.ofNullable(result);
		}

		logger.debug("C_OrderLine {} has neither M_PriceList_Version_ID nor M_PricingSystem_ID; -> return M_PriceList=null", olRecord.getC_OrderLine_ID());
		return Optional.empty();
	}

	@NonNull
	private CurrencyPrecision getPrecisionFromCurrency(@NonNull final org.compiere.model.I_C_OrderLine olRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(olRecord.getC_Currency_ID());
		return currencyBL.getStdPrecision(currencyId);
	}

	@Override
	public void setBPLocation(final I_C_OrderLine orderLine)
	{
		final int c_bPartner_id = orderLine.getC_BPartner_ID();
		if (c_bPartner_id <= 0)
		{
			return;
		}

		final org.compiere.model.I_C_BPartner bp = partnerBL.getById(BPartnerId.ofRepoId(c_bPartner_id));
		final I_C_BPartner_Location bpLoc = partnerBL.extractShipToLocation(bp);
		setBPartnerLocation(orderLine, bpLoc);
	}

	private void setBPartnerLocation(@NonNull final I_C_OrderLine orderLine, @Nullable final I_C_BPartner_Location bpartnerLocation)
	{
		final BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId = bpartnerLocation != null ? BPartnerLocationAndCaptureId.ofRecord(bpartnerLocation) : null;
		OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine).setLocationAndResetRenderedAddress(bpartnerLocationAndCaptureId);
	}

	@Override
	public void updateIsOnConsignmentNoSave(@NonNull I_C_OrderLine orderLine)
	{
		final boolean isOnConsignment = computeIsOnConsignmentFromASI(orderLine);
		orderLine.setIsOnConsignment(isOnConsignment);
	}

	private boolean computeIsOnConsignmentFromASI(@NonNull final I_C_OrderLine orderLine)
	{
		final OnConsignmentAttributeService onConsignmentAttributeService = SpringContextHolder.instance.getBean(OnConsignmentAttributeService.class);
		final I_M_AttributeSetInstance productSI = orderLine.getM_AttributeSetInstance();
		return onConsignmentAttributeService.isOnConsignment(productSI);
	}

	@Override
	public boolean isCatchWeight(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		return InvoicableQtyBasedOn.ofNullableCodeOrNominal(orderLine.getInvoicableQtyBasedOn()).isCatchWeight();
	}

}
