package de.metas.handlingunits.inout.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialsCollector;

/**
 * Builder class to generate packing material shipment lines for a given shipment.
 *
 * @author tsa
 *
 */
public class HUShipmentPackingMaterialLinesBuilder
{
	// Services
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private boolean configurable = true;
	private I_M_InOut _shipment;

	private final HUPackingMaterialsCollector packingMaterialsCollector;

	private boolean _overrideExistingPackingMaterialLines = false;
	private boolean _updateOverrideValues = false; // default: don't override the overrides because we assume at they are user entered

	/**
	 * @see HUConstants#isQuickShipment()
	 */
	private boolean _manualLUCollected;

	/* package */HUShipmentPackingMaterialLinesBuilder()
	{
		super();

		final IHUContext huCtx = null; // task 07734: we don't want to track M_MaterialTrackings, so we don't need to provide a context.
		packingMaterialsCollector = new HUPackingMaterialsCollector(huCtx);
	}

	public void setM_InOut(final I_M_InOut shipment)
	{
		Check.assumeNotNull(shipment, "shipment not null");
		Check.assume(shipment.isSOTrx(), "InOut shall be a shipment: {}", shipment);
		assertConfigurable();

		_shipment = shipment;
	}

	public I_M_InOut getM_InOut()
	{
		Check.assumeNotNull(_shipment, "shipment not null");
		return _shipment;
	}

	/**
	 * Sets if we want to delete existing packing materials lines and recreate them.
	 *
	 * If this option is <code>false</code> and if there are any packing material lines already existing, this builder will not recreate them.
	 *
	 * @param overrideExistingPackingMaterialLines <code>true</code> if the packing material lines shall be recreated if they exists
	 */
	public void setOverrideExistingPackingMaterialLines(final boolean overrideExistingPackingMaterialLines)
	{
		assertConfigurable();
		_overrideExistingPackingMaterialLines = overrideExistingPackingMaterialLines;
	}

	private final boolean isOverrideExistingPackingMaterialLines()
	{
		return _overrideExistingPackingMaterialLines;
	}

	/**
	 *
	 * @return true if override values shall be calculated and updated now, in this run
	 */
	private final boolean isUpdateOverrideValues()
	{
		return _updateOverrideValues;
	}

	/**
	 * Sets if override values (e.g. {@link I_M_InOutLine#setQtyTU_Override(BigDecimal)}) shall be initialized now in this run.
	 *
	 * Enable this functionality only when u are generating shipment lines and you want to have some initial values for the overrides.
	 *
	 * @param updateOverrideValues true if the override values shall be initialized and set now
	 */
	public void setUpdateOverrideValues(final boolean updateOverrideValues)
	{
		assertConfigurable();
		_updateOverrideValues = updateOverrideValues;
	}

	private final void assertConfigurable()
	{
		Check.assume(configurable, "builder shall be in configurable state");
	}

	/**
	 * Collect packing materials and update QtyCU/QtyTU/etc on shipment lines.
	 */
	public void collectPackingMaterialsAndUpdateShipmentLines()
	{
		final I_M_InOut inout = getM_InOut();

		//
		// Iterate shipment lines and collect packing materials for LUs and TUs
		final List<I_M_InOutLine> inoutLines = inOutDAO.retrieveLines(inout, I_M_InOutLine.class);
		for (final I_M_InOutLine inoutLineHU : inoutLines)
		{
			collectHUs(inoutLineHU);
		}
	}

	public void build()
	{
		final I_M_InOut inout = getM_InOut();

		//
		// Handle existing packing material lines
		if (isOverrideExistingPackingMaterialLines())
		{
			deleteExistingPackingMaterialLines();
		}
		else if (huInOutDAO.hasPackingMaterialLines(inout))
		{
			// Case: this shipment has already backing material lines
			// and we are not allowed to override existing packing material lines
			// => nothing we can do
			return;
		}

		collectPackingMaterialsAndUpdateShipmentLines();

		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);

		//
		// Sets M_Product_ID sort comparator to use
		final Comparator<Integer> candidatesSortComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(inout.getC_BPartner_ID())
				.setC_DocType(inout.getC_DocType())
				.findProductIdsComparator();
		packingMaterialsCollector.setProductIdSortComparator(candidatesSortComparator);

		//
		// Create Packing Material InOut Lines based on collected packing materials
		for (final HUPackingMaterialDocumentLineCandidate pmCandidate : packingMaterialsCollector.getAndClearCandidates())
		{
			final I_M_InOutLine packingMaterialLine = createPackingMaterialLine(pmCandidate);

			// task 09502: set the reference from line to packing-line
			final List<I_M_InOutLine> sourceIols = pmCandidate.getSources();
			for (final I_M_InOutLine sourceIol : sourceIols)
			{
				sourceIol.setM_PackingMaterial_InOutLine(packingMaterialLine);
				InterfaceWrapperHelper.save(sourceIol);
			}
		}

