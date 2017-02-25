package org.adempiere.pricing.api;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ProductPriceQuery
{
	public static final ProductPriceQuery newInstance()
	{
		return new ProductPriceQuery();
	}

	public static final ProductPriceQuery newInstance(final I_M_PriceList_Version plv)
	{
		return new ProductPriceQuery()
				.setContextProvider(plv)
				.setM_PriceList_Version_ID(plv);
	}

	/**
	 * Convenient method to check if the main product price exists.
	 *
	 * @param plv price list version or null
	 * @param productId product (negative values are tolerated)
	 * @return true if exists
	 */
	public static final boolean mainProductPriceExists(final I_M_PriceList_Version plv, final int productId)
	{
		if (plv == null)
		{
			return false;
		}
		if (productId <= 0)
		{
			return false;
		}

		return newMainProductPriceQuery(plv, productId)
				.matches();
	}

	public static final Optional<I_M_ProductPrice> retrieveMainProductPriceIfExists(final I_M_PriceList_Version plv, final int productId)
	{
		final I_M_ProductPrice productPrice = newMainProductPriceQuery(plv, productId)
				.toQuery()
				.firstOnly(I_M_ProductPrice.class);
		return Optional.ofNullable(productPrice);
	}

	private static final ProductPriceQuery newMainProductPriceQuery(final I_M_PriceList_Version plv, final int productId)
	{
		return newInstance(plv)
				.setM_Product_ID(productId)
				.noAttributePricing()
				//
				.addMatchersIfAbsent(MATCHERS_MainProductPrice); // IMORTANT: keep it last
	}

	public static void registerMainProductPriceMatcher(final IProductPriceQueryMatcher matcher)
	{
		Check.assumeNotNull(matcher, "Parameter matcher is not null");
		final boolean added = MATCHERS_MainProductPrice.addIfAbsent(matcher);
		if (!added)
		{
			logger.warn("Main product matcher {} was not registered because it's a duplicate: {}", matcher, MATCHERS_MainProductPrice);
		}
		else
		{
			logger.info("Registered main product matcher: {}", matcher);
		}
	}

	private static final Logger logger = LogManager.getLogger(ProductPriceQuery.class);

	private static final CopyOnWriteArrayList<IProductPriceQueryMatcher> MATCHERS_MainProductPrice = new CopyOnWriteArrayList<>();

	private Object _contextProvider;
	private int _priceListVersionId;
	private int _productId;

	private Boolean _attributePricing;
	private I_M_AttributeSetInstance _attributePricing_asiToMatch;

	private Boolean _scalePrice;

	private Map<String, IProductPriceQueryMatcher> _additionalMatchers = null;

	private ProductPriceQuery()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("priceListVersionId", _priceListVersionId)
				.add("productId", _productId)
				//
				.add("attributePricing", _attributePricing)
				.add("asiToMatch", _attributePricing_asiToMatch)
				//
				.add("scalePrice", _scalePrice)
				//
				.add("additionalMatchers", _additionalMatchers == null || _additionalMatchers.isEmpty() ? null : _additionalMatchers)
				.toString();
	}

	/** @return first matching product price or null */
	public I_M_ProductPrice firstMatching()
	{
		return firstMatching(I_M_ProductPrice.class);
	}

	/** @return first matching product price or null */
	public <T extends I_M_ProductPrice> T firstMatching(final Class<T> type)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = toQueryBuilder();

		queryBuilder.orderBy()
				.clear()
				.addColumn(I_M_ProductPrice.COLUMN_M_Product_ID, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_ProductPrice.COLUMN_MatchSeqNo, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, Direction.Ascending, Nulls.Last); // just to have a predictable order

		return queryBuilder.create().first(type);
	}

	/** @return true if there is at least one product price that matches */
	public boolean matches()
	{
		return toQuery().match();
	}

	public I_M_ProductPrice retrieveDefault(final boolean strictDefault)
	{
		return retrieveDefault(strictDefault, I_M_ProductPrice.class);
	}

	public <T extends I_M_ProductPrice> T retrieveDefault(final boolean strictDefault, final Class<T> type)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = toQueryBuilder();
		if (strictDefault)
		{
			queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_IsDefault, true);
		}
		queryBuilder.orderBy()
				// Take records with IsDefault=Y first
				.addColumn(I_M_ProductPrice.COLUMN_IsDefault, Direction.Descending, Nulls.Last)
				// Take records with lowest MatchSeqNo first
				.addColumn(I_M_ProductPrice.COLUMN_MatchSeqNo, Direction.Ascending, Nulls.Last);

		if (strictDefault)
		{
			final T strictDefaultFirstTry = queryBuilder.create().firstOnly(type);
			if (strictDefaultFirstTry != null)
			{
				logger.debug("Returning M_ProductPrice {} with IsDefault='Y' for {} (param 'strictDefault'==true).", strictDefaultFirstTry, this);
				return strictDefaultFirstTry;
			}
		}
		else
		{
			// NOTE: we can have more then one result; we take the first one
			final T nonStrictTry = queryBuilder.create().first(type);
			logger.debug("Returning M_ProductPrice {} for {} (param 'strictDefault'==false).", nonStrictTry, this);
			return nonStrictTry;
		}

		// task 09051: strictDefault = true, but there is no M_ProductPrice_Attribute with IsDefault='Y'
		// However, if there is just *one* single M_ProductPrice_Attribute, we can safely return that one as the default, even if it does not have the flag set.
		// It turned out that this is also consistent with the customer's intuition, so it will save us training/explaining effort :-)
		final T strictDefaultSecondTry = toQueryBuilder()
				.create()
				.firstOnlyOrNull(type);
		logger.debug("Returning *the only active* M_ProductPrice {} for {} as default despite it has IsDefault='N' (param 'strictDefault'==true).", strictDefaultSecondTry, this);
		return strictDefaultSecondTry;
	}

	private IQuery<I_M_ProductPrice> toQuery()
	{
		return toQueryBuilder().create();
	}

	private IQueryBuilder<I_M_ProductPrice> toQueryBuilder()
	{
		final Object contextProvider = getContextProvider();

		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class, contextProvider)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, getM_PriceList_Version_ID())
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, getM_Product_ID())
				.addOnlyActiveRecordsFilter();

		//
		// Attribute pricing records
		final Boolean attributePricing = getAttributePricing();
		if (attributePricing != null)
		{
			// Attributes matching enabled => match given ASI (if any)
			if (attributePricing)
			{
				queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, true);

				final I_M_AttributeSetInstance attributePricingASIToMatch = getAttributePricingASIToMatch();
				if (attributePricingASIToMatch != null)
				{
					queryBuilder.filter(ASIProductPriceAttributesFilter.of(attributePricingASIToMatch));
				}
			}
			// Attributes matching disabled => match only those product prices which are not attribute dependent
			else
			{
				queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, false);
			}
		}

		//
		// Scale price
		final Boolean scalePrice = getScalePrice();
		if (scalePrice != null)
		{
			queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_UseScalePrice, scalePrice);
		}

		//
		// Additional filters
		final Collection<IProductPriceQueryMatcher> additionalMatchers = getAdditionalMatchers();
		if (!additionalMatchers.isEmpty())
		{
			additionalMatchers.forEach(matcher -> queryBuilder.filter(matcher.getQueryFilter()));
		}

		//
		// Ordering
		// NOTE: we don't know the best ordering at this point!
		// queryBuilder.orderBy()
		// .addColumn(I_M_ProductPrice.COLUMN_IsAttributeDependant, Direction.Ascending, Nulls.Last)
		// .addColumn(I_M_ProductPrice.COLUMN_MatchSeqNo, Direction.Ascending, Nulls.Last)
		// .addColumn(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, Direction.Ascending, Nulls.Last);

		return queryBuilder;
	}

	public ProductPriceQuery setContextProvider(final Object contextProvider)
	{
		_contextProvider = contextProvider;
		return this;
	}

	public ProductPriceQuery setContextProvider(final Properties ctx, final String trxName)
	{
		setContextProvider(PlainContextAware.newWithTrxName(ctx, trxName));
		return this;
	}

	private Object getContextProvider()
	{
		Check.assumeNotNull(_contextProvider, "Parameter contextProvider is not null for {}", this);
		return _contextProvider;
	}

	public ProductPriceQuery setM_PriceList_Version_ID(final int priceListVersionId)
	{
		_priceListVersionId = priceListVersionId;
		return this;
	}

	public ProductPriceQuery setM_PriceList_Version_ID(final I_M_PriceList_Version priceListVersion)
	{
		setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		return this;
	}

	private int getM_PriceList_Version_ID()
	{
		Check.assume(_priceListVersionId > 0, "priceListVersionId > 0 for {}", this);
		return _priceListVersionId;
	}

	public ProductPriceQuery setM_Product_ID(final int productId)
	{
		_productId = productId;
		return this;
	}

	public ProductPriceQuery setM_Product_ID(final I_M_Product product)
	{
		setM_Product_ID(product == null ? -1 : product.getM_Product_ID());
		return this;
	}

	private int getM_Product_ID()
	{
		Check.assume(_productId > 0, "product shall be set for {}", this);
		return _productId;
	}

	/** Matches product price which is NOT marked as "attributed pricing" */
	public ProductPriceQuery noAttributePricing()
	{
		_attributePricing = Boolean.FALSE;
		_attributePricing_asiToMatch = null;
		return this;
	}

	/** Matches any product price which is marked as "attributed pricing" */
	public ProductPriceQuery onlyAttributePricing()
	{
		_attributePricing = Boolean.TRUE;
		// _attributePricing_asiToMatch = null;
		return this;
	}

	/**
	 * Matches product price which is marked as "attributed pricing" and the given ASI is matching.
	 * If <code>asi</code> is null then any "attributed pricing" will be matched.
	 *
	 * @param asi ASI to match or <code>null</code>
	 */
	public ProductPriceQuery matchingAttributes(final I_M_AttributeSetInstance asi)
	{
		_attributePricing = Boolean.TRUE;
		_attributePricing_asiToMatch = asi;
		return this;
	}
	
	public ProductPriceQuery dontMatchAttributes()
	{
		_attributePricing = null;
		_attributePricing_asiToMatch = null;
		return this;
	}

	private Boolean getAttributePricing()
	{
		return _attributePricing;
	}

	private I_M_AttributeSetInstance getAttributePricingASIToMatch()
	{
		return _attributePricing_asiToMatch;
	}

	public ProductPriceQuery onlyScalePrices()
	{
		_scalePrice = Boolean.TRUE;
		return this;
	}

	private Boolean getScalePrice()
	{
		return _scalePrice;
	}

	public ProductPriceQuery matching(final IProductPriceQueryMatcher matcher)
	{
		Check.assumeNotNull(matcher, "Parameter matcher is not null");

		if (_additionalMatchers == null)
		{
			_additionalMatchers = new LinkedHashMap<>();
		}
		_additionalMatchers.put(matcher.getName(), matcher);

		return this;
	}

	private final ProductPriceQuery addMatchersIfAbsent(final Collection<IProductPriceQueryMatcher> matchers)
	{
		if (matchers == null || matchers.isEmpty())
		{
			return this;
		}

		if (_additionalMatchers == null)
		{
			_additionalMatchers = new LinkedHashMap<>();
		}
		matchers.forEach(matcher -> _additionalMatchers.putIfAbsent(matcher.getName(), matcher));

		return this;
	}

	public ProductPriceQuery matching(final Collection<IProductPriceQueryMatcher> matchers)
	{
		if (matchers == null || matchers.isEmpty())
		{
			return this;
		}

		matchers.forEach(this::matching);

		return this;
	}

	private Collection<IProductPriceQueryMatcher> getAdditionalMatchers()
	{
		if (_additionalMatchers == null)
		{
			return ImmutableList.of();
		}
		return _additionalMatchers.values();
	}

	public static interface IProductPriceQueryMatcher
	{
		String getName();

		IQueryFilter<I_M_ProductPrice> getQueryFilter();
	}

	public static final class ProductPriceQueryMatcher implements IProductPriceQueryMatcher
	{
		public static final ProductPriceQueryMatcher of(final String name, final IQueryFilter<I_M_ProductPrice> filter)
		{
			return new ProductPriceQueryMatcher(name, filter);
		}

		private final String name;
		private final IQueryFilter<I_M_ProductPrice> filter;

		private ProductPriceQueryMatcher(final String name, final IQueryFilter<I_M_ProductPrice> filter)
		{
			Check.assumeNotEmpty(name, "name is not empty");
			Check.assumeNotNull(filter, "Parameter filter is not null");
			this.name = name;
			this.filter = filter;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("name", name)
					.add("filter", filter)
					.toString();
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public IQueryFilter<I_M_ProductPrice> getQueryFilter()
		{
			return filter;
		}

	}

	private static final class ASIProductPriceAttributesFilter implements IQueryFilter<I_M_ProductPrice>
	{
		public static final ASIProductPriceAttributesFilter of(final I_M_AttributeSetInstance asi)
		{
			return new ASIProductPriceAttributesFilter(asi);
		}

		private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		private final I_M_AttributeSetInstance _asi;
		private transient Map<Integer, I_M_AttributeInstance> _asiAttributes;

		private ASIProductPriceAttributesFilter(final I_M_AttributeSetInstance asi)
		{
			Check.assumeNotNull(asi, "Parameter asi is not null");
			_asi = asi;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(_asi).toString();
		}

		@Override
		public boolean accept(final I_M_ProductPrice productPrice)
		{
			// Guard against null, shall not happen
			if (productPrice == null)
			{
				return false;
			}

			// Consider only those product prices which have the attributes matching option enabled
			if (!productPrice.isAttributeDependant())
			{
				return false;
			}

			// If our ASI does not have attributes set, consider this product price as matching
			final Map<Integer, I_M_AttributeInstance> asiAttributes = getASIAttributes();
			if (asiAttributes.isEmpty())
			{
				return true;
			}

			// If there are no expected attributes (in product price),
			// consider it as matching
			final List<I_M_AttributeInstance> expectedAttributes = extractProductPriceAttributes(productPrice);
			if (expectedAttributes.isEmpty())
			{
				return true;
			}

			for (final I_M_AttributeInstance expectedAttribute : expectedAttributes)
			{
				final int attributeId = expectedAttribute.getM_Attribute_ID();
				final I_M_AttributeInstance asiAttribute = asiAttributes.get(attributeId);
				if (!isAttributeInstanceMatching(expectedAttribute, asiAttribute))
				{
					return false;
				}
			}

			return true;
		}

		private static boolean isAttributeInstanceMatching(final I_M_AttributeInstance expected, final I_M_AttributeInstance actual)
		{
			final int expectedAttributeValueId = Util.firstGreaterThanZero(expected.getM_AttributeValue_ID(), 0);
			
			final int actualAttributeValueId;
			if (actual == null)
			{
				actualAttributeValueId = 0;
			}
			else
			{
				actualAttributeValueId = Util.firstGreaterThanZero(actual.getM_AttributeValue_ID(), 0);
			}

			if (expectedAttributeValueId != actualAttributeValueId)
			{
				return false;
			}

			return true;
		}

		private Map<Integer, I_M_AttributeInstance> getASIAttributes()
		{
			if (_asiAttributes == null)
			{
				final List<I_M_AttributeInstance> asiAttributesList = attributeDAO.retrieveAttributeInstances(_asi);
				_asiAttributes = Maps.uniqueIndex(asiAttributesList, I_M_AttributeInstance::getM_Attribute_ID);
			}
			return _asiAttributes;
		}

		private final List<I_M_AttributeInstance> extractProductPriceAttributes(final I_M_ProductPrice productPrice)
		{
			final I_M_AttributeSetInstance productPriceASI = productPrice.getM_AttributeSetInstance();
			if (productPriceASI == null || productPriceASI.getM_AttributeSetInstance_ID() <= 0)
			{
				return ImmutableList.of();
			}

			final List<I_M_AttributeInstance> productPriceAttributes = attributeDAO.retrieveAttributeInstances(productPriceASI);
			return productPriceAttributes;
		}
	}
}
