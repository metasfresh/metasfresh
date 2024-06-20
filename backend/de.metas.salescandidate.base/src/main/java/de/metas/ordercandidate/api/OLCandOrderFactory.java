package de.metas.ordercandidate.api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.freighcost.FreightCostRule;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderLineGroup;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupRepository;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.MNote;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

class OLCandOrderFactory
{
	private static final Logger logger = LogManager.getLogger(OLCandOrderFactory.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOLCandBL olcandBL = Services.get(IOLCandBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final OrderGroupRepository orderGroupsRepository = SpringContextHolder.instance.getBean(OrderGroupRepository.class);

	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_DESC_1P = AdMessageKey.of("OLCandProcessor.ProcessingError_Desc");
	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_ORDER_COMPLETION_FAILED_2P = AdMessageKey.of("OLCandProcessor.Order_Completion_Failed");
	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_OLCAND_GROUPING_ERROR = AdMessageKey.of("OLCandProcessor.OLCandGroupingError");

	//
	// Parameters
	private final OLCandOrderDefaults orderDefaults;
	private final Properties ctx;
	private final UserId userInChargeId;
	private final ILoggable loggable;
	private final int olCandProcessorId;
	private final IOLCandListener olCandListeners;

	//
	private I_C_Order order;
	private final Collection<I_C_Order_Line_Alloc> allocations = new HashSet<>();
	private I_C_OrderLine currentOrderLine = null;
	private final Map<Integer, I_C_OrderLine> orderLines = new LinkedHashMap<>();
	private final List<OLCand> candidates = new ArrayList<>();
	private final ListMultimap<String, OrderLineId> groupsToOrderLines = ArrayListMultimap.create();
	private final Map<OrderLineId, OrderLineGroup> primaryOrderLineToGroup = new HashMap<>();

	@Builder
	private OLCandOrderFactory(
			@NonNull final OLCandOrderDefaults orderDefaults,
			final int olCandProcessorId,
			final UserId userInChargeId,
			final ILoggable loggable,
			final IOLCandListener olCandListeners)
	{
		this.orderDefaults = orderDefaults;
		ctx = Env.getCtx();
		this.userInChargeId = userInChargeId != null ? userInChargeId : UserId.SYSTEM;
		this.loggable = loggable != null ? loggable : Loggables.nop();

		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");
		this.olCandProcessorId = olCandProcessorId;

		this.olCandListeners = olCandListeners;

	}

	private I_C_Order newOrder(@NonNull final OLCand candidateOfGroup)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus(DocStatus.Drafted.getCode());
		order.setDocAction(X_C_Order.DOCACTION_Complete);

		//
		// use values from orderDefaults when the order candidate doesn't have such values
		order.setC_DocTypeTarget_ID(DocTypeId.toRepoId(orderDefaults.getDocTypeTargetId()));

		order.setM_Warehouse_ID(WarehouseId.toRepoId(CoalesceUtil.coalesce(candidateOfGroup.getWarehouseId(), orderDefaults.getWarehouseId())));

		// use the values from 'olCand'
		order.setAD_Org_ID(candidateOfGroup.getAD_Org_ID());

		OrderDocumentLocationAdapterFactory
				.locationAdapter(order)
				.setFrom(candidateOfGroup.getBPartnerInfo());

		// if the olc has no value set, we are not falling back here!
		final BPartnerInfo billBPartner = candidateOfGroup.getBillBPartnerInfo();
		if (billBPartner != null)
		{
			OrderDocumentLocationAdapterFactory
					.billLocationAdapter(order)
					.setFrom(billBPartner);
		}

		final Timestamp dateDoc = TimeUtil.asTimestamp(candidateOfGroup.getDateDoc());
		order.setDateOrdered(dateDoc);
		order.setDateAcct(dateDoc);

		// task 06269 (see KurzBeschreibung)
		// note that C_Order.DatePromised is propagated to C_OrderLine.DatePromised in MOrder.afterSave() and MOrderLine.setOrder()
		// also note that for now we set datepromised only in the header, so different DatePromised values result in differnt orders, and all ol have the same datepromised
		order.setDatePromised(TimeUtil.asTimestamp(candidateOfGroup.getDatePromised()));

		// if the olc has no value set, we are not falling back here!
		// 05617
		final BPartnerInfo dropShipBPartner = candidateOfGroup.getDropShipBPartnerInfo().orElse(null);
		if (dropShipBPartner != null)
		{
			OrderDocumentLocationAdapterFactory
					.deliveryLocationAdapter(order)
					.setFrom(dropShipBPartner);
			order.setIsDropShip(true);
		}
		else
		{
			order.setIsDropShip(false);
		}

		final BPartnerInfo handOverBPartner = candidateOfGroup.getHandOverBPartnerInfo().orElse(null);
		if (handOverBPartner != null)
		{
			OrderDocumentLocationAdapterFactory
					.handOverLocationAdapter(order)
					.setFrom(handOverBPartner);
			order.setIsUseHandOver_Location(true);
		}
		else
		{
			order.setIsUseHandOver_Location(false);
		}

		if (candidateOfGroup.getC_Currency_ID() > 0)
		{
			order.setC_Currency_ID(candidateOfGroup.getC_Currency_ID());
		}

		order.setPOReference(candidateOfGroup.getPOReference());

		order.setDeliveryRule(DeliveryRule.toCodeOrNull(candidateOfGroup.getDeliveryRule()));
		order.setDeliveryViaRule(DeliveryViaRule.toCodeOrNull(candidateOfGroup.getDeliveryViaRule()));
		order.setFreightCostRule(FreightCostRule.toCodeOrNull(candidateOfGroup.getFreightCostRule()));
		order.setInvoiceRule(InvoiceRule.toCodeOrNull(candidateOfGroup.getInvoiceRule()));
		order.setPaymentRule(PaymentRule.toCodeOrNull(candidateOfGroup.getPaymentRule()));
		order.setC_PaymentTerm_ID(PaymentTermId.toRepoId(candidateOfGroup.getPaymentTermId()));
		order.setM_PricingSystem_ID(PricingSystemId.toRepoId(candidateOfGroup.getPricingSystemId()));
		order.setM_Shipper_ID(ShipperId.toRepoId(candidateOfGroup.getShipperId()));

		final DocTypeId orderDocTypeId = candidateOfGroup.getOrderDocTypeId();
		if (orderDocTypeId != null)
		{
			order.setC_DocTypeTarget_ID(candidateOfGroup.getOrderDocTypeId().getRepoId());
		}

		final BPartnerId salesRepId = candidateOfGroup.getSalesRepId();
		final BPartnerId effectiveBillPartnerId = orderBL.getEffectiveBillPartnerId(order);
		if (salesRepId != null && !salesRepId.equals(effectiveBillPartnerId))
		{
			order.setC_BPartner_SalesRep_ID(salesRepId.getRepoId());

			final I_C_BPartner salesPartner = bpartnerDAO.getById(salesRepId);

			order.setSalesPartnerCode(salesPartner.getSalesPartnerCode());
		}

		if (candidateOfGroup.getAsyncBatchId() != null)
		{
			order.setC_Async_Batch_ID(candidateOfGroup.getAsyncBatchId().getRepoId());
		}

		// Save to SO the external header id, so that on completion it can be linked with its payment
		order.setExternalId(candidateOfGroup.getExternalHeaderId());

		// task 08926: set the data source; this shall trigger IsEdiEnabled to be set to true, if the data source is "EDI"
		final de.metas.order.model.I_C_Order orderWithDataSource = InterfaceWrapperHelper.create(order, de.metas.order.model.I_C_Order.class);
		orderWithDataSource.setAD_InputDataSource_ID(candidateOfGroup.getAD_InputDataSource_ID());

		setBPSalesRepIdToOrder(order, candidateOfGroup);

		order.setBPartnerName(candidateOfGroup.getBpartnerName());
		order.setEMail(candidateOfGroup.getEmail());
		order.setPhone(candidateOfGroup.getPhone());

		save(order);
		return order;
	}

	public void completeOrDelete()
	{
		final I_C_Order order = this.order;
		if (order == null)
		{
			return;
		}
		else if (orderLines.isEmpty())
		{
			delete(order);
		}
		else
		{
			try
			{
				validateAndCreateCompensationGroups();
			}
			catch (final AdempiereException ex)
			{
				logger.warn("Caught exception while validating compensation groups for OLCands", ex);
				onCompensationGroupFailure(ex);
				return;
			}
			try
			{
				documentBL.processEx(order, X_C_Order.DOCACTION_Complete, DocStatus.Completed.getCode());
				save(order);

				loggable.addLog("@Created@ @C_Order_ID@ " + order.getDocumentNo());
			}
			catch (final Exception ex)
			{
				final String errorMsg = msgBL.getMsg(ctx, MSG_OL_CAND_PROCESSOR_ORDER_COMPLETION_FAILED_2P, new Object[] { order.getDocumentNo(), ex.getLocalizedMessage() });
				loggable.addLog(errorMsg);
				logger.warn("Caught exception while completing {}", order, ex);

				final I_AD_Note note = createOrderCompleteErrorNote(errorMsg);
				for (final OLCand candidate : candidates)
				{
					candidate.setError(errorMsg, note.getAD_Note_ID());
					save(candidate.unbox());
				}
			}
		}
	}

	private void onCompensationGroupFailure(final AdempiereException ex)
	{
		deleteAll(allocations);
		deleteAll(orderLines.values());
		delete(order);

		for (final OLCand candidate : candidates)
		{
			candidate.setGroupingError(ex.getLocalizedMessage());
			save(candidate.unbox());
		}
	}

	private void validateAndCreateCompensationGroups()
	{
		groupsToOrderLines.keySet()
				.stream()
				.map(groupsToOrderLines::get)
				.forEach(this::createCompensationGroup);
	}

	private void createCompensationGroup(final List<OrderLineId> orderLineIds)
	{
		final Collection<I_C_OrderLine> mainOrderLineInGroupOpt = orderLineIds.stream()
				.filter(primaryOrderLineToGroup::containsKey)
				.map(OrderLineId::getRepoId)
				.map(orderLines::get)
				.collect(Collectors.toList());

		if (mainOrderLineInGroupOpt.size() != 1)
		{
			throw new AdempiereException("@" + MSG_OL_CAND_PROCESSOR_OLCAND_GROUPING_ERROR + "@");
		}
		final I_C_OrderLine mainOrderLineInGroup = mainOrderLineInGroupOpt.stream().findFirst().get();

		final ProductId productId = ProductId.ofRepoId(mainOrderLineInGroup.getM_Product_ID());
		final I_M_Product productForMainLine = productDAO.getById(productId);

		final GroupCompensationType groupCompensationType = getGroupCompensationType(productForMainLine);
		final GroupCompensationAmtType groupCompensationAmtType = getGroupCompensationAmtType(productForMainLine);

		if (groupCompensationType.equals(GroupCompensationType.Discount)
				&& groupCompensationAmtType.equals(GroupCompensationAmtType.Percent))
		{
			final OrderLineGroup orderLineGroup = primaryOrderLineToGroup.get(OrderLineId.ofRepoId(mainOrderLineInGroup.getC_OrderLine_ID()));

			Optional.ofNullable(orderLineGroup.getDiscount())
					.map(Percent::toBigDecimal)
					.ifPresent(mainOrderLineInGroup::setGroupCompensationPercentage);
		}

		mainOrderLineInGroup.setIsGroupCompensationLine(true);
		mainOrderLineInGroup.setGroupCompensationType(groupCompensationType.getAdRefListValue());
		mainOrderLineInGroup.setGroupCompensationAmtType(groupCompensationAmtType.getAdRefListValue());

		orderDAO.save(mainOrderLineInGroup);

		orderGroupsRepository.retrieveOrCreateGroup(GroupRepository.RetrieveOrCreateGroupRequest.builder()
				.orderLineIds(orderLineIds)
				.newGroupTemplate(createNewGroupTemplate(productId, productDAO.retrieveProductCategoryByProductId(productId)))
				.build());
	}

	@NonNull
	private GroupCompensationAmtType getGroupCompensationAmtType(final I_M_Product productForMainLine)
	{
		return GroupCompensationAmtType.ofAD_Ref_List_Value(CoalesceUtil.coalesce(productForMainLine.getGroupCompensationAmtType(), X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent));
	}

	@NonNull
	private GroupCompensationType getGroupCompensationType(final I_M_Product productForMainLine)
	{
		return GroupCompensationType.ofAD_Ref_List_Value(CoalesceUtil.coalesce(productForMainLine.getGroupCompensationType(), X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount));
	}

	private GroupTemplate createNewGroupTemplate(@NonNull final ProductId productId, @Nullable final ProductCategoryId productCategoryId)
	{
		return GroupTemplate.builder()
				.name(productBL.getProductName(productId))
				.regularLinesToAdd(ImmutableList.of())
				.compensationLines(ImmutableList.of())
				.productCategoryId(productCategoryId)
				.build();
	}

	public void closeCurrentOrderLine()
	{
		if (currentOrderLine == null)
		{
			return;
		}

		save(currentOrderLine);
		currentOrderLine = null;
	}

	public void addOLCand(@NonNull final OLCand candidate)
	{
		try
		{
			addOLCand0(candidate);
			olcandBL.markAsProcessed(candidate);
		}
		catch (final Exception ex)
		{
			olcandBL.markAsError(userInChargeId, candidate, ex);
		}
	}

	private void addOLCand0(@NonNull final OLCand candidate)
	{
		if (currentOrderLine == null)
		{
			currentOrderLine = newOrderLine(candidate);
		}

		setExternalBPartnerInfo(currentOrderLine, candidate);

		currentOrderLine.setM_Warehouse_ID(WarehouseId.toRepoId(candidate.getWarehouseId()));
		currentOrderLine.setM_Warehouse_Dest_ID(WarehouseId.toRepoId(candidate.getWarehouseDestId()));
		currentOrderLine.setProductDescription(candidate.getProductDescription()); // 08626: Propagate ProductDescription to C_OrderLine
		currentOrderLine.setLine(candidate.getLine());
		currentOrderLine.setExternalId(candidate.getExternalLineId());

		//
		// Quantity
		{
			final Quantity currentQty = Quantitys.create(currentOrderLine.getQtyEntered(), UomId.ofRepoId(currentOrderLine.getC_UOM_ID()));
			final Quantity newQtyEntered = Quantitys.add(UOMConversionContext.of(candidate.getM_Product_ID()), currentQty, candidate.getQty());
			currentOrderLine.setQtyEntered(newQtyEntered.toBigDecimal());

			currentOrderLine.setQtyItemCapacity(Quantitys.toBigDecimalOrNull(candidate.getQtyItemCapacityEff()));

			final BigDecimal qtyOrdered = orderLineBL.convertQtyEnteredToStockUOM(currentOrderLine).toBigDecimal();
			currentOrderLine.setQtyOrdered(qtyOrdered);
		}

		//
		// Prices
		{
			currentOrderLine.setInvoicableQtyBasedOn(candidate.getInvoicableQtyBasedOn().getCode());

			currentOrderLine.setIsManualPrice(candidate.isManualPrice());
			if (candidate.isManualPrice())
			{
				currentOrderLine.setPriceEntered(candidate.getPriceActual());
				currentOrderLine.setPrice_UOM_ID(candidate.getQty().getUomId().getRepoId());
			}
			else
			{
				// leave it to the pricing engine
			}
			currentOrderLine.setIsManualDiscount(candidate.isManualDiscount());
			if (candidate.isManualDiscount())
			{
				currentOrderLine.setDiscount(candidate.getDiscount());
			}
			if (candidate.isManualPrice() || candidate.isManualDiscount())
			{
				// FIXME: use price list's precision
				final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(candidate.getC_Currency_ID());
				final CurrencyPrecision stdPrecision = currencyId != null
						? currencyDAO.getStdPrecision(currencyId)
						: ICurrencyDAO.DEFAULT_PRECISION;
				orderLineBL.updatePriceActual(currentOrderLine, stdPrecision);
			}
		}

		//
		// Attach the olCand's IProductPriceAttributeAware to the new order line, so that the rest of the system can know if it is supposed to guess an ASI and PIIP or not (task 08839)
		{
			final IAttributeSetInstanceAware orderLineASIAware = attributeSetInstanceAwareFactoryService.createOrNull(currentOrderLine);
			Check.assumeNotNull(orderLineASIAware, "We can allways obtain a not-null ASI aware for C_OrderLine {} ", currentOrderLine);

			attributePricingBL.setDynAttrProductPriceAttributeAware(orderLineASIAware, candidate);
		}

		//
		// Fire listeners
		olCandListeners.onOrderLineCreated(candidate, currentOrderLine);

		//
		// Save the current order line
		InterfaceWrapperHelper.save(currentOrderLine);

		// Establishing a "real" link with FK-constraints between order candidate and order line (03472)
		createOla(candidate, currentOrderLine);
		final OrderLineGroup orderLineGroup = candidate.getOrderLineGroup();
		if (orderLineGroup != null && !Check.isBlank(orderLineGroup.getGroupKey()))
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoId(currentOrderLine.getC_OrderLine_ID());
			if (currentOrderLine.isGroupCompensationLine() || orderLineGroup.isGroupMainItem())
			{
				primaryOrderLineToGroup.put(orderLineId, orderLineGroup);
			}
			groupsToOrderLines.put(orderLineGroup.getGroupKey(), orderLineId);
		}

		//
		orderLines.put(currentOrderLine.getC_OrderLine_ID(), currentOrderLine);
		candidates.add(candidate);
	}

