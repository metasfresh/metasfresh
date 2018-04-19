package de.metas.contracts.flatrate.process;

import java.sql.Timestamp;
import java.util.Arrays;

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

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

public class C_Flatrate_Term_Extend
		extends JavaProcess
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);
	final private IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Param(parameterName = I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm, mandatory = false)
	private String p_forceComplete;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate, mandatory = false)
	private Timestamp p_startDate;

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Boolean forceComplete = StringUtils.toBooleanOrNull(p_forceComplete);

		if (I_C_Flatrate_Term.Table_Name.equals(getTableName()))
		{
			extendSingleTerm(forceComplete);
		}
		else
		{
			extendAllEligibleTerms(forceComplete);
		}
		return "@Success@";
	}

	private void extendSingleTerm(@Nullable final Boolean forceComplete)
	{
		final I_C_Flatrate_Term contractToExtend = getRecord(I_C_Flatrate_Term.class);

		// we are called from a given term => extend the term
		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(getAD_PInstance_ID())
				.contract(contractToExtend)
				.forceExtend(true)
				.forceComplete(forceComplete)
				.nextTermStartDate(p_startDate)
				.build();

		flatrateBL.extendContract(context);

		addLog("@Processed@: @C_Flatrate_Term_ID@ " + contractToExtend.getC_Flatrate_Term_ID());

		getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(contractToExtend));
	}

	private void extendAllEligibleTerms(@Nullable final Boolean forceComplete)
	{
		final ICompositeQueryFilter<I_C_Flatrate_Term> notQuitOrVoidedFilter = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addNotInArrayFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, Arrays.asList(X_C_Flatrate_Term.CONTRACTSTATUS_Quit, X_C_Flatrate_Term.CONTRACTSTATUS_Voided))
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, null);

		final Iterator<I_C_Flatrate_Term> termsToExtend = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_AD_PInstance_EndOfTerm_ID, 0, null)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_NoticeDate, Operator.LESS, SystemTime.asTimestamp())
				.filter(notQuitOrVoidedFilter)
				.orderBy().addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.setClient_ID()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // guaranteed = true, because the term extension changes AD_PInstance_EndOfTerm_ID
				.iterate(I_C_Flatrate_Term.class);

		int counter = 0;
		while (termsToExtend.hasNext())
		{
			final I_C_Flatrate_Term contractToExtend = termsToExtend.next();
			final ContractExtendingRequest context = ContractExtendingRequest.builder()
					.AD_PInstance_ID(getAD_PInstance_ID())
					.contract(contractToExtend)
					.forceExtend(false)
					.forceComplete(forceComplete)
					.nextTermStartDate(p_startDate)
					.build();

			flatrateBL.extendContract(context);

			if (contractToExtend.getC_FlatrateTerm_Next_ID() > 0)
			{
				counter++;
			}
		}
		addLog("Extended " + counter + " terms");
	}

}
