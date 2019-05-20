package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.validation.invoice_440;

import lombok.NonNull;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.util.Check;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class Invoice440Validator
{
	public void validateInvoice(@NonNull final InvoiceToExport invoice)
	{
		final Money amount = invoice.getAmount();
		Check.assume(InvoiceToExport.CURRENCY_CHF.equals(amount.getCurrency()), "The given invoice.amount's currency needs to be CHF; invoice={}", invoice);

		final Money alreadyPaidAmount = invoice.getAlreadyPaidAmount();
		Check.assume(InvoiceToExport.CURRENCY_CHF.equals(alreadyPaidAmount.getCurrency()), "The given invoice.alreadyPaidAmount's currency needs to be CHF; invoice={}", invoice);
	}
}
