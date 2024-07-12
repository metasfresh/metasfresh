package de.metas.edi.api;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.i18n.ITranslatableString;
import de.metas.report.ReportResultData;
import de.metas.util.ISingletonService;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

public interface IDesadvBL extends ISingletonService
{
	List<I_EDI_DesadvLine> retrieveLinesByIds(Collection<Integer> desadvLineIds);

	/**
	 * Creates a new desadv for the given <code>order</code>'s POReference, or retrieves an existing one. <br>
	 * If a new one is created, then values are taken from this order.<br>
	 * Also iterates the order's lines and creates {@link I_EDI_DesadvLine}s for their respective line numbers unless such desadv lines already exist.
	 * <p>
	 * Notes:
	 * <ul>
	 * <li>the order and its lines are modified (their referencing/FK columns are set), but only the lines are saved! This is because we call this method from a C_Order modelvalidator.
	 * <li>Assumes that the given order has a non-empty <code>POReference</code>.
	 * </ul>
	 */
	I_EDI_Desadv addToDesadvCreateForOrderIfNotExist(I_C_Order order);

	/**
	 * Removes the given <code>order</code> from its desadv (if any) and also removes its order lines from the desadv lines.
	 * <p>
	 * If because of that the desadv lines in question don't have any assigned order line left, they are deleted
	 */
	void removeOrderFromDesadv(I_C_Order order);

	/**
	 * Removes/detaches the given inOutLine from its desadv line (if any). If after this, no order lines are referencing the desadv line, then it is deleted
	 */
	void removeOrderLineFromDesadv(I_C_OrderLine orderLine);

	/**
	 * For existing desadv lines, just <code>QtyEntered</code> and <code>MovementQty</code>. are updated.
	 * Note that this method also sets the desadv(-line)s' IDs to the inOut and its lines and saves them.
	 */
	I_EDI_Desadv addToDesadvCreateForInOutIfNotExist(I_M_InOut inOut);

	/**
	 * Removes the given <code>inOut</code> from its desadv (if any) and also removes its inOut lines from the desadv lines.
	 * <p>
	 * Note: the inout and its lines are modified, but only the lines are saved! This is because we call this method from an M_InOut modelvalidator.
	 */
	void removeInOutFromDesadv(I_M_InOut inOut);

	/**
	 * Remove/detache the given inOutLine from its desadv line (if any) and subtracts the inout line's MovementQty from the desadv line's qtys
	 */
	void removeInOutLineFromDesadv(I_M_InOutLine inOutLine);

	/**
	 * Print SSCC18 labels for given {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} IDs by invoking a jasper-process, and forwarding its binary report data.
	 */
	ReportResultData printSSCC18_Labels(Properties ctx, Collection<EDIDesadvPackId> desadvPack_IDs_ToPrint);

	/**
	 * Set the current minimum sum percentage taken from the sys config 'de.metas.esb.edi.DefaultMinimumPercentage'
	 */
	void setMinimumPercentage(I_EDI_Desadv desadv);

	/**
	 * Iterate the given list and create user-friendly messages for all desadvs whose delivered quantity (fulfillment) is below their respective treshold.
	 */
	ImmutableList<ITranslatableString> createMsgsForDesadvsBelowMinimumFulfilment(ImmutableList<I_EDI_Desadv> desadvRecords);

	/**
	 * @return all <code>M_InOutLine</code>s (incl inactive ones) that reference the given <code>desadvLine</code>.
	 */
	List<I_M_InOutLine> retrieveAllInOutLines(I_EDI_DesadvLine desadvLine);
}
