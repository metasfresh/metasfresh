/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.workorder.undertest.interceptor;

import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project_WO_ObjectUnderTest.class)
@Component
public class C_Project_WO_ObjectUnderTest
{
	private final IProductBL productBL = Services.get(IProductBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WOObjectName)
	public void updateWOProjectObjectUnderTestProductId(final I_C_Project_WO_ObjectUnderTest objectUnderTest)
	{
		if (objectUnderTest.getM_Product_ID() > 0 || Check.isBlank(objectUnderTest.getWOObjectName()))
		{
			return;
		}

		final ProductId productId = productBL.getProductIdByValue(OrgId.ofRepoId(objectUnderTest.getAD_Org_ID()),
																  objectUnderTest.getWOObjectName());

		objectUnderTest.setM_Product_ID(ProductId.toRepoId(productId));
	}
}
