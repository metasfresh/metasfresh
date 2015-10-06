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

import org.adempiere.model.GenericPO;
import org.compiere.model.MTable;

/**
 * This class is here because {@link MTable#getClass(String)} needs it in order not to return "{@link GenericPO}" for the table name.
 * 
 * @author ts
 * 
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_%282009_0027_G8%29'>(2009 0027 G8)</a>"
 */
public class MCSponsorSalesRep extends X_C_Sponsor_SalesRep
{

	private static final long serialVersionUID = -1690506830514027338L;

	// private static final CLogger logger = CLogger.getCLogger(MCSponsorSalesRep.class);

	public MCSponsorSalesRep(final Properties ctx, final int C_Sponsor_SalesRep_ID, final String trxName)
	{
		super(ctx, C_Sponsor_SalesRep_ID, trxName);
	}

	public MCSponsorSalesRep(final Properties ctx, final ResultSet rs, final String trxName)
	{

		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MCSponsorSalesRep[Id=");
		sb.append(getC_Sponsor_SalesRep_ID());
		sb.append("; SponsorSalesRepType=");
		sb.append(getSponsorSalesRepType());
		sb.append("; C_Sponsor_ID=");
		sb.append(getC_Sponsor_ID());
		sb.append("; C_BPartner_ID=");
		sb.append(getC_BPartner_ID());
		sb.append(",C_Sponsor_Parent_ID=");
		sb.append(getC_Sponsor_Parent_ID());
		sb.append("; valid from ");
		sb.append(getValidFrom());
		sb.append(" until ");
		sb.append(getValidTo());
		sb.append("]");

		return sb.toString();
	}
}
