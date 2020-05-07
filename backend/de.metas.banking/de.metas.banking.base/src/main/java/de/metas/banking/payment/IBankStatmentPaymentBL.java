package de.metas.banking.payment;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.X_I_BankStatement;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.util.ISingletonService;


public interface IBankStatmentPaymentBL extends ISingletonService
{
	void setC_Payment(IBankStatementLineOrRef lineOrRef, I_C_Payment payment);

	String createPayment(I_C_BankStatementLine_Ref ref) throws Exception;

	String createPayment(MBankStatementLine bankStatementLine) throws Exception;

	String createPayment(X_I_BankStatement ibs) throws Exception;

}
