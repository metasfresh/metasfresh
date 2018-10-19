package de.metas.allocation.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;

import de.metas.builder.IBuilder;

/**
 * Implementors of this interface are used to create {@link I_C_AllocationHdr}s.
 * 
 * @see IAllocationBL#newBuilder(Object)
 * 
 */
public interface IAllocationBuilder extends IBuilder
{
	IAllocationBuilder setAD_Org_ID(int adOrgId);

	IAllocationBuilder setDateTrx(Timestamp date);

	IAllocationBuilder setDateAcct(Timestamp date);

	IAllocationBuilder setC_Currency_ID(int c_Currency_ID);

	/**
	 * @param manual true if this allocation shall be flagged as "manually created by user"
	 * @see I_C_AllocationHdr#setIsManual(boolean)
	 */
	IAllocationBuilder setManual(final boolean manual);

	IAllocationBuilder setDescription(final String description);

	/**
	 * Convenience method to create a new line builder using the {@link DefaultAllocationLineBuilder} implementation. Module specific subclasses can override this method.
	 * 
	 * @return
	 */
	IAllocationLineBuilder addLine();

	/**
	 * Creates a new allocation line builder to add another line to the allocation that is currently build
	 * 
	 * @param implClazz the allocation line builder implementation class to be used. If unsure which class to pick, then use {@link #addLine()}.
	 *            <p>
	 *            <b>IMPORTANT: when using an class that is included in another class, then this included class needs to be <code>static</code></b>
	 * @return
	 */
	<T extends DefaultAllocationLineBuilder> T addLine(Class<T> implClazz);

	/**
	 * Creates the {@link I_C_AllocationHdr}.
	 * 
	 * @param complete if <code>true</code>, then the new allocation header is also completed after creation.
	 * @return create allocation or <code>null</code> if no allocation was needed
	 */
	I_C_AllocationHdr create(boolean complete);

	/**
	 * Creates the {@link I_C_AllocationHdr} and completes it.
	 * 
	 * @return create allocation or <code>null</code> if no allocation was needed
	 */
	I_C_AllocationHdr createAndComplete();

	/**
	 * Gets single C_AllocationLine_ID.
	 * 
	 * If the built allocation has more lines, this method will return -1.
	 * 
	 * @return C_AllocationLine_ID or -1
	 */
	int getC_AllocationLine_ID();

	Set<Integer> getC_BPartner_IDs();

	/**
	 * Disable calculating the BPartner's TotalOpenBalance (which is quite expensive).
	 * 
	 * In this case, the caller shall take care about this aspect.
	 */
	IAllocationBuilder disableUpdateBPartnerTotalOpenBanace();

	/**
	 * Gets all C_AllocationLine
	 * 
	 * @return Iterator<I_C_AllocationLine>
	 */
	List<I_C_AllocationLine> getC_AllocationLines();
}
