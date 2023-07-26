package de.metas.security.impl;

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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.util.Env;

import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissions;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.GenericPermissions;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.UserMenuInfo;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

class UserRolePermissionsBuilder
{
	public static UserRolePermissionsBuilder of(final UserRolePermissionsDAO userRolePermissionsRepo, final UserRolePermissions permissions)
	{
		return new UserRolePermissionsBuilder(userRolePermissionsRepo)
				.setRoleId(permissions.getRoleId())
				.setAlreadyIncludedRolePermissions(permissions.getIncludes())
				.setClientId(permissions.getClientId())
				.setUserId(permissions.getUserId())
				.setUserLevel(permissions.getUserLevel())
				.setMenuInfo(permissions.getMenuInfo())
				//
				.setOrgPermissions(permissions.getOrgPermissions())
				.setTablePermissions(permissions.getTablePermissions())
				.setColumnPermissions(permissions.getColumnPermissions())
				.setWindowPermissions(permissions.getWindowPermissions())
				.setProcessPermissions(permissions.getProcessPermissions())
				.setTaskPermissions(permissions.getTaskPermissions())
				.setWorkflowPermissions(permissions.getWorkflowPermissions())
				.setFormPermissions(permissions.getFormPermissions())
				.setMiscPermissions(permissions.getMiscPermissions())
				//
				.setConstraints(permissions.getConstraints());
	}

	// services
	private final UserRolePermissionsDAO userRolePermissionsRepo;
	private final IRoleDAO rolesRepo = Services.get(IRoleDAO.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);

	//
	// Parameters
	private String name;
	private RoleId _adRoleId;
	private Role _role;
	private UserId _userId;
	private ClientId _adClientId;
	private I_AD_Client _adClient; // lazy
	private I_AD_ClientInfo _adClientInfo; // lazy
	private TableAccessLevel userLevel;
	private UserMenuInfo _menuInfo;  // lazy or configured

	//
	private OrgPermissions orgAccesses;
	private TablePermissions tableAccesses;
	private TableColumnPermissions columnAccesses;
	private ElementPermissions windowAccesses;
	private ElementPermissions processAccesses;
	private ElementPermissions taskAccesses;
	private ElementPermissions workflowAccesses;
	private ElementPermissions formAccesses;

	private GenericPermissions miscPermissions;
	private Constraints constraints;

	private UserRolePermissionsIncludesList userRolePermissionsAlreadyIncluded;
	private final List<UserRolePermissionsInclude> userRolePermissionsToInclude = new ArrayList<>();
	private UserRolePermissionsIncludesList userRolePermissionsIncluded;

	private final boolean accountingModuleActive;

	UserRolePermissionsBuilder(@NonNull final UserRolePermissionsDAO userRolePermissionsRepo)
	{
		this.userRolePermissionsRepo = userRolePermissionsRepo;
		this.accountingModuleActive = userRolePermissionsRepo.isAccountingModuleActive();
	}

