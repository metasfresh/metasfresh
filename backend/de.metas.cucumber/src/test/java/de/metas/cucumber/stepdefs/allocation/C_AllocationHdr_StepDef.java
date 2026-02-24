package de.metas.cucumber.stepdefs.allocation;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;

public class C_AllocationHdr_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Invoice_StepDefData invoiceTable;
	private final C_Payment_StepDefData paymentTable;

	public C_AllocationHdr_StepDef(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Payment_StepDefData paymentTable)
	{
		this.invoiceTable = invoiceTable;
		this.paymentTable = paymentTable;
	}

	@Then("validate C_AllocationHdr for invoice and payment")
	public void validate_C_AllocationHdr(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
			assertThat(invoice).isNotNull();

			final String paymentIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Payment payment = paymentTable.get(paymentIdentifier);
			assertThat(payment).isNotNull();

			final I_C_AllocationLine allocationLine = queryBL.createQueryBuilder(I_C_AllocationLine.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
					.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID())
					.create()
					.firstOnlyNotNull(I_C_AllocationLine.class);

			final I_C_AllocationHdr allocationHdr = InterfaceWrapperHelper.load(
					allocationLine.getC_AllocationHdr_ID(),
					I_C_AllocationHdr.class);
			assertThat(allocationHdr).isNotNull();

			final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(allocationHdr.getAD_Org_ID()));

			final SoftAssertions softly = new SoftAssertions();

			final LocalDate expectedDateAcct = DataTableUtil.extractLocalDateOrNullForColumnName(row, I_C_AllocationHdr.COLUMNNAME_DateAcct);
			if (expectedDateAcct != null)
			{
				softly.assertThat(TimeUtil.asLocalDate(allocationHdr.getDateAcct(), zoneId))
						.as("C_AllocationHdr.DateAcct")
						.isEqualTo(expectedDateAcct);
			}

			final LocalDate expectedDateTrx = DataTableUtil.extractLocalDateOrNullForColumnName(row, I_C_AllocationHdr.COLUMNNAME_DateTrx);
			if (expectedDateTrx != null)
			{
				softly.assertThat(TimeUtil.asLocalDate(allocationHdr.getDateTrx(), zoneId))
						.as("C_AllocationHdr.DateTrx")
						.isEqualTo(expectedDateTrx);
			}

			softly.assertAll();
		}
	}
}
