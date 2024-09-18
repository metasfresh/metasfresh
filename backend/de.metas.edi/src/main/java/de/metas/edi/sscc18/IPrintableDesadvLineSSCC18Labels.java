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

import de.metas.edi.api.impl.pack.EDIDesadvPack;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;

import java.math.BigDecimal;
import java.util.List;

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
public interface IPrintableDesadvLineSSCC18Labels
{
	I_EDI_DesadvLine getEDI_DesadvLine();

	Integer getLineNo();

	String getProductValue();

	String getProductName();

	Integer getExistingSSCC18sCount();

	List<EDIDesadvPack> getExistingSSCC18s();

	BigDecimal getRequiredSSCC18sCount();

	void setRequiredSSCC18sCount(BigDecimal requiredSSCC18sCount);

	/**
	 * Creates a new calculator which is initialized with the total qty ordered and helps removing from that quantity as much as it is needed for an LU.
	 */
	TotalQtyCUBreakdownCalculator breakdownTotalQtyCUsToLUs();
}
