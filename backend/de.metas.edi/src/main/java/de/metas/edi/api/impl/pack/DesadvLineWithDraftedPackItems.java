/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl.pack;

import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.EDIDesadvPackItemId;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Optional;

@Value
@Builder
public class DesadvLineWithDraftedPackItems
{
	@NonNull
	EDIDesadvLineId desadvLineId;

	//dev-note: drafted => EDI_Desadv_Pack_Item.M_InOutLine_ID is null
	@NonNull
	LinkedList<I_EDI_Desadv_Pack_Item> draftedPackItems;

	/**
	 *  The pack is removed from this list, if matched.
	 */
	@NonNull
	public Optional<EDIDesadvPackItemId> popFirstMatching(@NonNull final BigDecimal movementQty)
	{
		for (int i = 0; i < draftedPackItems.size(); i++)
		{
			final I_EDI_Desadv_Pack_Item packItem = draftedPackItems.get(i);
			if (movementQty.compareTo(packItem.getMovementQty()) == 0)
			{
				final EDIDesadvPackItemId packItemId = EDIDesadvPackItemId.ofRepoId(packItem.getEDI_Desadv_Pack_Item_ID());

				draftedPackItems.remove(i);

				return Optional.of(packItemId);
			}
		}

		return Optional.empty();
	}
}
