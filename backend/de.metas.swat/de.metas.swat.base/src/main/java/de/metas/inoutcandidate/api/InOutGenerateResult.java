package de.metas.inoutcandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import de.metas.inout.model.I_M_InOut;

/**
 * Intended to be used by InOut creating {@link org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor}s to create both shipments and receipts.
 * <p>
 * Use {@link IInOutCandidateBL#createEmptyInOutGenerateResult(boolean)} to get an instance.
 * 
 */
public interface InOutGenerateResult
{
	int getInOutCount();

	List<I_M_InOut> getInOuts();

	void addInOut(I_M_InOut inOut);
}
