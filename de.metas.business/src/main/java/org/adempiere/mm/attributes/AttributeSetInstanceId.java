package org.adempiere.mm.attributes;

import org.adempiere.util.Check;

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
public class AttributeSetInstanceId
{
	int repoId;

	public static AttributeSetInstanceId ofRepoId(final int repoId)
	{
		return new AttributeSetInstanceId(repoId);
	}

	public static AttributeSetInstanceId ofRepoIdOrNull(final int repoId)
	{
		return repoId >= 0
				? new AttributeSetInstanceId(repoId)
				: null;
	}

	public static int toRepoId(final AttributeSetInstanceId attributeSetInstanceId)
	{
		return attributeSetInstanceId != null ? attributeSetInstanceId.getRepoId() : -1;
	}

	private AttributeSetInstanceId(final int repoId)
	{
		// note that there is a special ASI which in fact does have ID=0
		this.repoId = Check.assumeGreaterOrEqualToZero(repoId, "repoId");
	}
}
