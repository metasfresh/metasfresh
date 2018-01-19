package de.metas.vertical.pharma.vendor.gateway.mvs3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import javax.xml.bind.JAXBElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import de.metas.vendor.gateway.api.model.AvailabilityRequest;
import de.metas.vendor.gateway.api.model.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.model.AvailabilityResponse;
import de.metas.vendor.gateway.api.model.AvailabilityResponse.AvailabilityResponseBuilder;
import de.metas.vendor.gateway.api.model.AvailabilityResponseItem;
import de.metas.vendor.gateway.msv3.schema.Msv3FaultInfo;
import de.metas.vendor.gateway.msv3.schema.ObjectFactory;
import de.metas.vendor.gateway.msv3.schema.VerbindungTesten;
import de.metas.vendor.gateway.msv3.schema.VerbindungTestenResponse;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragen;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnfragenResponse;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitAnteil;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitRueckmeldungTyp;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
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
	private static final int MSV3_MAX_QUANTITY_99999 = 99999;
	private final MSV3ClientConfig config;
	private final WebServiceTemplate webServiceTemplate;
	private final ObjectFactory objectFactory;

	private static final ExtendedMemorizingSupplier<String> CLIENT_SOFTWARE_IDENTIFIER = ExtendedMemorizingSupplier
			.of(() -> {
				final I_AD_System adSystem = Services.get(ISystemBL.class).get(Env.getCtx());
				return "metasfresh-" + adSystem.getDBVersion();
			});

	public MSV3Client(@NonNull final MSV3ClientConfig config)
	{
		this.config = config;
		this.objectFactory = new ObjectFactory();
		this.webServiceTemplate = createWebServiceTemplate(config);
	}

	private static WebServiceTemplate createWebServiceTemplate(@NonNull final MSV3ClientConfig config)
	{
		final HttpComponentsMessageSender messageSender = //
				createMessageSender(config.getAuthUsername(), config.getAuthPassword());

		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(de.metas.vendor.gateway.msv3.schema.ObjectFactory.class.getPackage().getName());

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		try
		{
			// Using the default messageFactory, we end up with SOAP 1.1. and the following error on the server side
			// Content-Type: text/xml; charset=utf-8 Supported ones are: [application/soap+xml]
			// ..which results in a http error Unsupported Media Type [415]
			webServiceTemplate.setMessageFactory(new SaajSoapMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL)));
		}
		catch (SOAPException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return webServiceTemplate;
	}

	private static HttpComponentsMessageSender createMessageSender(
			@NonNull final String authUsername,
			@NonNull final String authPassword)
	{
		final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(authUsername, authPassword);

		final HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setCredentials(credentials);
		try
		{
			messageSender.afterPropertiesSet(); // to make sure credentials are set to HttpClient
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		return messageSender;
	}

	public String testConnection()
	{

		final VerbindungTesten verbindungTesten = objectFactory.createVerbindungTesten();
		verbindungTesten.setClientSoftwareKennung(CLIENT_SOFTWARE_IDENTIFIER.get());

		final JAXBElement<?> responseElement = //
				(JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(
						config.getBaseUrl() + "/verbindungTesten",
						objectFactory.createVerbindungTesten(verbindungTesten));

		final Object value = responseElement.getValue();
		if (value instanceof VerbindungTestenResponse)
		{
			return "ok";
		}

		throw new AdempiereException(value.toString());
	}

	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		final AvailabilityResponseBuilder responseBuilder = AvailabilityResponse.builder()
				.originalRequest(request);

		final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne =//
				objectFactory.createVerfuegbarkeitsanfrageEinzelne();
		verfuegbarkeitsanfrageEinzelne.setId(UUID.randomUUID().toString());

		final List<Artikel> artikel = verfuegbarkeitsanfrageEinzelne.getArtikel();

		request.getAvailabilityRequestItems().forEach(requestItem -> {

			final Artikel singleArtikel = objectFactory.createVerfuegbarkeitsanfrageEinzelneArtikel();

			final int requiredQuantity = convertAndVerifyRequiredQuantity(requestItem);
			singleArtikel.setMenge(requiredQuantity);

			singleArtikel.setPzn(Long.parseLong(requestItem.getProductIdentifier()));
			singleArtikel.setBedarf("unspezifisch");

			artikel.add(singleArtikel);
		});

		final VerfuegbarkeitAnfragen verfuegbarkeitAnfragen = new VerfuegbarkeitAnfragen();
		verfuegbarkeitAnfragen.setVerfuegbarkeitsanfrage(verfuegbarkeitsanfrageEinzelne);
		verfuegbarkeitAnfragen.setClientSoftwareKennung(CLIENT_SOFTWARE_IDENTIFIER.get());

		final JAXBElement<?> responseElement = //
				(JAXBElement<?>)webServiceTemplate.marshalSendAndReceive(
						config.getBaseUrl() + "/verfuegbarkeitAnfragen",
						objectFactory.createVerfuegbarkeitAnfragen(verfuegbarkeitAnfragen));

		final Object response = responseElement.getValue();
		if (response instanceof VerfuegbarkeitAnfragenResponse)
		{
			final VerfuegbarkeitAnfragenResponse verfuegbarkeitAnfragenResponse //
					= (VerfuegbarkeitAnfragenResponse)response;
			final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort //
					= verfuegbarkeitAnfragenResponse.getReturn();

			final Predicate<VerfuegbarkeitAnteil> p = singleAnteil -> VerfuegbarkeitRueckmeldungTyp.NORMAL.equals(singleAnteil.getTyp());

			verfuegbarkeitsanfrageEinzelneAntwort.getArtikel()
					.forEach(singleArtikel -> {

						singleArtikel.getAnteile().stream()
								.filter(p)
								.forEach(singleAnteil -> {

									responseBuilder.availabilityResponseItem(
											new AvailabilityResponseItem(
													Long.toString(singleArtikel.getAnfragePzn()),
													new BigDecimal(singleAnteil.getMenge())));
								});

					});

			return responseBuilder.build();
		}
		else if (response instanceof Msv3FaultInfo)
		{
			throw new AdempiereException(response.toString());
		}
		else
		{
			throw new AdempiereException(response.toString());
		}
	}

	private int convertAndVerifyRequiredQuantity(@NonNull final AvailabilityRequestItem requestItem)
	{
		final int intValue = requestItem.getRequiredQuantity().setScale(0, RoundingMode.UP).intValue();
		Check.errorIf(intValue > MSV3_MAX_QUANTITY_99999,
				"The MSV3 standard allows a maximum quantity of {}; requestItem={}",
				MSV3_MAX_QUANTITY_99999, requestItem);
		return intValue;
	}
}
