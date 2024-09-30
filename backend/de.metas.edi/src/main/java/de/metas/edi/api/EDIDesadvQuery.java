/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.edi.api;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.IContextAware;

import javax.annotation.Nullable;

@Value
public class EDIDesadvQuery
{
	@NonNull String poReference;
	@NonNull IContextAware ctxAware;
	@Nullable
	BPartnerId bPartnerId;
	@Nullable
	OrderId orderId;

	@Builder
	public EDIDesadvQuery(
			@NonNull final String poReference,
			@NonNull final IContextAware ctxAware,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final OrderId orderId)
	{
		Check.assumeNotEmpty(poReference, "Param 'poReference' is not emtpy; ctxAware={}", ctxAware);

		this.poReference = poReference;
		this.bPartnerId = bPartnerId;
		this.ctxAware = ctxAware;
		this.orderId = orderId;
	}
}
