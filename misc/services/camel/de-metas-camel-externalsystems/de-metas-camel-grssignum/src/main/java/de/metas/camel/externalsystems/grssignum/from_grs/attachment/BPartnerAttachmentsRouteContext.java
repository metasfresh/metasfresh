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

package de.metas.camel.externalsystems.grssignum.from_grs.attachment;

import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerAttachment;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class BPartnerAttachmentsRouteContext
{
	@NonNull
	private final JsonBPartnerAttachment jsonBPartnerAttachment;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String exportDirectoriesBasePath;

	@NonNull
	public String getExportDirectoriesBasePathNotNull()
	{
		Check.assumeNotNull(exportDirectoriesBasePath, "exportDirectoriesBasePath is not null!");

		return exportDirectoriesBasePath;
	}
}
