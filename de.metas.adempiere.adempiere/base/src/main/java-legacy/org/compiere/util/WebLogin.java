/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.
 * This program is free software; you can redistribute it and/or modify it
 * under the terms version 2 of the GNU General Public License as published
 * by the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 * You may reach us at: ComPiere, Inc. - http://www.adempiere.org/license.html
 * 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA or info@adempiere.org 
 *****************************************************************************/
package org.compiere.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.compiere.model.MBPBankAccount;
import org.compiere.model.MMailMsg;
import org.compiere.model.MSession;


/**
 *	WebLogin provides a standard interface to login 
 *	from Webapps like WStore or CM
 *	
 *  @author Yves Sandfort
 *  @version $Id$
 */
public class WebLogin
{
	/**	Logging								*/
	private CLogger				log = CLogger.getCLogger(getClass());
	private final static String		COOKIE_NAME = "CompiereWebUser";
	
	/** Forward Parameter					*/
	private String		P_ForwardTo = "ForwardTo";
	/** SalesRep Parameter					*/
	private String		P_SalesRep_ID = "SalesRep_ID";
	/** EMail Parameter					*/
	private String		P_EMail = "EMail";
	/** Password Parameter					*/
	private String		P_Password = "Password";
	/** Mode/Action Parameter				*/
	private String		P_Action = "Mode";
	
	/** Login Page							*/
	private String		m_login_page = "/login.jsp";
    /** Update Page                         */
    private String		m_update_page = "/update.jsp";
    
    /** Message								*/
    private String      		m_message = null;
	/** Context								*/
	private Properties 			m_ctx;
	/** HttpServletRequest					*/
	private HttpServletRequest	m_request;
	/** HttpServletResponse					*/
	private HttpServletResponse	m_response;
	/** HttpSession						*/
	private HttpSession			m_session;
	/** adressConfirm						*/
	private boolean 			m_addressConfirm;
	/** forward								*/
	private String 				m_forward;
	/** SalesRep							*/
	private String 				m_salesRep;
	/** EMail								*/
	private String 				m_email;
	/** Password							*/
	private String 				m_password;
	/** WebUser								*/
	private WebUser 			m_wu;
	/** Mode					*/
	private String 				m_mode;
	
	/**
	 * 	WebLogin
	 *	@param t_request
	 *	@param t_response
	 *	@param t_ctx
	 */
	public WebLogin (HttpServletRequest t_request, HttpServletResponse t_response, Properties t_ctx)
	{
		m_request = t_request;
		m_response = t_response;
		m_ctx = t_ctx;
		// We will check the Request to see whether Parameters are overwritten
		if (m_request.getParameter ("P_ForwardTo")!=null) 
			setP_ForwardTo (m_request.getParameter("P_ForwardTo"));
		if (m_request.getParameter ("SalesRep_ID")!=null)
			setP_SalesRep_ID (m_request.getParameter ("SalesRep_ID"));
		if (m_request.getParameter ("P_EMail")!=null)
			setP_EMail (m_request.getParameter ("P_EMail"));
		if (m_request.getParameter ("P_Password")!=null)
			setP_Password (m_request.getParameter ("P_Password"));
		if (m_request.getParameter ("P_Action")!=null)
			setP_Action (m_request.getParameter ("P_Action"));
		if (m_request.getParameter ("LOGIN_RelURL")!=null)
			setLogin_RelURL (m_request.getParameter ("LOGIN_RelURL"));
		if (m_request.getParameter ("update_page")!=null)
			setLogin_RelURL (m_request.getParameter ("update_page"));
	}
	
	/**
	 * 	init will initialize the WebLogin Object for further use
	 *	@return true if init was successfull
	 */
	public boolean init()
	{
		m_session = m_request.getSession(true);	//	create new
		m_forward = WebUtil.getParameter (m_request, P_ForwardTo);			//	get forward from request
		if (m_forward != null)
			m_session.setAttribute(P_ForwardTo, m_forward);
		else
			m_forward = "";
		m_salesRep = WebUtil.getParameter (m_request, P_SalesRep_ID);		//	get SalesRep from request
		if (m_salesRep != null)
			m_session.setAttribute(P_SalesRep_ID, m_salesRep);

		//	Get Base Info
		m_email = WebUtil.getParameter (m_request, P_EMail);
		if (m_email == null)
			m_email = "";
		m_email = m_email.trim();
		if (m_email != null)
			m_session.setAttribute (P_EMail, m_email);
		m_password = WebUtil.getParameter (m_request, P_Password);
		if (m_password == null)
			m_password = "";	//	null loads w/o check
		m_password = m_password.trim();
		if (m_session.getAttribute (WebInfo.NAME)!=null) 
		{
			WebInfo wi = (WebInfo)m_session.getAttribute(WebInfo.NAME);
			m_wu = wi.getWebUser ();
		}
		return true;
	}
	
