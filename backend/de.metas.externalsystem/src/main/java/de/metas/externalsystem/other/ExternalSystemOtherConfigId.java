/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.other;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import lombok.NonNull;
import lombok.Value;

/**
 * Actually wraps the parent config ID, because each externalSystemConfig has just one child config
 * => probably makes sens to do this for all {@link IExternalSystemChildConfigId}s.
 */
@Value
public class ExternalSystemOtherConfigId implements IExternalSystemChildConfigId
{
	@NonNull
	ExternalSystemParentConfigId externalSystemParentConfigId;

	public static ExternalSystemOtherConfigId cast(@NonNull final IExternalSystemChildConfigId id)
	{
		return (ExternalSystemOtherConfigId)id;
	}

	@NonNull
	public static ExternalSystemOtherConfigId ofExternalSystemParentConfigId(@NonNull final ExternalSystemParentConfigId externalSystemParentConfigId)
	{
		return new ExternalSystemOtherConfigId(externalSystemParentConfigId);
	}

	@NonNull
	public ExternalSystemParentConfigId getParentConfigId()
	{
		return externalSystemParentConfigId;
	}

	@Override
	public ExternalSystemType getType()
	{
		return ExternalSystemType.Other;
	}

	@Override
	public int getRepoId()
	{
		return externalSystemParentConfigId.getRepoId();
	}
}
