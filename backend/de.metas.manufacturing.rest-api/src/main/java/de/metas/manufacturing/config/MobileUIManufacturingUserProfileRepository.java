package de.metas.manufacturing.config;

import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.springframework.stereotype.Repository;

@Repository
public class MobileUIManufacturingUserProfileRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserId, MobileUIManufacturingUserProfile> cache = CCache.<UserId, MobileUIManufacturingUserProfile>builder()
			.tableName(I_MobileUI_UserProfile_MFG.Table_Name)
			.build();

	public MobileUIManufacturingUserProfile getByUserId(@NonNull final UserId userId)
	{
		return cache.getOrLoad(userId, this::retrieveByUserId);
	}

	private MobileUIManufacturingUserProfile retrieveByUserId(@NonNull final UserId userId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_MFG.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_MFG.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_MobileUI_UserProfile_MFG.class)
				.map(MobileUIManufacturingUserProfileRepository::fromRecord)
				.orElseGet(() -> newDefault(userId));
	}

	private static MobileUIManufacturingUserProfile fromRecord(@NonNull final I_MobileUI_UserProfile_MFG record)
	{
		return MobileUIManufacturingUserProfile.builder()
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.isScanResourceRequired(record.isScanResourceRequired())
				.build();
	}

	private static MobileUIManufacturingUserProfile newDefault(@NonNull final UserId userId)
	{
		return MobileUIManufacturingUserProfile.builder()
				.userId(userId)
				.isScanResourceRequired(false)
				.build();
	}

}
