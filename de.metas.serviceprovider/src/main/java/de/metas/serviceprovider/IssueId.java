package de.metas.serviceprovider;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.adempiere.model.I_M_Product;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class IssueId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static IssueId ofRepoId(final int repoId)
	{
		return new IssueId(repoId);
	}

	public static IssueId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new IssueId(repoId) : null;
	}

	public static IssueId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new IssueId(repoId) : null;
	}

	public static int toRepoId(final IssueId productId)
	{
		return productId != null ? productId.getRepoId() : -1;
	}

	private IssueId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "issueId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Product.Table_Name, getRepoId());
	}
}
