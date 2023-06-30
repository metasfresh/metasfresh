/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.flatrate.process;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;

import java.sql.Timestamp;

public abstract class C_Flatrate_Term_Change_Base extends JavaProcess
{
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

	public static final String PARAM_CHANGE_DATE = "EventDate";

	public static final String PARAM_ACTION = I_C_Contract_Change.COLUMNNAME_Action;

	public static final String PARAM_TERMINATION_MEMO = I_C_Flatrate_Term.COLUMNNAME_TerminationMemo;
	public static final String PARAM_TERMINATION_REASON = I_C_Flatrate_Term.COLUMNNAME_TerminationReason;

	public static final String PARAM_IsCreditOpenInvoices = "IsCreditOpenInvoices";

	public static final String PARAM_IsCloseInvoiceCandidate = "IsCloseInvoiceCandidate";

	@Param(parameterName = PARAM_ACTION, mandatory = true)
	private String action;

	@Param(parameterName = PARAM_CHANGE_DATE, mandatory = true)
	private Timestamp changeDate;

	@Param(parameterName = PARAM_TERMINATION_MEMO)
	private String terminationMemo;

	@Param(parameterName = PARAM_TERMINATION_REASON)
	private String terminationReason;

	@Param(parameterName = PARAM_IsCreditOpenInvoices)
	private boolean isCreditOpenInvoices;

	@Param(parameterName = PARAM_IsCloseInvoiceCandidate)
	private boolean isCloseInvoiceCandidate;

	@Override
	protected String doIt()
	{
		if (IContractChangeBL.ChangeTerm_ACTION_SwitchContract.equals(action))
		{
			throw new AdempiereException("Not implemented");
		}

		final IContractChangeBL.ContractChangeParameters contractChangeParameters = IContractChangeBL.ContractChangeParameters.builder()
				.changeDate(changeDate)
				.isCloseInvoiceCandidate(isCloseInvoiceCandidate)
				.terminationMemo(terminationMemo)
				.terminationReason(terminationReason)
				.isCreditOpenInvoices(isCreditOpenInvoices)
				.action(action)
				.build();

		final Iterable<I_C_Flatrate_Term> flatrateTerms = getFlatrateTermsToChange();
		flatrateTerms.forEach(currentTerm -> contractChangeBL.cancelContract(currentTerm, contractChangeParameters));

		return MSG_OK;
	}

	protected abstract Iterable<I_C_Flatrate_Term> getFlatrateTermsToChange();

}