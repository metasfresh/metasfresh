package de.metas.pricing.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderOrderByClause;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

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

/**
 * Hint: use {@link ProductPrices} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProductPriceQuery
{
	public enum AttributePricing
	{
		/** Only match product prices with IsAttributeDependent='Y' */
		STRICT,

		/** Prefer product prices with IsAttributeDependent='Y', but also allow product prices with IsAttributeDependent='N' */
		NOT_STRICT,

		/** Only match product prices with IsAttributeDependent='N' */
		NONE,

		/** No ASI related matching at all */
		IGNORE;
	}

	private static final Logger logger = LogManager.getLogger(ProductPriceQuery.class);

	private PriceListVersionId _priceListVersionId;
	private ProductId _productId;

	private AttributePricing _attributePricing = AttributePricing.IGNORE;
	private I_M_AttributeSetInstance _attributePricing_asiToMatch;

	private Boolean _scalePrice;

	private Map<String, IProductPriceQueryMatcher> _additionalMatchers = null;

	private boolean _onlyValidPrices = true;

	/* package */ ProductPriceQuery()
	{
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
				.add("onlyValidPrices", _onlyValidPrices)
				.add("asiToMatch", _attributePricing_asiToMatch)
				//
				.add("scalePrice", _scalePrice)
				//
				.add("additionalMatchers", _additionalMatchers == null || _additionalMatchers.isEmpty() ? null : _additionalMatchers)
				.toString();
	}

	public List<I_M_ProductPrice> list()
	{
		return toQuery().list();
	}

	public <T extends I_M_ProductPrice> List<T> list(final Class<T> type)
	{
		return toQuery().list(type);
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

		final IQueryBuilderOrderByClause<I_M_ProductPrice> orderBy = queryBuilder.orderBy();
		orderBy.clear(); // note: orderBy is NOT immutable

		if (AttributePricing.NOT_STRICT.equals(_attributePricing))
		{ // we might have product prices with IsAttributeDependant both 'Y' and 'N'; prefer those with 'Y'.
			orderBy.addColumnDescending(I_M_ProductPrice.COLUMNNAME_IsAttributeDependant);
		}
		orderBy.addColumn(I_M_ProductPrice.COLUMN_M_Product_ID, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_ProductPrice.COLUMN_MatchSeqNo, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_ProductPrice.COLUMN_M_ProductPrice_ID, Direction.Ascending, Nulls.Last); // just to have a predictable order

		return queryBuilder.create().first(type);
	}

	/** @return true if there is at least one product price that matches */
	boolean matches()
	{
		return toQuery().anyMatch();
	}

	public <T extends I_M_ProductPrice> T retrieveStrictDefault(@NonNull final Class<T> type)
	{
		boolean strictDefault = true;
		return retrieveDefault(strictDefault, type);
	}

	public <T extends I_M_ProductPrice> T retrieveDefault(@NonNull final Class<T> type)
	{
		boolean strictDefault = false;
		return retrieveDefault(strictDefault, type);
	}

	/**
	 *
	 * @param strictDefault if {@code true}, the method throws an exception if there is more than one match.
	 *            If {@code false, it silently returns the first match which has the lowest sequence number.
	 * 			@param type
	 * @return
	 */
	private <T extends I_M_ProductPrice> T retrieveDefault(final boolean strictDefault, @NonNull final Class<T> type)
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
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, getPriceListVersionId())
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, getProductId());

		// Ignore invalid prices
		final boolean isOnlyValidPrices = isOnlyValidPrices();

		if (isOnlyValidPrices)
		{
			queryBuilder.addNotEqualsFilter(I_M_ProductPrice.COLUMN_IsInvalidPrice, true);
		}

		//
		// Attribute pricing records
		switch (_attributePricing)
		{
			case NONE:
				// Attributes matching disabled => match only those product prices which are not attribute dependent
				queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, false);
				break;
			case STRICT:
				// Attributes matching enabled => match given ASI (if any)
				queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, true); // don't even look at product prices that don't have the flag set

				final I_M_AttributeSetInstance attributePricingASIToMatchStrict = getAttributePricingASIToMatch();
				if (attributePricingASIToMatchStrict != null)
				{
					queryBuilder.filter(ASIProductPriceAttributesFilter.attributeDependantOnly(attributePricingASIToMatchStrict));
				}
				break;
			case NOT_STRICT:
				// Non-strict matching; if a productPrice is attribute dependent, then match it against the ASI
				final I_M_AttributeSetInstance attributePricingASIToMatchNotStrict = getAttributePricingASIToMatch();
				if (attributePricingASIToMatchNotStrict != null)
				{
					queryBuilder.filter(ASIProductPriceAttributesFilter.alsoAcceptIfNotAttributeDependant(attributePricingASIToMatchNotStrict));
				}
				break;
			case IGNORE: // do noting
				break;
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

		// NOTE: don't order because we don't know the best ordering at this point!
		return queryBuilder;
	}

	ProductPriceQuery setPriceListVersionId(final PriceListVersionId priceListVersionId)
	{
		this._priceListVersionId = priceListVersionId;
		return this;
	}

	private PriceListVersionId getPriceListVersionId()
	{
		Check.assumeNotNull(_priceListVersionId, "priceListVersionId is set");
		return _priceListVersionId;
	}

	public ProductPriceQuery setProductId(final ProductId productId)
	{
		_productId = productId;
		return this;
	}

	private ProductId getProductId()
	{
		Check.assumeNotNull(_productId, "product shall be set for {}", this);
		return _productId;
	}

	/** Matches product price which is NOT marked as "attributed pricing" */
	public ProductPriceQuery noAttributePricing()
	{
		_attributePricing = AttributePricing.NONE;
		_attributePricing_asiToMatch = null;
		return this;
	}

	public ProductPriceQuery onlyValidPrices(final boolean onlyValidPrices)
	{
		_onlyValidPrices = onlyValidPrices;
		return this;
	}

	public boolean isOnlyValidPrices()
	{
		return _onlyValidPrices;
	}

	/** Matches any product price which is marked as "attributed pricing" */
	public ProductPriceQuery onlyAttributePricing()
	{
		_attributePricing = AttributePricing.STRICT;
		// _attributePricing_asiToMatch = null;
		return this;
	}

	/**
	 * Matches product prices which are marked as "attributed pricing" and the given ASI is matching.
	 * If <code>asi</code> is null then any "attributed pricing" will be matched.
	 *
	 * @param asi ASI to match or <code>null</code>
	 */
	public ProductPriceQuery strictlyMatchingAttributes(@Nullable final I_M_AttributeSetInstance asi)
	{
		_attributePricing = AttributePricing.STRICT;
		_attributePricing_asiToMatch = asi;
		return this;
	}

	/**
	 * Like {@link #strictlyMatchingAttributes(I_M_AttributeSetInstance)}, but also accepts prices that are not attribute dependent at all.
	 */
	public ProductPriceQuery notStrictlyMatchingAttributes(@Nullable final I_M_AttributeSetInstance asi)
	{
		_attributePricing = AttributePricing.NOT_STRICT;
		_attributePricing_asiToMatch = asi;
		return this;

	}

	public ProductPriceQuery dontMatchAttributes()
	{
		_attributePricing = AttributePricing.IGNORE;
		_attributePricing_asiToMatch = null;
		return this;
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

	public ProductPriceQuery matching(@NonNull final IProductPriceQueryMatcher matcher)
	{
		if (_additionalMatchers == null)
		{
			_additionalMatchers = new LinkedHashMap<>();
		}
		_additionalMatchers.put(matcher.getName(), matcher);

		return this;
	}

	/* package */ final ProductPriceQuery addMatchersIfAbsent(final Collection<IProductPriceQueryMatcher> matchers)
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

	public ProductPriceQuery matching(@Nullable final Collection<IProductPriceQueryMatcher> matchers)
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

	public interface IProductPriceQueryMatcher
	{
		String getName();

		IQueryFilter<I_M_ProductPrice> getQueryFilter();
	}

	public static final class ProductPriceQueryMatcher implements IProductPriceQueryMatcher
	{
		public static ProductPriceQueryMatcher of(final String name, final IQueryFilter<I_M_ProductPrice> filter)
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
		public static ASIProductPriceAttributesFilter attributeDependantOnly(final I_M_AttributeSetInstance asi)
		{
			return new ASIProductPriceAttributesFilter(asi, /* acceptNotAttributeDependent */false);
		}

		public static IQueryFilter<I_M_ProductPrice> alsoAcceptIfNotAttributeDependant(final I_M_AttributeSetInstance asi)
		{
			return new ASIProductPriceAttributesFilter(asi, /* acceptNotAttributeDependent */true);
		}

		private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		private final I_M_AttributeSetInstance _asi;
		private final boolean _acceptNotAttributeDependent;
		private transient ImmutableMap<Integer, I_M_AttributeInstance> _asiAttributes;

		private ASIProductPriceAttributesFilter(
				@NonNull final I_M_AttributeSetInstance asi,
				final boolean acceptNotAttributeDependent)
		{
			_asi = asi;
			_acceptNotAttributeDependent = acceptNotAttributeDependent;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(_asi).toString();
		}

		@Override
		public boolean accept(@Nullable final I_M_ProductPrice productPrice)
		{
			// Guard against null, shall not happen
			if (productPrice == null)
			{
				return false;
			}

			if (!productPrice.isAttributeDependant())
			{
				return _acceptNotAttributeDependent; // either way, there is nothing more to do or match here
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
			final int expectedAttributeValueId = CoalesceUtil.firstGreaterThanZero(expected.getM_AttributeValue_ID(), 0);

			final int actualAttributeValueId;
			if (actual == null)
			{
				actualAttributeValueId = 0;
			}
			else
			{
				actualAttributeValueId = CoalesceUtil.firstGreaterThanZero(actual.getM_AttributeValue_ID(), 0);
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

		private List<I_M_AttributeInstance> extractProductPriceAttributes(final I_M_ProductPrice productPrice)
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
