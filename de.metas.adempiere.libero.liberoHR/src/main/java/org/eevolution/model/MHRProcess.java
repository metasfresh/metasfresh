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
 *****************************************************************************/
package org.eevolution.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;

/**
 * HR Process Model
 *
 * @author oscar.gomez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>Original contributor of Payroll Functionality
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * @author Cristina Ghita, www.arhipac.ro
 */
public class MHRProcess extends X_HR_Process implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 570699817555475782L;

	public int m_C_BPartner_ID = 0;
	public int m_AD_User_ID = 0;
	public int m_HR_Concept_ID = 0;
	public String m_columnType = "";
	public Timestamp m_dateFrom;
	public Timestamp m_dateTo;
	/** HR_Concept_ID->MHRMovement */
	public Hashtable<Integer, MHRMovement> m_movement = new Hashtable<Integer, MHRMovement>();
	public MHRPayrollConcept[] linesConcept;

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MHREmployee.class);
	public static final String CONCEPT_PP_COST_COLLECTOR_LABOR = "PP_COST_COLLECTOR_LABOR"; // HARDCODED

	private static StringBuffer s_scriptImport = new StringBuffer(" import org.eevolution.model.*;"
			+ " import org.compiere.util.DB;"
			+ " import java.math.*;"
			+ " import java.sql.*;");

	public static void addScriptImportPackage(String packageName)
	{
		s_scriptImport.append(" import ").append(packageName).append(";");
	}

	/**************************************************************************
	 * Default Constructor
	 * 
	 * @param ctx context
	 * @param HR_Process_ID To load, (0 create new order)
	 */
	public MHRProcess(Properties ctx, int HR_Process_ID, String trxName)
	{
		super(ctx, HR_Process_ID, trxName);
		if (HR_Process_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction(DOCACTION_Prepare);
			setC_DocType_ID(0);
			set_ValueNoCheck("DocumentNo", null);
			setProcessed(false);
			setProcessing(false);
			setPosted(false);
			setHR_Department_ID(0);
			setC_BPartner_ID(0);
		}
	}

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set record
	 */
	public MHRProcess(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MHRProcess

	@Override
	public final void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() <= 0)
		{
			return;
		}
		final String sql = "UPDATE HR_Process SET Processed=? WHERE HR_Process_ID=?";
		DB.executeUpdateEx(sql, new Object[] { processed, get_ID() }, get_TrxName());
	}	// setProcessed

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_Client_ID() == 0)
		{
			throw new AdempiereException("@AD_Client_ID@ = 0");
		}
		if (getAD_Org_ID() == 0)
		{
			int context_AD_Org_ID = getAD_Org_ID();
			if (context_AD_Org_ID == 0)
			{
				throw new AdempiereException("@AD_Org_ID@ = *");
			}
			setAD_Org_ID(context_AD_Org_ID);
			log.warn("Changed Org to Context=" + context_AD_Org_ID);
		}
		setC_DocType_ID(getC_DocTypeTarget_ID());

		return true;
	}

	/**
	 * Process document
	 * 
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
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
		log.info("invalidateIt - " + toString());
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
		log.info("prepareIt - " + toString());

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		// Std Period open?
		MHRPeriod period = MHRPeriod.get(getCtx(), getHR_Period_ID());
		MPeriod.testPeriodOpen(getCtx(), getHR_Period_ID() > 0 ? period.getDateAcct() : getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());

		// New or in Progress/Invalid
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0)
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}

		try
		{
			createMovements();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		//
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	// prepareIt

	/**
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// User Validation
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	// completeIt

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
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
		return true;
	}	// rejectIt

	/**
	 * Post Document - nothing
	 * 
	 * @return true if success
	 */
	public boolean postIt()
	{
		log.info("postIt - " + toString());
		return false;
	}	// postIt

	/**
	 * Void Document.
	 * Set Qtys to 0 - Sales: reverse all documents
	 * 
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	/**
	 * Close Document.
	 * Cancel not delivered Qunatities
	 * 
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		if (isProcessed())
		{
			log.info(toString());
			setProcessed(true);
			setDocAction(DOCACTION_None);
			return true;
		}
		return false;
	}	// closeIt

	/**
	 * Reverse Correction - same void
	 * 
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return voidIt();
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return true if success
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
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
		log.info("reActivateIt - " + toString());

		org.compiere.model.MDocType dt = org.compiere.model.MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubType = dt.getDocSubType();

		// Reverse Direct Documents
		if (MDocType.DOCSUBTYPE_OnCreditOrder.equals(DocSubType)		// (W)illCall(I)nvoice
				|| MDocType.DOCSUBTYPE_WarehouseOrder.equals(DocSubType)	// (W)illCall(P)ickup
				|| MDocType.DOCSUBTYPE_POSOrder.equals(DocSubType)) 			// (W)alkIn(R)eceipt
		{
			return false;
		}
		else
		{
			log.debug("reActivateIt - Existing documents not modified - SubType=" + DocSubType);
		}

		// Delete
		String sql = "DELETE FROM HR_Movement WHERE HR_Process_ID =" + this.getHR_Process_ID() + " AND IsRegistered = 'N'";
		int no = DB.executeUpdate(sql, get_TrxName());
		log.debug("HR_Process deleted #" + no);

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}	// reActivateIt

	/**
	 * Get Document Owner (Responsible)
	 * 
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return 0;
	}	// getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 * 
	 * @return amount
	 */
	@Override
	public java.math.BigDecimal getApprovalAmt()
	{
		return BigDecimal.ZERO;
	}	// getApprovalAmt

	/**
	 * 
	 */
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
		return "";
	}

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
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, 0);
		if (re == null)
			return null;
		return re.getPDF(file);
	}	// createPDF

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		org.compiere.model.MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	// getDocumentInfo

	/**
	 * Get Lines
	 * 
	 * @param requery requery
	 * @return lines
	 */
	public MHRMovement[] getLines(boolean requery)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// For HR_Process:
		whereClause.append(MHRMovement.COLUMNNAME_HR_Process_ID + "=?");
		params.add(getHR_Process_ID());
		// With Qty or Amounts
		whereClause.append("AND (Qty <> 0 OR Amount <> 0)"); // TODO: it's really needed ?
		// Only Active Concepts
		whereClause.append(" AND EXISTS(SELECT 1 FROM HR_Concept c WHERE c.HR_Concept_ID=HR_Movement.HR_Concept_ID"
				+ " AND c.IsActive=?"
				+ " AND c.AccountSign<>?)"); // TODO : why ?
		params.add(true);
		params.add(MHRConcept.ACCOUNTSIGN_Natural); // TODO : why ?
		// Concepts with accounting
		whereClause.append(" AND EXISTS(SELECT 1 FROM HR_Concept_Acct ca WHERE ca.HR_Concept_ID=HR_Movement.HR_Concept_ID"
				+ " AND ca.IsActive=? AND ca.IsBalancing<>?)");
		params.add(true);
		params.add(true);
		// BPartner field is filled
		whereClause.append(" AND C_BPartner_ID IS NOT NULL");
		//
		// ORDER BY
		StringBuffer orderByClause = new StringBuffer();
		orderByClause.append("(SELECT bp.C_BP_Group_ID FROM C_BPartner bp WHERE bp.C_BPartner_ID=HR_Movement.C_BPartner_ID)");
		//
		List<MHRMovement> list = new Query(getCtx(), MHRMovement.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(orderByClause.toString())
				.list();
		return list.toArray(new MHRMovement[list.size()]);
	}

	/**
	 * Load HR_Movements and store them in a HR_Concept_ID->MHRMovement hashtable
	 * 
	 * @param movements hashtable
	 * @param C_PBartner_ID
	 */
	private void loadMovements(Hashtable<Integer, MHRMovement> movements, int C_PBartner_ID)
	{
		final String whereClause = MHRMovement.COLUMNNAME_HR_Process_ID + "=?"
				+ " AND " + MHRMovement.COLUMNNAME_C_BPartner_ID + "=?";
		List<MHRMovement> list = new Query(getCtx(), MHRMovement.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getHR_Process_ID(), C_PBartner_ID })
				.list();
		for (MHRMovement mvm : list)
		{
			if (movements.containsKey(mvm.getHR_Concept_ID()))
			{
				MHRMovement lastM = movements.get(mvm.getHR_Concept_ID());
				String columntype = lastM.getColumnType();
				if (columntype.equals(MHRConcept.COLUMNTYPE_Amount))
				{
					mvm.addAmount(lastM.getAmount());
				}
				else if (columntype.equals(MHRConcept.COLUMNTYPE_Quantity))
				{
					mvm.addQty(lastM.getQty());
				}
			}
			movements.put(mvm.getHR_Concept_ID(), mvm);
		}
	}

	/**
	 * Execute the script
	 * 
	 * @param scriptCtx
	 * @param AD_Rule_ID
	 * @return
	 */
	private Object executeScript(final Map<String, Object> scriptCtx, final int AD_Rule_ID)
	{
		final I_AD_Rule rule = Services.get(IADRuleDAO.class).retrieveById(getCtx(), AD_Rule_ID);
		if (rule == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Rule_ID@ (ID=" + AD_Rule_ID + ")");
		}
		
		try
		{
			String scriptBody = "";
			if (rule.getScript() != null)
			{
				String regex = "[^.]get";
				String replacement = "process.get";
				scriptBody = rule.getScript().trim().replaceAll(regex, replacement);
			}
			
			final String script = s_scriptImport.toString()
					+ " double result = 0;"
					+ "\n" + scriptBody
					+ "\n return result;";

			final Object result = ScriptEngineFactory.get()
					.createExecutor(rule)
					.putAll(scriptCtx)
					.execute(script);
			
			return result;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Execution error - @AD_Rule_ID@=" + rule.getValue(), e);
		}
	}

	/**
	 * creates movements for concepts related to labor
	 * 
	 * @param C_BPartner_ID
	 * @param period
	 * @param scriptCtx
	 */
	private void createCostCollectorMovements(int C_BPartner_ID, MHRPeriod period, Map<String, Object> scriptCtx)
	{
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID=PP_Cost_Collector.AD_User_ID AND u.C_BPartner_ID=?)");
		params.add(C_BPartner_ID);
		whereClause.append(" AND " + MPPCostCollector.COLUMNNAME_MovementDate + ">=?");
		params.add(period.getStartDate());
		whereClause.append(" AND " + MPPCostCollector.COLUMNNAME_MovementDate + "<=?");
		params.add(period.getEndDate());
		whereClause.append(" AND " + MPPCostCollector.COLUMNNAME_DocStatus + " IN (?,?)");
		params.add(MPPCostCollector.DOCSTATUS_Completed);
		params.add(MPPCostCollector.DOCSTATUS_Closed);

		List<MPPCostCollector> listColector = new Query(getCtx(), MPPCostCollector.Table_Name,
				whereClause.toString(), get_TrxName())
						.setOnlyActiveRecords(true)
						.setParameters(params)
						.setOrderBy(MPPCostCollector.COLUMNNAME_PP_Cost_Collector_ID + " DESC")
						.list();

		for (MPPCostCollector cc : listColector)
		{
			createMovementForCC(C_BPartner_ID, cc, scriptCtx);
		}
	}

	/**
	 * create movement for cost collector
	 * 
	 * @param C_BPartner_ID
	 * @param cc
	 * @param scriptCtx
	 * @return
	 */
	private MHRMovement createMovementForCC(int C_BPartner_ID, I_PP_Cost_Collector cc, Map<String, Object> scriptCtx)
	{
		// get the concept that should store the labor
		MHRConcept concept = MHRConcept.forValue(getCtx(), CONCEPT_PP_COST_COLLECTOR_LABOR);

		// get the attribute for specific concept
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("? >= ValidFrom AND ( ? <= ValidTo OR ValidTo IS NULL)");
		params.add(m_dateFrom);
		params.add(m_dateTo);
		whereClause.append(" AND HR_Concept_ID = ? ");
		params.add(concept.get_ID());
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept conc WHERE conc.HR_Concept_ID = HR_Attribute.HR_Concept_ID )");
		MHRAttribute att = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
				.first();
		if (att == null)
		{
			throw new AdempiereException(); // TODO ?? is necessary
		}

		if (MHRConcept.TYPE_RuleEngine.equals(concept.getType()))
		{
			Object result = null;

			scriptCtx.put("_CostCollector", cc);
			try
			{
				result = executeScript(scriptCtx, att.getAD_Rule_ID());
			}
			finally
			{
				scriptCtx.remove("_CostCollector");
			}
			if (result == null)
			{
				// TODO: throw exception ???
				log.warn("Variable (result) is null");
			}

			// get employee
			MHREmployee employee = MHREmployee.getActiveEmployee(getCtx(), C_BPartner_ID, get_TrxName());

			// create movement
			MHRMovement mv = new MHRMovement(this, concept);
			mv.setC_BPartner_ID(C_BPartner_ID);
			mv.setAD_Rule_ID(att.getAD_Rule_ID());
			mv.setHR_Job_ID(employee.getHR_Job_ID());
			mv.setHR_Department_ID(employee.getHR_Department_ID());
			mv.setC_Activity_ID(employee.getC_Activity_ID());
			mv.setValidFrom(m_dateFrom);
			mv.setValidTo(m_dateTo);
			mv.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
			mv.setIsRegistered(true);
			mv.setColumnValue(result);
			mv.setProcessed(true);
			mv.saveEx();
			return mv;
		}
		else
		{
			throw new AdempiereException(); // TODO ?? is necessary
		}

	}

	/**
	 * create Movements for corresponding process , period
	 */
	private void createMovements() throws Exception
	{
		final Map<String, Object> scriptCtx = new HashMap<>();
		scriptCtx.put("process", this);
		scriptCtx.put("_Process", getHR_Process_ID());
		scriptCtx.put("_Period", getHR_Period_ID());
		scriptCtx.put("_Payroll", getHR_Payroll_ID());
		scriptCtx.put("_Department", getHR_Department_ID());

		log.info("info data - " + " Process: " + getHR_Process_ID() + ", Period: " + getHR_Period_ID() + ", Payroll: " + getHR_Payroll_ID() + ", Department: " + getHR_Department_ID());
		MHRPeriod period = new MHRPeriod(getCtx(), getHR_Period_ID(), get_TrxName());
		if (period != null)
		{
			m_dateFrom = period.getStartDate();
			m_dateTo = period.getEndDate();
			scriptCtx.put("_From", period.getStartDate());
			scriptCtx.put("_To", period.getEndDate());
		}

		// RE-Process, delete movement except concept type Incidence
		int no = DB.executeUpdateEx("DELETE FROM HR_Movement m WHERE HR_Process_ID=? AND IsRegistered<>?",
				new Object[] { getHR_Process_ID(), true },
				get_TrxName());
		log.info("HR_Movement deleted #" + no);

		linesConcept = MHRPayrollConcept.getPayrollConcepts(this);
		MBPartner[] linesEmployee = MHREmployee.getEmployees(this);
		//
		int count = 1;
		for (MBPartner bp : linesEmployee)	// =============================================================== Employee
		{
			log.info("Employee " + count + "  ---------------------- " + bp.getName());
			count++;
			m_C_BPartner_ID = bp.get_ID();

			MHREmployee employee = MHREmployee.getActiveEmployee(getCtx(), m_C_BPartner_ID, get_TrxName());
			// scriptCtx.put("_DateBirth", employee.getDateBirth());
			scriptCtx.put("_DateStart", employee.getStartDate());
			scriptCtx.put("_DateEnd", employee.getEndDate() == null ? TimeUtil.getDay(2999, 12, 31) : employee.getEndDate());
			scriptCtx.put("_Days", org.compiere.util.TimeUtil.getDaysBetween(period.getStartDate(), period.getEndDate()) + 1);
			scriptCtx.put("_C_BPartner_ID", bp.getC_BPartner_ID());

			createCostCollectorMovements(bp.get_ID(), period, scriptCtx);

			m_movement.clear();
			loadMovements(m_movement, m_C_BPartner_ID);
			//
			for (MHRPayrollConcept pc : linesConcept) // ==================================================== Concept
			{
				m_HR_Concept_ID = pc.getHR_Concept_ID();
				MHRConcept concept = MHRConcept.get(getCtx(), m_HR_Concept_ID);
				m_columnType = concept.getColumnType();

				List<Object> params = new ArrayList<Object>();
				StringBuffer whereClause = new StringBuffer();
				whereClause.append("? >= ValidFrom AND ( ? <= ValidTo OR ValidTo IS NULL)");
				params.add(m_dateFrom);
				params.add(m_dateTo);
				whereClause.append(" AND HR_Concept_ID = ? ");
				params.add(m_HR_Concept_ID);
				whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept conc WHERE conc.HR_Concept_ID = HR_Attribute.HR_Concept_ID )");

				// Check the concept is within a valid range for the attribute
				if (concept.isEmployee())
				{
					whereClause.append(" AND C_BPartner_ID = ? AND (HR_Employee_ID = ? OR HR_Employee_ID IS NULL)");
					params.add(employee.getC_BPartner_ID());
					params.add(employee.get_ID());
				}

				MHRAttribute att = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
						.setParameters(params)
						.setOnlyActiveRecords(true)
						.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
						.first();
				if (att == null || concept.isRegistered())
				{
					log.info("Skip concept " + concept + " - attribute not found");
					continue;
				}

				log.info("Concept - " + concept.getName());
				MHRMovement movement = new MHRMovement(getCtx(), 0, get_TrxName());
				movement.setC_BPartner_ID(m_C_BPartner_ID);
				movement.setHR_Concept_ID(m_HR_Concept_ID);
				movement.setHR_Concept_Category_ID(concept.getHR_Concept_Category_ID());
				movement.setHR_Process_ID(getHR_Process_ID());
				movement.setHR_Department_ID(employee.getHR_Department_ID());
				movement.setHR_Job_ID(employee.getHR_Job_ID());
				movement.setColumnType(m_columnType);
				movement.setAD_Rule_ID(att.getAD_Rule_ID());
				movement.setValidFrom(m_dateFrom);
				movement.setValidTo(m_dateTo);
				movement.setIsPrinted(att.isPrinted());
				movement.setIsRegistered(concept.isRegistered());
				movement.setC_Activity_ID(employee.getC_Activity_ID());
				if (MHRConcept.TYPE_RuleEngine.equals(concept.getType()))
				{
					Object result = executeScript(scriptCtx, att.getAD_Rule_ID());
					if (result == null)
					{
						// TODO: throw exception ???
						log.warn("Variable (result) is null");
						continue;
					}
					movement.setColumnValue(result);
				}
				else
				{
					movement.setQty(att.getQty());
					movement.setAmount(att.getAmount());
					movement.setTextMsg(att.getTextMsg());
					movement.setServiceDate(att.getServiceDate());
				}
				movement.setProcessed(true);
				m_movement.put(m_HR_Concept_ID, movement);
			}  // concept

			// Save movements:
			for (MHRPayrollConcept pc : linesConcept)
			{
				MHRMovement m = m_movement.get(pc.getHR_Concept_ID());
				if (m == null)
				{
					continue;
				}
				MHRConcept c = MHRConcept.get(getCtx(), pc.getHR_Concept_ID());
				if (c.isRegistered() || m.isEmpty())
				{
					log.debug("Skip saving " + m);
				}
				else
				{
					m.saveEx();
				}
			}
		}  // for each employee
 //
 // Save period & finish
		period.setProcessed(true);
		period.saveEx();
	} // createMovements

	// Helper methods -------------------------------------------------------------------------------

	/**
	 * Helper Method : get the value of the concept
	 * 
	 * @param pconcept
	 * @return
	 */
	public double getConcept(String pconcept)
	{
		MHRConcept concept = MHRConcept.forValue(getCtx(), pconcept.trim());

		if (concept == null)
		{
			return 0; // TODO throw exception ?
		}

		MHRMovement m = m_movement.get(concept.get_ID());
		if (m == null)
		{
			return 0; // TODO throw exception ?
		}

		String type = m.getColumnType();
		if (MHRMovement.COLUMNTYPE_Amount.equals(type))
		{
			return m.getAmount().doubleValue();
		}
		else if (MHRMovement.COLUMNTYPE_Quantity.equals(type))
		{
			return m.getQty().doubleValue();
		}
		else
		{
			// TODO: throw exception ?
			return 0;
		}
	} // getConcept

	/**
	 * Helper Method : sets the value of a concept
	 * 
	 * @param conceptValue
	 * @param value
	 */
	public void setConcept(String conceptValue, double value)
	{
		try
		{
			MHRConcept c = MHRConcept.forValue(getCtx(), conceptValue);
			if (c == null)
			{
				return; // TODO throw exception
			}
			MHRMovement m = new MHRMovement(getCtx(), 0, get_TrxName());
			m.setColumnType(c.getColumnType());
			m.setColumnValue(BigDecimal.valueOf(value));

			m.setHR_Process_ID(getHR_Process_ID());
			m.setHR_Concept_ID(m_HR_Concept_ID);
			m.setC_BPartner_ID(m_C_BPartner_ID);
			m.setDescription("Added From Rule"); // TODO: translate
			m.setValidFrom(m_dateTo);
			m.setValidTo(m_dateTo);
			m.saveEx();
		}
		catch (Exception e)
		{
			s_log.warn(e.getMessage());
		}
	} // setConcept

	/*
	 * Helper Method : sets the value of a concept and set if isRegistered
	 * 
	 * @param conceptValue
	 * 
	 * @param value
	 * 
	 * @param isRegistered
	 */
	public void setConcept(String conceptValue, double value, boolean isRegistered)
	{
		try
		{
			MHRConcept c = MHRConcept.forValue(getCtx(), conceptValue);
			if (c == null)
			{
				return; // TODO throw exception
			}
			MHRMovement m = new MHRMovement(Env.getCtx(), 0, get_TrxName());
			m.setColumnType(c.getColumnType());
			if (c.getColumnType().equals(MHRConcept.COLUMNTYPE_Amount))
				m.setAmount(BigDecimal.valueOf(value));
			else if (c.getColumnType().equals(MHRConcept.COLUMNTYPE_Quantity))
				m.setQty(BigDecimal.valueOf(value));
			else
				return;
			m.setHR_Process_ID(getHR_Process_ID());
			m.setHR_Concept_ID(c.getHR_Concept_ID());
			m.setC_BPartner_ID(m_C_BPartner_ID);
			m.setDescription("Added From Rule"); // TODO: translate
			m.setValidFrom(m_dateTo);
			m.setValidTo(m_dateTo);
			m.setIsRegistered(isRegistered);
			m.saveEx();
		}
		catch (Exception e)
		{
			s_log.warn(e.getMessage());
		}
	} // setConcept

	/**
	 * Helper Method : get the sum of the concept values, grouped by the Category
	 * 
	 * @param pconcept
	 * @return
	 */
	public double getConceptGroup(String pconcept)
	{
		final MHRConceptCategory category = MHRConceptCategory.forValue(getCtx(), pconcept);
		if (category == null)
		{
			return 0.0; // TODO: need to throw exception ?
		}
		//
		double value = 0.0;
		for (MHRPayrollConcept pc : linesConcept)
		{
			MHRConcept con = MHRConcept.get(getCtx(), pc.getHR_Concept_ID());
			if (con.getHR_Concept_Category_ID() == category.get_ID())
			{
				MHRMovement movement = m_movement.get(pc.getHR_Concept_ID());
				if (movement == null)
				{
					continue;
				}
				else
				{
					String columnType = movement.getColumnType();
					if (MHRConcept.COLUMNTYPE_Amount.equals(columnType))
					{
						value += movement.getAmount().doubleValue();
					}
					else if (MHRConcept.COLUMNTYPE_Quantity.equals(columnType))
					{
						value += movement.getQty().doubleValue();
					}
				}
			}
		}
		return value;
	} // getConceptGroup

	/**
	 * Helper Method : Get Concept [get concept to search key ]
	 * 
	 * @param pList Value List
	 * @param amount Amount to search
	 * @param column Number of column to return (1.......8)
	 * @return The amount corresponding to the designated column 'column'
	 */
	public double getList(String pList, double amount, String columnParam)
	{
		BigDecimal value = Env.ZERO;
		String column = columnParam;
		if (m_columnType.equals(MHRConcept.COLUMNTYPE_Amount))
		{
			column = column.toString().length() == 1 ? "Col_" + column : "Amount" + column;
			ArrayList<Object> params = new ArrayList<Object>();
			String sqlList = "SELECT " + column +
					" FROM HR_List l " +
					"INNER JOIN HR_ListVersion lv ON (lv.HR_List_ID=l.HR_List_ID) " +
					"INNER JOIN HR_ListLine ll ON (ll.HR_ListVersion_ID=lv.HR_ListVersion_ID) " +
					"WHERE l.IsActive='Y' AND lv.IsActive='Y' AND ll.IsActive='Y' AND l.Value = ? AND " +
					"l.AD_Client_ID = ? AND " +
					"(? BETWEEN lv.ValidFrom AND lv.ValidTo ) AND " +
					"(? BETWEEN ll.MinValue AND	ll.MaxValue)";
			params.add(pList);
			params.add(getAD_Client_ID());
			params.add(m_dateFrom);
			params.add(BigDecimal.valueOf(amount));

			value = DB.getSQLValueBDEx(get_TrxName(), sqlList, params);
		}
		//
		if (value == null)
		{
			throw new IllegalStateException("getList Out of Range");
		}
		return value.doubleValue();
	} // getList

	/**
	 * Helper Method : Get Attribute [get Attribute to search key concept ]
	 * 
	 * @param pConcept - Value to Concept
	 * @return Amount of concept, applying to employee
	 */
	public double getAttribute(String pConcept)
	{
		MHRConcept concept = MHRConcept.forValue(getCtx(), pConcept);
		if (concept == null)
			return 0;

		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check ValidFrom:
		whereClause.append(MHRAttribute.COLUMNNAME_ValidFrom + "<=?");
		params.add(m_dateFrom);
		// check client
		whereClause.append(" AND AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept c WHERE c.HR_Concept_ID=HR_Attribute.HR_Concept_ID"
				+ " AND c.Value = ?)");
		params.add(pConcept);
		//
		if (!concept.getType().equals(MHRConcept.TYPE_Information))
		{
			whereClause.append(" AND " + MHRAttribute.COLUMNNAME_C_BPartner_ID + " = ?");
			params.add(m_C_BPartner_ID);
		}

		MHRAttribute attribute = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
				.first();
		if (attribute == null)
			return 0.0;

		// if column type is Quantity return quantity
		if (concept.getColumnType().equals(MHRConcept.COLUMNTYPE_Quantity))
			return attribute.getQty().doubleValue();

		// if column type is Amount return amount
		if (concept.getColumnType().equals(MHRConcept.COLUMNTYPE_Amount))
			return attribute.getAmount().doubleValue();

		// something else
		return 0.0; // TODO throw exception ??
	} // getAttribute

	/**
	 * Helper Method : Get Attribute [get Attribute to search key concept ]
	 * 
	 * @param conceptValue
	 * @return ServiceDate
	 */
	public Timestamp getAttributeDate(String conceptValue)
	{
		MHRConcept concept = MHRConcept.forValue(getCtx(), conceptValue);
		if (concept == null)
			return null;

		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check client
		whereClause.append("AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept c WHERE c.HR_Concept_ID=HR_Attribute.HR_Concept_ID"
				+ " AND c.Value = ?)");
		params.add(conceptValue);
		//
		if (!concept.getType().equals(MHRConcept.TYPE_Information))
		{
			whereClause.append(" AND " + MHRAttribute.COLUMNNAME_C_BPartner_ID + " = ?");
			params.add(m_C_BPartner_ID);
		}

		MHRAttribute attribute = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
				.first();
		if (attribute == null)
			return null;

		return attribute.getServiceDate();
	} // getAttributeDate

	/**
	 * Helper Method : Get the number of days between start and end, in Timestamp format
	 * 
	 * @param date1
	 * @param date2
	 * @return no. of days
	 */
	public int getDays(Timestamp date1, Timestamp date2)
	{
		// adds one for the last day
		return org.compiere.util.TimeUtil.getDaysBetween(date1, date2) + 1;
	} // getDays

	/**
	 * Helper Method : Get the number of days between start and end, in String format
	 * 
	 * @param date1
	 * @param date2
	 * @return no. of days
	 */
	public int getDays(String date1, String date2)
	{
		Timestamp dat1 = Timestamp.valueOf(date1);
		Timestamp dat2 = Timestamp.valueOf(date2);
		return getDays(dat1, dat2);
	}  // getDays

	/**
	 * Helper Method : Get Months, Date in Format Timestamp
	 * 
	 * @param start
	 * @param end
	 * @return no. of month between two dates
	 */
	public int getMonths(Timestamp startParam, Timestamp endParam)
	{
		boolean negative = false;
		Timestamp start = startParam;
		Timestamp end = endParam;
		if (end.before(start))
		{
			negative = true;
			Timestamp temp = start;
			start = end;
			end = temp;
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar calEnd = new GregorianCalendar();

		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);

		if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR))
		{
			if (negative)
				return (calEnd.get(Calendar.MONTH) - cal.get(Calendar.MONTH)) * -1;
			return calEnd.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
		}

		// not very efficient, but correct
		int counter = 0;
		while (calEnd.after(cal))
		{
			cal.add(Calendar.MONTH, 1);
			counter++;
		}
		if (negative)
			return counter * -1;
		return counter;
	} // getMonths

	/**
	 * Helper Method : Concept for a range from-to in periods.
	 * Periods with values of 0 -1 1, etc. actual previous one period, next period
	 * 0 corresponds to actual period.
	 * 
	 * @param conceptValue concept key(value)
	 * @param periodFrom the search is done by the period value, it helps to search from previous years
	 * @param periodTo
	 */
	public double getConcept(String conceptValue, int periodFrom, int periodTo)
	{
		return getConcept(conceptValue, null, periodFrom, periodTo);
	} // getConcept

	/**
	 * Helper Method : Concept by range from-to in periods from a different payroll
	 * periods with values 0 -1 1, etc. actual previous one period, next period
	 * 0 corresponds to actual period
	 * 
	 * @param conceptValue
	 * @param pFrom
	 * @param pTo the search is done by the period value, it helps to search from previous years
	 * @param payrollValue is the value of the payroll.
	 */
	public double getConcept(String conceptValue, String payrollValue, int periodFrom, int periodTo)
	{
		int payroll_id;
		if (payrollValue == null)
		{
			payroll_id = getHR_Payroll_ID();
		}
		else
		{
			payroll_id = MHRPayroll.forValue(getCtx(), payrollValue).get_ID();
		}

		MHRConcept concept = MHRConcept.forValue(getCtx(), conceptValue);
		if (concept == null)
			return 0.0;
		//
		// Detect field name
		final String fieldName;
		if (MHRConcept.COLUMNTYPE_Quantity.equals(concept.getColumnType()))
		{
			fieldName = MHRMovement.COLUMNNAME_Qty;
		}
		else if (MHRConcept.COLUMNTYPE_Amount.equals(concept.getColumnType()))
		{
			fieldName = MHRMovement.COLUMNNAME_Amount;
		}
		else
		{
			return 0; // TODO: throw exception?
		}
		//
		MHRPeriod p = MHRPeriod.get(getCtx(), getHR_Period_ID());
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check client
		whereClause.append("AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND " + MHRMovement.COLUMNNAME_HR_Concept_ID + "=?");
		params.add(concept.get_ID());
		// check partner
		whereClause.append(" AND " + MHRMovement.COLUMNNAME_C_BPartner_ID + "=?");
		params.add(m_C_BPartner_ID);
		//
		// check process and payroll
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Process p"
				+ " INNER JOIN HR_Period pr ON (pr.HR_Period_id=p.HR_Period_ID)"
				+ " WHERE HR_Movement.HR_Process_ID = p.HR_Process_ID"
				+ " AND p.HR_Payroll_ID=?");

		params.add(payroll_id);
		if (periodFrom < 0)
		{
			whereClause.append(" AND pr.PeriodNo >= ?");
			params.add(p.getPeriodNo() + periodFrom);
		}
		if (periodTo > 0)
		{
			whereClause.append(" AND pr.PeriodNo <= ?");
			params.add(p.getPeriodNo() + periodTo);
		}
		whereClause.append(")");
		//
		StringBuffer sql = new StringBuffer("SELECT COALESCE(SUM(").append(fieldName).append("),0) FROM ").append(MHRMovement.Table_Name)
				.append(" WHERE ").append(whereClause);
		BigDecimal value = DB.getSQLValueBDEx(get_TrxName(), sql.toString(), params);
		return value.doubleValue();

	} // getConcept

	/**
	 * Helper Method: gets Concept value of a payrroll between 2 dates
	 * 
	 * @param pConcept
	 * @param pPayrroll
	 * @param from
	 * @param to
	 */
	public double getConcept(String conceptValue, String payrollValue, Timestamp from, Timestamp to)
	{
		int payroll_id;
		if (payrollValue == null)
		{
			payroll_id = getHR_Payroll_ID();
		}
		else
		{
			payroll_id = MHRPayroll.forValue(getCtx(), payrollValue).get_ID();
		}

		MHRConcept concept = MHRConcept.forValue(getCtx(), conceptValue);
		if (concept == null)
			return 0.0;
		//
		// Detect field name
		final String fieldName;
		if (MHRConcept.COLUMNTYPE_Quantity.equals(concept.getColumnType()))
		{
			fieldName = MHRMovement.COLUMNNAME_Qty;
		}
		else if (MHRConcept.COLUMNTYPE_Amount.equals(concept.getColumnType()))
		{
			fieldName = MHRMovement.COLUMNNAME_Amount;
		}
		else
		{
			return 0; // TODO: throw exception?
		}
		//
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check client
		whereClause.append("AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND " + MHRMovement.COLUMNNAME_HR_Concept_ID + "=?");
		params.add(concept.get_ID());
		// check partner
		whereClause.append(" AND " + MHRMovement.COLUMNNAME_C_BPartner_ID + "=?");
		params.add(m_C_BPartner_ID);
		// Adding dates
		whereClause.append(" AND validTo BETWEEN ? AND ?");
		params.add(from);
		params.add(to);
		//
		// check process and payroll
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Process p"
				+ " INNER JOIN HR_Period pr ON (pr.HR_Period_id=p.HR_Period_ID)"
				+ " WHERE HR_Movement.HR_Process_ID = p.HR_Process_ID"
				+ " AND p.HR_Payroll_ID=?");

		params.add(payroll_id);

		whereClause.append(")");
		//
		StringBuffer sql = new StringBuffer("SELECT COALESCE(SUM(").append(fieldName).append("),0) FROM ").append(MHRMovement.Table_Name)
				.append(" WHERE ").append(whereClause);
		BigDecimal value = DB.getSQLValueBDEx(get_TrxName(), sql.toString(), params);
		return value.doubleValue();

	} // getConcept

	/**
	 * Helper Method : Attribute that had from some date to another to date,
	 * if it finds just one period it's seen for the attribute of such period
	 * if there are two or more attributes based on the days
	 * 
	 * @param ctx
	 * @param vAttribute
	 * @param dateFrom
	 * @param dateTo
	 * @return attribute value
	 */
	public double getAttribute(Properties ctx, String vAttribute, Timestamp dateFrom, Timestamp dateTo)
	{
		// TODO ???
		log.warn("not implemented yet -> getAttribute (Properties, String, Timestamp, Timestamp)");
		return 0;
	} // getAttribute

	/**
	 * Helper Method : Attribute that had from some period to another to period,
	 * periods with values 0 -1 1, etc. actual previous one period, next period
	 * 0 corresponds to actual period
	 * Value of HR_Attribute
	 * if it finds just one period it's seen for the attribute of such period
	 * if there are two or more attributes
	 * pFrom and pTo the search is done by the period value, it helps to search
	 * from previous year based on the days
	 * 
	 * @param ctx
	 * @param vAttribute
	 * @param periodFrom
	 * @param periodTo
	 * @param pFrom
	 * @param pTo
	 * @return attribute value
	 */
	public double getAttribute(Properties ctx, String vAttribute, int periodFrom, int periodTo,
			String pFrom, String pTo)
	{
		// TODO ???
		log.warn("not implemented yet -> getAttribute (Properties, String, int, int)");
		return 0;
	} // getAttribute

	/**
	 * Helper Method : Get AttributeInvoice
	 * 
	 * @param pConcept - Value to Concept
	 * @return C_Invoice_ID, 0 if does't
	 */
	public int getAttributeInvoice(String pConcept)
	{
		MHRConcept concept = MHRConcept.forValue(getCtx(), pConcept);
		if (concept == null)
			return 0;

		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check ValidFrom:
		whereClause.append(MHRAttribute.COLUMNNAME_ValidFrom + "<=?");
		params.add(m_dateFrom);
		// check client
		whereClause.append(" AND AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept c WHERE c.HR_Concept_ID=HR_Attribute.HR_Concept_ID"
				+ " AND c.Value = ?)");
		params.add(pConcept);
		//
		if (!MHRConcept.TYPE_Information.equals(concept.getType()))
		{
			whereClause.append(" AND " + MHRAttribute.COLUMNNAME_C_BPartner_ID + " = ?");
			params.add(m_C_BPartner_ID);
		}

		MHRAttribute attribute = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
				.first();

		if (attribute != null)
			return (Integer)attribute.get_Value("C_Invoice_ID");
		else
			return 0;

	} // getAttributeInvoice

	/**
	 * Helper Method : Get AttributeDocType
	 * 
	 * @param pConcept - Value to Concept
	 * @return C_DocType_ID, 0 if does't
	 */
	public int getAttributeDocType(String pConcept)
	{
		MHRConcept concept = MHRConcept.forValue(getCtx(), pConcept);
		if (concept == null)
			return 0;

		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		// check ValidFrom:
		whereClause.append(MHRAttribute.COLUMNNAME_ValidFrom + "<=?");
		params.add(m_dateFrom);
		// check client
		whereClause.append(" AND AD_Client_ID = ?");
		params.add(getAD_Client_ID());
		// check concept
		whereClause.append(" AND EXISTS (SELECT 1 FROM HR_Concept c WHERE c.HR_Concept_ID=HR_Attribute.HR_Concept_ID"
				+ " AND c.Value = ?)");
		params.add(pConcept);
		//
		if (!MHRConcept.TYPE_Information.equals(concept.getType()))
		{
			whereClause.append(" AND " + MHRAttribute.COLUMNNAME_C_BPartner_ID + " = ?");
			params.add(m_C_BPartner_ID);
		}

		MHRAttribute attribute = new Query(getCtx(), MHRAttribute.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(MHRAttribute.COLUMNNAME_ValidFrom + " DESC")
				.first();

		if (attribute != null)
			return (Integer)attribute.get_Value("C_DocType_ID");
		else
			return 0;

	} // getAttributeDocType

	/**
	 * Helper Method : get days from specific period
	 * 
	 * @param period
	 * @return no. of days
	 */
	public double getDays(int period)
	{
		return Env.getContextAsInt(getCtx(), "_DaysPeriod") + 1;
	} // getDays

}	// MHRProcess
