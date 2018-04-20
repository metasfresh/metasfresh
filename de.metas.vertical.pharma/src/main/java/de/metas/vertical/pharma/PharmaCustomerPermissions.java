package de.metas.vertical.pharma;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.EqualsAndHashCode;

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
public final class PharmaCustomerPermissions
{
	public static PharmaCustomerPermissions of(final I_C_BPartner bpartner)
	{
		if (!bpartner.isCustomer())
		{
			return NONE;
		}

		final ImmutableSet.Builder<PharmaCustomerPermission> permissionsBuilder = ImmutableSet.builder();
		if (bpartner.isPharmaAgentPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_AGENT);
		}
		if (bpartner.isPharmaciePermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMACIE);
		}
		if (bpartner.isPharmaManufacturerPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_MANUFACTURER);
		}
		if (bpartner.isPharmaWholesalePermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_WHOLESALE);
		}
		if (bpartner.isVeterinaryPharmacyPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.VETERINARY_PHARMACY);
		}

		final ImmutableSet<PharmaCustomerPermission> permissions = permissionsBuilder.build();
		if (permissions.isEmpty())
		{
			return NONE;
		}

		return new PharmaCustomerPermissions(permissions);
	}

	private static final PharmaCustomerPermissions NONE = new PharmaCustomerPermissions(ImmutableSet.of());

	private final ImmutableSet<PharmaCustomerPermission> permissions;

	private PharmaCustomerPermissions(final Set<PharmaCustomerPermission> permissions)
	{
		this.permissions = ImmutableSet.copyOf(permissions);
	}

	public boolean hasAtLeastOnePermission()
	{
		return !permissions.isEmpty();
	}
}
