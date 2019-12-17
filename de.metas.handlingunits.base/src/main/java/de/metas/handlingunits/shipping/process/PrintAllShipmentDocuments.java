/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipping.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ReportStarter;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;

import java.util.List;

public class PrintAllShipmentDocuments extends ReportStarter implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final List<I_M_ShipperTransportation> selectedModels = context.getSelectedModels(I_M_ShipperTransportation.class);
		if (selectedModels.size() == 1)
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
	}

	@Override
	protected ExecuteReportStrategy getExecuteReportStrategy()
	{
		return new PrintAllShipmentsDocumentsStrategy();
	}
}
