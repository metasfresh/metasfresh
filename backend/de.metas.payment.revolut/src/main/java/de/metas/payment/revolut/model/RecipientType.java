/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut.model;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum RecipientType implements ReferenceListAwareEnum
{
	COMPANY(X_Revolut_Payment_Export.RECIPIENTTYPE_COMPANY),
	INDIVIDUAL(X_Revolut_Payment_Export.RECIPIENTTYPE_INDIVIDUAL),
	;

	@Getter
	String code;

	@NonNull
	public static RecipientType ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<RecipientType> typesByCode = ReferenceListAwareEnums.index(values());
}
