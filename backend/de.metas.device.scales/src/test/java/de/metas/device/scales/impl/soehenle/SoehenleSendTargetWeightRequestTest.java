/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.impl.soehenle;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class SoehenleSendTargetWeightRequestTest
{

	@Test
	void getCmd()
	{
		final SoehenleSendTargetWeightRequest request = SoehenleSendTargetWeightRequest.builder()
				.targetWeight(new BigDecimal("11"))
				.positiveTolerance(new BigDecimal("9.876"))
				.negativeTolerance(new BigDecimal("12"))
				.build();

		assertThat(request.getCmd()).isEqualTo("<K180K11,00;12,00;9,88>");
	}
}