package org.adempiere.ad.security.impl;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsBuilder;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.security.permissions.Constraints;
import org.adempiere.ad.security.permissions.DocumentApprovalConstraint;
import org.adempiere.ad.security.permissions.ElementPermissions;
import org.adempiere.ad.security.permissions.GenericPermissions;
import org.adempiere.ad.security.permissions.LoginOrgConstraint;
import org.adempiere.ad.security.permissions.OrgPermissions;
import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;
import org.adempiere.ad.security.permissions.StartupWindowConstraint;
import org.adempiere.ad.security.permissions.TableColumnPermissions;
import org.adempiere.ad.security.permissions.TablePermissions;
import org.adempiere.ad.security.permissions.TableRecordPermissions;
import org.adempiere.ad.security.permissions.UIDisplayedEntityTypes;
import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.security.permissions.WindowMaxQueryRecordsConstraint;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_Role;

class UserRolePermissionsBuilder implements IUserRolePermissionsBuilder
{
	private final transient IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	//
	// Parameters
	private String name;
	private Integer _adRoleId;
	private I_AD_Role _role;
	private Integer _userId;
	private Integer _adClientId;
	private I_AD_Client _adClient; // lazy
	private I_AD_ClientInfo _adClientInfo; // lazy
	private TableAccessLevel userLevel;
	private Integer _menu_AD_Tree_ID;  // lazy or configured

	//
	private OrgPermissions orgAccesses;
	private TablePermissions tableAccesses;
	private TableColumnPermissions columnAccesses;
	private TableRecordPermissions recordAccesses;
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

	UserRolePermissionsBuilder()
	{
		super();
	}

