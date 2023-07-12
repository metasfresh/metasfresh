package de.metas.acct.open_items.handlers;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerVendorAccountType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class V_Liability_OIHandler extends BPartnerOIHandler
{
	@Override
	public @NonNull AccountConceptualName getHandledAccountConceptualName() {return BPartnerVendorAccountType.V_Liability.getAccountConceptualName();}
}
