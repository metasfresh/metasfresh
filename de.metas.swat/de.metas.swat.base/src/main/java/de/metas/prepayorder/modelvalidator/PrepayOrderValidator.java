package de.metas.prepayorder.modelvalidator;

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


import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.prepayorder.inoutcandidate.spi.impl.PrepayCandidateProcessor;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * Initializes the code for metas prepay order. Note: Currently, {@link #docValidate(PO, int)} and
 * {@link #modelChange(PO, int)} don't do anything.
 * 
 * @author ts
 *@see "<a href='http://dewiki908/mediawiki/index.php/US342:_Definition_Provisionsausloeser_(2010070510001145)'>US342:_Definition_Provisionsausloeser_(2010070510001145)</a>"
 */
public class PrepayOrderValidator implements ModelValidator
{
	private int ad_Client_ID = -1;

	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	/**
	 * Registers a {@link IPrepayOrderBL} and a {@link IShipmentSchedulesAfterFirstPassUpdater} implementation.
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		Services.get(IShipmentScheduleBL.class).registerCandidateProcessor(new PrepayCandidateProcessor());
	}

	/**
	 * Does nothing
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	/**
	 * Does nothing
	 */
	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

	/**
	 * Does nothing
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		return null;
	}

}