	public UserRolePermissions build()
	{
		final RoleId adRoleId = getRoleId();
		final UserId adUserId = getUserId();
		final ClientId adClientId = getClientId();

		if (orgAccesses == null)
		{
			final Role role = getRole();
			orgAccesses = userRolePermissionsRepo.retrieveOrgPermissions(role, adUserId);
		}
		if (tableAccesses == null)
		{
			tableAccesses = userRolePermissionsRepo.retrieveTablePermissions(adRoleId);
		}
		if (columnAccesses == null)
		{
			columnAccesses = userRolePermissionsRepo.retrieveTableColumnPermissions(adRoleId);
		}
		if (windowAccesses == null)
		{
			windowAccesses = userRolePermissionsRepo.retrieveWindowPermissions(adRoleId, adClientId);
		}
		if (processAccesses == null)
		{
			processAccesses = userRolePermissionsRepo.retrieveProcessPermissions(adRoleId, adClientId);
		}
		if (taskAccesses == null)
		{
			taskAccesses = userRolePermissionsRepo.retrieveTaskPermissions(adRoleId, adClientId);
		}
		if (workflowAccesses == null)
		{
			workflowAccesses = userRolePermissionsRepo.retrieveWorkflowPermissions(adRoleId, adClientId);
		}
		if (formAccesses == null)
		{
			formAccesses = userRolePermissionsRepo.retrieveFormPermissions(adRoleId, adClientId);
		}

		if (miscPermissions == null)
		{
			miscPermissions = extractPermissions(getRole(), getAD_Client());
		}

		if (constraints == null)
		{
			constraints = getRole().getConstraints();
		}

		final UserRolePermissionsIncludesList.Builder userRolePermissionsIncludedBuilder = UserRolePermissionsIncludesList.builder();
		if (userRolePermissionsAlreadyIncluded != null)
		{
			userRolePermissionsIncludedBuilder.addAll(userRolePermissionsAlreadyIncluded);
		}

		//
		// Merge included permissions if any
		if (!userRolePermissionsToInclude.isEmpty())
		{
			final OrgPermissions.Builder orgAccessesBuilder = orgAccesses.asNewBuilder();
			final TablePermissions.Builder tableAccessesBuilder = tableAccesses.toBuilder();
			final TableColumnPermissions.Builder columnAccessesBuilder = columnAccesses.asNewBuilder();
			final ElementPermissions.Builder windowAccessesBuilder = windowAccesses.asNewBuilder();
			final ElementPermissions.Builder processAccessesBuilder = processAccesses.asNewBuilder();
			final ElementPermissions.Builder taskAccessesBuilder = taskAccesses.asNewBuilder();
			final ElementPermissions.Builder workflowAccessesBuilder = workflowAccesses.asNewBuilder();
			final ElementPermissions.Builder formAccessesBuilder = formAccesses.asNewBuilder();

			UserRolePermissionsInclude lastIncludedPermissionsRef = null;
			for (final UserRolePermissionsInclude includedPermissionsRef : userRolePermissionsToInclude)
			{
				final UserRolePermissionsBuilder includedPermissions = of(userRolePermissionsRepo, includedPermissionsRef.getUserRolePermissions());

				CollisionPolicy collisionPolicy = CollisionPolicy.Merge;
				//
				// If roles have same SeqNo, then, the second role will override permissions from first role
				if (lastIncludedPermissionsRef != null && includedPermissionsRef.getSeqNo() >= 0
						&& includedPermissionsRef.getSeqNo() >= 0
						&& lastIncludedPermissionsRef.getSeqNo() == includedPermissionsRef.getSeqNo())
				{
					collisionPolicy = CollisionPolicy.Override;
				}

				orgAccessesBuilder.addPermissions(includedPermissions.getOrgPermissions(), collisionPolicy);
				tableAccessesBuilder.addPermissions(includedPermissions.getTablePermissions(), collisionPolicy);
				columnAccessesBuilder.addPermissions(includedPermissions.getColumnPermissions(), collisionPolicy);
				windowAccessesBuilder.addPermissions(includedPermissions.getWindowPermissions(), collisionPolicy);
				processAccessesBuilder.addPermissions(includedPermissions.getProcessPermissions(), collisionPolicy);
				taskAccessesBuilder.addPermissions(includedPermissions.getTaskPermissions(), collisionPolicy);
				workflowAccessesBuilder.addPermissions(includedPermissions.getWorkflowPermissions(), collisionPolicy);
				formAccessesBuilder.addPermissions(includedPermissions.getFormPermissions(), collisionPolicy);

				// add it to the list of included permissions.
				userRolePermissionsIncludedBuilder.add(includedPermissionsRef);

				lastIncludedPermissionsRef = includedPermissionsRef;
			}

			orgAccesses = orgAccessesBuilder.build();
			tableAccesses = tableAccessesBuilder.build();
			columnAccesses = columnAccessesBuilder.build();
			windowAccesses = windowAccessesBuilder.build();
			processAccesses = processAccessesBuilder.build();
			taskAccesses = taskAccessesBuilder.build();
			workflowAccesses = workflowAccessesBuilder.build();
			formAccesses = formAccessesBuilder.build();
		}

		userRolePermissionsIncluded = userRolePermissionsIncludedBuilder.build();

		return new UserRolePermissions(this);
	}

