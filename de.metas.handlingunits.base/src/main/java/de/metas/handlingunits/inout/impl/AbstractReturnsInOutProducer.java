package de.metas.handlingunits.inout.impl;

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

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.LazyInitializer;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.inout.IInOutBL;

public abstract class AbstractReturnsInOutProducer implements IReturnsInOutProducer
{
	//
	// Services
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private Properties _ctx;
	protected boolean executed = false;

	private I_C_BPartner _bpartner = null;
	private int _bpartnerLocationId = -1;
	private String _movementType = null;
	private I_M_Warehouse _warehouse = null;
	private Date _movementDate = null;
	private boolean _complete = true;

	/** #643 The order on based on which the empties inout is created (if it was selected) */
	private I_C_Order _order = null;

	/** InOut header reference. It will be created just when it is needed. */
	protected final LazyInitializer<I_M_InOut> inoutRef = LazyInitializer.of(() -> createInOutHeader());

	public void setCtx(final Properties ctx)
	{
		this._ctx = ctx;
	}

	private final Properties getCtx()
	{
		return _ctx;
	}

	private IContextAware getContextProvider()
	{
		final Properties ctx = getCtx();

		final String trxName = trxManager.getThreadInheritedTrxName();
		trxManager.assertTrxNameNotNull(trxName);

		return PlainContextAware.newWithTrxName(ctx, trxName);
	}

	@Override
	public I_M_InOut create()
	{
		Check.assume(!executed, "inout not already created");
		executed = true;

		final boolean doComplete = isComplete();

		//
		// Create and complete the material return
		if (doComplete)
		{
			// Create document lines
			// NOTE: as a side effect the document header will be created, if there was at least one line
			createLines();
			if (!inoutRef.isInitialized())
			{
				// nothing created
				return null;
			}
			final I_M_InOut inout = inoutRef.getValue();
			if (inout.getM_InOut_ID() <= 0)
			{
				// nothing created
				return null;
			}

			docActionBL.processEx(inout, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

			return inout;
		}
		//
		// Create a draft material return, even if there are no lines
		else
		{
			createLines();
			final I_M_InOut inout = inoutRef.getValue();
			InterfaceWrapperHelper.save(inout);
			return inout;
		}
	}

	protected abstract void createLines();

	@Override
	public abstract boolean isEmpty();
	
	@Override
	public abstract IReturnsInOutProducer addPackingMaterial(I_M_HU_PackingMaterial packingMaterial, int qty);

	/**
	 * Check if this producer is empty.
	 *
	 * A producer is considered empty, when there are no packing material added.
	 *
	 * @return true if empty.
	 */

	private I_M_InOut createInOutHeader()
	{
		final IContextAware contextProvider = getContextProvider();

		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class, contextProvider);

		//
		// Document Type
		{
			final String movementType = getMovementTypeToUse();
			final boolean isSOTrx = inOutBL.getSOTrxFromMovementType(movementType);
			// isSOTrx = 'Y' means packing material coming back from the customer -> incoming -> Receipt
			// isSOTrx = 'N' means packing material is returned to the vendor -> outgoing -> Delivery
			final String docBaseType = isSOTrx ?  X_C_DocType.DOCBASETYPE_MaterialReceipt : X_C_DocType.DOCBASETYPE_MaterialDelivery;

			inout.setMovementType(movementType);
			inout.setIsSOTrx(isSOTrx);

			final int docTypeId = getReturnsDocTypeId(contextProvider, isSOTrx, inout, docBaseType);

			inout.setC_DocType_ID(docTypeId);
		}

		//
		// BPartner, Location & Contact
		{
			final I_C_BPartner bpartner = getC_BPartnerToUse();

			inout.setC_BPartner_ID(bpartner.getC_BPartner_ID());

			final int bpartnerLocationId = getC_BPartner_Location_ID_ToUse();
			inout.setC_BPartner_Location_ID(bpartnerLocationId);

			// inout.setAD_User_ID(bpartnerContactId); // TODO
		}

		//
		// Document Dates
		{
			final Timestamp movementDate = getMovementDateToUse();
			inout.setDateOrdered(movementDate);
			inout.setMovementDate(movementDate);
			inout.setDateAcct(movementDate);
		}

		//
		// Warehouse
		{
			final I_M_Warehouse warehouse = getM_WarehouseToUse();
			inout.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		}

		//
		// task #643: Add order related details
		{
			final I_C_Order order = getC_Order();

			if (order == null)
			{
				// nothing to do. The order was not selected
			}
			else
			{
				// if the order was selected, set its poreference to the inout
				final String poReference = order.getPOReference();

				inout.setPOReference(poReference);
				inout.setC_Order(order);
			}
		}

		// NOTE: don't save it. it will be saved later by "inoutLinesBuilder"
		// InterfaceWrapperHelper.save(inout);
		return inout;
	}

