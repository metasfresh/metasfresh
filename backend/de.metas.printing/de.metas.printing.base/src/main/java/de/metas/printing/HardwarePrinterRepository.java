/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing;

import com.google.common.collect.ImmutableList;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.cache.CCache;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.List;

@Repository
public class HardwarePrinterRepository
{
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	private final CCache<HardwarePrinterId, HardwarePrinter> cache = CCache.<HardwarePrinterId, HardwarePrinter>builder()
			.cacheName("HardwarePrinterCache")
			.tableName(I_AD_PrinterHW.Table_Name)
			.additionalTableNameToResetFor(I_AD_PrinterHW_MediaTray.Table_Name)
			.build();

	public HardwarePrinter getById(@NonNull final HardwarePrinterId id)
	{
		return cache.getOrLoad(id, this::retrieveById);
	}

	private HardwarePrinter retrieveById(@NonNull final HardwarePrinterId id)
	{
		final I_AD_PrinterHW printerRecord = printingDAO.retrieveHardwarePrinter(id);
		final List<I_AD_PrinterHW_MediaTray> trayRecords = printingDAO.retrieveMediaTrays(id);

		return fromRecord(printerRecord, trayRecords);
	}

	public static void validateOnBeforeSave(@NonNull final I_AD_PrinterHW printerRecord)
	{
		fromRecord(printerRecord, ImmutableList.of());
	}

	@NonNull
	private static HardwarePrinter fromRecord(
			@NonNull final I_AD_PrinterHW printerRecord,
			@NonNull final List<I_AD_PrinterHW_MediaTray> trayRecords)
	{
		return HardwarePrinter.builder()
				.id(HardwarePrinterId.ofRepoId(printerRecord.getAD_PrinterHW_ID()))
				.name(printerRecord.getName())
				.outputType(OutputType.ofCode(printerRecord.getOutputType()))
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(printerRecord.getExternalSystem_Config_ID()))
				.ippUrl(extractIPPUrl(printerRecord))
				.trays(trayRecords.stream()
						.map(HardwarePrinterRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private static URI extractIPPUrl(final @NonNull I_AD_PrinterHW printerRecord)
	{
		final String ippUrl = StringUtils.trimBlankToNull(printerRecord.getIPP_URL());
		if (ippUrl == null)
		{
			return null;
		}
		return URI.create(ippUrl);
	}

	@NonNull
	private static HardwareTray fromRecord(@NonNull final I_AD_PrinterHW_MediaTray trayRecord)
	{
		final HardwareTrayId trayId = HardwareTrayId.ofRepoId(trayRecord.getAD_PrinterHW_ID(), trayRecord.getAD_PrinterHW_MediaTray_ID());
		return new HardwareTray(trayId, trayRecord.getName(), trayRecord.getTrayNumber());
	}

	public void deleteCalibrations(@NonNull final HardwarePrinterId printerId) {printingDAO.deleteCalibrations(printerId);}

	public void deleteMediaTrays(@NonNull final HardwarePrinterId hardwarePrinterId) {printingDAO.deleteMediaTrays(hardwarePrinterId);}

	public void deleteMediaSizes(@NonNull final HardwarePrinterId hardwarePrinterId) {printingDAO.deleteMediaSizes(hardwarePrinterId);}
}
