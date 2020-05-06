package de.metas.document;

import javax.annotation.Nullable;

import org.compiere.util.Env;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class DocTypeQuery
{
	public static final String DOCSUBTYPE_Any = "DOCSUBTYPE_Any";
	public static final String DOCSUBTYPE_NONE = null;

	@NonNull
	String docBaseType;

	@Nullable
	@Default
	String docSubType = DOCSUBTYPE_Any;

	@NonNull
	Integer adClientId;

	/** Even if specified, the system will still try to fallback to {@code AD_Org_ID=0} if there is no doctype with a matching org-id. */
	@Default
	int adOrgId = Env.CTXVALUE_AD_Org_ID_System;

	@Nullable
	Boolean isSOTrx;

	@Nullable
	String name;
}
