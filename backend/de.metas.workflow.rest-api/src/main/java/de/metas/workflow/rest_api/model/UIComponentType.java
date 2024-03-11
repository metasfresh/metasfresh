/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.MapMaker;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.concurrent.ConcurrentMap;

@EqualsAndHashCode
public final class UIComponentType
{
	private static final ConcurrentMap<String, UIComponentType> cache = new MapMaker().makeMap(); // IMPORTANT to have it before the constants
	public static final UIComponentType SCAN_BARCODE = createAndCache("common/scanBarcode");
	public static final UIComponentType CONFIRM_BUTTON = createAndCache("common/confirmButton");

	@JsonCreator
	public static UIComponentType ofString(@NonNull final String value)
	{
		return cache.computeIfAbsent(value, UIComponentType::new);
	}

	private static UIComponentType createAndCache(@NonNull final String value)
	{
		final UIComponentType type = new UIComponentType(value);
		cache.put(type.value, type);
		return type;
	}

	private final String value;

	private UIComponentType(@NonNull final String value) {this.value = Check.assumeNotEmpty(value, "value");}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return value;
	}
}
