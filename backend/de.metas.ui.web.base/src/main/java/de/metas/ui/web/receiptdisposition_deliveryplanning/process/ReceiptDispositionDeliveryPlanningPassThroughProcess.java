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
import org.compiere.SpringContextHolder;

/**
 * What the receipt-disposition delivery-planning window's PASS-THROUGH actions share - "Korrektur", "Leergut
 * Ausgabe", "Leergut Rücknahme", "Foto" and "Drucken Produktanlieferung": each reads the schedule off the
 * selected GRID ROW and hands it to the shared {@link ReceiptScheduleActions}.
 * <p>
 * Deliberately NO planning guard here. None of these five produces a receipt, and "Korrektur" applies precisely
 * to a row that already has one - so the receive actions' already-{@code Processed} guard would disable the
 * action exactly where it is needed.
 */
abstract class ReceiptDispositionDeliveryPlanningPassThroughProcess extends ReceiptDispositionDeliveryPlanningViewBasedProcess
{
	// package-visible, non-final: substituted with a mock by the same-package unit test, which asserts WHICH
	// receipt schedule each adapter hands on. Same shape as M_Delivery_Planning_GenerateReceipt#helper.
	ReceiptScheduleActions actions = SpringContextHolder.instance.getBean(ReceiptScheduleActions.class);
}
