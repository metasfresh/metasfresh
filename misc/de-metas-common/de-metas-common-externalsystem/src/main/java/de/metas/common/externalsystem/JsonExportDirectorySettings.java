/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Value
public class JsonExportDirectorySettings
{
	@NonNull
	@JsonProperty("basePath")
	String basePath;

	@NonNull
	@JsonProperty("directories")
	List<String> directories;

	@JsonCreator
	@Builder
	public JsonExportDirectorySettings(
			@JsonProperty("basePath") @NonNull final String basePath,
			@JsonProperty("directories") @NonNull final List<String> directories)
	{
		this.basePath = basePath;
		this.directories = directories;
	}

	@JsonIgnore
	@NonNull
	public Path getBasePath(@NonNull final String bpartnerValue)
	{
		return Paths.get(basePath, bpartnerValue);
	}

	@JsonIgnore
	@NonNull
	public List<Path> getDirectoriesPath(@NonNull final String bpartnerValue)
	{
		final Path basePath = getBasePath(bpartnerValue);

		return directories.stream()
				.map(directory -> Paths.get(basePath.toString(), directory))
				.collect(ImmutableList.toImmutableList());
	}
}