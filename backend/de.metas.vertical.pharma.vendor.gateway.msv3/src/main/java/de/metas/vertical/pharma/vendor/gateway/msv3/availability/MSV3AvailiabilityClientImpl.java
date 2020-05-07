package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.metas.util.Check;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse.AvailabilityResponseBuilder;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.AvailabilityResponseItemBuilder;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.RequirementType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityClientJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3Client;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientMultiException;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MSV3AvailiabilityClientImpl implements MSV3AvailiabilityClient
{
	private static final String URL_SUFFIX_RETRIEVE_AVAILABILITY = "/verfuegbarkeitAnfragen";

	private final MSV3Client client;
	private final StockAvailabilityClientJAXBConverters jaxbConverters;

	@Builder
	private MSV3AvailiabilityClientImpl(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final StockAvailabilityClientJAXBConverters jaxbConverters)
	{
		client = MSV3Client.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.urlPrefix(URL_SUFFIX_RETRIEVE_AVAILABILITY)
				.faultInfoExtractor(jaxbConverters::extractFaultInfoOrNull)
				.build();

		this.jaxbConverters = jaxbConverters;

	}

	@Override
	public AvailabilityResponse retrieveAvailability(final AvailabilityRequest request)
	{
		try
		{
			return retrieveAvailability0(request);
		}
		catch (final Throwable ex)
		{
			throw Msv3ClientMultiException.createAllItemsSameThrowable(request.getAvailabilityRequestItems(), ex);
		}
	}

	private AvailabilityResponse retrieveAvailability0(@NonNull final AvailabilityRequest request)
	{
		final List<StockAvailabilityQueryItem> queryItems = new ArrayList<>();
		final Map<PZN, AvailabilityRequestItem> requestItemsByPZN = new HashMap<>();
		final Map<StockAvailabilityQueryItem, MSV3ArtikelContextInfo> contextInfosByQueryItem = new IdentityHashMap<>();
		for (final AvailabilityRequestItem requestItem : request.getAvailabilityRequestItems())
		{
			final StockAvailabilityQueryItem queryItem = createQueryItem(requestItem.getProductAndQuantity());
			requestItemsByPZN.put(queryItem.getPzn(), requestItem);
			queryItems.add(queryItem);

			contextInfosByQueryItem.put(queryItem, MSV3ArtikelContextInfo.forRequestItem(requestItem));
		}

		final BPartnerId bpartnerId = BPartnerId.of(request.getVendorId());

		final StockAvailabilityQuery query = StockAvailabilityQuery.builder()
				.id(UUID.randomUUID().toString())
				.bpartner(bpartnerId)
				.items(queryItems)
				.build();

		final MSV3AvailabilityTransaction availabilityTransaction = MSV3AvailabilityTransaction.builder()
				.vendorId(bpartnerId)
				.query(query)
				.build();
		availabilityTransaction.putContextInfos(contextInfosByQueryItem);

		try
		{
			// make the webservice call
			final StockAvailabilityResponse response = makeAvailabilityWebserviceCall(query);

			availabilityTransaction.setResponse(response);

			// process and return the results
			final AvailabilityResponseBuilder responseBuilder = prepareAvailabilityResponse(
					response,
					requestItemsByPZN,
					availabilityTransaction);

			return responseBuilder.originalRequest(request).build();
		}
		catch (final Msv3ClientException e)
		{
			availabilityTransaction.setFaultInfo(e.getMsv3FaultInfo());
			throw e;
		}
		catch (final Exception e)
		{
			availabilityTransaction.setOtherException(e);
			throw e;
		}
		finally
		{
			availabilityTransaction.store();
		}
	}

	private StockAvailabilityResponse makeAvailabilityWebserviceCall(@NonNull final StockAvailabilityQuery query)
	{
		final Object soapResponse = client.sendAndReceive(
				jaxbConverters.encodeRequest(query, client.getClientSoftwareId()),
				jaxbConverters.getResponseClass());
		return jaxbConverters.decodeResponse(soapResponse);

	}

	private static StockAvailabilityQueryItem createQueryItem(@NonNull final ProductAndQuantity requestItem)
	{
		return StockAvailabilityQueryItem.builder()
				.qtyRequired(Quantity.of(requestItem.getQuantity()))
				.pzn(PZN.of(requestItem.getProductIdentifier()))
				.requirementType(RequirementType.NON_SPECIFIC)
				.build();
	}

	private static AvailabilityResponseBuilder prepareAvailabilityResponse(
			@NonNull final StockAvailabilityResponse response,
			@NonNull final Map<PZN, AvailabilityRequestItem> requestItemsByPZN,
			@NonNull final MSV3AvailabilityTransaction availabilityTransaction)
	{
		final AvailabilityResponseBuilder responseBuilder = AvailabilityResponse.builder();

		for (final StockAvailabilityResponseItem responseItem : response.getItems())
		{
			final AvailabilityRequestItem requestItem = requestItemsByPZN.get(responseItem.getPzn());
			availabilityTransaction.putContextInfo(responseItem, MSV3ArtikelContextInfo.forRequestItem(requestItem));

			for (final StockAvailabilityResponseItemPart responseItemPart : responseItem.getParts())
			{
				final AvailabilityResponseItem availabilityResponseItem = prepareResponseItemBuilder(
						requestItemsByPZN,
						responseItem,
						responseItemPart)
								.build();

				responseBuilder.availabilityResponseItem(availabilityResponseItem);
			}
		}
		return responseBuilder;
	}

	private static AvailabilityResponseItemBuilder prepareResponseItemBuilder(
			@NonNull final Map<PZN, AvailabilityRequestItem> requestItemsByPZN,
			@NonNull final StockAvailabilityResponseItem responseItem,
			@NonNull final StockAvailabilityResponseItemPart responseItemPart)
	{
		// get the data to pass to the response item's builder
		final AvailabilityRequestItem correspondingRequestItem = requestItemsByPZN.get(responseItem.getPzn());

		final ZonedDateTime datePromised = responseItemPart.getDeliveryDate();

		// if we can't get a datePromised, then we assume the item as not available
		// final Type type = VerfuegbarkeitRueckmeldungTyp.NICHT_LIEFERBAR.equals(singleAnteil.getTyp()) ? Type.NOT_AVAILABLE : Type.AVAILABLE;
		final Type type = datePromised == null ? Type.NOT_AVAILABLE : Type.AVAILABLE;

		final String availabilityText = createAvailabilityText(responseItemPart);

		// create & return the response item
		return AvailabilityResponseItem.builder()
				.correspondingRequestItem(correspondingRequestItem)
				.productIdentifier(responseItem.getPzn().getValueAsString())
				.availableQuantity(responseItemPart.getQty().getValueAsBigDecimal())
				.datePromised(datePromised)
				.type(type)
				.availabilityText(availabilityText);
	}

	private static String createAvailabilityText(@NonNull final StockAvailabilityResponseItemPart reponseItemPart)
	{
		final StringBuilder availabilityText = new StringBuilder();
		availabilityText.append(reponseItemPart.getType());

		final boolean hasTour = !Check.isEmpty(reponseItemPart.getTour(), true);
		final StockAvailabilitySubstitutionReason reason = reponseItemPart.getReason();
		final boolean hasDefectReason = !StockAvailabilitySubstitutionReason.NO_INFO.equals(reason);

		if (hasTour || hasDefectReason)
		{
			availabilityText.append(" ( ");
		}

		if (hasTour)
		{
			availabilityText.append("Tour " + reponseItemPart.getTour());
		}
		if (hasDefectReason)
		{
			availabilityText.append("Grund " + reason);
		}

		if (hasTour || hasDefectReason)
		{
			availabilityText.append(" )");
		}

		return availabilityText.toString();
	}
}
