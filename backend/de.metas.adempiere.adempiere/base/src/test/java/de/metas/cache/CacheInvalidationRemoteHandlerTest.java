package de.metas.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.event.Event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

class CacheInvalidationRemoteHandlerTest
{

	@Test
	void createEventFromRequest()
	{
		final CacheInvalidateMultiRequest multiRequest = CacheInvalidateMultiRequest.of(CacheInvalidateRequest.builder()
				.rootRecord("SomeRootTable", 123)
				.childRecord("SomeChildTable", 456)
				.build());

		final Event result = CacheInvalidationRemoteHandler.instance.createEventFromRequest(multiRequest);
		assertThat(result).isNotNull();
	}

}
