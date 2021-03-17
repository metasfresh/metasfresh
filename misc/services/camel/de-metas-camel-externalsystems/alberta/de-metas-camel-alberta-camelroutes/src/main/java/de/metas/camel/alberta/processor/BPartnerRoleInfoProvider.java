/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta.processor;

import de.metas.common.bprelation.JsonBPRelationRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
@Builder
public class BPartnerRoleInfoProvider
{
	@NonNull
	String sourceBPartnerIdentifier;

	@NonNull
	String sourceBPartnerLocationIdentifier;

	@NonNull
	@Getter(AccessLevel.PRIVATE)
	Map<String, JsonBPRelationRole> bpIdentifier2Role;

	@NonNull
	public Optional<JsonBPRelationRole> getRoleByBPIdentifier(@NonNull final String bPartnerIdentifier)
	{
		return Optional.ofNullable(bpIdentifier2Role.get(bPartnerIdentifier));
	}
}
