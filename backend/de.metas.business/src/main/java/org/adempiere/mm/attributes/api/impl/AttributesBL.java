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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.JavaClassId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValueHandler;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Properties;

public class AttributesBL implements IAttributesBL
{
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ISysConfigBL sysConfigs = Services.get(ISysConfigBL.class);
	private final IJavaClassDAO javaClassDAO = Services.get(IJavaClassDAO.class);
	private final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@VisibleForTesting
	public static final String SYSCONFIG_AttributeAction = "de.metas.swat.AttributeAction";
	public static final AdMessageKey MSG_NoAttributeGenerator = AdMessageKey.of("de.metas.swat.Attribute.generatorError");

	private static final MathContext DEFAULT_MATHCONTEXT = new MathContext(2, RoundingMode.HALF_UP);

	@Override
	public I_M_Attribute getAttributeById(@NonNull final AttributeId attributeId)
	{
		return attributesRepo.getAttributeRecordById(attributeId);
	}

	@Override
	public @NonNull AttributeAction getAttributeAction(final Properties ctx)
	{
		final String attributeActionCode = sysConfigs.getValue(SYSCONFIG_AttributeAction, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		if (Check.isEmpty(attributeActionCode, true))
		{
			return AttributeAction.Ignore;
		}
		return AttributeAction.forCode(attributeActionCode);
	}

	@Override
	public @NonNull IAttributeValueGenerator getAttributeValueGenerator(final org.compiere.model.I_M_Attribute attributeParam)
	{
		final IAttributeValueGenerator generator = getAttributeValueGeneratorOrNull(attributeParam);
		if (generator == null)
		{
			throw new AdempiereException(AttributesBL.MSG_NoAttributeGenerator, attributeParam.getName());
		}

		return generator;
	}

	@Override
	public @NonNull IAttributeValueGenerator getAttributeValueGenerator(final Attribute attribute)
	{
		final IAttributeValueGenerator generator = getAttributeValueGeneratorOrNull(attribute);
		if (generator == null)
		{
			throw new AdempiereException(AttributesBL.MSG_NoAttributeGenerator, attribute.getDisplayName());
		}

		return generator;
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final AttributeId attributeId)
	{
		final I_M_Attribute attribute = attributesRepo.getAttributeRecordById(attributeId);
		return createAttributeValuesProvider(attribute);
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final org.compiere.model.I_M_Attribute attribute)
	{
		return createAttributeValuesProvider(AttributeDAO.fromRecord(attribute));
	}

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(final Attribute attribute)
	{
		final IAttributeValueHandler attributeHandler = getAttributeValueHandlerOrNull(attribute);

		//
		// First try: check if attributeHandler is implementing IAttributeValuesProvider and return it if that's the case
		if (attributeHandler instanceof IAttributeValuesProviderFactory)
		{
			final IAttributeValuesProviderFactory factory = (IAttributeValuesProviderFactory)attributeHandler;
			return factory.createAttributeValuesProvider(attribute);
		}
		//
		// Second try: check if our attribute is of type list, in which case we are dealing with standard M_AttributeValues
		else if (attribute.getValueType().isList())
		{
			return new DefaultAttributeValuesProvider(attributesRepo, attribute);
		}
		//
		// Fallback: there is no IAttributeValuesProvider because attribute does not support Lists
		else
		{
			return null;
		}
	}

	@Override
	public IAttributeValueGenerator getAttributeValueGeneratorOrNull(@NonNull final org.compiere.model.I_M_Attribute attributeParam)
	{
		final IAttributeValueHandler handler = getAttributeValueHandlerOrNull(attributeParam);
		if (handler instanceof IAttributeValueGenerator)
		{
			return (IAttributeValueGenerator)handler;
		}
		return null;
	}

	@Override
	public IAttributeValueGenerator getAttributeValueGeneratorOrNull(@NonNull final Attribute attribute)
	{
		final IAttributeValueHandler handler = getAttributeValueHandlerOrNull(attribute);
		if (handler instanceof IAttributeValueGenerator)
		{
			return (IAttributeValueGenerator)handler;
		}
		return null;
	}

	private IAttributeValueHandler getAttributeValueHandlerOrNull(final org.compiere.model.I_M_Attribute attributeRecord)
	{
		final JavaClassId listValuesProviderJavaClassId = AttributeDAO.extractListValuesProviderJavaClassId(attributeRecord);
		return listValuesProviderJavaClassId != null
				? javaClassBL.newInstance(listValuesProviderJavaClassId)
				: null;
	}

	private IAttributeValueHandler getAttributeValueHandlerOrNull(final Attribute attribute)
	{
		return attribute.getListValuesProviderJavaClassId() != null
				? javaClassBL.newInstance(attribute.getListValuesProviderJavaClassId())
				: null;
	}

	@Nullable
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
		final AttributeSetId attributeSetId = productsService.getAttributeSetId(productId);

		//
		// Get M_Attribute
		final org.compiere.model.I_M_Attribute attribute = attributesRepo.retrieveAttribute(attributeSetId, attributeId);
		if (attribute == null)
		{
			// The product's attribute set doesn't contain the needed attribute. Do nothing.
			return null;
		}

		return InterfaceWrapperHelper.create(attribute, I_M_Attribute.class);
	}

