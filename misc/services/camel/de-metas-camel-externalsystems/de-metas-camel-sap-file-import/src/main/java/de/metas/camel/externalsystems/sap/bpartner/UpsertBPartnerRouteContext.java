/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.common.externalsystem.JsonExternalSAPBPartnerImportSettings;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
@Data
public class UpsertBPartnerRouteContext
{
	@NonNull
	final String orgCode;

	@NonNull
	final JsonMetasfreshId externalSystemConfigId;

	@NonNull
	final ImmutableList<JsonExternalSAPBPartnerImportSettings> bPartnerImportSettings;

	@Nullable
	UpsertBPartnerRequestBuilder syncBPartnerRequestBuilder;

	public void initUpsertBPartnerRequestBuilder(@NonNull final BPartnerRow bPartnerRow) throws Exception
	{
		this.syncBPartnerRequestBuilder = UpsertBPartnerRequestBuilder.of(bPartnerRow, orgCode, externalSystemConfigId, bPartnerImportSettings);
	}
}
