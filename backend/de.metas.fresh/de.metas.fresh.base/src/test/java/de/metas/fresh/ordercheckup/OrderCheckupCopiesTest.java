package de.metas.fresh.ordercheckup;

import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;
import de.metas.fresh.ordercheckup.impl.OrderCheckupBL;
import de.metas.organization.OrgId;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copies of an order checkup printing-queue item ({@link OrderCheckupBL#getNumberOfCopies}): the Plant order checkup (PL)
 * prints the order shipper's (Lieferweg) count N &gt; 0; without a shipper count (or with 0) the sysconfig, as before.
 * The Warehouse order checkup (WH) keeps its sysconfig.
 */
public class OrderCheckupCopiesTest
{
	private static final String SYSCONFIG_PLANT_COPIES = "de.metas.fresh.ordercheckup.Copies";
	private static final String SYSCONFIG_WAREHOUSE_COPIES = "de.metas.fresh.ordercheckup_barcode.Copies";

	private OrderCheckupBL orderCheckupBL;
	private I_C_DocType shipmentDocType;
	private I_C_DocType orderDocType;
	private boolean purchaseOrder = false;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);
		SpringContextHolder.registerJUnitBean(new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(),
				new BPartnerPrintFormatRepository()));

		setSysConfig(SYSCONFIG_PLANT_COPIES, 5); // distinct from every shipper / print-format value used here
		setSysConfig(SYSCONFIG_WAREHOUSE_COPIES, 1);

		shipmentDocType = newInstance(I_C_DocType.class);
		shipmentDocType.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialDelivery);
		shipmentDocType.setDocumentCopies(1);
		save(shipmentDocType);

		orderDocType = newInstance(I_C_DocType.class);
		orderDocType.setDocBaseType(X_C_DocType.DOCBASETYPE_SalesOrder);
		orderDocType.setC_DocTypeShipment_ID(shipmentDocType.getC_DocType_ID());
		save(orderDocType);

		orderCheckupBL = new OrderCheckupBL();
	}

	@Test
	public void plant_shipperCopies2_prints2_notSysConfig()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"), null)).isEqualTo(2);
	}

	@Test
	public void plant_shipperCopies2_ignoresAddressPrintFormat3()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"), 3)).isEqualTo(2);
	}

	/** 0 suppresses only the automatic print (report advisor); a manual print gets the sysconfig. */
	@Test
	public void plant_shipperCopies0_manualPrintGetsSysConfig()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, BigDecimal.ZERO, 3)).isEqualTo(5);
	}

	/** Shipper count empty (NULL, the default): the sysconfig as before - the delivery note's print format is not used. */
	@Test
	public void plant_shipperCopiesNull_sysConfig()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, null, 3)).isEqualTo(5);
	}

	@Test
	public void plant_orderWithoutShipper_sysConfig()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, false, null, 2)).isEqualTo(5);
	}

	/** The shipper count applies to customer (sales) documents only, as for the delivery note. */
	@Test
	public void plant_purchaseOrder_sysConfig()
	{
		purchaseOrder = true;
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"), null)).isEqualTo(5);
	}

	@Test
	public void warehouse_keepsSysConfig()
	{
		assertThat(copiesFor(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse, true, new BigDecimal("2"), 3)).isEqualTo(1);
	}

	private int copiesFor(
			final String documentType,
			final boolean withShipper,
			@Nullable final BigDecimal shipperCopies,
			@Nullable final Integer addressPrintFormatCopies)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);
		final I_C_BPartner_Location location = newInstance(I_C_BPartner_Location.class);
		location.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(location);

		if (addressPrintFormatCopies != null)
		{
			final I_C_BP_PrintFormat bpPrintFormat = newInstance(I_C_BP_PrintFormat.class);
			bpPrintFormat.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			bpPrintFormat.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
			bpPrintFormat.setC_DocType_ID(shipmentDocType.getC_DocType_ID());
			bpPrintFormat.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name));
			bpPrintFormat.setDocumentCopies_Override(addressPrintFormatCopies);
			save(bpPrintFormat);
		}

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(!purchaseOrder);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		order.setC_DocType_ID(orderDocType.getC_DocType_ID());
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
