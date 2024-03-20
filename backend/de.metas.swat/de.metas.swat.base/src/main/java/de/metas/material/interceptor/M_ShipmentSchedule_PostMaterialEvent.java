package de.metas.material.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineFactory;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.OldShipmentScheduleData;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDetail;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Shipment Schedule module: M_ShipmentSchedule
 *
 * @author tsa
 */
@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule_PostMaterialEvent
{
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	private final PostMaterialEventService postMaterialEventService;
	private final ShipmentScheduleReferencedLineFactory referencedLineFactory;
	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final ReplenishInfoRepository replenishInfoRepository;

	public M_ShipmentSchedule_PostMaterialEvent(
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final ShipmentScheduleReferencedLineFactory referencedLineFactory,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final ReplenishInfoRepository replenishInfoRepository)
	{
		this.postMaterialEventService = postMaterialEventService;
		this.referencedLineFactory = referencedLineFactory;
		this.productDescriptorFactory = productDescriptorFactory;
		this.replenishInfoRepository = replenishInfoRepository;
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* before delete because we still need the M_ShipmentSchedule_ID */
	}, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_IsClosed,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override,
			I_M_ShipmentSchedule.COLUMNNAME_QtyReserved,
			I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID,
			I_M_ShipmentSchedule.COLUMNNAME_AD_Org_ID,
			I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,
			I_M_ShipmentSchedule.COLUMNNAME_PreparationDate,
			I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID,
			I_M_ShipmentSchedule.COLUMNNAME_IsActive /* IsActive=N shall be threaded like a deletion */ })
	public void createAndFireEvent(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final ModelChangeType timing)
	{
		final AbstractShipmentScheduleEvent event = createShipmentScheduleEvent(schedule, timing);

		postMaterialEventService.enqueueEventAfterNextCommit(event);
	}

	@VisibleForTesting
	@NonNull
	AbstractShipmentScheduleEvent createShipmentScheduleEvent(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final ModelChangeType timing)
	{
		final boolean created = timing.isNew() || ModelChangeUtil.isJustActivated(shipmentSchedule);
		if (created)
		{
			return createCreatedEvent(shipmentSchedule);
		}

		final boolean deleted = timing.isDelete() || ModelChangeUtil.isJustDeactivated(shipmentSchedule);
		if (deleted)
		{
			return createDeletedEvent(shipmentSchedule);
		}

		return createUpdatedEvent(shipmentSchedule);
	}

	@NonNull
	private ShipmentScheduleCreatedEvent createCreatedEvent(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final MaterialDescriptor materialDescriptor = //
				createMaterialDescriptor(shipmentSchedule);

		final DocumentLineDescriptor documentLineDescriptor = //
				referencedLineFactory.createFor(shipmentSchedule)
						.getDocumentLineDescriptor();

		final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository
				.getBy(materialDescriptor)
				.toMinMaxDescriptor();

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID());

		return ShipmentScheduleCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(clientAndOrgId))
				.materialDescriptor(materialDescriptor)
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
												.orderedQuantity(shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule))
												.reservedQuantity(shipmentSchedule.getQtyReserved())
												.reservedQuantityDelta(shipmentSchedule.getQtyReserved())
												.orderedQuantityDelta(shipmentSchedule.getQtyOrdered())
												// dev-note: no old data
												.oldShipmentScheduleData(null)
												.build())
				.shipmentScheduleId(shipmentSchedule.getM_ShipmentSchedule_ID())
				.documentLineDescriptor(documentLineDescriptor)
				.minMaxDescriptor(minMaxDescriptor)
				.build();
	}

	@NonNull
	private ShipmentScheduleUpdatedEvent createUpdatedEvent(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(shipmentSchedule);

		final DocumentLineDescriptor documentLineDescriptor = referencedLineFactory.createFor(shipmentSchedule)
				.getDocumentLineDescriptor();

		final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository
				.getBy(materialDescriptor)
				.toMinMaxDescriptor();

		final ShipmentScheduleUpdatedEvent.ShipmentScheduleUpdatedEventBuilder shipmentScheduleUpdatedEventBuilder = ShipmentScheduleUpdatedEvent.builder();

		shipmentScheduleUpdatedEventBuilder
				.eventDescriptor(EventDescriptor.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID()))
				.materialDescriptor(materialDescriptor)
				.shipmentScheduleId(shipmentSchedule.getM_ShipmentSchedule_ID())
				.documentLineDescriptor(documentLineDescriptor)
				.minMaxDescriptor(minMaxDescriptor);

		setShipmentScheduleDetail(shipmentScheduleUpdatedEventBuilder, materialDescriptor, shipmentSchedule);

		return shipmentScheduleUpdatedEventBuilder.build();
	}

	private void setShipmentScheduleDetail(
			@NonNull final ShipmentScheduleUpdatedEvent.ShipmentScheduleUpdatedEventBuilder shipmentScheduleUpdatedEventBuilder,
			@NonNull final MaterialDescriptor currentMaterialDescriptor,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule oldShipmentSchedule = toOldValues(shipmentSchedule);
		final MaterialDescriptor oldMaterialDescriptor = createMaterialDescriptor(oldShipmentSchedule);

		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule);
		final ShipmentScheduleDetail.ShipmentScheduleDetailBuilder shipmentScheduleDetailBuilder = ShipmentScheduleDetail.builder()
				.orderedQuantity(qtyOrdered)
				.reservedQuantity(shipmentSchedule.getQtyReserved());

		if (targetMaterialDescriptorChanged(currentMaterialDescriptor, oldMaterialDescriptor))
		{
			shipmentScheduleUpdatedEventBuilder
					.shipmentScheduleDetail(shipmentScheduleDetailBuilder
													.reservedQuantityDelta(shipmentSchedule.getQtyReserved())
													.orderedQuantityDelta(qtyOrdered)
													.oldShipmentScheduleData(buildOldShipmentScheduleData(oldMaterialDescriptor, oldShipmentSchedule))
													.build());
		}
		else
		{
			final BigDecimal oldQtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(oldShipmentSchedule);
			shipmentScheduleUpdatedEventBuilder
					.shipmentScheduleDetail(shipmentScheduleDetailBuilder
													.reservedQuantityDelta(shipmentSchedule.getQtyReserved().subtract(oldShipmentSchedule.getQtyReserved()))
													.orderedQuantityDelta(qtyOrdered.subtract(oldQtyOrdered))
													.build());
		}
	}

	@NonNull
	private OldShipmentScheduleData buildOldShipmentScheduleData(
			@NonNull final MaterialDescriptor oldMaterialDescriptor,
			@NonNull final I_M_ShipmentSchedule oldShipmentSchedule)
	{
		final BigDecimal oldQtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(oldShipmentSchedule);
		return OldShipmentScheduleData.builder()
				.oldMaterialDescriptor(oldMaterialDescriptor)
				.oldReservedQuantity(oldShipmentSchedule.getQtyReserved())
				.oldOrderedQuantity(oldQtyOrdered)
				.build();
	}

	private boolean targetMaterialDescriptorChanged(
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final MaterialDescriptor oldMaterialDescriptor)
	{
		return !materialDescriptor.getStorageAttributesKey().equals(oldMaterialDescriptor.getStorageAttributesKey())
				|| !materialDescriptor.getDate().equals(oldMaterialDescriptor.getDate())
				|| materialDescriptor.getProductId() != oldMaterialDescriptor.getProductId()
				|| !Objects.equals(materialDescriptor.getWarehouseId(), oldMaterialDescriptor.getWarehouseId());
	}

	@VisibleForTesting
	I_M_ShipmentSchedule toOldValues(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return InterfaceWrapperHelper.createOld(shipmentSchedule, I_M_ShipmentSchedule.class);
	}

	@NonNull
	private ShipmentScheduleDeletedEvent createDeletedEvent(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(shipmentSchedule);

		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule);
		return ShipmentScheduleDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID()))
				.materialDescriptor(materialDescriptor)
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
												.orderedQuantity(qtyOrdered)
												.orderedQuantityDelta(qtyOrdered)
												.reservedQuantity(shipmentSchedule.getQtyReserved())
												.reservedQuantityDelta(shipmentSchedule.getQtyReserved())
												.build())
				.shipmentScheduleId(shipmentSchedule.getM_ShipmentSchedule_ID())
				.build();
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal orderedQuantity = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule);
		final ZonedDateTime preparationDate = shipmentScheduleEffectiveBL.getPreparationDate(shipmentSchedule);
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(shipmentSchedule);

		return MaterialDescriptor.builder()
				.date(preparationDate.toInstant())
				.productDescriptor(productDescriptor)
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule))
				.customerId(shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule))
				.quantity(orderedQuantity.subtract(getDeliveredQtyFromHUs(shipmentSchedule)))
				.build();
	}

	@NonNull
	private BigDecimal getDeliveredQtyFromHUs(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPicked = shipmentScheduleAllocDAO
				.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		return shipmentScheduleQtyPicked
				.stream()
				.filter(I_M_ShipmentSchedule_QtyPicked::isActive)
				.filter(I_M_ShipmentSchedule_QtyPicked::isProcessed)
				.filter(qtyPicked -> qtyPicked.getM_InOutLine_ID() > 0)
				//dev-note: we only care about "real" qty movements
				// getValueOptional(qtyPicked, "VHU_ID") - it's an ugly workaround to avoid a circular dependency with de.metas.handlingunits.base
				.filter(qtyPicked -> InterfaceWrapperHelper.getValueOptional(qtyPicked, "VHU_ID").isPresent())
				.map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
}
