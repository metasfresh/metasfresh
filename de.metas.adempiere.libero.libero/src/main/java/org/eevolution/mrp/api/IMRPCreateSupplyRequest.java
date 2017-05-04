package org.eevolution.mrp.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.spi.IMRPSupplyProducer;

/**
 * Request object used to group all parameters needed by {@link IMRPSupplyProducer#createSupply(IMRPCreateSupplyRequest)} in order to create the supply document.
 * 
 * @author tsa
 *
 */
public interface IMRPCreateSupplyRequest
{
	/** @return context */
	IMRPContext getMRPContext();

	/** @return MRP executor which is running */
	IMRPExecutor getMRPExecutor();

	/** @return how much quantity is needed to supply */
	BigDecimal getQtyToSupply();

	/** @return demand date; i.e. the date when quantity to supply is really needed */
	Date getDemandDate();

	/**
	 * Gets single MRP Demand Record that needs to be allocated.
	 * 
	 * NOTE: in case there are more MRP demand records that needs to be allocated, this method will return null
	 * 
	 * @return MRP demand record or null
	 */
	I_PP_MRP getMRPDemandRecordOrNull();

	/**
	 * @return all MRP Demand records
	 */
	List<I_PP_MRP> getMRPDemandRecords();

	/**
	 * @return C_BPartner_ID or -1
	 */
	int getMRPDemandBPartnerId();

	/**
	 * @return sales C_OrderLine_ID or -1
	 */
	int getMRPDemandOrderLineSOId();
}
