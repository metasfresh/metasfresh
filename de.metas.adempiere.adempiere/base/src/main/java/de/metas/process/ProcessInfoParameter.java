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
package de.metas.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

/**
 * Immutable Process Parameter
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoParameter.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro <li>FR [ 2430845 ] Add ProcessInfoParameter.getParameterAsBoolean method
 */
public final class ProcessInfoParameter implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4536416337960754407L;
	
	public static final ProcessInfoParameter of(final String parameterName, final int parameterValue)
	{
		final Integer parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}
	
	public static final ProcessInfoParameter of(final String parameterName, final String parameterValue)
	{
		final String parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static final ProcessInfoParameter of(final String parameterName, final BigDecimal parameterValue)
	{
		final BigDecimal parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static final ProcessInfoParameter of(final String parameterName, final java.util.Date parameterValue)
	{
		final java.util.Date parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static final ProcessInfoParameter of(final String parameterName, final boolean parameterValue)
	{
		final Boolean parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}
	
	public static final ProcessInfoParameter ofValueObject(final String parameterName, final Object parameterValue)
	{
		final Object parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	/**
	 * Construct Parameter
	 * 
	 * @param parameterName parameter name
	 * @param parameter parameter
	 * @param parameter_To to parameter
	 * @param info info
	 * @param info_To to info
	 */
	public ProcessInfoParameter(final String parameterName,
			final Object parameter,
			final Object parameter_To,
			final String info,
			final String info_To)
	{
		super();

		m_ParameterName = parameterName;
		m_Parameter = parameter;
		m_Parameter_To = parameter_To;
		m_Info = info == null ? "" : info;
		m_Info_To = info_To == null ? "" : info_To;
	}

	private final String m_ParameterName;
	private final Object m_Parameter;
	private final Object m_Parameter_To;
	private final String m_Info;
	private final String m_Info_To;

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		// From .. To
		if (m_Parameter_To != null || m_Info_To.length() > 0)
		{
			return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
					+ (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
					+ " (" + m_Info + ") - "
					+ m_Parameter_To
					+ (m_Parameter_To == null ? "" : "{" + m_Parameter_To.getClass().getName() + "}")
					+ " (" + m_Info_To + ")";
		}
		// Value
		return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
				+ (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
				+ " (" + m_Info + ")";
	}	// toString

	/**
	 * Method getParameterName
	 * 
	 * @return String
	 */
	public String getParameterName()
	{
		return m_ParameterName;
	}

	/**
	 * Method getInfo
	 * 
	 * @return String
	 */
	public String getInfo()
	{
		return m_Info;
	}

	/**
	 * Method getInfo_To
	 * 
	 * @return String
	 */
	public String getInfo_To()
	{
		return m_Info_To;
	}

	/**
	 * Method getParameter
	 * 
	 * @return Object
	 */
	public Object getParameter()
	{
		return m_Parameter;
	}

	/**
	 * Method getParameter_To
	 * 
	 * @return Object
	 */
	public Object getParameter_To()
	{
		return m_Parameter_To;
	}

	public String getParameterAsString()
	{
		return toString(m_Parameter);
	}

	public String getParameter_ToAsString()
	{
		return toString(m_Parameter_To);
	}

	private static final String toString(Object value)
	{
		if (value == null)
		{
			return null;
		}
		return value.toString();
	}

	/**
	 * Method getParameter as Int
	 * 
	 * @return Object
	 */
	public int getParameterAsInt()
	{
		return toInt(m_Parameter);
	}	// getParameterAsInt

	/**
	 * Method getParameter as Int
	 * 
	 * @return Object
	 */
	public int getParameter_ToAsInt()
	{
		return toInt(m_Parameter_To);
	}	// getParameter_ToAsInt

	private static final int toInt(final Object value)
	{
		if (value == null)
		{
			return 0;
		}
		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		final BigDecimal bd = new BigDecimal(value.toString());
		return bd.intValue();
	}

	/**
	 * Method getParameter as Boolean
	 * 
	 * @return boolean value
	 */
	public boolean getParameterAsBoolean()
	{
		final boolean defaultValue = false;
		return toBoolean(m_Parameter, defaultValue);
	}

	public Boolean getParameterAsBooleanOrNull()
	{
		final Boolean defaultValue = null;
		return toBoolean(m_Parameter, defaultValue);
	}

	/**
	 * Method getParameter as Boolean
	 * 
	 * @return boolean
	 */
	public boolean getParameter_ToAsBoolean()
	{
		final boolean defaultValue = false;
		return toBoolean(m_Parameter_To, defaultValue);
	}

	private static final Boolean toBoolean(final Object value, final Boolean defaultValue)
	{
		return DisplayType.toBoolean(value, defaultValue);
	}

	// metas
	public Timestamp getParameterAsTimestamp()
	{
		return toTimestamp(m_Parameter);
	}

	// metas
	public Timestamp getParameter_ToAsTimestamp()
	{
		return toTimestamp(m_Parameter_To);
	}

	private final Timestamp toTimestamp(final Object value)
	{
		if(value == null)
		{
			return null;
		}
		if(value instanceof Timestamp)
		{
			return (Timestamp)value;
		}
		else if (value instanceof java.util.Date)
		{
			return TimeUtil.asTimestamp((java.util.Date)value);
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert "+value+" to Timestamp");
		}
	}

	public BigDecimal getParameterAsBigDecimal()
	{
		return toBigDecimal(m_Parameter);
	}

	public BigDecimal getParameter_ToAsBigDecimal()
	{
		return toBigDecimal(m_Parameter_To);
	}

	private static final BigDecimal toBigDecimal(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((Integer)value);
		}
		else
		{
			final BigDecimal bd = new BigDecimal(value.toString());
			return bd;
		}
	}
}   // ProcessInfoParameter
