/*
 * #%L
 * de.metas.shipper.gateway.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the kind of dangerous goods regulation.
 *
 * @see <a href="https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields">nShift Documentation (Dangerous goods object)</a>
 */
@RequiredArgsConstructor
@Getter
public enum JsonDGKind
{
	ADR_RID(0),
	IMDG(1),
	LIMITED_QUANTITIES(2),
	EXCEPTED_QUANTITIES(3);

	@JsonValue
	private final int jsonValue;
}