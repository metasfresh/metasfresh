package org.adempiere.ad.column.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MColumn;
import org.compiere.util.DisplayType;

import de.metas.adempiere.service.IColumnBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Disallow logging for Updated, UpdatedBy, Created, CreatedeBy
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Callout(I_AD_Column.class)
public class AD_Column
{

	public static final AD_Column instance = new AD_Column();

	public static final String ENTITYTYPE_Dictionary = "D";

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_ColumnName })
	public void onColumnName(final I_AD_Column column, final ICalloutField field)
	{
		if (column == null || Check.isEmpty(column.getColumnName(), true))
		{
			return;
		}

		final String columnName = column.getColumnName();
		column.setIsAllowLogging(Services.get(IColumnBL.class).getDefaultAllowLoggingByColumnName(columnName));
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_ColumnName })
	public void onColumnName(final I_AD_Column column)
	{
		if (MColumn.isSuggestSelectionColumn(column.getColumnName(), true))
		{
			column.setIsSelectionColumn(true);
		}
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_AD_Element_ID })
	public void onAD_Element_ID(final I_AD_Column column)
	{
		if (column.getAD_Element_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final I_AD_Element element = column.getAD_Element();

		final String elementColumnName = element.getColumnName();
		Check.assumeNotNull(elementColumnName, "The element {} does not have a column name set", element);

		column.setColumnName(elementColumnName);
		column.setName(element.getName());
		column.setDescription(element.getDescription());
		column.setHelp(element.getHelp());
		column.setIsCalculated(Services.get(IColumnBL.class).getDefaultIsCalculatedByColumnName(elementColumnName));

		final I_AD_Table table = column.getAD_Table();
		String entityType = table.getEntityType();

		if (ENTITYTYPE_Dictionary.equals(entityType))
		{
			entityType = element.getEntityType();
		}

		column.setEntityType(entityType);
		setTypeAndLength(column);

		if ("DocumentNo".equals(elementColumnName)
				|| "Value".equals(elementColumnName))
		{
			column.setIsUseDocSequence(true);
		}

	}

	/**
	 *
	 * @param column
	 * @see org.compiere.process.TableCreateColumns#addTableColumn
	 */
	public static void setTypeAndLength(final I_AD_Column column)
	{
		final String columnName = column.getColumnName();
		final int previousDisplayType = column.getAD_Reference_ID();
		final I_AD_Table table = column.getAD_Table();
		final String tableName = table.getTableName();
		// Key
		if (columnName.equalsIgnoreCase(tableName + "_ID"))
		{
			column.setIsKey(true);
			column.setAD_Reference_ID(DisplayType.ID);
			column.setIsUpdateable(false);
			column.setFieldLength(10);
		}
		// Account
		// bug [ 1637912 ]
		else if (columnName.toUpperCase().endsWith("_ACCT"))
		{
			// && size == 10)
			column.setAD_Reference_ID(DisplayType.Account);
			column.setFieldLength(10);
		}
		// Account
		else if (columnName.equalsIgnoreCase("C_Location_ID"))
		{
			column.setAD_Reference_ID(DisplayType.Location);
			column.setFieldLength(10);
		}
		// Product Attribute
		else if (columnName.equalsIgnoreCase("M_AttributeSetInstance_ID"))
		{
			column.setAD_Reference_ID(DisplayType.PAttribute);
			column.setFieldLength(10);
		}
		// SalesRep_ID (=User)
		else if (columnName.equalsIgnoreCase("SalesRep_ID"))
		{
			column.setAD_Reference_ID(DisplayType.Table);
			column.setAD_Reference_Value_ID(190);
			column.setFieldLength(10);
		}
		// ID
		else if (columnName.toUpperCase().endsWith("_ID"))
		{
			column.setAD_Reference_ID(DisplayType.TableDir);
			column.setFieldLength(10);
		}
		// Date
		else if (columnName.equalsIgnoreCase("Created")
				|| columnName.equalsIgnoreCase("Updated"))
		{
			column.setAD_Reference_ID(DisplayType.DateTime);
			column.setFieldLength(7);
		}
		else if (columnName.indexOf("Date") >= 0)
		{
			if (!DisplayType.isDate(previousDisplayType))
			{
				column.setAD_Reference_ID(DisplayType.Date);
			}
			column.setFieldLength(7);
		}
		// CreatedBy/UpdatedBy (=User)
		else if (columnName.equalsIgnoreCase("CreatedBy")
				|| columnName.equalsIgnoreCase("UpdatedBy"))
		{
			column.setAD_Reference_ID(DisplayType.Table);
			column.setAD_Reference_Value_ID(110);
			column.setIsUpdateable(false);
			column.setFieldLength(10);
		}
		// Entity Type
		else if (columnName.equalsIgnoreCase("EntityType"))
		{
			column.setAD_Reference_ID(DisplayType.Table);
			column.setAD_Reference_Value_ID(389);
		}
		// // CLOB
		// else if (dataType == Types.CLOB)
		// column.setAD_Reference_ID (DisplayType.TextLong);
		// // BLOB
		// else if (dataType == Types.BLOB)
		// column.setAD_Reference_ID (DisplayType.Binary);
		// Amount
		else if (columnName.toUpperCase().indexOf("AMT") != -1)
		{
			column.setAD_Reference_ID(DisplayType.Amount);
			column.setFieldLength(10);
			column.setIsMandatory(true);
		}
		// Qty
		else if (columnName.toUpperCase().indexOf("QTY") != -1)
		{
			column.setAD_Reference_ID(DisplayType.Quantity);
			column.setFieldLength(10);
		}
		// Boolean
		else if (columnName.toUpperCase().startsWith("IS"))
		{
			column.setAD_Reference_ID(DisplayType.YesNo);
			column.setFieldLength(1);
			column.setIsMandatory(true);
		}
		// // List
		// else if (size < 4 && dataType == Types.CHAR)
		// column.setAD_Reference_ID (DisplayType.List);
		// Name, DocumentNo
		else if (columnName.equalsIgnoreCase("Name")
				|| columnName.equals("DocumentNo"))
		{
			column.setAD_Reference_ID(DisplayType.String);
			column.setIsIdentifier(true);
			column.setSeqNo(1);
			column.setFieldLength(40);
		}
		// // String, Text
		// else if (dataType == Types.CHAR || dataType == Types.VARCHAR
		// || typeName.startsWith ("NVAR")
		// || typeName.startsWith ("NCHAR"))
		// {
		// if (typeName.startsWith("N")) // MultiByte
		// size /= 2;
		// if (size > 255)
		// column.setAD_Reference_ID (DisplayType.Text);
		// else
		// column.setAD_Reference_ID (DisplayType.String);
		// }
		// // Number
		// else if (dataType == Types.INTEGER || dataType == Types.SMALLINT
		// || dataType == Types.DECIMAL || dataType == Types.NUMERIC)
		// {
		// if (size == 10)
		// column.setAD_Reference_ID (DisplayType.Integer);
		// else
		// column.setAD_Reference_ID (DisplayType.Number);
		// }
		// ??
		else
		{
			if (column.getAD_Reference_ID() <= 0)
				column.setAD_Reference_ID(DisplayType.String);
		}

		// column.setFieldLength (size);
		if (column.isUpdateable()
				&& (table.isView()
						|| columnName.equalsIgnoreCase("AD_Client_ID")
						|| columnName.equalsIgnoreCase("AD_Org_ID")
						|| columnName.toUpperCase().startsWith("CREATED")
						|| columnName.toUpperCase().equals("UPDATED")))
		{
			column.setIsUpdateable(false);
		}
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_AD_Reference_ID })
	public void onAD_Reference(final I_AD_Column column)
	{
		final int referenceId = column.getAD_Reference_ID();

		if (referenceId == 0)
		{
			// not yet set
			return;
		}

		if (referenceId == DisplayType.Integer)
		{
			column.setIsMandatory(true);

			column.setDefaultValue("0");
		}

		else if (referenceId == DisplayType.YesNo)
		{

			column.setIsMandatory(true);

			final String columnName = column.getColumnName();

			if (columnName == null)
			{
				// nothing to do
			}

			if (I_AD_Column.COLUMNNAME_IsActive.equals(columnName))
			{
				column.setDefaultValue("Y");
			}
			else
			{
				column.setDefaultValue("N");
			}
		}
	}
}
