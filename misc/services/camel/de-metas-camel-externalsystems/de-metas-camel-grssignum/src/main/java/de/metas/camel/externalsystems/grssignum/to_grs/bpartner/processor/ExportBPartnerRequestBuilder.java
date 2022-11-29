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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPProductCamelRequest;
import de.metas.camel.externalsystems.common.v2.ExternalReferenceLookupCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.ExportBPartnerRouteContext;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.product.v2.response.JsonResponseProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import java.util.List;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_PRODUCT;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.GRSSIGNUM_SYSTEM_NAME;

@UtilityClass
public class ExportBPartnerRequestBuilder
{
	public void prepareRetrieveBPartnerProductRequest(@NonNull final Exchange exchange)
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final BPProductCamelRequest bpProductCamelRequest = BPProductCamelRequest.builder()
				.orgCode(routeContext.getOrgCode())
				.bPartnerIdentifier(String.valueOf(routeContext.getJsonResponseComposite().getBpartner().getMetasfreshId().getValue()))
				.build();

		exchange.getIn().setBody(bpProductCamelRequest);
	}

	public void prepareExternalReferenceLookupRequest(@NonNull final Exchange exchange)
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final JsonResponseProductBPartner jsonResponseProductBPartner = exchange.getIn().getBody(JsonResponseProductBPartner.class);

		if (Check.isEmpty(jsonResponseProductBPartner.getBPartnerProducts()))
		{
			exchange.getIn().setBody(null);
			return;
		}

		final ImmutableSet<JsonMetasfreshId> productIds = jsonResponseProductBPartner.getBPartnerProducts()
				.stream()
				.map(JsonProductBPartner::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final List<JsonExternalReferenceLookupItem> items = productIds.stream()
				.map(productId -> JsonExternalReferenceLookupItem.builder()
						.metasfreshId(productId)
						.type(EXTERNAL_REF_TYPE_PRODUCT)
						.build())
				.collect(ImmutableList.toImmutableList());

		final JsonExternalReferenceLookupRequest jsonExternalReferenceLookupRequest = JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of(GRSSIGNUM_SYSTEM_NAME))
				.items(items)
				.build();

		final ExternalReferenceLookupCamelRequest externalReferenceSearchCamelRequest = ExternalReferenceLookupCamelRequest.builder()
				.externalSystemConfigId(routeContext.getExternalSystemConfigId())
				.adPInstanceId(JsonMetasfreshId.ofOrNull(routeContext.getPinstanceId()))
				.orgCode(routeContext.getOrgCode())
				.jsonExternalReferenceLookupRequest(jsonExternalReferenceLookupRequest)
				.build();

		exchange.getIn().setBody(externalReferenceSearchCamelRequest);
	}
}
