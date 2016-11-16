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
package org.compiere.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.MField;
import org.compiere.model.MTab;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Create Field from Table Column. (which do not exist in the Tab yet)
 * 
 * @author Jorg Janke
 * @version $Id: TabCreateFields.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 
 * @author Teo Sarca <li>BF [ 2827782 ] TabCreateFields process not setting entity type well https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2827782&group_id=176962
 */
public class TabCreateFields extends SvrProcess
{
	private static final Logger log = LogManager.getLogger(TabCreateFields.class);

	/** AD_Tab_ID */
	private int p_AD_Tab_ID = -1;

	private static final String PARAM_EntityType = "EntityType";
	/** Column's EntityType */
	private String p_EntityType = null;

	private static final String PARAM_AD_Column_ID = "AD_Column_ID";
	private int p_AD_Column_ID = -1;

	private static final String PARAM_IsTest = "IsTest";
	private boolean p_IsTest = false;

	/**
	 * prepare
	 */
	@Override
	protected void prepare()
	{
		p_AD_Tab_ID = getRecord_ID();
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			
			final String parameterName = para.getParameterName();
			if (PARAM_EntityType.equals(parameterName))
			{
				p_EntityType = para.getParameterAsString();
			}
			if (PARAM_AD_Column_ID.equals(parameterName))
			{
				p_AD_Column_ID = para.getParameterAsInt();
			}
			else if (PARAM_IsTest.equals(parameterName))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}	// prepare

	/**
	 * Process
	 * 
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Tab_ID <= 0)
		{
			throw new FillMandatoryException(I_AD_Tab.COLUMNNAME_AD_Tab_ID);
		}
		final MTab adTab = new MTab(getCtx(), p_AD_Tab_ID, get_TrxName());
		if (adTab.getAD_Tab_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Tab_ID@ " + p_AD_Tab_ID);
		}
		log.info("Tab: " + adTab.toString());

		//
		int count = 0;
		for (final I_AD_Column adColumn : retrieveColumns(adTab))
		{
			try
			{
				final I_AD_Field field = createADField(adTab, adColumn, p_EntityType);
				addLog("@Created@: " + field.getName() + " (@AD_Column_ID@: " + adColumn.getColumnName() + ")");
				count++;
			}
			catch (Exception e)
			{
				log.warn("Failed to create field for {}", adColumn, e);
				addLog("Error creating " + adColumn.getColumnName() + ": " + e.getLocalizedMessage());
			}
		}

		if (p_IsTest)
		{
			throw new AdempiereException("ROLLBACK");
		}
		
		return "@Created@ #" + count;
	}	// doIt

	private List<I_AD_Column> retrieveColumns(final I_AD_Tab adTab)
	{
		final IQuery<I_AD_Field> queryTabFields = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class, getCtx(), get_TrxName())
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, adTab.getAD_Tab_ID())
				.create();

		final IQueryBuilder<I_AD_Column> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column.class, getCtx(), get_TrxName())
				//
				// Columns for Tab's AD_Table_ID
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTab.getAD_Table_ID())
				//
				// Don't add standard columns
				.filter(new NotQueryFilter<I_AD_Column>(
						new InArrayQueryFilter<I_AD_Column>(I_AD_Column.COLUMNNAME_ColumnName, "Created", "CreatedBy", "Updated", "UpdatedBy")
						))
				//
				// Columns that were not already added to Tab
				.filter(new NotQueryFilter<I_AD_Column>(
						new InSubQueryFilter<I_AD_Column>(
								I_AD_Column.COLUMN_AD_Column_ID,
								I_AD_Field.COLUMNNAME_AD_Column_ID, queryTabFields)
						));

		//
		// Filter by AD_Column's EntityType (if any)
		if (!Check.isEmpty(p_EntityType, true))
		{
			queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_EntityType, p_EntityType);
		}
		
		//
		// Filter by AD_Column_ID (if any)
		if (p_AD_Column_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Column_ID, p_AD_Column_ID);
		}

		queryBuilder.orderBy()
				.addColumn(I_AD_Column.COLUMNNAME_AD_Column_ID);

		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.list(I_AD_Column.class);
	}

	public static I_AD_Field createADField(final I_AD_Tab tab, final I_AD_Column adColumn, final String p_EntityType)
	{
		final String entityTypeToUse;
		if (Check.isEmpty(p_EntityType, true))
		{
			entityTypeToUse = tab.getEntityType(); // Use Tab's Entity Type - teo_sarca, BF [ 2827782 ]
		}
		else
		{
			entityTypeToUse = p_EntityType;
		}

		final MField field = new MField(tab);
		field.setColumn(adColumn);
		field.setEntityType(entityTypeToUse);

		if (adColumn.isKey())
		{
			field.setIsDisplayed(false);
			field.setIsDisplayedGrid(false);
		}
		
		InterfaceWrapperHelper.save(field);
		return field;
	}
}	// TabCreateFields
