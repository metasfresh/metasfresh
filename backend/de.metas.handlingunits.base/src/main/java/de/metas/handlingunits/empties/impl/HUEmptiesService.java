package de.metas.handlingunits.empties.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.empties.EmptiesMovementProducer;
import de.metas.handlingunits.empties.EmptiesMovementProducer.EmptiesMovementDirection;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.inout.returns.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inout.IInOutDAO;
import de.metas.material.planning.ddorder.IDistributionNetworkDAO;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
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

public class HUEmptiesService implements IHUEmptiesService
{
	@Override
	public I_M_Warehouse getEmptiesWarehouse(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		// services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);

		// In case the requirements will change and the empties ditribution network
		// will be product based, here we will need to get the product gebinde
		// and send it as parameter in the method above
		final I_DD_NetworkDistribution emptiesNetworkDistribution = handlingUnitsDAO.retrieveEmptiesDistributionNetwork(Env.getCtx(),
				null, // Product
				ITrx.TRXNAME_None);
		if (emptiesNetworkDistribution == null)
		{
			throw new AdempiereException("@NotFound@ @DD_NetworkDistribution_ID@ (@IsHUDestroyed@=@Y@)");
		}

		final List<I_DD_NetworkDistributionLine> lines = distributionNetworkDAO.retrieveNetworkLinesBySourceWarehouse(emptiesNetworkDistribution, warehouse.getM_Warehouse_ID());

		if (lines.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsHUDestroyed@=@Y@): " + warehouse.getName()
					+ "\n @DD_NetworkDistribution_ID@: " + emptiesNetworkDistribution);
		}

		return lines.get(0).getM_Warehouse();
	}

	@Override
	public I_M_Locator getEmptiesLocator(final I_M_Warehouse warehouse)
	{
		final I_M_Warehouse emptiesWarehouse = getEmptiesWarehouse(warehouse);
		final I_M_Locator emptiesLocator = InterfaceWrapperHelper.create(Services.get(IWarehouseBL.class).getDefaultLocator(emptiesWarehouse), I_M_Locator.class);
		return emptiesLocator;
	}

	@Override
	public EmptiesMovementProducer newEmptiesMovementProducer()
	{
		return EmptiesMovementProducer.newInstance();
	}

	@Override
	public void generateMovementFromEmptiesInout(final I_M_InOut emptiesInOut)
	{
		Check.assumeNotNull(emptiesInOut, "Parameter emptiesInOut is not null");

		//
		// Fetch shipment/receipt lines and convert them to packing material line candidates.
		final List<HUPackingMaterialDocumentLineCandidate> lines = Services.get(IInOutDAO.class).retrieveLines(emptiesInOut, I_M_InOutLine.class)
				.stream()
				.map(line -> HUPackingMaterialDocumentLineCandidate.of(
						loadOutOfTrx(line.getM_Locator_ID(), I_M_Locator.class),
						loadOutOfTrx(line.getM_Product_ID(), I_M_Product.class),
						line.getMovementQty().intValueExact()))
				.collect(GuavaCollectors.toImmutableList());

		//
		// Generate the empties movement
		newEmptiesMovementProducer()
				.setEmptiesMovementDirection(emptiesInOut.isSOTrx() ? EmptiesMovementDirection.ToEmptiesWarehouse : EmptiesMovementDirection.FromEmptiesWarehouse)
				.setReferencedInOutId(emptiesInOut.getM_InOut_ID())
				.addCandidates(lines)
				.createMovements();
	}

	@Override
	public boolean isEmptiesInOut(@NonNull final I_M_InOut inout)
	{
		final I_C_DocType docType = loadOutOfTrx(inout.getC_DocType_ID(), I_C_DocType.class);
		if (docType == null || docType.getC_DocType_ID() <= 0)
		{
			return false;
		}

		final String docSubType = docType.getDocSubType();
		return X_C_DocType.DOCSUBTYPE_Leergutanlieferung.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_Leergutausgabe.equals(docSubType);
	}

	@Override
	public IReturnsInOutProducer newReturnsInOutProducer(final Properties ctx)
	{
		return new EmptiesInOutProducer(ctx);
	}

}
