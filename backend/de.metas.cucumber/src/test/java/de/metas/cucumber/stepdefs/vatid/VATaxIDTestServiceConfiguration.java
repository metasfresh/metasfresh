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
 * Stubs the VAT-ID online check at its SPI boundary ({@link VATaxIDOnlineChecker}) rather than at the
 * HTTP layer, so no cucumber scenario depends on a live third-party service (VIES) — availability of a
 * public EU endpoint must never decide whether our build is green.
 *
 * <p>Picked up by component scanning; nothing references this class explicitly. {@code @Primary} makes
 * the mock the injected candidate. The class name is deliberately NOT {@code TestServiceConfiguration}
 * (the name used in {@code de.metas.cucumber.stepdefs.shipper}): scanned {@code @Configuration} beans
 * are named after their simple class name, so two identically named configuration classes would collide
 * with a {@code ConflictingBeanDefinitionException}.
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
