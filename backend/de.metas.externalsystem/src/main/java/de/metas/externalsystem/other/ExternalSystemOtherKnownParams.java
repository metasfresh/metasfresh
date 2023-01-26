/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum ExternalSystemOtherKnownParams
{
	EXPORT_BUDGET_PROJECT("ExportBudgetProject"),
	EXPORT_WO_PROJECT("ExportWOProject"),
	EXPORT_BPARTNER("ExportBPartner"),
	AUTO_EXPORT_DEFAULT_SHIPPING_ADDRESS("AutoExportDefaultShippingAddress");

	@NonNull
	private final String dbName;
}