	/**
	 * 	Action run functions against the Login process.
	 *	@return true if successfull
	 *	@throws IOException
	 *	@throws ServletException
	 */
	public boolean action() throws IOException, ServletException
	{
		//	Mode
		if (getMode() == null) 
		{
			String s = WebUtil.getParameter (m_request, P_Action);
			setMode(s);
		}
		boolean deleteCookie = "deleteCookie".equals(m_mode);
		if (deleteCookie)
		{
			log.fine("** deleteCookie");
			WebUtil.deleteCookieWebUser (m_request, m_response, COOKIE_NAME);
		}
		//
		boolean logout = "logout".equals(m_mode);
		if (logout || deleteCookie)
		{
			log.fine("** logout");
			if (m_session != null)
			{
				MSession cSession = MSession.get (m_ctx, false);
				if (cSession != null)
					cSession.logout();
				//
				m_wu = (WebUser)m_session.getAttribute(WebUser.NAME);
				if (m_wu != null)
					m_wu.logout();
	
	            m_session.removeAttribute(WebUser.NAME);
	            m_session.setMaxInactiveInterval(1);
				m_session.invalidate ();
			}
			//	Forward to unsecure /
			WebUtil.createForwardPage(m_response, "Logout", "http://" + m_request.getServerName() + "/", 2);
		}
		//	Send EMail				***	Send Password EMail Request
		else if ("SendEMail".equals(m_mode))
		{
			log.info("** send mail");
			m_wu = WebUser.get (m_ctx, m_email);			//	find it
			if (!m_wu.isEMailValid())
				m_wu.setPasswordMessage("EMail not found in system");
			else
			{
				m_wu.setPassword();		//	set password to current
				//
				String msg = WebUtil.sendEMail (m_request, m_wu,
					MMailMsg.MAILMSGTYPE_UserPassword, new Object[]{
						m_request.getServerName(),
						m_wu.getName(),
						WebUtil.getFrom(m_request),
						m_wu.getPassword()});
				if (EMail.SENT_OK.equals(msg))
					m_wu.setPasswordMessage ("EMail sent");
				else
					m_wu.setPasswordMessage ("Problem sending EMail: " + msg);
			}
			m_forward = getLogin_RelURL ();
		}	//	SendEMail
		//	Login
		else if ("Login".equals(m_mode))
		{
			log.info("** login " + m_email + "/" + m_password);
			//	add Cookie
			WebUtil.addCookieWebUser(m_request, m_response, m_email, COOKIE_NAME);

			//	Always re-query
			m_wu = WebUser.get (m_ctx, m_email, m_password, false);
			m_wu.login(m_password);
			//	Password valid
			if (m_wu.isLoggedIn())
			{
				if (m_forward==null || m_forward.equals(getLogin_RelURL ()))
					m_forward = "/index.jsp";
				//	Create Session with User ID
				MSession cSession = MSession.get (m_ctx, m_request.getRemoteAddr(), 
					m_request.getRemoteHost(), m_session.getId());
				if (cSession != null)
					cSession.setWebStoreSession(true);
			}
			else
			{
				m_forward = getLogin_RelURL ();
				log.fine("- PasswordMessage=" + m_wu.getPasswordMessage());
			}
			// If no session exists or is not loaded, load or create it
			if (m_session==null) 
				m_session = m_request.getSession (true);
			
			m_session.setAttribute (WebInfo.NAME, new WebInfo (m_ctx, m_wu));
		}	//	Login

		//	Login New
		else if ("LoginNew".equals(m_mode))
		{
			log.info("** loginNew");
			WebUtil.addCookieWebUser(m_request, m_response, "", COOKIE_NAME);
			m_wu =  WebUser.get (m_ctx, "");
			m_forward = getLogin_RelURL ();
		}

		//	Submit - update/new Contact
		else if ("Submit".equals(m_mode))
		{
			log.info("** submit " + m_email + "/" + m_password + " - AddrConf=" + m_addressConfirm);
			//	we have a record for address update
			if (m_wu != null && m_wu.isLoggedIn() && m_addressConfirm)	//	address update
				;
			else	//	Submit - always re-load user record
				m_wu = WebUser.get (m_ctx, m_email, null, false); //	load w/o password check direct
			//
			if (m_wu.getAD_User_ID() != 0)		//	existing BPC
			{
				String passwordNew = WebUtil.getParameter (m_request, "PasswordNew");
				if (passwordNew == null)
					passwordNew = "";
				boolean passwordChange = passwordNew.length() > 0 && !passwordNew.equals(m_password);
				if (m_addressConfirm || m_wu.login (m_password))
				{
					//	Create / set session
					if (m_wu.isLoggedIn())
					{
						MSession cSession = MSession.get (m_ctx, m_request.getRemoteAddr(), 
							m_request.getRemoteHost(), m_session.getId());
						if (cSession != null)
							cSession.setWebStoreSession(true);
					}
					//
					if (passwordChange)
						log.fine("- update Pwd " + m_email + ", Old=" + m_password + ", DB=" + m_wu.getPassword() + ", New=" + passwordNew);
					if (WebUtil.updateFields(m_request, m_wu, passwordChange))
					{
						if (passwordChange)
							m_session.setAttribute(WebSessionCtx.HDR_MESSAGE, "Password changed");
					}
					else
					{
						m_forward = getLogin_RelURL ();
						log.warning(" - update not done");
					}
				}
				else
				{
					m_forward = getLogin_RelURL ();
					m_session.setAttribute(WebSessionCtx.HDR_MESSAGE, "Email/Password not correct");
					log.warning(" - update not confirmed");
				}
			}
			else	//	new
			{
				log.fine("** new " + m_email + "/" + m_password);
				m_wu.setEmail (m_email);
				m_wu.setPassword (m_password);
				if (WebUtil.updateFields (m_request, m_wu, true))
				{
					if (m_wu.login(m_password))
					{
						m_session.setAttribute (WebInfo.NAME, new WebInfo (m_ctx, m_wu));
						//	Create / set session
						MSession cSession = MSession.get (m_ctx, m_request.getRemoteAddr(), 
							m_request.getRemoteHost(), m_session.getId());
						if (cSession != null)
							cSession.setWebStoreSession(true);
		    			WebUtil.resendCode(m_request, m_wu);
					}
					else
						m_forward = getLogin_RelURL ();
				}
				else
				{
					log.fine("- failed - " + m_wu.getSaveErrorMessage() + " - " + m_wu.getPasswordMessage());
					m_forward = getLogin_RelURL ();
				}
			}	//	new
			if (m_wu!=null)
				m_session.setAttribute (WebInfo.NAME, new WebInfo (m_ctx, m_wu));
		}	//	Submit

		else if("email".equals(m_mode))
        {
            String email = WebUtil.getParameter (m_request, "EMail");
            if (email == null)
                email = "";
            email = email.trim();

            String emailNew = WebUtil.getParameter (m_request, "EMailNew");
            if (emailNew == null)
                emailNew = "";

            email = email.trim();
            if((emailNew.length() == 0)||(emailNew.equals(email)))
            {
                setMessage("New EMail invalid.");
                return false;
            }

            if(!WebUtil.isEmailValid(emailNew))
            {
                setMessage("New EMail invalid.");
                return false;
            }

            m_wu.setEmail(emailNew);
            m_wu.save();
            m_session.setAttribute(WebSessionCtx.HDR_MESSAGE, "EMail Address Changed");
            m_session.setAttribute(WebInfo.NAME, new WebInfo(m_ctx, m_wu));
        }

        else if("password".equals(m_mode))
        {
    		if (m_wu == null)
    		{
    			log.warning("No web user");
    			return false;
    		}

            String password = WebUtil.getParameter (m_request, "Password");
            if (password == null)
                password = "";	//	null loads w/o check
            password = password.trim();

            if(!m_wu.login(password))
            {
                setMessage("Email/Password not correct");
                return false;
            }

            MSession cSession = MSession.get (m_ctx, m_request.getRemoteAddr(), m_request.getRemoteHost(), m_session.getId());
            if (cSession != null)
                cSession.setWebStoreSession(true);

            String passwordNew = WebUtil.getParameter (m_request, "PasswordNew");
            if (passwordNew == null)
                passwordNew = "";

            password = password.trim();
            if( (passwordNew.length() == 0) || (passwordNew.equals(password)))
            {
                setMessage("New Password invalid.");
                return false;
            }

            m_wu.setPasswordMessage(null);
            m_wu.setPassword(passwordNew);
            if(m_wu.getPasswordMessage() != null)
            {
                setMessage("New Password invalid.");
                return false;
            }
            m_wu.save();
			if (m_forward==null || m_forward.equals(getLogin_RelURL ()))
				m_forward = "/index.jsp";
            m_session.setAttribute(WebSessionCtx.HDR_MESSAGE, "Password Changed");
            m_session.setAttribute(WebInfo.NAME, new WebInfo(m_ctx, m_wu));
        }

        else if("address".equals(m_mode))
        {
            m_wu.setC_Country_ID(WebUtil.getParamOrNull(m_request, "C_Country_ID"));
            m_wu.setC_Region_ID(WebUtil.getParamOrNull(m_request, "C_Region_ID"));
            m_wu.setRegionName(WebUtil.getParamOrNull(m_request, "RegionName"));
            m_wu.setName(WebUtil.getParamOrNull(m_request, "Name"));
            m_wu.setCompany(WebUtil.getParamOrNull(m_request, "Company"));
            m_wu.setTitle(WebUtil.getParamOrNull(m_request, "Title"));
            m_wu.setAddress(WebUtil.getParamOrNull(m_request, "Address"));
            m_wu.setAddress2(WebUtil.getParamOrNull(m_request, "Address2"));
            m_wu.setCity(WebUtil.getParamOrNull(m_request, "City"));
            m_wu.setPostal(WebUtil.getParamOrNull(m_request, "Postal"));
            m_wu.setPhone(WebUtil.getParamOrNull(m_request, "Phone"));
            m_wu.setFax(WebUtil.getParamOrNull(m_request, "Fax"));
            m_wu.save();
            m_session.setAttribute(WebSessionCtx.HDR_MESSAGE, "Contact Information Changed");
            m_session.setAttribute(WebInfo.NAME, new WebInfo(m_ctx, m_wu));
        }
		
        else if ("EMailVerify".equals(m_mode))
        {
    		if (m_wu == null)
    		{
    			log.warning("No web user");
    			return false;
    		}

    		log.info(m_forward + " - " + m_wu.toString());

    		String cmd = WebUtil.getParameter(m_request, "ReSend");
    		if (cmd != null && cmd.length() > 1)
    			WebUtil.resendCode(m_request, m_wu);
    		else
    			m_wu.setEMailVerifyCode(WebUtil.getParameter(m_request, "VerifyCode"), m_request.getRemoteAddr());

        }
		
        else if ("bankaccountach".equals(m_mode))
        {
    		if (m_wu == null)
    		{
    			log.warning("No web user");
    			return false;
    		}

    		log.info(m_forward + " - " + m_wu.toString());

    		MBPBankAccount thisBPBankAccount = m_wu.getBankAccount (true);
    		// As this sets bankaccountach 
    		thisBPBankAccount.setIsACH (true);
    		thisBPBankAccount.setA_City (WebUtil.getParamOrNull (m_request, "A_City"));
    		thisBPBankAccount.setA_Name (WebUtil.getParamOrNull (m_request, "A_Name"));
    		thisBPBankAccount.setAccountNo (WebUtil.getParamOrNull (m_request, "AccountNo"));
    		thisBPBankAccount.setRoutingNo (WebUtil.getParamOrNull (m_request, "RoutingNo"));
    		thisBPBankAccount.save ();
        }
		else
			log.log(Level.WARNING, "Unknown request='" + m_mode + "'");

		return true;
	}	//	action
	
