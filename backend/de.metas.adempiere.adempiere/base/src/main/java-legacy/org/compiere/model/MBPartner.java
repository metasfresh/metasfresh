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

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Business Partner Model
 *
 * @author Jorg Janke
 * @version $Id: MBPartner.java,v 1.5 2006/09/23 19:38:07 comdivision Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1568774 ] Walk-In BP:
 *         invalid created/updated values
 *         <li>BF [ 1817752 ]
 *         MBPartner.getLocations should return only active one
 * @author Armen Rizal, GOODWILL CONSULT
 *         <LI>BF [ 2041226 ] BP Open Balance
 *         should count only Completed Invoice
 *         <LI>BF [ 2498949 ] BP Get Not
 *         Invoiced Shipment Value return null
 */
// metas: synched with rev 10155
public class MBPartner extends X_C_BPartner
{
	private static final long serialVersionUID = -3669895599574182217L;


	/**
	 * Get BPartner with Value
	 *
	 * @param ctx
	 *            context
	 * @param Value
	 *            value
	 * @return BPartner or null
	 */
	public static MBPartner get(final Properties ctx, final String Value)
	{
		if (Value == null || Value.length() == 0)
		{
			return null;
		}
		final String whereClause = "Value=? AND AD_Client_ID=?";
		final MBPartner retValue = new Query(ctx, MBPartner.Table_Name, whereClause, null).setParameters(
				new Object[] { Value, Env.getAD_Client_ID(ctx) }).firstOnly();
		return retValue;
	} // get

	public MBPartner(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	public static MBPartner newFromTemplate()
	{
		return new MBPartner(Env.getCtx(), -1, ITrx.TRXNAME_ThreadInherited);
	}

	@Deprecated
	public MBPartner(final Properties ctx, int C_BPartner_ID, final String trxName)
	{
		super(ctx, C_BPartner_ID, trxName);
		//
		if (C_BPartner_ID == -1)
		{
			initTemplate(Env.getContextAsInt(ctx, "AD_Client_ID"));
			C_BPartner_ID = 0;
		}
		if (C_BPartner_ID == 0)
		{
			// setValue ("");
			// setName ("");
			// setName2 (null);
			// setDUNS("");
			//
			setIsCustomer(true);
			setIsProspect(true);
			//
			setSendEMail(false);
			setIsOneTime(false);
			setIsVendor(false);
			setIsSummary(false);
			setIsEmployee(false);
			setIsSalesRep(false);
			setIsTaxExempt(false);
			setIsPOTaxExempt(false);
			setIsDiscountPrinted(false);
			//
			setFirstSale(null);

			setPotentialLifeTimeValue(BigDecimal.ZERO);
			setAcqusitionCost(BigDecimal.ZERO);
			setShareOfCustomer(0);
			setSalesVolume(0);
		}
		// ts: doesn't work with table level caching, when this instance is created by PO.copy().
		// Reason: at this stage we don't yet have a POInfo, but the toString() method calls getValue which requires a POInfo
		// log.debug(toString());
	} // MBPartner

	/** Addressed */
	private MBPartnerLocation[] m_locations = null;
	/** BP Bank Accounts */
	private MBPBankAccount[] m_accounts = null;
	/** Prim Address */
	private Integer m_primaryC_BPartner_Location_ID = null;

	/** BP Group */
	private I_C_BP_Group m_group = null;

	/**
	 * Load Default BPartner
	 *
	 * @param AD_Client_ID
	 *            client
	 * @return true if loaded
	 */
	private boolean initTemplate(final int AD_Client_ID)
	{
		if (AD_Client_ID == 0)
		{
			throw new IllegalArgumentException("Client_ID=0");
		}

		boolean success = true;
		final String sql = "SELECT * FROM C_BPartner "
				+ "WHERE C_BPartner_ID IN (SELECT C_BPartnerCashTrx_ID FROM AD_ClientInfo WHERE AD_Client_ID=?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			final ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				success = load(rs);
			}
			else
			{
				load(0, null);
				success = false;
				log.error("None found");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
				{
					pstmt.close();
				}
			}
			catch (final Exception e)
			{
			}
			pstmt = null;
		}
		setStandardDefaults();
		// Reset
		set_ValueNoCheck("C_BPartner_ID", I_ZERO);
		setValue("");
		setName("");
		setName2(null);

		set_ValueNoCheck(COLUMNNAME_AD_OrgBP_ID, null); // metas-ts: avoid DBUniqueConstraintException as we have a unique-constraint on that column (for good reasons :-) )

