package de.metas.vertical.pharma;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma
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

@EqualsAndHashCode
@ToString
public final class PharmaVendorPermissions
{
	public static PharmaVendorPermissions of(final I_C_BPartner bpartner)
	{
		if (!bpartner.isVendor())
		{
			return NONE;
		}

		final ImmutableSet.Builder<PharmaVendorPermission> permissionsBuilder = ImmutableSet.builder();
		if (bpartner.isPharmaVendorAgentPermission())
		{
			permissionsBuilder.add(PharmaVendorPermission.PHARMA_AGENT);
		}
		if (bpartner.isPharmaVendorManufacturerPermission())
		{
			permissionsBuilder.add(PharmaVendorPermission.PHARMA_MANUFACTURER);
		}
		if (bpartner.isPharmaVendorWholesalePermission())
		{
			permissionsBuilder.add(PharmaVendorPermission.PHARMA_WHOLESALE);
		}

		final ImmutableSet<PharmaVendorPermission> permissions = permissionsBuilder.build();
		if (permissions.isEmpty())
		{
			return NONE;
		}

		return new PharmaVendorPermissions(permissions);
	}

	private static final PharmaVendorPermissions NONE = new PharmaVendorPermissions(ImmutableSet.of());

	private final ImmutableSet<PharmaVendorPermission> permissions;

	private PharmaVendorPermissions(final Set<PharmaVendorPermission> permissions)
	{
		this.permissions = ImmutableSet.copyOf(permissions);
	}

	public boolean hasNoPermissions()
	{
		return permissions.isEmpty();
	}

	public boolean hasAtLeastOnePermission()
	{
		return !permissions.isEmpty();
	}

	public boolean hasPermission(final PharmaVendorPermission permission)
	{
		return permissions.contains(permission);
	}

	public boolean hasOnlyPermission(final PharmaVendorPermission permission)
	{
		return permissions.size() == 1 && permissions.contains(permission);
	}
}
