/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalsystem.JsonExportDirectorySettings;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class ExportBPartnerRouteContext
{
	@NonNull
	private final String remoteUrl;

	@Nullable
	private final String authToken;

	@NonNull
	private final String tenantId;

	@NonNull
	private final String orgCode;

	@NonNull
	private final JsonMetasfreshId externalSystemConfigId;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonResponseComposite jsonResponseComposite;

	@Nullable
	private JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse;

	@Nullable
	private final JsonExportDirectorySettings jsonExportDirectorySettings;

	@Nullable
	private final Integer pinstanceId;

	@Nullable
	private String bPartnerBasePath;

	@NonNull
	public JsonResponseComposite getJsonResponseComposite()
	{
		Check.assumeNotNull(jsonResponseComposite, "jsonResponseComposite must not be null when this is called!");

		return jsonResponseComposite;
	}
}