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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.InOutLineId;
import de.metas.inout.event.ReturnInOutUserNotificationsProducer;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

public class ManualCustomerReturnInOutProducer
{
	// services
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	//
	// Parameters
	private final I_M_InOut manualCustomerReturn;
	private final ImmutableListMultimap<InOutLineId, I_M_HU> husByLineId;
	private final ZonedDateTime movementDate;

	@Builder
	public ManualCustomerReturnInOutProducer(
			@NonNull final I_M_InOut manualCustomerReturn,
			@NonNull final ListMultimap<InOutLineId, I_M_HU> husByLineId)
	{
		this.manualCustomerReturn = manualCustomerReturn;
		this.husByLineId = ImmutableListMultimap.copyOf(husByLineId);
		this.movementDate = SystemTime.asZonedDateTime();
	}

	public void create()
	{
		trxManager.run(ITrx.TRXNAME_ThreadInherited, this::createInTrx);
	}

	private void createInTrx()
	{
		//
		// Iterate all HUs, group them by Partner and HU's warehouse
		// and create one customer returns producer for each group.
		final TreeMap<ArrayKey, CustomerReturnsInOutProducer> customerReturnProducers = new TreeMap<>();

		for (final InOutLineId originInOutLineId : husByLineId.keySet())
		{
			final I_M_InOutLine customerReturnLine = InterfaceWrapperHelper.loadOutOfTrx(originInOutLineId, I_M_InOutLine.class);

			for (final I_M_HU hu : husByLineId.get(originInOutLineId))
			{
				InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_ThreadInherited);

				final WarehouseId warehouseId = IHandlingUnitsBL.extractWarehouseId(hu);

				//
				// If the HU is not a top level one, extract it first
				huTrxBL.extractHUFromParentIfNeeded(hu);

				final BPartnerId bpartnerId = BPartnerId.ofRepoId(manualCustomerReturn.getC_BPartner_ID());

				// Add the HU to the right producer
				// NOTE: There will be one return inout for each partner and warehouse
				// The return inout lines will be created based on the origin inoutlines (from receipts)
				final ArrayKey customerReturnProducerKey = ArrayKey.of(warehouseId, bpartnerId);
				customerReturnProducers.computeIfAbsent(customerReturnProducerKey, k -> createCustomerReturnInOutProducer(bpartnerId, warehouseId))
						.addHUToReturn(hu, customerReturnLine.getM_InOutLine_ID());

				Services.get(IHUAssignmentBL.class).assignHU(customerReturnLine, hu, ITrx.TRXNAME_ThreadInherited);

			}
		}

		//
		// Iterate all vendor return producers and actually create the vendor returns InOut
		final List<I_M_InOut> returnInOuts = customerReturnProducers.values().stream()
				.map(CustomerReturnsInOutProducer::create) // create vendor return
				.map(returnInOut -> InterfaceWrapperHelper.create(returnInOut, I_M_InOut.class)) // wrap it
				.collect(GuavaCollectors.toImmutableList());

		//
		// Send notifications
		if (!returnInOuts.isEmpty())
		{
			ReturnInOutUserNotificationsProducer.newInstance()
					.notify(returnInOuts);

			// mark HUs as active and create movements to QualityReturnWarehouse for them
			final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
			huInOutBL.moveHUsToQualityReturnWarehouse(getHUsToReturn());
		}
	}

	/**
	 * Create customer return producer, set the details and use it to create the customer return inout.
	 */
	private CustomerReturnsInOutProducer createCustomerReturnInOutProducer(final BPartnerId bpartnerId, final WarehouseId warehouseId)
	{
		final Properties ctx = Env.getCtx();
		final I_C_BPartner partner = bpartnerDAO.getById(bpartnerId);
		final I_C_BPartner_Location shipFromLocation = bpartnerDAO.retrieveShipToLocation(ctx, bpartnerId.getRepoId(), ITrx.TRXNAME_None);
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final CustomerReturnsInOutProducer producer = CustomerReturnsInOutProducer.newInstance();
		producer.setC_BPartner(partner);
		producer.setC_BPartner_Location(shipFromLocation);

		producer.setMovementType(X_M_Transaction.MOVEMENTTYPE_CustomerReturns);
		producer.setM_Warehouse(warehouse);

		producer.setMovementDate(TimeUtil.asTimestamp(movementDate));

		producer.setManualReturnInOut(manualCustomerReturn);

		return producer;
	}

	private ImmutableList<I_M_HU> getHUsToReturn()
	{
		return ImmutableList.copyOf(husByLineId.values());
	}
}
