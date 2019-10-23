package de.metas.adempiere.docline.sort.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.X_C_DocType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link DocLineSortProductIdsComparator} - comparator shall correctly assess sequence number of different products against C_BPartner configuration.
 *
 * @author al
 */
public class DocLineSortProductIdsComparatorTest extends AbstractDocLineSortItemFinderTest
{
	/**
	 * Basic pre-sorted products (normally all lines are in a specific order)
	 */
	private List<Integer> orderedProductLines;

	@Override
	protected final void setupAdditionalData()
	{
		//
		// Configure DocLineSort scenarios
		final I_C_DocLine_Sort docLineSortDefaultMMS = docLineSort("MMS_Default", X_C_DocType.DOCBASETYPE_MaterialDelivery, true);
		docLineSortItem(docLineSortDefaultMMS, product2, 10);
		docLineSortItem(docLineSortDefaultMMS, product1, 20);

		final I_C_DocLine_Sort docLineSortMMS_BP1BP2 = docLineSort("MMS_BP1BP2", X_C_DocType.DOCBASETYPE_MaterialDelivery, false);
		docLineSortBP(docLineSortMMS_BP1BP2, bpartner1);
		docLineSortBP(docLineSortMMS_BP1BP2, bpartner2);
		docLineSortItem(docLineSortMMS_BP1BP2, product2, 10);
		docLineSortItem(docLineSortMMS_BP1BP2, product4, 20);

		final I_C_DocLine_Sort docLineSortMMR_BP4 = docLineSort("MMR_BP4", X_C_DocType.DOCBASETYPE_MaterialReceipt, false);
		docLineSortBP(docLineSortMMR_BP4, bpartner4);
		docLineSortItem(docLineSortMMR_BP4, product3, 10);

		orderedProductLines = Arrays.asList(
				product1.getM_Product_ID()
				, product2.getM_Product_ID()
				, product3.getM_Product_ID()
				, product4.getM_Product_ID()
				, product5.getM_Product_ID()
				);
	}

	@Test
	public void testBP3MMSDefault()
	{
		final Comparator<Integer> productIdsComparator = docLineSortDAO.findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(bpartner3.getC_BPartner_ID())
				.setC_DocType(docTypeMMS)
				.findProductIdsComparator();
		Collections.sort(orderedProductLines, productIdsComparator);

		Assert.assertEquals(product2.getM_Product_ID(), orderedProductLines.get(0).intValue());
		Assert.assertEquals(product1.getM_Product_ID(), orderedProductLines.get(1).intValue());
		Assert.assertEquals(product3.getM_Product_ID(), orderedProductLines.get(2).intValue());
		Assert.assertEquals(product4.getM_Product_ID(), orderedProductLines.get(3).intValue());
		Assert.assertEquals(product5.getM_Product_ID(), orderedProductLines.get(4).intValue());
	}

	@Test
	public void testBP1MMS()
	{
		final Comparator<Integer> productIdsComparator = docLineSortDAO.findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(bpartner1.getC_BPartner_ID())
				.setC_DocType(docTypeMMS)
				.findProductIdsComparator();
		Collections.sort(orderedProductLines, productIdsComparator);

		Assert.assertEquals(product2.getM_Product_ID(), orderedProductLines.get(0).intValue());
		Assert.assertEquals(product4.getM_Product_ID(), orderedProductLines.get(1).intValue());
		Assert.assertEquals(product1.getM_Product_ID(), orderedProductLines.get(2).intValue());
		Assert.assertEquals(product3.getM_Product_ID(), orderedProductLines.get(3).intValue());
		Assert.assertEquals(product5.getM_Product_ID(), orderedProductLines.get(4).intValue());
	}

	@Test
	public void testBP3MMR()
	{
		final Comparator<Integer> productIdsComparator = docLineSortDAO.findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(bpartner3.getC_BPartner_ID())
				.setC_DocType(docTypeMMR)
				.findProductIdsComparator();
		Collections.sort(orderedProductLines, productIdsComparator);

		Assert.assertEquals(product1.getM_Product_ID(), orderedProductLines.get(0).intValue());
		Assert.assertEquals(product2.getM_Product_ID(), orderedProductLines.get(1).intValue());
		Assert.assertEquals(product3.getM_Product_ID(), orderedProductLines.get(2).intValue());
		Assert.assertEquals(product4.getM_Product_ID(), orderedProductLines.get(3).intValue());
		Assert.assertEquals(product5.getM_Product_ID(), orderedProductLines.get(4).intValue());
	}

	@Test
	public void testBP4MMR()
	{
		final Comparator<Integer> productIdsComparator = docLineSortDAO.findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(bpartner4.getC_BPartner_ID())
				.setC_DocType(docTypeMMR)
				.findProductIdsComparator();
		Collections.sort(orderedProductLines, productIdsComparator);

		Assert.assertEquals(product3.getM_Product_ID(), orderedProductLines.get(0).intValue());
		Assert.assertEquals(product1.getM_Product_ID(), orderedProductLines.get(1).intValue());
		Assert.assertEquals(product2.getM_Product_ID(), orderedProductLines.get(2).intValue());
		Assert.assertEquals(product4.getM_Product_ID(), orderedProductLines.get(3).intValue());
		Assert.assertEquals(product5.getM_Product_ID(), orderedProductLines.get(4).intValue());
	}
}
