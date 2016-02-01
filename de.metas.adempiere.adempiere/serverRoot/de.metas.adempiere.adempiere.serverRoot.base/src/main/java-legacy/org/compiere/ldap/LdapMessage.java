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
 * You may reach us at: ComPiere, Inc. - http://www.compiere.org/license.html
 * 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA or info@compiere.org
 *****************************************************************************/
package org.compiere.ldap;

import java.util.logging.Level;

import org.compiere.util.CLogger;

import com.sun.jndi.ldap.BerDecoder;

/**
 * 	Ldap Message
 *
 *  @author Jorg Janke
 *  @version $Id: LdapMessage.java,v 1.1 2006/10/09 00:23:16 jjanke Exp $
 */
public class LdapMessage
{
	static public final int BIND_REQUEST = 96;
	static public final int BIND_RESPONSE = 97;
	static public final int UNBIND_REQUEST	= 98;
	static public final int SEARCH_REQUEST	= 99;
	static public final int SEARCH_REP_ENTRY	= 100;
	static public final int SEARCH_RES_RESULT	= 101;

	static public final int SIMPLE_AUTHENTICATION = 128;

	static public final int FILTER_AND = 160;
	static public final int FILTER_OR = 161;
	static public final int FILTER_NOT = 162;
	static public final int FILTER_EQUALITYMATCH = 163;

	static public final int SEQUENCE = 48;

	/** Decoder */
	private BerDecoder decoder = null;
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (LdapMessage.class);
	/** Protocol Operation		*/
	private int		m_protocolOp = -1;
    /** Message Id needed for the reply message */
	private int  msgId;
	/** Distinguished name */
	private String dn = null;
	/** Organization */
	private String org = null;
	/** Organization unit */
	private String orgUnit = null;
	/** User Id */
	private String userId = null;
	/** Password */
	private String passwd = null;
	/** base Object */
	private String baseObj = null;
	/** LdapResult object to hold if there's any error during parsing */
	private LdapResult result = null;

	/**
	 * 	Ldap Message
	 */
	public LdapMessage()
	{
	}	//	LdapMessage

	/*
	 *  Reset all the attributes
	 */
	public void reset(LdapResult result)
	{
		this.result = result;
		decoder = null;
		m_protocolOp = -1;
		msgId = -1;
		dn = null;
		org = null;
		orgUnit = null;
		userId = null;
		passwd = null;
		baseObj = null;

	}  // reset()

	/**
	 * 	Decode Message
	 *	@param data input buffer
	 *  @param length buffer size
	 */
	public void decode(byte[] data, int length)
	{
		try
		{
			// Create the decoder
			decoder = new BerDecoder(data, 0, length);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, data.toString(), e);
			return;
		}

