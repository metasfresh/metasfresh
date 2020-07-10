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

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.element.api.ElementChangedEvent.ChangedField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ILanguageDAO;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/**
 * System Element Model
 *
 * @author Jorg Janke
 * @version $Id: M_Element.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 *          FR: [ 2214883 ] Remove SQL code and Replace for Query - red1, teo_sarca
 */
@SuppressWarnings("serial")
public class M_Element extends X_AD_Element
{
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
		if (columnName == null || columnName.length() == 0)
		{
			return null;
		}
		//
		// TODO: caching if trxName == null
		final String whereClause = "UPPER(ColumnName)=?";
		M_Element retValue = new Query(ctx, M_Element.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(new Object[] { columnName.toUpperCase() })
				.firstOnly();
		return retValue;
	}	// get

	public M_Element(Properties ctx, int AD_Element_ID, String trxName)
	{
		super(ctx, AD_Element_ID, trxName);
	}

	public M_Element(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Minimum Constructor
	 */
	public M_Element(Properties ctx, String columnName, String EntityType, String trxName)
	{
		super(ctx, 0, trxName);
		setColumnName(columnName);
		setName(columnName);
		setPrintName(columnName);
		//
		setEntityType(EntityType);	// U
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
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Column AD_Element.ColumnName should be unique - teo_sarca [ 1613107 ]
		final boolean columnNameChanged = newRecord || is_ValueChanged(COLUMNNAME_ColumnName);
		if (columnNameChanged)
		{
			final String columnNameEffective = computeEffectiveColumnName(
					getColumnName(),
					AdElementId.ofRepoIdOrNull(getAD_Element_ID()));
			setColumnName(columnNameEffective);
		}

		return true;
	}

	@Nullable
	private static String computeEffectiveColumnName(@Nullable final String columnName, @Nullable final AdElementId adElementId)
	{
		if (columnName == null)
		{
			return null;
		}

		final String columnNameNormalized = StringUtils.trimBlankToNull(columnName);
		if (columnNameNormalized == null)
		{
			return null;
		}

		assertColumnNameDoesNotExist(columnNameNormalized, adElementId);
		return columnNameNormalized;
	}

	private static void assertColumnNameDoesNotExist(final String columnName, final AdElementId elementIdToExclude)
	{
		String sql = "select count(1) from AD_Element where UPPER(ColumnName)=UPPER(?)";
		if (elementIdToExclude != null)
		{
			sql += " AND AD_Element_ID<>" + elementIdToExclude.getRepoId();
		}

		final int no = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, columnName);
		if (no > 0)
		{
			throw new AdempiereException("@SaveErrorNotUnique@ @ColumnName@: " + columnName);
		}

	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!newRecord)
		{
			// update dependent entries only in case of existing element.
			// new elements are not used yet.
			updateDependentADEntries();
		}

		return success;
	}

	private void updateDependentADEntries()
	{
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
	}

	private boolean isValueChanged(@NonNull final ChangedField field)
	{
		return is_ValueChanged(field.getColumnName());
	}
}
