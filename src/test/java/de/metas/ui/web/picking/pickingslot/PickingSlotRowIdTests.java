package de.metas.ui.web.picking.pickingslot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.ui.web.window.datatypes.DocumentId;

/*
 * #%L
 * metasfresh-webui-api
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

public class PickingSlotRowIdTests
{
	/**
	 * Create a picking slot row ID that has no actual picking slot
	 */
	@Test
	public void testNoPickingSlotId()
	{
		final PickingSlotRowId rowId = PickingSlotRowId.ofSourceHU(2);
		assertThat(rowId.getPickingSlotId()).isLessThanOrEqualTo(0);
		assertThat(rowId.getHuId()).isEqualTo(2);
		assertThat(rowId.getHuStorageProductId()).isLessThanOrEqualTo(0);
	}

	@Test
	public void testOPfPickedHU()
	{
		final PickingSlotRowId rowId = PickingSlotRowId.ofPickedHU(1, 2, 3);
		assertThat(rowId.getPickingSlotId()).isEqualTo(1);
		assertThat(rowId.getHuId()).isEqualTo(2);
		assertThat(rowId.getHuStorageProductId()).isEqualTo(3);
	}

	/**
	 * Tests the conversion between {@link DocumentId} and {@link PickingSlotRowId} in case of a Picking Slot row.
	 */
	@Test
	public void testFromDocumentId_PickingSlot()
	{
		final DocumentId documentId = PickingSlotRowId.ofPickingSlotId(123).toDocumentId();
		assertThat(documentId.toJson()).isEqualTo("123");

		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(documentId);
		assertThat(rowId.toDocumentId()).isEqualTo(documentId);
		
		assertThat(rowId.getPickingSlotId()).isEqualTo(123);
		assertThat(rowId.getHuId()).isLessThanOrEqualTo(0);
		assertThat(rowId.getHuStorageProductId()).isLessThanOrEqualTo(0);
		assertIsPickingSlotRow(rowId);
	}

	/**
	 * Tests the conversion between {@link DocumentId} and {@link PickingSlotRowId} in case of a picked HU.
	 */
	@Test
	public void testFromDocumentId_HU()
	{
		final DocumentId documentId = PickingSlotRowId.ofPickedHU(123, 456, -111).toDocumentId();
		assertThat(documentId.toJson()).isEqualTo("123-456");

		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(documentId);
		assertThat(rowId.toDocumentId()).isEqualTo(documentId);

		assertThat(rowId.getPickingSlotId()).isEqualTo(123);
		assertThat(rowId.getHuId()).isEqualTo(456);
		assertThat(rowId.getHuStorageProductId()).isLessThanOrEqualTo(0);
		assertIsPickedHURow(rowId);
	}

	/**
	 * Tests the conversion between {@link DocumentId} and {@link PickingSlotRowId} in case of a picked HU storage.
	 */
	@Test
	public void testFromDocumentId_HUStorage()
	{
		final DocumentId documentId = PickingSlotRowId.ofPickedHU(123, 456, 789).toDocumentId();
		assertThat(documentId.toJson()).isEqualTo("123-456-789");

		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(documentId);
		assertThat(rowId.toDocumentId()).isEqualTo(documentId);

		assertThat(rowId.getPickingSlotId()).isEqualTo(123);
		assertThat(rowId.getHuId()).isEqualTo(456);
		assertThat(rowId.getHuStorageProductId()).isEqualTo(789);
		assertIsPickedHURow(rowId);
	}

	/**
	 * Tests the conversion between {@link DocumentId} and {@link PickingSlotRowId} in case of a source-HU row (which has no picking slot ID).
	 */
	@Test
	public void testFromDocumentId_SourceHU()
	{
		final DocumentId documentId = PickingSlotRowId.ofSourceHU(18052595).toDocumentId();
		assertThat(documentId.toJson()).isEqualTo("0-18052595");

		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(documentId);
		assertThat(rowId.toDocumentId()).isEqualTo(documentId);

		assertThat(rowId.getPickingSlotId()).isLessThanOrEqualTo(0);
		assertThat(rowId.getHuId()).isEqualTo(18052595);
		assertThat(rowId.getHuStorageProductId()).isLessThanOrEqualTo(0);
		assertIsPickingSourceHURow(rowId);
	}

	private static final void assertIsPickingSlotRow(final PickingSlotRowId rowId)
	{
		assertThat(rowId.isPickingSlotRow()).isTrue();
		assertThat(rowId.isPickedHURow()).isFalse();
		assertThat(rowId.isPickingSourceHURow()).isFalse();
	}

	private static final void assertIsPickedHURow(final PickingSlotRowId rowId)
	{
		assertThat(rowId.isPickingSlotRow()).isFalse();
		assertThat(rowId.isPickedHURow()).isTrue();
		assertThat(rowId.isPickingSourceHURow()).isFalse();
	}

	private static final void assertIsPickingSourceHURow(final PickingSlotRowId rowId)
	{
		assertThat(rowId.isPickingSlotRow()).isFalse();
		assertThat(rowId.isPickedHURow()).isFalse();
		assertThat(rowId.isPickingSourceHURow()).isTrue();
	}

}
