package de.metas.cucumber.stepdefs.invoice;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.order.C_OrderPaySchedule_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentschedule.InvoicePaySchedule;
import de.metas.invoice.paymentschedule.InvoicePayScheduleLine;
import de.metas.invoice.paymentschedule.service.InvoicePayScheduleService;
import de.metas.money.MoneyService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoicePaySchedule;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_InvoicePaySchedule_StepDef
{
	@NonNull InvoicePayScheduleService invoicePayScheduleService = SpringContextHolder.instance.getBean(InvoicePayScheduleService.class);
	@NonNull MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull C_Invoice_StepDefData invoiceTable;
	@NonNull C_Order_StepDefData orderTable;
	@NonNull private final C_OrderPaySchedule_StepDefData orderPayScheduleTable;

	@And("^the invoice identified by (.*) has following pay schedules$")
	public void verifyPaySchedules(@NonNull final String invoiceIdentifier, @NonNull final DataTable dataTable)
	{
		final InvoiceId invoiceId = invoiceTable.getId(invoiceIdentifier);
		final InvoicePaySchedule paySchedule = invoicePayScheduleService.getByInvoiceId(invoiceId)
				.orElseThrow(() -> new AdempiereException("No pay schedule found for " + invoiceId));

		final DataTableRows rows = DataTableRows.of(dataTable);
		assertThat(paySchedule.getLines()).hasSameSizeAs(rows.toList());

		rows.forEach((row, index) -> verifyPaySchedule(paySchedule.getLines().get(index), row));
	}

	public void verifyPaySchedule(@NonNull final InvoicePayScheduleLine payScheduleLine, @NonNull final DataTableRow row)
	{
		SharedTestContext.put("payScheduleLine", payScheduleLine);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBoolean(I_C_InvoicePaySchedule.COLUMNNAME_IsValid)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.isValid()).as("IsValid").isEqualTo(expected));
		row.getAsOptionalLocalDate(I_C_InvoicePaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		row.getAsOptionalMoney(I_C_InvoicePaySchedule.COLUMNNAME_DueAmt, moneyService::getCurrencyIdByCurrencyCode)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueAmount()).as("DueAmount").isEqualTo(expected));

		row.getAsOptionalIdentifier(I_C_InvoicePaySchedule.COLUMNNAME_C_Order_ID)
				.map(orderTable::getId)
				.ifPresent(expectedOrderId -> softly.assertThat(payScheduleLine.getOrderId()).as("C_Order_ID").isEqualTo(expectedOrderId));
		row.getAsOptionalIdentifier(I_C_InvoicePaySchedule.COLUMNNAME_C_OrderPaySchedule_ID)
				.map(orderPayScheduleTable::getId)
				.ifPresent(expectedOrderPayScheduleId -> softly.assertThat(payScheduleLine.getOrderPayScheduleId()).as("C_OrderPaySchedule_ID").isEqualTo(expectedOrderPayScheduleId));

		softly.assertAll();
	}

}
