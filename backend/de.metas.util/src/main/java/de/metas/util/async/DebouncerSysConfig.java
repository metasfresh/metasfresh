/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.util.async;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum DebouncerSysConfig
{
	EXPORT_BUFFER_MAX_SIZE("de.metas.externalsystem.debouncer.bufferMaxSize", 100),
	EXPORT_DELAY_IN_MILLIS("de.metas.externalsystem.debouncer.delayInMillis", 5000);

	@NonNull
	private final String sysConfigName;

	private final int defaultValue;
}
