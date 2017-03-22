package de.metas.inoutcandidate.spi;

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


import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;

/**
 * Abstract implementation of {@link IReceiptScheduleProducer} which implements common methods.
 * 
 * @author tsa
 *
 */
public abstract class AbstractReceiptScheduleProducer implements IReceiptScheduleProducer
{

	private IReceiptScheduleProducerFactory factory;

	@Override
	public final void setReceiptScheduleProducerFactory(IReceiptScheduleProducerFactory factory)
	{
		this.factory = factory;
	}

	@Override
	public final IReceiptScheduleProducerFactory getReceiptScheduleProducerFactory()
	{
		return factory;
	}
	
	protected final IReceiptScheduleWarehouseDestProvider getWarehouseDestProvider()
	{
		return getReceiptScheduleProducerFactory().getWarehouseDestProvider();
	}
}
