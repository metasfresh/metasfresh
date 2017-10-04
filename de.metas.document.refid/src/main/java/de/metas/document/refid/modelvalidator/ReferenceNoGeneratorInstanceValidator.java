package de.metas.document.refid.modelvalidator;

/*
 * #%L
 * de.metas.document.refid
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


import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.engine.IDocumentBL;
import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.logging.LogManager;

class ReferenceNoGeneratorInstanceValidator implements ModelValidator
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final IReferenceNoGeneratorInstance instance;
	private int m_AD_Client_ID = -1;
	private ModelValidationEngine engine;

	ReferenceNoGeneratorInstanceValidator(IReferenceNoGeneratorInstance instance)
	{
		Check.assume(instance != null, "instance not null");
		this.instance = instance;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (this.engine != null)
		{
			throw new IllegalStateException("Validator " + this + " was already registered to another validation engine: " + engine);
		}
		this.engine = engine;

		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		register();
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		// nothing
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		final int idxProcessed = po.get_ColumnIndex("Processed");

		if (type == TYPE_AFTER_NEW)
		{
			if (idxProcessed < 0)
			{
				// if Processed column is missing create/link to referenceNo right now
				Services.get(IReferenceNoBL.class).linkReferenceNo(po, instance);
			}
			else if (po.get_ValueAsBoolean(idxProcessed))
			{
				// create/link to referenceNo only if is processed
				Services.get(IReferenceNoBL.class).linkReferenceNo(po, instance);
			}
		}
		else if (type == TYPE_AFTER_CHANGE)
		{
			// consider it only if we have the Processed column, it was changed right and ...
			if (idxProcessed >= 0 && po.is_ValueChanged(idxProcessed))
			{
				if (po.get_ValueAsBoolean(idxProcessed))
				{
					// ... Processed is true => we need to create/link to referenceNo
					Services.get(IReferenceNoBL.class).linkReferenceNo(po, instance);
				}
				else
				{
					// ... Processed is false => we need to unlink to referenceNo
					Services.get(IReferenceNoBL.class).unlinkReferenceNo(po, instance);
				}
			}
		}
		else if (type == TYPE_BEFORE_DELETE)
		{
			Services.get(IReferenceNoBL.class).unlinkReferenceNo(po, instance);
		}

		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		final int clientId = instance.getType().getAD_Client_ID();
		if (clientId != 0 && clientId != po.getAD_Client_ID())
		{
			// this validator does not applies for current tenant
			return null;
		}

		if (timing == TIMING_AFTER_COMPLETE)
		{
			Services.get(IReferenceNoBL.class).linkReferenceNo(po, instance);
		}
		else if (timing == TIMING_AFTER_VOID
				|| timing == TIMING_AFTER_REACTIVATE
				|| timing == TIMING_AFTER_REVERSEACCRUAL
				|| timing == TIMING_AFTER_REVERSECORRECT)
		{
			Services.get(IReferenceNoBL.class).unlinkReferenceNo(po, instance);
		}
		return null;
	}

	/**
	 * Register table doc validators. Private because it is called automatically on {@link #initialize(ModelValidationEngine, MClient)}.
	 */
	private void register()
	{
		for (int tableId : instance.getAssignedTableIds())
		{
			final String tableName = MTable.getTableName(Env.getCtx(), tableId);

			if (Services.get(IDocumentBL.class).isDocumentTable(tableName))
			{
				engine.addDocValidate(tableName, this);
				logger.debug("Registered docValidate " + this);
			}
			else
			{
				engine.addModelChange(tableName, this);
				logger.debug("Registered modelChange " + this);
			}
		}
	}

	public void unregister()
	{
		for (int tableId : instance.getAssignedTableIds())
		{
			final String tableName = MTable.getTableName(Env.getCtx(), tableId);
			engine.removeModelChange(tableName, this);
			engine.removeDocValidate(tableName, this);
		}

		logger.debug("Unregistered " + this);
	}

	public IReferenceNoGeneratorInstance getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return "ReferenceNoGeneratorInstanceValidator [instance=" + instance + "]";
	}
}
