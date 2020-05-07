package de.metas.procurement.base;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

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

public interface IPMM_RfQ_DAO extends ISingletonService
{
	List<I_C_RfQResponseLine> retrieveAllActiveResponseLines(Properties ctx);

	List<I_C_RfQResponseLine> retrieveActiveResponseLines(Properties ctx, int bpartnerId);

	List<I_C_RfQResponseLine> retrieveResponseLines(I_C_RfQResponse rfqResponse);

	List<I_C_RfQResponseLineQty> retrieveResponseLineQtys(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine);

	I_C_RfQResponseLineQty retrieveResponseLineQty(de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine, Date date);

}
