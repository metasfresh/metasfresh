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

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Callout Engine.
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutEngine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *  
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *  		<li>BF [ 2104021 ] CalloutEngine returns null if the exception has null message
 */
public class CalloutEngine implements Callout
{
	/** No error return value. Use this when you are returning from a callout without error */
	public static final String NO_ERROR = "";
	
	/**
	 *	Constructor
	 */
	public CalloutEngine()
	{
		super();
	}

	/** Logger					*/
	protected Logger		log = LogManager.getLogger(getClass());
	private ICalloutExecutor currentCalloutExecutor = null;

	@Override
	public final void start(final String methodName, final ICalloutField calloutField)
	{
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException ("No Method Name");

		final GridField mField = extractGridFieldOrNull(calloutField);
		final GridTab mTab = mField == null ? null : mField.getGridTab();
		
		//	Find Method
		final Method method = getMethod(methodName);
		if (method == null)
			throw new IllegalArgumentException("Method not found: " + getClass().getName() + "." + methodName);
		int argLength = method.getParameterTypes().length;

		//	Call Method
		try
		{
			this.currentCalloutExecutor = calloutField.getCurrentCalloutExecutor();
			
			final Object[] args;
			if (argLength == 6)
			{
				args = new Object[] {calloutField.getCtx(), calloutField.getWindowNo(), mTab, mField, calloutField.getValue(), calloutField.getOldValue()};
			}
			else if (argLength == 5)
			{
				args = new Object[] {calloutField.getCtx(), calloutField.getWindowNo(), mTab, mField, calloutField.getValue()};
			}
			else if(argLength == 1)
			{
				args = new Object[]{calloutField};
			}
			else
			{
				throw new IllegalArgumentException ("Method " + methodName + " has invalid no of arguments: " + argLength);
			}
			
			final String retValue = (String)method.invoke(this, args);
			if(retValue != null && retValue.length() > 0)
			{
				throw new CalloutExecutionException(retValue);
			}
		}
		catch (Exception e)
		{
			throw CalloutExecutionException.of(e);
		}
		finally
		{
			currentCalloutExecutor = null;
		}
	}	//	start
	
	private final GridField extractGridFieldOrNull(final ICalloutField calloutField)
	{
		if (calloutField instanceof GridField)
		{
			return (GridField)calloutField;
		}
		return null;
	}
	
	/**
	 *	Conversion Rules.
	 *	Convert a String
	 *
	 *	@param methodName   method name
	 *  @param value    the value
	 *	@return converted String or Null if no method found
	 */
	@Override
	public String convert (String methodName, String value)
	{
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException ("No Method Name");
		//
		String retValue = null;
		StringBuffer msg = new StringBuffer(methodName).append(" - ").append(value);
		log.info(msg.toString());
		//
		//	Find Method
		Method method = getMethod(methodName);
		if (method == null)
			throw new IllegalArgumentException ("Method not found: " + methodName);
		int argLength = method.getParameterTypes().length;
		if (argLength != 1)
			throw new IllegalArgumentException ("Method " + methodName 
				+ " has invalid no of arguments: " + argLength);

		//	Call Method
		try
		{
			Object[] args = new Object[] {value};
			retValue = (String)method.invoke(this, args);
		}
		catch (Exception e)
		{
			log.error("convert: " + methodName, e);
			e.printStackTrace(System.err);
		}
		return retValue;
	}   //  convert
	
	/**
	 * 	Get Method
	 *	@param methodName method name
	 *	@return method or null
	 */
	private final Method getMethod(final String methodName)
	{
		Method[] allMethods = getClass().getMethods();
		for (int i = 0; i < allMethods.length; i++)
		{
			if (methodName.equals(allMethods[i].getName()))
				return allMethods[i];
		}
		return null;
	}	//	getMethod

	/*************************************************************************/
	
	//private static boolean s_calloutActive = false;
	
	/**
	 * 	Is the current callout being called in the middle of 
     *  another callout doing her works.
     *  Callout can use GridTab.getActiveCalloutInstance() method
     *  to find out callout for which field is running.
	 *	@return true if active
	 */
	protected boolean isCalloutActive()
	{
		if(currentCalloutExecutor == null)
		{
			return false;
		}
		
		final int activeCalloutsCount = currentCalloutExecutor.getActiveCalloutInstances().size();
		// greater than 1 instead of 0 to discount this callout instance
		if (activeCalloutsCount <= 1)
		{
			return false;
		}
		
		return true;
	}	//	isCalloutActive

	/**
	 * 	Set Callout (in)active.
     *  Depreciated as the implementation is not thread safe and
     *  fragile - break other callout if developer forget to call
     *  setCalloutActive(false) after calling setCalloutActive(true).
	 *  @deprecated
	 *	@param active active
	 */
	@Deprecated
	protected static void setCalloutActive (boolean active)
	{
		;
	}	//	setCalloutActive
	
	/**
	 *  Set Account Date Value.
	 * 	org.compiere.model.CalloutEngine.dateAcct
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
//	public String dateAcct (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	public String dateAcct (final ICalloutField field)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return NO_ERROR;
		
		final Object value = field.getValue();
		if (value == null || !(value instanceof java.util.Date))
			return NO_ERROR;
		
		final java.util.Date valueDate = (java.util.Date)value;
		
		final Object model = field.getModel(Object.class);
		InterfaceWrapperHelper.setValue(model, "DateAcct", TimeUtil.asTimestamp(valueDate));
		
		return NO_ERROR;
	}	//	dateAcct

	/**
	 *	Rate - set Multiply Rate from Divide Rate and vice versa
	 *	org.compiere.model.CalloutEngine.rate
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	@Deprecated
	public String rate (final ICalloutField field)
	{
		// NOTE: atm this method is used only by UOMConversions. When we will provide an implementation for that, we can get rid of this shit.
		
		final Object value = field.getValue();
		
		if (isCalloutActive() || value == null)		//	assuming it is Conversion_Rate
			return NO_ERROR;

		BigDecimal rate1 = (BigDecimal)value;
		BigDecimal rate2 = Env.ZERO;
		BigDecimal one = new BigDecimal(1.0);

		if (rate1.doubleValue() != 0.0)	//	no divide by zero
			rate2 = one.divide(rate1, 12, BigDecimal.ROUND_HALF_UP);
		
		//
		final String columnName = field.getColumnName();
		final Object model = field.getModel(Object.class);
		if (columnName.equals("MultiplyRate"))
		{
			InterfaceWrapperHelper.setValue(model, "DivideRate", rate2);
		}
		else
		{
			InterfaceWrapperHelper.setValue(model, "MultiplyRate", rate2);
		}
		log.info(columnName + "=" + rate1 + " => " + rate2);
		
		return NO_ERROR;
	}	//	rate
}	//	CalloutEngine
