package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.AccountConceptualName;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.util.text.tabular.Row;
import lombok.Builder;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Builder
public class FactAcctMatcher
{
	@NonNull private final DataTableRow row;

	@NonNull private final AccountConceptualName accountConceptualName;
	@Nullable private final BigDecimal amtAcctDr;
	@Nullable private final BigDecimal amtAcctCr;
	@Nullable private final Money amtSourceDr;
	@Nullable private final Money amtSourceCr;
	@Nullable private final Quantity qty;

	public void assertValid(
			@NonNull final I_Fact_Acct record,
			@NonNull final SoftAssertions softly,
			@NonNull final ContextAwareDescription description)
	{
		softly.assertThat(AccountConceptualName.ofString(record.getAccountConceptualName()))
				.as(description.newWithMessage("AccountConceptualName"))
				.isEqualTo(accountConceptualName);

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
	}

	@Override
	public String toString() {return row.toTabularString();}

	Row toTabularRow() {return row.toTabularRow();}
	
	int getLineNo() { return row.getLineNo();}
}
