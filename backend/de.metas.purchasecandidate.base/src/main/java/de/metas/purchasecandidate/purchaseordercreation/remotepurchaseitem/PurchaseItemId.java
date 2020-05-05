package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import de.metas.util.Check;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class PurchaseItemId
{
	public static PurchaseItemId ofRepoId(final int repoId)
	{
		return new PurchaseItemId(repoId);
	}

	public static PurchaseItemId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PurchaseItemId(repoId) : null;
	}

	public static int getRepoIdOr(final PurchaseItemId id, final int defaultValue)
	{
		return id != null ? id.getRepoId() : defaultValue;
	}

	int repoId;

	private PurchaseItemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

}
