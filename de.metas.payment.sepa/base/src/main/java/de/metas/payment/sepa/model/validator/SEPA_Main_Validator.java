package de.metas.payment.sepa.model.validator;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

public class SEPA_Main_Validator implements ModelValidator
{

	private int m_AD_Client_ID = -1;

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelValidator(new SEPA_Export_Line(), client); // task 07789

		engine.addModelValidator(new C_BP_BankAccount(), client); // 08477 Supporting IBAN and BIC
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null; // nothing to do
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		return null; // nothing to do
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}

}
