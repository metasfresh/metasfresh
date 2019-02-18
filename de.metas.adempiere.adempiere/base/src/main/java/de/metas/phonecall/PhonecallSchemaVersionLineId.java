package de.metas.phonecall;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

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
public class PhonecallSchemaVersionLineId implements RepoIdAware
{

	int repoId;

	@NonNull
	PhonecallSchemaId phonecallSchemaId;

	@NonNull
	PhonecallSchemaVersionId phonecallSchemaVersionId;

	public static PhonecallSchemaVersionLineId ofRepoId(
			@NonNull final PhonecallSchemaId phonecallSchemaId,
			@NonNull PhonecallSchemaVersionId phonecallSchemaVersionId,
			final int phonecallSchemaVersionLineId)
	{
		return new PhonecallSchemaVersionLineId(phonecallSchemaId, phonecallSchemaVersionId, phonecallSchemaVersionLineId);
	}

	public static PhonecallSchemaVersionLineId ofRepoId(
			final int phonecallSchemaId,
			final int phonecallSchemaVersionId,
			final int phonecallSchemaVersionLineId)
	{
		return new PhonecallSchemaVersionLineId(
				PhonecallSchemaId.ofRepoId(phonecallSchemaId),
				PhonecallSchemaVersionId.ofRepoId(phonecallSchemaId, phonecallSchemaVersionId),
				phonecallSchemaVersionLineId);
	}

	public static PhonecallSchemaVersionLineId ofRepoIdOrNull(
			@Nullable final Integer phonecallSchemaId,
			@Nullable final Integer phonecallSchemaVersionId,
			@Nullable final Integer phonecallSchemaVersionLineId)
	{
		return phonecallSchemaId != null && phonecallSchemaId > 0 &&
				phonecallSchemaVersionId != null && phonecallSchemaVersionId > 0 &&
				phonecallSchemaVersionLineId != null && phonecallSchemaVersionLineId > 0

						? ofRepoId(phonecallSchemaId, phonecallSchemaVersionId, phonecallSchemaVersionLineId)
						: null;
	}

	public static PhonecallSchemaVersionLineId ofRepoIdOrNull(
			@Nullable final PhonecallSchemaId phonecallSchemaId,
			@Nullable final PhonecallSchemaVersionId phonecallSchemaVersionId,
			final int phonecallSchemaVersionLineId)
	{
		return phonecallSchemaId != null && phonecallSchemaVersionId != null && phonecallSchemaVersionLineId > 0 ? ofRepoId(phonecallSchemaId, phonecallSchemaVersionId, phonecallSchemaVersionLineId) : null;
	}

	private PhonecallSchemaVersionLineId(
			@NonNull final PhonecallSchemaId phonecallSchemaId,
			@NonNull PhonecallSchemaVersionId phonecallSchemaVersionId,
			int phonecallSchemaVersionLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(phonecallSchemaVersionLineId, "phonecallSchemaVersionLineId");
		this.phonecallSchemaId = phonecallSchemaId;
		this.phonecallSchemaVersionId = phonecallSchemaVersionId;
	}

	public static int toRepoId(final PhonecallSchemaVersionLineId phonecallSchemaVersionLineId)
	{
		return toRepoIdOr(phonecallSchemaVersionLineId, -1);
	}

	public static int toRepoIdOr(final PhonecallSchemaVersionLineId phonecallSchemaVersionLineId, final int defaultValue)
	{
		return phonecallSchemaVersionLineId != null ? phonecallSchemaVersionLineId.getRepoId() : defaultValue;
	}

}
