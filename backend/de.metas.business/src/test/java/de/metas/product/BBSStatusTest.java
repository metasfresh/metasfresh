/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exhaustive status x action matrix test for {@link BBSStatus#isAllowed(ProductLifeCycleAction)}.
 * <p>
 * Matrix (see also {@link BBSStatus} class javadoc):
 * <pre>
 * | Action      | O (OK) | A (Auslauf) | G (Gesperrt) | N (Lieferstopp) |
 * |-------------|:------:|:-----------:|:------------:|:---------------:|
 * | PURCHASE    |   Y    |      N      |      N       |        Y        |
 * | SELL        |   Y    |      Y      |      N       |        Y        |
 * | PICK        |   Y    |      Y      |      N       |        Y        |
 * | MANUFACTURE |   Y    |      Y      |      N       |        Y        |
 * | SHIP        |   Y    |      Y      |      N       |        N        |
 * </pre>
 */
public class BBSStatusTest
{
	@ParameterizedTest
	@MethodSource("matrix")
	public void isAllowed_matchesMatrix(final BBSStatus status, final ProductLifeCycleAction action, final boolean expectedAllowed)
	{
		assertThat(status.isAllowed(action))
				.as("%s.isAllowed(%s)", status, action)
				.isEqualTo(expectedAllowed);
	}

	private static Stream<Arguments> matrix()
	{
		return Stream.of(
				// OK: everything allowed
				Arguments.of(BBSStatus.OK, ProductLifeCycleAction.PURCHASE, true),
				Arguments.of(BBSStatus.OK, ProductLifeCycleAction.SELL, true),
				Arguments.of(BBSStatus.OK, ProductLifeCycleAction.PICK, true),
				Arguments.of(BBSStatus.OK, ProductLifeCycleAction.MANUFACTURE, true),
				Arguments.of(BBSStatus.OK, ProductLifeCycleAction.SHIP, true),

				// PHASE_OUT (Auslauf): blocks PURCHASE only
				Arguments.of(BBSStatus.PHASE_OUT, ProductLifeCycleAction.PURCHASE, false),
				Arguments.of(BBSStatus.PHASE_OUT, ProductLifeCycleAction.SELL, true),
				Arguments.of(BBSStatus.PHASE_OUT, ProductLifeCycleAction.PICK, true),
				Arguments.of(BBSStatus.PHASE_OUT, ProductLifeCycleAction.MANUFACTURE, true),
				Arguments.of(BBSStatus.PHASE_OUT, ProductLifeCycleAction.SHIP, true),

				// BLOCKED (Gesperrt): blocks everything
				Arguments.of(BBSStatus.BLOCKED, ProductLifeCycleAction.PURCHASE, false),
				Arguments.of(BBSStatus.BLOCKED, ProductLifeCycleAction.SELL, false),
				Arguments.of(BBSStatus.BLOCKED, ProductLifeCycleAction.PICK, false),
				Arguments.of(BBSStatus.BLOCKED, ProductLifeCycleAction.MANUFACTURE, false),
				Arguments.of(BBSStatus.BLOCKED, ProductLifeCycleAction.SHIP, false),

				// DO_NOT_DELIVER (Lieferstopp): blocks SHIP only
				Arguments.of(BBSStatus.DO_NOT_DELIVER, ProductLifeCycleAction.PURCHASE, true),
				Arguments.of(BBSStatus.DO_NOT_DELIVER, ProductLifeCycleAction.SELL, true),
				Arguments.of(BBSStatus.DO_NOT_DELIVER, ProductLifeCycleAction.PICK, true),
				Arguments.of(BBSStatus.DO_NOT_DELIVER, ProductLifeCycleAction.MANUFACTURE, true),
				Arguments.of(BBSStatus.DO_NOT_DELIVER, ProductLifeCycleAction.SHIP, false));
	}

	@ParameterizedTest
	@EnumSource(BBSStatus.class)
	public void everyStatus_hasTheExactCodeFromXMProduct(final BBSStatus status)
	{
		// codes must match the AD_Ref_List values (X_M_Product.PRODUCTLIFECYCLESTATUS_* constants)
		assertThat(status.getCode()).isIn("O", "A", "G", "N");
	}

	@Test
	public void ofNullableCode_null_returnsNull()
	{
		assertThat(BBSStatus.ofNullableCode(null)).isNull();
	}

	@Test
	public void ofNullableCode_empty_returnsNull()
	{
		assertThat(BBSStatus.ofNullableCode("")).isNull();
	}

	@Test
	public void ofNullableCode_ok_returnsOK()
	{
		assertThat(BBSStatus.ofNullableCode("O")).isEqualTo(BBSStatus.OK);
	}

	@Test
	public void ofNullableCode_auslauf_returnsPhaseOut()
	{
		assertThat(BBSStatus.ofNullableCode("A")).isEqualTo(BBSStatus.PHASE_OUT);
	}

	@Test
	public void ofNullableCode_gesperrt_returnsBlocked()
	{
		assertThat(BBSStatus.ofNullableCode("G")).isEqualTo(BBSStatus.BLOCKED);
	}

	@Test
	public void ofNullableCode_lieferstopp_returnsDoNotDeliver()
	{
		assertThat(BBSStatus.ofNullableCode("N")).isEqualTo(BBSStatus.DO_NOT_DELIVER);
	}
}
