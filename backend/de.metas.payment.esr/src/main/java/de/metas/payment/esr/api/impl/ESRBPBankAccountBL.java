package de.metas.payment.esr.api.impl;

import org.springframework.stereotype.Service;

import de.metas.banking.Bank;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.payment.esr.api.IESRBPBankAccountBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;

@Service
public class ESRBPBankAccountBL implements IESRBPBankAccountBL
{
	private final BankRepository bankRepo;

	public ESRBPBankAccountBL(
			@NonNull final BankRepository bankRepo)
	{
		this.bankRepo = bankRepo;
	}

	@Override
	public String retrieveBankAccountNo(@NonNull final I_C_BP_BankAccount bankAccount)
	{
		if (isPostBank(bankAccount))
		{
			return "000000";
		}
		else
		{
			return bankAccount.getAccountNo();
		}
	}

	private boolean isPostBank(@NonNull final I_C_BP_BankAccount bankAccount)
	{
		final BankId bankId = BankId.ofRepoIdOrNull(bankAccount.getC_Bank_ID());
		if (bankId == null)
		{
			return false;
		}

		final Bank bank = bankRepo.getById(bankId);
		return bank.isEsrPostBank();
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
