package de.metas.acct.gljournal_sap;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class DebitAndCreditAmountsBD
{
	@NonNull BigDecimal debit;
	@NonNull BigDecimal credit;
}
