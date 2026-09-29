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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.inout.impl.ShipmentPackingUnitProjectConflictDetector.PackingUnit;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.spi.impl.InOutLineHUPackingMaterialCollectorSource;
import de.metas.project.ProjectId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_M_InOut;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Builder class to generate packing material shipment lines for a given shipment.
 *
 * @author tsa
 */
public class HUShipmentPackingMaterialLinesBuilder
{
	// Constants
	private static final String SYSCONFIG_SplitShipmentPackingMaterialLinesByProject = "de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject";

	// Services
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private boolean configurable = true;
	private I_M_InOut _shipment;

	private final HUPackingMaterialsCollector packingMaterialsCollector;

	private boolean _overrideExistingPackingMaterialLines = false;
	private boolean _updateOverrideValues = false; // default: don't override the overrides because we assume at they are user entered

	/**
	 * @see HUConstants#isQuickShipment()
	 */
	private boolean _manualLUCollected;

	/**
	 * The default-LU packing instruction added for this shipment, and the first manual-packing line it was added for.
	 */
	@Nullable private HuPackingInstructionsId _defaultLUPIId;
	@Nullable private InOutLineId _defaultLUFirstLineId;

	/* package */ HUShipmentPackingMaterialLinesBuilder()
	{
		final IHUContext huCtx = null; // task 07734: we don't want to track M_MaterialTrackings, so we don't need to provide a context.
		packingMaterialsCollector = new HUPackingMaterialsCollector(huCtx);
	}

	public void setM_InOut(@NonNull final I_M_InOut shipment)
	{
		Check.assume(shipment.isSOTrx(), "InOut shall be a shipment: {}", shipment);
		assertConfigurable();

		_shipment = shipment;
		// Only customer shipments are split; returns keep the project-agnostic grouping
		final boolean isCustomerShipment = X_M_InOut.MOVEMENTTYPE_CustomerShipment.equals(shipment.getMovementType());
		packingMaterialsCollector.setConsiderProject(
				isCustomerShipment
						&& sysConfigBL.getBooleanValue(SYSCONFIG_SplitShipmentPackingMaterialLinesByProject, false, shipment.getAD_Client_ID(), shipment.getAD_Org_ID()));
	}

	public I_M_InOut getM_InOut()
	{
		Check.assumeNotNull(_shipment, "shipment not null");
		return _shipment;
	}

	/**
	 * Sets if we want to delete existing packing materials lines and recreate them.
	 * <p>
	 * If this option is <code>false</code> and if there are any packing material lines already existing, this builder will not recreate them.
	 *
	 * @param overrideExistingPackingMaterialLines <code>true</code> if the packing material lines shall be recreated if they exists
	 */
	public void setOverrideExistingPackingMaterialLines(final boolean overrideExistingPackingMaterialLines)
	{
		assertConfigurable();
		_overrideExistingPackingMaterialLines = overrideExistingPackingMaterialLines;
	}

	private boolean isOverrideExistingPackingMaterialLines()
	{
		return _overrideExistingPackingMaterialLines;
	}

	/**
	 * @return true if override values shall be calculated and updated now, in this run
	 */
	private boolean isUpdateOverrideValues()
	{
		return _updateOverrideValues;
	}

	/**
	 * Sets if override values (e.g. {@link I_M_InOutLine#setQtyTU_Override(BigDecimal)}) shall be initialized now in this run.
	 * <p>
	 * Enable this functionality only when u are generating shipment lines and you want to have some initial values for the overrides.
	 *
	 * @param updateOverrideValues true if the override values shall be initialized and set now
	 */
	public void setUpdateOverrideValues(final boolean updateOverrideValues)
	{
		assertConfigurable();
		_updateOverrideValues = updateOverrideValues;
	}

	private void assertConfigurable()
	{
		Check.assume(configurable, "builder shall be in configurable state");
	}

	/**
	 * Collect packing materials and update QtyCU/QtyTU/etc on shipment lines.
	 */
	public void collectPackingMaterialsAndUpdateShipmentLines()
	{
		collectPackingMaterialsAndUpdateShipmentLines(retrieveShipmentLines());
	}

	private List<I_M_InOutLine> retrieveShipmentLines()
	{
		return inOutDAO.retrieveLines(getM_InOut(), I_M_InOutLine.class);
	}

