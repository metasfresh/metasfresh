package de.metas.error;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_Issue;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
@RequiredArgsConstructor
public enum IssueCategory implements ReferenceListAwareEnum
{
	ACCOUNTING(X_AD_Issue.ISSUECATEGORY_Accounting),
	OTHER(X_AD_Issue.ISSUECATEGORY_Other),
	MOBILE_UI(X_AD_Issue.ISSUECATEGORY_MOBILEUI),
	;

	public static final int AD_REFERENCE_ID = X_AD_Issue.ISSUECATEGORY_AD_Reference_ID;

	@NonNull private final String code;

	private static final ValuesIndex<IssueCategory> index = ReferenceListAwareEnums.index(values());

	public static IssueCategory ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static IssueCategory ofNullableCodeOrOther(@Nullable final String code)
	{
		final IssueCategory type = index.ofNullableCode(code);
		return type != null ? type : OTHER;
	}
}
