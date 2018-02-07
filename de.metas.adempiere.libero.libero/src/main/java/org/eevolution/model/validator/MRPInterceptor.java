package org.eevolution.model.validator;

import java.util.Set;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;
import org.slf4j.Logger;

import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;

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
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

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
