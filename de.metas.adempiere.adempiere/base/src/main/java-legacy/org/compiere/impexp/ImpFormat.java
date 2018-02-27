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
package org.compiere.impexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_ImpFormat;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Import Format a Row
 *
 * @author Jorg Janke
 * @version $Id: ImpFormat.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public final class ImpFormat
{
	/** Logger */
	private static Logger log = LogManager.getLogger(ImpFormat.class);

	@Getter
	private final String name;
	private final String formatType;
	@Getter
	private final boolean multiLine;

	/** The Table to be imported */
	private int m_AD_Table_ID;
	private String m_tableName;
	private String m_tablePK;
	private String m_tableUnique1;
	private String m_tableUnique2;
	private String m_tableUniqueParent;
	private String m_tableUniqueChild;
	private boolean hasDataImportIdColumn;
	//
	private ArrayList<ImpFormatRow> m_rows = new ArrayList<>();

	@Builder
	private ImpFormat(
			@NonNull final String name,
			final int adTableId,
			@NonNull final String formatType,
			final boolean multiLine)
	{
		Check.assumeNotEmpty(name, "name is not empty");

		this.name = name;

		if (formatType.equals(FORMATTYPE_FIXED) || formatType.equals(FORMATTYPE_COMMA)
				|| formatType.equals(FORMATTYPE_TAB) || formatType.equals(FORMATTYPE_XML))
		{
			this.formatType = formatType;
		}
		else
		{
			throw new IllegalArgumentException("FormatType must be F/C/T/X");
		}

		this.multiLine = multiLine;

		setTable(adTableId);
	}

	public String getTableName()
	{
		return m_tableName;
	}

	/**
	 * Import Table
	 * 
	 * @param AD_Table_ID table
	 */
	private void setTable(final int AD_Table_ID)
	{
		m_AD_Table_ID = AD_Table_ID;

		final POInfo poInfo = POInfo.getPOInfo(AD_Table_ID);
		Check.assumeNotNull(poInfo, "poInfo is not null for AD_Table_ID={}", AD_Table_ID);
		m_tableName = poInfo.getTableName();

		m_tablePK = poInfo.getKeyColumnName();
		if (m_tablePK == null)
		{
			throw new AdempiereException("Table " + m_tableName + " has not primary key");
		}

		hasDataImportIdColumn = poInfo.hasColumnName(I_C_DataImport.COLUMNNAME_C_DataImport_ID);

		// Set Additional Table Info
		m_tableUnique1 = "";
		m_tableUnique2 = "";
		m_tableUniqueParent = "";
		m_tableUniqueChild = "";

		if (m_AD_Table_ID == 532)		// I_Product
		{
			m_tableUnique1 = "UPC";						// UPC = unique
			m_tableUnique2 = "Value";
			m_tableUniqueChild = "VendorProductNo";		// Vendor No may not be unique !
			m_tableUniqueParent = "BPartner_Value";		// Makes it unique
		}
		else if (m_AD_Table_ID == 533)		// I_BPartner
		{
			// gody: 20070113 to allow multiple contacts per BP
			// m_tableUnique1 = "Value"; // the key
		}
		else if (m_AD_Table_ID == 534)		// I_ElementValue
		{
			m_tableUniqueParent = "ElementName";			// the parent key
			m_tableUniqueChild = "Value";					// the key
		}
		else if (m_AD_Table_ID == 535)		// I_ReportLine
		{
			m_tableUniqueParent = "ReportLineSetName";		// the parent key
			m_tableUniqueChild = "Name";					// the key
		}
	}   // setTable

	/**
	 * Get Import Table Name
	 * 
	 * @return AD_Table_ID
	 */
	public int getAD_Table_ID()
	{
		return m_AD_Table_ID;
	}   // getAD_Table_ID

	/** Format Type - Fixed Length F */
	public static final String FORMATTYPE_FIXED = X_AD_ImpFormat.FORMATTYPE_FixedPosition;
	/** Format Type - Comma Separated C */
	public static final String FORMATTYPE_COMMA = X_AD_ImpFormat.FORMATTYPE_CommaSeparated;
	/** Format Type - Tab Separated T */
	public static final String FORMATTYPE_TAB = X_AD_ImpFormat.FORMATTYPE_TabSeparated;
	/** Format Type - XML X */
	public static final String FORMATTYPE_XML = X_AD_ImpFormat.FORMATTYPE_XML;

	/*************************************************************************
	 * Add Format Row
	 * 
	 * @param row row
	 */
	public void addRow(final ImpFormatRow row)
	{
		m_rows.add(row);
	}	// addRow

	/**
	 * Get Row
	 * 
	 * @param index index
	 * @return Import Format Row
	 */
	public ImpFormatRow getRow(final int index)
	{
		if (index >= 0 && index < m_rows.size())
			return m_rows.get(index);
		return null;
	}	// getRow

	/**
	 * Get Row Count
	 * 
	 * @return row count
	 */
	public int getRowCount()
	{
		return m_rows.size();
	}	// getRowCount

	/*************************************************************************
	 * Factory load
	 * 
	 * @param name name
	 * @return Import Format
	 * @deprecated Please use {@link #load(I_AD_ImpFormat)}
	 */
	@Deprecated
	public static ImpFormat load(final String name)
	{
		final I_AD_ImpFormat impFormatModel = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ImpFormat.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ImpFormat.COLUMNNAME_Name, name)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.create()
				.firstOnlyOrNull(I_AD_ImpFormat.class);
		if (impFormatModel == null)
		{
			return null;
		}
		return load(impFormatModel);
	}	// getFormat

	public static ImpFormat load(final int impFormatId)
	{
		final I_AD_ImpFormat impFormatModel = InterfaceWrapperHelper.loadOutOfTrx(impFormatId, I_AD_ImpFormat.class);
		return load(impFormatModel);
	}

	public static ImpFormat load(final I_AD_ImpFormat impFormatModel)
	{
		Check.assumeNotNull(impFormatModel, "impFormatModel not null");

		final ImpFormat impFormat = ImpFormat.builder()
				.name(impFormatModel.getName())
				.formatType(impFormatModel.getFormatType())
				.multiLine(impFormatModel.isMultiLine())
				.adTableId(impFormatModel.getAD_Table_ID())
				.build();
		loadRows(impFormat, impFormatModel.getAD_ImpFormat_ID());
		return impFormat;
	}

	/**
	 * Load Format Rows with ID
	 * 
	 * @param format format
	 * @param adImpFormatId id
	 */
	private static void loadRows(final ImpFormat format, final int adImpFormatId)
	{
		final List<I_AD_ImpFormat_Row> impFormatRows = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ImpFormat_Row.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_ID, adImpFormatId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_ImpFormat_Row.COLUMNNAME_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_ImpFormat_Row.class);

		for (final I_AD_ImpFormat_Row impFormatRow : impFormatRows)
		{
			if (!impFormatRow.getAD_Column().isActive())
			{
				continue;
			}

			final ImpFormatRow row = new ImpFormatRow(impFormatRow);
			format.addRow(row);
		}
	}	// loadLines

	/*************************************************************************
	 * Parse Line returns ArrayList of values
	 *
	 * @param line line
	 * @param withLabel true if with label
	 * @param trace create trace info
	 * @param ignoreEmpty - ignore empty fields
	 * @return Array of values
	 */
	@Deprecated
	public String[] parseLine(final String line, final boolean withLabel, final boolean trace, final boolean ignoreEmpty)
	{
		final List<String> result = new ArrayList<>();
		for (final ImpDataCell cell : parseDataCells(line))
		{
			if (ignoreEmpty && cell.isEmpty())
			{
				continue;
			}

			result.add(withLabel ? cell.getValueAsSQL() : cell.getValueAsString());
		}
		return result.toArray(new String[result.size()]);
	}

	public List<ImpDataCell> parseDataCells(final String line)
	{
		final List<ImpDataCell> cells = new ArrayList<>();
		// for all columns
		for (int index = 0; index < m_rows.size(); index++)
		{
			final ImpFormatRow impFormatRow = m_rows.get(index);

			// Get Data
			String cellValueRaw = null;
			if (impFormatRow.isConstant())
			{
				cellValueRaw = "Constant";
			}
			else if (formatType.equals(FORMATTYPE_FIXED))
			{
				// check length
				if (impFormatRow.getStartNo() > 0 && impFormatRow.getEndNo() <= line.length())
					cellValueRaw = line.substring(impFormatRow.getStartNo() - 1, impFormatRow.getEndNo());
			}
			else
			{
				cellValueRaw = parseFlexFormat(line, formatType, impFormatRow.getStartNo());
			}
			if (cellValueRaw == null)
			{
				cellValueRaw = "";
			}

			final ImpDataCell cell = new ImpDataCell(impFormatRow);
			cell.setValue(cellValueRaw);
			cells.add(cell);
		}	// for all columns

		return ImmutableList.copyOf(cells);
	}	// parseLine

	/**
	 * Parse flexible line format. A bit inefficient as it always starts from the start
	 *
	 * @param line the line to be parsed
	 * @param formatType Comma or Tab
	 * @param fieldNo number of field to be returned
	 * @return field in lime or ""
	 * @throws IllegalArgumentException if format unknows
	 */
	private String parseFlexFormat(final String line, final String formatType, final int fieldNo)
	{
		final char QUOTE = '"';
		// check input
		char delimiter = ' ';
		if (formatType.equals(FORMATTYPE_COMMA))
			delimiter = ',';
		else if (formatType.equals(FORMATTYPE_TAB))
			delimiter = '\t';
		else
			throw new IllegalArgumentException("ImpFormat.parseFlexFormat - unknown format: " + formatType);
		if (line == null || line.length() == 0 || fieldNo < 0)
			return "";

		// We need to read line sequentially as the fields may be delimited
		// with quotes (") when fields contain the delimiter
		// Example: "Artikel,bez","Artikel,""nr""",DEM,EUR
		// needs to result in - Artikel,bez - Artikel,"nr" - DEM - EUR
		int pos = 0;
		int length = line.length();
		for (int field = 1; field <= fieldNo && pos < length; field++)
		{
			final StringBuilder content = new StringBuilder();
			// two delimiter directly after each other
			if (line.charAt(pos) == delimiter)
			{
				pos++;
				continue;
			}
			// Handle quotes
			if (line.charAt(pos) == QUOTE)
			{
				pos++;  // move over beginning quote
				while (pos < length)
				{
					// double quote
					if (line.charAt(pos) == QUOTE && pos + 1 < length && line.charAt(pos + 1) == QUOTE)
					{
						content.append(line.charAt(pos++));
						pos++;
					}
					// end quote
					else if (line.charAt(pos) == QUOTE)
					{
						pos++;
						break;
					}
					// normal character
					else
						content.append(line.charAt(pos++));
				}
				// we should be at end of line or a delimiter
				if (pos < length && line.charAt(pos) != delimiter)
					log.info("Did not find delimiter at pos " + pos + " " + line);
				pos++;  // move over delimiter
			}
			else
			// plain copy
			{
				while (pos < length && line.charAt(pos) != delimiter)
					content.append(line.charAt(pos++));
				pos++;  // move over delimiter
			}
			if (field == fieldNo)
				return content.toString();
		}

		// nothing found
		return "";
	}   // parseFlexFormat

	@Deprecated
	public boolean updateDB(final Properties ctx, final String lineStr, final String trxName)
	{
		try
		{
			final ImpDataLine line = ImpDataLine.builder()
					.impFormat(this)
					.fileLineNo(0) // unknown
					.lineStr(lineStr)
					.build();
			updateDB(ctx, line, trxName);
			return true;
		}
		catch (Exception e)
		{
			log.error("Error while importing: " + lineStr, e);
			return false;
		}
	}

	/*************************************************************************
	 * Insert/Update Database.
	 * 
	 * @param ctx context
	 * @param line line
	 * @param trxName transaction
	 * @return
	 * @return reference to import table record
	 */
	public ITableRecordReference updateDB(final Properties ctx, final ImpDataLine line, final String trxName) throws AdempiereException
	{
		if (line == null || line.isEmpty())
		{
			throw new AdempiereException("No Line");
		}

		final List<ImpDataCell> nodes = line.getValues();
		if (nodes.isEmpty())
		{
			throw new AdempiereException("Nothing parsed");
		}

		// Standard Fields
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID;
		if (getAD_Table_ID() == Services.get(IADTableDAO.class).retrieveTableId(I_I_GLJournal.Table_Name))
		{
			AD_Org_ID = 0;
		}
		else
		{
			AD_Org_ID = Env.getAD_Org_ID(ctx);
		}
		final int UpdatedBy = Env.getAD_User_ID(ctx);

		//
		// Re-use the same ID if we already imported this record
		int importRecordId = 0;
		final ITableRecordReference importRecordRef = line.getImportRecordRef();
		if (importRecordRef != null
				&& importRecordRef.getTableName().equals(m_tableName)
				&& importRecordRef.getRecord_ID() > 0)
		{
			final int recordId = importRecordRef.getRecord_ID();

			// make sure it still exists
			final int count = DB.getSQLValue(trxName, "SELECT COUNT(1) FROM " + m_tableName + " WHERE " + m_tablePK + "=" + recordId);
			if (count == 1)
			{
				importRecordId = recordId;
			}
		}

		//
		// Check if the record is already there (by looking up by unique keys)
		if (importRecordId <= 0)
		{
			final StringBuilder sql = new StringBuilder("SELECT COUNT(*), MAX(")
					.append(m_tablePK).append(") FROM ").append(m_tableName)
					.append(" WHERE AD_Client_ID=").append(AD_Client_ID).append(" AND (");
			//
			String where1 = null;
			String where2 = null;
			String whereParentChild = null;
			for (final ImpDataCell node : nodes)
			{
				if (node.isEmptyOrZero())
				{
					continue;
				}

				final String columnName = node.getColumnName();
				if (columnName.equals(m_tableUnique1))
				{
					where1 = node.getColumnNameEqualsValueSql();
				}
				else if (columnName.equals(m_tableUnique2))
				{
					where2 = node.getColumnNameEqualsValueSql();
				}
				else if (columnName.equals(m_tableUniqueParent) || columnName.equals(m_tableUniqueChild))
				{
					if (whereParentChild == null)
						whereParentChild = node.getColumnNameEqualsValueSql();
					else
						whereParentChild += " AND " + node.getColumnNameEqualsValueSql();
				}
			}

			final StringBuilder sqlFindExistingRecord = new StringBuilder();
			if (where1 != null)
			{
				sqlFindExistingRecord.append(where1);
			}
			if (where2 != null)
			{
				if (sqlFindExistingRecord.length() > 0)
					sqlFindExistingRecord.append(" OR ");
				sqlFindExistingRecord.append(where2);
			}
			if (whereParentChild != null && whereParentChild.indexOf(" AND ") != -1)	// need to have both criteria
			{
				if (sqlFindExistingRecord.length() > 0)
					sqlFindExistingRecord.append(" OR (").append(whereParentChild).append(")");	// may have only one
				else
					sqlFindExistingRecord.append(whereParentChild);
			}
			sql.append(sqlFindExistingRecord).append(")");
			//
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				if (sqlFindExistingRecord.length() > 0)
				{
					pstmt = DB.prepareStatement(sql.toString(), trxName);
					rs = pstmt.executeQuery();
					if (rs.next())
					{
						final int count = rs.getInt(1);
						if (count == 1)
							importRecordId = rs.getInt(2);
					}
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql.toString());
			}
			finally
			{
				DB.close(rs, pstmt);
			}
		}

		//
		// Insert into import table (only mandatory columns)
		if (importRecordId <= 0)
		{
			importRecordId = DB.getNextID(ctx, m_tableName, ITrx.TRXNAME_None);		// get ID
			if (importRecordId <= 0)
			{
				throw new AdempiereException("Cannot acquire next ID for " + m_tableName);
			}

			final StringBuilder sql = new StringBuilder("INSERT INTO ")
					.append(m_tableName).append("(").append(m_tablePK).append(",")
					.append("AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive")	// StdFields
					.append(") VALUES (").append(importRecordId).append(",")
					.append(AD_Client_ID).append(",").append(AD_Org_ID)
					.append(",now(),").append(UpdatedBy).append(",now(),").append(UpdatedBy).append(",'Y'")
					.append(")");
			//
			final int no = DB.executeUpdateEx(sql.toString(), trxName);
			if (no != 1)
			{
				throw new DBException("Failed inserting the record");
			}
			log.trace("New ID={}", importRecordId);
		}
		else
		{
			log.trace("Old ID={}", importRecordId);
		}

		//
		// Update import row
		{
			final StringBuilder sqlUpdate = new StringBuilder("UPDATE ")
					.append(m_tableName).append(" SET ");
			for (final ImpDataCell node : nodes)
			{
				if (node.isEmpty())
				{
					continue;
				}
				sqlUpdate.append(node.getColumnNameEqualsValueSql()).append(",");		// column=value
			}

			if (hasDataImportIdColumn && line.getDataImportId() > 0)
			{
				sqlUpdate.append(I_C_DataImport.COLUMNNAME_C_DataImport_ID).append("=").append(line.getDataImportId()).append(",");
			}

			sqlUpdate.append("IsActive='Y',Processed='N',I_IsImported='N',Updated=now(),UpdatedBy=").append(UpdatedBy);
			sqlUpdate.append(" WHERE ").append(m_tablePK).append("=").append(importRecordId);
			//
			final int no = DB.executeUpdateEx(sqlUpdate.toString(), trxName);
			if (no != 1)
			{
				throw new DBException("Failed updating the record");
			}
		}

		return TableRecordReference.of(m_tableName, importRecordId);
	}
}	// ImpFormat
