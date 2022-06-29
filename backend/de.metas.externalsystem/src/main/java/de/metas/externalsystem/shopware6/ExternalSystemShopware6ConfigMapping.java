/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.shopware6;

import de.metas.document.DocTypeId;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemShopware6ConfigMapping
{
	@NonNull
	Integer seqNo;

	@Nullable
	DocTypeId docTypeOrderId;

	@Nullable
	String paymentRule;

	@NonNull
	String bpartnerIfExists;

	@NonNull
	String bpartnerIfNotExists;

	@NonNull
	String bpartnerLocationIfExists;

	@NonNull
	String bpartnerLocationIfNotExists;

	@Nullable
	Boolean isInvoiceEmailEnabled;

	@Nullable
	PaymentTermId paymentTermId;

	@Nullable
	String sw6CustomerGroup;

	@Nullable
	String sw6PaymentMethod;

	@Nullable
	String description;

	@Builder
	public ExternalSystemShopware6ConfigMapping(
			@NonNull final Integer seqNo,
			final int docTypeOrderId,
			@Nullable final String paymentRule,
			final int paymentTermId,
			@NonNull final String bpartnerIfExists,
			@NonNull final String bpartnerIfNotExists,
			@NonNull final String bpartnerLocationIfExists,
			@NonNull final String bpartnerLocationIfNotExists,
			@Nullable final Boolean isInvoiceEmailEnabled,
			@Nullable final String sw6CustomerGroup,
			@Nullable final String sw6PaymentMethod,
			@Nullable final String description)
	{
		this.seqNo = seqNo;
		this.docTypeOrderId = DocTypeId.ofRepoIdOrNull(docTypeOrderId);
		this.paymentRule = paymentRule;
		this.paymentTermId = PaymentTermId.ofRepoIdOrNull(paymentTermId);
		this.sw6CustomerGroup = sw6CustomerGroup;
		this.sw6PaymentMethod = sw6PaymentMethod;
		this.description = description;
		this.bpartnerIfExists = bpartnerIfExists;
		this.bpartnerIfNotExists = bpartnerIfNotExists;
		this.bpartnerLocationIfExists = bpartnerLocationIfExists;
		this.bpartnerLocationIfNotExists = bpartnerLocationIfNotExists;
		this.isInvoiceEmailEnabled = isInvoiceEmailEnabled;
	}

}
