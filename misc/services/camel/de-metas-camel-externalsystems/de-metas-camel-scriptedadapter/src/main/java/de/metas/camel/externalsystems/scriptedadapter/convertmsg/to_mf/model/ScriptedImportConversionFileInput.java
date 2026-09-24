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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Envelope handed to the inbound script's {@code transform()} function for a file-based import
 * (e.g. {@code LOCAL_FILE} transport): the imported file's name and its base64-encoded bytes.
 * <p>
 * The field names are a contract with the customer-authored JavaScript that parses this JSON —
 * do not rename without updating the corresponding script(s).
 * <p>
 * Deliberately carries no {@code contentType}: nothing in this codebase derives a MIME type for an
 * arbitrary polled file (the {@code LOCAL_FILE} transport is not PDF-only — see
 * {@code ScriptedImportConversionLocalFileRouteBuilder}), so guessing one (e.g. hard-coding
 * {@code application/pdf}) would be wrong for any other file type. Add it back only once there is a real
 * source for the value.
 */
@Builder
@Jacksonized
@Value
public class ScriptedImportConversionFileInput
{
	@NonNull
	String fileName;

	@NonNull
	String fileBase64;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String attachmentFileName;
}
