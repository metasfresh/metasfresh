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
import de.metas.common.externalsystem.JsonExportDirectorySettings;
import lombok.Builder;
import lombok.Data;
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

	@Nullable
	private final JsonExportDirectorySettings jsonExportDirectorySettings;

	@Nullable
	private final Integer pinstanceId;

	@Nullable
	private String bPartnerBasePath;

	@Nullable
	private JsonResponseComposite jsonResponseComposite;
}