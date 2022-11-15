/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
public class WorkFile
{
	public static final String IN_PROGRESS_PREFIX = "InProgress_";

	public static WorkFile of(@NonNull final String filename)
	{
		return new WorkFile(filename);
	}

	@Getter(AccessLevel.NONE)
	String filename;

	private WorkFile(@NonNull final String filename)
	{
		this.filename = filename;
	}

	@NonNull
	public String getDownloadInProgressFilename()
	{
		return IN_PROGRESS_PREFIX + filename;
	}

	@NonNull
	public String getReadyFilename()
	{
		return filename;
	}
}
