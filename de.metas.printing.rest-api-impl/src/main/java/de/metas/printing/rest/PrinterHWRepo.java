package de.metas.printing.rest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.esb.api.PrinterHW;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaSize;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaTray;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import lombok.NonNull;

/*
 * #%L
 * de.metas.printing.rest-api-impl
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

@Repository
public class PrinterHWRepo
{
	public void createOrUpdatePrinters(
			@NonNull final String hostKey,
			@NonNull final PrinterHWList printerHWList)
	{
		final List<PrinterHW> hwPrinterPojos = printerHWList.getHwPrinters();
		for (final PrinterHW hwPrinterPojo : hwPrinterPojos)
		{
			final I_AD_PrinterHW printerRecord = retrieveOrCreatePrinterRecord(hostKey, hwPrinterPojo);

			for (final PrinterHWMediaSize mediaSizePojo : hwPrinterPojo.getPrinterHWMediaSizes())
			{
				retrieveOrCreateMediaSizeRecord(printerRecord, mediaSizePojo);
			}

			for (final PrinterHWMediaTray mediaTrayPojo : hwPrinterPojo.getPrinterHWMediaTrays())
			{
				retrieveOrCreateMediaTrayRecord(printerRecord, mediaTrayPojo);
			}
		}
	}

	private void retrieveOrCreateMediaSizeRecord(
			@NonNull final I_AD_PrinterHW printerRecord,
			@NonNull final PrinterHWMediaSize mediaSizePojo)
	{
		I_AD_PrinterHW_MediaSize mediaSizeRecord = Services.get(IQueryBL.class).createQueryBuilder(I_AD_PrinterHW_MediaSize.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW_MediaSize.COLUMN_Name, mediaSizePojo.getName())
				.addEqualsFilter(I_AD_PrinterHW_MediaSize.COLUMN_AD_PrinterHW_ID, printerRecord.getAD_PrinterHW_ID())
				.create()
				.firstOnly(I_AD_PrinterHW_MediaSize.class);
		if (mediaSizeRecord == null)
		{
			mediaSizeRecord = newInstance(I_AD_PrinterHW_MediaSize.class);
			mediaSizeRecord.setName(mediaSizePojo.getName());
			mediaSizeRecord.setAD_PrinterHW(printerRecord);
			save(mediaSizeRecord);
		}
	}

	private void retrieveOrCreateMediaTrayRecord(
			@NonNull final I_AD_PrinterHW printerRecord,
			@NonNull final PrinterHWMediaTray printerTrayPojo)
	{
		final Integer trayNumber = Integer.valueOf(printerTrayPojo.getTrayNumber());

		I_AD_PrinterHW_MediaTray printerTrayRecord = Services.get(IQueryBL.class).createQueryBuilder(I_AD_PrinterHW_MediaTray.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW_MediaTray.COLUMN_TrayNumber, trayNumber)
				.addEqualsFilter(I_AD_PrinterHW_MediaTray.COLUMN_AD_PrinterHW_ID, printerRecord.getAD_PrinterHW_ID())
				.create()
				.firstOnly(I_AD_PrinterHW_MediaTray.class);
		if (printerTrayRecord == null)
		{
			printerTrayRecord = newInstance(I_AD_PrinterHW_MediaTray.class);
			printerTrayRecord.setTrayNumber(trayNumber);
			printerTrayRecord.setAD_PrinterHW(printerRecord);
			printerTrayRecord.setName(printerTrayPojo.getName());
			save(printerTrayRecord);

			Services.get(IPrinterBL.class).createDefaultTrayMatching(printerTrayRecord);
		}

		printerTrayRecord.setName(printerTrayPojo.getName());
		save(printerTrayRecord);
	}

	private I_AD_PrinterHW retrieveOrCreatePrinterRecord(
			@NonNull final String hostKey,
			@NonNull final PrinterHW hwPrinter)
	{
		I_AD_PrinterHW printerRecord = Services.get(IQueryBL.class).createQueryBuilder(I_AD_PrinterHW.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW.COLUMNNAME_HostKey, hostKey)
				.addEqualsFilter(I_AD_PrinterHW.COLUMN_Name, hwPrinter.getName())
				.create()
				.firstOnly(I_AD_PrinterHW.class);

		if (printerRecord == null)
		{
			printerRecord = newInstance(I_AD_PrinterHW.class);
			printerRecord.setName(hwPrinter.getName());
			printerRecord.setHostKey(hostKey);
			save(printerRecord);

			Services.get(IPrinterBL.class).createConfigAndDefaultPrinterMatching(printerRecord);
		}
		return printerRecord;
	}
}
