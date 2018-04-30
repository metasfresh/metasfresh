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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.LazyInitializer;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import lombok.NonNull;

public abstract class AbstractReturnsInOutProducer implements IReturnsInOutProducer
{
	protected final ISnapshotProducer<I_M_HU> huSnapshotProducer;

	//
	// Services
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	protected final Properties _ctx;
	protected boolean executed = false;

	protected I_C_BPartner _bpartner = null;
	protected int _bpartnerLocationId = -1;
	protected String _movementType = null;
	protected I_M_Warehouse _warehouse = null;
	private Date _movementDate = null;
	private boolean _complete = true;

	protected I_M_InOut _manualReturnInOut = null;

	/** #643 The order on based on which the empties inout is created (if it was selected) */
	private I_C_Order _order = null;

	/** InOut header reference. It will be created just when it is needed. */
	protected final LazyInitializer<I_M_InOut> inoutRef = LazyInitializer.of(() -> createInOutHeader());

	protected AbstractReturnsInOutProducer(@NonNull final Properties ctx)
	{
		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(trxManager.createThreadContextAware(ctx));

		huSnapshotProducer = Services.get(IHUSnapshotDAO.class)
				.createSnapshot()
				.setContext(huContext);

		this._ctx = ctx;
	}

	protected final Properties getCtx()
	{
		return _ctx;
	}

	protected IContextAware getContextProvider()
	{
		final Properties ctx = getCtx();

		final String trxName = trxManager.getThreadInheritedTrxName();
		trxManager.assertTrxNameNotNull(trxName);

		return PlainContextAware.newWithTrxName(ctx, trxName);
	}

	@Override
	public I_M_InOut create()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, this::createInTrx);
	}

	private I_M_InOut createInTrx()
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

			// create snapshot

			createHUSnapshots();

			docActionBL.processEx(inout, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			afterInOutProcessed(inout);

			return inout;
		}
		//
		// Create a draft material return, even if there are no lines
		else
		{
			createLines();
			final I_M_InOut inout = inoutRef.getValue();

			// create snapshot

			createHUSnapshots();

			InterfaceWrapperHelper.save(inout);

			return inout;
		}
	}

	protected final void createHUSnapshots()
	{
		// Create the snapshots for all enqueued HUs so far.
		huSnapshotProducer.createSnapshots();

		// Set the Snapshot_UUID to current receipt (for later recall and reporting).
		final de.metas.handlingunits.model.I_M_InOut inout = InterfaceWrapperHelper.create(inoutRef.getValue(), de.metas.handlingunits.model.I_M_InOut.class);
		inout.setSnapshot_UUID(huSnapshotProducer.getSnapshotId());
		InterfaceWrapperHelper.save(inout);
	}

	protected void afterInOutProcessed(final I_M_InOut inout)
	{
		// nothing at this level
	}

	@Override
	public final void fillReturnsInOutHeader(final I_M_InOut returnsInOut)
	{
		ReturnsInOutHeaderFiller.newInstance()
				.setMovementDate(getMovementDateToUse())
				.setMovementType(getMovementTypeToUse())
				.setReturnsDocTypeIdProvider(this::getReturnsDocTypeId)
				//
				.setBPartnerId(getC_BPartner_ID_ToUse())
				.setBPartnerLocationId(getC_BPartner_Location_ID_ToUse())
				.setWarehouseId(getM_Warehouse_ID_ToUse())
				//
				.setOrder(getC_Order())
				//
				.fill(returnsInOut);
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

	protected I_M_InOut createInOutHeader()
	{
		// #1306: If the inout was already manually created ( customer return case) return it as it is, do not create a new document/
		if (_manualReturnInOut != null)
		{
			return _manualReturnInOut;
		}

		final IContextAware contextProvider = getContextProvider();

		final I_M_InOut emptiesInOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class, contextProvider);
		fillReturnsInOutHeader(emptiesInOut);

		// NOTE: don't save it. it will be saved later by "inoutLinesBuilder"
		// InterfaceWrapperHelper.save(inout);
		return emptiesInOut;
	}

	protected abstract int getReturnsDocTypeId(String docBaseType, boolean isSOTrx, int adClientId, int adOrgId);

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

	private int getC_BPartner_ID_ToUse()
	{
		if (_bpartner != null)
		{
			return _bpartner.getC_BPartner_ID();
		}
		return -1;
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

		final I_C_BPartner bpartner = _bpartner;
		if (bpartner != null)
		{
			final I_C_BPartner_Location bpLocation = bpartnerDAO.retrieveShipToLocation(getCtx(), bpartner.getC_BPartner_ID(), ITrx.TRXNAME_None);
			if (bpLocation != null)
			{
				return bpLocation.getC_BPartner_Location_ID();
			}
		}

		return -1;
	}

	@Override
	public IReturnsInOutProducer setMovementType(final String movementType)
	{
		assertConfigurable();

		_movementType = movementType;
		return this;
	}

	public IReturnsInOutProducer setManualReturnInOut(final I_M_InOut manualReturnInOut)
	{
		assertConfigurable();
		_manualReturnInOut = manualReturnInOut;

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

	private final int getM_Warehouse_ID_ToUse()
	{
		if (_warehouse != null)
		{
			return _warehouse.getM_Warehouse_ID();
		}
		return -1;
	}

	@Override
	public IReturnsInOutProducer setMovementDate(final Date movementDate)
	{
		Check.assumeNotNull(movementDate, "movementDate not null");
		_movementDate = movementDate;
		return this;
	}

	protected final Timestamp getMovementDateToUse()
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

	protected I_C_Order getC_Order()
	{
		return _order;
	}

	@Override
	public IReturnsInOutProducer dontComplete()
	{
		_complete = false;
		return this;
	}

	protected boolean isComplete()
	{
		return _complete;
	}
}
