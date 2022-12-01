/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bom;

import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/bom/version" })
@RestController
public class DeprecatedProductBOMRestController extends ProductBOMRestController
{
	public DeprecatedProductBOMRestController(@NonNull final BOMRestService bomRestService)
	{
		super(bomRestService);
	}
}
