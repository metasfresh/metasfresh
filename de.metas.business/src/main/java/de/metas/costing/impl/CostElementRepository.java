package de.metas.costing.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.X_M_CostElement;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.costing.CostElement;
import de.metas.costing.ICostElementRepository;
import lombok.NonNull;

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

public class CostElementRepository implements ICostElementRepository
{
	@Override
	public CostElement getById(final int costElementId)
	{
		// TODO: enable table level caching for M_CostElement
		final I_M_CostElement ce = InterfaceWrapperHelper.loadOutOfTrx(costElementId, I_M_CostElement.class);
		return toCostElement(ce);
	}

	@Override
	public CostElement getOrCreateMaterialCostElement(final int adClientId, @NonNull final String costingMethod)
	{
		final I_M_CostElement costElement = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostElement.class)
				.addEqualsFilter(I_M_CostElement.COLUMN_AD_Client_ID, adClientId)
				.addEqualsFilter(I_M_CostElement.COLUMN_CostingMethod, costingMethod)
				.addEqualsFilter(I_M_CostElement.COLUMN_CostElementType, X_M_CostElement.COSTELEMENTTYPE_Material)
				.orderBy(I_M_CostElement.COLUMN_AD_Org_ID)
				.create()
				.firstOnly(I_M_CostElement.class);
		if (costElement != null)
		{
			return toCostElement(costElement);
		}

		// Create New
		final I_M_CostElement newCostElement = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		InterfaceWrapperHelper.setValue(newCostElement, I_M_CostElement.COLUMNNAME_AD_Client_ID, adClientId);
		newCostElement.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		String name = Services.get(IADReferenceDAO.class).retrieveListNameTrl(X_M_CostElement.COSTINGMETHOD_AD_Reference_ID, costingMethod);
		if (Check.isEmpty(name, true))
		{
			name = costingMethod;
		}
		newCostElement.setName(name);
		newCostElement.setCostElementType(X_M_CostElement.COSTELEMENTTYPE_Material);
		newCostElement.setCostingMethod(costingMethod);
		newCostElement.setIsCalculated(false);
		InterfaceWrapperHelper.save(newCostElement);

		//
		return toCostElement(newCostElement);
	}

	private static CostElement toCostElement(final I_M_CostElement costElement)
	{
		return CostElement.builder()
				.id(costElement.getM_CostElement_ID())
				.name(costElement.getName())
				.costingMethod(costElement.getCostingMethod())
				.costElementType(costElement.getCostElementType())
				.calculated(costElement.isCalculated())
				.build();
	}

	@Override
	public List<CostElement> getCostElementsWithCostingMethods(final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_CostElement.COLUMN_AD_Client_ID, adClientId)
				.addNotNull(I_M_CostElement.COLUMN_CostingMethod)
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(ce -> toCostElement(ce))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getMaterialCostingMethods(final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_CostElement.COLUMN_AD_Client_ID, adClientId)
				.addNotNull(I_M_CostElement.COLUMN_CostingMethod)
				.addEqualsFilter(I_M_CostElement.COLUMN_CostElementType, X_M_CostElement.COSTELEMENTTYPE_Material)
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(ce -> toCostElement(ce))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getNonCostingMethods(final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_CostElement.COLUMN_AD_Client_ID, adClientId)
				.addEqualsFilter(I_M_CostElement.COLUMN_CostingMethod, null)
				//
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(ce -> toCostElement(ce))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getByCostingMethod(String CostingMethod)
	{
		// modernizing this to get rid of the debug-message
		// org.adempiere.exceptions.AdempiereException: Query does not have a modelClass defined and no 'clazz' was specified as parameter.We need to avoid this case, but for now we are trying to do a force casting
		// in the log
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, CostingMethod)
				//
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(ce -> toCostElement(ce))
				.collect(ImmutableList.toImmutableList());
	}

}
