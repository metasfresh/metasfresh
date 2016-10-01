package de.metas.callcenter.form;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.model.GridTab;
import org.compiere.model.I_R_ContactInterest;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MGroup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.callcenter.model.BundleUtil;
import de.metas.callcenter.model.CallCenterValidator;
import de.metas.callcenter.model.I_RV_R_Group_Prospect;
import de.metas.callcenter.model.I_R_Group_Prospect;
import de.metas.callcenter.model.I_R_Request;
import de.metas.callcenter.model.I_R_RequestUpdate;
import de.metas.callcenter.model.MRGroupProspect;
import de.metas.logging.LogManager;

/**
 * 
 * @author Teo Sarca, teo.sarca@gmail.com
 *
 */
public class CallCenterModel
{
	public static final int R_Group_AllBundles = -2;
	
	private final Logger log = LogManager.getLogger(getClass());
	
	private Properties m_ctx;
	private int m_windowNo;
	private Lookup m_bundlesLookup;
	private GridTab m_mTab;
	private GridTab m_mTabRequestUpdates;
	private GridTab m_mTabOtherRequests;
	private GridTab m_mTabInterestArea;
	private int m_R_Group_ID = -1;
	private boolean m_isShowOnlyDue = false;
	private boolean m_isShowOnlyOpen = false;
	
    ColumnInfo[] s_requestsTableLayout = new ColumnInfo[]{
    		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name", String.class),
    		new ColumnInfo(Msg.translate(Env.getCtx(), "Contact"), "Contact", String.class),
    		new ColumnInfo(Msg.translate(Env.getCtx(), "Phone"), "Phone", String.class),
    		new ColumnInfo(Msg.translate(Env.getCtx(), "Status"), "Status", String.class)
    };

	public CallCenterModel(Properties ctx, int windowNo)
	{
		m_ctx = ctx;
		m_windowNo = windowNo;
	}
	
	public Properties getCtx()
	{
		return m_ctx;
	}
	
	public int getWindowNo()
	{
		return m_windowNo;
	}
	
