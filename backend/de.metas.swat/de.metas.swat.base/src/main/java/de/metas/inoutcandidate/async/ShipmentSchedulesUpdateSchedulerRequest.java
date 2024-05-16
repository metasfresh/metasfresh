/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.async;

import de.metas.async.AsyncBatchId;
import lombok.Builder;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Properties;

@Value
@Builder
public class ShipmentSchedulesUpdateSchedulerRequest
{
	Properties ctx;
	String trxName;
	AsyncBatchId asyncBatchId;

	public ShipmentSchedulesUpdateSchedulerRequest(
			@NonNull final Properties ctx,
			@Nullable final String trxName,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		this.ctx = ctx;
		this.trxName = trxName;
		this.asyncBatchId = asyncBatchId;
	}
}
