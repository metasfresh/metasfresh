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

package de.metas.handlingunits.inout.returns.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsResult.CustomerHUReturnsResult;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsResult.MultiCustomerHUReturnsResultBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.event.ReturnInOutUserNotificationsProducer;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Note: For the time being ( task #1306) there is no requirement to have returns from customer created for more than 1 customer at the same time.
 * But nevertheless, I am writing the implementation similar with the Vendor Return part, to have them structured and to allow the possibility to perform the return from customer also from a POS.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MultiCustomerHUReturnsInOutProducer
{
	// services
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final AdMessageKey MSG_ERR_NO_BUSINESS_PARTNER_SHIP_TO_LOCATION = AdMessageKey.of("MSG_ERR_NO_BUSINESS_PARTNER_SHIP_TO_LOCATION");

	//
	// Parameters
	private Timestamp _movementDate;
	private final ImmutableList<I_M_HU> shippedHUsToReturn;
	private final WarehouseId returnToWarehouseId;
	private final AdTableId inOutLineTableId;

	@Builder
	private MultiCustomerHUReturnsInOutProducer(
			@NonNull final Collection<I_M_HU> shippedHUsToReturn)
	{
		this.shippedHUsToReturn = ImmutableList.copyOf(shippedHUsToReturn);
		this.returnToWarehouseId = huWarehouseDAO.retrieveFirstQualityReturnWarehouseId();
		this.inOutLineTableId = InterfaceWrapperHelper.getAdTableId(I_M_InOutLine.class);
	}

	public MultiCustomerHUReturnsResult create()
	{
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, this::createInTrx);
	}

	private MultiCustomerHUReturnsResult createInTrx()
	{
		final HashMap<CustomerReturnsProducerKey, CustomerReturnsInOutProducer> customerReturnProducers = new HashMap<>();

		for (final I_M_HU shippedHU : shippedHUsToReturn)
		{
			InterfaceWrapperHelper.setTrxName(shippedHU, ITrx.TRXNAME_ThreadInherited);

			final Set<InOutLineId> originalShipmentLineIds = getOriginalShipmentLineIds(shippedHU);

			final I_M_HU returnedHU = handlingUnitsBL.copyAsPlannedHUs()
					.huIdToCopy(HuId.ofRepoId(shippedHU.getM_HU_ID()))
					.targetWarehouseId(returnToWarehouseId)
					.execute()
					.getSingleNewHU();

			//
			// Get the HU and the original vendor receipt M_InOutLine_ID and add it to the right producer
			for (final InOutLineId originalShipmentLineId : originalShipmentLineIds)
			{
				final I_M_InOut originalShipment = getOriginalShipmentIfApplies(originalShipmentLineId);
				if (originalShipment == null)
				{
					continue;
				}

				// Add the HU to the right producer
				// NOTE: There will be one return inout for each partner, warehouse and order
				// The return inout lines will be created based on the origin inoutlines (from receipts)
				customerReturnProducers.computeIfAbsent(
								CustomerReturnsProducerKey.builder()
										.bpartnerId(BPartnerId.ofRepoId(originalShipment.getC_BPartner_ID()))
										.warehouseId(returnToWarehouseId)
										.originOrderId(OrderId.ofRepoIdOrNull(originalShipment.getC_Order_ID()))
										.build(),
								this::createCustomerReturnInOutProducer
						)
						.addHUToReturn(returnedHU, originalShipmentLineId);
			}
		}

		//
		// Iterate all customer return producers and actually create the customer returns
		final MultiCustomerHUReturnsResultBuilder resultBuilder = MultiCustomerHUReturnsResult.builder();
		for (final CustomerReturnsInOutProducer customerReturnProducer : customerReturnProducers.values())
		{
			final I_M_InOut customerReturn = customerReturnProducer.create();
			final List<I_M_HU> husReturned = customerReturnProducer.getHUsReturned();

			resultBuilder.item(CustomerHUReturnsResult.builder()
					.customerReturn(customerReturn)
					.returnedHUs(husReturned)
					.build());
		}

		final MultiCustomerHUReturnsResult result = resultBuilder.build();

		ReturnInOutUserNotificationsProducer.newInstance().notify(result.getCustomerReturns());

		return result;
	}

	@Nullable
	private I_M_InOut getOriginalShipmentIfApplies(final InOutLineId originalShipmentLineId)
	{
		final I_M_InOutLine originalShipmentLine = huInOutBL.getLineById(originalShipmentLineId);
		if (originalShipmentLine == null)
		{
			return null;
		}

		final I_M_InOut originalShipment = huInOutBL.getById(InOutId.ofRepoId(originalShipmentLine.getM_InOut_ID()));
		if (!originalShipment.isSOTrx())
		{
			// do not allow HUs from receipts to get into customer returns
			return null;
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(originalShipment.getDocStatus());
		if (!docStatus.isCompletedOrClosed())
		{
			// do not allow HUs from uncompleted inouts to get into customer returns
			return null;
		}

		if (huInOutBL.isCustomerReturn(originalShipment))
		{
			return null;
		}

		return originalShipment;
	}

	private Set<InOutLineId> getOriginalShipmentLineIds(final I_M_HU shippedHU)
	{
		final IContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx();

		//
		// Find out the HU assignments to original customer material shipment
		List<I_M_HU_Assignment> shipmentLineHUAssignments = huAssignmentDAO.retrieveTableHUAssignmentsNoTopFilter(ctxAware, inOutLineTableId.getRepoId(), shippedHU);
		// if the given HU does not have any inout line HU assignments, it might be that it is an aggregated HU.
		// fallback on the HU assignments of the top level HU
		if (shipmentLineHUAssignments.isEmpty())
		{
			final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(shippedHU);
			shipmentLineHUAssignments = huAssignmentDAO.retrieveTableHUAssignmentsNoTopFilter(ctxAware, inOutLineTableId.getRepoId(), topLevelHU);
		}

		final ImmutableSet<InOutLineId> shipmentLineIds = shipmentLineHUAssignments.stream()
				.map(shipmentLineHUAssignment -> InOutLineId.ofRepoId(shipmentLineHUAssignment.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());

		if (shipmentLineIds.isEmpty())
		{
			throw new AdempiereException("No shipment line HU assignments found for " + shippedHU);
		}

		return shipmentLineIds;
	}

	@Value
	@Builder
	private static class CustomerReturnsProducerKey
	{
		@NonNull BPartnerId bpartnerId;
		@NonNull WarehouseId warehouseId;
		@Nullable OrderId originOrderId;
	}

	/**
	 * Create customer return producer, set the details and use it to create the customer return inout.
	 */
	private CustomerReturnsInOutProducer createCustomerReturnInOutProducer(@NonNull final CustomerReturnsProducerKey key)
	{
		final BPartnerId bpartnerId = key.getBpartnerId();
		final WarehouseId warehouseId = key.getWarehouseId();
		final OrderId originOrderId = key.getOriginOrderId();

		final I_C_BPartner partner = bpartnerDAO.getById(bpartnerId);
		final I_C_BPartner_Location shipFromLocation = retrieveShipFromLocation(bpartnerId);
		final I_M_Warehouse warehouse = huWarehouseDAO.getById(warehouseId);

		final CustomerReturnsInOutProducer producer = CustomerReturnsInOutProducer.newInstance();
		producer.setC_BPartner(partner);
		producer.setC_BPartner_Location(shipFromLocation);

		producer.setMovementType(X_M_Transaction.MOVEMENTTYPE_CustomerReturns);
		producer.setM_Warehouse(warehouse);

		producer.setMovementDate(getMovementDate());

		if (originOrderId != null)
		{
			final I_C_Order originOrder = orderBL.getById(originOrderId);
			producer.setC_Order(originOrder);
		}

		return producer;
	}

	public MultiCustomerHUReturnsInOutProducer setMovementDate(final Timestamp movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	private Timestamp getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = Env.getDate(); // use login date by default
		}
		return _movementDate;
	}

	@NonNull
	private I_C_BPartner_Location retrieveShipFromLocation(@NonNull final BPartnerId bpartnerId)
	{
		final IBPartnerDAO.BPartnerLocationQuery query = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId)
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.build();

		final I_C_BPartner_Location shipFromLocation = bpartnerDAO.retrieveBPartnerLocation(query);
		if (shipFromLocation == null)
		{
			final I_C_BPartner bPartner = bpartnerDAO.getById(bpartnerId);
			throw new AdempiereException(MSG_ERR_NO_BUSINESS_PARTNER_SHIP_TO_LOCATION, bPartner.getName(), bPartner.getValue()).markAsUserValidationError();
		}

		return shipFromLocation;
	}

}
