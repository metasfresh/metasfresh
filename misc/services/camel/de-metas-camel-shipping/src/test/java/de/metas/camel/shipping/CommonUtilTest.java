/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonUtilTest
{

	@Test
	void removeOrgPrefix()
	{
		assertThat(CommonUtil.removeOrgPrefix("")).isEqualTo("");
		assertThat(CommonUtil.removeOrgPrefix("test")).isEqualTo("test");
		assertThat(CommonUtil.removeOrgPrefix("-test")).isEqualTo("test");
		assertThat(CommonUtil.removeOrgPrefix("-test-test")).isEqualTo("test-test");
		assertThat(CommonUtil.removeOrgPrefix("t-test-test")).isEqualTo("test-test");
		assertThat(CommonUtil.removeOrgPrefix("tt-test-test")).isEqualTo("test-test");
		assertThat(CommonUtil.removeOrgPrefix("tt-")).isEqualTo("");
	}
}