package org.adempiere.ad.table.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.M_Element;

/**
 * Producer class used to given {@link I_AD_Column}s (see {@link #setSourceColumns(List)}) to another table (see {@link #setTargetTable(I_AD_Table)}).
 *
 * @author tsa
 *
 */
public class CopyColumnsProducer
{
	//
	// Services
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IADColumnCalloutBL adColumnCalloutBL = Services.get(IADColumnCalloutBL.class);

	private final IContextAware context;
	private ILoggable logger;
	private I_AD_Table targetTable;
	private List<I_AD_Column> sourceColumns;
	private String entityType = null;

	private int countCreated = 0;

	public CopyColumnsProducer(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		this.context = context;
	}

	private Properties getCtx()
	{
		return context.getCtx();
	}

	private String get_TrxName()
	{
		return context.getTrxName();
	}

	private void addLog(final String message)
	{
		if (logger == null)
		{
			return;
		}

		logger.addLog(message);
	}

	public void setLogger(final ILoggable logger)
	{
		this.logger = logger;
	}

	protected I_AD_Table getTargetTable()
	{
		Check.assumeNotNull(targetTable, "targetTable not null");
		return targetTable;
	}

	public void setTargetTable(final I_AD_Table targetTable)
	{
		Check.assumeNotNull(targetTable, "targetTable not null");
		this.targetTable = targetTable;
	}

	protected List<I_AD_Column> getSourceColumns()
	{
		Check.assumeNotEmpty(sourceColumns, "sourceColumns not empty");
		return sourceColumns;
	}

	public void setSourceColumns(final List<I_AD_Column> sourceColumns)
	{
		Check.assumeNotEmpty(sourceColumns, "sourceColumns not empty");
		this.sourceColumns = new ArrayList<I_AD_Column>(sourceColumns);
	}

	public void setEntityType(final String entityType)
	{
		this.entityType = entityType;
	}

	public int getCountCreated()
	{
		return countCreated;
	}

	public void create()
	{
		final I_AD_Table targetTable = getTargetTable();
		final List<I_AD_Column> sourceColumns = getSourceColumns();

		for (final I_AD_Column sourceColumn : sourceColumns)
		{
			final I_AD_Column targetColumn = copyColumn(targetTable, sourceColumn);
			if (targetColumn == null)
			{
				continue;
			}

			countCreated++;
		}
	}	// doIt

	private I_AD_Column copyColumn(final I_AD_Table targetTable, final I_AD_Column sourceColumn)
	{
		if (targetTable.getAD_Table_ID() == sourceColumn.getAD_Table_ID())
		{
			addLog("@AD_Column_ID@ " + targetTable.getTableName() + "." + sourceColumn.getColumnName() + ": same table [SKIP]");
			return null;
		}

		final String sourceTableName = adTableDAO.retrieveTableName(sourceColumn.getAD_Table_ID());

		final I_AD_Column colTarget = createColumn(targetTable);

		// special case the key -> sourceTable_ID
		if (sourceColumn.getColumnName().equals(sourceTableName + "_ID"))
		{
			final String targetColumnName = new String(targetTable.getTableName() + "_ID");
			colTarget.setColumnName(targetColumnName);
			// if the element don't exist, create it
			M_Element element = M_Element.get(getCtx(), targetColumnName);
			if (element == null)
			{
				element = new M_Element(getCtx(), targetColumnName, targetTable.getEntityType(), get_TrxName());
				if (targetColumnName.equalsIgnoreCase(targetTable.getTableName() + "_ID"))
				{
					element.setColumnName(targetTable.getTableName() + "_ID");
					element.setName(targetTable.getName());
					element.setPrintName(targetTable.getName());
				}
				element.saveEx(get_TrxName());
				addLog("@AD_Element_ID@ " + element.getColumnName() + ": @Created@"); // metas
			}
			colTarget.setAD_Element_ID(element.getAD_Element_ID());
			colTarget.setName(targetTable.getName());
			colTarget.setDescription(targetTable.getDescription());
			colTarget.setHelp(targetTable.getHelp());
		}
		else
		{
			colTarget.setColumnName(sourceColumn.getColumnName());
			colTarget.setAD_Element_ID(sourceColumn.getAD_Element_ID());
			colTarget.setName(sourceColumn.getName());
			colTarget.setDescription(sourceColumn.getDescription());
			colTarget.setHelp(sourceColumn.getHelp());
		}

		// metas: begin
		if (adTableDAO.hasColumnName(targetTable.getTableName(), colTarget.getColumnName()))
		{
			addLog("@AD_Column_ID@ " + targetTable.getTableName() + "." + colTarget.getColumnName() + ": already exists [SKIP]");
			return null;
		}
		// metas: end

		colTarget.setVersion(sourceColumn.getVersion());
		colTarget.setAD_Val_Rule_ID(sourceColumn.getAD_Val_Rule_ID());
		colTarget.setDefaultValue(sourceColumn.getDefaultValue());
		colTarget.setFieldLength(sourceColumn.getFieldLength());
		colTarget.setIsKey(sourceColumn.isKey());
		colTarget.setIsParent(sourceColumn.isParent());
		colTarget.setIsMandatory(sourceColumn.isMandatory());
		colTarget.setIsTranslated(sourceColumn.isTranslated());
		colTarget.setIsIdentifier(sourceColumn.isIdentifier());
		colTarget.setSeqNo(sourceColumn.getSeqNo());
		colTarget.setIsEncrypted(sourceColumn.getIsEncrypted());
		colTarget.setAD_Reference_ID(sourceColumn.getAD_Reference_ID());
		colTarget.setAD_Reference_Value_ID(sourceColumn.getAD_Reference_Value_ID());
		colTarget.setIsActive(sourceColumn.isActive());
		colTarget.setVFormat(sourceColumn.getVFormat());
		// colTarget.setCallout(sourceColumns[i].getCallout()); // metas
		colTarget.setIsUpdateable(sourceColumn.isUpdateable());
		colTarget.setAD_Process_ID(sourceColumn.getAD_Process_ID());
		colTarget.setValueMin(sourceColumn.getValueMin());
		colTarget.setValueMax(sourceColumn.getValueMax());
		colTarget.setIsSelectionColumn(sourceColumn.isSelectionColumn());
		colTarget.setReadOnlyLogic(sourceColumn.getReadOnlyLogic());
		colTarget.setIsSyncDatabase(sourceColumn.getIsSyncDatabase());
		colTarget.setIsAlwaysUpdateable(sourceColumn.isAlwaysUpdateable());
		colTarget.setColumnSQL(sourceColumn.getColumnSQL());

		InterfaceWrapperHelper.save(colTarget);
		addLog("@AD_Column_ID@ " + targetTable.getTableName() + "." + colTarget.getColumnName() + ": @Created@"); // metas

		adColumnCalloutBL.copyAllFrom(colTarget, sourceColumn.getAD_Column_ID()); // metas: Copy callouts

		// TODO: Copy translations

		return colTarget;
	}

	private I_AD_Column createColumn(final I_AD_Table table)
	{
		// see org.compiere.model.MColumn.MColumn(MTable)

		final I_AD_Column column = InterfaceWrapperHelper.newInstance(I_AD_Column.class, context);
		column.setAD_Org_ID(0);
		column.setAD_Table_ID(table.getAD_Table_ID());

		if (Check.isEmpty(entityType))
		{
			column.setEntityType(table.getEntityType());
		}
		else
		{
			column.setEntityType(entityType);
		}

		return column;
	}
}
