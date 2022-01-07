package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.util.Check.assume;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
public class BankAccountQuery
{
	BPBankAcctUse bpBankAcctUse;
	@Nullable
	BPartnerId bPartnerId;
	@Nullable
	InvoiceId invoiceId;
	boolean containsQRIBAN;

	@Builder(toBuilder = true)
	private BankAccountQuery(
			final BPBankAcctUse bpBankAcctUse,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final InvoiceId invoiceId,
			final Boolean containsQRIBAN)
	{
		this.bpBankAcctUse = bpBankAcctUse;
		this.bPartnerId = bPartnerId;
		this.invoiceId = invoiceId;
		this.containsQRIBAN = CoalesceUtil.coalesce(containsQRIBAN, false);

		assume(bPartnerId != null || invoiceId != null,
				"At least one of the parameters 'bPartnerId, invoiceId and value needs to be non-null/non-empty");
	}

}