		configurable = false;
	}

	/**
	 * Check if we are dealing with a shipment line where packing materials were specified by user
	 *
	 * @param inoutLine
	 * @return
	 * @see HUConstants#isQuickShipment()
	 */
	private final boolean isManualPackingMaterials(final I_M_InOutLine inoutLine)
	{
		final boolean manualPackingMaterial = inoutLine.isManualPackingMaterial();
		return manualPackingMaterial;
	}

	/**
	 * Collect TU packing materials from shipment line
	 *
	 * @param packingMaterialsCollector
	 * @param shipmentLine
	 */
	private final void collectHUs(final I_M_InOutLine shipmentLine)
	{
		Check.assume(packingMaterialsCollector.getCountTUs() == 0, "At this point CountTUs shall be zero: {}", packingMaterialsCollector);

		final boolean initializeOverrides = isUpdateOverrideValues();

		//
		// Collect packing materials from assigned TUs
		// NOTE: we are doing this because we also want to explicitly count them
		// If we would fetch all HUs it could be to count more TUs than there are actually assigned to this particular inout line
		final HUPackingMaterialsCollector packingMaterialsCollectorFromHUs = packingMaterialsCollector.splitNew();
		final IQueryBuilder<I_M_HU_Assignment> tuAssignmentsQuery = huAssignmentDAO.retrieveTUHUAssignmentsForModelQuery(shipmentLine);
		packingMaterialsCollectorFromHUs.addTUHUsRecursively(tuAssignmentsQuery);
		final int countTUs_Calculated = packingMaterialsCollectorFromHUs.getAndResetCountTUs();

		//
		// Collect TU packing materials from overrides
		int countTUs_Override = shipmentLine.getQtyTU_Override().intValueExact();
		// Case: we need to initialize the overrides, so we are using the countTUs_Calculated if it's a value set there.
		if (initializeOverrides && countTUs_Calculated > 0)
		{
			countTUs_Override = countTUs_Calculated;
		}
		final HUPackingMaterialsCollector packingMaterialsCollectorFromOverrides = packingMaterialsCollector.splitNew();
		final I_M_HU_PI_Item_Product huPIItemProduct = shipmentLine.getM_HU_PI_Item_Product_Override();
		if (huPIItemProduct != null)
		{
			final I_M_HU_PI huPI = huPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
			packingMaterialsCollectorFromOverrides.addM_HU_PI(huPI, countTUs_Override, shipmentLine);
		}
		//
		// Collect LU packing materials from overrides
		// NOTE: in case of quick shipment the requirement is to add the 1x(Default LU) for all lines
		if (isManualPackingMaterials(shipmentLine) && !_manualLUCollected)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(shipmentLine);
			final int adOrgId = shipmentLine.getAD_Org_ID();
			final I_M_HU_PI luPI = handlingUnitsDAO.retrieveDefaultLUOrNull(ctx, adOrgId);
			if (luPI != null)
			{
				packingMaterialsCollector.addM_HU_PI(luPI, 1, shipmentLine);
			}
			
			_manualLUCollected = true;
		}

		//
		// Decide which version we are actually collecting to create the shipment packing material lines
		if (isManualPackingMaterials(shipmentLine))
		{
			packingMaterialsCollectorFromOverrides.mergeBackToParentAndClear();
		}
		else
		{
			packingMaterialsCollectorFromHUs.mergeBackToParentAndClear();
		}

		//
		// Update Qty TU of this shipment line
		shipmentLine.setQtyTU_Calculated(BigDecimal.valueOf(countTUs_Calculated));
		if (initializeOverrides)
		{
			shipmentLine.setQtyTU_Override(BigDecimal.valueOf(countTUs_Override));
		}
		InterfaceWrapperHelper.save(shipmentLine);

		// Make sure the countTUs is reset
		packingMaterialsCollector.getAndResetCountTUs();
	}
	
	private I_M_InOutLine createPackingMaterialLine(final HUPackingMaterialDocumentLineCandidate pmCandidate)
	{
		final I_M_InOut inout = getM_InOut();
		final I_M_InOutLine pmInOutLine = inOutBL.newInOutLine(inout, I_M_InOutLine.class);
		huInOutBL.updatePackingMaterialInOutLine(pmInOutLine, pmCandidate);
		InterfaceWrapperHelper.save(pmInOutLine);

		return pmInOutLine;
	}

	/**
	 * Delete existing packing material lines
	 */
	private final void deleteExistingPackingMaterialLines()
	{
		final I_M_InOut inout = getM_InOut();
		final List<I_M_InOutLine> retrievePackingMaterialLines = huInOutDAO.retrievePackingMaterialLines(inout);
		for (final I_M_InOutLine packingLine : retrievePackingMaterialLines)
		{
			InterfaceWrapperHelper.delete(packingLine);
		}
	}
}
