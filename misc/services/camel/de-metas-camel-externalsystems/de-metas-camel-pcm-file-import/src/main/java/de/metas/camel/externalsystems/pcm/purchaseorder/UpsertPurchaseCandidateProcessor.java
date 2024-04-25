/*
 * #%L
 * de-metas-camel-pcm-file-import
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.v2.PurchaseCandidateCamelRequest;
import de.metas.camel.externalsystems.pcm.ExternalId;
import de.metas.camel.externalsystems.pcm.purchaseorder.model.PurchaseOrderRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.JsonPrice;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateItem;
import de.metas.common.rest_api.v2.JsonPurchaseCandidateCreateRequest;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.rest_api.v2.JsonVendor;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.DEFAULT_CURRENCY_CODE;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.DEFAULT_UOM_X12DE355_CODE;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.EUROPE_BERLIN;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.LOCAL_DATE_TIME_FORMATTER;
import static de.metas.camel.externalsystems.pcm.purchaseorder.ImportConstants.PROPERTY_CURRENT_CSV_ROW;

@Value
@Builder
public class UpsertPurchaseCandidateProcessor implements Processor
{
	@NonNull JsonExternalSystemRequest externalSystemRequest;
	@NonNull PInstanceLogger pInstanceLogger;

	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final PurchaseOrderRow poRow = exchange.getIn().getBody(PurchaseOrderRow.class);
		exchange.setProperty(PROPERTY_CURRENT_CSV_ROW, poRow); // needed in case of problems

		final PurchaseCandidateCamelRequest poCamelRequest = mapRowToRequestItem(poRow)
				.map(item -> JsonPurchaseCandidateCreateRequest
						.builder()
						.purchaseCandidate(item)
						.build())
				.map(upsertRequest -> PurchaseCandidateCamelRequest.builder()
						.jsonPurchaseCandidateCreateRequest(upsertRequest)
						.build())
				.orElse(null);
	
		exchange.getIn().setBody(poCamelRequest);
	}

	@NonNull
	private Optional<JsonPurchaseCandidateCreateItem> mapRowToRequestItem(@NonNull final PurchaseOrderRow purchaseOrderRow)
	{
		if (hasMissingFields(purchaseOrderRow))
		{
			pInstanceLogger.logMessage("Skipping row due to missing mandatory information, see row: " + purchaseOrderRow);
			return Optional.empty();
		}

		final JsonPurchaseCandidateCreateItem requestItem = JsonPurchaseCandidateCreateItem.builder()
				.orgCode(externalSystemRequest.getOrgCode())
				.productIdentifier(ExternalId.ofId(purchaseOrderRow.getProductIdentifier()).toExternalIdentifierString())
				.warehouseIdentifier(ExternalId.ofId(purchaseOrderRow.getWarehouseIdentifier()).toExternalIdentifierString())
				.vendor(JsonVendor.builder()
								.bpartnerIdentifier(ExternalId.ofId(purchaseOrderRow.getBpartnerIdentifier()).toExternalIdentifierString())
								.build())
				.externalHeaderId(purchaseOrderRow.getExternalHeaderId())
				.externalLineId(purchaseOrderRow.getExternalLineId())
				.poReference(purchaseOrderRow.getPoReference())
				.qty(JsonQuantity.builder()
							 .qty(purchaseOrderRow.getQty())
							 .uomCode(DEFAULT_UOM_X12DE355_CODE)
							 .build())
				.isManualPrice(true)
				.isPrepared(true)
				.price(JsonPrice.builder()
							   .value(purchaseOrderRow.getPrice())
							   .currencyCode(DEFAULT_CURRENCY_CODE)
							   .priceUomCode(DEFAULT_UOM_X12DE355_CODE)
							   .build())
				.purchaseDateOrdered(Optional.ofNullable(StringUtils.trimBlankToNull(purchaseOrderRow.getDateOrdered()))
											 .map(UpsertPurchaseCandidateProcessor::parseDateTime)
											 .orElse(null))
				.purchaseDatePromised(Optional.ofNullable(StringUtils.trimBlankToNull(purchaseOrderRow.getDatePromised()))
											  .map(UpsertPurchaseCandidateProcessor::parseDateTime)
											  .orElse(null))
				.build();

		return Optional.of(requestItem);
	}

	private static boolean hasMissingFields(@NonNull final PurchaseOrderRow orderRow)
	{
		return Check.isBlank(orderRow.getProductIdentifier())
				|| Check.isBlank(orderRow.getBpartnerIdentifier())
				|| Check.isBlank(orderRow.getWarehouseIdentifier())
				|| Check.isBlank(orderRow.getPoReference())
				|| Check.isBlank(orderRow.getExternalHeaderId())
				|| Check.isBlank(orderRow.getExternalLineId())
				|| orderRow.getPrice() == null
				|| orderRow.getQty() == null;
	}

	@NonNull
	private static ZonedDateTime parseDateTime(@NonNull final String dateString)
	{
		final LocalDateTime localDateTime = LocalDateTime.parse(dateString, LOCAL_DATE_TIME_FORMATTER);
		return localDateTime.atZone(EUROPE_BERLIN);
	}
}
