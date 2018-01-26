package de.metas.vertical.pharma.vendor.gateway.mvs3.availability;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse.AvailabilityResponseBuilder;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientBase;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientException;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitAnfragen;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitDefektgrund;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitRueckmeldungTyp;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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
		final ObjectFactory objectFactory = getObjectFactory();

		// create request data
		final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne =//
				objectFactory.createVerfuegbarkeitsanfrageEinzelne();
		verfuegbarkeitsanfrageEinzelne.setId(UUID.randomUUID().toString());

		final List<Artikel> artikel = verfuegbarkeitsanfrageEinzelne.getArtikel();
		final Map<Long, ProductAndQuantity> productIdentifier2requestItem = new HashMap<>();

		for (final ProductAndQuantity requestItem : request.getAvailabilityRequestItems())
		{
			final Artikel singleArtikel = createArtikelForRequestItem(requestItem);
			productIdentifier2requestItem.put(singleArtikel.getPzn(), requestItem);
			artikel.add(singleArtikel);
		}

		// make the webservice call
		final Object response;
		try
		{
			response = makeAvailabilityWebserviceCall(verfuegbarkeitsanfrageEinzelne);
		}
		catch (final Throwable t)
		{
			throw MSV3ClientException.createAllItemsSameThrowable(request.getAvailabilityRequestItems(), t);
		}

		// process and return the results
		if (response instanceof VerfuegbarkeitAnfragenResponse)
		{
			final AvailabilityResponseBuilder responseBuilder = prepareAvailabilityResponse(
					(VerfuegbarkeitAnfragenResponse)response,
					productIdentifier2requestItem);

			return responseBuilder
					.originalRequest(request).build();
		}
		else if (response instanceof Msv3FaultInfo)
		{
			final Msv3FaultInfo msv3FaultInfo = (Msv3FaultInfo)response;
			throw MSV3ClientException
					.createAllItemsSameFaultInfo(request.getAvailabilityRequestItems(), msv3FaultInfo);
		}
		else
		{
			throw new AdempiereException(response.toString());
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

	private Object makeAvailabilityWebserviceCall(@NonNull final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne)
	{
		final VerfuegbarkeitAnfragen verfuegbarkeitAnfragen = getObjectFactory().createVerfuegbarkeitAnfragen();

		verfuegbarkeitAnfragen.setVerfuegbarkeitsanfrage(verfuegbarkeitsanfrageEinzelne);
		verfuegbarkeitAnfragen.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());

		final Object response = sendMessage(
				getObjectFactory().createVerfuegbarkeitAnfragen(verfuegbarkeitAnfragen));

		return response;
	}

	private AvailabilityResponseBuilder prepareAvailabilityResponse(
			@NonNull final VerfuegbarkeitAnfragenResponse verfuegbarkeitAnfragenResponse,
			@NonNull final Map<Long, ProductAndQuantity> productIdentifier2requestItem)
	{
		final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort //
				= verfuegbarkeitAnfragenResponse.getReturn();

		final AvailabilityResponseBuilder responseBuilder = AvailabilityResponse.builder();

		for (final VerfuegbarkeitsantwortArtikel singleArtikel : verfuegbarkeitsanfrageEinzelneAntwort.getArtikel())
		{
			for (final VerfuegbarkeitAnteil singleAnteil : singleArtikel.getAnteile())
			{
				final AvailabilityResponseItem availabilityResponseItem = createAvailabilityResponseItem(
						productIdentifier2requestItem,
						singleArtikel,
						singleAnteil);

				responseBuilder.availabilityResponseItem(availabilityResponseItem);
			}
		}
		return responseBuilder;
	}

	private AvailabilityResponseItem createAvailabilityResponseItem(
			@NonNull final Map<Long, ProductAndQuantity> productIdentifier2requestItem,
			@NonNull final VerfuegbarkeitsantwortArtikel singleArtikel,
			@NonNull final VerfuegbarkeitAnteil singleAnteil)
	{
		// get the data to pass to the response item's builder
		final ProductAndQuantity correspondingRequestItem = productIdentifier2requestItem.get(singleArtikel.getAnfragePzn());

		final String productIdentifier = Long.toString(singleArtikel.getAnfragePzn());

		final BigDecimal availableQuantity = new BigDecimal(singleAnteil.getMenge());

		final XMLGregorianCalendar lieferzeitpunkt = singleAnteil.getLieferzeitpunkt();

		final Date datePromised = lieferzeitpunkt == null ? null : lieferzeitpunkt.toGregorianCalendar().getTime();

		final Type type = VerfuegbarkeitRueckmeldungTyp.NICHT_LIEFERBAR.equals(singleAnteil.getTyp()) ? Type.NOT_AVAILABLE : Type.AVAILABLE;

		final StringBuilder availabilityText = createAvailabilityText(singleAnteil);

		// create & return the response item
		final AvailabilityResponseItem availabilityResponseItem = AvailabilityResponseItem.builder()
				.correspondingRequestItem(correspondingRequestItem)
				.productIdentifier(productIdentifier)
				.availableQuantity(availableQuantity)
				.datePromised(datePromised)
				.type(type)
				.availabilityText(availabilityText.toString())
				.build();
		return availabilityResponseItem;
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
