package de.metas.acct.doc;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class AcctDocContext
{
	@NonNull AcctDocRequiredServicesFacade services;
	@NonNull ImmutableList<AcctSchema> acctSchemas;
	@NonNull AcctDocModel documentModel;

	@Builder
	private AcctDocContext(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final List<AcctSchema> acctSchemas,
			@NonNull final AcctDocModel documentModel)
	{
		Check.assumeNotEmpty(acctSchemas, "acctSchemas is not empty");

		this.services = services;
		this.acctSchemas = ImmutableList.copyOf(acctSchemas);
		this.documentModel = documentModel;
	}
}
