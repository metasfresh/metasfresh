package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.Account;
import de.metas.acct.api.AccountDimension;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;

public interface IFactAcctBL extends ISingletonService
{

	/**
	 * Gets/creates the account (i.e. {@link I_C_ValidCombination}) of given fact line.
	 */
	Account getAccount(I_Fact_Acct factAcct);

	void updateFactLineFromDimension(I_Fact_Acct fa, AccountDimension dim);

	AccountDimension createAccountDimension(I_Fact_Acct fa);

	void setC_BPartner_Location_ID(@NonNull I_Fact_Acct factAcct, @Nullable BPartnerLocationId bPartnerLocationId);

	void setC_BPartner_ID(@NonNull I_Fact_Acct factAcct, @Nullable BPartnerId bpartnerId);
}
