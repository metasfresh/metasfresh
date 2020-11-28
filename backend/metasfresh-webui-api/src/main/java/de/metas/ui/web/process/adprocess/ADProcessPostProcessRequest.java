package de.metas.ui.web.process.adprocess;

import javax.annotation.Nullable;

import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class ADProcessPostProcessRequest
{
	ViewId viewId;
	ProcessInfo processInfo;
	ProcessExecutionResult processExecutionResult;
	DocumentId instanceIdOverride;

	@Builder
	private ADProcessPostProcessRequest(
			@Nullable final ViewId viewId,
			@NonNull final ProcessInfo processInfo,
			@NonNull final ProcessExecutionResult processExecutionResult,
			@Nullable final DocumentId instanceIdOverride)
	{
		this.viewId = viewId;
		this.processInfo = processInfo;
		this.processExecutionResult = processExecutionResult;
		this.instanceIdOverride = instanceIdOverride;
	}
}
