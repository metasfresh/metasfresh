package de.metas.contracts.subscription.process;

import java.util.Objects;

import org.adempiere.util.StringUtils;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

public abstract class C_SubscriptionProgressBase extends JavaProcess
		implements IProcessPrecondition
{
	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final String tableName = context.getTableName();
		if (!Objects.equals(tableName, I_C_Flatrate_Term.Table_Name) && !Objects.equals(tableName, I_C_SubscriptionProgress.Table_Name))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only for C_Flatrate_Term and C_SubscriptionProgress; not for tableName=" + tableName);
		}

		final I_C_Flatrate_Term term = getTermFromPreconditionsContext(context);
		if (term == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only if a term is actually selected");
		}
		if (!Objects.equals(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, term.getType_Conditions()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only for Type_Conditions=Subscr; not for " + term.getType_Conditions());
		}
		if (!term.isActive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only for active terms");
		}
		if (!Objects.equals(term.getDocStatus(), IDocument.STATUS_Completed))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only for DocStatus='CO' terms; not for " + term.getDocStatus());
		}

		return ProcessPreconditionsResolution.accept();
	}

	private final I_C_Flatrate_Term getTermFromPreconditionsContext(@NonNull final IProcessPreconditionsContext context)
	{
		final String tableName = context.getTableName();
		if (Objects.equals(tableName, I_C_Flatrate_Term.Table_Name))
		{
			return context.getSelectedModel(I_C_Flatrate_Term.class);
		}
		else if (Objects.equals(tableName, I_C_SubscriptionProgress.Table_Name))
		{
			return context.getSelectedModel(I_C_SubscriptionProgress.class).getC_Flatrate_Term();
		}

		final String message = StringUtils.formatMessage("Process is called with TableName={}; preconditionsContext={}; this={}", tableName, context, this);
		throw new IllegalStateException(message);
	}

	protected final I_C_Flatrate_Term getTermFromProcessInfo()
	{
		if (Objects.equals(getTableName(), I_C_Flatrate_Term.Table_Name))
		{
			return getRecord(I_C_Flatrate_Term.class);
		}
		else if (Objects.equals(getTableName(), I_C_SubscriptionProgress.Table_Name))
		{
			return getRecord(I_C_SubscriptionProgress.class).getC_Flatrate_Term();
		}
		final String message = StringUtils.formatMessage("Process is called with TableName={}; processInfo={}; this={}", getTableName(), getProcessInfo(), this);
		throw new IllegalStateException(message);
	}

}
