package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class POSTerminalRaw
{
	@NonNull POSTerminalId id;
	@NonNull PriceListId priceListId;
	@NonNull WarehouseId shipFromWarehouseId;
	@NonNull BPartnerId walkInCustomerId;
	@NonNull DocTypeId salesOrderDocTypeId;
	
	@NonNull BankAccountId cashbookId;
	@Nullable POSTerminalPaymentProcessorConfig paymentProcessorConfig;

	@Nullable POSCashJournalId cashJournalId;
	@NonNull BigDecimal cashLastBalance;
}
