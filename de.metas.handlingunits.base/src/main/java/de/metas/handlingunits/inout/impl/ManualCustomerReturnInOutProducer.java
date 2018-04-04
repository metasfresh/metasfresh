package de.metas.handlingunits.inout.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.event.ReturnInOutUserNotificationsProduder;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class ManualCustomerReturnInOutProducer
{
	// services
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	//
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	public static final ManualCustomerReturnInOutProducer newInstance()
	{
		return new ManualCustomerReturnInOutProducer();
	}

	//
	// Parameters
	private Timestamp _movementDate;
	private final List<I_M_HU> _husToReturn = new ArrayList<>();

	private final Map<Integer, List<I_M_HU>> _lineToHUs = new HashMap<>();

	private I_M_InOut _manualCustomerReturn;

	public List<I_M_InOut> create()
	{
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, this::createInTrx);
	}

	private List<I_M_InOut> createInTrx()
	{
		//
		// Iterate all HUs, group them by Partner and HU's warehouse
		// and create one vendor returns producer for each group.
		final Map<ArrayKey, CustomerReturnsInOutProducer> customerReturnProducers = new TreeMap<>();

		for (final Integer originInOutID : _lineToHUs.keySet())
		{
			final I_M_InOutLine customerReturnLine = InterfaceWrapperHelper.create(Env.getCtx(), originInOutID, I_M_InOutLine.class, ITrx.TRXNAME_None);

			for (final I_M_HU hu : _lineToHUs.get(originInOutID))
			{
				InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_ThreadInherited);

				final int warehouseId = hu.getM_Locator().getM_Warehouse_ID();

				//
				// If the HU is not a top level one, extract it first
				huTrxBL.extractHUFromParentIfNeeded(hu);


				final int bpartnerId = _manualCustomerReturn.getC_BPartner_ID();

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
			if (_manualCustomerReturn != null)
			{
				ReturnInOutUserNotificationsProduder.newInstance()
						.notify(returnInOuts);
			}

			else
			{
				InterfaceWrapperHelper.refresh(_manualCustomerReturn);
			}
			final Properties ctx = InterfaceWrapperHelper.getCtx(returnInOuts.get(0));
			// mark HUs as active and create movements to QualityReturnWarehouse for them
			Services.get(IHUInOutBL.class).moveHUsForCustomerReturn(ctx, getHUsToReturn());

		}

		// return the created vendor returns
		return returnInOuts;
	}

	/**
	 * Create customer return producer, set the details and use it to create the customer return inout.
	 *
	 * @param partnerId
	 * @param hus
	 * @return
	 */
	private CustomerReturnsInOutProducer createCustomerReturnInOutProducer(final int partnerId, final int warehouseId)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final Properties ctx = Env.getCtx();
		final I_C_BPartner partner = InterfaceWrapperHelper.loadOutOfTrx(partnerId, I_C_BPartner.class);
		final I_C_BPartner_Location shipFromLocation = bpartnerDAO.retrieveShipToLocation(ctx, partnerId, ITrx.TRXNAME_None);
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.loadOutOfTrx(warehouseId, I_M_Warehouse.class);

		final CustomerReturnsInOutProducer producer = CustomerReturnsInOutProducer.newInstance();
		producer.setC_BPartner(partner);
		producer.setC_BPartner_Location(shipFromLocation);

		producer.setMovementType(X_M_Transaction.MOVEMENTTYPE_CustomerReturns);
		producer.setM_Warehouse(warehouse);

		producer.setMovementDate(getMovementDate());

		if (_manualCustomerReturn != null)
		{
			producer.setManualReturnInOut(_manualCustomerReturn);
		}

		return producer;
	}

	public ManualCustomerReturnInOutProducer setMovementDate(final Timestamp movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	public ManualCustomerReturnInOutProducer setManualCustomerReturn(final I_M_InOut manualCustomerReturn)
	{
		_manualCustomerReturn = manualCustomerReturn;
		return this;
	}

	private Timestamp getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = Env.getDate(Env.getCtx()); // use login date by default
		}
		return _movementDate;
	}

	private final List<I_M_HU> getHUsToReturn()
	{
		return _husToReturn;
	}

	public ManualCustomerReturnInOutProducer addLineToHUs(final Map<Integer, List<I_M_HU>> lineToHus)
	{
		this._lineToHUs.putAll(lineToHus);
		for (final Integer key : lineToHus.keySet())
		{
			_husToReturn.addAll(lineToHus.get(key));
		}
		return this;
	}

}
