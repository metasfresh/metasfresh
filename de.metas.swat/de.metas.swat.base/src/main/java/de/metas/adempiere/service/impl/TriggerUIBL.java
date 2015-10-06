/**
 * 
 */
package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.I_AD_TriggerUI;
import org.adempiere.model.I_AD_TriggerUI_Action;
import org.adempiere.model.I_AD_TriggerUI_Criteria;
import org.adempiere.model.POWrapper;
import org.adempiere.model.X_AD_TriggerUI_Action;
import org.adempiere.model.X_AD_TriggerUI_Criteria;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

import de.metas.adempiere.service.ITriggerUIBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

/**
 * @author tsa
 * 
 */
public class TriggerUIBL implements ITriggerUIBL
{
	private final CLogger log = CLogger.getCLogger(getClass());

	public class TriggerUICalloutInstance implements ICalloutInstance
	{
		private final String id;
		private final ICalloutField field;

		public TriggerUICalloutInstance(ICalloutField field)
		{
			super();
			this.field = field;
			this.id = getClass().getSimpleName() + "-" + field.getAD_Table_ID() + "-" + field.getAD_Column_ID();
		}

		@Override
		public String getId()
		{
			return id;
		}

		@Override
		public void execute(final ICalloutExecutor executor, final ICalloutField field)
		{
			final GridField gridField = (GridField)field; // FIXME: find a better way to get rid of direct GridField access
			trigger(gridField);
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "["
					+ "field=" + field
					+ "]";
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(id)
					.append(field)
					.toHashcode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			final TriggerUICalloutInstance other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}

