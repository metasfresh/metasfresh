/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference.product;

import de.metas.adempiere.model.I_M_Product;
import de.metas.externalreference.IExternalReferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;

import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_Product;

@AllArgsConstructor
@Getter
public enum ProductExternalReferenceType implements IExternalReferenceType
{
	PRODUCT(TYPE_Product, I_M_Product.Table_Name);

	private final String code;
	private final String tableName;

	public static ProductExternalReferenceType ofCode(final String code)
	{
		if (PRODUCT.getCode().equals(code))
		{
			return PRODUCT;
		}
		throw new AdempiereException("Unsupported code " + code + " for ProductExternalReferenceType. Hint: only 'Product' is allowed");
	}
}
