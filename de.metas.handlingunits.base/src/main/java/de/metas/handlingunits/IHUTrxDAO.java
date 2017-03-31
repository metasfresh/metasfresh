package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

public interface IHUTrxDAO extends ISingletonService
{
	IHUTrxQuery createHUTrxQuery();

	List<I_M_HU_Trx_Line> retrieveReferencingTrxLines(Object referencedModel);

	List<I_M_HU_Trx_Line> retrieveReferencingTrxLines(Properties ctx, int adTableId, int recordId, String trxName);

	List<I_M_HU_Trx_Line> retrieveTrxLines(I_M_HU_Trx_Hdr trxHdr);

	List<I_M_HU_Trx_Line> retrieveTrxLines(Properties ctx, IHUTrxQuery huTrxQuery, String trxName);

	List<I_M_HU_Trx_Line> retrieveTrxLines(I_M_HU_Item huItem);

	/**
	 * Retrieves counterpart transaction.
	 *
	 * @param trxLine
	 * @return counterpart transaction; never returns null
	 */
	I_M_HU_Trx_Line retrieveCounterpartTrxLine(I_M_HU_Trx_Line trxLine);

	/**
	 * Retrieve all the {@link I_M_HU_Trx_Line} that reference the given {@code hu} with their {@code M_HU_ID} column.
	 * 
	 * @param hu
	 * @return
	 */
	List<I_M_HU_Trx_Line> retrieveReferencingTrxLinesForHU(I_M_HU hu);

}
