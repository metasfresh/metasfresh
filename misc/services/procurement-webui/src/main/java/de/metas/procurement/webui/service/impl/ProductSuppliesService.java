package de.metas.procurement.webui.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.vaadin.spring.i18n.I18N;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.UserProduct;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.UserProductRepository;
import de.metas.procurement.webui.repository.WeekSupplyRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.IServerSyncService;
import de.metas.procurement.webui.sync.ISyncAfterCommitCollector;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;

/*
 * #%L
 * de.metas.procurement.webui
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
 * Creates/Updates both {@link ProductSupply} and {@link WeekSupply} records, and also makes sure they are synchronized with the remote endpoint, see {@link SyncAfterCommit}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class ProductSuppliesService implements IProductSuppliesService
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private I18N i18n;

	@Autowired
	private UserProductRepository userProductRepository;

	@Autowired
	private ProductSupplyRepository productSupplyRepository;

	@Autowired
	private WeekSupplyRepository weekSupplyRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	@Lazy
	private BPartnerRepository bpartnersRepository;

	@Autowired
	private IServerSyncService syncService;

	@Override
	public List<Product> getUserFavoriteProducts(final User user)
	{
		final List<UserProduct> userProducts = userProductRepository.findByUser(user);

		final List<Product> products = new ArrayList<>();
		for (final UserProduct userProduct : userProducts)
		{
			final Product product = userProduct.getProduct();
			if (product.isDeleted())
			{
				continue;
			}
			products.add(product);
		}

		Collections.sort(products, Product.comparatorByName(i18n.getLocale()));
		return products;
	}

	@Override
	@Transactional
	public void addUserFavoriteProduct(final User user, final Product product)
	{
		final UserProduct existingUserProduct = userProductRepository.findByUserAndProduct(user, product);
		if (existingUserProduct != null)
		{
			return;
		}

		final UserProduct userProduct = UserProduct.build(user, product);
		userProductRepository.save(userProduct);
	}

	@Override
	@Transactional
	public boolean removeUserFavoriteProduct(final User user, final Product product)
	{
		final UserProduct existingUserProduct = userProductRepository.findByUserAndProduct(user, product);
		if (existingUserProduct == null)
		{
			return false;
		}

		userProductRepository.delete(existingUserProduct);
		return true;
	}

	@Override
	@Transactional
	public void reportSupply(final BPartner bpartner, final Product product, final ContractLine contractLine, final Date day, final BigDecimal qty)
	{
		ProductSupply productSupply = productSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, day);
		if (productSupply == null)
		{
			productSupply = ProductSupply.build(bpartner, product, contractLine, day);
		}

		productSupply.setQty(qty);
		productSupplyRepository.save(productSupply);

		syncAfterCommit().add(productSupply);
	}

	@Override
	public List<ProductSupply> getProductSupplies(final BPartner bpartner, final Date date)
	{
		final Date day = DateUtils.truncToDay(date);
		return productSupplyRepository.findByBpartnerAndDay(bpartner, day);
	}

	@Override
	public List<ProductSupply> getProductSupplies(final long bpartner_id, final long product_id, Date dayFrom, Date dayTo)
	{
		final BPartner bpartner;
		if (bpartner_id > 0)
		{
			bpartner = bpartnersRepository.findOne(bpartner_id);
			if (bpartner == null)
			{
				throw new RuntimeException("No BPartner found for ID=" + bpartner_id);
			}
		}
		else
		{
			bpartner = null;
		}

		final Product product;
		if (product_id > 0)
		{
			product = productRepository.findOne(product_id);
			if (product == null)
			{
				throw new RuntimeException("No Product found for ID=" + product_id);
			}
		}
		else
		{
			product = null;
		}

		dayFrom = DateUtils.truncToDay(dayFrom);
		if (dayFrom == null)
		{
			throw new RuntimeException("No DayFrom specified");
		}
		dayTo = DateUtils.truncToDay(dayTo);
		if (dayTo == null)
		{
			throw new RuntimeException("No DayTo specified");
		}

		logger.debug("Querying product supplies for: bpartner={}, product={}, day={}->{}", bpartner, product, dayFrom, dayTo);
		List<ProductSupply> productSupplies = productSupplyRepository.findBySelector(bpartner, product, dayFrom, dayTo);
		logger.debug("Got {} product supplies", productSupplies.size());

		return productSupplies;
	}

	@Override
	@Transactional
	public List<Product> getAllProducts()
	{
		return productRepository.findByDeletedFalse();
	}

	@Override
	@Transactional
	public List<Product> getAllSharedProducts()
	{
		return productRepository.findBySharedTrueAndDeletedFalse();
	}

	@Override
	public Trend getNextWeekTrend(final BPartner bpartner, final Product product, final DateRange week)
	{
		final Date weekDay = week.getNextWeek().getDateFrom();
		final WeekSupply weekSupply = weekSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, weekDay);
		if (weekSupply == null)
		{
			return null;
		}

		return Trend.ofCodeOrNull(weekSupply.getTrend());
	}

	@Override
	@Transactional
	public WeekSupply setNextWeekTrend(final BPartner bpartner, final Product product, final DateRange week, final Trend trend)
	{
		final Date weekDay = week.getNextWeek().getDateFrom();
		final String trendCode = trend == null ? null : trend.getCode();

		WeekSupply weeklySupply = weekSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, weekDay);
		if (weeklySupply == null)
		{
			weeklySupply = new WeekSupply();
			weeklySupply.setBpartner(bpartner);
			weeklySupply.setProduct(product);
			weeklySupply.setDay(weekDay);
		}

		weeklySupply.setTrend(trendCode);
		weekSupplyRepository.save(weeklySupply);

		syncAfterCommit().add(weeklySupply);

		return weeklySupply;
	}

	@Override
	public List<WeekSupply> getWeeklySupplies(long bpartner_id, long product_id, Date dayFrom, Date dayTo)
	{
		final BPartner bpartner;
		if (bpartner_id > 0)
		{
			bpartner = bpartnersRepository.findOne(bpartner_id);
			if (bpartner == null)
			{
				throw new RuntimeException("No BPartner found for ID=" + bpartner_id);
			}
		}
		else
		{
			bpartner = null;
		}

		final Product product;
		if (product_id > 0)
		{
			product = productRepository.findOne(product_id);
			if (product == null)
			{
				throw new RuntimeException("No Product found for ID=" + product_id);
			}
		}
		else
		{
			product = null;
		}

		dayFrom = DateUtils.truncToDay(dayFrom);
		if (dayFrom == null)
		{
			throw new RuntimeException("No DayFrom specified");
		}
		dayTo = DateUtils.truncToDay(dayTo);
		if (dayTo == null)
		{
			throw new RuntimeException("No DayTo specified");
		}

		logger.debug("Querying weekly supplies for: bpartner={}, product={}, day={}->{}", bpartner, product, dayFrom, dayTo);
		final List<WeekSupply> weeklySupplies = weekSupplyRepository.findBySelector(bpartner, product, dayFrom, dayTo);
		logger.debug("Got {} weekly supplies", weeklySupplies.size());

		return weeklySupplies;
	}

	private ISyncAfterCommitCollector syncAfterCommit()
	{
		return syncService.syncAfterCommit();
	}

}
