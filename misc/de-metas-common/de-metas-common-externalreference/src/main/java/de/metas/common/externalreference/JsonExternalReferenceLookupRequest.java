/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * A request can have one or multiple items.
 * Each item is a little request of its own and {@link JsonExternalReferenceLookupResponse}
 * contains one response item for each request item.
 */
@Value
@Builder
public class JsonExternalReferenceLookupRequest
{
	String systemName;

	@Singular
	List<JsonExternalReferenceLookupItem> items;
}