	private I_C_OrderLine newOrderLine(@NonNull final OLCand candToProcess)
	{
		if (order == null)
		{
			order = newOrder(candToProcess);
		}

		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(order);
		if (candToProcess.getC_Charge_ID() > 0)
		{
			orderLine.setC_Charge_ID(candToProcess.getC_Charge_ID());
		}
		else
		{
			final int productId = candToProcess.getM_Product_ID();
			if (productId <= 0)
			{
				throw new FillMandatoryException(I_C_OLCand.COLUMNNAME_M_Product_ID);
			}
			orderLine.setM_Product_ID(productId);
			orderLine.setC_UOM_ID(candToProcess.getQty().getUomId().getRepoId());
			orderLine.setM_AttributeSetInstance_ID(AttributeConstants.M_AttributeSetInstance_ID_None);
		}

		// make sure that both records have their independent ASI to avoid unwanted side effects if the order line's ASI is altered.
		attributeSetInstanceBL.cloneASI(orderLine, candToProcess);

		orderLine.setPresetDateInvoiced(TimeUtil.asTimestamp(candToProcess.getPresetDateInvoiced()));
		orderLine.setPresetDateShipped(TimeUtil.asTimestamp(candToProcess.getPresetDateShipped()));

		return orderLine;
	}

	// 03472
	// Between C_OL_Cand and C_OrderLine I think we have an explicit AD_Relation. It should be replaced with a table
	// similar to C_Invoice_Line_Alloc, so that it is more transparent and we can define decent FK constraints. (and in
	// future we can have m:n if we want to)
	private void createOla(final OLCand candidate, final I_C_OrderLine orderLine)
	{
		final int orderLineId = orderLine.getC_OrderLine_ID();

		final I_C_Order_Line_Alloc newOla = InterfaceWrapperHelper.newInstance(I_C_Order_Line_Alloc.class);
		newOla.setAD_Org_ID(candidate.getAD_Org_ID());

		newOla.setC_OLCand_ID(candidate.getId());
		newOla.setC_OrderLine_ID(orderLineId);
		newOla.setQtyOrdered(orderLine.getQtyOrdered());
		newOla.setC_OLCandProcessor_ID(olCandProcessorId);
		allocations.add(newOla);

		InterfaceWrapperHelper.save(newOla);
	}

