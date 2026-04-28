/*
 * #%L
 * de.metas.shipper.client.nshift
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

package de.metas.shipper.client.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Dangerous goods class, based on the enum values from the ShipServer API documentation.
 *
 * @see <a href="https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields">nShift Documentation (DGClass - Enum)</a>
 */
@RequiredArgsConstructor
@Getter
public enum JsonDGClass
{

	UNKNOWN(0),
	CLASS_1(1),
	CLASS_2(2),
	CLASS_2_1(3),
	CLASS_3(4),
	CLASS_4_1(5),
	CLASS_4_2(6),
	CLASS_4_3(7),
	CLASS_5_1(8),
	CLASS_5_2(9),
	CLASS_6_1(10),
	CLASS_6_2(11),
	CLASS_7(12),
	CLASS_8(13),
	CLASS_9(14),
	CLASS_1_1A(15),
	CLASS_1_1B(16),
	CLASS_1_1C(17),
	CLASS_1_1D(18),
	CLASS_1_1E(19),
	CLASS_1_1F(20),
	CLASS_1_1G(21),
	CLASS_1_1J(22),
	CLASS_1_1L(23),
	CLASS_1_2B(24),
	CLASS_1_2C(25),
	CLASS_1_2D(26),
	CLASS_1_2E(27),
	CLASS_1_2F(28),
	CLASS_1_2G(29),
	CLASS_1_2H(30),
	CLASS_1_2J(31),
	CLASS_1_2K(32),
	CLASS_1_2L(33),
	CLASS_1_3C(34),
	CLASS_1_3G(35),
	CLASS_1_3H(36),
	CLASS_1_3J(37),
	CLASS_1_3K(38),
	CLASS_1_3L(39),
	CLASS_1_4B(40),
	CLASS_1_4C(41),
	CLASS_1_4D(42),
	CLASS_1_4E(43),
	CLASS_1_4F(44),
	CLASS_1_4G(45),
	CLASS_1_4S(46),
	CLASS_1_5D(47),
	CLASS_1_6N(48),
	CLASS_2_2(49),
	CLASS_2_3(50);

	@JsonValue
	private final int jsonValue;
}