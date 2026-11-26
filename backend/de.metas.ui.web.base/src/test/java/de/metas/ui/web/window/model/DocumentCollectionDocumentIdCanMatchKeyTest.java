/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins which document ids are answered as "not found" before they reach the SQL layer.
 */
class DocumentCollectionDocumentIdCanMatchKeyTest
{
	// Signature reminder (positional):
	// (hasSingleIdField, singleIdFieldIsStringTyped, documentIdIsInt)

	@Nested
	class DocumentIdCanMatchKey
	{
		/**
		 * The case that reaches production: an ordinary integer-keyed window asked for a non-numeric id. The
		 * frontend sends both a {@code notfound} sentinel and, when a route parameter is unset, the literal
		 * {@code undefined}; neither can match an integer primary key.
		 */
		@Test
		void rejectsANonNumericIdOnAnIntegerKeyedEntity()
		{
			assertThat(DocumentCollection.documentIdCanMatchKey(true, false, false)).isFalse();
		}

		@Test
		void acceptsANumericIdOnAnIntegerKeyedEntity()
		{
			assertThat(DocumentCollection.documentIdCanMatchKey(true, false, true)).isTrue();
		}

		/** A string-typed key column takes a non-numeric id legitimately. */
		@Test
		void acceptsANonNumericIdOnAStringKeyedEntity()
		{
			assertThat(DocumentCollection.documentIdCanMatchKey(true, true, false)).isTrue();
		}

		/** A composed key is out of scope — the single-key SQL path is the one that converts to int. */
		@Test
		void acceptsANonNumericIdOnAComposedKeyEntity()
		{
			assertThat(DocumentCollection.documentIdCanMatchKey(false, false, false)).isTrue();
		}

		/** A numeric id is always addressable, whatever the key shape. */
		@Test
		void acceptsANumericIdRegardlessOfKeyShape()
		{
			assertThat(DocumentCollection.documentIdCanMatchKey(false, false, true)).isTrue();
			assertThat(DocumentCollection.documentIdCanMatchKey(true, true, true)).isTrue();
		}
	}
}
