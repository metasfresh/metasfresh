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
package org.compiere.apps.search;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.adempiere.util.text.TokenizedStringBuilder;
import org.compiere.apps.ADialog;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.I_AD_Field;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generic Table Search
 * <p>
 * Change log:
 * <ul>
 * <li>2007-02-14 - teo_sarca - [ 1659737 ] InfoGeneral not working with virtual columns
 * </ul>
 * <p>
 *    @author Jorg Janke
 *    @version $Id: InfoGeneral.java,v 1.3 2006/10/06 00:42:38 jjanke Exp $
 */
public class InfoGeneral extends Info
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7588425490485071820L;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Detail Protected Constructor.
	 *
	 * @param frame          parent
	 * @param modal          modal
	 * @param WindowNo       window no
	 * @param value          QueryValue
	 * @param tableName      table name
	 * @param keyColumn      key column (ignored)
	 * @param multiSelection multiple selections
	 * @param whereClause    where clause
	 */
	protected InfoGeneral(Frame frame, boolean modal, int WindowNo, String value,
						  String tableName, String keyColumn,
						  boolean multiSelection, String whereClause)
	{
		super(frame, modal, WindowNo, tableName, keyColumn, multiSelection, whereClause);

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		log.info("{} - {} - {}", tableName, keyColumn, whereClause);
		setTitle(msgBL.getMsg(Env.getCtx(), "Info"));

		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		getWindow().setMinimumSize(new Dimension(screenSize.width - 200, screenSize.height - 200));

		//
		statInit();
		p_loadedOK = initInfo();
		//
		int no = p_table.getRowCount();

		setStatusLine(no + " " + msgBL.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		//	Focus
		textField1.setValue(value);
		textField1.requestFocus();
		if (value != null && !value.isEmpty())
			executeQueryOnInit();
	}    //	InfoGeneral

	/**
	 * String Array of Column Info
	 */
	private Info_Column[] m_generalLayout;
	/**
	 * list of query columns
	 */
	private final ArrayList<FilterByColumn> filterByColumns = new ArrayList<>();

	//  Static data
	private final CLabel label1 = new CLabel();
	private final CTextField textField1 = new CTextField(10);
	private final CLabel label2 = new CLabel();
	private final CTextField textField2 = new CTextField(10);
	private final CLabel label3 = new CLabel();
	private final CTextField textField3 = new CTextField(10);
	private final CLabel label4 = new CLabel();
	private final CTextField textField4 = new CTextField(10);

	/**
	 * Static Setup - add fields to parameterPanel (GridLayout)
	 */
	private void statInit()
	{
		label1.setLabelFor(textField1);
		label1.setText("Label1");
		label1.setHorizontalAlignment(JLabel.LEADING);
		textField1.setBackground(AdempierePLAF.getInfoBackground());
		label2.setLabelFor(textField2);
		label2.setText("Label2");
		label2.setHorizontalAlignment(JLabel.LEADING);
		textField2.setBackground(AdempierePLAF.getInfoBackground());
		label3.setLabelFor(textField3);
		label3.setText("Label3");
		label3.setHorizontalAlignment(JLabel.LEADING);
		textField3.setBackground(AdempierePLAF.getInfoBackground());
		label4.setLabelFor(textField4);
		label4.setText("Label4");
		label4.setHorizontalAlignment(JLabel.LEADING);
		textField4.setBackground(AdempierePLAF.getInfoBackground());
		//
		parameterPanel.setLayout(new ALayout());
		parameterPanel.add(label1, new ALayoutConstraint(0, 0));
		parameterPanel.add(label2, null);
		parameterPanel.add(label3, null);
		parameterPanel.add(label4, null);
		//
		parameterPanel.add(textField1, new ALayoutConstraint(1, 0));
		parameterPanel.add(textField2, null);
		parameterPanel.add(textField3, null);
		parameterPanel.add(textField4, null);
	}    //	statInit

	/**
	 * General Init
	 *
	 * @return true, if success
	 */
	private boolean initInfo()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		initInfoTable();

		//  prepare table
		final StringBuilder where = new StringBuilder("IsActive='Y'");
		if (!p_whereClause.isEmpty())
			where.append(" AND ").append(p_whereClause);
		final String sqlOrderBy = buildSqlOrderBy();
		prepareTable(m_generalLayout,
				getTableName(),
				where.toString(),
				sqlOrderBy);

		//	Set & enable Fields
		label1.setText(msgBL.translate(Env.getCtx(), filterByColumns.get(0).getColumnName()));
		textField1.addActionListener(this);
		if (filterByColumns.size() > 1)
		{
			label2.setText(Services.get(IMsgBL.class).translate(Env.getCtx(), filterByColumns.get(1).getColumnName()));
			textField2.addActionListener(this);
		}
		else
		{
			label2.setVisible(false);
			textField2.setVisible(false);
		}
		if (filterByColumns.size() > 2)
		{
			label3.setText(Services.get(IMsgBL.class).translate(Env.getCtx(), filterByColumns.get(2).getColumnName()));
			textField3.addActionListener(this);
		}
		else
		{
			label3.setVisible(false);
			textField3.setVisible(false);
		}
		if (filterByColumns.size() > 3)
		{
			label4.setText(Services.get(IMsgBL.class).translate(Env.getCtx(), filterByColumns.get(3).getColumnName()));
			textField4.addActionListener(this);
		}
		else
		{
			label4.setVisible(false);
			textField4.setVisible(false);
		}
		return true;
	}    //	initInfo

	private String buildSqlOrderBy()
	{
		final List<Info_Column> sortColumns = new ArrayList<>();
		for (Info_Column infoColumn : m_generalLayout)
		{
			final int sortNo = infoColumn.getSortNo();
			if (sortNo == 0)
			{
				continue;
			}

			sortColumns.add(infoColumn);
		}

		sortColumns.sort(Comparator.comparingInt(o -> Math.abs(o.getSortNo())));

		if (sortColumns.isEmpty())
		{
			return "2"; // FIXME: legacy hardcoded column
		}

		final TokenizedStringBuilder sqlOrderBy = new TokenizedStringBuilder(", ");
		for (final Info_Column sortColumn : sortColumns)
		{
			final String columnSql = sortColumn.getColSQL();
			final String sortDirection = sortColumn.getSortNo() >= 0 ? "ASC" : "DESC";
			if (Check.isEmpty(columnSql, true))
			{
				// shall not happen
				continue;
			}

			sqlOrderBy.append(columnSql + " " + sortDirection);
		}

		return sqlOrderBy.toString();
	}

	/**
	 * Init info with Table.
	 * - find QueryColumns (Value, Name, ..)
	 * - build gridController & columsn
	 */
	private void initInfoTable()
	{
		loadFilterByColumns();
		loadGridColumns();
		setTitle();
	}

	private void loadFilterByColumns()
	{
		final String tableName = getTableName();

		final String sql = "SELECT c.ColumnName, t.AD_Table_ID, t.TableName, c.ColumnSql "
				+ "FROM AD_Table t"
				+ " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID)"
				+ "WHERE c.AD_Reference_ID=10"
				+ " AND t.TableName=?"    //	#1
				//	Displayed in Window
				+ " AND EXISTS (SELECT * FROM AD_Field f "
				+ "WHERE f.AD_Column_ID=c.AD_Column_ID"
				+ " AND f.IsDisplayed='Y' AND f.IsEncrypted='N' AND f.ObscureType IS NULL) "
				//
				+ "ORDER BY c.IsIdentifier DESC, c.SeqNo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String columnName = rs.getString(1);
				final String columnSql = StringUtils.trimBlankToOptional(rs.getString(4)).orElse(columnName);
				filterByColumns.add(FilterByColumn.builder()
						.columnName(columnName)
						.columnSql(columnSql)
						.build());
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (tableName.equalsIgnoreCase(I_AD_Field.Table_Name)
				&& filterByColumns.stream().noneMatch(c -> c.getColumnName().equals("ColumnName")))
		{
			filterByColumns.add(0, FilterByColumn.builder()
					.columnName("ColumnName")
					.columnSql("(SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=AD_Field.AD_Column_ID)")
					.build());
		}

		//	Only 4 Query Columns
		while (filterByColumns.size() > 4)
		{
			filterByColumns.remove(filterByColumns.size() - 1);
			filterByColumns.remove(filterByColumns.size() - 1);
		}

		if (tableName.equalsIgnoreCase(I_AD_Field.Table_Name))
		{
			filterByColumns.sort(FixedOrderByKeyComparator.of(
					"*",
					ImmutableList.of(
							"ColumnName",
							I_AD_Field.COLUMNNAME_Name,
							"*"
					),
					FilterByColumn::getColumnName));
		}
	}

	private void setTitle()
	{
		String title = msgBL.translate(Env.getCtx(), getTableName() + "_ID");  //  best bet
		if (title.endsWith("_ID"))
		{
			title = msgBL.translate(Env.getCtx(), getTableName());
		}             //  second best bet
		setTitle(getTitle() + " " + title);
	}

	private void loadGridColumns()
	{
		final String tableName = getTableName();
		final ArrayList<Info_Column> list = new ArrayList<>();

		final String sql = "SELECT c.ColumnName, c.AD_Reference_ID, c.IsKey, f.IsDisplayed, c.AD_Reference_Value_ID, c.ColumnSql"
				+ ", f." + I_AD_Field.COLUMNNAME_SortNo // 7
				+ " FROM AD_Column c"
				+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID)"
				+ " INNER JOIN AD_Tab tab ON (t.AD_Window_ID=tab.AD_Window_ID)"
				+ " INNER JOIN AD_Field f ON (tab.AD_Tab_ID=f.AD_Tab_ID AND f.AD_Column_ID=c.AD_Column_ID) "
				+ "WHERE t.TableName=? "
				+ " AND (c.IsKey='Y' OR "
				//	+ " (f.IsDisplayed='Y' AND f.IsEncrypted='N' AND f.ObscureType IS NULL)) "
				+ " (f.IsEncrypted='N' AND f.ObscureType IS NULL)) "
				+ " AND c.ColumnName NOT IN ('AD_Client_ID', 'AD_Org_ID', 'Created', 'CreatedBy', 'Updated', 'UpdatedBy') "
				+ "ORDER BY c.IsKey DESC, f.SeqNo";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();

			final AtomicInteger nextSeqNo = new AtomicInteger(10);
			while (rs.next())
			{
				String columnName = rs.getString(1);
				final int displayType;
				if ("AD_Language".equals(columnName) || "EntityType".equals(columnName))
				{
					// this column might be a table reference, but the value is a string, so we override the display type here.
					displayType = DisplayType.String;
				}
				else
				{
					displayType = rs.getInt(2);
				}
				boolean isKey = rs.getString(3).equals("Y");
				boolean isDisplayed = rs.getString(4).equals("Y");
				int AD_Reference_Value_ID = rs.getInt(5);
				// teo_sarca
				String columnSql = rs.getString(6);
				if (columnSql == null || columnSql.isEmpty())
				{
					columnSql = columnName;
				}
				//  Default
				StringBuilder colSql = new StringBuilder(columnSql);
				final int sortNo = rs.getInt(I_AD_Field.COLUMNNAME_SortNo);
				Class<?> colClass = null;
				String idColSQL = null;
				//
				if (isKey)
					colClass = IDColumn.class;
				else if (!isDisplayed)
					;
				else if (displayType == DisplayType.YesNo)
					colClass = Boolean.class;
				else if (displayType == DisplayType.Amount)
					colClass = BigDecimal.class;
				else if (displayType == DisplayType.Number || displayType == DisplayType.Quantity)
					colClass = Double.class;
				else if (displayType == DisplayType.Integer)
					colClass = Integer.class;
				else if (displayType == DisplayType.String || displayType == DisplayType.Text || displayType == DisplayType.Memo)
					colClass = String.class;
				else if (DisplayType.isDate(displayType))
					colClass = Timestamp.class;
					//  ignore Binary, Button, ID, RowID
					//	else if (displayType == DisplayType.Account)
					//	else if (displayType == DisplayType.Location)
					//	else if (displayType == DisplayType.Locator)
				else if (displayType == DisplayType.List)
				{
					if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
						colSql = new StringBuilder("(SELECT l.Name FROM AD_Ref_List l WHERE l.AD_Reference_ID=")
								.append(AD_Reference_Value_ID).append(" AND l.Value=").append(columnSql)
								.append(")")
								// task 09248
								// Do not build the columns with alias, because later they might be used is non select clauses and cause problems
								//.append(" AS ").append(columnName)
								;
					else
						colSql = new StringBuilder("(SELECT t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t "
								+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.AD_Reference_ID=")
								.append(AD_Reference_Value_ID).append(" AND l.Value=").append(columnSql)
								.append(" AND t.AD_Language='").append(Env.getAD_Language(Env.getCtx()))
								.append("')")
								// task 09248
								// Do not build the columns with alias, because later they might be used is non select clauses and cause problems
								//.append(" AS ").append(columnName)
								;
					colClass = String.class;
				}
				else if (displayType == DisplayType.TableDir || (displayType == DisplayType.Search && AD_Reference_Value_ID <= 0))
				{
					final Language language = Env.getLanguage(Env.getCtx());
					colSql = new StringBuilder("(")
							.append(MLookupFactory.newInstance().getLookup_TableDirEmbed(
									LanguageInfo.ofSpecificLanguage(language), // language
									columnName, // ColumnName
									tableName, // BaseTable
									columnSql // BaseColumn
							))
							.append(")");
					idColSQL = columnSql;
					colClass = KeyNamePair.class;
				}
				else if (displayType == DisplayType.Table || (displayType == DisplayType.Search && AD_Reference_Value_ID > 0))
				{
					final Language language = Env.getLanguage(Env.getCtx());
					colSql = new StringBuilder("(")
							.append(MLookupFactory.newInstance().getLookup_TableEmbed(
											LanguageInfo.ofSpecificLanguage(language), // language
											columnSql, // BaseColumn,
											tableName, // BaseTable,
											ReferenceId.ofRepoId(AD_Reference_Value_ID) // AD_Reference_Value_ID
									)
							)
							.append(")");
					idColSQL = columnSql;
					colClass = KeyNamePair.class;
				}
				//	else if (displayType == DisplayType.Table)
				//	else if (displayType == DisplayType.TableDir || displayType == DisplayType.Search)

				if (colClass != null)
				{
					final Info_Column infoColumn = new Info_Column(columnName, msgBL.translate(Env.getCtx(), columnName), colSql.toString(), colClass, idColSQL);
					infoColumn.setSeqNo(nextSeqNo.getAndAdd(10));
					infoColumn.setSortNo(sortNo);
					list.add(infoColumn);
					log.trace("Added Column={}", columnName);

				}
				else
				{
					log.trace("Not Added Column={}", columnName);
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (list.isEmpty())
		{
			final int p_WindowNo = getWindowNo();
			ADialog.error(p_WindowNo, getWindow(), "Error", "No Info Columns");
			log.error("No Info for {} - {}", tableName, sql);
			return;
		}
		log.trace("InfoColumns #{}", list.size());

		list.sort(getColumnsOrderBy());

		//  Convert ArrayList to Array
		m_generalLayout = new Info_Column[list.size()];
		list.toArray(m_generalLayout);
	}

	@NotNull
	private Comparator<Info_Column> getColumnsOrderBy()
	{
		if (I_AD_Field.Table_Name.equalsIgnoreCase(getTableName()))
		{
			return FixedOrderByKeyComparator.of(
							"*",
							ImmutableList.of(
									I_AD_Field.COLUMNNAME_AD_Column_ID,
									I_AD_Field.COLUMNNAME_Name,
									I_AD_Field.COLUMNNAME_EntityType,
									"*",
									I_AD_Field.COLUMNNAME_AD_Tab_ID
							),
							Info_Column::getColumnName)
					.thenComparing(Info_Column::getSeqNo);
		}
		else
		{
			return Comparator.comparing(Info_Column::getSeqNo);
		}
	}

	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters.
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *    @return where clause
	 */
	@Override
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();
		addSQLWhere(sql, 0, textField1.getText().toUpperCase());
		addSQLWhere(sql, 1, textField2.getText().toUpperCase());
		addSQLWhere(sql, 2, textField3.getText().toUpperCase());
		addSQLWhere(sql, 3, textField4.getText().toUpperCase());
		return sql.toString();
	}    //	getSQLWhere

	/**
	 * Add directly Query as Strings
	 *
	 * @param sql   sql buffer
	 * @param index index
	 * @param value value
	 */
	private void addSQLWhere(StringBuffer sql, int index, String value)
	{
		if (!(value.isEmpty() || value.equals("%")) && index < filterByColumns.size())
		{
			// Angelo Dabala' (genied) nectosoft: [2893220] avoid to append string parameters directly because of special chars like quote(s)
			sql.append(" AND UPPER(").append(filterByColumns.get(index).getColumnSql()).append(") LIKE ?");
		}
	}    //	addSQLWhere

	/**
	 * Get SQL WHERE parameter
	 *
	 * @param f field
	 * @return sql part
	 */
	private static String getSQLText(CTextField f)
	{
		String s = f.getText().toUpperCase();
		if (!s.contains("%"))
		{
			if (!s.endsWith("%"))
			{
				s += "%";
			}
			if (!s.startsWith("%"))
			{
				s = "%" + s;
			}
		}
		return s;
	}   //  getSQLText

	/**
	 * Set Parameters for Query.
	 * (as defined in getSQLWhere)
	 *
	 * @param pstmt    statement
	 * @param forCount for counting records
	 */
	@Override
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		if (!textField1.getText().isEmpty())
			pstmt.setString(index++, getSQLText(textField1));
		if (!textField2.getText().isEmpty())
			pstmt.setString(index++, getSQLText(textField2));
		if (!textField3.getText().isEmpty())
			pstmt.setString(index++, getSQLText(textField3));
		if (!textField4.getText().isEmpty())
			pstmt.setString(index++, getSQLText(textField4));
	}   //  setParameters

	//
	//
	//

	@Value
	@Builder
	private static class FilterByColumn
	{
		@NotNull String columnName;
		@NotNull String columnSql;
	}
}    //	InfoGeneral
