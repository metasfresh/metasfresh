/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.from_grs.bom.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.BOMUpsertCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.JsonBOMUtil;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOMLine;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateRequest;
import de.metas.common.rest_api.v2.bom.JsonCreateBOMLine;
import de.metas.common.util.Check;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

public class PushBOMProductsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final PushBOMsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_PUSH_BOMs_CONTEXT, PushBOMsRouteContext.class);

		final JsonBOM jsonBOM = context.getJsonBOM();

		final BOMUpsertCamelRequest camelRequest = getBOMUpsertCamelRequest(jsonBOM);

		exchange.getIn().setBody(camelRequest, BOMUpsertCamelRequest.class);
	}

	@NonNull
	private BOMUpsertCamelRequest getBOMUpsertCamelRequest(@NonNull final JsonBOM jsonBOM)
	{
		if (jsonBOM.getBomLines().isEmpty())
		{
			throw new RuntimeCamelException("Missing lines!");
		}

		final List<JsonCreateBOMLine> bomLines = jsonBOM.getBomLines()
				.stream()
				.map(line -> getJsonCreateBOMLine(line, jsonBOM.getScrap()))
				.collect(ImmutableList.toImmutableList());

		final JsonBOMCreateRequest jsonBOMCreateRequest = JsonBOMCreateRequest.builder()
				.uomCode(GRSSignumConstants.DEFAULT_UOM_CODE)
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(jsonBOM.getProductId()))
				.name(JsonBOMUtil.getName(jsonBOM))
				.isActive(jsonBOM.isActive())
				.validFrom(SystemTime.asInstant())
				.bomLines(bomLines)
				.build();

		return getBOMUpsertCamelRequest(jsonBOMCreateRequest);
	}

	@NonNull
	private static JsonCreateBOMLine getJsonCreateBOMLine(@NonNull final JsonBOMLine jsonBOMLine, @Nullable final BigDecimal scrap)
	{
		final JsonCreateBOMLine.JsonCreateBOMLineBuilder jsonCreateBOMLineBuilder = JsonCreateBOMLine.builder()
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(jsonBOMLine.getProductId()))
				.line(jsonBOMLine.getLine())
				.isQtyPercentage(Boolean.TRUE)
				.qtyBom(JsonQuantity.builder()
								.qty(jsonBOMLine.getQtyBOM())
								.uomCode(jsonBOMLine.getUom())
								.build())
				.scrap(scrap);

		if (Check.isNotBlank(jsonBOMLine.getCountryCode()))
		{
			jsonCreateBOMLineBuilder.attributeSetInstance(JsonAttributeSetInstance.builder()
																  .attributeInstance(JsonAttributeInstance.builder()
																							 .attributeCode(GRSSignumConstants.HERKUNFT_ATTRIBUTE_CODE)
																							 .valueStr(jsonBOMLine.getCountryCode())
																							 .build())
																  .build());
		}

		return jsonCreateBOMLineBuilder.build();
	}

	@NonNull
	private static BOMUpsertCamelRequest getBOMUpsertCamelRequest(@NonNull final JsonBOMCreateRequest jsonRequest)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		return BOMUpsertCamelRequest.builder()
				.jsonBOMCreateRequest(jsonRequest)
				.orgCode(credentials.getOrgCode())
				.build();
	}
}
