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

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.ConnectionDetails;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlPluFileConfigs;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonPluFileAudit;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.nio.file.Path;

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

	@NonNull
	private final JsonExternalSystemLeichMehlConfigProductMapping productMapping;

	@NonNull
	private final JsonExternalSystemLeichMehlPluFileConfigs pluFileConfigs;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonResponseManufacturingOrder jsonResponseManufacturingOrder;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonProductInfo.JsonProductInfoBuilder jsonProductBuilder;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonPluFileAudit jsonPluFileAudit;

	@Nullable
	@Getter(AccessLevel.NONE)
	private Path filePath;

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
	public JsonProductInfo.JsonProductInfoBuilder getProductInfoBuilderNonNull()
	{
		if (this.jsonProductBuilder == null)
		{
			throw new RuntimeCamelException("JsonProductInfo.JsonProductInfoBuilder cannot be null!");
		}

		return this.jsonProductBuilder;
	}

	@NonNull
	public JsonProductInfo getProductInfoNonNull()
	{
		return getProductInfoBuilderNonNull().build();
	}

	@NonNull
	public JsonPluFileAudit getJsonPluFileAuditNonNull()
	{
		if (this.jsonPluFileAudit == null)
		{
			throw new RuntimeCamelException("JsonPluFileAudit cannot be null!");
		}

		return jsonPluFileAudit;
	}

	@NonNull
	public Path getFilePathNonNull()
	{
		if (this.filePath == null)
		{
			throw new RuntimeCamelException("filePath cannot be null!");
		}

		return this.filePath;
	}
}
