package de.metas.security.mobile_application;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.permissions.PermissionsBuilder;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.commons.collections4.map.HashedMap;
import org.compiere.util.Env;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode
public class MobileApplicationPermissions
{
	private static final MobileApplicationPermissions EMPTY = new MobileApplicationPermissions(ImmutableList.of());

	@NonNull private final ImmutableMap<MobileApplicationRepoId, MobileApplicationPermission> byMobileApplicationId;

	private MobileApplicationPermissions(@NonNull final Collection<MobileApplicationPermission> permissions)
	{
		this.byMobileApplicationId = Maps.uniqueIndex(permissions, MobileApplicationPermission::getMobileApplicationId);
	}

	public static MobileApplicationPermissions ofCollection(final Collection<MobileApplicationPermission> collection)
	{
		return collection.isEmpty() ? EMPTY : new MobileApplicationPermissions(collection);
	}

	public static Collector<MobileApplicationPermission, ?, MobileApplicationPermissions> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(MobileApplicationPermissions::ofCollection);
	}

	public static MobileApplicationPermissions merge(
			@NonNull final MobileApplicationPermissions permissions1,
			@NonNull final MobileApplicationPermissions permissions2,
			@NonNull final PermissionsBuilder.CollisionPolicy collisionPolicy)
	{
		return merge(Arrays.asList(permissions1, permissions2), collisionPolicy);
	}

	public static MobileApplicationPermissions merge(
			@NonNull final List<MobileApplicationPermissions> permissionsList,
			@NonNull final PermissionsBuilder.CollisionPolicy collisionPolicy)
	{
		final HashedMap<MobileApplicationRepoId, MobileApplicationPermission> resultMap = new HashedMap<>();

		for (final MobileApplicationPermissions permissions : permissionsList)
		{
			if (permissions == null)
			{
				continue;
			}

			if (resultMap.isEmpty())
			{
				resultMap.putAll(permissions.byMobileApplicationId);
			}
			else
			{
				for (final MobileApplicationPermission permission : permissions.byMobileApplicationId.values())
				{
					final MobileApplicationRepoId mobileApplicationId = permission.getMobileApplicationId();
					final MobileApplicationPermission existingPermission = resultMap.get(mobileApplicationId);
					final MobileApplicationPermission newPermission = MobileApplicationPermission.merge(existingPermission, permission, collisionPolicy);
					resultMap.put(mobileApplicationId, newPermission);
				}
			}
		}

		return MobileApplicationPermissions.ofCollection(resultMap.values());
	}

	@Override
	public String toString()
	{
		final String permissionsName = getClass().getSimpleName();
		final Collection<MobileApplicationPermission> permissionsList = byMobileApplicationId.values();

		final StringBuilder sb = new StringBuilder();
		sb.append(permissionsName).append(": ");
		if (permissionsList.isEmpty())
		{
			sb.append("@NoRestrictions@");
		}
		else
		{
			sb.append(Env.NL);
		}

		Joiner.on(Env.NL)
				.skipNulls()
				.appendTo(sb, permissionsList);

		return sb.toString();
	}

	public boolean isAllowAccess(@NonNull final MobileApplicationRepoId mobileApplicationId)
	{
		final MobileApplicationPermission permission = byMobileApplicationId.get(mobileApplicationId);
		return permission != null && permission.isAllowAccess();
	}

	public boolean isAllowAction(@NonNull final MobileApplicationRepoId mobileApplicationId, @NonNull final MobileApplicationActionId actionId)
	{
		final MobileApplicationPermission permission = byMobileApplicationId.get(mobileApplicationId);
		return permission != null && permission.isAllowAction(actionId);
	}
}
