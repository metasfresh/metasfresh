package de.metas.payment.sumup.repository;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateByPendingStatusResult
{
	int countOK;
	int countError;

	public boolean isZero() {return countOK == 0 && countError == 0;}
}
