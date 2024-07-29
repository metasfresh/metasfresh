/*
 * #%L
 * de-metas-common-util
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

package de.metas.common.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleSequenceTest
{

	@Test
	void create()
	{
		final SimpleSequence simpleSequence = SimpleSequence.create();
		Assertions.assertThat(simpleSequence.next()).isEqualTo(10);
		Assertions.assertThat(simpleSequence.next()).isEqualTo(20);
	}

	@Test
	void createWithInitial()
	{
		final SimpleSequence simpleSequence = SimpleSequence.createWithInitial(20);
		Assertions.assertThat(simpleSequence.next()).isEqualTo(30);
		Assertions.assertThat(simpleSequence.next()).isEqualTo(40);
	}
}