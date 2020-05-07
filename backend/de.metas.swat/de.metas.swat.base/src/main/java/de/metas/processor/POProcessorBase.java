package de.metas.processor;

import org.compiere.model.MClient;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPOProcessorBL;
import de.metas.logging.LogManager;
import de.metas.processor.spi.IPOProcessor;
import de.metas.util.Services;

public class POProcessorBase implements ModelValidator
{
	private final Logger logger = LogManager.getLogger(getClass());

	private int m_AD_Client_ID = -1;

	public POProcessorBase()
	{
	}

	@Override
	public final int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}
	}

	@Override
	public final String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public final String docValidate(final PO po, final int timing)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		if (type != TYPE_SUBSEQUENT)
		{
			return null;
		}

		final IPOProcessor processor = Services.get(IPOProcessorBL.class).retrieveProcessorForPO(po);
		if (processor == null)
		{
			final String msg = "Unable to process '" + po + "'; Missing IOLCandCreator implmentation for table '" + MTable.getTableName(po.getCtx(), po.get_Table_ID()) + "'";
			logger.warn(msg);
			return msg;
		}

		boolean processed = processor.process(po);

		if (processed && po.set_ValueOfColumn("Processed", true))
		{
			po.saveEx();
		}

		return null;
	}
}
