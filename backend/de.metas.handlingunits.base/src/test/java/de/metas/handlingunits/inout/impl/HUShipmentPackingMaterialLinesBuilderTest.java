package de.metas.handlingunits.inout.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_InOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The project-split conflict check of the shipment packing-line builder, per document type and call path.
 * The fixture: two manual-packing lines of different projects share the default LU, which is a conflict when the split is on.
 */
class HUShipmentPackingMaterialLinesBuilderTest
{
	private static final String SYSCONFIG_SplitShipmentPackingMaterialLinesByProject = "de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject";

	private static final ProjectId PROJECT_1 = ProjectId.ofRepoId(2000001);
	private static final ProjectId PROJECT_2 = ProjectId.ofRepoId(2000002);

	private LUTUProducerDestinationTestSupport data;
	private IHUInOutBL huInOutBL;
	private IHUInOutDAO huInOutDAO;
	private IInOutDAO inOutDAO;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private Logger logbackLogger;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		huInOutBL = Services.get(IHUInOutBL.class);
		huInOutDAO = Services.get(IHUInOutDAO.class);
		inOutDAO = Services.get(IInOutDAO.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		data.piLU.setIsDefaultLU(true);
		saveRecord(data.piLU);

		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_SplitShipmentPackingMaterialLinesByProject, true, ClientId.SYSTEM, OrgId.ANY);

		logbackLogger = (Logger)LoggerFactory.getLogger(ShipmentPackingUnitProjectConflictDetector.class);
		listAppender = new ListAppender<>();
		listAppender.start();
		logbackLogger.addAppender(listAppender);
	}

	@AfterEach
	void tearDown()
	{
		logbackLogger.detachAppender(listAppender);
	}

	@Test
	void collectOnly_noPackingLinesCreated_noConflictWarning()
	{
		final I_M_InOut shipment = createInOutWithTwoProjectLines(X_M_InOut.MOVEMENTTYPE_CustomerShipment);

		final HUShipmentPackingMaterialLinesBuilder builder = new HUShipmentPackingMaterialLinesBuilder();
		builder.setM_InOut(shipment);
		builder.collectPackingMaterialsAndUpdateShipmentLines();

		assertThat(listAppender.list).isEmpty();
	}

	@Test
	void createPackingMaterialLines_shipment_conflictWarningLoggedOnce()
	{
		final I_M_InOut shipment = createInOutWithTwoProjectLines(X_M_InOut.MOVEMENTTYPE_CustomerShipment);

		huInOutBL.createPackingMaterialLines(shipment);

		assertThat(huInOutDAO.retrievePackingMaterialLines(shipment))
				.extracting(line -> ProjectId.ofRepoIdOrNull(line.getC_Project_ID()))
				.containsExactly(PROJECT_1); // the one default LU is booked to the first line's project
		assertThat(listAppender.list).hasSize(1);
		assertThat(listAppender.list.get(0).getFormattedMessage()).contains("DefaultLU-PI:" + data.piLU.getM_HU_PI_ID());
	}

	@Test
	void createPackingMaterialLines_tuOfManualPackingLines_notReportedAsConflict()
	{
		final I_M_InOut shipment = createInOutWithTwoProjectLines(X_M_InOut.MOVEMENTTYPE_CustomerShipment);
		final I_M_HU tu = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("10"));
		boolean isFirstLine = true;
		for (final I_M_InOutLine line : inOutDAO.retrieveLines(shipment, I_M_InOutLine.class))
		{
			assignTU(line, tu, isFirstLine);
			isFirstLine = false;
		}

		huInOutBL.createPackingMaterialLines(shipment);

		// the manual-packing lines book the default LU only; their TU's packing material is not booked, so it cannot conflict
		assertThat(listAppender.list).hasSize(1);
		assertThat(listAppender.list.get(0).getFormattedMessage()).contains("DefaultLU-PI:" + data.piLU.getM_HU_PI_ID());
	}

	@Test
	void recreatePackingMaterialLines_customerReturn_noProjectConflictCheck()
	{
		final I_M_InOut customerReturn = createInOutWithTwoProjectLines(X_M_InOut.MOVEMENTTYPE_CustomerReturns);

		huInOutBL.recreatePackingMaterialLines(customerReturn);

		// the conflict check only runs with the split on, so an empty log proves the split is off for returns
		assertThat(listAppender.list).isEmpty();
	}

	@Test
	void recreatePackingMaterialLines_vendorReturn_rejected()
	{
		final I_M_InOut vendorReturn = createInOutWithTwoProjectLines(X_M_InOut.MOVEMENTTYPE_VendorReturns);
		vendorReturn.setIsSOTrx(false); // as a real vendor return
		saveRecord(vendorReturn);

		// the packing lines of a purchase-side document are never built here, so nothing can be split per project
		assertThatThrownBy(() -> huInOutBL.recreatePackingMaterialLines(vendorReturn))
				.hasMessageContaining("shall be a shipment");
		assertThat(listAppender.list).isEmpty();
	}

	private I_M_InOut createInOutWithTwoProjectLines(@NonNull final String movementType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		saveRecord(docType);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(true);
		inout.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		inout.setMovementType(movementType);
		inout.setC_DocType_ID(docType.getC_DocType_ID());
		inout.setDocumentNo("INOUT-1");
		saveRecord(inout);

		createManualPackingLine(inout, PROJECT_1);
		createManualPackingLine(inout, PROJECT_2);
		return inout;
	}

	private static void assignTU(@NonNull final I_M_InOutLine line, @NonNull final I_M_HU tu, final boolean isTransferPackingMaterials)
	{
		final I_M_HU_Assignment assignment = newInstance(I_M_HU_Assignment.class);
		assignment.setAD_Table_ID(getTableId(I_M_InOutLine.class));
		assignment.setRecord_ID(line.getM_InOutLine_ID());
		assignment.setM_HU_ID(tu.getM_HU_ID());
		assignment.setM_TU_HU_ID(tu.getM_HU_ID());
		assignment.setIsTransferPackingMaterials(isTransferPackingMaterials);
		saveRecord(assignment);
	}

	private void createManualPackingLine(@NonNull final I_M_InOut inout, @NonNull final ProjectId projectId)
	{
		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);
		line.setM_InOut_ID(inout.getM_InOut_ID());
		line.setM_Product_ID(data.helper.pTomatoProductId.getRepoId());
		line.setIsManualPackingMaterial(true);
		line.setC_Project_ID(projectId.getRepoId());
		saveRecord(line);
	}
}
