/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.invoicingpool;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_DocType_Invoicing_Pool;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class DocTypeInvoicingPoolId implements RepoIdAware
{
	@JsonCreator
	public static DocTypeInvoicingPoolId ofRepoId(final int repoId)
	{
		return new DocTypeInvoicingPoolId(repoId);
	}

	@Nullable
	public static DocTypeInvoicingPoolId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@NonNull
	public static Optional<DocTypeInvoicingPoolId> optionalOfRepoId(@Nullable final Integer repoId)
	{
		return repoId != null
				? Optional.ofNullable(ofRepoIdOrNull(repoId))
				: Optional.empty();
	}

	int repoId;

	private DocTypeInvoicingPoolId(final int docTypeInvoicingPoolRepoId)
	{
		this.repoId = Check.assumeGreaterThanZero(docTypeInvoicingPoolRepoId, I_C_DocType_Invoicing_Pool.COLUMNNAME_C_DocType_Invoicing_Pool_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return docTypeInvoicingPoolId != null ? docTypeInvoicingPoolId.getRepoId() : -1;
	}
}
