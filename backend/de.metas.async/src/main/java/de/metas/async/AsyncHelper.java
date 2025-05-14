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

package de.metas.async;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;

import static de.metas.async.Async_Constants.DYNATTR_AsyncBatchId;

@UtilityClass
public class AsyncHelper
{
	public static void setAsyncBatchId(final Object record, @Nullable final AsyncBatchId asyncBatchId)
	{
		InterfaceWrapperHelper.setDynAttribute(record, DYNATTR_AsyncBatchId, asyncBatchId);
	}

	@Nullable
	public static AsyncBatchId getAsyncBatchId(@NonNull final Object record)
	{
		return InterfaceWrapperHelper.getDynAttribute(record, DYNATTR_AsyncBatchId);
	}

}
