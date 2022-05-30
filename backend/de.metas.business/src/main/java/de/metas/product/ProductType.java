package de.metas.product;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_Product;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@AllArgsConstructor
public enum ProductType implements ReferenceListAwareEnum
{
	Item(X_M_Product.PRODUCTTYPE_Item), // I
	Service(X_M_Product.PRODUCTTYPE_Service), // S
	Resource(X_M_Product.PRODUCTTYPE_Resource), // R
	ExpenseType(X_M_Product.PRODUCTTYPE_ExpenseType), // E
	Online(X_M_Product.PRODUCTTYPE_Online), // O
	FreightCost(X_M_Product.PRODUCTTYPE_FreightCost), // F
	Food(X_M_Product.PRODUCTTYPE_Nahrung)//N
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ProductType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@Nullable
	public static ProductType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static ProductType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static String toCodeOrNull(@Nullable final ProductType type) {return type != null ? type.getCode() : null;}

	public boolean isItem() {return this == Item;}

	/**
	 * @return {@code true} <b>for every</b> non-item, not just {@link #Service}.
	 */
	public boolean isService() {return this != Item;}

	public boolean isFreightCost() {return this == FreightCost;}

	public boolean isResource() {return this == Resource;}
}
