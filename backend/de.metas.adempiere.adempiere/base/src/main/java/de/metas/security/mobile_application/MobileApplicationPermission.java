package de.metas.security.mobile_application;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.permissions.Permission;
import de.metas.security.permissions.PermissionsBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class MobileApplicationPermission
{
	@NonNull @Getter MobileApplicationRepoId mobileApplicationId;
	@NonNull @Getter MobileApplicationResource resource;
	@Getter boolean allowAccess;
	boolean allowAllActions;
	@NonNull ImmutableSet<MobileApplicationActionId> allowedActionIds;

	@Builder(toBuilder = true)
	private MobileApplicationPermission(
			@NonNull final MobileApplicationRepoId mobileApplicationId,
			final boolean allowAccess,
			final boolean allowAllActions,
			@NonNull final Set<MobileApplicationActionId> allowedActionIds)
	{
		this.mobileApplicationId = mobileApplicationId;
		this.allowAccess = allowAccess;
		this.resource = MobileApplicationResource.of(mobileApplicationId);
		this.allowAllActions = allowAllActions;
		this.allowedActionIds = ImmutableSet.copyOf(allowedActionIds);
	}

	public static MobileApplicationPermission cast(@NonNull final Permission permission)
	{
		return (MobileApplicationPermission)permission;
	}

	public MobileApplicationPermission mergeFrom(@NonNull final MobileApplicationPermission from)
	{
		if (!Objects.equals(this.mobileApplicationId, from.mobileApplicationId))
		{
			throw new AdempiereException("Cannot merge permissions for different mobile applications: " + this + ", " + from);
		}

		return toBuilder()
				.allowAccess(this.allowAccess || from.allowAccess)
				.allowAllActions(this.allowAllActions || from.allowAllActions)
				.allowedActionIds(Sets.union(this.allowedActionIds, from.allowedActionIds))
				.build();
	}

	@NonNull
	public static MobileApplicationPermission merge(
			@Nullable final MobileApplicationPermission existingPermission,
			@NonNull final MobileApplicationPermission permission,
			@NonNull final PermissionsBuilder.CollisionPolicy collisionPolicy)
	{
		if (existingPermission == null)
		{
			return permission;
		}

		final boolean samePermissionAlreadyExists = Objects.equals(existingPermission, permission);

		if (collisionPolicy == PermissionsBuilder.CollisionPolicy.Override)
		{
			return permission;
		}
		else if (collisionPolicy == PermissionsBuilder.CollisionPolicy.Merge)
		{
			if (!samePermissionAlreadyExists)
			{
				return existingPermission.mergeFrom(permission);
			}
			else
			{
				return existingPermission;
			}
		}
		else if (collisionPolicy == PermissionsBuilder.CollisionPolicy.Fail)
		{
			if (!samePermissionAlreadyExists)
			{
				throw new AdempiereException("Found another permission for same resource but with different accesses: " + existingPermission + ", " + permission);
			}
			// NOTE: if they are equals, do nothing
			return existingPermission;
		}
		else if (collisionPolicy == PermissionsBuilder.CollisionPolicy.Skip)
		{
			return existingPermission;
		}
		else
		{
			throw new AdempiereException("Unknown CollisionPolicy: " + collisionPolicy);
		}
	}

	public boolean isAllowAction(@NonNull final MobileApplicationActionId actionId)
	{
		if (!allowAccess) {return false;}

		return allowAllActions || allowedActionIds.contains(actionId);
	}
}
