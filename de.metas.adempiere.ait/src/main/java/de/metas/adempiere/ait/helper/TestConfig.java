package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.Check;
import org.junit.Assert;

import ch.qos.logback.classic.Level;

public class TestConfig
{

	private int AD_Client_ID = 1000000;

	private int AD_Org_ID = 1000000;

	private int AD_Role_ID = 1000000;

	private int AD_User_Login_ID = 100;

	private int AD_User_Normal_ID;

	private int AD_User_Admin_ID;

	private Level logLevel = Level.INFO;

	private int M_PriceListVersion_Base_ID = 1000015;

	private String C_BPartner_Value = null;

	private String C_BPartner_Name = IHelper.DEFAULT_BPName;

	private int C_TaxCategory_Normal_ID;

	private int C_TaxCategory_Reduced_ID;

	private int C_TaxCategory_TaxExempt_ID;

	private int C_DocType_CreditMemo_ID;

	private final Properties props;

	public TestConfig(final Properties props)
	{
		this.props = props;

		this.setAD_Client_ID(Integer.parseInt(getProp("AD_Client_ID").trim()));
		this.setAD_Org_ID(Integer.parseInt(getProp("AD_Org_ID").trim()));
		this.setAD_Role_ID(Integer.parseInt(getProp("AD_Role_ID").trim()));
		this.setAD_User_Login_ID(Integer.parseInt(getProp("AD_User_Login_ID").trim()));
		this.setAD_User_Normal_ID(Integer.parseInt(getProp("AD_User_Normal_ID").trim()));
		this.getAD_User_Admin_ID(Integer.parseInt(getProp("AD_User_Admin_ID").trim()));
		this.setM_PriceListVersion_Base_ID(Integer.parseInt(getProp("M_PriceListVersion_Base_ID").trim()));
		this.setProjectWithMetasPrepayOrder(Boolean.parseBoolean(getProp("ProjectWithMetasPrepayOrder").trim()));
		this.setC_TaxCategory_Normal_ID(Integer.parseInt(getProp("C_TaxCategory_Normal_ID").trim()));
		this.setC_TaxCategory_Reduced_ID(Integer.parseInt(getProp("C_TaxCategory_Reduced_ID").trim()));
		this.setC_TaxCategory_TaxExempt_ID(Integer.parseInt(getProp("C_TaxCategory_TaxExempt_ID").trim()));
		this.setC_DocType_CreditMemo_ID(Integer.parseInt(getProp("C_DocType_CreditMemo_ID").trim()));
	}

	/**
	 * Copy constructor. This construtor calls {@link #TestConfig(Properties)} to read the properties. However, if the given <code>config</code> has members with values that differ from the props,
	 * they take precendence.
	 * 
	 * @param config
	 */
	public TestConfig(final TestConfig config)
	{
		this(config.props);

		this.testPrefix = config.testPrefix;

		this.AD_Client_ID = config.AD_Client_ID;
		this.AD_Org_ID = config.AD_Org_ID;
		this.AD_Role_ID = config.AD_Role_ID;
		this.AD_User_Admin_ID = config.AD_User_Admin_ID;
		this.AD_User_Login_ID = config.AD_User_Login_ID;
		this.AD_User_Normal_ID = config.AD_User_Normal_ID;
		this.C_BPartner_Value = config.C_BPartner_Value;
		this.C_BPartner_Name = config.C_BPartner_Name;
		this.logLevel = config.logLevel;
		this.M_PriceListVersion_Base_ID = config.M_PriceListVersion_Base_ID;
		this.projectWithMetasPrepayOrder = config.projectWithMetasPrepayOrder;

		this.C_TaxCategory_Normal_ID = config.C_TaxCategory_Normal_ID;
		this.C_TaxCategory_Reduced_ID = config.C_TaxCategory_Reduced_ID;
		this.C_TaxCategory_TaxExempt_ID = config.C_TaxCategory_TaxExempt_ID;

		this.customParams.putAll(config.customParams);
	}

