package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import de.metas.adempiere.service.IPrintingService;

@EqualsAndHashCode
@ToString
@Value
public class PrintingServiceImpl implements IPrintingService
{
	String printerName;
	boolean isDirectPrint;

	PrintingServiceImpl(final @NonNull String printerName, final boolean isDirectPrint)
	{
		this.printerName = printerName;
		this.isDirectPrint = isDirectPrint;
	}
}
