package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.AccountConceptualName;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.util.text.tabular.Row;
import lombok.Builder;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FactAcctBalanceMacher
{
	@NonNull private final DataTableRow row;

	//
	// Grouping 
	@NonNull private final AccountConceptualName accountConceptualName;
	@Nullable private final Optional<TaxId> taxId;
	@Nullable private final Optional<BPartnerId> bpartnerId;
	@Nullable private final Optional<ProductId> productId;

	//
	// Aggregated amounts
	@Nullable private final BigDecimal amtAcctDr;
	@Nullable private final BigDecimal amtAcctCr;
	@Nullable private final BigDecimal acctBalance;
	@Nullable private final Money amtSourceDr;
	@Nullable private final Money amtSourceCr;
	@Nullable private final Money sourceBalance;
	@Nullable private final Quantity qty;

	@Override
	public String toString() {return row.toTabularString();}

	Row toTabularRow() {return row.toTabularRow();}

	void assertMatching(
			@NonNull final SoftAssertions softly,
			@NonNull final FactAcctRecords records)
	{
		final ContextAwareDescription description = ContextAwareDescription.newInstance();

		SharedTestContext.put("expectation", this);

		final FactAcctRecords matchingRecords = records.filter(this::isMatching);
		SharedTestContext.put("matchingRecords", matchingRecords);

		if (amtAcctDr != null)
		{
			final BigDecimal actual = matchingRecords.getAmtAcctDr();
			softly.assertThat(actual).as(description.newWithMessage("AmtAcctDr")).isEqualByComparingTo(amtAcctDr);
		}
		if (amtAcctCr != null)
		{
			final BigDecimal actual = matchingRecords.getAmtAcctCr();
			softly.assertThat(actual).as(description.newWithMessage("AmtAcctCr")).isEqualByComparingTo(amtAcctCr);
		}
		if (acctBalance != null)
		{
			final BigDecimal actual = matchingRecords.getAcctBalance();
			softly.assertThat(actual).as(description.newWithMessage("AcctBalance")).isEqualByComparingTo(acctBalance);
		}
		if (amtSourceDr != null)
		{
			final Money actual = matchingRecords.getAmtSourceDr().toNoneOrSingleValue().orElseGet(amtSourceDr::toZero);
			softly.assertThat(actual).as(description.newWithMessage("AmtSourceDr")).isEqualTo(amtSourceDr);
		}
		if (amtSourceCr != null)
		{
			final Money actual = matchingRecords.getAmtSourceCr().toNoneOrSingleValue().orElseGet(amtSourceCr::toZero);
			softly.assertThat(actual).as(description.newWithMessage("AmtSourceCr")).isEqualTo(amtSourceCr);
		}
		if (sourceBalance != null)
		{
			final Money actual = matchingRecords.getSourceBalance().toNoneOrSingleValue().orElseGet(sourceBalance::toZero);
			softly.assertThat(actual).as(description.newWithMessage("SourceBalance")).isEqualTo(sourceBalance);
		}
		if (qty != null)
		{
			final Quantity actual = matchingRecords.getQty().toNoneOrSingleValue().orElseGet(qty::toZero);
			softly.assertThat(actual).as(description.newWithMessage("Qty")).isEqualTo(qty);
		}
	}

	@SuppressWarnings("OptionalAssignedToNull")
	private boolean isMatching(@NonNull final I_Fact_Acct record)
	{
		final AccountConceptualName actualAccountConceptualName = AccountConceptualName.ofString(record.getAccountConceptualName());
		if (!AccountConceptualName.equals(actualAccountConceptualName, this.accountConceptualName))
		{
			return false;
		}
		if (taxId != null)
		{
			final TaxId actualTaxId = TaxId.ofRepoIdOrNull(record.getC_Tax_ID());
			final TaxId expectedTaxId = taxId.orElse(null);
			if (!TaxId.equals(actualTaxId, expectedTaxId))
			{
				return false;
			}
		}
		if (bpartnerId != null)
		{
			final BPartnerId actualBPartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());
			final BPartnerId expectedBPartnerId = bpartnerId.orElse(null);
			if (!BPartnerId.equals(actualBPartnerId, expectedBPartnerId))
			{
				return false;
			}
		}
		if (productId != null)
		{
			final ProductId actualProductId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
			final ProductId expectedProductId = productId.orElse(null);
			//noinspection RedundantIfStatement
			if (!ProductId.equals(actualProductId, expectedProductId))
			{
				return false;
			}
		}

		return true;
	}
}
