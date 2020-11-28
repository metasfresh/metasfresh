package de.metas.handlingunits.picking;

import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum PickingCandidatePickStatus implements ReferenceListAwareEnum
{
	TO_BE_PICKED(X_M_Picking_Candidate.PICKSTATUS_ToBePicked), //
	PICKED(X_M_Picking_Candidate.PICKSTATUS_Picked), //
	PACKED(X_M_Picking_Candidate.PICKSTATUS_Packed), //
	WILL_NOT_BE_PICKED(X_M_Picking_Candidate.PICKSTATUS_WillNotBePicked) //
	;

	public static final int AD_REFERENCE_ID = X_M_Picking_Candidate.PICKSTATUS_AD_Reference_ID;

	private static final ValuesIndex<PickingCandidatePickStatus> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private String code;

	private PickingCandidatePickStatus(final String code)
	{
		this.code = code;
	}

	public static PickingCandidatePickStatus ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	private boolean isPicked()
	{
		return PICKED.equals(this);
	}

	private boolean isToBePicked()
	{
		return TO_BE_PICKED.equals(this);
	}

	public boolean isPickRejected()
	{
		return WILL_NOT_BE_PICKED.equals(this);
	}

	private boolean isPacked()
	{
		return PACKED.equals(this);
	}

	public boolean isEligibleForPicking()
	{
		return isToBePicked() || isPickRejected();
	}

	public boolean isEligibleForRejectPicking()
	{
		return !isPickRejected();
	}

	public boolean isEligibleForPacking()
	{
		return !isPickRejected();
	}

	public boolean isEligibleForReview()
	{
		return isPicked() || isPacked() || isPickRejected();
	}

	public boolean isEligibleForProcessing()
	{
		return isPacked() || isPickRejected();
	}

}
