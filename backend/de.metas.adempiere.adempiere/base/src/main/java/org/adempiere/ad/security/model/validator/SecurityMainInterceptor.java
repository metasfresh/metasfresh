package org.adempiere.ad.security.model.validator;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsEventBus;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Role;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class SecurityMainInterceptor extends AbstractModuleInterceptor
{
	public static final transient SecurityMainInterceptor instance = new SecurityMainInterceptor();

	private static final transient Logger logger = LogManager.getLogger(SecurityMainInterceptor.class);

	private SecurityMainInterceptor()
	{
		super();
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		// Role and included roles
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Role.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Role_Included.instance, client);

		// Source tables
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Org.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Window.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Process.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Form.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Workflow.instance, client);

		//
		// Trigger permissions cache reset if any of permissions table was changed
		{
			// TableNames which shall trigger a full permissions reset
			ImmutableSet.<String> builder()
					.add(I_AD_Role.Table_Name) // role table itself
					.addAll(Services.get(IUserRolePermissionsDAO.class).getRoleDependentTableNames()) // all role dependent table name
					.build()
					.forEach(triggeringTableName -> {
						engine.addModelValidator(new PermissionsCacheResetInterceptor(triggeringTableName), client);
						logger.debug("Registered permissions cache reset on {}", triggeringTableName);
					});
		}
	}

	@Override
	protected void onAfterInit()
	{
		UserRolePermissionsEventBus.install();
	}

	@ToString
	@EqualsAndHashCode(callSuper = false)
	private static final class PermissionsCacheResetInterceptor extends AbstractModelInterceptor
	{
		private final String triggeringTableName;

		private PermissionsCacheResetInterceptor(final String triggeringTableName)
		{
			this.triggeringTableName = triggeringTableName;
		}

		@Override
		protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
		{
			engine.addModelChange(triggeringTableName, this);
		}

		@Override
		public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
		{
			if (!changeType.isAfter())
			{
				return;
			}

			logger.debug("Scheduling permissions cache reset (trigger={}, changeType={})", model, changeType);
			Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
		}
	}
}
