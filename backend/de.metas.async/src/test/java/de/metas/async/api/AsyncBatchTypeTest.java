/*
 * #%L
 * de.metas.async
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

package de.metas.async.api;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class AsyncBatchTypeTest
{
	private AsyncBatchType.AsyncBatchTypeBuilder builder()
	{
		return AsyncBatchType.builder()
				.id(AsyncBatchTypeId.ofRepoId(1))
				.internalName("testInternalName")
				.keepAlive(Duration.ZERO)
				.skipTimeout(Duration.ZERO);
	}

	@Test
	public void isCheckProcessedNeeded_checkProcessedTrue()
	{
		final AsyncBatchType asyncBatchType = builder()
				.checkProcessed(true)
				.adBoilderPlateId(0)
				.build();

		assertThat(asyncBatchType.isCheckProcessedNeeded()).isTrue();
	}

	@Test
	public void isCheckProcessedNeeded_checkProcessedFalse_noBoilerplate()
	{
		final AsyncBatchType asyncBatchType = builder()
				.checkProcessed(false)
				.adBoilderPlateId(0)
				.build();

		assertThat(asyncBatchType.isCheckProcessedNeeded()).isFalse();
	}

	@Test
	public void isCheckProcessedNeeded_checkProcessedFalse_withBoilerplate()
	{
		final AsyncBatchType asyncBatchType = builder()
				.checkProcessed(false)
				.adBoilderPlateId(12345)
				.build();

		assertThat(asyncBatchType.isCheckProcessedNeeded()).isTrue();
	}
}
