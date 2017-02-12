package de.metas.materialtracking.ait.helpers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IInvoicedSumProvider;

/*
 * #%L
 * de.metas.materialtracking.ait
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
 * Allows us for module tests to just assume that ICs from a previous quality inspection where meanwhile properly invoiced.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MockedInvoicedSumProvider implements IInvoicedSumProvider
{

	public static final MockedInvoicedSumProvider instance = new MockedInvoicedSumProvider();

	private final Map<String, BigDecimal> lot2sum = new HashMap<String, BigDecimal>();

	private MockedInvoicedSumProvider()
	{
	};

	/**
	 *
	 * @param lotMaterialTracking
	 * @param alreadyInvoicedNetSum
	 * @return old sum or <code>null</code>
	 */
	public BigDecimal putAlreadyInvoicedNetSum(final String lotMaterialTracking, final BigDecimal alreadyInvoicedNetSum)
	{
		return lot2sum.put(lotMaterialTracking, alreadyInvoicedNetSum);
	}

	@Override
	public BigDecimal getAlreadyInvoicedNetSum(final I_M_Material_Tracking materialTracking)
	{
		return lot2sum.get(materialTracking.getLot());
	}

}
