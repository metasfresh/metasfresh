/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ordercandidate.api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.effective.BPartnerAddressEffective;
import de.metas.bpartner.effective.BPartnerAddressEffectiveBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.externalsystem.ExternalSystemId;
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
import de.metas.order.OrderId;
import de.metas.order.OrderLineGroup;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupRepository;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.promotioncode.PromotionCodeId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
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
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.asi_aware.factory.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final BPartnerAddressEffectiveBL bpartnerAddressEffectiveBL = SpringContextHolder.instance.getBean(BPartnerAddressEffectiveBL.class);

	private final OrderGroupRepository orderGroupsRepository = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final GroupTemplateRepository groupTemplateRepository = SpringContextHolder.instance.getBean(GroupTemplateRepository.class);
	private final OLCandValidatorService olCandValidatorService = SpringContextHolder.instance.getBean(OLCandValidatorService.class);
	private final CustomColumnService customColumnService = SpringContextHolder.instance.getBean(CustomColumnService.class);

	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_DESC_1P = AdMessageKey.of("OLCandProcessor.ProcessingError_Desc");
	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_ORDER_COMPLETION_FAILED_2P = AdMessageKey.of("OLCandProcessor.Order_Completion_Failed");
	private static final AdMessageKey MSG_OL_CAND_PROCESSOR_OLCAND_GROUPING_ERROR = AdMessageKey.of("OLCandProcessor.OLCandGroupingError");
	private static final String SYSCONFIG_USE_QTY_UOM_ON_MANUAL_PRICE = "de.metas.ordercandidate.api.OLCandOrderFactory.UseQtyUOMOnManualPrice";

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
	// The earliest of the per-line DatePromised values; the header C_Order.DatePromised is set to it in completeOrDelete().
	private ZonedDateTime earliestLineDatePromised = null;
	private final List<OLCand> candidates = new ArrayList<>();
	// Compensation-group headers created via schema explosion (tryExplodeCompensationGroupSchema); rolled back in onCompensationGroupFailure.
	private final List<GroupId> compensationGroupIds = new ArrayList<>();
	private final ListMultimap<String, OrderLineId> groupsToOrderLines = ArrayListMultimap.create();
	private final Map<OrderLineId, OrderLineGroup> primaryOrderLineToGroup = new HashMap<>();

	/**
	 * If {@code true} and the {@link OLCand} used as parameter in {@link #newOrder(OLCand)} has an asyncBatchId, then propagate it to the C_Order record.
	 */
	private final boolean propagateAsyncBatchIdToOrderRecord;

	@Builder
	private OLCandOrderFactory(
			@NonNull final OLCandOrderDefaults orderDefaults,
			final boolean propagateAsyncBatchIdToOrderRecord,
			final int olCandProcessorId,
			final UserId userInChargeId,
			final ILoggable loggable,
			final IOLCandListener olCandListeners)
	{
		this.orderDefaults = orderDefaults;
		this.propagateAsyncBatchIdToOrderRecord = propagateAsyncBatchIdToOrderRecord;
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

		final Timestamp dateOrdered = TimeUtil.asTimestamp(candidateOfGroup.getDateOrdered());
		order.setDateOrdered(dateOrdered);
		order.setDateAcct(dateOrdered);

		// task 06269 (see KurzBeschreibung)
		// note that C_Order.DatePromised is propagated to C_OrderLine.DatePromised in MOrder.afterSave() and MOrderLine.setOrder().
		// We seed the header with the first candidate's DatePromised here so the order can be saved (lines need the FK).
		// Each line then gets its own olcand DatePromised at creation (see addOLCand0), and the header is set to the
		// earliest of those line dates in completeOrDelete() via applyEarliestHeaderDatePromised(). The MOrder.afterSave()
		// header->line broadcast does not overwrite the per-line dates (guarded by isUIAction || lineDateNotSet there).
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
		order.setIsAutoInvoice(candidateOfGroup.isAutoInvoice());
		order.setPaymentRule(PaymentRule.toCodeOrNull(candidateOfGroup.getPaymentRule()));
		order.setC_PaymentTerm_ID(PaymentTermId.toRepoId(candidateOfGroup.getPaymentTermId()));
		order.setM_PricingSystem_ID(PricingSystemId.toRepoId(candidateOfGroup.getPricingSystemId()));
		order.setM_Shipper_ID(ShipperId.toRepoId(candidateOfGroup.getShipperId()));

		final BPartnerLocationId dropShipLocationId = candidateOfGroup.getDropShipBPartnerInfo()
				.map(BPartnerInfo::getBpartnerLocationId)
				.orElse(null);
		final BPartnerLocationId locationId = candidateOfGroup.getBPartnerInfo().getBpartnerLocationId();
		if (locationId != null)
		{
			final BPartnerAddressEffective addressEffective = bpartnerAddressEffectiveBL.getDeliveryEffective(dropShipLocationId, locationId);
			order.setIsPreAdviceRequired(addressEffective.isPreAdviceRequired());
		}

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

		if (candidateOfGroup.getAsyncBatchId() != null && propagateAsyncBatchIdToOrderRecord)
		{
			order.setC_Async_Batch_ID(candidateOfGroup.getAsyncBatchId().getRepoId());
		}

		// Save to SO the external header id, so that on completion it can be linked with its payment
		order.setExternalId(candidateOfGroup.getExternalHeaderId());
		order.setExternalSystem_ID(ExternalSystemId.toRepoId(candidateOfGroup.getExternalSystemId()));

		final de.metas.order.model.I_C_Order orderWithDataSource = InterfaceWrapperHelper.create(order, de.metas.order.model.I_C_Order.class);
		orderWithDataSource.setAD_InputDataSource_ID(candidateOfGroup.getAD_InputDataSource_ID());

		setBPSalesRepIdToOrder(order, candidateOfGroup);

		order.setBPartnerName(candidateOfGroup.getBpartnerName());
		order.setEMail(candidateOfGroup.getEmail());
		order.setPhone(candidateOfGroup.getPhone());

		if (candidateOfGroup.getIncotermsId() != null)
		{
			order.setC_Incoterms_ID(candidateOfGroup.getIncotermsId().getRepoId());
			order.setIncotermLocation(candidateOfGroup.getIncotermLocation());
		}

		// First-class field propagation: promotion codes from OLCand to order header
		final PromotionCodeId promotionCodeId = candidateOfGroup.getPromotionCodeId();
		if (promotionCodeId != null)
		{
			order.setC_PromotionCode_ID(promotionCodeId.getRepoId());
		}
		final PromotionCodeId promotionCode2Id = candidateOfGroup.getPromotionCode2Id();
		if (promotionCode2Id != null)
		{
			order.setC_PromotionCode2_ID(promotionCode2Id.getRepoId());
		}

		customColumnService.copyCustomColumns(candidateOfGroup.unbox(), I_C_OLCand.Table_Name, I_C_Order.Table_Name, order);

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
			applyEarliestHeaderDatePromised(order);

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
					final AdIssueId adIssueId = errorManager.createIssue(ex);
					candidate.setError(errorMsg, note.getAD_Note_ID(), adIssueId);

					save(candidate.unbox());

					olCandValidatorService.sendNotificationAfterCommit(TableRecordReference.of(I_C_OLCand.Table_Name, candidate.getId()));
				}
			}
		}
	}

	/**
	 * Sets the header {@code C_Order.DatePromised} to the earliest of the lines' own delivery dates.
	 * <p>
	 * Each line already carries its own {@code DatePromised} from creation (see {@link #addOLCand0(OLCand)}); this method
	 * only sets the header, leaving the per-line dates intact. When no line carried a {@code DatePromised}, the header
	 * keeps its fallback and nothing is changed here.
	 */
	private void applyEarliestHeaderDatePromised(@NonNull final I_C_Order order)
	{
		if (earliestLineDatePromised == null)
		{
			return;
		}

		// compare by instant (the header stores the same moment), so the guard skips a no-op save
		if (!Objects.equals(earliestLineDatePromised.toInstant(), TimeUtil.asInstant(order.getDatePromised())))
		{
			order.setDatePromised(TimeUtil.asTimestamp(earliestLineDatePromised));
			orderDAO.save(order);
		}
	}

	private void onCompensationGroupFailure(final AdempiereException ex)
	{
		deleteAll(allocations);
		deleteAll(orderLines.values());
		// Delete any compensation-group headers created by schema explosion BEFORE delete(order): their order lines are
		// already gone (above), so each header is the last, FK-orphaned row and its deferred C_Order FK would abort the
		// transaction at COMMIT otherwise. deleteGroupById is idempotent, so a header already removed elsewhere is fine.
		for (final GroupId compensationGroupId : compensationGroupIds)
		{
			orderGroupsRepository.deleteGroupById(compensationGroupId);
		}
		delete(order);

		for (final OLCand candidate : candidates)
		{
			candidate.setGroupingError(ex.getLocalizedMessage());
			save(candidate.unbox());

			olCandValidatorService.sendNotificationAfterCommit(TableRecordReference.of(I_C_OLCand.Table_Name, candidate.getId()));
		}
	}

	private void validateAndCreateCompensationGroups()
	{
		orderLines.values()
				.stream()
				//dev-note: make sure the compensation groups are created in the right order
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.map(OrderLineId::ofRepoId)
				.map(primaryOrderLineToGroup::get)
				.filter(Objects::nonNull)
				.map(OrderLineGroup::getGroupKey)
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
		final OrderLineGroup orderLineGroup = primaryOrderLineToGroup.get(OrderLineId.ofRepoId(mainOrderLineInGroup.getC_OrderLine_ID()));

		if (groupCompensationType.equals(GroupCompensationType.Discount)
				&& groupCompensationAmtType.equals(GroupCompensationAmtType.Percent))
		{
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
				.newGroupTemplate(createNewGroupTemplate(productId))
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

	private GroupTemplate createNewGroupTemplate(@NonNull final ProductId productId)
	{
		return GroupTemplate.builder()
				.name(productBL.getProductName(productId))
				.regularLinesToAdd(ImmutableList.of())
				.compensationLines(ImmutableList.of())
				.productCategoryId(productDAO.retrieveProductCategoryByProductId(productId))
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
		validateCandidateOutOfTrx(candidate.unbox());

		try
		{
			addOLCand0(candidate);

			olcandBL.markAsProcessed(candidate);
		}
		catch (final Exception ex)
		{
			trxManager.runInNewTrx(() -> olcandBL.markAsError(userInChargeId, candidate, ex));

			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private void addOLCand0(@NonNull final OLCand candidate)
	{
		// If the candidate's product carries a compensation-group schema (trading-BOM),
		// explode it into the schema's component order lines instead of creating a single un-exploded line
		// (mirrors what the sales-order window / Quick-Input does).
		if (tryExplodeCompensationGroupSchema(candidate))
		{
			return;
		}

		final boolean isNewOrderLine;
		if (currentOrderLine == null)
		{
			currentOrderLine = newOrderLine(candidate);
			isNewOrderLine = true;

			// Carry this candidate's own delivery date onto the new line from creation, so the line owns its
			// per-line DatePromised. When several candidates aggregate into one line, the line keeps the creating
			// candidate's date. The header is set to the earliest such date in completeOrDelete() via
			// applyEarliestHeaderDatePromised().
			final ZonedDateTime lineDatePromised = candidate.getDatePromised();
			if (lineDatePromised != null)
			{
				currentOrderLine.setDatePromised(TimeUtil.asTimestamp(lineDatePromised));
				if (earliestLineDatePromised == null || lineDatePromised.isBefore(earliestLineDatePromised))
				{
					earliestLineDatePromised = lineDatePromised;
				}
			}
		}
		else
		{
			isNewOrderLine = false;
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
			final Quantity currentQty = Quantitys.of(currentOrderLine.getQtyEntered(), UomId.ofRepoId(currentOrderLine.getC_UOM_ID()));
			final Quantity newQtyEntered = Quantitys.add(UOMConversionContext.of(candidate.getM_Product_ID()), currentQty, candidate.getQty());
			currentOrderLine.setQtyEntered(newQtyEntered.toBigDecimal());

			currentOrderLine.setQtyItemCapacity(Quantitys.toBigDecimalOrNull(candidate.getQtyItemCapacityEff()));

			final BigDecimal qtyOrdered = orderLineBL.convertQtyEnteredToStockUOM(currentOrderLine).toBigDecimal();
			currentOrderLine.setQtyOrdered(qtyOrdered);
		}

		// Quantity in price UOM
		{
			final boolean isManualQtyInPriceUOM = candidate.getManualQtyInPriceUOM() != null;

			if (isNewOrderLine)
			{
				currentOrderLine.setIsManualQtyInPriceUOM(isManualQtyInPriceUOM);
			}
			else if (currentOrderLine.isManualQtyInPriceUOM() != isManualQtyInPriceUOM)
			{
				throw new AdempiereException("Aggregating with different IsManualQtyInPriceUOM is not allowed");
			}

			if (isManualQtyInPriceUOM)
			{
				currentOrderLine.setQtyEnteredInPriceUOM(currentOrderLine.getQtyEnteredInPriceUOM().add(candidate.getManualQtyInPriceUOM()));
			}
		}

		//
		// Prices
		{
			currentOrderLine.setInvoicableQtyBasedOn(candidate.getInvoicableQtyBasedOn().getCode());

			currentOrderLine.setIsManualPrice(candidate.isManualPrice());
			if (candidate.isManualPrice())
			{
				currentOrderLine.setPriceEntered(candidate.getPriceActual());
				if (sysConfigBL.getBooleanValue(SYSCONFIG_USE_QTY_UOM_ON_MANUAL_PRICE, false))
				{
					currentOrderLine.setPrice_UOM_ID(candidate.getQty().getUomId().getRepoId());
				}
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

		// First-class field propagation: IsWithoutCharge and Reason from OLCand to order line
		// Done before firing the listeners, so that listeners observe a fully-populated order line.
		{
			currentOrderLine.setIsWithoutCharge(candidate.isWithoutCharge());
			currentOrderLine.setReason(candidate.getReason());

			customColumnService.copyCustomColumns(candidate.unbox(), I_C_OLCand.Table_Name, I_C_OrderLine.Table_Name, currentOrderLine);
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

	/**
	 * If the candidate's product has a compensation-group schema (a trading-BOM) and the candidate is
	 * NOT already part of an explicit OLCand group, explode the ordered qty into the schema's component order
	 * lines (regular + compensation) using the same business API the sales-order window / Quick-Input uses, and link
	 * the candidate to each generated regular line via {@code C_Order_Line_Alloc}.
	 *
	 * @return {@code true} if the candidate was handled by schema explosion (caller must NOT create a plain order
	 * line); {@code false} otherwise (caller proceeds with the plain-line flow).
	 */
	private boolean tryExplodeCompensationGroupSchema(@NonNull final OLCand candidate)
	{
		// Guard (b): leave candidates that already belong to an explicit CompensationGroupKey group to the existing grouping path.
		final OrderLineGroup orderLineGroup = candidate.getOrderLineGroup();
		if (orderLineGroup != null && !Check.isBlank(orderLineGroup.getGroupKey()))
		{
			return false;
		}

		// Guard (a): the product must carry a compensation-group schema.
		final int productRepoId = candidate.getM_Product_ID();
		if (productRepoId <= 0)
		{
			return false;
		}
		final ProductId productId = ProductId.ofRepoId(productRepoId);
		final GroupTemplateId groupTemplateId = productDAO.getGroupTemplateIdByProductId(productId).orElse(null);
		if (groupTemplateId == null)
		{
			return false;
		}

		// Make sure the order exists (without creating a stray order line).
		if (order == null)
		{
			order = newOrder(candidate);
		}
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// The ordered quantity scales the schema's template-line quantities.
		// M5: orderedQty is passed as the pure group qty-multiplier with NO UOM conversion, matching
		// OrderLineQuickInputProcessor. This assumes candidate.getQty() is already expressed in the schema
		// product's own stocking (counting) UOM; a UOM other than that would scale
		// every exploded component quantity wrongly. Guard the assumption instead of silently trusting the
		// (REST/EDI) caller: fail loud if the candidate qty UOM differs from the schema product's stocking UOM.
		final UomId candidateQtyUomId = candidate.getQty().getUomId();
		final UomId schemaProductStockUomId = productBL.getStockUOMId(productId);
		if (!UomId.equals(candidateQtyUomId, schemaProductStockUomId))
		{
			throw new AdempiereException("OLCand qty UOM does not match the compensation-group-schema product's"
					+ " stocking UOM; the exploded component quantities would be mis-scaled")
					.setParameter("productId", productId)
					.setParameter("candidateQtyUomId", candidateQtyUomId)
					.setParameter("schemaProductStockUomId", schemaProductStockUomId)
					.setParameter("C_OLCand_ID", candidate.getId())
					.appendParametersToMessage();
		}
		final BigDecimal orderedQty = candidate.getQty().toBigDecimal();

		// C2: thread the candidate's flatrate/contract conditions into createGroup. This is how the
		// exploded component lines get their C_Flatrate_Conditions_ID: OrderGroupRepository.createRegularLineFromTemplate
		// sets it from the request's newContractConditionsId (and the same id also drives which template regular
		// lines match). This is the Quick-Input mechanism; the OLCand listener path (FlatrateOLCandListener) is
		// deliberately NOT used for generated lines (see below).
		final ConditionsId flatrateConditionsId = ConditionsId.ofRepoIdOrNull(candidate.getFlatrateConditionsId());

		final Group group = orderGroupsRepository.prepareNewGroup()
				.groupTemplate(groupTemplateRepository.getById(groupTemplateId))
				.qty(orderedQty)
				.createGroup(orderId, flatrateConditionsId);

		// Track the created compensation-group header so onCompensationGroupFailure can delete it during rollback.
		// Its C_Order_CompensationGroup->C_Order FK is DEFERRABLE INITIALLY DEFERRED: an orphaned header (left after
		// the order+lines are deleted) would otherwise abort the whole transaction at COMMIT.
		compensationGroupIds.add(group.getGroupId());

		// H3: we deliberately do NOT fire olCandListeners.onOrderLineCreated for the generated component lines.
		// HU packing-instruction (OLCandPIIPListener) is product-specific and must not be copied from the schema-product
		// candidate onto its differing-product component lines; flatrate conditions are instead threaded via
		// createGroup's conditions parameter (above).
		//
		// C1: createGroup persists real C_OrderLine rows for BOTH the regular AND the compensation lines of the
		// group. Track every one of them in `orderLines` (and allocate the candidate to each) so that a later
		// rollback in onCompensationGroupFailure (deleteAll(orderLines) + delete(order)) does not leave an
		// untracked compensation line still FK-referencing C_Order and make delete(order) fail.
		final List<OrderLineId> generatedLineIds = new ArrayList<>();
		group.getRegularLines().forEach(regularLine -> generatedLineIds.add(OrderLineId.ofRepoId(regularLine.getRepoId().getRepoId())));
		group.getCompensationLines().forEach(compensationLine -> generatedLineIds.add(OrderLineId.ofRepoId(compensationLine.getRepoId().getRepoId())));

		for (final OrderLineId generatedLineId : generatedLineIds)
		{
			// Persistence primitives (load/save) go through IOrderDAO, not InterfaceWrapperHelper directly
			// (docs/coding-rules/service-injection.md §4 — a BL/Factory class must not call the persistence primitives).
			final I_C_OrderLine componentOrderLine = orderDAO.getOrderLineById(generatedLineId, I_C_OrderLine.class);

			// H4: the generated group lines otherwise inherit warehouse/org from the order header only. Mirror the
			// plain path (see addOLCand0) and align them with THIS candidate, so a later compensation-group-schema candidate
			// aggregated into the same order (different warehouse/org) does not silently diverge from its own values.
			componentOrderLine.setM_Warehouse_ID(WarehouseId.toRepoId(candidate.getWarehouseId()));
			componentOrderLine.setM_Warehouse_Dest_ID(WarehouseId.toRepoId(candidate.getWarehouseDestId()));
			componentOrderLine.setAD_Org_ID(candidate.getAD_Org_ID());
			orderDAO.save(componentOrderLine);

			// Preserve the OLCand -> order traceability and cover the line for rollback.
			createOla(candidate, componentOrderLine);
			orderLines.put(componentOrderLine.getC_OrderLine_ID(), componentOrderLine);
		}

		candidates.add(candidate);
		return true;
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

	private void validateCandidateOutOfTrx(@NonNull final I_C_OLCand candidate)
	{
		olCandValidatorService.setValidationProcessInProgress(true);

		try
		{
			final I_C_OLCand validatedOlCand = trxManager.callInNewTrx(() -> {
				final I_C_OLCand cand = olCandValidatorService.validate(candidate);

				saveRecord(cand);
				return cand;
			});

			if (validatedOlCand.isError())
			{
				throw new AdempiereException("Fail to validate candidate.")
						.appendParametersToMessage()
						.setParameter("C_OLCand_ID", candidate.getC_OLCand_ID());
			}
		}
		finally
		{
			olCandValidatorService.setValidationProcessInProgress(false);
		}
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

	private void setExternalBPartnerInfo(@NonNull final I_C_OrderLine orderLine, @NonNull final OLCand candidate)
	{
		orderLine.setExternalSeqNo(candidate.getLine());

		final I_C_OLCand olCand = candidate.unbox();

		orderLine.setBPartner_QtyItemCapacity(olCand.getQtyItemCapacity());

		final UomId uomId = UomId.ofRepoIdOrNull(olCand.getC_UOM_ID());

		if (uomId != null)
		{
			orderLine.setC_UOM_BPartner_ID(uomId.getRepoId());
			orderLine.setQtyEnteredInBPartnerUOM(olCandEffectiveValuesBL.getEffectiveQtyEntered(olCand));
		}
	}
}
