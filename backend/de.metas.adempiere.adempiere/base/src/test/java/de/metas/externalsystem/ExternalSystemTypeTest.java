/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.externalsystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalSystemTypeTest
{
	@Test
	void ofLegacyCode()
	{
		assertThat(ExternalSystemType.ofLegacyCode("S6")).isEqualTo(ExternalSystemType.Shopware6);
		assertThat(ExternalSystemType.ofLegacyCode("Shopware6")).isEqualTo(ExternalSystemType.Shopware6);
		assertThat(ExternalSystemType.ofLegacyCode("A")).isEqualTo(ExternalSystemType.Alberta);
		assertThat(ExternalSystemType.ofLegacyCode("Alberta")).isEqualTo(ExternalSystemType.Alberta);
		assertThat(ExternalSystemType.ofLegacyCode("WOO")).isEqualTo(ExternalSystemType.WOO);
		assertThat(ExternalSystemType.ofLegacyCode("Other")).isEqualTo(ExternalSystemType.Other);
		assertThat(ExternalSystemType.ofLegacyCode("RabbitMQ")).isEqualTo(ExternalSystemType.RabbitMQ);
		assertThat(ExternalSystemType.ofLegacyCode("GRS")).isEqualTo(ExternalSystemType.WOO);
		assertThat(ExternalSystemType.ofLegacyCode("LM")).isEqualTo(ExternalSystemType.LeichUndMehl);
		assertThat(ExternalSystemType.ofLegacyCode("PC")).isEqualTo(ExternalSystemType.PrintClient);
		assertThat(ExternalSystemType.ofLegacyCode("PCM")).isEqualTo(ExternalSystemType.ProCareManagement);
	}
}