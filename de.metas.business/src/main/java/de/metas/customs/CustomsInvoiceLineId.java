package de.metas.customs;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class CustomsInvoiceLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	CustomsInvoiceId customsInvoiceId;

	public static CustomsInvoiceLineId ofRepoId(@NonNull final CustomsInvoiceId customsInvoiceId, final int customsInvoiceLineid)
	{
		return new CustomsInvoiceLineId(customsInvoiceId, customsInvoiceLineid);
	}

	public static CustomsInvoiceLineId ofRepoId(final int customsInvoiceId, final int customsInvoiceLineId)
	{
		return new CustomsInvoiceLineId(CustomsInvoiceId.ofRepoId(customsInvoiceId), customsInvoiceLineId);
	}

	public static CustomsInvoiceLineId ofRepoIdOrNull(
			@Nullable final CustomsInvoiceId customsInvoiceId,
			final int customsInvoiceLineId)
	{
		return customsInvoiceId != null && customsInvoiceLineId > 0 ? ofRepoId(customsInvoiceId, customsInvoiceLineId) : null;
	}

	private CustomsInvoiceLineId(@NonNull final CustomsInvoiceId customsInvoiceId, final int customsInvoiceLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(customsInvoiceLineId, "shipmentDeclarationLineId");
		this.customsInvoiceId = customsInvoiceId;
	}
}
