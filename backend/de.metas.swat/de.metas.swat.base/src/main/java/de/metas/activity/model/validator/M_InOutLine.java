package de.metas.activity.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.acct.api.IProductAcctDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	private final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
	private final IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_InOutLine.COLUMNNAME_M_Product_ID })
	public void updateActivity(final I_M_InOutLine inOutLine)
	{
		if (inOutLine.getC_Activity_ID() > 0)
		{
			return; // was already set, so don't try to auto-fill it
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(inOutLine.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final ActivityId productActivityId = productAcctDAO.retrieveActivityForAcct(
				ClientId.ofRepoId(inOutLine.getAD_Client_ID()),
				OrgId.ofRepoId(inOutLine.getAD_Org_ID()),
				productId);
		if (productActivityId == null)
		{
			return;
		}

		final Dimension orderLineDimension = dimensionService.getFromRecord(inOutLine);
		if (orderLineDimension == null)
		{
			//nothing to do
			return;
		}

		dimensionService.updateRecord(inOutLine, orderLineDimension.withActivityId(productActivityId));
	}
}
