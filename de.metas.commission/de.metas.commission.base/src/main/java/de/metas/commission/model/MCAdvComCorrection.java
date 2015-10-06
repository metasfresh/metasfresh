package de.metas.commission.model;

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


import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.util.proxy.Cached;
import org.compiere.model.MTable;
import org.compiere.model.PO;

public class MCAdvComCorrection extends X_C_AdvComCorrection
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8536829339207501082L;

	public MCAdvComCorrection(final Properties ctx, final int C_AdvComCorrection_ID,
			final String trxName)
	{
		super(ctx, C_AdvComCorrection_ID, trxName);
	}

	public MCAdvComCorrection(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Cached
	public PO retrievePO()
	{

		// if this PO hasn't been saved yet, a wrong value might be returned
		// by the cache for retrievePO
		assert get_ID() > 0 : this;

		final PO po = MTable.get(getCtx(), getAD_Table_ID()).getPO(
				getRecord_ID(), get_TrxName());
		if (po == null)
		{
			throw new NullPointerException("Missing PO for " + this);
		}
		return po;
	}
}
