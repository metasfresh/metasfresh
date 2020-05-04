package de.metas.contracts.model;

/*
 * #%L
 * de.metas.contracts
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

import de.metas.contracts.model.X_C_SubscriptionProgress;

public class MSubscriptionProgress extends X_C_SubscriptionProgress
{
	private static final long serialVersionUID = -770469915391063757L;

	public MSubscriptionProgress(Properties ctx, int C_SubscriptionProgress_ID, String trxName)
	{
		super(ctx, C_SubscriptionProgress_ID, trxName);
	}

	public MSubscriptionProgress(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MSubscriptionProgress[") //
				.append(get_ID()) //
				.append(", EventDate=").append(getEventDate()) //
				.append(", EventType=").append(getEventType()) //
				.append(", SeqNo=").append(getSeqNo());

		sb.append(", Qty=").append(getQty());
		
		sb.append(", C_Flatrate_Term_ID=").append(getC_Flatrate_Term_ID());
		sb.append(", Status=").append(getStatus());
		sb.append("]");

		return sb.toString();
	}
}
