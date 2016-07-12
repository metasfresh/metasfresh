package de.metas.rfq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.business
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

public interface IRfqDAO extends ISingletonService
{
	List<I_C_RfQLine> retrieveLines(I_C_RfQ rfq);

	int retrieveLinesCount(I_C_RfQ rfq);

	List<I_C_RfQLineQty> retrieveLineQtys(I_C_RfQLine line);

	int retrieveLineQtysCount(I_C_RfQLine line);

	List<I_C_RfQResponse> retrieveAllResponses(I_C_RfQ rfq);

	List<I_C_RfQResponse> retrieveCompletedResponses(I_C_RfQ rfq);

	List<I_C_RfQResponseLine> retrieveResponseLines(I_C_RfQResponse rfqResponse);

	<T extends I_C_RfQResponseLine> List<T> retrieveResponseLines(I_C_RfQResponse rfqResponse, Class<T> returnType);

	boolean hasSelectedWinnerLines(I_C_RfQResponse rfqResponse);

	List<I_C_RfQResponseLineQty> retrieveResponseQtys(I_C_RfQLineQty rfqLineQty);

	List<I_C_RfQResponseLineQty> retrieveResponseQtys(I_C_RfQResponseLine rfqResponseLine);

	boolean hasResponseQtys(I_C_RfQResponseLine rfqResponseLine);

	BigDecimal calculateQtyPromised(I_C_RfQResponseLine rfqResponseLine);

	I_C_RfQResponseLineQty retrieveResponseQty(I_C_RfQResponseLine rfqResponseLine, Date date);

	boolean hasQtyRequiered(I_C_RfQResponse rfqResponse);
}
