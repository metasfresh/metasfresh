package de.metas.handlingunits.shipmentschedule.api;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsRepository;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.impl.ShipperTransportationRepository;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentScheduleWithHUServiceTest
{
	private ShipmentScheduleId shipmentScheduleId;
	private I_C_BPartner bPartner;
	private I_C_BPartner_Location bPartnerLocation;
	private I_C_UOM uom;
	private I_M_Product product;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ShipperTransportationRepository());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsRepository());
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		bPartner = BusinessTestHelper.createBPartner("bpartner");
		bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);

		uom = BusinessTestHelper.createUOM("stockUOM");
		product = BusinessTestHelper.createProduct("product", uom);

		shipmentScheduleId = createShipmentScheduleRecord();
	}

	@Test
	void getEligibleSplits()
	{
		final I_M_ShipmentSchedule_Split notProcessedSplit = createShipmentScheduleSplit(DocStatus.Completed, false);
		createShipmentScheduleSplit(DocStatus.Completed, true);
		createShipmentScheduleSplit(DocStatus.Voided, false);
		createShipmentScheduleSplit(DocStatus.Reversed, false);
		createShipmentScheduleSplit(DocStatus.Closed, false);

		final ShipmentScheduleWithHUService shipmentScheduleWithHUService = ShipmentScheduleWithHUService.newInstanceForUnitTesting();
		final List<ShipmentScheduleSplit> result = shipmentScheduleWithHUService.getEligibleSplits(shipmentScheduleId);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId().getRepoId()).isEqualTo(notProcessedSplit.getM_ShipmentSchedule_Split_ID());

	}

	private ShipmentScheduleId createShipmentScheduleRecord()
	{
		final I_M_ShipmentSchedule record = newInstance(I_M_ShipmentSchedule.class);
		record.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		record.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		record.setQtyToDeliver(BigDecimal.TEN);
		InterfaceWrapperHelper.saveRecord(record);

		return ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());
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

		createShipmentScheduleQtyPickedRecord(line.getM_InOutLine_ID());

		return InOutAndLineId.ofRepoId(shipment.getM_InOut_ID(), line.getM_InOutLine_ID());
	}

	private I_M_ShipmentSchedule_Split createShipmentScheduleSplit(final DocStatus docStatus, final boolean processed)
	{
		final BigDecimal movementQty = BigDecimal.valueOf(10);
		final I_M_ShipmentSchedule_Split split = newInstance(I_M_ShipmentSchedule_Split.class);
		split.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		split.setC_UOM_ID(uom.getC_UOM_ID());
		split.setQtyToDeliver(movementQty);
		split.setDeliveryDate(SystemTime.asDayTimestamp());
		split.setProcessed(processed);
		final InOutAndLineId shipmentAndLineId = createShipmentLine(docStatus, movementQty);
		split.setM_InOut_ID(shipmentAndLineId.getInOutId().getRepoId());
		split.setM_InOutLine_ID(shipmentAndLineId.getInOutLineId().getRepoId());
		InterfaceWrapperHelper.saveRecord(split);

		return split;
	}

	private void createShipmentScheduleQtyPickedRecord(final int inoutLineId)
	{
		final I_M_ShipmentSchedule_QtyPicked record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
		record.setM_InOutLine_ID(inoutLineId);
		record.setProcessed(true);
		InterfaceWrapperHelper.saveRecord(record);
	}
}