package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.CreatePlanningHUsRequest;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.List;

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

	private static final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private static final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	/**
	 * @return true if given receipt schedule is eligible for receiving HUs
	 */
	public static ProcessPreconditionsResolution checkEligibleForReceivingHUs(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// Receipt schedule shall not be already closed
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

		final I_M_ReceiptSchedule receiptSchedule = context.getSelectedModel(I_M_ReceiptSchedule.class);

		// guard against null (might happen if the selected ID is not valid)
		if (receiptSchedule == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return checkEligibleForReceivingHUs(receiptSchedule);
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
		final IMutableHUContext huContextInitial =huContextFactory.createMutableHUContextForProcessing(getCtx(), ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()));

		final boolean isUpdateReceiptScheduleDefaultConfiguration = isUpdateReceiptScheduleDefaultConfiguration();


		final CreatePlanningHUsRequest request = CreatePlanningHUsRequest.builder()
				.lutuConfiguration(lutuConfiguration)
				.huContextInitial(huContextInitial)
				.receiptSchedule(receiptSchedule)
				.isUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration)
				.isDestroyExistingHUs(true)
				.build();
		final List<I_M_HU> hus = huReceiptScheduleBL.createPlanningHUs(request);

		hus.forEach(hu -> {
			updateAttributes(hu, receiptSchedule);
		});

		openHUsToReceive(hus);

		return MSG_OK;
	}

	protected final I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getRecord(I_M_ReceiptSchedule.class);
	}
}
