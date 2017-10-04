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

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MPeriod;
import org.compiere.model.MProject;
import org.compiere.model.MStorage;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;

/**
 * Order Distribution Model. Please do not set DocStatus and C_DocType_ID directly. They are set in the process() method. Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>Original contributor of Distribution Functionality <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MDDOrder extends X_DD_Order implements IDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2407222565384020843L;

	/**
	 * Create new Order by copying
	 * 
	 * @param from order
	 * @param dateDoc date of the document date
	 * @param C_DocTypeTarget_ID target document type
	 * @param isSOTrx sales order
	 * @param counter create counter links
	 * @param copyASI copy line attributes Attribute Set Instance, Resaouce Assignment
	 * @param trxName trx
	 * @return Distribution Order
	 */
	public static MDDOrder copyFrom(MDDOrder from, Timestamp dateDoc,
			int C_DocTypeTarget_ID, boolean isSOTrx, boolean counter, boolean copyASI,
			String trxName)
	{
		MDDOrder to = new MDDOrder(from.getCtx(), 0, trxName);
		to.set_TrxName(trxName);
		PO.copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck("DD_Order_ID", I_ZERO);
		to.set_ValueNoCheck("DocumentNo", null);
		//
		to.setDocStatus(DOCSTATUS_Drafted);		// Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setIsSOTrx(isSOTrx);
		//
		to.setIsSelected(false);
		to.setDateOrdered(dateDoc);
		to.setDatePromised(dateDoc);	// assumption
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		//
		to.setIsApproved(false);
		//
		to.setIsDelivered(false);
		to.setPosted(false);
		to.setProcessed(false);
		if (counter)
			to.setRef_Order_ID(from.getDD_Order_ID());
		else
			to.setRef_Order_ID(0);
		//
		if (!to.save(trxName))
			throw new IllegalStateException("Could not create Order");
		if (counter)
			from.setRef_Order_ID(to.getDD_Order_ID());

		if (to.copyLinesFrom(from, counter, copyASI) == 0)
			throw new IllegalStateException("Could not create Order Lines");

		return to;
	}	// copyFrom

	/**************************************************************************
	 * Default Constructor
	 * 
	 * @param ctx context
	 * @param DD_Order_ID order to load, (0 create new order)
	 * @param trxName trx name
	 */
	public MDDOrder(Properties ctx, int DD_Order_ID, String trxName)
	{
		super(ctx, DD_Order_ID, trxName);
		// New
		if (DD_Order_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction(DOCACTION_Prepare);
			//
			setDeliveryRule(DELIVERYRULE_Availability);
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

	/**************************************************************************
	 * Project Constructor
	 * 
	 * @param project Project to create Order from
	 * @param IsSOTrx sales order
	 * @param DocSubType if SO DocType Target (default DocSubType_OnCredit)
	 */
	public MDDOrder(MProject project, boolean IsSOTrx, String DocSubType)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setSalesRep_ID(project.getSalesRep_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
			setDateOrdered(ts);
		ts = project.getDateFinish();
		if (ts != null)
			setDatePromised(ts);
		//
		setC_BPartner_ID(project.getC_BPartner_ID());
		setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		setAD_User_ID(project.getAD_User_ID());
		//
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		//
		setIsSOTrx(IsSOTrx);
	}	// MDDOrder

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MDDOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDDOrder

	/** Order Lines */
	private MDDOrderLine[] m_lines = null;

	// /** Force Creation of order */
	// private boolean m_forceCreation = false;

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	/**
	 * Set Ship Business Partner
	 *
	 * @param C_BPartner_ID bpartner
	 */
	public void setShip_BPartner_ID(int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
	}	// setShip_BPartner_ID

	/**
	 * Set Ship Business Partner Location
	 *
	 * @param C_BPartner_Location_ID bp location
	 */
	public void setShip_Location_ID(int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID(C_BPartner_Location_ID);
	}	// setShip_Location_ID

	/**
	 * Set Ship Business Partner Contact
	 *
	 * @param AD_User_ID user
	 */
	public void setShip_User_ID(int AD_User_ID)
	{
		super.setAD_User_ID(AD_User_ID);
	}	// setShip_User_ID

	/**
	 * Set Business Partner Defaults & Details. SOTrx should be set.
	 * 
	 * @param bp business partner
	 */
	public void setBPartner(MBPartner bp)
	{
		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());
		// Defaults Payment Term
		int ii = 0;
		if (isSOTrx())
			ii = bp.getC_PaymentTerm_ID();
		else
			ii = bp.getPO_PaymentTerm_ID();

		// Default Price List
		if (isSOTrx())
			ii = bp.getM_PriceList_ID();
		else
			ii = bp.getPO_PriceList_ID();
		// Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (ss != null)
			setDeliveryRule(ss);
		ss = bp.getDeliveryViaRule();
		if (ss != null)
			setDeliveryViaRule(ss);
		// Default Invoice/Payment Rule
		ss = bp.getInvoiceRule();

		if (getSalesRep_ID() == 0)
		{
			ii = Env.getAD_User_ID(getCtx());
			if (ii != 0)
				setSalesRep_ID(ii);
		}

		// Set Locations
		MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (int i = 0; i < locs.length; i++)
			{
				if (locs[i].isShipTo())
				{
					super.setC_BPartner_Location_ID(locs[i].getC_BPartner_Location_ID());
				}
			}
			// set to first
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
			{
				super.setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
			}
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			log.error("MDDOrder.setBPartner - Has no Ship To Address: " + bp);
		}

		// Set Contact
		List<I_AD_User> contacts = bp.getContacts(false);
		if (contacts != null && contacts.size() == 1)
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	}	// setBPartner

	/**
	 * Copy Lines From other Order
	 *
	 * @param otherOrder order
	 * @param counter set counter info
	 * @param copyASI copy line attributes Attribute Set Instance, Resource Assignment
	 * @return number of lines copied
	 */
	public int copyLinesFrom(MDDOrder otherOrder, boolean counter, boolean copyASI)
	{
		if (isProcessed() || isPosted() || otherOrder == null)
			return 0;
		MDDOrderLine[] fromLines = otherOrder.getLines(true, null);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			MDDOrderLine line = new MDDOrderLine(this);
			PO.copyValues(fromLines[i], line, getAD_Client_ID(), getAD_Org_ID());
			line.setDD_Order(this);
			line.setOrder(this);
			// References
			if (!copyASI)
			{
				line.setM_AttributeSetInstance_ID(0);
				// line.setS_ResourceAssignment_ID(0);
			}

			line.setQtyDelivered(Env.ZERO);
			line.setQtyReserved(Env.ZERO);
			line.setDateDelivered(null);

			line.setProcessed(false);
			if (line.save(get_TrxName()))
				count++;
		}
		if (fromLines.length != count)
			log.error("Line difference - From=" + fromLines.length + " <> Saved=" + count);
		return count;
	}	// copyLinesFrom

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MDDOrder[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append("]");
		return sb.toString();
	}	// toString

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
	}	// getDocumentInfo

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
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
	}	// getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file)
	{
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.DISTRIBUTION_ORDER, getDD_Order_ID());
		if (re == null)
			return null;
		return re.getPDF(file);
	}	// createPDF

	/**************************************************************************
	 * Get Lines of Order
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return lines
	 */
	public MDDOrderLine[] getLines(String whereClause, String orderClause)
	{
		StringBuffer whereClauseFinal = new StringBuffer(MDDOrderLine.COLUMNNAME_DD_Order_ID).append("=?");
		if (!Check.isEmpty(whereClause, true))
			whereClauseFinal.append("AND (").append(whereClause).append(")");
		//
		List<MDDOrderLine> list = new Query(getCtx(), MDDOrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
				.setParameters(new Object[] { getDD_Order_ID() })
				.setOrderBy(orderClause)
				.list();
		return list.toArray(new MDDOrderLine[list.size()]);
	}	// getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MDDOrderLine[] getLines(boolean requery, final String orderBy)
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
			orderClause += orderBy;
		else
			orderClause += I_DD_OrderLine.COLUMNNAME_Line;

		m_lines = getLines(null, orderClause);
		return m_lines;
	}	// getLines

	/**
	 * Renumber Lines
	 *
	 * @param step start and step
	 */
	public void renumberLines(final int step)
	{
		int number = step;
		final MDDOrderLine[] lines = getLines(true, null);	// Line is default
		for (int i = 0; i < lines.length; i++)
		{
			MDDOrderLine line = lines[i];
			line.setLine(number);
			line.save(get_TrxName());
			number += step;
		}
		m_lines = null;
	}	// renumberLines

	/**
	 * Get Shipments of Order
	 * 
	 * @return shipments
	 */
	public MMovement[] getMovement()
	{
		ArrayList<MMovement> list = new ArrayList<MMovement>();
		String sql = "SELECT DISTINCT io.* FROM M_MovementLine ml " +
				"INNER JOIN M_Movement m ON (m.M_Movement_ID = ml.M_Movement_ID) " +
				"INNER JOIN DD_ORDERLINE ol ON (ol.DD_ORDERLINE_ID=ml.DD_ORDERLINE_ID) " +
				"INNER JOIN DD_ORDER o ON (o.DD_ORDER_ID=ol.DD_ORDER_ID) " +
				"WHERE	o.DD_ORDER_ID=? " +
				"ORDER BY m.Created DESC";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getDD_Order_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MMovement(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
			pstmt = null;
		}
		//
		MMovement[] retValue = new MMovement[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getShipments

	/**
	 * Set Processed. Propergate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);

		if (get_ID() <= 0)
		{
			return;
		}

		//
		// Flag DD_OrderLines as processed, right now
		{
			final String sql = "SET Processed='"
					+ (processed ? "Y" : "N")
					+ "' WHERE DD_Order_ID=" + getDD_Order_ID();
			final int noLine = DB.executeUpdate("UPDATE DD_OrderLine " + sql, get_TrxName());
			log.debug("setProcessed - " + processed + " - Lines=" + noLine);

			m_lines = null; // reset cached lines
		}

	}	// setProcessed

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return save
	 */
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
				log.warn("Changed Org to Context=" + context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_AD_Client_ID);
		}

		// New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
			setC_DocType_ID(0);

		// Default Warehouse
		if (getM_Warehouse_ID() <= 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
			if (ii > 0)
				setM_Warehouse_ID(ii);
			else
			{
				throw new FillMandatoryException(COLUMNNAME_M_Warehouse_ID);
			}
		}
		// Reservations in Warehouse
		if (!newRecord && is_ValueChanged(COLUMNNAME_M_Warehouse_ID))
		{
			final MDDOrderLine[] lines = getLines(true, null);
			for (int i = 0; i < lines.length; i++)
			{
				if (!lines[i].canChangeWarehouse())
				{
					throw new AdempiereException("Cannot change warehouse because of " + lines[i]); // TODO TRL
				}
			}
		}

		// No Partner Info - set Template
		if (getC_BPartner_ID() <= 0)
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		if (getC_BPartner_Location_ID() <= 0)
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), ITrx.TRXNAME_None));

		// Default Sales Rep
		if (getSalesRep_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#AD_User_ID");
			if (ii != 0)
				setSalesRep_ID(ii);
		}

		return true;
	}	// beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return true if can be saved
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;

		// Propagate Description changes
		if (is_ValueChanged("Description") || is_ValueChanged("POReference"))
		{
			final String sql = DB.convertSqlToNative("UPDATE M_Movement i"
					+ " SET (Description,POReference)="
					+ "(SELECT Description,POReference "
					+ "FROM DD_Order o WHERE i.DD_Order_ID=o.DD_Order_ID) "
					+ "WHERE DocStatus NOT IN ('RE','CL') AND DD_Order_ID=" + getDD_Order_ID());
			int no = DB.executeUpdateEx(sql, get_TrxName());
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
			int no = DB.executeUpdateEx(sql, get_TrxName());
			log.debug(columnName + " Lines -> #" + no);
		}
	}	// afterSaveSync

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
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
		log.info("unlockIt - " + toString());
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
		log.info(toString());
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
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		// Std Period open?
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		MPeriod.testPeriodOpen(getCtx(), getDateOrdered(), dt.getDocBaseType(), getAD_Org_ID());

		// Lines
		final MDDOrderLine[] lines = getLines(true, I_DD_OrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}

		// Bug 1564431
		final String deliveryRule = getDeliveryRule();
		if (X_DD_Order.DELIVERYRULE_CompleteOrder.equals(deliveryRule))
		{
			for (final I_DD_OrderLine line : lines)
			{
				I_M_Product product = line.getM_Product();
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
			return IDocument.STATUS_Invalid;

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
		BigDecimal Volume = Env.ZERO;
		BigDecimal Weight = Env.ZERO;

		// Always check and (un) Reserve Inventory
		for (int i = 0; i < lines.length; i++)
		{
			MDDOrderLine line = lines[i];
			MLocator locator_from = MLocator.get(getCtx(), line.getM_Locator_ID());
			MLocator locator_to = MLocator.get(getCtx(), line.getM_LocatorTo_ID());
			BigDecimal reserved_ordered = line.getQtyOrdered()
					.subtract(line.getQtyReserved())
					.subtract(line.getQtyDelivered());
			if (reserved_ordered.signum() == 0)
			{
				final I_M_Product product = line.getM_Product();
				if (product != null)
				{
					Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
					Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
				}
				continue;
			}

			log.debug("Line=" + line.getLine()
					+ " - Ordered=" + line.getQtyOrdered()
					+ ",Reserved=" + line.getQtyReserved() + ",Delivered=" + line.getQtyDelivered());

			// Check Product - Stocked and Item
			final I_M_Product product = line.getM_Product();
			if (product != null)
			{
				if (Services.get(IProductBL.class).isStocked(product))
				{
					// Update Storage
					if (!MStorage.add(getCtx(), locator_from.getM_Warehouse_ID(), locator_from.getM_Locator_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(), line.getM_AttributeSetInstance_ID(),
							Env.ZERO, reserved_ordered, Env.ZERO, get_TrxName()))
					{
						throw new AdempiereException();
					}

					if (!MStorage.add(getCtx(), locator_to.getM_Warehouse_ID(), locator_to.getM_Locator_ID(),
							line.getM_Product_ID(),
							line.getM_AttributeSetInstanceTo_ID(), line.getM_AttributeSetInstance_ID(),
							Env.ZERO, Env.ZERO, reserved_ordered, get_TrxName()))
					{
						throw new AdempiereException();
					}

				}	// stockec
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

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	// approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	// rejectIt

	/**************************************************************************
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
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
				return status;
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
				info.append(" - ");
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

	/**
	 * Void Document. Set Qtys to 0 - Sales: reverse all documents
	 * 
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final MDDOrderLine[] lines = getLines(true, I_DD_OrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MDDOrderLine line = lines[i];
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
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	/**
	 * Create Shipment/Invoice Reversals
	 * 
	 * @return true if success
	 */
	/*
	 * private boolean createReversals() { // Cancel only Sales if (!isSOTrx()) return true;
	 * 
	 * log.info("createReversals"); StringBuffer info = new StringBuffer();
	 * 
	 * // Reverse All *Shipments* info.append("@M_InOut_ID@:"); MInOut[] shipments = getShipments(); for (int i = 0; i < shipments.length; i++) { MInOut ship = shipments[i]; // if closed - ignore if
	 * (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus()) || MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus()) || MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus()) ) continue;
	 * ship.set_TrxName(get_TrxName());
	 * 
	 * // If not completed - void - otherwise reverse it if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus())) { if (ship.voidIt()) ship.setDocStatus(MInOut.DOCSTATUS_Voided); } else if
	 * (ship.reverseCorrectIt()) // completed shipment { ship.setDocStatus(MInOut.DOCSTATUS_Reversed); info.append(" ").append(ship.getDocumentNo()); } else { m_processMsg =
	 * "Could not reverse Shipment " + ship; return false; } ship.setDocAction(MInOut.DOCACTION_None); ship.save(get_TrxName()); } // for all shipments
	 * 
	 * // Reverse All *Invoices* info.append(" - @C_Invoice_ID@:"); MInvoice[] invoices = getInvoices(); for (int i = 0; i < invoices.length; i++) { MInvoice invoice = invoices[i]; // if closed -
	 * ignore if (MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus()) || MInvoice.DOCSTATUS_Reversed.equals(invoice.getDocStatus()) || MInvoice.DOCSTATUS_Voided.equals(invoice.getDocStatus()) )
	 * continue; invoice.set_TrxName(get_TrxName());
	 * 
	 * // If not completed - void - otherwise reverse it if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus())) { if (invoice.voidIt()) invoice.setDocStatus(MInvoice.DOCSTATUS_Voided); }
	 * else if (invoice.reverseCorrectIt()) // completed invoice { invoice.setDocStatus(MInvoice.DOCSTATUS_Reversed); info.append(" ").append(invoice.getDocumentNo()); } else { m_processMsg =
	 * "Could not reverse Invoice " + invoice; return false; } invoice.setDocAction(MInvoice.DOCACTION_None); invoice.save(get_TrxName()); } // for all shipments
	 * 
	 * m_processMsg = info.toString(); return true; } // createReversals
	 */

	/**
	 * Close Document. Cancel not delivered Qunatities
	 * 
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// Close Not delivered Qty - SO/PO
		final MDDOrderLine[] lines = getLines(true, "M_Product_ID");
		for (int i = 0; i < lines.length; i++)
		{
			final MDDOrderLine line = lines[i];
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
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	/**
	 * Reverse Correction - same void
	 * 
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseAccrualIt

	/**
	 * Re-activate.
	 * 
	 * @return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}	// reActivateIt

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());

		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	// getSummary

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
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
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}	// isComplete

}	// MDDOrder
