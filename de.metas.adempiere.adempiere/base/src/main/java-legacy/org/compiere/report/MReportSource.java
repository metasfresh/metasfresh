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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.PO;
import org.compiere.model.X_PA_ReportSource;

import de.metas.acct.api.AcctSchemaElementType;

/**
 * Report Line Source Model
 *
 * @author Jorg Janke
 * @version $Id: MReportSource.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class MReportSource extends X_PA_ReportSource
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6085437491271873555L;

	/**
	 * Constructor
	 * 
	 * @param ctx context
	 * @param PA_ReportSource_ID id
	 * @param trxName transaction
	 */
	public MReportSource(final Properties ctx, final int PA_ReportSource_ID, final String trxName)
	{
		super(ctx, PA_ReportSource_ID, trxName);
		if (PA_ReportSource_ID == 0)
		{
		}
	}	// MReportSource

	/**
	 * Constructor
	 * 
	 * @param ctx context
	 * @param rs ResultSet to load from
	 * @param trxName transaction
	 */
	public MReportSource(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MReportSource

	/**
	 * Get SQL where clause from this source's <code>ElementType</code> and the respective value. If the value is zero/null, then the string <code>1=1</code> is returned.
	 * 
	 * @param PA_Hierarchy_ID hierarchy
	 * @return where clause
	 */
	public String getWhereClause(final int PA_Hierarchy_ID)
	{
		final AcctSchemaElementType acctSchemaElementType;
		final int ID; // ID for Tree Leaf Value
		
		final String reportElementType = getElementType();
		if (X_PA_ReportSource.ELEMENTTYPE_Account.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Account;
			ID = getC_ElementValue_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_Activity.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Activity;
			ID = getC_Activity_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_BPartner.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.BPartner;
			ID = getC_BPartner_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_Campaign.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Campaign;
			ID = getC_Campaign_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_LocationFrom.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.LocationFrom;
			ID = getC_Location_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_LocationTo.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.LocationTo;
			ID = getC_Location_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_Organization.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Organization;
			ID = getOrg_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_Product.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Product;
			ID = getM_Product_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_Project.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.Project;
			ID = getC_Project_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_SalesRegion.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.SalesRegion;
			ID = getC_SalesRegion_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_OrgTrx.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.OrgTrx;
			ID = getOrg_ID();	// (re)uses Org_ID
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_UserList1.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserList1;
			ID = getC_ElementValue_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_UserList2.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserList2;
			ID = getC_ElementValue_ID();
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_UserElement1.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserElement1;
			return "UserElement1_ID=" + getUserElement1_ID(); // Not Tree
		}
		else if (X_PA_ReportSource.ELEMENTTYPE_UserElement2.equals(reportElementType))
		{
			acctSchemaElementType = AcctSchemaElementType.UserElement2;
			return "UserElement2_ID=" + getUserElement2_ID(); // Not Tree
		}
		// Financial Report Source with Type Combination
		else if (X_PA_ReportSource.ELEMENTTYPE_Combination.equals(reportElementType))
		{
			return getWhereCombination(PA_Hierarchy_ID);
		}
		else
		{
			acctSchemaElementType = null;
			ID = -1;
		}
		
		if (ID <= 0)
		{
			// task 07492 this report source record is used only for grouping, not for filtering.
			return "1=1";
		}

		//
		return MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, acctSchemaElementType, ID);
	}	// getWhereClause

	/**
	 * Obtain where clause for the combination type
	 * 
	 * @param PA_Hierarchy_ID
	 * @return
	 */
	private String getWhereCombination(final int PA_Hierarchy_ID)
	{
		final StringBuffer whcomb = new StringBuffer();
		if (getC_ElementValue_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Account, getC_ElementValue_ID());
			if (isIncludeNullsElementValue())
			{
				whcomb.append(" AND (Account_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsElementValue())
		{
			whcomb.append(" AND Account_ID IS NULL");
		}

		if (getC_Activity_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Activity, getC_Activity_ID());
			if (isIncludeNullsActivity())
			{
				whcomb.append(" AND (C_Activity_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsActivity())
		{
			whcomb.append(" AND C_Activity_ID IS NULL");
		}

		if (getC_BPartner_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.BPartner, getC_BPartner_ID());
			if (isIncludeNullsBPartner())
			{
				whcomb.append(" AND (C_BPartner_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsBPartner())
		{
			whcomb.append(" AND C_BPartner_ID IS NULL");
		}

		if (getC_Campaign_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Campaign, getC_Campaign_ID());
			if (isIncludeNullsCampaign())
			{
				whcomb.append(" AND (C_Campaign_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsCampaign())
		{
			whcomb.append(" AND C_Campaign_ID IS NULL");
		}

		if (getC_Location_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.LocationFrom, getC_Location_ID());
			if (isIncludeNullsLocation())
			{
				whcomb.append(" AND (C_LocFrom_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsLocation())
		{
			whcomb.append(" AND C_LocFrom_ID IS NULL");
		}

		if (getOrg_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Organization, getOrg_ID());
			if (isIncludeNullsOrg())
			{
				whcomb.append(" AND (AD_Org_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsOrg())
		{
			whcomb.append(" AND AD_Org_ID IS NULL");
		}

		if (getAD_OrgTrx_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.OrgTrx, getAD_OrgTrx_ID());
			if (isIncludeNullsOrgTrx())
			{
				whcomb.append(" AND (AD_OrgTrx_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsOrgTrx())
		{
			whcomb.append(" AND AD_OrgTrx_ID IS NULL");
		}

		if (getM_Product_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Product, getM_Product_ID());
			if (isIncludeNullsProduct())
			{
				whcomb.append(" AND (M_Product_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsProduct())
		{
			whcomb.append(" AND M_Product_ID IS NULL");
		}

		if (getC_Project_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.Project, getC_Project_ID());
			if (isIncludeNullsProject())
			{
				whcomb.append(" AND (C_Project_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsProject())
		{
			whcomb.append(" AND C_Project_ID IS NULL");
		}

		if (getC_SalesRegion_ID() > 0)
		{
			final String whtree = MReportTree.getWhereClause(getCtx(), PA_Hierarchy_ID, AcctSchemaElementType.SalesRegion, getC_SalesRegion_ID());
			if (isIncludeNullsSalesRegion())
			{
				whcomb.append(" AND (C_SalesRegion_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsSalesRegion())
		{
			whcomb.append(" AND C_SalesRegion_ID IS NULL");
		}

		if (getUserElement1_ID() > 0)
		{
			final String whtree = "UserElement1_ID=" + getUserElement1_ID(); // No Tree
			if (isIncludeNullsUserElement1())
			{
				whcomb.append(" AND (UserElement1_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsUserElement1())
		{
			whcomb.append(" AND UserElement1_ID IS NULL");
		}

		if (getUserElement2_ID() > 0)
		{
			final String whtree = "UserElement2_ID=" + getUserElement2_ID(); // No Tree
			if (isIncludeNullsUserElement2())
			{
				whcomb.append(" AND (UserElement2_ID IS NULL OR ").append(whtree).append(")");
			}
			else
			{
				whcomb.append(" AND ").append(whtree);
			}
		}
		else if (isIncludeNullsUserElement2())
		{
			whcomb.append(" AND UserElement2_ID IS NULL");
		}

		// drop the first " AND "
		if (whcomb.length() > 5 && whcomb.toString().startsWith(" AND "))
		{
			whcomb.delete(0, 5);
		}

		return whcomb.toString();
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MReportSource[")
				.append(get_ID()).append(" - ").append(getDescription())
				.append(" - ").append(getElementType());
		sb.append("]");
		return sb.toString();
	}	// toString

	/**************************************************************************
	 * Copy Constructor
	 * 
	 * @param ctx context
	 * @param AD_Client_ID parent
	 * @param AD_Org_ID parent
	 * @param PA_ReportLine_ID parent
	 * @param source copy source
	 * @param trxName transaction
	 * @return Report Source
	 */
	public static MReportSource copy(final Properties ctx, final int AD_Client_ID, final int AD_Org_ID,
			final int PA_ReportLine_ID, final MReportSource source, final String trxName)
	{
		final MReportSource retValue = new MReportSource(ctx, 0, trxName);
		PO.copyValues(source, retValue, AD_Client_ID, AD_Org_ID);
		retValue.setPA_ReportLine_ID(PA_ReportLine_ID);
		return retValue;
	}	// copy

}	// MReportSource
