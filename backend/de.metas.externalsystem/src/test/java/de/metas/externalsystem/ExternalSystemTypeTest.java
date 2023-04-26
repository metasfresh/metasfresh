/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

import static org.assertj.core.api.Assertions.*;

class ExternalSystemTypeTest
{
	@Test
	void ofCodeOrNameOrNull()
	{
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("S6")).isEqualTo(ExternalSystemType.Shopware6);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("Shopware6")).isEqualTo(ExternalSystemType.Shopware6);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("A")).isEqualTo(ExternalSystemType.Alberta);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("Alberta")).isEqualTo(ExternalSystemType.Alberta);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("Ebay")).isEqualTo(ExternalSystemType.Ebay);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("WOO")).isEqualTo(ExternalSystemType.WOO);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("SAP")).isEqualTo(ExternalSystemType.SAP);
		assertThat(ExternalSystemType.ofCodeOrNameOrNull("blah")).isNull();
	}
}