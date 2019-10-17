package de.metas.banking.exception;

/*
 * #%L
 * de.metas.banking.base
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


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BP_BankAccount;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;

public class BankingException extends AdempiereException
{
	private final String errorMsg;

	private I_C_BP_BankAccount executiveAcct;
	private I_C_BP_BankAccount targetAcct;

	/**
	 *
	 */
	private static final long serialVersionUID = -7998877126463669563L;

	public BankingException(final String message)
	{
		super(message);
		errorMsg = message;
	}

	public BankingException(final String message, final Throwable cause)
	{
		super(message, cause);
		errorMsg = message;
	}

	public void setExecutiveAcct(final I_C_BP_BankAccount senderAccount)
	{
		executiveAcct = senderAccount;
		resetMessageBuilt();
	}

	public void setTargetAcct(final I_C_BP_BankAccount receiverAccount)
	{
		targetAcct = receiverAccount;
		resetMessageBuilt();
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder sb = TranslatableStrings.builder();
		
		sb.appendADMessage("Error");
		sb.append(" ");
		sb.append(errorMsg);
		sb.append("\n");

		if (executiveAcct != null)
		{
			sb.append("Quellkonto:\n");
			sb.append("\tKonto-Nr: ").append(executiveAcct.getAccountNo());
			sb.append("\n");
			sb.append("\tBLZ: ").append(executiveAcct.getC_Bank().getRoutingNo());
			sb.append("\n");
		}
		if (targetAcct != null)
		{
			sb.append("Zielkonto:\n");
			sb.append("\tEmpfaenger: ")
					.append(targetAcct.getC_BPartner().getName());
			sb.append("\n");
			sb.append("\tKonto-Nr: ").append(targetAcct.getAccountNo());
			sb.append("\n");
			sb.append("\tBLZ: ").append(targetAcct.getRoutingNo());
			sb.append("\n");
		}
		return sb.build();
	}
}