	/** 
	 * setMessage to set a Message
	 * @param newVal 
	 */
	public void setMessage(String newVal)
	{
		if (newVal!=null)
			m_message = newVal;
	}
	
	/**
	 * getMessage back
	 * @return Message
	 */
	public String getMessage()
	{
		return m_message;
	}
	
	/**
	 * 	setP_ForwardTo to overwrite default "ForwardTo" Parameter
	 *	@param newVal new value to look for
	 */
	public void setP_ForwardTo(String newVal) 
	{
		if (newVal!=null) 
			P_ForwardTo = newVal;
	}
	
	/**
	 * 	getP_ForwardTo
	 *	@return ForwardTo request parameter
	 */
	public String getP_ForwardTo() 
	{
		return P_ForwardTo;
	}

	/**
	 * 	setP_EMail to overwrite default "EMail" Parameter
	 *	@param newVal new value to look for
	 */
	public void setP_EMail(String newVal) 
	{
		if (newVal!=null) 
			P_EMail = newVal;
	}
	
	/**
	 * 	getP_EMail
	 *	@return EMail request parameter
	 */
	public String getP_EMail() 
	{
		return P_EMail;
	}

	/**
	 * 	setP_Password to overwrite default "Password" Parameter
	 *	@param newVal new value to look for
	 */
	public void setP_Password(String newVal) 
	{
		if (newVal!=null) 
			P_Password = newVal;
	}
	
