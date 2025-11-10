/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class TUPickingTarget
{
	@NonNull String id;
	@NonNull String caption;

	//
	// New TU
	@Nullable HuPackingInstructionsId tuPIId;
	boolean isDefaultPacking;

	//
	// Existing TU
	@Nullable HuId tuId;
	@Nullable HUQRCode tuQRCode;

	@Builder(toBuilder = true)
	private TUPickingTarget(
			@NonNull final String caption,
			@Nullable final HuPackingInstructionsId tuPIId,
			final boolean isDefaultPacking,
			@Nullable HuId tuId,
			@Nullable HUQRCode tuQRCode)
	{
		this.caption = caption;

		if (tuId != null)
		{
			this.tuPIId = null;
			this.isDefaultPacking = false;
			this.tuId = tuId;
			this.tuQRCode = tuQRCode;
			this.id = "existing-" + tuId.getRepoId();
		}
		else if (tuPIId != null)
		{
			this.tuPIId = tuPIId;
			this.isDefaultPacking = isDefaultPacking;
			this.tuId = null;
			this.tuQRCode = null;
			this.id = "new-" + tuPIId.getRepoId();
		}
		else
		{
			throw new AdempiereException("Invalid picking target");
		}
	}

	public static boolean equals(@Nullable final TUPickingTarget o1, @Nullable final TUPickingTarget o2)
	{
		return Objects.equals(o1, o2);
	}

	@NonNull
	public static TUPickingTarget ofPackingInstructions(@NonNull final HuPackingInstructionsId tuPIId, @NonNull final String caption)
	{
		return builder().tuPIId(tuPIId).caption(caption).build();
	}

	public static TUPickingTarget ofExistingHU(@NonNull final HuId luId, @NonNull final HUQRCode qrCode)
	{
		return builder().tuId(luId).tuQRCode(qrCode).caption(qrCode.toDisplayableQRCode()).build();
	}

	public boolean isExistingTU()
	{
		return tuId != null;
	}

	public boolean isNewTU()
	{
		return tuId == null && tuPIId != null;
	}

	public HuPackingInstructionsId getTuPIIdNotNull()
	{
		return Check.assumeNotNull(tuPIId, "TU PI shall be set for {}", this);
	}

	public HuId getTuIdNotNull()
	{
		return Check.assumeNotNull(tuId, "TU shall be set for {}", this);
	}

}
