/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl;

public interface LeichMehlConstants
{
	String ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT = "ExportPPOrderRouteContext";

	String XML_ELEMENT_RI = "ri";
	String XML_ELEMENT_RECV_PLU = "recvPLU";

	String XML_PROPERTY_FILE_ENCODING_VALUE = "Windows-1252";
	String XML_PROPERTY_VALUE_YES = "yes";

	String NODE_ATTRIBUTE_TYPE = "xsi:type";
	String NODE_ATTRIBUTE_NAME = "name";

	String ELEMENT_TAG_PRINT_OBJECTS = "printObjects";
	String ELEMENT_TAG_PRINT_OBJECT = "printObject";

	String ATTRIBUTE_TARGET_ELEMENT_DATA = "data";
	String ATTRIBUTE_TARGET_ELEMENT_FORMAT = "format";
	String ATTRIBUTE_TARGET_ELEMENT_TEMPLATE = "template";

	String AD_PINSTANCE_TABLE_NAME = "AD_PInstance";
}
