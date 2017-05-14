package org.adempiere.ad.security.model.validator;

import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ITableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.impl.AD_Role_POCopyRecordSupport;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_Role;
import org.compiere.util.CCache.CacheMapType;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_Role;

@Interceptor(I_AD_Role.class)
public class AD_Role
{
	public static final transient AD_Role instance = new AD_Role();

	private AD_Role()
	{
		super();
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{

		CopyRecordFactory.enableForTableName(I_AD_Role.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_AD_Role.Table_Name, AD_Role_POCopyRecordSupport.class);

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

		//
		// Automatically assign new role to SuperUser and to the user who created it.
		if (changeType.isNew())
		{
			// Add Role to SuperUser
			roleDAO.createUserRoleAssignmentIfMissing(IUserDAO.SUPERUSER_USER_ID, role.getAD_Role_ID());

			// Add Role to User which created this record
			final int createdByUserId = role.getCreatedBy();
			if (createdByUserId != IUserDAO.SUPERUSER_USER_ID)
			{
				roleDAO.createUserRoleAssignmentIfMissing(createdByUserId, role.getAD_Role_ID());
			}
		}

		//
		// Update role access records
		if (changeType.isNew()
				|| InterfaceWrapperHelper.isValueChanged(role, org.compiere.model.I_AD_Role.COLUMNNAME_UserLevel))
		{
			Services.get(IUserRolePermissionsDAO.class).updateAccessRecords(role);
		}

		//
		// Reset the cached role permissions after the transaction is commited.
		// NOTE: not needed because it's performed automatically
		//Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
	}	// afterSave

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAccessRecords(final I_AD_Role role)
	{
		Services.get(IUserRolePermissionsDAO.class).deleteAccessRecords(role);
	} 	// afterDelete

}
