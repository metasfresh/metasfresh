package de.metas.procurement.base.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.purchasing.api.IBPartnerProductDAO;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link I_PMM_Product} ProductName builder.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PMMProductNameBuilder
{
	public static final PMMProductNameBuilder newBuilder()
	{
		return new PMMProductNameBuilder();
	}

	private static final Joiner PRODUCTNAME_Joiner = Joiner.on(" - ").skipNulls();

	private I_PMM_Product pmmProduct;
	private Optional<I_C_BPartner_Product> _bpartnerProduct;
	private String _productNamePartIfUsingMProduct;
	private boolean _productNamePartIfUsingMProductSet;
	private Optional<String> _asiDescription;

	private PMMProductNameBuilder()
	{
		super();
	}

	public String build()
	{
		final String productNamePart = getProductNamePart();
		final String asiDescription = getASIDescription();
		final String productName = PRODUCTNAME_Joiner.join(productNamePart, asiDescription);
		return productName;
	}

	public PMMProductNameBuilder setPMM_Product(final I_PMM_Product pmmProduct)
	{
		this.pmmProduct = pmmProduct;

		// reset cached values
		_bpartnerProduct = null;
		_asiDescription = null;

		return this;
	}

	private I_PMM_Product getPMM_Product()
	{
		return pmmProduct;
	}

	private I_C_BPartner_Product getC_BPartner_Product()
	{
		if (_bpartnerProduct == null)
		{
			I_C_BPartner_Product bpartnerProduct = null;
			if (pmmProduct.getC_BPartner_ID() > 0 && pmmProduct.getM_Product_ID() > 0)
			{
				final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);

				final I_C_BPartner bpartner = pmmProduct.getC_BPartner();
				final I_M_Product product = pmmProduct.getM_Product();
				final int orgId = product.getAD_Org_ID();

				bpartnerProduct = InterfaceWrapperHelper.create(bpartnerProductDAO.retrieveBPartnerProductAssociation(bpartner, product, orgId), I_C_BPartner_Product.class);
				if (bpartnerProduct != null && !Check.isEmpty(bpartnerProduct.getProductName(), true))
				{
					bpartnerProduct = null;
				}
			}
			
			_bpartnerProduct = Optional.fromNullable(bpartnerProduct);
		}

		return _bpartnerProduct.orNull();
	}

	public PMMProductNameBuilder setProductNamePartIfUsingMProduct(final String productNamePart)
	{
		_productNamePartIfUsingMProduct = productNamePart;
		_productNamePartIfUsingMProductSet = true;
		return this;
	}

	private String getProductNamePart()
	{
		if (_productNamePartIfUsingMProductSet)
		{
			return _productNamePartIfUsingMProduct;
		}

		final I_PMM_Product pmmProduct = getPMM_Product();
		if (pmmProduct == null)
		{
			return null;
		}

		String productNamePart = null;

		//
		// Get from C_BPartner_Product, if any
		final I_C_BPartner_Product bpartnerProduct = getC_BPartner_Product();
		if (bpartnerProduct != null && !Check.isEmpty(bpartnerProduct.getProductName(), true))
		{
			productNamePart = bpartnerProduct.getProductName();
		}

		//
		// Get form M_Product.Name
		if (Check.isEmpty(productNamePart, true) && pmmProduct.getM_Product() != null)
		{
			if (_productNamePartIfUsingMProductSet)
			{
				productNamePart = _productNamePartIfUsingMProduct;
			}
			else
			{
				productNamePart = pmmProduct.getM_Product().getName();
			}
		}

		// Normalize product name
		if (Check.isEmpty(productNamePart, true))
		{
			productNamePart = null;
		}

		return productNamePart;
	}

	private String getASIDescription()
	{
		if (_asiDescription == null)
		{
			final I_PMM_Product pmmProduct = getPMM_Product();
			if (pmmProduct == null)
			{
				return null;
			}

			//
			// Get ASI description if any
			String asiDescription = null;
			if (pmmProduct.getM_AttributeSetInstance_ID() > 0)
			{
				final I_M_AttributeSetInstance asi = pmmProduct.getM_AttributeSetInstance();
				asiDescription = asi.getDescription();
				if (Check.isEmpty(asiDescription, true))
				{
					asiDescription = null;
				}
			}

			_asiDescription = Optional.fromNullable(asiDescription);
		}

		return _asiDescription.orNull();
	}
}
