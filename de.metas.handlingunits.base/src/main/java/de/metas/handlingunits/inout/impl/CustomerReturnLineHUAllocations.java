package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.document.impl.AbstractHUAllocations;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleAllocBuilder;
import de.metas.handlingunits.storage.IProductStorage;

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

public class CustomerReturnLineHUAllocations extends AbstractHUAllocations

{

	private static transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);

	public CustomerReturnLineHUAllocations(final I_M_InOutLine inOutLine, final IProductStorage productStorage)
	{
		super(inOutLine, productStorage);

	}

	@Override
	protected void createAllocation(
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU vhu,
			final BigDecimal qtyToAllocate,
			final I_C_UOM uom,
			final boolean deleteOldTUAllocations)
	{
		// In case TU is null, consider using VHU as HU (i.e. the case of an VHU on LU, or free VHU)
		// NOTE: we do this shit because in some BLs TU is assumed to be there not null
		// and also, before VHU level allocation the TU field was filled with VHU.
		final I_M_HU tuHUActual = tuHU == null ? vhu : tuHU;

		Check.assumeNotNull(tuHUActual, "tuHU not null");

		final IContextAware contextProvider = getContextProvider();
		final I_M_InOutLine inOutLine = getDocumentLineModel();

		if (deleteOldTUAllocations)
		{
			deleteAllocationsOfTU(inOutLine, tuHUActual);
		}

		final HUCustomerReturnAllocBuilder builder = new HUCustomerReturnAllocBuilder();
		builder.setContext(contextProvider)

				.setM_InOutLine(inOutLine)
				.setQtyToAllocate(BigDecimal.ZERO)
				.setQtyWithIssues(BigDecimal.ZERO) // to be sure...
		;
		builder.setHU_QtyAllocated(qtyToAllocate, uom)
				.setM_LU_HU(luHU)
				.setM_TU_HU(tuHUActual)
				.setVHU(vhu);

		// Create RSA and save it
		builder.buildAndSave();

	}

	private void deleteAllocationsOfTU(final I_M_InOutLine inOutLine, final I_M_HU tuHU)
	{

		final String trxName = getTrxName();

		final List<I_M_HU> tradingUnitsToUnassign = Collections.singletonList(tuHU);
		huInOutDAO.deleteTradingUnitAllocations(inOutLine, tradingUnitsToUnassign, trxName);
	}

	@Override
	protected void deleteAllocations(Collection<I_M_HU> husToUnassign)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void deleteAllocations()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected final I_M_InOutLine getDocumentLineModel()
	{
		return (I_M_InOutLine)super.getDocumentLineModel();
	}

}
