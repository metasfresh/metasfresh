package de.metas.mobile.application.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.mobile.application.MobileApplicationAction;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.I_Mobile_Application_Action;
import org.compiere.model.I_Mobile_Application_Trl;
import org.compiere.util.DB;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MobileApplicationInfoRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<Integer, MobileApplicationInfoMap> cache = CCache.<Integer, MobileApplicationInfoMap>builder()
			.tableName(I_Mobile_Application.Table_Name)
			.build();

	private static final String FUNCTION_update_Mobile_Application_TRLs = "update_Mobile_Application_TRLs";

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
		final List<I_Mobile_Application> records = queryBL.createQueryBuilder(I_Mobile_Application.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final ImmutableListMultimap<MobileApplicationRepoId, I_Mobile_Application_Trl> recordTrls = queryBL.createQueryBuilder(I_Mobile_Application_Trl.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						MobileApplicationInfoRepository::extractId,
						recordTrl -> recordTrl
				));

		final ImmutableListMultimap<MobileApplicationRepoId, MobileApplicationAction> actions = queryBL.createQueryBuilder(I_Mobile_Application_Action.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						MobileApplicationInfoRepository::extractId,
						MobileApplicationInfoRepository::fromRecord
				));

		return records.stream()
				.map(record -> fromRecord(record, recordTrls, actions))
				.collect(GuavaCollectors.collectUsingListAccumulator(MobileApplicationInfoMap::new));
	}

	private static MobileApplicationAction fromRecord(@NonNull final I_Mobile_Application_Action record)
	{
		return MobileApplicationAction.builder()
				.id(MobileApplicationActionId.ofRepoId(record.getMobile_Application_Action_ID()))
				.internalName(StringUtils.trimBlankToOptional(record.getInternalName()).orElseThrow(() -> new AdempiereException("No internal name: " + record)))
				.build();
	}

	private static MobileApplicationInfo fromRecord(
			@NonNull final I_Mobile_Application record,
			@NonNull final ImmutableListMultimap<MobileApplicationRepoId, I_Mobile_Application_Trl> recordTrls,
			final ImmutableListMultimap<MobileApplicationRepoId, MobileApplicationAction> actions)
	{
		final MobileApplicationRepoId repoId = extractId(record);

		return MobileApplicationInfo.builder()
				.repoId(repoId)
				.id(MobileApplicationId.ofString(record.getValue()))
				.caption(extractNameTrl(record, recordTrls.get(repoId)))
				.actions(actions.get(repoId))
				.showInMainMenu(record.isShowInMainMenu())
				.build();
	}

	private static ITranslatableString extractNameTrl(@NonNull final I_Mobile_Application record,
													  @NonNull final ImmutableList<I_Mobile_Application_Trl> recordTrls)
	{
		final HashMap<String, String> trlMap = new HashMap<>();
		for (final I_Mobile_Application_Trl recordTrl : recordTrls)
		{
			final String adLanguage = recordTrl.getAD_Language();
			final String nameTrl = recordTrl.isUseCustomization()
					? StringUtils.trimBlankToOptional(recordTrl.getName_Customized()).orElseGet(recordTrl::getName)
					: recordTrl.getName();
			trlMap.put(adLanguage, nameTrl);
		}

		return TranslatableStrings.ofMap(trlMap, record.getName());
	}

	private static @NotNull MobileApplicationRepoId extractId(final I_Mobile_Application record) {return MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID());}

	private static @NotNull MobileApplicationRepoId extractId(final I_Mobile_Application_Trl recordTrl) {return MobileApplicationRepoId.ofRepoId(recordTrl.getMobile_Application_ID());}

	private static MobileApplicationRepoId extractId(final I_Mobile_Application_Action record) {return MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID());}

	public void updateMobileApplicationTrl(final MobileApplicationRepoId mobileApplicationRepoId, @NonNull final String adLanguage)
	{

		DB.executeFunctionCallEx(
				ITrx.TRXNAME_ThreadInherited,
				addUpdateFunctionCall(FUNCTION_update_Mobile_Application_TRLs, mobileApplicationRepoId, adLanguage),
				null);
	}

	@SuppressWarnings("SameParameterValue")
	private String addUpdateFunctionCall(final String functionCall, final MobileApplicationRepoId mobileApplicationRepoId, final String adLanguage)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + mobileApplicationRepoId.getRepoId() + "," + DB.TO_STRING(adLanguage) + ") ";
	}

	//
	//
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
