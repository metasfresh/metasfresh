/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.ui.web.handlingunits.process.HUEditorReceiptSources.ReferencedReceiptSource;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * What the HU editor's confirm makes of the row it was launched from.
 * <p>
 * The editor is ONE view reached from more than one window, and until now its confirm read every referencing row
 * as a {@code M_ReceiptSchedule} one. A row of the receipt-disposition delivery-planning window is a
 * {@code RV_ReceiptDisposition_DeliveryPlanning} one, and on a PLANNED row it carries the delivery planning the
 * goods are arriving for - the one piece of provenance the finished receipt has to carry, and the one thing an
 * assumed-away table name silently drops while the receive still reports success.
 * <p>
 * The {@code M_ReceiptSchedule} shape is pinned alongside, because the receipt-schedule window must go on
 * behaving exactly as it does: it resolves to that schedule and to NO planning.
 */
class HUEditorReceiptSourcesTest
{
	/** "Wareneingangsdisposition inkl. Lieferplanung" - the window whose rows are the new provenance. */
	private static final WindowId RECEIPT_DISPOSITION_WINDOW_ID = WindowId.of(542190);

	/** The receipt-schedule window - the only provenance that existed until now. */
	private static final WindowId RECEIPT_SCHEDULE_WINDOW_ID = WindowId.of(540196);

	/** The delivery-planning window - pre-existing, and a launcher of this same editor. */
	private static final WindowId DELIVERY_PLANNING_WINDOW_ID = WindowId.of(541928);

