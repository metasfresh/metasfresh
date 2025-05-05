/*
 * #%L
 * ext-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package com.adekia.exchange.metasfresh.ctx;

import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.context.CtxProvider;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = "ctx", name = "provider", havingValue = "metasfresh")
public class MetasfreshCtxProviderImpl implements CtxProvider
{
	public Ctx getCtx(final Object ctx) throws Exception {
		JsonExternalSystemRequest request = (JsonExternalSystemRequest) ctx;
		Map<String, String> parameters = (request==null) ? new HashMap<String,String>() : request.getParameters();

		return Ctx.builder()
				.properties(parameters)
				.build();
	}
}
