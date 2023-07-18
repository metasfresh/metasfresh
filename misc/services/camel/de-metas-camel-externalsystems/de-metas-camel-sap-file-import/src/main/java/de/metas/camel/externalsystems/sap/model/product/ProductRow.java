/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.sap.model.product;

import lombok.Getter;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "	", skipField =true)
@Getter
public class ProductRow
{
	@DataField(pos = 1)
	private String materialCode;

	@DataField(pos = 3)
	private String sectionCode;

	@DataField(pos = 4)
	private String name;

	@DataField(pos = 5)
	private String uom;

	@DataField(pos = 6)
	private String productHierarchy;

	@DataField(pos = 9)
	private String description;

	@DataField(pos = 13)
	private String materialType;

	@DataField(pos = 14)
	private String warehouseName;

	@DataField(pos = 17)
	private String materialGroup;
}
