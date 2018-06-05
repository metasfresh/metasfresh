package org.adempiere.service;

import org.adempiere.util.Check;
import org.compiere.util.Env;

import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * AD_Client_ID
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class ClientId
{
	public static ClientId ofRepoId(final int repoId)
	{
		if (repoId == SYSTEM.repoId)
		{
			return SYSTEM;
		}
		return new ClientId(repoId);
	}

	public static ClientId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == SYSTEM.repoId)
		{
			return SYSTEM;
		}
		else if (repoId <= 0)
		{
			return null;
		}
		else
		{
			return ofRepoId(repoId);
		}
	}

	public static int toRepoId(final ClientId clientId)
	{
		return clientId != null ? clientId.getRepoId() : -1;
	}

	public static final ClientId SYSTEM = new ClientId();

	int repoId;

	private ClientId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	private ClientId()
	{
		this.repoId = Env.CTXVALUE_AD_Client_ID_System;
	}

	public boolean isSystem()
	{
		return repoId == Env.CTXVALUE_AD_Client_ID_System;
	}
}
