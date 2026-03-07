package de.metas.security.mobile_application;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Mobile_Application_Access;
import org.compiere.model.I_Mobile_Application_Action_Access;

public class MobileApplicationPermissionsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<RoleId, MobileApplicationPermissions> mobileApplicationPermissionsCache = CCache.<RoleId, MobileApplicationPermissions>builder()
			.tableName(I_Mobile_Application_Access.Table_Name)
			.build();

	public MobileApplicationPermissions getMobileApplicationPermissions(final RoleId adRoleId)
	{
		return mobileApplicationPermissionsCache.getOrLoad(adRoleId, this::retrieveMobileApplicationPermissions0);
	}

	private MobileApplicationPermissions retrieveMobileApplicationPermissions0(final RoleId adRoleId)
	{
		final ImmutableList<I_Mobile_Application_Access> accessList = queryBL.createQueryBuilder(I_Mobile_Application_Access.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.list();

		final ImmutableListMultimap<MobileApplicationRepoId, I_Mobile_Application_Action_Access> actionAccessByMobileApplicationId = queryBL.createQueryBuilder(I_Mobile_Application_Action_Access.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Mobile_Application_Action_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID()),
						record -> record
				));

		return accessList.stream()
				.map(record -> fromRecord(record, actionAccessByMobileApplicationId))
				.collect(MobileApplicationPermissions.collect());
	}

	private static MobileApplicationPermission fromRecord(
			@NonNull final I_Mobile_Application_Access applicationAccess,
			@NonNull final ImmutableListMultimap<MobileApplicationRepoId, I_Mobile_Application_Action_Access> actionAccessByMobileApplicationId)
	{
		final MobileApplicationRepoId mobileApplicationId = MobileApplicationRepoId.ofRepoId(applicationAccess.getMobile_Application_ID());

		final boolean allowAllActions = applicationAccess.isAllowAllActions();
		final ImmutableSet<MobileApplicationActionId> allowedActionIds;
		if (allowAllActions)
		{
			allowedActionIds = ImmutableSet.of(); // not relevant
		}
		else
		{
			allowedActionIds = actionAccessByMobileApplicationId.get(mobileApplicationId)
					.stream()
					.map(action -> MobileApplicationActionId.ofRepoId(action.getMobile_Application_Action_ID()))
					.collect(ImmutableSet.toImmutableSet());
		}

		return MobileApplicationPermission.builder()
				.mobileApplicationId(mobileApplicationId)
				.allowAccess(true)
				.allowAllActions(allowAllActions)
				.allowedActionIds(allowedActionIds)
				.build();
	}

	public void createMobileApplicationAccess(@NonNull final CreateMobileApplicationAccessRequest request)
	{
		final RoleId roleId = request.getRoleId();
		final MobileApplicationRepoId applicationId = request.getApplicationId();

		I_Mobile_Application_Access accessRecord = queryBL
				.createQueryBuilder(I_Mobile_Application_Access.class)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_Mobile_Application_ID, applicationId)
				.create()
				.firstOnly(I_Mobile_Application_Access.class);

		if (accessRecord == null)
		{
			accessRecord = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Access.class);
			InterfaceWrapperHelper.setValue(accessRecord, I_Mobile_Application_Access.COLUMNNAME_AD_Client_ID, request.getClientId().getRepoId());
			accessRecord.setAD_Org_ID(request.getOrgId().getRepoId());
			accessRecord.setAD_Role_ID(roleId.getRepoId());
			accessRecord.setMobile_Application_ID(applicationId.getRepoId());
			accessRecord.setIsAllowAllActions(true);
		}

		accessRecord.setIsActive(true);

		InterfaceWrapperHelper.save(accessRecord);
	}

	public void deleteMobileApplicationAccess(@NonNull final MobileApplicationRepoId applicationId)
	{
		queryBL.createQueryBuilder(I_Mobile_Application_Access.class)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_Mobile_Application_ID, applicationId)
				.create()
				.delete();
	}

}