	private void collectPackingMaterialsAndUpdateShipmentLines(@NonNull final List<I_M_InOutLine> inoutLines)
	{
		//
		// Iterate shipment lines and collect packing materials for LUs and TUs
		for (final I_M_InOutLine inoutLineHU : inoutLines)
		{
			final IHUPackingMaterialCollectorSource inOutLineSource = InOutLineHUPackingMaterialCollectorSource.of(inoutLineHU);
			collectHUs(inOutLineSource);
		}
	}

	private void logPackingUnitProjectConflicts(@NonNull final List<I_M_InOutLine> inoutLines)
	{
		if (!packingMaterialsCollector.isConsiderProject())
		{
			return;
		}

		final List<ShipmentPackingUnitProjectConflictDetector.Usage> usages = buildPackingUnitProjectUsages(inoutLines);
		ShipmentPackingUnitProjectConflictDetector.logWarnings(getM_InOut(), ShipmentPackingUnitProjectConflictDetector.detect(usages));
	}

	private List<ShipmentPackingUnitProjectConflictDetector.Usage> buildPackingUnitProjectUsages(@NonNull final List<I_M_InOutLine> inoutLines)
	{
		final List<ShipmentPackingUnitProjectConflictDetector.Usage> usages = new ArrayList<>();

		// Deliberately NOT filtered on IsTransferPackingMaterials: a TU shared by more than one line gets
		// IsTransferPackingMaterials='Y' on only its first assignment, so a filtered lookup would never see the other lines.
		final ImmutableMap<InOutLineId, I_M_InOutLine> inoutLinesById = Maps.uniqueIndex(inoutLines, inoutLine -> InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID()));
		final ImmutableListMultimap<InOutLineId, I_M_HU_Assignment> assignmentsByInOutLineId = Multimaps.index(
				huAssignmentBL.retrieveActiveHUAssignments(TableRecordReferenceSet.of(I_M_InOutLine.Table_Name, inoutLinesById.keySet())),
				assignment -> InOutLineId.ofRepoId(assignment.getRecord_ID()));

		// Default LU: one usage per manual-packing line; booked = the first manual line, which is where the pallet was added.
		final PackingUnit defaultLU = isDefaultLUWithPackingMaterial() ? PackingUnit.ofDefaultLUPackingInstructionsId(_defaultLUPIId) : null;

		final Map<HuId, InOutLineId> earliestInOutLineIdByLuHuId = new HashMap<>();
		final List<LuOccurrence> luOccurrences = new ArrayList<>();
		for (final Map.Entry<InOutLineId, I_M_InOutLine> inoutLineEntry : inoutLinesById.entrySet())
		{
			final InOutLineId inOutLineId = inoutLineEntry.getKey();
			final I_M_InOutLine inoutLine = inoutLineEntry.getValue();
			final ProjectId projectId = ProjectId.ofRepoIdOrNull(inoutLine.getC_Project_ID());

			if (isManualPackingMaterials(inoutLine))
			{
				// A manual-packing line books its packing from the overrides and the default LU; the packing of its HUs is not booked
				if (defaultLU != null)
				{
					usages.add(new ShipmentPackingUnitProjectConflictDetector.Usage(defaultLU, projectId, inOutLineId.equals(_defaultLUFirstLineId)));
				}
				continue;
			}

			for (final I_M_HU_Assignment assignment : assignmentsByInOutLineId.get(inOutLineId))
			{
				final HuId tuHuId = HuId.ofRepoIdOrNull(assignment.getM_TU_HU_ID());
				if (tuHuId != null)
				{
					// TU: booked = the row with IsTransferPackingMaterials='Y' (set only on the first assignment).
					usages.add(new ShipmentPackingUnitProjectConflictDetector.Usage(
							PackingUnit.ofHuId(tuHuId), projectId, assignment.isTransferPackingMaterials()));
				}
				final HuId luHuId = HuId.ofRepoIdOrNull(assignment.getM_LU_HU_ID());
				if (luHuId != null)
				{
					// LU: booked = the assignment with the earliest Record_ID (= earliest-created line) for that LU.
					// An approximation for the warning text only: the collector decides per (LU, VHU), see IHUAssignmentDAO.hasMoreLUAssigmentsForSameModelType
					earliestInOutLineIdByLuHuId.merge(luHuId, inOutLineId, (id1, id2) -> id1.getRepoId() <= id2.getRepoId() ? id1 : id2);
					luOccurrences.add(new LuOccurrence(luHuId, inOutLineId, projectId));
				}
			}
		}
		for (final LuOccurrence luOccurrence : luOccurrences)
		{
			final boolean isBooked = luOccurrence.getInOutLineId().equals(earliestInOutLineIdByLuHuId.get(luOccurrence.getLuHuId()));
			usages.add(new ShipmentPackingUnitProjectConflictDetector.Usage(
					PackingUnit.ofHuId(luOccurrence.getLuHuId()), luOccurrence.getProjectId(), isBooked));
		}

