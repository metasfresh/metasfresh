package de.metas.adempiere.modelvalidator;

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


import java.math.BigDecimal;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MClient;
import org.compiere.model.MStorage;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.slf4j.Logger;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.modelvalidator.SwatValidator;
import de.metas.order.model.interceptor.C_OrderLine;

/**
 * Makes sure that there are no negative QtyReserved values.
 *
 * Note: This model validator is not registered by {@link SwatValidator}, because we want the option to disable it
 * without code change if required
 *
 * @author ts
 *
 */
public class ProhibitNegativeQtyReserved implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(ProhibitNegativeQtyReserved.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_C_OrderLine.Table_Name, this);
		engine.addModelChange(I_M_Storage.Table_Name, this);

	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, int type)
	{
		if (type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
		{
			return null;
		}

		if (!po.is_ValueChanged(I_C_OrderLine.COLUMNNAME_QtyReserved))
		{
			return null;
		}

		if (po instanceof MStorage)
		{
			final MStorage st = (MStorage)po;
			if (st.getQtyReserved().signum() < 0)
			{
				st.setQtyReserved(BigDecimal.ZERO);
// no need for the warning & stacktrace for now.
//				final AdempiereException ex = new AdempiereException("@" + C_OrderLine.ERR_NEGATIVE_QTY_RESERVED + "@. Setting QtyReserved to ZERO."
//						+ "\nStorage: " + st);
//				logger.warn(ex.getLocalizedMessage(), ex);
				logger.info(Services.get(IMsgBL.class).getMsg(po.getCtx(), "@" + C_OrderLine.ERR_NEGATIVE_QTY_RESERVED + "@. Setting QtyReserved to ZERO." + "\nStorage: " + st));
				return null;
			}
		}
		return null;
	}
}
