package de.metas.procurement.base;

import org.adempiere.util.ISingletonService;

import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IPMM_RfQ_BL extends ISingletonService
{
	boolean isProcurement(I_C_RfQ rfq);

	boolean isProcurement(I_C_RfQResponse rfqResponse);

	boolean isDraft(I_C_RfQResponseLine rfqResponseLine);

	boolean isClosed(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine);

	boolean isCompletedOrClosed(I_C_RfQResponse rfqResponse);

	boolean isCompletedOrClosed(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine);

	void createDraftContractsForWinners(I_C_RfQ rfq);

	void createDraftContractsForSelectedWinners(I_C_RfQResponse rfqResponse);

	void checkCompleteContractsForWinners(I_C_RfQResponse rfqResponse);

	void checkCompleteContractIfWinner(I_C_RfQResponseLine rfqResponseLine);

}
