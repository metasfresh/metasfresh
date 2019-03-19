package de.metas.phonecall;

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
			final int phonecallSchemaId,
			final int phonecallSchemaVersionId,
			final int phonecallSchemaVersionLineId)
	{
		return new PhonecallSchemaVersionLineId(
				PhonecallSchemaId.ofRepoId(phonecallSchemaId),
				PhonecallSchemaVersionId.ofRepoId(phonecallSchemaId, phonecallSchemaVersionId),
				phonecallSchemaVersionLineId);
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
}
