package de.metas.fresh.ordercheckup;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.FallbackDocumentReportAdvisor;
import de.metas.report.PrintCopies;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Report info for the order checkup ({@code C_Order_MFGWarehouse_Report}): the generic result, adjusted for a Bestellkontrolle Büro (Plant)
 * whose order shipper (Lieferweg) carries a copy count ({@code M_Shipper.DocumentCopies}):
 * <ul>
 * <li>0: the automatic print is suppressed (the PDF is still archived; a manual print from the outbound documents still works,
 * because the suppress flag lives only on the archive instance created here)</li>
 * <li>N &gt; 0: the archive copies are 1. The N copies are set by the print handler on every enqueue
 * ({@link IOrderCheckupBL#getNumberOfCopies}); the printing queue multiplies them by the archive copies, so anything else
 * (e.g. a partner print-format count) would print k x N.</li>
 * </ul>
 * Found by table name only (no standard report type), like the generic advisor it replaces for this table.
 */
@Component
public class OrderCheckupDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);
	private final FallbackDocumentReportAdvisor fallbackAdvisor;

	public OrderCheckupDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.fallbackAdvisor = new FallbackDocumentReportAdvisor(util);
	}

	@Override
	@NonNull
	public String getHandledTableName()
	{
		return I_C_Order_MFGWarehouse_Report.Table_Name;
	}

	@Override
	@Nullable
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return null;
	}

	@Override
	@NonNull
	public DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId,
			@Nullable final AdProcessId reportProcessIdToUse)
	{
		final DocumentReportInfo reportInfo = fallbackAdvisor.getDocumentReportInfo(recordRef, adPrintFormatToUseId, reportProcessIdToUse);

		final I_C_Order_MFGWarehouse_Report report = recordRef.getModel(I_C_Order_MFGWarehouse_Report.class);
		final PrintCopies shipperCopies = orderCheckupBL.getShipperCopiesOrNull(report);
		if (shipperCopies == null)
		{
			return reportInfo;
		}

		return shipperCopies.isZero()
				? reportInfo.toBuilder().suppressAutoPrint(true).build()
				: reportInfo.toBuilder().copies(PrintCopies.ONE).build();
	}
}
