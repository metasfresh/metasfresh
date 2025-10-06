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
package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the packing group for dangerous goods, as specified by nShift.
 * The packing group indicates the degree of danger.
 */
@RequiredArgsConstructor
@Getter
public enum JsonDGPackingGroup
{
	/**
	 * Packing Group None: Not applicable.
	 */
	NONE(0),
	/**
	 * Packing Group I: High danger.
	 */
	I(1),
	/**
	 * Packing Group II: Medium danger.
	 */
	II(2),
	/**
	 * Packing Group III: Low danger.
	 */
	III(3);

	@JsonValue
	private final int jsonValue;
}