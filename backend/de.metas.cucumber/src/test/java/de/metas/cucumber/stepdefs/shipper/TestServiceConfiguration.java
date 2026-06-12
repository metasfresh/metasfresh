/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.shipper.client.nshift.NShiftShipmentService;
import de.metas.shipper.gateway.nshift.client.ShipAdvisorService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestServiceConfiguration
{
	/**
	 * Stub advise at the gateway boundary ({@link ShipAdvisorService}) instead of the underlying
	 * client service, so the cucumber advise stub stays independent of which advise endpoint the
	 * client layer routes to (a switch between endpoints can be added below this seam).
	 * Bean name differs from the scanned {@code @Service} ({@code shipAdvisorService}) so both
	 * coexist; {@code @Primary} makes this mock the one injected.
	 */
	@Bean
	@Primary
	public ShipAdvisorService shipAdvisorServiceMock()
	{
		return Mockito.mock(ShipAdvisorService.class);
	}

	@Bean
	@Primary
	public NShiftShipmentService nShiftShipmentService()
	{
		return Mockito.mock(NShiftShipmentService.class);
	}
}
