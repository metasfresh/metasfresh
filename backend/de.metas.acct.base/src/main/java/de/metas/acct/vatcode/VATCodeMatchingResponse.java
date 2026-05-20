package de.metas.acct.vatcode;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of {@link IVATCodeDAO#findVATCode(VATCodeMatchingRequest)}: carries both the
 * resolved {@link VATCode} and the {@link VATCodeAmountType} of the matched master record.
 * <p>
 * The matcher already knows {@code AmountType} at lookup time (from the matched
 * {@code C_VAT_Code} row); returning it here lets callers persist the discriminator on
 * the fact line they're posting, instead of having to look the master record up again.
 */
@Value
@Builder
public class VATCodeMatchingResponse
{
	@NonNull VATCode vatCode;

	/** {@code null} when the matched {@code C_VAT_Code} master row has {@code AmountType=NULL}. */
	@Nullable VATCodeAmountType vatCodeAmountType;
}
