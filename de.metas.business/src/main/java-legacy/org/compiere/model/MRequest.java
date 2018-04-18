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

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.core.io.FileSystemResource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.event.Topic;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.UserNotificationRequestBuilder;

/**
 * Request Model
 *
 * @author Jorg Janke
 * @version $Id: MRequest.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRequest extends X_R_Request
{
	/**
	 * Shall we notify users if something changed?
	 * 
	 * @see http://dewiki908/mediawiki/index.php/06113_E-Mail_Notice_Fenster_Rechtsberatung_Org._015_%28107601692471%29
	 */
	public static final String SYSCONFIG_EnableNotifications = "org.compiere.model.MRequest.EnableNotifications";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3989278951102963994L;

	/**
	 * Get Request ID from mail text
	 * 
	 * @param mailText mail text
	 * @return ID if it contains request tag otherwise 0
	 */
	public static int getR_Request_ID(String mailText)
	{
		if (mailText == null)
			return 0;
		int indexStart = mailText.indexOf(TAG_START);
		if (indexStart == -1)
			return 0;
		int indexEnd = mailText.indexOf(TAG_END, indexStart);
		if (indexEnd == -1)
			return 0;
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
	public static final String SEPARATOR = "\n---------.----------.----------.----------.----------.----------\n";

	public static final Topic TOPIC_Requests = Topic.remote("de.metas.requests");

	/** Request Type */
	private MRequestType m_requestType = null;
	/** Changed */
	private boolean m_changed = false;


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
		set_Value("SalesRep_ID", SalesRep_ID < 0 ? null : new Integer(SalesRep_ID));	// could be 0
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
			log.warn("No default found");
		else
			super.setR_RequestType_ID(m_requestType.getR_RequestType_ID());
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
				setR_Status_ID(0);
		}
		else
			setR_Status_ID(status.getR_Status_ID());
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
			;
		else if (oldResult == null || oldResult.length() == 0)
			setResult(Result);
		else
			setResult(oldResult + "\n-\n" + Result);
	}	// addToResult

	/**
	 * Set DueType based on Date Next Action
	 */
	public void setDueType()
	{
		Timestamp due = getDateNextAction();
		if (due == null)
			return;
		//
		Timestamp overdue = TimeUtil.addDays(due, getRequestType().getDueDateTolerance());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		//
		String DueType = DUETYPE_Faellig;
		if (now.before(due))
			DueType = DUETYPE_Geplant;
		else if (now.after(overdue))
			DueType = DUETYPE_Ueberfaellig;
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
					continue;
				// Internal not if Customer/Public
				if (ru.getConfidentialTypeEntry().equals(CONFIDENTIALTYPEENTRY_Internal)
						&& (confidentialType.equals(CONFIDENTIALTYPEENTRY_PartnerConfidential)
								|| confidentialType.equals(CONFIDENTIALTYPEENTRY_PublicInformation)))
					continue;
				// No Customer if public
				if (ru.getConfidentialTypeEntry().equals(CONFIDENTIALTYPEENTRY_PartnerConfidential)
						&& confidentialType.equals(CONFIDENTIALTYPEENTRY_PublicInformation))
					continue;
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
			return null;
		return Services.get(IUserDAO.class).retrieveUserOrNull(getCtx(), getSalesRep_ID());
	}	// getSalesRep

	private I_C_BPartner getBPartner()
	{
		if (getC_BPartner_ID() <= 0)
			return null;
		return loadOutOfTrx(getC_BPartner_ID(), I_C_BPartner.class);
	}

	private void setPriority()
	{
		if (getPriorityUser() == null)
			setPriorityUser(PRIORITYUSER_Low);
		//
		if (getBPartner() != null)
		{
			MBPGroup bpg = MBPGroup.get(getCtx(), getBPartner().getC_BP_Group_ID());
			String prioBase = bpg.getPriorityBase();
			if (prioBase != null && !prioBase.equals(X_C_BP_Group.PRIORITYBASE_Same))
			{
				char targetPrio = getPriorityUser().charAt(0);
				if (prioBase.equals(X_C_BP_Group.PRIORITYBASE_Lower))
					targetPrio += 2;
				else
					targetPrio -= 2;
				if (targetPrio < PRIORITY_High.charAt(0))	// 1
					targetPrio = PRIORITY_High.charAt(0);
				if (targetPrio > PRIORITY_Low.charAt(0))	// 9
					targetPrio = PRIORITY_Low.charAt(0);
				if (getPriority() == null)
					setPriority(String.valueOf(targetPrio));
				else	// previous priority
				{
					if (targetPrio < getPriority().charAt(0))
						setPriority(String.valueOf(targetPrio));
				}
			}
		}
		// Same if nothing else
		if (getPriority() == null)
			setPriority(getPriorityUser());
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
			ConfidentialTypeEntry = getConfidentialType();
		//
		if (CONFIDENTIALTYPE_Internal.equals(getConfidentialType()))
			super.setConfidentialTypeEntry(CONFIDENTIALTYPE_Internal);
		else if (CONFIDENTIALTYPE_PrivateInformation.equals(getConfidentialType()))
		{
			if (CONFIDENTIALTYPE_Internal.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PrivateInformation.equals(ConfidentialTypeEntry))
				super.setConfidentialTypeEntry(ConfidentialTypeEntry);
			else
				super.setConfidentialTypeEntry(CONFIDENTIALTYPE_PrivateInformation);
		}
		else if (CONFIDENTIALTYPE_PartnerConfidential.equals(getConfidentialType()))
		{
			if (CONFIDENTIALTYPE_Internal.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PrivateInformation.equals(ConfidentialTypeEntry)
					|| CONFIDENTIALTYPE_PartnerConfidential.equals(ConfidentialTypeEntry))
				super.setConfidentialTypeEntry(ConfidentialTypeEntry);
			else
				super.setConfidentialTypeEntry(CONFIDENTIALTYPE_PartnerConfidential);
		}
		else if (CONFIDENTIALTYPE_PublicInformation.equals(getConfidentialType()))
			super.setConfidentialTypeEntry(ConfidentialTypeEntry);
	}	// setConfidentialTypeEntry

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MRequest[");
		sb.append(get_ID()).append("-").append(getDocumentNo()).append("]");
		return sb.toString();
	}	// toString

	/**
	 * Create PDF
	 * 
	 * @return pdf or null
	 */
	public File createPDF()
	{
		// globalqss - comment to solve bug [ 1688794 ] System is generating lots of temp files
		// try
		// {
		// File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
		// return createPDF (temp);
		// }
		// catch (Exception e)
		// {
		// log.error("Could not create PDF - " + e.getMessage());
		// }
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
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}	// createPDF

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
					setIsInvoiced(m_requestType.isInvoiced());
				if (getDateNextAction() == null && m_requestType.getAutoDueDateDays() > 0)
					setDateNextAction(TimeUtil.addDays(new Timestamp(System.currentTimeMillis()),
							m_requestType.getAutoDueDateDays()));
			}
			// Is Status Valid
			if (getR_Status_ID() != 0)
			{
				MStatus sta = MStatus.get(getCtx(), getR_Status_ID());
				MRequestType rt = MRequestType.get(getCtx(), getR_RequestType_ID());
				if (sta.getR_StatusCategory_ID() != rt.getR_StatusCategory_ID())
					setR_Status_ID();	// set to default
			}
		}

		// Request Status
		if (getR_Status_ID() == 0)
			setR_Status_ID();
		// Validate/Update Due Type
		setDueType();
		MStatus status = MStatus.get(getCtx(), getR_Status_ID());
		// Close/Open
		if (status != null)
		{
			if (status.isOpen())
			{
				if (getStartDate() == null)
					setStartDate(new Timestamp(System.currentTimeMillis()));
				if (getCloseDate() != null)
					setCloseDate(null);
			}
			if (status.isClosed()
					&& getCloseDate() == null)
				setCloseDate(new Timestamp(System.currentTimeMillis()));
			if (status.isFinalClose())
				setProcessed(true);
		}

		// Confidential Info
		if (getConfidentialType() == null)
		{
			getRequestType();
			if (m_requestType != null)
			{
				String ct = m_requestType.getConfidentialType();
				if (ct != null)
					setConfidentialType(ct);
			}
			if (getConfidentialType() == null)
				setConfidentialType(CONFIDENTIALTYPEENTRY_PublicInformation);
		}
		if (getConfidentialTypeEntry() == null)
			setConfidentialTypeEntry(getConfidentialType());
		else
			setConfidentialTypeEntry(getConfidentialTypeEntry());

		// Importance / Priority
		setPriority();

		// New
		if (newRecord)
			return true;

		// Change Log
		m_changed = false;
		ArrayList<String> sendInfo = new ArrayList<>();
		MRequestAction ra = new MRequestAction(this, false);
		//
		if (checkChange(ra, "R_RequestType_ID"))
			sendInfo.add("R_RequestType_ID");
		if (checkChange(ra, "R_Group_ID"))
			sendInfo.add("R_Group_ID");
		if (checkChange(ra, "R_Category_ID"))
			sendInfo.add("R_Category_ID");
		if (checkChange(ra, "R_Status_ID"))
			sendInfo.add("R_Status_ID");
		if (checkChange(ra, "R_Resolution_ID"))
			sendInfo.add("R_Resolution_ID");
		//
		if (checkChange(ra, "SalesRep_ID"))
		{
			// Sender
			int AD_User_ID = Env.getAD_User_ID(getCtx());
			if (AD_User_ID == 0)
				AD_User_ID = getUpdatedBy();
			// Old
			Object oo = get_ValueOld("SalesRep_ID");
			int oldSalesRep_ID = 0;
			if (oo instanceof Integer)
				oldSalesRep_ID = ((Integer)oo).intValue();
			if (oldSalesRep_ID != 0)
			{
				final IUserDAO userDAO = Services.get(IUserDAO.class);
				// RequestActionTransfer - Request {} was transfered by {} from {} to {}
				Object[] args = new Object[] { getDocumentNo(),
						userDAO.retrieveUser(AD_User_ID),
						userDAO.retrieveUser(oldSalesRep_ID),
						userDAO.retrieveUser(getSalesRep_ID())
				};
				String msg = Services.get(IMsgBL.class).getMsg(getCtx(), "RequestActionTransfer", args);
				addToResult(msg);
				sendInfo.add("SalesRep_ID");
			}
		}
		checkChange(ra, "AD_Role_ID");
		//
		checkChange(ra, "Priority");
		if (checkChange(ra, "PriorityUser"))
			sendInfo.add("PriorityUser");
		if (checkChange(ra, "IsEscalated"))
			sendInfo.add("IsEscalated");
		//
		checkChange(ra, "ConfidentialType");
		checkChange(ra, "Summary");
		checkChange(ra, "IsSelfService");
		checkChange(ra, "C_BPartner_ID");
		checkChange(ra, "AD_User_ID");
		checkChange(ra, "C_Project_ID");
		checkChange(ra, "A_Asset_ID");
		checkChange(ra, "C_Order_ID");
		checkChange(ra, "C_Invoice_ID");
		checkChange(ra, "M_Product_ID");
		checkChange(ra, "C_Payment_ID");
		checkChange(ra, "M_InOut_ID");
		checkChange(ra, "M_RMA_ID");
		// checkChange(ra, "C_Campaign_ID");
		// checkChange(ra, "RequestAmt");
		checkChange(ra, "IsInvoiced");
		checkChange(ra, "C_Activity_ID");
		checkChange(ra, "DateNextAction");
		checkChange(ra, "M_ProductSpent_ID");
		checkChange(ra, "QtySpent");
		checkChange(ra, "QtyInvoiced");
		checkChange(ra, "StartDate");
		checkChange(ra, "CloseDate");
		checkChange(ra, "TaskStatus");
		checkChange(ra, "DateStartPlan");
		checkChange(ra, "DateCompletePlan");
		//
		if (m_changed)
			ra.save();

		// Current Info
		MRequestUpdate update = new MRequestUpdate(this);
		if (update.isNewInfo())
			update.save();
		else
			update = null;
		//
		if (update != null || sendInfo.size() > 0)
		{
			// Note that calling the notifications from beforeSave is causing the
			// new interested are not notified if the RV_RequestUpdates view changes
			// this is, when changed the sales rep (solved in sendNotices)
			// or when changed the request category or group or contact (unsolved - the old ones are notified)
			sendNotices(sendInfo);

			// Update
			setDateLastAction(getUpdated());
			setLastResult(getResult());
			setDueType();
			// Reset
			setConfidentialTypeEntry(getConfidentialType());
			// setStartDate(null); //red1 - bug [ 1743159 ] Requests - Start Date is not retained.
			setEndTime(null);
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

	/**
	 * Check for changes
	 * 
	 * @param ra request action
	 * @param columnName column
	 * @return true if changes
	 */
	private boolean checkChange(MRequestAction ra, String columnName)
	{
		if (is_ValueChanged(columnName))
		{
			Object value = get_ValueOld(columnName);
			if (value == null)
				ra.addNullColumn(columnName);
			else
				ra.set_ValueNoCheck(columnName, value);
			m_changed = true;
			return true;
		}
		return false;
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
			super.setSalesRep_ID(SalesRep_ID);
		else if (getSalesRep_ID() != 0)
			log.warn("Ignored - Tried to set SalesRep_ID to 0 from " + getSalesRep_ID());
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
			return success;

		// Create Update
		if (newRecord && getResult() != null)
		{
			MRequestUpdate update = new MRequestUpdate(this);
			update.save();
		}

		//
		// Send Initial Mail
		if (newRecord)
		{
			sendNotices(new ArrayList<String>());
		}

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

	/**
	 * Send Update EMail/Notices
	 * 
	 * @param list list of changes
	 */
	private void sendNotices(final List<String> changedColumnNames)
	{
		//
		// Shall we send notifications?
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableNotifications, true, getAD_Client_ID(), getAD_Org_ID()))
		{
			return;
		}

		final Set<Integer> userIdsToNotify = getUserIdsToNotify();
		if (userIdsToNotify.isEmpty())
		{
			return;
		}

		// Subject
		final ITranslatableString subject = TranslatableStringBuilder.newInstance()
				.appendADElement("R_Request_ID").append(" ").appendADElement("Updated").append(": " + getDocumentNo())
				.build();

		// Content
		final int updatedByUserId = Env.getAD_User_ID(getCtx());
		final I_AD_User updatedByUser = Services.get(IUserDAO.class).retrieveUserOrNull(getCtx(), updatedByUserId);
		final ITranslatableString content = buildEMailNotificationContent(updatedByUser, changedColumnNames);

		final File pdf = createPDF();

		final IUserDAO userDAO = Services.get(IUserDAO.class);
		final List<UserNotificationRequest> notificationRequests = userIdsToNotify.stream()
				.map(userId -> {
					final UserNotificationsConfig notificationsConfig = userDAO.getUserNotificationsConfig(userId);
					final String adLanguage = notificationsConfig.getUserADLanguageOrGet(Env::getADLanguageOrBaseLanguage);
					final UserNotificationRequestBuilder builder = UserNotificationRequest.builder()
							.notificationsConfig(notificationsConfig)
							.topic(TOPIC_Requests)
							.recipientUserId(userId)
							.subjectPlain(subject.translate(adLanguage))
							.contentPlain(content.translate(adLanguage))
							.targetRecord(TableRecordReference.of(I_R_Request.Table_Name, getR_Request_ID()));

					if (pdf != null)
					{
						builder.attachment(new FileSystemResource(pdf));
					}

					return builder.build();
				})
				.collect(ImmutableList.toImmutableList());

		Services.get(INotificationBL.class).notifyUserAfterCommit(notificationRequests);
	}	// sendNotice

	private ITranslatableString buildEMailNotificationContent(final I_AD_User from, final List<String> changedColumnNames)
	{
		// Message
		final TranslatableStringBuilder messageBuilder = TranslatableStringBuilder.newInstance();

		// UpdatedBy: Joe
		if (from != null)
			messageBuilder.appendADElement(COLUMNNAME_UpdatedBy).append(": ").append(from.getName());
		// LastAction/Created: ...
		if (getDateLastAction() != null)
			messageBuilder.append("\n").appendADElement(COLUMNNAME_DateLastAction).append(": ").appendDateTime(getDateLastAction());
		else
			messageBuilder.append("\n").appendADElement(COLUMNNAME_Created).append(": ").appendDateTime(getCreated());

		// Changes
		for (final String columnName : changedColumnNames)
		{
			messageBuilder.append("\n").appendADElement(columnName)
					.append(": ").append(get_DisplayValue(columnName, false))
					.append(" -> ").append(get_DisplayValue(columnName, true));
		}

		// NextAction
		if (getDateNextAction() != null)
			messageBuilder.append("\n").appendADElement(COLUMNNAME_DateNextAction).append(": ").appendDateTime(getDateNextAction());

		messageBuilder.append(SEPARATOR)
				.append(getSummary());

		if (getResult() != null)
			messageBuilder.append("\n----------\n").append(getResult());

		// Mail Trailer
		messageBuilder.append(SEPARATOR)
				.appendADElement("R_Request_ID").append(": ").append(getDocumentNo()).append("  ").append(TAG_START + getR_Request_ID() + TAG_END)
				.append("\nSent by ").append(Adempiere.getName());

		return messageBuilder.build();
	}

	private Set<Integer> getUserIdsToNotify()
	{
		final String sql = "SELECT u.AD_User_ID, MAX(r.AD_Role_ID) "
				+ "FROM RV_RequestUpdates_Only ru"
				+ " INNER JOIN AD_User u ON (ru.AD_User_ID=u.AD_User_ID OR u.AD_User_ID=?)"
				+ " LEFT OUTER JOIN AD_User_Roles r ON (u.AD_User_ID=r.AD_User_ID and r.IsActive='Y') "
				+ "WHERE ru.R_Request_ID=? "
				+ "GROUP BY u.AD_User_ID";
		final Object[] sqlParams = new Object[] { getSalesRep_ID(), getR_Request_ID() };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<Integer> userIds = ImmutableSet.builder();
			while (rs.next())
			{
				final int adUserId = rs.getInt("AD_User_ID");

				rs.getInt("AD_Role_ID");
				final boolean isInternalUser = !rs.wasNull();

				// No confidential to externals
				if (isInternalUser
						&& (CONFIDENTIALTYPE_Internal.equals(getConfidentialTypeEntry())
								|| CONFIDENTIALTYPE_PrivateInformation.equals(getConfidentialTypeEntry())))
				{
					continue;
				}

				userIds.add(adUserId);
			}

			return userIds.build();
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
