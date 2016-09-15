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
package org.compiere.acct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JComboBox;

import org.adempiere.acct.api.IAcctSchemaBL;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.model.MRefList;
import org.compiere.model.X_Fact_Acct;
import org.compiere.report.core.RColumn;
import org.compiere.report.core.RModel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Account Viewer State - maintains State information for the Account Viewer
 *
 * @author Jorg Janke
 * @version $Id: AcctViewerData.java,v 1.3 2006/08/10 01:00:27 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1748449 ] Info Account - Posting Type is not translated <li>BF [ 1778373 ] AcctViewer: data is not sorted proper
 */
class AcctViewerData
{
	public static final String COLUMNNAME_Account_ID = "Account_ID";

	// Services
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final transient IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final transient IFactAcctBL factAcctBL = Services.get(IFactAcctBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Constructor
	 *
	 * @param ctx context
	 * @param windowNo window no
	 * @param ad_Client_ID client
	 * @param ad_Table_ID table
	 */
	public AcctViewerData(final Properties ctx, final int windowNo, final int ad_Client_ID, final int ad_Org_ID, final int ad_Table_ID)
	{
		super();

		this.WindowNo = windowNo;

		int adClientIdToSet = ad_Client_ID;
		if (adClientIdToSet <= 0)
		{
			adClientIdToSet = Env.getContextAsInt(Env.getCtx(), WindowNo, "AD_Client_ID");
		}
		if (adClientIdToSet <= 0)
		{
			adClientIdToSet = Env.getContextAsInt(Env.getCtx(), "AD_Client_ID");
		}
		this.AD_Client_ID = adClientIdToSet;

		this.AD_Table_ID = ad_Table_ID;

		//
		this.acctSchemas = MAcctSchema.getClientAcctSchema(ctx, AD_Client_ID);

		final I_C_AcctSchema acctSchemaDefault = acctSchemaDAO.retrieveAcctSchema(ctx, AD_Client_ID, ad_Org_ID);
		setC_AcctSchema(acctSchemaDefault);
	}   // AcctViewerData

	/** Window */
	private final int WindowNo;
	/** Client */
	private final int AD_Client_ID;
	/** All Acct Schema */
	private final MAcctSchema[] acctSchemas;
	/** This Acct Schema */
	private I_C_AcctSchema _acctSchema = null;

	// Selection Info
	/** Document Query */
	private boolean documentQuery = false;
	/** Posting Type */
	private String PostingType = "";
	/** Organization */
	private int AD_Org_ID = 0;
	/** Date From */
	private Timestamp DateFrom = null;
	/** Date To */
	private Timestamp DateTo = null;
	private int accountId = -1;
	private int accountToId = -1;

	// Document Table Selection Info
	/** Table ID */
	private int AD_Table_ID;
	/** Record */
	private int Record_ID;

	/** Containing Column and Query */
	private Map<String, String> _whereInfo = new HashMap<>();
	/** Containing TableName and AD_Table_ID */
	private Map<String, Integer> _tableInfo = new HashMap<String, Integer>();

	// Display Info
	/** Display Qty */
	boolean displayQty = false;
	/** Display Source Currency */
	boolean displaySourceAmt = false;
	/** Display Document info */
	boolean displayDocumentInfo = false;
	/** Display Account Ending Balance */
	boolean displayEndingBalance = true;
	
	/**
	 * task 09243: flag to tell if the void/reversed docs are to be displayed or not
	 */
	boolean displayVoidDocuments = true;
	
	//
	String sortBy1 = "";
	String sortBy2 = "";
	String sortBy3 = "";
	String sortBy4 = "";
	//
	boolean group1 = false;
	boolean group2 = false;
	boolean group3 = false;
	boolean group4 = false;

	/** Leasing Columns */
	private int m_leadingColumns = 0;
	/** UserElement1 Reference */
	private String m_ref1 = null;
	/** UserElement2 Reference */
	private String m_ref2 = null;
	/** Logger */
	private static Logger log = LogManager.getLogger(AcctViewerData.class);

	/**
	 * Dispose
	 */
	public void dispose()
	{
		// acctSchemas = null;
		_acctSchema = null;
		//
		_whereInfo.clear();
		_whereInfo = null;
		//
		Env.clearWinContext(WindowNo);
	}   // dispose

	/**************************************************************************
	 * Fill Accounting Schema
	 * 
	 * @param cb JComboBox to be filled
	 */
	protected void fillAcctSchema(JComboBox<KeyNamePair> cb)
	{
		final I_C_AcctSchema acctSchemaDefault = getC_AcctSchema();

		for (int i = 0; i < acctSchemas.length; i++)
		{
			final KeyNamePair item = new KeyNamePair(acctSchemas[i].getC_AcctSchema_ID(), acctSchemas[i].getName());
			cb.addItem(item);
			if (acctSchemaDefault != null && acctSchemas[i].getC_AcctSchema_ID() == acctSchemaDefault.getC_AcctSchema_ID())
			{
				cb.setSelectedItem(item);
			}
		}
	}   // fillAcctSchema

	/**
	 * Fill Posting Type
	 * 
	 * @param cb JComboBox to be filled
	 */
	protected void fillPostingType(JComboBox<ValueNamePair> cb)
	{
		int AD_Reference_ID = 125;
		final ValueNamePair[] pt = MRefList.getList(Env.getCtx(), AD_Reference_ID, true);
		for (int i = 0; i < pt.length; i++)
		{
			cb.addItem(pt[i]);
		}
	}   // fillPostingType

	/**
	 * Fill Table with ValueNamePair (TableName, translatedKeyColumnName) and tableInfo with (TableName, AD_Table_ID) and select the entry for AD_Table_ID
	 *
	 * @param cb JComboBox to be filled
	 */
	protected void fillTable(final JComboBox<ValueNamePair> cb)
	{
		ValueNamePair select = null;
		//
		String sql = "SELECT AD_Table_ID, TableName FROM AD_Table t "
				+ "WHERE EXISTS (SELECT * FROM AD_Column c"
				+ " WHERE t.AD_Table_ID=c.AD_Table_ID AND c.ColumnName='Posted')"
				+ " AND IsView='N'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				String tableName = rs.getString(2);
				String name = msgBL.translate(Env.getCtx(), tableName + "_ID");
				//
				final ValueNamePair pp = new ValueNamePair(tableName, name);
				cb.addItem(pp);
				_tableInfo.put(tableName, new Integer(id));
				if (id == AD_Table_ID)
				{
					select = pp;
				}
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (select != null)
		{
			cb.setSelectedItem(select);
		}
	}   // fillTable

	/**
	 * Fill Org
	 *
	 * @param cb JComboBox to be filled
	 */
	protected void fillOrg(JComboBox<KeyNamePair> cb)
	{
		KeyNamePair pp = new KeyNamePair(0, "");
		cb.addItem(pp);

		String sql = "SELECT AD_Org_ID, Name FROM AD_Org WHERE AD_Client_ID=? ORDER BY Value";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				cb.addItem(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}   // fillOrg

	/**
	 * Get Button Text
	 *
	 * @param tableName table
	 * @param columnName column
	 * @param selectSQL sql
	 * @return Text on button
	 */
	protected String getButtonText(String tableName, String columnName, String selectSQL)
	{
		// SELECT (<embedded>) FROM tableName avd WHERE avd.<selectSQL>
		StringBuffer sql = new StringBuffer("SELECT (");
		LanguageInfo languageInfo = LanguageInfo.ofSpecificLanguage(Env.getCtx());
		sql.append(MLookupFactory.getLookup_TableDirEmbed(languageInfo, columnName, "avd"))
				.append(") FROM ").append(tableName).append(" avd WHERE avd.").append(selectSQL);
		String retValue = "<" + selectSQL + ">";

		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(sql.toString());
			if (rs.next())
			{
				retValue = rs.getString(1);
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
		return retValue;
	}   // getButtonText

	/**************************************************************************
	 * /** Create Query and submit
	 * 
	 * @return Report Model
	 */
	protected RModel query()
	{
		// Set Where Clause
		final StringBuilder whereClause = new StringBuilder();

		// Add Accounting Schema
		final int acctSchemaId = getC_AcctSchema_ID();
		if (acctSchemaId > 0)
		{
			whereClause.append(RModel.TABLE_ALIAS).append(".C_AcctSchema_ID=").append(acctSchemaId);
		}

		// Posting Type Selected
		if (PostingType != null && PostingType.length() > 0)
		{
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			whereClause.append(RModel.TABLE_ALIAS)
					.append(".PostingType='").append(PostingType).append("'");
		}

		//
		if (documentQuery)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(RModel.TABLE_ALIAS).append(".AD_Table_ID=").append(AD_Table_ID);
			whereClause.append(" AND ");
			whereClause.append(RModel.TABLE_ALIAS).append(".Record_ID=").append(Record_ID);
		}
		else
		{
			// get values (Queries)
			appendAdditionalWhereClauseFromWhereInfo(whereClause);

			// Add DateAcct between DateFrom and DateTo
			if (DateFrom != null || DateTo != null)
			{
				if (whereClause.length() > 0)
					whereClause.append(" AND ");
				if (DateFrom != null && DateTo != null)
					whereClause.append("TRUNC(").append(RModel.TABLE_ALIAS).append(".DateAcct) BETWEEN ")
							.append(DB.TO_DATE(DateFrom)).append(" AND ").append(DB.TO_DATE(DateTo));
				else if (DateFrom != null)
					whereClause.append("TRUNC(").append(RModel.TABLE_ALIAS).append(".DateAcct) >= ")
							.append(DB.TO_DATE(DateFrom));
				else
					// DateTo != null
					whereClause.append("TRUNC(").append(RModel.TABLE_ALIAS).append(".DateAcct) <= ")
							.append(DB.TO_DATE(DateTo));
			}

			// Add Organization
			if (AD_Org_ID > 0)
			{
				if (whereClause.length() > 0)
					whereClause.append(" AND ");
				whereClause.append(RModel.TABLE_ALIAS).append(".AD_Org_ID=").append(AD_Org_ID);
			}

			// Add Account_ID between Account_ID and AccountTo_ID
			appendAccountWhereClause(whereClause);
		}
		
		if(!isDisplayVoidDocuments())
		{
			whereClause.append( "AND ").append(RModel.TABLE_ALIAS).append(".DocStatus NOT IN (")
			.append("'").append(X_Fact_Acct.DOCSTATUS_Reversed).append("',")
			.append("'").append(X_Fact_Acct.DOCSTATUS_Closed).append("',")
			.append("'").append(X_Fact_Acct.DOCSTATUS_Voided).append("')");
		}

		RModel rm = getRModel();

		// Set Order By Clause
		StringBuffer orderClause = new StringBuffer();
		if (sortBy1.length() > 0)
		{
			RColumn col = rm.getRColumn(sortBy1);
			if (col != null)
				orderClause.append(col.getDisplaySQL());
			else
				orderClause.append(RModel.TABLE_ALIAS).append(".").append(sortBy1);
		}
		if (sortBy2.length() > 0)
		{
			if (orderClause.length() > 0)
				orderClause.append(",");
			RColumn col = rm.getRColumn(sortBy2);
			if (col != null)
				orderClause.append(col.getDisplaySQL());
			else
				orderClause.append(RModel.TABLE_ALIAS).append(".").append(sortBy2);
		}
		if (sortBy3.length() > 0)
		{
			if (orderClause.length() > 0)
				orderClause.append(",");
			RColumn col = rm.getRColumn(sortBy3);
			if (col != null)
				orderClause.append(col.getDisplaySQL());
			else
				orderClause.append(RModel.TABLE_ALIAS).append(".").append(sortBy3);
		}
		if (sortBy4.length() > 0)
		{
			if (orderClause.length() > 0)
				orderClause.append(",");
			RColumn col = rm.getRColumn(sortBy4);
			if (col != null)
				orderClause.append(col.getDisplaySQL());
			else
				orderClause.append(RModel.TABLE_ALIAS).append(".").append(sortBy4);
		}
		if (orderClause.length() == 0)
			orderClause.append(RModel.TABLE_ALIAS).append(".Fact_Acct_ID");

		// Groups
		if (group1 && sortBy1.length() > 0)
			rm.setGroup(sortBy1);
		if (group2 && sortBy2.length() > 0)
			rm.setGroup(sortBy2);
		if (group3 && sortBy3.length() > 0)
			rm.setGroup(sortBy3);
		if (group4 && sortBy4.length() > 0)
			rm.setGroup(sortBy4);

		// Totals
		rm.setFunction(I_Fact_Acct.COLUMNNAME_AmtAcctDr, RModel.FUNCTION_SUM);
		rm.setFunction(I_Fact_Acct.COLUMNNAME_AmtAcctCr, RModel.FUNCTION_SUM);
		rm.setFunction(I_Fact_Acct.COLUMNNAME_Qty, RModel.FUNCTION_ProductQtySum);

		rm.query(Env.getCtx(), whereClause.toString(), orderClause.toString());

		return rm;
	}   // query

	private void appendAccountWhereClause(final StringBuilder whereClause)
	{
		final int accountId = getAccount_ID();
		final int accountToId = getAccountTo_ID();

		// Case: no account specified
		if (accountId <= 0 && accountToId <= 0)
		{
			return;
		}
		// Case: account and accountTo specified => select all accounts in that interval
		else if (accountId > 0 && accountToId > 0)
		{
			final String sqlAccountFrom = "SELECT LPAD(trim(ev.Value), 20, '0') FROM C_ElementValue ev WHERE ev.C_ElementValue_ID=" + accountId;
			final String sqlAccountTo = "SELECT LPAD(trim(ev.Value), 20, '0') FROM C_ElementValue ev WHERE ev.C_ElementValue_ID=" + accountToId;
			final String sqlFactAcctAccount = "SELECT LPAD(trim(ev.Value), 20, '0') from C_ElementValue ev where ev.C_ElementValue_ID=" + RModel.TABLE_ALIAS + "." + COLUMNNAME_Account_ID;

			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append("(")
					.append("(").append(sqlFactAcctAccount).append(")")
					.append(" BETWEEN ")
					.append("(").append(sqlAccountFrom).append(")")
					.append(" AND ")
					.append("(").append(sqlAccountTo).append(")")
					.append(")");
		}
		//
		// Case: accountId was set only => old behaviour: select only lines with that account
		else if (accountId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(RModel.TABLE_ALIAS).append(".").append(COLUMNNAME_Account_ID).append("=").append(accountId);
		}
		//
		// Case: accountToId was set only => select only lines with that account
		else if (accountToId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(RModel.TABLE_ALIAS).append(".").append(COLUMNNAME_Account_ID).append("=").append(accountToId);
		}
	}

	/**
	 * Create Report Model (Columns)
	 * 
	 * @return Report Model
	 */
	private RModel getRModel()
	{
		Properties ctx = Env.getCtx();
		RModel rm = new RModel(I_Fact_Acct.Table_Name);
		// Add Key (Lookups)
		ArrayList<String> keys = createKeyColumns();
		int max = m_leadingColumns;
		if (max == 0)
			max = keys.size();
		for (int i = 0; i < max; i++)
		{
			String column = keys.get(i);
			if (column != null && column.startsWith("Date"))
				rm.addColumn(new RColumn(ctx, column, DisplayType.Date));
			else if (column != null && column.endsWith("_ID"))
				rm.addColumn(new RColumn(ctx, column, DisplayType.TableDir));
		}
		// Main Info
		rm.addColumn(new RColumn(ctx, "AmtAcctDr", DisplayType.Amount));
		rm.addColumn(new RColumn(ctx, "AmtAcctCr", DisplayType.Amount));
		if (displayEndingBalance)
		{
			final RColumn endingBalanceRColumn = factAcctBL.createEndingBalanceRColumn(ctx, _whereInfo);
			if (endingBalanceRColumn != null)
			{
				rm.addColumn(endingBalanceRColumn);
				rm.setFunction(endingBalanceRColumn.getColumnName(), RModel.FUNCTION_ACCOUNT_BALANCE);
			}
		}
		if (displaySourceAmt)
		{
			if (!keys.contains("DateTrx"))
			{
				rm.addColumn(new RColumn(ctx, "DateTrx", DisplayType.Date));
			}
			rm.addColumn(new RColumn(ctx, "C_Currency_ID", DisplayType.TableDir));
			rm.addColumn(new RColumn(ctx, "AmtSourceDr", DisplayType.Amount));
			rm.addColumn(new RColumn(ctx, "AmtSourceCr", DisplayType.Amount));
			rm.addColumn(new RColumn(ctx, "Rate", DisplayType.Amount,
					"COALESCE("
					+I_Fact_Acct.COLUMNNAME_CurrencyRate
					+ ", (CASE WHEN (AmtSourceDr + AmtSourceCr) = 0 THEN 0 ELSE (AmtAcctDr + AmtAcctCr) / (AmtSourceDr + AmtSourceCr) END)" // backward compatibility
					+ ")"));
		}
		// Remaining Keys
		for (int i = max; i < keys.size(); i++)
		{
			String column = keys.get(i);
			if (column != null && column.startsWith("Date"))
				rm.addColumn(new RColumn(ctx, column, DisplayType.Date));
			else if (column.startsWith("UserElement"))
			{
				if (column.indexOf('1') != -1)
					rm.addColumn(new RColumn(ctx, column, DisplayType.TableDir, null, 0, m_ref1));
				else
					rm.addColumn(new RColumn(ctx, column, DisplayType.TableDir, null, 0, m_ref2));
			}
			else if (column != null && column.endsWith("_ID"))
				rm.addColumn(new RColumn(ctx, column, DisplayType.TableDir));
		}
		// Info
		if (!keys.contains("DateAcct"))
			rm.addColumn(new RColumn(ctx, "DateAcct", DisplayType.Date));
		if (!keys.contains("C_Period_ID"))
			rm.addColumn(new RColumn(ctx, "C_Period_ID", DisplayType.TableDir));
		if (displayQty)
		{
			rm.addColumn(new RColumn(ctx, "C_UOM_ID", DisplayType.TableDir));
			rm.addColumn(new RColumn(ctx, "Qty", DisplayType.Quantity));
		}
		if (displayDocumentInfo)
		{
			rm.addColumn(new RColumn(ctx, "AD_Table_ID", DisplayType.TableDir));
			rm.addColumn(new RColumn(ctx, "Record_ID", DisplayType.ID));
			rm.addColumn(new RColumn(ctx, "Description", DisplayType.String));
			rm.addColumn(new RColumn(ctx, I_Fact_Acct.COLUMNNAME_C_Tax_ID, DisplayType.TableDir));
			rm.addColumn(new RColumn(ctx, I_Fact_Acct.COLUMNNAME_VATCode, DisplayType.String));
		}
		if (PostingType == null || PostingType.length() == 0)
			rm.addColumn(new RColumn(ctx, "PostingType", DisplayType.List, // teo_sarca, [ 1664208 ]
					RModel.TABLE_ALIAS + ".PostingType",
					X_Fact_Acct.POSTINGTYPE_AD_Reference_ID,
					null));
		
		// task 09243: add docstatus
		rm.addColumn(new RColumn(ctx, "DocStatus", DisplayType.String));
		return rm;
	}   // createRModel

	/**
	 * Create the key columns in sequence
	 * 
	 * @return List of Key Columns
	 */
	private ArrayList<String> createKeyColumns()
	{
		final ArrayList<String> columns = new ArrayList<String>();

		//
		// Add identifier so that we can load the entry later on if needed
		columns.add(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID);

		m_leadingColumns = 0;
		// Sorting Fields
		columns.add(sortBy1);
		// may add ""
		if (!columns.contains(sortBy2))
		{
			columns.add(sortBy2);
		}
		if (!columns.contains(sortBy3))
		{
			columns.add(sortBy3);
		}
		if (!columns.contains(sortBy4))
		{
			columns.add(sortBy4);
		}

		// Add Account Segments

		final I_C_AcctSchema acctSchema = getC_AcctSchema();
		final List<I_C_AcctSchema_Element> elements = acctSchemaDAO.retrieveSchemaElements(acctSchema);
		for (final I_C_AcctSchema_Element ase : elements)
		{
			if (m_leadingColumns == 0 && columns.contains("AD_Org_ID") && columns.contains(COLUMNNAME_Account_ID))
				m_leadingColumns = columns.size();
			//

			final String columnName = acctSchemaBL.getColumnName(ase);
			if (columnName.startsWith("UserElement"))
			{
				if (columnName.indexOf('1') != -1)
				{
					m_ref1 = acctSchemaBL.getDisplayColumnName(ase);
				}
				else
				{
					m_ref2 = acctSchemaBL.getDisplayColumnName(ase);
				}
			}
			if (!columns.contains(columnName))
			{
				columns.add(columnName);
			}
		}

		if (m_leadingColumns == 0 && columns.contains("AD_Org_ID") && columns.contains(COLUMNNAME_Account_ID))
		{
			m_leadingColumns = columns.size();
		}
		return columns;
	}   // createKeyColumns

	public final String getAdditionalWhereClauseInfo()
	{
		String whereClauseInfo = StringUtils.toString(_whereInfo.values(), ", ");
		if (Check.isEmpty(whereClauseInfo, true))
		{
			return "";
		}

		// NOTE: we are doing this for backward compatibility
		whereClauseInfo = ", " + whereClauseInfo;

		return whereClauseInfo;
	}

	private void appendAdditionalWhereClauseFromWhereInfo(final StringBuilder whereClause)
	{
		for (final String where : _whereInfo.values())
		{
			if (Check.isEmpty(where, true))
			{
				continue;
			}

			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}

			whereClause.append(RModel.TABLE_ALIAS).append(".").append(where);
		}
	}

	public void setAdditionalWhereClause(final String columnName, final int value)
	{
		// Skip columns for which we have special handling
		if (COLUMNNAME_Account_ID.equals(columnName))
		{
			return;
		}

		_whereInfo.put(columnName, columnName + "=" + value);
	}

	public void resetAdditionalWhereClause(final String columnName)
	{
		_whereInfo.put(columnName, "");
	}

	public int getWindowNo()
	{
		return this.WindowNo;
	}

	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	public void setC_AcctSchema(final KeyNamePair acctSchemaKNP)
	{
		if (acctSchemaKNP == null || acctSchemaKNP.getKey() <= 0)
		{
			this._acctSchema = null;
		}
		else
		{
			final int acctSchemaId = acctSchemaKNP.getKey();
			this._acctSchema = MAcctSchema.get(Env.getCtx(), acctSchemaId);
			log.info(this._acctSchema.toString());
		}
	}

	public void setC_AcctSchema(final I_C_AcctSchema acctSchema)
	{
		this._acctSchema = acctSchema;
	}

	public I_C_AcctSchema getC_AcctSchema()
	{
		return this._acctSchema;
	}

	public int getC_AcctSchema_ID()
	{
		if (this._acctSchema == null)
		{
			return 0;
		}
		else
		{
			return this._acctSchema.getC_AcctSchema_ID();
		}
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public void setAD_Table_ID(final int adTableId)
	{
		this.AD_Table_ID = adTableId;
	}

	public void setAD_Table_ID(final String tableName)
	{
		final Integer adTableId = _tableInfo.get(tableName);
		Check.assumeNotNull(adTableId, "adTableId not null");
		setAD_Table_ID(adTableId);
	}

	public int getRecord_ID()
	{
		return Record_ID;
	}

	public void setRecord_ID(final int recordId)
	{
		this.Record_ID = recordId;
	}

	public boolean isDocumentQuery()
	{
		return documentQuery;
	}

	public void setDocumentQuery(boolean documentQuery)
	{
		this.documentQuery = documentQuery;
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public void setAD_Org_ID(int aD_Org_ID)
	{
		this.AD_Org_ID = aD_Org_ID;
	}

	public Timestamp getDateFrom()
	{
		return DateFrom;
	}

	public void setDateFrom(Timestamp dateFrom)
	{
		this.DateFrom = dateFrom;
	}

	public Timestamp getDateTo()
	{
		return DateTo;
	}

	public void setDateTo(Timestamp dateTo)
	{
		this.DateTo = dateTo;
	}

	public String getPostingType()
	{
		return PostingType;
	}

	public void setPostingType(final String postingType)
	{
		this.PostingType = postingType;
	}

	public void setAccount_ID(final int accountId)
	{
		this.accountId = accountId;
	}

	public int getAccount_ID()
	{
		return this.accountId;
	}

	public void setAccountTo_ID(final int accountToId)
	{
		this.accountToId = accountToId;
	}

	public int getAccountTo_ID()
	{
		return this.accountToId;
	}

	public boolean isDisplayQty()
	{
		return displayQty;
	}

	public void setDisplayQty(boolean displayQty)
	{
		this.displayQty = displayQty;
	}

	public boolean isDisplaySourceAmt()
	{
		return displaySourceAmt;
	}

	public void setDisplaySourceAmt(boolean displaySourceAmt)
	{
		this.displaySourceAmt = displaySourceAmt;
	}

	public boolean isDisplayDocumentInfo()
	{
		return displayDocumentInfo;
	}

	public void setDisplayDocumentInfo(boolean displayDocumentInfo)
	{
		this.displayDocumentInfo = displayDocumentInfo;
	}

	public boolean isDisplayEndingBalance()
	{
		return displayEndingBalance;
	}

	public void setDisplayEndingBalance(boolean displayEndingBalance)
	{
		this.displayEndingBalance = displayEndingBalance;
	}

	public void setDisplayVoidDocuments(boolean isDisplayVoidDocuments)
	{
		this.displayVoidDocuments = isDisplayVoidDocuments;
		
	}
	
	public boolean isDisplayVoidDocuments()
	{
		return displayVoidDocuments;
	}
}   // AcctViewerData
