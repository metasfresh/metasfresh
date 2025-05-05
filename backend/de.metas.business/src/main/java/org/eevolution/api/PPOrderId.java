/*
 * #%L
 * de.metas.business
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

package org.eevolution.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPOrderId implements RepoIdAware
{
	@JsonCreator
	public static PPOrderId ofRepoId(final int repoId)
	{
		return new PPOrderId(repoId);
	}

	public static PPOrderId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PPOrderId(repoId) : null;
	}

	public static Optional<PPOrderId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final PPOrderId PPOrderId)
	{
		return PPOrderId != null ? PPOrderId.getRepoId() : -1;
	}

	int repoId;

	private PPOrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public TableRecordReference toRecordRef() {return TableRecordReference.of(I_PP_Order.Table_Name, this);}

	public static PPOrderId ofRecordRef(@NonNull final TableRecordReference recordRef) {return recordRef.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId);}
}
