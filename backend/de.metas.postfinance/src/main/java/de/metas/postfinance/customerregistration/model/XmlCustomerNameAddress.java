/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.customerregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record XmlCustomerNameAddress(
		@JacksonXmlProperty(localName = "NamePrivate") @JsonInclude(JsonInclude.Include.NON_NULL) XmlNamePrivate namePrivate,
		@JacksonXmlProperty(localName = "NameCompany") @JsonInclude(JsonInclude.Include.NON_NULL) XmlNameCompany nameCompany,
		@JacksonXmlProperty(localName = "Address") String address,
		@JacksonXmlProperty(localName = "Zip") String zip,
		@JacksonXmlProperty(localName = "City") String city,
		@JacksonXmlProperty(localName = "Country") String country)
{
}
