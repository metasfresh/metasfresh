package de.metas.customs.process;

import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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
@Repository
public class ShipmentLinesForCustomsInvoiceRepo
{

	public boolean foundAtLeastOneUnregisteredShipment(final ImmutableList<InOutId> selectedShipments)
	{
		return selectedShipments
				.stream()
				.filter(this::isValidShipment)
				.map(inoutId -> retrieveValidLinesToExport(inoutId))
				.findAny()
				.isPresent();

	}

	public List<InOutAndLineId> retrieveValidLinesToExport(final ImmutableList<InOutId> selectedShipments)
	{
		final ImmutableList<InOutId> validShipments = selectedShipments
				.stream()
				.filter(this::isValidShipment)
				.collect(ImmutableList.toImmutableList());

		final List<InOutAndLineId> shipmentLinesToExport = new ArrayList<>();

		validShipments
				.stream()
				.forEach(shipmentId -> shipmentLinesToExport.addAll(retrieveValidLinesToExport(shipmentId)));

		return shipmentLinesToExport;

	}

	private List<InOutAndLineId> retrieveValidLinesToExport(final InOutId shipmentId)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		List<InOutAndLineId> shipmentLines = new ArrayList<>();

		final ImmutableList<InOutAndLineId> shipmentLinesToExport = inOutDAO.retrieveLinesForInOutId(shipmentId)
				.stream()
				.filter(this::isValidLineToExport)
				.collect(ImmutableList.toImmutableList());

		shipmentLines.addAll(shipmentLinesToExport);

		return shipmentLines;
	}

	private boolean isValidLineToExport(final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final InOutLineId inOutLineId = inoutAndLineId.getInOutLineId();

		final I_M_InOutLine shipmentLineRecord = inOutDAO.getLineByIdOutOfTrx(inOutLineId, I_M_InOutLine.class);

		if (shipmentLineRecord.isPackagingMaterial())
		{
			return false;
		}

		if (shipmentLineRecord.getC_OrderLine_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	private boolean isValidShipment(final InOutId shipmentId)
	{
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final I_M_InOut shipment = inOutDAO.getById(shipmentId);

		if (!shipment.isSOTrx())
		{
			return false;
		}

		if (inOutBL.isReversal(shipment))
		{
			return false;
		}

		final DocStatus shipmentDocStatus = DocStatus.ofCode(shipment.getDocStatus());
		if (!shipmentDocStatus.isCompletedOrClosed())
		{
			return false;
		}

		return true;
	}
}
