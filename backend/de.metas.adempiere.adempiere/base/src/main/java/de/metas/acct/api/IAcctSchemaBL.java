/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.acct.api;

import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;

public interface IAcctSchemaBL extends ISingletonService
{
	AcctSchemaId getAcctSchemaIdByClientAndOrg(@NonNull ClientId clientId, @NonNull OrgId orgId);

	CurrencyId getAcctCurrencyId(@NonNull ClientId clientId, @NonNull OrgId orgId);

	CurrencyId getAcctCurrencyId(@NonNull AcctSchemaId acctSchemaId);

	void updateDebitorCreditorIds(@NonNull final AcctSchema acctSchema, @Nullable OrgId orgId);

	void updateDebitorCreditorIds(@NonNull AcctSchema acctSchema, @NonNull I_C_BPartner bpartner);
}
