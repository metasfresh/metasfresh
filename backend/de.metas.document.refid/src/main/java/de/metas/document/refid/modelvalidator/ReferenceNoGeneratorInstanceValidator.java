package de.metas.document.refid.modelvalidator;

import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.slf4j.Logger;

import de.metas.document.engine.IDocumentBL;
import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

class ReferenceNoGeneratorInstanceValidator implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(ReferenceNoGeneratorInstanceValidator.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IReferenceNoBL referenceNoBL = Services.get(IReferenceNoBL.class);

	private final IReferenceNoGeneratorInstance instance;
	private int m_AD_Client_ID = -1;
	private ModelValidationEngine engine;

	ReferenceNoGeneratorInstanceValidator(@NonNull final IReferenceNoGeneratorInstance instance)
	{
		this.instance = instance;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, MClient client)
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
				referenceNoBL.linkReferenceNo(po, instance);
			}
			else if (po.get_ValueAsBoolean(idxProcessed))
			{
				// create/link to referenceNo only if is processed
				referenceNoBL.linkReferenceNo(po, instance);
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
					referenceNoBL.linkReferenceNo(po, instance);
				}
				else
				{
					// ... Processed is false => we need to unlink to referenceNo
					referenceNoBL.unlinkReferenceNo(po, instance);
				}
			}
		}
		else if (type == TYPE_BEFORE_DELETE)
		{
			referenceNoBL.unlinkReferenceNo(po, instance);
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
			referenceNoBL.linkReferenceNo(po, instance);
		}
		else if (timing == TIMING_AFTER_VOID
				|| timing == TIMING_AFTER_REACTIVATE
				|| timing == TIMING_AFTER_REVERSEACCRUAL
				|| timing == TIMING_AFTER_REVERSECORRECT)
		{
			referenceNoBL.unlinkReferenceNo(po, instance);
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
			final String tableName = adTableDAO.retrieveTableName(tableId);

			if (documentBL.isDocumentTable(tableName))
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
			final String tableName = adTableDAO.retrieveTableName(tableId);
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
