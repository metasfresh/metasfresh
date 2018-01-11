package de.metas.payment.sepa.model.validator;

/*
 * #%L
 * de.metas.payment.sepa
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


import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.payment.sepa.api.ISEPADocumentSourceFactory;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAExportLineDocumentSource;

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
		
		Services.get(ISEPADocumentSourceFactory.class).registerSEPADocumentSource(I_SEPA_Export_Line.Table_Name, SEPAExportLineDocumentSource.class);
		

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
