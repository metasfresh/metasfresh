package org.eevolution.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_PP_Product_Planning.class)
public class PP_Product_Planning
{
	public static final PP_Product_Planning INSTANCE = new PP_Product_Planning();

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID //
	)
	public void updateStorageAttributesKey(@NonNull final I_PP_Product_Planning productPlanning)
	{
		final AttributesKey attributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(productPlanning.getM_AttributeSetInstance_ID())
				.orElse(AttributesKey.NONE);

		productPlanning.setStorageAttributesKey(attributesKey.getAsString());
	}
}
