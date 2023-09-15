package de.metas.acct.api;

import de.metas.acct.api.impl.AcctSchemaElementId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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

@Getter
@EqualsAndHashCode
@ToString
public class AcctSchemaElement
{
	@NonNull final AcctSchemaId acctSchemaId;
	@NonNull final AcctSchemaElementType elementType;
	@NonNull final String name;
	final int seqNo;
	final int defaultValue;
	final @NonNull OrgId OrgId;
	final @NonNull String displayColumnName;
	final boolean mandatory;
	final boolean displayedInEditor;
	final boolean balanced;

	@Nullable
	@Setter
	AcctSchemaElementId id;
	@Setter
	@Nullable ChartOfAccountsId chartOfAccountsId;

	@Builder
	public AcctSchemaElement(@NonNull final AcctSchemaElementType elementType,
			@NonNull final String name,
			final int seqNo,
			final int defaultValue,
			final ChartOfAccountsId chartOfAccountsId,
			@NonNull final String displayColumnName,
			final boolean mandatory,
			final boolean displayedInEditor,
			final boolean balanced,
			final AcctSchemaElementId id,
			final AcctSchemaId acctSchemaId,
			final OrgId OrgId)
	{
		this.elementType = elementType;
		this.name = name;
		this.seqNo = seqNo;
		this.defaultValue = defaultValue;
		this.chartOfAccountsId = chartOfAccountsId;
		this.displayColumnName = displayColumnName;
		this.mandatory = mandatory;
		this.displayedInEditor = displayedInEditor;
		this.balanced = balanced;
		this.id = id;
		this.acctSchemaId = acctSchemaId;
		this.OrgId = OrgId;
	}

	public String getColumnName()
	{
		return getElementType().getColumnName();
	}
}
