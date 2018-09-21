package de.metas.dunning.invoice.model.validator;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.util.Check;
import de.metas.util.Services;

public class InvoiceDunningValidator implements ModelValidator
{

	private int adClientId = -1;

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			adClientId = client.getAD_Client_ID();
		}

		Check.assume(Services.isAutodetectServices(), "We work with activated service auto detection");

		Services.get(de.metas.dunning.api.IDunningBL.class)
				.getDunningConfig()
				.getDunnableSourceFactory()
				.registerSource(de.metas.dunning.invoice.spi.impl.InvoiceSource.class);

		engine.addModelValidator(new de.metas.dunning.invoice.model.validator.C_Invoice(), client);
		engine.addModelValidator(new de.metas.dunning.invoice.model.validator.C_Dunning_Candidate(), client);
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type)
	{
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

}
