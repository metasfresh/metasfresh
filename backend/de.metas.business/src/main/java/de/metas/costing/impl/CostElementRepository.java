package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.CCache;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.i18n.Language;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostElement;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

@Component
public class CostElementRepository implements ICostElementRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ADReferenceService adReferenceService;

	private final CCache<Integer, IndexedCostElements> cache = CCache.<Integer, IndexedCostElements>builder()
			.tableName(I_M_CostElement.Table_Name)
			.initialCapacity(1)
			.build();

	public CostElementRepository(@NonNull final ADReferenceService adReferenceService) {this.adReferenceService = adReferenceService;}

	private IndexedCostElements getIndexedCostElements()
	{
		return cache.getOrLoad(0, this::retrieveIndexedCostElements);
	}

	private IndexedCostElements retrieveIndexedCostElements()
	{
		final ImmutableList<CostElement> costElements = queryBL
				.createQueryBuilder(I_M_CostElement.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_CostElement.COLUMN_M_CostElement_ID)
				.create()
				.stream(I_M_CostElement.class)
				.map(CostElementRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
		return new IndexedCostElements(costElements);
	}

	@Override
	public Optional<CostElement> getByIdIfExists(@NonNull final CostElementId costElementId)
	{
		return getIndexedCostElements().getById(costElementId);
	}

	@Override
	@NonNull
	public CostElement getById(@NonNull final CostElementId costElementId)
	{
		return getByIdIfExists(costElementId)
				.orElseThrow(() -> new AdempiereException("No active cost element found for " + costElementId));
	}

	public @NonNull CostElement getOrCreateMaterialCostElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		final List<CostElement> costElements = getIndexedCostElements()
				.streamByClientId(clientId)
				.filter(ce -> ce.isMaterialCostingMethod(costingMethod))
				.collect(ImmutableList.toImmutableList());
		if (costElements.isEmpty())
		{
			return createDefaultMaterialCostingElement(clientId, costingMethod);
		}
		else if (costElements.size() == 1)
		{
			return costElements.get(0);
		}
		else
		{
			throw new AdempiereException("Only one cost element was expected but got " + costElements);
		}
	}

	private CostElement createDefaultMaterialCostingElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		String name = adReferenceService.retrieveListNameTranslatableString(CostingMethod.AD_REFERENCE_ID, costingMethod.getCode())
				.translate(Language.getBaseAD_Language());
		if (Check.isBlank(name))
		{
			name = costingMethod.name();
		}

		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		InterfaceWrapperHelper.setValue(record, I_M_CostElement.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(costingMethod.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.save(record);
		// assume cache will be automatically reset after save/trx commit

		//
		return fromRecord(record);
	}

	private static CostElement fromRecord(@NonNull final I_M_CostElement record)
	{
		return CostElement.builder()
				.id(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.name(record.getName())
				.costingMethod(CostingMethod.ofCode(record.getCostingMethod()))
				.costElementType(CostElementType.ofCode(record.getCostElementType()))
				.allowUserChangingCurrentCosts(!record.isCalculated())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.build();
	}

	@Override
	public List<CostElement> getByTypes(@NonNull final ClientId clientId, @NonNull final CostElementType... types)
	{
		if (types.length == 0)
		{
			return ImmutableList.of();
		}

		final List<CostElementType> typesList = Arrays.asList(types);

		return getIndexedCostElements()
				.streamByClientId(clientId)
				.filter(costElement -> typesList.contains(costElement.getCostElementType()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		return streamByCostingMethod(costingMethod)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getByClientId(@NonNull final ClientId clientId)
	{
		return getIndexedCostElements()
				.streamByClientId(clientId)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Set<CostElementId> getIdsByClientId(@NonNull final ClientId clientId)
	{
		return getIndexedCostElements()
				.streamByClientId(clientId)
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Set<CostElementId> getIdsByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));

		return getIndexedCostElements()
				.streamByClientIdAndCostingMethod(clientId, costingMethod)
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<CostElement> getMaterialCostingElementsForCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));
		return getIndexedCostElements()
				.streamByClientId(clientId)
				.filter(ce -> ce.isMaterialCostingMethod(costingMethod))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Set<CostElementId> getActiveCostElementIds()
	{
		return getIndexedCostElements()
				.stream()
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private Stream<CostElement> streamByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));
		return getIndexedCostElements()
				.streamByClientId(clientId)
				.filter(ce -> ce.getCostingMethod() == costingMethod);
	}

	private static class IndexedCostElements
	{
		private final ImmutableMap<CostElementId, CostElement> costElementsById;

		private IndexedCostElements(final Collection<CostElement> costElements)
		{
			costElementsById = Maps.uniqueIndex(costElements, CostElement::getId);
		}

		public Optional<CostElement> getById(final CostElementId id)
		{
			return Optional.ofNullable(costElementsById.get(id));
		}

		public Stream<CostElement> streamByClientId(@NonNull final ClientId clientId)
		{
			return stream().filter(ce -> ClientId.equals(ce.getClientId(), clientId));
		}

		public Stream<CostElement> streamByClientIdAndCostingMethod(@NonNull final ClientId clientId, @NonNull final CostingMethod costingMethod)
		{
			return streamByClientId(clientId).filter(ce -> costingMethod.equals(ce.getCostingMethod()));
		}

		public Stream<CostElement> stream()
		{
			return costElementsById.values().stream();
		}
	}
}