	private IContextAware context;
	private DocumentCollection documentsCollection;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		context = PlainContextAware.newWithThreadInheritedTrx();
		documentsCollection = Mockito.mock(DocumentCollection.class);
	}

	private I_M_ReceiptSchedule createReceiptSchedule()
	{
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule;
	}

	/**
	 * One row of the receipt-disposition delivery-planning view, as the grid produced it: the schedule on both
	 * branches, the planning only on the planned one (the view selects {@code NULL::numeric(10)} there otherwise).
	 */
	private DocumentPath receiptDispositionRow(
			final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		final I_RV_ReceiptDisposition_DeliveryPlanning row =
				InterfaceWrapperHelper.newInstance(I_RV_ReceiptDisposition_DeliveryPlanning.class);
		row.setM_ReceiptSchedule_ID(receiptSchedule.getM_ReceiptSchedule_ID());
		// 0, not -1: NULL::numeric(10) is what the view's unplanned branch selects, and 0 is how the
		// persistence layer hands that back.
		row.setM_Delivery_Planning_ID(deliveryPlanningId != null ? deliveryPlanningId.getRepoId() : 0);
		InterfaceWrapperHelper.save(row);

		return documentPathFor(
				RECEIPT_DISPOSITION_WINDOW_ID,
				TableRecordReference.of(
						I_RV_ReceiptDisposition_DeliveryPlanning.Table_Name,
						row.getRV_ReceiptDisposition_DeliveryPlanning_ID()));
	}

	/**
	 * A launch whose referencing row is the PLANNING record itself. Two launchers produce this shape, which is
	 * why it is not an edge case: the delivery-planning window, whose rows simply ARE {@code M_Delivery_Planning};
	 * and a PLANNED row of the receipt-disposition window, which is keyed on its planning rather than on the view
	 * (see {@code WEBUI_M_HU_CreateReceipt_Base}'s note about the launching window's rows).
	 */
	private DocumentPath deliveryPlanningRow(
			final I_M_ReceiptSchedule receiptSchedule,
			final WindowId launchedFrom)
	{
		final I_M_Delivery_Planning deliveryPlanning = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		deliveryPlanning.setM_ReceiptSchedule_ID(receiptSchedule.getM_ReceiptSchedule_ID());
		InterfaceWrapperHelper.save(deliveryPlanning);

		return documentPathFor(
				launchedFrom,
				TableRecordReference.of(
						I_M_Delivery_Planning.Table_Name,
						deliveryPlanning.getM_Delivery_Planning_ID()));
	}

	/** One row of the receipt-schedule window - today's only shape. */
	private DocumentPath receiptScheduleRow(final I_M_ReceiptSchedule receiptSchedule)
	{
		return documentPathFor(
				RECEIPT_SCHEDULE_WINDOW_ID,
				TableRecordReference.of(I_M_ReceiptSchedule.Table_Name, receiptSchedule.getM_ReceiptSchedule_ID()));
	}

	/**
	 * The one thing the editor gets from its launcher. The mapping documentPath -&gt; record reference is the
	 * platform's ({@code DocumentCollection}); what is under test is what the confirm does with the reference.
	 */
	private DocumentPath documentPathFor(final WindowId windowId, final TableRecordReference recordRef)
	{
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, DocumentId.of(recordRef.getRecord_ID()));
		Mockito.doReturn(recordRef).when(documentsCollection).getTableRecordReference(documentPath);
		return documentPath;
	}

	@Nested
	@DisplayName("resolving the launching view's rows")
	class Resolve
	{
		@Test
		@DisplayName("a PLANNED receipt-disposition row yields its receipt schedule AND its delivery planning")
		void plannedReceiptDispositionRow()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(540020);

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(receiptDispositionRow(receiptSchedule, deliveryPlanningId)));

			assertThat(sources).hasSize(1);
			assertThat(sources.get(0).getReceiptSchedule().getM_ReceiptSchedule_ID())
					.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());
			assertThat(sources.get(0).getDeliveryPlanningId())
					.as("the planning the goods are arriving for - dropping it produces a receipt nothing links back to")
					.isEqualTo(deliveryPlanningId);
		}

		@Test
		@DisplayName("an UNPLANNED receipt-disposition row yields its receipt schedule and NO planning")
		void unplannedReceiptDispositionRow()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(receiptDispositionRow(receiptSchedule, null)));

			assertThat(sources.get(0).getReceiptSchedule().getM_ReceiptSchedule_ID())
					.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());
			assertThat(sources.get(0).getDeliveryPlanningId())
					.as("an unplanned row is a bare receipt schedule - null, not a zero")
					.isNull();
		}

		@Test
		@DisplayName("a PLANNING row from the receipt-disposition window yields its schedule AND itself as the planning")
		void planningRowFromReceiptDispositionWindow()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();
			final DocumentPath row = deliveryPlanningRow(receiptSchedule, RECEIPT_DISPOSITION_WINDOW_ID);

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context, ImmutableList.of(row));

			assertThat(sources).hasSize(1);
			assertThat(sources.get(0).getReceiptSchedule().getM_ReceiptSchedule_ID())
					.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());
			assertThat(sources.get(0).getDeliveryPlanningId())
					.as("the planning the operator received for, so the confirm books against it")
					.isNotNull();
		}

		@Test
		@DisplayName("a PLANNING row from the delivery-planning window resolves too - that window predates this feature")
		void planningRowFromDeliveryPlanningWindow()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();
			final DocumentPath row = deliveryPlanningRow(receiptSchedule, DELIVERY_PLANNING_WINDOW_ID);

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context, ImmutableList.of(row));

			assertThat(sources).hasSize(1);
			assertThat(sources.get(0).getReceiptSchedule().getM_ReceiptSchedule_ID())
					.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());
		}

		@Test
		@DisplayName("a RECEIPT-SCHEDULE row is unchanged: that schedule, and no planning")
		void receiptScheduleRowIsUnchanged()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(receiptScheduleRow(receiptSchedule)));

			assertThat(sources.get(0).getReceiptSchedule().getM_ReceiptSchedule_ID())
					.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());
			assertThat(sources.get(0).getDeliveryPlanningId()).isNull();
		}

		@Test
		@DisplayName("a launch from a window nobody taught it about is refused loudly, never read as a receipt schedule")
		void unknownProvenanceIsRefused()
		{
			final DocumentPath documentPath = documentPathFor(
					WindowId.of(123456),
					TableRecordReference.of("M_Delivery_Planning", 540030));

			assertThatThrownBy(() -> HUEditorReceiptSources.resolve(
					documentsCollection, context, ImmutableList.of(documentPath)))
					.hasMessageContaining("M_Delivery_Planning");
		}
	}

	@Nested
	@DisplayName("the planning each confirmed HU is received for")
	class PlanningByHu
	{
		private final HuId firstHuId = HuId.ofRepoId(540101);
		private final HuId secondHuId = HuId.ofRepoId(540102);

		@Test
		@DisplayName("EVERY confirmed HU maps to the planning the editor was launched for - not only the generated ones")
		void everyConfirmedHuMapsToTheLaunchingPlanning()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(540020);

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(receiptDispositionRow(receiptSchedule, deliveryPlanningId)));

			// The two HUs are what EXISTS at confirm time, which is not what was generated: the operator can split
			// and merge inside the editor, and WEBUI_M_HU_Transform plumbs no provenance onto what it creates. So the
			// map has to be built from the SELECTION at confirm, keyed by whatever HU the operator ended up with.
			assertThat(HUEditorReceiptSources.deliveryPlanningIdByHuId(sources, ImmutableSet.of(firstHuId, secondHuId)))
					.containsOnlyKeys(firstHuId, secondHuId)
					.containsValues(deliveryPlanningId);
		}

		@Test
		@DisplayName("an UNPLANNED launch maps nothing - the plain receipt against the schedule")
		void unplannedLaunchMapsNothing()
		{
			final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule();

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(receiptDispositionRow(receiptSchedule, null)));

			assertThat(HUEditorReceiptSources.deliveryPlanningIdByHuId(sources, ImmutableSet.of(firstHuId))).isEmpty();
		}

		@Test
		@DisplayName("a launch from SEVERAL rows of which one is planned is refused: which HU belongs to which planning cannot be known")
		void severalPlannedRowsAreRefused()
		{
			final DeliveryPlanningId first = DeliveryPlanningId.ofRepoId(540020);
			final DeliveryPlanningId second = DeliveryPlanningId.ofRepoId(540021);

			final ImmutableList<ReferencedReceiptSource> sources = HUEditorReceiptSources.resolve(
					documentsCollection, context,
					ImmutableList.of(
							receiptDispositionRow(createReceiptSchedule(), first),
							receiptDispositionRow(createReceiptSchedule(), second)));

			// Guessing here would stamp both plannings' HUs with one of them, i.e. book goods against a planning
			// they did not arrive for - invisible in the UI and wrong in the books.
			assertThatThrownBy(() -> HUEditorReceiptSources.deliveryPlanningIdByHuId(sources, ImmutableSet.of(firstHuId, secondHuId)))
					.hasMessageContaining(String.valueOf(first.getRepoId()));
		}
	}
}
