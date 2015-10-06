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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MProductCategory;

import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.adempiere.util.Parameter;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.ISponsorBL;

public class ContractBL implements IContractBL
{

	/**
	 * Checks C_AdvCommissionType, C_AdvCommissionTerm and C_AdvCommissionCondition for a M_ProductCategory setting (in that order).
	 * 
	 * @return the first MProductCategory it finds or null
	 */
	@Override
	public MProductCategory retrieveProductCategory(final I_C_AdvCommissionTerm term, final I_C_Sponsor sponsor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);

		final I_C_AdvComSystem_Type comSystemType = term.getC_AdvComSystem_Type();

		final int productCategoryTypeId = comSystemType.getM_Product_Category_ID();

		if (productCategoryTypeId > 0)
		{
			return MProductCategory.get(ctx, productCategoryTypeId);
		}

		final int productCategoryTermId = term.getM_Product_Category_ID();

		if (productCategoryTermId > 0)
		{
			return MProductCategory.get(ctx, productCategoryTermId);
		}

		final I_C_AdvCommissionCondition contr = term.getC_AdvCommissionCondition();

		final int productCategoryContrId = contr.getM_Product_Category_ID();

		if (productCategoryContrId > 0)
		{
			return MProductCategory.get(ctx, productCategoryContrId);
		}

		return null;
	}

	@Override
	public Object retrieveSponsorParam(final ICommissionContext commissionCtx, final String name)
	{
		final BaseConfig baseConfig = retrieveSponsorBaseConfig(commissionCtx, true);

		return baseConfig.getParameter(name).getValue();
	}

	private BaseConfig retrieveSponsorBaseConfig(final ICommissionContext commissionCtx, final boolean assertTermExists)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final I_C_AdvCommissionTerm term = sponsorBL.retrieveTerm(commissionCtx, assertTermExists);
		if (term == null)
		{
			return null;
		}
		final IParameterBL paramBL = Services.get(IParameterBL.class);
		final List<Parameter> params = paramBL.retrieveParams(term, ICommissionTermDAO.PARAM_TABLE);

		final BaseConfig baseConfig = new BaseConfig(params);
		return baseConfig;
	}

	@Override
	public Object retrieveInstanceParam(final Properties ctx, final I_C_AdvComSystem_Type systemType, final String name, final String trxName)
	{
		final BaseConfig baseConfig = retrieveInstanceBaseConfig(ctx, systemType.getC_AdvComSystem_Type_ID(), trxName);

		return baseConfig.getParameter(name).getValue();
	}

	@Cached
	/* package */ BaseConfig retrieveInstanceBaseConfig(
			final @CacheCtx Properties ctx,
			final int comSystemTypeId,
			final @CacheTrx String trxName)
	{
		final IParameterBL paramBL = Services.get(IParameterBL.class);
		final List<Parameter> params = paramBL.retrieveParams(ctx, I_C_AdvComSystem_Type.Table_Name, comSystemTypeId, MCAdvComSystemType.PARAM_TABLE, trxName);

		final BaseConfig baseConfig = new BaseConfig(params);
		return baseConfig;
	}

	@Override
	public boolean hasInstanceParam(
			final Properties ctx,
			final I_C_AdvComSystem_Type systemType,
			final String name,
			final String trxName)
	{
		final BaseConfig baseConfig = retrieveInstanceBaseConfig(ctx, systemType.getC_AdvComSystem_Type_ID(), trxName);

		return baseConfig.hasParameter(name);
	}

	@Override
	public boolean hasSponsorParam(final ICommissionContext commissionCtx, final String name)
	{
		final BaseConfig baseConfig = retrieveSponsorBaseConfig(commissionCtx, false);

		return baseConfig == null ? false : baseConfig.hasParameter(name);
	}
}
