/*
 * #%L
 * de-metas-camel-pcm-file-import
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

package de.metas.camel.externalsystems.pcm.warehouse.model;

import lombok.Getter;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", skipField = true)
@Getter
public class WarehouseRow
{
	@DataField(pos = 2)
	private String warehouseIdentifier;

	@DataField(pos = 3)
	private String warehouseValue;

	@DataField(pos = 4)
	private String name;

	@DataField(pos = 5)
	private String address1;

	@DataField(pos = 6)
	private String postal;

	@DataField(pos = 7)
	private String city;

	@DataField(pos = 8)
	private String gln;
}
