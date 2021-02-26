package de.metas.manufacturing.acct;

import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import de.metas.acct.doc.AcctDocProviderTemplate;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class ManufacturingAcctDocProvider extends AcctDocProviderTemplate
{
	public ManufacturingAcctDocProvider()
	{
		super(ImmutableMap.<String, AcctDocFactory> builder()
				.put(I_PP_Order.Table_Name, Doc_PPOrder::new)
				.put(I_PP_Cost_Collector.Table_Name, Doc_PPCostCollector::new)
				.put(I_DD_Order.Table_Name, Doc_DDOrder::new)
				.build());
	}
}
