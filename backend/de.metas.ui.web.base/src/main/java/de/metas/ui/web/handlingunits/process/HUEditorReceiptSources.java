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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * What the rows of an HU editor's LAUNCHING view stand for.
 * <p>
 * The editor is ONE view reached from several windows, and the confirm inside it books against whatever launched
 * it - which the editor knows only as its view's REFERENCING DOCUMENT PATHS. Each of those is resolved BY THE
 * TABLE IT BELONGS TO rather than by assuming the one provenance that existed until now: a row of the
 * receipt-disposition delivery-planning window is a {@code RV_ReceiptDisposition_DeliveryPlanning} one and
 * carries, besides the receipt schedule to book against, the DELIVERY PLANNING the goods are arriving for.
 * <p>
 * An unknown table is refused rather than read as a receipt schedule anyway: the persistence layer will happily
 * wrap a foreign record whose columns happen to line up, so a silently mis-read provenance produces a receipt
 * that looks right and links back to nothing.
 */
final class HUEditorReceiptSources
{
	private HUEditorReceiptSources()
	{
	}

	/** One referencing row of the launching view, resolved to the records it stands for. */
	@Value
	static class ReferencedReceiptSource
	{
		@NonNull I_M_ReceiptSchedule receiptSchedule;

		/** {@code null} unless the launching window keys its rows on a delivery planning and this row is a planned one. */
		@Nullable DeliveryPlanningId deliveryPlanningId;
	}

	static ImmutableList<ReferencedReceiptSource> resolve(
			@NonNull final DocumentCollection documentsCollection,
			@NonNull final IContextAware context,
			@NonNull final Collection<DocumentPath> referencingDocumentPaths)
	{
		return referencingDocumentPaths.stream()
				.map(documentsCollection::getTableRecordReference)
				.map(recordRef -> resolve(context, recordRef))
				.collect(ImmutableList.toImmutableList());
	}

	private static ReferencedReceiptSource resolve(
			@NonNull final IContextAware context,
			@NonNull final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();

		//
		// The receipt-schedule window: the record IS the schedule, and there is no planning. Loaded exactly as
		// before, so that window's confirm is untouched.
		if (I_M_ReceiptSchedule.Table_Name.equals(tableName))
		{
			return new ReferencedReceiptSource(recordRef.getModel(context, I_M_ReceiptSchedule.class), null);
		}

		//
		// The receipt-disposition delivery-planning window. Both of the view's branches select
		// M_ReceiptSchedule_ID; only the planned one selects a planning, the unplanned one selects NULL - which
		// is the very distinction the receive has to preserve.
		if (I_RV_ReceiptDisposition_DeliveryPlanning.Table_Name.equals(tableName))
		{
			final I_RV_ReceiptDisposition_DeliveryPlanning row =
					recordRef.getModelNonNull(context, I_RV_ReceiptDisposition_DeliveryPlanning.class);

			return new ReferencedReceiptSource(
					TableRecordReference.of(I_M_ReceiptSchedule.Table_Name, row.getM_ReceiptSchedule_ID())
							.getModelNonNull(context, I_M_ReceiptSchedule.class),
					DeliveryPlanningId.ofRepoIdOrNull(row.getM_Delivery_Planning_ID()));
		}

		//
		// The PLANNING record itself. Two launchers arrive here, which is why this is not an edge case:
		// the delivery-planning window, whose rows simply ARE M_Delivery_Planning; and a PLANNED row of the
		// receipt-disposition window, which the grid keys on its planning rather than on the view (the same
		// keying WEBUI_M_HU_CreateReceipt_Base relies on when it notifies the launching window's rows).
		if (I_M_Delivery_Planning.Table_Name.equals(tableName))
		{
			final I_M_Delivery_Planning deliveryPlanning =
					recordRef.getModelNonNull(context, I_M_Delivery_Planning.class);

			final int receiptScheduleRepoId = deliveryPlanning.getM_ReceiptSchedule_ID();
			if (receiptScheduleRepoId <= 0)
			{
				// An OUTGOING planning has no receipt schedule, so there is nothing to receive against. Said
				// here rather than letting the load below fail on id 0, which reads like a missing record.
				throw new AdempiereException("Cannot receive for " + recordRef + ": this delivery planning has no receipt schedule")
						.appendParametersToMessage()
						.setParameter("M_Delivery_Planning_ID", deliveryPlanning.getM_Delivery_Planning_ID());
			}

			return new ReferencedReceiptSource(
					TableRecordReference.of(I_M_ReceiptSchedule.Table_Name, receiptScheduleRepoId)
							.getModelNonNull(context, I_M_ReceiptSchedule.class),
					DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
		}

		throw new AdempiereException("Cannot receive for " + recordRef + ": the HU editor was launched from a window this receive does not know")
				.appendParametersToMessage()
				.setParameter("tableName", tableName);
	}

	/**
	 * The delivery planning each HU of this confirm is being received for, in the shape
	 * {@code CreateReceiptsParameters} wants it.
	 * <p>
	 * Keyed by the HUs the operator ended up with at CONFIRM time, not by the ones that were generated: the whole
	 * point of the editor is that the operator repacks inside it, and {@code WEBUI_M_HU_Transform} plumbs no
	 * provenance onto what a split creates. All of them belong to the ONE row that launched the editor, which is
	 * what makes the blanket mapping correct - and why a launch that cannot name one row is refused.
	 */
	static ImmutableMap<HuId, DeliveryPlanningId> deliveryPlanningIdByHuId(
			@NonNull final List<ReferencedReceiptSource> sources,
			@NonNull final Set<HuId> selectedHuIds)
	{
		final ImmutableSet<DeliveryPlanningId> deliveryPlanningIds = deliveryPlanningIds(sources);
		if (deliveryPlanningIds.isEmpty())
		{
			// Every launch that has no planning at all - the receipt-schedule window, and an unplanned
			// receipt-disposition row: the plain receipt against the schedule.
			return ImmutableMap.of();
		}

		if (deliveryPlanningIds.size() > 1 || sources.size() > 1)
		{
			// Guessing here would stamp the HUs of several plannings with one of them, i.e. book goods against a
			// planning they did not arrive for - invisible in the UI, wrong in the books. Unreachable from the
			// receipt-disposition window today (its receive actions are single-selection), so this is the guard
			// that keeps it that way rather than a case to support.
			throw new AdempiereException("Cannot receive for more than one delivery planning in one HU editor")
					.appendParametersToMessage()
					.setParameter("M_Delivery_Planning_IDs", deliveryPlanningIds);
		}

		// The MAPPING itself is the shared receive's own, so both receive paths key it identically.
		return ReceiptFromReceiptScheduleService.deliveryPlanningIdByHuId(selectedHuIds, deliveryPlanningIds.iterator().next());
	}

	/** The delivery plannings the launching rows stand for; empty when none of them is a planned row. */
	static ImmutableSet<DeliveryPlanningId> deliveryPlanningIds(@NonNull final List<ReferencedReceiptSource> sources)
	{
		return sources.stream()
				.map(ReferencedReceiptSource::getDeliveryPlanningId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
