package org.adempiere.mm.attributes;

<<<<<<< HEAD
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

=======
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

<<<<<<< HEAD
=======
import java.util.Objects;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class AttributeValueId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static AttributeValueId ofRepoId(final int repoId)
	{
		return new AttributeValueId(repoId);
	}

	public static AttributeValueId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final AttributeValueId attributeValueId)
	{
		return attributeValueId != null ? attributeValueId.getRepoId() : -1;
	}

	private AttributeValueId(final int repoId)
	{
<<<<<<< HEAD
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
=======
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_AttributeValue_ID");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(final AttributeValueId id1, final AttributeValueId id2)
	{
		return Objects.equals(id1, id2);
	}
}
