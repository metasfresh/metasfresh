package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;

import de.metas.commission.model.I_C_AdvComDoc;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_Sponsor_Cond;
import de.metas.commission.service.IComRelevantPoBL;

/**
 * 
 * Future improvements:
 * <ul>
 * <li>Dynamically register and unregister according to I_C_AdvCommissionRelevantPO changes.
 * <li>Don't fire on every modelChange, but where possible only fire on docValidate (e.g. order, invoice) to reduce unnecessary invocations
 * </ul>
 * 
 * @author ts
 * 
 */
public class CommissionRelevantPOs implements ModelValidator
{

	private static final CLogger logger = CLogger.getCLogger(CommissionRelevantPOs.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		// client = null for global validator
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
			CommissionRelevantPOs.logger.info("Initializing validator: " + this + " for client " + client.toString());
		}
		else
		{
			CommissionRelevantPOs.logger.info("Initializing global validator: " + this);
		}

		engine.addModelChange(I_C_Order.Table_Name, this);
		engine.addModelChange(I_C_Invoice.Table_Name, this);
		engine.addModelChange(I_C_AllocationHdr.Table_Name, this);
		engine.addModelChange(I_C_AdvCommissionFact.Table_Name, this);
		engine.addModelChange(I_C_Sponsor_Cond.Table_Name, this);
		engine.addModelChange(I_C_AdvComSalesRepFact.Table_Name, this);
		engine.addModelChange(I_C_AdvComDoc.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		if (type == ModelValidator.TYPE_AFTER_NEW || type == ModelValidator.TYPE_AFTER_CHANGE)
		{
			Services.get(IComRelevantPoBL.class).validatePOAfterNewOrChange(po);
		}
		else if (type == ModelValidator.TYPE_AFTER_DELETE)
		{
			Services.get(IComRelevantPoBL.class).validatePOBeforeDelete(po);
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
