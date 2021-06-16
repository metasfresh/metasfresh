/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.name.strategy;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_BP_Group;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum BPartnerNameAndGreetingStrategyCode implements ReferenceListAwareEnum
{
	FirstContact(X_C_BP_Group.BPNAMEANDGREETINGSTRATEGY_FirstContact),
	MembershipContact(X_C_BP_Group.BPNAMEANDGREETINGSTRATEGY_MembershipContact);

	@Getter
	private final String code;

	BPartnerNameAndGreetingStrategyCode(@NonNull final String code)
	{
		this.code = code;
	}

	public static BPartnerNameAndGreetingStrategyCode ofCode(@NonNull final String code)
	{
		final BPartnerNameAndGreetingStrategyCode strategy = strategiesByCode.get(code);
		if (strategy == null)
		{
			throw new AdempiereException("No " + BPartnerNameAndGreetingStrategyCode.class + " found for code: " + code);
		}

		return strategy;
	}

	@Nullable
	public static BPartnerNameAndGreetingStrategyCode ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public boolean isFirstContact()
	{
		return this == FirstContact;
	}

	public boolean isMembershipContact()
	{
		return this == MembershipContact;
	}

	private static final ImmutableMap<String, BPartnerNameAndGreetingStrategyCode> strategiesByCode =
			Maps.uniqueIndex(Arrays.asList(values()), BPartnerNameAndGreetingStrategyCode::getCode);

}