	public Lookup getBundlesLookup()
	{
		if (m_bundlesLookup != null)
		{
			return m_bundlesLookup;
		}
		final String validationCode =
			// All bundles that are not Done or Close
			"R_Group."+BundleUtil.R_Group_CCM_Bundle_Status+" NOT IN ("
			+" "+DB.TO_STRING(BundleUtil.CCM_Bundle_Status_Done)
			+","+DB.TO_STRING(BundleUtil.CCM_Bundle_Status_Closed)
			+")";
		try
		{
			final MColumn c = MTable.get(m_ctx, I_R_Group_Prospect.Table_ID).getColumn(I_R_Group_Prospect.COLUMNNAME_R_Group_ID);
			m_bundlesLookup = MLookupFactory.get(m_ctx, m_windowNo,
					0,								// Column_ID,
					DisplayType.Table,				//AD_Reference_ID,
					c.getColumnName(),				// ColumnName
					c.getAD_Reference_Value_ID(),	// AD_Reference_Value_ID,
					false,							// IsParent,
					validationCode					// ValidationCode
			);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		//
		return m_bundlesLookup;
	}
	
	public int getAD_Window_ID()
	{
		return MSysConfig.getIntValue("de.metas.callcenter.form.CallCenterModel.AD_Window_ID",
				540013, //Request Group Selected Prospects (internal) 
				Env.getAD_Client_ID(m_ctx));
	}
	
	public GridTab getContactsGridTab()
	{
		if (m_mTab != null)
			return m_mTab;
		m_mTab = MiscUtils.getGridTabForTableAndWindow(m_ctx, m_windowNo, getAD_Window_ID(), I_RV_R_Group_Prospect.Table_ID, true);
		return m_mTab;
	}
	
	public GridTab getRequestUpdatesGridTab()
	{
		if (m_mTabRequestUpdates != null)
			return m_mTabRequestUpdates;
		m_mTabRequestUpdates = MiscUtils.getGridTabForTableAndWindow(m_ctx, m_windowNo, getAD_Window_ID(), I_R_RequestUpdate.Table_ID, true);
		return m_mTabRequestUpdates;
	}
	
	public GridTab getOtherRequestsGridTab()
	{
		if (m_mTabOtherRequests != null)
			return m_mTabOtherRequests;
		m_mTabOtherRequests = MiscUtils.getGridTabForTableAndWindow(m_ctx, m_windowNo, getAD_Window_ID(), InterfaceWrapperHelper.getTableId(I_R_Request.class), true);
		return m_mTabOtherRequests;
	}
	
	public GridTab getContactInterestGridTab()
	{
		if (m_mTabInterestArea != null)
			return m_mTabInterestArea;
		m_mTabInterestArea = MiscUtils.getGridTabForTableAndWindow(m_ctx, m_windowNo, getAD_Window_ID(), I_R_ContactInterest.Table_ID, true);
		return m_mTabInterestArea;
	}
	
	public I_RV_R_Group_Prospect getRV_R_Group_Prospect(boolean refresh)
	{
		if (refresh)
			m_mTab.dataRefresh();
		I_RV_R_Group_Prospect contact = InterfaceWrapperHelper.create(m_mTab, I_RV_R_Group_Prospect.class);
		return contact;
	}
	
	public boolean isContactSelected()
	{
		GridTab tab = getContactsGridTab();
		boolean selected = tab != null && tab.getCurrentRow() >= 0;
		return selected;
	}
	
	public void query(int R_Group_ID, boolean isShowOnlyDue, boolean isShowOnlyOpen)
	{
		m_R_Group_ID = R_Group_ID;
		m_isShowOnlyDue = isShowOnlyDue;
		m_isShowOnlyOpen = isShowOnlyOpen;
		MQuery query = new MQuery(m_mTab.getTableName());
		if (m_R_Group_ID != R_Group_AllBundles)
		{
			query.addRestriction(I_RV_R_Group_Prospect.COLUMNNAME_R_Group_ID, Operator.EQUAL, m_R_Group_ID);
		}
		if (m_isShowOnlyDue)
		{
			query.addRestriction(I_RV_R_Group_Prospect.COLUMNNAME_DateNextAction+" IS NULL"
					+" OR "+I_RV_R_Group_Prospect.COLUMNNAME_DateNextAction+" < getDate()");
		}
		if (m_isShowOnlyOpen)
		{
			query.addRestriction(I_RV_R_Group_Prospect.COLUMNNAME_IsClosed+"<>'Y'"
					+" AND "+I_RV_R_Group_Prospect.COLUMNNAME_IsFinalClose+"<>'Y'");
		}
		m_mTab.setQuery(query);
		m_mTab.query(false);
		//m_mTab.dataRefresh();
		queryDetails();
	}
	
	public void queryDetails()
	{
		final I_RV_R_Group_Prospect contact = getRV_R_Group_Prospect(true);
		//System.out.println(toString(contact));
		//
		//
		{
			MQuery query = new MQuery(m_mTabRequestUpdates.getTableName());
			//
			if (contact != null)
			{
				query.addRestriction(I_R_RequestUpdate.COLUMNNAME_R_Request_ID, Operator.EQUAL, contact.getR_Request_ID());
			}
			else
			{
				query.addRestriction("1=2");
			}
			//
			m_mTabRequestUpdates.setQuery(query);
			m_mTabRequestUpdates.query(false);
		}
		//
		//
		{
			MQuery query = new MQuery(m_mTabOtherRequests.getTableName());
			//
			if (contact != null)
			{
				query.addRestriction(I_R_Request.COLUMNNAME_C_BPartner_ID, Operator.EQUAL, contact.getC_BPartner_ID());
				query.addRestriction(I_R_Request.COLUMNNAME_R_Request_ID, Operator.NOT_EQUAL, contact.getR_Request_ID());
				query.addRestriction(I_R_Request.COLUMNNAME_R_RequestType_ID, Operator.EQUAL, getDefault_RequestType_ID());
			}
			else
			{
				query.addRestriction("1=2");
			}
			//
			m_mTabOtherRequests.setQuery(query);
			m_mTabOtherRequests.query(false);
		}
		//
		//
		if (m_mTabInterestArea != null)
		{
			MQuery query = new MQuery(m_mTabInterestArea.getTableName());
			if (contact != null)
			{
				query.addRestriction(I_R_ContactInterest.COLUMNNAME_AD_User_ID, Operator.EQUAL, contact.getAD_User_ID());
				query.addRestriction(I_R_ContactInterest.COLUMNNAME_IsActive, Operator.EQUAL, "Y");
			}
			else
			{
				query.addRestriction("1=2");
			}
			m_mTabInterestArea.setQuery(query);
			m_mTabInterestArea.query(false);
		}
	}
	
	public String getBundleInfo(int R_Group_ID)
	{
		if (R_Group_ID <= 0)
			return "";
		
		final String sql = "SELECT rs.Value, count(*), rs.SeqNo"
			+" FROM R_Group_Prospect rgp"
			+" LEFT OUTER JOIN R_Request rq ON (rq.R_Request_ID=rgp.R_Request_ID)"
			+" LEFT OUTER JOIN R_Status rs ON (rs.R_Status_ID=rq.R_Status_ID)"
		+" WHERE rgp.R_Group_ID=?"
		+" GROUP BY rs.R_Status_ID, rs.Value, rs.SeqNo"
		+" ORDER BY rs.SeqNo, rs.Value"
		;
		StringBuffer info = new StringBuffer();
		int count_all = 0;
		int count_new = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			DB.setParameters(pstmt, new Object[]{R_Group_ID});
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String name = rs.getString(1);
				int count = rs.getInt(2);
				if (name == null)
				{
					count_new += count;
				}
				else
				{
					if (info.length() > 0)
						info.append(", ");
					info.append(count).append(" ").append(name);
				}
				count_all += count;
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		// New records
		if (count_new > 0)
		{
			String name = Msg.translate(m_ctx, "de.metas.callcenter.NewContacts");
			if (info.length() > 0)
				info.insert(0, ", ");
			info.insert(0, count_new+" "+name);
		}
		// All records
		if (count_all > 0 && info.length() > 0)
		{
			String name = Msg.translate(m_ctx, "de.metas.callcenter.TotalRecords");
			info.insert(0, count_all+" "+name+", ");
		}
		//
		return info.toString();
	}
	
	public int getLoggedUser_ID()
	{
		return Env.getAD_User_ID(m_ctx);
	}
	
	public int getDefault_RequestType_ID()
	{
		int AD_Client_ID = Env.getAD_Client_ID(m_ctx);
		if (m_Default_R_RequestType_ID <= 0)
		{
			m_Default_R_RequestType_ID = DB.getSQLValueEx(null,
					"SELECT R_RequestType_ID FROM R_RequestType"
					+" WHERE AD_Client_ID=?"
					+" AND "+BundleUtil.R_RequestType_CCM_Default+"=?",
					new Object[]{AD_Client_ID, true});
		}
		if (m_Default_R_RequestType_ID <= 0)
		{
			log.warn("No Call Center Default request type defined."
					+" Please open the Request Type window and check Default for Call Center."); 
		}
		return m_Default_R_RequestType_ID;
	}
	private int m_Default_R_RequestType_ID = -1;
	
	public int getRequest_Window_ID()
	{
		if (s_Request_Window_ID > 0)
		{
			return s_Request_Window_ID;
		}
		
		s_Request_Window_ID = DB.getSQLValueEx(null,
				"SELECT tt.AD_Window_ID FROM AD_Tab tt"
				+" WHERE tt.AD_Table_ID=? AND tt.TabLevel=? AND tt.EntityType=?"
				+" ORDER BY tt.AD_Tab_ID",
				new Object[]{MTable.getTable_ID(I_R_Request.Table_Name), 0, CallCenterValidator.ENTITYTYPE});
		if (s_Request_Window_ID <= 0)
		{
			throw new AdempiereException("Request window not found!");
		}
		return s_Request_Window_ID;
	}
	private int s_Request_Window_ID = -1;
	
	protected void lockContact(I_RV_R_Group_Prospect contact)
	{
		MRGroupProspect gp = MRGroupProspect.get(Env.getCtx(), contact, null);
		gp.expireLock();
		if (gp.isLocked() && gp.getLockedBy() != getLoggedUser_ID())
		{
			gp.saveEx();
			throw new AdempiereException("de.metas.callcenter.ContactAlreadyLocked");
		}
		gp.lockContact();
		gp.saveEx();
		getContactsGridTab().dataRefresh();
	}
	
	protected void unlockContact(I_RV_R_Group_Prospect contact)
	{
		MRGroupProspect gp = MRGroupProspect.get(Env.getCtx(), contact, null);
		if (!gp.isLocked())
		{
			return;
		}
		if (gp.getLockedBy() != getLoggedUser_ID())
		{
			throw new AdempiereException("de.metas.callcenter.ContactLockedByOther");
		}
		gp.unlockContact();
		gp.saveEx();
		getContactsGridTab().dataRefresh();
	}
	
	public void setRequestDefaults(I_R_Request request, I_RV_R_Group_Prospect contact)
	{
		request.setAD_Org_ID(contact.getAD_Org_ID());
		request.setR_Group_ID(contact.getR_Group_ID());
		request.setC_BPartner_ID(contact.getC_BPartner_ID());
		request.setAD_User_ID(contact.getAD_User_ID());
		request.setSalesRep_ID(Env.getAD_User_ID(m_ctx));
		//
		MGroup group = MGroup.get(Env.getCtx(), contact.getR_Group_ID());
		String summary = group.getDescription();
		if (Util.isEmpty(summary))
			summary = group.getHelp();
		if (Util.isEmpty(summary))
			summary = group.getName();
		request.setSummary(summary);
		request.setR_RequestType_ID(getDefault_RequestType_ID());
		request.setR_Category_ID(group.get_ValueAsInt(BundleUtil.R_Group_R_Category_ID));
	}
	
	public void setContactPhoneNo(de.metas.callcenter.model.I_R_Request request, ContactPhoneNo phoneNo)
	{
		if (phoneNo == null)
			return;
		if (!Util.isEmpty(phoneNo.getPhoneNo()))
		{
			request.setCCM_PhoneActual(phoneNo.getPhoneNo());
			if (phoneNo.getAD_User_ID() > 0)
			{
				request.setAD_User_ID(phoneNo.getAD_User_ID());
			}
		}
	}
	
	public static String toString(I_RV_R_Group_Prospect c)
	{
		if (c == null)
			return "null";
		StringBuffer sb = new StringBuffer();
		sb.append("RV_R_Group_Prospect[")
		.append("C_BPartner_ID="+c.getBPValue()+"/"+c.getC_BPartner_ID())
		.append(", R_Request_ID="+c.getR_Request_ID())
		.append("]");
		return sb.toString();
	}
	
	public List<ContactPhoneNo> getContactPhoneNumbers()
	{
		final I_RV_R_Group_Prospect prospect = getRV_R_Group_Prospect(false);
		final ArrayList<ContactPhoneNo> list = new ArrayList<ContactPhoneNo>();
		if (prospect == null)
			return list;
		for (MUser contact : MUser.getOfBPartner(m_ctx, prospect.getC_BPartner_ID(), null))
		{
			if (!Util.isEmpty(contact.getPhone(), true))
				list.add(new ContactPhoneNo(contact.getPhone(), contact.getName(), contact.getAD_User_ID()));
			if (!Util.isEmpty(contact.getPhone2(), true))
				list.add(new ContactPhoneNo(contact.getPhone2(), contact.getName(), contact.getAD_User_ID()));
		}
		return list;
	}
}