	/**
	 * Returns the property value for the given parameter, or throws an AdempiereException if that value is null.
	 * 
	 * @param name
	 */
	protected String getProp(String name)
	{
		final String val = props.getProperty(name);
		Check.errorIf(val == null, "Missing property with name {}", name);
		return val;
	}

	/**
	 * Returns the property value for the given parameter, or the given defaultValue if the properties value is null.
	 * 
	 * @param name
	 * @param defaultValue
	 */
	protected String getProp(String name, String defaultValue)
	{
		final String val = props.getProperty(name);
		return val == null ? defaultValue : val;
	}

	public Properties getTestProperties()
	{
		return props;
	}

	public int getAD_User_Login_ID()
	{
		return AD_User_Login_ID;
	}

	public void setAD_User_Login_ID(int aD_User_ID)
	{
		AD_User_Login_ID = aD_User_ID;
	}

	public int getAD_User_Normal_ID()
	{
		return AD_User_Normal_ID;
	}

	public void setAD_User_Normal_ID(int aD_User_ID)
	{
		AD_User_Normal_ID = aD_User_ID;
	}

	public int getAD_User_Admin_ID()
	{
		return AD_User_Admin_ID;
	}

	public void getAD_User_Admin_ID(int aD_User_ID)
	{
		AD_User_Admin_ID = aD_User_ID;
	}

	public TestConfig setAD_Client_ID(int aD_Client_ID)
	{
		AD_Client_ID = aD_Client_ID;
		return this;
	}

	public TestConfig setAD_Org_ID(int aD_Org_ID)
	{
		AD_Org_ID = aD_Org_ID;
		return this;
	}

	public TestConfig setAD_Role_ID(int aD_Role_ID)
	{
		AD_Role_ID = aD_Role_ID;
		return this;
	}

	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public int getAD_Role_ID()
	{
		return AD_Role_ID;
	}

	public Level getLogLevel()
	{
		return logLevel;
	}

	public int getC_DocType_CreditMemo_ID()
	{
		return this.C_DocType_CreditMemo_ID;
	}

	private void setC_DocType_CreditMemo_ID(int C_DocType_CreditMemo_ID)
	{
		this.C_DocType_CreditMemo_ID = C_DocType_CreditMemo_ID;
	}

	public TestConfig setLogLevel(Level logLevel)
	{
		Check.assumeNotNull(logLevel, "logLevel not null");
		this.logLevel = logLevel;
		return this;
	}

	public int getM_PriceListVersion_Base_ID()
	{
		return M_PriceListVersion_Base_ID;
	}

	public TestConfig setM_PriceListVersion_Base_ID(int m_PriceListVersion_Base_ID)
	{
		M_PriceListVersion_Base_ID = m_PriceListVersion_Base_ID;
		return this;
	}

	private boolean projectWithMetasPrepayOrder = true;

	public TestConfig setProjectWithMetasPrepayOrder(boolean projectWithMetasPrepayOrder)
	{
		this.projectWithMetasPrepayOrder = projectWithMetasPrepayOrder;
		return this;
	}

	public boolean isProjectWithMetasPrepayOrder()
	{
		return projectWithMetasPrepayOrder;
	}

	public String getC_BPartner_Value()
	{
		return C_BPartner_Value;
	}

	/**
	 * 
	 * 
	 * @param c_BPartner_Value
	 * @return
	 * @deprecated Some modules require "simple" bpValues such as 8-digit values to work (currently the only known example is the ESR module). If integration tests create BPartners and give them
	 *             explicit values -- instead of letting AD_Sequence do that job -- this might either lead to errors on such modules or to undesired collisions with existing, "real" BPartners. For
	 *             this reason, AIT developers are advised not to assign explicit BPartner Values at all, unless they know what they do.
	 */
	@Deprecated
	public TestConfig setC_BPartner_Value(String c_BPartner_Value)
	{
		C_BPartner_Value = c_BPartner_Value;
		return this;
	}

