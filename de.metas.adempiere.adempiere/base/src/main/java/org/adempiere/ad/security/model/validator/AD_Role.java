package org.adempiere.ad.security.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.compiere.model.MUserRoles;
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
		final String trxName = InterfaceWrapperHelper.getTrxName(role);

		//
		// Automatically assign new role to SuperUser and to the user who created it.
		if (changeType.isNew())
		{
			// Add Role to SuperUser
			final Properties ctx = InterfaceWrapperHelper.getCtx(role);

			if (!roleDAO.hasUserRoleAssignment(ctx, IUserDAO.SUPERUSER_USER_ID, role.getAD_Role_ID()))
			{
				final MUserRoles su = new MUserRoles(ctx, IUserDAO.SUPERUSER_USER_ID, role.getAD_Role_ID(), trxName);
				InterfaceWrapperHelper.save(su);
			}

			// Add Role to User which created this record
			final int createdByUserId = role.getCreatedBy();
			
			if ((createdByUserId != IUserDAO.SUPERUSER_USER_ID) && !roleDAO.hasUserRoleAssignment(ctx, createdByUserId, role.getAD_Role_ID()))
			{
				final MUserRoles ur = new MUserRoles(ctx, createdByUserId, role.getAD_Role_ID(), trxName);
				InterfaceWrapperHelper.save(ur);
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
		Services.get(ITrxManager.class)
				.getTrxListenerManager(trxName)
				.registerListener(new TrxListenerAdapter()
				{
					@Override
					public void afterCommit(final ITrx trx)
					{
						Env.resetUserRolePermissions();
					}
				});
	}	// afterSave

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAccessRecords(final I_AD_Role role)
	{
		Services.get(IUserRolePermissionsDAO.class).deleteAccessRecords(role);
	} 	// afterDelete

}
