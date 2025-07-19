/*
 * #%L
 * de-metas-camel-scripting
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scripting;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MsgFromMfContext
{
	@NonNull private final String scriptingRequestBody;
	@NonNull private final String scriptIdentifier;

	private String script;
	private String scriptReturnValue;

	// TODO These three shall soon be replaced with a config- or request-object for de-metas-camel-outbound-endpoints  
	@NonNull private final String outboundHttpEP;
	@NonNull private final String outboundHttpToken;
	@NonNull private final String outboundHttpMethod;
}
