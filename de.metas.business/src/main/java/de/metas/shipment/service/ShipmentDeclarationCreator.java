package de.metas.shipment.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.shipment.ShipmentDeclarationVetoer;

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
@Service
public class ShipmentDeclarationCreator
{
	private Set<ShipmentDeclarationVetoer> shipmentDeclarationVetoers = new HashSet<>();

	public void registerVetoer(final ShipmentDeclarationVetoer shipmentDeclarationVetoer)
	{
		shipmentDeclarationVetoers.add(shipmentDeclarationVetoer);
	}

	@Autowired
	ShipmentDeclarationRepository shipmentDeclarationRepo;

	public void generateShipmentDeclarations(final ShipmentDeclarationConfigId shipmentDeclarationCreatorId, final List<InOutAndLineId> shipmentLineIds)
	{

		final ShipmentDeclarationConfig config = shipmentDeclarationRepo.getConfigById(shipmentDeclarationCreatorId);

		final int documentLinesNumber = config.getDocumentLinesNumber().intValue();

		Iterator<List<InOutAndLineId>> inoutLineIdsSubsets = Lists.partition(shipmentLineIds, documentLinesNumber).iterator();

		while (inoutLineIdsSubsets.hasNext())
		{
			final List<InOutAndLineId> inOutLinesForShipmentDeclaration = inoutLineIdsSubsets.next();

			shipmentDeclarationRepo.createAndCompleteShipmentDeclaration(config, inOutLinesForShipmentDeclaration);
		}
	}

	public void createShipmentDeclarationsIfNeeded(final InOutId inoutId)
	{
		for (ShipmentDeclarationVetoer vetoer : shipmentDeclarationVetoers)
		{
			vetoer.onFoundCompletedShipment(inoutId);
		}
	}
}
