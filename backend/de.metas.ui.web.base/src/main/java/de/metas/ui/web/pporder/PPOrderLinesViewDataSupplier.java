package de.metas.ui.web.pporder;

import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.ui.web.view.ASIViewRowAttributesProvider;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderLinesViewDataSupplier
{
	private final ASIViewRowAttributesProvider asiAttributesProvider;
	private final ExtendedMemorizingSupplier<PPOrderLinesViewData> dataSupplier;

	@Builder
	private PPOrderLinesViewDataSupplier(
			@NonNull final WindowId viewWindowId,
			@NonNull final PPOrderId ppOrderId,
			@Nullable final ASIViewRowAttributesProvider asiAttributesProvider,
			@NonNull final SqlViewBinding huSQLViewBinding,
			@NonNull final HUReservationService huReservationService)
	{
		this.asiAttributesProvider = asiAttributesProvider;
		dataSupplier = ExtendedMemorizingSupplier
				.of(() -> PPOrderLinesViewDataLoader
						.builder(viewWindowId)
						.asiAttributesProvider(asiAttributesProvider)
						.huSQLViewBinding(huSQLViewBinding)
						.huReservationService(huReservationService)
						.build()
						.retrieveData(ppOrderId));
	}

	public PPOrderLinesViewData getData()
	{
		return dataSupplier.get();
	}

	public void invalidate()
	{
		dataSupplier.forget();
		if (asiAttributesProvider != null)
		{
			asiAttributesProvider.invalidateAll();
		}
	}
}
