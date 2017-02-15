package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */ abstract class WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_Base extends JavaProcess implements IProcessPrecondition
{
	protected abstract I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template);
	protected abstract boolean isUpdateReceiptScheduleDefaultConfiguration();
	
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		// Receipt schedule shall not be already closed
		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);
		if (receiptScheduleBL.isClosed(receiptSchedule))
		{
			return ProcessPreconditionsResolution.reject("receipt schedule closed");
		}

		// Receipt schedule shall not be about packing materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return ProcessPreconditionsResolution.reject("not applying for packing materials");
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected static I_M_HU_LUTU_Configuration getCurrentLUTUConfiguration(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfig = Services.get(IHUReceiptScheduleBL.class)
				.createLUTUConfigurationManager(receiptSchedule)
				.getCreateLUTUConfiguration();

		// Make sure nobody is overriding the existing configuration
		if (lutuConfig.getM_HU_LUTU_Configuration_ID() > 0)
		{
			InterfaceWrapperHelper.setSaveDeleteDisabled(lutuConfig, true);
		}

		return lutuConfig;
	}

	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(this)
				.addM_ReceiptSchedule(receiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration());

		//
		// Get/Create the initial LU/TU configuration
		final I_M_HU_LUTU_Configuration lutuConfigurationOrig = getCurrentLUTUConfiguration(receiptSchedule);

		//
		// Create the effective LU/TU configuration
		final I_M_HU_LUTU_Configuration lutuConfiguration = createM_HU_LUTU_Configuration(lutuConfigurationOrig);
		Services.get(ILUTUConfigurationFactory.class).save(lutuConfiguration);
		huGenerator.setM_HU_LUTU_Configuration(lutuConfiguration);

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
		getResult().setRecordsToOpen(TableRecordReference.ofList(hus));

		return MSG_OK;
	}

	protected final I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getRecord(I_M_ReceiptSchedule.class);
	}
}
