package de.metas.handlingunits.materialtracking.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUAssignmentDAO;
import de.metas.handlingunits.impl.HandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.util.Services;

@ExtendWith(AdempiereTestWatcher.class)
public class HUHandlingUnitsInfoFactoryTest
{
	private HUHandlingUnitsInfoFactory factory;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IHUAssignmentDAO.class, new HUAssignmentDAO());
		Services.registerService(IHandlingUnitsBL.class, new HandlingUnitsBL());

		factory = new HUHandlingUnitsInfoFactory();
	}

	@Test
	public void createFromModel_InOutLine_resolvesFromHUAssignment()
	{
		// given
		final I_M_HU_PI_Version tuPIV = createHU_PIVersion("IFCO");
		final I_M_HU tuHU = createHU(tuPIV);

		final I_M_InOutLine inoutLine = createInOutLine(3);
		createHUAssignment(inoutLine, tuHU, tuHU);

		// when
		final IHandlingUnitsInfo result = factory.createFromModel(inoutLine);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getQtyTU()).isEqualTo(3);
		assertThat(result.getTUName()).isEqualTo("IFCO");
	}

	@Test
	public void createFromModel_InOutLine_resolvesFromHUAssignment_LUWithTU()
	{
		// given: LU as top-level HU, TU referenced via M_TU_HU_ID
		final I_M_HU_PI_Version luPIV = createHU_PIVersion("Euro Pallet");
		final I_M_HU luHU = createHU(luPIV);

		final I_M_HU_PI_Version tuPIV = createHU_PIVersion("IFCO");
		final I_M_HU tuHU = createHU(tuPIV);

		final I_M_InOutLine inoutLine = createInOutLine(5);
		createHUAssignment_LU_TU(inoutLine, luHU, tuHU);

		// when
		final IHandlingUnitsInfo result = factory.createFromModel(inoutLine);

		// then: should use the TU PI, not the LU PI
		assertThat(result).isNotNull();
		assertThat(result.getQtyTU()).isEqualTo(5);
		assertThat(result.getTUName()).isEqualTo("IFCO");
	}

	@Test
	public void createFromModel_InOutLine_skipsVirtualHUs()
	{
		// given: one virtual HU assignment and one real TU assignment
		final I_M_HU virtualHU = createVirtualHU();
		final I_M_HU_PI_Version tuPIV = createHU_PIVersion("Karton");
		final I_M_HU tuHU = createHU(tuPIV);

		final I_M_InOutLine inoutLine = createInOutLine(2);
		createHUAssignment(inoutLine, virtualHU, virtualHU);
		createHUAssignment(inoutLine, tuHU, tuHU);

		// when
		final IHandlingUnitsInfo result = factory.createFromModel(inoutLine);

		// then: should skip the virtual HU and use the real one
		assertThat(result).isNotNull();
		assertThat(result.getTUName()).isEqualTo("Karton");
	}

	@Test
	public void createFromModel_InOutLine_onlyVirtualHUs_throws()
	{
		// given: only virtual HU assignments, no M_HU_PI_Item_Product fallback
		final I_M_HU virtualHU = createVirtualHU();

		final I_M_InOutLine inoutLine = createInOutLine(1);
		createHUAssignment(inoutLine, virtualHU, virtualHU);

		// when/then
		assertThatThrownBy(() -> factory.createFromModel(inoutLine))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TU Packing Instructions could not be determined");
	}

	@Test
	public void createFromModel_InOutLine_noHUAssignments_noFallback_throws()
	{
		// given: no HU assignments and no M_HU_PI_Item_Product
		final I_M_InOutLine inoutLine = createInOutLine(1);

		// when/then
		assertThatThrownBy(() -> factory.createFromModel(inoutLine))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("TU Packing Instructions could not be determined");
	}

	@Test
	public void createFromModel_InOutLine_multipleDistinctPIs_usesFirst()
	{
		// given: two HU assignments with different TU PIs
		final I_M_HU_PI_Version pivA = createHU_PIVersion("IFCO");
		final I_M_HU tuA = createHU(pivA);

		final I_M_HU_PI_Version pivB = createHU_PIVersion("Karton");
		final I_M_HU tuB = createHU(pivB);

		final I_M_InOutLine inoutLine = createInOutLine(4);
		createHUAssignment(inoutLine, tuA, tuA);
		createHUAssignment(inoutLine, tuB, tuB);

		// when
		final IHandlingUnitsInfo result = factory.createFromModel(inoutLine);

		// then: should succeed (with warning logged), using the first PI
		assertThat(result).isNotNull();
		assertThat(result.getQtyTU()).isEqualTo(4);
	}

	@Test
	public void createFromModel_InOutLine_topLevelOnlyAssignment_fallsBackToMHU()
	{
		// given: only a top-level assignment (no derived assignment with M_TU_HU_ID)
		// This exercises Pass 2 (fallback to M_HU) since Pass 1 finds nothing.
		final I_M_HU_PI_Version tuPIV = createHU_PIVersion("Paloxe");
		final I_M_HU tuHU = createHU(tuPIV);

		final I_M_InOutLine inoutLine = createInOutLine(1);

		// create ONLY a top-level assignment (M_TU_HU left null)
		final I_M_HU_Assignment topLevelAssignment = newInstance(I_M_HU_Assignment.class);
		topLevelAssignment.setAD_Table_ID(getTableId(org.compiere.model.I_M_InOutLine.class));
		topLevelAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		topLevelAssignment.setM_HU(tuHU);
		saveRecord(topLevelAssignment);

		// when
		final IHandlingUnitsInfo result = factory.createFromModel(inoutLine);

		// then: Pass 1 finds nothing (no M_TU_HU_ID), Pass 2 picks up top-level M_HU
		assertThat(result).isNotNull();
		assertThat(result.getTUName()).isEqualTo("Paloxe");
	}

	// --- helpers ---

	private I_M_InOutLine createInOutLine(final int qtyEnteredTU)
	{
		final org.compiere.model.I_M_InOut inout = newInstance(org.compiere.model.I_M_InOut.class);
		inout.setDocumentNo("TEST-001");
		inout.setMovementType("V+"); // vendor receipt
		saveRecord(inout);

		final org.compiere.model.I_M_InOutLine baseLine = newInstance(org.compiere.model.I_M_InOutLine.class);
		baseLine.setM_InOut_ID(inout.getM_InOut_ID());
		baseLine.setLine(10);
		saveRecord(baseLine);

		// wrap to HU-extended interface to set QtyEnteredTU
		final I_M_InOutLine line = org.adempiere.model.InterfaceWrapperHelper.create(baseLine, I_M_InOutLine.class);
		line.setQtyEnteredTU(BigDecimal.valueOf(qtyEnteredTU));
		saveRecord(line);
		return line;
	}

	private I_M_HU_PI_Version createHU_PIVersion(final String name)
	{
		final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
		pi.setName(name);
		saveRecord(pi);

		final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setIsCurrent(true);
		piVersion.setName(name);
		piVersion.setHU_UnitType("TU");
		saveRecord(piVersion);

		return piVersion;
	}

	private I_M_HU createHU(final I_M_HU_PI_Version piVersion)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(piVersion.getM_HU_PI_Version_ID());
		saveRecord(hu);
		return hu;
	}

	private I_M_HU createVirtualHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		saveRecord(hu);
		return hu;
	}

	/**
	 * Creates HU assignments like production code does for receipts:
	 * 1. A top-level assignment (M_HU only, M_TU_HU/M_LU_HU = null) — created by assignHU/transferHandlingUnit
	 * 2. A derived assignment with M_TU_HU set — created by createTradingUnitDerivedAssignmentBuilder
	 *
	 * @see de.metas.inoutcandidate.spi.impl.InOutProducerFromReceiptScheduleHU
	 */
	private void createHUAssignment(final I_M_InOutLine inoutLine, final I_M_HU topLevelHU, final I_M_HU tuHU)
	{
		// top-level assignment (like assignHU creates)
		final I_M_HU_Assignment topLevelAssignment = newInstance(I_M_HU_Assignment.class);
		topLevelAssignment.setAD_Table_ID(getTableId(org.compiere.model.I_M_InOutLine.class));
		topLevelAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		topLevelAssignment.setM_HU(topLevelHU);
		// M_TU_HU and M_LU_HU intentionally left null
		saveRecord(topLevelAssignment);

		// derived assignment with TU info (like createTradingUnitDerivedAssignmentBuilder creates)
		final I_M_HU_Assignment derivedAssignment = newInstance(I_M_HU_Assignment.class);
		derivedAssignment.setAD_Table_ID(getTableId(org.compiere.model.I_M_InOutLine.class));
		derivedAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		derivedAssignment.setM_HU(topLevelHU);
		derivedAssignment.setM_TU_HU(tuHU);
		saveRecord(derivedAssignment);
	}

	private void createHUAssignment_LU_TU(final I_M_InOutLine inoutLine, final I_M_HU luHU, final I_M_HU tuHU)
	{
		// top-level assignment
		final I_M_HU_Assignment topLevelAssignment = newInstance(I_M_HU_Assignment.class);
		topLevelAssignment.setAD_Table_ID(getTableId(org.compiere.model.I_M_InOutLine.class));
		topLevelAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		topLevelAssignment.setM_HU(luHU);
		saveRecord(topLevelAssignment);

		// derived assignment with LU + TU
		final I_M_HU_Assignment derivedAssignment = newInstance(I_M_HU_Assignment.class);
		derivedAssignment.setAD_Table_ID(getTableId(org.compiere.model.I_M_InOutLine.class));
		derivedAssignment.setRecord_ID(inoutLine.getM_InOutLine_ID());
		derivedAssignment.setM_HU(luHU);
		derivedAssignment.setM_LU_HU(luHU);
		derivedAssignment.setM_TU_HU(tuHU);
		saveRecord(derivedAssignment);
	}
}
