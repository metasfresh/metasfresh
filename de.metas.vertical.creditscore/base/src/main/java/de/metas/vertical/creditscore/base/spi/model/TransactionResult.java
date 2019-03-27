package de.metas.vertical.creditscore.base.spi.model;

import de.metas.vertical.creditscore.base.spi.repository.TransactionResultId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class TransactionResult
{

	private int resultCode;

	@NonNull
	TransactionResultId transactionResultId;

	@NonNull
	private LocalDateTime requestDate;

}
