package de.metas.security.model.interceptor;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_Role;
import org.compiere.util.Env;

@Interceptor(I_AD_Role.class)
public class AD_Role
{
	public static final AD_Role instance = new AD_Role();

	private AD_Role()
	{
		super();
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_AD_Role.Table_Name);

		final IModelCacheService cachingService = Services.get(IModelCacheService.class);
		cachingService.createTableCacheConfigBuilder(I_AD_Role.class)
				.setEnabled(true)
				.setInitialCapacity(50)
				.setMaxCapacity(50)
				.setExpireMinutes(ITableCacheConfig.EXPIREMINUTES_Never)
				.setCacheMapType(CacheMapType.LRU)
				.setTrxLevel(TrxLevel.OutOfTransactionOnly)
				.register();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_Role role)
	{
		if (role.getAD_Client_ID() == Env.CTXVALUE_AD_Client_ID_System)
		{
			role.setUserLevel(X_AD_Role.USERLEVEL_System);
		}
		else if (role.getUserLevel().equals(X_AD_Role.USERLEVEL_System))
		{
			throw new AdempiereException("@AccessTableNoUpdate@ @UserLevel@");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_AD_Role role, final ModelChangeType changeType)
	{
		// services:
		final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
		final RoleId roleId = RoleId.ofRepoId(role.getAD_Role_ID());

		//
		// Automatically assign new role to SuperUser and to the user who created it.
		if (changeType.isNew() && !InterfaceWrapperHelper.isCopying(role))
		{
			// Add Role to SuperUser
			roleDAO.createUserRoleAssignmentIfMissing(UserId.METASFRESH, roleId);

			// Add Role to User which created this record
			final UserId createdByUserId = UserId.ofRepoId(role.getCreatedBy());
			if (!createdByUserId.equals(UserId.METASFRESH))
			{
				roleDAO.createUserRoleAssignmentIfMissing(createdByUserId, roleId);
			}
		}

		//
		// Update role access records
		if ((changeType.isNew() || InterfaceWrapperHelper.isValueChanged(role, I_AD_Role.COLUMNNAME_UserLevel))
				&& !role.isManual())
		{
			final UserId userId = UserId.ofRepoId(role.getUpdatedBy());
			Services.get(IUserRolePermissionsDAO.class).updateAccessRecords(roleId, userId);
		}

		//
		// Reset the cached role permissions after the transaction is commited.
		// NOTE: not needed because it's performed automatically
		// Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
	}    // afterSave

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAccessRecords(final I_AD_Role role)
	{
		final RoleId roleId = RoleId.ofRepoId(role.getAD_Role_ID());
		Services.get(IUserRolePermissionsDAO.class).deleteAccessRecords(roleId);
	}    // afterDelete

}
