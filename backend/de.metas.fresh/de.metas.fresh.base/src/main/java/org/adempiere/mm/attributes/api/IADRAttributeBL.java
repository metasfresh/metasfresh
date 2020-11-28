package org.adempiere.mm.attributes.api;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Properties;

import org.adempiere.mm.attributes.AttributeListValue;

import de.metas.fresh.model.I_C_BPartner;
import de.metas.util.ISingletonService;

public interface IADRAttributeBL extends ISingletonService, IBPartnerAwareAttributeService
{
	/**
	 * Get or create the ADR attribute value based on the Sysconfig de.metas.swat.AttributeAction
	 * 
	 * @param ctx
	 * @param partner
	 * @param isSOTrx
	 * @param trxName
	 * @return the attribute value (existing or created)
	 */
	AttributeListValue getCreateAttributeValue(Properties ctx, I_C_BPartner partner, boolean isSOTrx, String trxName);

	/**
	 * We need this because we can have different ADR for a BPartner to use in sales and purchase transactions.
	 * 
	 * @param partner
	 * @param isSOTrx will return different values for sales and purchase transactions, if the ADR flag is set accordingly
	 * @return
	 */
	String getADRForBPartner(I_C_BPartner partner, boolean isSOTrx);
}
