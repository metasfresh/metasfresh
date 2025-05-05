/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.request.RequestId;
import de.metas.request.notifications.RequestNotificationsSender;
import de.metas.request.notifications.RequestSalesRepChanged;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Request Model
 *
 * @author Jorg Janke
 * @version $Id: MRequest.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRequest extends X_R_Request
{
	/**
	 * Get Request ID from mail text
	 * 
	 * @param mailText mail text
	 * @return ID if it contains request tag otherwise 0
	 */
	public static int getR_Request_ID(String mailText)
	{
		if (mailText == null)
		{
			return 0;
		}
		int indexStart = mailText.indexOf(TAG_START);
		if (indexStart == -1)
		{
			return 0;
		}
		int indexEnd = mailText.indexOf(TAG_END, indexStart);
		if (indexEnd == -1)
		{
			return 0;
		}
		//
		indexStart += 5;
		String idString = mailText.substring(indexStart, indexEnd);
		int R_Request_ID = 0;
		try
		{
			R_Request_ID = Integer.parseInt(idString);
		}
		catch (Exception e)
		{
			s_log.error("Cannot parse " + idString);
		}
		return R_Request_ID;
	}	// getR_Request_ID

	/** Static Logger */
	private static final Logger s_log = LogManager.getLogger(MRequest.class);
	/** Request Tag Start */
	private static final String TAG_START = "[Req#";
	/** Request Tag End */
	private static final String TAG_END = "#ID]";
	/** Separator line */
	private static final String SEPARATOR = "\n---------.----------.----------.----------.----------.----------\n";

	/** Request Type */
	private MRequestType m_requestType = null;
	// /** Changed */
	// private boolean m_changed = false;

	public MRequest(Properties ctx, int R_Request_ID, String trxName)
	{
		super(ctx, R_Request_ID, trxName);
		if (is_new())
		{
			setDueType(DUETYPE_Faellig);
			// setSalesRep_ID (0);
			// setDocumentNo (null);
			setConfidentialType(CONFIDENTIALTYPE_PublicInformation);	// A
			setConfidentialTypeEntry(CONFIDENTIALTYPEENTRY_PublicInformation);	// A
			setProcessed(false);
			setRequestAmt(BigDecimal.ZERO);
			setPriorityUser(PRIORITY_Low);
			// setR_RequestType_ID (0);
			// setSummary (null);
			setIsEscalated(false);
			setIsSelfService(false);
			setIsInvoiced(false);
		}
	}	// MRequest

	/**
	 * SelfService Constructor
	 * 
	 * @param ctx context
	 * @param SalesRep_ID SalesRep
	 * @param R_RequestType_ID request type
	 * @param Summary summary
	 * @param isSelfService self service
	 * @param trxName transaction
	 */
	public MRequest(Properties ctx, int SalesRep_ID,
			int R_RequestType_ID, String Summary, boolean isSelfService, String trxName)
	{
		this(ctx, 0, trxName);
		set_Value(I_R_Request.COLUMNNAME_SalesRep_ID, SalesRep_ID < 0 ? null : new Integer(SalesRep_ID));	// could be 0
		set_Value("R_RequestType_ID", new Integer(R_RequestType_ID));
		setSummary(Summary);
		setIsSelfService(isSelfService);
		getRequestType();
		if (m_requestType != null)
		{
			String ct = m_requestType.getConfidentialType();
			if (ct != null)
			{
				setConfidentialType(ct);
				setConfidentialTypeEntry(ct);
			}
		}
	}	// MRequest

	public MRequest(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MRequest

	/**
	 * Set Default Request Type.
	 */
	public void setR_RequestType_ID()
	{
		m_requestType = MRequestType.getDefault(getCtx());
		if (m_requestType == null)
		{
			log.warn("No default found");
		}
		else
		{
			super.setR_RequestType_ID(m_requestType.getR_RequestType_ID());
		}
	}	// setR_RequestType_ID

	/**
	 * Set Default Request Status.
	 */
	public void setR_Status_ID()
	{
		MStatus status = MStatus.getDefault(getCtx(), getR_RequestType_ID());
		if (status == null)
		{
			log.warn("No default found");
			if (getR_Status_ID() != 0)
			{
				setR_Status_ID(0);
			}
		}
		else
		{
			setR_Status_ID(status.getR_Status_ID());
		}
	}	// setR_Status_ID

	/**
	 * Add To Result
	 * 
	 * @param Result
	 */
	private void addToResult(String Result)
	{
		String oldResult = getResult();
		if (Result == null || Result.length() == 0)
		{

		}
		else if (oldResult == null || oldResult.length() == 0)
		{
			setResult(Result);
		}
		else
		{
			setResult(oldResult + "\n-\n" + Result);
		}
	}	// addToResult

	/**
	 * Set DueType based on Date Next Action
	 */
	public void setDueType()
	{
		Timestamp due = getDateNextAction();
		if (due == null)
		{
			return;
		}
		//
		Timestamp overdue = TimeUtil.addDays(due, getRequestType().getDueDateTolerance());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		//
		String DueType = DUETYPE_Faellig;
		if (now.before(due))
		{
			DueType = DUETYPE_Geplant;
		}
		else if (now.after(overdue))
		{
			DueType = DUETYPE_Ueberfaellig;
		}
		super.setDueType(DueType);
	}	// setDueType

	/**
	 * Get Updates
	 * 
	 * @param confidentialType maximum confidential type - null = all
	 * @return updates
	 */
	public MRequestUpdate[] getUpdates(String confidentialType)
	{
		final String whereClause = MRequestUpdate.COLUMNNAME_R_Request_ID + "=?";
		List<MRequestUpdate> listUpdates = new Query(getCtx(), MRequestUpdate.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.setOrderBy("Created DESC")
				.list(MRequestUpdate.class);
		ArrayList<MRequestUpdate> list = new ArrayList<>();
		for (MRequestUpdate ru : listUpdates)
		{
			if (confidentialType != null)
			{
				// Private only if private
				if (ru.getConfidentialTypeEntry().equals(CONFIDENTIALTYPEENTRY_PrivateInformation)
						&& !confidentialType.equals(CONFIDENTIALTYPEENTRY_PrivateInformation))
				{
					continue;
				}
				// Internal not if Customer/Public
				if (ru.getConfidentialTypeEntry().equals(CONFIDENTIALTYPEENTRY_Internal)
						&& (confidentialType.equals(CONFIDENTIALTYPEENTRY_PartnerConfidential)
								|| confidentialType.equals(CONFIDENTIALTYPEENTRY_PublicInformation)))
				{
					continue;
				}
				// No Customer if public
				if (ru.getConfidentialTypeEntry().equals(CONFIDENTIALTYPEENTRY_PartnerConfidential)
						&& confidentialType.equals(CONFIDENTIALTYPEENTRY_PublicInformation))
				{
					continue;
				}
			}
			list.add(ru);
		}
		//
		MRequestUpdate[] retValue = new MRequestUpdate[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getUpdates

	/**
	 * Get Request Type
	 * 
	 * @return Request Type
	 */
	public MRequestType getRequestType()
	{
		if (m_requestType == null)
		{
			int R_RequestType_ID = getR_RequestType_ID();
			if (R_RequestType_ID == 0)
			{
				setR_RequestType_ID();
				R_RequestType_ID = getR_RequestType_ID();
			}
			m_requestType = MRequestType.get(getCtx(), R_RequestType_ID);
		}
		return m_requestType;
	}	// getRequestType

	/**
	 * Is Overdue
	 * 
	 * @return true if overdue
	 */
	public boolean isOverdue()
	{
		return DUETYPE_Ueberfaellig.equals(getDueType());
	}	// isOverdue

	/**
	 * Is due
	 * 
	 * @return true if due
	 */
	public boolean isDue()
	{
		return DUETYPE_Faellig.equals(getDueType());
	}	// isDue

	/**
	 * Set Date Last Alert to today
	 */
	public void setDateLastAlert()
	{
		super.setDateLastAlert(new Timestamp(System.currentTimeMillis()));
	}	// setDateLastAlert

	/**
	 * Get Sales Rep
	 * 
	 * @return Sales Rep User
	 */
	@Override
	public I_AD_User getSalesRep()
	{
		if (getSalesRep_ID() <= 0)
		{
			return null;
		}
		return Services.get(IUserDAO.class).retrieveUserOrNull(getCtx(), getSalesRep_ID());
	}	// getSalesRep

	private I_C_BPartner getBPartner()
	{
		if (getC_BPartner_ID() <= 0)
		{
			return null;
		}
		return loadOutOfTrx(getC_BPartner_ID(), I_C_BPartner.class);
	}

	private void setPriority()
	{
		if (getPriorityUser() == null)
		{
			setPriorityUser(PRIORITYUSER_Low);
		}
		//
		if (getBPartner() != null)
		{
			final BPGroupId bpGroupId = BPGroupId.ofRepoId(getBPartner().getC_BP_Group_ID());
			final I_C_BP_Group bpg = Services.get(IBPGroupDAO.class).getById(bpGroupId);
			String prioBase = bpg.getPriorityBase();
			if (prioBase != null && !prioBase.equals(X_C_BP_Group.PRIORITYBASE_Same))
			{
				char targetPrio = getPriorityUser().charAt(0);
				if (prioBase.equals(X_C_BP_Group.PRIORITYBASE_Lower))
				{
					targetPrio += 2;
				}
				else
				{
					targetPrio -= 2;
				}
				if (targetPrio < PRIORITY_High.charAt(0))
				{
					targetPrio = PRIORITY_High.charAt(0);
				}
				if (targetPrio > PRIORITY_Low.charAt(0))
				{
					targetPrio = PRIORITY_Low.charAt(0);
				}
				if (getPriority() == null)
				{
					setPriority(String.valueOf(targetPrio));
				}
				else	// previous priority
				{
					if (targetPrio < getPriority().charAt(0))
					{
						setPriority(String.valueOf(targetPrio));
					}
				}
			}
		}
		// Same if nothing else
		if (getPriority() == null)
		{
			setPriority(getPriorityUser());
		}
	}	// setPriority

	/**
	 * Set Confidential Type Entry
	 * 
	 * @param ConfidentialTypeEntry confidentiality
	 */
	@Override
	public void setConfidentialTypeEntry(String ConfidentialTypeEntry)
	{
		if (ConfidentialTypeEntry == null)
		{
			ConfidentialTypeEntry = getConfidentialType();
		}
		//
		if (CONFIDENTIALTYPE_Internal.equals(getConfidentialType()))
		{
			super.setConfidentialTypeEntry(CONFIDENTIALTYPE_Internal);
		}
		else if (CONFIDENTIALTYPE_PrivateInformation.equals(getConfidentialType()))
		{
			if (CONFIDENTIALTYPE_Internal.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PrivateInformation.equals(ConfidentialTypeEntry))
			{
				super.setConfidentialTypeEntry(ConfidentialTypeEntry);
			}
			else
			{
				super.setConfidentialTypeEntry(CONFIDENTIALTYPE_PrivateInformation);
			}
		}
		else if (CONFIDENTIALTYPE_PartnerConfidential.equals(getConfidentialType()))
		{
			if (CONFIDENTIALTYPE_Internal.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PrivateInformation.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PartnerConfidential.equals(ConfidentialTypeEntry))
			{
				super.setConfidentialTypeEntry(ConfidentialTypeEntry);
			}
			else
			{
				super.setConfidentialTypeEntry(CONFIDENTIALTYPE_PartnerConfidential);
			}
		}
		else if (CONFIDENTIALTYPE_PublicInformation.equals(getConfidentialType()))
		{
			super.setConfidentialTypeEntry(ConfidentialTypeEntry);
		}
	}	// setConfidentialTypeEntry

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MRequest[");
		sb.append(get_ID()).append("-").append(getDocumentNo()).append("]");
		return sb.toString();
	}	// toString

	/**************************************************************************
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Request Type
		getRequestType();
		if (newRecord || is_ValueChanged("R_RequestType_ID"))
		{
			if (m_requestType != null)
			{
				if (isInvoiced() != m_requestType.isInvoiced())
				{
					setIsInvoiced(m_requestType.isInvoiced());
				}
				if (getDateNextAction() == null && m_requestType.getAutoDueDateDays() > 0)
				{
					setDateNextAction(TimeUtil.addDays(new Timestamp(System.currentTimeMillis()),
							m_requestType.getAutoDueDateDays()));
				}
			}
			// Is Status Valid
			if (getR_Status_ID() != 0)
			{
				MStatus sta = MStatus.get(getCtx(), getR_Status_ID());
				MRequestType rt = MRequestType.get(getCtx(), getR_RequestType_ID());
				if (sta.getR_StatusCategory_ID() != rt.getR_StatusCategory_ID())
				{
					setR_Status_ID();	// set to default
				}
			}
		}

		// Request Status
		if (getR_Status_ID() == 0)
		{
			setR_Status_ID();
		}
		// Validate/Update Due Type
		setDueType();
		MStatus status = MStatus.get(getCtx(), getR_Status_ID());
		// Close/Open
		if (status != null)
		{
			if (status.isOpen())
			{
				if (getStartTime() == null)
				{
					setStartTime(SystemTime.asTimestamp());
				}
				if (getCloseDate() != null)
				{
					setCloseDate(null);
				}
			}
			if (status.isClosed()
					&& getCloseDate() == null)
			{
				setCloseDate(new Timestamp(System.currentTimeMillis()));
			}
			if (status.isFinalClose())
			{
				setProcessed(true);
			}
		}

		// Confidential Info
		if (getConfidentialType() == null)
		{
			getRequestType();
			if (m_requestType != null)
			{
				String ct = m_requestType.getConfidentialType();
				if (ct != null)
				{
					setConfidentialType(ct);
				}
			}
			if (getConfidentialType() == null)
			{
				setConfidentialType(CONFIDENTIALTYPEENTRY_PublicInformation);
			}
		}
		if (getConfidentialTypeEntry() == null)
		{
			setConfidentialTypeEntry(getConfidentialType());
		}
		else
		{
			setConfidentialTypeEntry(getConfidentialTypeEntry());
		}

		// Importance / Priority
		setPriority();

		// New
		if (newRecord)
		{
			return true;
		}

		// Change Log
		// ArrayList<String> sendInfo = new ArrayList<>();
		MRequestAction requestAction = new MRequestAction(this);
		if (checkChanges(requestAction))
		{
			saveRecord(requestAction);
		}
		else
		{
			requestAction = null;
		}

		// Current Info
		MRequestUpdate requestUpdate = new MRequestUpdate(this);
		if (requestUpdate.isNewInfo())
		{
			requestUpdate.save();
		}
		else
		{
			requestUpdate = null;
		}

		//
		if (requestUpdate != null || requestAction != null)
		{
			// Update
			setDateLastAction(getUpdated());
			setLastResult(getResult());
			setDueType();
			// Reset
			setConfidentialTypeEntry(getConfidentialType());
			// setStartDate(null); //red1 - bug [ 1743159 ] Requests - Start Date is not retained.
			setR_StandardResponse_ID(0);
			setR_MailText_ID(0);
			setResult(null);
			// globalqss - these fields must be cleared (waiting to open bug in sf)
			// setM_ProductSpent_ID(0);
			// setQtySpent(null);
			// setQtyInvoiced(null);
		}

		return true;
	}	// beforeSave

	private static final ImmutableSet<String> //
	columnsToCheckForRequestActionChanges = ImmutableSet.of(
			I_R_Request.COLUMNNAME_R_RequestType_ID,
			I_R_Request.COLUMNNAME_R_Group_ID,
			I_R_Request.COLUMNNAME_R_Category_ID,
			I_R_Request.COLUMNNAME_R_Status_ID,
			I_R_Request.COLUMNNAME_R_Resolution_ID,
			I_R_Request.COLUMNNAME_SalesRep_ID,
			I_R_Request.COLUMNNAME_AD_Role_ID,
			I_R_Request.COLUMNNAME_Priority,
			I_R_Request.COLUMNNAME_PriorityUser,
			I_R_Request.COLUMNNAME_IsEscalated,
			I_R_Request.COLUMNNAME_ConfidentialType,
			I_R_Request.COLUMNNAME_Summary,
			I_R_Request.COLUMNNAME_IsSelfService,
			I_R_Request.COLUMNNAME_C_BPartner_ID,
			I_R_Request.COLUMNNAME_AD_User_ID,
			I_R_Request.COLUMNNAME_C_Project_ID,
			I_R_Request.COLUMNNAME_A_Asset_ID,
			I_R_Request.COLUMNNAME_C_Order_ID,
			I_R_Request.COLUMNNAME_C_Invoice_ID,
			I_R_Request.COLUMNNAME_M_Product_ID,
			I_R_Request.COLUMNNAME_C_Payment_ID,
			I_R_Request.COLUMNNAME_M_InOut_ID,
			I_R_Request.COLUMNNAME_M_RMA_ID,
			// I_R_Request.COLUMNNAME_C_Campaign_ID,
			// I_R_Request.COLUMNNAME_RequestAmt,
			I_R_Request.COLUMNNAME_IsInvoiced,
			I_R_Request.COLUMNNAME_C_Activity_ID,
			I_R_Request.COLUMNNAME_DateNextAction,
			I_R_Request.COLUMNNAME_M_ProductSpent_ID,
			I_R_Request.COLUMNNAME_QtySpent,
			I_R_Request.COLUMNNAME_QtyInvoiced,
			I_R_Request.COLUMNNAME_StartTime,
			I_R_Request.COLUMNNAME_CloseDate,
			I_R_Request.COLUMNNAME_TaskStatus,
			I_R_Request.COLUMNNAME_DateStartPlan,
			I_R_Request.COLUMNNAME_DateCompletePlan);

	private boolean checkChanges(final MRequestAction ra)
	{
		boolean hasChanges = false;
		for (final String columnName : columnsToCheckForRequestActionChanges)
		{
			if (checkChangeAndUpdateRequestAction(ra, columnName))
			{
				hasChanges = true;
			}
		}

		return hasChanges;
	}

	/**
	 * Check for changes
	 * 
	 * @param ra request action
	 * @param columnName column
	 * @return true if changes
	 */
	private boolean checkChangeAndUpdateRequestAction(final MRequestAction ra, final String columnName)
	{
		if (is_ValueChanged(columnName))
		{
			Object value = get_ValueOld(columnName);
			if (value == null)
			{
				ra.addNullColumn(columnName);
			}
			else
			{
				ra.set_ValueNoCheck(columnName, value);
			}

			return true;
		}
		else
		{
			return false;
		}
	}	// checkChange

	/**
	 * Set SalesRep_ID
	 * 
	 * @param SalesRep_ID id
	 */
	@Override
	public void setSalesRep_ID(int SalesRep_ID)
	{
		if (SalesRep_ID != 0)
		{
			super.setSalesRep_ID(SalesRep_ID);
		}
		else if (getSalesRep_ID() != 0)
		{
			log.warn("Ignored - Tried to set SalesRep_ID to 0 from " + getSalesRep_ID());
		}
	}	// setSalesRep_ID

	/**
	 * After Save
	 * 
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Create Update
		if (newRecord && getResult() != null)
		{
			final MRequestUpdate update = new MRequestUpdate(this);
			update.save();
		}

		//
		sendNotifications();

		// ChangeRequest - created in Request Processor
		if (getM_ChangeRequest_ID() != 0
				&& is_ValueChanged(COLUMNNAME_R_Group_ID))	// different ECN assignment?
		{
			int oldID = get_ValueOldAsInt(COLUMNNAME_R_Group_ID);
			if (getR_Group_ID() == 0)
			{
				setM_ChangeRequest_ID(0);	// not effective as in afterSave
			}
			else
			{
				MGroup oldG = MGroup.get(getCtx(), oldID);
				MGroup newG = MGroup.get(getCtx(), getR_Group_ID());
				if (oldG.getPP_Product_BOM_ID() != newG.getPP_Product_BOM_ID()
						|| oldG.getM_ChangeNotice_ID() != newG.getM_ChangeNotice_ID())
				{
					MChangeRequest ecr = new MChangeRequest(getCtx(), getM_ChangeRequest_ID(), get_TrxName());
					if (!ecr.isProcessed()
							|| ecr.getM_FixChangeNotice_ID() == 0)
					{
						ecr.setPP_Product_BOM_ID(newG.getPP_Product_BOM_ID());
						ecr.setM_ChangeNotice_ID(newG.getM_ChangeNotice_ID());
						ecr.save();
					}
				}
			}
		}

		return success;
	}	// afterSave

	private void sendNotifications()
	{
		final UserId oldSalesRepId = getOldSalesRepId();
		final UserId newSalesRepId = getNewSalesRepId();
		if (!UserId.equals(oldSalesRepId, newSalesRepId))
		{
			RequestNotificationsSender.newInstance()
					.notifySalesRepChanged(RequestSalesRepChanged.builder()
							.changedById(UserId.ofRepoIdOrNull(getUpdatedBy()))
							.fromSalesRepId(oldSalesRepId)
							.toSalesRepId(newSalesRepId)
							.requestDocumentNo(getDocumentNo())
							.requestId(RequestId.ofRepoId(getR_Request_ID()))
							.build());
		}
	}

	private UserId getOldSalesRepId()
	{
		final Object oldSalesRepIdObj = get_ValueOld(I_R_Request.COLUMNNAME_SalesRep_ID);
		if (oldSalesRepIdObj instanceof Integer)
		{
			final int repoId = ((Integer)oldSalesRepIdObj).intValue();
			return UserId.ofRepoId(repoId);
		}
		else
		{
			return null;
		}
	}

	private UserId getNewSalesRepId()
	{
		final int repoId = getSalesRep_ID();
		// NOTE: System(=0) is not a valid SalesRep anyways
		return repoId > 0
				? UserId.ofRepoId(repoId)
				: null;
	}
}
