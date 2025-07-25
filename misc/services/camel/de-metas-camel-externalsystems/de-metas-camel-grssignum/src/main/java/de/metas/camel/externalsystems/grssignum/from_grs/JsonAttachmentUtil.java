/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class JsonAttachmentUtil
{
	public JsonAttachment createLocalFileJsonAttachment(
			@NonNull final String basePath,
			@NonNull final String filePath)
	{
		return createLocalFileJsonAttachment(basePath, filePath, null);
	}
	
	public JsonAttachment createLocalFileJsonAttachment(
			@NonNull final String basePath,
			@NonNull final String filePath, 
			@Nullable final ImmutableList<JsonTag> jsonTags)
	{
		final Path attachmentPath = Paths.get(basePath, filePath);
		
		//attachmentPath.toUri().toString() fails in our tests when run on windows. 
		// It sneaks in the drive-letter like "C:/" and breaks the expectation from the snapshot-file.
		// If we ever need to file-URL to contain such a drive-letter, we can still add support for it here.
		final String dataURI = "file://" + attachmentPath.toString().replace("\\", "/");
		
		return JsonAttachment.builder()
				.type(JsonAttachmentSourceType.LocalFileURL)
				.fileName(attachmentPath.getFileName().toString())
				.data(dataURI)
				.tags(jsonTags)
				.build();
	}
}
