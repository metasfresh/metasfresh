/*
 * #%L
 * de.metas.swat.base
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

package de.metas.shippingnotification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
public class ShippingNotificationId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ShippingNotificationId ofRepoId(final int repoId)
	{
		return new ShippingNotificationId(repoId);
	}

	@Nullable
	public static ShippingNotificationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private ShippingNotificationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ShippingNotificationId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public TableRecordReference toTableRecordReference() {return TableRecordReference.of(I_M_Shipping_Notification.Table_Name, this);}
}
