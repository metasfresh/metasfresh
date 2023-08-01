package de.metas.order.model.interceptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.IOrderLinePricingConditions;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.warehouseassignment.ProductWarehouseAssignmentService;
import de.metas.warehouseassignment.ProductWarehouseAssignments;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.CalloutOrder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.isCopy;

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

@Component
@Interceptor(I_C_OrderLine.class)
@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	public static final AdMessageKey ERR_NEGATIVE_QTY_RESERVED = AdMessageKey.of("MSG_NegativeQtyReserved");
	private static final AdMessageKey ORDER_DIFFERENT_WAREHOUSE_MSG_KEY = AdMessageKey.of("ORDER_DIFFERENT_WAREHOUSE");
	private static final Logger logger = LogManager.getLogger(C_OrderLine.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerProductBL partnerProductBL = Services.get(IBPartnerProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderLinePricingConditions orderLinePricingConditions = Services.get(IOrderLinePricingConditions.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final OrderGroupCompensationChangesHandler groupChangesHandler;
	private final OrderLineDetailRepository orderLineDetailRepository;
	private final BPartnerSupplierApprovalService bPartnerSupplierApprovalService;

	private final DimensionService dimensionService;
	private final ProductWarehouseAssignmentService productWarehouseAssignmentService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	C_OrderLine(
			@NonNull final OrderGroupCompensationChangesHandler groupChangesHandler,
			@NonNull final OrderLineDetailRepository orderLineDetailRepository,
			@NonNull final BPartnerSupplierApprovalService bPartnerSupplierApprovalService,
			@NonNull final DimensionService dimensionService,
			@NonNull final ProductWarehouseAssignmentService productWarehouseAssignmentService)
	{
		this.groupChangesHandler = groupChangesHandler;
		this.orderLineDetailRepository = orderLineDetailRepository;
		this.bPartnerSupplierApprovalService = bPartnerSupplierApprovalService;
		this.dimensionService = dimensionService;
		this.productWarehouseAssignmentService = productWarehouseAssignmentService;

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_C_OrderLine orderLine)
	{
		unlinkReferencedOrderLines(orderLine);
		orderLineDetailRepository.deleteByOrderLineId(OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID()));
	}

	/**
	 * 09557: If a purchase order line is deleted, then all sales order lines need to un-reference it to avoid an FK-constraint-error
	 * FRESH-386: likewise, also make sure that counter document lines are unlinked as well.
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
	 * Task https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	private void unlinkReferencedOrderLines(final I_C_OrderLine purchaseOrderLine)
	{
		// 09557
		queryBL
				.createQueryBuilder(I_C_PO_OrderLine_Alloc.class)
				.addEqualsFilter(I_C_PO_OrderLine_Alloc.COLUMNNAME_C_PO_OrderLine_ID, purchaseOrderLine.getC_OrderLine_ID())
				.create()
				.delete();

		// FRESH-386
		queryBL
				.createQueryBuilder(I_C_OrderLine.class, purchaseOrderLine)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_Ref_OrderLine_ID, purchaseOrderLine.getC_OrderLine_ID()) // ref_orderline_id is used with counter docs
				.create()
				.update(ol -> {
					ol.setRef_OrderLine(null);
					return IQueryUpdater.MODEL_UPDATED;
				});

	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_M_Product_ID
	})
	public void validateHaddex(final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		if (!orderBL.isHaddexOrder(order))
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		if (productBL.isHaddexProduct(productId))
		{
			orderBL.validateHaddexDate(order);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_M_Product_ID
	})
	public void validateSupplierApproval(final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		if (order.isSOTrx())
		{
			// Only purchase orders are relevant
			return;
		}

		if (orderLine.getM_Product_ID() <= 0)
		{
			//nothing to do
			return;
		}

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		final ImmutableList<String> supplierApprovalNorms = productBL.retrieveSupplierApprovalNorms(productId);

		if (Check.isEmpty(supplierApprovalNorms))
		{
			// nothing to validate
			return;
		}

		final BPartnerId partnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()));

		bPartnerSupplierApprovalService.validateSupplierApproval(partnerId, TimeUtil.asLocalDate(order.getDatePromised(), timeZone), supplierApprovalNorms);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setProjectToOrderline(final I_C_OrderLine orderLine)
	{
		if (orderLine.getC_Order_ID() > 0 && orderLine.getC_Project_ID() <= 0)
		{
			final I_C_Order order = orderLine.getC_Order();
			orderLine.setC_Project_ID(order.getC_Project_ID());
		}
	}

	/**
	 * Set qtyOrdered, to make sure is up2date. Note that this value is also set in
	 * {@link CalloutOrder#amt(org.adempiere.ad.callout.api.ICalloutField))}.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID,
			I_C_OrderLine.COLUMNNAME_QtyEntered,
			I_C_OrderLine.COLUMNNAME_C_UOM_ID
	})
	public void setQtyOrdered(final I_C_OrderLine orderLine)
	{
		final Quantity qtyOrdered = orderLineBL.convertQtyEnteredToStockUOM(orderLine);
		orderLine.setQtyOrdered(qtyOrdered.toBigDecimal());
	}

	/**
	 * Task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyOrdered,
			I_C_OrderLine.COLUMNNAME_QtyDelivered,
			I_C_OrderLine.COLUMNNAME_IsDeliveryClosed,
			I_C_OrderLine.COLUMNNAME_C_Order_ID })
	public void updateReserved(final I_C_OrderLine orderLine)
	{
		orderLineBL.updateQtyReserved(orderLine);
	}

	/**
	 * Ported this method from de.metas.adempiere.modelvalidator.OrderLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyReserved)
	public void checkQtyReserved(final I_C_OrderLine ol)
	{
		if (ol.getQtyReserved().signum() >= 0)
		{
			return; // nothing to do
		}
		// task: 08665
		// replace the negative qty with 0, logging a warning message
		ol.setQtyReserved(BigDecimal.ZERO);

		// task: 08665 ts: but only log when in developer mode. i don't see how this warning should help the user
		if (developerModeBL.isEnabled())
		{
			final AdempiereException ex = new AdempiereException("@" + ERR_NEGATIVE_QTY_RESERVED + "@. Setting QtyReserved to ZERO."
																		 + "\nStorage: " + ol);
			logger.warn(ex.getLocalizedMessage(), ex);
		}
	}

	/**
	 * Task https://github.com/metasfresh/metasfresh/issues/3298
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyReserved)
	public void checkQtyOrdered(final I_C_OrderLine ol)
	{
		final boolean qtyOrderedLessThanZero = ol.getQtyOrdered().signum() < 0;

		Check.errorIf(qtyOrderedLessThanZero,
					  "QtyOrdered needs to be >= 0, but the given ol has QtyOrdered={}; ol={}; C_Order_ID={}",
					  ol.getQtyOrdered(), ol, ol.getC_Order_ID());
	}

	// task 06727
	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_PriceEntered,
			I_C_OrderLine.COLUMNNAME_Discount
	})
	public void setIsManual(final I_C_OrderLine olPO)
	{
		// urgent-no-taskname available yet: commenting out the code that sets IsManual because where that, attribute changes don't update the price anymore (checking for InterfaceWrapperHelper.isUI
		// doesn't help, because it actually *was* a UI action..the action just didn't change one of these two)
		// olPO.setIsManualPrice(true);
		// olPO.setIsManualDiscount(true);
	}

	/**
	 * Task http://dewiki908/mediawiki/index.php/09285_add_deliver_and_invoice_status_to_order_window
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_QtyOrdered,
			I_C_OrderLine.COLUMNNAME_QtyInvoiced,
			I_C_OrderLine.COLUMNNAME_QtyDelivered
	})
	public void updateQuantities(final I_C_OrderLine orderLine)
	{
		orderBL.updateOrderQtySums(orderLine.getC_Order());
	}

	@CalloutMethod(columnNames = {
			I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage,
			I_C_OrderLine.COLUMNNAME_GroupCompensationType,
			I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType
	})
	public void onGroupCompensationLineChanged(final I_C_OrderLine orderLine)
	{
		if (Check.isEmpty(orderLine.getGroupCompensationType()) && !orderLine.isGroupCompensationLine())
		{
			return; // nothing to do..however note that ideally we wouldn't have been called in the first place
		}
		groupChangesHandler.updateCompensationLineNoSave(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_LineNetAmt,
			I_C_OrderLine.COLUMNNAME_GroupCompensationType,
			I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType,
			I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage
	}, skipIfCopying = true)
	public void handleCompensantionGroupChange(final I_C_OrderLine orderLine)
	{
		groupChangesHandler.onOrderLineChanged(orderLine);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void handleCompensantionGroupDelete(final I_C_OrderLine orderLine)
	{
		groupChangesHandler.onOrderLineDeleted(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_DiscountSchemaBreak_ID, I_C_OrderLine.COLUMNNAME_IsTempPricingConditions })
	public void updateNoPriceConditionsColor(final I_C_OrderLine orderLine)
	{
		orderLinePricingConditions.updateNoPriceConditionsColor(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_C_BPartner_ID, I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void checkExcludedProducts(final I_C_OrderLine orderLine)
	{
		if (orderLine.getM_Product_ID() <= 0 || orderLine.getC_BPartner_ID() <= 0)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final BPartnerId partnerId = BPartnerId.ofRepoId(orderLine.getC_BPartner_ID());

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(order.isSOTrx());

		partnerProductBL.assertNotExcludedFromTransaction(soTrx, productId, partnerId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyEntered, I_C_OrderLine.COLUMNNAME_M_DiscountSchemaBreak_ID })
	public void updatePricesOverrideExistingDiscounts(final I_C_OrderLine orderLine)
	{
		if (isCopy(orderLine))
		{
			return;
		}
		if (orderLine.isProcessed())
		{
			return;
		}

		// make the BL revalidate the discounts..the new QtyEntered might also mean a new discount schema break
		orderLine.setM_DiscountSchemaBreak(null);

		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
										 .orderLine(orderLine)
										 .resultUOM(ResultUOM.PRICE_UOM)
										 .updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(false) // i.e. always update them
										 .updateLineNetAmt(true)
										 .build());

		logger.debug("Setting TaxAmtInfo for {}", orderLine);
		orderLineBL.setTaxAmtInfo(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void ensureOrderLineHasShipper(final I_C_OrderLine orderLine)
	{
		if (orderLine.getM_Shipper_ID() <= 0)
		{
			logger.debug("Making sure {} has a M_Shipper_ID", orderLine);
			orderLineBL.setShipper(orderLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void updateProductDescriptionFromProductBOMIfConfigured(final I_C_OrderLine orderLine)
	{
		orderLineBL.updateProductDescriptionFromProductBOMIfConfigured(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void updateProductDocumentNote(final I_C_OrderLine orderLine)
	{
		orderLineBL.updateProductDocumentNote(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID })
	public void renumberLinesIfCompensationGroupChanged(@NonNull final I_C_OrderLine orderLine)
	{
		if (!OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return;
		}

		groupChangesHandler.renumberOrderLinesForOrderId(OrderId.ofRepoId(orderLine.getC_Order_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_InvoiceLine.COLUMNNAME_C_VAT_Code_ID })
	public void updateTaxFromVatCodeId(final I_C_OrderLine orderLine)
	{
		if (orderLine.isProcessed())
		{
			return;
		}
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(orderLine.getC_VAT_Code_ID());
		if (vatCodeId == null)
		{
			return;
		}
		final Tax tax = taxDAO.getTaxFromVatCodeIfManualOrNull(vatCodeId);
		if (tax != null)
		{
			orderLine.setC_Tax_ID(tax.getTaxId().getRepoId());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID })
	public void updateIsOnConsignment(final I_C_OrderLine orderLine)
	{
		orderLineBL.updateIsOnConsignmentNoSave(orderLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_IsOnConsignment })
	public void updateIsOnConsignmentOrder(final I_C_OrderLine orderLine)
	{
		final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());

		trxManager.accumulateAndProcessAfterCommit(
				"orderIdsToUpdateIsOnConsigment",
				ImmutableSet.of(orderId),
				this::updateIsOnConsignmentFromLines
		);
	}

	private void updateIsOnConsignmentFromLines(final List<OrderId> orderIds)
	{
		for (final OrderId orderId : ImmutableSet.copyOf(orderIds))
		{
			orderBL.updateIsOnConsignmentFromLines(orderId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void copyDimensionFromHeader(final org.compiere.model.I_C_OrderLine orderLine)
	{
		// only update the section code and user elements. It's not specified if the other dimensions should be inherited from the order header to the lines
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		orderLine.setM_SectionCode_ID(order.getM_SectionCode_ID());

		final Dimension orderDimension = dimensionService.getFromRecord(order);
		dimensionService.updateRecordUserElements(orderLine, orderDimension);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_M_Product_ID)
	public void checkProductWarehouseAssignment(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (!productWarehouseAssignmentService.enforceWarehouseAssignmentsForProducts())
		{
			return;
		}

		final I_C_Order orderRecord = orderBL.getById(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()));
		final ProductId productId = ProductId.ofRepoId(orderLineRecord.getM_Product_ID());

		final ProductWarehouseAssignments assignments = productWarehouseAssignmentService
				.getByProductIdOrError(productId);

		if (!assignments.isWarehouseAssigned(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID())))
		{
			throw new AdempiereException(ORDER_DIFFERENT_WAREHOUSE_MSG_KEY)
					.appendParametersToMessage()
					.setParameter("C_Order_ID", orderRecord.getC_Order_ID())
					.setParameter("C_OrderLine_ID", orderLineRecord.getC_OrderLine_ID())
					.setParameter("C_Order.M_Warehouse_ID", orderRecord.getM_Warehouse_ID())
					.setParameter("M_Warehouse_IDs assigned to Product", assignments.getWarehouseIds())
					.markAsUserValidationError();
		}
	}
}
