package de.metas.payment.sumup.repository;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateByPendingStatusResult
{
	int countOK;
	int countError;
}
