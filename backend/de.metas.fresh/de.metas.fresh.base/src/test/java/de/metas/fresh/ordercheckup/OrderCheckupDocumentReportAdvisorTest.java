package de.metas.fresh.ordercheckup;

import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.FallbackDocumentReportAdvisor;
import de.metas.report.PrintCopies;
import de.metas.report.PrintFormatId;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The Bestellkontrolle report advisor answers like the generic fallback, except for a Bestellkontrolle Büro (Plant): its archive copies
 * are always 1 (the print handler sets the delivery-note copies, the printing queue's archive multiplier must not turn them into k x N),
 * and a shipper (Lieferweg) count of 0 suppresses the automatic print.
 */
public class OrderCheckupDocumentReportAdvisorTest
{
	private DocumentReportAdvisorUtil util;
	private I_C_BPartner bpartner;
	private I_C_DocType docType;
	private PrintFormatId printFormatId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		util = new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(),
				new BPartnerPrintFormatRepository());
		SpringContextHolder.registerJUnitBean(util);

		bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("BKB");
		docType.setDocumentCopies(1);
		save(docType);

		final I_AD_PrintFormat printFormat = newInstance(I_AD_PrintFormat.class);
		printFormat.setJasperProcess_ID(540001);
		save(printFormat);
		printFormatId = PrintFormatId.ofRepoId(printFormat.getAD_PrintFormat_ID());
	}

	@Test
	public void plant_shipperCopies0_suppressesAutoPrint_otherwiseAsFallback()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, BigDecimal.ZERO);

		final DocumentReportInfo info = advisor().getDocumentReportInfo(report, printFormatId, null);

		assertThat(info.isSuppressAutoPrint()).isTrue();
		assertThat(info).isEqualTo(fallback(report).toBuilder().copies(PrintCopies.ONE).suppressAutoPrint(true).build());
	}

	@Test
	public void plant_shipperCopies2_archiveCopies1_notSuppressed()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"));

		final DocumentReportInfo info = advisor().getDocumentReportInfo(report, printFormatId, null);

		assertThat(info.isSuppressAutoPrint()).isFalse();
		assertThat(info.getCopies()).isEqualTo(PrintCopies.ONE);
		assertThat(info).isEqualTo(fallback(report).toBuilder().copies(PrintCopies.ONE).build());
	}

	/** A partner print-format count must not multiply the shipper's N (would print 2 x N). */
	@Test
	public void plant_shipperCopies2_partnerPrintFormat2_archiveCopies1()
	{
		final I_C_BP_PrintFormat bpPrintFormat = newInstance(I_C_BP_PrintFormat.class);
		bpPrintFormat.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpPrintFormat.setDocumentCopies_Override(2);
		save(bpPrintFormat);

		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, new BigDecimal("2"));

		assertThat(fallback(report).getCopies()).as("precondition: the generic advisor applies the partner count").isEqualTo(PrintCopies.ofInt(2));
		assertThat(advisor().getDocumentReportInfo(report, printFormatId, null).getCopies()).isEqualTo(PrintCopies.ONE);
	}

	/** No shipper count: the copies come from the shipment rule in the print handler; the archive copies stay 1, nothing suppressed. */
	@Test
	public void plant_shipperCopiesNull_archiveCopies1_notSuppressed()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, true, null);
		assertThat(advisor().getDocumentReportInfo(report, printFormatId, null)).isEqualTo(fallback(report).toBuilder().copies(PrintCopies.ONE).build());
	}

	@Test
	public void plant_orderWithoutShipper_archiveCopies1_notSuppressed()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, false, null);
		assertThat(advisor().getDocumentReportInfo(report, printFormatId, null)).isEqualTo(fallback(report).toBuilder().copies(PrintCopies.ONE).build());
	}

	@Test
	public void warehouse_shipperCopies0_asFallback()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse, true, BigDecimal.ZERO);
		assertThat(advisor().getDocumentReportInfo(report, printFormatId, null)).isEqualTo(fallback(report));
	}

	@Test
	public void warehouse_shipperCopies2_asFallback()
	{
		final TableRecordReference report = createReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse, true, new BigDecimal("2"));
		assertThat(advisor().getDocumentReportInfo(report, printFormatId, null)).isEqualTo(fallback(report));
	}

	@Test
	public void handlesOrderCheckupTable_withoutStandardReportType()
	{
		assertThat(advisor().getHandledTableName()).isEqualTo(I_C_Order_MFGWarehouse_Report.Table_Name);
		assertThat(advisor().getStandardDocumentReportType()).isNull();
	}

	private OrderCheckupDocumentReportAdvisor advisor()
	{
		return new OrderCheckupDocumentReportAdvisor(util);
	}

	private DocumentReportInfo fallback(final TableRecordReference report)
	{
		return new FallbackDocumentReportAdvisor(util).getDocumentReportInfo(report, printFormatId, null);
	}

	private TableRecordReference createReport(final String documentType, final boolean withShipper, @Nullable final BigDecimal shipperCopies)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
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
		report.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		report.setC_DocType_ID(docType.getC_DocType_ID());
		report.setDocumentType(documentType);
		save(report);

		return TableRecordReference.of(getTableId(I_C_Order_MFGWarehouse_Report.class), report.getC_Order_MFGWarehouse_Report_ID());
	}
}
