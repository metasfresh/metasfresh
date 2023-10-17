/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.receiptschedule.impl;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.CreatePlanningHUsRequest;
import de.metas.organization.ClientAndOrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import java.util.List;

class CreatePlanningHUsCommand
{
	//
	// Services
	@NonNull private final IHUContextFactory huContextFactory;
	@NonNull private final PlanningHUAttributesUpdater attributesUpdater;

	//
	// Parameters
	@NonNull I_M_ReceiptSchedule receiptSchedule;
	@NonNull I_M_HU_LUTU_Configuration lutuConfiguration;
	boolean isUpdateReceiptScheduleDefaultConfiguration;
	boolean isDestroyExistingHUs;

	@Builder
	private CreatePlanningHUsCommand(
			final @NonNull IHUContextFactory huContextFactory,
			final @NonNull PlanningHUAttributesUpdater attributesUpdater,
			//
			final @NonNull CreatePlanningHUsRequest request)
	{
		this.huContextFactory = huContextFactory;
		this.attributesUpdater = attributesUpdater;

		this.receiptSchedule = request.getReceiptSchedule();
		this.lutuConfiguration = request.getLutuConfiguration();
		this.isUpdateReceiptScheduleDefaultConfiguration = request.isUpdateReceiptScheduleDefaultConfiguration();
		this.isDestroyExistingHUs = request.isDestroyExistingHUs();
	}

	public List<I_M_HU> execute()
	{
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(Env.getCtx(), ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID()));

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContextInitial)
				.addM_ReceiptSchedule(receiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(isUpdateReceiptScheduleDefaultConfiguration)
				.setM_HU_LUTU_Configuration(lutuConfiguration);


		huGenerator.setDestroyExistingHUs(isDestroyExistingHUs);
		//
		// Calculate the target CUs that we want to allocate
		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + lutuConfiguration);
		}
		huGenerator.setQtyToAllocateTarget(qtyCUsTotal);

		//
		// Generate the HUs
		final List<I_M_HU> hus = huGenerator.generateWithinOwnTransaction();

		hus.forEach(hu -> attributesUpdater.updateAttributes(hu, receiptSchedule));

		return hus;
	}
}
