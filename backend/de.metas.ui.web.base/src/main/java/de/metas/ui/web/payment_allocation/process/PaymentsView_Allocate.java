package de.metas.ui.web.payment_allocation.process;

import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PaymentsView_Allocate extends PaymentsView_Allocate_Template implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkPreconditions(newPaymentsViewAllocateCommand());
	}

	@VisibleForTesting
	static ProcessPreconditionsResolution checkPreconditions(@NonNull final PaymentsViewAllocateCommand command)
	{
		final PaymentAllocationResult result;
		try
		{
			result = command.dryRun().orElse(null);
		}
		catch (final AdempiereException ex)
		{
			if (!ex.isUserValidationError())
			{
				throw ex;
			}
			// e.g. a payment bonus above what the customer pays: the user sees why the action is not offered
			return ProcessPreconditionsResolution.reject(AdempiereException.extractMessageTrl(ex));
		}
		if (result == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("invalid");
		}

		if (result.getCandidates().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to allocate");
		}
		if (!result.isOK())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a valid selection");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
