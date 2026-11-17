package de.metas.fresh.ordercheckup;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;
import de.metas.fresh.ordercheckup.impl.OrderCheckupBL;
import de.metas.organization.OrgId;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copies of an order checkup printing-queue item ({@link OrderCheckupBL#getNumberOfCopies}): the Bestellkontrolle Büro (Plant)
 * prints the order shipper's (Lieferweg) copy count, if it is greater than zero; everything else keeps its sysconfig.
 */
public class OrderCheckupCopiesTest
{
	private static final String SYSCONFIG_PLANT_COPIES = "de.metas.fresh.ordercheckup.Copies";
	private static final String SYSCONFIG_WAREHOUSE_COPIES = "de.metas.fresh.ordercheckup_barcode.Copies";

	private OrderCheckupBL orderCheckupBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		setSysConfig(SYSCONFIG_PLANT_COPIES, 1);
		setSysConfig(SYSCONFIG_WAREHOUSE_COPIES, 1);
		orderCheckupBL = new OrderCheckupBL();
	}

	@Test
	public void plant_shipperCopies2_prints2()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"))).isEqualTo(2);
	}

	@Test
	public void plant_shipperCopies3_prints3()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("3"))).isEqualTo(3);
	}

	@Test
	public void plant_shipperCopiesReplaceSysConfig()
	{
		setSysConfig(SYSCONFIG_PLANT_COPIES, 3);
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"))).isEqualTo(2);
	}

	/** 0 suppresses only the automatic print (report advisor); a manual print falls back to the sysconfig. */
	@Test
	public void plant_shipperCopies0_sysConfig()
	{
		setSysConfig(SYSCONFIG_PLANT_COPIES, 1);
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, BigDecimal.ZERO)).isEqualTo(1);
	}

	@Test
	public void plant_shipperCopiesNull_sysConfig()
	{
		setSysConfig(SYSCONFIG_PLANT_COPIES, 2);
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, null)).isEqualTo(2);
	}

	@Test
	public void plant_orderWithoutShipper_sysConfig()
	{
		setSysConfig(SYSCONFIG_PLANT_COPIES, 2);
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, false, null)).isEqualTo(2);
	}

	@Test
	public void warehouse_ignoresShipperCopies()
	{
		setSysConfig(SYSCONFIG_WAREHOUSE_COPIES, 1);
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse, true, new BigDecimal("2"))).isEqualTo(1);
	}

	private int copiesFor(final String documentType, final boolean withShipper, @Nullable final BigDecimal shipperCopies)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		if (withShipper)
		{
			final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
			shipper.setName("Lieferweg");
			shipper.setDocumentCopies(shipperCopies);
			save(shipper);
			order.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}
		save(order);

		final I_C_Order_MFGWarehouse_Report report = newInstance(I_C_Order_MFGWarehouse_Report.class);
		report.setC_Order_ID(order.getC_Order_ID());
		report.setDocumentType(documentType);
		save(report);

		final I_AD_Archive archive = newInstance(I_AD_Archive.class);
		archive.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Order_MFGWarehouse_Report.Table_Name));
		archive.setRecord_ID(report.getC_Order_MFGWarehouse_Report_ID());
		save(archive);

		final I_C_Printing_Queue queueItem = newInstance(I_C_Printing_Queue.class);
		save(queueItem);

		return orderCheckupBL.getNumberOfCopies(queueItem, archive);
	}

	private static void setSysConfig(final String name, final int value)
	{
		Services.get(ISysConfigBL.class).setValue(name, value, ClientId.SYSTEM, OrgId.ANY);
	}
}
