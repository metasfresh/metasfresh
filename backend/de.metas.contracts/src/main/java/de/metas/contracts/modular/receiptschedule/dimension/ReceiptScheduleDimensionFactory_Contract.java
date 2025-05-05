/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.receiptschedule.dimension;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReceiptScheduleDimensionFactory_Contract extends ReceiptScheduleDimensionFactory
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public void updateRecord(@NonNull final I_M_ReceiptSchedule record)
	{
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofNullable(OrderId.ofRepoIdOrNull(record.getC_Order_ID()),
																		OrderLineId.ofRepoIdOrNull(record.getC_OrderLine_ID()));

		if (orderAndLineId == null)
		{
			return;
		}

		final FlatrateTermId flatrateTermId = extractContractId(orderBL.getLineById(orderAndLineId)).orElse(null);

		if (flatrateTermId == null)
		{
			return;
		}

		record.setC_Flatrate_Term_ID(flatrateTermId.getRepoId());
	}

	@NonNull
	private Optional<FlatrateTermId> extractContractId(@NonNull final I_C_OrderLine record)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(record.getC_OrderLine_ID());
		return flatrateDAO.getByOrderLineId(orderLineId, TypeConditions.MODULAR_CONTRACT)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}
}