		return usages;
	}

	private boolean isDefaultLUWithPackingMaterial()
	{
		final BPartnerId bpartnerId = null;
		return _defaultLUPIId != null && !handlingUnitsDAO.retrievePackingMaterialIds(_defaultLUPIId, bpartnerId).isEmpty();
	}

	/**
	 * One line's occurrence of an LU {@code M_HU_Assignment} row; {@code booked} is decided once all lines are known.
	 */
	@Value
	private static class LuOccurrence
	{
		HuId luHuId;
		InOutLineId inOutLineId;
		@Nullable ProjectId projectId;
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

		final List<I_M_InOutLine> inoutLines = retrieveShipmentLines();
		collectPackingMaterialsAndUpdateShipmentLines(inoutLines);
		logPackingUnitProjectConflicts(inoutLines);

		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);

		//
		// Sets M_Product_ID sort comparator to use
		final Comparator<Integer> candidatesSortComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(inout.getC_BPartner_ID())
				.setC_DocType(load(inout.getC_DocType_ID(), I_C_DocType.class))
				.findProductIdsComparator();
		packingMaterialsCollector.setProductIdSortComparator(candidatesSortComparator);

		//
		// Create Packing Material InOut Lines based on collected packing materials
		for (final HUPackingMaterialDocumentLineCandidate pmCandidate : packingMaterialsCollector.getAndClearCandidates())
		{
			final I_M_InOutLine packingMaterialLine = createPackingMaterialLine(pmCandidate);
			// task 09502: set the reference from line to packing-line
			final Set<IHUPackingMaterialCollectorSource> sourceIols = pmCandidate.getSources();
			sourceIols.stream()
					.filter(source -> source instanceof InOutLineHUPackingMaterialCollectorSource)
					.map(source -> (InOutLineHUPackingMaterialCollectorSource)source)
					.forEach(inOutLineSource -> linkInOutLineToPackingMaterialLine(inOutLineSource, packingMaterialLine));
		}

		configurable = false;
	}

	private void linkInOutLineToPackingMaterialLine(
			@NonNull final InOutLineHUPackingMaterialCollectorSource source,
			@NonNull final I_M_InOutLine packingMaterialLine)
	{
		final I_M_InOutLine sourceIol = source.getM_InOutLine();
		if (sourceIol.getM_HU_PI_Item_Product_Override() == null)
		{
			return;
		}
		final I_M_HU_PI_Version huPiVersion = sourceIol
				.getM_HU_PI_Item_Product_Override()
				.getM_HU_PI_Item()
				.getM_HU_PI_Version();

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(sourceIol.getM_InOut().getC_BPartner_ID());
		final I_M_HU_PackingMaterial packingMaterial = Services.get(IHandlingUnitsDAO.class)
				.retrievePackingMaterial(huPiVersion, bpartnerId);
		if (packingMaterial != null && packingMaterial.getM_Product_ID() == packingMaterialLine.getM_Product_ID())
		{
			sourceIol.setM_PackingMaterial_InOutLine(packingMaterialLine);
			InterfaceWrapperHelper.save(sourceIol);
		}
	}

	/**
	 * Check if we are dealing with a shipment line where packing materials were specified by user
	 *
	 * @see HUConstants#isQuickShipment()
	 */
	private boolean isManualPackingMaterials(final I_M_InOutLine inoutLine)
	{
		return inoutLine.isManualPackingMaterial();
	}

	/**
	 * Collect TU packing materials from the given {@code huPackingMaterialCollectorSource} into our internal {@link HUPackingMaterialsCollector}.
	 */
	private void collectHUs(@NonNull final IHUPackingMaterialCollectorSource huPackingMaterialCollectorSource)
	{
		Check.assume(huPackingMaterialCollectorSource instanceof InOutLineHUPackingMaterialCollectorSource,
				"The given huPackingMaterialCollectorSource needs to be instanceof {}; huPackingMaterialCollectorSource={}",
				InOutLineHUPackingMaterialCollectorSource.class, huPackingMaterialCollectorSource);

		Check.assume(packingMaterialsCollector.getCountTUs() == 0,
				"At this point CountTUs shall be zero: packingMaterialsCollector={}", packingMaterialsCollector);

		final boolean initializeOverrides = isUpdateOverrideValues();

		// Collect packing materials from assigned TUs
		// NOTE: we are doing this because we also want to explicitly count them
		// If we would fetch all HUs it could be to count more TUs than there are actually assigned to this particular inout line
		final HUPackingMaterialsCollector packingMaterialsCollectorFromHUs = packingMaterialsCollector.splitNew();

		final InOutLineHUPackingMaterialCollectorSource shipmentLineSource = (InOutLineHUPackingMaterialCollectorSource)huPackingMaterialCollectorSource;
		final I_M_InOutLine shipmentLine = shipmentLineSource.getM_InOutLine();

		final IQueryBuilder<I_M_HU_Assignment> tuAssignmentsQuery = huAssignmentDAO.retrieveTUHUAssignmentsForModelQuery(shipmentLine);
		packingMaterialsCollectorFromHUs.releasePackingMaterialForTUHUsRecursively(tuAssignmentsQuery);
		// Must reset packingMaterialsCollectorFromHUs internal counter so mergeBackToParentAndClear() at line 323 doesn't contaminate the parent collector's TU count
		final int packingMaterial_countTUs_Calculated = packingMaterialsCollectorFromHUs.getAndResetCountTUs();

		// Instead of relying on the packingMaterialsCollectorFromHUs.getAndResetCountTUs(), we're re-counting the number of distinct TUs assigned to this line.
		// Why? Because a TU/LU will only be counted once for each Shipment creation run, and it will be counted on the first InOutLine encountered (the one where M_HU_Assignment.IsTransferPackingMaterials='Y')
		// That mechanism is designed to prevent double-counting of TUs/LUs when multiple InOutLines reference the same TU/LU.
		// But the customer's gripe is that the shipment document looks weird, as if no TU has been assigned to a shipment line. And that would happen if multiple lines are packed into the same TU.
		final BigDecimal display_countTUs_Calculated = BigDecimal.valueOf(huAssignmentBL.retrieveTUCountForModel(shipmentLine));

		//
		// Collect TU packing materials from overrides
		BigDecimal countTUs_Override = shipmentLine.getQtyTU_Override();
		// Case: we need to initialize the overrides, so we are using the countTUs_Calculated if it's a value set there.
		if (initializeOverrides && display_countTUs_Calculated.signum() > 0)
		{
			countTUs_Override = display_countTUs_Calculated;
		}
		final HUPackingMaterialsCollector packingMaterialsCollectorFromOverrides = packingMaterialsCollector.splitNew();
		final I_M_HU_PI_Item_Product huPIItemProduct = shipmentLine.getM_HU_PI_Item_Product_Override();
		if (huPIItemProduct != null)
		{
			final int packingMaterial_countTUs_Override = initializeOverrides && packingMaterial_countTUs_Calculated > 0 ? packingMaterial_countTUs_Calculated : shipmentLine.getQtyTU_Override().intValueExact();
			final I_M_HU_PI huPI = huPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
			packingMaterialsCollectorFromOverrides.addM_HU_PI(huPI, packingMaterial_countTUs_Override, shipmentLineSource);
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
				packingMaterialsCollector.addM_HU_PI(luPI, 1, shipmentLineSource);
				_defaultLUPIId = HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID());
				_defaultLUFirstLineId = InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID());
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
		shipmentLine.setQtyTU_Calculated(display_countTUs_Calculated);
		if (initializeOverrides)
		{
			shipmentLine.setQtyTU_Override(countTUs_Override);
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
	private void deleteExistingPackingMaterialLines()
	{
		final I_M_InOut inout = getM_InOut();
		final List<I_M_InOutLine> retrievePackingMaterialLines = huInOutDAO.retrievePackingMaterialLines(inout);
		for (final I_M_InOutLine packingLine : retrievePackingMaterialLines)
		{
			InterfaceWrapperHelper.delete(packingLine);
		}
	}
}
