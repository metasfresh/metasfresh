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

import org.compiere.model.MOrg;
import org.compiere.model.Query;

import de.metas.commission.exception.CommissionException;

public class MCAdvComSystem extends X_C_AdvComSystem
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MCAdvComSystem(final Properties ctx, final int C_AdvComSystem_ID, final String trxName)
	{
		super(ctx, C_AdvComSystem_ID, trxName);
	}

	public MCAdvComSystem(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static MCAdvComSystem retrieveDefaultEx(final Properties ctx, final int adOrgId, final String trxName)
	{
		final String wc = I_C_AdvComSystem.COLUMNNAME_AD_Org_ID + "=? AND " + I_C_AdvComSystem.COLUMNNAME_IsDefault + "='Y'";

		final MCAdvComSystem result = new Query(ctx, I_C_AdvComSystem.Table_Name, wc, trxName)
				.setParameters(adOrgId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly();

		if (result == null)
		{
			// TODO -> AD_Message
			throw CommissionException.inconsistentConfig(
					"In der Organisation " + MOrg.get(ctx, adOrgId).getName() + " gibt es keinen Standard-Verguetungsplan", null);
		}
		return result;
	}

	public static MCAdvComSystem retrieveForRootSponsor(final Properties ctx, final I_C_Sponsor root, final String trxName)
	{
		final String wc = I_C_AdvComSystem.COLUMNNAME_C_Sponsor_Root_ID + "=?";

		final MCAdvComSystem result = new Query(ctx, I_C_AdvComSystem.Table_Name, wc, trxName)
				.setParameters(root.getC_Sponsor_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly();

		return result;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvComSystem[Id=");
		sb.append(getC_AdvComSystem_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", IsDefault=");
		sb.append(isDefault());
		sb.append("]");

		return sb.toString();
	}
}
