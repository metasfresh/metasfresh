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

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

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
@AllArgsConstructor
public class DerKurierDeliveryOrderService implements DeliveryOrderService
{
	public static final String SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME = "DerKurier.csv";

	private final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;

	private final AttachmentEntryService attachmentEntryService;

	@Override
	@NonNull
	public String getShipperGatewayId()
	{
		return SHIPPER_GATEWAY_ID;
	}

	@Override
	@NonNull
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getId());
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

	public AttachmentEntry attachCsvToDeliveryOrder(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<String> csvLines)
	{
		final I_DerKurier_DeliveryOrder record = load(
				deliveryOrder.getId(),
				I_DerKurier_DeliveryOrder.class);

		final String attachmentFileName = "DeliveryOrder-" + deliveryOrder.getId() + ".csv";

		return attachCsvToRecord(csvLines, attachmentFileName, record);
	}

	public AttachmentEntry attachCsvToShippertransportation(
			@NonNull final ShipperTransportationId shipperTransportationId,
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<String> csvLines)
	{
		final I_M_ShipperTransportation record = loadOutOfTrx(shipperTransportationId, I_M_ShipperTransportation.class);

		final AttachmentEntry existingAttachment = attachmentEntryService.getByFilenameOrNull(
				TableRecordReference.of(record),
				SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME);
		if (existingAttachment == null)
		{
			return attachCsvToRecord(csvLines, SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME, record);
		}

		final byte[] exitingEntryData = attachmentEntryService.retrieveData(existingAttachment.getId());

		final LinkedHashSet<String> existingCsvLines = readLinesFromArray(exitingEntryData);
		final int originalSize = existingCsvLines.size();
		csvLines.forEach(existingCsvLines::add);

		if (existingCsvLines.size() == originalSize)
		{
			return existingAttachment; // nothing new was added
		}

		attachmentEntryService.unattach(TableRecordReference.of(record), existingAttachment);
		return attachCsvToRecord(ImmutableList.copyOf(existingCsvLines), SHIPPER_TRANSPORTATION_ATTACHMENT_FILENAME, record);
	}

	private AttachmentEntry attachCsvToRecord(
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

		return attachmentEntryService.createNewAttachment(
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
}
