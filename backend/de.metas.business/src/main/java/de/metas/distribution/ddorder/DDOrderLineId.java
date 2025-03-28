/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.distribution.ddorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;

@Value
public class DDOrderLineId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderLineId ofRepoId(final int repoId)
	{
		return new DDOrderLineId(repoId);
	}

	@Nullable
	public static DDOrderLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DDOrderLineId(repoId) : null;
	}

	private DDOrderLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static int toRepoId(@Nullable final DDOrderLineId id) {return id != null ? id.getRepoId() : -1;}

	public TableRecordReference toTableRecordReference() {return TableRecordReference.of(I_DD_OrderLine.Table_Name, repoId);}
}
