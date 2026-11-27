package de.metas.shipping.api.impl;

import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A manual transport order is a transport order (never a delivery instruction) flagged {@code IsManualOrder}.
 */
class ShipperTransportationBLTest
{
	private ShipperTransportationBL shipperTransportationBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ShipperTransportationDocSubTypeGuard());
		shipperTransportationBL = new ShipperTransportationBL();
	}

	private I_M_ShipperTransportation shipperTransportation(final String docSubType, final boolean isManualOrder)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("docType-" + docSubType);
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(docSubType);
		InterfaceWrapperHelper.save(docType);

		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setC_DocType_ID(docType.getC_DocType_ID());
		record.setIsManualOrder(isManualOrder);
		return record;
	}

	@Test
	void transportOrder_withFlag_isManual()
	{
		assertThat(shipperTransportationBL.isManualTransportOrder(shipperTransportation(null, true))).isTrue();
	}

	@Test
	void transportOrder_withoutFlag_isNotManual()
	{
		assertThat(shipperTransportationBL.isManualTransportOrder(shipperTransportation(null, false))).isFalse();
	}

	@Test
	void deliveryInstruction_withFlag_isNotManual()
	{
		assertThat(shipperTransportationBL.isManualTransportOrder(shipperTransportation(DocSubType.DeliveryInstruction.getCode(), true))).isFalse();
	}
}
