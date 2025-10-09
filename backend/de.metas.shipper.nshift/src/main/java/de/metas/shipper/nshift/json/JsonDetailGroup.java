/*
 * #%L
 * de.metas.shipper.gateway.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.nshift.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "GroupID")
@JsonSubTypes({
		@JsonSubTypes.Type(value = JsonUnknown.class, name = "0"),
		@JsonSubTypes.Type(value = JsonCustomsArticleInfo.class, name = "1"),
		@JsonSubTypes.Type(value = JsonCustomsInfo.class, name = "2"),
		@JsonSubTypes.Type(value = JsonFedExCustomsInformation.class, name = "4"),
		@JsonSubTypes.Type(value = JsonDHLFiling.class, name = "5"),
		@JsonSubTypes.Type(value = JsonOrderData.class, name = "6"),
})
@Getter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class JsonDetailGroup
{

}