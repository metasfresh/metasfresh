package de.metas.invoice.service.impl;

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


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.document.IDocLineCopyHandler;

/**
 * 
 * Note: This class is currently instantiated and called directly from BLs in this package.<br>
 * Please move this class to <code>org.adempiere.invoice.spi.impl</code> as soon as it is registered at and invoked via {@link de.metas.document.ICopyHandlerBL}.
 * 
 */
final class DefaultDocLineCopyHandler<LT> implements IDocLineCopyHandler<LT>
{
	private final Class<LT> lineClass;

	public DefaultDocLineCopyHandler(Class<LT> lineClass)
	{
		this.lineClass = lineClass;
	}

	@Override
	public void copyPreliminaryValues(final LT from, final LT to)
	{
		InterfaceWrapperHelper.copyValues(from, to);
	}

	@Override
	public void copyValues(final LT from, final LT to)
	{
		// nothing should happen in the default handler
	}

	@Override
	public Class<LT> getSupportedItemsClass()
	{
		return lineClass;
	}
}
