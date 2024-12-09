package de.metas.acct.doc;

<<<<<<< HEAD
import java.util.List;

import com.google.common.collect.ImmutableList;

=======
import com.google.common.collect.ImmutableList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class AcctDocContext
{
<<<<<<< HEAD
	@NonNull
	AcctDocRequiredServicesFacade services;

	@NonNull
	ImmutableList<AcctSchema> acctSchemas;

	@NonNull
	Object documentModel;
=======
	@NonNull AcctDocRequiredServicesFacade services;
	@NonNull ImmutableList<AcctSchema> acctSchemas;
	@NonNull AcctDocModel documentModel;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Builder
	private AcctDocContext(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final List<AcctSchema> acctSchemas,
<<<<<<< HEAD
			@NonNull final Object documentModel)
=======
			@NonNull final AcctDocModel documentModel)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		Check.assumeNotEmpty(acctSchemas, "acctSchemas is not empty");

		this.services = services;
		this.acctSchemas = ImmutableList.copyOf(acctSchemas);
		this.documentModel = documentModel;
	}
}
