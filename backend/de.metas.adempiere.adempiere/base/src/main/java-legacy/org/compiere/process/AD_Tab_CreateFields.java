package org.compiere.process;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.MField;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

/**
 * Create Field from Table Column. (which do not exist in the Tab yet)
 *
 * @author Jorg Janke
 * @version $Id: TabCreateFields.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 *
 * @author Teo Sarca
 *         <li>BF [ 2827782 ] TabCreateFields process not setting entity type well https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2827782&group_id=176962
 */
public class AD_Tab_CreateFields extends JavaProcess
{
	private static final transient Logger log = LogManager.getLogger(AD_Tab_CreateFields.class);

	private static final Set<String> COLUMNNAMES_Standard = ImmutableSet.of("Created", "CreatedBy", "Updated", "UpdatedBy");

	@Param(parameterName = "EntityType")
	private String p_EntityType;

	@Param(parameterName = "AD_Column_ID")
	private int p_AD_Column_ID;

	@Param(parameterName = "IsCreateStandardFields")
	private boolean p_IsCreateStandardFields;

	@Param(parameterName = "IsTest")
	private boolean p_IsTest = false;

	@Param(parameterName = "IsDisplayNonIDColumns")
	private boolean p_IsDisplayNonIDColumns = false;

	@Override
	protected String doIt()
	{
		final I_AD_Tab adTab = getRecord(I_AD_Tab.class);

		if (adTab.getTemplate_Tab_ID() > 0)
		{
			throw new AdempiereException("Not allowed when using a template tab");
		}

		//
		int count = 0;
		for (final I_AD_Column adColumn : retrieveColumns(adTab))
		{
			try
			{
				final I_AD_Field field = createADField(adTab, adColumn, p_IsDisplayNonIDColumns, p_EntityType);
				addLog("@Created@: " + field.getName() + " (@AD_Column_ID@: " + adColumn.getColumnName() + ")");
				count++;
			}
			catch (final Exception e)
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
				// Columns that were not already added to Tab
				.addNotInSubQueryFilter(I_AD_Column.COLUMN_AD_Column_ID, I_AD_Field.COLUMN_AD_Column_ID, queryTabFields);

		//
		// Filter by AD_Column's EntityType (if any)
		if (!Check.isEmpty(p_EntityType, true))
		{
			queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_EntityType, p_EntityType);
		}

		//
		// Filter by AD_Column_ID (if any)
		boolean createStandardFields = p_IsCreateStandardFields;
		if (p_AD_Column_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Column_ID, p_AD_Column_ID);
			createStandardFields = true; // set it to true to avoid excluding this column
		}

		//
		// Exclude standard columns
		if (!createStandardFields)
		{
			queryBuilder.addNotInArrayFilter(I_AD_Column.COLUMN_ColumnName, COLUMNNAMES_Standard);
		}

		queryBuilder.orderBy()
				.addColumn(I_AD_Column.COLUMNNAME_AD_Column_ID);

		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.list(I_AD_Column.class);
	}

	public static I_AD_Field createADField(
			final I_AD_Tab tab,
			final I_AD_Column adColumn,
			final boolean displayedIfNotIDColumn,
			@Nullable final String p_EntityType)
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

		if (adColumn.isKey() || !displayedIfNotIDColumn)
		{
			field.setIsDisplayed(false);
			field.setIsDisplayedGrid(false);
		}
		
		if("AD_Client_ID".equals(adColumn.getColumnName()))
		{
			field.setIsReadOnly(true);
		}

		InterfaceWrapperHelper.save(field);
		return field;
	}
}	// TabCreateFields
