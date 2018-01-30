package de.metas.costing.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostElement;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
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
	private final CCache<Integer, IndexedCostElements> cache = CCache.newCache(I_M_CostElement.Table_Name + "#All", 1, 0);

	private IndexedCostElements getIndexedCostElements()
	{
		return cache.getOrLoad(0, () -> retrieveIndexedCostElements());
	}

	private IndexedCostElements retrieveIndexedCostElements()
	{
		final ImmutableList<CostElement> costElements = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(CostElementRepository::toCostElement)
				.collect(ImmutableList.toImmutableList());
		return new IndexedCostElements(costElements);
	}

	@Override
	public CostElement getById(final int costElementId)
	{
		return getIndexedCostElements().getByIdOrNull(costElementId);
	}

	@Override
	public CostElement getOrCreateMaterialCostElement(final int adClientId, @NonNull final CostingMethod costingMethod)
	{
		final CostElement costElement = getIndexedCostElements()
				.stream()
				.filter(ce -> ce.getAdClientId() == adClientId)
				.filter(ce -> ce.getCostingMethod() == costingMethod)
				.filter(ce -> ce.getCostElementType() == CostElementType.Material)
				.collect(GuavaCollectors.singleElementOrNullOrThrow(costElements -> new AdempiereException("Only one cost element was expected but got " + costElements)));
		if (costElement != null)
		{
			return costElement;
		}

		return createNewDefaultCostingElement(adClientId, costingMethod);
	}

	private CostElement createNewDefaultCostingElement(final int adClientId, @NonNull final CostingMethod costingMethod)
	{
		final I_M_CostElement newCostElementPO = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		InterfaceWrapperHelper.setValue(newCostElementPO, I_M_CostElement.COLUMNNAME_AD_Client_ID, adClientId);
		newCostElementPO.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		String name = Services.get(IADReferenceDAO.class).retrieveListNameTrl(CostingMethod.AD_REFERENCE_ID, costingMethod.getCode());
		if (Check.isEmpty(name, true))
		{
			name = costingMethod.name();
		}
		newCostElementPO.setName(name);
		newCostElementPO.setCostElementType(CostElementType.Material.getCode());
		newCostElementPO.setCostingMethod(costingMethod.getCode());
		newCostElementPO.setIsCalculated(false);
		InterfaceWrapperHelper.save(newCostElementPO);
		// assume cache will be automatically reset after save/trx commit

		//
		return toCostElement(newCostElementPO);
	}

	private static CostElement toCostElement(final I_M_CostElement costElement)
	{
		return CostElement.builder()
				.id(costElement.getM_CostElement_ID())
				.name(costElement.getName())
				.costingMethod(CostingMethod.ofNullableCode(costElement.getCostingMethod()))
				.costElementType(CostElementType.ofCode(costElement.getCostElementType()))
				.calculated(costElement.isCalculated())
				.adClientId(costElement.getAD_Client_ID())
				.build();
	}

	@Override
	public List<CostElement> getCostElementsWithCostingMethods(final int adClientId)
	{
		return getIndexedCostElements()
				.stream()
				.filter(ce -> ce.getAdClientId() == adClientId)
				.filter(ce -> ce.getCostingMethod() != null)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getMaterialCostingMethods(final int adClientId)
	{
		return getIndexedCostElements()
				.stream()
				.filter(ce -> ce.getAdClientId() == adClientId)
				.filter(ce -> ce.isMaterialCostingMethod())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getNonCostingMethods(final int adClientId)
	{
		return getIndexedCostElements()
				.stream()
				.filter(ce -> ce.getAdClientId() == adClientId)
				.filter(ce -> ce.getCostingMethod() == null)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());

		return getIndexedCostElements()
				.stream()
				.filter(ce -> ce.getAdClientId() == adClientId)
				.filter(ce -> ce.getCostingMethod() == costingMethod)
				.collect(ImmutableList.toImmutableList());
	}

	private static class IndexedCostElements
	{
		private final ImmutableMap<Integer, CostElement> costElementsById;

		private IndexedCostElements(final Collection<CostElement> costElements)
		{
			costElementsById = Maps.uniqueIndex(costElements, CostElement::getId);
		}

		public CostElement getByIdOrNull(final int id)
		{
			return costElementsById.get(id);
		}

		public Stream<CostElement> stream()
		{
			return costElementsById.values().stream();
		}
	}
}
