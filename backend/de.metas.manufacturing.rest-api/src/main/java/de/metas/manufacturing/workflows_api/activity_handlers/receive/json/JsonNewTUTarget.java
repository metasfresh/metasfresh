/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.frontend_testing.JsonTestId;
import de.metas.handlingunits.HUPIItemProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

@Value
@Builder
@Jacksonized
public class JsonNewTUTarget
{
	@Nullable String caption;
	@NonNull HUPIItemProductId tuPIItemProductId;
	@Nullable JsonTestId testId;
}
