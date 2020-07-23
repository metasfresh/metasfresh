package org.adempiere.mm.attributes;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

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
public class AttributeSetInstanceId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static AttributeSetInstanceId ofRepoId(final int repoId)
	{
		final AttributeSetInstanceId id = ofRepoIdOrNull(repoId);
		if (id == null)
		{
			throw new AdempiereException("Invalid repoId: " + repoId);
		}
		return id;
	}

	public static AttributeSetInstanceId ofRepoIdOrNone(final int repoId)
	{
		final AttributeSetInstanceId asiId = ofRepoIdOrNull(repoId);
		return asiId != null ? asiId : NONE;
	}

	public static AttributeSetInstanceId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == NONE.repoId)
		{
			return NONE;
		}
		else if (repoId > 0)
		{
			return new AttributeSetInstanceId(repoId);
		}
		else
		{
			return null;
		}
	}

	public static int toRepoId(final AttributeSetInstanceId attributeSetInstanceId)
	{
		return attributeSetInstanceId != null ? attributeSetInstanceId.getRepoId() : -1;
	}

	public static final AttributeSetInstanceId NONE = new AttributeSetInstanceId();

	private AttributeSetInstanceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	private AttributeSetInstanceId()
	{
		this.repoId = 0;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isNone()
	{
		return repoId == NONE.repoId;
	}

	/** @return true if this is about a "real" greater-than-zero {@code M_AttributeSetInstance_ID}. */
	public boolean isRegular()
	{
		return repoId > NONE.repoId;
	}

	public static boolean isRegular(@Nullable final AttributeSetInstanceId asiId)
	{
		return asiId != null && asiId.isRegular();
	}
}
