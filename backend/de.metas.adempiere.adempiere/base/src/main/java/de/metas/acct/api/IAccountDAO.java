package de.metas.acct.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

import java.util.List;
import java.util.Properties;
import java.util.Set;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IAccountDAO extends ISingletonService
{
	@NonNull
	MAccount getById(Properties ctx, int validCombinationId);

	@NonNull
	default MAccount getById(final int validCombinationId)
	{
		return getById(Env.getCtx(), validCombinationId);
	}

	@NonNull
	MAccount getById(AccountId accountId);

	List<I_C_ValidCombination> getByElementTypes(@NonNull Set<AcctSchemaElementType> elementTypes, int value);

	List<I_C_ValidCombination> getByAcctSchemaId(@NonNull AcctSchemaId acctSchemaId);

	List<I_C_ValidCombination> getByClientId(@NonNull ClientId clientId);

	@NonNull
	AccountId getOrCreateAccountId(@NonNull AccountDimension dimension);

	@NonNull MAccount getOrCreateAccount(@NonNull AccountDimension dimension);

	void save(I_C_ValidCombination record);
}
