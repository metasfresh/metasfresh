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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluRootElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

public class XMLPluRootElementTest
{
	@Test
	public void test()
	{
		final XMLPluElement element = XMLPluElement.builder()
				.content("element")
				.build();

		final XMLPluRootElement root = XMLPluRootElement.builder()
				.xmlPluElement(element)
				.build();

		final byte[] data = SerializationUtils.serialize(root);
		final Object deserializedRoot = SerializationUtils.deserialize(data);

		Assertions.assertThat(root).isEqualTo(deserializedRoot);
	}
}
