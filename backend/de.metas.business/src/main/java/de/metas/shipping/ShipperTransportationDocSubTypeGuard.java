/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipping;

import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * {@code M_ShipperTransportation} carries two different documents - a plain transport order and a delivery
 * instruction - distinguished only by {@link DocSubType#DeliveryInstruction}. A number of processes are attached to
 * the table but apply to only ONE of the two documents; this guard is the single place that decides which.
 */
@Service
public class ShipperTransportationDocSubTypeGuard
{
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	/**
	 * @return the {@link DocSubType} of the given {@code shipperTransportation}'s document type, or {@link DocSubType#NONE} when it has none set.
	 */
	@NonNull
	public DocSubType getDocSubType(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(shipperTransportation.getC_DocType_ID());
		if (docTypeId == null)
		{
			return DocSubType.NONE;
		}
		return DocSubType.ofNullableCode(docTypeDAO.getById(docTypeId).getDocSubType());
	}

	public boolean isDeliveryInstruction(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		return getDocSubType(shipperTransportation).isDeliveryInstruction();
	}

	/**
	 * Precondition guard for a process that applies only to a transport order (never to a delivery instruction).
	 */
	@NonNull
	public ProcessPreconditionsResolution rejectIfDeliveryInstruction(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		return isDeliveryInstruction(shipperTransportation)
				? ProcessPreconditionsResolution.rejectWithInternalReason("Not applicable to a delivery instruction")
				: ProcessPreconditionsResolution.accept();
	}

	/**
	 * Precondition guard for a process that applies only to a delivery instruction (never to a plain transport order).
	 */
	@NonNull
	public ProcessPreconditionsResolution rejectIfNotDeliveryInstruction(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		return isDeliveryInstruction(shipperTransportation)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Not applicable to a transport order");
	}
}
