package org.adempiere.mm.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;

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
public class AttributeSetId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static AttributeSetId ofRepoId(final int repoId)
	{
		final AttributeSetId id = ofRepoIdOrNull(repoId);
		if (id == null)
		{
			throw new AdempiereException("Invalid repoId: " + repoId);
		}
		return id;
	}

	public static AttributeSetId ofRepoIdOrNone(final int repoId)
	{
		final AttributeSetId id = ofRepoIdOrNull(repoId);
		return id != null ? id : NONE;
	}

	@Nullable
	public static AttributeSetId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == NONE.repoId)
		{
			return NONE;
		}
		else if (repoId > 0)
		{
			return new AttributeSetId(repoId);
		}
		else
		{
			return null;
		}
	}

	public static int toRepoId(@Nullable final AttributeSetId attributeSetId)
	{
		return attributeSetId != null ? attributeSetId.getRepoId() : -1;
	}

	public static final AttributeSetId NONE = new AttributeSetId();

	private AttributeSetId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	private AttributeSetId()
	{
		this.repoId = AttributeConstants.M_AttributeSet_ID_None;
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
}
