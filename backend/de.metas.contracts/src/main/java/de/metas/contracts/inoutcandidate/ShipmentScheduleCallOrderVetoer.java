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

package de.metas.contracts.inoutcandidate;

import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

public class ShipmentScheduleCallOrderVetoer implements ModelWithoutShipmentScheduleVetoer
{
	private final CallOrderContractService callOrderContractService;

	public ShipmentScheduleCallOrderVetoer(@NonNull final CallOrderContractService callOrderContractService)
	{
		this.callOrderContractService = callOrderContractService;
	}

	@Override
	public OnMissingCandidate foundModelWithoutInOutCandidate(final Object model)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final boolean isCallOrderContractLine = callOrderContractService.isCallOrderContractLine(ol);

		return isCallOrderContractLine ? OnMissingCandidate.I_VETO : OnMissingCandidate.I_DONT_CARE;
	}
}
