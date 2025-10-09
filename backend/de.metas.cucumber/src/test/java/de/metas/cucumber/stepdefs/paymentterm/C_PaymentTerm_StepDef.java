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

package de.metas.cucumber.stepdefs.paymentterm;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_Value;

@RequiredArgsConstructor
public class C_PaymentTerm_StepDef
{
	@NonNull private final C_PaymentTerm_StepDefData paymentTermTable;
	@NonNull private final C_PaymentTerm_Break_StepDefData paymentTermBreakTable;

	private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("load C_PaymentTerm by id:")
	public void loadC_PaymentTerm(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int paymentTermId = DataTableUtil.extractIntForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID);

			final I_C_PaymentTerm paymentTerm = InterfaceWrapperHelper.load(paymentTermId, I_C_PaymentTerm.class);

			final String paymentTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			paymentTermTable.put(paymentTermIdentifier, paymentTerm);
		}
	}

	@And("metasfresh contains C_PaymentTerm")
	public void add_C_PaymentTerm(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID)
				.forEach((row) -> {
					final ValueAndName valueAndName = row.suggestValueAndName();

					final I_C_PaymentTerm paymentTermRecord = CoalesceUtil.coalesceSuppliers(
							() -> queryBL.createQueryBuilder(I_C_PaymentTerm.class)
									.addEqualsFilter(COLUMNNAME_Value, valueAndName.getValue())
									.create()
									.firstOnlyOrNull(I_C_PaymentTerm.class),
							() -> InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class));

					assertThat(paymentTermRecord).isNotNull();

					final boolean isComplex = row.getAsOptionalBoolean(I_C_PaymentTerm.COLUMNNAME_IsComplex).orElse(false);

					paymentTermRecord.setValue(valueAndName.getValue());
					paymentTermRecord.setName(valueAndName.getName());
					paymentTermRecord.setIsComplex(isComplex);

					saveRecord(paymentTermRecord);

					row.getAsIdentifier().put(paymentTermTable, paymentTermRecord);

				});
	}

	@And("metasfresh contains C_PaymentTerm_Break")
	public void add_C_PaymentTerm_Break(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_PaymentTerm_Break(tableRow);
		}
	}

	private void createC_PaymentTerm_Break(@NonNull final Map<String, String> tableRow)
	{
		final int paymentTermId = DataTableUtil.extractIntForColumnName(tableRow, I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID);
		final int percent = DataTableUtil.extractIntForColumnName(tableRow, I_C_PaymentTerm_Break.COLUMNNAME_Percent);
		final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, I_C_PaymentTerm_Break.COLUMNNAME_SeqNo);
		final String referenceDateType = DataTableUtil.extractStringForColumnName(tableRow, I_C_PaymentTerm_Break.COLUMNNAME_ReferenceDateType);

		final I_C_PaymentTerm_Break paymentTermBreak = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm_Break.class);

		paymentTermBreak.setC_PaymentTerm_ID(paymentTermId);
		paymentTermBreak.setPercent(percent);
		paymentTermBreak.setReferenceDateType(referenceDateType);
		paymentTermBreak.setSeqNo(seqNo);

		InterfaceWrapperHelper.saveRecord(paymentTermBreak);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_C_PaymentTerm_Break.Table_Name);
		paymentTermBreakTable.putOrReplace(recordIdentifier, paymentTermBreak);
	}

	@And("validate C_PaymentTerm:")
	public void validateC_PaymentTerm(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String value = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_Name);

			final String incotermsIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_PaymentTerm paymentTerm = paymentTermTable.get(incotermsIdentifier);

			softly.assertThat(paymentTerm.getValue()).isEqualTo(value);
			softly.assertThat(paymentTerm.getName()).isEqualTo(name);

			softly.assertAll();
		}
	}

	public Optional<PaymentTermId> extractPaymentTermId(final @NonNull DataTableRow row)
	{
		final StepDefDataIdentifier paymentTermIdentifier = Optionals.firstPresentOfSuppliers(
						() -> row.getAsOptionalIdentifier("paymentTerm"),
						() -> row.getAsOptionalIdentifier(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID))
				.orElse(null);
		if (paymentTermIdentifier == null)
		{
			return Optional.empty();
		}

		//
		// Lookup C_PaymentTerm table
		PaymentTermId paymentTermId = paymentTermTable.getIdOptional(paymentTermIdentifier).orElse(null);
		if (paymentTermId != null)
		{
			return Optional.of(paymentTermId);
		}

		//
		// Search by name
		paymentTermId = paymentTermRepo.retrievePaymentTermId(
						PaymentTermQuery.builder()
								.orgId(StepDefConstants.ORG_ID)
								.value(paymentTermIdentifier.getAsString())
								.build())
				.orElse(null);
		if (paymentTermId != null)
		{
			return Optional.of(paymentTermId);
		}

		//
		// Consider it numeric ID
		paymentTermId = paymentTermIdentifier.getAsId(PaymentTermId.class);
		return Optional.of(paymentTermId);
	}
}
