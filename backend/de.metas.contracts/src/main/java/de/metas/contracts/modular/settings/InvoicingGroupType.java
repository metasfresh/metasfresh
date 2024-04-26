/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.settings;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import static de.metas.contracts.model.X_ModCntr_Module.INVOICINGGROUP_AD_Reference_ID;
import static de.metas.contracts.model.X_ModCntr_Module.INVOICINGGROUP_Costs;
import static de.metas.contracts.model.X_ModCntr_Module.INVOICINGGROUP_Service;

@Getter
@AllArgsConstructor
public enum InvoicingGroupType implements ReferenceListAwareEnum
{
	SERVICES(INVOICINGGROUP_Service),
	COSTS(INVOICINGGROUP_Costs);

	private static final ReferenceListAwareEnums.ValuesIndex<InvoicingGroupType> index = ReferenceListAwareEnums.index(values());

	@NonNull
	private final String code;

	@NonNull
	public static InvoicingGroupType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isServicesType()
	{
		return this == SERVICES;
	}

	public boolean isCostsType()
	{
		return this == COSTS;
	}

	public ITranslatableString getDisplayName()
	{
		return TranslatableStrings.adRefList(INVOICINGGROUP_AD_Reference_ID, code);
	}
}