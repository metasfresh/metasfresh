package de.metas.adempiere.ait.validator;

/*
 * #%L
 * de.metas.adempiere.ait
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


import java.util.HashSet;
import java.util.Set;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * This validator fires test events on behalf of {@link AIntegrationTestDriver}s.
 * 
 * @author ts
 * 
 */
public class InvoiceCandidateValidator implements ModelValidator
{
	private int clientId = -1;
	private boolean isRegistered = false;

	private static final InvoiceCandidateValidator instance = new InvoiceCandidateValidator();

	private Set<AIntegrationTestDriver> drivers = new HashSet<AIntegrationTestDriver>();

	public static InvoiceCandidateValidator get()
	{
		return instance;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			this.clientId = client.getAD_Client_ID();

		engine.addModelChange(I_C_Invoice_Candidate.Table_Name, this);
		isRegistered = true;
	}

	/**
	 * Registers the given driver and makes sure that this model validator is added to the {@link ModelValidationEngine}
	 * . The model validator can fire test events on behalf of registered drivers.
	 * 
	 * @param driver
	 */
	public void register(final AIntegrationTestDriver driver)
	{
		drivers.add(driver);

		if (isRegistered)
		{
			return;
		}
		ModelValidationEngine.get().addModelValidator(this, null);
	}

	@Override
	public int getAD_Client_ID()
	{
		return clientId;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (type == TYPE_AFTER_NEW)
		{
			for (final AIntegrationTestDriver driver : drivers)
			{
				driver.fireTestEvent(EventType.INVOICECAND_CREATE_AFTER, po);
			}
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		// nothing to do (currently)
		return null;
	}
}
