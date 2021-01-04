/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.customerreturns.process;

import de.metas.inout.InOutId;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

public class CustomerReturns_AddSpareParts
		extends CustomerReturnsBasedProcess
		implements IProcessPrecondition, IProcessParametersCallout
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private ProductId productId;

	@Param(parameterName = "Qty", mandatory = true)
	private BigDecimal qtyBD;

	private static final String PARAM_C_UOM_ID = "C_UOM_ID";
	@Param(parameterName = PARAM_C_UOM_ID, mandatory = true)
	private UomId uomId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return checkSingleDraftedServiceRepairReturns(context);
	}

	@Override
	public void onParameterChanged(@NonNull final String parameterName)
	{
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			final ProductId productId = this.productId;
			uomId = productId != null
					? productBL.getStockUOMId(productId)
					: null;
		}
	}

	@Override
	protected String doIt()
	{
		final InOutId customerReturnId = getCustomerReturnId();
		final Quantity qtyReturned = getQtyReturned();

		repairCustomerReturnsService.prepareAddSparePartsToCustomerReturn()
				.customerReturnId(customerReturnId)
				.productId(productId)
				.qtyReturned(qtyReturned)
				.build();

		return MSG_OK;
	}

	private Quantity getQtyReturned()
	{
		final I_C_UOM uom = uomDAO.getById(uomId);
		final Quantity qtyReturned = Quantity.of(qtyBD, uom);
		return qtyReturned;
	}
}
