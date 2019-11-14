/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dhl;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static de.metas.shipper.gateway.dhl.DhlTestHelper.ACCOUNT_NUMBER_AT;
import static de.metas.shipper.gateway.dhl.DhlTestHelper.createDummyDeliveryOrderDEtoAT;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class IntegrationDEtoATTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@DisplayName("Delivery Order DE -> AT + test persistence after all steps")
	void testAllSteps()
	{
		DhlTestHelper.testAllSteps(createDummyDeliveryOrderDEtoAT(), ACCOUNT_NUMBER_AT);
	}
}
