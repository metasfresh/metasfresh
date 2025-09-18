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
		assertThat(ExternalSystemType.ofLegacyCode("S6").isShopware6()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("A").isAlberta()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("WOO").isWOO()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("Other").isOther()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("RabbitMQ").isRabbitMQ()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("GRS").isGRSSignum()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("LM").isLeichUndMehl()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("PC").isPrintClient()).isTrue();
		assertThat(ExternalSystemType.ofLegacyCode("PCM").isProCareManagement()).isTrue();
	}

	@Test
	void ofValue()
	{
		assertThat(ExternalSystemType.ofValue("Shopware6").isShopware6()).isTrue();
		assertThat(ExternalSystemType.ofValue("ALBERTA").isAlberta()).isTrue();
		assertThat(ExternalSystemType.ofValue("WOO").isWOO()).isTrue();
		assertThat(ExternalSystemType.ofValue("Other").isOther()).isTrue();
		assertThat(ExternalSystemType.ofValue("RabbitMQRESTAPI").isRabbitMQ()).isTrue();
		assertThat(ExternalSystemType.ofValue("GRSSignum").isGRSSignum()).isTrue();
		assertThat(ExternalSystemType.ofValue("LeichUndMehl").isLeichUndMehl()).isTrue();
		assertThat(ExternalSystemType.ofValue("PrintingClient").isPrintClient()).isTrue();
		assertThat(ExternalSystemType.ofValue("ProCareManagement").isProCareManagement()).isTrue();
		assertThat(ExternalSystemType.ofValue("Github").isGithub()).isTrue();
		assertThat(ExternalSystemType.ofValue("Everhour").isEverhour()).isTrue();
	}
}