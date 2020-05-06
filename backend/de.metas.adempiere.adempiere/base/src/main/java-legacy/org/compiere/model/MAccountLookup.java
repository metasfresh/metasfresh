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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

/**
 * Account Model Lookup - Maintains ValidCombination Info for Display & Edit - not cached
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 *         http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @version $Id: MAccountLookup.java,v 1.3 2006/07/30 00:54:54 jjanke Exp $
 */
public final class MAccountLookup extends Lookup implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5565166586045152280L;

	/**
	 * Constructor
	 * 
	 * @param ctx context
	 * @param WindowNo window no
	 */
	public MAccountLookup(final Properties ctx, final int WindowNo)
	{
		super(DisplayType.TableDir, WindowNo);
		m_ctx = ctx;
	}	// MAccountLookup

	/** Context */
	private final Properties m_ctx;
	/** Account_ID */
	private int C_ValidCombination_ID;
	private String Combination;
	private String Description;

	/**
	 * Get Display for Value
	 *
	 * @param ignored_evalCtx
	 * @param value the <code>C_Validcombination_ID</code> of this lookup as {@link Integer}, or <code>null</code>.
	 * @return <ul>
	 *         <li>the empty string if the given <code>value</code> is <code>null</code>.
	 *         <li><code>"<" + value + ">"</code> of the given value is not null, but not the key of an active <code>C_ValidCombination</code>.
	 *         <li><code>Combination + " (" + Description + ")"</code> otherwise
	 *         
	 * @task 07502 Name in Validcombination (107086145302)
	 */
	@Override
	public String getDisplay(final IValidationContext ignored_evalCtx, final Object value)
	{
		// NOTE: even if "value" is null, don't return immediatelly,
		// because we need to call "containsKey" method which is setting current validCombinationId
		
		if (!containsKey(ignored_evalCtx, value))
		{
			final String fallbackString = value == null ? "" : "<" + value.toString() + ">";
			return fallbackString;
		}

		if (C_ValidCombination_ID <= 0)
		{
			final String fallbackString = value == null ? "" : "<" + value.toString() + ">";
			return fallbackString;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append(Combination);
		sb.append(" (").append(Description).append(")");

		return sb.toString();
	}	// getDisplay

	/**
	 * Get Object of Key Value
	 * 
	 * @param value value
	 * @return Object or null
	 */
	@Override
	public NamePair get(final IValidationContext evalCtx, Object value)
	{
		if (value == null)
			return null;
		if (!containsKey(value))
			return null;
		return new KeyNamePair(C_ValidCombination_ID, toString());
	}	// get

	/**
	 * If the given <code>key</code> is an not-<code>null</code> Integer, then this method calls {@link #load(int)} so load the <code>C_ValidCombination</code> record for the key.
	 * 
	 * @param ignored_evalCtx
	 * @param key
	 * @return <code>true</code> if a <code>C_ValidCombination</code> exists for the given key.
	 */
	@Override
	public boolean containsKey(final IValidationContext ignored_evalCtx, final Object key)
	{
		int intValue = 0;
		if (key instanceof Integer)
		{
			intValue = ((Integer)key).intValue();
		}
		else if (key != null)
		{
			intValue = Integer.parseInt(key.toString());
		}
		//
		return load(intValue);
	}   // containsKey

	/**
	 * Get Description
	 * 
	 * @return Description
	 */
	public String getDescription()
	{
		return Description;
	}   // getDescription

	/**
	 * Return String representation
	 * 
	 * @return Combination
	 */
	@Override
	public String toString()
	{
		if (C_ValidCombination_ID == 0)
		{
			return "";
		}
		return Combination;
	}	// toString

	/**
	 * Load C_ValidCombination with the given <code>ID</code> and (if the record exists and is active) sets this instance's members with the loaded record's <code>C_ValidCombination_ID</code>,
	 * <code>Combination</code> and <code>Description</code>.
	 * 
	 * @param ID C_ValidCombination_ID
	 * @return true if found
	 */
	private boolean load(final int validcombinationID)
	{
		if (validcombinationID <= 0)						// new
		{
			C_ValidCombination_ID = 0;
			Combination = "";
			Description = "";
			return true;
		}
		if (validcombinationID == C_ValidCombination_ID)	// already loaded
		{
			return true;
		}

		final I_C_ValidCombination account = InterfaceWrapperHelper.create(m_ctx, validcombinationID, I_C_ValidCombination.class, ITrx.TRXNAME_None);
		if (account == null || !account.isActive())
		{
			return false;
		}

		C_ValidCombination_ID = account.getC_ValidCombination_ID();
		Combination = account.getCombination();
		Description = account.getDescription();

		return true;
	}	// load

	@Override
	public String getTableName()
	{
		return I_C_ValidCombination.Table_Name;
	}

	/**
	 * Get underlying fully qualified Table.Column Name
	 * 
	 * @return ""
	 */
	@Override
	public String getColumnName()
	{
		return I_C_ValidCombination.Table_Name + "." + I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID;
	}   // getColumnName

	@Override
	public String getColumnNameNotFQ()
	{
		return I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID;
	}   // getColumnName

	/**
	 * Return data as sorted Array. Used in Web Interface
	 * 
	 * @param mandatory mandatory
	 * @param onlyValidated only valid
	 * @param onlyActive only active
	 * @param temporary force load for temporary display
	 * @return ArrayList with KeyNamePair
	 */
	@Override
	public ArrayList<Object> getData(boolean mandatory, boolean onlyValidated, boolean onlyActive, boolean temporary)
	{
		ArrayList<Object> list = new ArrayList<>();
		if (!mandatory)
			list.add(KeyNamePair.EMPTY);
		//
		ArrayList<Object> params = new ArrayList<>();
		String whereClause = "AD_Client_ID=?";
		params.add(Env.getAD_Client_ID(m_ctx));

		List<MAccount> accounts = new Query(m_ctx, MAccount.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(params)
				.setOrderBy(MAccount.COLUMNNAME_Combination)
				.setOnlyActiveRecords(onlyActive)
				.list(MAccount.class);

		for (final I_C_ValidCombination account : accounts)
		{
			list.add(new KeyNamePair(account.getC_ValidCombination_ID(), account.getCombination() + " - " + account.getDescription()));
		}
		// Sort & return
		return list;
	}   // getData

	public int getC_ValidCombination_ID()
	{
		return C_ValidCombination_ID;
	}
}	// MAccountLookup
