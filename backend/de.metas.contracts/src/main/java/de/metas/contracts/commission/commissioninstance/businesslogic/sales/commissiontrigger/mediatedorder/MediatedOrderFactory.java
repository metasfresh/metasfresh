 /*
  * #%L
  * de.metas.contracts
  * %%
  * Copyright (C) 2021 metas GmbH
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

 package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder;

 import ch.qos.logback.classic.Level;
 import com.google.common.collect.ImmutableList;
 import de.metas.bpartner.BPartnerId;
 import de.metas.bpartner.service.IBPartnerOrgBL;
 import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
 import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
 import de.metas.currency.CurrencyPrecision;
 import de.metas.document.engine.DocStatus;
 import de.metas.logging.LogManager;
 import de.metas.order.IOrderBL;
 import de.metas.order.IOrderDAO;
 import de.metas.order.IOrderLineBL;
 import de.metas.order.OrderId;
 import de.metas.order.OrderLineId;
 import de.metas.organization.IOrgDAO;
 import de.metas.organization.OrgId;
 import de.metas.product.ProductId;
 import de.metas.quantity.Quantity;
 import de.metas.tax.api.ITaxDAO;
 import de.metas.tax.api.Tax;
 import de.metas.uom.IUOMConversionBL;
 import de.metas.uom.IUOMDAO;
 import de.metas.uom.UomId;
 import de.metas.util.Check;
 import de.metas.util.Loggables;
 import de.metas.util.Services;
 import lombok.NonNull;
 import org.compiere.model.I_C_Order;
 import org.compiere.model.I_C_OrderLine;
 import org.compiere.util.TimeUtil;
 import org.slf4j.Logger;
 import org.springframework.stereotype.Service;

 import java.math.BigDecimal;
 import java.time.ZoneId;
 import java.util.List;
 import java.util.Objects;
 import java.util.Optional;

 @Service
 public class MediatedOrderFactory
 {
	 private static final Logger logger = LogManager.getLogger(MediatedOrderFactory.class);

	 private final IOrderBL orderBL = Services.get(IOrderBL.class);
	 private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	 private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	 private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	 private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	 private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	 private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);
	 private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	 private final CommissionProductService commissionProductService;

	 public MediatedOrderFactory(@NonNull final CommissionProductService commissionProductService)
	 {
		 this.commissionProductService = commissionProductService;
	 }

	 @NonNull
	 public Optional<MediatedOrder> forRecord(@NonNull final I_C_Order mediatedOrder)
	 {
		 final DocStatus orderDocStatus = DocStatus.ofCode(mediatedOrder.getDocStatus());

		 if (!hasTheRightStatus(orderDocStatus))
		 {
			 Loggables.withLogger(logger, Level.DEBUG).addLog("C_Order.DocStatus is not supported: {}! C_OrderId: {}", orderDocStatus, mediatedOrder.getC_Order_ID());
			 return Optional.empty();
		 }

		 if (!orderBL.isMediated(mediatedOrder))
		 {
			 Loggables.withLogger(logger, Level.DEBUG).addLog("C_Order is not mediated! C_OrderId: {}", mediatedOrder.getC_Order_ID());
			 return Optional.empty();
		 }

		 final OrderId orderId = OrderId.ofRepoId(mediatedOrder.getC_Order_ID());

		 final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId, I_C_OrderLine.class);

		 if (Check.isEmpty(orderLines))
		 {
			 Loggables.withLogger(logger, Level.DEBUG).addLog("C_Order has no lines! C_OrderId: {}", mediatedOrder.getC_Order_ID());
			 return Optional.empty();
		 }

		 final OrgId orgId = OrgId.ofRepoId(mediatedOrder.getAD_Org_ID());

		 final Optional<BPartnerId> orgBPartnerIdOpt = bPartnerOrgBL.retrieveLinkedBPartnerId(orgId);
		 if (!orgBPartnerIdOpt.isPresent())
		 {
			 Loggables.withLogger(logger, Level.DEBUG).addLog("C_Order.AD_Org_ID has no linked BPartner! C_OrderId: {}, OrgId: {}", mediatedOrder.getC_Order_ID(), mediatedOrder.getAD_Org_ID());
			 return Optional.empty();
		 }

		 final ImmutableList<MediatedOrderLine> mediatedOrderLines = orderLines.stream()
				 .map(orderLine -> toMediatedOrderLine(orderLine, mediatedOrder.isTaxIncluded()))
				 .filter(Optional::isPresent)
				 .map(Optional::get)
				 .collect(ImmutableList.toImmutableList());

		 if (mediatedOrderLines.isEmpty())
		 {
			 Loggables.withLogger(logger, Level.DEBUG).addLog("C_Order has no 'commission-able' lines! C_OrderId: {}, OrgId: {}", mediatedOrder.getC_Order_ID());
			 return Optional.empty();
		 }

		 final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		 return Optional.of(MediatedOrder.builder()
									.orgId(orgId)
									.id(orderId)
									.orgBPartnerId(orgBPartnerIdOpt.get())
									.vendorBPartnerId(BPartnerId.ofRepoId(mediatedOrder.getC_BPartner_ID()))
									.orderDateAcct(Objects.requireNonNull(TimeUtil.asLocalDate(mediatedOrder.getDateAcct(), orgZoneId)))
									.updated(TimeUtil.asInstantNonNull(mediatedOrder.getUpdated()))
									.mediatedOrderLines(mediatedOrderLines)
									.build());
	 }

	 @NonNull
	 private Optional<MediatedOrderLine> toMediatedOrderLine(@NonNull final I_C_OrderLine orderLine, final boolean isTaxIncluded)
	 {
		 if (commissionProductService.productPreventsCommissioning(ProductId.ofRepoId(orderLine.getM_Product_ID())))
		 {
			 logger.debug("C_OrderLine: {} has M_Product_ID={} that prevents commissioning; -> return empty", orderLine.getC_OrderLine_ID(), orderLine.getM_Product_ID());
			 return Optional.empty();
		 }

		 return Optional.of(MediatedOrderLine.builder()
									.id(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
									.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
									.updated(Objects.requireNonNull(TimeUtil.asInstant(orderLine.getUpdated())))
									.invoicedCommissionPoints(getCommissionPoints(orderLine, isTaxIncluded))
									.build());
	 }

	 @NonNull
	 private CommissionPoints getCommissionPoints(@NonNull final I_C_OrderLine orderLine, final boolean isTaxIncluded)
	 {
		 if (orderLine.getQtyOrdered().signum() == 0)
		 {
			 return CommissionPoints.ZERO;
		 }

		 final Tax taxRecord = taxDAO.getTaxById(orderLine.getC_Tax_ID());
		 final CurrencyPrecision precision = orderLineBL.extractPricePrecision(orderLine);

		 final UomId stockUOMId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		 final UomId priceUOMId = UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID());
		 final Quantity orderedQtyStock = Quantity.of(orderLine.getQtyOrdered(), uomDao.getById(stockUOMId));

		 final Quantity orderedQtyPriceUOM = priceUOMId != null
				 ? uomConversionService.convertQuantityTo(orderedQtyStock, ProductId.ofRepoId(orderLine.getM_Product_ID()), priceUOMId)
				 : orderedQtyStock;

		 final BigDecimal priceForOrderedQty = orderedQtyPriceUOM.toBigDecimal().multiply(orderLine.getPriceActual());

		 final BigDecimal taxAdjustedAmount = taxRecord.calculateBaseAmt(
				 priceForOrderedQty,
				 isTaxIncluded,
				 precision.toInt());

		 return CommissionPoints.of(taxAdjustedAmount);
	 }

	 private boolean hasTheRightStatus(@NonNull final DocStatus orderDocStatus)
	 {
		 return orderDocStatus.isCompleted()
				 || orderDocStatus.isInProgress() // we need to consider this status for the case that a completed order is reactivated
				 || orderDocStatus.isClosed();
	 }
 }
