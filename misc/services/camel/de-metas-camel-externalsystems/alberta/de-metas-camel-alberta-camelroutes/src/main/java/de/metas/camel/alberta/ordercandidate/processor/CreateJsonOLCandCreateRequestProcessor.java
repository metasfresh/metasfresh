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

package de.metas.camel.alberta.ordercandidate.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.alberta.ProcessorHelper;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
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

import static de.metas.camel.alberta.common.CommonAlbertaConstants.ALBERTA_DATA_INPUT_SOURCE;
import static de.metas.camel.alberta.common.CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM;
import static de.metas.camel.alberta.common.CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX;
import static de.metas.camel.alberta.ordercandidate.GetOrdersRouteConstants.ROUTE_PROPERTY_ORG_CODE;
import static org.threeten.bp.temporal.ChronoField.EPOCH_DAY;

public class CreateJsonOLCandCreateRequestProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final Order order = exchange.getIn().getBody(Order.class);

		if (order == null)
		{
			throw new RuntimeException("Empty body!");
		}

		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ORG_CODE, String.class);

		final JsonOLCandCreateBulkRequest olCandCreateBulkRequest =
				buildJsonCandCreateBulkRequest(orgCode, order);

		exchange.getIn().setBody(olCandCreateBulkRequest);
	}

	@NonNull
	private JsonOLCandCreateBulkRequest buildJsonCandCreateBulkRequest(@NonNull final String orgCode, @NonNull final Order order)
	{
		final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandRequestBuilder = JsonOLCandCreateRequest.builder();
		olCandRequestBuilder
				.orgCode(orgCode)
				.externalHeaderId(order.getId())
				.bpartner(getBPartnerIdentifiers(order))
				.dataSource(ALBERTA_DATA_INPUT_SOURCE)
				.poReference(order.getSalesId())
				.dateRequired(asJavaLocalDate(order.getDeliveryDate()));

		final JsonAlbertaOrderInfo.JsonAlbertaOrderInfoBuilder albertaOrderInfoBuilder = JsonAlbertaOrderInfo.builder();
		albertaOrderInfoBuilder
				.rootId(order.getRootId())

				.creationDate(asJavaLocalDate(order.getCreationDate()))
				.startDate(asJavaLocalDate(order.getStartDate()))
				.endDate(asJavaLocalDate(order.getEndDate()))
				.nextDelivery(asJavaLocalDate(order.getNextDelivery()))
				.updated(asInstant(order.getUpdated()))

				.doctorBPartnerIdentifier(asExternalIdentifierOrNull(order.getDoctorId()))
				.pharmacyBPartnerIdentifier(asExternalIdentifierOrNull(order.getPharmacyId()))

				.isInitialCare(order.isIsInitialCare())
				.isSeriesOrder(order.isIsSeriesOrder())
				.isArchived(order.isArchived())

				.annotation(order.getAnnotation());

		return processLines(olCandRequestBuilder, albertaOrderInfoBuilder, order);
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBPartnerIdentifiers(@NonNull final Order order)
	{
		if (order.getPatientId() == null)
		{
			throw new RuntimeException("Missing mandatory data! Order: " + order);
		}

		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + order.getPatientId())
				.bPartnerLocationIdentifier(EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + order.getPatientId())
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

					if (orderedArticle.getDuration() != null)
					{
						albertaInfoBuilder.durationAmount(orderedArticle.getDuration().getAmount());
						albertaInfoBuilder.timePeriod(orderedArticle.getDuration().getTimePeriod());
					}

					final JsonAlbertaOrderInfo albertaOrderInfo = albertaInfoBuilder
							.salesLineId(orderedArticle.getSalesLineId())
							.unit(orderedArticle.getUnit())
							.isRentalEquipment(orderedArticle.isIsRentalEquipment())
							.isPrivateSale(orderedArticle.isIsPrivateSale())
							.updated(asInstant(orderedArticle.getUpdated()))
							.build();

					return requestBuilder
							.albertaOrderInfo(albertaOrderInfo)
							.externalLineId(orderedArticle.getId())
							.productIdentifier(EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + orderedArticle.getArticleId())
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

		return EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + externalIdentifierValue;
	}
}
