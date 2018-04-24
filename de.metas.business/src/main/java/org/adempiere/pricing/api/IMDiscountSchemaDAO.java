package org.adempiere.pricing.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;

public interface IMDiscountSchemaDAO extends ISingletonService
{
	I_M_DiscountSchema getById(final int discountSchemaId);
	
	/**
	 * @return the breaks of the given schema, ordered by seqno
	 */
	List<I_M_DiscountSchemaBreak> retrieveBreaks(int discountSchemaId);

	/**
	 * @return the breaks of the given schema, ordered by seqno
	 */
	List<I_M_DiscountSchemaBreak> retrieveBreaks(I_M_DiscountSchema schema);

	/**
	 * @return the lines of the given schema ordered by the seqno
	 */
	List<I_M_DiscountSchemaLine> retrieveLines(I_M_DiscountSchema schema);
}
