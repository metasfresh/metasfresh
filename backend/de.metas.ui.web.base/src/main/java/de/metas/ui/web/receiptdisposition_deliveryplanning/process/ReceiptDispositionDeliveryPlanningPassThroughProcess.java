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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.ui.web.handlingunits.process.ReceiptScheduleActions;

/**
 * What the receipt-disposition delivery-planning window's PASS-THROUGH actions share - "Korrektur", "Leergut Ausgabe", "Leergut
 * Rücknahme", "Foto" and "Drucken Produktanlieferung".
 * <p>
 * They are pass-through in the precise sense that they do exactly what the receipt-schedule window's action of
 * the same name does, to the same record: the row's receipt schedule. They exist as separate classes for one
 * mechanical reason - for a view row the platform resolves a process' record via
 * {@code IView#getTableRecordReferenceOrNull}, which on this window yields {@code RV_ReceiptDisposition_DeliveryPlanning}, while
 * every {@code WEBUI_M_ReceiptSchedule_*} action asks for {@code M_ReceiptSchedule}, and the seam is sealed
 * ({@code JavaProcess#getRecord} is {@code protected final}). So these adapters read the schedule off the
 * selected GRID ROW instead, and hand it to the shared {@link ReceiptScheduleActions}, which is the ONE place
 * each action's body lives.
 * <p>
 * <b>Deliberately no planning guard here.</b> {@link ReceiptDispositionDeliveryPlanningReceiveProcess} refuses a selection whose
 * planning is already {@code Processed}, because a planning may hold at most one receipt. None of these five
 * produces a receipt, and "Korrektur" applies precisely to a row that already has one - so applying that guard
 * here would disable the action exactly where it is needed. The planning id a planned row carries is simply not
 * part of what these actions do: both row types behave identically (AC7).
 */
abstract class ReceiptDispositionDeliveryPlanningPassThroughProcess extends ReceiptDispositionDeliveryPlanningViewBasedProcess
{
	// package-visible, non-final: substituted with a mock by the same-package unit test, which asserts WHICH
	// receipt schedule each adapter hands on. Same shape as M_Delivery_Planning_GenerateReceipt#helper.
	ReceiptScheduleActions actions = ReceiptScheduleActions.newInstance();
}
