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

package de.metas.externalsystem.externalservice.authorization;

import de.metas.i18n.AdMessageKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value
@Builder
public class ESMissingConfigurationException extends RuntimeException
{
	@NonNull
	AdMessageKey adMessageKey;
	@Nullable
	List<Object> params;

	public ESMissingConfigurationException(
			@NonNull final AdMessageKey adMessageKey,
			@Nullable final List<Object> params)
	{
		super(adMessageKey.toAD_Message());
		this.adMessageKey = adMessageKey;
		this.params = params;
	}
}
