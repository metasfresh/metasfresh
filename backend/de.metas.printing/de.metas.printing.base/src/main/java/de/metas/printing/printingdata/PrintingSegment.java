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

package de.metas.printing.printingdata;

import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrinterRoutingId;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * Belongs to one {@link PrintingData} instance.
 * Specifies a range of pages from the respective printing-data and an output-printer and tray.
 */
@EqualsAndHashCode(exclude = { "pageFrom", "pageTo" })
@ToString
public class PrintingSegment
{
	private Integer pageFrom;
	private Integer pageTo;

	private final int initialPageFrom;
	private final int initialPageTo;
	private final int lastPages;
	private final String routingType;

	@Getter
	private final PrinterRoutingId printerRoutingId;

	@Getter
	private final HardwarePrinter printer;

	@Getter
	private final HardwareTrayId trayId;

	@Builder
	private PrintingSegment(
			final int initialPageFrom,
			final int initialPageTo,
			final int lastPages,
			@Nullable final String routingType,
			@Nullable final PrinterRoutingId printerRoutingId,
			@NonNull final HardwarePrinter printer,
			@Nullable final HardwareTrayId trayId)
	{
		this.initialPageFrom = initialPageFrom;
		this.initialPageTo = initialPageTo;
		this.lastPages = lastPages;
		this.routingType = routingType;
		this.printerRoutingId = printerRoutingId;
		this.printer = printer;
		this.trayId = trayId;

		Check.assume(initialPageFrom <= initialPageTo, "initialPageFrom={} is less or equal to initialPageTo={}", initialPageFrom, initialPageTo);
	}

	@NonNull
	public PrintingSegment copy()
	{
		return new PrintingSegment(initialPageFrom, initialPageTo, lastPages, routingType, printerRoutingId, printer, trayId);
	}
	
	public void setPageFrom(final int pageFrom)
	{
		this.pageFrom = pageFrom;
	}

	public int getPageFrom()
	{
		if (pageFrom != null)
		{
			return pageFrom;
		}
		return initialPageFrom;
	}

	public int getLastPages()
	{
		return lastPages;
	}

	public void setPageTo(final int pageTo)
	{
		this.pageTo = pageTo;
	}

	public int getPageTo()
	{
		if (pageTo != null)
		{
			return pageTo;
		}
		return initialPageTo;
	}

	public boolean isPageRange()
	{
		return I_AD_PrinterRouting.ROUTINGTYPE_PageRange.equals(routingType);
	}

	public boolean isLastPages()
	{
		return I_AD_PrinterRouting.ROUTINGTYPE_LastPages.equals(routingType);
	}
}
