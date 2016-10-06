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
import java.util.List;

import javax.swing.text.JTextComponent;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.search.FieldAutoCompleter;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.autocomplete.model.I_AD_Table;

public class VAccountAutoCompleter extends FieldAutoCompleter
{

	private final MAccountLookup accountLookup;
	private final VAccount editor;
	private final int acctSchemaId;

	/**
	 *
	 * @param comp the UI-text component the text of which we are going to auto-complete.
	 * @param editor the VEditor to which we will set the new auto-complete value after we found it.
	 * @param accountLookup the lookup whose {@link MAccountLookup#getDisplay(org.adempiere.ad.validationRule.IValidationContext, Object)} method we will use to present the eligible auto-lookup
	 *            results.
	 * @param acctSchemaId the <code>C_AcctSchema_ID</code> by which we filter the eligible records.
	 */
	public VAccountAutoCompleter(final JTextComponent comp, final VAccount editor, final MAccountLookup accountLookup, final int acctSchemaId)
	{
		super(comp);
		this.accountLookup = accountLookup;
		this.editor = editor;
		this.acctSchemaId = acctSchemaId;

		// copied from VLookupAutocompleter..can't actually claim that i understand this..
		//
		// Set Popup Mininum Chars:
		final int popupMinimumChars = InterfaceWrapperHelper.create(MTable.get(Env.getCtx(), I_C_ValidCombination.Table_Name), I_AD_Table.class).getACTriggerLength();
		if (popupMinimumChars > 0)
		{
			setPopupMinimumChars(popupMinimumChars);
		}

		//
		// When editor value is changed (i.e. on focus lost) hide the popup
		editor.addVetoableChangeListener(new VetoableChangeListener()
		{
			@Override
			public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
			{
				hidePopup();
			}
		});

		//
		// When editor's editing component has lost focus, hide the popup
		editor.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(final FocusEvent e)
			{
				if (e.isTemporary())
				{
					return;
				}
				hidePopup();
			}

			@Override
			public void focusGained(final FocusEvent e)
			{
				// nothing
			}
		});
	}

	/**
	 * Uses the {@link IQueryBL} to generate a query, only for the purpose of getting the SQL.
	 * <p>
	 * <b>IMPORTANT:</b> Keep in sync with {@link VAccount#cmd_text()}<br>
	 * FIXME: extract the code from both methods into a service.
	 */
	@Override
	protected String getSelectSQL(
			final String searchInput,
			final int caretPosition,
			final List<Object> params)
	{
		final String searchStringForFilter = getSearchStringSQL(searchInput, caretPosition);
		final boolean ignoreCase = true;

		final ICompositeQueryFilter<I_C_ValidCombination> filter =
				Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_ValidCombination.class)
						.setJoinOr()
						.addSubstringFilter(I_C_ValidCombination.COLUMNNAME_Alias, searchStringForFilter, ignoreCase)
						.addSubstringFilter(I_C_ValidCombination.COLUMNNAME_Description, searchStringForFilter, ignoreCase)
						.addSubstringFilter(I_C_ValidCombination.COLUMNNAME_Combination, searchStringForFilter, ignoreCase);

		final IQuery<I_C_ValidCombination> query = Services.get(IQueryBL.class).createQueryBuilder(I_C_ValidCombination.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ValidCombination.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.filter(filter)
				.create();

		if (query instanceof TypedSqlQuery)
		{
			final TypedSqlQuery<I_C_ValidCombination> sqlQuery = (TypedSqlQuery<I_C_ValidCombination>)query;
			
			// params.add(sqlQuery.getParametersEffective()); // doesn't work, because the code that runs this SQL won't be able to deal with the 'true' param of the OnlyActiveRecordsFilter.
			params.add("Y");
			params.add(acctSchemaId);

			final String sql = sqlQuery.getSQL();

			// this is taken from VAccount.cmd_text() which used to do the lookup
			return Env.getUserRolePermissions().addAccessSQL(sql, "C_ValidCombination", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		}
		return null; // according to the doc we shall return "null" for "error"
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

		// Note: as we use the query builder to make the SQL there is no need to prepend and append the '%'
		return search;
	}

	/**
	 * Creates and returns a {@link KeyNamePair} with the given ResultSet's C_ValidCombination_ID and the result of
	 * {@link MAccountLookup#getDisplay(org.adempiere.ad.validationRule.IValidationContext, Object)}.
	 */
	@Override
	protected Object fetchUserObject(final ResultSet rs) throws SQLException
	{
		final int id = rs.getInt(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID);
		final KeyNamePair item = new KeyNamePair(id, accountLookup.getDisplay(null, id));

		return item;
	}

	/**
	 * Calls {@link FieldAutoCompleter#setUserObject(Object)}, then calls {@link VAccount#setValue(Object)} for set the given <code>userObject</code> into our <code>VEditor</code>.
	 */
	@Override
	public void setUserObject(final Object userObject)
	{
		super.setUserObject(userObject);
		if (userObject != null)
		{
			final KeyNamePair keyNamePair = (KeyNamePair)userObject; // it's always a KeyNamePair, see fetchUserObject
			editor.setValue(keyNamePair.getID());
		}
		else
		{
			editor.setValue(null);
		}
	}
}
