package org.adempiere.ad.table.process;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.M_Element;
import org.compiere.model.X_AD_Column;
import org.compiere.process.TabCreateFields;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class TablePrimaryKeyGenerator
{
	// services
	private static final Logger logger = LogManager.getLogger(TablePrimaryKeyGenerator.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Properties ctx;
	private Set<String> resultTableNames = new LinkedHashSet<>();

	public TablePrimaryKeyGenerator(final Properties ctx)
	{
		super();
		this.ctx = ctx;
	}

	public void generateForTable(final I_AD_Table adTable)
	{
		if (adTable.isView())
		{
			addLog("Skip {} because it's view", adTable.getTableName());
			return;
		}

		if(hasColumnPK(adTable))
		{
			addLog("Skip {} because it already has PK", adTable.getTableName());
			return;
		}
		final I_AD_Column columnPK = createColumnPK(adTable);
		createColumnPK_DDL(columnPK);

		addToTabs(columnPK);

		CacheMgt.get().reset(I_AD_Table.Table_Name);
		CacheMgt.get().reset(I_AD_Column.Table_Name);
		CacheMgt.get().reset("POInfo");

		resultTableNames.add(adTable.getTableName());
	}

	public void generateForTablesIfPossible(final Iterable<I_AD_Table> adTables)
	{
		for (final I_AD_Table adTable : adTables)
		{
			trxManager.run(new TrxRunnableAdapter()
			{

				@Override
				public void run(final String localTrxName) throws Exception
				{
					generateForTable(adTable);
				}

				@Override
				public boolean doCatch(final Throwable ex) throws Throwable
				{
					logger.warn("Failed generating PK for {}", adTable, ex);
					addLog("@Error@ Generating for {}: {}", adTable.getTableName(), ex.getLocalizedMessage());
					return ROLLBACK;
				}
			});
		}
	}

	public String getSummary()
	{
		return "Generated primary keys for " + resultTableNames.size() + " table(s): " + resultTableNames;
	}

	public Properties getCtx()
	{
		return ctx;
	}

	private void addLog(final String msg, final Object... msgParameters)
	{
		Loggables.get().addLog(msg, msgParameters);
	}

	private final boolean hasColumnPK(final I_AD_Table table)
	{
		return queryBL
				.createQueryBuilder(I_AD_Column.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_Column.COLUMN_AD_Table_ID, table.getAD_Table_ID())
				.addEqualsFilter(I_AD_Column.COLUMN_IsKey, true)
				.create()
				.match();
	}

	private final I_AD_Column createColumnPK(final I_AD_Table table)
	{
		final I_AD_Column columnPK = InterfaceWrapperHelper.create(getCtx(), I_AD_Column.class, ITrx.TRXNAME_ThreadInherited);
		columnPK.setAD_Org_ID(0);
		columnPK.setAD_Table(table);
		columnPK.setEntityType(table.getEntityType());

		final I_AD_Element adElement = getCreateAD_Element(table);
		columnPK.setAD_Element(adElement);
		columnPK.setColumnName(adElement.getColumnName());
		columnPK.setName(adElement.getName());
		columnPK.setDescription(adElement.getDescription());
		columnPK.setHelp(adElement.getHelp());

		columnPK.setVersion(BigDecimal.ONE);
		columnPK.setAD_Val_Rule(null);
		columnPK.setDefaultValue(null);
		columnPK.setFieldLength(10);
		columnPK.setIsKey(true);
		columnPK.setIsParent(false);
		columnPK.setIsMandatory(true);
		columnPK.setIsTranslated(false);
		columnPK.setIsIdentifier(false);
		// columnPK.setSeqNo();
		columnPK.setIsEncrypted(X_AD_Column.ISENCRYPTED_NichtVerschluesselt);
		columnPK.setAD_Reference_ID(DisplayType.ID);
		columnPK.setAD_Reference_Value(null);
		columnPK.setIsActive(true);
		columnPK.setVFormat(null);
		columnPK.setIsUpdateable(false);
		// columnPK.setAD_Process_ID();
		// columnPK.setValueMin();
		// columnPK.setValueMax();
		columnPK.setIsSelectionColumn(false);
		columnPK.setReadOnlyLogic(null);
		// columnPK.setIsAlwaysUpdateable();
		// columnPK.setColumnSQL(null);
		columnPK.setIsAllowLogging(true);

		columnPK.setAllowZoomTo(false);

		InterfaceWrapperHelper.save(columnPK);
		addLog("@Created@ @AD_Column_ID@ " + table.getTableName() + "." + columnPK.getColumnName());

		return columnPK;
	}

	private final I_AD_Element getCreateAD_Element(final I_AD_Table table)
	{
		final String columnName = table.getTableName() + "_ID";

		//
		// Check existing
		final I_AD_Element existingElement = M_Element.get(getCtx(), columnName);
		if (existingElement != null)
		{
			return existingElement;
		}

		//
		// Create new
		final I_AD_Element element = new M_Element(getCtx(), columnName, table.getEntityType(), ITrx.TRXNAME_ThreadInherited);
		element.setColumnName(columnName);
		element.setName(table.getName());
		element.setPrintName(table.getName());
		element.setEntityType(table.getEntityType());
		InterfaceWrapperHelper.save(element);
		addLog("@Created@ @AD_Element_ID@: " + element.getColumnName());
		return element;
	}

	private void createColumnPK_DDL(final I_AD_Column columnPK)
	{
		final String tableName = columnPK.getAD_Table().getTableName();
		final String pkColumnName = columnPK.getColumnName();
		final String pkName = (tableName + "_pkey").toLowerCase();
		final String pkNameAlt = (tableName + "_key").toLowerCase(); // some tables also have this PK name

		final String sqlDefaultValue = DB.TO_TABLESEQUENCE_NEXTVAL(tableName);

		executeDDL("ALTER TABLE " + tableName + " ADD COLUMN " + pkColumnName + " numeric(10,0) NOT NULL DEFAULT " + sqlDefaultValue);

		executeDDL("ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + pkName);
		executeDDL("ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + pkNameAlt);

		executeDDL("ALTER TABLE " + tableName + " ADD CONSTRAINT " + pkName + " PRIMARY KEY (" + pkColumnName + ")");
	}

	private final void executeDDL(final String sql)
	{
		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		addLog("DDL: " + sql);
	}

	private final void addToTabs(final I_AD_Column column)
	{
		final int adTableId = column.getAD_Table_ID();

		queryBL
				.createQueryBuilder(I_AD_Tab.class, column)
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Table_ID, adTableId)
				.create()
				.stream(I_AD_Tab.class)
				.forEach(tab -> createAD_Field(tab, column));
	}

	private final I_AD_Field createAD_Field(final I_AD_Tab tab, final I_AD_Column column)
	{
		final String fieldEntityType = null; // auto

		I_AD_Field field = null;
		try
		{
			field = TabCreateFields.createADField(tab, column, fieldEntityType);

			// log
			final I_AD_Window window = tab.getAD_Window();
			addLog("@Created@ " + window + " -> " + tab + " -> " + field);

		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating AD_Field for {} in {}", column, tab, ex);
			addLog("@Error@ creating AD_Field for {} in {}: {}", column, tab, ex.getLocalizedMessage());
		}

		return field;
	}
}