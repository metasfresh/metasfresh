/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.ordercandidate.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.common.AlbertaUtil;
import de.metas.camel.externalsystems.alberta.common.CommonAlbertaConstants;
import de.metas.camel.externalsystems.alberta.common.DataMapper;
import de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants;
import de.metas.camel.externalsystems.alberta.ordercandidate.NextImportSinceTimestamp;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderLineInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import io.swagger.client.model.Order;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;
import static de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants.ROUTE_PROPERTY_CURRENT_ORDER;
import static de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants.ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static org.threeten.bp.temporal.ChronoField.EPOCH_DAY;

public class JsonOLCandCreateRequestProcessor implements Processor
{
	public static void prepareBPartnerInfoForPatientRequest(@NonNull final Exchange exchange)
	{
		final Order order = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CURRENT_ORDER, Order.class);

		final JsonMetasfreshId externalSystemConfigId = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID, JsonMetasfreshId.class);

		final Integer adPInstanceId = exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class);

		final BPRetrieveCamelRequest retrieveCamelRequest = BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier(formatExternalId(order.getPatientId()))
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(JsonMetasfreshId.of(adPInstanceId))
				.build();

		exchange.getIn().setBody(retrieveCamelRequest);
	}

	public static void processUpsertDeliveryAddressResponse(@NonNull final Exchange exchange)
	{
		final JsonResponseUpsert deliveryAddressUpsertResponse = exchange.getIn().getBody(JsonResponseUpsert.class);

		if (deliveryAddressUpsertResponse == null || CollectionUtils.isEmpty(deliveryAddressUpsertResponse.getResponseItems()))
		{
			throw new RuntimeException("No deliveryJsonResponseUpsert found!");
		}

		final JsonMetasfreshId deliveryAddressId = extractDeliveryAddressMFId(deliveryAddressUpsertResponse);

		exchange.setProperty(GetOrdersRouteConstants.ROUTE_PROPERTY_DELIVERY_ADDRESS_METASFRESH_ID, deliveryAddressId);
	}

	@Override
	public void process(final Exchange exchange)
	{
		final JsonResponseComposite bpartnerResponseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		final Order order = ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_CURRENT_ORDER, Order.class);
		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_ORG_CODE, String.class);
		final JsonMetasfreshId deliveryAddressId = ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_DELIVERY_ADDRESS_METASFRESH_ID, JsonMetasfreshId.class);

		final JsonOLCandCreateBulkRequest olCandCreateBulkRequest =
				buildJsonCandCreateBulkRequest(orgCode, order, deliveryAddressId, bpartnerResponseComposite);

		computeNextImportDate(exchange, order);

		exchange.getIn().setBody(olCandCreateBulkRequest);
	}

	@NonNull
	private JsonOLCandCreateBulkRequest buildJsonCandCreateBulkRequest(
			@NonNull final String orgCode,
			@NonNull final Order order,
			@NonNull final JsonMetasfreshId deliveryAddressId,
			@NonNull final JsonResponseComposite jsonResponseComposite)
	{
		final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandRequestBuilder = JsonOLCandCreateRequest.builder();
		olCandRequestBuilder
				.orgCode(orgCode)
				.externalHeaderId(order.getId())
				.bpartner(getBPartnerIdentifiers(jsonResponseComposite, deliveryAddressId))
				.dataSource(CommonAlbertaConstants.ALBERTA_DATA_INPUT_SOURCE)
				.poReference(CoalesceUtil.firstNotEmptyTrimmed(order.getSalesId(), order.getId()))
				.dateRequired(asJavaLocalDate(order.getDeliveryDate()));

		DataMapper.pharmacyToDropShipBPartner(order.getPharmacyId()).ifPresent(olCandRequestBuilder::dropShipBPartner);

		getBillToBPartner(jsonResponseComposite).ifPresent(olCandRequestBuilder::billBPartner);

		final JsonAlbertaOrderInfo.JsonAlbertaOrderInfoBuilder albertaOrderInfoBuilder = JsonAlbertaOrderInfo.builder();
		albertaOrderInfoBuilder
				.externalId(order.getId())
				.rootId(order.getRootId())
				.creationDate(asInstant(order.getCreationDate()))
				.startDate(asJavaLocalDate(order.getStartDate()))
				.endDate(asJavaLocalDate(order.getEndDate()))
				.dayOfDelivery(order.getDayOfDelivery())
				.nextDelivery(asJavaLocalDate(order.getNextDelivery()))
				.updated(asInstant(order.getUpdated()))

				.doctorBPartnerIdentifier(asExternalIdentifierOrNull(order.getDoctorId()))
				.pharmacyBPartnerIdentifier(asExternalIdentifierOrNull(order.getPharmacyId()))

				.isInitialCare(order.isIsInitialCare())
				.isSeriesOrder(order.isIsSeriesOrder())
				.isArchived(order.isArchived())

				.annotation(order.getAnnotation())

				.therapy(order.getTherapyId() != null ? String.valueOf(order.getTherapyId()) : null)
				.therapyTypes(extractTherapyTypes(order))

				.deliveryInformation(order.getDeliveryInformation())
				.deliveryNote(order.getDeliveryNote());

		return processLines(olCandRequestBuilder, albertaOrderInfoBuilder, order);
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBPartnerIdentifiers(
			@NonNull final JsonResponseComposite jsonResponseComposite,
			@NonNull final JsonMetasfreshId deliveryAddressId)
	{
		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(JsonMetasfreshId.toValueStr(jsonResponseComposite.getBpartner().getMetasfreshId()))
				.bPartnerLocationIdentifier(JsonMetasfreshId.toValueStr(deliveryAddressId))
				.build();
	}

	@Nullable
	private LocalDate asJavaLocalDate(@Nullable final org.threeten.bp.LocalDate localDate)
	{
		if (localDate == null)
		{
			return null;
		}

		return LocalDate.ofEpochDay(localDate.getLong(EPOCH_DAY));
	}

	@NonNull
	private JsonOLCandCreateBulkRequest processLines(
			@NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder requestBuilder,
			@NonNull final JsonAlbertaOrderInfo.JsonAlbertaOrderInfoBuilder albertaInfoBuilder,
			@NonNull final Order order)
	{
		if (CollectionUtils.isEmpty(order.getOrderedArticleLines()))
		{
			throw new RuntimeException("Missing OrderedArticleLines!");
		}

		final ImmutableList<JsonOLCandCreateRequest> jsonOLCandCreateRequestList = order.getOrderedArticleLines()
				.stream()
				.map(orderedArticle -> {
					if (StringUtils.isEmpty(orderedArticle.getArticleId()))
					{
						throw new RuntimeException("Missing articleId! OrderedArticleId: " + orderedArticle.getId());
					}

					final JsonAlbertaOrderLineInfo.JsonAlbertaOrderLineInfoBuilder albertaOrderLineInfoBuilder = JsonAlbertaOrderLineInfo.builder();

					if (orderedArticle.getDuration() != null)
					{
						albertaOrderLineInfoBuilder.durationAmount(orderedArticle.getDuration().getAmount());
						albertaOrderLineInfoBuilder.timePeriod(orderedArticle.getDuration().getTimePeriod());
					}

					albertaOrderLineInfoBuilder
							.externalId(orderedArticle.getId())
							.salesLineId(orderedArticle.getSalesLineId())
							.unit(orderedArticle.getUnit())
							.isRentalEquipment(orderedArticle.isIsRentalEquipment())
							.isPrivateSale(orderedArticle.isIsPrivateSale())
							.updated(asInstant(orderedArticle.getUpdated()));

					albertaInfoBuilder.jsonAlbertaOrderLineInfo(albertaOrderLineInfoBuilder.build());

					return requestBuilder
							.albertaOrderInfo(albertaInfoBuilder.build())
							.externalLineId(orderedArticle.getId())
							.productIdentifier(ExternalIdentifierFormat.formatExternalId(orderedArticle.getArticleId()))
							.qty(orderedArticle.getQuantity())
							.build();
				})
				.collect(ImmutableList.toImmutableList());

		return JsonOLCandCreateBulkRequest.builder()
				.requests(jsonOLCandCreateRequestList)
				.build();
	}

	@Nullable
	private Instant asInstant(@Nullable final OffsetDateTime offsetDateTime)
	{
		if (offsetDateTime == null)
		{
			return null;
		}

		return Instant.ofEpochSecond(offsetDateTime.toEpochSecond());
	}

	@Nullable
	private String asExternalIdentifierOrNull(@Nullable final String externalIdentifierValue)
	{
		if (StringUtils.isEmpty(externalIdentifierValue))
		{
			return null;
		}

		return ExternalIdentifierFormat.formatExternalId(externalIdentifierValue);
	}

	@Nullable
	private List<String> extractTherapyTypes(@NonNull final Order order)
	{
		if (order.getTherapyTypeIds() == null)
		{
			return null;
		}

		return order.getTherapyTypeIds().stream()
				.filter(Objects::nonNull)
				.map(String::valueOf)
				.collect(Collectors.toList());
	}

	@NonNull
	private static JsonMetasfreshId extractDeliveryAddressMFId(@NonNull final JsonResponseUpsert deliveryAddressUpsertResponse)
	{
		final JsonResponseUpsertItem responseUpsertItem = Check.singleElement(deliveryAddressUpsertResponse.getResponseItems());

		if (responseUpsertItem.getMetasfreshId() == null)
		{
			throw new RuntimeException("Delivery address wasn't successfully persisted! ExternalId: " + responseUpsertItem.getIdentifier());
		}

		return responseUpsertItem.getMetasfreshId();
	}

	private void computeNextImportDate(@NonNull final Exchange exchange, @NonNull final Order orderCandidate)
	{
		final Instant nextImportSinceDateCandidate = AlbertaUtil.asInstant(orderCandidate.getCreationDate());

		if (nextImportSinceDateCandidate == null)
		{
			return;
		}

		final NextImportSinceTimestamp currentNextImportSinceDate =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetOrdersRouteConstants.ROUTE_PROPERTY_UPDATED_AFTER, NextImportSinceTimestamp.class);

		if (nextImportSinceDateCandidate.isAfter(currentNextImportSinceDate.getDate()))
		{
			currentNextImportSinceDate.setDate(nextImportSinceDateCandidate);
		}
	}

	@NonNull
	private static Optional<JsonRequestBPartnerLocationAndContact> getBillToBPartner(@NonNull final JsonResponseComposite jsonResponseComposite)
	{
		final Optional<JsonResponseLocation> billingAddressLocation = jsonResponseComposite.getLocations()
				.stream()
				.filter(JsonResponseLocation::isBillTo)
				.findFirst();

		if (billingAddressLocation.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(JsonRequestBPartnerLocationAndContact.builder()
								   .bPartnerIdentifier(JsonMetasfreshId.toValueStr(jsonResponseComposite.getBpartner().getMetasfreshId()))
								   .bPartnerLocationIdentifier(JsonMetasfreshId.toValueStr(billingAddressLocation.get().getMetasfreshId()))
								   .build());
	}
}
