/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.adempiere.model.I_AD_User;
import de.metas.logging.LogManager;

/**
 *  User Model
 *
 *  @author Jorg Janke
 *  @version $Id: MUser.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2788430 ] MUser.getOfBPartner add trxName parameter
 * 				https://sourceforge.net/tracker/index.php?func=detail&aid=2788430&group_id=176962&atid=879335
 */
public class MUser extends X_AD_User
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1399447378628744412L;


	/**
	 * Get active Users of BPartner
	 * @param ctx context
	 * @param C_BPartner_ID id
	 * @return array of users
	 * @deprecated Since 3.5.3a. Please use {@link #getOfBPartner(Properties, int, String)}.
	 */
	@Deprecated
	public static MUser[] getOfBPartner (Properties ctx, int C_BPartner_ID)
	{
		return getOfBPartner(ctx, C_BPartner_ID, null);
	}

	/**
	 * Get active Users of BPartner
	 * @param ctx
	 * @param C_BPartner_ID
	 * @param trxName
	 * @return array of users
	 */
	public static MUser[] getOfBPartner (Properties ctx, int C_BPartner_ID, String trxName)
	{
		List<MUser> list = new Query(ctx, MUser.Table_Name, "C_BPartner_ID=?", trxName)
		.setParameters(new Object[]{C_BPartner_ID})
		.setOnlyActiveRecords(true)
		.setOrderBy(MUser.COLUMNNAME_AD_User_ID) // metas: tsa: make sure we have same order every time
		.list();

		MUser[] retValue = new MUser[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfBPartner

	/**
	 * 	Get Users with Role
	 *	@param role role
	 *	@return array of users
	 */
	public static MUser[] getWithRole(I_AD_Role role)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(role);
		List<MUser> list = new ArrayList<MUser>();
		String sql = "SELECT * FROM AD_User u "
			+ "WHERE u.IsActive='Y'"
			+ " AND EXISTS (SELECT * FROM AD_User_Roles ur "
				+ "WHERE ur.AD_User_ID=u.AD_User_ID AND ur.AD_Role_ID=? AND ur.IsActive='Y')";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt (1, role.getAD_Role_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MUser(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		MUser[] retValue = new MUser[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getWithRole

	/**
	 * 	Get User (cached)
	 * 	Also loads Admninistrator (0)
	 *	@param ctx context
	 *	@param AD_User_ID id
	 *	@return user
	 * @deprecated Please use {@link IUserDAO#retrieveUser(Properties, int)}.
	 */
	@Deprecated
	public static MUser get (final Properties ctx, final int AD_User_ID)
	{
		final I_AD_User user = Services.get(IUserDAO.class).retrieveUser(ctx, AD_User_ID);
		return LegacyAdapters.convertToPO(user);
	}	//	get

	/**
	 * 	Get Current User (cached)
	 *	@param ctx context
	 *	@return user
	 */
	public static MUser get (Properties ctx)
	{
		return get(ctx, Env.getAD_User_ID(ctx));
	}	//	get

	/**
	 * 	Get User
	 *	@param ctx context
	 *	@param name name
	 *	@param password password
	 *	@return user or null
	 */
	public static MUser get (Properties ctx, String name, String password)
	{
		if (name == null || name.length() == 0 || password == null || password.length() == 0)
		{
			s_log.warn("Invalid Name/Password = " + name + "/" + password);
			return null;
		}
		int AD_Client_ID = Env.getAD_Client_ID(ctx);

		MUser retValue = null;
		/* TODO: Implement same validation as in Login.java -
		 * about (SELECT IsEncrypted FROM AD_Column WHERE AD_Column_ID=417)='N') */
		String sql = "SELECT * FROM AD_User "
			+ "WHERE Name=? AND (Password=? OR Password=?) AND IsActive='Y' AND AD_Client_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString (1, name);
			pstmt.setString (2, password);
			pstmt.setString(3, SecureEngine.encrypt(password));
			pstmt.setInt(4, AD_Client_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = new MUser (ctx, rs, null);
				if (rs.next())
					s_log.warn("More then one user with Name/Password = " + name);
			}
			else
				s_log.debug("No record");
 		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	get

	/**
	 *  Get Name of AD_User
	 *  @param  AD_User_ID   System User
	 *  @return Name of user or ?
	 */
	public static String getNameOfUser (int AD_User_ID)
	{
		String name = "?";
		//	Get ID
		String sql = "SELECT Name FROM AD_User WHERE AD_User_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_User_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				name = rs.getString(1);
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return name;
	}	//	getNameOfUser


	/**
	 * 	User is SalesRep
	 *	@param AD_User_ID user
	 *	@return true if sales rep
	 */
	public static boolean isSalesRep (int AD_User_ID)
	{
		if (AD_User_ID == 0)
			return false;
		String sql = "SELECT MAX(AD_User_ID) FROM AD_User u"
			+ " INNER JOIN C_BPartner bp ON (u.C_BPartner_ID=bp.C_BPartner_ID) "
			+ "WHERE bp.IsSalesRep='Y' AND AD_User_ID=?";
		int no = DB.getSQLValue(null, sql, AD_User_ID);
		return no == AD_User_ID;
	}	//	isSalesRep

	/**	Static Logger			*/
	private static Logger	s_log	= LogManager.getLogger(MUser.class);


	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param AD_User_ID id
	 * 	@param trxName transaction
	 */
	public MUser (Properties ctx, int AD_User_ID, String trxName)
	{
		super (ctx, AD_User_ID, trxName);	//	0 is also System
		if (AD_User_ID == 0)
		{
			setIsFullBPAccess (true);
			setNotificationType(NOTIFICATIONTYPE_EMail);
		}
	}	//	MUser

	/**
	 * 	Parent Constructor
	 *	@param partner partner
	 */
	public MUser (I_C_BPartner partner)
	{
		this(InterfaceWrapperHelper.getCtx(partner),
				0,
				InterfaceWrapperHelper.getTrxName(partner));
		setClientOrg(InterfaceWrapperHelper.getPO(partner));
		setC_BPartner_ID (partner.getC_BPartner_ID());
		setName(partner.getName());
	}	//	MUser

	/**
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs current row of result set to be loaded
	 * 	@param trxName transaction
	 */
	public MUser (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MUser

	/** User Access Rights				*/
	private List<I_AD_UserBPAccess>	m_bpAccess = null;

	/** Is Administrator */
	private final Supplier<Boolean> m_isAdministratorSupplier = Suppliers.memoize(() -> {
		return Services.get(IUserRolePermissionsDAO.class)
				.matchUserRolesPermissionsForUser(getCtx(), getAD_User_ID(), IUserRolePermissions::isSystemAdministrator);
	});

	/**
	 * 	Get Value - 7 bit lower case alpha numerics max length 8
	 *	@return value
	 */
	@Override
	public String getValue()
	{
		String s = super.getValue();
		if (s != null)
			return s;
		setValue(null);
		return super.getValue();
	}	//	getValue

	/**
	 * 	Set Value - 7 bit lower case alpha numerics max length 8
	 *	@param Value
	 */
	@Override
	public void setValue(String Value)
	{
		if (Value == null || Value.trim().length () == 0)
			Value = getLDAPUser();
		if (Value == null || Value.length () == 0)
			Value = getName();
		if (Value == null || Value.length () == 0)
			Value = "noname";
		//
		String result = cleanValue(Value);
		if (result.length() > 8)
		{
			String first = getName(Value, true);
			String last = getName(Value, false);
			if (last.length() > 0)
			{
				String temp = last;
				if (first.length() > 0)
					temp = first.substring (0, 1) + last;
				result = cleanValue(temp);
			}
			else
				result = cleanValue(first);
		}
		if (result.length() > 8)
			result = result.substring (0, 8);
		super.setValue(result);
	}	//	setValue

	/**
	 * 	Clean Value
	 *	@param value value
	 *	@return lower case cleaned value
	 */
	private String cleanValue (String value)
	{
		char[] chars = value.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			char ch = chars[i];
			ch = Character.toLowerCase (ch);
			if ((ch >= '0' && ch <= '9')		//	digits
				|| (ch >= 'a' && ch <= 'z'))	//	characters
				sb.append(ch);
		}
		return sb.toString ();
	}	//	cleanValue

	/**
	 * 	Get First Name
	 *	@return first name
	 */
	public String getFirstName()
	{
		return getName (getName(), true);
	}	//	getFirstName

	/**
	 * 	Get Last Name
	 *	@return first name
	 */
	public String getLastName()
	{
		return getName (getName(), false);
	}	//	getLastName

	/**
	 * 	Get First/Last Name
	 *	@param name name
	 *	@param getFirst if true first name is returned
	 *	@return first/last name
	 */
	private String getName (String name, boolean getFirst)
	{
		if (name == null || name.length () == 0)
			return "";
		String first = null;
		String last = null;
		//	Janke, Jorg R - Jorg R Janke
		//	double names not handled gracefully nor titles
		//	nor (former) aristrocratic world de/la/von
		boolean lastFirst = name.indexOf(',') != -1;
		StringTokenizer st = null;
		if (lastFirst)
			st = new StringTokenizer(name, ",");
		else
			st = new StringTokenizer(name, " ");
		while (st.hasMoreTokens())
		{
			String s = st.nextToken().trim();
			if (lastFirst)
			{
				if (last == null)
					last = s;
				else if (first == null)
					first = s;
			}
			else
			{
				if (first == null)
					first = s;
				else
					last = s;
			}
		}
		if (getFirst)
		{
			if (first == null)
				return "";
			return first.trim();
		}
		if (last == null)
			return "";
		return last.trim();
	}	//	getName


	/**
	 * 	Add to Description
	 *	@param description description to be added
	 */
	public void addDescription (String description)
	{
		if (description == null || description.length() == 0)
			return;
		String descr = getDescription();
		if (descr == null || descr.length() == 0)
			setDescription (description);
		else
			setDescription (descr + " - " + description);
	}	//	addDescription


	/**
	 * 	String Representation
	 *	@return Info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MUser[")
			.append(get_ID())
			.append(",Name=").append(getName())
			.append(",EMailUserID=").append(getEMailUser())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Is it an Online Access User
	 *	@return true if it has an email and password
	 */
	public boolean isOnline ()
	{
		if (getEMail() == null || getPassword() == null)
			return false;
		return true;
	}	//	isOnline

	/**
	 * 	Set EMail - reset validation
	 *	@param EMail email
	 */
	@Override
	public void setEMail(String EMail)
	{
		super.setEMail (EMail);
		setEMailVerifyDate (null);
	}	//	setEMail

	/**
	 * 	Convert EMail
	 *	@return Valid Internet Address
	 */
	public InternetAddress getInternetAddress ()
	{
		String email = getEMail();
		if (email == null || email.length() == 0)
			return null;
		try
		{
			InternetAddress ia = new InternetAddress (email, true);
			if (ia != null)
				ia.validate();	//	throws AddressException
			return ia;
		}
		catch (AddressException ex)
		{
			log.warn(email + " - " + ex.getLocalizedMessage());
		}
		return null;
	}	//	getInternetAddress

	/**
	 * 	Validate Email (does not work).
	 * 	Check DNS MX record
	 * 	@param ia email
	 *	@return error message or ""
	 */
	private String validateEmail (InternetAddress ia)
	{
		if (ia == null)
			return "NoEmail";
                else return ia.getAddress();
		/*
                if (true)
			return null;

		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
	//	env.put(Context.PROVIDER_URL, "dns://admin.adempiere.org");
		try
		{
			DirContext ctx = new InitialDirContext(env);
		//	Attributes atts = ctx.getAttributes("admin");
			Attributes atts = ctx.getAttributes("dns://admin.adempiere.org", new String[] {"MX"});
			NamingEnumeration en = atts.getAll();
	//		NamingEnumeration en = ctx.list("adempiere.org");
			while (en.hasMore())
			{
				System.out.println(en.next());
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		return null;
                */
	}	//	validateEmail

	/**
	 * 	Is the email valid
	 * 	@return return true if email is valid (artificial check)
	 */
	public boolean isEMailValid()
	{
		return validateEmail(getInternetAddress()) != null;
	}	//	isEMailValid

	/**
	 * 	Could we send an email
	 * 	@return true if EMail Uwer/PW exists
	 */
	public boolean isCanSendEMail()
	{
		final String emailUser = getEMailUser();
		if(Check.isEmpty(emailUser, true))
		{
			return false;
		}
		
		// If SMTP authorization is not required, then don't check password - teo_sarca [ 1723309 ]
		if (!MClient.get(getCtx()).isSmtpAuthorization())
		{
			return true;
		}
		final String emailPassword = getEMailUserPW();
		return !Check.isEmpty(emailPassword, false);
	}	//	isCanSendEMail

	/**
	 * 	Get EMail Validation Code
	 *	@return code
	 */
	public String getEMailVerifyCode()
	{
		long code = getAD_User_ID()
			+ getName().hashCode();
		return "C" + String.valueOf(Math.abs(code)) + "C";
	}	//	getEMailValidationCode

	/**
	 * 	Check & Set EMail Validation Code.
	 *	@param code code
	 *	@param info info
	 *	@return true if valid
	 */
	public boolean setEMailVerifyCode (String code, String info)
	{
		boolean ok = code != null
			&& code.equals(getEMailVerifyCode());
		if (ok)
			setEMailVerifyDate(new Timestamp(System.currentTimeMillis()));
		else
			setEMailVerifyDate(null);
		setEMailVerify(info);
		return ok;
	}	//	setEMailValidationCode

	/**
	 * 	Is EMail Verified by response
	 *	@return true if verified
	 */
	public boolean isEMailVerified()
	{
		//	UPDATE AD_User SET EMailVerifyDate=now(), EMailVerify='Direct' WHERE AD_User_ID=1
		return getEMailVerifyDate() != null
			&& getEMailVerify() != null
			&& getEMailVerify().length() > 0;
	}	//	isEMailVerified

	/**
	 * 	Is User an Administrator?
	 *	@return true id Admin
	 */
	public boolean isAdministrator()
	{
		return m_isAdministratorSupplier.get();
	}	//	isAdministrator

	/**
	 * 	Has the user Access to BP info and resources
	 *	@param BPAccessType access type
	 *	@param params opt parameter
	 *	@return true if access
	 */
	public boolean hasBPAccess (String BPAccessType, Object[] params)
	{
		if (isFullBPAccess())
			return true;
		for (final I_AD_UserBPAccess bpAccess : getBPAccess())
		{
			if (Check.equals(bpAccess.getBPAccessType(), BPAccessType))
			{
				return true;
			}
		}
		return false;
	}	//	hasBPAccess

	/**
	 * 	Get active BP Access records
	 *	@param requery requery
	 *	@return access list
	 */
	private List<I_AD_UserBPAccess> getBPAccess ()
	{
		if (m_bpAccess != null)
		{
			return m_bpAccess;
		}

		m_bpAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_UserBPAccess.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UserBPAccess.COLUMNNAME_AD_User_ID, getAD_User_ID())
				.create()
				.list();

		return m_bpAccess;
	}	//	getBPAccess


	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	New Address invalidates verification
		if (!newRecord && is_ValueChanged("EMail"))
			setEMailVerifyDate(null);
		if (newRecord || super.getValue() == null || is_ValueChanged("Value"))
			setValue(super.getValue());
		return true;
	}	//	beforeSave


	/**
	 * 	Test
	 *	@param args ignored
	 *
	public static void main (String[] args)
	{
		try
		{
			validateEmail(new InternetAddress("jjanke@adempiere.org"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	//	org.compiere.Adempiere.startupClient();
	//	System.out.println ( MUser.get(Env.getCtx(), "SuperUser", "22") );
	}	//	main	/* */
}	//	MUser
