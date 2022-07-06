/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.ConnectionDetails;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ExportPPOrderRouteContext
{
	@NonNull
	private final JsonExternalSystemRequest jsonExternalSystemRequest;

	@NonNull
	private final ConnectionDetails connectionDetails;

	@NonNull
	private final String productBaseFolderName;

	@NonNull
	private final JsonExternalSystemLeichMehlConfigProductMapping productMapping;
}
