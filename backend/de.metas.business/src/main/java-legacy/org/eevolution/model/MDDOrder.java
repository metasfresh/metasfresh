/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.model;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.order.DeliveryRule;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;
import de.metas.product.ProductId;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Order Distribution Model. Please do not set DocStatus and C_DocType_ID directly. They are set in the process() method. Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>Original contributor of Distribution Functionality <li>FR [ 2520591 ] Support multiples calendar for Org
 */
public class MDDOrder extends X_DD_Order implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2407222565384020843L;

	public MDDOrder(Properties ctx, int DD_Order_ID, String trxName)
	{
		super(ctx, DD_Order_ID, trxName);
		if (is_new())
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction(DOCACTION_Prepare);
			//
			setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());
			setFreightCostRule(FREIGHTCOSTRULE_FreightIncluded);
			setPriorityRule(PRIORITYRULE_Medium);
			setDeliveryViaRule(DELIVERYVIARULE_Pickup);
			//
			setIsSelected(false);
			setIsSOTrx(true);
			setIsDropShip(false);
			setSendEMail(false);
			//
			setIsApproved(false);
			setIsPrinted(false);
			setIsDelivered(false);
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);

			setDatePromised(new Timestamp(System.currentTimeMillis()));
			setDateOrdered(new Timestamp(System.currentTimeMillis()));

			setFreightAmt(Env.ZERO);
			setChargeAmt(Env.ZERO);

		}
	}	// MDDOrder


	public MDDOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Order Lines */
	private MDDOrderLine[] m_lines = null;

	private void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}	// addDescription

	/**
	 * Set Business Partner Defaults & Details. SOTrx should be set.
	 *
	 * @param bp business partner
	 */
	public void setBPartner(I_C_BPartner bp)
	{
		if (bp == null)
		{
			return;
		}

		setC_BPartner_ID(bp.getC_BPartner_ID());
		// Defaults Payment Term
		int ii = 0;
		if (isSOTrx())
		{
			ii = bp.getC_PaymentTerm_ID();
		}
		else
		{
			ii = bp.getPO_PaymentTerm_ID();
		}

		// Default Price List
		if (isSOTrx())
		{
			ii = bp.getM_PriceList_ID();
		}
		else
		{
			ii = bp.getPO_PriceList_ID();
		}
		// Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (ss != null)
		{
			setDeliveryRule(ss);
		}
		ss = bp.getDeliveryViaRule();
		if (ss != null)
		{
			setDeliveryViaRule(ss);
		}

		if (getSalesRep_ID() == 0)
		{
			ii = Env.getAD_User_ID(getCtx());
			if (ii != 0)
			{
				setSalesRep_ID(ii);
			}
		}

		// Set Locations
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final List<I_C_BPartner_Location> locs = bpartnersRepo.retrieveBPartnerLocations(bp);
		if (locs != null)
		{
			for (I_C_BPartner_Location loc : locs)
			{
				if (loc.isShipTo())
				{
					super.setC_BPartner_Location_ID(loc.getC_BPartner_Location_ID());
				}
			}
			// set to first
			if (getC_BPartner_Location_ID() <= 0 && !locs.isEmpty())
			{
				super.setC_BPartner_Location_ID(locs.get(0).getC_BPartner_Location_ID());
			}
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			log.error("MDDOrder.setBPartner - Has no Ship To Address: " + bp);
		}

		// Set Contact
		final List<I_AD_User> contacts = bpartnersRepo.retrieveContacts(bp);
		if (contacts != null && contacts.size() == 1)
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	}	// setBPartner

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MDDOrder[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	// getDocumentInfo

	@Override
	public File createPDF()
	{
		final DocumentReportService documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
		final ReportResultData report = documentReportService.createStandardDocumentReportData(getCtx(), StandardDocumentReportType.DISTRIBUTION_ORDER, getDD_Order_ID());
		return report.writeToTemporaryFile(get_TableName() + get_ID());
	}

	/**************************************************************************
	 * Get Lines of Order
	 *
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return lines
	 */
	private MDDOrderLine[] getLines(String whereClause, String orderClause)
	{
		StringBuffer whereClauseFinal = new StringBuffer(MDDOrderLine.COLUMNNAME_DD_Order_ID).append("=?");
		if (!Check.isEmpty(whereClause, true))
		{
			whereClauseFinal.append("AND (").append(whereClause).append(")");
		}
		//
		List<MDDOrderLine> list = new Query(getCtx(), MDDOrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
				.setParameters(new Object[] { getDD_Order_ID() })
				.setOrderBy(orderClause)
				.list(MDDOrderLine.class);
		return list.toArray(new MDDOrderLine[list.size()]);
	}	// getLines

	/**
	 * Get Lines of Order
	 *
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	private MDDOrderLine[] getLines(boolean requery, final String orderBy)
	{
		// NOTE: requery is a legacy parameter and it shall always be true
		if (!requery)
		{
			final AdempiereException ex = new AdempiereException("requery parameter shall be true");
			log.warn(ex.getLocalizedMessage() + ". Considering it as true", ex);
			requery = true;
		}

		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
		{
			orderClause += orderBy;
		}
		else
		{
			orderClause += I_DD_OrderLine.COLUMNNAME_Line;
		}

		m_lines = getLines(null, orderClause);
		return m_lines;
	}	// getLines


	/**
	 * Set Processed. Propagate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);

		if (getDD_Order_ID() <= 0)
		{
			return;
		}

		//
		// Flag DD_OrderLines as processed, right now
		{
			final String sql = "SET Processed='"
					+ (processed ? "Y" : "N")
					+ "' WHERE DD_Order_ID=" + getDD_Order_ID();
			final int noLine = DB.executeUpdateAndSaveErrorOnFail("UPDATE DD_OrderLine " + sql, get_TrxName());
			log.debug("setProcessed - " + processed + " - Lines=" + noLine);

			m_lines = null; // reset cached lines
		}

	}	// setProcessed

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Client/Org Check
		if (getAD_Org_ID() == 0)
		{
			int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID != 0)
			{
				setAD_Org_ID(context_AD_Org_ID);
				log.warn("Changed Org to Context={}", context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() <= 0)
		{
			throw new FillMandatoryException(I_DD_Order.COLUMNNAME_AD_Client_ID);
		}

		// New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
		{
			setC_DocType_ID(0);
		}

		// Default Warehouse
		if (getM_Warehouse_ID() <= 0)
		{
			final int warehouseId = Env.getContextAsInt(getCtx(), Env.CTXNAME_M_Warehouse_ID);
			if (warehouseId > 0)
			{
				setM_Warehouse_ID(warehouseId);
			}
			else
			{
				throw new FillMandatoryException(COLUMNNAME_M_Warehouse_ID);
			}
		}
		// Reservations in Warehouse
		if (!newRecord && is_ValueChanged(COLUMNNAME_M_Warehouse_ID))
		{
			final MDDOrderLine[] lines = getLines(true, null);
			for (MDDOrderLine line : lines)
			{
				if (!line.canChangeWarehouse())
				{
					throw new AdempiereException("Cannot change warehouse because of " + line); // TODO TRL
				}
			}
		}

		// No Partner Info - set Template
		if (getC_BPartner_ID() <= 0)
		{
			throw new FillMandatoryException(I_DD_Order.COLUMNNAME_C_BPartner_ID);
		}
		if (getC_BPartner_Location_ID() <= 0)
		{
			setBPartner(Services.get(IBPartnerDAO.class).getById(getC_BPartner_ID()));
		}

		// Default Sales Rep
		if (getSalesRep_ID() <= 0)
		{
			final int loggedUserId = Env.getAD_User_ID(getCtx());
			if (loggedUserId > 0)
			{
				setSalesRep_ID(loggedUserId);
			}
		}

		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success || newRecord)
		{
			return success;
		}

		// Propagate Description changes
		if (is_ValueChanged("Description") || is_ValueChanged("POReference"))
		{
			final String sql = DB.convertSqlToNative("UPDATE M_Movement i"
					+ " SET (Description,POReference)="
					+ "(SELECT Description,POReference "
					+ "FROM DD_Order o WHERE i.DD_Order_ID=o.DD_Order_ID) "
					+ "WHERE DocStatus NOT IN ('RE','CL') AND DD_Order_ID=" + getDD_Order_ID());
			int no = DB.executeUpdateAndThrowExceptionOnFail(sql, get_TrxName());
			log.debug("Description -> #" + no);
		}

		// Sync Lines
		afterSaveSync("AD_Org_ID");
		// afterSaveSync("C_BPartner_ID"); task 07653 the column is not existing
		// afterSaveSync("C_BPartner_Location_ID"); task 07653 the column is not existing
		afterSaveSync("DateOrdered");
		afterSaveSync("DatePromised");
		// afterSaveSync("M_Shipper_ID"); task 07653 the column is not existing
		//
		return true;
	}	// afterSave

	private void afterSaveSync(String columnName)
	{
		if (is_ValueChanged(columnName))
		{
			String sql = "UPDATE DD_OrderLine ol"
					+ " SET " + columnName + " ="
					+ "(SELECT " + columnName
					+ " FROM DD_Order o WHERE ol.DD_Order_ID=o.DD_Order_ID) "
					+ "WHERE DD_Order_ID=" + getDD_Order_ID();
			int no = DB.executeUpdateAndThrowExceptionOnFail(sql, get_TrxName());
			log.debug(columnName + " Lines -> #" + no);
		}
	}	// afterSaveSync

	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}	// processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}	// unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		setDocAction(DOCACTION_Prepare);
		return true;
	}	// invalidateIt

	/**************************************************************************
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Std Period open?
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		MPeriod.testPeriodOpen(getCtx(), getDateOrdered(), DocBaseType.ofCode(dt.getDocBaseType()), getAD_Org_ID());

		// Lines
		final MDDOrderLine[] lines = getLines(true, I_DD_OrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			throw AdempiereException.noLines();
		}

		// Bug 1564431
		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(getDeliveryRule());
		if (DeliveryRule.COMPLETE_ORDER.equals(deliveryRule))
		{
			final IProductBL productBL = Services.get(IProductBL.class);

			for (final I_DD_OrderLine line : lines)
			{
				final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());
				final I_M_Product product = productBL.getById(productId);
				if (product != null && product.isExcludeAutoDelivery())
				{
					m_processMsg = "@M_Product_ID@ " + product.getValue() + " @IsExcludeAutoDelivery@";
					return IDocument.STATUS_Invalid;
				}
			}
		}

		// Mandatory Product Attribute Set Instance
		String mandatoryType = "='Y'";	// IN ('Y','S')
		String sql = "SELECT COUNT(*) "
				+ "FROM DD_OrderLine ol"
				+ " INNER JOIN M_Product p ON (ol.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_AttributeSet pas ON (p.M_AttributeSet_ID=pas.M_AttributeSet_ID) "
				+ "WHERE pas.MandatoryType " + mandatoryType
				+ " AND ol.M_AttributeSetInstance_ID IS NULL"
				+ " AND ol.DD_Order_ID=?";
		int no = DB.getSQLValue(get_TrxName(), sql, getDD_Order_ID());
		if (no != 0)
		{
			m_processMsg = "@LinesWithoutProductAttribute@ (" + no + ")";
			return IDocument.STATUS_Invalid;
		}

		reserveStock(lines);

		// From this point on, don't allow MRP to remove this document
		setMRP_AllowCleanup(false);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		return IDocument.STATUS_InProgress;
	}	// prepareIt

	/**
	 * Reserve Inventory. Counterpart: MMovement.completeIt()
	 *
	 * @param lines distribution order lines (ordered by M_Product_ID for deadlock prevention)
	 * @return true if (un) reserved
	 */
	private void reserveStock(MDDOrderLine[] lines)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		BigDecimal Volume = BigDecimal.ZERO;
		BigDecimal Weight = BigDecimal.ZERO;

		// Always check and (un) Reserve Inventory
		for (MDDOrderLine line : lines)
		{
			final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());
			final I_M_Product product = productBL.getById(productId);

			final int fromLocatorId = line.getM_Locator_ID();
			final WarehouseId fromWarehouseId = warehousesRepo.getWarehouseIdByLocatorRepoId(fromLocatorId);
			final int toLocatorId = line.getM_LocatorTo_ID();
			final WarehouseId toWarehouseId = warehousesRepo.getWarehouseIdByLocatorRepoId(toLocatorId);
			BigDecimal reserved_ordered = line.getQtyOrdered()
					.subtract(line.getQtyReserved())
					.subtract(line.getQtyDelivered());
			if (reserved_ordered.signum() == 0)
			{
				if (product != null)
				{
					Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
					Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
				}
				continue;
			}

			// Check Product - Stocked and Item
			if (product != null)
			{
				if (productBL.isStocked(product))
				{
					// Update Storage
					final IStorageBL storageBL = Services.get(IStorageBL.class);
					storageBL.add(getCtx(),
							fromWarehouseId.getRepoId(),
							fromLocatorId,
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(), line.getM_AttributeSetInstance_ID(),
							BigDecimal.ZERO, reserved_ordered, BigDecimal.ZERO, get_TrxName());

					storageBL.add(getCtx(),
							toWarehouseId.getRepoId(),
							toLocatorId,
							line.getM_Product_ID(),
							line.getM_AttributeSetInstanceTo_ID(), line.getM_AttributeSetInstance_ID(),
							BigDecimal.ZERO, BigDecimal.ZERO, reserved_ordered, get_TrxName());
				}
				
				// update line
				line.setQtyReserved(line.getQtyReserved().add(reserved_ordered));
				line.saveEx(get_TrxName());
				//
				Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
				Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
			}	// product
		}	// reverse inventory

		setVolume(Volume);
		setWeight(Weight);
	}	// reserveStock

	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	}	// approveIt

	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}	// rejectIt

	@Override
	public String completeIt()
	{
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return IDocument.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		//
		// Call Model Validator: BEFORE_COMPLETE
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		//
		// Process Lines
		final MDDOrderLine[] lines = getLines(true, null);
		for (final I_DD_OrderLine line : lines)
		{
			line.setProcessed(true);
			InterfaceWrapperHelper.save(line);
		}

		//
		// Call Model Validator: AFTER_COMPLETE
		final StringBuilder info = new StringBuilder();
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			if (info.length() > 0)
			{
				info.append(" - ");
			}
			info.append(valid);
			m_processMsg = info.toString();
			return IDocument.STATUS_Invalid;
		}

		//
		// Update Document Status and return new status: Completed
		setProcessed(true);
		m_processMsg = info.toString();
		setDocAction(DOCACTION_Close);
		return IDocument.STATUS_Completed;
	}	// completeIt

	@Override
	public boolean voidIt()
	{
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final MDDOrderLine[] lines = getLines(true, I_DD_OrderLine.COLUMNNAME_M_Product_ID);
		for (MDDOrderLine line : lines)
		{
			BigDecimal old = line.getQtyOrdered();
			if (old.signum() != 0)
			{
				line.addDescription(msgBL.getMsg(getCtx(), "Voided") + " (" + old + ")");
				line.setProcessed(true);
				line.save(get_TrxName());
			}
		}

		addDescription(msgBL.getMsg(getCtx(), "Voided"));

		// Clear Reservations
		reserveStock(lines);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// Close Not delivered Qty - SO/PO
		final MDDOrderLine[] lines = getLines(true, "M_Product_ID");
		for (final MDDOrderLine line : lines)
		{
			final BigDecimal qtyOrderedOld = line.getQtyOrdered();
			if (qtyOrderedOld.compareTo(line.getQtyDelivered()) != 0)
			{
				line.setQtyOrdered(line.getQtyDelivered());
				// QtyEntered unchanged
				line.addDescription("Close (" + qtyOrderedOld + ")");
				line.setProcessed(true);
				line.save(get_TrxName());
			}
		}

		// Clear Reservations
		reserveStock(lines);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		return voidIt();
	}	// reverseCorrectionIt

	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}	// reverseAccrualIt

	@Override
	public boolean reActivateIt()
	{
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}	// reActivateIt

	@Override
	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());

		if (m_lines != null)
		{
			sb.append(" (#").append(m_lines.length).append(")");
		}
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}	// getSummary

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getCreated(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}	// getDoc_User_ID

	@Override
	public BigDecimal getApprovalAmt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_Currency_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		final String docStatus = getDocStatus();
		return DOCSTATUS_Completed.equals(docStatus)
				|| DOCSTATUS_Closed.equals(docStatus)
				|| DOCSTATUS_Reversed.equals(docStatus);
	}

}	// MDDOrder
