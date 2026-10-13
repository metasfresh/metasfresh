/*
 * #%L
 * de.metas.business
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

package org.adempiere.mm.attributes.api;

import de.metas.document.sequence.DocSequenceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder
public class LotNoContext
{
	@NonNull
	DocSequenceId sequenceId;

	@NonNull
	ClientId clientId;

	/** The PP_Order this lot number is generated for; exposed to a {@link de.metas.document.sequenceno.CustomSequenceNoProvider} via the eval-context {@code Record_ID}. */
	@Nullable
	PPOrderId ppOrderId;

}
