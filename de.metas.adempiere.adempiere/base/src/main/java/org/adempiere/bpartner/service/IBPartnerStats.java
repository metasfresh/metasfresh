package org.adempiere.bpartner.service;

import java.math.BigDecimal;

import org.compiere.model.I_C_BPartner_Stats;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 * 
 *         Immutable object to keep the {@link I_C_BPartner_Stats} values in one place.
 *         It is useful because database operations will not be needed during compact pieces of logic.
 *         See implementations in org.adempiere.bpartner.service.impl.BPartnerStatsBL
 *
 */
public interface IBPartnerStats
{

	/**
	 * @return
	 */
	BigDecimal getTotalOpenBalance();

	/**
	 * @return
	 */
	String getSOCreditStatus();

	/**
	 * @return
	 */
	BigDecimal getSOCreditUsed();

	/**
	 * @return
	 */
	BigDecimal getActualLifeTimeValue();

	/**
	 * @return
	 */
	int getC_BPartner_ID();

}
