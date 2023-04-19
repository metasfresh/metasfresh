/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.process;

import de.metas.banking.BankStatementLineId;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BankStatementLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

public class C_BankStatement_ChangeCurrencyRate extends BankStatementBasedProcess implements IProcessDefaultParametersProvider
{
	private static final String PARAM_CurrencyRate = "CurrencyRate";
	@Param(parameterName = PARAM_CurrencyRate, mandatory = true)
	private BigDecimal currencyRate;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return checkBankStatementIsDraftOrInProcessOrCompleted(context)
				.and(() -> checkSingleLineSelectedWhichIsForeignCurrency(context));
	}

	private ProcessPreconditionsResolution checkSingleLineSelectedWhichIsForeignCurrency(@NonNull final IProcessPreconditionsContext context)
	{
		final Set<TableRecordReference> bankStatemementLineRefs = context.getSelectedIncludedRecords();
		if (bankStatemementLineRefs.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("a single line shall be selected");
		}

		final TableRecordReference bankStatemementLineRef = bankStatemementLineRefs.iterator().next();
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatemementLineRef.getRecord_ID());
		final I_C_BankStatementLine line = bankStatementBL.getLineById(bankStatementLineId);

		final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final CurrencyId baseCurrencyId = bankStatementBL.getBaseCurrencyId(line);
		if (CurrencyId.equals(currencyId, baseCurrencyId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("line is not in foreign currency");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_CurrencyRate.equals(parameter.getColumnName()))
		{
			return getSingleSelectedBankStatementLine().getCurrencyRate();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		if (currencyRate == null || currencyRate.signum() == 0)
		{
			throw new FillMandatoryException("CurrencyRate");
		}

		bankStatementBL.changeCurrencyRate(getSingleSelectedBankStatementLineId(), currencyRate);

		return MSG_OK;
	}
}
