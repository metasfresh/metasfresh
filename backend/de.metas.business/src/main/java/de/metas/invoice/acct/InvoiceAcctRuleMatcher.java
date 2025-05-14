/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.acct;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;

@Value
@Builder
public class InvoiceAcctRuleMatcher
{
	public static final Comparator<InvoiceAcctRuleMatcher> ORDER_FROM_SPECIFIC_TO_GENERIC = Comparator.comparing(InvoiceAcctRuleMatcher::getAcctSchemaId)
			.thenComparing(InvoiceAcctRuleMatcher::getAccountConceptualName, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparing(InvoiceAcctRuleMatcher::getInvoiceAndLineId, Comparator.nullsLast(Comparator.naturalOrder()));

	@NonNull AcctSchemaId acctSchemaId;
	@Nullable InvoiceAndLineId invoiceAndLineId;
	@Nullable AccountConceptualName accountConceptualName;

	void assertInvoiceId(@NonNull InvoiceId expectedInvoiceId)
	{
		if (invoiceAndLineId != null)
		{
			invoiceAndLineId.assertInvoiceId(expectedInvoiceId);
		}
	}

	boolean matches(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final AccountConceptualName accountConceptualName,
			@Nullable final InvoiceAndLineId invoiceAndLineId)
	{
		return AcctSchemaId.equals(this.acctSchemaId, acctSchemaId)
				&& (this.accountConceptualName == null || AccountConceptualName.equals(this.accountConceptualName, accountConceptualName))
				&& (this.invoiceAndLineId == null || InvoiceAndLineId.equals(this.invoiceAndLineId, invoiceAndLineId));
	}
}
