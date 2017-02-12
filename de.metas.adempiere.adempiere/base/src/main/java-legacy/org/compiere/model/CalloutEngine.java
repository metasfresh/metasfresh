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
import org.compiere.util.CCache;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Callout Engine.
 *
 * @author Jorg Janke
 * @version $Id: CalloutEngine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 2104021 ] CalloutEngine returns null if the exception has null message
 */
public class CalloutEngine implements Callout
{
	/** No error return value. Use this when you are returning from a callout without error */
	public static final String NO_ERROR = new String("");

	/**
	 * Constructor
	 */
	public CalloutEngine()
	{
		super();
	}

	private static final CCache<String, CalloutMethodExecutor> _calloutMethodExecutorsCache = new CCache<>("CalloutEngine.MethodExecutors", 100);

	/** Logger */
	protected final transient Logger log = LogManager.getLogger(getClass());
	private final ThreadLocal<ICalloutExecutor> currentCalloutExecutorHolder = new ThreadLocal<>();

	@Override
	public final void start(final String methodName, final ICalloutField calloutField)
	{
		final CalloutMethodExecutor methodExecutor = getCalloutMethodExecutor(getClass(), methodName);
		final ICalloutExecutor currentCalloutExecutor = calloutField.getCurrentCalloutExecutor();

		// Call Method
		try
		{
			currentCalloutExecutorHolder.set(currentCalloutExecutor);

			final String retValue = methodExecutor.execute(this, calloutField);

			if (retValue != null && retValue.length() > 0)
			{
				throw new CalloutExecutionException(retValue)
						.setField(calloutField)
						.setCalloutExecutor(currentCalloutExecutor);
			}
		}
		catch (final CalloutExecutionException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw CalloutExecutionException.of(e)
					.setField(calloutField)
					.setCalloutExecutor(currentCalloutExecutor);
		}
		finally
		{
			currentCalloutExecutorHolder.remove();
		}
	}	// start

	private static final GridField extractGridFieldOrNull(final ICalloutField calloutField)
	{
		if (calloutField instanceof GridField)
		{
			return (GridField)calloutField;
		}
		return null;
	}

	/**
	 * Conversion Rules.
	 * Convert a String
	 *
	 * @param methodName method name
	 * @param value the value
	 * @return converted String or Null if no method found
	 */
	@Override
	public final String convert(final String methodName, final String value)
	{
		log.info("convert: methodName={} - {}", methodName, value);

		//
		// Find Method
		final Method method = findMethod(getClass(), methodName);
		final int argLength = method.getParameterTypes().length;
		if (argLength != 1)
		{
			throw new IllegalArgumentException("Method " + methodName + " has invalid no of arguments: " + argLength);
		}

		// Call Method
		try
		{
			final Object result = method.invoke(this, value);
			return result == null ? null : result.toString();
		}
		catch (final Exception e)
		{
			log.error("convert: " + methodName, e);
			e.printStackTrace(System.err);
			return null;
		}
	}   // convert

	/**
	 * Find Method
	 *
	 * @param methodName method name
	 * @return method or null
	 */
	private static final Method findMethod(final Class<?> clazz, final String methodName)
	{
		if (methodName == null || methodName.isEmpty())
		{
			throw new IllegalArgumentException("No Method Name: '" + methodName + "'");
		}

		for (final Method method : clazz.getMethods())
		{
			if (methodName.equals(method.getName()))
			{
				return method;
			}
		}

		throw new IllegalArgumentException("Method not found: " + clazz.getName() + "." + methodName);
	}

	@FunctionalInterface
	private static interface CalloutMethodExecutor
	{
		String execute(final CalloutEngine instance, final ICalloutField calloutField) throws Exception;
	}

	private final CalloutMethodExecutor getCalloutMethodExecutor(final Class<?> clazz, final String methodName)
	{
		final String key = clazz.getName() + "." + methodName;
		return _calloutMethodExecutorsCache.getOrLoad(key, ()->createCalloutMethodExecutor(clazz, methodName));
	}

