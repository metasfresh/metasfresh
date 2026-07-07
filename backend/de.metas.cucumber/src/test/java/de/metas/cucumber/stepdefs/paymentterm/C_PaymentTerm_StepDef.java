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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.paymentterm.repository.PaymentTermQuery;
import de.metas.util.OptionalBoolean;
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

import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_PaymentTerm_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IPaymentTermRepository paymentTermRepo = Services.get(IPaymentTermRepository.class);

	@NonNull private final C_PaymentTerm_StepDefData paymentTermTable;
	@NonNull private final C_PaymentTerm_Break_StepDefData paymentTermBreakTable;

	/** Wait budget for the PaymentTermRepository cache-reset to land after the C_PaymentTerm_Break rows were committed. */
	private static final int VALIDATE_TIMEOUT_SECONDS = 30;
	private static final int VALIDATE_CHECK_INTERVAL_MS = 200;

	@And("metasfresh contains C_PaymentTerm")
	public void createPaymentTerms(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID)
				.forEach(this::createPaymentTerm);
	}

	private void createPaymentTerm(final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();

		final I_C_PaymentTerm paymentTermRecord = InterfaceWrapperHelper.loadOrNew(paymentTermRepo.firstIdOnly(
						PaymentTermQuery.builder()
								.orgId(StepDefConstants.ORG_ID)
								.value(valueAndName.getValue())
								.build())
				.orElse(null), I_C_PaymentTerm.class);

		assertThat(paymentTermRecord).isNotNull();

		if (InterfaceWrapperHelper.isNew(paymentTermRecord))
		{
			paymentTermRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			paymentTermRecord.setNetDays(0);
			paymentTermRecord.setDiscount(ZERO);
			paymentTermRecord.setDiscount2(ZERO);
			paymentTermRecord.setDiscountDays(0);
			paymentTermRecord.setDiscountDays2(0);
			paymentTermRecord.setGraceDays(0);
		}

		paymentTermRecord.setValue(valueAndName.getValue());
		paymentTermRecord.setName(valueAndName.getName());

		row.getAsOptionalInt(I_C_PaymentTerm.COLUMNNAME_NetDays).ifPresent(paymentTermRecord::setNetDays);
		row.getAsOptionalBoolean(I_C_PaymentTerm.COLUMNNAME_IsAllowOverrideDueDate).ifPresent(paymentTermRecord::setIsAllowOverrideDueDate);

		saveRecord(paymentTermRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> paymentTermTable.put(identifier, paymentTermRecord));
	}

	@And("metasfresh contains C_PaymentTerm_Break")
	public void createC_PaymentTerm_Breaks(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_PaymentTerm_Break);
	}

	private void createC_PaymentTerm_Break(@NonNull final DataTableRow row)
	{
		final PaymentTermId paymentTermId = row.getAsIdentifier(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID).lookupNotNullIdIn(paymentTermTable);
		final int seqNo = row.getAsInt(I_C_PaymentTerm_Break.COLUMNNAME_SeqNo);

		final I_C_PaymentTerm_Break breakRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
						.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermId)
						.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_SeqNo, seqNo)
						.create()
						.firstOnly(I_C_PaymentTerm_Break.class),
				() -> newInstance(I_C_PaymentTerm_Break.class));

		breakRecord.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		breakRecord.setSeqNo(seqNo);
		row.getAsOptionalInt(I_C_PaymentTerm_Break.COLUMNNAME_Percent)
				.ifPresent(breakRecord::setPercent);
		row.getAsOptionalEnum(I_C_PaymentTerm_Break.COLUMNNAME_ReferenceDateType, ReferenceDateType.class)
				.ifPresent(referenceDateType -> breakRecord.setReferenceDateType(referenceDateType.getCode()));
		row.getAsOptionalInt(I_C_PaymentTerm_Break.COLUMNNAME_OffsetDays)
				.ifPresent(breakRecord::setOffsetDays);
		
		saveRecord(breakRecord);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> paymentTermBreakTable.put(identifier, breakRecord));
	}

	@And("validate C_PaymentTerm:")
	public void validateC_PaymentTerm(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID)
				.forEach(row -> {
					final SoftAssertions softly = new SoftAssertions();

					final StepDefDataIdentifier identifier = row.getAsIdentifier();
					final I_C_PaymentTerm paymentTerm = identifier.lookupNotNullIn(paymentTermTable);

					row.getAsOptionalString("Value")
							.ifPresent(value -> softly.assertThat(paymentTerm.getValue()).as("Value").isEqualTo(value));
					row.getAsOptionalString("Name")
							.ifPresent(name -> softly.assertThat(paymentTerm.getName()).as("Name").isEqualTo(name));
					row.getAsOptionalBoolean("IsComplex")
							.ifPresent(isComplex -> softly.assertThat(paymentTerm.isComplex()).as("IsComplex").isEqualTo(isComplex));

					final OptionalBoolean expectedIsValid = row.getAsOptionalBoolean("IsValid");
					if (expectedIsValid.isPresent())
					{
						assertPaymentTermRepoIsValid(identifier, expectedIsValid.isTrue(), softly);
					}

					softly.assertAll();
				});
	}

	/**
	 * Asserts the {@code IsValid} flag against the {@link IPaymentTermRepository}-computed validity, polling until it settles.
	 * <p>
	 * The repository caches all payment terms in one monolithic entry, whose per-term {@code valid} flag is derived at load
	 * time from the {@code C_PaymentTerm_Break} rows visible then. The cache reset triggered by inserting the breaks is
	 * delivered asynchronously and can lag under executor load. Reading the persisted {@code I_C_PaymentTerm.IsValid} column
	 * would neither exercise nor wait on that cache, so a scenario could proceed to order completion while the repository
	 * still served a stale partial term (e.g. only a 30% break -> "Total percent ... 30%"), and
	 * {@code PaymentTerm.assertValid()} would then throw during {@code C_Order} completion. Polling the repository here
	 * internalizes that wait: the step blocks until {@code getById()} reflects all committed breaks before the scenario
	 * continues, going through the exact same load path that completion later uses.
	 */
	private void assertPaymentTermRepoIsValid(
			@NonNull final StepDefDataIdentifier identifier,
			final boolean expectedIsValid,
			@NonNull final SoftAssertions softly) throws InterruptedException
	{
		final PaymentTermId paymentTermId = paymentTermTable.getId(identifier);

		StepDefUtil.tryAndWait(
				VALIDATE_TIMEOUT_SECONDS,
				VALIDATE_CHECK_INTERVAL_MS,
				() -> paymentTermRepo.getById(paymentTermId).isValid() == expectedIsValid);

		softly.assertThat(paymentTermRepo.getById(paymentTermId).isValid())
				.as("IsValid (PaymentTermRepository-computed)")
				.isEqualTo(expectedIsValid);
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
		paymentTermId = paymentTermRepo.firstIdOnly(
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
