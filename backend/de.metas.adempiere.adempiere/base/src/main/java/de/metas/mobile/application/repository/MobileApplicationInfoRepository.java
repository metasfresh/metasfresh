package de.metas.mobile.application.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Mobile_Application;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MobileApplicationInfoRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<Integer, MobileApplicationInfoMap> cache = CCache.<Integer, MobileApplicationInfoMap>builder()
			.tableName(I_Mobile_Application.Table_Name)
			.build();

	public MobileApplicationInfo getById(final MobileApplicationId applicationId)
	{
		return getMap().getById(applicationId);
	}

	public List<MobileApplicationInfo> getAllActive()
	{
		return getMap().toList();
	}

	private MobileApplicationInfoMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private MobileApplicationInfoMap retrieveMap()
	{
		return queryBL.createQueryBuilder(I_Mobile_Application.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(MobileApplicationInfoRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(MobileApplicationInfoMap::new));
	}

	private static MobileApplicationInfo fromRecord(final I_Mobile_Application record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return MobileApplicationInfo.builder()
				.repoId(MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID()))
				.id(MobileApplicationId.ofString(record.getValue()))
				.caption(trls.getColumnTrl(I_Mobile_Application.COLUMNNAME_Name, record.getName()))
				.showInMainMenu(record.isShowInMainMenu())
				.build();
	}

	//
	//
	//

	private static class MobileApplicationInfoMap
	{
		@NonNull private final ImmutableList<MobileApplicationInfo> list;
		@NonNull private final ImmutableMap<MobileApplicationId, MobileApplicationInfo> byApplicationId;

		private MobileApplicationInfoMap(@NonNull final List<MobileApplicationInfo> list)
		{
			this.list = ImmutableList.copyOf(list);
			this.byApplicationId = Maps.uniqueIndex(list, MobileApplicationInfo::getId);
		}

		public MobileApplicationInfo getById(final MobileApplicationId applicationId)
		{
			final MobileApplicationInfo applicationInfo = byApplicationId.get(applicationId);
			if (applicationInfo == null)
			{
				throw new AdempiereException("No application info found for " + applicationId);
			}
			return applicationInfo;
		}

		public List<MobileApplicationInfo> toList()
		{
			return list;
		}
	}
}
