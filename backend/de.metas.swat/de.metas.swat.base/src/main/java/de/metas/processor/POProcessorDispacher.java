package de.metas.processor;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_POProcessor;
import de.metas.adempiere.service.IPOProcessorBL;
import de.metas.adempiere.service.impl.POProcessorBL;
import de.metas.logging.LogManager;
import de.metas.processor.spi.IPOProcessor;

public class POProcessorDispacher implements ModelValidator
{
	private final Logger logger = LogManager.getLogger(getClass());

	private int m_AD_Client_ID = -1;

	private ModelValidationEngine engine;

	private MClient client;

	private Map<String, POProcessorBase> registeredProcessors = new HashMap<String, POProcessorBase>();

	private IPOProcessorBL poProcessorBL;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		Services.registerService(IPOProcessorBL.class, new POProcessorBL());
		this.poProcessorBL = Services.get(IPOProcessorBL.class);

		this.engine = engine;
		engine.addModelChange(I_AD_POProcessor.Table_Name, this);

		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			registerProcessorsForClient(client);
		}
		else
		{
			for (final MClient currentClient : MClient.getAll(Env.getCtx()))
			{
				registerProcessorsForClient(currentClient);
			}
		}
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	private void registerProcessorsForClient(final MClient client)
	{
		// register the existing candidate generators

		for (final MOrg org : MOrg.getOfClient(client))
		{
			final List<I_AD_POProcessor> list = poProcessorBL.retrieveProcessorDefForOrg(Env.getCtx(), org.getAD_Org_ID());
			for (final I_AD_POProcessor def : list)
			{
				register(def);
			}
		}
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		final I_AD_POProcessor processorDef = InterfaceWrapperHelper.create(po, I_AD_POProcessor.class);

		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			if (po.is_ValueChanged(I_AD_POProcessor.COLUMNNAME_IsActive))
			{
				if (processorDef.isActive())
				{
					register(processorDef);
				}
				else
				{
					unregister(processorDef, false);
				}
			}

			if (po.is_ValueChanged(I_AD_POProcessor.COLUMNNAME_Classname))
			{
				unregister(processorDef, true);
				register(processorDef);
			}
			if (po.is_ValueChanged(I_AD_POProcessor.COLUMNNAME_IsProcessDirectly))
			{

				setProcessDirectly(processorDef);

			}

		}
		if (type == TYPE_BEFORE_DELETE)
		{
			unregister(processorDef, false);
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}

	private void setProcessDirectly(final I_AD_POProcessor processorDef)
	{
		final POProcessorBase olCandCreatorBase = registeredProcessors.get(processorDef.getClassname());
		engine.enableModelValidatorSubsequentProcessing(olCandCreatorBase, processorDef.isProcessDirectly());
	}

	private void unregister(final I_AD_POProcessor processorDef, final boolean oldValue)
	{
		final String unregisterClassName;
		if (oldValue)
		{
			unregisterClassName = processorDef.getClassname();
		}
		else
		{
			final I_AD_POProcessor processorDefOld = InterfaceWrapperHelper.create(processorDef, I_AD_POProcessor.class, true);
			unregisterClassName = processorDefOld.getClassname();
		}

		final POProcessorBase processorBase = registeredProcessors.remove(unregisterClassName);
		if (processorBase != null)
		{
			final String sourceTable = poProcessorBL.getSourceTableName(processorDef);
			engine.removeModelChange(sourceTable, processorBase);
		}
	}

	private void register(final I_AD_POProcessor processorDef)
	{
		// making sure that the class name has no leading or trailing white spaces.
		if (!processorDef.getClassname().equals(processorDef.getClassname().trim()))
		{
			logger.info("Current Classname='" + processorDef.getClassname() + "' contains whitespaces. Trimming.");
			processorDef.setClassname(processorDef.getClassname().trim());
		}
		final String registerClassName = processorDef.getClassname();

		if (!registeredProcessors.containsKey(registerClassName))
		{
			final IPOProcessor processorImpl = Util.getInstanceOrNull(IPOProcessor.class, registerClassName);
			if (processorImpl == null)
			{
				// TODO -> AD_Message
				throw new AdempiereException("Implementierung '" + registerClassName + "' nicht gefunden!");
			}

			final String sourceTableName = getSourceTableName(processorImpl);
			final int sourceTableID = MTable.getTable_ID(sourceTableName);
			if (sourceTableID <= 0)
			{
				// TODO -> AD_Message
				throw new AdempiereException("Quell-Tabelle " + sourceTableName + " der Implementierung ist nicht vorhanden!");
			}
			// Note: this method is called before-save, so we can still set this property
			processorDef.setAD_Table_Source_ID(sourceTableID);

			final POProcessorBase processorBase = new POProcessorBase();
			processorBase.initialize(engine, client);
			engine.addModelChange(sourceTableName, processorBase);
			engine.enableModelValidatorSubsequentProcessing(processorBase, processorDef.isProcessDirectly());
			registeredProcessors.put(registerClassName, processorBase);
		}
	}

	private static final String getSourceTableName(IPOProcessor processor)
	{
		return InterfaceWrapperHelper.getTableName(processor.getTrxClass());
	}

	public ModelValidationEngine getModelValidationEngine()
	{
		return this.engine;
	}

	public MClient getAD_Client()
	{
		return this.client;
	}

}
