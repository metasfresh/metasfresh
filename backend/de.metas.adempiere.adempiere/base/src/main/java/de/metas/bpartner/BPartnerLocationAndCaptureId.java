/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner;

import de.metas.location.LocationId;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class BPartnerLocationAndCaptureId
{
	@NonNull
	BPartnerLocationId bpartnerLocationId;

	@Nullable
	LocationId locationCaptureId;

	private BPartnerLocationAndCaptureId(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@Nullable final LocationId locationCaptureId)
	{
		this.bpartnerLocationId = bpartnerLocationId;
		this.locationCaptureId = locationCaptureId;
	}

	public static BPartnerLocationAndCaptureId of(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@Nullable final LocationId locationCaptureId)
	{
		return new BPartnerLocationAndCaptureId(bpartnerLocationId, locationCaptureId);
	}

	public static BPartnerLocationAndCaptureId of(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return new BPartnerLocationAndCaptureId(bpartnerLocationId, null);
	}

	public static BPartnerLocationAndCaptureId ofRepoId(
			final int bpartnerRepoId,
			final int bpartnerLocationRepoId)
	{
		return ofRepoId(bpartnerRepoId, bpartnerLocationRepoId, -1);
	}

	public static BPartnerLocationAndCaptureId ofRepoId(
			final int bpartnerRepoId,
			final int bpartnerLocationRepoId,
			final int locationCaptureRepoId)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerRepoId, bpartnerLocationRepoId);
		final LocationId locationCaptureId = LocationId.ofRepoIdOrNull(locationCaptureRepoId);
		return new BPartnerLocationAndCaptureId(bpartnerLocationId, locationCaptureId);
	}

	public static Optional<BPartnerLocationAndCaptureId> optionalOfRepoId(
			final int bpartnerRepoId,
			final int bpartnerLocationRepoId,
			final int locationCaptureRepoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(bpartnerRepoId, bpartnerLocationRepoId, locationCaptureRepoId));
	}

	@Nullable
	public static BPartnerLocationAndCaptureId ofRepoIdOrNull(
			final int bpartnerRepoId,
			final int bpartnerLocationRepoId)
	{
		return ofRepoIdOrNull(bpartnerRepoId, bpartnerLocationRepoId, -1);
	}

	@Nullable
	public static BPartnerLocationAndCaptureId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			final int bpartnerLocationRepoId)
	{
		return ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId, -1);
	}

	@Nullable
	public static BPartnerLocationAndCaptureId ofRepoIdOrNull(
			final int bpartnerRepoId,
			final int bpartnerLocationRepoId,
			final int locationCaptureRepoId)
	{
		return ofRepoIdOrNull(BPartnerId.ofRepoIdOrNull(bpartnerRepoId), bpartnerLocationRepoId, locationCaptureRepoId);
	}

	@Nullable
	public static BPartnerLocationAndCaptureId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			final int bpartnerLocationRepoId,
			final int locationCaptureRepoId)
	{
		if (bpartnerId == null)
		{
			return null;
		}

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId);
		if (bpartnerLocationId == null)
		{
			return null;
		}

		final LocationId locationCaptureId = LocationId.ofRepoIdOrNull(locationCaptureRepoId);

		return new BPartnerLocationAndCaptureId(bpartnerLocationId, locationCaptureId);
	}

	public static BPartnerLocationAndCaptureId ofRecord(@NonNull final I_C_BPartner_Location record)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID());
		final LocationId locationCaptureId = LocationId.ofRepoIdOrNull(record.getC_Location_ID());
		return new BPartnerLocationAndCaptureId(bpartnerLocationId, locationCaptureId);
	}

	public static BPartnerLocationAndCaptureId ofNullableLocationWithUnknownCapture(@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		return bpartnerLocationId != null
				? new BPartnerLocationAndCaptureId(bpartnerLocationId, null)
				: null;
	}

	public static boolean equals(@Nullable BPartnerLocationAndCaptureId o1, @Nullable BPartnerLocationAndCaptureId o2)
	{
		return Objects.equals(o1, o2);
	}

	public BPartnerId getBpartnerId()
	{
		return getBpartnerLocationId().getBpartnerId();
	}

	public int getBpartnerRepoId()
	{
		return getBpartnerLocationId().getBpartnerId().getRepoId();
	}

	public int getBPartnerLocationRepoId()
	{
		return getBpartnerLocationId().getRepoId();
	}

	public static int toBPartnerLocationRepoId(@Nullable BPartnerLocationAndCaptureId value)
	{
		return value != null ? value.getBPartnerLocationRepoId() : -1;
	}

	public int getLocationCaptureRepoId()
	{
		return LocationId.toRepoId(getLocationCaptureId());
	}
}
