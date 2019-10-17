package de.metas.payment.esr.api.impl;

import de.metas.payment.esr.api.IBPBankAccountBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_C_Bank;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class BPBankAccountBL implements IBPBankAccountBL
{
	@Override
	public String retrieveBankAccountNo(@NonNull final I_C_BP_BankAccount bankAccount)
	{
		final I_C_Bank bank = bankAccount.getC_Bank();
		if (bank != null && bank.isESR_PostBank())
		{
			return "000000";
		}
		else
		{
			return bankAccount.getAccountNo();
		}
	}

	@Override
	public String retrieveESRAccountNo(final I_C_BP_BankAccount bankAccount)
	{
		Check.assume(bankAccount.isEsrAccount(), bankAccount + " has IsEsrAccount=Y");

		final String renderedNo = bankAccount.getESR_RenderedAccountNo();
		Check.assume(!Check.isEmpty(renderedNo, true), bankAccount + " has a ESR_RenderedAccountNo");

		if (!renderedNo.contains("-"))
		{
			// task 07789: the rendered number is not "rendered" to start with. This happens e.g. if the number was parsed from an ESR payment string.
			return renderedNo;
		}

		final String[] renderenNoComponents = renderedNo.split("-");
		Check.assume(renderenNoComponents.length == 3, renderedNo + " contains three '-' separated parts");

		final StringBuilder sb = new StringBuilder();
		sb.append(renderenNoComponents[0]);
		sb.append(StringUtils.lpadZero(renderenNoComponents[1], 6, "middle section of " + renderedNo));
		sb.append(renderenNoComponents[2]);

		return sb.toString();

	}
}