		return success;
	} // getTemplate

	public List<I_AD_User> getContacts(final boolean reload_NOTUSED)
	{
		return Services.get(IBPartnerDAO.class).retrieveContacts(this);
	}

	/**
	 * Get specified or first Contact
	 *
	 * @param AD_User_ID
	 *            optional user
	 * @return contact or null
	 */
	public I_AD_User getContact(final int AD_User_ID)
	{
		final List<I_AD_User> users = getContacts(false);
		if (users.isEmpty())
		{
			return null;
		}
		for (final I_AD_User user : users)
		{
			if (user.getAD_User_ID() == AD_User_ID)
			{
				return user;
			}
		}
		return users.get(0);
	} // getContact

	/**
	 * Get All Locations (only active)
	 *
	 * @param reload
	 *            if true locations will be requeried
	 * @return locations
	 *
	 * @deprecated Please use {@link IBPartnerDAO#retrieveBPartnerLocations(I_C_BPartner)}
	 */
	@Deprecated
	public MBPartnerLocation[] getLocations(final boolean reload)
	{
		if (reload || m_locations == null || m_locations.length == 0)
		{
			;
		}
		else
		{
			return m_locations;
			//
		}

		final List<I_C_BPartner_Location> locations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(this);
		m_locations = LegacyAdapters.convertToPOArray(locations, MBPartnerLocation.class);

		return m_locations;
	} // getLocations

	/**
	 * Get explicit or first bill Location
	 *
	 * @param C_BPartner_Location_ID
	 *            optional explicit location
	 * @return location or null
	 */
	public MBPartnerLocation getLocation(final int C_BPartner_Location_ID)
	{
		final MBPartnerLocation[] locations = getLocations(false);
		if (locations.length == 0)
		{
			return null;
		}
		MBPartnerLocation retValue = null;
		for (final MBPartnerLocation location : locations)
		{
			if (location.getC_BPartner_Location_ID() == C_BPartner_Location_ID)
			{
				return location;
			}
			if (retValue == null && location.isBillTo())
			{
				retValue = location;
			}
		}
		if (retValue == null)
		{
			return locations[0];
		}
		return retValue;
	} // getLocation

	/**
	 * Get Bank Accounts
	 *
	 * @param requery
	 *            requery
	 * @return Bank Accounts
	 */
	public MBPBankAccount[] getBankAccounts(final boolean requery)
	{
		if (m_accounts != null && m_accounts.length >= 0 && !requery)
		{
			return m_accounts;
		}
		//
		final ArrayList<MBPBankAccount> list = new ArrayList<>();
		final String sql = "SELECT * FROM C_BP_BankAccount WHERE C_BPartner_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_BPartner_ID());
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MBPBankAccount(getCtx(), rs, get_TrxName()));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
				{
					pstmt.close();
				}
			}
			catch (final Exception e)
			{
			}
			pstmt = null;
		}

		m_accounts = new MBPBankAccount[list.size()];
		list.toArray(m_accounts);
		return m_accounts;
	} // getBankAccounts

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		// NOTE: don't print the stats, because that will involve database access which is quite expensive for a stupid toString method!
		// final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		// final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(partner);

		final StringBuilder sb = new StringBuilder("MBPartner[ID=").append(get_ID())
				.append(",Value=").append(getValue()).append(",Name=").append(getName())
				// .append(",Open=").append(stats.getTotalOpenBalance())
				.append("]");
		return sb.toString();
	} // toString

	/**
	 * Set Client/Org
	 *
	 * @param AD_Client_ID
	 *            client
	 * @param AD_Org_ID
	 *            org
	 */
	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	} // setClientOrg

	// /**
	// * Set Linked Organization. (is Button)
	// *
	// * @param AD_OrgBP_ID
	// */
	// public void setAD_OrgBP_ID(int AD_OrgBP_ID) {
	// if (AD_OrgBP_ID == 0)
	// super.setAD_OrgBP_ID(null);
	// else
	// super.setAD_OrgBP_ID(String.valueOf(AD_OrgBP_ID));
	// } // setAD_OrgBP_ID
	//
	// @Override
	// public void setAD_OrgBP_ID(final String AD_OrgBP_ID) {
	//
	// if (AD_OrgBP_ID == null) {
	// setAD_OrgBP_ID(0);
	// } else {
	// throw new RuntimeException(
	// "DB-Column 'AD_OrgBP_ID' is numeric, not varchar");
	// }
	// }

	/*
	 * This is the original source. It assumes that the DB column has a String
	 * type while in fact it has a numeric type.
	 */
	// public void setAD_OrgBP_ID (int AD_OrgBP_ID)
	// {
	// if (AD_OrgBP_ID == 0)
	// super.setAD_OrgBP_ID (null);
	// else
	// super.setAD_OrgBP_ID (String.valueOf(AD_OrgBP_ID));
	// } // setAD_OrgBP_ID

	/**
	 * Get Linked Organization. (is Button) The Business Partner is another
	 * Organization for explicit Inter-Org transactions
	 *
	 * @return AD_Org_ID if BP
	 */
	public int getAD_OrgBP_ID_Int()
	{
		return super.getAD_OrgBP_ID();
		// String org = super.getAD_OrgBP_ID();
		// if (org == null)
		// return 0;
		// int AD_OrgBP_ID = 0;
		// try {
		// AD_OrgBP_ID = Integer.parseInt(org);
		// } catch (Exception ex) {
		// log.error(org, ex);
		// }
		// return AD_OrgBP_ID;
	} // getAD_OrgBP_ID_Int

	/**
	 * Get Primary C_BPartner_Location_ID
	 *
	 * @return C_BPartner_Location_ID
	 */
	public int getPrimaryC_BPartner_Location_ID()
	{
		if (m_primaryC_BPartner_Location_ID == null)
		{
			final MBPartnerLocation[] locs = getLocations(false);
			for (int i = 0; m_primaryC_BPartner_Location_ID == null
					&& i < locs.length; i++)
			{
				if (locs[i].isBillTo())
				{
					setPrimaryC_BPartner_Location_ID(locs[i]
							.getC_BPartner_Location_ID());
					break;
				}
			}
			// get first
			if (m_primaryC_BPartner_Location_ID == null && locs.length > 0)
			{
				setPrimaryC_BPartner_Location_ID(locs[0]
						.getC_BPartner_Location_ID());
			}
		}
		if (m_primaryC_BPartner_Location_ID == null)
		{
			return 0;
		}
		return m_primaryC_BPartner_Location_ID.intValue();
	} // getPrimaryC_BPartner_Location_ID

	/**
	 * Get Primary C_BPartner_Location
	 *
	 * @return C_BPartner_Location
	 */
	public MBPartnerLocation getPrimaryC_BPartner_Location()
	{
		if (m_primaryC_BPartner_Location_ID == null)
		{
			m_primaryC_BPartner_Location_ID = getPrimaryC_BPartner_Location_ID();
		}
		if (m_primaryC_BPartner_Location_ID == null)
		{
			return null;
		}
		return new MBPartnerLocation(getCtx(), m_primaryC_BPartner_Location_ID,
				null);
	} // getPrimaryC_BPartner_Location

	/**
	 * Set Primary C_BPartner_Location_ID
	 *
	 * @param C_BPartner_Location_ID
	 *            id
	 */
	public void setPrimaryC_BPartner_Location_ID(final int C_BPartner_Location_ID)
	{
		m_primaryC_BPartner_Location_ID = C_BPartner_Location_ID;
	} // setPrimaryC_BPartner_Location_ID

	/**
	 * Credit Status is Stop or Hold.
	 *
	 * @return true if Stop/Hold
	 */
	public boolean isCreditStopHold()
	{
		final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		final BPartnerStats stats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(partner);

		final String status = stats.getSOCreditStatus();

		return X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(status)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(status);
	} // isCreditStopHold

	private I_C_BP_Group getBPGroup()
	{
		if (m_group != null)
		{
			return m_group;
		}

		final IBPGroupDAO bpGroupsRepo = Services.get(IBPGroupDAO.class);

		final BPGroupId bpGroupId = BPGroupId.ofRepoIdOrNull(getC_BP_Group_ID());
		if (bpGroupId == null)
		{
			final ClientId clientId = ClientId.ofRepoId(getAD_Client_ID());
			m_group = bpGroupsRepo.getDefaultByClientId(clientId);
		}
		else
		{
			m_group = bpGroupsRepo.getByIdInInheritedTrx(bpGroupId);
		}
		return m_group;
	} // getBPGroup

	/**
	 * Get BP Group
	 *
	 * @param group
	 *            group
	 */
	private void setBPGroup(final I_C_BP_Group group)
	{
		m_group = group;
		if (m_group == null)
		{
			return;
		}
		setC_BP_Group_ID(m_group.getC_BP_Group_ID());
		if (m_group.getC_Dunning_ID() != 0)
		{
			setC_Dunning_ID(m_group.getC_Dunning_ID());
		}
		if (m_group.getM_PriceList_ID() != 0)
		{
			setM_PriceList_ID(m_group.getM_PriceList_ID());
		}
		if (m_group.getPO_PriceList_ID() != 0)
		{
			setPO_PriceList_ID(m_group.getPO_PriceList_ID());
		}
		if (m_group.getM_DiscountSchema_ID() != 0)
		{
			setM_DiscountSchema_ID(m_group.getM_DiscountSchema_ID());
		}
		if (m_group.getPO_DiscountSchema_ID() != 0)
		{
			setPO_DiscountSchema_ID(m_group.getPO_DiscountSchema_ID());
		}
	} // setBPGroup

	/**
	 * Get PriceList
	 *
	 * @return price List
	 */
	@Override
	public int getM_PriceList_ID()
	{
		int ii = super.getM_PriceList_ID();
		if (ii == 0)
		{
			ii = getBPGroup().getM_PriceList_ID();
		}
		return ii;
	} // getM_PriceList_ID

	/**
	 * Get PO PriceList
	 *
	 * @return price list
	 */
	@Override
	public int getPO_PriceList_ID()
	{
		int ii = super.getPO_PriceList_ID();
		if (ii == 0)
		{
			ii = getBPGroup().getPO_PriceList_ID();
		}
		return ii;
	} //

	/**
	 * Get DiscountSchema
	 *
	 * @return Discount Schema
	 */
	@Override
	public int getM_DiscountSchema_ID()
	{
		int ii = super.getM_DiscountSchema_ID();
		if (ii == 0)
		{
			ii = getBPGroup().getM_DiscountSchema_ID();
		}
		return ii;
	} // getM_DiscountSchema_ID

	/**
	 * Get PO DiscountSchema
	 *
	 * @return po discount
	 */
	@Override
	public int getPO_DiscountSchema_ID()
	{
		int ii = super.getPO_DiscountSchema_ID();
		if (ii == 0)
		{
			ii = getBPGroup().getPO_DiscountSchema_ID();
		}
		return ii;
	} // getPO_DiscountSchema_ID

	// metas
	public static int getDefaultContactId(final int cBPartnerId)
	{

		for (final I_AD_User user : Services.get(IBPartnerDAO.class).retrieveContacts(Env.getCtx(), cBPartnerId, ITrx.TRXNAME_None))
		{
			if (user.isDefaultContact())
			{
				return user.getAD_User_ID();
			}
		}
		LogManager.getLogger(MBPartner.class).warn("Every BPartner with associated contacts is expected to have exactly one default contact, but C_BPartner_ID {} doesn't have one.", cBPartnerId);
		return -1;
	}

	// end metas

	/**
	 * Before Save
	 *
	 * @param newRecord
	 *            new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (newRecord || is_ValueChanged("C_BP_Group_ID"))
		{
			final I_C_BP_Group grp = getBPGroup();
			if (grp == null)
			{
				throw new AdempiereException("@NotFound@:  @C_BP_Group_ID@");
			}
			setBPGroup(grp); // setDefaults
		}
		return true;
	} // beforeSave

	/**************************************************************************
	 * After Save
	 *
	 * @param newRecord
	 *            new
	 * @param success
	 *            success
	 * @return success
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (newRecord && success)
		{
			// Trees
			insert_Tree(MTree_Base.TREETYPE_BPartner);
			// Accounting
			insert_Accounting("C_BP_Customer_Acct", "C_BP_Group_Acct",
					"p.C_BP_Group_ID=" + getC_BP_Group_ID());
			insert_Accounting("C_BP_Vendor_Acct", "C_BP_Group_Acct",
					"p.C_BP_Group_ID=" + getC_BP_Group_ID());
			insert_Accounting("C_BP_Employee_Acct", "C_AcctSchema_Default",
					null);
		}

		// Value/Name change
		if (success && !newRecord
				&& (is_ValueChanged("Value") || is_ValueChanged("Name")))
		{
			MAccount.updateValueDescription(getCtx(), "C_BPartner_ID="
					+ getC_BPartner_ID(), get_TrxName());
		}

		return success;
	} // afterSave

	/**
	 * Before Delete
	 *
	 * @return true
	 */
	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting("C_BP_Customer_Acct")
				&& delete_Accounting("C_BP_Vendor_Acct")
				&& delete_Accounting("C_BP_Employee_Acct");
	} // beforeDelete

	/**
	 * After Delete
	 *
	 * @param success
	 * @return deleted
	 */
	@Override
	protected boolean afterDelete(final boolean success)
	{
		if (success)
		{
			delete_Tree(MTree_Base.TREETYPE_BPartner);
		}
		return success;
	} // afterDelete

} // MBPartner
