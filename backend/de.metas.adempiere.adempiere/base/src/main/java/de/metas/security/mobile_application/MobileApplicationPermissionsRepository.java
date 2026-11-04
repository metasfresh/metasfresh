package de.metas.security.mobile_application;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Mobile_Application_Access;
import org.compiere.model.I_Mobile_Application_Action_Access;

import java.util.Map;

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
		if (request.getIsAllowAllActions() != null)
		{
			accessRecord.setIsAllowAllActions(request.getIsAllowAllActions());
		}

		InterfaceWrapperHelper.save(accessRecord);
		createActionAccessIfMissing(request);
	}

	private void createActionAccessIfMissing(@NonNull final CreateMobileApplicationAccessRequest request)
	{
		final ImmutableSet<MobileApplicationActionId> actionIds = request.getAllowedActionIds();
		if (actionIds.isEmpty())
		{
			return;
		}

		// one query for all the action-access rows of the role
		final Map<MobileApplicationActionId, I_Mobile_Application_Action_Access> existingByActionId = queryBL
				.createQueryBuilder(I_Mobile_Application_Action_Access.class)
				.addEqualsFilter(I_Mobile_Application_Action_Access.COLUMNNAME_AD_Role_ID, request.getRoleId())
				.addInArrayFilter(I_Mobile_Application_Action_Access.COLUMNNAME_Mobile_Application_Action_ID, actionIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						row -> MobileApplicationActionId.ofRepoId(row.getMobile_Application_Action_ID()),
						row -> row,
						// duplicate rows of one role+action are a data error: fail loud
						(row1, row2) -> {
							throw new DBMoreThanOneRecordsFoundException("More than one " + I_Mobile_Application_Action_Access.Table_Name
									+ " row for " + I_Mobile_Application_Action_Access.COLUMNNAME_AD_Role_ID + "=" + request.getRoleId().getRepoId()
									+ ", " + I_Mobile_Application_Action_Access.COLUMNNAME_Mobile_Application_Action_ID + "=" + row1.getMobile_Application_Action_ID());
						}));

		for (final MobileApplicationActionId actionId : actionIds)
		{
			I_Mobile_Application_Action_Access actionAccess = existingByActionId.get(actionId);
			if (actionAccess == null)
			{
				actionAccess = InterfaceWrapperHelper.newInstance(I_Mobile_Application_Action_Access.class);
				InterfaceWrapperHelper.setValue(actionAccess, I_Mobile_Application_Action_Access.COLUMNNAME_AD_Client_ID, request.getClientId().getRepoId());
				actionAccess.setAD_Org_ID(request.getOrgId().getRepoId());
				actionAccess.setAD_Role_ID(request.getRoleId().getRepoId());
				actionAccess.setMobile_Application_ID(request.getApplicationId().getRepoId());
				actionAccess.setMobile_Application_Action_ID(actionId.getRepoId());
			}

			actionAccess.setIsActive(true);
			InterfaceWrapperHelper.save(actionAccess);
		}
	}

	public void deleteMobileApplicationAccess(@NonNull final MobileApplicationRepoId applicationId)
	{
		queryBL.createQueryBuilder(I_Mobile_Application_Access.class)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_Mobile_Application_ID, applicationId)
				.create()
				.delete();
	}

}
