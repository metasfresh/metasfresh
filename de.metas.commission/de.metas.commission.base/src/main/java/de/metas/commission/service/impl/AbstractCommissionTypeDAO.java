package de.metas.commission.service.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.service.ICommissionTypeDAO;

public class AbstractCommissionTypeDAO implements ICommissionTypeDAO
{
	@Override
	public I_C_AdvCommissionType retrieveForClass(final Properties ctx,
			final Class<? extends ICommissionType> commissionTypeClass,
			final int orgId,
			final String trxName)
	{
		final String wc = I_C_AdvCommissionType.COLUMNNAME_Classname + "=? AND " + I_C_AdvCommissionType.COLUMNNAME_AD_Org_ID + " in (0,?)";

		final Object[] params = { commissionTypeClass.getCanonicalName(), orgId };

		final String orderBy = I_C_AdvCommissionType.COLUMNNAME_Version + " DESC, " + I_C_AdvCommissionType.COLUMNNAME_AD_Org_ID + " DESC ";

		final List<I_C_AdvCommissionType> result = new Query(ctx, I_C_AdvCommissionType.Table_Name, wc, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_AdvCommissionType.class);

		assert result.size() > 0 : "class=" + commissionTypeClass + "; orgId=" + orgId;

		return result.get(0);
	}

	@Override
	@Cached
	public I_C_AdvCommissionType retrieve(
			@CacheCtx final Properties ctx, final int typeId,
			@CacheIgnore final String trxName)
	{
		return InterfaceWrapperHelper.create(ctx, typeId, I_C_AdvCommissionType.class, trxName);
	}

	@Override
	public List<I_C_AdvComSystem_Type> retrieveSystemTypesForCommissionType(final I_C_AdvCommissionType type)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_AdvComSystem_Type.class, type)
				.filter(new EqualsQueryFilter<I_C_AdvComSystem_Type>(I_C_AdvComSystem_Type.COLUMNNAME_C_AdvCommissionType_ID, type.getC_AdvCommissionType_ID()))
				.create()
				.list(I_C_AdvComSystem_Type.class);
	}

}
