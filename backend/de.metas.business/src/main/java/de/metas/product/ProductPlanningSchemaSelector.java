package de.metas.product;

import com.google.common.collect.ImmutableMap;
import de.metas.product.model.X_M_Product_PlanningSchema;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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

public enum ProductPlanningSchemaSelector implements ReferenceListAwareEnum
{
	NORMAL(X_M_Product_PlanningSchema.M_PRODUCTPLANNINGSCHEMA_SELECTOR_Normal), //
	GENERATE_QUOTATION_BOM_PRODUCT(X_M_Product_PlanningSchema.M_PRODUCTPLANNINGSCHEMA_SELECTOR_QuotationBOMProduct) //
	;

	private static final ImmutableMap<String, ProductPlanningSchemaSelector> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	@Getter
	private final String code;

	ProductPlanningSchemaSelector(final String code)
	{
		this.code = code;
	}

	@Nullable
	public static ProductPlanningSchemaSelector ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static ProductPlanningSchemaSelector ofCode(@NonNull final String code)
	{
		final ProductPlanningSchemaSelector type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ProductPlanningSchemaSelector.class + " found for: " + code);
		}
		return type;
	}
}
