/**
 *
 */
package org.compiere.grid.ed;

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


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.text.JTextComponent;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.db.DBConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.search.FieldAutoCompleter;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MTable;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import de.metas.autocomplete.model.I_AD_Table;

/**
 *
 * @author teo_sarca
 *
 */
/* package */class VLookupAutoCompleter extends FieldAutoCompleter
{
	private final VLookup editor;
	private final MLookupInfo lookupInfo;
	// private final MLookup lookup;

	private final VLookupAutoCompleterValidationRule autocompleterValidationRule;
	// private final IValidationContext validationCtx;
	private final IValidationRule validationRule;

	public VLookupAutoCompleter(JTextComponent comp, VLookup editor, MLookupInfo lookupInfo)
	{
		super(comp);
		this.editor = editor;
		this.lookupInfo = lookupInfo;

		final Properties ctx = Env.getCtx();
		final String tableName = lookupInfo.getTableName();
		final MTable table = MTable.get(ctx, tableName);

		this.autocompleterValidationRule = createAutoCompleterValidationRule(table, lookupInfo);

		//
		// Init Complete validationRule
		{
			final CompositeValidationRule validationRule = new CompositeValidationRule();
			validationRule.addValidationRule(lookupInfo.getValidationRule());
			validationRule.addValidationRule(autocompleterValidationRule);
			this.validationRule = validationRule;

			// validationCtx = lookup.getValidationContext();
		}

		//
		// Set Popup Mininum Chars:
		final int popupMinimumChars = InterfaceWrapperHelper.create(table, I_AD_Table.class).getACTriggerLength();
		if (popupMinimumChars > 0)
		{
			setPopupMinimumChars(popupMinimumChars);
		}

		//
		// When editor value is changed (i.e. on focus lost) hide the popup
		editor.addVetoableChangeListener(new VetoableChangeListener()
		{
			@Override
			public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
			{
				hidePopup();
			}
		});

		//
		// When editor's editing component has lost focus, hide the popup
		editor.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				if (e.isTemporary())
				{
					return;
				}
				hidePopup();
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				// nothing
			}
		});
	}

	private static VLookupAutoCompleterValidationRule createAutoCompleterValidationRule(final MTable table, final MLookupInfo lookupInfo)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final String tableName = table.getTableName();

		//
		// Init Search Columns
		final List<String> searchColumns = new ArrayList<String>();
		final List<String> searchColumnsSQL = new ArrayList<String>();
		{
			for (final MColumn c : table.getColumns(false))
			{
				if (DisplayType.isText(c.getAD_Reference_ID())
						&& (c.isIdentifier() || c.isSelectionColumn()))
				{
					searchColumns.add(c.getColumnName());
					if (adTableDAO.isVirtualColumn(c))
					{
						searchColumnsSQL.add(c.getColumnSQL());
					}
					else
					{
						searchColumnsSQL.add(c.getColumnName());
					}
				}
			}
		}

		final List<Object> paramsTemplate = new ArrayList<Object>();
		final StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(tableName).append(".IsActive=?");
		paramsTemplate.add(true);

		if (!searchColumns.isEmpty())
		{
			sqlWhere.append(" AND (");
			for (int i = 0; i < searchColumns.size(); i++)
			{
				final String searchColumn = searchColumns.get(i);
				final String searchColumnSQL = searchColumnsSQL.get(i);

				if (i > 0)
				{
					sqlWhere.append(" OR ");
				}
				sqlWhere.append("UPPER(");
				if (searchColumn.equals(searchColumnsSQL))
				{
					sqlWhere.append(DBConstants.FUNC_unaccent_string(tableName+"."+searchColumn));
				}
				else
				{
					sqlWhere.append(DBConstants.FUNC_unaccent_string(tableName+"."+searchColumnSQL));
				}
				sqlWhere.append(") LIKE UPPER(" + DBConstants.FUNC_unaccent_string("?") + ") ");
				paramsTemplate.add(VLookupAutoCompleterValidationRule.SEARCHSQL_PLACEHOLDER);
			}

			// Full generated identifier search
			if (!Check.isEmpty(lookupInfo.getDisplayColumnSQL(), true))
			{
				sqlWhere.append(" OR UPPER(" + DBConstants.FUNC_unaccent_string(lookupInfo.getDisplayColumnSQL()) + ")"
						+ " LIKE UPPER(" + DBConstants.FUNC_unaccent_string("?") + ")");
				paramsTemplate.add(VLookupAutoCompleterValidationRule.SEARCHSQL_PLACEHOLDER);
			}
			//
			sqlWhere.append(") ");
		}
		else
		{
			// log.warn("No search columns found for " + lookup);
			sqlWhere.append(" AND 1=2 ");
		}

		return new VLookupAutoCompleterValidationRule(sqlWhere.toString(), paramsTemplate);
	}

	private IValidationContext getValidationContext()
	{
		return editor.getValidationContext();
	}

	@Override
	protected Object fetchUserObject(ResultSet rs) throws SQLException
	{
		final String name = rs.getString(MLookupFactory.COLUMNINDEX_DisplayName);

		final NamePair item;
		if (lookupInfo.isNumericKey())
		{
			final int key = rs.getInt(MLookupFactory.COLUMNINDEX_Key);
			item = new KeyNamePair(key, name);
		}
		else
		{
			final String value = rs.getString(MLookupFactory.COLUMNINDEX_Value);
			item = new ValueNamePair(value, name);
		}

		// Validate the result
		if (!validationRule.accept(getValidationContext(), item))
		{
			return null;
		}

		return item;
	}

	private String getSearchStringSQL(final String searchInput, final int caretPosition)
	{
		final String search;
		if (caretPosition > 0 && caretPosition < searchInput.length())
		{
			search = new StringBuilder(searchInput).insert(caretPosition, "%").toString();
		}
		else
		{
			search = searchInput;
		}

		String searchSQL = search;
		if (!searchSQL.startsWith("%"))
		{
			searchSQL = "%" + searchSQL;
		}
		if (!searchSQL.endsWith("%"))
		{
			searchSQL += "%";
		}

		return searchSQL;
	}

	@Override
	protected String getSelectSQL(final String searchInput, final int caretPosition, final List<Object> params)
	{
		final String searchSQL = getSearchStringSQL(searchInput, caretPosition);

		final String sqlSelect = lookupInfo.getSelectSqlPart();
		if (Check.isEmpty(sqlSelect, true))
		{
			log.warn("Empty SELECT SQL found for: " + lookupInfo);
			return null;
		}

		// final StringBuilder sql = new StringBuilder();
		// sql.append(sqlSelect);

		final StringBuilder sqlWhere = new StringBuilder();
		{
			if (!Check.isEmpty(lookupInfo.getWhereClauseSqlPart()))
			{
				sqlWhere.append("(").append(lookupInfo.getWhereClauseSqlPart()).append(")");
			}

			final String sqlWhereValRule = validationRule.getPrefilterWhereClause(getValidationContext());
			if (sqlWhereValRule == IValidationRule.WHERECLAUSE_ERROR)
			{
				return null;
			}
			else if (!Check.isEmpty(sqlWhereValRule, true))
			{
				if (sqlWhere.length() > 0)
				{
					sqlWhere.append(" AND ");
				}
				sqlWhere.append("(").append(sqlWhereValRule).append(")");

				params.addAll(autocompleterValidationRule.getParameterValues(searchSQL));
			}
		}

		final StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(sqlSelect);
		if (sqlWhere.length() > 0)
		{
			sqlBuilder.append(" WHERE ").append(sqlWhere);
		}

		final String sqlOrderBy = lookupInfo.getOrderBySqlPart();
		if (!Check.isEmpty(sqlOrderBy, true))
		{
			sqlBuilder.append(" ORDER BY ").append(sqlOrderBy);
		}

		// Add Security
		final String sqlFinal;
		if (lookupInfo.isSecurityDisabled())
		{
			sqlFinal = sqlBuilder.toString();
		}
		else
		{
			sqlFinal = Env.getUserRolePermissions().addAccessSQL(sqlBuilder.toString(), lookupInfo.TableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		}

		//
		return sqlFinal;
	}

	@Override
	public boolean isEnabled()
	{
		return this.textBox.hasFocus();
	}

	@Override
	public void setUserObject(Object userObject)
	{
		String textOld = textBox.getText();
		int caretPosition = textBox.getCaretPosition();
		//
		super.setUserObject(userObject);
		// Object valueOld = editor.getValue();
		Object value = null;
		if (userObject == null)
		{
			editor.setValue(null);
		}
		else if (userObject instanceof ValueNamePair)
		{
			ValueNamePair vnp = (ValueNamePair)userObject;
			value = vnp.getValue();
		}
		else if (userObject instanceof KeyNamePair)
		{
			KeyNamePair knp = (KeyNamePair)userObject;
			value = knp.getKey();
		}
		else
		{
			log.warn("Not supported - " + userObject + ", class=" + userObject.getClass());
			return;
		}
		editor.actionCombo(value);
		if (value == null)
		{
			textBox.setText(textOld);
			textBox.setCaretPosition(caretPosition);
		}
	}

}
