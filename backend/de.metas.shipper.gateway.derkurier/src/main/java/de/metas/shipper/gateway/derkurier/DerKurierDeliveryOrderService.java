/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shipper.gateway.derkurier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.List;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.SHIPPER_GATEWAY_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Service
@RequiredArgsConstructor
public class DerKurierDeliveryOrderService implements DeliveryOrderService
{
	public static final String SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME = "DerKurier.csv";

	@NonNull private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;
	@NonNull private final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;
	@NonNull private final AttachmentEntryService attachmentEntryService;
	@NonNull private final DerKurierDraftDeliveryOrderCreator deliveryOrderCreator;
	@NonNull private final Converters converters;

	public static DerKurierDeliveryOrderService newInstanceForUnitTesting()
	{
		final DerKurierShipperConfigRepository configRepo = new DerKurierShipperConfigRepository();
		final Converters converters = new Converters();
		return new DerKurierDeliveryOrderService(
				configRepo,
				new DerKurierDeliveryOrderRepository(converters),
				AttachmentEntryService.createInstanceForUnitTesting(),
				new DerKurierDraftDeliveryOrderCreator(configRepo),
				converters
		);
	}

	@Override
	@NonNull
	public ShipperGatewayId getShipperGatewayId()
	{
		return SHIPPER_GATEWAY_ID;
	}

	@Override
	public @NotNull DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		return deliveryOrderCreator.createDraftDeliveryOrder(request);
	}

	@Override
	@NonNull
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getIdNotNull());
	}

	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		return derKurierDeliveryOrderRepository.getByRepoId(deliveryOrderId);
	}

	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		return derKurierDeliveryOrderRepository.save(deliveryOrder);
	}

	public void attachCsvToDeliveryOrder(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<String> csvLines)
	{
		final I_DerKurier_DeliveryOrder record = load(
				deliveryOrder.getIdNotNull(),
				I_DerKurier_DeliveryOrder.class);

		final String attachmentFileName = "DeliveryOrder-" + deliveryOrder.getId() + ".csv";

		attachCsvToRecord(csvLines, attachmentFileName, record);
	}

	public void attachCsvToShippertransportation(
			@NonNull final ShipperTransportationId shipperTransportationId,
			@NonNull final List<String> csvLines)
	{
		final I_M_ShipperTransportation record = loadOutOfTrx(shipperTransportationId, I_M_ShipperTransportation.class);

		final AttachmentEntry existingAttachment = attachmentEntryService.getByFilenameOrNull(
				TableRecordReference.of(record),
				SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME);
		if (existingAttachment == null)
		{
			attachCsvToRecord(csvLines, SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME, record);
			return;
		}

		final byte[] exitingEntryData = attachmentEntryService.retrieveData(existingAttachment.getId());

		final LinkedHashSet<String> existingCsvLines = readLinesFromArray(exitingEntryData);
		final int originalSize = existingCsvLines.size();
		csvLines.forEach(existingCsvLines::add);

		if (existingCsvLines.size() == originalSize)
		{
			return; // nothing new was added
		}

		attachmentEntryService.unattach(TableRecordReference.of(record), existingAttachment);
		attachCsvToRecord(ImmutableList.copyOf(existingCsvLines), SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME, record);
	}

	private void attachCsvToRecord(
			@NonNull final List<String> csvLines,
			@NonNull final String attachmentFileName,
			@NonNull final Object targetRecordModel)
	{

		// thx to https://stackoverflow.com/a/5619144/1012103
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(baos);
		for (final String csvLine : csvLines)
		{
			writeLineToStream(csvLine, out);
		}
		final byte[] byteArray = baos.toByteArray();

		attachmentEntryService.createNewAttachment(
				TableRecordReference.of(targetRecordModel),
				attachmentFileName,
				byteArray);
	}

	private void writeLineToStream(
			@NonNull final String csvLine,
			@NonNull final DataOutputStream dataOutputStream)
	{
		try
		{
			final byte[] bytes = (csvLine + "\n").getBytes(DerKurierConstants.CSV_DATA_CHARSET);
			dataOutputStream.write(bytes);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("IOException writing cvsLine to dataOutputStream", e).appendParametersToMessage()
					.setParameter("csvLine", csvLine)
					.setParameter("dataOutputStream", dataOutputStream);
		}
	}

	public static LinkedHashSet<String> readLinesFromArray(@NonNull final byte[] bytes)
	{
		final InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(bytes), DerKurierConstants.CSV_DATA_CHARSET);

		try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
		{
			final LinkedHashSet<String> result = new LinkedHashSet<>();
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine())
			{
				result.add(line);
			}
			return result;
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public @NotNull ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final DerKurierShipperConfig shipperConfig = derKurierShipperConfigRepository.retrieveConfigForShipperId(shipperId.getRepoId());
		return createClient(shipperConfig);
	}

	@VisibleForTesting
	DerKurierClient createClient(@NonNull final DerKurierShipperConfig shipperConfig)
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(shipperConfig.getRestApiBaseUrl());

		final RestTemplate restTemplate = restTemplateBuilder.build();
		extractAndConfigureObjectMapperOfRestTemplate(restTemplate);

		return new DerKurierClient(
				restTemplate,
				converters,
				this,
				derKurierDeliveryOrderRepository);
	}

	/**
	 * Put JavaTimeModule into the rest template's jackson object mapper.
	 * <b>
	 * Note 1: there have to be better ways to achieve this, but i don't know them.
	 * thx to https://stackoverflow.com/a/47176770/1012103
	 * <b>
	 * Note 2: visible because this is the object mapper we run with; we want our unit tests to use it as well.
	 */
	@VisibleForTesting
	public static ObjectMapper extractAndConfigureObjectMapperOfRestTemplate(@NonNull final RestTemplate restTemplate)
	{
		final MappingJackson2HttpMessageConverter messageConverter = restTemplate
				.getMessageConverters()
				.stream()
				.filter(MappingJackson2HttpMessageConverter.class::isInstance)
				.map(MappingJackson2HttpMessageConverter.class::cast)
				.findFirst().orElseThrow(() -> new RuntimeException("MappingJackson2HttpMessageConverter not found"));

		final ObjectMapper objectMapper = messageConverter.getObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		return objectMapper;
	}
}
