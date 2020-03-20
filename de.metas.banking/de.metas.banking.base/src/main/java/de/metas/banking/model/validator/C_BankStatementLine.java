package de.metas.banking.model.validator;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

import com.google.common.annotations.VisibleForTesting;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;

@Interceptor(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void onBeforeNewOrChange(final I_C_BankStatementLine bankStatementLine, final ModelChangeType changeType)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());

		if (changeType.isNew())
		{
			final I_C_BankStatement bankStatement = Services.get(IBankStatementDAO.class).getById(bankStatementId);
			final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(bankStatement.getDocStatus());
			if (docStatus.isCompletedOrClosedOrReversed())
			{
				throw new AdempiereException("@ParentComplete@ @C_BankStatementLine_ID@");
			}
		}

		if (bankStatementLine.getChargeAmt().signum() != 0 && bankStatementLine.getC_Charge_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_BankStatementLine.COLUMNNAME_C_Charge_ID);
		}

		// Set Line No
		if (bankStatementLine.getLine() == 0)
		{
			final String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_BankStatementLine WHERE C_BankStatement_ID=?";
			final int lineNo = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, bankStatementId);
			bankStatementLine.setLine(lineNo);
		}

		final BigDecimal chargeAmt = bankStatementLine.getStmtAmt()
				.subtract(bankStatementBL.computeStmtAmtExcludingChargeAmt(bankStatementLine));
		bankStatementLine.setChargeAmt(chargeAmt);

		//
		if (changeType.isNew() || InterfaceWrapperHelper.isValueChanged(bankStatementLine, I_C_BankStatementLine.COLUMNNAME_C_Payment_ID))
		{
			updatePaymentDependentFields(bankStatementLine, changeType);
		}
	}

	private void updatePaymentDependentFields(final I_C_BankStatementLine bankStatementLine, final ModelChangeType changeType)
	{
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

		//
		// Do nothing if we are dealing with a new line which does not have an C_Payment_ID
		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(bankStatementLine.getC_Payment_ID());
		if (changeType.isNew() && paymentId == null)
		{
			return;
		}

		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);
		if (paymentId != null)
		{
			final I_C_Payment payment = paymentBL.getById(paymentId);
			bankStatmentPaymentBL.setC_Payment(bankStatementLine, payment);

			paymentBL.markReconciled(payment);
		}
		else
		{
			bankStatmentPaymentBL.setC_Payment(bankStatementLine, null);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void onAfterNewOrChange(final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		updateStatementDifferenceAndEndingBalance(bankStatementId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_BankStatementLine bankStatementLine)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
		bankStatementBL.deleteReferences(bankStatementLineId);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onAfterDelete(final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		updateStatementDifferenceAndEndingBalance(bankStatementId);
	}

	@VisibleForTesting
	protected void updateStatementDifferenceAndEndingBalance(final BankStatementId bankStatementId)
	{
		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET StatementDifference=(SELECT COALESCE(SUM(StmtAmt),0) FROM C_BankStatementLine bsl "
					+ "WHERE bsl.C_BankStatement_ID=bs.C_BankStatement_ID AND bsl.IsActive='Y') "
					+ "WHERE C_BankStatement_ID=?";
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
		}

		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET EndingBalance=BeginningBalance+StatementDifference "
					+ "WHERE C_BankStatement_ID=?";
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
		}
	}
}
