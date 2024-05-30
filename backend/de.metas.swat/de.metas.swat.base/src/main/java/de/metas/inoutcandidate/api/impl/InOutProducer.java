package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.DocumentLocation;
import de.metas.forex.ForexContractRef;
import de.metas.inout.IInOutBL;
import de.metas.inout.event.InOutUserNotificationsProducer;
import de.metas.inout.impl.InOutDAO;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

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

/**
 * Class responsible for converting {@link I_M_ReceiptSchedule}s to {@link I_M_InOut} receipts.
 *
 * @author tsa
 */
public class InOutProducer implements IInOutProducer
{
	//
	// Services
	// (protected to be accessible for extending classes too)
	protected final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	protected final IInOutBL inoutBL = Services.get(IInOutBL.class);
	protected final IAggregationKeyBuilder<I_M_ReceiptSchedule> headerAggregationKeyBuilder = receiptScheduleBL.getHeaderAggregationKeyBuilder();

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository = SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);

	private static final String DYNATTR_HeaderAggregationKey = InOutProducer.class.getName() + "#HeaderAggregationKey";

	private ITrxItemProcessorContext processorCtx;

	private final InOutGenerateResult result;
	private final boolean complete;

	private final ReceiptMovementDateRule movementDateRule;

	@NonNull
	private final Map<ReceiptScheduleId, ReceiptScheduleExternalInfo> externalInfoByReceiptScheduleId;
	@Nullable private final ForexContractRef forexContractRef;
	@Nullable private final DeliveryPlanningId deliveryPlanningId;

	@Nullable // can be null between two InOuts
	private I_M_InOut _currentReceipt = null;
	private int _currentReceiptLinesCount = 0;
	private I_M_ReceiptSchedule currentReceiptSchedule = null;
	private I_M_ReceiptSchedule previousReceiptSchedule = null;
	private final Set<Integer> _currentOrderIds = new HashSet<>();

	public InOutProducer(final InOutGenerateResult result, final boolean complete)
	{
		this(result, complete, ReceiptMovementDateRule.CURRENT_DATE, null, null, null);
	}

	/**
	 * @param movementDateRule if {@code ReceiptMovementDateRule#CURRENT_DATE} (the default), then a new InOut is created with the current date from {@link Env#getDate(Properties)}.
	 *                         else if {@code ReceiptMovementDateRule#EXTERNAL_DATE_IF_AVAIL} then the MovementDate will be taken from {@code externalInfoByReceiptScheduleId} if available
	 *                         else if {@code ReceiptMovementDateRule#ORDER_DATE_PROMISED} then the date will be the DatePromised value of the receipt schedule's C_Order.
	 */
	protected InOutProducer(
			@NonNull final InOutGenerateResult result,
			final boolean complete,
			@NonNull final ReceiptMovementDateRule movementDateRule,
			@Nullable final Map<ReceiptScheduleId, ReceiptScheduleExternalInfo> externalInfoByReceiptScheduleId,
			@Nullable final ForexContractRef forexContractRef,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		this.result = result;
		this.complete = complete;
		this.movementDateRule = movementDateRule;
		this.externalInfoByReceiptScheduleId = CoalesceUtil.coalesceNotNull(externalInfoByReceiptScheduleId, ImmutableMap::of);
		this.forexContractRef = forexContractRef;
		this.deliveryPlanningId = deliveryPlanningId;
	}

	@Override
	public final void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public final InOutGenerateResult getResult()
	{
		return result;
	}

	protected final Properties getCtx()
	{
		return processorCtx.getCtx();
	}

	protected final String getTrxName()
	{
		return processorCtx.getTrxName();
	}

	@Override
	public final void process(@NonNull final I_M_ReceiptSchedule rs) throws Exception
	{
		Check.assumeNull(currentReceiptSchedule, "currentReceiptSchedule shall be null");
		currentReceiptSchedule = rs;

		final String rsTrxNameOld = InterfaceWrapperHelper.getTrxName(rs);
		try
		{
			//
			// Make sure receipt schedule is in our current transaction.
			// This is important if we want to retrieve objects from it and then we want to change & save them.
			// NOTE: usually the item to process is out of transaction
			InterfaceWrapperHelper.setTrxName(rs, ITrx.TRXNAME_ThreadInherited);

			final I_M_InOut currentReceipt = getCurrentReceipt();

			final List<? extends I_M_InOutLine> receiptLines = createCurrentReceiptLines();
			for (final I_M_InOutLine receiptLine : receiptLines)
			{
				unsetHeaderProjectIdIfDiverging(currentReceipt, receiptLine, rs);

				receiptScheduleBL.createRsaIfNotExists(rs, receiptLine);
			}

			addToCurrentReceiptLines(receiptLines);

			//
			// Collect C_Order_ID
			final int orderId = rs.getC_Order_ID();
			if (orderId > 0)
			{
				_currentOrderIds.add(orderId);
			}
		}
		finally
		{
			// Restore receipt schedule's old transaction
			InterfaceWrapperHelper.setTrxName(rs, rsTrxNameOld);

			// Clear current receipt schedule
			currentReceiptSchedule = null;
		}
		previousReceiptSchedule = rs;
	}

	private void unsetHeaderProjectIdIfDiverging(
			final @NonNull I_M_InOut receipt,
			final @NonNull I_M_InOutLine receiptLine,
			final @NonNull I_M_ReceiptSchedule rs)
	{
		if (receiptLine.getC_Project_ID() > 0
				&& receipt.getC_Project_ID() > 0
				&& receiptLine.getC_Project_ID() != receipt.getC_Project_ID())
		{
			// our lines have diverging project-IDs => need to unset the header's ID
			Loggables.get().addLog("M_InOut_ID={} has C_Project_ID={}, but M_InOutLine_ID={} (M_ReceiptSchedule_ID={}) has C_Project_ID={}; -> Setting M_InOut.C_Project_ID:=0",
								   receipt.getM_InOut_ID(), receipt.getC_Project_ID(),
								   receiptLine.getM_InOutLine_ID(), rs.getM_ReceiptSchedule_ID(), receiptLine.getC_Project_ID());
			receipt.setC_Project_ID(0);
			InterfaceWrapperHelper.save(receipt);
		}
	}

	@Override
	public final boolean isSameChunk(final I_M_ReceiptSchedule rs)
	{
		if (previousReceiptSchedule == null)
		{
			// First receipt schedule to process => ofc it's a new chunk
			return false;
		}

		return !isNewReceiptRequired(previousReceiptSchedule, rs);
	}

	/**
	 * @return true if given receipt schedules shall not be part of the same receipt
	 */
	// package level because of JUnit tests
	/* package */
	final boolean isNewReceiptRequired(final I_M_ReceiptSchedule previousReceiptSchedule, final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(previousReceiptSchedule, "previousReceiptSchedule not null");
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");

		//
		// If previous and current receipt schedule does not have the same header aggregation key,
		// then for sure they shall be on different receipts
		if (!headerAggregationKeyBuilder.isSame(previousReceiptSchedule, receiptSchedule))
		{
			return true;
		}

		//
		// Make sure we are talking about same C_Order because this is defenetelly needed in case of a DropShip (08402)
		// NOTE: the standard HeaderAggregationKeyBuilder is checking for same C_Order_ID but we are doing it here to make it obvious
		// NOTE2: in future maybe we can enforce this rule only if previous or current receipt schedule is about a DropShip order.
		if (previousReceiptSchedule.getC_Order_ID() != receiptSchedule.getC_Order_ID())
		{
			return true;
		}

		//
		// If we reach this point we can safely consider the receipt schedules to be in the same receipt
		return false;
	}

	private void addToCurrentReceiptLines(final List<? extends I_M_InOutLine> lines)
	{
		Check.assumeNotNull(_currentReceipt, "current receipt not null");

		if (lines == null || lines.isEmpty())
		{
			return;
		}

		_currentReceiptLinesCount += lines.size();
	}

	private int getCurrentReceiptLinesCount()
	{
		return _currentReceiptLinesCount;
	}

	@Override
	public final void newChunk(final I_M_ReceiptSchedule rs)
	{
		final I_M_InOut receipt = createReceiptHeader(rs);
		setCurrentReceipt(receipt);
	}

	@Override
	public final void completeChunk()
	{
		final List<? extends I_M_InOutLine> bottomReceiptLines = createBottomReceiptLines();
		addToCurrentReceiptLines(bottomReceiptLines);

		//
		// Process current receipt (if it has lines).
		final I_M_InOut receipt = getCurrentReceipt();
		final int currentReceiptLinesCount = getCurrentReceiptLinesCount();
		if (currentReceiptLinesCount > 0)
		{
			processReceipt(receipt);
			result.addInOut(receipt);

			// Notify the user that a new receipt was created (task 09334)
			InOutUserNotificationsProducer.newInstance()
					.notifyInOutProcessed(receipt);
		}
		else
		{
			// if the receipt has no lines, make sure we get rid of it
			InterfaceWrapperHelper.delete(receipt);
		}

		//
		// Reset status
		resetCurrentReceipt();
	}

	/**
	 * Create bottom receipt lines, right before completing the receipt.
	 * <p>
	 * NOTE: at this level this method does nothing but you are free to implement your functionality.
	 *
	 * @return created lines
	 */
	protected List<? extends I_M_InOutLine> createBottomReceiptLines()
	{
		return Collections.emptyList();
		// nothing
	}

	/**
	 * Sets current receipt on which next lines will be added
	 */
	private void setCurrentReceipt(final I_M_InOut currentReceipt)
	{
		Check.assumeNotNull(currentReceipt, "currentReceipt not null");

		resetCurrentReceipt();
		this._currentReceipt = currentReceipt;
	}

	private void processReceipt(final I_M_InOut currentReceipt)
	{
		// In case we have only one C_Order_ID, set it to Receipt header
		if (_currentOrderIds.size() == 1)
		{
			final int orderId = _currentOrderIds.iterator().next();
			currentReceipt.setC_Order_ID(orderId);
		}

		//
		// Save receipt
		InterfaceWrapperHelper.save(currentReceipt);

		//
		// Shall we also complete it?
		if (!complete)
		{
			return;
		}

		// Process current receipt
		documentBL.processEx(currentReceipt, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void resetCurrentReceipt()
	{
		_currentReceipt = null;
		_currentReceiptLinesCount = 0;
		_currentOrderIds.clear();
	}

	@Override
	public final void cancelChunk()
	{
		// NOTE: no need to delete current receipt if not null because when this method is called the API is preparing to rollback
		resetCurrentReceipt();
	}

	/**
	 * Gets current receipt.
	 * <p>
	 * If there is no current receipt, an exception will be thrown.
	 *
	 * @return current receipt; never return null.
	 */
	protected final I_M_InOut getCurrentReceipt()
	{
		Check.assumeNotNull(_currentReceipt, "currentReceipt not null");
		return _currentReceipt;
	}

	/**
	 * Same as {@link #getCurrentReceipt()} but it will wrapped to given model interface.
	 *
	 * @return current receipt; never returns null
	 * @see #getCurrentReceipt()
	 */
	protected final <InOutType extends I_M_InOut> InOutType getCurrentReceipt(final Class<InOutType> inoutType)
	{
		return InterfaceWrapperHelper.create(getCurrentReceipt(), inoutType);
	}

	/**
	 * Gets current HeaderAggregationKey used to aggregate current chunk.
	 *
	 * @return header aggregation key
	 */
	protected final String getCurrentHeaderAggregationKey()
	{
		final I_M_InOut currentReceipt = getCurrentReceipt();
		final String headerAggregationKey = InterfaceWrapperHelper.getDynAttribute(currentReceipt, DYNATTR_HeaderAggregationKey);
		Check.assumeNotEmpty(headerAggregationKey, "HeaderAggregationKey should be set for {}", currentReceipt);
		return headerAggregationKey;
	}

	/**
	 * Gets current receipt schedule (i.e. the receipt schedule which is currently processing, see {@link #process(I_M_ReceiptSchedule)})
	 *
	 * @return current receipt schedule; never return null
	 */
	protected final I_M_ReceiptSchedule getCurrentReceiptSchedule()
	{
		Check.assumeNotNull(currentReceiptSchedule, "currentReceiptSchedule not null");
		return currentReceiptSchedule;
	}

	private I_M_InOut createReceiptHeader(@NonNull final I_M_ReceiptSchedule rs)
	{
		final Properties ctx = processorCtx.getCtx();
		final String trxName = processorCtx.getTrx().getTrxName();

		final I_M_InOut receiptHeader = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, trxName);
		receiptHeader.setAD_Org_ID(rs.getAD_Org_ID());
		receiptHeader.setM_SectionCode_ID(rs.getM_SectionCode_ID());
		receiptHeader.setC_Project_ID(rs.getC_Project_ID()); // going to set this to null later, in case there are lines with different projects

		// Document Type
		{
			receiptHeader.setMovementType(X_M_InOut.MOVEMENTTYPE_VendorReceipts);
			receiptHeader.setIsSOTrx(false);
			receiptHeader.setC_DocType_ID(getReceiptDoctypeID(rs));
		}

		//
		// BPartner, Location & Contact
		{
			final BPartnerId bpartnerId = receiptScheduleBL.getBPartnerEffectiveId(rs);
			final int bpartnerLocationId = receiptScheduleBL.getC_BPartner_Location_Effective_ID(rs);
			final BPartnerContactId bpartnerContactId = receiptScheduleBL.getBPartnerContactID(rs);

			InOutDocumentLocationAdapterFactory
					.locationAdapter(receiptHeader)
					.setFrom(DocumentLocation.builder()
							.bpartnerId(bpartnerId)
							.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationId))
							.contactId(bpartnerContactId)
							.build());
		}

		//
		// Document Dates
		{
			receiptHeader.setDateOrdered(rs.getDateOrdered());

			final Timestamp movementDate = getMovementDate(rs, ctx);
			final Timestamp dateAcct = getDateAcct(rs,ctx);

			receiptHeader.setDateReceived(getExternalReceivedDate(rs));
			receiptHeader.setMovementDate(movementDate);
			receiptHeader.setDateAcct(dateAcct);
		}

		//
		// Warehouse
		{
			final WarehouseId warehouseId = receiptScheduleBL.getWarehouseEffectiveId(rs);
			receiptHeader.setM_Warehouse_ID(warehouseId.getRepoId());
		}

		//
		// Set's HeaderAggregationKey
		{
			final String headerAggregationKey = headerAggregationKeyBuilder.buildKey(rs);
			InterfaceWrapperHelper.setDynAttribute(receiptHeader, DYNATTR_HeaderAggregationKey, headerAggregationKey);
		}

		//
		// DropShip informations (08402)
		final I_C_Order order = rs.getC_Order();

		final boolean propagateToMInOut = orderEmailPropagationSysConfigRepository.isPropagateToMInOut(ClientAndOrgId.ofClientAndOrg(receiptHeader.getAD_Client_ID(), receiptHeader.getAD_Org_ID()));
		if (propagateToMInOut)
		{
			receiptHeader.setEMail(order.getEMail());
			receiptHeader.setAD_InputDataSource_ID(order.getAD_InputDataSource_ID());
			receiptHeader.setPOReference(order.getPOReference());
		}
		if (order != null && order.isDropShip())
		{
			receiptHeader.setIsDropShip(true);
			InOutDocumentLocationAdapterFactory
					.deliveryLocationAdapter(receiptHeader)
					.setFrom(OrderDocumentLocationAdapterFactory.deliveryLocationAdapter(order).toDocumentLocation());
		}
		else
		{
			receiptHeader.setIsDropShip(false);
		}

		//external id
		{
			receiptHeader.setExternalId(getExternalId(rs));
			receiptHeader.setExternalResourceURL(getExternalResourceURL(rs));
		}

		InOutDAO.updateRecordFromForeignContractRef(receiptHeader, forexContractRef);
		receiptHeader.setM_Delivery_Planning_ID(DeliveryPlanningId.toRepoId(deliveryPlanningId));

		receiptHeader.setPOReference(rs.getPOReference());

		//
		// Save & Return
		InterfaceWrapperHelper.save(receiptHeader);
		return receiptHeader;
	}

	private int getReceiptDoctypeID(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// allow specific receipt doctype from order first
		final I_C_Order order = receiptSchedule.getC_Order();
		if (order != null && order.getC_Order_ID() > 0)
		{
			final I_C_DocType orderDoctype = docTypeDAO.getRecordById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
			if (orderDoctype.getC_DocTypeShipment_ID() > 0)
			{
				return orderDoctype.getC_DocTypeShipment_ID();
			}
		}

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(DocBaseType.MaterialReceipt)
				.adClientId(receiptSchedule.getAD_Client_ID())
				.adOrgId(receiptSchedule.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query).getRepoId();
	}

	/**
	 * Helper method to create a new receipt line, linked to current receipt.
	 *
	 * @return newly created receipt line
	 */
	protected final I_M_InOutLine newReceiptLine()
	{
		final I_M_InOut receipt = getCurrentReceipt();
		return inoutBL.newInOutLine(receipt, I_M_InOutLine.class);
	}

	/**
	 * Helper method to update an "empty" receipt line with values from given {@link I_M_ReceiptSchedule}.
	 */
	protected void updateReceiptLine(final I_M_InOutLine line, final I_M_ReceiptSchedule rs)
	{
		final I_M_InOut inout = getCurrentReceipt();

		line.setM_SectionCode_ID(rs.getM_SectionCode_ID());

		// Product & ASI
		line.setM_Product_ID(rs.getM_Product_ID());
		final I_M_AttributeSetInstance rsASI = receiptScheduleBL.getM_AttributeSetInstance_Effective(rs);
		if (rsASI == null || rsASI.getM_AttributeSetInstance_ID() == 0)
		{
			line.setM_AttributeSetInstance_ID(AttributeConstants.M_AttributeSetInstance_ID_None);
		}
		else
		{
			// Do a deep copy of receipt schedule's ASI to prevent changing back to (receipt schedule, order line) in case receipt line's ASI is changed (07317)
			final I_M_AttributeSetInstance asi = Services.get(IAttributeDAO.class).copy(rsASI);
			line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		}

		//
		// Line Warehouse & Locator
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(inout.getM_Warehouse_ID());
			final LocatorId locatorId = Services.get(IWarehouseBL.class).getOrCreateDefaultLocatorId(warehouseId);
			line.setM_Locator_ID(locatorId.getRepoId());
		}

		//
		// Quantities
		final StockQtyAndUOMQty qtyToMove = receiptScheduleBL.getQtyToMove(rs);
		line.setMovementQty(qtyToMove.getStockQty().toBigDecimal());

		final UomId lineUomId = coalesceNotNull(
				UomId.ofRepoIdOrNull(rs.getC_UOM_ID()),
				qtyToMove.getStockQty().getUomId());

		final Quantity qtyEntered = uomConversionBL
				.convertQuantityTo(
						qtyToMove.getStockQty(),
						UOMConversionContext.of(qtyToMove.getProductId()),
						lineUomId);
		line.setQtyEntered(qtyEntered.toBigDecimal());
		line.setC_UOM_ID(lineUomId.getRepoId());

		if (qtyToMove.getUOMQtyOpt().isPresent())
		{
			line.setQtyDeliveredCatch(qtyToMove.getUOMQtyNotNull().toBigDecimal());
			line.setCatch_UOM_ID(qtyToMove.getUOMQtyNotNull().getUomId().getRepoId());
		}

		//
		// Order Line Link
		line.setC_Order_ID(rs.getC_Order_ID());
		line.setC_OrderLine_ID(rs.getC_OrderLine_ID());

		//
		// Contract
		line.setC_Flatrate_Term_ID(rs.getC_Flatrate_Term_ID());

		dimensionService.updateRecord(line, dimensionService.getFromRecord(rs));
	}

	/**
	 * Create receipt line(s) from current receipt schedule (see {@link #getCurrentReceiptSchedule()}).
	 * <p>
	 * NOTE: this method can be overriden by extending classes.
	 *
	 * @return created receipt lines
	 */
	protected List<? extends I_M_InOutLine> createCurrentReceiptLines()
	{
		final I_M_ReceiptSchedule rs = getCurrentReceiptSchedule();

		final I_M_InOutLine line = newReceiptLine();
		updateReceiptLine(line, rs);
		InterfaceWrapperHelper.save(line);

		return Collections.singletonList(line);
	}

	@NonNull
	private Timestamp getPromisedDate(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		if (receiptSchedule.getC_Order_ID() > 0) // guarding against NPE, but actually C_Order_ID is always set.
		{
			// task: 08648: see the javadoc of createReceiptWithDatePromised
			return receiptSchedule.getC_Order().getDatePromised();
		}
		else
		{
			return Env.getDate(context);
		}
	}

	@NonNull
	private Timestamp getExternalMovementDate(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID());
		final ReceiptScheduleExternalInfo externalInfo = externalInfoByReceiptScheduleId.get(receiptScheduleId);

		if (externalInfo != null && externalInfo.getMovementDate() != null)
		{
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(receiptSchedule.getAD_Org_ID()));
			return TimeUtil.asTimestamp(externalInfo.getMovementDate(), timeZone);
		}

		return Env.getDate(context);
	}

	@NonNull
	private Timestamp getExternalDateAcct(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID());
		final ReceiptScheduleExternalInfo externalInfo = externalInfoByReceiptScheduleId.get(receiptScheduleId);

		if (externalInfo != null && externalInfo.getDateAcct() != null)
		{
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(receiptSchedule.getAD_Org_ID()));
			return TimeUtil.asTimestamp(externalInfo.getDateAcct(), timeZone);
		}

		return Env.getDate(context);
	}

	@Nullable
	private Timestamp getExternalReceivedDate(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID());

		final ReceiptScheduleExternalInfo externalInfo = externalInfoByReceiptScheduleId.get(receiptScheduleId);

		return externalInfo != null
				? TimeUtil.asTimestamp(externalInfo.getDateReceived())
				: null;
	}

	@Nullable
	private String getExternalId(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID());

		final ReceiptScheduleExternalInfo externalInfo = externalInfoByReceiptScheduleId.get(receiptScheduleId);

		return externalInfo != null
				? StringUtils.trimBlankToNull(externalInfo.getExternalId())
				: null;
	}

	@Nullable
	private String getExternalResourceURL(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return StringUtils.trimBlankToNull(receiptSchedule.getExternalResourceURL());

	}

	private Timestamp getMovementDate(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		return movementDateRule.map(new ReceiptMovementDateRule.CaseMapper<Timestamp>()
		{
			@Override
			public Timestamp orderDatePromised() {return getPromisedDate(receiptSchedule, context);}

			@Override
			public Timestamp externalDateIfAvailable() {return getExternalMovementDate(receiptSchedule, context);}

			// Use Login Date as movement date because some roles will rely on the fact that they can override it (08247)
			@Override
			public Timestamp currentDate() {return Env.getDate(context);}

			@Override
			public Timestamp fixedDate(@NonNull final Instant fixedDate) {return Timestamp.from(fixedDate);}
		});
	}

	private Timestamp getDateAcct (@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		return movementDateRule.map(new ReceiptMovementDateRule.CaseMapper<Timestamp>()
		{
			@Override
			public Timestamp orderDatePromised() {return getPromisedDate(receiptSchedule, context);}

			@Override
			public Timestamp externalDateIfAvailable() {return getExternalDateAcct(receiptSchedule, context);}

			// Use Login Date as movement date because some roles will rely on the fact that they can override it (08247)
			@Override
			public Timestamp currentDate() {return Env.getDate(context);}

			@Override
			public Timestamp fixedDate(@NonNull final Instant fixedDate) {return Timestamp.from(fixedDate);}
		});
	}
}
