package de.metas.email;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_InvoiceRejection;
import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_MassDunning;
import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_OrgCompiereUtilLogin;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Getter
@AllArgsConstructor
public enum EMailCustomType implements ReferenceListAwareEnum
{
	OrgCompiereUtilLogin(CUSTOMTYPE_OrgCompiereUtilLogin),
	InvoiceRejection(CUSTOMTYPE_InvoiceRejection),
	MassDunning(CUSTOMTYPE_MassDunning);

	private final String code;

	private static final ImmutableMap<String, EMailCustomType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	@Nullable
	public static EMailCustomType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static EMailCustomType ofCode(@NonNull final String code)
	{
		final EMailCustomType eMailCustomType = typesByCode.get(code);
		if (eMailCustomType == null)
		{
			throw Check.mkEx("No " + EMailCustomType.class + " found for code: " + code);
		}
		return eMailCustomType;
	}

}
