package de.metas.acct.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Properties;

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

	@NonNull
	AccountId getOrCreate(@NonNull AccountDimension dimension);

	@NonNull MAccount getOrCreateAccount(@NonNull AccountDimension dimension);
}
