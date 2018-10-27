package org.adempiere.mm.attributes.api.impl;

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

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.model.X_M_AttributeValue;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class AttributesBL implements IAttributesBL
{
	public static final String SYSCONFIG_AttributeAction = "de.metas.swat.AttributeAction";
	private static final String MSG_NoAttributeGenerator = "de.metas.swat.Attribute.generatorError";

	private static final MathContext DEFAULT_MATHCONTEXT = new MathContext(2, RoundingMode.HALF_UP);

	@Override
	public AttributeAction getAttributeAction(final Properties ctx)
	{
		final String attributeActionCode = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_AttributeAction, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		if (Check.isEmpty(attributeActionCode, true))
		{
			return AttributeAction.Ignore;
		}
		return AttributeAction.forCode(attributeActionCode);
	}

	@Override
	public IAttributeValueGenerator getAttributeValueGenerator(final org.compiere.model.I_M_Attribute attributeParam)
	{
		final IAttributeValueGenerator generator = getAttributeValueGeneratorOrNull(attributeParam);
		if (generator == null)
		{
			throw new AdempiereException(AttributesBL.MSG_NoAttributeGenerator, new Object[] { attributeParam.getName() });
		}

		return generator;
	}

	@Override
	public IAttributeValueGenerator getAttributeValueGeneratorOrNull(final org.compiere.model.I_M_Attribute attributeParam)
	{
		Check.assumeNotNull(attributeParam, "attributeParam not null");

		final I_M_Attribute attribute = InterfaceWrapperHelper.create(attributeParam, I_M_Attribute.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		final I_AD_JavaClass javaClassDef = Services.get(IJavaClassDAO.class).retriveJavaClassOrNull(ctx, attribute.getAD_JavaClass_ID());
		if (javaClassDef == null)
		{
			return null;
		}

		final IAttributeValueGenerator generator = Services.get(IJavaClassBL.class).newInstance(javaClassDef);
		return generator;
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final org.compiere.model.I_M_Attribute attribute)
	{
		final IAttributeValueGenerator attributeHandler = getAttributeValueGeneratorOrNull(attribute);

		//
		// First try: check if attributeHandler is implementing IAttributeValuesProvider and return it if that's the case
		if (attributeHandler instanceof IAttributeValuesProviderFactory)
		{
			IAttributeValuesProviderFactory factory = (IAttributeValuesProviderFactory)attributeHandler;
			return factory.createAttributeValuesProvider(attribute);
		}
		//
		// Second try: check if our attribute is of type list, in which case we are dealing with standard M_AttributeValues
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			return new DefaultAttributeValuesProvider(attribute);
		}
		//
		// Fallback: there is no IAttributeValuesProvider because attribute does not support Lists
		else
		{
			return null;
		}
	}

	@Override
	public I_M_Attribute getAttributeOrNull(final ProductId productId, final AttributeId attributeId)
	{
		//
		// Check Product
		if (productId == null)
		{
			// No product. Do nothing
			return null;
		}

		//
		// Check M_Attribute_ID
		if (attributeId == null)
		{
			// We don't have an attribute set. Do nothing.
			return null;
		}

		//
		// Check Attribute Set
		final AttributeSetId attributeSetId = Services.get(IProductBL.class).getAttributeSetId(productId);

		//
		// Get M_Attribute
		final org.compiere.model.I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttribute(attributeSetId, attributeId);
		if (attribute == null)
		{
			// The product's attribute set doesn't contain the needed attribute. Do nothing.
			return null;
		}

		return InterfaceWrapperHelper.create(attribute, I_M_Attribute.class);
	}

	@Override
	public boolean isSameTrx(final I_M_AttributeValue attributeValue, final boolean isSOTrx)
	{
		final boolean attributeSOTrx;
		final String attributeTrx = attributeValue.getAvailableTrx();
		if (attributeTrx == null)
		{
			// always accept, return right away
			return true;
		}
		else if (X_M_AttributeValue.AVAILABLETRX_SO.equals(attributeTrx))
		{
			attributeSOTrx = true;
		}
		else if (X_M_AttributeValue.AVAILABLETRX_PO.equals(attributeTrx))
		{
			attributeSOTrx = false;
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @AvailableTrx@: " + attributeTrx);
		}

		return attributeSOTrx == isSOTrx;
	}

	@Override
	public MathContext getMathContext(final org.compiere.model.I_M_Attribute attribute)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		final I_C_UOM uom = attribute.getC_UOM();

		final MathContext mc;
		if (uom != null)
		{
			mc = new MathContext(uom.getStdPrecision(), RoundingMode.HALF_UP);
		}
		else
		{
			mc = AttributesBL.DEFAULT_MATHCONTEXT;
		}
		return mc;
	}

	@Override
	public Date calculateBestBeforeDate(
			final Properties ctx,
			final ProductId productId,
			final BPartnerId vendorBPartnerId,
			@NonNull final Date dateReceipt)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());
		//
		// Get Best-Before days
		final I_C_BPartner_Product bpartnerProduct = Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(ctx, vendorBPartnerId, productId, orgId);
		if (bpartnerProduct == null)
		{
			// no BPartner-Product association defined, so we cannot fetch the bestBeforeDays
			return null;
		}
		final int bestBeforeDays = bpartnerProduct.getShelfLifeMinDays(); // TODO: i think we shall introduce BestBeforeDays
		if (bestBeforeDays <= 0)
		{
			// BestBeforeDays was not configured
			return null;
		}

		//
		// Calculate the Best-Before date
		final Date bestBeforeDate = TimeUtil.addDays(dateReceipt, bestBeforeDays);
		return bestBeforeDate;
	}

}
