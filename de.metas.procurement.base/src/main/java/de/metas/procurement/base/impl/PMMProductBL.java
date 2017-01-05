package de.metas.procurement.base.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.model.I_PMM_Product;

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

public class PMMProductBL implements IPMMProductBL
{
	@Override
	public void update(final I_PMM_Product pmmProduct)
	{
		//
		// Update PMM_Product from M_HU_PI_Item_Product
		final I_M_HU_PI_Item_Product huPiItemProd = pmmProduct.getM_HU_PI_Item_Product();
		if (huPiItemProd != null)
		{
			pmmProduct.setValidFrom(huPiItemProd.getValidFrom());
			pmmProduct.setValidTo(huPiItemProd.getValidTo());
			pmmProduct.setC_BPartner_ID(huPiItemProd.getC_BPartner_ID());
			pmmProduct.setPackDescription(huPiItemProd.getDescription());

			final I_M_Product product = huPiItemProd.getM_Product();
			pmmProduct.setM_Product(product);
		}
		else
		{
			pmmProduct.setValidFrom(null);
			pmmProduct.setValidTo(null);
			pmmProduct.setC_BPartner(null);
			pmmProduct.setPackDescription(null);
		}

		//
		// Build and set the full product name
		final String productName = PMMProductNameBuilder.newBuilder()
				.setPMM_Product(pmmProduct)
				.build();
		pmmProduct.setProductName(productName);
	}

	@Override
	public void updateByHUPIItemProduct(final I_M_HU_PI_Item_Product huPIItemProduct)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByHUPIItemProduct(huPIItemProduct);
		if (pmmProducts.isEmpty())
		{
			return;
		}

		// Prevent changing the IsActive flag is there are some PMM_Products for it
		if (!huPIItemProduct.isActive())
		{
			throw new AdempiereException("@I_M_HU_PI_Item_Product_ID@ @IsActive@=@N@: " + huPIItemProduct.getDescription());
		}

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			pmmProduct.setM_HU_PI_Item_Product(huPIItemProduct); // optimization
			update(pmmProduct);
		}
	}

	@Override
	public void updateByProduct(final I_M_Product product)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByProduct(product);

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			update(pmmProduct);
			InterfaceWrapperHelper.save(pmmProduct);
		}
	}

	@Override
	public void updateByBPartner(final I_C_BPartner bpartner)
	{
		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveByBPartner(bpartner);

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			update(pmmProduct);
			InterfaceWrapperHelper.save(pmmProduct);
		}
	}

	@Override
	public I_PMM_Product getPMMProductForDateProductAndASI(final Date date, final int productId, final int partnerId, final int huPIPId, final I_M_AttributeSetInstance asi)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final List<I_PMM_Product> pmmProducts = Services.get(IPMMProductDAO.class).retrieveForDateAndProduct(date, productId, partnerId, huPIPId);

		if (pmmProducts.isEmpty())
		{
			// no pmm product found for partner, product and huPIP
			return null;
		}

		final List<I_M_AttributeInstance> instances = attributeDAO.retrieveAttributeInstances(asi);

		// this shows which was the maximum number of instances set and fit in both the lists
		// if it stays -1, there are no suitable pmm products
		int maxmatches = -1;

		// the pmmProduct which fits the most
		I_PMM_Product resultPMMProduct = null;

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{

			final I_M_AttributeSetInstance pmmASI = pmmProduct.getM_AttributeSetInstance();
			final List<I_M_AttributeInstance> pmmAttributeInstances = attributeDAO.retrieveAttributeInstances(pmmASI);

			final int instancesMatchings = countInstanceMatchings(pmmAttributeInstances, instances);

			if (instancesMatchings > maxmatches)
			{
				maxmatches = instancesMatchings;
				resultPMMProduct = pmmProduct;
			}

		}

		return resultPMMProduct;

	}

	/**
	 * This method checks how many of the instance attributes of the two lists have matching {@code value} fields.
	 * If the first list contains instances that don't exist in the second and have a set value, the result will be -1.
	 * If both the lists contain an instance but with different values the result will be -1.
	 * In case the first list is empty, the result will be 0.
	 * The results > 0 will be the number of instances from the second list that are set and have matchings in the first list.
	 * 
	 * @param pmmAttributeInstances
	 * @param instances
	 * @return
	 */
	private int countInstanceMatchings(final List<I_M_AttributeInstance> pmmAttributeInstances, final List<I_M_AttributeInstance> instances)
	{
		// Some AIs don't have a string value, but other sorts of values. 
		// Don't count them for this BL, because they would lead to false negatives.
		final List<I_M_AttributeInstance> pmmAttributeInstancesToUse = pmmAttributeInstances.stream()
				.filter(i -> !Check.isEmpty(i.getValue()))
				.collect(Collectors.toList());
		
		if (pmmAttributeInstancesToUse.isEmpty())
		{
			// there are no attributes in the pmm product. This pmm Product matches for any ASI
			return 0;
		}

		if (instances.isEmpty())
		{
			// in case there are attributes in the pmm product but not in the instances, the pmm product is not eligible
			return -1;
		}

		// if this turns false it means the pmm instances are not a subset of the instances => the pmm product to which the instances belog is not valid
		boolean isValid = true;

		// this counts how many of the instances are set and fit
		int matchings = 0;

		for (final I_M_AttributeInstance pmmAttributeInstance : pmmAttributeInstancesToUse)
		{
			boolean found = false;

			for (final I_M_AttributeInstance instance : instances)
			{
				if (pmmAttributeInstance.getM_Attribute_ID() != instance.getM_Attribute_ID())
				{
					continue;
				}

				// if the values are set but don't match, the pmmAttributeInstances are not valid

				final String pmmStringValue = pmmAttributeInstance.getValue();
				final String instanceStringValue = instance.getValue();

				if (pmmStringValue != null && instanceStringValue == null)
				{
					// not valid
					isValid = false;
					break;
				}

				if (!Check.equals(pmmStringValue,instanceStringValue))
				{
					// not valid
					isValid = false;
					break;
				}

				// the values either fit or are not set in the pmmProduct attribute instances
				found = true;

				// if the values fit, count this one as a matching

				if (pmmStringValue != null)
				{
					matchings++;
				}

			}

			// if the attribute was not found in the ASI, the pmm product is not valid.
			if (!found)
			{
				isValid = false;
				break;
			}
		}

		if (!isValid)
		{
			return -1;
		}

		return matchings;
	}

}
