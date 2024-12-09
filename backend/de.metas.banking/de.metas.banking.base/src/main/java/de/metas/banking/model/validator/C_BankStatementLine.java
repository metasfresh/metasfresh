package de.metas.banking.model.validator;

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

<<<<<<< HEAD
=======
import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.document.engine.DocStatus;
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

<<<<<<< HEAD
import com.google.common.annotations.VisibleForTesting;

import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.document.engine.DocStatus;
import lombok.NonNull;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
@Interceptor(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	private final IBankStatementBL bankStatementBL;

	public C_BankStatementLine(@NonNull final IBankStatementBL bankStatementBL)
	{
		this.bankStatementBL = bankStatementBL;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void onBeforeNewOrChange(final I_C_BankStatementLine bankStatementLine, final ModelChangeType changeType)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());

		if (changeType.isNew())
		{
			final I_C_BankStatement bankStatement = bankStatementBL.getById(bankStatementId);
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
		if (bankStatementLine.getLine() <= 0)
		{
			final int nextLineNo = bankStatementBL.computeNextLineNo(bankStatementId);
			bankStatementLine.setLine(nextLineNo);
		}

		BankStatementLineAmounts.of(bankStatementLine).assertNoDifferences();
<<<<<<< HEAD
=======
		assertReconcilationConsistent(bankStatementLine);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void onAfterNewOrChange(final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		updateBankStatementHeader(bankStatementId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());
		bankStatementBL.deleteReferences(bankStatementLineId);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onAfterDelete(final I_C_BankStatementLine bankStatementLine)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		updateBankStatementHeader(bankStatementId);
	}

	private void updateBankStatementHeader(final BankStatementId bankStatementId)
	{
		updateStatementDifferenceAndEndingBalance(bankStatementId);
		updateBankStatementIsReconciledFlag(bankStatementId);

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(
				ITrx.TRXNAME_ThreadInherited,
				CacheInvalidateMultiRequest.rootRecord(I_C_BankStatement.Table_Name, bankStatementId));
	}

	@VisibleForTesting
	protected void updateStatementDifferenceAndEndingBalance(final BankStatementId bankStatementId)
	{
		// StatementDifference
		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET StatementDifference=(SELECT COALESCE(SUM(StmtAmt),0) FROM C_BankStatementLine bsl "
					+ "WHERE bsl.C_BankStatement_ID=bs.C_BankStatement_ID AND bsl.IsActive='Y') "
					+ "WHERE C_BankStatement_ID=?";
<<<<<<< HEAD
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
=======
			DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		// EndingBalance
		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET EndingBalance=BeginningBalance+StatementDifference "
					+ "WHERE C_BankStatement_ID=?";
<<<<<<< HEAD
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
=======
			DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	@VisibleForTesting
	protected void updateBankStatementIsReconciledFlag(final BankStatementId bankStatementId)
	{
		final String sql = "UPDATE C_BankStatement bs"
				+ " SET IsReconciled=(CASE WHEN (SELECT COUNT(1) FROM C_BankStatementLine bsl WHERE bsl.C_BankStatement_ID = bs.C_BankStatement_ID AND bsl.IsReconciled = 'N') = 0 THEN 'Y' ELSE 'N' END)"
				+ " WHERE C_BankStatement_ID=?";
<<<<<<< HEAD
		DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
=======
		DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
	}

	private void assertReconcilationConsistent(final I_C_BankStatementLine line)
	{
		if (line.getLink_BankStatementLine_ID() > 0 && line.getC_Payment_ID() > 0)
		{
			throw new AdempiereException("Invalid reconcilation: cannot have bank transfer and payment at the same time");
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
