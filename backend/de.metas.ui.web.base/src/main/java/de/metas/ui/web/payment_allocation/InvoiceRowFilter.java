/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.payment_allocation;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Predicate;

@Value
class InvoiceRowFilter implements Predicate<InvoiceRow>
{
	public static InvoiceRowFilter ANY = builder().build();

	@Nullable
	String documentNo;
	@Nullable
	String poReference;

	@Builder
	private InvoiceRowFilter(
			@Nullable final String documentNo,
			@Nullable final String poReference)
	{
		this.documentNo = StringUtils.trimBlankToNull(documentNo);
		this.poReference = StringUtils.trimBlankToNull(poReference);
	}

	@Override
	public boolean test(final InvoiceRow invoiceRow)
	{
		return (documentNo == null || Objects.equals(documentNo, invoiceRow.getDocumentNo()))
				&& (poReference == null || Objects.equals(poReference, invoiceRow.getPoReference()));
	}
}
