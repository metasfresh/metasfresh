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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.service.IParameterBL;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.service.ICommissionTypeBL;

public class MCAdvComSystemType extends X_C_AdvComSystem_Type
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2598289489689750534L;

	private static final Logger logger = LogManager.getLogger(MCAdvComSystemType.class);

	public static final String PARAM_TABLE = I_C_AdvComInstanceParam.Table_Name;

	public MCAdvComSystemType(final Properties ctx, final int C_AdvComSystem_Type_ID, final String trxName)
	{
		super(ctx, C_AdvComSystem_Type_ID, trxName);
	}

	public MCAdvComSystemType(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Note: We can't create the params on beforeSave, because if this type is new, its ID is still 0 at that time.
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return false;
		}

		if (newRecord
				|| is_ValueChanged(I_C_AdvComSystem_Type.COLUMNNAME_C_AdvCommissionType_ID)
				|| MCAdvComInstanceParam.retrieve(this).isEmpty())
		{
			final I_C_AdvCommissionType commissionTypeDef = getC_AdvCommissionType();
			final ICommissionType commissionType = Services.get(ICommissionTypeBL.class).getBusinessLogic(commissionTypeDef, this);

			final IParameterBL paramBL = Services.get(IParameterBL.class);

			paramBL.deleteParameters(this, MCAdvComSystemType.PARAM_TABLE);

			paramBL.createParameters(this, commissionType.getInstanceParams(getCtx(), getC_AdvComSystem(), get_TrxName()), MCAdvComSystemType.PARAM_TABLE);
		}
		return true;

	}

	@Override
	protected boolean beforeDelete()
	{
		final IParameterBL paramBL = Services.get(IParameterBL.class);
		paramBL.deleteParameters(this, MCAdvComSystemType.PARAM_TABLE);

		return true;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvComSystemType[Id=");
		sb.append(getC_AdvComSystem_Type_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", C_AdvComSystem_ID=");
		sb.append(getC_AdvComSystem_ID());
		sb.append(", C_AdvCommissionType_ID=");
		sb.append(getC_AdvCommissionType_ID());
		sb.append("]");

		return sb.toString();
	}

	public static List<MCAdvComSystemType> retrieveAll(final Properties ctx, final int adOrgID, final String trxName)
	{
		final String wc = I_C_AdvComSystem_Type.COLUMNNAME_AD_Org_ID + "=?";

		final List<MCAdvComSystemType> result = new Query(ctx, I_C_AdvComSystem_Type.Table_Name, wc, trxName)
				.setParameters(adOrgID)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_AdvComSystem_Type.COLUMNNAME_C_AdvComSystem_Type_ID)
				.setClient_ID()
				.list();

		MCAdvComSystemType.logger.info("Retrieved " + result.size() + " record(s)");

		return result;
	}

}
