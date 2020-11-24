package de.metas.printing.api.impl;

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


import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;

/**
 * Abstract {@link IPrintingQueueSource} which implements some common methods
 * 
 * @author tsa
 */
public abstract class AbstractPrintingQueueSource implements IPrintingQueueSource
{
	private String name;
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public boolean isPrinted(@NonNull final I_C_Printing_Queue item)
	{
		final boolean printed = item.isProcessed();
		return printed;
	}

	@Override
	public void markPrinted(@NonNull final I_C_Printing_Queue item)
	{
		item.setProcessed(true);
		InterfaceWrapperHelper.save(item);
	}
}
