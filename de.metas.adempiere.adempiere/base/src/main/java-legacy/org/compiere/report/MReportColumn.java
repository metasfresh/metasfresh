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
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_PA_ReportColumn;

import de.metas.acct.api.AcctSchemaElementType;

/**
 *  Report Column Model
 *
 *  @author Jorg Janke
 *  @version $Id: MReportColumn.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class MReportColumn extends X_PA_ReportColumn
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2395905882810790219L;

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param PA_ReportColumn_ID id
	 * 	@param trxName transaction
	 */
	public MReportColumn (Properties ctx, int PA_ReportColumn_ID, String trxName)
	{
		super (ctx, PA_ReportColumn_ID, trxName);
		if (PA_ReportColumn_ID == 0)
		{
			setIsPrinted (true);
			setSeqNo (0);
		}
	}	//	MReportColumn

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param rs ResultSet to load from
	 * 	@param trxName transaction
	 */
	public MReportColumn (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MReportColumn

	/**************************************************************************
	 * 	Get Column SQL Select Clause.
	 * 	@param withSum with SUM() function
	 * 	@return select clause - AmtAcctCR+AmtAcctDR/etc or "null" if not defined
	 */
	public String getSelectClause (boolean withSum)
	{
		String amountType = getPAAmountType();	//	first character
		StringBuffer sb = new StringBuffer();
		if (withSum)
			sb.append("SUM(");
		if (PAAMOUNTTYPE_BalanceExpectedSign.equals(amountType))
		//	sb.append("AmtAcctDr-AmtAcctCr");
			sb.append("acctBalance(Account_ID,AmtAcctDr,AmtAcctCr)");
		else if ( PAAMOUNTTYPE_BalanceAccountedSign.equals(amountType) )
			sb.append("AmtAcctDr-AmtAcctCr");
		else if (PAAMOUNTTYPE_CreditOnly.equals(amountType))
			sb.append("AmtAcctCr");
		else if (PAAMOUNTTYPE_DebitOnly.equals(amountType))
			sb.append("AmtAcctDr");
		else if (PAAMOUNTTYPE_QuantityAccountedSign.equals(amountType))
			sb.append("Qty");
		else if (PAAMOUNTTYPE_QuantityExpectedSign.equals(amountType))
			sb.append("acctBalance(Account_ID,Qty,0)");
		else
		{
			log.error("AmountType=" + getPAAmountType () + ", at=" + amountType);
			return "NULL";
		}
		if (withSum)
			sb.append(")");
		return sb.toString();
	}	//	getSelectClause

	/**
	 * 	Is it Period Info ?
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
	 * 	Is it Year Info ?
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
	 * 	Is it Total Info ?
	 * 	@return true if Year Amount Type
	 */
	public boolean isTotal()
	{
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		return PAPERIODTYPE_Total.equals(pt);
	}	//	isTotalBalance

	/**
	 * Is it natural balance ?
	 * Natural balance means year balance for profit and loss a/c, total balance for balance sheet account
	 * @return true if Natural Balance Amount Type
	 */
	public boolean isNatural() {
		String pt = getPAPeriodType();
		if (pt == null)
			return false;
		return PAPERIODTYPE_Natural.equals(pt);
	}

	/**
	 * 	Get Segment Value Where Clause
	 * 	@param PA_Hierarchy_ID hierarchy
	 * 	@return where clause, prefixed with AND
	 */
	public String getWhereClause(int PA_Hierarchy_ID)
	{
		if (!isColumnTypeSegmentValue())
		{
			return "";
		}
		
		final AcctSchemaElementType acctSchemaElementType;
		final int id; // ID for Tree Leaf Value
		
		final String reportElementType = getElementType();
		if (MReportColumn.ELEMENTTYPE_Account.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Account;
			id = getC_ElementValue_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_Activity.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Activity;
			id = getC_Activity_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_BPartner.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.BPartner;
			id = getC_BPartner_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_Campaign.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Campaign;
			id = getC_Campaign_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_LocationFrom.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.LocationFrom;
			id = getC_Location_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_LocationTo.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.LocationTo;
			id = getC_Location_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_Organization.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Organization;
			id = getOrg_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_Product.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Product;
			id = getM_Product_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_Project.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Project;
			id = getC_Project_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_SalesRegion.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.SalesRegion;
			id = getC_SalesRegion_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_OrgTrx.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.OrgTrx;
			id = getOrg_ID();	//	(re)uses Org_ID
		}
		else if (MReportColumn.ELEMENTTYPE_UserList1.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserList1;
			id = getC_ElementValue_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_UserList2.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserList2;
			id = getC_ElementValue_ID();
		}
		else if (MReportColumn.ELEMENTTYPE_UserElement1.equals(reportElementType))
		{
			return " AND UserElement1_ID="+getUserElement1_ID(); // Not Tree
		}
		else if (MReportColumn.ELEMENTTYPE_UserElement2.equals(reportElementType))
		{
			return " AND UserElement2_ID="+getUserElement2_ID(); // Not Tree
		}
		// Financial Report Source with Type Combination
		else if (MReportColumn.ELEMENTTYPE_Combination.equals(reportElementType))
		{
			return getWhereCombination(PA_Hierarchy_ID);
		}
		else
		{
			log.warn("Unsupported Element Type={}", reportElementType);
			return "";
		}

		if (id <= 0)
		{
			log.debug("No Restrictions - No ID for EntityType=" + reportElementType);
			return "";
		}
		
		
		return " AND " + MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, acctSchemaElementType, id);
	}	//	getWhereClause
	
	/**
	 * Obtain where clause for the combination type
	 * @param PA_Hierarchy_ID
	 * @return
	 */
	private String getWhereCombination(int PA_Hierarchy_ID) {
		StringBuffer whcomb = new StringBuffer();
		if (getC_ElementValue_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Account, getC_ElementValue_ID());
			if (isIncludeNullsElementValue())
				whcomb.append(" AND (Account_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsElementValue())
				whcomb.append(" AND Account_ID IS NULL");

		if (getC_Activity_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Activity, getC_Activity_ID());
			if (isIncludeNullsActivity())
				whcomb.append(" AND (C_Activity_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsActivity())
				whcomb.append(" AND C_Activity_ID IS NULL");

		if (getC_BPartner_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.BPartner, getC_BPartner_ID());
			if (isIncludeNullsBPartner())
				whcomb.append(" AND (C_BPartner_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsBPartner())
				whcomb.append(" AND C_BPartner_ID IS NULL");

		if (getC_Campaign_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Campaign, getC_Campaign_ID());
			if (isIncludeNullsCampaign())
				whcomb.append(" AND (C_Campaign_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsCampaign())
				whcomb.append(" AND C_Campaign_ID IS NULL");

		if (getC_Location_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.LocationFrom, getC_Location_ID());
			if (isIncludeNullsLocation())
				whcomb.append(" AND (C_LocFrom_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsLocation())
				whcomb.append(" AND C_LocFrom_ID IS NULL");

		if (getOrg_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Organization, getOrg_ID());
			if (isIncludeNullsOrg())
				whcomb.append(" AND (AD_Org_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsOrg())
				whcomb.append(" AND AD_Org_ID IS NULL");
		
		if (getAD_OrgTrx_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.OrgTrx, getAD_OrgTrx_ID());
			if (isIncludeNullsOrgTrx())
				whcomb.append(" AND (AD_OrgTrx_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsOrgTrx())
				whcomb.append(" AND AD_OrgTrx_ID IS NULL");
		

		if (getM_Product_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Product, getM_Product_ID());
			if (isIncludeNullsProduct())
				whcomb.append(" AND (M_Product_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsProduct())
				whcomb.append(" AND M_Product_ID IS NULL");

		if (getC_Project_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Project, getC_Project_ID());
			if (isIncludeNullsProject())
				whcomb.append(" AND (C_Project_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsProject())
				whcomb.append(" AND C_Project_ID IS NULL");

		if (getC_SalesRegion_ID() > 0) {
			String whtree = MReportTree.getWhereClause (getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.SalesRegion, getC_SalesRegion_ID());
			if (isIncludeNullsSalesRegion())
				whcomb.append(" AND (C_SalesRegion_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsSalesRegion())
				whcomb.append(" AND C_SalesRegion_ID IS NULL");

		if (getUserElement1_ID() > 0) {
			String whtree = "UserElement1_ID=" + getUserElement1_ID(); // No Tree
			if (isIncludeNullsUserElement1())
				whcomb.append(" AND (UserElement1_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsUserElement1())
				whcomb.append(" AND UserElement1_ID IS NULL");

		if (getUserElement2_ID() > 0) {
			String whtree = "UserElement2_ID=" + getUserElement2_ID(); // No Tree
			if (isIncludeNullsUserElement2())
				whcomb.append(" AND (UserElement2_ID IS NULL OR ").append(whtree).append(")");
			else
				whcomb.append(" AND ").append(whtree);
		} else
			if (isIncludeNullsUserElement2())
				whcomb.append(" AND UserElement2_ID IS NULL");

		return whcomb.toString();
	}

	
	/**
	 * 	Get String Representation
	 * 	@return	String Representation
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MReportColumn[")
			.append(get_ID()).append(" - ").append(getName()).append(" - ").append(getDescription())
			.append(", SeqNo=").append(getSeqNo()).append(", AmountType=").append(getPAAmountType())
			.append(", PeriodType=").append(getPAPeriodType())
			.append(", CurrencyType=").append(getCurrencyType()).append("/").append(getC_Currency_ID())
			.append(" - ColumnType=").append(getColumnType());
		if (isColumnTypeCalculation())
			sb.append(" - Calculation=").append(getCalculationType())
				.append(" - ").append(getOper_1_ID()).append(" - ").append(getOper_2_ID());
		else if (isColumnTypeRelativePeriod())
			sb.append(" - Period=").append(getRelativePeriod());
		else
			sb.append(" - SegmentValue ElementType=").append(getElementType());
		sb.append ("]");
		return sb.toString ();
	}	//	toString

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
	 *	@return true id add
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
	 * 	Column Type Calculation
	 *	@return true if calculation
	 */
	public boolean isColumnTypeCalculation()
	{
		return COLUMNTYPE_Calculation.equals(getColumnType());
	}
	/**
	 * 	Column Type Relative Period
	 *	@return true if relative period
	 */
	public boolean isColumnTypeRelativePeriod()
	{
		return COLUMNTYPE_RelativePeriod.equals(getColumnType());
	}
	/**
	 * 	Column Type Segment Value
	 *	@return true if segment value
	 */
	public boolean isColumnTypeSegmentValue()
	{
		return COLUMNTYPE_SegmentValue.equals(getColumnType());
	}
	/**
	 * 	Get Relative Period As Int
	 *	@return relative period
	 */
	public int getRelativePeriodAsInt ()
	{
		BigDecimal bd = getRelativePeriod();
		if (bd == null)
			return 0;
		return bd.intValue();
	}	//	getRelativePeriodAsInt

	/**
	 *	Get Relative Period
	 *	@return relative period
	 */
	@Override
	public BigDecimal getRelativePeriod()
	{
		if (getColumnType().equals(COLUMNTYPE_RelativePeriod)
			|| getColumnType().equals(COLUMNTYPE_SegmentValue))
			return super.getRelativePeriod();
		return null;
	}	//	getRelativePeriod
	/**
	 *	Get Element Type
	 */
	@Override
	public String getElementType()
	{
		if (getColumnType().equals(COLUMNTYPE_SegmentValue))
		{
			return super.getElementType();
		}
		else
		{
			return null;
		}
	}	//	getElementType
	
	/**
	 *	Get Calculation Type
	 */
	@Override
	public String getCalculationType()
	{
		if (getColumnType().equals(COLUMNTYPE_Calculation))
			return super.getCalculationType();
		return null;
	}	//	getCalculationType
	
	/**
	 *	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//	Validate Type
		String ct = getColumnType();
		if (ct.equals(COLUMNTYPE_RelativePeriod))
		{
			setElementType(null);
			setCalculationType(null);
		}
		else if (ct.equals(COLUMNTYPE_Calculation))
		{
			setElementType(null);
			setRelativePeriod(null);
		}
		else if (ct.equals(COLUMNTYPE_SegmentValue))
		{
			setCalculationType(null);
		}
		return true;
	}	//	beforeSave
	/**************************************************************************

	/**
	 * 	Copy
	 * 	@param ctx context
	 * 	@param AD_Client_ID parent
	 * 	@param AD_Org_ID parent
	 * 	@param PA_ReportColumnSet_ID parent
	 * 	@param source copy source
	 * 	@param trxName transaction
	 * 	@return Report Column
	 */
	public static MReportColumn copy (Properties ctx, int AD_Client_ID, int AD_Org_ID, 
		int PA_ReportColumnSet_ID, MReportColumn source, String trxName)
	{
		MReportColumn retValue = new MReportColumn (ctx, 0, trxName);
		MReportColumn.copyValues(source, retValue, AD_Client_ID, AD_Org_ID);
		//
		retValue.setPA_ReportColumnSet_ID(PA_ReportColumnSet_ID);	//	parent
		retValue.setOper_1_ID(0);
		retValue.setOper_2_ID(0);
		return retValue;
	}	//	copy
}	//	MReportColumn
