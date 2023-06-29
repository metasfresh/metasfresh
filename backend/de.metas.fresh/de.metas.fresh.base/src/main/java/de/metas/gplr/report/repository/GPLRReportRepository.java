package de.metas.gplr.report.repository;

import com.google.common.collect.ImmutableList;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.gplr.report.model.GPLRReportCharge;
import de.metas.gplr.report.model.GPLRReportId;
import de.metas.gplr.report.model.GPLRReportLineItem;
import de.metas.gplr.report.model.GPLRReportNote;
import de.metas.gplr.report.model.GPLRReportPurchaseOrder;
import de.metas.gplr.report.model.GPLRReportShipment;
import de.metas.invoice.InvoiceId;
import de.metas.location.ICountryCodeFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GPLR_Report;
import org.compiere.model.I_GPLR_Report_Charge;
import org.compiere.model.I_GPLR_Report_Line;
import org.compiere.model.I_GPLR_Report_Note;
import org.compiere.model.I_GPLR_Report_PurchaseOrder;
import org.compiere.model.I_GPLR_Report_SalesOrder;
import org.compiere.model.I_GPLR_Report_Shipment;
import org.compiere.model.I_GPLR_Report_Summary;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.function.Function;

@Repository
public class GPLRReportRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICountryCodeFactory countryCodeFactory = Services.get(ICountryCodeFactory.class);
	private final Function<OrgId, ZoneId> orgId2timeZoneMapper = orgDAO::getTimeZone;

	public void createNew(@NonNull GPLRReport report)
	{
		Check.assumeNull(report.getId(), "Report shall not be already saved: {}", report);

		final OrgId orgId = report.getSourceDocument().getOrgId();

		final GPLRReportId gplrReportId;
		{
			final I_GPLR_Report record = InterfaceWrapperHelper.newInstance(I_GPLR_Report.class);
			GPLRReportSourceDocument_Mapper.updateRecord(record, report.getSourceDocument(), orgId2timeZoneMapper);
			InterfaceWrapperHelper.save(record);
			gplrReportId = GPLRReportId.ofRepoId(record.getGPLR_Report_ID());

			report.setId(gplrReportId);
			report.setCreated(record.getCreated().toInstant());
		}

		//
		// Sales order
		{
			final I_GPLR_Report_SalesOrder record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_SalesOrder.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportSalesOrder_Mapper.updateRecord(record, report.getSalesOrder(), orgId);
			InterfaceWrapperHelper.save(record);
		}

		//
		// Shipments
		for (final GPLRReportShipment shipment : report.getShipments())
		{
			final I_GPLR_Report_Shipment record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Shipment.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportShipment_Mapper.updateRecord(record, shipment, orgId, orgId2timeZoneMapper);
			InterfaceWrapperHelper.save(record);
		}

		//
		// Purchase Order
		for (final GPLRReportPurchaseOrder purchaseOrder : report.getPurchaseOrders())
		{
			final I_GPLR_Report_PurchaseOrder record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_PurchaseOrder.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportPurchaseOrder_Mapper.updateRecord(record, purchaseOrder, orgId);
			InterfaceWrapperHelper.save(record);
		}

		//
		// Summary
		{
			final I_GPLR_Report_Summary record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Summary.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportSummary_Mapper.updateRecord(record, report.getSummary(), orgId);
			InterfaceWrapperHelper.save(record);
		}

		//
		// Report Line Items
		final SeqNoProvider lineNextSeqNo = SeqNoProvider.ofInt(10);
		for (final GPLRReportLineItem line : report.getLineItems())
		{
			final I_GPLR_Report_Line record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Line.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportLineItem_Mapper.updateRecord(record, line, orgId, lineNextSeqNo.getAndIncrement().toInt());
			InterfaceWrapperHelper.save(record);
		}

		//
		// Charges
		for (final GPLRReportCharge charge : report.getCharges())
		{
			final I_GPLR_Report_Charge record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Charge.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportCharge_Mapper.updateRecord(record, charge, orgId);
			InterfaceWrapperHelper.save(record);
		}

		//
		// Notes
		for (final GPLRReportNote note : report.getOtherNotes())
		{
			final I_GPLR_Report_Note record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Note.class);
			record.setGPLR_Report_ID(gplrReportId.getRepoId());
			GPLRReportNote_Mapper.updateRecord(record, note, orgId);
			InterfaceWrapperHelper.save(record);
		}
	}

	public GPLRReport getById(@NonNull final GPLRReportId id)
	{
		GPLRReport.GPLRReportBuilder result = GPLRReport.builder();

		{
			final I_GPLR_Report record = InterfaceWrapperHelper.load(id, I_GPLR_Report.class);
			result.id(id);
			result.created(record.getCreated().toInstant());
			result.sourceDocument(GPLRReportSourceDocument_Mapper.fromRecord(record, orgId2timeZoneMapper));
		}

		//
		// Sales order
		{
			@NonNull final I_GPLR_Report_SalesOrder record = queryBL.createQueryBuilder(I_GPLR_Report_SalesOrder.class)
					.addEqualsFilter(I_GPLR_Report_SalesOrder.COLUMNNAME_GPLR_Report_ID, id)
					.create()
					.firstOnlyNotNull(I_GPLR_Report_SalesOrder.class);
			result.salesOrder(GPLRReportSalesOrder_Mapper.fromRecord(record));
		}

		//
		// Shipments
		result.shipments(
				queryBL.createQueryBuilder(I_GPLR_Report_Shipment.class)
						.addEqualsFilter(I_GPLR_Report_Shipment.COLUMNNAME_GPLR_Report_ID, id)
						.orderBy(I_GPLR_Report_Shipment.COLUMNNAME_GPLR_Report_Shipment_ID)
						.stream()
						.map(record -> GPLRReportShipment_Mapper.fromRecord(record, orgId2timeZoneMapper, countryCodeFactory))
						.collect(ImmutableList.toImmutableList())
		);

		//
		// Purchase Orders
		result.purchaseOrders(
				queryBL.createQueryBuilder(I_GPLR_Report_PurchaseOrder.class)
						.addEqualsFilter(I_GPLR_Report_PurchaseOrder.COLUMNNAME_GPLR_Report_ID, id)
						.orderBy(I_GPLR_Report_PurchaseOrder.COLUMNNAME_GPLR_Report_PurchaseOrder_ID)
						.stream()
						.map(GPLRReportPurchaseOrder_Mapper::fromRecord)
						.collect(ImmutableList.toImmutableList())
		);

		//
		// Summary
		{
			@NonNull final I_GPLR_Report_Summary record = queryBL.createQueryBuilder(I_GPLR_Report_Summary.class)
					.addEqualsFilter(I_GPLR_Report_Summary.COLUMNNAME_GPLR_Report_ID, id)
					.create()
					.firstOnlyNotNull(I_GPLR_Report_Summary.class);
			result.summary(GPLRReportSummary_Mapper.fromRecord(record));
		}

		//
		// Report Line Items
		result.lineItems(
				queryBL.createQueryBuilder(I_GPLR_Report_Line.class)
						.addEqualsFilter(I_GPLR_Report_Line.COLUMNNAME_GPLR_Report_ID, id)
						.orderBy(I_GPLR_Report_Line.COLUMNNAME_SeqNo)
						.stream()
						.map(GPLRReportLineItem_Mapper::fromRecord)
						.collect(ImmutableList.toImmutableList())
		);

		//
		// Charges
		result.charges(
				queryBL.createQueryBuilder(I_GPLR_Report_Charge.class)
						.addEqualsFilter(I_GPLR_Report_Charge.COLUMNNAME_GPLR_Report_ID, id)
						.orderBy(I_GPLR_Report_Charge.COLUMNNAME_GPLR_Report_Charge_ID)
						.stream()
						.map(GPLRReportCharge_Mapper::fromRecord)
						.collect(ImmutableList.toImmutableList())
		);

		//
		// Notes
		result.otherNotes(
				queryBL.createQueryBuilder(I_GPLR_Report_Note.class)
						.addEqualsFilter(I_GPLR_Report_Note.COLUMNNAME_GPLR_Report_ID, id)
						.orderBy(I_GPLR_Report_Note.COLUMNNAME_GPLR_Report_Note_ID)
						.stream()
						.map(GPLRReportNote_Mapper::fromRecord)
						.collect(ImmutableList.toImmutableList())
		);

		//
		//
		return result.build();
	}

	public boolean isReportGeneratedForInvoice(@NonNull final InvoiceId invoiceId)
	{
		final IQueryBuilder<I_GPLR_Report> queryBuilder = queryBL.createQueryBuilder(I_GPLR_Report.class)
				.addOnlyActiveRecordsFilter();

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addEqualsFilter(I_GPLR_Report.COLUMNNAME_Sales_Invoice_ID, invoiceId)
				.addEqualsFilter(I_GPLR_Report.COLUMNNAME_Purchase_Invoice_ID, invoiceId);

		return queryBuilder.anyMatch();
	}

	public void deleteById(@NonNull final GPLRReportId reportId)
	{
		queryBL.createQueryBuilder(I_GPLR_Report_SalesOrder.class).addEqualsFilter(I_GPLR_Report_SalesOrder.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_Shipment.class).addEqualsFilter(I_GPLR_Report_Shipment.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_PurchaseOrder.class).addEqualsFilter(I_GPLR_Report_PurchaseOrder.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_Summary.class).addEqualsFilter(I_GPLR_Report_Summary.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_Line.class).addEqualsFilter(I_GPLR_Report_Line.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_Charge.class).addEqualsFilter(I_GPLR_Report_Charge.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report_Note.class).addEqualsFilter(I_GPLR_Report_Note.COLUMNNAME_GPLR_Report_ID, reportId).create().deleteDirectly();
		queryBL.createQueryBuilder(I_GPLR_Report.class).addEqualsFilter(I_GPLR_Report.COLUMNNAME_GPLR_Report_ID, reportId).create().delete();
	}
}
