/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct.aggregation.legacy;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.Duration;

@Value
@Builder
public class FactAcctLogProcessResult
{
	public static FactAcctLogProcessResult ZERO = builder()
			.iterations(0)
			.processedLogRecordsCount(0)
			.build();

	@Builder.Default
	int iterations = 1;

	int processedLogRecordsCount;

	@With
	@Builder.Default
	@NonNull Duration duration = Duration.ZERO;

	public FactAcctLogProcessResult combineWith(final FactAcctLogProcessResult other)
	{
		return FactAcctLogProcessResult.builder()
				.iterations(this.iterations + other.iterations)
				.processedLogRecordsCount(this.processedLogRecordsCount + other.processedLogRecordsCount)
				.duration(this.duration.plus(other.duration))
				.build();
	}
}
