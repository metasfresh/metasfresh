package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.util.Check;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse.AvailabilityResponseBuilder;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.AvailabilityResponseItemBuilder;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ClientBase;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.MSV3ClientMultiException;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitDefektgrund;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
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

public class MSV3AvailiabilityClient extends MSV3ClientBase
{
	private static final String URL_SUFFIX_RETRIEVE_AVAILABILITY = "/verfuegbarkeitAnfragen";

	public MSV3AvailiabilityClient(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config)
	{
		super(connectionFactory, config);
	}

	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		try
		{
			return retrieveAvailability0(request);
		}
		catch (final Throwable t)
		{
			throw MSV3ClientMultiException.createAllItemsSameThrowable(request.getAvailabilityRequestItems(), t);
		}
	}

	private AvailabilityResponse retrieveAvailability0(@NonNull final AvailabilityRequest request)
	{
		final ObjectFactory objectFactory = getObjectFactory();

		// create request data
		final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne =//
				objectFactory.createVerfuegbarkeitsanfrageEinzelne();
		verfuegbarkeitsanfrageEinzelne.setId(UUID.randomUUID().toString());

		final MSV3AvailabilityTransaction availabilityTransaction = MSV3AvailabilityTransaction.builder()
				.vendorId(request.getVendorId())
				.verfuegbarkeitsanfrageEinzelne(verfuegbarkeitsanfrageEinzelne)
				.build();

		final List<Artikel> artikel = verfuegbarkeitsanfrageEinzelne.getArtikel();
		final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem = new HashMap<>();

		for (final AvailabilityRequestItem requestItem : request.getAvailabilityRequestItems())
		{
			final Artikel singleArtikel = createArtikelForRequestItem(requestItem.getProductAndQuantity());
			productIdentifier2requestItem.put(singleArtikel.getPzn(), requestItem);
			artikel.add(singleArtikel);

			availabilityTransaction.putContextInfo(singleArtikel, MSV3ArtikelContextInfo.forRequestItem(requestItem));
		}

		try
		{
			// make the webservice call
			final VerfuegbarkeitAnfragenResponse response = makeAvailabilityWebserviceCall(verfuegbarkeitsanfrageEinzelne);

			availabilityTransaction.setVerfuegbarkeitsanfrageEinzelneAntwort(response.getReturn());

			// process and return the results
			final AvailabilityResponseBuilder responseBuilder = prepareAvailabilityResponse(
					response,
					productIdentifier2requestItem,
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

	private Artikel createArtikelForRequestItem(@NonNull final ProductAndQuantity requestItem)
	{
		final Artikel singleArtikel = getObjectFactory().createVerfuegbarkeitsanfrageEinzelneArtikel();

		singleArtikel.setMenge(MSV3Util.extractMenge(requestItem));
		singleArtikel.setPzn(MSV3Util.extractPZN(requestItem));
		singleArtikel.setBedarf("unspezifisch");
		return singleArtikel;
	}

	private VerfuegbarkeitAnfragenResponse makeAvailabilityWebserviceCall(@NonNull final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne)
	{
		final VerfuegbarkeitAnfragen verfuegbarkeitAnfragen = getObjectFactory().createVerfuegbarkeitAnfragen();

		verfuegbarkeitAnfragen.setVerfuegbarkeitsanfrage(verfuegbarkeitsanfrageEinzelne);
		verfuegbarkeitAnfragen.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());

		final VerfuegbarkeitAnfragenResponse response = sendAndReceive(
				getObjectFactory().createVerfuegbarkeitAnfragen(verfuegbarkeitAnfragen),
				VerfuegbarkeitAnfragenResponse.class);

		return response;
	}

	/**
	 * @param verfuegbarkeitAnfragenResponse
	 * @param productIdentifier2requestItem
	 * @param availabilityTransaction passed so that its {@link MSV3AvailabilityTransaction#putContextInfo(VerfuegbarkeitsantwortArtikel, MSV3ArtikelContextInfo)} can be called.
	 */
	private AvailabilityResponseBuilder prepareAvailabilityResponse(
			@NonNull final VerfuegbarkeitAnfragenResponse verfuegbarkeitAnfragenResponse,
			@NonNull final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem,
			@NonNull final MSV3AvailabilityTransaction availabilityTransaction)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort //
				= verfuegbarkeitAnfragenResponse.getReturn();

		final AvailabilityResponseBuilder responseBuilder = AvailabilityResponse.builder();

		for (final VerfuegbarkeitsantwortArtikel singleArtikel : verfuegbarkeitsanfrageEinzelneAntwort.getArtikel())
		{
			final AvailabilityRequestItem availabilityRequestItem = productIdentifier2requestItem.get(singleArtikel.getAnfragePzn());
			availabilityTransaction.putContextInfo(singleArtikel, MSV3ArtikelContextInfo.forRequestItem(availabilityRequestItem));

			for (final VerfuegbarkeitAnteil singleAnteil : singleArtikel.getAnteile())
			{
				final AvailabilityResponseItem availabilityResponseItem = prepareResponseItemBuilder(
						productIdentifier2requestItem,
						singleArtikel,
						singleAnteil).build();

				responseBuilder.availabilityResponseItem(availabilityResponseItem);
			}
		}
		return responseBuilder;
	}

	private AvailabilityResponseItemBuilder prepareResponseItemBuilder(
			@NonNull final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem,
			@NonNull final VerfuegbarkeitsantwortArtikel singleArtikel,
			@NonNull final VerfuegbarkeitAnteil singleAnteil)
	{
		// get the data to pass to the response item's builder
		final AvailabilityRequestItem correspondingRequestItem = productIdentifier2requestItem.get(singleArtikel.getAnfragePzn());

		final String productIdentifier = Long.toString(singleArtikel.getAnfragePzn());

		final BigDecimal availableQuantity = new BigDecimal(singleAnteil.getMenge());

		final XMLGregorianCalendar lieferzeitpunkt = singleAnteil.getLieferzeitpunkt();

		// if we can't get a datePromised, then we assume the item as not available
		//final Type type = VerfuegbarkeitRueckmeldungTyp.NICHT_LIEFERBAR.equals(singleAnteil.getTyp()) ? Type.NOT_AVAILABLE : Type.AVAILABLE;
		final Type type = lieferzeitpunkt == null ? Type.NOT_AVAILABLE : Type.AVAILABLE;

		final StringBuilder availabilityText = createAvailabilityText(singleAnteil);

		// create & return the response item
		final AvailabilityResponseItemBuilder availabilityResponseItemBuilder = AvailabilityResponseItem.builder();
		availabilityResponseItemBuilder
				.correspondingRequestItem(correspondingRequestItem)
				.productIdentifier(productIdentifier)
				.availableQuantity(availableQuantity)
				.datePromised(MSV3Util.toDateOrNull(lieferzeitpunkt))
				.type(type)
				.availabilityText(availabilityText.toString());
		return availabilityResponseItemBuilder;
	}

	private StringBuilder createAvailabilityText(@NonNull final VerfuegbarkeitAnteil singleAnteil)
	{
		final StringBuilder availabilityText = new StringBuilder();
		availabilityText.append(singleAnteil.getTyp());

		final boolean hasTour = !Check.isEmpty(singleAnteil.getTour(), true);
		final VerfuegbarkeitDefektgrund grund = singleAnteil.getGrund();
		final boolean hasDefectReason = !VerfuegbarkeitDefektgrund.KEINE_ANGABE.equals(grund);

		if (hasTour || hasDefectReason)
		{
			availabilityText.append(" ( ");
		}

		if (hasTour)
		{
			availabilityText.append("Tour " + singleAnteil.getTour());
		}
		if (hasDefectReason)
		{
			availabilityText.append("Grund " + grund.toString());
		}

		if (hasTour || hasDefectReason)
		{
			availabilityText.append(" )");
		}

		return availabilityText;
	}

	@Override
	public String getUrlSuffix()
	{
		return URL_SUFFIX_RETRIEVE_AVAILABILITY;
	}
}
