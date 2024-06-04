/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.api;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.LULoader;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.config.PickingConfigV2;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.DefaultPickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitRepository;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.logging.LogManager;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.PackageableList;
import de.metas.picking.api.PackageableQuery;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleWithHUService
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleWithHUService.class);

	private static final String SYSCFG_PICK_AVAILABLE_HUS_ON_THE_FLY = "de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly";

	private static final AdMessageKey MSG_NoQtyPicked = AdMessageKey.of("MSG_NoQtyPicked");
	public static final String SYSCONFIG_PACK_CUS_TO_TU = "de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU";

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);

	@NonNull private final ShipmentScheduleWithHUSupportingServices shipmentScheduleWithHUSupportingServices;
	@NonNull private final ShipmentScheduleSplitService shipmentScheduleSplitService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLoaderSupportingServicesFactory pickingJobLoaderSupportingServicesFactory;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final ModularContractProvider modularContractProvider;

	public static ShipmentScheduleWithHUService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		final PickingJobRepository pickingJobRepository = new PickingJobRepository();
		final PickingJobSlotService pickingJobSlotService = new PickingJobSlotService(pickingJobRepository);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final HUQRCodesService huQRCodeService = new HUQRCodesService(
				new HUQRCodesRepository(),
				new GlobalQRCodeService(DoNothingMassPrintingService.instance),
				new QRCodeConfigurationService(new QRCodeConfigurationRepository())
		);

		final DefaultPickingJobLoaderSupportingServicesFactory defaultPickingJobLoaderSupportingServicesFactory = new DefaultPickingJobLoaderSupportingServicesFactory(
				pickingJobSlotService,
				bpartnerBL,
				huQRCodeService
		);

		final HUReservationService huReservationServiceTest = new HUReservationService(new HUReservationRepository());
		final InventoryService inventoryServiceTest = InventoryService.newInstanceForUnitTesting();
		final PickingCandidateService pickingCandidateServiceTest = new PickingCandidateService(
				new PickingConfigRepository(),
				new PickingCandidateRepository(),
				new HuId2SourceHUsService(new HUTraceRepository()),
				huReservationServiceTest,
				Services.get(IBPartnerBL.class),
				ADReferenceService.newMocked(),
				inventoryServiceTest);

		return new ShipmentScheduleWithHUService(
				ShipmentScheduleWithHUSupportingServices.newInstanceForUnitTesting(),
				new ShipmentScheduleSplitService(new ShipmentScheduleSplitRepository()),
				pickingJobRepository,
				defaultPickingJobLoaderSupportingServicesFactory,
				huReservationServiceTest,
				pickingCandidateServiceTest,
				new PickingConfigRepositoryV2(),
				inventoryServiceTest,
				new ModularContractProvider(new ModularContractSettingsBL(new ModularContractSettingsDAO()))
		);
	}

	@Value
	@Builder
	public static class CreateCandidatesRequest
	{
		@NonNull
		IHUContext huContext;

		@NonNull
		ShipmentScheduleId shipmentScheduleId;

		@NonNull
		M_ShipmentSchedule_QuantityTypeToUse quantityType;

		/**
		 * If {@code false} and HUs are picked on-the-fly, then those HUs are created as CUs that are taken from bigger LUs, TUs or CUs (the default).
		 * If {@code true}, then the on-the-fly picked HUs are created as TUs, using the respective shipment schedules' packing instructions.
		 */
		@Builder.Default
		boolean onTheFlyPickToPackingInstructions = false;

		/**
		 * If set and {@link #quantityType} is TYPE_QTY_TO_DELIVER (or _BOTH), then the respective M_ShipmentSchedule's current QtyToDeliver and QtyToDeliver_Override will be ignored.
		 */
		@Nullable
		Quantity quantityToDeliverOverride;

		/**
		 * Fails if no picked HUs were found.
		 * Applies only when <code>quantityType</code> is PickedQty.
		 */
		@Builder.Default boolean isFailIfNoPickedHUs = true;
	}

	/**
	 * Create {@link ShipmentScheduleWithHU} (i.e. candidates) for given <code>schedule</code>.
	 * <p>
	 * NOTE: this method will create missing LUs before.
	 *
	 * @return one single candidate if there are no {@link I_M_ShipmentSchedule_QtyPicked} for the given schedule. One candidate per {@link I_M_ShipmentSchedule_QtyPicked} otherwise.
	 */
	public ImmutableList<ShipmentScheduleWithHU> createShipmentSchedulesWithHU(@NonNull final CreateCandidatesRequest request)
	{
		final ImmutableList.Builder<ShipmentScheduleWithHU> candidates = ImmutableList.builder();

		final ShipmentScheduleWithHUFactory factory = ShipmentScheduleWithHUFactory.builder()
				.supportingServices(shipmentScheduleWithHUSupportingServices)
				.huContext(request.getHuContext())
				.build();

		final M_ShipmentSchedule_QuantityTypeToUse quantityType = request.getQuantityType();

		final I_M_ShipmentSchedule scheduleRecord = huShipmentScheduleBL.getById(request.getShipmentScheduleId());

		switch (quantityType)
		{
			case TYPE_QTY_TO_DELIVER:
				candidates.addAll(createShipmentSchedulesWithHUForQtyToDeliver(scheduleRecord, request.getQuantityToDeliverOverride(), quantityType, request.isOnTheFlyPickToPackingInstructions(), factory));
				break;
			case TYPE_PICKED_QTY:
				final Collection<? extends ShipmentScheduleWithHU> candidatesForPick = createAndValidateCandidatesForPick(factory, scheduleRecord, quantityType, request.isFailIfNoPickedHUs());
				candidates.addAll(candidatesForPick);
				break;
			case TYPE_BOTH:
				candidates.addAll(createShipmentScheduleWithHUForPick(scheduleRecord, factory, quantityType));
				candidates.addAll(createShipmentSchedulesWithHUForQtyToDeliver(scheduleRecord, request.getQuantityToDeliverOverride(), quantityType, request.isOnTheFlyPickToPackingInstructions(), factory));
				break;
			case TYPE_SPLIT_SHIPMENT:
				candidates.addAll(createShipmentScheduleWithHUForSplitShipments(scheduleRecord, factory));
				break;
			default:
				throw new AdempiereException("Unexpected QuantityType=" + quantityType + "; CreateCandidatesRequest=" + request);
		}

		return candidates.build();
	}

	/**
	 * @param onTheFlyPickToPackingInstructions If {@code false} and HUs are picked on-the-fly, then those HUs are created as CUs that are taken from bigger LUs, TUs or CUs (the default). If {@code true}, then in addition the on-the-fly picked HUs are created as TUs, using the respective shipment schedules' packing instructions.
	 */
	public ImmutableList<ShipmentScheduleWithHU> createShipmentSchedulesWithHU(
			@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse,
			final boolean onTheFlyPickToPackingInstructions,
			@NonNull final ImmutableMap<ShipmentScheduleId, BigDecimal> scheduleId2QtyToDeliverOverride,
			final boolean isFailIfNoPickedHUs)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}

		final IHUContext huContext = huContextFactory.createMutableHUContext();

		final CreateCandidatesRequest.CreateCandidatesRequestBuilder requestBuilder = CreateCandidatesRequest.builder()
				.huContext(huContext)
				.onTheFlyPickToPackingInstructions(onTheFlyPickToPackingInstructions)
				.quantityType(quantityTypeToUse)
				.isFailIfNoPickedHUs(isFailIfNoPickedHUs);

		final ArrayList<ShipmentScheduleWithHU> candidates = new ArrayList<>();

		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final Quantity quantityToDeliverOverride = extractQuantityToDeliverOverrideOrNull(scheduleId2QtyToDeliverOverride, shipmentSchedule);
			requestBuilder.quantityToDeliverOverride(quantityToDeliverOverride);

			final ImmutableList<ShipmentScheduleWithHU> candidatesForSched = createCandidatesForSched(requestBuilder, shipmentSchedule);
			candidates.addAll(candidatesForSched);
		}

		// Sort our candidates
		candidates.sort(new ShipmentScheduleWithHUComparator());

		return ImmutableList.copyOf(candidates);
	}

	@Nullable
	private Quantity extractQuantityToDeliverOverrideOrNull(
			@NonNull final ImmutableMap<ShipmentScheduleId, BigDecimal> scheduleId2QtyToDeliverOverride,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final Quantity quantityToDeliverOverride;
		final BigDecimal qtyToDeliverOverride = scheduleId2QtyToDeliverOverride.get(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()));
		if (qtyToDeliverOverride != null)
		{
			quantityToDeliverOverride = Quantitys.of(qtyToDeliverOverride, ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()));

		}
		else
		{
			quantityToDeliverOverride = null;
		}
		return quantityToDeliverOverride;
	}

	private List<ShipmentScheduleWithHU> createShipmentSchedulesWithHUForQtyToDeliver(
			@NonNull final I_M_ShipmentSchedule scheduleRecord,
			@Nullable final Quantity quantityToDeliverOverride,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse,
			final boolean pickAccordingToPackingInstruction,
			@NonNull final ShipmentScheduleWithHUFactory factory)
	{
		final ArrayList<ShipmentScheduleWithHU> result = new ArrayList<>();

		final Quantity qtyToDeliver = CoalesceUtil.coalesceSuppliersNotNull(
				() -> quantityToDeliverOverride,
				() -> shipmentScheduleBL.getQtyToDeliver(scheduleRecord));

		if (productBL.isStocked(ProductId.ofRepoId(scheduleRecord.getM_Product_ID())))
		{
			result.addAll(pickHUsOnTheFlyIfPossible(scheduleRecord, qtyToDeliver, pickAccordingToPackingInstruction, factory));
		}
		else
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ProductId={} is not stocked; skip picking it on the fly", scheduleRecord.getM_Product_ID());
		}

		// find out if and what the pickHUsOnTheFly() method did for us
		final Quantity allocatedQty = result
				.stream()
				.map(ShipmentScheduleWithHU::getQtyPicked)
				.reduce(qtyToDeliver.toZero(), Quantity::add);
		Loggables.withLogger(logger, Level.DEBUG).addLog("QtyToDeliver={}; Qty picked on-the-fly from available HUs: {}", qtyToDeliver, allocatedQty);

		final Quantity remainingQtyToAllocate = qtyToDeliver.subtract(allocatedQty);
		if (remainingQtyToAllocate.signum() > 0)
		{
			final boolean hasNoPickedHUs = result.isEmpty();

			final Quantity catchQtyOverride = hasNoPickedHUs
					? shipmentScheduleBL.getCatchQtyOverride(scheduleRecord).orElse(null)
					: null /* if at least one HU was picked, the catchOverride qty was added there */;

			final ProductId productId = ProductId.ofRepoId(scheduleRecord.getM_Product_ID());
			final StockQtyAndUOMQty stockQtyAndCatchQty = StockQtyAndUOMQty.builder()
					.productId(productId)
					.stockQty(remainingQtyToAllocate)
					.uomQty(catchQtyOverride)
					.build();

			result.add(factory.ofShipmentScheduleWithoutHu(
					scheduleRecord,
					stockQtyAndCatchQty,
					quantityTypeToUse));
		}
		return ImmutableList.copyOf(result);
	}

	@NonNull
	private HUsToPickOnTheFly retrievePickAvailableHUsOntheFly(@NonNull final IHUContext huContext)
	{
		final Properties ctx = huContext.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		final String pickAvailableHUsOntheFlyConfig = Services.get(ISysConfigBL.class)
				.getValue(SYSCFG_PICK_AVAILABLE_HUS_ON_THE_FLY,
						HUsToPickOnTheFly.OnlySourceHUs.getCode(),
						adClientId,
						adOrgId);

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("SysConfig {}={} for AD_Client_ID={} and AD_Org_ID={}",
						SYSCFG_PICK_AVAILABLE_HUS_ON_THE_FLY, pickAvailableHUsOntheFlyConfig, adClientId, adOrgId);

		return HUsToPickOnTheFly.ofCode(pickAvailableHUsOntheFlyConfig);
	}

	/**
	 * If there are any existing HUs that match the given {@code scheduleRecord},<br>
	 * then pick them now, although they were not explicitly picked by users.
	 * <p>
	 * Goal: help keeping the metasfresh stock quantity near the real quantity and avoid some inventory effort for users that don't want to use metasfresh's picking.
	 * <p>
	 * Note that we don't use the picked HUs' catch weights since we don't know which HUs were actually picked in the real world.
	 */
	private ImmutableList<ShipmentScheduleWithHU> pickHUsOnTheFly(
			@NonNull final I_M_ShipmentSchedule scheduleRecord,
			@NonNull final Quantity qtyToDeliver,
			final boolean pickAccordingToPackingInstruction,
			@NonNull final ShipmentScheduleWithHUFactory factory,
			@NonNull final HUsToPickOnTheFly hUsToPickOnTheFly)
	{
		if (hUsToPickOnTheFly == HUsToPickOnTheFly.None)
		{
			return ImmutableList.of();
		}

		final ILoggable loggableWithLogger = Loggables.withLogger(logger, Level.DEBUG);

		if (qtyToDeliver.signum() <= 0)
		{
			loggableWithLogger.addLog("pickHUsOnTheFly - qtyToDeliver={} is <= 0; nothing to do", qtyToDeliver);
			return ImmutableList.of();
		}

		//		final IStorageQuery storageQuery = shipmentScheduleBL.createStorageQuery(scheduleRecord, true/* considerAttributes */);
		//
		//		final boolean isHuStorageQuery = storageQuery instanceof HUStorageQuery;
		//		if (!isHuStorageQuery)
		//		{
		//			loggableWithLogger.addLog("pickHUsOnTheFly - ShipmentSchedule's storageQuery is not a HUStorageQuery; nothing to do");
		//			return ImmutableList.of();
		//		}

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(scheduleRecord.getM_ShipmentSchedule_ID());
		final List<ShipmentScheduleWithHU> alreadyPickedOnTheFlyButNotDelivered = getAlreadyPickedOnTheFlyButNotDelivered(shipmentScheduleId, factory);

		result.addAll(alreadyPickedOnTheFlyButNotDelivered);

		Quantity remainingQtyToAllocate = getQtyToAllocate(alreadyPickedOnTheFlyButNotDelivered, qtyToDeliver);

		if (remainingQtyToAllocate.signum() <= 0)
		{
			return result.build();
		}

		boolean firstHU = true;

		// if we have HUs on stock, get them now
		final List<I_M_HU> husToPick = switch (hUsToPickOnTheFly)
		{
			case None -> throw new AdempiereException("Trying to pick on the fly when hUsToPickOnTheFly=None");
			case OnlySourceHUs -> retrievePickOnTheFlySourceHUs(scheduleRecord);
			case AnyHu -> retrieveAnyMatchingHUs(scheduleRecord);
		};

		for (final I_M_HU sourceHURecord : husToPick)
		{
			final ProductId productId = ProductId.ofRepoId(scheduleRecord.getM_Product_ID());

			final I_C_UOM uomRecord = remainingQtyToAllocate.getUOM();
			final Quantity qtyOfSourceHU = extractQtyOfHU(sourceHURecord, productId, uomRecord);
			if (qtyOfSourceHU.signum() <= 0)
			{
				continue; // expected not to happen, but shall not be our problem if it does
			}

			final Quantity quantityToSplit = qtyOfSourceHU.min(remainingQtyToAllocate);
			loggableWithLogger.addLog("pickHUsOnTheFly - QtyToDeliver={}; split Qty={} from available M_HU_ID={} with Qty={}",
					qtyToDeliver, quantityToSplit, sourceHURecord.getM_HU_ID(), qtyOfSourceHU);

			final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
			loggable.addLog("pickHUsOnTheFly - QtyToDeliver={}; split Qty={} from available M_HU_ID={} with Qty={}",
					qtyToDeliver, quantityToSplit, sourceHURecord.getM_HU_ID(), qtyOfSourceHU);

			final List<I_M_HU> newHURecords = createNewlyPickedHUs(scheduleRecord, sourceHURecord, quantityToSplit, pickAccordingToPackingInstruction);

			// we stumbled over HUs with null-trx, which caused some trouble down the line
			InterfaceWrapperHelper.setThreadInheritedTrxName(newHURecords);

			for (final I_M_HU newHURecord : newHURecords)
			{
				final Quantity qtyOfNewHU = extractQtyOfHU(newHURecord, productId, uomRecord);
				loggableWithLogger.addLog("pickHUsOnTheFly - QtyToDeliver={}; assign split M_HU_ID={} with Qty={}", qtyToDeliver, newHURecord.getM_HU_ID(), qtyOfNewHU);

				Quantity catchQtyOverride = null;
				if (firstHU)
				{
					catchQtyOverride = shipmentScheduleBL.getCatchQtyOverride(scheduleRecord).orElse(null);
					firstHU = false;
				}
				// We don't extract the HU's catch weight; see method's javadoc comment.
				// but if this is the first HU, then we add the shipment schedule's override quantity (if any)
				final StockQtyAndUOMQty qtys = StockQtyAndUOMQtys.createConvert(
						qtyOfNewHU/* qtyInAnyUom */,
						productId,
						catchQtyOverride);

				final boolean anonymousTuPickedOnTheFly = true;
				result.add(huShipmentScheduleBL.addQtyPickedAndUpdateHU(
						scheduleRecord,
						qtys,
						newHURecord,
						factory,
						anonymousTuPickedOnTheFly));
				remainingQtyToAllocate = remainingQtyToAllocate.subtract(qtyOfNewHU);
			}

			if (remainingQtyToAllocate.signum() <= 0)
			{
				break; // we are done here
			}
		}

		if (remainingQtyToAllocate.signum() > 0 && hUsToPickOnTheFly == HUsToPickOnTheFly.AnyHu)
		{
			final I_M_HU huForRemainingQty = createHUForRemainingQty(scheduleRecord, remainingQtyToAllocate);

			final Quantity catchQtyOverride = firstHU
					? shipmentScheduleBL.getCatchQtyOverride(scheduleRecord).orElse(null)
					: null;

			final StockQtyAndUOMQty qtys = StockQtyAndUOMQtys.createConvert(
					remainingQtyToAllocate,
					ProductId.ofRepoId(scheduleRecord.getM_Product_ID()),
					catchQtyOverride);

			result.add(huShipmentScheduleBL.addQtyPickedAndUpdateHU(
					scheduleRecord,
					qtys,
					huForRemainingQty,
					factory,
					true));
		}

		return result.build();
	}

	// method coming from tenacious_d
	@NonNull
	private List<I_M_HU> retrievePickOnTheFlySourceHUs(final @NonNull I_M_ShipmentSchedule scheduleRecord)
	{
		final ImmutableList.Builder<I_M_HU> result = ImmutableList.builder();

		// first, get HUs that were explicitly reserved for this scheduleRecord
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(scheduleRecord.getC_OrderLine_ID());
		if (orderLineId != null)
		{
			final Optional<HUReservation> reservation = huReservationService.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId));
			if (reservation.isPresent())
			{
				for (final I_M_HU reservedHURecord : handlingUnitsDAO.getByIds(reservation.get().getVhuIds()))
				{
					if (huStatusBL.isStatusActive(reservedHURecord))
					{
						result.add(reservedHURecord);
					}
				}
			}
		}

		// second, get all unreserved HUs that are available for picking
		final IHUPickingSlotBL.PickingHUsQuery pickingHUsQuery = IHUPickingSlotBL.PickingHUsQuery
				.builder()
				.shipmentSchedule(scheduleRecord)
				.onlyTopLevelHUs(true)
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.excludeAllReserved(true) // we already have the reserved HUs; make sure to not load them again.
				.build();
		result.addAll(huPickingSlotBL.retrieveAvailableHUsToPick(pickingHUsQuery));

		return result.build();
	}

	// method from master_integration
	@NonNull
	private List<I_M_HU> createNewlyPickedHUs(
			final @NonNull I_M_ShipmentSchedule scheduleRecord,
			final @NonNull I_M_HU sourceHURecord, final @NonNull Quantity quantityToSplit, final boolean pickAccordingToPackingInstruction)
	{
		final HUTransformService huTransformService = HUTransformService.newInstance();

		// split a part out of the current HU
		final HUsToNewCUsRequest cuRequest = HUsToNewCUsRequest
				.builder()
				.keepNewCUsUnderSameParent(false)

				// new, from PR https://github.com/metasfresh/metasfresh/pull/12146
				// FIXME: we shall consider not reserved or reserved ones too if they are reserved for us
				.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ALL)

				// OLD
				// .onlyFromUnreservedHUs(false) // note: the HUs returned by the query do not contain HUs which are reserved to someone else

				.productId(ProductId.ofRepoId(scheduleRecord.getM_Product_ID()))
				.qtyCU(quantityToSplit)
				.sourceHU(sourceHURecord)
				.build();
		final List<I_M_HU> newCURecords = huTransformService.husToNewCUs(cuRequest);

		final List<I_M_HU> newHURecords;
		if (pickAccordingToPackingInstruction)
		{
			final HUPIItemProductId piItemProductId = huShipmentScheduleBL.getEffectivePackingMaterialId(scheduleRecord);
			if (piItemProductId == null || piItemProductId.isVirtualHU()) // there is no packing instruction for TUs to put the CUs into
			{
				newHURecords = newCURecords;
			}
			else
			{
				final I_M_HU_PI_Item_Product huPIItemProduct = hupiItemProductDAO.getRecordById(piItemProductId);
				newHURecords = new ArrayList<>();
				for (final I_M_HU newCU : newCURecords)
				{
					newHURecords.addAll(huTransformService.cuToNewTUs(newCU,
							null/*consume the complete CU*/,
							huPIItemProduct,
							true /*assume the packing materials that we might use is ours, not the customer's*/));
				}
			}
		}
		else
		{
			newHURecords = newCURecords;
		}
		return newHURecords;
	}

	private Quantity extractQtyOfHU(
			final I_M_HU sourceHURecord,
			final ProductId productId,
			final I_C_UOM uomRecord)
	{
		return handlingUnitsBL
				.getStorageFactory()
				.getStorage(sourceHURecord)
				.getQuantity(productId, uomRecord);
	}

	private Collection<? extends ShipmentScheduleWithHU> createAndValidateCandidatesForPick(
			@NonNull final ShipmentScheduleWithHUFactory factory,
			final I_M_ShipmentSchedule schedule,
			final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse,
			final boolean isFailIfNoPickedHUs)
	{
		final Collection<? extends ShipmentScheduleWithHU> candidatesForPick = createShipmentScheduleWithHUForPick(schedule, factory, quantityTypeToUse);

		if (Check.isEmpty(candidatesForPick) && isFailIfNoPickedHUs)
		{
			// the parameter insists that we use qtyPicked records, but there aren't any
			// => nothing to do, basically

			// If we got no qty picked records just because they were already delivered,
			// don't fail this workpackage but just log the issue (task 09048)
			final boolean wereDelivered = shipmentScheduleAllocDAO.retrieveOnShipmentLineRecordsQuery(schedule).create().anyMatch();
			if (wereDelivered)
			{
				Loggables.withLogger(logger, Level.INFO).addLog("Skipped shipment schedule because it was already delivered: {}", schedule);
				return Collections.emptyList();
			}
			Loggables.withLogger(logger, Level.WARN).addLog("Shipment schedule has no I_M_ShipmentSchedule_QtyPicked records (or these records have inactive HUs); M_ShipmentSchedule={}", schedule);
			throw new AdempiereException(MSG_NoQtyPicked);
		}

		return candidatesForPick;
	}

	private Collection<? extends ShipmentScheduleWithHU> createShipmentScheduleWithHUForPick(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final ShipmentScheduleWithHUFactory factory,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityType)
	{
		List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = retrieveQtyPickedRecords(schedule);
		if (qtyPickedRecords.isEmpty())
		{
			return Collections.emptyList();

		}

		final List<ShipmentScheduleWithHU> candidatesForPick = new ArrayList<>();

		//
		// Create necessary TUs/LUs (if any)
		createTUsLUsIfNeeded(schedule, quantityType);

		// retrieve the qty picked entries again, some new ones might have been created on LU creation
		qtyPickedRecords = retrieveQtyPickedRecords(schedule);

		//
		// Iterate all QtyPicked records and create candidates from them

		for (final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			final I_M_ShipmentSchedule_QtyPicked qtyPickedRecordHU = InterfaceWrapperHelper.create(qtyPickedRecord, I_M_ShipmentSchedule_QtyPicked.class);

			// guard: Skip inactive records.
			if (!qtyPickedRecordHU.isActive())
			{
				Loggables.withLogger(logger, Level.INFO).addLog("Skipped inactive qtyPickedRecordHU={}", qtyPickedRecordHU);
				continue;
			}

			//
			// Create ShipmentSchedule+HU candidate and add it to our list
			final ShipmentScheduleWithHU candidate = //
					factory.ofQtyPickedRecord(qtyPickedRecordHU, quantityType);
			candidatesForPick.add(candidate);
		}

		return candidatesForPick;
	}

	/**
	 * @return records that do not have an {@link I_M_InOutLine} assigned to them and that also have
	 * <ul>
	 * <li>either no HU assigned to them, or</li>
	 * <li>HUs which are already picked or shipped assigned to them</li>
	 * </ul>
	 * <p>
	 * Hint: also take a look at {@link #isPickedOrShippedOrNoHU(I_M_ShipmentSchedule_QtyPicked)}.
	 */
	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(final I_M_ShipmentSchedule schedule)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(schedule.getC_Order_ID());
		if (orderId != null)
		{
			final Optional<PickingJob> pickingJobForShipmentSchedule = getPickingJobByOrderId(orderId);
			if (pickingJobForShipmentSchedule.isPresent() && pickingJobInReview(pickingJobForShipmentSchedule.get()))
			{
				Loggables.withLogger(logger, Level.INFO).addLog("Skipped shipmentSchedule={}, as there's an associated picking job that's not approved", schedule.getM_ShipmentSchedule_ID());
				return Collections.emptyList();
			}
		}

		final List<I_M_ShipmentSchedule_QtyPicked> unshippedHUs = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.filter(this::isPickedOrShippedOrNoHU)
				.collect(ImmutableList.toImmutableList());

		// if we have an "undone" picking, i.e. positive and negative values sum up to zero, then return an empty list
		// this supports the case that something went wrong with picking, and the user needs to get out the shipment asap
		final BigDecimal qtySumOfUnshippedHUs = unshippedHUs.stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (qtySumOfUnshippedHUs.signum() <= 0)
		{
			return ImmutableList.of();
		}

		return unshippedHUs;
	}

	private Collection<? extends ShipmentScheduleWithHU> createShipmentScheduleWithHUForSplitShipments(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final ShipmentScheduleWithHUFactory factory)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final List<ShipmentScheduleSplit> shipmentScheduleSplits = shipmentScheduleSplitService.getByShipmentScheduleId(shipmentScheduleId);
		if (shipmentScheduleSplits.isEmpty())
		{
			throw new AdempiereException("No shipment schedule split records defined");
		}

		return shipmentScheduleSplits.stream()
				.map(split -> factory.ofSplit(schedule, split))
				.collect(ImmutableList.toImmutableList());
	}

	private Optional<PickingJob> getPickingJobByOrderId(@NonNull final OrderId orderId)
	{
		final PickingJobLoaderSupportingServices loadingSupportingServices = pickingJobLoaderSupportingServicesFactory.createLoaderSupportingServices();
		return pickingJobRepository.getDraftBySalesOrderId(orderId, loadingSupportingServices);
	}

	/**
	 * @param pickingJob the picking job to check
	 * @return true if the picking job needs to be approved, but no approval is yet given.
	 */
	private boolean pickingJobInReview(final PickingJob pickingJob)
	{
		return pickingJob.isPickingReviewRequired() && pickingJob.isReadyToReview() && !pickingJob.isApproved();
	}

	/**
	 * Create TUs for picked VHUs if needed. Create LUs for given shipment schedule.
	 * <p>
	 * After calling this method, all our CUs/TU from QtyPicked records shall have an LU.
	 */
	private void createTUsLUsIfNeeded(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityType)
	{
		// Don't generate any HUs if we are in QuickShipment mode,
		// because in that mode we are creating shipments without and HUs

		// in case of using the isUseQtyPicked, create the LUs
		final boolean onlyUseQtyToDeliver = quantityType.isOnlyUseToDeliver();

		if (HUConstants.isQuickShipment() && onlyUseQtyToDeliver)
		{
			return;
		}

		if (sysConfigBL.getBooleanValue(SYSCONFIG_PACK_CUS_TO_TU, true))
		{
			aggregateCUsToTUs(ImmutableSet.of(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID())));
		}

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);

		//
		// Case: this shipment schedule line was not picked at all
		// => generate LUs for the whole Qty
		if (qtyPickedRecords.isEmpty())
		{
			createLUsForQtyToDeliver(schedule);
		}
		//
		// Case: this shipment schedule line was at least partial picked
		// => take all TUs which does not have an LU and add them to LUs
		else
		{
			createLUsForTUs(schedule, qtyPickedRecords);
		}
	}

	/**
	 * Returns {@code true} if there is either no HU assigned to the given {@code schedQtyPicked} or if that HU is either picked or shipped.
	 * If you don't see why it could possibly be already shipped, please take a look at issue <a href="https://github.com/metasfresh/metasfresh/issues/1174">#1174</a>.
	 */
	private boolean isPickedOrShippedOrNoHU(final I_M_ShipmentSchedule_QtyPicked schedQtyPicked)
	{
		final I_M_HU huToVerify;
		if (schedQtyPicked.getVHU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getVHU();
		}
		else if (schedQtyPicked.getM_TU_HU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getM_TU_HU();
		}
		else if (schedQtyPicked.getM_LU_HU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getM_LU_HU();
		}
		else
		{
			return true;
		}

		if (huToVerify == null)
		{
			return true; // this *might* happen with our "minidumps" there we don't have the HU data in our DB
		}

		final String huStatus = huToVerify.getHUStatus();
		return X_M_HU.HUSTATUS_Picked.equals(huStatus) || X_M_HU.HUSTATUS_Shipped.equals(huStatus);
	}

	/**
	 * Take all TUs from <code>qtyPickedRecords</code> which does not have an LU and create/add them to LUs
	 */
	private void createLUsForTUs(final I_M_ShipmentSchedule schedule, final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords)
	{
		//
		// Create HUContext from "schedule" because we want to get the Ctx and TrxName from there
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(schedule);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		//
		// Create our LU Loader. This will help us to aggregate TUs on corresponding LUs
		final LULoader luLoader = new LULoader(huContext);

		//
		// Iterate QtyPicked records
		for (final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			// refresh because it might be that a previous LU creation to update this record too
			InterfaceWrapperHelper.refresh(qtyPickedRecord);

			// Skip inactive lines
			if (!qtyPickedRecord.isActive())
			{
				continue;
			}

			// Skip lines without TUs
			if (qtyPickedRecord.getM_TU_HU_ID() <= 0)
			{
				continue;
			}

			// Skip lines with ZERO Qty
			if (qtyPickedRecord.getQtyPicked().signum() == 0)
			{
				continue;
			}

			// Skip lines which already have LUs created
			if (qtyPickedRecord.getM_LU_HU_ID() > 0)
			{
				continue;
			}

			final I_M_HU tuHU = qtyPickedRecord.getM_TU_HU();

			// 4507
			// make sure the TU from qtyPicked is a real TU
			if (!handlingUnitsBL.isTransportUnit(tuHU))
			{
				continue;
			}

			// skip LU creation when there is no LU Packing Instruction Version for this TU, eg.
			// - Paloxe, as it can be placed directly in a truck, without a Pallet
			// - any other TUs/LUs which are misconfigured (side effect)
			{
				final I_M_HU_PI_Version tuPIVersion = handlingUnitsBL.getEffectivePIVersion(tuHU);
				final I_M_HU_PI tuPI = handlingUnitsDAO.getPackingInstructionById(HuPackingInstructionsId.ofRepoId(tuPIVersion.getM_HU_PI_ID()));
				final I_M_HU_PI_Item luPIItem = handlingUnitsDAO.retrieveDefaultParentPIItem(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, null);
				if (luPIItem == null)
				{
					continue;
				}
			}

			luLoader.addTU(tuHU);

			// NOTE: after TU was added to an LU we expect this qtyPickedRecord to be updated and M_LU_HU_ID to be set
			// Also, if there are more than one QtyPickedRecords for tuHU, all those shall be updated
			// see de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleHUTrxListener.huParentChanged(I_M_HU, I_M_HU_Item)
		}

		luLoader.close();
	}

	/**
	 * Create LUs for the whole QtyToDeliver from shipment schedule.
	 * <p>
	 * Note: this method is not checking current QtyPicked records (because we assume there are none).
	 */
	private void createLUsForQtyToDeliver(final I_M_ShipmentSchedule schedule)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(schedule);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		//
		// Create Allocation Request: whole Qty to Deliver
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());
		final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(schedule);
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				productId,
				qtyToDeliver,
				SystemTime.asZonedDateTime(),
				schedule,      // reference model
				false // forceQtyAllocation
		);

		//
		// Create Allocation Source & Destination
		final IAllocationSource allocationSource = createAllocationSource(schedule);
		final ILUTUProducerAllocationDestination allocationDestination = createLUTUProducerDestination(schedule);

		//
		// Execute transfer
		final IAllocationResult result = HULoader.of(allocationSource, allocationDestination)
				.setAllowPartialLoads(false)
				.setAllowPartialUnloads(false)
				.load(request);
		Check.assume(result.isCompleted(), "Result shall be completed: {}", result);

		// NOTE: at this point we shall have QtyPicked records with M_LU_HU_ID set
	}

	private IAllocationSource createAllocationSource(final I_M_ShipmentSchedule schedule)
	{
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		return new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);
	}

	@NonNull
	ILUTUProducerAllocationDestination createLUTUProducerDestination(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.deriveM_HU_LUTU_Configuration(schedule);
		lutuConfigurationFactory.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		//
		// Make sure we have our LU PI configured
		if (luProducerDestination.isNoLU())
		{
			throw new HUException("No Loading Unit found for TU: " + luProducerDestination.getTUPI()
					+ "\n@M_ShipmentSchedule_ID@: " + schedule
					+ "\n@Destination@: " + luProducerDestination);
		}

		return luProducerDestination;
	}

	private ImmutableList<ShipmentScheduleWithHU> createCandidatesForSched(
			@NonNull final ShipmentScheduleWithHUService.CreateCandidatesRequest.CreateCandidatesRequestBuilder requestBuilder,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.isProcessed())
		{
			return ImmutableList.of();
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		try (final MDC.MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleId(shipmentScheduleId))
		{
			final CreateCandidatesRequest request = requestBuilder
					.shipmentScheduleId(shipmentScheduleId)
					.build();

			return createShipmentSchedulesWithHU(request);
		}
	}

	@NonNull
	private List<ShipmentScheduleWithHU> getAlreadyPickedOnTheFlyButNotDelivered(@NonNull final ShipmentScheduleId shipmentScheduleId, @NonNull final ShipmentScheduleWithHUFactory factory)
	{
		return shipmentScheduleAllocDAO.retrievePickedOnTheFlyAndNotDelivered(shipmentScheduleId, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.map(factory::ofQtyPickedRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Quantity getQtyToAllocate(@NonNull final List<ShipmentScheduleWithHU> alreadyAllocatedHUs, @NonNull final Quantity qtyToDeliver)
	{
		return alreadyAllocatedHUs.stream()
				.map(ShipmentScheduleWithHU::getQtyPicked)
				.reduce(qtyToDeliver, Quantity::subtract);
	}

	public void aggregateCUsToTUs(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		ShipmentSchedulesCUsToTUsAggregator.builder()
				.huShipmentScheduleBL(huShipmentScheduleBL)
				.shipmentScheduleAllocDAO(shipmentScheduleAllocDAO)
				.handlingUnitsBL(handlingUnitsBL)
				//
				.shipmentScheduleIds(shipmentScheduleIds)
				//
				.build().execute();
	}

	@NonNull
	private ImmutableList<ShipmentScheduleWithHU> pickHUsOnTheFlyIfPossible(
			@NonNull final I_M_ShipmentSchedule scheduleRecord,
			@NonNull final Quantity qtyToDeliver,
			final boolean pickAccordingToPackingInstruction,
			@NonNull final ShipmentScheduleWithHUFactory factory)
	{
		final HUsToPickOnTheFly hUsToPickOnTheFly = retrievePickAvailableHUsOntheFly(factory.getHuContext());
		return pickHUsOnTheFly(scheduleRecord,
				qtyToDeliver,
				pickAccordingToPackingInstruction,
				factory,
				hUsToPickOnTheFly);
	}

	@NonNull
	private List<I_M_HU> retrieveAnyMatchingHUs(final @NonNull I_M_ShipmentSchedule scheduleRecord)
	{
		final PackageableQuery query = PackageableQuery.builder()
				.onlyShipmentScheduleIds(ImmutableSet.of(ShipmentScheduleId.ofRepoId(scheduleRecord.getM_ShipmentSchedule_ID())))
				.build();

		final PackageableList items = packagingDAO.stream(query).collect(PackageableList.collect());

		final PickingConfigV2 pickingConfig = pickingConfigRepo.getPickingConfig();
		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(items)
				.considerAttributes(pickingConfig.isConsiderAttributes())
				.build());

		final ImmutableList<HuId> topLevelHuIds = plan.getSortedPickFromTopLevelHUIds();
		if (topLevelHuIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return handlingUnitsDAO.getByIds(topLevelHuIds);
	}

	@NonNull
	private I_M_HU createHUForRemainingQty(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final Quantity remainingQtyToAllocate)
	{
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIdsOrNull(schedule.getC_Order_ID(), schedule.getC_OrderLine_ID());

		final FlatrateTermId contractId = modularContractProvider.getSinglePurchaseContractsForSalesOrderLineOrNull(orderAndLineId);

		final CreateVirtualInventoryWithQtyReq req = CreateVirtualInventoryWithQtyReq.builder()
				.clientId(ClientId.ofRepoId(schedule.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(schedule.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(schedule.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(schedule.getM_Product_ID()))
				.qty(remainingQtyToAllocate)
				.movementDate(SystemTime.asZonedDateTime())
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNull(schedule.getM_AttributeSetInstance_ID()))
				.modularContractId(contractId)
				.build();

		final HuId createdHuId = inventoryService.createInventoryForMissingQty(req);

		return handlingUnitsDAO.getById(createdHuId);
	}
}
