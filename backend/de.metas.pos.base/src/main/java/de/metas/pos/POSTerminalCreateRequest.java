package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

/**
 * Creates a {@code C_POS} record with no payment processor, no cash journal and a zero cash balance.
 */
@Value
@Builder
public class POSTerminalCreateRequest
{
	@NonNull OrgId orgId;
	@NonNull String name;
	@NonNull BPartnerId walkInCustomerId;
	@NonNull BankAccountId cashbookId;
	@NonNull DocTypeId salesOrderDocTypeId;
	@NonNull PriceListId priceListId;
	@NonNull WarehouseId shipFromWarehouseId;
}
