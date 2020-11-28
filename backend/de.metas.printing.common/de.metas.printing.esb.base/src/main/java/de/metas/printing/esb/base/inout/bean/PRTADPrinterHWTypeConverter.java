package de.metas.printing.esb.base.inout.bean;

/*
 * #%L
 * de.metas.printing.esb.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.metas.printing.esb.api.PrinterHW;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaSize;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaTray;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWMediaSizeType;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWMediaTrayType;
import de.metas.printing.esb.base.jaxb.generated.PRTADPrinterHWType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

/**
 * {@link PRTADPrinterHWType} converters.
 * 
 * NOTE: please don't add here camel related code
 * 
 * @author tsa
 * 
 */
public class PRTADPrinterHWTypeConverter
{
	protected final transient ObjectFactory factory = new ObjectFactory();

	public List<PRTADPrinterHWType> mkPRTADPrinterHWs(final PrinterHWList printerHWList, final int sessionId)
	{
		final List<PrinterHW> printers = printerHWList.getHwPrinters();
		if (printerHWList == null || printerHWList.getHwPrinters().isEmpty())
		{
			return Collections.emptyList();
		}

		final List<PRTADPrinterHWType> result = new ArrayList<PRTADPrinterHWType>();

		for (final PrinterHW printer : printers)
		{
			if (printer == null)
			{
				throw new IllegalArgumentException("Printer needs to be set.");
			}

			// first, add the printer
			final PRTADPrinterHWType printerHW = mkPRTADPrinterHW(printer);
			printerHW.setADSessionIDAttr(BigInteger.valueOf(sessionId));

			// next, add it's size, tray and calibration
			if (printer.getPrinterHWMediaSizes() != null)
			{
				for (final PrinterHWMediaSize size : printer.getPrinterHWMediaSizes())
				{
					PRTADPrinterHWMediaSizeType printerHWsize = mkPRTADPrinterHWMediaSize(size);

					printerHW.getPRTADPrinterHWMediaSize().add(printerHWsize);
				}
			}

			if (printer.getPrinterHWMediaTrays() != null)
			{
				for (final PrinterHWMediaTray tray : printer.getPrinterHWMediaTrays())
				{
					PRTADPrinterHWMediaTrayType printerHWtray = mkPRTADPrinterHWMediaTray(tray);

					printerHW.getPRTADPrinterHWMediaTray().add(printerHWtray);
				}
			}

			result.add(printerHW);
		}

		return result;
	}

	private PRTADPrinterHWType mkPRTADPrinterHW(final PrinterHW printer)
	{
		final PRTADPrinterHWType printerHW = new PRTADPrinterHWType();

		printerHW.setName(printer.getName());

		// ADempiere Specific Data
		printerHW.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		printerHW.setReplicationModeAttr(ReplicationModeEnum.Table);
		printerHW.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		printerHW.setVersionAttr(JAXBConstants.PRT_AD_PRINTER_HW_FORMAT_VERSION);

		return printerHW;
	}

	private PRTADPrinterHWMediaSizeType mkPRTADPrinterHWMediaSize(final PrinterHWMediaSize size)
	{
		final PRTADPrinterHWMediaSizeType printerHWMediaSize = new PRTADPrinterHWMediaSizeType();

		printerHWMediaSize.setName(size.getName());

		return printerHWMediaSize;
	}

	private PRTADPrinterHWMediaTrayType mkPRTADPrinterHWMediaTray(final PrinterHWMediaTray tray)
	{
		final PRTADPrinterHWMediaTrayType printerHWMediaTray = new PRTADPrinterHWMediaTrayType();
		printerHWMediaTray.setName(tray.getName());
		printerHWMediaTray.setTrayNumber(BigInteger.valueOf(Integer.valueOf(tray.getTrayNumber())));

		return printerHWMediaTray;
	}
}
