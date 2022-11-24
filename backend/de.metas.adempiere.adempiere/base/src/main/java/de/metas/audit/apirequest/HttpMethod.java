/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.apirequest;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.compiere.model.X_API_Audit_Config.METHOD_CONNECT;
import static org.compiere.model.X_API_Audit_Config.METHOD_DELETE;
import static org.compiere.model.X_API_Audit_Config.METHOD_GET;
import static org.compiere.model.X_API_Audit_Config.METHOD_HEAD;
import static org.compiere.model.X_API_Audit_Config.METHOD_OPTIONS;
import static org.compiere.model.X_API_Audit_Config.METHOD_PATCH;
import static org.compiere.model.X_API_Audit_Config.METHOD_POST;
import static org.compiere.model.X_API_Audit_Config.METHOD_PUT;
import static org.compiere.model.X_API_Audit_Config.METHOD_TRACE;

@AllArgsConstructor
@Getter
public enum HttpMethod implements ReferenceListAwareEnum
{
	GET(METHOD_GET),
	POST(METHOD_POST),
	DELETE(METHOD_DELETE),
	PUT(METHOD_PUT),
	PATCH(METHOD_PATCH),
	OPTIONS(METHOD_OPTIONS),
	HEAD(METHOD_HEAD),
	TRACE(METHOD_TRACE),
	CONNECT(METHOD_CONNECT),
	;

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<HttpMethod> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static HttpMethod ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static HttpMethod ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static Optional<HttpMethod> ofCodeOptional(@Nullable final String code) {return Optional.ofNullable(ofNullableCode(code));}

}
