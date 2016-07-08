package org.adempiere.serverRoot.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 *  Web User Info.
 *	Assumes that Email is a direct match.
 *  UPDATE AD_User SET EMail=TRIM(EMail) WHERE Email<>TRIM(EMail)
 *  @author Jorg Janke
 *  @version $Id$
 */
public class WebUser
{
	/**
	 * 	Get from request
	 *	@param request request
	 *	@return web user if exists
	 */
	public static WebUser get (HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return (WebUser)session.getAttribute(WebUser.NAME);
	}	//	get
	
	
	/**
	 * 	Get user unconditional from cache
	 * 	@param ctx context
	 *	@param email email
	 * 	@return web user
	 */
	public static WebUser get (Properties ctx, String email)
	{
		return get (ctx, email, null, true);
	}	//	get

	/**
	 * 	Get user
	 * 	@param ctx context
	 *	@param email email
	 * 	@param password optional password
	 * 	@param useCache use cache
	 * 	@return web user
	 */
	public static WebUser get (Properties ctx, String email, String password, boolean useCache)
	{
		if (!useCache)
			s_cache = null;
		if (s_cache != null && email != null && email.equals(s_cache.getEmail()))
		{
			//	if password is null, don't check it
			if (password == null || password.equals(s_cache.getPassword()))
				return s_cache;
			s_cache.setPasswordOK(false, null);
			return s_cache;
		}
		s_cache = new WebUser (ctx, email, password);
		return s_cache;
	}	//	get

	/**
	 * 	Get user unconditional (from cache)
	 * 	@param ctx context
	 *	@param AD_User_ID BP Contact
	 * 	@return web user
	 */
	public static WebUser get (Properties ctx, int AD_User_ID)
	{
		if (s_cache != null && s_cache.getAD_User_ID() == AD_User_ID)
			return s_cache;
		s_cache = new WebUser (ctx, AD_User_ID, null);
		return s_cache;
	}	//	get

	/** Short term Cache for immediate re-query/post (hit rate 20%)	*/
	private static WebUser	s_cache = null;

	/*************************************************************************/

	/**	Attribute Name - also in JSPs		*/
	public static final String		NAME = "webUser";
	/**	Logging						*/
	private Logger					log = LogManager.getLogger(getClass());

	/**
	 *	Load User with password
	 *	@param ctx context
	 *	@param email email
	 *	@param password password
	 */
	private WebUser (Properties ctx, String email, String password)
	{
		m_ctx = ctx;
		m_AD_Client_ID = Env.getAD_Client_ID(ctx);
		load (email, password);
	}	//	WebUser

	/**
	 *	Load User with password
	 *	@param ctx context
	 *	@param AD_User_ID BP Contact
	 * 	@param trxName transaction
	 */
	private WebUser (Properties ctx, int AD_User_ID, String trxName)
	{
		m_ctx = ctx;
		m_AD_Client_ID = Env.getAD_Client_ID(ctx);
		load (AD_User_ID);
	}	//	WebUser

	private Properties			m_ctx;
	//
	private MBPartner		 	m_bp;
	private MUser			 	m_bpc;
	private MBPartnerLocation 	m_bpl;
	private MLocation 			m_loc;
	//
	private boolean				m_passwordOK = false;
	private String				m_passwordMessage;
	private String				m_saveErrorMessage;
	//
	private int 				m_AD_Client_ID = 0;
	private boolean				m_loggedIn = false;


	/**
	 * 	Load Contact
	 * 	@param email email
	 *	@param password optional password
	 */
	private void load (String email, String password)
	{
		log.info(email + " - AD_Client_ID=" + m_AD_Client_ID);
		String sql = "SELECT * "
			+ "FROM AD_User "
			+ "WHERE AD_Client_ID=?"
			+ " AND TRIM(EMail)=?";
		if (email == null)
			email = "";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Client_ID);
			pstmt.setString(2, email.trim());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_bpc = new MUser (m_ctx, rs, null);
				log.debug("Found BPC=" + m_bpc);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Check Password
		m_passwordOK = false;
		/**	User has no password
		if (m_bpc != null && m_bpc.getPassword() == null)
		{
			if (password != null)
				m_bpc.setPassword(password);
			m_passwordOK = true;
		}	**/
		//	We have a password
		if (m_bpc != null && password != null && password.equals(m_bpc.getPassword()))
			m_passwordOK = true;
		if (m_passwordOK || m_bpc == null)
			m_passwordMessage = null;
		else
			setPasswordOK (false, password);

