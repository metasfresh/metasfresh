package de.metas.dist.ait.sales.order;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.metas.dist.ait.Field;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * #%L
 * metasfresh-dist-ait
 * %%
 * Copyright (C) 2017 metas GmbH
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

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class SalesOrder
{
	final int windowId;
	final int id;
	final Map<String, Field> fieldsByName;
}
