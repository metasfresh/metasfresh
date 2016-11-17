package de.metas.handlingunits.maintenance.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.movement.api.impl.HUMovementBuilder;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.interfaces.I_M_Movement;
import de.metas.process.JavaProcess;

/**
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/fresh_08412_Auslagerung_manuelle_DD_Orders_Karotten_April_%28108521976654%29
 */
public class Fresh_08412_ProcessHUs extends JavaProcess
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final transient IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final transient IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final transient IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// Status
	private int countProcessed = 0;
	private int countErrors = 0;

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<HUToProcess> husToProcess = IteratorUtils.asIterator(new HUToProcessSQLIterator(getCtx()));
		try
		{
			while (husToProcess.hasNext())
			{
				final HUToProcess huToProcess = husToProcess.next();
				process(huToProcess);
			}
		}
		finally
		{
			IteratorUtils.close(husToProcess);
		}

		return "Processed=" + countProcessed + ", Errors=" + countErrors;
	}

	private void process(final HUToProcess huToProcess)
	{
		trxManager.run(new TrxRunnable2()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				process0(huToProcess);
				countProcessed++;
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				countErrors++;
				addLog("Failed to process " + huToProcess + ": " + e.getLocalizedMessage());
				log.warn(e.getLocalizedMessage(), e);
				return true; // rollback
			}

			@Override
			public void doFinally()
			{
				// nothing
			}
		});
	}

	private void process0(final HUToProcess huToProcess)
	{
		final I_M_HU hu = huToProcess.getM_HU();
		if (handlingUnitsBL.isDestroyedRefreshFirst(hu))
		{
			throw new HUException("HU was already destroyed");
		}

		moveHUToManufacturingWarehouse(huToProcess);

		createManufacturingOrder(huToProcess);
		issueHUToManufacturingOrder(huToProcess);

		addLog("Processed: " + huToProcess);
	}

	private final void moveHUToManufacturingWarehouse(final HUToProcess huToProcess)
	{
		Check.assumeNotNull(huToProcess, "huToProcess not null");

		final I_M_HU hu = huToProcess.getM_HU();
		Check.assumeNotNull(hu, "hu not null");

		final I_M_Warehouse destinationWarehouse = huToProcess.getManufacturing_Warehouse();
		Check.assumeNotNull(destinationWarehouse, "destinationWarehouse not null");

		final Timestamp movementDate = huToProcess.getMovementDate();
		Check.assumeNotNull(movementDate, "movementDate not null");

		//
		// If HU is already in target warehouse then there is nothing to move
		final I_M_Locator huLocator = hu.getM_Locator();
		if (huLocator.getM_Warehouse_ID() == destinationWarehouse.getM_Warehouse_ID())
		{
			return;
		}

		//
		// Move HU to target warehouse
		final I_M_Movement movement = new HUMovementBuilder()
				.setContextInitial(getCtx())
				.setMovementDate(movementDate)
				.setLocatorFrom(huLocator)
				.setWarehouseTo(destinationWarehouse)
				.setDescription(huToProcess.getReference())
				.addHU(hu)
				.createMovement();

		//
		// Close movement (to not allow somebody to touch it)
		factAcctDAO.deleteForDocument(movement);
		movement.setPosted(true);
		movement.setDocStatus(DocAction.STATUS_Closed);
		movement.setDocAction(DocAction.ACTION_None);
		InterfaceWrapperHelper.save(movement);

		huToProcess.setM_Movement(movement);
	}

	/**
	 * Create an complete the manufacturing order
	 */
	private final void createManufacturingOrder(final HUToProcess huToProcess)
	{
		Check.assumeNotNull(huToProcess, "huToProcess not null");

		final I_M_HU hu = huToProcess.getM_HU();
		final Timestamp processingDate = huToProcess.getMovementDate();

		//
		// Find product planning data
		final I_M_Warehouse warehouse = huToProcess.getManufacturing_Warehouse();
		final int adOrgId = warehouse.getAD_Org_ID();
		final I_PP_Product_BOMLine productBOMLine = huToProcess.getPP_Product_BOMLine();
		final I_PP_Product_BOM productBOM = productBOMLine.getPP_Product_BOM();
		final I_S_Resource plant = huToProcess.getManufacturing_Plant();
		final int manufacturingWorkflowId = huToProcess.getManufacturing_Workflow_ID();

		//
		// Create manufacturing order
		final PlainContextAware context = new PlainContextAware(getCtx(), ITrx.TRXNAME_ThreadInherited);
		final I_PP_Order order = InterfaceWrapperHelper.newInstance(I_PP_Order.class, context);
		order.setLine(10);
		ppOrderBL.addDescription(order, "Generated by " + getClass());

		//
		// Planning dimension
		order.setAD_Org_ID(adOrgId);
		order.setS_Resource(plant);
		order.setM_Warehouse(warehouse);

		//
		// Document Type & Status
		// NOTE: not sure if we need to use MaterialTrackingPPOrderBL.C_DocType_DOCSUBTYPE_QualityInspection or not
		ppOrderBL.setDocType(order, X_C_DocType.DOCBASETYPE_ManufacturingOrder, IDocTypeDAO.DOCSUBTYPE_Any);
		order.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		order.setDocAction(X_PP_Order.DOCACTION_Complete);

		//
		// Product, UOM, ASI
		order.setM_Product_ID(productBOM.getM_Product_ID());
		order.setC_UOM(productBOM.getC_UOM());
		order.setM_AttributeSetInstance_ID(0);

		//
		// BOM & Workflow
		order.setPP_Product_BOM(productBOM);
		order.setAD_Workflow_ID(manufacturingWorkflowId);

		//
		// Dates
		order.setDateOrdered(processingDate);
		order.setDatePromised(processingDate);
		order.setDateStartSchedule(processingDate);
		order.setDateFinishSchedule(processingDate);

		//
		// Qtys
		ppOrderBL.setQty(order, BigDecimal.ZERO);
		// QtyBatchSize : do not set it, let the MO to take it from workflow
		order.setYield(Env.ZERO);

		order.setScheduleType(X_PP_MRP.TYPEMRP_Demand);
		order.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);

		//
		// Misc
		order.setC_OrderLine(null);
		order.setC_BPartner_ID(hu.getC_BPartner_ID());
		order.setDescription(huToProcess.getReference());

		//
		// Save the manufacturing order
		InterfaceWrapperHelper.save(order);

		//
		// Process the manufacturing order
		docActionBL.processEx(order, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		huToProcess.setPP_Order(order);
	}

	private void issueHUToManufacturingOrder(final HUToProcess huToProcess)
	{
		final I_PP_Order ppOrder = huToProcess.getPP_Order();
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		final I_M_HU hu = huToProcess.getM_HU();
		final I_M_Product rawProduct = huToProcess.getRaw_Product();
		final I_PP_Order_BOMLine ppOrderBOMLine = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, rawProduct);

		huPPOrderBL.createIssueProducer(getCtx())
				.setTrxName(ITrx.TRXNAME_ThreadInherited)
				.setMovementDate(ppOrder.getDatePromised())
				.setTargetOrderBOMLine(ppOrderBOMLine)
				.createIssues(hu);

		if (!handlingUnitsBL.isDestroyedRefreshFirst(hu))
		{
			throw new HUException("HU was not destroyed, maybe because it was not issued completelly");
		}

		//
		// Close the manufacturing order
		// => this will trigger quality fields re-calculations
		docActionBL.processEx(ppOrder, DocAction.ACTION_Close, DocAction.STATUS_Closed);
		// Delete the accountings
		ppOrder.setPosted(true);
		InterfaceWrapperHelper.save(ppOrder);
		factAcctDAO.deleteForDocument(ppOrder); // just to be sure

		//
		// Mark all cost collectors as closed.
		// Delete the accountings for them.
		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.retrieveForOrder(ppOrder);

		for (final I_PP_Cost_Collector cc : costCollectors)
		{
			cc.setPosted(true);
			cc.setDocStatus(DocAction.STATUS_Closed);
			cc.setDocAction(DocAction.ACTION_None);
			InterfaceWrapperHelper.save(cc);

			factAcctDAO.deleteForDocument(cc);
		}
	}
}

