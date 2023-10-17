package de.metas.ui.web.handlingunits.process;

import java.util.List;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

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

/**
 * Base class for receiving HUs from selected receipt schedule.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */ abstract class WEBUI_M_ReceiptSchedule_ReceiveHUs_Base extends ReceiptScheduleBasedProcess
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	protected abstract I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template);

	protected abstract boolean isUpdateReceiptScheduleDefaultConfiguration();
	private static final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private static final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

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

		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);

		// guard against null (might happen if the selected ID is not valid)
		if (receiptSchedule == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return checkEligibleForReceivingHUs(receiptSchedule);
	}

	/**
	 * @return true if given receipt schedule is eligible for receiving HUs
	 */
	public static ProcessPreconditionsResolution checkEligibleForReceivingHUs(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// Receipt schedule shall not be already closed
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
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
		return huReceiptScheduleBL.getCurrentLUTUConfiguration(receiptSchedule);
	}


	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();

		//
		// Get/Create the initial LU/TU configuration
		final I_M_HU_LUTU_Configuration lutuConfigurationOrig = getCurrentLUTUConfiguration(receiptSchedule);
		final I_M_HU_LUTU_Configuration lutuConfiguration = createM_HU_LUTU_Configuration(lutuConfigurationOrig);
		lutuConfigurationFactory.save(lutuConfiguration);

		final List<I_M_HU> hus = huReceiptScheduleBL.createPlanningHUs(
				CreatePlanningHUsRequest.builder()
						.lutuConfiguration(lutuConfiguration)
						.receiptSchedule(receiptSchedule)
						.isUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration())
						.isDestroyExistingHUs(true)
						.build());

		openHUsToReceive(hus);

		return MSG_OK;
	}

	protected final I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getRecord(I_M_ReceiptSchedule.class);
	}
}
