/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mutable counter aggregating success/failure of per-element fan-out dispatches
 * inside {@link ScriptedAdapterConvertMsgFromMFRouteBuilder}.
 * <p>
 * One instance lives on the exchange property for the duration of a single
 * fan-out split and accumulates the per-iteration outcomes. The post-split
 * processor then decides whether to throw (all failed) or pass (at least one
 * succeeded).
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class FanOutResult
{
	@Getter
	private int successCount = 0;

	@Getter
	private int failureCount = 0;

	private final List<String> failureMessages = new ArrayList<>();

	public void recordSuccess()
	{
		successCount++;
	}

	public void recordFailure(@NonNull final String message)
	{
		failureCount++;
		failureMessages.add(message);
	}

	public int getTotalCount()
	{
		return successCount + failureCount;
	}

	public boolean isAllFailed()
	{
		return failureCount > 0 && successCount == 0;
	}

	@NonNull
	public List<String> getFailureMessages()
	{
		return Collections.unmodifiableList(failureMessages);
	}
}
