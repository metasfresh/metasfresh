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

package de.metas.handlingunits.reservation.handleordervoid;

public enum HandlerResult
{
	/**
	 * The handler took no action.
	 */
	NOT_HANDELED,

	/**
	 * The handler took action and believes that no other handler would have any business with the given order.
	 */
	HANDELED_DONE,

	/**
	 * The handler took action, but wouldn't mind if another handler also took a shot at the given order.
	 */
	HANDELED_CONTINUE
}
