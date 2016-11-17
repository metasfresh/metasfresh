package de.metas.inoutcandidate.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;

/**
 * Service which is creating the right {@link IReceiptScheduleProducer} for given parameters.
 * 
 * @author tsa
 * 
 */
public interface IReceiptScheduleProducerFactory extends ISingletonService
{

	/**
	 * Creates the receipt schedules producer for given <code>tableName</code>
	 * 
	 * NOTE: in case we ask for an asynchronous producer, that producer will always return <code>null</code> results
	 * 
	 * @param tableName
	 * @param async if true then a producer which creates the records asynchronously will be returned
	 * @return
	 */
	IReceiptScheduleProducer createProducer(String tableName, final boolean async);

	IReceiptScheduleProducerFactory registerProducer(String tableName, Class<? extends IReceiptScheduleProducer> producerClass);

	IReceiptScheduleWarehouseDestProvider getWarehouseDestProvider();

	IReceiptScheduleProducerFactory registerWarehouseDestProvider(IReceiptScheduleWarehouseDestProvider provider);

}
