package de.metas.printing.exception;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.AdMessageKey;
import de.metas.printing.api.IPrintJobLinesAggregator;

/**
 * Exception thrown from {@link IPrintJobLinesAggregator} implementation when failing to aggregate one printing queue item
 */
@SuppressWarnings("serial")
public class PrintingQueueAggregationException extends AdempiereException
{
	private static final AdMessageKey MSG = AdMessageKey.of("de.metas.printing.exception.PrintingQueueAggregationException");

	public PrintingQueueAggregationException(final int printingQueueId)
	{
		super(MSG, new Object[] { printingQueueId });
	}
	
	public PrintingQueueAggregationException(final int printingQueueId, final Throwable cause)
	{
		this(printingQueueId);
		this.initCause(cause);
	}
}
