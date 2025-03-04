package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.util.Comparator;

@Repository
public class HULabelConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HULabelConfigMap> cache = CCache.<Integer, HULabelConfigMap>builder()
			.tableName(I_M_HU_Label_Config.Table_Name)
			.initialCapacity(1)
			.build();

	public ExplainedOptional<HULabelConfig> getFirstMatching(HULabelConfigQuery query)
	{
		return getMap().getFirstMatching(query);
	}

	private HULabelConfigMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private HULabelConfigMap retrieveMap()
	{
		ImmutableList<HULabelConfigRoute> list = queryBL.createQueryBuilder(I_M_HU_Label_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(HULabelConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new HULabelConfigMap(list);
	}

	public static HULabelConfigRoute fromRecord(final I_M_HU_Label_Config record)
	{
		return HULabelConfigRoute.builder()
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.sourceDocType(HULabelSourceDocType.ofNullableCode(record.getHU_SourceDocType()))
				.acceptHUUnitTypes(extractAcceptedHuUnitTypes(record))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.config(HULabelConfig.builder()
						.printFormatProcessId(AdProcessId.ofRepoId(record.getLabelReport_Process_ID()))
						.autoPrint(record.isAutoPrint())
						.autoPrintCopies(PrintCopies.ofIntOrOne(record.getAutoPrintCopies()))
						.build())
				.build();
	}

	public static ImmutableSet<HuUnitType> extractAcceptedHuUnitTypes(final I_M_HU_Label_Config record)
	{
		final ImmutableSet.Builder<HuUnitType> builder = ImmutableSet.builder();
		if (record.isApplyToLUs())
		{
			builder.add(HuUnitType.LU);
		}
		if (record.isApplyToTUs())
		{
			builder.add(HuUnitType.TU);
		}
		if (record.isApplyToCUs())
		{
			builder.add(HuUnitType.VHU);
		}

		return builder.build();
	}

	//
	//
	//
	//
	//

	private static class HULabelConfigMap
	{
		private final ImmutableList<HULabelConfigRoute> orderedList;

		public HULabelConfigMap(final ImmutableList<HULabelConfigRoute> list)
		{
			this.orderedList = list.stream()
					.sorted(Comparator.comparing(HULabelConfigRoute::getSeqNo))
					.collect(ImmutableList.toImmutableList());
		}

		public ExplainedOptional<HULabelConfig> getFirstMatching(@NonNull final HULabelConfigQuery query)
		{
			return orderedList.stream()
					.filter(route -> route.isMatching(query))
					.map(HULabelConfigRoute::getConfig)
					.findFirst()
					.map(ExplainedOptional::of)
					.orElseGet(() -> ExplainedOptional.emptyBecause("No HU Label Config found for " + query));
		}
	}
}
