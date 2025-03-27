package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.AccountConceptualName;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.util.text.tabular.Row;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FactAcctLineMatcher
{
	@NonNull private final DataTableRow row;

	@Nullable private final AccountConceptualName accountConceptualName;
	@Nullable private final BigDecimal amtAcctDr;
	@Nullable private final BigDecimal amtAcctCr;
	@Nullable private final Money amtSourceDr;
	@Nullable private final Money amtSourceCr;
	@Nullable private final Quantity qty;
	@NonNull @Getter private final TableRecordReference documentRef;
	@Nullable @Getter private final TaxId taxId;
	@Nullable Optional<BPartnerId> bpartnerId;

	@Override
	public String toString() {return row.toTabularString();}

	Row toTabularRow() {return row.toTabularRow();}

	public boolean isWildcardMatcher()
	{
		return accountConceptualName == null;
	}

	void assertMatching(
			@NonNull final SoftAssertions softly,
			@NonNull final FactAcctRecordsToMatch records)
	{
		for (int recordIndex = 0; recordIndex < records.size(); recordIndex++)
		{
			if (records.isMatched(recordIndex))
			{
				continue;
			}

			final I_Fact_Acct record = records.get(recordIndex);

			if (isMatching(record))
			{
				records.markAsMatched(recordIndex);
				return;
			}
		}

		softly.fail("Expected Fact_Acct record was not found:"
				+ "\n" + this);
	}

	public boolean isMatching(@NonNull final I_Fact_Acct record)
	{
		final SoftAssertions recordSoftly = new SoftAssertions();
		assertMatching(record, recordSoftly, ContextAwareDescription.newInstance());
		final List<AssertionError> errors = recordSoftly.assertionErrorsCollected();
		return errors.isEmpty();
	}

	@SuppressWarnings("OptionalAssignedToNull")
	public void assertMatching(
			@NonNull final I_Fact_Acct record,
			@NonNull final SoftAssertions softly,
			@NonNull final ContextAwareDescription description)
	{
		if (accountConceptualName != null)
		{
			softly.assertThat(AccountConceptualName.ofString(record.getAccountConceptualName()))
					.as(description.newWithMessage("AccountConceptualName"))
					.isEqualTo(accountConceptualName);
		}
		if (amtAcctDr != null)
		{
			softly.assertThat(record.getAmtAcctDr())
					.as(description.newWithMessage("AmtAcctDr"))
					.isEqualByComparingTo(amtAcctDr);
		}
		if (amtAcctCr != null)
		{
			softly.assertThat(record.getAmtAcctCr())
					.as(description.newWithMessage("AmtAcctCr"))
					.isEqualByComparingTo(amtAcctCr);
		}
		if (amtSourceDr != null)
		{
			softly.assertThat(record.getAmtSourceDr())
					.as(description.newWithMessage("AmtSourceDr"))
					.isEqualByComparingTo(amtSourceDr.toBigDecimal());
			softly.assertThat(record.getC_Currency_ID())
					.as(description.newWithMessage("C_Currency_ID"))
					.isEqualTo(amtSourceDr.getCurrencyId().getRepoId());
		}
		if (amtSourceCr != null)
		{
			softly.assertThat(record.getAmtSourceCr())
					.as(description.newWithMessage("AmtSourceCr"))
					.isEqualByComparingTo(amtSourceCr.toBigDecimal());
			softly.assertThat(record.getC_Currency_ID())
					.as(description.newWithMessage("C_Currency_ID"))
					.isEqualTo(amtSourceCr.getCurrencyId().getRepoId());
		}
		if (qty != null)
		{
			softly.assertThat(record.getQty())
					.as(description.newWithMessage("Qty"))
					.isEqualByComparingTo(qty.toBigDecimal());
			softly.assertThat(record.getC_UOM_ID())
					.as(description.newWithMessage("C_UOM_ID"))
					.isEqualTo(qty.getUomId().getRepoId());
		}
		//if (documentRef != null)
		{
			softly.assertThat(TableIdsCache.instance.getTableName(AdTableId.ofRepoId(record.getAD_Table_ID())))
					.as(description.newWithMessage("AD_Table_ID"))
					.isEqualTo(documentRef.getTableName());
			softly.assertThat(record.getRecord_ID())
					.as(description.newWithMessage("Record_ID"))
					.isEqualTo(documentRef.getRecord_ID());
		}
		if (taxId != null)
		{
			softly.assertThat(TaxId.ofRepoIdOrNull(record.getC_Tax_ID()))
					.as(description.newWithMessage("C_Tax_ID"))
					.isEqualTo(taxId);
		}
		if (bpartnerId != null)
		{
			softly.assertThat(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID())).isEqualTo(bpartnerId.orElse(null));
		}
	}
}
