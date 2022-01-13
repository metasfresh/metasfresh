/*
 * #%L
 * de.metas.async
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

package de.metas.async.event;

import de.metas.async.QueueWorkPackageId;
import de.metas.util.JSONObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static de.metas.async.event.WorkpackageProcessedEvent.Status.DONE;

class WorkpackageProcessedEventTest
{
	@Test
	void serializeDeserialize()
	{
		final WorkpackageProcessedEvent event = WorkpackageProcessedEvent.builder()
				.status(DONE)
				.correlationId(UUID.randomUUID())
				.workPackageId(QueueWorkPackageId.ofRepoId(23)).build();

		final JSONObjectMapper<WorkpackageProcessedEvent> objectMapper = JSONObjectMapper.forClass(WorkpackageProcessedEvent.class);
		final String string = objectMapper.writeValueAsString(event);

		final WorkpackageProcessedEvent event1 = objectMapper.readValue(string);

		Assertions.assertThat(event1).isEqualTo(event);

	}
}