/*
 * #%L
 * de.metas.swat.base
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

package de.metas.payment.process;

import de.metas.banking.BankAccountId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.compiere.model.I_C_Payment;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;

public class C_Payment_Employee_WriteOff extends JavaProcess
{
	public static final String PARAM_AD_ORG_ID = "Parameter_AD_Org_ID";
	public static final String PARAM_WRITEOFF_DATE = "WriteOffDate";
	public static final String PARAM_C_BP_BankAccount_ID = I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID;
	public static final String PARAM_START_DATE = "StartDate";
	public static final String PARAM_END_DATE = "EndDate";

	@Param(parameterName = PARAM_AD_ORG_ID, mandatory = true)
	private OrgId p_OrgId;

	@Param(parameterName = PARAM_WRITEOFF_DATE, mandatory = true)
	private Instant p_WriteOffDate;

	@Param(parameterName = PARAM_C_BP_BankAccount_ID, mandatory = true)
	private BankAccountId p_bankAccountId;

	@Param(parameterName = PARAM_START_DATE, mandatory = true)
	private Instant p_startDate;

	@Param(parameterName = PARAM_END_DATE, mandatory = true)
	private Instant p_endDate;

	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Payment> paymentsForEmployees = paymentDAO.retrieveEmployeePaymentsForTimeframe
				(p_OrgId,
				 p_bankAccountId,
				 p_startDate,
				 p_endDate);

		paymentBL.fullyWriteOffPayments(paymentsForEmployees, p_WriteOffDate);

		return MSG_OK;
	}
}
