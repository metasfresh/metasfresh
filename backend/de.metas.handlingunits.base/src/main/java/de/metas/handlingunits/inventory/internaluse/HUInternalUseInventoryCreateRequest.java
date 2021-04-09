/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.inventory.internaluse;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inventory.InventoryDocSubType;
import de.metas.product.acct.api.ActivityId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Value
@Builder
public class HUInternalUseInventoryCreateRequest
{
	@NonNull ZonedDateTime movementDate;

	@Nullable
	DocTypeId internalUseInventoryDocTypeId;

	/**
	  NOTE: only the top level HUs are processed to avoid issue <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/578">metasfresh/metasfresh-webui-api#578</a>.
	  Included lower-level HUs are processed recursively.
	 */
	@NonNull
	@Singular("hu")
	ImmutableList<I_M_HU> hus;

	@Nullable
	ActivityId activityId;

	@Nullable
	String description;

	@Builder.Default
	boolean completeInventory = true;

	@Builder.Default
	boolean moveEmptiesToEmptiesWarehouse = false;

	@Builder.Default
	boolean sendNotifications = true;
}