class HUToProcess
{
	private I_M_Product rawProduct;
	private I_PP_Product_BOMLine ppProductBOMLine;
	private Timestamp movementDate;
	private I_M_HU hu;
	private I_M_Warehouse manufacturingWarehouse;
	private I_S_Resource manufacturingPlant;
	private int manufacturingWorkflowId = -1;
	private String reference;

	// Created documents:
	private I_PP_Order ppOrder;
	private I_M_Movement movement; // movement to manufacturing warehouse

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public I_M_Product getRaw_Product()
	{
		return rawProduct;
	}

	public void setRaw_Product(final I_M_Product raw_Product)
	{
		rawProduct = raw_Product;
	}

	public I_PP_Product_BOMLine getPP_Product_BOMLine()
	{
		return ppProductBOMLine;
	}

	public void setPP_Product_BOMLine(final I_PP_Product_BOMLine pP_Product_BOMLine)
	{
		ppProductBOMLine = pP_Product_BOMLine;
	}

	public Timestamp getMovementDate()
	{
		return movementDate;
	}

	public void setMovementDate(final Timestamp movementDate)
	{
		this.movementDate = movementDate;
	}

	public I_M_HU getM_HU()
	{
		return hu;
	}

	public void setM_HU(final I_M_HU m_HU)
	{
		hu = m_HU;
	}

