package de.metas.pricing;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_ProductPrice;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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

@RequiredArgsConstructor
@Getter
public enum InvoicableQtyBasedOn implements ReferenceListAwareEnum
{
	CatchWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_CatchWeight),
	NominalWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_Nominal),
	;

	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<InvoicableQtyBasedOn> index = ReferenceListAwareEnums.index(values());

	public static InvoicableQtyBasedOn ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static InvoicableQtyBasedOn ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static InvoicableQtyBasedOn ofNullableCodeOrNominal(@Nullable final String code)
	{
		final InvoicableQtyBasedOn type = index.ofNullableCode(code);
		return type != null ? type : NominalWeight;
	}

	public boolean isCatchWeight() {return CatchWeight.equals(this);}
}
