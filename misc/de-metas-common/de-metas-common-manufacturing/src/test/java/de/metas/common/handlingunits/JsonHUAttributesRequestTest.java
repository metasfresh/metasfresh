/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.handlingunits;

import de.metas.common.JsonTestHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class JsonHUAttributesRequestTest
{
	@Test
	void testSerializeDeserialize()
	{
		final JsonHUAttributes attributes = new JsonHUAttributes();
		attributes.putAttribute("stringAttribute", "string value");
		attributes.putAttribute("integerAttribute", 123);
		attributes.putAttribute("numberAttribute", new BigDecimal("123.45"));
		attributes.putAttribute("booleanAttribute", true);

		JsonTestHelper.testSerializeDeserialize(
				JsonHUAttributesRequest.builder()
						.huId("1")
						.attributes(attributes)
						.build()
		);
	}
}