	/**
	 * 	getP_Password
	 *	@return Password request parameter
	 */
	public String getP_Password() 
	{
		return P_Password;
	}

	/**
	 * 	setP_SalesRep_ID to overwrite default "SalesRep_ID" Parameter
	 *	@param newVal new value to look for
	 */
	public void setP_SalesRep_ID(String newVal) 
	{
		if (newVal!=null) 
			P_SalesRep_ID = newVal;
	}
	
	/**
	 * 	getP_SalesRep_ID
	 *	@return SalesRep_ID request parameter
	 */
	public String getP_SalesRep_ID() 
	{
		return P_SalesRep_ID;
	}

	/**
	 * 	setP_Action to overwrite default "Action/Mode" Parameter
	 *	@param newVal new value to look for
	 */
	public void setP_Action(String newVal) 
	{
		if (newVal!=null) 
			P_Action = newVal;
	}
	
	/**
	 * 	getP_SalesRep_ID
	 *	@return SalesRep_ID request parameter
	 */
	public String getP_Action() 
	{
		return P_Action;
	}

	/**
	 * 	setLogin_RelURL to overwrite default Login Relative URL
	 *	@param newVal new relative URL inside Domain to goto
	 */
	public void setLogin_RelURL(String newVal) 
	{
		if (newVal!=null) 
			m_login_page = newVal;
	}
	
