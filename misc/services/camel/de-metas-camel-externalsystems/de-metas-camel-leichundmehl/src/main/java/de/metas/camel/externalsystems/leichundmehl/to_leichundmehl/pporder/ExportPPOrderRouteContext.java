/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.ConnectionDetails;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfig;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfigs;
import de.metas.common.externalsystem.leichundmehl.JsonPluFileAudit;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.util.List;

@Data
@Builder
public class ExportPPOrderRouteContext
{
	@NonNull
	private final JsonExternalSystemRequest jsonExternalSystemRequest;

	@NonNull
	private final ConnectionDetails connectionDetails;

	@NonNull
	private final String productBaseFolderName;

	private final boolean pluFileExportAuditEnabled;

	@NonNull
	private final JsonExternalSystemLeichMehlConfigProductMapping productMapping;

	@NonNull
	private final JsonExternalSystemLeichMehlPluFileConfigs pluFileConfigs;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonResponseManufacturingOrder jsonResponseManufacturingOrder;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonProduct jsonProduct;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonPluFileAudit jsonPluFileAudit;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String pluFileXmlContent;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String filename;

	@NonNull
	public JsonResponseManufacturingOrder getManufacturingOrderNonNull()
	{
		if (this.jsonResponseManufacturingOrder == null)
		{
			throw new RuntimeCamelException("JsonResponseManufacturingOrder cannot be null!");
		}

		return this.jsonResponseManufacturingOrder;
	}

	@NonNull
	public JsonProduct getProductInfoNonNull()
	{
		if (this.jsonProduct == null)
		{
			throw new RuntimeCamelException("JsonProduct cannot be null!");
		}

		return this.jsonProduct;
	}

	@NonNull
	public JsonPluFileAudit getJsonPluFileAuditNonNull()
	{
		if (this.jsonPluFileAudit == null)
		{
			throw new RuntimeCamelException("JsonPluFileAudit cannot be null!");
		}

		return this.jsonPluFileAudit;
	}

	@NonNull
	public String getUpdatedPLUFileContent()
	{
		if (this.pluFileXmlContent == null)
		{
			throw new RuntimeCamelException("pluFileXmlContent cannot be null!");
		}

		return this.pluFileXmlContent;
	}

	@NonNull
	public String getFilename()
	{
		if (this.filename == null)
		{
			throw new RuntimeCamelException("filename cannot be null!");
		}

		return this.filename;
	}

	@NonNull
	public List<String> getPluFileConfigKeys()
	{
		return this.pluFileConfigs.getPluFileConfigs()
				.stream()
				.map(JsonExternalSystemLeichMehlPluFileConfig::getTargetFieldName)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public Integer getAdPInstance()
	{
		return JsonMetasfreshId.toValue(this.jsonExternalSystemRequest.getAdPInstanceId());
	}
}
