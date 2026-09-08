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
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public class MobileUIManufacturingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final MobileUIManufacturingConfig DEFAULT_CONFIG = MobileUIManufacturingConfig.builder()
			.isScanResourceRequired(OptionalBoolean.FALSE)
			.isAllowIssuingAnyHU(OptionalBoolean.FALSE)
			.isAllowEmptyingHUs(OptionalBoolean.TRUE)
			.isConfirmEmptyingHU(OptionalBoolean.TRUE)
			.build();

	private final CCache<UserId, Optional<MobileUIManufacturingConfig>> userConfigsCache = CCache.<UserId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_UserProfile_MFG.Table_Name)
			.build();

	private final CCache<ClientId, Optional<MobileUIManufacturingConfig>> globalConfigsCache = CCache.<ClientId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_MFG_Config.Table_Name)
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
		//noinspection DataFlowIssue
		return userConfigsCache.getOrLoad(userId, this::retrieveUserConfig).orElse(null);
	}

	public MobileUIManufacturingConfig getGlobalConfig(@NonNull final ClientId clientId)
	{
		//noinspection DataFlowIssue
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
		return retrieveGlobalConfigRecord(clientId)
				.map(MobileUIManufacturingConfigRepository::fromRecord);
	}

	private Optional<I_MobileUI_MFG_Config> retrieveGlobalConfigRecord(@NonNull final ClientId clientId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_MFG_Config.COLUMNNAME_AD_Client_ID, clientId)
				.create()
				.firstOnlyOptional(I_MobileUI_MFG_Config.class);
	}

	private static MobileUIManufacturingConfig fromRecord(@NonNull final I_MobileUI_MFG_Config record)
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.ofBoolean(record.isScanResourceRequired()))
				.isAllowIssuingAnyHU(OptionalBoolean.ofBoolean(record.isAllowIssuingAnyHU()))
				.isAllowEmptyingHUs(OptionalBoolean.ofBoolean(record.isAllowEmptyingHUs()))
				.isConfirmEmptyingHU(OptionalBoolean.ofBoolean(record.isConfirmEmptyingHU()))
				.build();
	}

	private static void updateGlobalRecord(@NonNull final I_MobileUI_MFG_Config record, @NonNull final MobileUIManufacturingConfig from)
	{
		record.setIsScanResourceRequired(from.getIsScanResourceRequired().isTrue());
		record.setIsAllowIssuingAnyHU(from.getIsAllowIssuingAnyHU().isTrue());
		record.setIsAllowEmptyingHUs(from.getIsAllowEmptyingHUs().isTrue());
		record.setIsConfirmEmptyingHU(from.getIsConfirmEmptyingHU().isTrue());
	}

	public void saveUserConfig(@NonNull final MobileUIManufacturingConfig newConfig, @NonNull final UserId userId)
	{
		final I_MobileUI_UserProfile_MFG record = retrieveUserConfigRecord(userId).orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class));
		record.setIsActive(true);
		record.setAD_User_ID(userId.getRepoId());
		updateRecord(record, newConfig);
		InterfaceWrapperHelper.save(record);
	}

	/**
	 * Test-support entry point: {@code MobileUI_MFG_Config} has no production save path (only the
	 * per-user profile is user-editable), but the frontend-testing masterdata harness needs one to
	 * deterministically drive {@code IsAllowEmptyingHUs} / {@code IsConfirmEmptyingHU} for E2E specs
	 * (see {@code MobileConfigManufacturingCommand}). Upserts the single row for the given client.
	 * Mirrors the pre-existing {@link #saveUserConfig}, whose only caller is likewise that harness.
	 */
	public void saveGlobalConfig(@NonNull final MobileUIManufacturingConfig newConfig, @NonNull final ClientId clientId)
	{
		// The generated model exposes getAD_Client_ID() but deliberately NO setter, so a new record's
		// AD_Client_ID is assigned by the framework from the ambient context. To make sure a new row lands
		// under the passed clientId (not whatever the caller's ambient context happens to be), the record is
		// created against a local context that has clientId forced into it -- the same idiom used e.g. by
		// C_Flatrate_Term / PrintingQueueBL / InboundEMailService / WorkPackageQueue.
		final I_MobileUI_MFG_Config record = retrieveGlobalConfigRecord(clientId).orElseGet(() -> newGlobalConfigRecord(clientId));
		record.setIsActive(true);
		updateGlobalRecord(record, newConfig);
		InterfaceWrapperHelper.save(record);
	}

	private static I_MobileUI_MFG_Config newGlobalConfigRecord(@NonNull final ClientId clientId)
	{
		final Properties localCtx = Env.deriveCtx(Env.getCtx());
		Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, clientId.getRepoId());

		return InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class, localCtx);
	}

}