			return new EqualsBuilder()
					.append(this.id, other.id)
					.append(this.field, other.field)
					.isEqual();
		}
	}

	public static class TriggerUICalloutProvider implements ICalloutProvider
	{
		@Override
		public List<ICalloutInstance> getCallouts(final ICalloutField field)
		{
			final ITriggerUIBL triggerUIService = Services.get(ITriggerUIBL.class);
			final ICalloutInstance callout = triggerUIService.getCalloutInstance(field);
			return Collections.singletonList(callout);
		}
	}

	private static class EvalCtx
	{
		private final Properties ctx;
		private final int windowNo;
		private final int tabNo;

		public EvalCtx(Properties ctx, int windowNo, int tabNo)
		{
			this.ctx = ctx;
			this.windowNo = windowNo;
			this.tabNo = tabNo;
		}

		public Properties getCtx()
		{
			return this.ctx;
		}

		public int getWindowNo()
		{
			return this.windowNo;
		}

		public int getTabNo()
		{
			return this.tabNo;
		}
	}

	public static class ModelValidator implements org.compiere.model.ModelValidator
	{
		private int m_AD_Client_ID = -1;

		@Override
		public int getAD_Client_ID()
		{
			return m_AD_Client_ID;
		}

		@Override
		public void initialize(ModelValidationEngine engine, MClient client)
		{
			if (client != null)
			{
				m_AD_Client_ID = client.getAD_Client_ID();
			}

			Services.get(ICalloutFactory.class).registerCalloutProvider(new TriggerUICalloutProvider());

			engine.addModelChange(I_AD_TriggerUI.Table_Name, this);
			engine.addModelChange(I_AD_TriggerUI_Criteria.Table_Name, this);
			engine.addModelChange(I_AD_TriggerUI_Action.Table_Name, this);
		}

		@Override
		public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
		{
			return null;
		}

		@Override
		public String modelChange(PO po, int type) throws Exception
		{
			if (I_AD_TriggerUI.Table_Name.equals(po.get_TableName()) && TYPE_BEFORE_DELETE == type)
			{
				ITriggerUIBL triggerService = Services.get(ITriggerUIBL.class);
				triggerService.removeDependencies(POWrapper.create(po, I_AD_TriggerUI.class));
			}
			if (I_AD_TriggerUI_Criteria.Table_Name.equals(po.get_TableName())
					&& (TYPE_BEFORE_NEW == type || TYPE_BEFORE_CHANGE == type))
			{
				ITriggerUIBL triggerService = Services.get(ITriggerUIBL.class);
				triggerService.validate(POWrapper.create(po, I_AD_TriggerUI_Criteria.class));
			}
			if (I_AD_TriggerUI_Action.Table_Name.equals(po.get_TableName())
					&& (TYPE_BEFORE_NEW == type || TYPE_BEFORE_CHANGE == type))
			{
				ITriggerUIBL triggerService = Services.get(ITriggerUIBL.class);
				triggerService.validate(POWrapper.create(po, I_AD_TriggerUI_Action.class));
			}

			return null;
		}

		@Override
		public String docValidate(PO po, int timing)
		{
			// nothing
			return null;
		}
	}

	@Override
	public List<I_AD_TriggerUI> retrieveForGridField(GridField gridField)
	{
		List<I_AD_TriggerUI> triggers = new ArrayList<I_AD_TriggerUI>();
		for (I_AD_TriggerUI trigger : retrieveForGridTab(gridField.getGridTab()))
		{
			for (I_AD_TriggerUI_Criteria criteria : retrieveCriterias(trigger))
			{
				if (X_AD_TriggerUI_Criteria.TYPE_FieldValue.equals(criteria.getType()))
				{
					if (criteria.getAD_Column_ID() == gridField.getAD_Column_ID())
					{
						triggers.add(trigger);
					}
				}
			}
		}

		return triggers;
	}

	public List<I_AD_TriggerUI> retrieveForGridTab(GridTab gridTab)
	{
		final Properties ctx = Env.getCtx();
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_Table_ID = gridTab.getAD_Table_ID();
		final int AD_Tab_ID = gridTab.getAD_Tab_ID();
		return retrieveForGridTab0(ctx, AD_Table_ID, AD_Tab_ID, AD_Client_ID, AD_Org_ID);
	}

	@Cached
	/* package */ List<I_AD_TriggerUI> retrieveForGridTab0(
			@CacheCtx Properties ctx,
			int AD_Table_ID, int AD_Tab_ID, int AD_Client_ID, int AD_Org_ID)
	{
		final String whereClause = I_AD_TriggerUI.COLUMNNAME_AD_Table_ID + "=?"
				+ " AND (" + I_AD_TriggerUI.COLUMNNAME_AD_Tab_ID + "=? OR " + I_AD_TriggerUI.COLUMNNAME_AD_Tab_ID + " IS NULL)"
				+ " AND " + I_AD_TriggerUI.COLUMNNAME_AD_Client_ID + " IN (0,?)"
				+ " AND " + I_AD_TriggerUI.COLUMNNAME_AD_Org_ID + " IN (0,?)";
		List<I_AD_TriggerUI> list = new Query(ctx, I_AD_TriggerUI.Table_Name, whereClause, null)
				.setParameters(AD_Table_ID, AD_Tab_ID, AD_Client_ID, AD_Org_ID)
				.setOnlyActiveRecords(true)
				.list(I_AD_TriggerUI.class);
		return Collections.unmodifiableList(list);
	}

	public List<I_AD_TriggerUI_Criteria> retrieveCriterias(I_AD_TriggerUI trigger)
	{
		final Properties ctx = POWrapper.getCtx(trigger);
		final String trxName = POWrapper.getTrxName(trigger);
		final int AD_TriggerUI_ID = trigger.getAD_TriggerUI_ID();
		return retrieveCriterias(ctx, AD_TriggerUI_ID, trxName);
	}

	@Cached
	public List<I_AD_TriggerUI_Criteria> retrieveCriterias(
			@CacheCtx Properties ctx,
			int AD_TriggerUI_ID,
			@CacheTrx String trxName)
	{
		final String whereClause = I_AD_TriggerUI_Criteria.COLUMNNAME_AD_TriggerUI_ID + "=?";
		List<I_AD_TriggerUI_Criteria> list = new Query(ctx, I_AD_TriggerUI_Criteria.Table_Name, whereClause, trxName)
				.setParameters(AD_TriggerUI_ID)
				.setOnlyActiveRecords(true)
				.list(I_AD_TriggerUI_Criteria.class);

		return Collections.unmodifiableList(list);
	}

	public List<I_AD_TriggerUI_Action> retrieveActions(I_AD_TriggerUI trigger)
	{
		Properties ctx = POWrapper.getCtx(trigger);
		String trxName = POWrapper.getTrxName(trigger);
		final int AD_TriggerUI_ID = trigger.getAD_TriggerUI_ID();
		return retrieveActions(ctx, AD_TriggerUI_ID, trxName);
	}

	@Cached
	public List<I_AD_TriggerUI_Action> retrieveActions(
			@CacheCtx Properties ctx,
			int AD_TriggerUI_ID,
			@CacheTrx String trxName)
	{
		final String whereClause = I_AD_TriggerUI_Action.COLUMNNAME_AD_TriggerUI_ID + "=?";
		List<I_AD_TriggerUI_Action> list = new Query(ctx, I_AD_TriggerUI_Action.Table_Name, whereClause, trxName)
				.setParameters(AD_TriggerUI_ID)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_AD_TriggerUI_Action.COLUMNNAME_SeqNo
						+ "," + I_AD_TriggerUI_Action.COLUMNNAME_AD_TriggerUI_ID)
				.list(I_AD_TriggerUI_Action.class);

		return Collections.unmodifiableList(list);
	}

	@Override
	public void trigger(GridField gridField)
	{
		final GridTab gridTab = gridField.getGridTab();
		for (I_AD_TriggerUI trigger : retrieveForGridField(gridField))
		{
			if (match(gridTab, trigger))
			{
				applyActions(gridTab, trigger);
			}
		}
	}

	public boolean match(GridTab tab, I_AD_TriggerUI trigger)
	{
		boolean matched = false;
		for (I_AD_TriggerUI_Criteria criteria : retrieveCriterias(trigger))
		{
			boolean m = match(tab, criteria);
			if (!m && criteria.isMandatory())
				return false;
			if (m)
				matched = true;
		}
		return matched;
	}

	private boolean match(GridTab tab, I_AD_TriggerUI_Criteria criteria)
	{
		final EvalCtx evalCtx = new EvalCtx(Env.getCtx(), tab.getWindowNo(), tab.getTabNo());
		if (X_AD_TriggerUI_Criteria.TYPE_FieldValue.equals(criteria.getType()))
		{
			final GridField field = getGridFieldByColumnId(tab, criteria.getAD_Column_ID());
			final Object fieldValue = field.getValue();
			final int displayType = field.getDisplayType();
			if (match(criteria, fieldValue, displayType, evalCtx))
			{
				return true;
			}
		}
		else if (X_AD_TriggerUI_Criteria.TYPE_ContextValue.equals(criteria.getType()))
		{
			final Object fieldValue = getContext(evalCtx, criteria.getAttributeName());
			final int displayType = DisplayType.String;
			if (match(criteria, fieldValue, displayType, evalCtx))
			{
				return true;
			}
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@=" + criteria.getType());
		}
		return false;
	}

	private boolean match(I_AD_TriggerUI_Criteria criteria, Object fieldValue, int displayType, EvalCtx evalCtx)
	{
		final String op = criteria.getOperation();
		final Object value;
		if (criteria.isNullFieldValue())
			value = null;
		else
			value = convertValueToGridFieldType(criteria.getFieldValue(), criteria.getFieldValueFormat(), displayType, evalCtx);

		if (X_AD_TriggerUI_Criteria.OPERATION_Eq.equals(op))
		{
			return equals(fieldValue, value);
		}
		else if (X_AD_TriggerUI_Criteria.OPERATION_NotEq.equals(op))
		{
			return !equals(fieldValue, value);
		}
		else if (X_AD_TriggerUI_Criteria.OPERATION_GtEq.equals(op))
		{
			return compare(fieldValue, value) >= 0;
		}
		else if (X_AD_TriggerUI_Criteria.OPERATION_Gt.equals(op))
		{
			return compare(fieldValue, value) > 0;
		}
		else if (X_AD_TriggerUI_Criteria.OPERATION_Le.equals(op))
		{
			return compare(fieldValue, value) < 0;
		}
		else if (X_AD_TriggerUI_Criteria.OPERATION_LeEq.equals(op))
		{
			return compare(fieldValue, value) <= 0;
		}
		else
		{
			// public static final String OPERATION_Like = "~~";
			// public static final String OPERATION_X = "AB";
			// public static final String OPERATION_Sql = "SQ";
			// public static final String OPERATION_NotEq = "!=";
			throw new AdempiereException("@NotSupported@ @Operation@=" + op);
		}
	}

	private boolean equals(Object value1, Object value2)
	{
		if (value1 == value2)
			return true;
		if (value1 == null || value2 == null)
			return false;
		return value1.equals(value2);
	}

	@SuppressWarnings("unchecked")
	private int compare(Object value1, Object value2)
	{
		if (equals(value1, value2))
			return 0;

		if (!(value1 instanceof Comparable<?>))
		{
			throw new IllegalArgumentException("Value1 is not comparable: " + value1 + " (" + value1.getClass() + ")");
		}
		if (!(value2 instanceof Comparable<?>))
		{
			throw new IllegalArgumentException("Value2 is not comparable: " + value2 + " (" + value2.getClass() + ")");
		}

		@SuppressWarnings("rawtypes")
		Comparable c1 = (Comparable)value1;

		@SuppressWarnings("rawtypes")
		Comparable c2 = (Comparable)value2;

		return c1.compareTo(c2);
	}

	private GridField getGridFieldByColumnId(GridTab tab, int AD_Column_ID)
	{
		for (GridField field : tab.getFields())
		{
			if (field.getAD_Column_ID() == AD_Column_ID)
				return field;
		}
		return null;
	}

	public void applyActions(GridTab tab, I_AD_TriggerUI trigger)
	{
		for (I_AD_TriggerUI_Action action : retrieveActions(trigger))
		{
			applyAction(tab, action);
		}
	}

	private void applyAction(GridTab tab, I_AD_TriggerUI_Action action)
	{
		if (X_AD_TriggerUI_Action.TYPE_SetFieldValue.equals(action.getType()))
		{
			GridField field = getGridFieldByColumnId(tab, action.getAD_Column_ID());
			final Object value;
			if (action.isNullFieldValue())
			{
				value = null;
			}
			else
			{
				final int displayType = field.getDisplayType();
				final EvalCtx evalCtx = new EvalCtx(Env.getCtx(), tab.getWindowNo(), tab.getTabNo());
				value = convertValueToGridFieldType(action.getFieldValue(), action.getFieldValueFormat(), displayType, evalCtx);
			}
			tab.setValue(field, value);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@=" + action.getType());
		}
	}

	private Object convertValueToGridFieldType(String valueStr, String format, int displayType, EvalCtx evalCtx)
	{
		// final int displayType = field.getDisplayType();
		return convertValueToType(valueStr, format, displayType, evalCtx);
	}

	private Object convertValueToType(String valueStr, String format, int displayType, EvalCtx evalCtx)
			throws AdempiereException
	{
		final Class<?> clazz = DisplayType.getClass(displayType, true);

		// Check and resolve context variable
		final String variableName = getContextVariableName(valueStr);
		if (variableName != null)
		{
			if (evalCtx == null)
			{
				log.fine("Context field not specified. Ignoring context variable " + variableName);
				return null;
			}
			return getContextValueAsType(evalCtx, variableName, displayType);
		}

		if (String.class.equals(clazz))
		{
			return valueStr;
		}
		else if (Integer.class.equals(clazz))
		{
			if (valueStr == null)
				return 0;
			return parseBigDecimal(valueStr, format).intValueExact();
		}
		else if (BigDecimal.class.equals(clazz))
		{
			if (valueStr == null)
				return Env.ZERO;
			return parseBigDecimal(valueStr, format);
		}
		else if (Boolean.class.equals(clazz))
		{
			if (valueStr == null)
				return null;
			return parseBoolean(valueStr);
		}
		else if (java.sql.Timestamp.class.equals(clazz))
		{
			return parseTimestamp(valueStr, format);
		}
		else
		{
			throw new IllegalStateException("Value class not supported: " + clazz);
		}
	}

	private Object getContextValueAsType(EvalCtx evalCtx, String context, int displayType)
	{
		final Class<?> clazz = DisplayType.getClass(displayType, true);
		final String valueStr = getContext(evalCtx, context);

		if (String.class.equals(clazz))
		{
			return valueStr;
		}
		else if (Integer.class.equals(clazz))
		{
			if (Check.isEmpty(valueStr))
				return 0;
			return new BigDecimal(valueStr).intValueExact();
		}
		else if (BigDecimal.class.equals(clazz))
		{
			if (Check.isEmpty(valueStr))
				return Env.ZERO;
			return new BigDecimal(valueStr);
		}
		else if (Boolean.class.equals(clazz))
		{
			if (Check.isEmpty(valueStr))
				return null;
			return parseBoolean(valueStr);
		}
		else if (java.sql.Timestamp.class.equals(clazz))
		{
			return Env.parseTimestamp(valueStr);
		}
		else
		{
			throw new IllegalStateException("Value class not supported: " + clazz);
		}

	}

	private String getContextVariableName(String valueStr)
	{
		if (valueStr == null)
			return null;
		valueStr = valueStr.trim();
		if (valueStr.startsWith("@") && valueStr.endsWith("@"))
		{
			return valueStr.substring(1, valueStr.length() - 1);
		}
		return null;
	}

	private BigDecimal parseBigDecimal(String valueStr, String format)
	{
		if (Check.isEmpty(format, true))
		{
			return new BigDecimal(valueStr);
		}
		else
		{
			DecimalFormat df = new DecimalFormat(format);
			df.setParseBigDecimal(true);
			try
			{
				return (BigDecimal)df.parse(valueStr);
			}
			catch (ParseException e)
			{
				throw new AdempiereException(e);
			}
		}
	}

	private java.sql.Timestamp parseTimestamp(String valueStr, String format)
	{
		DateFormat df = new SimpleDateFormat(format == null ? "yyyy.MM.dd" : format);
		try
		{
			Date date = df.parse(valueStr);
			return new java.sql.Timestamp(date.getTime());
		}
		catch (ParseException e)
		{
			throw new AdempiereException(e);
		}
	}

	private Boolean parseBoolean(String valueStr)
	{
		if ("Y".equalsIgnoreCase(valueStr) || "true".equalsIgnoreCase(valueStr))
			return true;
		if ("N".equalsIgnoreCase(valueStr) || "false".equalsIgnoreCase(valueStr))
			return false;
		throw new AdempiereException("Invalid boolean value " + valueStr + ". Please use Y/N/true/false");
	}

	@Override
	public ICalloutInstance getCalloutInstance(final ICalloutField field)
	{
		return new TriggerUICalloutInstance(field);
	}

	@Override
	public org.compiere.model.ModelValidator createModelValidator()
	{
		return new ModelValidator();
	}

	@Override
	public void removeDependencies(I_AD_TriggerUI trigger)
	{
		for (I_AD_TriggerUI_Criteria criteria : retrieveCriterias(trigger))
		{
			POWrapper.delete(criteria);
		}
		for (I_AD_TriggerUI_Action action : retrieveActions(trigger))
		{
			POWrapper.delete(action);
		}
	}

	@Override
	public void validate(I_AD_TriggerUI_Criteria criteria)
	{
		if (X_AD_TriggerUI_Criteria.TYPE_FieldValue.equals(criteria.getType()))
		{
			if (criteria.getAD_Column_ID() <= 0)
				throw new FillMandatoryException(I_AD_TriggerUI_Criteria.COLUMNNAME_AD_Column_ID);
			if (!criteria.isNullFieldValue())
			{
				final String valueStr = criteria.getFieldValue();
				final int displayType = criteria.getAD_Column().getAD_Reference_ID();
				final String format = criteria.getFieldValueFormat();
				// this will throw an exception is value has wrong format
				convertValueToType(valueStr, format, displayType, null);
			}
		}
		else if (X_AD_TriggerUI_Criteria.TYPE_ContextValue.equals(criteria.getType()))
		{
			// nothing to do
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@:" + criteria.getType());
		}
	}

	@Override
	public void validate(I_AD_TriggerUI_Action action)
	{
		if (X_AD_TriggerUI_Action.TYPE_SetFieldValue.equals(action.getType()))
		{
			if (action.getAD_Column_ID() <= 0)
				throw new FillMandatoryException(I_AD_TriggerUI_Action.COLUMNNAME_AD_Column_ID);
			if (!action.isNullFieldValue())
			{
				final String valueStr = action.getFieldValue();
				final int displayType = action.getAD_Column().getAD_Reference_ID();
				final String format = action.getFieldValueFormat();
				// this will throw an exception is value has wrong format:
				convertValueToType(valueStr, format, displayType, null);
			}
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@:" + action.getType());
		}
	}

	private String getContext(EvalCtx evalCtx, String context)
	{
		return Env.getContext(evalCtx.getCtx(), evalCtx.getWindowNo(), evalCtx.getTabNo(), context);
	}
}
