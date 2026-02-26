/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.payment;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.process.C_PaySelection_CreateFrom;
import de.metas.banking.process.C_PaySelection_CreatePayments;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderPaySchedule_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_PaySelection_StepDef
{
	private final @NonNull IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final @NonNull IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final @NonNull IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final @NonNull IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private final @NonNull C_PaySelection_StepDefData paySelectionTable;
	private final @NonNull C_BP_BankAccount_StepDefData bankAccountTable;
	private final @NonNull C_BPartner_StepDefData bpartnerTable;
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull C_OrderPaySchedule_StepDefData orderPayScheduleTable;
	private final @NonNull C_Invoice_StepDefData invoiceTable;
	private final @NonNull C_Payment_StepDefData paymentTable;

	@And("metasfresh contains Pay Selection")
	public void addPaySelections(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
				.forEach(this::addPaySelection);
	}

	private void addPaySelection(@NonNull final DataTableRow row)
	{
		final I_C_PaySelection paySelectionRecord = newInstance(I_C_PaySelection.class);

		final ValueAndName valueAndName = row.suggestValueAndName();
		paySelectionRecord.setName(valueAndName.getName());

		row.getAsOptionalIdentifier(I_C_PaySelection.COLUMNNAME_C_BP_BankAccount_ID)
				.map(bankAccountTable::getId)
				.ifPresent(id -> paySelectionRecord.setC_BP_BankAccount_ID(id.getRepoId()));

		row.getAsOptionalInstantTimestamp(I_C_PaySelection.COLUMNNAME_PayDate)
				.ifPresent(paySelectionRecord::setPayDate);

		row.getAsOptionalString(I_C_PaySelection.COLUMNNAME_PaySelectionTrxType)
				.ifPresent(paySelectionRecord::setPaySelectionTrxType);

		saveRecord(paySelectionRecord);

		paySelectionTable.putOrReplace(row.getAsIdentifier(), paySelectionRecord);
	}

	@And("^\"Create from...\" is invoked for pay selection (.*), using following parameters:$")
	public void createFrom_invoked(@NonNull String paySelectionIdentifierStr, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
				.singleRow();
		final PaySelectionId paySelectionId = paySelectionTable.getId(paySelectionIdentifierStr);

		final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
				.setAD_Process_ID(adProcessDAO.retrieveProcessIdByClass(C_PaySelection_CreateFrom.class))
				.setRecord(I_C_PaySelection.Table_Name, paySelectionId)
				.addParameter(C_PaySelection_CreateFrom.PARAM_MatchRequirement, row.getAsString("MatchRequirement"));

		row.getAsOptionalIdentifier("C_BPartner_ID")
				.map(bpartnerTable::getId)
				.ifPresent(bpartnerId -> processInfoBuilder.addParameter(C_PaySelection_CreateFrom.PARAM_C_BPartner_ID, bpartnerId.getRepoId()));
		row.getAsOptionalBoolean("OnlyDue")
				.ifPresent(onlyDue -> processInfoBuilder.addParameter(C_PaySelection_CreateFrom.PARAM_OnlyDue, onlyDue));
		row.getAsOptionalLocalDate("PayDate")
				.ifPresent(payDate -> processInfoBuilder.addParameter(C_PaySelection_CreateFrom.PARAM_PayDate, payDate));

		processInfoBuilder
				.buildAndPrepareExecution()
				.executeSync();
	}

	@Then("^\"Create Payments\" is invoked for pay selection (.*)$")
	public void createPayments_invoked(@NonNull String paySelectionIdentifierStr)
	{
		final PaySelectionId paySelectionId = paySelectionTable.getId(paySelectionIdentifierStr);

		ProcessInfo.builder()
				.setAD_Process_ID(adProcessDAO.retrieveProcessIdByClass(C_PaySelection_CreatePayments.class))
				.setRecord(I_C_PaySelection.Table_Name, paySelectionId)
				.buildAndPrepareExecution()
				.executeSync();
	}

	@And("^the pay selection identified by (.*) is completed$")
	public void paysSelection_Complete(@NonNull final String paySelectionIdentifier)
	{
		final I_C_PaySelection paySelection = paySelectionTable.get(paySelectionIdentifier);
		paySelection.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(paySelection, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@And("^the Pay selection identified by (.*) has exactly the following lines$")
	public void verifyPaySelectionLines(@NonNull final String paySelectionIdentifier, @NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable).setAdditionalRowIdentifierColumnName(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID);
		final PaySelectionId paySelectionId = paySelectionTable.getId(paySelectionIdentifier);
		final List<I_C_PaySelectionLine> lines = paySelectionBL.retrievePaySelectionLines(paySelectionId);

		assertThat(lines).hasSize(rows.size());
		rows.forEach((row, index) -> {
			final I_C_PaySelectionLine line = lines.get(index);
			verifyPaySelectionLine(line, row);
		});
	}

	private void verifyPaySelectionLine(@NonNull final I_C_PaySelectionLine line, @NonNull final DataTableRow expectation)
	{
		final SoftAssertions softly = new SoftAssertions();

		expectation.getAsOptionalIdentifier(I_C_PaySelectionLine.COLUMNNAME_C_Order_ID)
				.ifPresent(orderIdentifier -> {
					final OrderId expectedOrderId = orderIdentifier.isNullPlaceholder() ? null : orderTable.getId(orderIdentifier);
					softly.assertThat(OrderId.ofRepoIdOrNull(line.getC_Order_ID())).as("C_Order_ID").isEqualTo(expectedOrderId);
				});
		expectation.getAsOptionalIdentifier(I_C_PaySelectionLine.COLUMNNAME_C_OrderPaySchedule_ID)
				.ifPresent(orderPayScheduleIdentifier -> {
					final OrderPayScheduleId expectedOrderPayScheduleId = orderPayScheduleIdentifier.isNullPlaceholder() ? null : orderPayScheduleTable.getId(orderPayScheduleIdentifier);
					softly.assertThat(OrderPayScheduleId.ofRepoIdOrNull(line.getC_OrderPaySchedule_ID())).as("C_OrderPaySchedule_ID").isEqualTo(expectedOrderPayScheduleId);
				});
		expectation.getAsOptionalIdentifier(I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID)
				.ifPresent(invoiceIdentifier -> {
					final InvoiceId expectedInvoiceId = invoiceIdentifier.isNullPlaceholder() ? null : invoiceTable.getId(invoiceIdentifier);
					softly.assertThat(InvoiceId.ofRepoIdOrNull(line.getC_Invoice_ID())).as("C_Invoice_ID").isEqualTo(expectedInvoiceId);
				});
		expectation.getAsOptionalIdentifier(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID)
				.ifPresent(paymentIdentifier -> validatePaymentId(softly, paymentIdentifier, PaymentId.ofRepoIdOrNull(line.getC_Payment_ID())));
		expectation.getAsOptionalBigDecimal(I_C_PaySelectionLine.COLUMNNAME_PayAmt)
				.ifPresent(expectedPayAmt -> softly.assertThat(line.getPayAmt()).as("PayAmt").isEqualTo(expectedPayAmt));
		expectation.getAsOptionalBigDecimal(I_C_PaySelectionLine.COLUMNNAME_OpenAmt)
				.ifPresent(expectedOpenAmt -> softly.assertThat(line.getOpenAmt()).as("OpenAmt").isEqualTo(expectedOpenAmt));

		softly.assertAll();
	}

	private void validatePaymentId(
			@NonNull SoftAssertions softly,
			@NonNull final StepDefDataIdentifier expectedPaymentIdentifier,
			@Nullable final PaymentId actualPaymentId)
	{
		final String actualPaymentIdentifier = actualPaymentId != null
				? paymentTable.getFirstIdentifierById(actualPaymentId).map(StepDefDataIdentifier::getAsString).orElse("?NEW?")
				: null;

		final String description = "expectedPaymentIdentifier=" + expectedPaymentIdentifier + ", actualPaymentId=" + actualPaymentId + ", actualPaymentIdentifier=" + actualPaymentIdentifier;

		if (expectedPaymentIdentifier.isNullPlaceholder())
		{
			softly.assertThat(actualPaymentId).as(description).isNull();
		}
		else
		{
			softly.assertThat(actualPaymentId).as(description).isNotNull();
			if (actualPaymentId != null)
			{
				final PaymentId expectedPaymentId = paymentTable.getIdOptional(expectedPaymentIdentifier).orElse(null);
				if (expectedPaymentId == null)
				{
					paymentTable.put(expectedPaymentIdentifier, paymentBL.getById(actualPaymentId));
				}
				else
				{
					softly.assertThat(actualPaymentId).as(description).isEqualTo(expectedPaymentId);
				}
			}
		}
	}

}
