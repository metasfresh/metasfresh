package de.metas.ordercandidate.api;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class OLCandProcessorDescriptor
{
	private final int id;
	private final OLCandOrderDefaults defaults;
	private final OLCandAggregation aggregationInfo;
	private final int userInChangeId;

	@Builder
	private OLCandProcessorDescriptor(
			final int id,
			@NonNull final OLCandOrderDefaults defaults,
			@NonNull final OLCandAggregation aggregationInfo,
			final int userInChangeId)
	{
		Check.assume(id > 0, "id > 0");
		Check.assume(userInChangeId > 0, "userInChangeId > 0");

		this.id = id;
		this.defaults = defaults;
		this.aggregationInfo = aggregationInfo;
		this.userInChangeId = userInChangeId;
	}

}