	protected abstract int getReturnsDocTypeId(IContextAware contextProvider, boolean isSOTrx, I_M_InOut inout, String docBaseType);

	/**
	 * Asserts this producer is in configuration stage (nothing produced yet)
	 */
	private final void assertConfigurable()
	{
		Check.assume(!executed, "producer shall not be executed");
		Check.assume(!inoutRef.isInitialized(), "inout not created yet");
	}

	@Override
	public IReturnsInOutProducer setC_BPartner(final I_C_BPartner bpartner)
	{
		assertConfigurable();
		_bpartner = bpartner;
		return this;
	}

	private I_C_BPartner getC_BPartnerToUse()
	{
		Check.assumeNotNull(_bpartner, "bpartner not null");
		return _bpartner;
	}

	@Override
	public IReturnsInOutProducer setC_BPartner_Location(final I_C_BPartner_Location bpLocation)
	{
		assertConfigurable();
		Check.assumeNotNull(bpLocation, "bpLocation not null");
		_bpartnerLocationId = bpLocation.getC_BPartner_Location_ID();
		return this;
	}

	private int getC_BPartner_Location_ID_ToUse()
	{
		if (_bpartnerLocationId > 0)
		{
			return _bpartnerLocationId;
		}

		final I_C_BPartner bpartner = getC_BPartnerToUse();
		final I_C_BPartner_Location bpLocation = bpartnerDAO.retrieveShipToLocation(getCtx(), bpartner.getC_BPartner_ID(), ITrx.TRXNAME_None);
		Check.assumeNotNull(bpLocation, "bpLocation not null");
		return bpLocation.getC_BPartner_Location_ID();
	}

	@Override
	public IReturnsInOutProducer setMovementType(final String movementType)
	{
		assertConfigurable();

		_movementType = movementType;
		return this;
	}

	private String getMovementTypeToUse()
	{
		Check.assumeNotNull(_movementType, "movementType not null");
		return _movementType;
	}

	@Override
	public IReturnsInOutProducer setM_Warehouse(final I_M_Warehouse warehouse)
	{
		assertConfigurable();

		_warehouse = warehouse;
		return this;
	}

	private final I_M_Warehouse getM_WarehouseToUse()
	{
		Check.assumeNotNull(_warehouse, "warehouse not null");
		return _warehouse;
	}

	@Override
	public IReturnsInOutProducer setMovementDate(final Date movementDate)
	{
		Check.assumeNotNull(movementDate, "movementDate not null");
		_movementDate = movementDate;
		return this;
	}

	private final Timestamp getMovementDateToUse()
	{
		if (_movementDate != null)
		{
			return TimeUtil.asTimestamp(_movementDate);
		}

		final Properties ctx = getCtx();
		final Timestamp movementDate = Env.getDate(ctx); // use Login date (08306)
		return movementDate;
	}

	@Override
	public IReturnsInOutProducer setC_Order(final I_C_Order order)
	{
		assertConfigurable();
		_order = order;
		return this;
	}

	private I_C_Order getC_Order()
	{
		return _order;
	}

	@Override
	public IReturnsInOutProducer dontComplete()
	{
		_complete = false;
		return this;
	}

	private boolean isComplete()
	{
		return _complete;
	}
}

