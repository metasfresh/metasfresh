package de.metas.payment.sepa.api;

import org.compiere.model.I_C_BP_BankAccount;

import de.metas.util.ISingletonService;

public interface ISEPABankAccountBL extends ISingletonService
{

	String getSwiftCode(I_C_BP_BankAccount bankAccount);

	String getSwiftCodeOrNull(I_C_BP_BankAccount bankAccount);

	void setSwiftCode(I_C_BP_BankAccount bankAccount, String swiftCode);

}