	private static final CalloutMethodExecutor createCalloutMethodExecutor(final Class<?> clazz, final String methodName)
	{
		final Method method = findMethod(clazz, methodName);
		final int argLength = method.getParameterTypes().length;

		if (argLength == 6)
		{
			return (instance, calloutField) -> {
				final GridField mField = extractGridFieldOrNull(calloutField);
				final GridTab mTab = mField == null ? null : mField.getGridTab();
				return (String)method.invoke(instance, calloutField.getCtx(), calloutField.getWindowNo(), mTab, mField, calloutField.getValue(), calloutField.getOldValue());
			};
		}
		else if (argLength == 5)
		{
			return (instance, calloutField) -> {
				final GridField mField = extractGridFieldOrNull(calloutField);
				final GridTab mTab = mField == null ? null : mField.getGridTab();
				return (String)method.invoke(instance, calloutField.getCtx(), calloutField.getWindowNo(), mTab, mField, calloutField.getValue());
			};
		}
		else if (argLength == 1)
		{
			return (instance, calloutField) -> {
				return (String)method.invoke(instance, calloutField);
			};
		}
		else
		{
			throw new IllegalArgumentException("Method " + methodName + " has invalid no of arguments: " + argLength);
		}

	}

	/*************************************************************************/

	/**
	 * Is the current callout being called in the middle of
	 * another callout doing her works.
	 * Callout can use GridTab.getActiveCalloutInstance() method
	 * to find out callout for which field is running.
	 *
	 * @return true if active
	 */
	protected final boolean isCalloutActive()
	{
		final ICalloutExecutor currentCalloutExecutor = this.currentCalloutExecutorHolder.get();

		if (currentCalloutExecutor == null)
		{
			return false;
		}

		final int activeCalloutsCount = currentCalloutExecutor.getActiveCalloutInstancesCount();
		// greater than 1 instead of 0 to discount this callout instance
		if (activeCalloutsCount <= 1)
		{
			return false;
		}

		return true;
	}	// isCalloutActive

	/**
	 * Set Account Date Value.
	 * org.compiere.model.CalloutEngine.dateAcct
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String dateAcct(final ICalloutField field)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final Object value = field.getValue();
		if (value == null || !(value instanceof java.util.Date))
		{
			return NO_ERROR;
		}

		final java.util.Date valueDate = (java.util.Date)value;

		final Object model = field.getModel(Object.class);
		InterfaceWrapperHelper.setValue(model, "DateAcct", TimeUtil.asTimestamp(valueDate));

		return NO_ERROR;
	}	// dateAcct

	/**
	 * Rate - set Multiply Rate from Divide Rate and vice versa
	 * org.compiere.model.CalloutEngine.rate
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	@Deprecated
	public String rate(final ICalloutField field)
	{
		// NOTE: atm this method is used only by UOMConversions. When we will provide an implementation for that, we can get rid of this shit.

		final Object value = field.getValue();

		if (isCalloutActive() || value == null)
		{
			return NO_ERROR;
		}

		final BigDecimal rate1 = (BigDecimal)value;
		BigDecimal rate2 = BigDecimal.ZERO;
		final BigDecimal one = BigDecimal.ONE;

		if (rate1.signum() != 0)
		{
			rate2 = one.divide(rate1, 12, BigDecimal.ROUND_HALF_UP);
		}

		//
		final String columnName = field.getColumnName();
		final Object model = field.getModel(Object.class);
		if ("MultiplyRate".equals(columnName))
		{
			InterfaceWrapperHelper.setValue(model, "DivideRate", rate2);
		}
		else
		{
			InterfaceWrapperHelper.setValue(model, "MultiplyRate", rate2);
		}
		log.info("{} = {} => ", columnName, rate1, rate2);

		return NO_ERROR;
	}	// rate
}	// CalloutEngine
