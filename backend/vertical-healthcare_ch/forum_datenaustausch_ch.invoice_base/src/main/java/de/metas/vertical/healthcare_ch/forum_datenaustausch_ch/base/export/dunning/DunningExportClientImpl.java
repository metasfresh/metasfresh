package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.dunning;

import static de.metas.util.Check.assumeNotNull;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import de.metas.dunning_gateway.spi.DunningExportClient;
import de.metas.dunning_gateway.spi.model.DunningAttachment;
import de.metas.dunning_gateway.spi.model.DunningExportResult;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.dunning_gateway.spi.model.MetasfreshVersion;
import de.metas.dunning_gateway.spi.model.Money;
import de.metas.util.Check;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.Types.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlPayload.PayloadMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.ProcessingMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.RequestMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlBody.BodyMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.XmlReminder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance.BalanceMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog.PrologMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.prolog.XmlSoftware.SoftwareMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.TransportMod;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

public class DunningExportClientImpl implements DunningExportClient
{
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;
	private final CrossVersionRequestConverter exportConverter;
	private final XmlMode exportFileMode;
	private final String exportFileFromEAN;
	private final String exportFileViaEAN;

	public DunningExportClientImpl(
			@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry,
			@NonNull final ExportConfig exportConfig)
	{
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
		this.exportConverter = crossVersionServiceRegistry.getRequestConverterForSimpleVersionName(exportConfig.getXmlVersion());
		this.exportFileMode = assumeNotNull(exportConfig.getMode(), "The given exportConfig needs to have a non-null mode; exportconfig={}", exportConfig);
		this.exportFileFromEAN = exportConfig.getFromEAN();
		this.exportFileViaEAN = exportConfig.getViaEAN();
	}

	@Override
	public boolean canExport(@NonNull final DunningToExport dunningToExport)
	{
		final ImmutableMultimap<CrossVersionRequestConverter, DunningAttachment> //
		converters = extractConverters(dunningToExport.getDunningAttachments());

		// TODO check if
		// * the invoice's language and currency is OK, and if the invoice has a supported XML attachment
		// * ..and if all the invoice's lines have exactly *one* externalId that matches a serviceRecord from the XML attachment
		return !converters.isEmpty();
	}

	@Override
	public List<DunningExportResult> export(@NonNull final DunningToExport dunning)
	{
		final ImmutableMultimap<CrossVersionRequestConverter, DunningAttachment> //
		converter2ConvertableAttachment = extractConverters(dunning.getDunningAttachments());

		final ImmutableList.Builder<DunningExportResult> exportResults = ImmutableList.builder();

		for (final CrossVersionRequestConverter importConverter : converter2ConvertableAttachment.keySet())
		{
			for (final DunningAttachment attachment : converter2ConvertableAttachment.get(importConverter))
			{
				final XmlRequest xRequest = importConverter.toCrossVersionRequest(attachment.getDataAsInputStream());
				final XmlRequest xAugmentedRequest = augmentRequest(xRequest, dunning);

				final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				exportConverter.fromCrossVersionRequest(xAugmentedRequest, outputStream);

				final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

				final DunningExportResult exportResult = DunningExportResult.builder()
						.data(inputStream)
						.fileName("DunningExport_" + attachment.getFileName())
						.mimeType(attachment.getMimeType())
						.dunningExportProviderId(ForumDatenaustauschChConstants.DUNNING_EXPORT_PROVIDER_ID)
						.recipientId(dunning.getRecipientId())
						.build();

				exportResults.add(exportResult);
			}
		}

		return exportResults.build();
	}

	private ImmutableMultimap<CrossVersionRequestConverter, DunningAttachment> extractConverters(
			@NonNull final List<DunningAttachment> dunningAttachments)
	{
		final Builder<CrossVersionRequestConverter, DunningAttachment> //
		result = ImmutableMultimap.<CrossVersionRequestConverter, DunningAttachment> builder();

		for (final DunningAttachment attachment : dunningAttachments)
		{
			if (!MimeType.TYPE_XML.equals(attachment.getMimeType()))
			{
				continue;
			}

			final String xsdName = coalesceSuppliers(
					() -> attachment.getTags().get(ForumDatenaustauschChConstants.XSD_NAME),
					() -> XmlIntrospectionUtil.extractXsdValueOrNull(attachment.getDataAsInputStream()));

			final CrossVersionRequestConverter converter = crossVersionServiceRegistry.getRequestConverterForXsdName(xsdName);
			if (converter == null)
			{
				continue;
			}
			result.put(converter, attachment);
		}

		return result.build();
	}

