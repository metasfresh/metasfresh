package org.eevolution.mrp.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;

import de.metas.process.ISvrProcessPrecondition;
import de.metas.process.SvrProcess;

/**
 * Recreates {@link I_PP_MRP} records for selected document/record
 * 
 * @author tsa
 *
 */
public class PP_MRP_RecreateForDocument extends SvrProcess implements ISvrProcessPrecondition
{
	/** Standard AD_Process.Value */
	public static final transient String PROCESSVALUE = "PP_MRP_RecreateForDocument";

	// Services
	private final transient IMRPSupplyProducerFactory mrpSupplyProducerFactory = Services.get(IMRPSupplyProducerFactory.class);

	// Parameters
	private Object model;

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final String tableName = context.getTableName();
		return mrpSupplyProducerFactory.getAllSupplyProducers().isRecreatedMRPRecordsSupported(tableName);
	}

	@Override
	protected void prepare()
	{
		final String tableName = getTableName();
		final int recordId = getRecord_ID();

		this.model = InterfaceWrapperHelper.create(getCtx(), tableName, recordId, Object.class, getTrxName());
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.assumeNotNull(model, "model not null");

		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final List<IMRPSupplyProducer> supplyProducers = mrpSupplyProducerFactory.getSupplyProducers(tableName);
		for (final IMRPSupplyProducer supplyProducer : supplyProducers)
		{
			supplyProducer.recreateMRPRecords(model);
		}

		return MSG_OK;
	}
}
