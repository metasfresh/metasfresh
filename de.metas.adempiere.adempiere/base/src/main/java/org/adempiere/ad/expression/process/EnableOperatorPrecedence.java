package org.adempiere.ad.expression.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.ILogicExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * 
 * @author ad
 * @task http://dewiki908/mediawiki/index.php/03093:_Introduce_paranthesis_support_for_our_logic_expressions_%282012080710000021%29
 */
public class EnableOperatorPrecedence extends SvrProcess
{
	private boolean p_IsTest = true;

	@Override
	protected void prepare()
	{

		ProcessInfoParameter[] paras = getParametersAsArray();
		for (ProcessInfoParameter para : paras)
		{
			final String name = para.getParameterName();
			if ("IsTest".equals(name))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		ILogicExpressionCompiler compiler = Services.get(IExpressionFactory.class).getCompiler(ILogicExpression.class);

		if (compiler.isUseOperatorPrecedence())
		{
			return "Already enabled";
		}

		// fix columns
		final String whereClauseColumn = I_AD_Column.COLUMNNAME_ReadOnlyLogic + " IS NOT NULL "
				+ " OR " + I_AD_Column.COLUMNNAME_MandatoryLogic + " IS NOT NULL ";

		final List<I_AD_Column> columns = new TypedSqlQuery<I_AD_Column>(getCtx(), I_AD_Column.class, whereClauseColumn, get_TrxName())
				.list(I_AD_Column.class);

		for (I_AD_Column column : columns)
		{
			fixColumn(column);
		}

		// fix fields
		final String whereClauseField = I_AD_Field.COLUMNNAME_DisplayLogic + " IS NOT NULL ";
		final List<I_AD_Field> fields = new TypedSqlQuery<I_AD_Field>(getCtx(), I_AD_Field.class, whereClauseField, get_TrxName()).list(I_AD_Field.class);

		for (I_AD_Field field : fields)
		{
			fixField(field);
		}

		// fix tabs
		final String whereClauseTab = I_AD_Tab.COLUMNNAME_DisplayLogic + " IS NOT NULL "
				+ " OR " + I_AD_Tab.COLUMNNAME_ReadOnlyLogic + " IS NOT NULL ";

		final List<I_AD_Tab> tabs = new TypedSqlQuery<I_AD_Tab>(getCtx(), I_AD_Tab.class, whereClauseTab, get_TrxName()).list(I_AD_Tab.class);

		for (I_AD_Tab tab : tabs)
		{
			fixTab(tab);
		}

		if (p_IsTest)
		{
			throw new AdempiereException("ROLLBACK");
		}

		compiler.setUseOperatorPrecedence(true);
		return "Ok";
	}

	private void fixTab(I_AD_Tab tab)
	{
		final I_AD_Window window = tab.getAD_Window();
		final String displayLogic = tab.getDisplayLogic();
		if (!Check.isEmpty(displayLogic, true))
		{
			final String info1 = "Column " + window.getName() + "." + tab.getName() + " DisplayLogic: old=" + displayLogic;
			final String displayLogicNew = fixExpression(displayLogic, info1);
			if (!isEquivalent(displayLogic, displayLogicNew))
			{
				tab.setDisplayLogic(displayLogicNew);
				addLog(info1 + " => " + displayLogicNew);
			}
		}

		final String readOnlyLogic = tab.getReadOnlyLogic();
		if (!Check.isEmpty(readOnlyLogic, true))
		{
			final String info2 = "Column " + window.getName() + "." + tab.getName() + " ReadOnlyLogic: old=" + readOnlyLogic;
			final String readOnlyLogicNew = fixExpression(readOnlyLogic, info2);
			if (!isEquivalent(readOnlyLogic, readOnlyLogicNew))
			{
				tab.setReadOnlyLogic(readOnlyLogicNew);
				addLog(info2 + " => " + readOnlyLogicNew);
			}
		}
		InterfaceWrapperHelper.save(tab);
	}

	private void fixField(I_AD_Field field)
	{
		final I_AD_Tab tab = field.getAD_Tab();
		final I_AD_Window window = tab.getAD_Window();
		final String displayLogic = field.getDisplayLogic();
		if (!Check.isEmpty(displayLogic, true))
		{
			final String info = "Field " + window.getName() + "." + tab.getName() + "." + field.getName() + " DisplayLogic: old=" + displayLogic;
			final String displayLogicNew = fixExpression(displayLogic, info);
			if (!isEquivalent(displayLogic, displayLogicNew))
			{
				field.setDisplayLogic(displayLogicNew);
				addLog(info + " => " + displayLogicNew);
			}
		}
		InterfaceWrapperHelper.save(field);
	}

	private void fixColumn(I_AD_Column column)
	{
		final I_AD_Table table = column.getAD_Table();

		final String readOnlyLogic = column.getReadOnlyLogic();
		if (!Check.isEmpty(readOnlyLogic, true))
		{
			final String info1 = "Column " + table.getTableName() + "." + column.getColumnName() + " ReadOnlyLogic '" + readOnlyLogic;
			final String readOnlyLogicNew = fixExpression(readOnlyLogic, info1);
			if (!isEquivalent(readOnlyLogic, readOnlyLogicNew))
			{
				column.setReadOnlyLogic(readOnlyLogicNew);
				addLog(info1 + " => " + readOnlyLogicNew);
			}
		}

		final String mandatoryLogic = column.getMandatoryLogic();
		if (!Check.isEmpty(mandatoryLogic, true))
		{
			final String info2 = "Column " + table.getTableName() + "." + column.getColumnName() + " MandatoryLogic '" + mandatoryLogic;
			final String mandatoryLogicNew = fixExpression(column.getMandatoryLogic(), info2);
			if (!isEquivalent(mandatoryLogic, mandatoryLogicNew))
			{
				column.setMandatoryLogic(mandatoryLogicNew);
				addLog(info2 + " => " + mandatoryLogicNew);
			}
		}

		InterfaceWrapperHelper.save(column);
	}

	private String fixExpression(String logic, String info)
	{
		try
		{
			final ILogicExpression tree = Services.get(IExpressionFactory.class).compile(logic, ILogicExpression.class);
			return tree.getFormatedExpressionString();
		}
		catch (Exception e)
		{
			addLog("Exception " + e.getMessage() + " thrown on " + info);
			return logic;
		}
	}

	private boolean isEquivalent(String oldLogic, String newLogic)
	{
		return Check.equals(oldLogic.replaceAll(" ", "").replace("!=", "!"), newLogic.replaceAll(" ", ""));
	}
}
