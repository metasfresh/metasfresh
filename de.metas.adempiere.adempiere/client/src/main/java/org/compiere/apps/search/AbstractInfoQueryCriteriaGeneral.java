package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.Lookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CEditor;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public abstract class AbstractInfoQueryCriteriaGeneral implements IInfoQueryCriteria
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private IInfoSimple _parent = null;
	private I_AD_InfoColumn _infoColumn = null;
	protected String label = null;
	protected CEditor editor = null;
	protected CEditor editor2 = null;

	@Override
	public void init(final IInfoSimple parent, final I_AD_InfoColumn infoColumn, final String searchText)
	{
		_parent = parent;
		_infoColumn = infoColumn;
		label = Services.get(IMsgBL.class).translate(parent.getCtx(), infoColumn.getAD_Element().getColumnName());
		initComponents(searchText);
		initDefaults();
	}

	protected abstract CEditor createCheckboxEditor(String columnName, String label);

	protected abstract CEditor createLookupEditor(String columnName, Lookup lookup, Object defaultValue);

	protected abstract CEditor createStringEditor(String columnName, String defaultValue);

	protected abstract CEditor createNumberEditor(String columnName, String title, int displayType);

	protected abstract CEditor createDateEditor(String columnName, String title, int displayType);

	protected void initComponents(final String searchText)
	{
		// TODO: merge here the changes from org.compiere.apps.search.InfoQueryCriteriaGeneral#initComponents, and drop that method
		final IInfoSimple parent = getParent();
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		final Properties ctx = parent.getCtx();
		final int windowNo = parent.getWindowNo();
		final int displayType = infoColumn.getAD_Reference_ID();
		final String columnName = infoColumn.getAD_Element().getColumnName();

		if (DisplayType.YesNo == displayType)
		{
			editor = createCheckboxEditor(columnName, label);
			label = null;
		}
		else if (DisplayType.List == displayType
				|| DisplayType.Table == displayType
				|| DisplayType.TableDir == displayType
				|| DisplayType.Search == displayType)
		{

			final MLookup lookup;
			try
			{
				lookup = MLookupFactory.get(ctx,
						windowNo,
						0, // Column_ID,
						infoColumn.getAD_Reference_ID(),
						columnName,
						infoColumn.getAD_Reference_Value_ID(),
						false, // IsParent
						infoColumn.getAD_Val_Rule_ID()
						);
			}
			catch (final Exception e)
			{
				throw new AdempiereException(e);
			}

			// default value
			final Object defaultValue = parent.getContextVariable(columnName);

			editor = createLookupEditor(columnName, lookup, defaultValue);
			//
			if (infoColumn.isRange())
			{
				editor2 = createLookupEditor(columnName + "_2", lookup, null);
			}
		}
		else if (DisplayType.String == displayType)
		{
			editor = createStringEditor(columnName, null);
			//
			if (infoColumn.isRange())
			{
				editor2 = createStringEditor(columnName + "_2", null);
			}
		}
		// Number
		else if (DisplayType.isNumeric(displayType))
		{
			editor = createNumberEditor(columnName, infoColumn.getName(), displayType);
			//
			if (infoColumn.isRange())
			{
				editor2 = createNumberEditor(columnName + "_2", infoColumn.getName(), displayType);
			}
		}
		// Date
		else if (DisplayType.isDate(displayType))
		{
			editor = createDateEditor(columnName, infoColumn.getName(), displayType);
			//
			if (infoColumn.isRange())
			{
				editor2 = createDateEditor(columnName + "_2", infoColumn.getName(), displayType);
			}
		}
		else
		{
			throw new AdempiereException("Not supported reference: " + displayType);
		}
	}

	private void initDefaults()
	{
		try
		{
			initDefaults0();
		}
		catch (final Exception e)
		{
			// Exception was thrown mainly because DefaultValue expression could not be evaluated.
			// It's not such a big deal, so a lower logging level shall be fine
			logger.info(e.getLocalizedMessage(), e);
		}
	}

	private void initDefaults0()
	{
		//
		// If there is no editor, there is no point to compute default value
		if (editor == null)
		{
			return;
		}

		//
		// Get DefaultValue expression string
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();
		final String defaultValueExpressionStr = infoColumn.getDefaultValue();
		if (Check.isEmpty(defaultValueExpressionStr, true))
		{
			return;
		}

		//
		// Evaluate DefaultValue expression in parent's context
		final IInfoSimple parent = getParent();
		final Evaluatee evalCtx = Evaluatees.ofCtx(parent.getCtx(), parent.getWindowNo(), false); // onlyWindow=false
		final IStringExpression defaultValueExpression = Services.get(IExpressionFactory.class).compile(defaultValueExpressionStr, IStringExpression.class);
		final String defaultValueStr = defaultValueExpression.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);
		if (defaultValueExpression.isNoResult(defaultValueStr))
		{
			// expression could not be evaluated
			return;
		}

		//
		// Convert evaluated expression string to it's DisplayType java class
		final String columnName = infoColumn.getColumnName();
		final int displayType = infoColumn.getAD_Reference_ID();
		final Object defaultValue = DisplayType.convertToDisplayType(defaultValueStr, columnName, displayType);

		//
		// Set value to editor
		editor.setValue(defaultValue);
	}

	@Override
	public int getParameterCount()
	{
		return 1;
	}

	@Override
	public String getLabel(final int index)
	{
		if (index == 0)
		{
			return label;
		}
		return null;
	}

	@Override
	public Object getParameterComponent(final int index)
	{
		if (index == 0)
		{
			return editor;
		}
		return null;
	}

	@Override
	public Object getParameterToComponent(final int index)
	{
		if (index == 0)
		{
			return editor2;
		}
		return null;
	}

	@Override
	public String[] getWhereClauses(final List<Object> params)
	{
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		if (infoColumn.isTree())
		{
			return null;
		}

		final String column = infoColumn.getSelectClause();

		final Object value = getParameterValue(0, false);
		final Object valueTo;
		final boolean isRange;
		if (editor2 != null)
		{
			valueTo = getParameterValue(0, true);
			isRange = true;
		}
		else
		{
			valueTo = null;
			isRange = false;
		}

		//
		// Set Context Value
		final IInfoSimple parent = getParent();
		if (value == null)
		{
			final int displayType = infoColumn.getAD_Reference_ID();
			if (DisplayType.isID(displayType) || DisplayType.isNumeric(displayType))
			{
				parent.setCtxAttribute(column, -1);
			}
			else if (DisplayType.isText(displayType))
			{
				parent.setCtxAttribute(column, "");
			}
		}
		else
		{
			parent.setCtxAttribute(column, value);
		}

		final StringBuilder where = new StringBuilder();
		if (infoColumn.isQueryCriteria())
		{
			if (value == null && !isRange)
			{
				// do nothing
			}
			else if (value instanceof String)
			{
				// Ignore empty values
				if (value == null || value.toString().length() == 0)
				{
					return null;
				}
				//
				final String whereClauseStr = FindHelper.buildStringRestriction(infoColumn.getQueryCriteriaFunction(), column, value, false, params);
				if (!Check.isEmpty(whereClauseStr))
				{
					where.append(" ").append(whereClauseStr).append(" ");
				}
			}
			else if (value instanceof Integer)
			{
				final int id = value != null ? ((Integer)value).intValue() : 0;
				{
					if (isRange)
					{
						final int id2 = valueTo != null ? ((Integer)valueTo).intValue() : 0;
						if (id != 0 || id2 != 0)
						{
							where.append(" ( ");
						}
						if (id != 0)
						{
							where.append(column + ">?");
							params.add(id);
						}
						if (id2 != 0)
						{
							if (id != 0)
							{
								where.append(" AND ");
							}
							where.append(column + "< ? ");
							params.add(id2);
						}
						if (id != 0 || id2 != 0)
						{
							where.append(" ) ");
						}
					}
					else
					{
						if (id != 0)
						{
							where.append(column + "=?");
							params.add(id);
						}
					}

				}
			}
			else
			{
				if (isRange)
				{
					if (value != null || valueTo != null)
					{
						where.append(" ( ");
					}
					if (value != null)
					{
						where.append(column).append("> ?");
						params.add(value);
					}
					if (valueTo != null)
					{
						if (value != null)
						{
							where.append(" AND ");
						}
						where.append(column).append("< ?");
						params.add(valueTo);
					}
					if (value != null || valueTo != null)
					{
						where.append(" ) ");
					}
				}
				else
				{
					where.append(column);

					where.append("= ?");
					params.add(value);
				}
			}
		}

		return new String[] { where.toString() };
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return _infoColumn;
	}

	public IInfoSimple getParent()
	{
		return _parent;
	}

	@Override
	public String toString()
	{
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		if (infoColumn != null)
		{
			return getClass().getSimpleName() + "[" + infoColumn.getName() + "]";
		}
		else
		{
			return super.toString();
		}
	}

	@Override
	public final String getText()
	{
		if (editor == null)
		{
			return null;
		}

		final Object value = editor.getValue();
		if (value == null)
		{
			return null;
		}

		return value.toString();
	}

	@Override
	public Object getParameterValue(final int index, final boolean returnValueTo)
	{
		final Object field;
		if (returnValueTo)
		{
			field = getParameterToComponent(index);
		}
		else
		{
			field = getParameterComponent(index);
		}

		if (field instanceof CEditor)
		{
			final CEditor editor = (CEditor)field;
			return editor.getValue();
		}
		else
		{
			throw new AdempiereException("Component type not supported - " + field);
		}
	}

	/**
	 * Method called when one of the parameter fields changed
	 */
	protected void onFieldChanged()
	{
		// parent.executeQuery(); we don't want to query each time, because we might block the search
	}
}
