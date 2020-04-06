package de.metas.ui.web.view.json;

import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public enum JSONViewDataType
{
	/** Side list data */
	list(Characteristic.SideListField),

	/** Grid layout data */
	grid(Characteristic.GridViewField),

	/** Included view layout */
	includedView(null);

	private final Characteristic requiredFieldCharacteristic;

	private JSONViewDataType(final Characteristic requiredFieldCharacteristic)
	{
		this.requiredFieldCharacteristic = requiredFieldCharacteristic;
	}

	public Characteristic getRequiredFieldCharacteristic()
	{
		return requiredFieldCharacteristic;
	}
}
