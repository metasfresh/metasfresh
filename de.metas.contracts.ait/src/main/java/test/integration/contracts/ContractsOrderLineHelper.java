package test.integration.contracts;

/*
 * #%L
 * de.metas.contracts.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.helper.OrderLineHelper;
import de.metas.adempiere.ait.helper.ProductPriceVO.LineType;
import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.subscription.model.I_C_OrderLine;

public class ContractsOrderLineHelper extends OrderLineHelper
{

	private I_C_Flatrate_Conditions contractConditions;

	public ContractsOrderLineHelper(OrderHelper parent, IHelper parentHelper, final LineType lineType)
	{
		super(parent, parentHelper, lineType);
	}

	public ContractsOrderLineHelper setContractConditions(I_C_Flatrate_Conditions contractConditions)
	{
		this.contractConditions = contractConditions;
		return this;
	}

	@Override
	public I_C_OrderLine createLine(final I_C_Order order)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(super.createLine(order), I_C_OrderLine.class);
		
		ol.setC_Flatrate_Conditions_ID(contractConditions.getC_Flatrate_Conditions_ID());
		
		InterfaceWrapperHelper.save(ol);
		
		return ol;
	}

}
