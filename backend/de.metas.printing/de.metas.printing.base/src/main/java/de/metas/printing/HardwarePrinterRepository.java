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

import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.cache.CCache;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

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
		return cache.getOrLoad(id, key -> getById0(key));
	}

	private HardwarePrinter getById0(@NonNull final HardwarePrinterId id)
	{
		final I_AD_PrinterHW adPrinterHW = printingDAO.retrieveHardwarePrinter(id);

		final HardwarePrinter.HardwarePrinterBuilder printer = HardwarePrinter.builder()
				.id(id)
				.name(adPrinterHW.getName())
				.outputType(OutputType.ofCode(adPrinterHW.getOutputType()))
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(adPrinterHW.getExternalSystem_Config_ID()));

		for (final I_AD_PrinterHW_MediaTray printerHWMediaTray : printingDAO.retrieveMediaTrays(id))
		{
			final HardwareTrayId trayId = HardwareTrayId.ofRepoId(id, printerHWMediaTray.getAD_PrinterHW_MediaTray_ID());
			printer.tray(new HardwareTray(trayId, printerHWMediaTray.getName(), printerHWMediaTray.getTrayNumber()));
		}
		return printer.build();
	}
}
