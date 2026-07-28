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

package de.metas.camel.externalsystems.scriptedadapter;

public interface ScriptedAdapterConstants
{
	String ROUTE_MSG_FROM_MF_CONTEXT = "ScriptedAdapterConvertMsgFromMFContext";
	String ATTACHMENT_FILE_NAME = "scripted-adapter-log.txt";

	String SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME = "ScriptedImportConversion";
	String PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT = "ScriptedImportedConversionRouteContext";
	String PREFIX_IMPORT_AUTHORITY = "IMPORT:";
	String PROPERTY_ENDPOINT_NAME = "endpointName";
	String FIELD_ERROR_MESSAGE = "errors";
	String EXCEPTION_PREFIX = "Exception - ";

	/**
	 * Exchange property holding the original (pre-transform) payload string, captured before the
	 * scripted transform runs, so it can be archived LOCALLY on both the success and the error path
	 * (see {@code ScriptedImportConversionLocalArchiver}). Shared between the SFTP and the REST dynamic
	 * route builders.
	 */
	String PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD = "ScriptedImportConversion-originalPayload";

	// Default LOCAL archive folders (container paths) used when the endpoint's own dir fields are unset.
	// Mirrors the PROPERTY_SCRIPTING_REPO_BASE_DIR container-path convention (/app/scriptedadapter/repo).
	String DEFAULT_LOCAL_PROCESSED_DIR = "/app/scriptedadapter/archive/processed";
	String DEFAULT_LOCAL_ERROR_DIR = "/app/scriptedadapter/archive/error";
}
