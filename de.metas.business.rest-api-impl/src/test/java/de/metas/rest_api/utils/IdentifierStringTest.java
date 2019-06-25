package de.metas.rest_api.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.util.rest.ExternalId;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

class IdentifierStringTest
{

	@Test
	void of_externalId()
	{
		final IdentifierString testee = IdentifierString.of("ext-abcd");

		assertThat(testee.getType()).isEqualTo(Type.EXTERNAL_ID);
		assertThat(testee.getValue()).isEqualTo("abcd");
		assertThat(testee.asExternalId()).isEqualTo(ExternalId.of("abcd"));
	}

}
