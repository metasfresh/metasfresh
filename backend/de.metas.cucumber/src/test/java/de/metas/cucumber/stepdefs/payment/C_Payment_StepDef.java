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

import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_Payment_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsAllocated;
import static org.compiere.model.I_C_Payment.COLUMNNAME_IsReceipt;
import static org.compiere.model.I_C_Payment.COLUMNNAME_PayAmt;

public class C_Payment_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Payment_StepDefData paymentTable;
	private final CurrencyRepository currencyRepository;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;

	public C_Payment_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final C_Payment_StepDefData paymentTable,
			@NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.currencyRepository = currencyRepository;
		this.paymentTable = paymentTable;
		this.bpBankAccountTable = bpBankAccountTable;
	}

	@And("metasfresh contains C_Payment")
	public void addC_Payment(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			create_C_Payment(dataTableRow);
		}
	}

	@And("^the payment identified by (.*) is (completed|reversed)$")
	public void payment_action(@NonNull final String paymentIdentifier, @NonNull final String action)
	{
		final I_C_Payment payment = paymentTable.get(paymentIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reversed:
				payment.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(payment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			case completed:
				payment.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(payment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			default:
				throw new AdempiereException("Unhandled C_Payment action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@And("validate payments")
	public void validate_created_payments(@NonNull final DataTable table)
	{
		final List<Map<String, String>> rows = table.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			final String paymentIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, COLUMNNAME_C_Payment_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Boolean paymentIsAllocated = DataTableUtil.extractBooleanForColumnName(dataTableRow, COLUMNNAME_C_Payment_ID + "." + COLUMNNAME_IsAllocated);
			final BigDecimal expectedAvailableAmt = DataTableUtil.extractBigDecimalOrNullForColumnName(dataTableRow, "OPT.OpenAmt");
			final I_C_Payment payment = paymentTable.get(paymentIdentifier);
			InterfaceWrapperHelper.refresh(payment);

			final BigDecimal paymentAvailableAmt = paymentDAO.getAvailableAmount(PaymentId.ofRepoId(payment.getC_Payment_ID()));

			assertThat(payment.isAllocated()).isEqualTo(paymentIsAllocated);
			if (expectedAvailableAmt != null)
			{
				assertThat(paymentAvailableAmt).isEqualTo(payment.isReceipt() ? expectedAvailableAmt : expectedAvailableAmt.negate());
			}
		}
	}

	private void create_C_Payment(@NonNull final Map<String, String> row)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final BigDecimal paymentAmount = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_PayAmt);

		final String currencyISO = DataTableUtil.extractStringForColumnName(row, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyISO));

		final String docTypeName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_DocType_ID + ".Name");
		final DocTypeId docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
				.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, docTypeName)
				.orderBy(I_C_DocType.COLUMNNAME_Name)
				.create()
				.firstId(DocTypeId::ofRepoIdOrNull);

		final boolean isReceipt = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsReceipt);

		final String bpBankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.Table_Name + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BP_BankAccount bpBankAccount = bpBankAccountTable.get(bpBankAccountIdentifier);

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);

		payment.setC_BPartner_ID(bPartnerId);
		payment.setPayAmt(paymentAmount);
		payment.setC_Currency_ID(currencyId.getRepoId());
		payment.setC_DocType_ID(docTypeId.getRepoId());
		payment.setDateTrx(TimeUtil.asTimestamp(LocalDate.now()));
		payment.setC_BP_BankAccount_ID(bpBankAccount.getC_BP_BankAccount_ID());
		payment.setIsReceipt(isReceipt);
		payment.setIsAutoAllocateAvailableAmt(false);

		paymentDAO.save(payment);

		final String paymentIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
		paymentTable.putOrReplace(paymentIdentifier, payment);
	}
}
