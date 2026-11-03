package de.metas.material.dispo.commons.reconcile;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class SourceDocumentStatusTest
{
	@Test
	public void testStillOpenIsContributingToAtp()
	{
		assertThat(SourceDocumentStatus.STILL_OPEN.isContributingToAtp()).isTrue();
	}

	@Test
	public void testClosedIsNotContributingToAtp()
	{
		assertThat(SourceDocumentStatus.CLOSED.isContributingToAtp()).isFalse();
	}

	@Test
	public void testNoSourceDocumentIsNotContributingToAtp()
	{
		assertThat(SourceDocumentStatus.NO_SOURCE_DOCUMENT.isContributingToAtp()).isFalse();
	}

	/**
	 * Guards the implicit default in {@link SourceDocumentStatus#isContributingToAtp()}, which is
	 * {@code this == STILL_OPEN} — i.e. any NEW constant silently becomes non-contributing. Concrete
	 * scenario this prevents: a fourth status is added for the unreliable-era date cutoff, nobody adds a
	 * test for it, and it is silently treated as "does not contribute to ATP" with every other test green.
	 * This test fails on any added or renamed constant, forcing an explicit decision for it.
	 */
	@Test
	public void testEveryConstantIsClassifiedDeliberately()
	{
		assertThat(SourceDocumentStatus.values())
				.containsExactly(
						SourceDocumentStatus.STILL_OPEN,
						SourceDocumentStatus.CLOSED,
						SourceDocumentStatus.NO_SOURCE_DOCUMENT);

		assertThat(Arrays.stream(SourceDocumentStatus.values())
						.filter(SourceDocumentStatus::isContributingToAtp))
				.containsExactly(SourceDocumentStatus.STILL_OPEN);
	}
}
