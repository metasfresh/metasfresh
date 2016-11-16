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
package org.compiere.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.adempiere.acct.api.IAcctSchemaBL;
import org.adempiere.acct.api.IFactAcctCubeBL;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.LpadQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_PA_ReportCube;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.X_C_AcctSchema_Element;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable2;

import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;
import de.metas.logging.LogManager;

/**
 * Financial Report Engine
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting <li>FR [2857076] User Element 1 and 2 completion - https://sourceforge.net/tracker/?func=detail&aid=2857076&group_id=176962&atid=879335
 *
 * @version $Id: FinReport.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class FinReport extends SvrProcess
{
	// Services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);

	/** Period Parameter */
	private int p_C_Period_ID = 0;
	/** Org Parameter */
	private int p_Org_ID = 0;
	private int p_C_ElementValue_ID = -1;
	private int p_C_ElementValue_ID_To = -1;
	/** BPartner Parameter */
	private int p_C_BPartner_ID = 0;
	/** Product Parameter */
	private int p_M_Product_ID = 0;
	/** Project Parameter */
	private int p_C_Project_ID = 0;
	/** Activity Parameter */
	private int p_C_Activity_ID = 0;
	/** SalesRegion Parameter */
	private int p_C_SalesRegion_ID = 0;
	/** Campaign Parameter */
	private int p_C_Campaign_ID = 0;
	/** User 1 Parameter */
	private int p_User1_ID = 0;
	/** User 2 Parameter */
	private int p_User2_ID = 0;
	/** User Element 1 Parameter */
	private int p_UserElement1_ID = 0;
	/** User Element 2 Parameter */
	private int p_UserElement2_ID = 0;
	/** Details before Lines */
	private boolean p_DetailsSourceFirst = false;
	/** Hierarchy */
	private int p_PA_Hierarchy_ID = 0;
	/** Optional report cube */
	private int p_PA_ReportCube_ID = 0;

	/** Start Time */
	private long m_start = System.currentTimeMillis();

	/** Report Definition */
	private MReport m_report = null;
	/** Periods in Calendar */
	private FinReportPeriod[] m_periods = null;
	/** Index of m_C_Period_ID in m_periods **/
	private int m_reportPeriod = -1;
	/** Parameter Where Clause */
	private final StringBuilder m_parameterWhere = new StringBuilder();
	/** Parameter Where Clause SQL params */
	private final List<Object> m_parameterWhereParams = new ArrayList<Object>();
	/** The Report Columns */
	private MReportColumn[] m_columns;
	/** The Report Lines */
	private MReportLine[] m_lines;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		StringBuilder sb = new StringBuilder("Record_ID=")
				.append(getRecord_ID());
		// Parameter
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
			{
				;
			}
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = para[i].getParameterAsInt();
			else if (name.equals("PA_Hierarchy_ID"))
				p_PA_Hierarchy_ID = para[i].getParameterAsInt();
			else if (name.equals("Org_ID"))
				p_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			//
			else if (name.equals("C_ElementValue_ID"))
			{
				p_C_ElementValue_ID = para[i].getParameterAsInt();
				p_C_ElementValue_ID_To = para[i].getParameter_ToAsInt();
			}
			//
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Project_ID"))
				p_C_Project_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Activity_ID"))
				p_C_Activity_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_SalesRegion_ID"))
				p_C_SalesRegion_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Campaign_ID"))
				p_C_Campaign_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("User1_ID"))
				p_User1_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("User2_ID"))
				p_User2_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("UserElement1_ID"))
				p_UserElement1_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("UserElement2_ID"))
				p_UserElement2_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DetailsSourceFirst"))
				p_DetailsSourceFirst = "Y".equals(para[i].getParameter());
			else if (name.equals("PA_ReportCube_ID"))
				p_PA_ReportCube_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}

		// Optional Org
		if (p_Org_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Organization, p_Org_ID));
		// Optional Account
		if (p_C_ElementValue_ID > 0 || p_C_ElementValue_ID_To > 0)
		{
			appendElementValueWhereClause(m_parameterWhere, m_parameterWhereParams);
		}
		// Optional BPartner
		if (p_C_BPartner_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_BPartner, p_C_BPartner_ID));
		// Optional Product
		if (p_M_Product_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Product, p_M_Product_ID));
		// Optional Project
		if (p_C_Project_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Project, p_C_Project_ID));
		// Optional Activity
		if (p_C_Activity_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Activity, p_C_Activity_ID));
		// Optional Campaign
		if (p_C_Campaign_ID != 0)
		{
			m_parameterWhere.append(" AND C_Campaign_ID=").append(p_C_Campaign_ID);
			// m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
			// MAcctSchemaElement.ELEMENTTYPE_Campaign, p_C_Campaign_ID));
		}
		// Optional Sales Region
		if (p_C_SalesRegion_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_SalesRegion, p_C_SalesRegion_ID));
		// Optional User1_ID
		if (p_User1_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_UserList1, p_User1_ID));
		// Optional User2_ID
		if (p_User2_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_UserList2, p_User2_ID));
		// Optional UserElement1_ID
		if (p_UserElement1_ID != 0)
			m_parameterWhere.append(" AND UserElement1_ID=").append(p_UserElement1_ID);
		// Optional UserElement2_ID
		if (p_UserElement2_ID != 0)
			m_parameterWhere.append(" AND UserElement2_ID=").append(p_UserElement2_ID);

		// Load Report Definition
		m_report = new MReport(getCtx(), getRecord_ID(), ITrx.TRXNAME_None);
		sb.append(" - ").append(m_report);
		//
		setPeriods();
		sb.append(" - C_Period_ID=").append(p_C_Period_ID)
				.append(" - ").append(m_parameterWhere);
		//

		if (p_PA_ReportCube_ID > 0)
			m_parameterWhere.append(" AND PA_ReportCube_ID=").append(p_PA_ReportCube_ID);

		log.info(sb.toString());
		// m_report.list();
	}	// prepare

	/**
	 * Appends Account_ID where clause
	 * 
	 * @param whereClause
	 */
	private void appendElementValueWhereClause(final StringBuilder whereClause, final List<Object> whereClauseSqlParams)
	{
		if (p_C_ElementValue_ID > 0 && p_C_ElementValue_ID_To > 0)
		{
			final I_C_ElementValue elementValueFrom = InterfaceWrapperHelper.create(getCtx(), p_C_ElementValue_ID, I_C_ElementValue.class, ITrx.TRXNAME_None);
			final I_C_ElementValue elementValueTo = InterfaceWrapperHelper.create(getCtx(), p_C_ElementValue_ID_To, I_C_ElementValue.class, ITrx.TRXNAME_None);

			final LpadQueryFilterModifier lpadModifier = new LpadQueryFilterModifier(20, "0");
			final List<Integer> elementValueIds = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_ElementValue.class, this)
					.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, Operator.GREATER_OR_EQUAL, elementValueFrom.getValue(), lpadModifier)
					.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, Operator.LESS_OR_EQUAL, elementValueTo.getValue(), lpadModifier)
					.create()
					.listIds();

			final String columnName = Services.get(IAcctSchemaBL.class).getColumnName(X_C_AcctSchema_Element.ELEMENTTYPE_Account);
			final String sql = DB.buildSqlList(elementValueIds, whereClauseSqlParams);
			whereClause.append(" AND ").append(columnName).append(" IN ").append(sql);
		}
		else if (p_C_ElementValue_ID <= 0 && p_C_ElementValue_ID_To <= 0)
		{
			// no accounts specified, nothing to do
			return;
		}
		else if (p_C_ElementValue_ID > 0)
		{
			whereClause.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Account, p_C_ElementValue_ID));
		}
		else if (p_C_ElementValue_ID_To > 0)
		{
			whereClause.append(" AND ").append(MReportTree.getWhereClause(getCtx(),
					p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Account, p_C_ElementValue_ID_To));
		}
	}

	private void appendParametersWhereClause(final StringBuilder sql, final List<Object> sqlParamsOut)
	{
		sql.append(m_parameterWhere);
		sqlParamsOut.addAll(m_parameterWhereParams);
	}

	/**
	 * Set Periods
	 */
	private void setPeriods()
	{
		log.info("C_Calendar_ID=" + m_report.getC_Calendar_ID());
		Timestamp today = TimeUtil.getDay(System.currentTimeMillis());
		ArrayList<FinReportPeriod> list = new ArrayList<FinReportPeriod>();

		String sql = "SELECT p.C_Period_ID, p.Name, p.StartDate, p.EndDate, MIN(p1.StartDate) "
				+ "FROM C_Period p "
				+ " INNER JOIN C_Year y ON (p.C_Year_ID=y.C_Year_ID),"
				+ " C_Period p1 "
				+ "WHERE y.C_Calendar_ID=?"
				// globalqss - cruiz - Bug [ 1577712 ] Financial Period Bug
				+ " AND p.IsActive='Y'"
				+ " AND p.PeriodType='S' "
				+ " AND p1.C_Year_ID=y.C_Year_ID AND p1.PeriodType='S' "
				+ "GROUP BY p.C_Period_ID, p.Name, p.StartDate, p.EndDate "
				+ "ORDER BY p.StartDate";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_report.getC_Calendar_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final FinReportPeriod frp = new FinReportPeriod(rs.getInt(1), rs.getString(2),
						rs.getTimestamp(3), rs.getTimestamp(4), rs.getTimestamp(5));
				list.add(frp);
				if (p_C_Period_ID <= 0 && frp.inPeriod(today))
				{
					p_C_Period_ID = frp.getC_Period_ID();
				}
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// convert to Array
		m_periods = new FinReportPeriod[list.size()];
		list.toArray(m_periods);
		// today after latest period
		if (p_C_Period_ID == 0)
		{
			m_reportPeriod = m_periods.length - 1;
			p_C_Period_ID = m_periods[m_reportPeriod].getC_Period_ID();
		}
	}	// setPeriods

	/**************************************************************************
	 * Perform process.
	 * 
	 * @return Message to be translated
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("AD_PInstance_ID=" + getAD_PInstance_ID());

		if (p_PA_ReportCube_ID > 0)
		{
			final I_PA_ReportCube reportCube = InterfaceWrapperHelper.create(getCtx(), p_PA_ReportCube_ID, I_PA_ReportCube.class, get_TrxName());
			Services.get(IFactAcctCubeBL.class)
					.createFactAcctCubeUpdater()
					.setContext(this)
					.setPA_ReportCube(reportCube)
					.setForceUpdate(false)
					.setResetCube(false)
					.update();
		}
		
		// ** Create Temporary and empty Report Lines from PA_ReportLine
		// - AD_PInstance_ID, PA_ReportLine_ID, 0, 0
		int PA_ReportLineSet_ID = m_report.getLineSet().getPA_ReportLineSet_ID();
		StringBuilder sql = new StringBuilder("INSERT INTO T_Report "
				+ "(AD_PInstance_ID, PA_ReportLine_ID, Record_ID,Fact_Acct_ID, SeqNo,LevelNo, Name,Description) "
				+ "SELECT ").append(getAD_PInstance_ID()).append(", PA_ReportLine_ID, 0,0, SeqNo,0, Name,Description "
				+ "FROM PA_ReportLine "
				+ "WHERE IsActive='Y' AND PA_ReportLineSet_ID=").append(PA_ReportLineSet_ID);

		int no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Report Lines = " + no);

		// ** Get Data ** Segment Values
		m_columns = m_report.getColumnSet().getColumns();
		if (m_columns.length == 0)
			throw new AdempiereUserError("@No@ @PA_ReportColumn_ID@");
		m_lines = m_report.getLineSet().getLiness();
		if (m_lines.length == 0)
			throw new AdempiereUserError("@No@ @PA_ReportLine_ID@");

		includeSublines(); // metas-2009_0021_AP1_CR080
		// for all lines
		for (int line = 0; line < m_lines.length; line++)
		{
			// Line Segment Value (i.e. not calculation)
			if (m_lines[line].isLineTypeSegmentValue())
				insertLine(line);
		}	// for all lines

		insertLineDetail();
		doCalculations();

		deleteUnprintedLines();

		scaleResults();

		// Create Report
		getResult().setPrintFormat(getPrintFormat());

		return MSG_OK;
	}	// doIt

	/**************************************************************************
	 * For all columns (in a line) with relative period access
	 * 
	 * @param paReportLineIndex line
	 */
	private void insertLine(final int paReportLineIndex)
	{
		final MReportLine paReportLine = m_lines[paReportLineIndex];
		log.info("" + paReportLine);

		// No source lines - Headings
		if (paReportLine == null || paReportLine.getSources().length == 0)
		{
			log.warn("No Source lines: " + paReportLine);
			return;
		}

		final boolean isSuppressZeroLine = paReportLine.isSuppressZeroLine();	// metas-2009_0021_AP1_CR080
		boolean isZeroLine = isSuppressZeroLine ? true : false;					// metas-2009_0021_AP1_CR080
		final StringBuilder update = new StringBuilder();
		final List<Object> updateSqlParams = new ArrayList<Object>();

		// for all columns
		for (int paReportColumnIndex = 0; paReportColumnIndex < m_columns.length; paReportColumnIndex++)
		{
			final MReportColumn paReportColumn = m_columns[paReportColumnIndex];

			// Ignore calculation columns
			if (paReportColumn.isColumnTypeCalculation())
			{
				continue;
			}

			final StringBuilder info = new StringBuilder();
			info.append("Line=").append(paReportLineIndex).append(",Col=").append(paReportColumnIndex);

			// SELECT SUM()
			final List<Object> selectSqlParams = new ArrayList<Object>();
			final StringBuilder select = new StringBuilder("SELECT ");
			if (paReportLine.getPAAmountType() != null)				// line amount type overwrites column
			{
				String sql = paReportLine.getSelectClause(true);
				select.append(sql);
				info.append(": LineAmtType=").append(paReportLine.getPAAmountType());
			}
			else if (paReportColumn.getPAAmountType() != null)
			{
				String sql = paReportColumn.getSelectClause(true);
				select.append(sql);
				info.append(": ColumnAmtType=").append(paReportColumn.getPAAmountType());
			}
			else
			{
				log.warn("No Amount Type in line: " + paReportLine + " or column: " + paReportColumn);
				continue;
			}

			if (isSuppressZeroLine && isZeroLine)
			{
				select.append(", COUNT(*) "); // metas-2009_0021_AP1_CR080
			}

			if (p_PA_ReportCube_ID > 0)
			{
				select.append(" FROM Fact_Acct_Summary fa WHERE DateAcct ");
			}
			else
			{
				// Get Period/Date info
				select.append(" FROM Fact_Acct fa WHERE TRUNC(DateAcct) ");
			}

			BigDecimal relativeOffset = null;	// current
			if (paReportColumn.isColumnTypeRelativePeriod())
			{
				relativeOffset = paReportColumn.getRelativePeriod();
			}
			final FinReportPeriod frp = getPeriod(relativeOffset);
			if (paReportLine.getPAPeriodType() != null)			// line amount type overwrites column
			{
				info.append(" - LineDateAcct=");
				if (paReportLine.isPeriod())
				{
					final String sql = frp.getPeriodWhere();
					info.append("Period");
					select.append(sql);
				}
				else if (paReportLine.isYear())
				{
					final String sql = frp.getYearWhere();
					info.append("Year");
					select.append(sql);
				}
				else if (paReportLine.isTotal())
				{
					final String sql = frp.getTotalWhere();
					info.append("Total");
					select.append(sql);
				}
				else if (paReportLine.isNatural())
				{
					select.append(frp.getNaturalWhere("fa"));
				}
				else
				{
					log.error("No valid Line PAPeriodType");
					select.append("=0");	// valid sql
				}
			}
			else if (paReportColumn.getPAPeriodType() != null)
			{
				info.append(" - ColumnDateAcct=");
				if (paReportColumn.isPeriod())
				{
					final String sql = frp.getPeriodWhere();
					info.append("Period");
					select.append(sql);
				}
				else if (paReportColumn.isYear())
				{
					final String sql = frp.getYearWhere();
					info.append("Year");
					select.append(sql);
				}
				else if (paReportColumn.isTotal())
				{
					final String sql = frp.getTotalWhere();
					info.append("Total");
					select.append(sql);
				}
				else if (paReportColumn.isNatural())
				{
					select.append(frp.getNaturalWhere("fa"));
				}
				else
				{
					log.error("No valid Column PAPeriodType");
					select.append("=0");	// valid sql
				}
			}

			// Line Where
			final String paReportLineWhereClause = paReportLine.getWhereClause(p_PA_Hierarchy_ID);	// (sources, posting type)
			if (!Check.isEmpty(paReportLineWhereClause, true))
			{
				select.append(" AND ").append(paReportLineWhereClause);
			}

			// Report Where
			final String paReportWhereClause = m_report.getWhereClause();
			if (Check.isEmpty(paReportWhereClause))
			{
				select.append(" AND ").append(paReportWhereClause);
			}

			// PostingType
			if (!paReportLine.isPostingType())		// only if not defined on line
			{
				final String PostingType = paReportColumn.getPostingType();
				if (!Check.isEmpty(PostingType))
					select.append(" AND PostingType='").append(PostingType).append("'");
				// globalqss - CarlosRuiz
				if (PostingType.equals(MReportColumn.POSTINGTYPE_Budget))
				{
					if (paReportColumn.getGL_Budget_ID() > 0)
						select.append(" AND GL_Budget_ID=" + paReportColumn.getGL_Budget_ID());
				}
				// end globalqss
			}

			if (paReportColumn.isColumnTypeSegmentValue())
			{
				select.append(paReportColumn.getWhereClause(p_PA_Hierarchy_ID));
			}

			// Parameter Where
			appendParametersWhereClause(select, selectSqlParams);
			log.trace("Line=" + paReportLineIndex + ",Col=" + paReportLineIndex + ": " + select);

			// metas-2009_0021_AP1_CR080: begin
			if (isSuppressZeroLine && isZeroLine)
			{
				isZeroLine = checkSuppressZeroLine(select, selectSqlParams);
			}
			// metas-2009_0021_AP1_CR080: end

			//
			// Update SET portion
			if (update.length() > 0)
				update.append(", ");
			update.append("Col_").append(paReportColumnIndex)
					.append(" = (").append(select).append(")");
			updateSqlParams.addAll(selectSqlParams);
			//
			log.trace(info.toString());
		}

		//
		// Delete ZERO Line
		// (metas-2009_0021_AP1_CR080)
		if (isZeroLine)
		{
			final String sql = "DELETE FROM T_Report WHERE AD_PInstance_ID=? AND PA_ReportLine_ID=?";
			final int no = DB.executeUpdateEx(sql,
					new Object[] { getAD_PInstance_ID(), paReportLine.getPA_ReportLine_ID() },
					get_TrxName());
			if (no != 1)
				log.error("#=" + no + " for " + sql);
			log.trace(sql);
		}
		//
		// Update Line Values
		else if (update.length() > 0)
		{
			update.insert(0, "UPDATE T_Report SET ");
			update.append(" WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND PA_ReportLine_ID=").append(paReportLine.getPA_ReportLine_ID())
					.append(" AND ABS(LevelNo)<2");		// 0=Line 1=Acct
			final int no = DB.executeUpdateEx(update.toString(),
					updateSqlParams.toArray(),
					get_TrxName());
			if (no != 1)
				log.error("#=" + no + " for " + update);
			log.trace(update.toString());
		}
	}	// insertLine

	/**
	 * Checks if given select SQL returns NULL {@link BigDecimal} and if so, replaces the select with "NULL"
	 * 
	 * @param select
	 * @param selectSqlParams
	 * @return true if SQL returned null result
	 */
	private final boolean checkSuppressZeroLine(final StringBuilder select, final List<Object> selectSqlParams)
	{
		boolean isZeroLine = true;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try
		{
			pstmt2 = DB.prepareStatement(select.toString(), get_TrxName());
			DB.setParameters(pstmt2, selectSqlParams);
			rs2 = pstmt2.executeQuery();
			if (rs2.next())
			{
				BigDecimal value = rs2.getBigDecimal(1);
				int count = rs2.getInt(2);
				if (count == 0)
				{
					value = null;
				}
				else
				{
					isZeroLine = false;
				}

				//
				// Replace SELECT SQL with retrieved value
				select.delete(0, select.length());
				select.append(value == null ? "NULL" : value.toString());
				selectSqlParams.clear();
			}
			if (rs2.next())
			{
				log.error("More than one resulting row found for " + select);
			}
		}
		catch (SQLException e)
		{
			log.error("Error for sql: " + select, e);
		}
		finally
		{
			DB.close(rs2, pstmt2);
			rs2 = null;
			pstmt2 = null;
		}

		return isZeroLine;
	}

	/**************************************************************************
	 * Line + Column calculation
	 */
	private void doCalculations()
	{
		// for all lines ***************************************************
		for (int line = 0; line < m_lines.length; line++)
		{
			if (!m_lines[line].isLineTypeCalculation())
				continue;

			int oper_1 = m_lines[line].getOper_1_ID();
			int oper_2 = m_lines[line].getOper_2_ID();

			log.debug("Line " + line + " = #" + oper_1 + " "
					+ m_lines[line].getCalculationType() + " #" + oper_2);

			// Adding
			if (m_lines[line].isCalculationTypeAdd()
					|| m_lines[line].isCalculationTypeRange())
			{
				// Reverse range
				if (oper_1 > oper_2)
				{
					int temp = oper_1;
					oper_1 = oper_2;
					oper_2 = temp;
				}
				StringBuilder sb = new StringBuilder("UPDATE T_Report SET (");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("Col_").append(col);
				}
				sb.append(") = (SELECT ");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("COALESCE(SUM(r2.Col_").append(col).append("),0)");
				}
				sb.append(" FROM T_Report r2 WHERE r2.AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND r2.PA_ReportLine_ID IN (");
				if (m_lines[line].isCalculationTypeAdd())
					// sb.append(oper_1).append(",").append(oper_2);
					sb.append(getAllLineIDsSQL(oper_1, oper_2)); // metas
				else
					sb.append(getAllLineIntervalIDsSQL(oper_1, oper_2)); // metas
				// sb.append(getLineIDs (oper_1, oper_2)); // list of columns to add up
				sb.append(") AND ABS(r2.LevelNo)<1) "		// 0=Line 1=Acct
						+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND PA_ReportLine_ID=").append(m_lines[line].getPA_ReportLine_ID())
						.append(" AND ABS(LevelNo)<1");		// not trx
				int no = DB.executeUpdate(DB.convertSqlToNative(sb.toString()), get_TrxName());
				if (no != 1)
					log.error("(+) #=" + no + " for " + m_lines[line] + " - " + sb.toString());
				else
				{
					log.debug("(+) Line=" + line + " - " + m_lines[line]);
					log.trace("(+) " + sb.toString());
				}
			}
			else
			// No Add (subtract, percent)
			{
				// metas: tsa: begin:
				oper_1 = getIncludedLineID(oper_1);
				oper_2 = getIncludedLineID(oper_2);
				// metas: tsa: end
				// Step 1 - get First Value or 0 in there
				StringBuilder sb = new StringBuilder("UPDATE T_Report SET (");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("Col_").append(col);
				}
				sb.append(") = (SELECT ");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("COALESCE(r2.Col_").append(col).append(",0)");
				}
				sb.append(" FROM T_Report r2 WHERE r2.AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND r2.PA_ReportLine_ID=").append(oper_1)
						.append(" AND r2.Record_ID=0 AND r2.Fact_Acct_ID=0) "
								//
								+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND PA_ReportLine_ID=").append(m_lines[line].getPA_ReportLine_ID())
						.append(" AND ABS(LevelNo)<1");			// 0=Line 1=Acct
				int no = DB.executeUpdate(DB.convertSqlToNative(sb.toString()), get_TrxName());
				if (no != 1)
				{
					log.error("(x) #=" + no + " for " + m_lines[line] + " - " + sb.toString());
					continue;
				}

				// Step 2 - do Calculation with Second Value
				sb = new StringBuilder("UPDATE T_Report r1 SET (");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("Col_").append(col);
				}
				sb.append(") = (SELECT ");
				for (int col = 0; col < m_columns.length; col++)
				{
					if (col > 0)
						sb.append(",");
					sb.append("COALESCE(r1.Col_").append(col).append(",0)");
					// fix bug [ 1563664 ] Errors in values shown in Financial Reports
					// Carlos Ruiz - globalqss
					if (m_lines[line].isCalculationTypeSubtract())
					{
						sb.append("-");
						// Solution, for subtraction replace the null with 0, instead of 0.000000001
						sb.append("COALESCE(r2.Col_").append(col).append(",0)");
					}
					else
					{
						// Solution, for division don't replace the null, convert 0 to null (avoid ORA-01476)
						sb.append("/");
						sb.append("DECODE (r2.Col_").append(col).append(", 0, NULL, r2.Col_").append(col).append(")");
					}
					// end fix bug [ 1563664 ]
					if (m_lines[line].isCalculationTypePercent())
						sb.append(" *100");
				}
				sb.append(" FROM T_Report r2 WHERE r2.AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND r2.PA_ReportLine_ID=").append(oper_2)
						.append(" AND r2.Record_ID=0 AND r2.Fact_Acct_ID=0) "
								//
								+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
						.append(" AND PA_ReportLine_ID=").append(m_lines[line].getPA_ReportLine_ID())
						.append(" AND ABS(LevelNo)<1");			// 0=Line 1=Acct
				no = DB.executeUpdate(DB.convertSqlToNative(sb.toString()), get_TrxName());
				if (no != 1)
					log.error("(x) #=" + no + " for " + m_lines[line] + " - " + sb.toString());
				else
				{
					log.debug("(x) Line=" + line + " - " + m_lines[line]);
					log.trace(sb.toString());
				}
			}
		}	// for all lines

		// for all columns ***********************************************
		for (int col = 0; col < m_columns.length; col++)
		{
			// Only Calculations
			if (!m_columns[col].isColumnTypeCalculation())
				continue;

			StringBuilder sb = new StringBuilder("UPDATE T_Report SET ");
			// Column to set
			sb.append("Col_").append(col).append("=");
			// First Operand
			int ii_1 = getColumnIndex(m_columns[col].getOper_1_ID());
			if (ii_1 < 0)
			{
				log.error("Column Index for Operator 1 not found - " + m_columns[col]);
				continue;
			}
			// Second Operand
			int ii_2 = getColumnIndex(m_columns[col].getOper_2_ID());
			if (ii_2 < 0)
			{
				log.error("Column Index for Operator 2 not found - " + m_columns[col]);
				continue;
			}
			log.debug("Column " + col + " = #" + ii_1 + " "
					+ m_columns[col].getCalculationType() + " #" + ii_2);
			// Reverse Range
			if (ii_1 > ii_2 && m_columns[col].isCalculationTypeRange())
			{
				log.debug("Swap operands from " + ii_1 + " op " + ii_2);
				int temp = ii_1;
				ii_1 = ii_2;
				ii_2 = temp;
			}

			// +
			if (m_columns[col].isCalculationTypeAdd())
				sb.append("COALESCE(Col_").append(ii_1).append(",0)")
						.append("+")
						.append("COALESCE(Col_").append(ii_2).append(",0)");
			// -
			else if (m_columns[col].isCalculationTypeSubtract())
				sb.append("COALESCE(Col_").append(ii_1).append(",0)")
						.append("-")
						.append("COALESCE(Col_").append(ii_2).append(",0)");
			// /
			if (m_columns[col].isCalculationTypePercent())
				sb.append("CASE WHEN COALESCE(Col_").append(ii_2)
						.append(",0)=0 THEN NULL ELSE ")
						.append("COALESCE(Col_").append(ii_1).append(",0)")
						.append("/")
						.append("Col_").append(ii_2)
						.append("*100 END");	// Zero Divide
			// Range
			else if (m_columns[col].isCalculationTypeRange())
			{
				sb.append("COALESCE(Col_").append(ii_1).append(",0)");
				for (int ii = ii_1 + 1; ii <= ii_2; ii++)
					sb.append("+COALESCE(Col_").append(ii).append(",0)");
			}
			//
			sb.append(" WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND ABS(LevelNo)<2");			// 0=Line 1=Acct
			int no = DB.executeUpdate(sb.toString(), get_TrxName());
			if (no < 1)
				log.error("#=" + no + " for " + m_columns[col]
						+ " - " + sb.toString());
			else
			{
				log.debug("Col=" + col + " - " + m_columns[col]);
				log.trace(sb.toString());
			}
		} 	// for all columns

	}	// doCalculations

	/**
	 * Get Column Index
	 * 
	 * @param PA_ReportColumn_ID PA_ReportColumn_ID
	 * @return zero based index or if not found
	 */
	private int getColumnIndex(int PA_ReportColumn_ID)
	{
		for (int i = 0; i < m_columns.length; i++)
		{
			if (m_columns[i].getPA_ReportColumn_ID() == PA_ReportColumn_ID)
				return i;
		}
		return -1;
	}	// getColumnIndex

	/**
	 * See {@link #getPeriod(int)}.
	 * 
	 * @param relativeOffset may be <code>null</code>, in that case <code>0</code> is assumed.
	 * 
	 * 
	 */
	private FinReportPeriod getPeriod(BigDecimal relativeOffset)
	{
		if (relativeOffset == null)
			return getPeriod(0);
		return getPeriod(relativeOffset.intValue());
	}	// getPeriod

	/**
	 * Get Financial Reporting Period based on reporting Period and offset.
	 * 
	 * @param relativeOffset offset in <b>terms of periods</b>. So if there are month-periods, and you want an offset of minus one year, then this value needs to be <code>-12</code>, even if the
	 *            period type in the report row or col is "year".
	 * @return reporting period
	 */
	private FinReportPeriod getPeriod(final int relativeOffset)
	{
		// find current reporting period C_Period_ID
		if (m_reportPeriod < 0)
		{
			for (int i = 0; i < m_periods.length; i++)
			{
				if (p_C_Period_ID == m_periods[i].getC_Period_ID())
				{
					m_reportPeriod = i;
					break;
				}
			}
		}
		if (m_reportPeriod < 0 || m_reportPeriod >= m_periods.length)
		{
			throw new UnsupportedOperationException("Period index not found - ReportPeriod="
					+ m_reportPeriod + ", C_Period_ID=" + p_C_Period_ID);
		}

		// Bounds check
		int index = m_reportPeriod + relativeOffset;
		if (index < 0)
		{
			log.error("Relative Offset(" + relativeOffset
					+ ") not valid for selected Period(" + m_reportPeriod + ")");
			index = 0;
		}
		else if (index >= m_periods.length)
		{
			log.error("Relative Offset(" + relativeOffset
					+ ") not valid for selected Period(" + m_reportPeriod + ")");
			index = m_periods.length - 1;
		}
		// Get Period
		return m_periods[index];
	}	// getPeriod

	/**************************************************************************
	 * Insert Detail Lines if enabled
	 */
	private void insertLineDetail()
	{
		if (!m_report.isListSources())
		{
			return;
		}
		log.info("");

		// for all source lines
		for (int line = 0; line < m_lines.length; line++)
		{
			// Line Segment Value (i.e. not calculation)
			if (m_lines[line].isLineTypeSegmentValue())
			{
				insertLineSources(line);
			}
		}

		//
		// Clean up empty rows
		{
			final StringBuilder sql = new StringBuilder("DELETE FROM T_Report WHERE ABS(LevelNo)<>0")
					.append(" AND Col_0 IS NULL AND Col_1 IS NULL AND Col_2 IS NULL AND Col_3 IS NULL AND Col_4 IS NULL AND Col_5 IS NULL AND Col_6 IS NULL AND Col_7 IS NULL AND Col_8 IS NULL AND Col_9 IS NULL")
					.append(" AND Col_10 IS NULL AND Col_11 IS NULL AND Col_12 IS NULL AND Col_13 IS NULL AND Col_14 IS NULL AND Col_15 IS NULL AND Col_16 IS NULL AND Col_17 IS NULL AND Col_18 IS NULL AND Col_19 IS NULL AND Col_20 IS NULL");
			final int no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted empty #=" + no);
		}

		//
		// Set SeqNo
		{
			final StringBuilder sql = new StringBuilder("UPDATE T_Report r1 "
					+ "SET SeqNo = (SELECT SeqNo "
					+ "FROM T_Report r2 "
					+ "WHERE r1.AD_PInstance_ID=r2.AD_PInstance_ID AND r1.PA_ReportLine_ID=r2.PA_ReportLine_ID"
					+ " AND r2.Record_ID=0 AND r2.Fact_Acct_ID=0)"
					+ "WHERE SeqNo IS NULL");
			final int no = DB.executeUpdateEx(sql.toString(), get_TrxName());
			log.debug("SeqNo #=" + no);
		}

		if (!m_report.isListTrx())
			return;

		//
		// Set Name,Description
		{
			String sql_select = "SELECT e.Name, fa.Description "
					+ "FROM Fact_Acct fa"
					+ " INNER JOIN AD_Table t ON (fa.AD_Table_ID=t.AD_Table_ID)"
					+ " INNER JOIN AD_Element e ON (t.TableName||'_ID'=e.ColumnName) "
					+ "WHERE r.Fact_Acct_ID=fa.Fact_Acct_ID";
			// Translated Version ...
			final StringBuilder sql = new StringBuilder("UPDATE T_Report r SET (Name,Description)=(")
					.append(sql_select).append(") "
							+ "WHERE Fact_Acct_ID <> 0 AND AD_PInstance_ID=")
					.append(getAD_PInstance_ID());
			final int no = DB.executeUpdateEx(DB.convertSqlToNative(sql.toString()), get_TrxName());
			if (LogManager.isLevelFinest())
				log.debug("Trx Name #=" + no + " - " + sql.toString());
		}
	}	// insertLineDetail

	/**
	 * Insert Detail Line per Source. For all columns (in a line) with relative period access - AD_PInstance_ID, PA_ReportLine_ID, variable, 0 - Level 1
	 * 
	 * @param line the array index (from the private {@link #m_lines} index) of the line to insert sources for
	 */
	private void insertLineSources(final int line)
	{
		final MReportLine currentReportLine = m_lines[line];

		log.info("Line=" + line + " - " + currentReportLine);

		// No source lines
		if (currentReportLine == null || currentReportLine.getSources().length == 0)
		{
			return; // nothing to do
		}

		// task 07429
		final MReportSource[] lineSources = currentReportLine.getSources(); // get the line sources, we will evaluate them all
		final int idxLineSources = 0; // start with the first one
		final List<Pair<String, Integer>> higherLevelRecords = new ArrayList<Pair<String, Integer>>(); // this list (now empty) contains the column and ID of the respective higher level sources.

		final String variable = insertLineSource(currentReportLine, lineSources, idxLineSources, higherLevelRecords);

		// for now, we just dump the actual transactions at the end or the beginning, but we do not try to fiddle them in between the groupings
		if (m_report.isListTrx() && !Check.isEmpty(variable))
		{
			insertLineTrx(line, variable);
		}
	}	// insertLineSource

	/**
	 * Recursive method to insert <code>T_Report</code> for the given lines for the given <code>lineSources</code>.
	 * 
	 * @param currentReportLine
	 * @param lineSources all source records for the given <code>currentReportLine</code>, ordered by <code>SeqNo</code>.
	 * @param idxLineSources the array index of the particular line source to be used currently. If the index exceeds the array's size, then this method does nothing.
	 * @param higherLevelRecords infos about higher level source records (to be used for grouping, filtering etc).
	 * 
	 */
	private String insertLineSource(
			final MReportLine currentReportLine,
			final MReportSource[] lineSources,
			final int idxLineSources,
			final List<Pair<String, Integer>> higherLevelRecords)
	{
		if (lineSources.length <= idxLineSources)
		{
			return null; // nothing more to do
		}

		final MReportSource currentLineSource = lineSources[idxLineSources];

		final String variable = acctSchemaBL.getColumnName(currentLineSource.getElementType());
		if (variable == null)
		{
			return null;
		}
		log.debug("Variable=" + variable);

		// INSERT INTO
		final List<Object> insertSqlParams = new ArrayList<Object>();
		final StringBuilder insert = new StringBuilder("INSERT INTO T_Report " + "(AD_PInstance_ID, PA_ReportLine_ID, Record_ID,Fact_Acct_ID,LevelNo ");
		for (int paReportColumnIndex = 0; paReportColumnIndex < m_columns.length; paReportColumnIndex++)
		{
			insert.append(",Col_").append(paReportColumnIndex);
		}
		insert.append(")");
		// ... SELECT
		insert.append(" SELECT ")
				.append(getAD_PInstance_ID()).append(",") // AD_PInstance_ID
				.append(currentReportLine.getPA_ReportLine_ID()).append(",") // PA_ReportLine_ID
				.append(variable).append(",") // Record_ID
				.append("0,"); // Fact_Acct_ID
		if (p_DetailsSourceFirst)
		{
			insert.append("-1 "); // LevelNo
		}
		else
		{
			insert.append("1 "); // LevelNo
		}
		//
		// for all columns create select statement
		for (int paReportColumnIndex = 0; paReportColumnIndex < m_columns.length; paReportColumnIndex++)
		{
			final MReportColumn paReportColumn = m_columns[paReportColumnIndex];

			insert.append(", ");

			// No calculation
			if (paReportColumn.isColumnTypeCalculation())
			{
				insert.append("NULL");
				continue;
			}

			// SELECT SUM()
			final StringBuilder select = new StringBuilder("SELECT ");
			final List<Object> selectSqlParams = new ArrayList<Object>();
			if (currentReportLine.getPAAmountType() != null)				// line amount type overwrites column
			{
				select.append(currentReportLine.getSelectClause(true));
			}
			else if (paReportColumn.getPAAmountType() != null)
			{
				select.append(paReportColumn.getSelectClause(true));
			}
			else
			{
				insert.append("NULL");
				continue;
			}

			// FROM ... WHERE ...
			if (p_PA_ReportCube_ID > 0)
			{
				select.append(" FROM Fact_Acct_Summary fb WHERE DateAcct ");
			}  // report cube
			else
			{
				// Get Period info
				select.append(" FROM Fact_Acct fb WHERE TRUNC(DateAcct) ");
			}

			// compute the date constraints of the WHERE clause
			final FinReportPeriod frp = getPeriod(paReportColumn.getRelativePeriod());
			if (currentReportLine.getPAPeriodType() != null)			// line amount type overwrites column
			{
				if (currentReportLine.isPeriod())
					select.append(frp.getPeriodWhere());
				else if (currentReportLine.isYear())
					select.append(frp.getYearWhere());
				else if (currentReportLine.isNatural())
					select.append(frp.getNaturalWhere("fb"));
				else
					select.append(frp.getTotalWhere());
			}
			else if (paReportColumn.getPAPeriodType() != null)
			{
				if (paReportColumn.isPeriod())
					select.append(frp.getPeriodWhere());
				else if (paReportColumn.isYear())
					select.append(frp.getYearWhere());
				else if (paReportColumn.isNatural())
					select.append(frp.getNaturalWhere("fb"));
				else
					select.append(frp.getTotalWhere());
			}

			// Link
			select.append(" AND fb.").append(variable).append("=x.").append(variable);

			// task 07429
			for (final Pair<String, Integer> higlerlevel : higherLevelRecords)
			{
				select.append(" AND fb.").append(higlerlevel.getFirst()).append("=x.").append(higlerlevel.getFirst());
			}

			// PostingType
			if (!currentReportLine.isPostingType())		// only if not defined on line
			{
				final String PostingType = paReportColumn.getPostingType();
				if (!Check.isEmpty(PostingType))
				{
					select.append(" AND fb.PostingType='").append(PostingType).append("'");
				}
				// globalqss - CarlosRuiz
				if (PostingType.equals(MReportColumn.POSTINGTYPE_Budget))
				{
					if (paReportColumn.getGL_Budget_ID() > 0)
						select.append(" AND GL_Budget_ID=" + paReportColumn.getGL_Budget_ID());
				}
				// end globalqss
			}
			// Report Where
			final String paReportWhereClause = m_report.getWhereClause();
			if (!Check.isEmpty(paReportWhereClause))
			{
				select.append(" AND ").append(paReportWhereClause);
			}

			// Limited Segment Values
			if (paReportColumn.isColumnTypeSegmentValue())
			{
				select.append(paReportColumn.getWhereClause(p_PA_Hierarchy_ID));
			}

			// Parameter Where
			appendParametersWhereClause(select, selectSqlParams);
			// System.out.println("    c=" + col + ", l=" + line + ": " + select);

			// Append the SELECT to main INSERT
			insert.append("(").append(select).append(")");
			insertSqlParams.addAll(selectSqlParams);
		}

		//
		// WHERE (sources, posting type)
		final StringBuilder where = new StringBuilder(currentReportLine.getWhereClause(p_PA_Hierarchy_ID));
		final String paReportWhereClause = m_report.getWhereClause();
		if (!Check.isEmpty(paReportWhereClause))
		{
			if (where.length() > 0)
			{
				where.append(" AND ");
			}
			where.append(paReportWhereClause);
		}
		if (where.length() > 0)
		{
			where.append(" AND ");
		}
		where.append(variable).append(" IS NOT NULL");

		// task 07429
		for (final Pair<String, Integer> higlerlevel : higherLevelRecords)
		{
			where.append(" AND x.").append(higlerlevel.getFirst()).append("=").append(higlerlevel.getSecond());
		}

		if (p_PA_ReportCube_ID > 0)
		{
			insert.append(" FROM Fact_Acct_Summary x WHERE ").append(where);
		}
		else
		{
			// FROM .. WHERE
			insert.append(" FROM Fact_Acct x WHERE ").append(where);
		}

		//
		// Append Parameters SQL Where Clause
		appendParametersWhereClause(insert, insertSqlParams);

		//
		// Append GROUP BYs
		insert.append(" GROUP BY ").append(variable);
		// task 07429: append the parent
		for (final Pair<String, Integer> higlerlevel : higherLevelRecords)
		{
			insert.append(", ").append(higlerlevel.getFirst());
		}

		insert.append(" RETURNING RECORD_ID ");

		final int[] count = { 0 };
		trxManager.run(
				get_TrxName(),
				trxManager.createTrxRunConfig(TrxPropagation.NESTED, OnRunnableSuccess.COMMIT, OnRunnableFail.ROLLBACK),
				new TrxRunnable2()
				{
					private PreparedStatement pstmt = null;
					private ResultSet rs = null;

					@Override
					public void run(String localTrxName) throws Exception
					{
						pstmt = DB.prepareStatement(insert.toString(), localTrxName);
						DB.setParameters(pstmt, insertSqlParams);
						rs = pstmt.executeQuery();

						while (rs.next())
						{
							count[0]++;
							final int insertedRecordId = rs.getInt(1);
							final List<Pair<String, Integer>> newParentList = new ArrayList<Pair<String, Integer>>(higherLevelRecords);
							newParentList.add(new Pair<String, Integer>(variable, insertedRecordId));

							insertLineSource(currentReportLine, lineSources, idxLineSources + 1, newParentList);
						}
					}

					@Override
					public boolean doCatch(Throwable e) throws Throwable
					{
						throw e;
					}

					@Override
					public void doFinally()
					{
						DB.close(rs, pstmt);
						rs = null;
						pstmt = null;
					}
				});

		// int no = DB.executeUpdate(insert.toString(), get_TrxName());
		if (LogManager.isLevelFinest())
		{
			log.debug("Source #=" + count[0] + " - " + insert);
		}
		if (count[0] == 0)
		{
			return null;
		}

		// Set Name,Description
		{
			final StringBuilder sql = new StringBuilder("UPDATE T_Report SET (Name,Description)=(")
					// .append(currentReportLine.getSourceValueQuery())
					.append(MAcctSchemaElement.getValueQuery(lineSources[idxLineSources].getElementType()))
					.append("T_Report.Record_ID) "
							//
							+ "WHERE Record_ID <> 0 AND AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND PA_ReportLine_ID=").append(currentReportLine.getPA_ReportLine_ID())
					.append(" AND Fact_Acct_ID=0");
			final int no = DB.executeUpdateEx(DB.convertSqlToNative(sql.toString()), get_TrxName());
			if (LogManager.isLevelFinest())
				log.debug("Name #=" + no + " - " + sql.toString());
		}
		return variable;
	}

	/**
	 * Create Trx Line per Source Detail. - AD_PInstance_ID, PA_ReportLine_ID, variable, Fact_Acct_ID - Level 2
	 * 
	 * @param line line
	 * @param variable variable, e.g. Account_ID
	 */
	private void insertLineTrx(int line, String variable)
	{
		log.info("Line=" + line + " - Variable=" + variable);

		// Insert
		StringBuilder insert = new StringBuilder("INSERT INTO T_Report "
				+ "(AD_PInstance_ID, PA_ReportLine_ID, Record_ID,Fact_Acct_ID,LevelNo ");
		for (int col = 0; col < m_columns.length; col++)
			insert.append(",Col_").append(col);
		// Select
		insert.append(") SELECT ")
				.append(getAD_PInstance_ID()).append(",")
				.append(m_lines[line].getPA_ReportLine_ID()).append(",")
				.append(variable).append(",Fact_Acct_ID, ");
		if (p_DetailsSourceFirst)
			insert.append("-2 ");
		else
			insert.append("2 ");

		// for all columns create select statement
		for (int col = 0; col < m_columns.length; col++)
		{
			insert.append(", ");
			// Only relative Period (not calculation or segment value)
			if (!(m_columns[col].isColumnTypeRelativePeriod()
			&& m_columns[col].getRelativePeriodAsInt() == 0))
			{
				insert.append("NULL");
				continue;
			}
			// Amount Type ... Qty
			if (m_lines[line].getPAAmountType() != null)				// line amount type overwrites column
				insert.append(m_lines[line].getSelectClause(false));
			else if (m_columns[col].getPAAmountType() != null)
				insert.append(m_columns[col].getSelectClause(false));
			else
			{
				insert.append("NULL");
				continue;
			}
		}
		//
		insert.append(" FROM Fact_Acct WHERE ")
				.append(m_lines[line].getWhereClause(p_PA_Hierarchy_ID));	// (sources, posting type)
		// Report Where
		String s = m_report.getWhereClause();
		if (s != null && s.length() > 0)
			insert.append(" AND ").append(s);
		// Period restriction
		FinReportPeriod frp = getPeriod(0);
		insert.append(" AND TRUNC(DateAcct) ")
				.append(frp.getPeriodWhere());
		// PostingType ??
		// if (!m_lines[line].isPostingType()) // only if not defined on line
		// {
		// String PostingType = m_columns[col].getPostingType();
		// if (PostingType != null && PostingType.length() > 0)
		// insert.append(" AND PostingType='").append(PostingType).append("'");
		// // globalqss - CarlosRuiz
		// if (PostingType.equals(MReportColumn.POSTINGTYPE_Budget)) {
		// if (m_columns[col].getGL_Budget_ID() > 0)
		// select.append(" AND GL_Budget_ID=" + m_columns[col].getGL_Budget_ID());
		// }
		// // end globalqss
		// }

		int no = DB.executeUpdate(insert.toString(), get_TrxName());
		log.trace("Trx #=" + no + " - " + insert);
		if (no == 0)
			return;
	}	// insertLineTrx

	/**************************************************************************
	 * Delete Unprinted Lines
	 */
	private void deleteUnprintedLines()
	{
		for (int line = 0; line < m_lines.length; line++)
		{
			// Not Printed - Delete in T
			if (!m_lines[line].isPrinted())
			{
				String sql = "DELETE FROM T_Report WHERE AD_PInstance_ID=" + getAD_PInstance_ID()
						+ " AND PA_ReportLine_ID=" + m_lines[line].getPA_ReportLine_ID();
				int no = DB.executeUpdate(sql, get_TrxName());
				if (no > 0)
					log.debug(m_lines[line].getName() + " - #" + no);
			}
		}	// for all lines
	}	// deleteUnprintedLines

	private void scaleResults()
	{

		for (int column = 0; column < m_columns.length; column++)
		{
			String factor = m_columns[column].getFactor();
			if (factor != null)
			{
				int divisor = 1;
				if (factor.equals("k"))
					divisor = 1000;
				else if (factor.equals("M"))
					divisor = 1000000;
				else
					break;

				String sql = "UPDATE T_Report SET Col_" + column
						+ "=Col_" + column + "/" + divisor
						+ " WHERE AD_PInstance_ID=" + getAD_PInstance_ID();
				int no = DB.executeUpdate(sql, get_TrxName());
				if (no > 0)
					log.debug(m_columns[column].getName() + " - #" + no);
			}
		}

	}

	/**************************************************************************
	 * Get/Create PrintFormat
	 * 
	 * @return print format
	 */
	private MPrintFormat getPrintFormat()
	{
		int AD_PrintFormat_ID = m_report.getAD_PrintFormat_ID();
		log.info("AD_PrintFormat_ID=" + AD_PrintFormat_ID);
		MPrintFormat pf = null;
		boolean createNew = AD_PrintFormat_ID == 0;

		// Create New
		if (createNew)
		{
			int AD_Table_ID = 544;		// T_Report
			pf = MPrintFormat.createFromTable(Env.getCtx(), AD_Table_ID);
			AD_PrintFormat_ID = pf.getAD_PrintFormat_ID();
			m_report.setAD_PrintFormat_ID(AD_PrintFormat_ID);
			m_report.save();
		}
		else
			pf = MPrintFormat.get(getCtx(), AD_PrintFormat_ID, false);	// use Cache

		// Print Format Sync
		if (!m_report.getName().equals(pf.getName()))
			pf.setName(m_report.getName());
		if (m_report.getDescription() == null)
		{
			if (pf.getDescription() != null)
				pf.setDescription(null);
		}
		else if (!m_report.getDescription().equals(pf.getDescription()))
			pf.setDescription(m_report.getDescription());
		pf.save();
		log.debug(pf + " - #" + pf.getItemCount());

		// Print Format Item Sync
		int count = pf.getItemCount();
		for (int i = 0; i < count; i++)
		{
			MPrintFormatItem pfi = pf.getItem(i);
			String ColumnName = pfi.getColumnName();
			//
			if (ColumnName == null)
			{
				log.error("No ColumnName for #" + i + " - " + pfi);
				if (pfi.isPrinted())
					pfi.setIsPrinted(false);
				if (pfi.isOrderBy())
					pfi.setIsOrderBy(false);
				if (pfi.getSortNo() != 0)
					pfi.setSortNo(0);
			}
			else if (ColumnName.startsWith("Col"))
			{
				int index = Integer.parseInt(ColumnName.substring(4));
				if (index < m_columns.length)
				{
					pfi.setIsPrinted(m_columns[index].isPrinted());
					String s = m_columns[index].getName();

					if (m_columns[index].isColumnTypeRelativePeriod())
					{
						BigDecimal relativeOffset = m_columns[index].getRelativePeriod();
						FinReportPeriod frp = getPeriod(relativeOffset);

						if (s.contains("@Period@"))
							s = s.replace("@Period@", frp.getName());
					}

					if (!pfi.getName().equals(s))
					{
						pfi.setName(s);
						pfi.setPrintName(s);
					}
					int seq = 30 + index;
					if (pfi.getSeqNo() != seq)
						pfi.setSeqNo(seq);

					s = m_columns[index].getFormatPattern();
					pfi.setFormatPattern(s);
				}
				else
				// not printed
				{
					if (pfi.isPrinted())
						pfi.setIsPrinted(false);
				}
				// Not Sorted
				if (pfi.isOrderBy())
					pfi.setIsOrderBy(false);
				if (pfi.getSortNo() != 0)
					pfi.setSortNo(0);
			}
			else if (ColumnName.equals("SeqNo"))
			{
				if (pfi.isPrinted())
					pfi.setIsPrinted(false);
				if (!pfi.isOrderBy())
					pfi.setIsOrderBy(true);
				if (pfi.getSortNo() != 10)
					pfi.setSortNo(10);
			}
			else if (ColumnName.equals("LevelNo"))
			{
				if (pfi.isPrinted())
					pfi.setIsPrinted(false);
				if (!pfi.isOrderBy())
					pfi.setIsOrderBy(true);
				if (pfi.getSortNo() != 20)
					pfi.setSortNo(20);
			}
			else if (ColumnName.equals("Name"))
			{
				if (pfi.getSeqNo() != 10)
					pfi.setSeqNo(10);
				if (!pfi.isPrinted())
					pfi.setIsPrinted(true);
				if (!pfi.isOrderBy())
					pfi.setIsOrderBy(true);
				if (pfi.getSortNo() != 30)
					pfi.setSortNo(30);
			}
			else if (ColumnName.equals("Description"))
			{
				if (pfi.getSeqNo() != 20)
					pfi.setSeqNo(20);
				if (!pfi.isPrinted())
					pfi.setIsPrinted(true);
				if (pfi.isOrderBy())
					pfi.setIsOrderBy(false);
				if (pfi.getSortNo() != 0)
					pfi.setSortNo(0);
			}
			else
			// Not Printed, No Sort
			{
				if (pfi.isPrinted())
					pfi.setIsPrinted(false);
				if (pfi.isOrderBy())
					pfi.setIsOrderBy(false);
				if (pfi.getSortNo() != 0)
					pfi.setSortNo(0);
			}
			pfi.save();
			log.debug(pfi.toString());
		}
		// set translated to original
		pf.setTranslation();

		// Reload to pick up changed pfi
		pf = MPrintFormat.get(getCtx(), AD_PrintFormat_ID, true);	// no cache
		return pf;
	}	// getPrintFormat

	// metas-2009_0021_AP1_CR080
	private void includeSublines()
	{
		boolean doRecalcSeqNo = false;
		boolean changed = false;
		do
		{
			changed = false;
			ArrayList<MReportLine> list = new ArrayList<MReportLine>();
			for (int line = 0; line < m_lines.length; line++)
			{
				if (m_lines[line].isLineTypeIncluded())
				{
					MReportLine.checkIncludedReportLineSetCycles(m_lines[line]);
					list.addAll(includeLine(m_lines[line]));
					changed = true;
					doRecalcSeqNo = true;
				}
				else
				{
					list.add(m_lines[line]);
				}
			}	// for all lines
			m_lines = list.toArray(new MReportLine[list.size()]);
		}
		while (changed);

		//
		// We need to recalculate the sequence numbers:
		if (doRecalcSeqNo)
		{
			int seqNo = 10;
			for (MReportLine line : m_lines)
			{
				DB.executeUpdateEx(
						"UPDATE T_Report SET SeqNo=? WHERE AD_PInstance_ID=? AND PA_ReportLine_ID=?",
						new Object[] { seqNo, getAD_PInstance_ID(), line.getPA_ReportLine_ID() },
						get_TrxName());
				seqNo += 10;
			}
		}
	}

	// metas-2009_0021_AP1_CR080
	private Collection<MReportLine> includeLine(MReportLine line)
	{
		final int included_reportLineSet_ID = line.getIncluded_ReportLineSet_ID();
		// Insert Included Lines
		StringBuilder sql = new StringBuilder("INSERT INTO T_Report "
				+ "(AD_PInstance_ID, PA_ReportLine_ID, Record_ID,Fact_Acct_ID, SeqNo,LevelNo, Name,Description) "
				+ "SELECT ?, PA_ReportLine_ID, 0,0, ?,0, Name,Description "
				+ "FROM PA_ReportLine "
				+ "WHERE IsActive='Y' AND PA_ReportLineSet_ID=?");
		int no = DB.executeUpdateEx(sql.toString(),
				new Object[] { getAD_PInstance_ID(), line.getSeqNo(), included_reportLineSet_ID },
				get_TrxName());
		log.debug("Included report lines[" + line.getName() + "] #" + no);
		// Delete old line
		DB.executeUpdateEx(
				"DELETE FROM T_Report WHERE AD_PInstance_ID=? AND PA_ReportLine_ID=?",
				new Object[] { getAD_PInstance_ID(), line.getPA_ReportLine_ID() },
				get_TrxName());
		//
		MReportLineSet includedLineSet = new MReportLineSet(getCtx(), included_reportLineSet_ID, null);
		return Arrays.asList(includedLineSet.getLiness());
	}

	// metas-2009_0021_AP1_CR080
	private MReportLine findReportLine(int PA_ReportLine_ID, MReportLineSet lineSet)
	{
		MReportLine[] lines = lineSet.getLiness();
		for (MReportLine line : lines)
		{
			if (PA_ReportLine_ID == line.getPA_ReportLine_ID())
				return line;
			//
			MReportLineSet includedLineSet = null;
			if (line.isLineTypeIncluded())
				includedLineSet = line.getIncluded_ReportLineSet();
			if (includedLineSet != null)
			{
				MReportLine line2 = findReportLine(PA_ReportLine_ID, includedLineSet);
				if (line2 != null)
					return line2;
			}
		}
		return null;
	}

	/**
	 * Creates a list of IDs that contains this line ID and all children line IDs
	 * 
	 * @param PA_ReportLineSet_ID root(scoped) line set; this will be used when we evaluate if we need to include a line in parent calculation
	 * @param line report line
	 * @param list ID list container
	 * @return will return the list parameter
	 */
	private Collection<Integer> fetchAllLineIDs(int PA_ReportLineSet_ID, MReportLine line, Collection<Integer> list)
	{
		// If is not a direct child line of main line set, we check the IsInclInParentCalc flag
		if (line.getPA_ReportLineSet_ID() != PA_ReportLineSet_ID)
		{
			if (!line.isInclInParentCalc())
			{
				log.info("Skiping indirect line inclusion: " + line + " - IsInclInParentCalc=N");
				return list;
			}
		}
		list.add(line.getPA_ReportLine_ID());
		//
		MReportLineSet includedLineSet = null;
		if (line.isLineTypeIncluded())
			includedLineSet = line.getIncluded_ReportLineSet();
		if (includedLineSet != null)
		{
			for (MReportLine includedLine : includedLineSet.getLiness())
			{
				fetchAllLineIDs(PA_ReportLineSet_ID, includedLine, list);
			}
		}

		return list;
	}

	private Collection<Integer> getAllLineIDs(int... ids)
	{
		Collection<Integer> list = new TreeSet<Integer>();
		for (int id : ids)
		{
			MReportLine line = findReportLine(id, m_report.getLineSet());
			int PA_ReportLineSet_ID = line.getPA_ReportLineSet_ID();
			fetchAllLineIDs(PA_ReportLineSet_ID, line, list);
		}
		return list;
	}

	private String getAllLineIDsSQL(int... ids)
	{
		Collection<Integer> list = getAllLineIDs(ids);
		return toSQLList(list);
	}

	private String getAllLineIntervalIDsSQL(int fromID, int toID)
	{
		log.trace("From=" + fromID + " To=" + toID);
		int firstPA_ReportLine_ID = 0;
		int lastPA_ReportLine_ID = 0;

		Collection<Integer> ids = new TreeSet<Integer>();
		MReportLine line1 = findReportLine(fromID, m_report.getLineSet());
		MReportLine line2 = findReportLine(toID, m_report.getLineSet());
		if (line1 == null)
		{
			log.warn("PA_ReportLine not found for " + fromID);
			return toSQLList(ids);
		}
		if (line2 == null)
		{
			log.warn("PA_ReportLine not found for " + toID);
			return toSQLList(ids);
		}
		if (line1.getPA_ReportLineSet_ID() != line2.getPA_ReportLineSet_ID())
		{
			// should not happen
			throw new AdempiereException("Internal Error - different PA_ReportLineSet for lines " + line1 + ", " + line2);
		}

		final int PA_ReportLineSet_ID = line1.getPA_ReportLineSet_ID();
		final MReportLine[] lines = line1.getPA_ReportLineSet().getLiness();

		// find the first and last ID
		for (int line = 0; line < lines.length; line++)
		{
			int PA_ReportLine_ID = lines[line].getPA_ReportLine_ID();
			if (PA_ReportLine_ID == fromID || PA_ReportLine_ID == toID)
			{
				if (firstPA_ReportLine_ID == 0)
				{
					firstPA_ReportLine_ID = PA_ReportLine_ID;
				}
				else
				{
					lastPA_ReportLine_ID = PA_ReportLine_ID;
					break;
				}
			}
		}

		// add to the list
		boolean addToList = false;
		for (int line = 0; line < lines.length; line++)
		{
			int PA_ReportLine_ID = lines[line].getPA_ReportLine_ID();
			log.trace("Add=" + addToList + " ID=" + PA_ReportLine_ID + " - " + m_lines[line]);
			if (addToList)
			{
				fetchAllLineIDs(PA_ReportLineSet_ID, lines[line], ids);
				if (PA_ReportLine_ID == lastPA_ReportLine_ID)		// done
					break;
			}
			else if (PA_ReportLine_ID == firstPA_ReportLine_ID)	// from already added
			{
				fetchAllLineIDs(PA_ReportLineSet_ID, lines[line], ids);
				addToList = true;
			}
		}

		return toSQLList(ids);
	}

	private String toSQLList(Collection<Integer> list)
	{
		if (list == null || list.size() == 0)
			return "-1";
		final StringBuilder sb = new StringBuilder();
		for (int id : list)
		{
			if (sb.length() > 0)
				sb.append(",");

			sb.append(id);
		}
		return sb.toString();
	}

	private boolean isLineTypeIncluded(int PA_ReportLine_ID)
	{
		MReportLine line = findReportLine(PA_ReportLine_ID, m_report.getLineSet());
		return line.isLineTypeIncluded();
	}

	private int getIncludedLineID(int PA_ReportLine_ID)
	{
		if (isLineTypeIncluded(PA_ReportLine_ID))
		{
			Collection<Integer> ids = getAllLineIDs(PA_ReportLine_ID);
			ids.remove(PA_ReportLine_ID);
			if (ids.size() != 1)
			{
				throw new AdempiereException("Calculation not supported for included linesets with more then one line included in parent calculation"); // TODO: translate
			}
			return ids.iterator().next();
		}
		else
		{
			return PA_ReportLine_ID;
		}

	}
	
	protected final MReport getPA_Report()
	{
		return m_report;
	}
}	// FinReport