	@Override
	public IUserRolePermissions build()
	{
		final int adRoleId = getAD_Role_ID();
		final int adUserId = getAD_User_ID();
		final int adClientId = getAD_Client_ID();

		if (orgAccesses == null)
		{
			final I_AD_Role role = getAD_Role();
			orgAccesses = userRolePermissionsDAO.retrieveOrgPermissions(role, adUserId);
		}
		if (tableAccesses == null)
		{
			tableAccesses = userRolePermissionsDAO.retrieveTablePermissions(adRoleId);
		}
		if (columnAccesses == null)
		{
			columnAccesses = userRolePermissionsDAO.retrieveTableColumnPermissions(adRoleId);
		}
		if (recordAccesses == null)
		{
			recordAccesses = userRolePermissionsDAO.retrieveRecordPermissions(adRoleId);
		}
		if (windowAccesses == null)
		{
			windowAccesses = userRolePermissionsDAO.retrieveWindowPermissions(adRoleId, adClientId);
		}
		if (processAccesses == null)
		{
			processAccesses = userRolePermissionsDAO.retrieveProcessPermissions(adRoleId, adClientId);
		}
		if (taskAccesses == null)
		{
			taskAccesses = userRolePermissionsDAO.retrieveTaskPermissions(adRoleId, adClientId);
		}
		if (workflowAccesses == null)
		{
			workflowAccesses = userRolePermissionsDAO.retrieveWorkflowPermissions(adRoleId, adClientId);
		}
		if (formAccesses == null)
		{
			formAccesses = userRolePermissionsDAO.retrieveFormPermissions(adRoleId, adClientId);
		}

		if (miscPermissions == null)
		{
			miscPermissions = extractPermissions(getAD_Role(), getAD_Client());
		}

		if (constraints == null)
		{
			constraints = extractConstraints(getAD_Role());
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
			final TablePermissions.Builder tableAccessesBuilder = tableAccesses.asNewBuilder();
			final TableColumnPermissions.Builder columnAccessesBuilder = columnAccesses.asNewBuilder();
			final TableRecordPermissions.Builder recordAccessesBuilder = recordAccesses.asNewBuilder();
			final ElementPermissions.Builder windowAccessesBuilder = windowAccesses.asNewBuilder();
			final ElementPermissions.Builder processAccessesBuilder = processAccesses.asNewBuilder();
			final ElementPermissions.Builder taskAccessesBuilder = taskAccesses.asNewBuilder();
			final ElementPermissions.Builder workflowAccessesBuilder = workflowAccesses.asNewBuilder();
			final ElementPermissions.Builder formAccessesBuilder = formAccesses.asNewBuilder();

			UserRolePermissionsInclude lastIncludedPermissionsRef = null;
			for (final UserRolePermissionsInclude includedPermissionsRef : userRolePermissionsToInclude)
			{
				final IUserRolePermissionsBuilder includedPermissions = includedPermissionsRef.getUserRolePermissions().asNewBuilder();

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
				recordAccessesBuilder.addPermissions(includedPermissions.getRecordPermissions(), collisionPolicy);
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
			recordAccesses = recordAccessesBuilder.build();
			windowAccesses = windowAccessesBuilder.build();
			processAccesses = processAccessesBuilder.build();
			taskAccesses = taskAccessesBuilder.build();
			workflowAccesses = workflowAccessesBuilder.build();
			formAccesses = formAccessesBuilder.build();
		}

		userRolePermissionsIncluded = userRolePermissionsIncludedBuilder.build();

		return new UserRolePermissions(this);
	}

	private static GenericPermissions extractPermissions(final I_AD_Role role, final I_AD_Client adClient)
	{
		final GenericPermissions.Builder rolePermissions = GenericPermissions.builder();

		rolePermissions.addPermissionIfCondition(role.isAccessAllOrgs(), IUserRolePermissions.PERMISSION_AccessAllOrgs);

		rolePermissions.addPermissionIfCondition(role.isCanReport(), IUserRolePermissions.PERMISSION_CanReport);
		rolePermissions.addPermissionIfCondition(role.isCanExport(), IUserRolePermissions.PERMISSION_CanExport);
		rolePermissions.addPermissionIfCondition(role.isPersonalAccess(), IUserRolePermissions.PERMISSION_PersonalAccess);
		rolePermissions.addPermissionIfCondition(role.isPersonalLock(), IUserRolePermissions.PERMISSION_PersonalLock);
		rolePermissions.addPermissionIfCondition(role.isOverwritePriceLimit(), IUserRolePermissions.PERMISSION_OverwritePriceLimit);
		rolePermissions.addPermissionIfCondition(role.isChangeLog(), IUserRolePermissions.PERMISSION_ChangeLog);
		rolePermissions.addPermissionIfCondition(role.isMenuAvailable(), IUserRolePermissions.PERMISSION_MenuAvailable);
		rolePermissions.addPermissionIfCondition(role.isAllowLoginDateOverride(), IUserRolePermissions.PERMISSION_AllowLoginDateOverride);
		rolePermissions.addPermissionIfCondition(role.isRoleAlwaysUseBetaFunctions() || adClient.isUseBetaFunctions(), IUserRolePermissions.PERMISSION_UseBetaFunctions);

		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Product(), IUserRolePermissions.PERMISSION_InfoWindow_Product);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_BPartner(), IUserRolePermissions.PERMISSION_InfoWindow_BPartner);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Account(), IUserRolePermissions.PERMISSION_InfoWindow_Account);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Schedule(), IUserRolePermissions.PERMISSION_InfoWindow_Schedule);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_MRP(), IUserRolePermissions.PERMISSION_InfoWindow_MRP);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_CRP(), IUserRolePermissions.PERMISSION_InfoWindow_CRP);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Order(), IUserRolePermissions.PERMISSION_InfoWindow_Order);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Invoice(), IUserRolePermissions.PERMISSION_InfoWindow_Invoice);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_InOut(), IUserRolePermissions.PERMISSION_InfoWindow_InOut);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Payment(), IUserRolePermissions.PERMISSION_InfoWindow_Payment);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_CashJournal(), IUserRolePermissions.PERMISSION_InfoWindow_CashJournal);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Resource(), IUserRolePermissions.PERMISSION_InfoWindow_Resource);
		rolePermissions.addPermissionIfCondition(role.isAllow_Info_Asset(), IUserRolePermissions.PERMISSION_InfoWindow_Asset);
		
		//
		// Accounting module
		// TODO: we need to extract this logic and plug it from accounting module.
		if (Services.get(IPostingService.class).isEnabled())
		{
			rolePermissions.addPermissionIfCondition(role.isShowAcct(), IUserRolePermissions.PERMISSION_ShowAcct);
		}

		return rolePermissions.build();
	}

	private static final Constraints extractConstraints(final I_AD_Role role)
	{
		final Constraints.Builder constraints = Constraints.builder();

		constraints.addConstraint(UserPreferenceLevelConstraint.forPreferenceType(role.getPreferenceType()));
		constraints.addConstraint(WindowMaxQueryRecordsConstraint.of(role.getMaxQueryRecords(), role.getConfirmQueryRecords()));
		constraints.addConstraintIfNotEquals(StartupWindowConstraint.ofAD_Form_ID(role.getAD_Form_ID()), StartupWindowConstraint.NULL);
		constraints.addConstraint(DocumentApprovalConstraint.of(role.isCanApproveOwnDoc(), role.getAmtApproval(), role.getC_Currency_ID()));
		constraints.addConstraint(LoginOrgConstraint.of(role.getLogin_Org_ID(), role.isOrgLoginMandatory()));
		constraints.addConstraint(UIDisplayedEntityTypes.of(role.isShowAllEntityTypes()));

		return constraints.build();
	}

	@Override
	public UserRolePermissionsBuilder setAD_Role_ID(final int adRoleId)
	{
		_adRoleId = adRoleId;
		_role = null;
		return this;
	}

	private final I_AD_Role getAD_Role()
	{
		if (_role == null)
		{
			final int adRoleId = getAD_Role_ID();
			_role = Services.get(IRoleDAO.class).retrieveRole(Env.getCtx(), adRoleId);
			Check.assumeNotNull(_role, "AD_Role shall exist for {}", adRoleId);
		}
		return _role;
	}

	@Override
	public final int getAD_Role_ID()
	{
		Check.assumeNotNull(_adRoleId != null && _adRoleId >= 0, "AD_Role_ID shall be set but it was {}", _adRoleId);
		return _adRoleId;
	}

	public final String getName()
	{
		if (name != null)
		{
			return name;
		}
		return getAD_Role().getName();
	}

	public UserRolePermissionsBuilder setName(final String name)
	{
		this.name = name;
		return this;
	}

	@Override
	public UserRolePermissionsBuilder setAD_User_ID(final int adUserId)
	{
		_userId = adUserId;
		return this;
	}

	@Override
	public final int getAD_User_ID()
	{
		Check.assume(_userId != null && _userId >= 0, "userId shall be set but it was {}", _userId);
		return _userId;
	}

	@Override
	public UserRolePermissionsBuilder setAD_Client_ID(final int adClientId)
	{
		_adClientId = adClientId;
		return this;
	}

	@Override
	public final int getAD_Client_ID()
	{
		// Check if the AD_Client_ID was set and it was not set to something like "-1"
		if (_adClientId != null && _adClientId >= 0)
		{
			return _adClientId;
		}

		// Fallback: use role's AD_Client_ID
		return getAD_Role().getAD_Client_ID();
	}
	
	private I_AD_Client getAD_Client()
	{
		if(_adClient == null)
		{
			final int adClientId = getAD_Client_ID();
			_adClient = Services.get(IClientDAO.class).retriveClient(Env.getCtx(), adClientId);
		}
		return _adClient;
	}
	
	private I_AD_ClientInfo getAD_ClientInfo()
	{
		if(_adClientInfo == null)
		{
			final int adClientId = getAD_Client_ID();
			_adClientInfo = Services.get(IClientDAO.class).retrieveClientInfo(Env.getCtx(), adClientId);
		}
		return _adClientInfo;
	}


	@Override
	public UserRolePermissionsBuilder setUserLevel(final TableAccessLevel userLevel)
	{
		this.userLevel = userLevel;
		return this;
	}

	@Override
	public TableAccessLevel getUserLevel()
	{
		if (userLevel != null)
		{
			return userLevel;
		}
		return TableAccessLevel.forUserLevel(getAD_Role().getUserLevel());
	}

	@Override
	public OrgPermissions getOrgPermissions()
	{
		return orgAccesses;
	}

	public UserRolePermissionsBuilder setOrgPermissions(final OrgPermissions orgAccesses)
	{
		this.orgAccesses = orgAccesses;
		return this;
	}

	@Override
	public TablePermissions getTablePermissions()
	{
		return tableAccesses;
	}

	public UserRolePermissionsBuilder setTablePermissions(final TablePermissions tableAccesses)
	{
		this.tableAccesses = tableAccesses;
		return this;
	}

	@Override
	public TableColumnPermissions getColumnPermissions()
	{
		return columnAccesses;
	}

	public UserRolePermissionsBuilder setColumnPermissions(final TableColumnPermissions columnAccesses)
	{
		this.columnAccesses = columnAccesses;
		return this;
	}

	@Override
	public TableRecordPermissions getRecordPermissions()
	{
		return recordAccesses;
	}

	public UserRolePermissionsBuilder setRecordPermissions(final TableRecordPermissions recordAccesses)
	{
		this.recordAccesses = recordAccesses;
		return this;
	}

	@Override
	public ElementPermissions getWindowPermissions()
	{
		return windowAccesses;
	}

	public UserRolePermissionsBuilder setWindowPermissions(final ElementPermissions windowAccesses)
	{
		this.windowAccesses = windowAccesses;
		return this;
	}

	@Override
	public ElementPermissions getProcessPermissions()
	{
		return processAccesses;
	}

	public UserRolePermissionsBuilder setProcessPermissions(final ElementPermissions processAccesses)
	{
		this.processAccesses = processAccesses;
		return this;
	}

	@Override
	public ElementPermissions getTaskPermissions()
	{
		return taskAccesses;
	}

	public UserRolePermissionsBuilder setTaskPermissions(final ElementPermissions taskAccesses)
	{
		this.taskAccesses = taskAccesses;
		return this;
	}

	@Override
	public ElementPermissions getWorkflowPermissions()
	{
		return workflowAccesses;
	}

	public UserRolePermissionsBuilder setWorkflowPermissions(final ElementPermissions workflowAccesses)
	{
		this.workflowAccesses = workflowAccesses;
		return this;
	}

	@Override
	public ElementPermissions getFormPermissions()
	{
		return formAccesses;
	}

	@Override
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

	@Override
	public IUserRolePermissionsBuilder includeUserRolePermissions(final IUserRolePermissions userRolePermissions, final int seqNo)
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
	
	public UserRolePermissionsBuilder setMenu_AD_Tree_ID(Integer _menu_AD_Tree_ID)
	{
		this._menu_AD_Tree_ID = _menu_AD_Tree_ID;
		return this;
	}

	public int getMenu_Tree_ID()
	{
		if(_menu_AD_Tree_ID == null)
		{
			_menu_AD_Tree_ID = findMenu_Tree_ID();
		}
		return _menu_AD_Tree_ID;
	}
	
	private int findMenu_Tree_ID()
	{
		int roleMenuTreeId = getAD_Role().getAD_Tree_Menu_ID();
		if(roleMenuTreeId > 0)
		{
			return roleMenuTreeId;
		}
		
		final I_AD_ClientInfo adClientInfo = getAD_ClientInfo();
		final int adClientMenuTreeId = adClientInfo.getAD_Tree_Menu_ID();
		if(adClientMenuTreeId > 0)
		{
			return adClientMenuTreeId;
		}

		// Fallback: when role has NO menu and there is no menu defined on AD_ClientInfo level - shall not happen
		return 10; // Menu // FIXME: hardcoded
	}
}
