/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.process;

import de.metas.bpartner.BPartnerId;
import de.metas.contacts.invoice.interim.command.InterimInvoiceFlatrateTermCreateCommand;
import de.metas.contracts.ConditionsId;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import lombok.NonNull;

import java.sql.Timestamp;

public class Create_InterimInvoice_Flatrate_Term extends JavaProcess implements IProcessPrecondition
{
	@Param(mandatory = true, parameterName = "C_Bpartner_ID")
	private int p_C_Bpartner_ID;
	@Param(mandatory = true, parameterName = "C_Flatrate_Conditions_ID")
	private int p_C_Flatrate_Conditions_ID;
	@Param(mandatory = true, parameterName = "M_Product_ID")
	private int p_M_Product_ID;
	@Param(mandatory = true, parameterName = "DateFrom")
	private Timestamp p_DateFrom;
	@Param(mandatory = true, parameterName = "DateTo")
	private Timestamp p_DateTo;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}
	@Override
	protected String doIt() throws Exception
	{
		InterimInvoiceFlatrateTermCreateCommand.builder()
				.ctx(getCtx())
				.bpartnerId(BPartnerId.ofRepoId(p_C_Bpartner_ID))
				.conditionsId(ConditionsId.ofRepoId(p_C_Flatrate_Conditions_ID))
				.productId(ProductId.ofRepoId(p_M_Product_ID))
				.orderLineId(OrderLineId.ofRepoId(getRecord_ID()))
				.dateFrom(p_DateFrom)
				.dateTo(p_DateTo)
				.build()
				.execute();

		return null;
	}

}
