/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package org.eevolution.model;

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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MProject;
import org.compiere.model.MResource;
import org.compiere.model.MStorage;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.exceptions.LiberoException;

import de.metas.product.IProductBL;

/**
 * PP Order Model.
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPOrder extends X_PP_Order implements DocAction
{
	private static final long serialVersionUID = 1L;

	/**
	 * get if Component is Available
	 *
	 * @param MPPOrdrt Manufacturing order
	 * @param issues
	 * @param minGuaranteeDate Guarantee Date
	 * @return true when the qty available is enough
	 */
	public static boolean isQtyAvailable(MPPOrder order, final Map<Integer, PPOrderBOMLineModel> issue, Timestamp minGuaranteeDate)
	{
		boolean isCompleteQtyDeliver = false;
		for (int i = 0; i < issue.size(); i++)
		{
			final PPOrderBOMLineModel bomLineModel = issue.get(i);
			final KeyNamePair key = bomLineModel.getKnp();
			boolean isSelected = key.getName().equals("Y");
			if (key == null || !isSelected)
			{
				continue;
			}

			final String value = bomLineModel.getValue();
			final int M_Product_ID = bomLineModel.getM_Product_ID();
			final BigDecimal qtyToDeliver = bomLineModel.getQtyToDeliver();
			final BigDecimal qtyScrapComponent = bomLineModel.getQtyScrapComponent();

			final I_M_Product product = InterfaceWrapperHelper.create(order.getCtx(), I_M_Product.Table_Name, M_Product_ID, I_M_Product.class, order.get_TrxName());

			if (product != null && Services.get(IProductBL.class).isStocked(product))
			{
				int M_AttributeSetInstance_ID = 0;
				if (value == null && isSelected)
				{
					M_AttributeSetInstance_ID = key.getKey();
				}
				else if (value != null && isSelected)
				{
					int PP_Order_BOMLine_ID = key.getKey();
					if (PP_Order_BOMLine_ID > 0)
					{
						final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(order.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, order.get_TrxName());
						// Validate if AttributeSet generate instance
						M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
					}
				}

				MStorage[] storages = MPPOrder.getStorages(order.getCtx(),
						M_Product_ID,
						order.getM_Warehouse_ID(),
						M_AttributeSetInstance_ID,
						minGuaranteeDate, order.get_TrxName());

				if (M_AttributeSetInstance_ID == 0)
				{
					BigDecimal toIssue = qtyToDeliver.add(qtyScrapComponent);
					for (MStorage storage : storages)
					{
						// TODO Selection of ASI
						if (storage.getQtyOnHand().signum() == 0)
							continue;
						BigDecimal issueActual = toIssue.min(storage.getQtyOnHand());
						toIssue = toIssue.subtract(issueActual);
						if (toIssue.signum() <= 0)
							break;
					}
				}
				else
				{
					BigDecimal qtydelivered = qtyToDeliver;
					qtydelivered.setScale(4, BigDecimal.ROUND_HALF_UP);
					qtydelivered = Env.ZERO;
				}

				BigDecimal onHand = Env.ZERO;
				for (MStorage storage : storages)
				{
					onHand = onHand.add(storage.getQtyOnHand());
				}

				isCompleteQtyDeliver = onHand.compareTo(qtyToDeliver.add(qtyScrapComponent)) >= 0;
				if (!isCompleteQtyDeliver)
					break;

			}
		} // for each line

		return isCompleteQtyDeliver;
	}

	public static MStorage[] getStorages(
			Properties ctx,
			int M_Product_ID,
			int M_Warehouse_ID,
			int M_ASI_ID,
			Timestamp minGuaranteeDate, String trxName)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, M_Product_ID, I_M_Product.class, ITrx.TRXNAME_None);
		if (product != null && Services.get(IProductBL.class).isStocked(product))
		{
			String MMPolicy = Services.get(IProductBL.class).getMMPolicy(product);

			// Validate if AttributeSet of product generated instance
			if (product.getM_AttributeSetInstance_ID() == 0)
			{
				return MStorage.getWarehouse(ctx,
						M_Warehouse_ID,
						M_Product_ID,
						M_ASI_ID,
						minGuaranteeDate,
						MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName
						);
			}
			else
			{
				// TODO: vpj-cd Create logic to get storage that matched with attribure set that not create instances
				return MStorage.getWarehouse(ctx,
						M_Warehouse_ID,
						M_Product_ID,
						0,
						minGuaranteeDate,
						MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName
						);
			}

		}
		else
		{
			return new MStorage[0];
		}
	}

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param PP_Order_ID order to load, (0 create new order)
	 */
	public MPPOrder(Properties ctx, int PP_Order_ID, String trxName)
	{
		super(ctx, PP_Order_ID, trxName);
		// New
		if (PP_Order_ID == 0)
		{
			setDefault();
		}
	} // PP_Order

	/**************************************************************************
	 * Project Constructor
	 *
	 * @param project Project to create Order from
	 * @param DocSubType if SO DocType Target (default DocSubType_OnCredit)
	 */
	public MPPOrder(MProject project, int PP_Product_BOM_ID, int AD_Workflow_ID)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		setLine(10);
		setPriorityRule(MPPOrder.PRIORITYRULE_Medium);
		if (project.getDateContract() == null)
			throw new IllegalStateException("Date Contract is mandatory for Manufacturing Order.");
		if (project.getDateFinish() == null)
			throw new IllegalStateException("Date Finish is mandatory for Manufacturing Order.");

		Timestamp ts = project.getDateContract();
		Timestamp df = project.getDateContract();

		if (ts != null)
			setDateOrdered(ts);
		if (ts != null)
			this.setDateStartSchedule(ts);
		ts = project.getDateFinish();
		if (df != null)
			setDatePromised(df);
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		setPP_Product_BOM_ID(PP_Product_BOM_ID);
		setAD_Workflow_ID(AD_Workflow_ID);
		setQtyEntered(Env.ONE);
		setQtyOrdered(Env.ONE);
		MPPProductBOM bom = new MPPProductBOM(project.getCtx(), PP_Product_BOM_ID, project.get_TrxName());
		final I_M_Product product = bom.getM_Product();
		setC_UOM_ID(product.getC_UOM_ID());

		setM_Product_ID(bom.getM_Product_ID());

		String where = MResource.COLUMNNAME_IsManufacturingResource + " = 'Y' AND " +
				MResource.COLUMNNAME_ManufacturingResourceType + " = '" + MResource.MANUFACTURINGRESOURCETYPE_Plant + "' AND " +
				MResource.COLUMNNAME_M_Warehouse_ID + " = " + project.getM_Warehouse_ID();
		MResource resource = (MResource)TableModelLoader.instance.getPO(project.getCtx(), MResource.Table_Name, where, project.get_TrxName());
		if (resource == null)
			throw new IllegalStateException("Resource is mandatory.");
		setS_Resource_ID(resource.getS_Resource_ID());
	} // MOrder

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 */
	public MPPOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	} // MOrder

	/**
	 * Get BOM Lines of PP Order
	 *
	 * @param requery
	 * @return Order BOM Lines
	 */
	private List<I_PP_Order_BOMLine> getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			for (final I_PP_Order_BOMLine line : m_lines)
			{
				InterfaceWrapperHelper.setTrxName(line, get_TrxName());
			}
			return m_lines;
		}

		m_lines = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(this);
		return m_lines;
	}

	private List<I_PP_Order_BOMLine> m_lines = null;

	/**
	 * Get Order BOM Lines
	 *
	 * @return Order BOM Lines
	 */
	private List<I_PP_Order_BOMLine> getLines()
	{
		return getLines(true);
	}

	public void setC_DocTypeTarget_ID(String docBaseType)
	{
		if (getC_DocTypeTarget_ID() > 0)
			return;

		MDocType[] doc = MDocType.getOfDocBaseType(getCtx(), docBaseType);
		if (doc == null)
		{
			throw new DocTypeNotFoundException(docBaseType, "");
		}
		else
		{
			setC_DocTypeTarget_ID(doc[0].get_ID());
		}
	}

	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);

		// FIXME: do we still need this?
		// Update DB:
		if (get_ID() <= 0)
			return;
		final String sql = "UPDATE PP_Order SET Processed=? WHERE PP_Order_ID=?";
		DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
	} // setProcessed

	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	@Override
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	} // unlockIt

	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	@Override
	public String prepareIt()
	{
		log.info(toString());
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

		//
		// Call Model Validator: BEFORE_PREPARE
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		//
		// Validate BOM Lines
		final List<I_PP_Order_BOMLine> lines = getLines(true);
		if (lines.isEmpty())
		{
			throw new LiberoException("@NoLines@");
		}

		//
		// Cannot change Std to anything else if different warehouses
		// NOTE: shall not happen because we are updating BOM Line's warehouse/locator when order's Warehouse/Locator is changed
		if (getC_DocType_ID() > 0)
		{
			for (final I_PP_Order_BOMLine line : lines)
			{
				if (line.getM_Warehouse_ID() != getM_Warehouse_ID())
				{
					throw new LiberoException("@CannotChangeDocType@"
							+ "\n@PP_Order_BOMLine_ID@: " + line
							+ "\n@PP_Order_BOMLine_ID@ @M_Warehouse_ID@: " + line.getM_Warehouse()
							+ "\n@PP_Order_ID@ @M_Warehouse_ID@: " + getM_Warehouse());
				}
			}
		}

		//
		// New or in Progress/Invalid
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0)
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}

		final String docBaseType = MDocType.get(getCtx(), getC_DocType_ID()).getDocBaseType();
		if (X_C_DocType.DOCBASETYPE_QualityOrder.equals(docBaseType))
		{
			; // nothing
		}
		// ManufacturingOrder, MaintenanceOrder
		else
		{
			Services.get(IPPOrderBOMBL.class).reserveStock(lines);

			ppOrderBL.setForceQtyReservation(this, true);
			ppOrderBL.orderStock(this);
		}

		// From this point on, don't allow MRP to remove this document
		setMRP_AllowCleanup(false);

		//
		// Call Model Validator: AFTER_PREPARE
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		//
		// Update Document Status and return new status "InProgress"
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	} // prepareIt

	@Override
	public boolean approveIt()
	{
		log.info("approveIt - " + toString());
		MDocType doc = MDocType.get(getCtx(), getC_DocType_ID());
		if (doc.getDocBaseType().equals(X_C_DocType.DOCBASETYPE_QualityOrder))
		{
			String whereClause = COLUMNNAME_PP_Product_BOM_ID + "=? AND " + COLUMNNAME_AD_Workflow_ID + "=?";
			MQMSpecification qms = new Query(getCtx(), MQMSpecification.Table_Name, whereClause, get_TrxName())
					.setParameters(new Object[] { getPP_Product_BOM_ID(), getAD_Workflow_ID() })
					.firstOnly();
			return qms != null ? qms.isValid(getM_AttributeSetInstance_ID()) : true;
		}
		else
		{
			setIsApproved(true);
		}

		return true;
	} // approveIt

	@Override
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	@Override
	public String completeIt()
	{
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		//
		// Call Model Validator: BEFORE_COMPLETE
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		//
		// Mark BOM Lines as processed
		final List<I_PP_Order_BOMLine> orderBOMLines = getLines(true);
		for (final I_PP_Order_BOMLine orderBOMLine : orderBOMLines)
		{
			orderBOMLine.setProcessed(true);
			InterfaceWrapperHelper.save(orderBOMLine);
		}

		//
		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		//
		// Copy cost records from M_Cost to PP_Order_Cost
		Services.get(IPPOrderCostBL.class).createStandardCosts(this);

		//
		// Auto receipt and issue for kit
		final I_PP_Order_BOM ppOrderBOM = getPP_Order_BOM();
		if (X_PP_Order_BOM.BOMTYPE_Make_To_Kit.equals(ppOrderBOM.getBOMType())
				&& X_PP_Order_BOM.BOMUSE_Manufacturing.equals(ppOrderBOM.getBOMUse()))
		{
			completeMakeToKit();
			return DOCSTATUS_Closed;
		}

		//
		// Create the Activity Control
		autoReportActivities();

		//
		// Update Document Status
		// NOTE: we need to have it Processed=Yes before calling triggering AFTER_COMPLETE model validator event
		setProcessed(true);
		setDocAction(DOCACTION_Close);

		//
		// Call Model Validator: AFTER_COMPLETE
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//
		// Return new document status: Completed
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * Complete/Process the manufacturing order which is dealing with a Make-To-Kit BOM.
	 *
	 * NOTE: in this case we need a special was of completing it. After this method, the document can be automatically marked as closed.
	 */
	private void completeMakeToKit()
	{
		final Timestamp today = SystemTime.asTimestamp();

		// metas : cg task: 06004 - refactored a bit : start
		final Map<Integer, PPOrderBOMLineModel> issue = new HashMap<>();

		for (final I_PP_Order_BOMLine line : getLines())
		{
			KeyNamePair id = null;

			if (X_PP_Order_BOMLine.ISSUEMETHOD_Backflush.equals(line.getIssueMethod()))
			{
				id = new KeyNamePair(line.getPP_Order_BOMLine_ID(), "Y"); // 0 - MPPOrderBOMLine ID
			}
			else
			{
				id = new KeyNamePair(line.getPP_Order_BOMLine_ID(), "N"); // 0 - MPPOrderBOMLine ID
			}

			// final boolean isCritical = line.isCritical(); //1 - Critical not used
			final I_M_Product product = line.getM_Product();
			final String value = product.getValue(); // 2 - Value
			final int M_Product_ID = product.getM_Product_ID(); // 3 - Product id
			final BigDecimal qtyToDeliver = line.getQtyRequiered(); // 4 - QtyToDeliver
			final BigDecimal qtyScrapComponent = Env.ZERO; // 5 - QtyScrapComponent

			final PPOrderBOMLineModel bomLineModel = new PPOrderBOMLineModel(id, line.isCritical(), value, M_Product_ID, qtyToDeliver, qtyScrapComponent);
			//
			final int i = issue.size();
			issue.put(i, bomLineModel);
		}

		// metas : cg task: 06004 - refactor a bit : end

		boolean forceIssue = false;
		final I_C_OrderLine oline = getC_OrderLine();
		final String orderDeliveryRule = oline.getC_Order().getDeliveryRule();
		if (X_C_Order.DELIVERYRULE_CompleteLine.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_CompleteOrder.equals(orderDeliveryRule))
		{
			final boolean isCompleteQtyDeliver = isQtyAvailable(this, issue, today);
			if (!isCompleteQtyDeliver)
			{
				throw new LiberoException("@NoQtyAvailable@");
			}
		}
		else if (X_C_Order.DELIVERYRULE_Availability.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_AfterReceipt.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_Manual.equals(orderDeliveryRule))
		{
			throw new LiberoException("@ActionNotSupported@");
		}
		else if (X_C_Order.DELIVERYRULE_Force.equals(orderDeliveryRule))
		{
			forceIssue = true;
		}

		for (int i = 0; i < issue.size(); i++)
		{
			final PPOrderBOMLineModel model = issue.get(i);

			int M_AttributeSetInstance_ID = 0;
			final KeyNamePair key = model.getKnp();
			// final String value = model.getValue(); not used
			final int M_Product_ID = model.getM_Product_ID();
			// final I_M_Product product = InterfaceWrapperHelper.create(getCtx(), I_M_Product.Table_Name, M_Product_ID, I_M_Product.class, get_TrxName()); // not used
			final BigDecimal qtyToDeliver = model.getQtyToDeliver();
			BigDecimal qtyScrapComponent = model.getQtyScrapComponent();

			int PP_Order_BOMLine_ID = key.getKey();
			if (PP_Order_BOMLine_ID > 0)
			{
				final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, get_TrxName());
				// Validate if AttributeSet generate instance
				M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
			}

			MStorage[] storages = MPPOrder.getStorages(getCtx(),
					M_Product_ID,
					getM_Warehouse_ID(),
					M_AttributeSetInstance_ID
					, today, get_TrxName());

			MPPOrder.createIssue(
					this,
					key.getKey(),
					today, qtyToDeliver,
					qtyScrapComponent,
					Env.ZERO,
					storages, forceIssue);
		}

		final BigDecimal qtyToReceive = Services.get(IPPOrderBL.class).getQtyOpen(this);

		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IReceiptCostCollectorCandidate candidate = ppCostCollectorBL.createReceiptCostCollectorCandidate();
		candidate.setPP_Order(this);
		candidate.setMovementDate(today);
		candidate.setQtyToReceive(qtyToReceive);
		candidate.setQtyScrap(getQtyScrap());
		candidate.setQtyReject(getQtyReject());
		candidate.setM_Locator_ID(getM_Locator_ID());
		candidate.setM_Product(getM_Product());
		candidate.setC_UOM(getC_UOM());
		candidate.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
		ppCostCollectorBL.createReceipt(candidate);

		setQtyDelivered(qtyToReceive);
	}

	/**
	 * Check if the Quantity from all BOM Lines is available (QtyOnHand >= QtyRequired)
	 *
	 * @return true if entire Qty is available for this Order
	 */
	public boolean isAvailable()
	{
		String whereClause = "QtyOnHand >= QtyRequiered AND PP_Order_ID=?";
		boolean available = new Query(getCtx(), "RV_PP_Order_Storage", whereClause, get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.match();
		return available;
	}

	@Override
	public boolean voidIt()
	{
		log.info(toString());

		//
		// Call Model ValidatorȘ Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		//
		// Make sure there was nothing reported on this manufacturing order
		if (isDelivered())
		{
			throw new LiberoException("Cannot void this document because exist transactions"); // TODO: Create Message for Translation
		}

		//
		// Set QtyRequired=0 on all BOM Lines
		for (final I_PP_Order_BOMLine line : getLines())
		{
			final BigDecimal qtyRequiredOld = line.getQtyRequiered();
			if (qtyRequiredOld.signum() != 0)
			{
				Services.get(IPPOrderBOMBL.class).addDescription(line, Services.get(IMsgBL.class).parseTranslation(getCtx(), "@Voided@ @QtyRequiered@ : (" + qtyRequiredOld + ")"));
				line.setQtyRequiered(BigDecimal.ZERO);
				line.setProcessed(true);
				InterfaceWrapperHelper.save(line);
			}
		}

		//
		// Void all activitions
		getMPPOrderWorkflow().voidActivities();

		//
		// Set QtyOrdered/QtyEntered=0 to ZERO
		final BigDecimal qtyOrderedOld = getQtyOrdered();
		if (qtyOrderedOld.signum() != 0)
		{
			addDescription(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@Voided@ @QtyOrdered@ : (" + qtyOrderedOld + ")"));
			Services.get(IPPOrderBL.class).setQtyOrdered(this, BigDecimal.ZERO);
			Services.get(IPPOrderBL.class).setQtyEntered(this, BigDecimal.ZERO);
			InterfaceWrapperHelper.save(this);
		}

		//
		// Clear Ordered Quantities
		Services.get(IPPOrderBL.class).orderStock(this);

		//
		// Clear BOM Lines Reservations
		Services.get(IPPOrderBOMBL.class).reserveStock(getLines());

		//
		// Call Model Validator: AFTER_VOID
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		//
		// Update document status and return true
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	} // voidIt

	@Override
	public boolean closeIt()
	{
		log.info(toString());

		//
		// Call Model Validator: Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		//
		// Check already closed
		final String docStatus = getDocStatus();
		if (X_PP_Order.DOCSTATUS_Closed.equals(docStatus))
		{
			return true;
		}

		//
		// If DocStatus is not Completed => complete it now
		// TODO: don't know if this approach is ok, i think we shall throw an exception instead
		if (!X_PP_Order.DOCSTATUS_Completed.equals(docStatus))
		{
			String DocStatus = completeIt();
			setDocStatus(DocStatus);
			setDocAction(MPPOrder.ACTION_None);
		}

		// 06946: Commented out for now as a working increment.
		// if (!isDelivered())
		// {
		// throw new LiberoException("Cannot close this document because do not exist transactions"); // TODO: Create Message for Translation
		// }

		//
		// Create usage variances
		createVariances();

		//
		// Update BOM Lines and set QtyRequired=QtyDelivered
		final IPPOrderBOMBL ppOrderBOMLineBL = Services.get(IPPOrderBOMBL.class);
		for (I_PP_Order_BOMLine line : getLines())
		{
			ppOrderBOMLineBL.close(line);
		}

		//
		// Close all the activity do not reported
		final MPPOrderWorkflow ppOrderWorkflow = getMPPOrderWorkflow();
		ppOrderWorkflow.closeActivities(
				ppOrderWorkflow.getLastNode(getAD_Client_ID()), // Current Activity to start from => last activity
				getUpdated(), // MovementDate
				false // stop on first milestone => no
				);

		//
		// Set QtyOrdered=QtyDelivered
		// Clear Ordered Quantities
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		ppOrderBL.closeQtyOrdered(this);

		//
		// Clear BOM Lines Reservations
		// NOTE: at this point we assume QtyRequired==QtyDelivered => QtyReserved(new)=0
		Services.get(IPPOrderBOMBL.class).reserveStock(getLines());

		if (this.getDateDelivered() == null)
		{
			// making sure the column is set, even if there were no receipts
			this.setDateDelivered(SystemTime.asTimestamp());
		}

		//
		// Set Document status.
		// Do this before firing the AFTER_CLOSE events because the interceptors shall see the DocStatus=CLosed, in case some BLs are depending on that.
		setDocStatus(DOCSTATUS_Closed);
		setProcessed(true);
		setDocAction(DOCACTION_None);

		//
		// Call Model Validator: AFTER_CLOSE
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	} // closeIt

	@Override
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return voidIt();
	} // reverseCorrectionIt

	@Override
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		throw new LiberoException("@NotSupported@");
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt()
	{
		if (Services.get(IPPOrderBL.class).isDelivered(this))
		{
			throw new LiberoException("Cannot re activate this document because exist transactions"); // TODO: Create Message for Translation
		}

		//
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		//
		// Iterate Order BOM Lines and un-process them
		for (final I_PP_Order_BOMLine orderBOMLine : Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(this))
		{
			orderBOMLine.setProcessed(false);
			InterfaceWrapperHelper.save(orderBOMLine);
		}

		//
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	} // reActivateIt

	@Override
	public int getDoc_User_ID()
	{
		return getPlanner_ID();
	} // getDoc_User_ID

	@Override
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	} // getApprovalAmt

	@Override
	public int getC_Currency_ID()
	{
		return 0;
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public String getSummary()
	{
		return "" + getDocumentNo() + "/" + getDatePromised();
	}

	@Override
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	} // getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	private File createPDF(File file)
	{
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.MANUFACTURING_ORDER, getPP_Order_ID());
		if (re == null)
			return null;
		return re.getPDF(file);
	} // createPDF

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	} // getDocumentInfo

	public I_PP_Order_BOM getPP_Order_BOM()
	{
		return Services.get(IPPOrderBOMDAO.class).retrieveOrderBOM(this);
	}

	public MPPOrderWorkflow getMPPOrderWorkflow()
	{
		final I_PP_Order_Workflow ppOrderWorkflow = Services.get(IPPOrderBL.class).getPP_Order_Workflow(this);

		//
		// 07619: Preserve the transaction of this PPOrder on the workflow
		InterfaceWrapperHelper.setTrxName(ppOrderWorkflow, get_TrxName());

		return LegacyAdapters.convertToPO(ppOrderWorkflow);
	}

	/**
	 * Create Issue
	 *
	 * @param PP_OrderBOMLine_ID
	 * @param movementdate
	 * @param qty
	 * @param qtyScrap
	 * @param qtyReject
	 * @param storages
	 * @param force Issue
	 *
	 * @deprecated Please use {@link IPPCostCollectorBL#createIssue(I_PP_Order_BOMLine, int, int, java.util.Date, BigDecimal, BigDecimal, BigDecimal, org.compiere.model.I_C_UOM)}.
	 */
	@Deprecated
	public static String createIssue(
			final I_PP_Order order,
			final int PP_Order_BOMLine_ID,
			final Timestamp movementdate,
			final BigDecimal qty,
			final BigDecimal qtyScrap,
			final BigDecimal qtyReject,
			final MStorage[] storages,
			final boolean forceIssue)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("\n");

		if (qty.signum() == 0)
		{
			return sb.toString();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(ctx, PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, trxName);

		BigDecimal toIssue = qty.add(qtyScrap);
		for (final I_M_Storage storage : storages)
		{
			// TODO Selection of ASI

			final BigDecimal qtyOnHand = storage.getQtyOnHand();
			if (qtyOnHand.signum() <= 0)
			{
				continue;
			}

			final BigDecimal qtyIssue = toIssue.min(qtyOnHand);
			// log.debug("ToIssue: " + issue);
			// create record for negative and positive transaction
			if (qtyIssue.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0)
			{
				String CostCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue;
				// Method Variance
				if (orderBOMLine.getQtyBatch().signum() == 0
						&& orderBOMLine.getQtyBOM().signum() == 0)
				{
					CostCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance;
				}
				else if (Services.get(IPPOrderBOMBL.class).isComponentType(orderBOMLine, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product))
				{
					CostCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance;
				}
				//
				final I_PP_Cost_Collector cc = MPPCostCollector.createCollector(
						order, 															// MPPOrder
						orderBOMLine.getM_Product_ID(),									// M_Product_ID
						storage.getM_Locator_ID(),										// M_Locator_ID
						0, // storage.getM_AttributeSetInstance_ID(),					// M_AttributeSetInstance_ID
						order.getS_Resource_ID(),										// S_Resource_ID
						orderBOMLine.getPP_Order_BOMLine_ID(),							// PP_Order_BOMLine_ID
						0,																// PP_Order_Node_ID
						MDocType.getDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector), 	// C_DocType_ID,
						CostCollectorType, 												// Production "-"
						movementdate,													// MovementDate
						qtyIssue, qtyScrap, qtyReject,									// qty,scrap,reject
						0,																// durationSetup
						Env.ZERO														// duration
						);

				sb.append(cc.getDocumentNo());
				sb.append("\n");

			}

			toIssue = toIssue.subtract(qtyIssue);
			if (toIssue.signum() == 0)
			{
				break;
			}
		}
		if (forceIssue && toIssue.signum() != 0)
		{
			final I_PP_Cost_Collector cc = MPPCostCollector.createCollector(
					order, 																	// MPPOrder
					orderBOMLine.getM_Product_ID(),											// M_Product_ID
					orderBOMLine.getM_Locator_ID(),											// M_Locator_ID
					orderBOMLine.getM_AttributeSetInstance_ID(),							// M_AttributeSetInstance_ID
					order.getS_Resource_ID(),												// S_Resource_ID
					orderBOMLine.getPP_Order_BOMLine_ID(),									// PP_Order_BOMLine_ID
					0,																		// PP_Order_Node_ID
					MDocType.getDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector), 	// C_DocType_ID,
					X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, 						// Production "-"
					movementdate,															// MovementDate
					toIssue, Env.ZERO, Env.ZERO,											// qty,scrap,reject
					0, Env.ZERO																// durationSetup,duration
					);

			sb.append(cc.getDocumentNo());
			sb.append("\n");

			toIssue = BigDecimal.ZERO;
		}

		//
		if (toIssue.signum() != 0)
		{
			// should not happen because we validate Qty On Hand on start of this process
			throw new LiberoException("Should not happen toIssue=" + toIssue);
		}

		return sb.toString();
	}

	public static boolean isQtyAvailable(MPPOrder order, I_PP_Order_BOMLine line)
	{
		final I_M_Product product = line.getM_Product();
		if (product == null || !Services.get(IProductBL.class).isStocked(product))
		{
			return true;
		}

		BigDecimal qtyToDeliver = line.getQtyRequiered();
		BigDecimal qtyScrap = line.getQtyScrap();
		BigDecimal qtyRequired = qtyToDeliver.add(qtyScrap);
		BigDecimal qtyAvailable = MStorage.getQtyAvailable(order.getM_Warehouse_ID(), 0,
				line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
				order.get_TrxName());
		return qtyAvailable.compareTo(qtyRequired) >= 0;
	}

	/**
	 * @return true if work was delivered for this MO (i.e. Stock Issue, Stock Receipt, Activity Control Report)
	 */
	@Deprecated
	public boolean isDelivered()
	{
		return Services.get(IPPOrderBL.class).isDelivered(this);
	}

	/**
	 * Set default value and reset values whe copy record
	 */
	public void setDefault()
	{
		setLine(10);
		setPriorityRule(PRIORITYRULE_Medium);
		setDescription("");
		setQtyDelivered(Env.ZERO);
		setQtyReject(Env.ZERO);
		setQtyScrap(Env.ZERO);
		setIsSelected(false);
		setIsSOTrx(false);
		setIsApproved(false);
		setIsPrinted(false);
		setProcessed(false);
		setProcessing(false);
		setPosted(false);
		setC_DocTypeTarget_ID(MDocType.DOCBASETYPE_ManufacturingOrder);
		setC_DocType_ID(getC_DocTypeTarget_ID());
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction(DOCACTION_Complete);
	}

	@Deprecated
	public void addDescription(String description)
	{
		Services.get(IPPOrderBL.class).addDescription(this, description);
	}	// addDescription

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPPOrder[ID=").append(get_ID())
				.append("-DocumentNo=").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append("]");
		return sb.toString();
	}

	/*
	 * Auto report the first Activity and Sub contracting if are Milestone Activity
	 */
	private final void autoReportActivities()
	{
		for (MPPOrderNode activity : getMPPOrderWorkflow().getNodes())
		{
			if (activity.isMilestone())
			{
				if (activity.isSubcontracting() || activity.getPP_Order_Node_ID() == getMPPOrderWorkflow().getPP_Order_Node_ID())
				{
					MPPCostCollector.createCollector(
							this,
							getM_Product_ID(),
							getM_Locator_ID(),
							getM_AttributeSetInstance_ID(),
							getS_Resource_ID(),
							0,
							activity.getPP_Order_Node_ID(),
							MDocType.getDocType(MDocType.DOCBASETYPE_ManufacturingCostCollector),
							MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl,
							getUpdated(),
							activity.getQtyToDeliver(),
							Env.ZERO,
							Env.ZERO,
							0,
							Env.ZERO);
				}
			}
		}
	}

	private void createVariances()
	{
		for (final I_PP_Order_BOMLine line : getLines(true))
		{
			createUsageVariance(line);
		}
		m_lines = null; // needs to be requeried
		//
		MPPOrderWorkflow orderWorkflow = getMPPOrderWorkflow();
		if (orderWorkflow != null)
		{
			for (MPPOrderNode node : orderWorkflow.getNodes(true))
			{
				createUsageVariance(node);
			}
		}
		// orderWorkflow.m_nodes = null; // TODO: reset nodes cache
	}

	private void createUsageVariance(final I_PP_Order_BOMLine line)
	{
		MPPOrder order = this;
		Timestamp movementDate = order.getUpdated();

		// If QtyBatch and QtyBOM is zero, than this is a method variance
		// (a product that "was not" in BOM was used)
		if (line.getQtyBatch().signum() == 0 && line.getQtyBOM().signum() == 0)
		{
			return;
		}
		// 06005
		if (Services.get(IPPOrderBOMBL.class).isComponentType(line, X_PP_Order_BOMLine.COMPONENTTYPE_Variant))
		{
			return;
		}

		final BigDecimal qtyUsageVariancePrev = Services.get(IPPOrderBOMDAO.class).retrieveQtyUsageVariance(line); // Previous booked usage variance
		final BigDecimal qtyOpen = Services.get(IPPOrderBOMBL.class).getQtyToIssue(line);
		// Current usage variance = QtyOpen - Previous Usage Variance
		final BigDecimal qtyUsageVariance = qtyOpen.subtract(qtyUsageVariancePrev);
		//
		if (qtyUsageVariance.signum() == 0)
		{
			return;
		}
		// Get Locator
		int M_Locator_ID = line.getM_Locator_ID();
		if (M_Locator_ID <= 0)
		{
			final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(order.getM_Warehouse());
			if (locator != null)
			{
				M_Locator_ID = locator.getM_Locator_ID();
			}
		}
		//
		MPPCostCollector.createCollector(
				order,
				line.getM_Product_ID(),
				M_Locator_ID,
				line.getM_AttributeSetInstance_ID(),
				order.getS_Resource_ID(),
				line.getPP_Order_BOMLine_ID(),
				0, // PP_Order_Node_ID,
				MDocType.getDocType(MDocType.DOCBASETYPE_ManufacturingCostCollector), // C_DocType_ID,
				MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance,
				movementDate,
				qtyUsageVariance, // Qty
				Env.ZERO, // scrap,
				Env.ZERO, // reject,
				0, // durationSetup,
				Env.ZERO // duration
				);
	}

	private void createUsageVariance(I_PP_Order_Node orderNode)
	{
		MPPOrder order = this;
		final Timestamp movementDate = order.getUpdated();
		final MPPOrderNode node = (MPPOrderNode)orderNode;
		//
		final BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
		final BigDecimal durationReal = BigDecimal.valueOf(node.getDurationReal());
		if (setupTimeReal.signum() == 0 && durationReal.signum() == 0)
		{
			// nothing reported on this activity => it's not a variance, this will be auto-reported on close
			return;
		}
		//
		final BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
		final BigDecimal durationVariancePrev = node.getDurationUsageVariance();
		final BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequiered());
		final BigDecimal durationRequired = BigDecimal.valueOf(node.getDurationRequiered());
		final BigDecimal qtyOpen = node.getQtyToDeliver();
		//
		final BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
		final BigDecimal durationVariance = durationRequired.subtract(durationReal).subtract(durationVariancePrev);
		//
		if (qtyOpen.signum() == 0 && setupTimeVariance.signum() == 0 && durationVariance.signum() == 0)
		{
			return;
		}
		//
		MPPCostCollector.createCollector(
				order,
				order.getM_Product_ID(),
				order.getM_Locator_ID(),
				order.getM_AttributeSetInstance_ID(),
				node.getS_Resource_ID(),
				0, // PP_Order_BOMLine_ID
				node.getPP_Order_Node_ID(),
				MDocType.getDocType(MDocType.DOCBASETYPE_ManufacturingCostCollector), // C_DocType_ID
				MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance,
				movementDate,
				qtyOpen, // Qty
				Env.ZERO, // scrap,
				Env.ZERO, // reject,
				setupTimeVariance.intValueExact(), // durationSetup,
				durationVariance // duration
				);
	}
} // MPPOrder
