package de.metas.material.cockpit.view.eventhandler;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.DetailDataRecordIdentifier;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.detailrecord.DetailDataRequestHandler;
import de.metas.material.cockpit.view.detailrecord.InsertDetailRequest;
import de.metas.material.cockpit.view.detailrecord.InsertDetailRequest.InsertDetailRequestBuilder;
import de.metas.material.cockpit.view.detailrecord.RemoveDetailRequest;
import de.metas.material.cockpit.view.detailrecord.UpdateDetailRequest;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
<<<<<<< HEAD
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
=======
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.OldShipmentScheduleData;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
<<<<<<< HEAD
=======
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
<<<<<<< HEAD
=======
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.impl.ProductBOM;
import org.eevolution.api.impl.ProductBOMRequest;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.time.ZoneId;
import java.util.Collection;
=======
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * metasfresh-material-cockpit
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

@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class ShipmentScheduleEventHandler
		implements MaterialEventHandler<AbstractShipmentScheduleEvent>
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleEventHandler.class);

	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final DetailDataRequestHandler detailRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
<<<<<<< HEAD
	
=======
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final static String SYSCFG_BOM_SUPPORT = "de.metas.ui.web.material.cockpit.field.QtyOrdered_SalesOrder_AtDate.BOMSupport";

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public ShipmentScheduleEventHandler(
			@NonNull final MainDataRequestHandler dataUpdateRequestHandler,
			@NonNull final DetailDataRequestHandler detailRequestHandler)
	{
		this.detailRequestHandler = detailRequestHandler;
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends AbstractShipmentScheduleEvent>> getHandledEventType()
	{
		return ImmutableList.of(
				ShipmentScheduleCreatedEvent.class,
				ShipmentScheduleUpdatedEvent.class,
				ShipmentScheduleDeletedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final AbstractShipmentScheduleEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final AbstractShipmentScheduleEvent event)
	{
		final OrgId orgId = event.getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
<<<<<<< HEAD
		
		final MaterialDescriptor materialDescriptor = event.getMaterialDescriptor();
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.createForMaterial(materialDescriptor, timeZone);

		createAndHandleMainDataRequest(event, identifier);
		createAndHandleDetailRequest(event, identifier);
=======

		final MaterialDescriptor materialDescriptor = event.getMaterialDescriptor();
		final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.createForMaterial(materialDescriptor, timeZone);

		createAndHandleMainDataRequest(event, identifier, timeZone);
		createAndHandleDetailRequest(event, identifier, timeZone);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private void createAndHandleMainDataRequest(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent,
<<<<<<< HEAD
			@NonNull final MainDataRecordIdentifier identifier)
	{
		if (shipmentScheduleEvent.getOrderedQuantityDelta().signum() == 0
				&& shipmentScheduleEvent.getReservedQuantityDelta().signum() == 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Skipping this event because is has both orderedQuantityDelta and reservedQuantityDelta = zero");
			return;
		}

		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.orderedSalesQty(shipmentScheduleEvent.getOrderedQuantityDelta())
				.qtyDemandSalesOrder(shipmentScheduleEvent.getReservedQuantityDelta())
				.build();
		dataUpdateRequestHandler.handleDataUpdateRequest(request);
=======
			@NonNull final MainDataRecordIdentifier identifier,
			@NonNull final ZoneId timeZone)
	{
		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.qtyDemandSalesOrder(shipmentScheduleEvent.getReservedQuantityDelta())
				.orderedSalesQty(shipmentScheduleEvent.getOrderedQuantityDelta())
				.build();
		dataUpdateRequestHandler.handleDataUpdateRequest(request);

		final OldShipmentScheduleData oldShipmentScheduleData = shipmentScheduleEvent.getOldShipmentScheduleData();
		if (oldShipmentScheduleData != null)
		{
			final MainDataRecordIdentifier oldIdentifier = MainDataRecordIdentifier.createForMaterial(oldShipmentScheduleData.getOldMaterialDescriptor(), timeZone);

			createAndHandleMainDataRequestForOldValues(oldShipmentScheduleData, oldIdentifier);
		}

		if (sysConfigBL.getBooleanValue(SYSCFG_BOM_SUPPORT, true))
		{
			final ProductBOMRequest bomRequest = ProductBOMRequest.builder()
					.productDescriptor(identifier.getProductDescriptor())
					.date(identifier.getDate())
					.build();
			final Optional<ProductBOM> productBOMOptional = productBOMBL.retrieveValidProductBOM(bomRequest);
			if (!productBOMOptional.isPresent())
			{
				return;
			}

			final ProductBOM productBOM = productBOMOptional.get();
			final I_C_UOM uom = productBL.getStockUOM(identifier.getProductDescriptor().getProductId());
			final Quantity qty = Quantity.of(shipmentScheduleEvent.getOrderedQuantityDelta(), uom);
			final Map<ProductDescriptor, Quantity> components = productBOMBL.calculateRequiredQtyInStockUOMForComponents(qty, productBOM);
			for (final Map.Entry<ProductDescriptor, Quantity> component : components.entrySet())
			{
				final MainDataRecordIdentifier bomIdentifier = MainDataRecordIdentifier.builder()
						.productDescriptor(component.getKey())
						.date(identifier.getDate())
						.build();
				final UpdateMainDataRequest requestForBOM = UpdateMainDataRequest.builder()
						.identifier(bomIdentifier)
						.orderedSalesQty(component.getValue().toBigDecimal())
						.build();
				dataUpdateRequestHandler.handleDataUpdateRequest(requestForBOM);
			}
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private void createAndHandleDetailRequest(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent,
<<<<<<< HEAD
			@NonNull final MainDataRecordIdentifier identifier)
=======
			@NonNull final MainDataRecordIdentifier identifier,
			@NonNull final ZoneId timeZone)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final DetailDataRecordIdentifier detailIdentifier = DetailDataRecordIdentifier.createForShipmentSchedule(
				identifier,
				shipmentScheduleEvent.getShipmentScheduleId());

		if (shipmentScheduleEvent instanceof ShipmentScheduleCreatedEvent)
		{
<<<<<<< HEAD
			final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent = (ShipmentScheduleCreatedEvent)shipmentScheduleEvent;
			createAndHandleAddDetailRequest(detailIdentifier, shipmentScheduleCreatedEvent);
		}
		else if (shipmentScheduleEvent instanceof ShipmentScheduleUpdatedEvent)
		{
			detailRequestHandler
					.handleUpdateDetailRequest(UpdateDetailRequest.builder()
							.detailDataRecordIdentifier(detailIdentifier)
							.qtyOrdered(shipmentScheduleEvent.getMaterialDescriptor().getQuantity())
							.qtyReserved(shipmentScheduleEvent.getReservedQuantity())
							.build());
=======
			createAndHandleAddDetailRequest(detailIdentifier, shipmentScheduleEvent);
		}
		else if (shipmentScheduleEvent instanceof ShipmentScheduleUpdatedEvent)
		{
			createAndHandleUpdateDetailRequest(detailIdentifier, shipmentScheduleEvent, timeZone);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else if (shipmentScheduleEvent instanceof ShipmentScheduleDeletedEvent)
		{
			final int deletedCount = detailRequestHandler
					.handleRemoveDetailRequest(RemoveDetailRequest.builder()
<<<<<<< HEAD
							.detailDataRecordIdentifier(detailIdentifier)
							.build());
=======
													   .detailDataRecordIdentifier(detailIdentifier)
													   .build());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			Loggables.withLogger(logger, Level.DEBUG).addLog("Deleted {} detail records", deletedCount);
		}
	}

<<<<<<< HEAD
	private void createAndHandleAddDetailRequest(
			@NonNull final DetailDataRecordIdentifier identifier,
			@NonNull final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent)
	{
		final DocumentLineDescriptor documentLineDescriptor = //
				shipmentScheduleCreatedEvent
=======
	private void createAndHandleUpdateDetailRequest(
			@NonNull final DetailDataRecordIdentifier detailIdentifier,
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent,
			@NonNull final ZoneId timeZone)
	{
		final OldShipmentScheduleData oldShipmentScheduleData = shipmentScheduleEvent.getOldShipmentScheduleData();

		if (oldShipmentScheduleData != null)
		{
			final MainDataRecordIdentifier oldIdentifier = MainDataRecordIdentifier.createForMaterial(oldShipmentScheduleData.getOldMaterialDescriptor(), timeZone);

			createAndHandleRemoveDetailRequest(oldIdentifier, shipmentScheduleEvent);
			createAndHandleAddDetailRequest(detailIdentifier, shipmentScheduleEvent);
		}
		else
		{
			detailRequestHandler
					.handleUpdateDetailRequest(UpdateDetailRequest.builder()
													   .detailDataRecordIdentifier(detailIdentifier)
													   .qtyOrdered(shipmentScheduleEvent.getOrderedQuantity())
													   .qtyReserved(shipmentScheduleEvent.getReservedQuantity())
													   .build());
		}
	}

	private void createAndHandleRemoveDetailRequest(
			@NonNull final MainDataRecordIdentifier oldIdentifier,
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent)
	{
		final DetailDataRecordIdentifier oldDetailIdentifier = DetailDataRecordIdentifier.createForShipmentSchedule(
				oldIdentifier,
				shipmentScheduleEvent.getShipmentScheduleId());

		final int deletedCount = detailRequestHandler
				.handleRemoveDetailRequest(RemoveDetailRequest.builder()
												   .detailDataRecordIdentifier(oldDetailIdentifier)
												   .build());
		Loggables.withLogger(logger, Level.DEBUG).addLog("Deleted {} detail records", deletedCount);
	}

	private void createAndHandleAddDetailRequest(
			@NonNull final DetailDataRecordIdentifier identifier,
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent)
	{
		final DocumentLineDescriptor documentLineDescriptor = //
				shipmentScheduleEvent
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						.getDocumentLineDescriptor();

		final InsertDetailRequestBuilder addDetailsRequest = InsertDetailRequest.builder()
				.detailDataRecordIdentifier(identifier)
<<<<<<< HEAD
				.qtyOrdered(shipmentScheduleCreatedEvent.getMaterialDescriptor().getQuantity())
				.qtyReserved(shipmentScheduleCreatedEvent.getReservedQuantity());
=======
				.qtyOrdered(shipmentScheduleEvent.getOrderedQuantity())
				.qtyReserved(shipmentScheduleEvent.getReservedQuantity());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		if (documentLineDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = //
					(OrderLineDescriptor)documentLineDescriptor;
			addDetailsRequest
					.orderId(orderLineDescriptor.getOrderId())
					.orderLineId(orderLineDescriptor.getOrderLineId())
					.bPartnerId(orderLineDescriptor.getOrderBPartnerId())
					.docTypeId(orderLineDescriptor.getDocTypeId());
		}
		else if (documentLineDescriptor instanceof SubscriptionLineDescriptor)
		{
			final SubscriptionLineDescriptor subscriptionLineDescriptor = //
					(SubscriptionLineDescriptor)documentLineDescriptor;
			addDetailsRequest
					.subscriptionId(subscriptionLineDescriptor.getFlatrateTermId())
					.subscriptionLineId(subscriptionLineDescriptor.getSubscriptionProgressId())
					.bPartnerId(subscriptionLineDescriptor.getSubscriptionBillBPartnerId());
		}
		else
		{
			Check.errorIf(true,
<<<<<<< HEAD
					"The DocumentLineDescriptor has an unexpected type; documentLineDescriptor={}", documentLineDescriptor);
=======
						  "The DocumentLineDescriptor has an unexpected type; documentLineDescriptor={}", documentLineDescriptor);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		detailRequestHandler.handleInsertDetailRequest(addDetailsRequest.build());
	}
<<<<<<< HEAD
=======

	private void createAndHandleMainDataRequestForOldValues(
			@NonNull final OldShipmentScheduleData oldShipmentScheduleData,
			@NonNull final MainDataRecordIdentifier identifier)
	{
		final BigDecimal oldReservedQuantity = oldShipmentScheduleData.getOldReservedQuantity();
		final BigDecimal oldOrderedQuantity = oldShipmentScheduleData.getOldOrderedQuantity();

		if (oldReservedQuantity.signum() == 0 && oldOrderedQuantity.signum() == 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Skipping this event because it has oldReservedQuantity and oldOrderedQuantity = zero");
			return;
		}

		final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
				.identifier(identifier)
				.qtyDemandSalesOrder(oldReservedQuantity.negate())
				.orderedSalesQty(oldOrderedQuantity.negate())
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(request);

		if (sysConfigBL.getBooleanValue(SYSCFG_BOM_SUPPORT, true))
		{
			final ProductBOMRequest bomRequest = ProductBOMRequest.builder()
					.productDescriptor(identifier.getProductDescriptor())
					.date(identifier.getDate())
					.build();
			final Optional<ProductBOM> productBOMOptional = productBOMBL.retrieveValidProductBOM(bomRequest);
			if (!productBOMOptional.isPresent())
			{
				return;
			}

			final ProductBOM productBOM = productBOMOptional.get();
			final I_C_UOM uom = productBL.getStockUOM(identifier.getProductDescriptor().getProductId());
			final Quantity qty = Quantity.of(oldOrderedQuantity.negate(), uom);
			final Map<ProductDescriptor, Quantity> components = productBOMBL.calculateRequiredQtyInStockUOMForComponents(qty, productBOM);
			for (final Map.Entry<ProductDescriptor, Quantity> component : components.entrySet())
			{
				final MainDataRecordIdentifier bomIdentifier = MainDataRecordIdentifier.builder()
						.productDescriptor(component.getKey())
						.date(identifier.getDate())
						.build();
				final UpdateMainDataRequest requestForBOM = UpdateMainDataRequest.builder()
						.identifier(bomIdentifier)
						.orderedSalesQty(component.getValue().toBigDecimal())
						.build();
				dataUpdateRequestHandler.handleDataUpdateRequest(requestForBOM);
			}
		}
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
