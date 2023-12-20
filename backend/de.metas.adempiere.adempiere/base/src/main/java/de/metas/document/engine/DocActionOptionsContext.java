package de.metas.document.engine;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.security.UserRolePermissionsKey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Data
@Builder
public final class DocActionOptionsContext
{
	@NonNull
	private final UserRolePermissionsKey userRolePermissionsKey;

	@NonNull
	private final String tableName;

	@NonNull
	private final String docStatus;

	// NOTE: we are tolerating null/not set C_DocType_ID because not all of our documents have this column.
	@Nullable
	private final DocTypeId docTypeId;

	private final boolean processing;
	private final String orderType;

	@NonNull
	private final SOTrx soTrx;

	private String docActionToUse;

	@NonNull
	@Default
	private ImmutableSet<String> docActions = ImmutableSet.of();

	@Getter(AccessLevel.NONE)
	@NonNull
	private final IValidationContext validationContext;

	public ClientId getAdClientId()
	{
		return getUserRolePermissionsKey().getClientId();
	}

	public String getParameterValue(final String parameterName)
	{
		return validationContext.get_ValueAsString(parameterName);
	}
}
