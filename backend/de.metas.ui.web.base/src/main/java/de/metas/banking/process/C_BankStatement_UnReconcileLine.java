package de.metas.banking.process;

<<<<<<< HEAD
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
=======
import com.google.common.collect.ImmutableList;
import de.metas.banking.BankStatementLineId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

<<<<<<< HEAD
import com.google.common.collect.ImmutableList;

import de.metas.banking.BankStatementLineId;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
=======
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class C_BankStatement_UnReconcileLine extends BankStatementBasedProcess
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		return checkBankStatementIsDraftOrInProcessOrCompleted(context)
				.and(() -> checkSingleLineSelectedWhichIsReconciled(context));
	}

<<<<<<< HEAD
	private final ProcessPreconditionsResolution checkSingleLineSelectedWhichIsReconciled(@NonNull final IProcessPreconditionsContext context)
=======
	private ProcessPreconditionsResolution checkSingleLineSelectedWhichIsReconciled(@NonNull final IProcessPreconditionsContext context)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// there should be a single line selected
		final Set<TableRecordReference> bankStatemementLineRefs = context.getSelectedIncludedRecords();
		if (bankStatemementLineRefs.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("a single line shall be selected");
		}

		final TableRecordReference bankStatemementLineRef = bankStatemementLineRefs.iterator().next();
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatemementLineRef.getRecord_ID());
		final I_C_BankStatementLine line = bankStatementBL.getLineById(bankStatementLineId);
		if (!line.isReconciled())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("line shall be reconciled");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_C_BankStatement bankStatement = getSelectedBankStatement();
<<<<<<< HEAD
		final DocStatus docStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (!docStatus.isDraftedInProgressOrCompleted())
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_BankStatement_MustBe_Draft_InProgress_Or_Completed));
		}

		final I_C_BankStatementLine bankStatementLine = getSingleSelectedBankStatementLine();
		bankStatementBL.unlinkPaymentsAndDeleteReferences(ImmutableList.of(bankStatementLine));
=======
		bankStatementBL.assertBankStatementIsDraftOrInProcessOrCompleted(bankStatement);

		final I_C_BankStatementLine bankStatementLine = getSingleSelectedBankStatementLine();
		bankStatementBL.unreconcile(ImmutableList.of(bankStatementLine));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		bankStatementBL.unpost(bankStatement);

		return MSG_OK;
	}

}
