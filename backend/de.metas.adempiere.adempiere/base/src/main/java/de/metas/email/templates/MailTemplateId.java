package de.metas.email.templates;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
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
public class MailTemplateId implements RepoIdAware
{
	@JsonCreator
	public static MailTemplateId ofRepoId(final int repoId)
	{
		return new MailTemplateId(repoId);
	}

	public static MailTemplateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new MailTemplateId(repoId) : null;
	}

	public static Optional<MailTemplateId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private MailTemplateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "R_MailText_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final MailTemplateId mailTemplateId)
	{
		return mailTemplateId != null ? mailTemplateId.getRepoId() : -1;
	}
}