		//	Load BPartner
		if (m_bpc != null)
		{
			m_bp = new MBPartner (m_ctx, m_bpc.getC_BPartner_ID (), null);
			log.debug("Found BP=" + m_bp);
		}
		else
			m_bp = null;
		//	Load Loacation
		if (m_bpc != null)
		{
			if (m_bpc.getC_BPartner_Location_ID() != 0)
			{
				m_bpl = new MBPartnerLocation (m_ctx, m_bpc.getC_BPartner_Location_ID (), null);
				log.debug("Found BPL=" + m_bpl);
			}
			else
			{
				MBPartnerLocation[] bpls = m_bp.getLocations(false);
				if (bpls != null && bpls.length > 0)
				{
					m_bpl = bpls[0];
					log.debug("Found BPL=" + m_bpl);
				}
			}
			if (m_bpl != null)
			{
				m_loc = MLocation.get (m_ctx, m_bpl.getC_Location_ID(), null);
				log.debug("Found LOC=" + m_loc);
			}
			else
				m_loc = null;
		}
		else
		{
			m_bpl = null;
			m_loc = null;
		}

		//	Make sure that all entities exist
		if (m_bpc == null)
		{
			m_bpc = new MUser (m_ctx, 0, null);
			m_bpc.setEMail(email);
			m_bpc.setPassword(password);
		}
		if (m_bp == null)
		{
			m_bp = new MBPartner (m_ctx); //	template
			m_bp.setIsCustomer(true);
		}
		if (m_bpl == null)
			m_bpl = new MBPartnerLocation (m_bp);
		if (m_loc == null)
			m_loc = new MLocation (m_ctx, 0, null);
		//
		log.info(m_bp + " - " + m_bpc);
	}	//	load

	/**
	 * 	Load Contact
	 * 	@param AD_User_ID BP Contact
	 */
	private void load (int AD_User_ID)
	{
		log.info("ID=" + AD_User_ID + ", AD_Client_ID=" + m_AD_Client_ID);
		String sql = "SELECT * "
			+ "FROM AD_User "
			+ "WHERE AD_Client_ID=?"
			+ " AND AD_User_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Client_ID);
			pstmt.setInt(2, AD_User_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_bpc = new MUser (m_ctx, rs, null);
				log.debug("= found BPC=" + m_bpc);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Password not entered
		m_passwordOK = false;
		m_loggedIn = false;

		//	Load BPartner
		if (m_bpc != null)
		{
			m_bp = new MBPartner (m_ctx, m_bpc.getC_BPartner_ID (), null);
			log.debug("= Found BP=" + m_bp);
		}
		else
			m_bp = null;
		//	Load Loacation
		if (m_bpc != null)
		{
			if (m_bpc.getC_BPartner_Location_ID() != 0)
			{
				m_bpl = new MBPartnerLocation (m_ctx, m_bpc.getC_BPartner_Location_ID (), null);
				log.debug("= Found BPL=" + m_bpl);
			}
			else
			{
				MBPartnerLocation[] bpls = m_bp.getLocations(false);
				if (bpls != null && bpls.length > 0)
				{
					m_bpl = bpls[0];
					log.debug("= Found BPL=" + m_bpl);
				}
			}
			if (m_bpl != null)
			{
				m_loc = MLocation.get (m_ctx, m_bpl.getC_Location_ID(), null);
				log.debug("= Found LOC=" + m_loc);
			}
			else
				m_loc = null;
		}
		else
		{
			m_bpl = null;
			m_loc = null;
		}

		//	Make sure that all entities exist
		if (m_bpc == null)
		{
			m_bpc = new MUser (m_ctx, 0, null);
			m_bpc.setEMail("?");
			m_bpc.setPassword("?");
		}
		if (m_bp == null)
		{
			m_bp = new MBPartner (m_ctx); //	template
			m_bp.setIsCustomer(true);
		}
		if (m_bpl == null)
			m_bpl = new MBPartnerLocation (m_bp);
		if (m_loc == null)
			m_loc = new MLocation (m_ctx, 0, null);
		//
		log.info("= " + m_bp + " - " + m_bpc);
	}	//	load

	/**
	 * 	Return Valid.
	 * 	@return return true if found
	 */
	public boolean isValid()
	{
		if (m_bpc == null)
			return false;
		boolean ok = m_bpc.getAD_User_ID() != 0;
		return ok;
	}	//	isValid

	/**
	 * 	Return Email Validation.
	 * 	@return return true if email is valid
	 */
	public boolean isEMailValid()
	{
		if (m_bpc == null || !WebUtil.exists(getEmail()))
		{
			log.debug(getEmail() + ", bpc=" + m_bpc);
			return false;
		}
		//
		boolean ok = m_bpc.getAD_User_ID() != 0
			&& m_bpc.isEMailValid();
		if (!ok)
			log.debug(getEmail()
				+ ", ID=" + m_bpc.getAD_User_ID()
				+ ", Online=" + m_bpc.isOnline()
				+ ", EMailValid=" + m_bpc.isEMailValid());
		return ok;
	}	//	isEMailValid

	/**
	 * 	Return Email Verification (reply).
	 * 	@return return true if reply received
	 */
	public boolean isEMailVerified()
	{
		return m_bpc != null
			&& m_bpc.isEMailVerified();
	}	//	isEMailVerified
	
	
	/**
	 * 	Info
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer("WebUser[");
		sb.append(getEmail())
			.append(",LoggedIn=").append(m_loggedIn)
			.append(",").append(m_bpc)
			.append(",PasswordOK=").append(m_passwordOK)
			.append(",Valid=").append(isValid())
			.append(" - ").append(m_bp).append("Customer=").append(isCustomer())
			.append("]");
		return sb.toString();
	}	//	toString

	
	/**************************************************************************
	 * 	Save BPartner Objects
	 * 	@return true if saved
	 */
	public boolean save()
	{
		m_saveErrorMessage = null;
		log.info("BP.Value=" + m_bp.getValue() + ", Name=" + m_bp.getName());
		try
		{
			//	check if BPartner exists	***********************************
			if (m_bp.getC_BPartner_ID() == 0)
			{
				String sql = "SELECT * FROM C_BPartner WHERE AD_Client_ID=? AND Value=?";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt (1, m_AD_Client_ID);
					pstmt.setString (2, m_bp.getValue());
					rs = pstmt.executeQuery();
					if (rs.next())
					{
						m_bp = new MBPartner (m_ctx, m_bpc.getC_BPartner_ID (), null);
						log.debug("BP loaded =" + m_bp);
					}
  			}
				catch (Exception e)
				{
					log.error("save-check", e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
			}

			//	save BPartner			***************************************
			if (m_bp.getName () == null || m_bp.getName().length() == 0)
				m_bp.setName (m_bpc.getName());
			if (m_bp.getValue() == null || m_bp.getValue().length() == 0)
				m_bp.setValue(m_bpc.getEMail());
			log.debug("BP=" + m_bp);
			if (!m_bp.save ())
			{
				m_saveErrorMessage = "Could not save Business Partner";
				return false;
			}

			//	save Location			***************************************
			log.debug("LOC=" + m_loc);
			m_loc.save ();

			//	save BP Location		***************************************
			if (m_bpl.getC_BPartner_ID () != m_bp.getC_BPartner_ID())
				m_bpl.setC_BPartner_ID (m_bp.getC_BPartner_ID());
			if (m_bpl.getC_Location_ID () != m_loc.getC_Location_ID())
				m_bpl.setC_Location_ID (m_loc.getC_Location_ID());
			log.debug("BPL=" + m_bpl);
			if (!m_bpl.save ())
			{
				m_saveErrorMessage = "Could not save Location";
				return false;
			}

			//	save Contact			***************************************
			if (m_bpc.getC_BPartner_ID () != m_bp.getC_BPartner_ID())
				m_bpc.setC_BPartner_ID (m_bp.getC_BPartner_ID());
			if (m_bpc.getC_BPartner_Location_ID () != m_bpl.getC_BPartner_Location_ID ())
				m_bpc.setC_BPartner_Location_ID (m_bpl.getC_BPartner_Location_ID ());
			log.debug("BPC=" + m_bpc);
			if (!m_bpc.save ())
			{
				m_saveErrorMessage = "Could not save Contact";
				return false;
			}
		}
		catch (Exception ex)
		{
			log.error("save", ex);
			m_saveErrorMessage = ex.toString();
			return false;
		}
		//
		return true;
	}	//	save

	/**
	 * 	Set Save Error Message
	 *	@param msg message
	 */
	public void setSaveErrorMessage(String msg)
	{
		m_saveErrorMessage = msg;
	}
	/**
	 * 	Get Save Error Message
	 *	@return message
	 */
	public String getSaveErrorMessage()
	{
		return m_saveErrorMessage;
	}

	/**************************************************************************
	 * 	Get EMail address.
	 * 	used as jsp parameter
	 * 	@return email address of contact
	 */
	public String getEmail ()
	{
		return m_bpc.getEMail();
	}

	public void setEmail (String email)
	{
		m_bpc.setEMail(email);
	}

	public String getName ()
	{
		return m_bpc.getName();
	}

	public void setName (String name)
	{
		m_bpc.setName(name);
	}

	public void setValue (String value)
	{
		m_bp.setValue (value);
	}

	public String getTitle ()
	{
		return m_bpc.getTitle();
	}

	public void setTitle (String title)
	{
		m_bpc.setTitle(title);
	}

	/**
	 * 	Get Password
	 * 	@return password
	 */
	public String getPassword ()
	{
		String pwd = m_bpc.getPassword();
		if (pwd == null || pwd.length() == 0)	//	if no password use time
			pwd = String.valueOf(System.currentTimeMillis());
		return pwd;
	}	//	getPassword

	/**
	 * 	Check & Save Password
	 */
	public void setPassword ()
	{
		String pwd = m_bpc.getPassword();
		if ((pwd == null || pwd.length() == 0)	//	no password set
			&& m_bpc.getC_BPartner_ID() != 0 && m_bpc.getAD_User_ID() != 0 )	//	existing BPartner
		{
			pwd = String.valueOf (System.currentTimeMillis ());
			m_bpc.setPassword (pwd);
			m_bpc.save();
		}
	}	//	setPassword

	/**
	 * 	Set Password
	 * 	@param password new password
	 */
	public void setPassword (String password)
	{
		if (password == null || password.length() == 0)
			m_passwordMessage = "Enter Password";
		m_bpc.setPassword (password);
	}	//	setPassword

	/**
	 * 	Set Password OK
	 * 	@param ok password valid
	 * 	@param password password
	 */
	private void setPasswordOK (boolean ok, String password)
	{
		m_passwordOK = ok;
		if (ok)
			m_passwordMessage = null;
		else if (password == null || password.length() == 0)
			m_passwordMessage = "Enter Password";
		else
			m_passwordMessage = "Invalid Password";
	}	//	setPasswordOK

	/**
	 * 	Is Password OK
	 * 	@return true if OK
	 */
	public boolean isPasswordOK()
	{
		if (m_bpc == null || !WebUtil.exists(m_bpc.getPassword()))
			return false;
		return m_passwordOK;
	}	//	isPasswordOK

	/**
	 * 	Set Password Message
	 * 	@return error message or null
	 */
	public String getPasswordMessage ()
	{
		return m_passwordMessage;
	}	//	getPasswordMessage

	/**
	 *	Set Password Message
	 * 	@param passwordMessage message
	 */
	public void setPasswordMessage (String passwordMessage)
	{
		m_passwordMessage = passwordMessage;
	}	//	setPasswordMessage

	/**
	 * 	Log in with password
	 * 	@param password password
	 *	@return true if the user is logged in
	 */
	public boolean login (String password)
	{
		m_loggedIn = isValid () 			//	we have a contact
			 && WebUtil.exists (password) 	//	we have a password
			 && password.equals (getPassword ());
		setPasswordOK (m_loggedIn, password);
		log.debug("success=" + m_loggedIn);
		if (m_loggedIn)
			Env.setContext(m_ctx, "#AD_User_ID", getAD_User_ID());
		return m_loggedIn;
	}	//	isLoggedIn

	/**
	 * 	Log in with oassword
	 */
	public void logout ()
	{
		m_loggedIn = false;
	}	//	isLoggedIn

	/**
	 * 	Is User Logged in
	 *	@return is the user logged in
	 */
	public boolean isLoggedIn ()
	{
		return m_loggedIn;
	}	//	isLoggedIn

	public String getPhone ()
	{
		return m_bpc.getPhone();
	}

	public void setPhone (String phone)
	{
		m_bpc.setPhone(phone);
	}

	public String getPhone2 ()
	{
		return m_bpc.getPhone2();
	}

	public void setPhone2 (String phone2)
	{
		m_bpc.setPhone2(phone2);
	}

	public String getFax ()
	{
		return m_bpc.getFax();
	}

	public void setFax (String fax)
	{
		m_bpc.setFax(fax);
	}

	public Timestamp getBirthday ()
	{
		return m_bpc.getBirthday();
	}

	public void setBirthday (Timestamp birthday)
	{
		m_bpc.setBirthday(birthday);
	}

	public String getTaxID ()
	{
		return m_bp.getVATaxID();
	}

	public void setTaxID (String taxID)
	{
		m_bp.setVATaxID(taxID);
	}

	public int getAD_Client_ID ()
	{
		return m_bpc.getAD_Client_ID();
	}

	public int getAD_User_ID ()
	{
		return m_bpc.getAD_User_ID();
	}

	public int getContactID ()
	{
		return getAD_User_ID();
	}

	/*************************************************************************/

	/**
	 * 	Get Company Name
	 *	@return company name
	 */
	public String getCompany()
	{
		return m_bp.getName();
	}

	public void setCompany(String company)
	{
		if (company==null) {
			m_bp.setName (m_bpc.getName ());
		} else {
			m_bp.setName(company);
		}
	}

	public int getC_BPartner_ID ()
	{
		return m_bp.getC_BPartner_ID();
	}
	
	public int getBpartnerID ()
	{
		return m_bp.getC_BPartner_ID();
	}

	public int getM_PriceList_ID ()
	{
		return m_bp.getM_PriceList_ID();
	}


	/*************************************************************************/

	/**
	 * 	Get BP Location ID
	 *	@return BP Loaction
	 */
	public int getC_BPartner_Location_ID ()
	{
		return m_bpl.getC_BPartner_Location_ID();
	}

	/**************************************************************************
	 * 	Get Location
	 *	@return location address
	 */
	public String getAddress ()
	{
		return m_loc.getAddress1();
	}

	public void setAddress (String address)
	{
		m_loc.setAddress1(address);
	}

	public String getAddress2 ()
	{
		return m_loc.getAddress2();
	}

	public void setAddress2 (String address2)
	{
		m_loc.setAddress2(address2);
	}

	public String getCity ()
	{
		return m_loc.getCity();
	}

	public void setCity (String city)
	{
		m_loc.setCity(city);
	}

	public String getPostal ()
	{
		return m_loc.getPostal();
	}

	public void setPostal (String postal)
	{
		m_loc.setPostal(postal);
	}

	/**************************************************************************
	 * 	Get (additional) Region name
	 *	@return location region name
	 */
	public String getRegionName ()
	{
		return m_loc.getRegionName(false);
	}

	public void setRegionName (String region)
	{
		m_loc.setRegionName(region);
	}

	public int getC_Region_ID ()
	{
		return m_loc.getC_Region_ID();
	}

	public String getRegionID ()
	{
		return String.valueOf(getC_Region_ID());
	}

	public void setC_Region_ID (int C_Region_ID)
	{
		m_loc.setC_Region_ID(C_Region_ID);
	}

	public void setC_Region_ID (String C_Region_ID)
	{
		try
		{
			if (C_Region_ID == null || C_Region_ID.length() == 0)
				setC_Region_ID(0);
			else
				setC_Region_ID(Integer.parseInt(C_Region_ID));
		}
		catch (Exception e)
		{
			setC_Region_ID(0);
			log.warn(C_Region_ID, e);
		}
	}

	public String getCountryName ()
	{
		return m_loc.getCountryName();
	}

	public void setCountryName (String country)
	{
		log.warn(country + " Ignored - C_Country_ID=" + m_loc.getC_Country_ID());
		//	m_loc.setCountryName(country);
	}

	public int getC_Country_ID ()
	{
		return m_loc.getC_Country_ID();
	}

	public String getCountryID ()
	{
		return String.valueOf(getC_Country_ID());
	}

	public void setC_Country_ID (int C_Country_ID)
	{
		m_loc.setC_Country_ID(C_Country_ID);
	}

	public void setC_Country_ID (String C_Country_ID)
	{
		try
		{
			if (C_Country_ID == null || C_Country_ID.length() == 0)
				setC_Country_ID(0);
			else
				setC_Country_ID(Integer.parseInt(C_Country_ID));
		}
		catch (Exception e)
		{
			setC_Country_ID(0);
			log.warn(C_Country_ID, e);
		}
	}

	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		m_bp.setC_BP_Group_ID (C_BP_Group_ID);
	}

	public void setC_BP_Group_ID (String C_BP_Group_ID)
	{
		try
		{
			if (C_BP_Group_ID == null || C_BP_Group_ID.length() == 0)
				setC_BP_Group_ID(0);
			else
				setC_BP_Group_ID(Integer.parseInt(C_BP_Group_ID));
		}
		catch (Exception e)
		{
			setC_BP_Group_ID(0);
			log.warn(C_BP_Group_ID, e);
		}
	}

	public boolean isEmployee()
	{
		return m_bp.isEmployee();
	}

	public boolean isSalesRep()
	{
		return m_bp.isSalesRep();
	}

	public boolean isCustomer()
	{
		return m_bp.isCustomer();
	}

	public void setIsCustomer(boolean isCustomer)
	{
		m_bp.setIsCustomer(isCustomer);
	}

	public boolean isVendor()
	{
		return m_bp.isVendor();
	}

	public int getSalesRep_ID()
	{
		return m_bp.getSalesRep_ID();
	}

	public boolean hasBPAccess (String BPAccessType, Object[] params)
	{
		return m_bpc.hasBPAccess (BPAccessType, params);
	}
	
	/**
	 * 	Credit Status is Stop or Hold.
	 * 	Used in Asset download
	 *	@return true if Stop/Hold
	 */
	public boolean isCreditStopHold()
	{
		return m_bp.isCreditStopHold();
	}	//	isCreditStopHold

	/**************************************************************************
	 * Get BP Bank Account (or create it)
	 * 
	 * @return Bank Account
	 */
	public MBPBankAccount getBankAccount()
	{
		return getBankAccount(false);
	}
	
	/**************************************************************************
	 * 	Get BP Bank Account (or create it)
	 *  @param requery Requery data
	 *	@return Bank Account
	 */
	public MBPBankAccount getBankAccount(boolean requery)
	{
		MBPBankAccount retValue = null;
		//	Find Bank Account for exact User
		MBPBankAccount[] bas = m_bp.getBankAccounts(requery);
		for (int i = 0; i < bas.length; i++)
		{
			if (bas[i].getAD_User_ID() == getAD_User_ID() && bas[i].isActive())
				retValue = bas[i];
		}

		//	create new
		if (retValue == null)
		{
			retValue = new MBPBankAccount (m_ctx, m_bp, m_bpc, m_loc);
			retValue.setAD_User_ID(getAD_User_ID());
			retValue.save();
		}
		
		return retValue;
	}	//	getBankAccount

	/**
	 * 	Get EMail Verification Code
	 *	@return verification code
	 */
	public String getEMailVerifyCode()
	{
		return m_bpc.getEMailVerifyCode();
	}

	/**
	 * 	Check & Set EMail Validation Code.
	 *	@param code code
	 *	@param info info
	 */
	public void setEMailVerifyCode(String code, String info)
	{
		if (m_bpc.setEMailVerifyCode(code, info))
			setPasswordMessage(null);
		else
			setPasswordMessage("Invalid Code");
		m_bpc.save();
	}	//	setEMailVerifyCode
	
}	//	WebUser
