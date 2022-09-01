/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.base.model;

import org.adempiere.exceptions.AdempiereException;

public enum SyncDirection
{
	LOCAL_TO_REMOTE,
	REMOTE_TO_LOCAL;

	public interface CaseMapper<R>
	{
		R localToRemote();

		R remoteToLocal();
	}

	public <R> R map(final CaseMapper<R> mapper)
	{
		switch (this)
		{
			case LOCAL_TO_REMOTE:
				return mapper.localToRemote();
			case REMOTE_TO_LOCAL:
				return mapper.remoteToLocal();
			default:
				throw new AdempiereException("Unsupported sync direction: " + this);
		}
	}
}
