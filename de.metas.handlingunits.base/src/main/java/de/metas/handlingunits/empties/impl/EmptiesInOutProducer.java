package de.metas.handlingunits.empties.impl;


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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.LazyInitializer;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.handlingunits.impl.PlainPackingMaterialDocumentLineSource;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.inout.impl.AbstractReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.inout.IInOutBL;
import de.metas.logging.LogManager;



public class EmptiesInOutProducer extends AbstractReturnsInOutProducer
{

	// Services

	private static final transient Logger logger = LogManager.getLogger(EmptiesInOutProducer.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	/** InOut header reference. It will be created just when it is needed. */

	private final LazyInitializer<I_M_InOut> inoutRef = LazyInitializer.of(() -> createInOutHeader());
	private final List<IPackingMaterialDocumentLineSource> sources = new ArrayList<>();

	private final EmptiesInOutLinesProducer inoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);
	
	private Properties _ctx;
	private ITrxManager trxManager;


	public EmptiesInOutProducer(final Properties ctx)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");

		_ctx = ctx;
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
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(new TrxCallable<I_M_InOut>()
		{

			@Override
			public I_M_InOut call() throws Exception
			{
				return createInTrx();
			}
			
			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				throw e;
			}
		});
	}
	
	private I_M_InOut createInTrx()
	{
		Check.assume(!executed, "inout not already created");
		executed = true;
		
		final EmptiesInOutLinesProducer inoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);
		inoutLinesBuilder.addSources(sources);


		final boolean doComplete = isComplete();

		//
		// Create and complete the material return
		if (doComplete)
		{
			// Create document lines
			// NOTE: as a side effect the document header will be created, if there was at least one line
			inoutLinesBuilder.create();
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
			inoutLinesBuilder.create();
			final I_M_InOut inout = inoutRef.getValue();
			InterfaceWrapperHelper.save(inout);
			return inout;
		}

	//	setCtx(ctx);
	}

	@Override
	public final boolean isEmpty()
	{
		return sources.isEmpty();
	}

	public IReturnsInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		Check.assume(!executed, "inout shall not be generated yet");
		
		sources.add(PlainPackingMaterialDocumentLineSource.of(packingMaterial, qty));
		
		return this;
	}

	private I_M_InOut createInOutHeader()
	{
		final IContextAware contextProvider = getContextProvider();
		final Properties ctx = contextProvider.getCtx();

		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class, contextProvider);

		//
		// Document Type
		{
			final String movementType = getMovementTypeToUse();
			final boolean isSOTrx = inOutBL.getSOTrxFromMovementType(movementType);
			// isSOTrx = 'Y' means packing material coming back from the customer -> incoming -> Receipt
			// isSOTrx = 'N' means packing material is returned to the vendor -> outgoing -> Delivery
			final String docBaseType = isSOTrx ? X_C_DocType.DOCBASETYPE_MaterialReceipt : X_C_DocType.DOCBASETYPE_MaterialDelivery;

			inout.setMovementType(movementType);
			inout.setIsSOTrx(isSOTrx);

			final I_C_DocType docType = getEmptiesDocType(ctx,
					docBaseType,
					inout.getAD_Client_ID(),
					inout.getAD_Org_ID(),
					isSOTrx,
					ITrx.TRXNAME_None);
			inout.setC_DocType(docType);
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
			final org.compiere.model.I_C_Order order = getC_Order();

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


	private I_C_DocType getEmptiesDocType(final Properties ctx, final String docBaseType, final int adClientId, final int adOrgId, final boolean isSOTrx, final String trxName)
	{
		final List<I_C_DocType> docTypes = docTypeDAO.retrieveDocTypesByBaseType(ctx, docBaseType, adClientId, adOrgId, ITrx.TRXNAME_None);
		if(docTypes == null)
		{
			logger.warn("No document types found for docBaseType={}, adClientId={}, adOrgId={}", docBaseType, adClientId, adOrgId);
			return null;
		}

		//
		// Search for specific empties shipment/receipt document sub-type (task 07694)
		// 07694: using the empties-subtype for receipts.
		final String docSubType = isSOTrx ? X_C_DocType.DOCSUBTYPE_Leergutanlieferung : X_C_DocType.DOCSUBTYPE_Leergutausgabe;
		for (final I_C_DocType docType : docTypes)
		{
			final String subType = docType.getDocSubType();
			if (docSubType.equals(subType))
			{
				return docType;
			}
		}
		
		//
		// If the empties doc type was not found (should not happen) fallback to the default one
		{
			final I_C_DocType defaultDocType = docTypes.get(0);
			logger.warn("No empties document type found for docBaseType={}, docSubType={}, adClientId={}, adOrgId={}. Using fallback docType={}", docBaseType, docSubType, adClientId, adOrgId, defaultDocType);
			return defaultDocType;
		}
	}

	@Override
	protected void createLines()
	{
		inoutLinesBuilder.create();

	}

	@Override
	protected int getReturnsDocTypeId(
			final IContextAware contextProvider,
			final boolean isSOTrx,
			final I_M_InOut inout,
			final String docBaseType)
	{
		final Properties ctx = contextProvider.getCtx();
		final String trxName = contextProvider.getTrxName();

		final I_C_DocType docType = getEmptiesDocType(ctx,
				docBaseType,
				inout.getAD_Client_ID(),
				inout.getAD_Org_ID(),
				isSOTrx,
				trxName);

		int docTypeId = docType == null ? -1 : docType.getC_DocType_ID();

		// If the empties doc type was not found (should not happen) fallback to the default one
		if (docTypeId <= 0)
		{
			docTypeId = docTypeDAO.getDocTypeId(ctx,
					docBaseType,
					inout.getAD_Client_ID(),
					inout.getAD_Org_ID(),
					trxName);
		}

		return docTypeId;
	}
}