	/**
	 * Sets a name to be used when creating/retrieving a new C_BPartner
	 * 
	 * @return
	 */
	public String getC_BPartner_Name()
	{
		return C_BPartner_Name;
	}

	public TestConfig setC_BPartner_Name(String c_BPartner_Name)
	{
		C_BPartner_Name = c_BPartner_Name;
		return this;
	}

	public int getC_TaxCategory_Normal_ID()
	{
		return C_TaxCategory_Normal_ID;
	}

	public void setC_TaxCategory_Normal_ID(int c_TaxCategory_Normal_ID)
	{
		C_TaxCategory_Normal_ID = c_TaxCategory_Normal_ID;
	}

	public int getC_TaxCategory_Reduced_ID()
	{
		return C_TaxCategory_Reduced_ID;
	}

	public void setC_TaxCategory_Reduced_ID(int c_TaxCategory_Reduced_ID)
	{
		C_TaxCategory_Reduced_ID = c_TaxCategory_Reduced_ID;
	}

	public int getC_TaxCategory_TaxExempt_ID()
	{
		return C_TaxCategory_TaxExempt_ID;
	}

	public void setC_TaxCategory_TaxExempt_ID(int c_TaxCategory_TaxExempt_ID)
	{
		C_TaxCategory_TaxExempt_ID = c_TaxCategory_TaxExempt_ID;
	}

	final Map<String, Object> customParams = new HashMap<String, Object>();

	public void setCustomParam(String key, Object value)
	{
		customParams.put(key, value);
	}

	/**
	 * Only sets a custom parameter if is has not yet been set.
	 * 
	 * @param key
	 * @param value
	 */
	public void setCustomParamIfEmpty(String key, Object value)
	{
		if (!customParams.containsKey(key))
		{
			customParams.put(key, value);
		}
	}

	public Object getCustomParam(String key)
	{
		final Object value = customParams.get(key);
		Assert.assertNotNull("Missing value for custom key '" + key + "'", value);

		return value;
	}

	public boolean getCustomParamBool(String key)
	{
		return (Boolean)getCustomParam(key);
	}

	public int getCustomParamInt(String key)
	{
		return (Integer)getCustomParam(key);
	}

	public BigDecimal getCustomParamBD(String key)
	{
		return (BigDecimal)getCustomParam(key);
	}

	public String getCustomParamStr(String key)
	{
		return (String)getCustomParam(key);
	}

	@Override
	public String toString()
	{
		return "TestConfig [AD_Client_ID=" + AD_Client_ID + ", AD_Org_ID=" + AD_Org_ID + ", AD_Role_ID=" + AD_Role_ID
				+ ", AD_User_Login_ID=" + AD_User_Login_ID
				+ ", logLevel=" + logLevel
				+ ", M_PriceListVersion_Base_ID=" + M_PriceListVersion_Base_ID
				+ ", C_BPartner_Value=" + C_BPartner_Value + ", projectWithMetasPrepayOrder=" + projectWithMetasPrepayOrder
				+ ", customParams=" + customParams + "]";
	}

	private String testPrefix;

	public TestConfig setPrefix(String prefix)
	{
		this.testPrefix = prefix;
		return this;
	}

	public String getPrefix()
	{
		return testPrefix;
	}

	public int getPropertyAsInt(String name)
	{
		final int defaultValue = -1;
		if (props == null)
			return defaultValue;

		Object value = props.getProperty(name);
		if (value == null)
			return defaultValue;

		try
		{
			return Integer.parseInt(value.toString());
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	public static boolean getPropertyAsBoolean(Properties props, String name)
	{
		final boolean valueDefault = false;

		String value = props.getProperty(name);
		if (value == null)
			return valueDefault;

		value = value.trim();
		return Boolean.parseBoolean(value);
	}
}
