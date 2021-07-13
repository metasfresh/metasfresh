/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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
package de.metas.payment.esr.dataimporter;

import javax.annotation.Nullable;

import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

public enum ESRType implements ReferenceListAwareEnum
{
	TYPE_QRR(X_ESR_ImportLine.TYPE_QRR),
	TYPE_ESR(X_ESR_ImportLine.TYPE_ESR);

	@Getter
	private final String code;
	
	
	ESRType(@NonNull final String code)
	{
		this.code = code;
	}

	public static ESRType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
	
	@Nullable
	public static ESRType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
	
	private static final ValuesIndex<ESRType> index = ReferenceListAwareEnums.index(values());

}
