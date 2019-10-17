package de.metas.vertical.pharma;

import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
public final class PharmaCustomerPermissions
{
	public static PharmaCustomerPermissions of(@NonNull final org.compiere.model.I_C_BPartner bpartner)
	{
		if (!bpartner.isCustomer())
		{
			return NONE;
		}

		final I_C_BPartner pharmaBPartner = InterfaceWrapperHelper.create(bpartner, I_C_BPartner.class);

		final ImmutableSet.Builder<PharmaCustomerPermission> permissionsBuilder = ImmutableSet.builder();
		if (pharmaBPartner.isPharmaAgentPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_AGENT);
		}
		if (pharmaBPartner.isPharmaciePermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMACIE);
		}
		if (pharmaBPartner.isPharmaManufacturerPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_MANUFACTURER);
		}
		if (pharmaBPartner.isPharmaWholesalePermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_WHOLESALE);
		}
		if (pharmaBPartner.isVeterinaryPharmacyPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.VETERINARY_PHARMACY);
		}
		if (pharmaBPartner.isPharmaCustomerNarcoticsPermission())
		{
			permissionsBuilder.add(PharmaCustomerPermission.PHARMA_NARCOTICS);
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

	public boolean hasPermission(final PharmaCustomerPermission permission)
	{
		return permissions.contains(permission);
	}

	public boolean hasOnlyPermission(final PharmaCustomerPermission permission)
	{
		return permissions.size() == 1 && permissions.contains(permission);
	}

	public ITranslatableString toTrlString()
	{
		if (permissions.isEmpty())
		{
			return TranslatableStrings.anyLanguage("-");
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		for (final PharmaCustomerPermission permission : permissions)
		{
			if (!builder.isEmpty())
			{
				builder.append(", ");
			}

			builder.append(msgBL.translatable(permission.getDisplayNameAdMessage()));
		}

		return builder.build();
	}
}
