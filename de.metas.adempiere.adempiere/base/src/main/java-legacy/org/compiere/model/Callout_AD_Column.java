/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.model;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;

/**
 * @author teo_sarca
 * 
 */
public class Callout_AD_Column extends CalloutEngine
{
	public static final String ENTITYTYPE_Dictionary = "D";

	public String onColumnName(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_AD_Column column = InterfaceWrapperHelper.create(mTab, I_AD_Column.class);
		if (MColumn.isSuggestSelectionColumn(column.getColumnName(), true))
		{
			column.setIsSelectionColumn(true);
		}
		return NO_ERROR;
	}

	public String onAD_Element_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_AD_Column column = InterfaceWrapperHelper.create(mTab, I_AD_Column.class);
		if (column.getAD_Element_ID() <= 0)
			return "";
		I_AD_Element element = column.getAD_Element();
		column.setColumnName(element.getColumnName());
		column.setName(element.getName());
		column.setDescription(element.getDescription());
		column.setHelp(element.getHelp());

		I_AD_Table table = column.getAD_Table();
		String entityType = table.getEntityType();
		if (ENTITYTYPE_Dictionary.equals(entityType))
			entityType = element.getEntityType();
		
		column.setEntityType(entityType);
		setTypeAndLength(column);

		//
		return "";
	}

	/**
	 * 
	 * @param column
	 * @see org.compiere.process.TableCreateColumns#addTableColumn
	 */
	public static void setTypeAndLength(I_AD_Column column)
	{
		final String columnName = column.getColumnName();
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
		else if (
		// dataType == Types.DATE || dataType == Types.TIME
		// || dataType == Types.TIMESTAMP
		// || columnName.toUpperCase().indexOf("DATE") != -1
		columnName.equalsIgnoreCase("Created")
				|| columnName.equalsIgnoreCase("Updated"))
		{
			column.setAD_Reference_ID(DisplayType.DateTime);
			column.setFieldLength(7);
		}
		else if (columnName.indexOf("Date") >= 0)
		{
			column.setAD_Reference_ID(DisplayType.Date);
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

}
