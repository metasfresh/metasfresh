package de.metas.manufacturing.config;

import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MobileUIManufacturingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final MobileUIManufacturingConfig DEFAULT_CONFIG = MobileUIManufacturingConfig.builder()
			.isScanResourceRequired(OptionalBoolean.FALSE)
			.isAllowIssuingAnyHU(OptionalBoolean.FALSE)
			.build();

	private final CCache<UserId, Optional<MobileUIManufacturingConfig>> userConfigsCache = CCache.<UserId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_UserProfile_MFG.Table_Name)
			.build();

	private final CCache<ClientId, Optional<MobileUIManufacturingConfig>> globalConfigsCache = CCache.<ClientId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_UserProfile_MFG.Table_Name)
			.build();

	public MobileUIManufacturingConfig getConfig(@NonNull final UserId userId, @NonNull final ClientId clientId)
	{
		return MobileUIManufacturingConfig.merge(
						getUserConfig(userId),
						getGlobalConfig(clientId),
						DEFAULT_CONFIG
				)
				.orElse(DEFAULT_CONFIG);
	}

	private MobileUIManufacturingConfig getUserConfig(@NonNull final UserId userId)
	{
		return userConfigsCache.getOrLoad(userId, this::retrieveUserConfig).orElse(null);
	}

	public MobileUIManufacturingConfig getGlobalConfig(@NonNull final ClientId clientId)
	{
		return globalConfigsCache.getOrLoad(clientId, this::retrieveGlobalConfig).orElse(null);
	}

	private Optional<MobileUIManufacturingConfig> retrieveUserConfig(@NonNull final UserId userId)
	{
		return retrieveUserConfigRecord(userId)
				.filter(I_MobileUI_UserProfile_MFG::isActive)
				.map(MobileUIManufacturingConfigRepository::fromRecord);
	}

	private Optional<I_MobileUI_UserProfile_MFG> retrieveUserConfigRecord(final @NonNull UserId userId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_MFG.class)
				//.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_MFG.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_MobileUI_UserProfile_MFG.class);
	}

	private static MobileUIManufacturingConfig fromRecord(@NonNull final I_MobileUI_UserProfile_MFG record)
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.ofNullableString(record.getIsScanResourceRequired()))
				.isAllowIssuingAnyHU(OptionalBoolean.ofNullableString(record.getIsAllowIssuingAnyHU()))
				.build();
	}

	private static void updateRecord(@NonNull final I_MobileUI_UserProfile_MFG record, @NonNull final MobileUIManufacturingConfig from)
	{
		record.setIsScanResourceRequired(from.getIsScanResourceRequired().toBooleanString());
		record.setIsAllowIssuingAnyHU(from.getIsAllowIssuingAnyHU().toBooleanString());
	}

	private Optional<MobileUIManufacturingConfig> retrieveGlobalConfig(@NonNull final ClientId clientId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_MFG_Config.COLUMNNAME_AD_Client_ID, clientId)
				.create()
				.firstOnlyOptional(I_MobileUI_MFG_Config.class)
				.map(MobileUIManufacturingConfigRepository::fromRecord);
	}

	private static MobileUIManufacturingConfig fromRecord(@NonNull final I_MobileUI_MFG_Config record)
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.ofBoolean(record.isScanResourceRequired()))
				.isAllowIssuingAnyHU(OptionalBoolean.ofBoolean(record.isAllowIssuingAnyHU()))
				.build();
	}

	public void saveUserConfig(@NonNull final MobileUIManufacturingConfig newConfig, @NonNull final UserId userId)
	{
		final I_MobileUI_UserProfile_MFG record = retrieveUserConfigRecord(userId).orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class));
		record.setIsActive(true);
		record.setAD_User_ID(userId.getRepoId());
		updateRecord(record, newConfig);
		InterfaceWrapperHelper.save(record);
	}

}
