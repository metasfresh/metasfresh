/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd;

import org.junit.jupiter.api.Disabled;

@Disabled("Makes ACTUAL calls to DPD api and needs auth")
public class IntegrationDEtoUSTest
{
	// todo to be implemented at a later time; depends on: https://github.com/metasfresh/me03/issues/3157
	// @BeforeEach
	// void setUp()
	// {
	// 	AdempiereTestHelper.get().init();
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD E12 + test persistence after all steps")
	// void E12()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_E12));
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD Classic + test persistence after all steps")
	// void Classic()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_CLASSIC));
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD Express + test persistence after all steps")
	// void Express()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_EXPRESS));
	// }
}
