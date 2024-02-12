/*
 * #%L
 * de.metas.printing.rest-api-impl
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

package de.metas.printing.rest.v2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.v2.printing.response.JsonPrinterHW;
import de.metas.common.rest_api.v2.printing.response.JsonPrinterTray;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingData;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingSegment;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwareTray;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.printingdata.PrintingData;
import de.metas.printing.printingdata.PrintingSegment;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PrintDataRequestHandler
{ 	public JsonPrintingDataResponse createResponse(@NonNull final ImmutableList<PrintingData> dataList)
	{
		final List<JsonPrintingData> jsonPrintingDataList = new ArrayList<>();
		for(final PrintingData data : dataList)
		{
			final List<JsonPrintingSegment> jsonPrintingSegments = new ArrayList<>();
			for (final PrintingSegment segment : data.getSegments())
			{
				final HardwarePrinter printer = segment.getPrinter();

				final List<JsonPrinterTray> trays = new ArrayList<>();
				final ImmutableMap<HardwareTrayId, HardwareTray> traySource = printer.getTrays();
				traySource.forEach((trayId, tray) -> trays.add(getJsonTray(tray)));

				final JsonPrinterHW jsonPrinterHW = JsonPrinterHW.builder()
						.name(printer.getName())
						.outputType(printer.getOutputType().getCode())
						.trays(trays)
						.build();

				final JsonPrintingSegment jsonPrintingSegment = JsonPrintingSegment.builder()
						.pageFrom(segment.getPageFrom())
						.pageTo(segment.getPageTo())
						.printerHW(jsonPrinterHW)
						.trayId(HardwareTrayId.toRepoId(segment.getTrayId()))
						.copies(segment.getCopies())
						.build();
				jsonPrintingSegments.add(jsonPrintingSegment);
			}
			final JsonPrintingData jsonPrintingData = JsonPrintingData.builder()
					.base64Data(Base64.getEncoder().encodeToString(data.getData()))
					.printingQueueId(data.getPrintingQueueItemId().getRepoId())
					.documentFileName(data.getDocumentFileName())
					.segments(jsonPrintingSegments)
					.build();
			jsonPrintingDataList.add(jsonPrintingData);
		}

		return JsonPrintingDataResponse.builder().jsonPrintingDataList(jsonPrintingDataList).build();
	}

	private JsonPrinterTray getJsonTray(@NonNull final HardwareTray tray)
	{
		return JsonPrinterTray.builder()
				.name(tray.getName())
				.trayId(tray.getId().getRepoId())
				.number(tray.getTrayNumber())
				.build();
	}
}
