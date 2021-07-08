/*
 * #%L
 * de-metas-common-procurement
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

package de.metas.common.procurement.sync.protocol.dto;

public interface IConfirmableDTO
{
	String getUuid();

	boolean isDeleted();

	IConfirmableDTO withNotDeleted();

	/**
	 * If greater than zero, then the sender requests a confirmation, using this ID.<br>
	 * The confirmation shall be generated using {@link SyncConfirmation#forConfirmId(long)}, using the ID returned by this method.
	 *
	 * task https://metasfresh.atlassian.net/browse/FRESH-206
	 */
	long getSyncConfirmationId();
}
