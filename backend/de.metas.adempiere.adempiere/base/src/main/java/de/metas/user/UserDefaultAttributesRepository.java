package de.metas.user;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_UserDefaults_Attribute3;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDefaultAttributesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserId, ImmutableSet<String>> attributes3ByUserId = CCache.<UserId, ImmutableSet<String>>builder()
			.tableName(I_AD_UserDefaults_Attribute3.Table_Name)
			.build();

	public ImmutableSet<String> getAttributes3(@NonNull final UserId userId)
	{
		return attributes3ByUserId.getOrLoad(userId, this::retrieveAttributes3);
	}

	private ImmutableSet<String> retrieveAttributes3(@NonNull final UserId userId)
	{
		final List<String> attributes = queryBL.createQueryBuilder(I_AD_UserDefaults_Attribute3.class)
				.addEqualsFilter(I_AD_UserDefaults_Attribute3.COLUMNNAME_AD_User_ID, userId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UserDefaults_Attribute3.COLUMNNAME_Attributes3)
				.create()
				.listDistinct(I_AD_UserDefaults_Attribute3.COLUMNNAME_Attributes3, String.class);

		return ImmutableSet.copyOf(attributes);
	}

}
