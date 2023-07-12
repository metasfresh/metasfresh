package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.currency.Amount;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
class FutureClearingAmount
{
	@NonNull FAOpenItemKey key;
	@NonNull Amount amount;

	public FutureClearingAmount add(FutureClearingAmount other)
	{
		assertSameKey(other);

		if (other.amount.isZero())
		{
			return this;
		}
		else if (this.amount.isZero())
		{
			return other;
		}
		else
		{
			return FutureClearingAmount.builder()
					.key(key)
					.amount(this.amount.add(other.amount))
					.build();
		}
	}

	private void assertSameKey(@NonNull final FutureClearingAmount other)
	{
		if (!FAOpenItemKey.equals(this.key, other.key))
		{
			throw new AdempiereException("Key does not match: " + this + ", " + other);
		}
	}
}
