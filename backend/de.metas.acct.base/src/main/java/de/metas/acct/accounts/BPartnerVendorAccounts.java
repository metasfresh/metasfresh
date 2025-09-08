package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.slf4j.Logger;

@Value
@Builder
public class BPartnerVendorAccounts
{
	private static final Logger logger = LogManager.getLogger(BPartnerVendorAccounts.class);

	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Account V_Liability_Acct;
	@NonNull Account V_Liability_Services_Acct;
	@NonNull Account V_Prepayment_Acct;

	@NonNull
	public Account getAccount(@NonNull final BPartnerVendorAccountType acctType)
	{
		switch (acctType)
		{
			case V_Liability:
				return V_Liability_Acct;
			case V_Liability_Services:
				return V_Liability_Services_Acct;
			case V_Prepayment:
				// metas: changed per Mark request: don't use prepayment account:
				logger.warn("V_Prepayment account shall not be used", new Exception());
				return V_Prepayment_Acct;
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}
}
