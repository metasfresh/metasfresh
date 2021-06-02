/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.banking.invoice_auto_allocation;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocTypeId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode
@ToString
public class BankAccountInvoiceAutoAllocRules
{
	public static final BankAccountInvoiceAutoAllocRules MATCH_ALL = builder().matchByDefault(true).build();

	private final ImmutableList<BankAccountInvoiceAutoAllocRule> includeRules;
	private final boolean matchByDefault;

	@Builder
	private BankAccountInvoiceAutoAllocRules(
			@Nullable final List<BankAccountInvoiceAutoAllocRule> includeRules,
			@NonNull final Boolean matchByDefault)
	{
		this.includeRules = includeRules != null
				? ImmutableList.copyOf(includeRules)
				: ImmutableList.of();

		this.matchByDefault = matchByDefault;
	}

	public boolean isMatching(@NonNull final DocTypeId invoiceDocTypeId)
	{
		if (includeRules.stream().anyMatch(rule -> rule.isMatching(invoiceDocTypeId)))
		{
			return true;
		}

		return matchByDefault;
	}
}
