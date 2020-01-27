package de.metas.edi.sscc18;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;

import de.metas.adempiere.form.terminal.table.annotation.TableInfo;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;

/**
 * {@link I_EDI_DesadvLine} printable labels.
 * We use this interface to:
 * <ul>
 * <li>Present to user what we are going to print
 * <li>instruct the SSCC18 label generator what to generate and print.
 * </ul>
 *
 * If you want to create a new instance, please use {@link PrintableDesadvLineSSCC18Labels#builder()}.
 *
 * @author tsa
 *
 */
// NOTE: because there is a bug in JXTable code which is not sorting correctly primitive types (i.e. int) we are using Integer
// see org.jdesktop.swingx.sort.TableSortController.getComparator(int)
public interface IPrintableDesadvLineSSCC18Labels
{
	@TableInfo(hidden = true)
	I_EDI_DesadvLine getEDI_DesadvLine();

	@TableInfo(displayName = "Line", translate = true, seqNo = 10, prototypeValue = "0000")
	Integer getLineNo();

	@TableInfo(displayName = "ProductValue", translate = true, seqNo = 20)
	String getProductValue();

	@TableInfo(displayName = "ProductName", translate = true, seqNo = 30, prototypeValue = "Raschelsacke Goldgelb mit Verschlussband 20kg (Ballen à 1000 Stk)")
	String getProductName();

	@TableInfo(displayName = "ExistingSSCC18sCount", translate = true, seqNo = 40, prototypeValue = "000")
	Integer getExistingSSCC18sCount();

	@TableInfo(hidden = true)
	List<I_EDI_DesadvLine_Pack> getExistingSSCC18s();

	@TableInfo(displayName = "RequiredSSCC18sCount", translate = true, seqNo = 50, prototypeValue = "00000000000000000000")
	BigDecimal getRequiredSSCC18sCount();

	void setRequiredSSCC18sCount(BigDecimal requiredSSCC18sCount);

	/**
	 * Creates a new calculator which is initialized with the total qty ordered and helps removing from that quantity as much as it is needed for an LU.
	 */
	TotalQtyCUBreakdownCalculator breakdownTotalQtyCUsToLUs();
}
