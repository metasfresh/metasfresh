/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.process;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.GLCategoryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class GLJournalRequest
{
	@NonNull AcctSchemaId acctSchemaId;

	@NonNull ClientId clientId;

	@NonNull OrgId orgId;

	@NonNull Instant dateAcct;

	@NonNull Instant dateDoc;

	@NonNull String postingType;

	@NonNull CurrencyId currencyId;

	@NonNull String description;

	@Nullable
	GLCategoryId glCategoryId;

}
