package org.eevolution.model.validator;

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


import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.eevolution.mrp.process.PP_MRP_RecreateForDocument;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;

/**
 * MRP Model Interceptors
 * 
 * @author tsa
 * 
 */
public class MRPInterceptor extends AbstractModelInterceptor
{
	private static final transient Logger logger = LogManager.getLogger(MRPInterceptor.class);

	public MRPInterceptor()
	{
		super();
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// services
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		//
		// Get "PP_MRP_RecreateForDocument"'s AD_Process_ID
		final Properties ctx = Env.getCtx();
		final int processId_PP_MRP_RecreateForDocument = adProcessDAO.retriveProcessIdByValue(ctx, PP_MRP_RecreateForDocument.PROCESSVALUE);
		if (processId_PP_MRP_RecreateForDocument <= 0)
		{
			logger.warn("No process was found for '{}'. Manually re-creating MRP records won't be available ", PP_MRP_RecreateForDocument.PROCESSVALUE);
		}

		//
		// Common interceptors
		engine.addModelValidator(new org.eevolution.model.validator.PP_MRP(), client);

		//
		// Dynamically add further MVs for supply producers
		// NOTE: keep these model validators as last registered
		final IMRPSupplyProducer producers = Services.get(IMRPSupplyProducerFactory.class).getAllSupplyProducers();
		final Set<String> sourceTableNames = producers.getSourceTableNames();
		for (final String sourceTableName : sourceTableNames)
		{
			//
			// Model Change
			engine.addModelChange(sourceTableName, this);

			//
			// Document Change
			if (docActionBL.isDocumentTable(sourceTableName))
			{
				engine.addDocValidate(sourceTableName, this);
			}

			//
			// Register source table related processes:
			// recreate PP_MRP records for selected document/record
			if (processId_PP_MRP_RecreateForDocument > 0)
			{
				adProcessDAO.registerTableProcess(sourceTableName, processId_PP_MRP_RecreateForDocument);
			}
		}

	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		for (final IMRPSupplyProducer producer : Services.get(IMRPSupplyProducerFactory.class).getSupplyProducers(tableName))
		{
			producer.onRecordChange(model, changeType);
		}
	}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		for (final IMRPSupplyProducer producer : Services.get(IMRPSupplyProducerFactory.class).getSupplyProducers(tableName))
		{
			producer.onDocumentChange(model, timing);
		}
	}

}
