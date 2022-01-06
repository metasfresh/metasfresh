/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.shopware6;

import de.metas.common.util.Check;
import de.metas.externalsystem.model.X_ExternalSystem_Config_Shopware6Mapping;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum BPartnerLookup implements ReferenceListAwareEnum
{
	MetsafreshId(X_ExternalSystem_Config_Shopware6Mapping.BPARTNERLOOKUPVIA_Metasfresh_ID),
	ExternalReference(X_ExternalSystem_Config_Shopware6Mapping.BPARTNERLOOKUPVIA_Shopware6_ID);

	@Getter
	final String code;

	@Nullable
	public static String toCode(@Nullable final BPartnerLookup bPartnerLookup)
	{
		if (bPartnerLookup == null)
		{
			return null;
		}

		return bPartnerLookup.getCode();
	}

	@NonNull
	public static BPartnerLookup ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static BPartnerLookup ofCodeOrNull(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}
		return ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<BPartnerLookup> typesByCode = ReferenceListAwareEnums.index(values());

}