	private GenericPermissions extractPermissions(final Role role, final I_AD_Client adClient)
	{
		final GenericPermissions.Builder rolePermissions = role.getPermissions().toBuilder();

		if (adClient.isUseBetaFunctions())
		{
			rolePermissions.addPermission(IUserRolePermissions.PERMISSION_UseBetaFunctions, CollisionPolicy.Override);
		}

		if (!accountingModuleActive)
		{
			rolePermissions.removePermission(IUserRolePermissions.PERMISSION_ShowAcct);
		}

		return rolePermissions.build();
	}

	public UserRolePermissionsBuilder setRoleId(@NonNull final RoleId adRoleId)
	{
		_adRoleId = adRoleId;
		_role = null;
		return this;
	}

	private final Role getRole()
	{
		if (_role == null)
		{
			final RoleId adRoleId = getRoleId();
			_role = rolesRepo.getById(adRoleId);
			Check.assumeNotNull(_role, "AD_Role shall exist for {}", adRoleId);
		}
		return _role;
	}

	public final RoleId getRoleId()
	{
		Check.assumeNotNull(_adRoleId, "Role shall be set");
		return _adRoleId;
	}

	public final String getName()
	{
		if (name != null)
		{
			return name;
		}
		return getRole().getName();
	}

	public UserRolePermissionsBuilder setName(final String name)
	{
		this.name = name;
		return this;
	}

	public UserRolePermissionsBuilder setUserId(final UserId adUserId)
	{
		_userId = adUserId;
		return this;
	}

	public final UserId getUserId()
	{
		Check.assumeNotNull(_userId, "userId shall be set");
		return _userId;
	}

	public UserRolePermissionsBuilder setClientId(final ClientId adClientId)
	{
		_adClientId = adClientId;
		return this;
	}

	public final ClientId getClientId()
	{
		// Check if the AD_Client_ID was set and it was not set to something like "-1"
		if (_adClientId != null)
		{
			return _adClientId;
		}

		// Fallback: use role's AD_Client_ID
		return getRole().getClientId();
	}

	private I_AD_Client getAD_Client()
	{
		if (_adClient == null)
		{
			final ClientId adClientId = getClientId();
			_adClient = clientsRepo.getById(adClientId);
		}
		return _adClient;
	}

	private I_AD_ClientInfo getAD_ClientInfo()
	{
		if (_adClientInfo == null)
		{
			final ClientId adClientId = getClientId();
			_adClientInfo = clientsRepo.retrieveClientInfo(Env.getCtx(), adClientId.getRepoId());
		}
		return _adClientInfo;
	}

	public UserRolePermissionsBuilder setUserLevel(final TableAccessLevel userLevel)
	{
		this.userLevel = userLevel;
		return this;
	}

	public TableAccessLevel getUserLevel()
	{
		if (userLevel != null)
		{
			return userLevel;
		}
		return getRole().getUserLevel();
	}

	public OrgPermissions getOrgPermissions()
	{
		return orgAccesses;
	}

	public UserRolePermissionsBuilder setOrgPermissions(final OrgPermissions orgAccesses)
	{
		this.orgAccesses = orgAccesses;
		return this;
	}

	public TablePermissions getTablePermissions()
	{
		return tableAccesses;
	}

	public UserRolePermissionsBuilder setTablePermissions(final TablePermissions tableAccesses)
	{
		this.tableAccesses = tableAccesses;
		return this;
	}

	public TableColumnPermissions getColumnPermissions()
	{
		return columnAccesses;
	}

	public UserRolePermissionsBuilder setColumnPermissions(final TableColumnPermissions columnAccesses)
	{
		this.columnAccesses = columnAccesses;
		return this;
	}

	public ElementPermissions getWindowPermissions()
	{
		return windowAccesses;
	}

	public UserRolePermissionsBuilder setWindowPermissions(final ElementPermissions windowAccesses)
	{
		this.windowAccesses = windowAccesses;
		return this;
	}

