/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.bpartner.service;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Order;

import java.math.BigDecimal;

public class MembershipOrderCreateCommand
{

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final OrderId existingMembershipOrderId;
	private final ProductId productId;
	private final ConditionsId conditionsId;

	@Builder
	public MembershipOrderCreateCommand(@NonNull final OrderId existingMembershipOrderId,
			@NonNull final ProductId productId,
			@NonNull final ConditionsId conditionsId)
	{
		this.existingMembershipOrderId = existingMembershipOrderId;
		this.productId = productId;
		this.conditionsId = conditionsId;
	}

	public void execute()
	{
		final I_C_Order existingMembershipOrderRecord = orderBL.getById(existingMembershipOrderId);

		final I_C_Order newSalesOrder = copyOrderHeader(existingMembershipOrderRecord);

		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(newSalesOrder, I_C_OrderLine.class);

		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		orderLine.setQtyEntered(BigDecimal.ONE);
		orderLine.setQtyOrdered(BigDecimal.ONE);

		orderDAO.save(orderLine);

		documentBL.processEx(newSalesOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

	}

	private I_C_Order copyOrderHeader(@NonNull final I_C_Order initialMembershipOrder)
	{
		final I_C_Order newSalesOrder = InterfaceWrapperHelper.copy()
				.setFrom(initialMembershipOrder)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);

		newSalesOrder.setDocStatus(DocStatus.Drafted.getCode());
		newSalesOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		orderDAO.save(newSalesOrder);

		return newSalesOrder;
	}

}
