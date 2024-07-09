package de.metas.handlingunits.shipmentschedule.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.forex.ForexContractRef;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.impl.HUShipmentPackingMaterialLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.i18n.BooleanWithReason;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.event.InOutUserNotificationsProducer;
import de.metas.inout.impl.InOutDAO;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create Shipments from {@link ShipmentScheduleWithHU} records.
 *
 * @author tsa
 */
public class InOutProducerFromShipmentScheduleWithHU
		implements IInOutProducerFromShipmentScheduleWithHU, ITrxItemChunkProcessor<ShipmentScheduleWithHU, InOutGenerateResult>
{
	// Services
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	//
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final transient ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepo = SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);

	private final InOutGenerateResult result;
	private final IAggregationKeyBuilder<I_M_ShipmentSchedule> shipmentScheduleKeyBuilder;
	private final IAggregationKeyBuilder<ShipmentScheduleWithHU> huShipmentScheduleKeyBuilder;

	private final ShipmentLineNoInfo shipmentLineNoInfo = new ShipmentLineNoInfo();

	private ITrxItemProcessorContext processorCtx;
	private ITrxItemExceptionHandler trxItemExceptionHandler = FailTrxItemExceptionHandler.instance;

	@Nullable
	private I_M_InOut currentShipment;

	@Nullable
	private ShipmentLineBuilder currentShipmentLineBuilder;

	/**
	 * All candidates for {@link #currentShipment}
	 */
	@Nullable
	private List<ShipmentScheduleWithHU> currentCandidates;

	/**
	 * Last processed item
	 */
	private ShipmentScheduleWithHU lastItem;

	//
	// 07113: constant to know if the document shall be completed or not.
	private boolean processShipments = true;

	private boolean createPackingLines = false;

	private CalculateShippingDateRule calculateShippingDateRule = CalculateShippingDateRule.DELIVERY_DATE_OR_TODAY;

	/**
	 * A list of TUs which are assigned to different shipment lines.
	 * <p>
	 * This list is shared between all shipment lines from all shipments which are produced by this producer.
	 * <p>
	 * In this way, we {@link I_M_HU_Assignment#setIsTransferPackingMaterials(boolean)} to <code>true</code> only on first assignment.
	 */
	private final Set<HuId> tuIdsAlreadyAssignedToShipmentLine = new HashSet<>();

	private final Map<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfo = new HashMap<>();

	@Nullable private ForexContractRef forexContractRef;
	@Nullable private DeliveryPlanningId deliveryPlanningId;
	@Nullable private InOutId b2bReceiptId;

	public InOutProducerFromShipmentScheduleWithHU(@NonNull final InOutGenerateResult result)
	{
		this.result = result;

		shipmentScheduleKeyBuilder = shipmentScheduleBL.mkShipmentHeaderAggregationKeyBuilder();
		huShipmentScheduleKeyBuilder = huShipmentScheduleBL.mkHUShipmentScheduleHeaderAggregationKeyBuilder();
	}

	@Override
	public InOutGenerateResult createShipments(@NonNull final List<ShipmentScheduleWithHU> candidates)
	{
		final InOutUserNotificationsProducer shipmentGeneratedNotifications = InOutUserNotificationsProducer.newInstance();

		try
		{
			final InOutGenerateResult result = trxItemProcessorExecutorService
					.<ShipmentScheduleWithHU, InOutGenerateResult>createExecutor()
					.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.setProcessor(this)
					.setExceptionHandler(trxItemExceptionHandler)
					.process(candidates);

			//
			// Send notifications
			shipmentGeneratedNotifications.notifyInOutsProcessed(result.getInOuts());

			return result;
		}
		catch (final Exception ex)
		{
			final String sourceInfo = extractSourceInfo(candidates);
			shipmentGeneratedNotifications.notifyShipmentError(sourceInfo, ex.getLocalizedMessage());

			// propagate
			throw AdempiereException.wrapIfNeeded(ex)
					.markUserNotified();
		}

	}

	private static String extractSourceInfo(final List<ShipmentScheduleWithHU> candidates)
	{
		return candidates.stream()
				.map(InOutProducerFromShipmentScheduleWithHU::extractSourceInfo)
				.distinct()
				.collect(Collectors.joining(", "));
	}

	private static String extractSourceInfo(final ShipmentScheduleWithHU candidate)
	{
		final I_M_HU luHU = candidate.getM_LU_HU();
		if (luHU != null)
		{
			return luHU.getValue();
		}

		final I_M_HU tuHU = candidate.getM_TU_HU();
		if (tuHU != null)
		{
			return tuHU.getValue();
		}

		// default, shall not reach this point
		return "#" + candidate.getM_ShipmentSchedule().getM_ShipmentSchedule_ID();
	}

	@Override
	public boolean isSameChunk(@NonNull final ShipmentScheduleWithHU item)
	{
		//
		// If there is no last item (i.e. this is the first item), consider it as a new chunk
		if (lastItem == null)
		{
			return false;
		}

		final I_M_ShipmentSchedule sched = item.getM_ShipmentSchedule();
		final I_M_ShipmentSchedule lastSched = lastItem.getM_ShipmentSchedule();

		return shipmentScheduleKeyBuilder.isSame(sched, lastSched) &&
				huShipmentScheduleKeyBuilder.isSame(item, lastItem);
	}

	@Override
	public void newChunk(final ShipmentScheduleWithHU sched)
	{
		currentShipment = getCreateShipmentHeader(sched);
		currentShipmentLineBuilder = null;
		currentCandidates = new ArrayList<>();
	}

	/**
	 * If the schedule allows consolidation, try to retrieve the fist open shipment found matching requested transportation. If none is found, create a new shipment header.
	 *
	 * @return shipment (header)
	 */
	private I_M_InOut getCreateShipmentHeader(@NonNull final ShipmentScheduleWithHU candidate)
	{
		final LocalDate shipmentDate = calculateShipmentDate(candidate, calculateShippingDateRule);

		//
		// Search for existing shipment to consolidate on
		I_M_InOut shipment = null;
		if (shipmentScheduleBL.isSchedAllowsConsolidate(candidate.getM_ShipmentSchedule()))
		{
			shipment = huShipmentScheduleBL.getOpenShipmentOrNull(candidate, shipmentDate);
		}
		//
		// If there was no shipment found, create a new one now
		if (shipment == null)
		{
			shipment = createShipmentHeader(candidate, shipmentDate);
		}

		MDC.put(I_M_InOut.COLUMNNAME_M_InOut_ID, Integer.toString(shipment.getM_InOut_ID()));
		return shipment;
	}

	@VisibleForTesting
	LocalDate calculateShipmentDate(@NonNull final ShipmentScheduleWithHU candidate, @NonNull final CalculateShippingDateRule calculateShippingDateType)
	{
		return calculateShippingDateType.map(new CalculateShippingDateRule.CaseMapper<>()
		{
			@Override
			public LocalDate today()
			{
				return SystemTime.asLocalDate();
			}

			@Override
			public LocalDate deliveryDate()
			{
				return candidate.getDeliveryDate().orElseGet(SystemTime::asLocalDate);
			}

			@Override
			public LocalDate deliveryDateOrToday()
			{
				final LocalDate today = SystemTime.asLocalDate();
				final LocalDate deliveryDate = candidate.getDeliveryDate().orElse(today);
				return TimeUtil.max(deliveryDate, today);
			}

			@Override
			public LocalDate fixedDate(@NonNull final LocalDate fixedDate)
			{
				return fixedDate;
			}
		});
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL#getOpenShipmentOrNull(ShipmentScheduleWithHU, LocalDate)}.
	 */
	private I_M_InOut createShipmentHeader(final ShipmentScheduleWithHU candidate, final LocalDate dateDoc)
	{
		final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();

		final I_M_InOut shipment = InterfaceWrapperHelper.newInstance(I_M_InOut.class, processorCtx);
		shipment.setAD_Org_ID(shipmentSchedule.getAD_Org_ID());

		shipment.setM_SectionCode_ID(candidate.getM_ShipmentSchedule().getM_SectionCode_ID());

		// Document Type
		{
			shipment.setC_DocType_ID(getInoutDoctypeID(shipmentSchedule));
			shipment.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerShipment);
			shipment.setIsSOTrx(true);

			final ShipmentScheduleExternalInfo externalInfo = scheduleId2ExternalInfo.get(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()));

			if (externalInfo != null && Check.isNotBlank(externalInfo.getDocumentNo()))
			{
				shipment.setDocumentNo(externalInfo.getDocumentNo());
			}
		}

		//
		// BPartner, Location & Contact
		InOutDocumentLocationAdapterFactory
				.locationAdapter(shipment)
				.setFrom(shipmentScheduleEffectiveValuesBL.getDocumentLocation(shipmentSchedule));

		//
		// Document Dates
		{
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID()));
			final Timestamp movementDate = TimeUtil.asTimestamp(dateDoc, timeZone);
			shipment.setMovementDate(movementDate);
			shipment.setDateAcct(movementDate);
		}

		//
		// Warehouse
		{
			shipment.setM_Warehouse_ID(shipmentScheduleEffectiveValuesBL.getWarehouseId(shipmentSchedule).getRepoId());
		}

		//
		// C_Order reference
		{
			final de.metas.order.model.I_C_Order order = orderDAO.getById(OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID()), de.metas.order.model.I_C_Order.class);
			if (order != null && order.getC_Order_ID() > 0)
			{
				shipment.setDateOrdered(order.getDateOrdered());
				shipment.setC_Order_ID(order.getC_Order_ID()); // TODO change if partner allow consolidation too
				shipment.setPOReference(order.getPOReference());
				shipment.setC_Incoterms_ID(order.getC_Incoterms_ID());
				shipment.setIncotermLocation(order.getIncotermLocation());
				shipment.setDeliveryViaRule(order.getDeliveryViaRule());
				shipment.setM_Shipper_ID((order.getM_Shipper_ID()));
				shipment.setM_Tour_ID(shipmentSchedule.getM_Tour_ID());

				if (orderEmailPropagationSysConfigRepo.isPropagateToMInOut(ClientAndOrgId.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID())))
				{
					shipment.setEMail(order.getEMail());
				}

				shipment.setAD_InputDataSource_ID(order.getAD_InputDataSource_ID());

				shipment.setSalesRep_ID(order.getSalesRep_ID());
			}
		}

		//
		// M_ShipperTransportation (07973: set it on the shipment so that it can be re-used in aggregation)
		{
			final List<I_M_HU> hus = new ArrayList<>();
			hus.add(candidate.getVHU());
			hus.add(candidate.getM_TU_HU());
			hus.add(candidate.getM_LU_HU());
			final I_M_ShipperTransportation shipperTransportation = huShipperTransportationBL.getCommonM_ShipperTransportationOrNull(hus);
			if (shipperTransportation != null)
			{
				shipment.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
			}
		}

		if (shipmentSchedule.getC_Async_Batch_ID() > 0)
		{
			shipment.setC_Async_Batch_ID(shipmentSchedule.getC_Async_Batch_ID());
		}

		InOutDAO.updateRecordFromForeignContractRef(shipment, forexContractRef);
		shipment.setM_Delivery_Planning_ID(DeliveryPlanningId.toRepoId(deliveryPlanningId));
		shipment.setB2B_InOut_ID(InOutId.toRepoId(b2bReceiptId));

		//
		// Save Shipment Header
		InterfaceWrapperHelper.save(shipment);

		return shipment;
	}

	private int getInoutDoctypeID(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		// allow specific inout doctype from order first
		final de.metas.order.model.I_C_Order order = orderDAO.getById(OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID()), de.metas.order.model.I_C_Order.class);
		if (order != null && order.getC_Order_ID() > 0)
		{
			final I_C_DocType orderDoctype = docTypeDAO.getRecordById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
			if (orderDoctype.getC_DocTypeShipment_ID() > 0)
			{
				return orderDoctype.getC_DocTypeShipment_ID();
			}
		}

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(DocBaseType.MaterialDelivery)
				.adClientId(shipmentSchedule.getAD_Client_ID())
				.adOrgId(shipmentSchedule.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query).getRepoId();
	}

	/**
	 * If {@link #currentShipmentLineBuilder} is set and it can create a shipment line then:
	 * <ul>
	 * <li>create the shipment line
	 * <li>update {@link #currentCandidates}
	 * <li>reset the current builder (set it to null)
	 * </ul>
	 */
	private void createShipmentLineIfAny()
	{
		if (currentShipmentLineBuilder == null)
		{
			return;
		}

		if (currentShipmentLineBuilder.canCreateShipmentLine())
		{
			currentShipmentLineBuilder.createShipmentLine();

			final List<ShipmentScheduleWithHU> candidates = currentShipmentLineBuilder.getCandidates();
			currentCandidates.addAll(candidates);
		}

		currentShipmentLineBuilder = null;
	}

	@Override
	public void completeChunk()
	{
		//
		// Create current shipment line (if any)
		createShipmentLineIfAny();

		//
		// Process shipment (if is not empty)
		final BooleanWithReason valid = checkValidCurrentShipment();
		if (valid.isTrue())
		{
			processCurrentShipment();
		}
		//
		// Process shipment (if is not empty)
		else
		{
			Loggables.addLog("Deleting invalid shipment {0} because: {1}", currentShipment, valid.getReason());
			InterfaceWrapperHelper.delete(currentShipment);
		}

		resetCurrentShipment();
	}

	private void processCurrentShipment()
	{
		final ImmutableList<InOutLineId> shipmentLineIdsWithLineNoCollisions = shipmentLineNoInfo.getShipmentLineIdsWithLineNoCollisions();
		inOutDAO.unsetLineNos(shipmentLineIdsWithLineNoCollisions);

		final HUShipmentPackingMaterialLinesBuilder packingMaterialLinesBuilder = huInOutBL.createHUShipmentPackingMaterialLinesBuilder(currentShipment);

		// because this is the time when we are actually generating the shipment lines,
		// we want to initialize overrides values too
		packingMaterialLinesBuilder.setUpdateOverrideValues(true);

		// task 08138
		if (createPackingLines)
		{
			packingMaterialLinesBuilder.setOverrideExistingPackingMaterialLines(true); // delete existing packing material lines, if any
			packingMaterialLinesBuilder.build();
		}
		else
		{
			packingMaterialLinesBuilder.collectPackingMaterialsAndUpdateShipmentLines();
		}

		//
		// Process current shipment
		// 07113: if the CompleteShipment is false, we will have them only drafted (as default)
		if (processShipments)
		{
			docActionBL.processEx(currentShipment, IDocument.ACTION_Complete, null);
		}
		result.addInOut(currentShipment);

		//
		// Iterate all candidates which were added on this shipment and notify them about the generated shipment
		for (final ShipmentScheduleWithHU candidate : currentCandidates)
		{
			candidate.setM_InOut(currentShipment);
		}

		//
		// Iterate all candidates and update shipment schedule's HU related Qtys
		final Set<Integer> seenM_ShipmentSchedule_IDs = new HashSet<>();
		for (final ShipmentScheduleWithHU candidate : currentCandidates)
		{
			final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();
			final int shipmentScheduleId = shipmentSchedule.getM_ShipmentSchedule_ID();
			if (!seenM_ShipmentSchedule_IDs.add(shipmentScheduleId))
			{
				// already seen this shipment schedule
				continue;
			}

			// save the shipment schedule using current transaction
			InterfaceWrapperHelper.save(shipmentSchedule, processorCtx.getTrxName());
		}

		//
		// B2B: Link back the B2B receipt to this B2B shipment
		final InOutId b2bReceiptId = InOutId.ofRepoIdOrNull(currentShipment.getB2B_InOut_ID());
		if (b2bReceiptId != null)
		{
			final I_M_InOut b2bReceipt = inOutDAO.getById(b2bReceiptId, I_M_InOut.class);
			if (b2bReceipt == null)
			{
				throw new AdempiereException("No B2B receipt found for " + b2bReceiptId);
			}
			b2bReceipt.setB2B_InOut_ID(currentShipment.getM_InOut_ID());
			inOutDAO.save(b2bReceipt);
		}

		Loggables.addLog("Shipment {0} was created;\nShipmentScheduleWithHUs: {1}", currentShipment, currentCandidates);
	}

	private BooleanWithReason checkValidCurrentShipment()
	{
		//
		if (currentCandidates == null || currentCandidates.isEmpty())
		{
			return BooleanWithReason.falseBecause("got no candidates so shipment would be empty");
		}

		//
		// check complete order
		final BooleanWithReason completeOrderDelivery = CompleteOrderDeliveryRuleChecker.builder()
				.shipmentScheduleEffectiveValuesBL(shipmentScheduleEffectiveValuesBL)
				.shipmentSchedulesRepo(shipmentSchedulesRepo)
				.candidates(currentCandidates)
				.build()
				.check();
		if (completeOrderDelivery.isFalse())
		{
			return completeOrderDelivery;
		}

		//
		return BooleanWithReason.TRUE;
	}

	private void resetCurrentShipment()
	{
		currentShipmentLineBuilder = null;
		currentShipment = null;
		currentCandidates = null;

		MDC.remove(I_M_InOut.COLUMNNAME_M_InOut_ID);
	}

	@Override
	public final void cancelChunk()
	{
		// NOTE: no need to delete current shipment if not null because when this method is called the API is preparing to rollback
		resetCurrentShipment();
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setTrxItemExceptionHandler(@NonNull final ITrxItemExceptionHandler trxItemExceptionHandler)
	{
		this.trxItemExceptionHandler = trxItemExceptionHandler;
		return this;
	}

	@Override
	public void process(@NonNull final ShipmentScheduleWithHU item) throws Exception
	{
		updateShipmentDate(currentShipment, item);
		createUpdateShipmentLine(item);
		lastItem = item;
	}

	private void updateShipmentDate(@NonNull final I_M_InOut shipment, @NonNull final ShipmentScheduleWithHU candidate)
	{
		final LocalDate candidateShipmentDate = calculateShipmentDate(candidate, calculateShippingDateRule);

		// the shipment was created before but wasn't yet completed;
		if (isShipmentDeliveryDateBetterThanMovementDate(shipment, candidateShipmentDate))
		{
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(shipment.getAD_Org_ID()));
			final Timestamp candidateShipmentDateTS = TimeUtil.asTimestamp(candidateShipmentDate, timeZone);
			shipment.setMovementDate(candidateShipmentDateTS);
			shipment.setDateAcct(candidateShipmentDateTS);

			InterfaceWrapperHelper.save(shipment);
		}
	}

	@VisibleForTesting
	static boolean isShipmentDeliveryDateBetterThanMovementDate(
			final @NonNull I_M_InOut shipment,
			final @NonNull LocalDate shipmentDeliveryDate)
	{
		final ZoneId timeZone = Services.get(IOrgDAO.class).getTimeZone(OrgId.ofRepoId(shipment.getAD_Org_ID()));

		final LocalDate today = SystemTime.asLocalDate();
		final LocalDate movementDate = TimeUtil.asLocalDate(shipment.getMovementDate(), timeZone);

		final boolean isCandidateInThePast = shipmentDeliveryDate.isBefore(today);
		final boolean isMovementDateInThePast = movementDate.isBefore(today);
		final boolean isCandidateSoonerThanMovementDate = movementDate.isAfter(shipmentDeliveryDate);

		if (isCandidateInThePast)
		{
			return false;
		}

		if (isMovementDateInThePast)
		{
			return true;
		}

		else if (isCandidateSoonerThanMovementDate)
		{
			return true;
		}

		return false;
	}

	private void createUpdateShipmentLine(@NonNull final ShipmentScheduleWithHU candidate)
	{
		//
		// If we cannot add this "candidate" to current shipment line builder
		// then create shipment line (if any) and reset the builder
		if (currentShipmentLineBuilder != null && !currentShipmentLineBuilder.canAdd(candidate))
		{
			createShipmentLineIfAny(); // => currentShipmentLineBuilder is null after this
		}

		//
		// If we don't have an active shipment line builder
		// then create one
		if (currentShipmentLineBuilder == null)
		{
			currentShipmentLineBuilder = new ShipmentLineBuilder(currentShipment, shipmentLineNoInfo);
			currentShipmentLineBuilder.setManualPackingMaterial(candidate.isAdviseManualPackingMaterial());
			currentShipmentLineBuilder.setQtyTypeToUse(candidate.getQtyTypeToUse());
			currentShipmentLineBuilder.setAlreadyAssignedTUIds(tuIdsAlreadyAssignedToShipmentLine);
		}

		//
		// Add current "candidate"
		currentShipmentLineBuilder.add(candidate);
	}

	@Override
	public InOutGenerateResult getResult()
	{
		return result;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setProcessShipments(final boolean processShipments)
	{
		this.processShipments = processShipments;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setCreatePackingLines(final boolean createPackingLines)
	{
		this.createPackingLines = createPackingLines;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU computeShipmentDate(final CalculateShippingDateRule calculateShippingDateRule)
	{
		this.calculateShippingDateRule = calculateShippingDateRule;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setScheduleIdToExternalInfo(@NonNull final ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfo)
	{
		this.scheduleId2ExternalInfo.putAll(scheduleId2ExternalInfo);
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setForexContractRef(@Nullable final ForexContractRef forexContractRef)
	{
		this.forexContractRef = forexContractRef;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setDeliveryPlanningId(@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		this.deliveryPlanningId = deliveryPlanningId;
		return this;
	}

	@Override
	public InOutProducerFromShipmentScheduleWithHU setB2BReceiptId(@Nullable final InOutId b2bReceiptId)
	{
		this.b2bReceiptId = b2bReceiptId;
		return this;
	}

	@Override
	public String toString()
	{
		return "InOutProducerFromShipmentSchedule [result=" + result
				+ ", shipmentScheduleKeyBuilder=" + shipmentScheduleKeyBuilder + ", huShipmentScheduleKeyBuilder=" + huShipmentScheduleKeyBuilder
				+ ", processorCtx=" + processorCtx + ", currentShipment=" + currentShipment
				+ ", currentShipmentLineBuilder=" + currentShipmentLineBuilder + ", currentCandidates=" + currentCandidates
				+ ", lastItem=" + lastItem + "]";
	}
}
