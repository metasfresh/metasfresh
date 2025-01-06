/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.inoutcandidate.split;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ShipmentScheduleSplitServiceTest
{
	private ShipmentScheduleSplitService shipmentScheduleSplitService;
	private I_M_ShipmentSchedule shipmentSchedule;
	private I_C_BPartner bPartner;
	private I_C_BPartner_Location bPartnerLocation;
	private I_C_UOM uom;
	private I_M_Product product;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final ShipmentScheduleSplitRepository shipmentScheduleSplitRepository = new ShipmentScheduleSplitRepository();
		shipmentScheduleSplitService = new ShipmentScheduleSplitService(shipmentScheduleSplitRepository);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		bPartner = BusinessTestHelper.createBPartner("bpartner");
		bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);

		uom = BusinessTestHelper.createUOM("stockUOM");
		product = BusinessTestHelper.createProduct("product", uom);

		shipmentSchedule = createShipmentScheduleRecord();
	}

	@Test
	void testGetByShipmentScheduleId()
	{
		final I_M_ShipmentSchedule_Split completedSplit = createShipmentScheduleSplit(DocStatus.Completed);
		createShipmentScheduleSplit(DocStatus.Voided);

		final List<ShipmentScheduleSplit> result = shipmentScheduleSplitService.getByShipmentScheduleIdExcludingVoidedShipments(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()));

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId().getRepoId()).isEqualTo(completedSplit.getM_ShipmentSchedule_Split_ID());

	}

	private I_M_ShipmentSchedule createShipmentScheduleRecord()
	{
		final I_M_ShipmentSchedule record = newInstance(I_M_ShipmentSchedule.class);
		record.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		record.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		record.setQtyToDeliver(BigDecimal.TEN);
		InterfaceWrapperHelper.saveRecord(record);

		return record;
	}

	private InOutAndLineId createShipmentLine(final DocStatus docStatus, final BigDecimal movementQty)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setIsSOTrx(true);
		shipment.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		shipment.setMovementDate(SystemTime.asDayTimestamp());
		shipment.setDocStatus(docStatus.getCode());
		shipment.setProcessed(true);

		InterfaceWrapperHelper.saveRecord(shipment);

		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);

		line.setAD_Org_ID(shipment.getAD_Org_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setMovementQty(movementQty);
		line.setM_InOut_ID(shipment.getM_InOut_ID());
		InterfaceWrapperHelper.saveRecord(line);

		createShipmentScheduleQtyPickedRecord(shipmentSchedule, line.getM_InOutLine_ID());

		return InOutAndLineId.ofRepoId(shipment.getM_InOut_ID(), line.getM_InOutLine_ID());
	}

	private I_M_ShipmentSchedule_Split createShipmentScheduleSplit(final DocStatus docStatus)
	{
		final BigDecimal movementQty = BigDecimal.valueOf(10);
		final I_M_ShipmentSchedule_Split split = newInstance(I_M_ShipmentSchedule_Split.class);
		split.setM_ShipmentSchedule_ID(shipmentSchedule.getM_ShipmentSchedule_ID());
		split.setC_UOM_ID(uom.getC_UOM_ID());
		split.setQtyToDeliver(movementQty);
		split.setDeliveryDate(SystemTime.asDayTimestamp());
		split.setProcessed(false);
		final InOutAndLineId shipmentAndLineId = createShipmentLine(docStatus, movementQty);
		split.setM_InOut_ID(shipmentAndLineId.getInOutId().getRepoId());
		split.setM_InOutLine_ID(shipmentAndLineId.getInOutLineId().getRepoId());
		InterfaceWrapperHelper.saveRecord(split);

		return split;
	}

	private void createShipmentScheduleQtyPickedRecord(final I_M_ShipmentSchedule ss, final int inoutLineId)
	{
		final I_M_ShipmentSchedule_QtyPicked record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_ShipmentSchedule_ID(ss.getM_ShipmentSchedule_ID());
		record.setM_InOutLine_ID(inoutLineId);
		record.setProcessed(true);
		InterfaceWrapperHelper.saveRecord(record);
	}
}
