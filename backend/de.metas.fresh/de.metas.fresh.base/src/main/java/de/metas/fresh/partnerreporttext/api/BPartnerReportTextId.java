/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.fresh.partnerreporttext.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

public class BPartnerReportTextId implements RepoIdAware
{
	@JsonCreator
	@Nullable
	public static BPartnerReportTextId ofNullableObject(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		try
		{
			final int id = NumberUtils.asInt(obj, -1);
			return ofRepoIdOrNull(id);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot convert `" + obj + "` from " + obj.getClass() + " to " + BPartnerReportTextId.class, ex);
		}
	}

	public static BPartnerReportTextId ofRepoId(final int repoId)
	{
		return new BPartnerReportTextId(repoId);
	}

	@Nullable
	public static BPartnerReportTextId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private final int repoId;

	public BPartnerReportTextId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "C_BPartner_Report_Text_ID");
		this.repoId = repoId;
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
