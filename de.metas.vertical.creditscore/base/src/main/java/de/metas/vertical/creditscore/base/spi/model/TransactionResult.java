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

	@NonNull
	private ResultCode resultCode;

	@NonNull
	private ResultCode resultCodeEffective;

	private ResultCode resultCodeOverride;

	@NonNull
	TransactionResultId transactionResultId;

	@NonNull
	private LocalDateTime requestDate;

}
