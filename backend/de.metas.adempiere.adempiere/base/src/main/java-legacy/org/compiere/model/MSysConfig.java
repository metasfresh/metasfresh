/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Contributor: Goodwill Consulting (www.goodwill.co.id)                      *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * System Configuration
 *
 * @author Armen Rizal
 * @version $Id: MSysConfig.java,v 1.5 2005/11/28 11:56:45 armen Exp $
 *          Contributor: Carlos Ruiz - globalqss - [ 1800371 ] System Configurator Enhancements
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1885496 ] Performance NEEDS
 * 
 * @deprecated Please use {@link ISysConfigBL}
 */
@Deprecated
public class MSysConfig extends X_AD_SysConfig
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5271070197457739666L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_SysConfig_ID id
	 */
	public MSysConfig(Properties ctx, int AD_SysConfig_ID, String trxName)
	{
		super(ctx, AD_SysConfig_ID, trxName);
		if (AD_SysConfig_ID == 0)
		{
			// setName (null);
			// setValue (null);
		}
	}	// MSysConfig

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 */
	public MSysConfig(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MSysConfig

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MSysConfig.class);
	/** Cache */
	private static CCache<String, String> s_cache = new CCache<>(Table_Name, 40, 0); // expire=never
	
	private static final String NO_Value = new String(""); // NOTE: new instance to make sure it's unique

	/**
	 * Reset Cache
	 * 
	 */
	public static void resetCache()
	{
		s_cache.reset();
	}

	/**
	 * Get system configuration property of type string
	 * 
	 * @param Name
	 * @param defaultValue
	 * @return String
	 */
	public static String getValue(String Name, String defaultValue)
	{
		return getValue(Name, defaultValue, 0, 0);
	}

	/**
	 * Get system configuration property of type string
	 * 
	 * @param Name
	 * @return String
	 */
	public static String getValue(String Name)
	{
		return getValue(Name, null);
	}

	/**
	 * Get system configuration property of type int
	 * 
	 * @param Name
	 * @param defaultValue
	 * @return int
	 */
	public static int getIntValue(String Name, int defaultValue)
	{
		String s = getValue(Name);
		if (s == null)
			return defaultValue;

		if (s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type double
	 * 
	 * @param Name
	 * @param defaultValue
	 * @return double
	 */
	public static double getDoubleValue(String Name, double defaultValue)
	{
		String s = getValue(Name);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type boolean
	 * 
	 * @param Name
	 * @param defaultValue
	 * @return boolean
	 */
	public static boolean getBooleanValue(String Name, boolean defaultValue)
	{
		String s = getValue(Name);
		if (s == null || s.length() == 0)
			return defaultValue;

		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}

	/**
	 * Get client configuration property of type string
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return String
	 */
	public static String getValue(String Name, String defaultValue, int AD_Client_ID)
	{
		return getValue(Name, defaultValue, AD_Client_ID, 0);
	}

	/**
	 * Get system configuration property of type string
	 * 
	 * @param Name
	 * @param Client ID
	 * @return String
	 */
	public static String getValue(String Name, int AD_Client_ID)
	{
		return (getValue(Name, null, AD_Client_ID));
	}

	/**
	 * Get system configuration property of type int
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return int
	 */
	public static int getIntValue(String Name, int defaultValue, int AD_Client_ID)
	{
		String s = getValue(Name, AD_Client_ID);
		if (s == null)
			return defaultValue;

		if (s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type double
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return double
	 */
	public static double getDoubleValue(String Name, double defaultValue, int AD_Client_ID)
	{
		String s = getValue(Name, AD_Client_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type boolean
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return boolean
	 */
	public static boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID)
	{
		String s = getValue(Name, AD_Client_ID);
		if (s == null || s.length() == 0)
			return defaultValue;

		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}

	/**
	 * Get client configuration property of type string
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public static String getValue(final String Name, final String defaultValue, final int AD_Client_ID, final int AD_Org_ID)
	{
		//
		// Get/retrieve the sysconfig's value
		final String key = "" + AD_Client_ID + "_" + AD_Org_ID + "_" + Name;
		final String value = s_cache.get(key, new Supplier<String>()
		{
			@Override
			public String get()
			{
				final String valueRetrieved = retrieveSysConfigValue(Name, AD_Client_ID, AD_Org_ID);
				return valueRetrieved == null ? NO_Value : valueRetrieved;
			}
		});
		
		//
		// If we got no value, return the defaultValue provided.
		// NOTE: we don't expect "value" to be null, but it would be the same decission as for NO_Value.
		if (value == null || value == NO_Value)
		{
			return defaultValue;
		}
		
		return value;
	}

	/**
	 * 
	 * @param Name
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return sysconfig's value or <code>null</code> if it was not found
	 */
	private static final String retrieveSysConfigValue(String Name, int AD_Client_ID, int AD_Org_ID)
	{
		final String sql = "SELECT Value FROM AD_SysConfig"
				+ " WHERE Name=? AND AD_Client_ID IN (0, ?) AND AD_Org_ID IN (0, ?) AND IsActive='Y'"
				+ " ORDER BY AD_Client_ID DESC, AD_Org_ID DESC";
		final Object[] sqlParams = new Object[] { Name, AD_Client_ID, AD_Org_ID };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String value = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				value = rs.getString(1);
			}
		}
		catch (final SQLException sqlEx)
		{
			final DBException ex = new DBException(sqlEx, sql, sqlParams);
			s_log.error("Failed retrieving the sysconfig value", ex);
			
			value = null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return value;
	}

	/**
	 * Get system configuration property of type string
	 * 
	 * @param Name
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public static String getValue(String Name, int AD_Client_ID, int AD_Org_ID)
	{
		return getValue(Name, null, AD_Client_ID, AD_Org_ID);
	}

	/**
	 * Get system configuration property of type int
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return int
	 */
	public static int getIntValue(String Name, int defaultValue, int AD_Client_ID, int AD_Org_ID)
	{
		String s = getValue(Name, AD_Client_ID, AD_Org_ID);
		if (s == null)
			return defaultValue;

		if (s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type double
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return double
	 */
	public static double getDoubleValue(String Name, double defaultValue, int AD_Client_ID, int AD_Org_ID)
	{
		String s = getValue(Name, AD_Client_ID, AD_Org_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			s_log.error("getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}

	/**
	 * Get system configuration property of type boolean
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return boolean
	 */
	public static boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID)
	{
		String s = getValue(Name, AD_Client_ID, AD_Org_ID);
		if (s == null || s.length() == 0)
			return defaultValue;

		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return true if save
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		log.debug("New=" + newRecord);

		if (getAD_Client_ID() != 0 || getAD_Org_ID() != 0)
		{

			// Get the configuration level from the System Record
			String configLevel = null;
			String sql = "SELECT ConfigurationLevel FROM AD_SysConfig WHERE Name=? AND AD_Client_ID = 0 AND AD_Org_ID = 0";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, getName());
				rs = pstmt.executeQuery();
				if (rs.next())
					configLevel = rs.getString(1);
			}
			catch (SQLException e)
			{
				s_log.error("getValue", e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			if (configLevel == null)
			{
				// not found for system
				// if saving an org parameter - look config in client
				if (getAD_Org_ID() != 0)
				{
					// Get the configuration level from the System Record
					sql = "SELECT ConfigurationLevel FROM AD_SysConfig WHERE Name=? AND AD_Client_ID = ? AND AD_Org_ID = 0";
					try
					{
						pstmt = DB.prepareStatement(sql, null);
						pstmt.setString(1, getName());
						pstmt.setInt(2, getAD_Client_ID());
						rs = pstmt.executeQuery();
						if (rs.next())
							configLevel = rs.getString(1);
					}
					catch (SQLException e)
					{
						s_log.error("getValue", e);
					}
					finally
					{
						DB.close(rs, pstmt);
						rs = null;
						pstmt = null;
					}
				}
			}

			if (configLevel != null)
			{

				setConfigurationLevel(configLevel);

				// Disallow saving org parameter if the system parameter is marked as 'S' or 'C'
				if (getAD_Org_ID() != 0 &&
						(configLevel.equals(MSysConfig.CONFIGURATIONLEVEL_System) ||
						configLevel.equals(MSysConfig.CONFIGURATIONLEVEL_Client)))
				{
					throw new AdempiereException("Can't Save Org Level. This is a system or client parameter, you can't save it as organization parameter");
				}

				// Disallow saving client parameter if the system parameter is marked as 'S'
				if (getAD_Client_ID() != 0 && configLevel.equals(MSysConfig.CONFIGURATIONLEVEL_System))
				{
					throw new AdempiereException("Can't Save Client Level. This is a system parameter, you can't save it as client parameter");
				}

			}
			else
			{

				// fix possible wrong config level
				if (getAD_Org_ID() != 0)
					setConfigurationLevel(CONFIGURATIONLEVEL_Organization);
				else if (getAD_Client_ID() != 0 && MSysConfig.CONFIGURATIONLEVEL_System.equals(getConfigurationLevel()))
					setConfigurationLevel(CONFIGURATIONLEVEL_Client);

			}

		}

		return true;
	}	// beforeSave

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + get_ID()
				+ ", " + getName() + "=" + getValue()
				+ ", ConfigurationLevel=" + getConfigurationLevel()
				+ ", Client|Org=" + getAD_Client_ID() + "|" + getAD_Org_ID()
				+ ", EntityType=" + getEntityType()
				+ "]";
	}
}	// MSysConfig
