package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.AbstractList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MPeriod;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.adempiere.service.ISweepTableBL;
import de.metas.commission.model.I_C_AdvComFact_SalesRepFact;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.I_C_AdvCommissionPayroll;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Helper process that supports regression tests by deleting all commission data that was created after a given date
 * 
 * @author ts
 * 
 */
public class DeleteCommissionData extends SvrProcess
{

	private MPeriod period;

	@Override
	protected String doIt() throws Exception
	{

		//
		// commission facts
		final int[] possibleFactIdsToDelete =
				new Query(getCtx(), I_C_AdvCommissionFact.Table_Name, I_C_AdvCommissionFact.COLUMNNAME_Created + ">=?", get_TrxName())
						.setParameters(period.getStartDate())
						.setClient_ID()
						.getIDs();

		final Set<Integer> factIdsToDelete = new HashSet<Integer>();

		for (final int possibleFactIdToDelete : possibleFactIdsToDelete)
		{
			final MCAdvCommissionFact possibleFactToDelete = new MCAdvCommissionFact(getCtx(), possibleFactIdToDelete, get_TrxName());
			final PO referencedPO = possibleFactToDelete.retrievePO();

			final Timestamp dateToUse;
			if (I_C_AdvCommissionPayrollLine.Table_Name.equals(referencedPO.get_TableName()))
			{
				dateToUse = period.getEndDate();
			}
			else
			{
				dateToUse = period.getStartDate();
			}
			if (!referencedPO.getCreated().before(dateToUse))
			{
				factIdsToDelete.add(possibleFactToDelete.get_ID());
			}
		}

		final ISweepTableBL sweepTableBL = Services.get(ISweepTableBL.class);
		sweepTableBL.sweepTable(getCtx(), I_C_AdvCommissionFact.Table_Name, factIdsToDelete, -1, this, get_TrxName());

		for (final int factIdToDelete : factIdsToDelete)
		{
			//
			// find that existing fact that won't be deleted, but have a deletable fact as followup
			final String whereClause = I_C_AdvCommissionFact.COLUMNNAME_C_AdvComFact_IDs_FollowUp + " LIKE ?";

			final List<I_C_AdvCommissionFact> factsToEdit =
					new Query(getCtx(), I_C_AdvCommissionFact.Table_Name, whereClause, get_TrxName())
							.setParameters(Integer.toString(factIdToDelete))
							.setClient_ID()
							.list(I_C_AdvCommissionFact.class);

			for (final I_C_AdvCommissionFact factToEdit : factsToEdit)
			{
				if (factIdsToDelete.contains(factToEdit.getC_AdvCommissionFact_ID()))
				{
					// no need to edit the follow-up info of 'factToEdit', because it will also be deleted
					continue;
				}

				// remove 'factIdToDelete' from the followUpInfo of 'factToEdit'
				String followUpInfo = factToEdit.getC_AdvComFact_IDs_FollowUp().replace(Integer.toString(factIdToDelete), "");
				followUpInfo = followUpInfo.replace(",,", ",");
				if (followUpInfo.startsWith(","))
				{
					followUpInfo = followUpInfo.substring(1);
				}
				if (followUpInfo.endsWith(","))
				{
					followUpInfo = followUpInfo.substring(0, followUpInfo.length() - 1);
				}
				factToEdit.setC_AdvComFact_IDs_FollowUp(followUpInfo);
				InterfaceWrapperHelper.save(factToEdit);

			}
		}
		addLog(I_C_AdvCommissionFact.Table_Name + ": deleted " + factIdsToDelete.size() + " records");

		//
		// deleting sales rep facts
		final String deleteSrfSQL =
				"DELETE FROM " + I_C_AdvComSalesRepFact.Table_Name + " srf "
						+ " WHERE NOT EXISTS ("
						+ "   select 1 from " + I_C_AdvComFact_SalesRepFact.Table_Name + " cf_srf "
						+ "   where "
						+ "     cf_srf." + I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + "=srf." + I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID
						+ "     and +cf_srf." + I_C_AdvComFact_SalesRepFact.COLUMNNAME_AD_Client_ID + "=srf." + I_C_AdvComSalesRepFact.COLUMNNAME_AD_Client_ID
						+ " )";
		int delCount = DB.executeUpdateEx(deleteSrfSQL, get_TrxName());
		addLog(I_C_AdvComSalesRepFact.Table_Name + ": deleted " + delCount + " records");

		//
		// deleting commission instances and referring records (i.e. commission payroll lines)
		final String deleteInstSQL = " NOT EXISTS ("
				+ "   select 1 from " + I_C_AdvCommissionFact.Table_Name + " cf "
				+ "   where "
				+ "     cf." + I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID + "="
				+ I_C_AdvCommissionInstance.Table_Name + "." + I_C_AdvCommissionInstance.COLUMNNAME_C_AdvCommissionInstance_ID
				+ "     and +cf." + I_C_AdvCommissionFact.COLUMNNAME_AD_Client_ID + "="
				+ I_C_AdvCommissionInstance.Table_Name + "." + I_C_AdvCommissionInstance.COLUMNNAME_AD_Client_ID
				+ " )";

		final int[] instanceIdsToDelete =
				new Query(getCtx(), I_C_AdvCommissionInstance.Table_Name, deleteInstSQL, get_TrxName())
						.setClient_ID()
						.getIDs();
		sweepTableBL.sweepTable(getCtx(), I_C_AdvCommissionInstance.Table_Name, asList(instanceIdsToDelete), -1, this, get_TrxName());
		addLog(I_C_AdvCommissionInstance.Table_Name + ": deleted " + delCount + " records");

		//
		// deleting commission payroll headers which don't have lines
		final String deleteComPayrollSQL =
				"DELETE FROM " + I_C_AdvCommissionPayroll.Table_Name + " p "
						+ " WHERE NOT EXISTS ("
						+ "   select 1 from " + I_C_AdvCommissionPayrollLine.Table_Name + " pl "
						+ "   where "
						+ "     pl." + I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayroll_ID + "=p." + I_C_AdvCommissionPayroll.COLUMNNAME_C_AdvCommissionPayroll_ID
						+ "     and +pl." + I_C_AdvCommissionPayrollLine.COLUMNNAME_AD_Client_ID + "=p." + I_C_AdvCommissionPayroll.COLUMNNAME_AD_Client_ID
						+ " )";
		delCount = DB.executeUpdateEx(deleteComPayrollSQL, get_TrxName());
		addLog(I_C_AdvCommissionPayroll.Table_Name + ": deleted " + delCount + " records");

		final String delCandSql = "DELETE FROM " + I_C_AdvCommissionFactCand.Table_Name
				+ " WHERE " + I_C_AdvCommissionFactCand.COLUMNNAME_Created + ">=? AND "
				+ I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvComFactCand_Cause_ID + " IS NOT NULL ";
		delCount = DB.executeUpdateEx(delCandSql, new Object[] { period.getStartDate() }, get_TrxName());
		addLog(I_C_AdvCommissionFactCand.Table_Name + ": deleted " + delCount + " records that have been scheduled indirectly");

		//
		// resetting the candidates so that they will be reevaluated
		final String updateCandSql = "UPDATE " + I_C_AdvCommissionFactCand.Table_Name + " SET "
				+ I_C_AdvCommissionFactCand.COLUMNNAME_IsImmediateProcessingDone + "='N',"
				+ I_C_AdvCommissionFactCand.COLUMNNAME_IsSubsequentProcessingDone + "='N',"
				+ I_C_AdvCommissionFactCand.COLUMNNAME_IsError + "='N',"
				+ I_C_AdvCommissionFactCand.COLUMNNAME_AD_Issue_ID + "=null,"
				+ I_C_AdvCommissionFactCand.COLUMNNAME_AlsoHandleTypesWithProcessNow + "='Y'"
				+ " WHERE " + I_C_AdvCommissionFactCand.COLUMNNAME_Created + ">=?";

		final int updateCount = DB.executeUpdateEx(updateCandSql, new Object[] { period.getStartDate() }, get_TrxName());
		addLog(I_C_AdvCommissionFactCand.Table_Name + ": reset " + updateCount + " records to be evaluated another time");

		return "@Success@";
	}

	/**
	 * Thx to http://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java
	 * 
	 * @param is
	 * @return
	 */
	public static List<Integer> asList(final int[] is)
	{
		return new AbstractList<Integer>()
		{
			@Override
			public Integer get(final int i)
			{
				return is[i];
			}

			@Override
			public int size()
			{
				return is.length;
			}
		};
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("C_Period_ID"))
			{
				final int periodId = ((BigDecimal)para[i].getParameter()).intValue();
				period = MPeriod.get(getCtx(), periodId);
			}
		}
	}
}
