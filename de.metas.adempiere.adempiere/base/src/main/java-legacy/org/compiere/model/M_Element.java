/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.element.api.ElementChangedEvent.ChangedField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ILanguageDAO;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * System Element Model
 *
 * @author Jorg Janke
 * @version $Id: M_Element.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 *          FR: [ 2214883 ] Remove SQL code and Replace for Query - red1, teo_sarca
 */
public class M_Element extends X_AD_Element
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7426812810619889250L;

	/**
	 * Get case sensitive Column Name
	 *
	 * @param columnName case insensitive column name
	 * @return case sensitive column name
	 */
	public static String getColumnName(String columnName)
	{
		return getColumnName(columnName, null);
	}

	/**
	 * Get case sensitive Column Name
	 *
	 * @param columnName case insensitive column name
	 * @param trxName optional transaction name
	 * @return case sensitive column name
	 */
	public static String getColumnName(String columnName, String trxName)
	{
		if (columnName == null || columnName.length() == 0)
			return columnName;
		M_Element element = get(Env.getCtx(), columnName, trxName);
		if (element == null)
			return columnName;
		return element.getColumnName();

	}	// getColumnName

	/**
	 * Get Element
	 *
	 * @param ctx context
	 * @param columnName case insensitive column name
	 * @return case sensitive column name
	 */
	@Cached(cacheName = I_AD_Element.Table_Name + "#by#" + I_AD_Element.COLUMNNAME_ColumnName)
	public static M_Element get(@CacheCtx Properties ctx, String columnName)
	{
		return get(ctx, columnName, ITrx.TRXNAME_None);
	}

	/**
	 * Get Element
	 *
	 * @param ctx context
	 * @param columnName case insensitive column name
	 * @param trxName optional transaction name
	 * @return case sensitive column name
	 */
	public static M_Element get(Properties ctx, String columnName, String trxName)
	{
		if (columnName == null || columnName.length() == 0)
			return null;
		//
		// TODO: caching if trxName == null
		final String whereClause = "UPPER(ColumnName)=?";
		M_Element retValue = new Query(ctx, M_Element.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { columnName.toUpperCase() })
				.firstOnly();
		return retValue;
	}	// get

	/**
	 * Get Element
	 *
	 * @param ctx context
	 * @param columnName case insensitive column name
	 * @param trxName trx
	 * @return case sensitive column name
	 */
	public static M_Element getOfColumn(Properties ctx, int AD_Column_ID, String trxName)
	{
		if (AD_Column_ID == 0)
			return null;
		String whereClause = "EXISTS (SELECT 1 FROM AD_Column c "
				+ "WHERE c.AD_Element_ID=AD_Element.AD_Element_ID AND c.AD_Column_ID=?)";
		M_Element retValue = new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(new Object[] { AD_Column_ID })
				.firstOnly();
		return retValue;
	}	// get

	/**
	 * Get Element
	 *
	 * @param ctx context
	 * @param columnName case insentitive column name
	 * @return case sensitive column name
	 */
	public static M_Element getOfColumn(Properties ctx, int AD_Column_ID)
	{
		return getOfColumn(ctx, AD_Column_ID, null);
	}	// get

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Element_ID element
	 * @param trxName transaction
	 */
	public M_Element(Properties ctx, int AD_Element_ID, String trxName)
	{
		super(ctx, AD_Element_ID, trxName);
		if (AD_Element_ID == 0)
		{
			// setColumnName (null);
			// setEntityType (null); // U
			// setName (null);
			// setPrintName (null);
		}
	}	// M_Element

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public M_Element(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// M_Element

	/**
	 * Minimum Constructor
	 *
	 * @param ctx context
	 * @param columnName column
	 * @param EntityType entity type
	 * @param trxName trx
	 */
	public M_Element(Properties ctx, String columnName, String EntityType,
			String trxName)
	{
		super(ctx, 0, trxName);
		setColumnName(columnName);
		setName(columnName);
		setPrintName(columnName);
		//
		setEntityType(EntityType);	// U
	}	// M_Element

	/*
	 * (non-Javadoc)
	 *
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Column AD_Element.ColumnName should be unique - teo_sarca [ 1613107 ]
		final boolean columnNameChange = newRecord || is_ValueChanged(COLUMNNAME_ColumnName);

		if (columnNameChange && !Check.isEmpty(getColumnName(), true))
		{
			final String originalColumnName = getColumnName();

			if (originalColumnName == null)
			{
				return true;
			}

			final String trimmedColumnName = originalColumnName.trim();
			if (originalColumnName.length() != trimmedColumnName.length())
			{
				setColumnName(trimmedColumnName);
			}

			String sql = "select count(*) from AD_Element where UPPER(ColumnName)=?";
			if (!newRecord)
				sql += " AND AD_Element_ID<>" + get_ID();
			int no = DB.getSQLValue(null, sql, trimmedColumnName.toUpperCase());
			if (no > 0)
			{
				throw new AdempiereException("@SaveErrorNotUnique@ @ColumnName@");
			}
		}

		return true;
	}

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (newRecord)
		{
			// the new element is not yet used so no updates are needed
			return success;
		}

		final String baseLanguage = Services.get(ILanguageDAO.class).retrieveBaseLanguage();

		final ImmutableSet<ChangedField> columnsChanged = ElementChangedEvent.ChangedField.streamAll()
				.filter(columnName -> isValueChanged(columnName))
				.collect(ImmutableSet.toImmutableSet());

		Services.get(IElementTranslationBL.class).updateDependentADEntries(ElementChangedEvent.builder()
				.adElementId(AdElementId.ofRepoId(getAD_Element_ID()))
				.adLanguage(baseLanguage)
				.updatedColumns(columnsChanged)
				.columnName(getColumnName())
				.name(getName())
				.printName(getPrintName())
				.description(getDescription())
				.help(getHelp())
				.commitWarning(getCommitWarning())
				.poDescription(getPO_Description())
				.poHelp(getPO_Help())
				.poName(getPO_Name())
				.poPrintName(getPO_PrintName())
				.webuiNameBrowse(getWEBUI_NameBrowse())
				.webuiNameNew(getWEBUI_NameNew())
				.webuiNameNewBreadcrumb(getWEBUI_NameNewBreadcrumb())
				.build());

		return success;
	}	// afterSave
	
	private boolean isValueChanged(@NonNull final ChangedField field)
	{
		return is_ValueChanged(field.getColumnName());
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("M_Element[")
				.append(get_ID())
				.append("-")
				.append(getColumnName())
				.append("]");
		return sb.toString();
	}	// toString

}	// M_Element
