/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonErrorTest
{

	@Test
	void union()
	{
		// given
		final JsonError error1 = JsonError.builder()
				.error(JsonErrorItem.builder().message("error11").build())
				.error(JsonErrorItem.builder().message("error12").build())
				.build();

		final JsonError error2 = JsonError.builder()
				.error(JsonErrorItem.builder().message("error21").build())
				.error(JsonErrorItem.builder().message("error22").build())
				.build();

		// when
		final JsonError union = JsonError.union(error1, error2);

		// then
		assertThat(union.getErrors())
				.extracting("message")
				.containsExactlyInAnyOrder("error11", "error12", "error21", "error22");
	}
}