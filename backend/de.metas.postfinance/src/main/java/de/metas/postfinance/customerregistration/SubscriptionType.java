/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.customerregistration;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import de.metas.postfinance.model.X_PostFinance_Customer_Registration_Message;

@AllArgsConstructor
public enum SubscriptionType implements ReferenceListAwareEnum
{
	REGISTRATION(X_PostFinance_Customer_Registration_Message.SUBSCRIPTIONTYPE_Registration), //
	DIRECT_REGISTRATION(X_PostFinance_Customer_Registration_Message.SUBSCRIPTIONTYPE_DirectRegistration), //
	DE_REGISTRATION(X_PostFinance_Customer_Registration_Message.SUBSCRIPTIONTYPE_Deregistration);

	@NonNull
	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<SubscriptionType> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static SubscriptionType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isDeRegistration()
	{
		return this == DE_REGISTRATION;
	}
}
