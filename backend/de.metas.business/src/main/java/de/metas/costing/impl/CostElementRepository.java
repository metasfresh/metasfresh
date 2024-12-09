package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
<<<<<<< HEAD
=======
import de.metas.ad_reference.ADReferenceService;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.cache.CCache;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
<<<<<<< HEAD
=======
import de.metas.i18n.Language;
import de.metas.organization.OrgId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
<<<<<<< HEAD
import org.adempiere.ad.service.IADReferenceDAO;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostElement;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
=======
import java.util.Arrays;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
=======
	private final ADReferenceService adReferenceService;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private final CCache<Integer, IndexedCostElements> cache = CCache.<Integer, IndexedCostElements>builder()
			.tableName(I_M_CostElement.Table_Name)
			.initialCapacity(1)
			.build();

<<<<<<< HEAD
=======
	public CostElementRepository(@NonNull final ADReferenceService adReferenceService) {this.adReferenceService = adReferenceService;}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.map(CostElementRepository::toCostElement)
=======
				.map(CostElementRepository::fromRecord)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	@Override
	public @NonNull CostElement getOrCreateMaterialCostElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		final List<CostElement> costElements = getIndexedCostElements()
				.streamForClientId(clientId)
=======
	public @NonNull CostElement getOrCreateMaterialCostElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		final List<CostElement> costElements = getIndexedCostElements()
				.streamByClientId(clientId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.filter(ce -> ce.isMaterialCostingMethod(costingMethod))
				.collect(ImmutableList.toImmutableList());
		if (costElements.isEmpty())
		{
<<<<<<< HEAD
			return createNewDefaultCostingElement(clientId, costingMethod);
=======
			return createDefaultMaterialCostingElement(clientId, costingMethod);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	private CostElement createNewDefaultCostingElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		final I_M_CostElement newCostElementPO = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		InterfaceWrapperHelper.setValue(newCostElementPO, I_M_CostElement.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		newCostElementPO.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		String name = adReferenceDAO.retrieveListNameTrl(CostingMethod.AD_REFERENCE_ID, costingMethod.getCode());
=======
	private CostElement createDefaultMaterialCostingElement(final ClientId clientId, @NonNull final CostingMethod costingMethod)
	{
		String name = adReferenceService.retrieveListNameTranslatableString(CostingMethod.AD_REFERENCE_ID, costingMethod.getCode())
				.translate(Language.getBaseAD_Language());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (Check.isBlank(name))
		{
			name = costingMethod.name();
		}
<<<<<<< HEAD
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
				.id(CostElementId.ofRepoId(costElement.getM_CostElement_ID()))
				.name(costElement.getName())
				.costingMethod(CostingMethod.ofNullableCode(costElement.getCostingMethod()))
				.costElementType(CostElementType.ofCode(costElement.getCostElementType()))
				.allowUserChangingCurrentCosts(!costElement.isCalculated())
				.clientId(ClientId.ofRepoId(costElement.getAD_Client_ID()))
=======

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.build();
	}

	@Override
<<<<<<< HEAD
	public List<CostElement> getCostElementsWithCostingMethods(final ClientId clientId)
	{
		return getIndexedCostElements()
				.streamForClientId(clientId)
				// .filter(ce -> ce.getCostingMethod() != null) // commenting out because costingMethod is not null
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getMaterialCostingMethods(final ClientId clientId)
	{
		return getIndexedCostElements()
				.streamForClientId(clientId)
				.filter(CostElement::isMaterial)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<CostElement> getByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		return streamByCostingMethod(costingMethod)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
<<<<<<< HEAD
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public Set<CostElementId> getActiveCostElementIds()
	{
		return getIndexedCostElements()
				.stream()
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

<<<<<<< HEAD
	@Override
	public Set<CostElementId> getIdsByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		return streamByCostingMethod(costingMethod)
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<CostElement> getMaterialCostingElementsForCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));
		return getIndexedCostElements()
				.streamForClientId(clientId)
				.filter(ce -> ce.isMaterialCostingMethod(costingMethod))
				.collect(ImmutableList.toImmutableList());
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private Stream<CostElement> streamByCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));
		return getIndexedCostElements()
<<<<<<< HEAD
				.streamForClientId(clientId)
=======
				.streamByClientId(clientId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
		public Stream<CostElement> streamForClientId(@NonNull final ClientId clientId)
=======
		public Stream<CostElement> streamByClientId(@NonNull final ClientId clientId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return stream().filter(ce -> ClientId.equals(ce.getClientId(), clientId));
		}

<<<<<<< HEAD
=======
		public Stream<CostElement> streamByClientIdAndCostingMethod(@NonNull final ClientId clientId, @NonNull final CostingMethod costingMethod)
		{
			return streamByClientId(clientId).filter(ce -> costingMethod.equals(ce.getCostingMethod()));
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		public Stream<CostElement> stream()
		{
			return costElementsById.values().stream();
		}
	}
}
