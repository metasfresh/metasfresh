package de.metas.adempiere.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.create;

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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/**
 *
 * @author ts [metas 00036] Modelvalidator sets #StoreCreditCardData in the context. That value can then be used in the
 *         application dictionary.
 */
public class OrgInfo implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(OrgInfo.class);

	public static final String ENV_ORG_INFO_STORE_CC_DATA = "#StoreCreditCardData";
	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		final Properties ctx = Env.getCtx();

		final I_AD_OrgInfo orgInfo = create(Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, AD_Org_ID, ITrx.TRXNAME_None), I_AD_OrgInfo.class);
		if(orgInfo == null)
		{
			logger.warn("Unable to retrieve AD_OrgInfo for AD_Org_ID={}; AD_Role_ID={}; AD_User_ID={}", AD_Org_ID, AD_Role_ID, AD_User_ID);
			return null;
		}
		final String ccStoreMode = orgInfo.getStoreCreditCardData();

		Env.setContext(ctx, ENV_ORG_INFO_STORE_CC_DATA, ccStoreMode);

		return null;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_AD_OrgInfo.Table_Name, this);
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			if (po.is_ValueChanged(I_AD_OrgInfo.COLUMNAME_StoreCreditCardData))
			{
				final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(po, I_AD_OrgInfo.class);

				final String ccStoreMode = orgInfo.getStoreCreditCardData();

				Env.setContext(po.getCtx(), ENV_ORG_INFO_STORE_CC_DATA, ccStoreMode);
				Env.setContext(Env.getCtx(), ENV_ORG_INFO_STORE_CC_DATA, ccStoreMode);
			}
		}
		return null;
	}
}
