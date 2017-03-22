package de.metas.ordercandidate.modelvalidator;

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


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelInterceptor2ModelValidatorWrapper;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCandGenerator;
import de.metas.ordercandidate.spi.IOLCandCreator;

/**
 * Commons model validator that is notified of changes in the DB table <code>C_OLCandGenerator</code>. It reacts to
 * those changes by registering and unregistering instances of {@link OLCandCreatorBase}.
 *
 * @author ts
 *
 */
public class OLCandGenerator extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(OLCandGenerator.class);

	private IModelValidationEngine engine;

	private MClient client;

	private final Map<String, OLCandCreatorBase> registeredProcessors = new HashMap<String, OLCandCreatorBase>();

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelChange(I_C_OLCandGenerator.Table_Name, this);
		this.engine = engine;

		if (client != null)
		{
			registerProcessorsForClient(client);
		}
		else
		{
			for (final I_AD_Client currentClient : Services.get(IClientDAO.class).retrieveAllClients(Env.getCtx()))
			{
				registerProcessorsForClient(currentClient);
			}
		}
	}

	/**
	 * Register the existing candidate generators.
	 *
	 * @param client
	 */
	private void registerProcessorsForClient(final I_AD_Client client)
	{
		final Properties ctx = Env.getCtx();

		final PO clientPO = InterfaceWrapperHelper.getPO(client);
		for (final MOrg org : MOrg.getOfClient(clientPO))
		{
			final List<I_C_OLCandGenerator> creators = Services.get(IOLCandDAO.class).retrieveOlCandCreatorForOrg(ctx, org.getAD_Org_ID());
			for (final I_C_OLCandGenerator olCandGenerator : creators)
			{
				register(olCandGenerator);
			}
		}
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		final I_C_OLCandGenerator olCandGenerator = InterfaceWrapperHelper.create(model, I_C_OLCandGenerator.class);

		if (changeType == ModelChangeType.BEFORE_NEW || changeType == ModelChangeType.BEFORE_CHANGE)
		{
			if (InterfaceWrapperHelper.isValueChanged(model, I_C_OLCandGenerator.COLUMNNAME_IsActive))
			{
				if (olCandGenerator.isActive())
				{
					register(olCandGenerator);
				}
				else
				{
					unregister(olCandGenerator, false);
				}
			}

			if (InterfaceWrapperHelper.isValueChanged(model, I_C_OLCandGenerator.COLUMNNAME_OCGeneratorImpl))
			{
				unregister(olCandGenerator, true);
				register(olCandGenerator);
			}
			if (InterfaceWrapperHelper.isValueChanged(model, I_C_OLCandGenerator.COLUMNNAME_IsProcessDirectly))
			{
				setProcessDirectly(olCandGenerator);
			}
		}

		if (changeType == ModelChangeType.BEFORE_DELETE)
		{
			unregister(olCandGenerator, false);
		}
	}

	private void setProcessDirectly(final I_C_OLCandGenerator olCandGenerator)
	{
		final OLCandCreatorBase olCandCreatorBase = registeredProcessors.get(olCandGenerator.getOCGeneratorImpl());
		if (engine instanceof ModelValidationEngine) // FIXME POJOLookupMap does not support this
		{
			final ModelValidator wrapper = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(olCandCreatorBase);
			((ModelValidationEngine)engine).enableModelValidatorSubsequentProcessing(wrapper, olCandGenerator.isProcessDirectly());
		}
	}

	private void unregister(final I_C_OLCandGenerator olCandGenerator, final boolean oldValue)
	{
		final String unregisterClassName;
		if (oldValue)
		{
			unregisterClassName = olCandGenerator.getOCGeneratorImpl();
		}
		else
		{
			final I_C_OLCandGenerator olCandGeneratorOld = InterfaceWrapperHelper.createOld(olCandGenerator, I_C_OLCandGenerator.class);
			unregisterClassName = olCandGeneratorOld.getOCGeneratorImpl();
		}

		final OLCandCreatorBase olCandCreatorBase = registeredProcessors.remove(unregisterClassName);
		if (olCandCreatorBase != null)
		{
			final String sourceTable = olCandGenerator.getAD_Table_Source().getName();
			if (engine instanceof ModelValidationEngine) // FIXME POJOLookupMap does not support this
			{
				final ModelValidator wrapper = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(olCandCreatorBase);
				((ModelValidationEngine)engine).removeModelChange(sourceTable, wrapper);
			}
		}
	}

	private String register(final I_C_OLCandGenerator olCandGenerator)
	{
		// making sure that the class name has no leading or trailing white spaces.
		if (!olCandGenerator.getOCGeneratorImpl().equals(olCandGenerator.getOCGeneratorImpl().trim()))
		{
			logger.info("Current OCGeneratorImpl='" + olCandGenerator.getOCGeneratorImpl() + "' contains whitespaces. Trimming.");
			olCandGenerator.setOCGeneratorImpl(olCandGenerator.getOCGeneratorImpl().trim());
		}
		final String registerClassName = olCandGenerator.getOCGeneratorImpl();

		if (!registeredProcessors.containsKey(registerClassName))
		{
			final IOLCandCreator newCreatorImpl = Util.getInstanceOrNull(IOLCandCreator.class, registerClassName);
			if (newCreatorImpl == null)
			{
				// TODO -> AD_Message
				throw new AdempiereException("Implementierung '" + registerClassName + "' nicht gefunden!");
			}

			final String sourceTableName = newCreatorImpl.getSourceTable();
			final int sourceTableID = Services.get(IADTableDAO.class).retrieveTableId(sourceTableName);
			if (sourceTableID <= 0)
			{
				// TODO -> AD_Message
				throw new AdempiereException("Quell-Tabelle " + sourceTableName + " der Implementierung ist nicht vorhanden!");
			}
			// Note: this method is called before-save, so we can still set this property
			olCandGenerator.setAD_Table_Source_ID(sourceTableID);

			final OLCandCreatorBase newCreatorBase = new OLCandCreatorBase();
			newCreatorBase.initialize(engine, client);
			engine.addModelChange(sourceTableName, newCreatorBase);
			if (engine instanceof ModelValidationEngine) // FIXME POJOLookupMap does not support this
			{
				final ModelValidator wrapper = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(newCreatorBase);
				((ModelValidationEngine)engine).enableModelValidatorSubsequentProcessing(wrapper, olCandGenerator.isProcessDirectly());
			}

			registeredProcessors.put(registerClassName, newCreatorBase);
		}
		return null;
	}

	public static class OLCandCreatorBase extends AbstractModelInterceptor
	{
		public OLCandCreatorBase()
		{
			super();
		}
		
		@Override
		protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
		{
			// NOTE: model change registration will be performed later by calling method.
		}

		@Override
		public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
		{
			if (changeType != ModelChangeType.SUBSEQUENT)
			{
				return;
			}

			final PO modelPO = InterfaceWrapperHelper.getPO(model);
			Services.get(IOLCandBL.class).invokeOLCandCreator(modelPO);
		}
	}
}
