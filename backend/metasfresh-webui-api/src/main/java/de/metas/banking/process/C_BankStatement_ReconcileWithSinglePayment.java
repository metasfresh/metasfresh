/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

import java.util.Set;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;

public class C_BankStatement_ReconcileWithSinglePayment extends BankStatementBasedProcess implements IProcessDefaultParametersProvider
{
	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	@Param(parameterName = PARAM_C_BPartner_ID, mandatory = true)
	private BPartnerId bpartnerId;

	private static final String PARAM_C_Payment_ID = "C_Payment_ID";
	@Param(parameterName = PARAM_C_Payment_ID)
	private PaymentId paymentId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return checkBankStatementIsDraftOrInProcessOrCompleted(context)
				.and(() -> checkSingleLineSelectedWhichIsNotReconciled(context));
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_C_BPartner_ID.contentEquals(parameter.getColumnName()))
		{
			final I_C_BankStatementLine bankStatementLine = getSingleSelectedBankStatementLine();
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
			if (bpartnerId != null)
			{
				return bpartnerId;
			}
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_C_Payment_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup, lookupTableName = I_C_Payment.Table_Name)
	private LookupValuesList paymentLookupProvider()
	{
		if (bpartnerId == null)
		{
			return LookupValuesList.EMPTY;
		}

		final I_C_BankStatementLine bankStatementLine = getSingleSelectedBankStatementLine();
		final int limit = 20;
		final Set<PaymentId> paymentIds = bankStatementPaymentBL.findEligiblePaymentIds(
				bankStatementLine,
				bpartnerId,
				ImmutableSet.of(), // excludePaymentIds
				limit);
		return LookupDataSourceFactory.instance.searchInTableLookup(I_C_Payment.Table_Name).findByIdsOrdered(paymentIds);
	}

	@Override
	protected String doIt()
	{
		final I_C_BankStatement bankStatement = getSelectedBankStatement();
		final I_C_BankStatementLine bankStatementLine = getSingleSelectedBankStatementLine();

		bankStatementLine.setC_BPartner_ID(bpartnerId.getRepoId());

		if (paymentId != null)
		{
			bankStatementPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, paymentId);
		}
		else
		{
			final Set<PaymentId> eligiblePaymentIds = bankStatementPaymentBL.findEligiblePaymentIds(
					bankStatementLine,
					bpartnerId,
					ImmutableSet.of(), // excludePaymentIds
					2 // limit
			);

			if (eligiblePaymentIds.isEmpty())
			{
				bankStatementPaymentBL.createSinglePaymentAndLink(bankStatement, bankStatementLine);
			}
			else if (eligiblePaymentIds.size() == 1)
			{
				PaymentId eligiblePaymentId = eligiblePaymentIds.iterator().next();
				bankStatementPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, eligiblePaymentId);
			}
			else
			{
				throw new FillMandatoryException(PARAM_C_Payment_ID);
			}
		}

		return MSG_OK;
	}
}
