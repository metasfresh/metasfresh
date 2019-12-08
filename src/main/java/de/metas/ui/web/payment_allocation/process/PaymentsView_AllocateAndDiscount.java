package de.metas.ui.web.payment_allocation.process;

import org.compiere.util.DisplayType;

import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;

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

public class PaymentsView_AllocateAndDiscount extends PaymentsView_Allocate_Template implements IProcessPrecondition
{
	@Override
	protected PaymentAllocationBuilder preparePaymentAllocationBuilder()
	{
		final PaymentAllocationBuilder builder = super.preparePaymentAllocationBuilder();
		if (builder == null)
		{
			return null;
		}

		return builder.payableRemainingOpenAmtPolicy(PayableRemainingOpenAmtPolicy.DISCOUNT);
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final PaymentAllocationBuilder builder = preparePaymentAllocationBuilder();
		if (builder == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("invalid");
		}

		final PaymentAllocationResult result = builder.dryRun().build();
		if (result.getCandidates().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to allocate");
		}
		if (!result.isOK())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a valid selection");
		}
		if (result.getTotalDiscountAmt().signum() == 0)
		{
			// NOTE: there is other process is would allocate without writing off
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to discount");
		}

		return ProcessPreconditionsResolution.accept()
				.deriveWithCaptionOverride(computeCaption(result));
	}

	private static ITranslatableString computeCaption(final PaymentAllocationResult result)
	{
		return TranslatableStrings.builder()
				.appendADElement("DiscountAmt").append(": ")
				.append(result.getTotalDiscountAmt(), DisplayType.Amount)
				.build();
	}
}
