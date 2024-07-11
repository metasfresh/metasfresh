package de.metas.handlingunits.process.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_Process;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.HUProcessDescriptor.HUProcessDescriptorBuilder;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.util.Collection;
import java.util.List;

public class MHUProcessDAO implements IMHUProcessDAO
{
	private final CCache<Integer, IndexedHUProcessDescriptors> huProcessDescriptors = CCache.newCache(I_M_HU_Process.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	@Override
	public Collection<HUProcessDescriptor> getHUProcessDescriptors()
	{
		return getIndexedHUProcessDescriptors().getAsCollection();
	}

	@Override
	public HUProcessDescriptor getByProcessIdOrNull(@NonNull final AdProcessId processId)
	{
		return getIndexedHUProcessDescriptors().getByProcessIdOrNull(processId);
	}

	private IndexedHUProcessDescriptors getIndexedHUProcessDescriptors()
	{
		return huProcessDescriptors.getOrLoad(0, this::retrieveIndexedHUProcessDescriptors);
	}

	private IndexedHUProcessDescriptors retrieveIndexedHUProcessDescriptors()
	{
		final ImmutableList<HUProcessDescriptor> huProcessDescriptors = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_HU_Process.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_M_HU_Process.class)
				.map(MHUProcessDAO::toHUProcessDescriptor)
				.collect(ImmutableList.toImmutableList());

		return new IndexedHUProcessDescriptors(huProcessDescriptors);
	}

	private static HUProcessDescriptor toHUProcessDescriptor(final I_M_HU_Process huProcessRecord)
	{
		final HUProcessDescriptorBuilder builder = HUProcessDescriptor.builder()
				.processId(AdProcessId.ofRepoId(huProcessRecord.getAD_Process_ID()))
				.internalName(huProcessRecord.getAD_Process().getValue());

		if (huProcessRecord.isApplyToLUs())
		{
			builder.acceptHUUnitType(HuUnitType.LU);
		}
		if (huProcessRecord.isApplyToTUs())
		{
			builder.acceptHUUnitType(HuUnitType.TU);
		}
		if (huProcessRecord.isApplyToCUs())
		{
			builder.acceptHUUnitType(HuUnitType.VHU);
		}

		builder.provideAsUserAction(huProcessRecord.isProvideAsUserAction());
		builder.acceptOnlyTopLevelHUs(huProcessRecord.isApplyToTopLevelHUsOnly());

		return builder.build();
	}

	private static final class IndexedHUProcessDescriptors
	{
		private final ImmutableMap<AdProcessId, HUProcessDescriptor> huProcessDescriptorsByProcessId;

		private IndexedHUProcessDescriptors(final List<HUProcessDescriptor> huProcessDescriptors)
		{
			huProcessDescriptorsByProcessId = Maps.uniqueIndex(huProcessDescriptors, HUProcessDescriptor::getProcessId);
		}

		public Collection<HUProcessDescriptor> getAsCollection()
		{
			return huProcessDescriptorsByProcessId.values();
		}

		public HUProcessDescriptor getByProcessIdOrNull(final AdProcessId processId)
		{
			return huProcessDescriptorsByProcessId.get(processId);
		}
	}
}