	public ElementPermissions getProcessPermissions()
	{
		return processAccesses;
	}

	public UserRolePermissionsBuilder setProcessPermissions(final ElementPermissions processAccesses)
	{
		this.processAccesses = processAccesses;
		return this;
	}

	public ElementPermissions getTaskPermissions()
	{
		return taskAccesses;
	}

	public UserRolePermissionsBuilder setTaskPermissions(final ElementPermissions taskAccesses)
	{
		this.taskAccesses = taskAccesses;
		return this;
	}

	public ElementPermissions getWorkflowPermissions()
	{
		return workflowAccesses;
	}

	public UserRolePermissionsBuilder setWorkflowPermissions(final ElementPermissions workflowAccesses)
	{
		this.workflowAccesses = workflowAccesses;
		return this;
	}

	public ElementPermissions getFormPermissions()
	{
		return formAccesses;
	}

	public UserRolePermissionsBuilder setFormPermissions(final ElementPermissions formAccesses)
	{
		this.formAccesses = formAccesses;
		return this;
	}

	UserRolePermissionsBuilder setMiscPermissions(final GenericPermissions permissions)
	{
		Check.assumeNull(miscPermissions, "permissions not already configured");
		miscPermissions = permissions;
		return this;
	}

	public GenericPermissions getMiscPermissions()
	{
		Check.assumeNotNull(miscPermissions, "permissions configured");
		return miscPermissions;
	}

	UserRolePermissionsBuilder setConstraints(final Constraints constraints)
	{
		Check.assumeNull(this.constraints, "constraints not already configured");
		this.constraints = constraints;
		return this;
	}

	public Constraints getConstraints()
	{
		Check.assumeNotNull(constraints, "constraints configured");
		return constraints;
	}

	public UserRolePermissionsBuilder includeUserRolePermissions(final UserRolePermissions userRolePermissions, final int seqNo)
	{
		userRolePermissionsToInclude.add(UserRolePermissionsInclude.of(userRolePermissions, seqNo));
		return this;
	}

	UserRolePermissionsBuilder setAlreadyIncludedRolePermissions(final UserRolePermissionsIncludesList userRolePermissionsAlreadyIncluded)
	{
		Check.assumeNotNull(userRolePermissionsAlreadyIncluded, "included not null");
		Check.assumeNull(this.userRolePermissionsAlreadyIncluded, "already included permissions were not configured before");

		this.userRolePermissionsAlreadyIncluded = userRolePermissionsAlreadyIncluded;
		return this;
	}

	UserRolePermissionsIncludesList getUserRolePermissionsIncluded()
	{
		Check.assumeNotNull(userRolePermissionsIncluded, "userRolePermissionsIncluded not null");
		return userRolePermissionsIncluded;
	}

	public UserRolePermissionsBuilder setMenuInfo(final UserMenuInfo menuInfo)
	{
		this._menuInfo = menuInfo;
		return this;
	}

	public UserMenuInfo getMenuInfo()
	{
		if (_menuInfo == null)
		{
			_menuInfo = findMenuInfo();
		}
		return _menuInfo;
	}

	private UserMenuInfo findMenuInfo()
	{
		final Role adRole = getRole();
		final AdTreeId roleMenuTreeId = adRole.getMenuTreeId();
		if (roleMenuTreeId != null)
		{
			return UserMenuInfo.of(roleMenuTreeId, adRole.getRootMenuId());
		}

		final I_AD_ClientInfo adClientInfo = getAD_ClientInfo();
		final AdTreeId adClientMenuTreeId = AdTreeId.ofRepoIdOrNull(adClientInfo.getAD_Tree_Menu_ID());
		if (adClientMenuTreeId != null)
		{
			return UserMenuInfo.of(adClientMenuTreeId, adRole.getRootMenuId());
		}

		// Fallback: when role has NO menu and there is no menu defined on AD_ClientInfo level - shall not happen
		return UserMenuInfo.DEFAULT_MENU;
	}
}