	private XmlRequest augmentRequest(
			@NonNull final XmlRequest xRequest,
			@NonNull final DunningToExport dunning)
	{
		final RequestMod requestMod = RequestMod.builder()
				.modus(exportFileMode)
				.processingMod(createProcessingMod())
				.payloadMod(createPayloadMod(dunning, xRequest.getPayload()))
				.build();

		return xRequest.withMod(requestMod);
	}

	private ProcessingMod createProcessingMod()
	{
		return ProcessingMod.builder()
				.transportMod(createTransportMod())
				.build();
	}

	private TransportMod createTransportMod()
	{
		if (Check.isEmpty(exportFileFromEAN, true))
		{
			return null;
		}
		return TransportMod.builder()
				.from(exportFileFromEAN)
				.replacementViaEAN(exportFileViaEAN)
				.build();
	}

	private PayloadMod createPayloadMod(
			@NonNull final DunningToExport dunning,
			@NonNull final XmlPayload xPayLoad)
	{
		return PayloadMod.builder()
				.type(RequestType.REMINDER.getValue())
				.reminder(createReminder(dunning))
				.bodyMod(createBodyMod(dunning))
				.build();
	}

	private XmlReminder createReminder(@NonNull final DunningToExport dunning)
	{
		final XmlReminder createReminder = XmlReminder.builder()
				.requestDate(asXmlCalendar(dunning.getDunningDate()))
				.requestTimestamp(BigInteger.valueOf(dunning.getDunningTimestamp().getEpochSecond()))
				.requestId(dunning.getDocumentNumber())
				.reminderText(dunning.getDunningText())
				.reminderLevel("1")
				.build();

		return createReminder;
	}

	private BodyMod createBodyMod(
			@NonNull final DunningToExport dunning)
	{
		return BodyMod
				.builder()
				.prologMod(createPrologMod(dunning.getMetasfreshVersion()))
				.balanceMod(createBalanceMod(dunning))
				.build();
	}

	private PrologMod createPrologMod(@NonNull final MetasfreshVersion metasfreshVersion)
	{
		final SoftwareMod softwareMod = createSoftwareMod(metasfreshVersion);
		return PrologMod.builder()
				.pkgMod(softwareMod)
				.generatorMod(createSoftwareMod(metasfreshVersion))
				.build();
	}

	private XMLGregorianCalendar asXmlCalendar(@NonNull final Calendar cal)
	{
		try
		{
			final GregorianCalendar gregorianCal = new GregorianCalendar(cal.getTimeZone());
			gregorianCal.setTime(cal.getTime());

			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCal);
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new AdempiereException(e).appendParametersToMessage()
					.setParameter("cal", cal);
		}
	}

	private SoftwareMod createSoftwareMod(@NonNull final MetasfreshVersion metasfreshVersion)
	{
		final long versionNumber = metasfreshVersion.getMajor() * 100 + metasfreshVersion.getMinor(); // .. as advised in the documentation

		return SoftwareMod
				.builder()
				.name("metasfresh")
				.version(versionNumber)
				.description(metasfreshVersion.getFullVersion())
				.id(0L)
				.copyright("Copyright (C) 2018 metas GmbH")
				.build();
	}

	private BalanceMod createBalanceMod(@NonNull final DunningToExport dunning)
	{
		final Money amount = dunning.getAmount();

		final Money alreadyPaidAmount = dunning.getAlreadyPaidAmount();
		final BigDecimal amountDue = amount.getAmount().subtract(alreadyPaidAmount.getAmount());

		return BalanceMod.builder()
				.currency(amount.getCurrency())
				.amount(amount.getAmount())
				.amountDue(amountDue)
				.build();
	}

}
