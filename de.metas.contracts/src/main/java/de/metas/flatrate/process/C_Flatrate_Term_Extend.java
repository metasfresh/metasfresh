package de.metas.flatrate.process;

import java.sql.Timestamp;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.process.Param;
import de.metas.process.SvrProcess;

public class C_Flatrate_Term_Extend
		extends SvrProcess
{
	@Param(parameterName = I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm, mandatory = true)
	private boolean p_forceComplete;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate, mandatory = false)
	private Timestamp p_startDate;

	@Override
	protected String doIt() throws Exception
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		if (I_C_Flatrate_Term.Table_Name.equals(getTableName()))
		{
			final I_C_Flatrate_Term termToExtend = getRecord(I_C_Flatrate_Term.class);

			// we are called from a given term => extend the term
			flatrateBL.extendContract(termToExtend,
					true,   // forceExtend
					p_forceComplete,
					p_startDate,
					null); // ol
			termToExtend.setAD_PInstance_EndOfTerm_ID(getAD_PInstance_ID());

			addLog("@Processed@: @C_Flatrate_Term_ID@ " + termToExtend.getC_Flatrate_Term_ID());
			InterfaceWrapperHelper.save(termToExtend);
		}
		else
		{
			// extend all terms that are due for extension
			final String wc = "COALESCE (" + I_C_Flatrate_Term.COLUMNNAME_AD_PInstance_EndOfTerm_ID + ",0)=0 AND "
					+ I_C_Flatrate_Term.COLUMNNAME_DocStatus + "='" + X_C_Flatrate_Term.DOCSTATUS_Completed + "' AND "
					+ I_C_Flatrate_Term.COLUMNNAME_NoticeDate + "<? AND "
					// 04432 don't extend canceled contracts
					+ "COALESCE (" + I_C_Flatrate_Term.COLUMNNAME_ContractStatus + ",'') != " + DB.TO_STRING(X_C_Flatrate_Term.CONTRACTSTATUS_Gekuendigt);

			final Iterator<I_C_Flatrate_Term> termsToExtend = new Query(getCtx(), I_C_Flatrate_Term.Table_Name, wc, get_TrxName())
					.setParameters(SystemTime.asTimestamp())
					.setClient_ID() // .setApplyAccessRules requires a more sophisticated role access setup which we urrently don't have to the user "system" () and this is not a "user"
					// process
					.setOnlyActiveRecords(true)
					.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
					.iterate(
							I_C_Flatrate_Term.class,
							true); // guaranteed = true, because the term extension changes AD_PInstance_EndOfTerm_ID
			int counter = 0;
			while (termsToExtend.hasNext())
			{
				final I_C_Flatrate_Term termToExtend = termsToExtend.next();
				flatrateBL.extendContract(termToExtend,
						false,   // forceExtend
						false,   // forceComplete
						p_startDate,
						null); // ol
				termToExtend.setAD_PInstance_EndOfTerm_ID(getAD_PInstance_ID());
				if (termToExtend.getC_FlatrateTerm_Next_ID() > 0)
				{
					counter++;
				}
				InterfaceWrapperHelper.save(termToExtend);
			}
			addLog("Extended " + counter + " terms");
		}
		return "@Success@";
	}
}
