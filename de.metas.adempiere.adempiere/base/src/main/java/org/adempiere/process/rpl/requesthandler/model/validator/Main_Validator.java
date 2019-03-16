package org.adempiere.process.rpl.requesthandler.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.process.rpl.requesthandler.RequestHandler_Constants;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.spi.impl.LoadPORequestHandler;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Ini;

import de.metas.util.Check;
import de.metas.util.Services;

// TODO: add to AD_ModelValidator table
public class Main_Validator implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		Check.assume(Services.isAutodetectServices(), "Service auto detection is enabled");

		if (!Ini.isSwingClient())
		{
			// make sure that the handlers defined in this module are registered
			Services.get(IReplRequestHandlerBL.class).registerHandlerType("LoadPO", LoadPORequestHandler.class, RequestHandler_Constants.ENTITY_TYPE);
		}
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null; // nothing to do
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		return null; // nothing to do
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null; // nothing to do
	}

}
