package de.metas.inoutcandidate.process;

import java.util.List;

import org.adempiere.uom.api.Quantity;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class M_ReceiptSchedule_GeneratePlanningHUs extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final ReceiptScheduleHUGenerator huGenerator = new ReceiptScheduleHUGenerator();
		huGenerator.setContext(this);
		final I_M_ReceiptSchedule schedule = getRecord(I_M_ReceiptSchedule.class);
		huGenerator.addM_ReceiptSchedule(schedule);

		//
		// Get/Create and Edit LU/TU configuration
		final IDocumentLUTUConfigurationManager lutuConfigurationManager = huGenerator.getLUTUConfigurationManager();

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationManager.createAndEdit(lutuConfigurationToEdit -> lutuConfigurationToEdit);

		//
		// No configuration => user cancelled => don't open editor
		if (lutuConfiguration == null)
		{
			return null;
		}

		//
		// Calculate the target CUs that we want to allocate
		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new TerminalException("LU/TU configuration is resulting to infinite quantity: " + lutuConfiguration);
		}
		huGenerator.setQtyToAllocateTarget(qtyCUsTotal);

		//
		// Generate the HUs
		final List<I_M_HU> hus = huGenerator.generate();
		getResult().setRecordsToSelectAfterExecution(TableRecordReference.ofList(hus));

		return MSG_OK;
	}
}
