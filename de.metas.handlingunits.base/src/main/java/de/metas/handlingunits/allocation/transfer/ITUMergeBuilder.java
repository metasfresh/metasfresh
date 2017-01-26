package de.metas.handlingunits.allocation.transfer;

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

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Implementors perform HU merges. In a merger, we load stuff from source HUs to <b>already existing</b> destination HUs.<br>
 * That the destination HUs already exist is the main different between merge an split and makes merging "simpler" (i.e. we can internally use a {@link HUListAllocationSourceDestination} instead of a {@link ILUTUProducerAllocationDestination}).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ITUMergeBuilder
{
	ITUMergeBuilder setSourceHUs(List<I_M_HU> sourceHUs);

	ITUMergeBuilder setTargetTU(I_M_HU targetHU);

	ITUMergeBuilder setCUProduct(I_M_Product product);

	ITUMergeBuilder setCUQty(BigDecimal qty);

	ITUMergeBuilder setCUUOM(I_C_UOM uom);

	ITUMergeBuilder setCUTrxReferencedModel(Object trxReferencedModel);

	void mergeTUs();
}
