package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.test.ErrorMessage;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_MovementLine;

public class PackingMaterialsExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	// services
	@ToStringBuilder(skip = true)
	private final transient IMovementDAO movementDAO = Services.get(IMovementDAO.class);

	private final List<PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>>> packingMaterialExepectations = new ArrayList<>();

	public static final PackingMaterialsExpectation<Object> newExpectation()
	{
		return new PackingMaterialsExpectation<Object>();
	}

	public PackingMaterialsExpectation()
	{
		super();
	}

	public PackingMaterialsExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public PackingMaterialsExpectation<ParentExpectationType> assertExpected(final I_M_Movement movement)
	{
		final List<I_M_MovementLine> movementLines = InterfaceWrapperHelper.createList(
				movementDAO.retrieveLines(movement),
				I_M_MovementLine.class);

		final ErrorMessage message = newErrorMessage()
				.addContextInfo("Movement", movement)
				.addContextInfo("Movement lines", movementLines);

		final Map<I_M_Product, BigDecimal> product2qty = extractPackingMaterialProduct2Qty(movement);

		return assertExpected(message, product2qty);
	}

	private PackingMaterialsExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final Map<I_M_Product, BigDecimal> product2qtyMap)
	{
		Check.assumeNotNull(product2qtyMap, "product2qty not null");

		final ErrorMessage messageToUse = derive(message)
				.addContextInfo("Product2Qty", product2qtyMap);

		assertEquals(messageToUse.expect("Same count of packing material lines"), packingMaterialExepectations.size(), product2qtyMap.size());

		final Iterator<Entry<I_M_Product, BigDecimal>> product2QtyEntriesIterator = product2qtyMap.entrySet().iterator();
		while (product2QtyEntriesIterator.hasNext())
		{
			final Entry<I_M_Product, BigDecimal> product2qty = product2QtyEntriesIterator.next();
			final I_M_Product product = product2qty.getKey();
			final BigDecimal qty = product2qty.getValue();

			findPackingMaterialExpectationForProduct(product)
					.assertExpected(messageToUse, product, qty);
		}

		return this;
	}

	private Map<I_M_Product, BigDecimal> extractPackingMaterialProduct2Qty(final I_M_Movement movement)
	{
		final Map<I_M_Product, BigDecimal> packingMaterialProductId2qty = new HashMap<>();
		final List<I_M_MovementLine> movementLines = InterfaceWrapperHelper.createList(
				Services.get(IMovementDAO.class).retrieveLines(movement),
				I_M_MovementLine.class);
		for (final I_M_MovementLine movementLine : movementLines)
		{
			if (!movementLine.isPackagingMaterial())
			{
				continue;
			}

			final I_M_Product product = movementLine.getM_Product();
			final BigDecimal qty = movementLine.getMovementQty();

			final BigDecimal qtyOld = packingMaterialProductId2qty.put(product, qty);
			if (qtyOld != null)
			{
				throw new AdempiereException("We assumed that there is only one line for each packing material but we found more then one for " + movementLine.getM_Product().getName()
						+ "\n Novement Lines: " + movementLines);
			}
		}

		return packingMaterialProductId2qty;
	}

	/**
	 * Finds expectation for given product
	 *
	 * @param product
	 * @return expectation; never return null
	 */
	private PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>> findPackingMaterialExpectationForProduct(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		return ListUtils.singleElement(this.packingMaterialExepectations, new Predicate<PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>>>()
		{

			@Override
			public boolean evaluate(final PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>> e)
			{
				return product.getM_Product_ID() == e.getM_Product_ID();
			}
		});
	}

	public PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>> newPackingMaterialExpectation()
	{
		final PackingMaterialExpectation<PackingMaterialsExpectation<ParentExpectationType>> e = new PackingMaterialExpectation<>(this);
		packingMaterialExepectations.add(e);
		return e;
	}

	private PackingMaterialsExpectation<ParentExpectationType> addProductAndQty(final I_M_Product product, final BigDecimal qty)
	{
		newPackingMaterialExpectation()
				.product(product)
				.qty(qty);

		return this;
	}

	public PackingMaterialsExpectation<ParentExpectationType> addProductAndQty(final I_M_HU_PI_Item piItemPackingMaterial, final BigDecimal qty)
	{
		return addProductAndQty(piItemPackingMaterial.getM_HU_PackingMaterial().getM_Product(), qty);
	}
}