	public I_M_Warehouse getManufacturing_Warehouse()
	{
		return manufacturingWarehouse;
	}

	public void setManufacturing_Warehouse(final I_M_Warehouse manufacturing_Warehouse)
	{
		manufacturingWarehouse = manufacturing_Warehouse;
	}

	public I_S_Resource getManufacturing_Plant()
	{
		return manufacturingPlant;
	}

	public void setManufacturing_Plant(final I_S_Resource manufacturing_Plant)
	{
		manufacturingPlant = manufacturing_Plant;
	}

	public int getManufacturing_Workflow_ID()
	{
		return manufacturingWorkflowId;
	}

	public void setManufacturing_Workflow_ID(final int manufacturingWorkflowId)
	{
		this.manufacturingWorkflowId = manufacturingWorkflowId;
	}

	public String getReference()
	{
		return reference;
	}

	public void setReference(final String reference)
	{
		this.reference = reference;
	}

	public void setPP_Order(final I_PP_Order ppOrder)
	{
		this.ppOrder = ppOrder;
	}

	public I_PP_Order getPP_Order()
	{
		return ppOrder;
	}

	public void setM_Movement(final I_M_Movement movement)
	{
		this.movement = movement;
	}

	public I_M_Movement getM_Movement()
	{
		return movement;
	}
}

class HUToProcessSQLIterator extends AbstractPreparedStatementBlindIterator<HUToProcess>
{
	private static final String Table_Name = "backup.08412_HUsToProcess";
	private static final String COLUMNNAME_Raw_Product_ID = "Raw_Product_ID";
	private static final String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";
	private static final String COLUMNNAME_MovementDate = "MovementDate";
	private static final String COLUMNNAME_M_HU_ID = "M_HU_ID";
	private static final String COLUMNNAME_Manufacturing_Warehouse_ID = "Manufacturing_Warehouse_ID";
	private static final String COLUMNNAME_Manufacturing_Plant_ID = "Manufacturing_Plant_ID";
	private static final String COLUMNNAME_Manufacturing_Workflow_ID = "Manufacturing_Workflow_ID";
	private static final String COLUMNNAME_Reference = "Reference";
	private static final String sql = "select"
			+ "\n " + COLUMNNAME_Raw_Product_ID
			+ "\n , " + COLUMNNAME_PP_Product_BOMLine_ID
			+ "\n , " + COLUMNNAME_MovementDate
			+ "\n , " + COLUMNNAME_M_HU_ID
			+ "\n , " + COLUMNNAME_Manufacturing_Warehouse_ID
			+ "\n , " + COLUMNNAME_Manufacturing_Plant_ID
			+ "\n , " + COLUMNNAME_Manufacturing_Workflow_ID
			+ "\n , " + COLUMNNAME_Reference
			+ "\n from " + Table_Name + " t "
			+ "\n where true"
			// make sure HU was not already destroyed
			+ " and exists (select 1 from M_HU hu where hu.M_HU_ID=t.M_HU_ID and hu.HUStatus<>'D')";

	private final Properties ctx;
	private final String trxName;

	public HUToProcessSQLIterator(final Properties ctx)
	{
		super();
		this.ctx = ctx;
		trxName = ITrx.TRXNAME_None;
	}

	@Override
	protected PreparedStatement createPreparedStatement() throws SQLException
	{
		return DB.prepareStatement(sql, trxName);
	}

	@Override
	protected HUToProcess fetch(final ResultSet rs) throws SQLException
	{
		final HUToProcess row = new HUToProcess();
		row.setRaw_Product(retrieveModel(rs, COLUMNNAME_Raw_Product_ID, I_M_Product.class));
		row.setPP_Product_BOMLine(retrieveModel(rs, COLUMNNAME_PP_Product_BOMLine_ID, I_PP_Product_BOMLine.class));
		row.setMovementDate(rs.getTimestamp(COLUMNNAME_MovementDate));
		row.setM_HU(retrieveModel(rs, COLUMNNAME_M_HU_ID, I_M_HU.class));
		row.setManufacturing_Warehouse(retrieveModel(rs, COLUMNNAME_Manufacturing_Warehouse_ID, I_M_Warehouse.class));
		row.setManufacturing_Plant(retrieveModel(rs, COLUMNNAME_Manufacturing_Plant_ID, I_S_Resource.class));
		row.setManufacturing_Workflow_ID(rs.getInt(COLUMNNAME_Manufacturing_Workflow_ID));
		row.setReference(rs.getString(COLUMNNAME_Reference));
		return row;
	}

	private final <T> T retrieveModel(final ResultSet rs, final String columnName, final Class<T> modelClass) throws SQLException
	{
		final int id = rs.getInt(columnName);
		final T model = InterfaceWrapperHelper.create(ctx, id, modelClass, ITrx.TRXNAME_ThreadInherited);
		return model;
	}
}
