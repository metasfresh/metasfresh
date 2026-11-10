package de.metas.cucumber.stepdefs.costing;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountType;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Builder
class CostDetailMatcher
{
	@NonNull @Getter private final DataTableRow row;
	@NonNull @Getter private final CostingDocumentRef documentRef;
	@NonNull @Getter private final CostAmountType amtType;
	@Nullable private final Money amt;
	@Nullable private final Quantity qty;
	// expected M_CostDetail.Prev_* (cost state BEFORE the movement); null = not asserted
	@Nullable private final Money prevCurrentCostPrice;
	@Nullable private final Quantity prevCurrentQty;
	@Nullable private final Money prevCumulatedAmt;
	@Nullable private final Quantity prevCumulatedQty;

	public BooleanWithReason checkValid(@NonNull final CostDetail costDetail)
	{
		if (!CostingDocumentRef.equals(costDetail.getDocumentRef(), documentRef))
		{
			return BooleanWithReason.falseBecause("Invalid documentRef " + costDetail.getDocumentRef() + ", expected " + documentRef);
		}

		if (amt != null)
		{
			final CostAmount amtActual = costDetail.getAmt();
			if (!Money.equals(amtActual.toMoney(), amt)
					&& !Money.equals(amtActual.toSourceMoney(), amt))
			{
				return BooleanWithReason.falseBecause("Invalid amt " + amtActual + ", expected " + amt);
			}
		}

		if (qty != null)
		{
			final Quantity actualQty = costDetail.getQty();
			if (actualQty.compareTo(qty) != 0)
			{
				return BooleanWithReason.falseBecause("Invalid qty " + actualQty + ", expected " + qty);
			}
		}

		if (prevCurrentCostPrice != null || prevCurrentQty != null || prevCumulatedAmt != null || prevCumulatedQty != null)
		{
			final CostDetailPreviousAmounts prev = costDetail.getPreviousAmounts();
			if (prev == null)
			{
				return BooleanWithReason.falseBecause("No Prev_* amounts, expected some");
			}
			if (prevCurrentCostPrice != null && !Money.equals(prev.getCostPrice().toCostAmount().toMoney(), prevCurrentCostPrice))
			{
				return BooleanWithReason.falseBecause("Invalid Prev_CurrentCostPrice " + prev.getCostPrice().toCostAmount() + ", expected " + prevCurrentCostPrice);
			}
			if (prevCurrentQty != null && prev.getQty().compareTo(prevCurrentQty) != 0)
			{
				return BooleanWithReason.falseBecause("Invalid Prev_CurrentQty " + prev.getQty() + ", expected " + prevCurrentQty);
			}
			if (prevCumulatedAmt != null && !Money.equals(prev.getCumulatedAmt().toMoney(), prevCumulatedAmt))
			{
				return BooleanWithReason.falseBecause("Invalid Prev_CumulatedAmt " + prev.getCumulatedAmt() + ", expected " + prevCumulatedAmt);
			}
			if (prevCumulatedQty != null && prev.getCumulatedQty().compareTo(prevCumulatedQty) != 0)
			{
				return BooleanWithReason.falseBecause("Invalid Prev_CumulatedQty " + prev.getCumulatedQty() + ", expected " + prevCumulatedQty);
			}
		}

		return BooleanWithReason.TRUE;
	}

	public TableRecordReference getTableRecordReference() {return documentRef.toTableRecordReference();}
}
