package de.metas.vertical.pharma.vendor.gateway.mvs3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;
import org.springframework.ws.client.core.WebServiceTemplate;

import de.metas.vendor.gateway.api.model.AvailabilityRequest;
import de.metas.vendor.gateway.api.model.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.model.AvailabilityResponse;
import de.metas.vendor.gateway.api.model.AvailabilityResponse.AvailabilityResponseBuilder;
import de.metas.vendor.gateway.api.model.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.model.AvailabilityResponseItem.Type;
import de.metas.vendor.gateway.msv3.schema.Msv3FaultInfo;
import de.metas.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vendor.gateway.msv3.schema.VerbindungTesten;
import de.metas.vendor.gateway.msv3.schema.VerbindungTestenResponse;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnteil;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitDefektgrund;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitRueckmeldungTyp;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsantwortArtikel;
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

public class MSV3Client
{
	private static final String URL_SUFFIX_RETRIEVE_AVAILABILITY = "/verfuegbarkeitAnfragen";

	private static final String URL_SUFFIX_TEST_CONNECTION = "/verbindungTesten";

	private static final int MSV3_MAX_QUANTITY_99999 = 99999;

	private static final ExtendedMemorizingSupplier<String> CLIENT_SOFTWARE_IDENTIFIER = ExtendedMemorizingSupplier
			.of(() -> retrieveSoftwareIndentifier());

	private final MSV3ClientConfig config;
	private final WebServiceTemplate webServiceTemplate;
	private final ObjectFactory objectFactory;

	public MSV3Client(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config)
	{
		this.config = config;
		objectFactory = new ObjectFactory();
		webServiceTemplate = connectionFactory.createWebServiceTemplate(config);
	}

	public String testConnection()
	{
		final VerbindungTesten verbindungTesten = objectFactory.createVerbindungTesten();
		verbindungTesten.setClientSoftwareKennung(CLIENT_SOFTWARE_IDENTIFIER.get());

		final JAXBElement<?> responseElement = //
				(JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(
						config.getBaseUrl() + URL_SUFFIX_TEST_CONNECTION,
						objectFactory.createVerbindungTesten(verbindungTesten));

		final Object value = responseElement.getValue();
		if (value instanceof VerbindungTestenResponse)
		{
			return "ok";
		}

		throw new AdempiereException(value.toString()).appendParametersToMessage()
				.setParameter("config", config);
	}

	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		// create request data
		final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne =//
				objectFactory.createVerfuegbarkeitsanfrageEinzelne();
		verfuegbarkeitsanfrageEinzelne.setId(UUID.randomUUID().toString());

		final List<Artikel> artikel = verfuegbarkeitsanfrageEinzelne.getArtikel();
		final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem = new HashMap<>();

		for (final AvailabilityRequestItem requestItem : request.getAvailabilityRequestItems())
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

	private Artikel createArtikelForRequestItem(@NonNull final AvailabilityRequestItem requestItem)
	{
		final Artikel singleArtikel = objectFactory.createVerfuegbarkeitsanfrageEinzelneArtikel();

		final int requiredQuantity = convertAndVerifyRequiredQuantity(requestItem);
		singleArtikel.setMenge(requiredQuantity);

		final String productIdentifier = requestItem.getProductIdentifier();
		singleArtikel.setPzn(Long.parseLong(productIdentifier));
		singleArtikel.setBedarf("unspezifisch");
		return singleArtikel;
	}

	private int convertAndVerifyRequiredQuantity(@NonNull final AvailabilityRequestItem requestItem)
	{
		final int intValue = requestItem.getRequiredQuantity().setScale(0, RoundingMode.UP).intValue();
		Check.errorIf(intValue > MSV3_MAX_QUANTITY_99999,
				"The MSV3 standard allows a maximum quantity of {}; requestItem={}",
				MSV3_MAX_QUANTITY_99999, requestItem);
		return intValue;
	}

	private Object makeAvailabilityWebserviceCall(@NonNull final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne)
	{
		final VerfuegbarkeitAnfragen verfuegbarkeitAnfragen = new VerfuegbarkeitAnfragen();
		verfuegbarkeitAnfragen.setVerfuegbarkeitsanfrage(verfuegbarkeitsanfrageEinzelne);
		verfuegbarkeitAnfragen.setClientSoftwareKennung(CLIENT_SOFTWARE_IDENTIFIER.get());

		final Object response = makeWebserviceCall(
				objectFactory.createVerfuegbarkeitAnfragen(verfuegbarkeitAnfragen),
				URL_SUFFIX_RETRIEVE_AVAILABILITY);

		return response;
	}

	private Object makeWebserviceCall(
			@NonNull final Object requestPayload,
			@NonNull final String urlSuffix)
	{
		final JAXBElement<?> responseElement = //
				(JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(
						config.getBaseUrl() + urlSuffix,
						requestPayload);

		final Object response = responseElement.getValue();
		return response;
	}

	private AvailabilityResponseBuilder prepareAvailabilityResponse(
			@NonNull final VerfuegbarkeitAnfragenResponse verfuegbarkeitAnfragenResponse,
			@NonNull final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem)
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
			@NonNull final Map<Long, AvailabilityRequestItem> productIdentifier2requestItem,
			@NonNull final VerfuegbarkeitsantwortArtikel singleArtikel,
			@NonNull final VerfuegbarkeitAnteil singleAnteil)
	{
		// get the data to pass to the response item's builder
		final AvailabilityRequestItem correspondingRequestItem = productIdentifier2requestItem.get(singleArtikel.getAnfragePzn());

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

	private static String retrieveSoftwareIndentifier()
	{
		try
		{
			final I_AD_System adSystem = Services.get(ISystemBL.class).get(Env.getCtx());
			return "metasfresh-" + adSystem.getDBVersion();
		}
		catch (final RuntimeException e)
		{
			return "metasfresh-<unable to retrieve version!>";
		}
	}
}
