package de.metas.notification;

import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Recipient
{
	public static Recipient allUsers()
	{
		return ALL_USERS;
	}

	public static Recipient user(@NonNull final UserId userId)
	{
		return _builder()
				.type(RecipientType.User)
				.userId(userId)
				.build();
	}

	public static Recipient userAndRole(@NonNull final UserId userId, @NonNull final RoleId roleId)
	{
		return _builder()
				.type(RecipientType.User)
				.userId(userId)
				.roleId(roleId)
				.build();
	}

	public static Recipient role(@NonNull final RoleId roleId)
	{
		return _builder()
				.type(RecipientType.Role)
				.roleId(roleId)
				.build();
	}

	@NonNull
	public static Recipient group(@NonNull final UserGroupId groupId)
	{
		return _builder()
				.type(RecipientType.Group)
				.groupId(groupId)
				.build();
	}

	public static Recipient allRolesContainingGroup(final NotificationGroupName notificationGroupName)
	{
		return _builder()
				.type(RecipientType.AllRolesContainingGroup)
				.notificationGroupName(notificationGroupName)
				.build();
	}

	public static Recipient allOrgUsersForGroupAndOrgId(final NotificationGroupName notificationGroupName, final OrgId orgId)
	{
		return _builder()
				.type(RecipientType.OrgUsersContainingGroup)
				.notificationGroupName(notificationGroupName)
				.orgId(orgId)
				.build();
	}


	public enum RecipientType
	{
		AllUsers, User, Role, AllRolesContainingGroup, Group, OrgUsersContainingGroup
	}

	private static final Recipient ALL_USERS = _builder().type(RecipientType.AllUsers).build();

	RecipientType type;
	UserId userId;
	RoleId roleId;
	UserGroupId groupId;
	NotificationGroupName notificationGroupName;
	OrgId orgId;

	@Builder(builderMethodName = "_builder", toBuilder = true)
	private Recipient(
			@NonNull final RecipientType type,
			final UserId userId,
			final RoleId roleId,
			final UserGroupId groupId,
			final NotificationGroupName notificationGroupName,
			final OrgId orgId)
	{
		this.type = type;
		if (type == RecipientType.AllUsers)
		{
			this.userId = null;
			this.roleId = null;
			this.groupId = null;
			this.notificationGroupName = null;
			this.orgId = null;
		}
		else if (type == RecipientType.User)
		{
			Check.assumeNotNull(userId, "userId is not null");
			this.userId = userId;
			this.roleId = roleId;
			this.groupId = null;
			this.notificationGroupName = null;
			this.orgId = null;
		}
		else if (type == RecipientType.Role)
		{
			Check.assumeNotNull(roleId, "roleId is not null");
			this.userId = null;
			this.roleId = roleId;
			this.groupId = null;
			this.notificationGroupName = null;
			this.orgId = null;
		}
		else if (type == RecipientType.Group)
		{
			this.userId = null;
			this.roleId = null;
			this.groupId = groupId;
			this.notificationGroupName = null;
			this.orgId = null;
		}
		else if (type == RecipientType.AllRolesContainingGroup)
		{
			Check.assumeNotNull(notificationGroupName, "Parameter notificationGroupName is not null");
			this.userId = null;
			this.roleId = null;
			this.groupId = null;
			this.notificationGroupName = notificationGroupName;
			this.orgId = orgId;
		}
		else if (type == RecipientType.OrgUsersContainingGroup)
		{
			Check.assumeNotNull(notificationGroupName, "Parameter notificationGroupName is not null");
			Check.assumeNotNull(orgId, "Parameter orgId is not null");
			this.userId = null;
			this.roleId = null;
			this.groupId = null;
			this.notificationGroupName = notificationGroupName;
			this.orgId = orgId;
		}
		else
		{
			throw new IllegalArgumentException("Unknown: " + type);
		}
	}

	public boolean isAllUsers()
	{
		return type == RecipientType.AllUsers;
	}

	public boolean isUser()
	{
		return type == RecipientType.User;
	}

	public boolean isRole()
	{
		return type == RecipientType.Role;
	}

	public boolean isGroup()
	{
		return type == RecipientType.Group;
	}

	public boolean isAllRolesContainingGroup()
	{
		return type == RecipientType.AllRolesContainingGroup;
	}
	public boolean isOrgUsersContainingGroup()
	{
		return type == RecipientType.OrgUsersContainingGroup;
	}

	public UserId getUserId()
	{
		if (type != RecipientType.User)
		{
			throw new AdempiereException("UserId not available: " + this);
		}
		return userId;
	}

	public UserGroupId getGroupId()
	{
		if (type != RecipientType.Group)
		{
			throw new AdempiereException("UserGroupId not available: " + this);
		}
		return groupId;
	}

	public boolean isRoleIdSet()
	{
		return roleId != null;
	}

	public NotificationGroupName getNotificationGroupName()
	{
		if (type != RecipientType.AllRolesContainingGroup && type != RecipientType.OrgUsersContainingGroup)
		{
			throw new AdempiereException("NotificationGroupName not available: " + this);
		}
		return notificationGroupName;
	}

}