	/**
	 * 	getLogin_RelURL
	 *	@return Login_RelURL request parameter
	 */
	public String getLogin_RelURL() 
	{
		return m_login_page;
	}

	/**
	 * 	setLogin_RelURL to overwrite default Login Relative URL
	 *	@param newVal new relative URL inside Domain to goto
	 */
	public void setUpdate_page(String newVal) 
	{
		if (newVal!=null) 
			m_update_page = newVal;
	}
	
	/**
	 * 	getLogin_RelURL
	 *	@return Login_RelURL request parameter
	 */
	public String getUpdate_page() 
	{
		return m_update_page;
	}
	
	/**
	 * 	setForward updates Forward URL
	 *	@param newVal
	 */
	public void setForward(String newVal)
	{
		if (newVal!=null)
			m_forward = newVal;
	}

	/**
	 * 	getForward
	 *	@return URL to forward request on to
	 */
	public String getForward() 
	{
		return m_forward;
	}

	/**
	 * 	getSalesRep_ID
	 *	@return SalesRep_ID of the SalesRep_ID in the Request
	 */
	public String getSalesRep_ID() 
	{
		return m_salesRep;
	}
	
	/**
	 * 	setAddressConfirm 
	 *	@param newVal new addressConfirm
	 */
	public void setAddressConfirm(boolean newVal) 
	{
			m_addressConfirm = newVal;
	}
	
	/**
	 * 	getAdressConfirm
	 *	@return boolean addressConfirm
	 */
	public boolean getAddressConfirm() 
	{
		return m_addressConfirm;
	}
	
	public WebUser getWebUser() 
	{
		return m_wu;
	}
	
	public void setMode(String t_mode) 
	{
		m_mode = t_mode;
	}
	
	public String getMode() 
	{
		return m_mode;
	}
}