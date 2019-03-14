package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.OrgId;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.uom.api.UOMConversionContext;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MProductCategory;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public final class ProductBL implements IProductBL
{
	private static final Logger logger = LogManager.getLogger(ProductBL.class);

	@Override
	public int getUOMPrecision(final I_M_Product product)
	{
		final int uomId = product.getC_UOM_ID();
		return Services.get(IUOMConversionBL.class).getPrecision(uomId);
	}

	@Override
	public int getUOMPrecision(final int productId)
	{
		return getUOMPrecision(ProductId.ofRepoId(productId));
	}

	@Override
	public int getUOMPrecision(@NonNull final ProductId productId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return getUOMPrecision(product);
	}

	@Override
	public String getMMPolicy(final I_M_Product product)
	{
		final MProductCategory pc = MProductCategory.get(Env.getCtx(), product.getM_Product_Category_ID());
		String policy = pc.getMMPolicy();
		if (policy == null || policy.length() == 0)
		{
			policy = Services.get(IClientDAO.class).retriveClient(Env.getCtx()).getMMPolicy();
		}
		return policy;
	}

	@Override
	public String getMMPolicy(final int productId)
	{
		Check.assume(productId > 0, "productId > 0");
		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		return getMMPolicy(product);
	}

	@Override
	public I_C_UOM getStockingUOM(@NonNull final I_M_Product product)
	{
		return Services.get(IUOMDAO.class).getById(product.getC_UOM_ID());
	}

	@Override
	public I_C_UOM getStockingUOM(final int productId)
	{
		// we don't know if the product of productId was already committed, so we can't load it out-of-trx
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		return Check.assumeNotNull(getStockingUOM(product), "The uom for productId={} may not be null", productId);
	}

	/**
	 *
	 * @param product
	 * @return UOM used for Product's Weight; never return null
	 */
	public I_C_UOM getWeightUOM(final I_M_Product product)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);

		// FIXME: we hardcoded the UOM for M_Product.Weight to Kilogram
		return Services.get(IUOMDAO.class).retrieveByX12DE355(ctx, IUOMDAO.X12DE355_Kilogram);
	}

	@Override
	public BigDecimal getWeight(@NonNull final I_M_Product product, @NonNull final I_C_UOM uomTo)
	{
		final BigDecimal weightPerStockingUOM = product.getWeight();
		if (weightPerStockingUOM.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final I_C_UOM stockingUom = getStockingUOM(product);

		//
		// Calculate the rate to convert from stocking UOM to "uomTo"
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(product.getM_Product_ID());
		final BigDecimal stocking2uomToRate = uomConversionBL.convertQty(uomConversionCtx, BigDecimal.ONE, stockingUom, uomTo);

		//
		// Calculate the Weight for one "uomTo"
		final int weightPerUomToPrecision = getWeightUOM(product).getStdPrecision();
		final BigDecimal weightPerUomTo = weightPerStockingUOM
				.multiply(stocking2uomToRate)
				.setScale(weightPerUomToPrecision, RoundingMode.HALF_UP);

		return weightPerUomTo;
	}

	@Override
	public boolean isItem(final I_M_Product product)
	{
		if (X_M_Product.PRODUCTTYPE_Item.equals(product.getProductType()))
		{
			return true;
		}

// @formatter:off
// task 07246: IsDiverse does not automatically mean that the product is an item!
//		final de.metas.adempiere.model.I_M_Product productToUse = InterfaceWrapperHelper.create(product, de.metas.adempiere.model.I_M_Product.class);
//		if (productToUse.isDiverse())
//		{
//			return true;
//		}
// @formatter:on
		return false;
	}

	@Override
	public boolean isItem(@NonNull final ProductId productId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return isItem(product);
	}

	@Override
	public boolean isService(final I_M_Product product)
	{
		// i.e. PRODUCTTYPE_Service, PRODUCTTYPE_Resource, PRODUCTTYPE_Online
		return !isItem(product);
	}

	@Override
	public boolean isStocked(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");

		if (!product.isStocked())
		{
			return false;
		}

		return isItem(product);
	}

	@Override
	public boolean isStocked(final int productId)
	{
		if (productId <= 0)
		{
			return false;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return isStocked(product);
	}

	@Override
	public boolean isDiverse(@NonNull final ProductId productId)
	{
		return Services.get(IProductDAO.class)
				.getById(productId, de.metas.adempiere.model.I_M_Product.class)
				.isDiverse();
	}

	@Override
	public AttributeSetId getAttributeSetId(final I_M_Product product)
	{
		int attributeSetId = product.getM_AttributeSet_ID();
		if (attributeSetId > 0)
		{
			return AttributeSetId.ofRepoId(attributeSetId);
		}
		if (product.getM_Product_Category_ID() <= 0) // guard against NPE which might happen in unit tests
		{
			return AttributeSetId.NONE;
		}

		attributeSetId = product.getM_Product_Category().getM_AttributeSet_ID();
		return attributeSetId > 0 ? AttributeSetId.ofRepoId(attributeSetId) : AttributeSetId.NONE;
	}

	@Override
	public AttributeSetId getAttributeSetId(@NonNull final ProductId productId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final I_M_Product product = productsRepo.getById(productId);
		return getAttributeSetId(product);
	}

	public I_M_AttributeSet getM_AttributeSet(@NonNull final ProductId productId)
	{
		final AttributeSetId attributeSetId = getAttributeSetId(productId);
		if (attributeSetId.isNone())
		{
			return null;
		}
		return Services.get(IAttributeDAO.class).getAttributeSetById(attributeSetId);
	}

	@Override
	public I_M_AttributeSet getM_AttributeSet(I_M_Product product)
	{
		final AttributeSetId attributeSetId = getAttributeSetId(product);
		if (attributeSetId.isNone())
		{
			return null;
		}
		return Services.get(IAttributeDAO.class).getAttributeSetById(attributeSetId);
	}

	@Override
	public I_M_AttributeSetInstance getCreateASI(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID)
	{
		// Load Instance if not 0
		if (M_AttributeSetInstance_ID > 0)
		{
			logger.debug("From M_AttributeSetInstance_ID={}", M_AttributeSetInstance_ID);
			return InterfaceWrapperHelper.create(ctx, M_AttributeSetInstance_ID, I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
		}

		// Get new from Product
		logger.debug("From M_Product_ID={}", M_Product_ID);
		if (M_Product_ID <= 0)
		{
			return null;
		}

		final AttributeSetId attributeSetId = getAttributeSetId(ProductId.ofRepoId(M_Product_ID));
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(ctx, I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
		asi.setM_AttributeSet_ID(attributeSetId.getRepoId());
		return asi;
	}	// get

	@Override
	public boolean isTradingProduct(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		return product.isPurchased()
				&& product.isSold();
	}

	@Override
	public boolean isASIMandatory(@NonNull final I_M_Product product, final boolean isSOTrx)
	{
		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

		final ClientId adClientId = ClientId.ofRepoId(product.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(product.getAD_Org_ID());

		//
		// If CostingLevel is BatchLot ASI is always mandatory - check all client acct schemas
		for (final AcctSchema as : acctSchemasRepo.getAllByClient(adClientId))
		{
			if (as.isDisallowPostingForOrg(adOrgId))
			{
				continue;
			}

			final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, as);
			if (CostingLevel.BatchLot == costingLevel)
			{
				return true;
			}
		}

		//
		// Check Attribute Set settings
		final AttributeSetId attributeSetId = getAttributeSetId(product);
		if (!attributeSetId.isNone())
		{
			final MAttributeSet mas = MAttributeSet.get(attributeSetId);
			if (mas == null || !mas.isInstanceAttribute())
			{
				return false;
			}
			// Outgoing transaction
			else if (isSOTrx)
			{
				return mas.isMandatory();
			}
			// Incoming transaction
			else
			{
				// isSOTrx == false
				return mas.isMandatoryAlways();
			}
		}
		//
		// Default not mandatory
		return false;
	}

	@Override
	public boolean isASIMandatory(@NonNull final ProductId productId, final boolean isSOTrx)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return isASIMandatory(product, isSOTrx);
	}

	@Override
	public boolean isInstanceAttribute(@NonNull final ProductId productId)
	{
		final I_M_AttributeSet mas = getM_AttributeSet(productId);
		return mas != null && mas.isInstanceAttribute();
	}

	@Override
	public boolean isProductInCategory(final ProductId productId, final ProductCategoryId expectedProductCategoryId)
	{
		if (productId == null || expectedProductCategoryId == null)
		{
			return false;
		}

		final ProductCategoryId productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(productId);
		return Objects.equals(productCategoryId, expectedProductCategoryId);
	}

	@Override
	public String getProductValueAndName(final ProductId productId)
	{
		if (productId == null)
		{
			return "-";
		}

		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getValue() + "_" + product.getName();
	}

	@Override
	public String getProductValue(@NonNull final ProductId productId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getValue();
	}

	@Override
	public String getProductName(@NonNull final ProductId productId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getName();
	}
}
