package de.metas.handlingunits.ddorder.api.impl;

import java.util.Collection;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_OrderLine;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.model.I_M_HU;

public class HUDDOrderBL implements IHUDDOrderBL
{
	@Override
	public DDOrderLinesAllocator createMovements()
	{
		return DDOrderLinesAllocator.newInstance();
	}

	@Override
	public void closeLine(final I_DD_OrderLine ddOrderLine)
	{
		ddOrderLine.setIsDelivered_Override(X_DD_OrderLine.ISDELIVERED_OVERRIDE_Yes);
		InterfaceWrapperHelper.save(ddOrderLine);

		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.clearHUsScheduledToMoveList(ddOrderLine);
	}

	@Override
	public void unassignHUs(final I_DD_OrderLine ddOrderLine, final Collection<I_M_HU> hus)
	{
		//
		// Unassign the given HUs from DD_OrderLine
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.unassignHUs(ddOrderLine, hus);

		//
		// Remove those HUs from scheduled to move list (task 08639)
		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.removeFromHUsScheduledToMoveList(ddOrderLine, hus);
	}
}
