package de.metas.dunning.modelvalidator;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.dunning.invoice.model.validator.InvoiceDunningValidator;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.spi.impl.DunningPrintingQueueHandler;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Module activator (model validator)
 * 
 * @author tsa
 * 
 */
public class DunningValidator implements ModelValidator
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

		engine.addModelValidator(new C_Dunning(), client);
		engine.addModelValidator(new C_DunningLevel(), client);
		engine.addModelValidator(new C_Dunning_Candidate(), client);
		engine.addModelValidator(new C_DunningDoc(), client);
		engine.addModelValidator(new C_DunningDoc_Line_Source(), client);

		// 03405 registering the default dunning candidate producer
		Services.get(de.metas.dunning.api.IDunningBL.class)
				.getDunningConfig()
				.getDunningCandidateProducerFactory()
				.registerDunningCandidateProducer(de.metas.dunning.api.impl.DefaultDunningCandidateProducer.class);

		//
		// Register dunning modules
		engine.addModelValidator(new InvoiceDunningValidator(), client); // invoice
		
		Services.get(IPrintingQueueBL.class).registerHandler(DunningPrintingQueueHandler.instance);
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
