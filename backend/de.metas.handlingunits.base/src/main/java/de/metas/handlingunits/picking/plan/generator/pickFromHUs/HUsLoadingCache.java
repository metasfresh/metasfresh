package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HUsLoadingCache
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Params
	private final ImmutableSet<AttributeCode> attributesToConsider;

	//
	// State
	private final IAttributeStorageFactory attributesFactory;
	private final HashMap<HuId, I_M_HU> husById = new HashMap<>();
	private final HashMap<HuId, HuId> topLevelHUIds = new HashMap<>();
	private final HashMap<HuId, ImmutableSet<HuId>> vhuIds = new HashMap<>();
	private final HashMap<HuId, ImmutableAttributeSet> huAttributesCache = new HashMap<>();

	public HUsLoadingCache(@NonNull final ImmutableSet<AttributeCode> attributesToConsider)
	{
		Check.assumeNotEmpty(attributesToConsider, "attributesToConsider not empty");

		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

		this.attributesToConsider = attributesToConsider;
		this.attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
	}

	public I_M_HU getHUById(final HuId huId)
	{
		return husById.computeIfAbsent(huId, handlingUnitsBL::getById);
	}

	public Collection<I_M_HU> getHUsByIds(final Set<HuId> huIds)
	{
		return CollectionUtils.getAllOrLoad(husById, huIds, handlingUnitsBL::getByIdsReturningMap);
	}

	private void addToCache(final List<I_M_HU> hus)
	{
		hus.forEach(this::addToCache);
	}

	private void addToCache(final I_M_HU hu)
	{
		husById.put(extractHUId(hu), hu);
	}

	public void warmUpCacheForHuIds(final Collection<HuId> huIds)
	{
		CollectionUtils.getAllOrLoad(husById, huIds, this::retrieveHUs);
	}

	private Map<HuId, I_M_HU> retrieveHUs(final Collection<HuId> huIds)
	{
		return Maps.uniqueIndex(handlingUnitsBL.getByIds(huIds), HUsLoadingCache::extractHUId);
	}

	public HuId getTopLevelHUId(final HuId huId)
	{
		return topLevelHUIds.computeIfAbsent(huId, this::retrieveTopLevelHUId);
	}

	private HuId retrieveTopLevelHUId(final HuId huId)
	{
		final I_M_HU hu = getHUById(huId);
		final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu).getTopLevelHU();
		addToCache(topLevelHU);
		return extractHUId(topLevelHU);
	}

	public ImmutableSet<HuId> getVHUIds(final HuId huId) {return vhuIds.computeIfAbsent(huId, this::retrieveVHUIds);}

	private ImmutableSet<HuId> retrieveVHUIds(final HuId huId)
	{
		final I_M_HU hu = getHUById(huId);
		final List<I_M_HU> vhus = handlingUnitsBL.getVHUs(hu);
		addToCache(vhus);
		return extractHUIds(vhus);
	}

	private static ImmutableSet<HuId> extractHUIds(final List<I_M_HU> hus) {return hus.stream().map(HUsLoadingCache::extractHUId).collect(ImmutableSet.toImmutableSet());}

	private static HuId extractHUId(final I_M_HU hu) {return HuId.ofRepoId(hu.getM_HU_ID());}

	public ImmutableAttributeSet getHUAttributes(final HuId huId)
	{
		return huAttributesCache.computeIfAbsent(huId, this::retrieveHUAttributes);
	}

	private ImmutableAttributeSet retrieveHUAttributes(final HuId huId)
	{
		final I_M_HU hu = getHUById(huId);
		final IAttributeStorage attributes = attributesFactory.getAttributeStorage(hu);
		return ImmutableAttributeSet.createSubSet(attributes, a -> attributesToConsider.contains(AttributeCode.ofString(a.getValue())));
	}

	public LocatorId getLocatorIdByHuId(final HuId huId)
	{
		final I_M_HU hu = getHUById(huId);
		return IHandlingUnitsBL.extractLocatorId(hu);
	}
}
