/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.other;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ExternalSystemOtherValue
{
	@NonNull
	public static ExternalSystemOtherValue ofString(@NonNull final String value)
	{
		try
		{
			final int externalSystemParenConfigRepoId = Integer.parseInt(value);

			final ExternalSystemParentConfigId configId = ExternalSystemParentConfigId.ofRepoId(externalSystemParenConfigRepoId);

			return of(configId);
		}
		catch (final Throwable t)
		{
			throw new InvalidIdentifierException(value, null, t);
		}
	}

	@NonNull
	ExternalSystemParentConfigId externalSystemParentConfigId;

	@NonNull
	public String getStringValue()
	{
		return String.valueOf(externalSystemParentConfigId.getRepoId());
	}

	@NonNull
	public ExternalSystemOtherConfigId getExternalSystemOtherConfigId()
	{
		return ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(getExternalSystemParentConfigId());
	}
}
