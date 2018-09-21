package org.adempiere.mm.attributes;

import org.adempiere.exceptions.AdempiereException;

import de.metas.lang.RepoIdAware;
import de.metas.util.Check;
import lombok.NonNull;
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
public class AttributeId implements RepoIdAware
{
	int repoId;

	public static AttributeId ofRepoId(final int repoId)
	{
		return new AttributeId(repoId);
	}

	public static AttributeId ofRepoIdObj(@NonNull final Object repoIdObj)
	{
		if (repoIdObj instanceof AttributeId)
		{
			return (AttributeId)repoIdObj;
		}
		else if (repoIdObj instanceof Integer)
		{
			return ofRepoId((int)repoIdObj);
		}
		else
		{
			try
			{
				final int repoId = Integer.parseInt(repoIdObj.toString());
				return ofRepoId(repoId);
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("Failed converting '" + repoIdObj + "' (" + repoIdObj.getClass() + ") to " + AttributeId.class, ex);
			}
		}
	}

	public static AttributeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final AttributeId attributeId)
	{
		return attributeId != null ? attributeId.getRepoId() : -1;
	}

	private AttributeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}
}