		try
		{
			// Parse the message envelope
			decoder.parseSeq(null);

			//  Parse message Id
			msgId = decoder.parseInt();

			// Parse the operation protocol
			m_protocolOp = decoder.parseSeq(null);

			//
			//	Payload
			if (m_protocolOp == BIND_REQUEST)
				handleBind();
			else if (m_protocolOp == UNBIND_REQUEST)
				log.info("#" + msgId + ": unbind");
			else if (m_protocolOp == SEARCH_REQUEST)
				handleSearch();
			else  // Only supoort BIND, UNBIND and SEARCH
			{
				result.setErrorNo(LdapResult.LDAP_PROTOCOL_ERROR);
				result.setErrorString(": Unsupported Request");
				log.warning("#" + msgId + ": Unknown Op + " + m_protocolOp);
			}
		}
		catch (Exception ex)
		{
			result.setErrorNo(LdapResult.LDAP_PROTOCOL_ERROR);
			log.log(Level.SEVERE, "", ex);
		}
	}	//	decode

	/*
	 * Encode the search request message
	 */
	private void handleSearch()
	{
		try
		{
			// Parse the base Object
			baseObj = decoder.parseString(true);
			parseDN(baseObj);

			decoder.parseEnumeration();  // scope
			decoder.parseEnumeration();  // derefAliases
			decoder.parseInt();  // sizeLimit
			decoder.parseInt();  // timeLimit
			decoder.parseBoolean();  // typeOnly

			boolean equalityFilter = false;
			while (true)
			{
				int filter = decoder.parseSeq(null); //Filter
				if (filter == FILTER_EQUALITYMATCH)
				{
					decoder.parseString(true);
					userId = decoder.parseString(true);
					equalityFilter = true;
					break;
				}
				else if (filter == FILTER_AND)
					decoder.parseStringWithTag(135, true, null);
				else if (filter == SEQUENCE)
					break;
			}  // while true

			if (!equalityFilter)  // Didn't find the it
			{
				result.setErrorNo(LdapResult.LDAP_PROTOCOL_ERROR);
				result.setErrorString("Can't can't Filter - EqualityMatch");
			}
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
	}   // handleSearch()

	/*
	 * Encode the bind request message
	 */
	private void handleBind()
	{
		try
		{
			// Parse LDAP version; only support v3
			int version = decoder.parseInt();
			if (version != 3)
			{
				result.setErrorNo(LdapResult.LDAP_PROTOCOL_ERROR);
				result.setErrorString("Unsupported LDAP version");
				log.info("#" + msgId + ": unsupported LDAP version - " + version);
				return;
			}

			// Parse DN
			dn = decoder.parseString(true);

			// Peek on AuthenticationChoice; only support simple authentication
			int auth = decoder.peekByte();
			if (auth != SIMPLE_AUTHENTICATION)  // 0x80 - simple authentication
			{
				result.setErrorNo(LdapResult.LDAP_AUTH_METHOD_NOT_SUPPORTED);
				log.info("#" + msgId + ": unsupported authentication method - " + auth);
				return;
			}

			// It is simple authentication, get the authentication string
			passwd = decoder.parseStringWithTag(SIMPLE_AUTHENTICATION, true, null);
			if (passwd != null && passwd.length() > 0)
			{
				parseDN(dn);
				if (userId == null || userId.length() <= 0)
				{
					result.setErrorNo(LdapResult.LDAP_NO_SUCH_OBJECT);
					result.setErrorString(": \"cn\" not defined");
					log.info("#" + msgId + ": \"cn\" not defined");
					return;
				}
			}

			// Log the information
			log.info("#" + msgId + ": bind - version=" + version + ", userId=" + userId);
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
	}  // handleBind()

	/*
	 * Parse the DN to find user id, organization and organization unit
	 */
	private void parseDN(String dName)
	{
		String[] dnArray = dName.split(",");
		for (int i = 0; i < dnArray.length; i++)
		{
			if (dnArray[i].startsWith("cn="))
				userId = dnArray[i].split("=")[1];
			else if (dnArray[i].startsWith("o="))
				org = dnArray[i].split("=")[1];
			else if (dnArray[i].startsWith("ou="))
				orgUnit = dnArray[i].split("=")[1];
		}
	}  // parseDN()

	/**
	 * 	Get Operation Code
	 *	@return protocolOp
	 */
	public int getOperation()
	{
		return m_protocolOp;
	}	//	getOperation

	/**
	 * 	Get message id
	 *	@return msgId
	 */
	public int getMsgId()
	{
		return msgId;
	}	//	getMsgId()

	/**
	 * 	Get DN
	 *	@return dn
	 */
	public String getDN()
	{
		return dn;
	}	//	getDN()

	/**
	 * 	Get User Id
	 *	@return userId
	 */
	public String getUserId()
	{
		return userId;
	}	//	getUserId()

	/**
	 * 	Get User passwod
	 *	@return passwd
	 */
	public String getUserPasswd()
	{
		return passwd;
	}	//	getUserPasswd()

	/**
	 * 	Get base object
	 *	@return baseObj
	 */
	public String getBaseObj()
	{
		return baseObj;
	}	//	getBaseObj()

	/**
	 * 	Get organization
	 *	@return org
	 */
	public String getOrg()
	{
		return org;
	}	//	getOrg()

	/**
	 * 	Get organization unit
	 *	@return orgUnit
	 */
	public String getOrgUnit()
	{
		return orgUnit;
	}	//	getOrgUnit()
}	//	LdapMessage
