/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.blockstatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_C_BPartner_BlockStatus;

import javax.annotation.Nullable;

@Value
public class BPartnerBlockStatusId implements RepoIdAware
{
	@JsonCreator
	public static BPartnerBlockStatusId ofRepoId(final int repoId)
	{
		return new BPartnerBlockStatusId(repoId);
	}

	@Nullable
	public static BPartnerBlockStatusId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new BPartnerBlockStatusId(repoId) : null;
	}

	public static int getRepoId(@Nullable final BPartnerBlockStatusId bPartnerBlockStatusId)
	{
		return bPartnerBlockStatusId != null ? bPartnerBlockStatusId.getRepoId() : -1;
	}

	int repoId;

	private BPartnerBlockStatusId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_BlockStatus_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
