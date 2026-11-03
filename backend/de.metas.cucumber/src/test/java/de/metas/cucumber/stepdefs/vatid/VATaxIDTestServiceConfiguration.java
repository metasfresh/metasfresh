/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.vatid;

import de.metas.vatid.VATaxIDOnlineChecker;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Stubs the VAT-ID online check at its SPI boundary ({@link VATaxIDOnlineChecker}) rather than at the HTTP
 * layer, so no scenario depends on the availability of a live third-party service.
 *
 * <p>Picked up by component scanning; {@code @Primary} makes the mock the injected candidate. The name is
 * deliberately not {@code TestServiceConfiguration} (used in {@code stepdefs.shipper}): scanned
 * {@code @Configuration} beans are named after the simple class name, so two would collide with a
 * {@code ConflictingBeanDefinitionException}.
 *
 * <p>Programmed per scenario by {@code VATaxIDOnlineChecker_StepDef}.
 */
@Configuration
public class VATaxIDTestServiceConfiguration
{
	@Bean
	@Primary
	public VATaxIDOnlineChecker vataxIDOnlineCheckerMock()
	{
		return Mockito.mock(VATaxIDOnlineChecker.class);
	}
}
