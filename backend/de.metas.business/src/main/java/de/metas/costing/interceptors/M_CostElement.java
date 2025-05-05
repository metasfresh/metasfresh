package de.metas.costing.interceptors;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.ModelValidator;

import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_M_CostElement.class)
class M_CostElement
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAcctSchemaDAO acctSchemaDAO;

	public M_CostElement(@NonNull final IAcctSchemaDAO acctSchemaDAO)
	{
		this.acctSchemaDAO = acctSchemaDAO;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_CostElement costElement)
	{
		costElement.setAD_Org_ID(OrgId.ANY.getRepoId());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_M_CostElement costElement)
	{
		final CostElementType costElementType = CostElementType.ofCode(costElement.getCostElementType());
		final CostingMethod costingMethod = CostingMethod.ofNullableCode(costElement.getCostingMethod());
		final boolean isCostingMethod = costElementType.isMaterial() && costingMethod != null;
		if (!isCostingMethod)
		{
			return;
		}

		// Costing Methods on AS level
		final ClientId clientId = ClientId.ofRepoId(costElement.getAD_Client_ID());
		for (final AcctSchema as : acctSchemaDAO.getAllByClient(clientId))
		{
			if (as.getCosting().getCostingMethod().equals(costingMethod))
			{
				throw new AdempiereException("@CannotDeleteUsed@ @C_AcctSchema_ID@");
			}
		}

		// Costing Methods on PC level
		// FIXME: this shall go in some DAO/Repository
		final String productCategoriesUsingCostingMethod = queryBL
				.createQueryBuilder(I_M_Product_Category_Acct.class)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMN_CostingMethod, costingMethod.getCode())
				.andCollect(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, I_M_Product_Category.class)
				.orderBy(I_M_Product_Category.COLUMNNAME_Name)
				.create()
				.setLimit(50)
				.listDistinct(I_M_Product_Category.COLUMNNAME_Name, String.class)
				.stream()
				.collect(Collectors.joining(", "));
		if (!Check.isEmpty(productCategoriesUsingCostingMethod, true))
		{
			throw new AdempiereException("@CannotDeleteUsed@ @M_Product_Category_ID@ (" + productCategoriesUsingCostingMethod + ")");
		}

	}    // beforeDelete

}