	@Override
	public boolean hasAttributeAssigned(@NonNull final ProductId productId, @NonNull final AttributeId attributeId)
	{
		final AttributeSetId attributeSetId = productsService.getAttributeSetId(productId);
		return attributesRepo.getAttributeSetDescriptorById(attributeSetId).contains(attributeId);
	}

	@Override
	public boolean isMandatoryOnReceipt(@NonNull final ProductId productId, @NonNull final AttributeId attributeId)
	{
		final AttributeSetId attributeSetId = productsService.getAttributeSetId(productId);
		final Boolean mandatoryOnReceipt = attributesRepo.getAttributeSetDescriptorById(attributeSetId)
				.getMandatoryOnReceipt(attributeId)
				.toBooleanOrNull();
		if (mandatoryOnReceipt != null)
		{
			return mandatoryOnReceipt;
		}

		return attributesRepo.getAttributeRecordById(attributeId).isMandatory();
	}

	@Override
	public boolean isMandatoryOnShipment(@NonNull final ProductId productId, @NonNull final AttributeId attributeId)
	{
		final AttributeSetId attributeSetId = productsService.getAttributeSetId(productId);
		final Boolean mandatoryOnShipment = attributesRepo.getAttributeSetDescriptorById(attributeSetId)
				.getMandatoryOnShipment(attributeId)
				.toBooleanOrNull();
		if (mandatoryOnShipment != null)
		{
			return mandatoryOnShipment;
		}

		return attributesRepo.getAttributeRecordById(attributeId).isMandatory();
	}

	@Override
	public ImmutableList<Attribute> getAttributesMandatoryOnPicking(final ProductId productId)
	{
		final AttributeSetId attributeSetId = productBL.getAttributeSetId(productId);
		return attributesRepo.getAttributesByAttributeSetId(attributeSetId).stream()
				.filter(attribute -> isMandatoryOnPicking(productId, attribute.getAttributeId()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public ImmutableList<Attribute> getAttributesMandatoryOnShipment(final ProductId productId)
	{
		final AttributeSetId attributeSetId = productBL.getAttributeSetId(productId);
		return attributesRepo.getAttributesByAttributeSetId(attributeSetId).stream()
				.filter(attribute -> isMandatoryOnShipment(productId, attribute.getAttributeId()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public boolean isMandatoryOnPicking(@NonNull final ProductId productId, @NonNull final AttributeId attributeId)
	{
		final AttributeSetId attributeSetId = productsService.getAttributeSetId(productId);
		final Boolean mandatoryOnPicking = attributesRepo.getAttributeSetDescriptorById(attributeSetId)
				.getMandatoryOnPicking(attributeId)
				.toBooleanOrNull();
		if (mandatoryOnPicking != null)
		{
			return mandatoryOnPicking;
		}

		return attributesRepo.getAttributeRecordById(attributeId).isMandatory();
	}

	@Override
	public MathContext getMathContext(final org.compiere.model.I_M_Attribute attribute)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		final I_C_UOM uom = uomDAO.getByIdOrNull(attribute.getC_UOM_ID());

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
		final I_M_Product product = productsService.getById(productId);
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());
		//
		// Get Best-Before days
		final I_C_BPartner_Product bpartnerProduct = bpartnerProductDAO.retrieveBPartnerProductAssociation(ctx, vendorBPartnerId, productId, orgId);
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
		return TimeUtil.addDays(dateReceipt, bestBeforeDays);
	}

	@Override
	public int getNumberDisplayType(@NonNull final I_M_Attribute attribute)
	{
		return isInteger(UomId.ofRepoIdOrNull(attribute.getC_UOM_ID()))
				? DisplayType.Integer
				: DisplayType.Number;
	}

	public static boolean isInteger(@Nullable final UomId uomId)
	{
		return uomId != null && UomId.equals(uomId, UomId.EACH);
	}

	@Override
	public boolean isStorageRelevant(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return attribute.isStorageRelevant();
	}

	@Override
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final AttributeId attributeId, @Nullable final String value)
	{
		return attributesRepo.retrieveAttributeValueOrNull(attributeId, value);
	}
}
