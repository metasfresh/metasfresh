package de.metas.acct.open_items.handlers;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerCustomerAccountType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class C_Receivable_OIHandler extends BPartnerOIHandler
{
	@Override
	public @NonNull AccountConceptualName getHandledAccountConceptualName() {return BPartnerCustomerAccountType.C_Receivable.getAccountConceptualName();}
}