	private I_AD_Note createOrderCompleteErrorNote(final String errorMsg)
	{
		final org.compiere.model.I_AD_User user = userDAO.getById(userInChargeId);

		final String candidateIdsAsString = candidates.stream()
				.map(OLCand::getId)
				.map(String::valueOf)
				.collect(Collectors.joining(", "));
		final Language adLanguage = userBL.getUserLanguage(user);

		final MNote note = new MNote(ctx, IOLCandBL.MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P, userInChargeId.getRepoId(), ITrx.TRXNAME_None);
		note.setClientOrg(user.getAD_Client_ID(), user.getAD_Org_ID());
		note.setReference(errorMsg);
		note.setTextMsg(msgBL.getMsg(adLanguage.getAD_Language(), MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_DESC_1P, new Object[] { candidateIdsAsString }));
		save(note);

		return note;
	}

	@Nullable
	@VisibleForTesting
	I_C_Order getOrder()
	{
		return order;
	}

	private void setBPSalesRepIdToOrder(@NonNull final I_C_Order order, @NonNull final OLCand olCand)
	{
		switch (olCand.getAssignSalesRepRule())
		{
			case Candidate:
				order.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(olCand.getSalesRepId()));
				break;
			case BPartner:
				order.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(olCand.getSalesRepInternalId()));
				break;
			case CandidateFirst:
				final int salesRepInt = Optional.ofNullable(olCand.getSalesRepId())
						.map(BPartnerId::getRepoId)
						.orElseGet(() -> BPartnerId.toRepoId(olCand.getSalesRepInternalId()));

				order.setC_BPartner_SalesRep_ID(salesRepInt);
				break;
			default:
				throw new AdempiereException("Unsupported SalesRepFrom type")
						.appendParametersToMessage()
						.setParameter("salesRepFrom", olCand.getAssignSalesRepRule());
		}
	}

	private static void setExternalBPartnerInfo(@NonNull final I_C_OrderLine orderLine, @NonNull final OLCand candidate)
	{
		orderLine.setExternalSeqNo(candidate.getLine());

		final I_C_OLCand olCand = candidate.unbox();

		orderLine.setBPartner_QtyItemCapacity(olCand.getQtyItemCapacity());

		final UomId uomId = UomId.ofRepoIdOrNull(olCand.getC_UOM_ID());

		if (uomId != null)
		{
			orderLine.setC_UOM_BPartner_ID(uomId.getRepoId());
			orderLine.setQtyEnteredInBPartnerUOM(olCand.getQtyEntered());
		}
	}
}
