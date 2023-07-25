package de.metas.sales_region;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_SalesRegion;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class SalesRegionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<Integer, SalesRegionsMap> cache = CCache.<Integer, SalesRegionsMap>builder()
			.tableName(I_C_SalesRegion.Table_Name)
			.build();

	public SalesRegion getById(final SalesRegionId salesRegionId)
	{
		return getMap().getById(salesRegionId);
	}

	private SalesRegionsMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private SalesRegionsMap retrieveMap()
	{
		final ImmutableList<SalesRegion> list = queryBL.createQueryBuilder(I_C_SalesRegion.class)
				.create()
				.stream()
				.map(SalesRegionRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new SalesRegionsMap(list);
	}

	private static SalesRegion fromRecord(final I_C_SalesRegion record)
	{
		return SalesRegion.builder()
				.id(SalesRegionId.ofRepoId(record.getC_SalesRegion_ID()))
				.value(record.getValue())
				.name(record.getName())
				.isActive(record.isActive())
				.salesRepId(UserId.ofRepoIdOrNullIfSystem(record.getSalesRep_ID()))
				.build();
	}

	public Optional<SalesRegion> getBySalesRepId(@NonNull final UserId salesRepId)
	{
		return getMap().stream()
				.filter(salesRegion -> salesRegion.isActive()
						&& UserId.equals(salesRegion.getSalesRepId(), salesRepId))
				.sorted(Comparator.comparing(SalesRegion::getId).reversed())
				.findFirst();
	}

	//
	//
	//

	private static class SalesRegionsMap
	{
		private final ImmutableMap<SalesRegionId, SalesRegion> byId;

		public SalesRegionsMap(final List<SalesRegion> list)
		{
			this.byId = Maps.uniqueIndex(list, SalesRegion::getId);
		}

		public SalesRegion getById(final SalesRegionId salesRegionId)
		{
			final SalesRegion salesRegion = byId.get(salesRegionId);
			if (salesRegion == null)
			{
				throw new AdempiereException("No sales region found for " + salesRegionId);
			}
			return salesRegion;
		}

		public Stream<SalesRegion> stream() {return byId.values().stream();}
	}
}
