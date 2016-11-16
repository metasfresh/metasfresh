package org.eevolution.drp.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;

/**
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08118_Wie_geht_das_zur%C3%BCck%2C_was_noch_bei_der_Linie_steht_%28Prozess%29_%28107566315908%29
 */
public class DD_Order_GenerateRawMaterialsReturn extends SvrProcess
{
	// Services
	private final transient IStorageEngineService storageEngineService = Services.get(IStorageEngineService.class);
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private int p_M_Warehouse_ID = -1;
	private I_M_Warehouse _warehouse;
	private boolean p_IsTest = false;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String parameterName = para.getParameterName();
			if (I_M_Warehouse.COLUMNNAME_M_Warehouse_ID.equals(parameterName))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else if ("IsTest".equals(parameterName))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}

		if (p_M_Warehouse_ID <= 0 && I_M_Warehouse.Table_Name.equals(getTableName()))
		{
			p_M_Warehouse_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_Warehouse warehouse = getM_Warehouse();

		final IStorageEngine storageEngine = storageEngineService.getStorageEngine();
		final IStorageQuery storageQuery = storageEngine.newStorageQuery();
		storageQuery.addWarehouse(warehouse);

		final Map<ArrayKey, RawMaterialsReturnDDOrderLineCandidate> key2candidate = new HashMap<>();

		//
		// Retrieve all storages and aggregate them to "Raw Materials to return" candidates
		final Collection<IStorageRecord> storageRecords = storageEngine.retrieveStorageRecords(this, storageQuery);
		for (final IStorageRecord storageRecord : storageRecords)
		{
			//
			// Get/Create candidate
			final ArrayKey key = mkDDOrderLineCandidateKey(storageRecord);
			RawMaterialsReturnDDOrderLineCandidate candidate = key2candidate.get(key);
			if (candidate == null)
			{
				candidate = new RawMaterialsReturnDDOrderLineCandidate(getCtx(), storageRecord.getProduct(), storageRecord.getLocator());
				key2candidate.put(key, candidate);
			}

			//
			// Aggregate current storage record to our candidate
			candidate.addStorageRecord(storageRecord);
		}

		//
		// Iterate the candidates and create a DD_Order for each of them
		for (final RawMaterialsReturnDDOrderLineCandidate candidate : key2candidate.values())
		{
			// Skip zero Qty candidates (shall not happen)
			if (candidate.getQty().signum() == 0)
			{
				addLog("@NotValid@ " + candidate.getSummary() + ": @Qty@=0");
				continue;
			}
			
			// Skip invalid candidates
			if (!candidate.isValid())
			{
				addLog("@NotValid@ " + candidate.getSummary() + ": " + candidate.getNotValidReasons());
				continue;
			}

			//
			// Create DD_Order for candidate
			trxManager.run(getTrxName(), new TrxRunnable2()
			{

				@Override
				public void run(final String localTrxName) throws Exception
				{
					final I_DD_Order ddOrder = createAndComplete(candidate);
					addLog("@Created@ " + candidate.getSummary() + ": @DD_Order_ID@=" + ddOrder.getDocumentNo());
				}

				@Override
				public boolean doCatch(final Throwable ex) throws Throwable
				{
					addLog("@NotValid@ " + candidate.getSummary() + ": " + ex.getLocalizedMessage());
					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}

		// Force a ROLLBACK if we are running in testing mode
		if (p_IsTest)
		{
			throw new AdempiereException("@IsTest@=@Y@");
		}

		return MSG_OK;
	}

	private final I_M_Warehouse getM_Warehouse()
	{
		if (_warehouse == null)
		{
			_warehouse = InterfaceWrapperHelper.create(getCtx(), p_M_Warehouse_ID, I_M_Warehouse.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(_warehouse, "warehouse not null");
		}

		return _warehouse;
	}

	private final ArrayKey mkDDOrderLineCandidateKey(final IStorageRecord storageRecord)
	{
		final I_M_Product product = storageRecord.getProduct();
		final I_M_Locator locator = storageRecord.getLocator();
		return Util.mkKey(product.getM_Product_ID(), locator.getM_Locator_ID());
	}

	private I_DD_Order createAndComplete(final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		final I_DD_Order ddOrder = createDD_Order(candidate);
		createDD_OrderLine(ddOrder, candidate);

		docActionBL.processEx(ddOrder, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		return ddOrder;
	}

	private I_DD_Order createDD_Order(final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		Check.assume(candidate.isValid(), "candidate is valid: {}", candidate);

		final IContextAware context = new PlainContextAware(getCtx(), ITrx.TRXNAME_ThreadInherited);
		final Timestamp dateOrdered = candidate.getDateOrdered();
		final int shipperId = candidate.getDD_NetworkDistributionLine().getM_Shipper_ID();
		final I_AD_Org org = candidate.getAD_Org();
		final I_C_BPartner orgBPartner = candidate.getOrgBPartner();
		final I_C_BPartner_Location orgBPLocation = candidate.getOrgBPLocation();
		final int salesRepId = candidate.getPlanner_ID();
		final I_M_Warehouse warehouseInTrasit = candidate.getInTransitWarehouse();
		final I_S_Resource rawMaterialsPlant = candidate.getRawMaterialsPlant();

		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class, context);
		ddOrder.setAD_Org(org);
		ddOrder.setPP_Plant(rawMaterialsPlant);
		ddOrder.setC_BPartner(orgBPartner);
		ddOrder.setC_BPartner_Location(orgBPLocation);
		ddOrder.setSalesRep_ID(salesRepId);

		final int docTypeId = docTypeDAO.getDocTypeId(
				getCtx(),
				X_C_DocType.DOCBASETYPE_DistributionOrder,
				ddOrder.getAD_Client_ID(),
				ddOrder.getAD_Org_ID(),
				ITrx.TRXNAME_None);
		ddOrder.setC_DocType_ID(docTypeId);
		ddOrder.setM_Warehouse(warehouseInTrasit);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrder.setDateOrdered(dateOrdered);
		ddOrder.setDatePromised(dateOrdered);
		ddOrder.setM_Shipper_ID(shipperId);
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);

		InterfaceWrapperHelper.save(ddOrder);
		return ddOrder;
	}

	private void createDD_OrderLine(final I_DD_Order ddOrder, final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		Check.assume(candidate.isValid(), "candidate is valid: {}", candidate);

		final I_M_Product product = candidate.getM_Product();
		final I_C_UOM uom = candidate.getC_UOM();
		final BigDecimal qtyToMove = candidate.getQty();
		final I_DD_NetworkDistributionLine networkLine = candidate.getDD_NetworkDistributionLine();
		final I_M_Locator locatorFrom = candidate.getM_Locator();
		final I_M_Locator locatorTo = candidate.getRawMaterialsLocator();

		//
		// Create DD Order Line
		final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
		ddOrderline.setAD_Org_ID(ddOrder.getAD_Org_ID());
		ddOrderline.setDD_Order(ddOrder);
		// ddOrderline.setC_BPartner_ID(bpartnerId);

		//
		// Locator From/To
		ddOrderline.setM_Locator(locatorFrom);
		ddOrderline.setM_LocatorTo(locatorTo);

		//
		// Product, UOM, Qty
		ddOrderline.setM_Product(product);
		ddOrderline.setC_UOM(uom);
		ddOrderline.setQtyEntered(qtyToMove);
		ddOrderline.setQtyOrdered(qtyToMove);
		ddOrderline.setTargetQty(qtyToMove);

		//
		// Dates
		ddOrderline.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderline.setDatePromised(ddOrder.getDatePromised());

		//
		// Other flags
		ddOrderline.setIsInvoiced(false);
		ddOrderline.setDD_AllowPush(networkLine.isDD_AllowPush());
		ddOrderline.setIsKeepTargetPlant(networkLine.isKeepTargetPlant());

		InterfaceWrapperHelper.save(ddOrderline);
	};
}
