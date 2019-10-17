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
public class PhonecallSchemaVersionId implements RepoIdAware
{
	int repoId;

	@NonNull
	PhonecallSchemaId phonecallSchemaId;

	public static PhonecallSchemaVersionId ofRepoId(@NonNull final PhonecallSchemaId phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		return new PhonecallSchemaVersionId(phonecallSchemaId, phonecallSchemaVersionId);
	}

	public static PhonecallSchemaVersionId ofRepoId(final int phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		return new PhonecallSchemaVersionId(PhonecallSchemaId.ofRepoId(phonecallSchemaId), phonecallSchemaVersionId);
	}

	public static PhonecallSchemaVersionId ofRepoIdOrNull(
			@Nullable final PhonecallSchemaId phonecallSchemaId,
			final int phonecallSchemaVersionId)
	{
		return phonecallSchemaId != null && phonecallSchemaVersionId > 0 ? ofRepoId(phonecallSchemaId, phonecallSchemaVersionId) : null;
	}

	private PhonecallSchemaVersionId(@NonNull final PhonecallSchemaId phonecallSchemaId, final int phonecallSchemaVersionId)
	{
		this.repoId = Check.assumeGreaterThanZero(phonecallSchemaVersionId, "phonecallSchemaVersionId");
		this.phonecallSchemaId = phonecallSchemaId;
	}
}
