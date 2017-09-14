package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.storage.IHUProductStorage;
import lombok.NonNull;

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

public class AddHUToPickingSlot
{
	private final PickingCandidateRepository pickingCandidateRepository;

	public AddHUToPickingSlot(@NonNull final PickingCandidateRepository pickingCandidateRepository)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	public void perform(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		// Get HU's single product storage
		final BigDecimal qty;
		final I_C_UOM uom;
		{
			final I_M_HU hu = load(huId, I_M_HU.class);
			final List<IHUProductStorage> productStorages = Services.get(IHUContextFactory.class)
					.createMutableHUContext()
					.getHUStorageFactory()
					.getStorage(hu)
					.getProductStorages();
			if (productStorages.isEmpty())
			{
				// Allow empty storage. That's the case when we are adding a newly created HU
				qty = BigDecimal.ZERO;
				uom = null;
				// throw new AdempiereException("HU is empty").setParameter("hu", hu);
			}
			else if (productStorages.size() > 1)
			{
				throw new AdempiereException("HU has more than one product").setParameter("productStorages", productStorages);
			}
			else
			{
				final IHUProductStorage productStorage = productStorages.get(0);
				qty = productStorage.getQty();
				uom = productStorage.getC_UOM();
			}

		}

		final I_M_Picking_Candidate pickingCandidate = pickingCandidateRepository.getCreateCandidate(huId, pickingSlotId, shipmentScheduleId);

		pickingCandidate.setQtyPicked(qty);
		pickingCandidate.setC_UOM(uom);
		save(pickingCandidate);
	}
}
