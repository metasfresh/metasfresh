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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_PA_ReportLine;
import org.compiere.util.DB;

import de.metas.acct.api.AcctSchemaElementType;
import de.metas.util.Check;


/**
 *	Report Line Model
 *
 *  @author Jorg Janke
 *  @version $Id: MReportLine.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class MReportLine extends X_PA_ReportLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3957315092529097396L;

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param PA_ReportLine_ID id
	 * 	@param trxName transaction
	 */
	public MReportLine (Properties ctx, int PA_ReportLine_ID, String trxName)
	{
		super (ctx, PA_ReportLine_ID, trxName);
		if (PA_ReportLine_ID == 0)
		{
			setSeqNo (0);
		//	setIsSummary (false);		//	not active in DD 
			setIsPrinted (false);
		}
		else
			loadSources();
	}	//	MReportLine

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param rs ResultSet to load from
	 * 	@param trxName transaction
	 */
	public MReportLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		loadSources();
	}	//	MReportLine

	/**	Containt Sources				*/
	private MReportSource[]		m_sources = null;
	/** Cache result					*/
	private String				m_whereClause = null;

	/**
	 * 	Load contained Sources
	 */
	private void loadSources()
	{
		//FIXME: move to DAO (today I'm out of time :-( )
		ArrayList<MReportSource> list = new ArrayList<MReportSource>();
		String sql = "SELECT * FROM PA_ReportSource WHERE PA_ReportLine_ID=? AND IsActive='Y' ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getPA_ReportLine_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MReportSource (getCtx(), rs, null));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(null, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}
		//
		m_sources = new MReportSource[list.size()];
		list.toArray(m_sources);
		log.trace("ID=" + getPA_ReportLine_ID()
			+ " - Size=" + list.size());
	}	//	loadSources

	/**
	 * 	Get Sources
	 * 	@return sources
	 */
	public MReportSource[] getSources()
	{
		return m_sources;
	}	//	getSources

	/**
	 * 	List Info
	 */
	public void list()
	{
		System.out.println("- " + toString());
		if (m_sources == null)
			return;
		for (int i = 0; i < m_sources.length; i++)
			System.out.println("  - " + m_sources[i].toString());
	}	//	list


	/**
	 * Get Source Column Name or <code>null</code> if there more or less than one source.
	 * 
	 * @return Source ColumnName
	 */
	public String getSourceColumnName()
	{
		final Set<String> columnNames = new HashSet<>();
		for (final MReportSource source : m_sources)
		{
			final AcctSchemaElementType elementType = AcctSchemaElementType.ofCodeOrNull(source.getElementType());
			if(elementType == null)
			{
				continue;
			}
			
			final String columnName = elementType.getColumnName();
			columnNames.add(columnName);
		}
		
		if(columnNames.isEmpty())
		{
			return null;
		}
		else if(columnNames.size() == 1)
		{
			return columnNames.iterator().next();
		}
		else
		{
			log.info("More than one source column found: {}. Returning null", columnNames);
			return null;
		}
	}	//	getColumnName


	/**
	 * 	Get SQL Select Clause.
	 * 	@param withSum with SUM() function
	 * 	@return select clause - AmtAcctCR+AmtAcctDR/etc or "null" if not defined
	 */
	public String getSelectClause (boolean withSum)
	{
		String at = getPAAmountType().substring(0,1);	//	first letter
		StringBuffer sb = new StringBuffer();
		if (withSum)
			sb.append("SUM(");
		if (PAAMOUNTTYPE_BalanceExpectedSign.equals(at))
		//	sb.append("AmtAcctDr-AmtAcctCr");
			sb.append("acctBalance(Account_ID,AmtAcctDr,AmtAcctCr)");
		else if (PAAMOUNTTYPE_BalanceAccountedSign.equals(at))
			sb.append("AmtAcctDr-AmtAcctCr");
		else if (PAAMOUNTTYPE_CreditOnly.equals(at))
			sb.append("AmtAcctCr");
		else if (PAAMOUNTTYPE_DebitOnly.equals(at))
			sb.append("AmtAcctDr");
		else if (PAAMOUNTTYPE_QuantityAccountedSign.equals(at))
			sb.append("Qty");
		else if (PAAMOUNTTYPE_QuantityExpectedSign.equals(at))
				sb.append("acctBalance(Account_ID,Qty,0)");		
		else
		{
			log.error("AmountType=" + getPAAmountType () + ", at=" + at);
			return "NULL";
		}
		if (withSum)
			sb.append(")");
		return sb.toString();
	}	//	getSelectClause

	/**
	 * 	Is it Period ?
	 * 	@return true if Period Amount Type
	 */
	public boolean isPeriod()
	{
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		return PAPERIODTYPE_Period.equals(pt);
	}	//	isPeriod

	/**
	 * 	Is it Year ?
	 * 	@return true if Year Amount Type
	 */
	public boolean isYear()
	{
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		return PAPERIODTYPE_Year.equals(pt);
	}	//	isYear

	/**
	 * 	Is it Total ?
	 * 	@return true if Year Amount Type
	 */
	public boolean isTotal()
	{
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		return PAPERIODTYPE_Total.equals(pt);
	}	//	isTotal
	
	/**
	 * Is it natural balance ?
	 * Natural balance means year balance for profit and loss a/c, total balance for balance sheet account
	 * @return true if Natural Balance Amount Type
	 */
	public boolean isNatural()
	{
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		
		return PAPERIODTYPE_Natural.equals(pt);
	}

	/**
	 * 	Get SQL where clause (sources, posting type)
	 * 	@param PA_Hierarchy_ID hierarchy
	 * 	@return where clause
	 */
	public String getWhereClause(int PA_Hierarchy_ID)
	{
		if (m_sources == null)
		{
			return "";
		}
		if (m_whereClause == null)
		{
			//	Only one
			if (m_sources.length == 0)
			{
				m_whereClause = "";
			}
			else if (m_sources.length == 1)
			{
				m_whereClause = m_sources[0].getWhereClause(PA_Hierarchy_ID);
			}
			else
			{
				//	Multiple
				final StringBuffer sb = new StringBuffer ("(");
				for (int i = 0; i < m_sources.length; i++)
				{
					if (i > 0)
					{
						sb.append (" AND "); // task 07429 we "AND" the sources' where clauses, instead of ORing them
					}
					sb.append (m_sources[i].getWhereClause(PA_Hierarchy_ID));
				}
				sb.append (")");
				m_whereClause = sb.toString ();
			}
			//	Posting Type
			final String PostingType = getPostingType();
			if (!Check.isEmpty(PostingType))
			{
				if (m_whereClause.length() > 0)
				{
					m_whereClause += " AND ";
				}
				m_whereClause += "PostingType='" + PostingType + "'";
				// globalqss - CarlosRuiz
				if (PostingType.equals(MReportLine.POSTINGTYPE_Budget)) 
				{
					if (getGL_Budget_ID() > 0)
					{
						m_whereClause += " AND GL_Budget_ID=" + getGL_Budget_ID();
					}
				}
				// end globalqss
			}
			log.debug(m_whereClause);
		}
		return m_whereClause;
	}	//	getWhereClause

	/**
	 * 	Has Posting Type
	 *	@return true if posting
	 */
	public boolean isPostingType()
	{
		String PostingType = getPostingType();
		return (PostingType != null && PostingType.length() > 0);
	}	//	isPostingType

	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MReportLine[")
			.append(get_ID()).append(" - ").append(getName()).append(" - ").append(getDescription())
			.append(", SeqNo=").append(getSeqNo()).append(", AmountType=").append(getPAAmountType())
			.append(", PeriodType=").append(getPAPeriodType())
			.append(" - LineType=").append(getLineType());
		if (isLineTypeCalculation())
			sb.append(" - Calculation=").append(getCalculationType())
				.append(" - ").append(getOper_1_ID()).append(" - ").append(getOper_2_ID());
		else	//	SegmentValue
			sb.append(" - SegmentValue - PostingType=").append(getPostingType())
				.append(", AmountType=").append(getPAAmountType())
				.append(", PeriodType=").append(getPAPeriodType());
		sb.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Line Type Calculation
	 *	@return true if calculation
	 */
	public boolean isLineTypeCalculation()
	{
		return LINETYPE_Calculation.equals(getLineType());
	}
	/**
	 * 	Line Type Segment Value
	 *	@return true if segment value
	 */
	public boolean isLineTypeSegmentValue()
	{
		return LINETYPE_SegmentValue.equals(getLineType());
	}
	
	/**
	 * 	Calculation Type Range
	 *	@return true if range
	 */
	public boolean isCalculationTypeRange()
	{
		return CALCULATIONTYPE_AddRangeOp1ToOp2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Add
	 *	@return true if add
	 */
	public boolean isCalculationTypeAdd()
	{
		return CALCULATIONTYPE_AddOp1PlusOp2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Subtract
	 *	@return true if subtract
	 */
	public boolean isCalculationTypeSubtract()
	{
		return CALCULATIONTYPE_SubtractOp1_Op2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Percent
	 *	@return true if percent
	 */
	public boolean isCalculationTypePercent()
	{
		return CALCULATIONTYPE_PercentageOp1OfOp2.equals(getCalculationType());
	}

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		checkIncludedReportLineSetCycles(this); // metas
		if (LINETYPE_SegmentValue.equals(getLineType()))
		{
			if (getCalculationType() != null)
				setCalculationType(null);
			if (getOper_1_ID() != 0)
				setOper_1_ID(0);
			if (getOper_2_ID() != 0)
				setOper_2_ID(0);
		}
		return true;
	}	//	beforeSave
	

	/**************************************************************************
	 * 	Copy
	 * 	@param ctx context
	 * 	@param AD_Client_ID parent
	 * 	@param AD_Org_ID parent
	 * 	@param PA_ReportLineSet_ID parent
	 * 	@param source copy source
	 * 	@param trxName transaction
	 * 	@return Report Line
	 */
	public static MReportLine copy (Properties ctx, int AD_Client_ID, int AD_Org_ID, 
		int PA_ReportLineSet_ID, MReportLine source, String trxName)
	{
		MReportLine retValue = new MReportLine (ctx, 0, trxName);
		MReportLine.copyValues(source, retValue, AD_Client_ID, AD_Org_ID);
		//
		retValue.setPA_ReportLineSet_ID(PA_ReportLineSet_ID);
		retValue.setOper_1_ID(0);
		retValue.setOper_2_ID(0);
		return retValue;
	}	//	copy

	public MReportLineSet getIncluded_ReportLineSet()
	{
		if (!isLineTypeIncluded() || getIncluded_ReportLineSet_ID() <= 0)
		{
			includedLineSet = null;
			return null;
		}
		if (includedLineSet != null)
			return includedLineSet;
		includedLineSet = new MReportLineSet(getCtx(), getIncluded_ReportLineSet_ID(), get_TrxName());
		return includedLineSet;
	}
	private MReportLineSet includedLineSet = null;
	
	public static final String LINETYPE_IncludedLineSet = "I";
	public boolean isLineTypeIncluded()
	{
		return LINETYPE_IncludedLineSet.equals(this.getLineType());
	}

	public int getIncluded_ReportLineSet_ID()
	{
		return this.get_ValueAsInt("Included_ReportLineSet_ID");
	}
	
	public boolean isSuppressZeroLine()
	{
		return this.get_ValueAsBoolean("IsSuppressZeroLine");
	}
	
	@Override
	public MReportLineSet getPA_ReportLineSet()
	{
		if (parent == null)
		{
			parent = (MReportLineSet)super.getPA_ReportLineSet();
		}
		return parent;
	}
	void setParent(MReportLineSet lineSet)
	{
		this.parent = lineSet;
	}
	private MReportLineSet parent = null;
	
	public boolean isInclInParentCalc()
	{
		return this.get_ValueAsBoolean("IsInclInParentCalc");
	}

	public static void checkIncludedReportLineSetCycles(MReportLine line)
	{
		checkIncludedReportLineSetCycles(line, new TreeSet<Integer>(), new StringBuffer());
	}
	private static void checkIncludedReportLineSetCycles(MReportLine line, Collection<Integer> trace, StringBuffer traceStr)
	{
		StringBuffer traceStr2 = new StringBuffer(traceStr);
		if (traceStr2.length() > 0)
			traceStr2.append(" - ");
		traceStr2.append(line.getName());
		
		if (trace.contains(line.getPA_ReportLine_ID()))
		{
			throw new AdempiereException("@PA_ReportLine_ID@ Loop Detected: "+traceStr2.toString()); // TODO: translate
		}
		Collection<Integer> trace2 = new TreeSet<Integer>(trace);
		trace2.add(line.getPA_ReportLine_ID());
		
		if (line.isLineTypeIncluded() && line.getPA_ReportLineSet() != null)
		{
			MReportLineSet includedSet = line.getIncluded_ReportLineSet();
			traceStr2.append("(").append(includedSet.getName()).append(")");
			for (MReportLine includedLine : includedSet.getLiness())
			{
				checkIncludedReportLineSetCycles(includedLine, trace2, traceStr2);
			}
		}
	}
}	//	MReportLine
