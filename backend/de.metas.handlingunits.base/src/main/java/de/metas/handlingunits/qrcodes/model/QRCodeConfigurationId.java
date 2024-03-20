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

package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class QRCodeConfigurationId implements RepoIdAware
{
	@JsonCreator
	public static QRCodeConfigurationId ofRepoId(final int repoId)
	{
		return new QRCodeConfigurationId(repoId);
	}

	public static QRCodeConfigurationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new QRCodeConfigurationId(repoId) : null;
	}

	@NonNull
	public static Optional<QRCodeConfigurationId> ofRepoIdOptional(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private QRCodeConfigurationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "QRCode_Configuration_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final QRCodeConfigurationId qrCodeConfigurationId)
	{
		return qrCodeConfigurationId != null ? qrCodeConfigurationId.getRepoId() : -1;
	}
}
