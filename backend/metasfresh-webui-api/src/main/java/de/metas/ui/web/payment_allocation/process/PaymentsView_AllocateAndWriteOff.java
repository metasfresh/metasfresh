package de.metas.ui.web.payment_allocation.process;

import org.compiere.util.DisplayType;

import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.process.PaymentsViewAllocateCommand.PaymentsViewAllocateCommandBuilder;
import lombok.NonNull;

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

public class PaymentsView_AllocateAndWriteOff extends PaymentsView_Allocate_Template implements IProcessPrecondition
{
	@Override
	protected void customizePaymentsViewAllocateCommandBuilder(@NonNull final PaymentsViewAllocateCommandBuilder builder)
	{
		builder.payableRemainingOpenAmtPolicy(PayableRemainingOpenAmtPolicy.WRITE_OFF);
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final PaymentAllocationResult result = newPaymentsViewAllocateCommand().dryRun().orElse(null);
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
		if (result.getTotalWriteOffAmtAsBigDecimal().signum() == 0)
		{
			// NOTE: there is other process is would allocate without writing off
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to write-off");
		}

		return ProcessPreconditionsResolution.accept()
				.deriveWithCaptionOverride(computeCaption(result));
	}

	private static ITranslatableString computeCaption(final PaymentAllocationResult result)
	{
		return TranslatableStrings.builder()
				.appendADElement("WriteOffAmt").append(": ")
				.append(result.getTotalWriteOffAmtAsBigDecimal(), DisplayType.Amount)
				.build();
	}
}
