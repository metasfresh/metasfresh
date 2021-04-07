package de.metas.procurement.webui.service.impl;

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
import de.metas.procurement.webui.sync.ISenderToMetasfreshService;
import de.metas.procurement.webui.util.DateUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.threeten.extra.YearWeek;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
 * Creates/Updates both {@link ProductSupply} and {@link WeekSupply} records, and also makes sure they are synchronized with the remote endpoint, see SyncAfterCommit.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class ProductSuppliesService implements IProductSuppliesService
{
	private static final Logger logger = LoggerFactory.getLogger(ProductSuppliesService.class);
	private final UserProductRepository userProductRepository;
	private final ProductSupplyRepository productSupplyRepository;
	private final WeekSupplyRepository weekSupplyRepository;
	private final ProductRepository productRepository;
	private final BPartnerRepository bpartnersRepository;
	private final ISenderToMetasfreshService senderToMetasfreshService;

	public ProductSuppliesService(
			@NonNull final UserProductRepository userProductRepository,
			@NonNull final ProductSupplyRepository productSupplyRepository,
			@NonNull final WeekSupplyRepository weekSupplyRepository,
			@NonNull final ProductRepository productRepository,
			@NonNull final BPartnerRepository bpartnersRepository,
			@Lazy @NonNull final ISenderToMetasfreshService senderToMetasfreshService)
	{
		this.userProductRepository = userProductRepository;
		this.productSupplyRepository = productSupplyRepository;
		this.weekSupplyRepository = weekSupplyRepository;
		this.productRepository = productRepository;
		this.bpartnersRepository = bpartnersRepository;
		this.senderToMetasfreshService = senderToMetasfreshService;
	}

	@Override
	public ProductSupply getProductSupplyById(final long product_supply_id)
	{
		return productSupplyRepository.getOne(product_supply_id);
	}

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

		// Collections.sort(products, Product.comparatorByName(i18n.getLocale()));
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
	public void removeUserFavoriteProduct(final User user, final Product product)
	{
		final UserProduct existingUserProduct = userProductRepository.findByUserAndProduct(user, product);
		if (existingUserProduct == null)
		{
			return;
		}

		userProductRepository.delete(existingUserProduct);
	}

	@Override
	@Transactional
	public void reportSupply(@NonNull final ReportDailySupplyRequest request)
	{
		final BPartner bpartner = request.getBpartner();
		final ContractLine contractLine = request.getContractLine();
		final Product product = productRepository.getOne(request.getProductId());
		final LocalDate date = request.getDate();
		final BigDecimal qty = request.getQty();

		ProductSupply productSupply = productSupplyRepository.findByProductAndBpartnerAndDay(
				product,
				bpartner,
				DateUtils.toSqlDate(date));
		if (productSupply == null)
		{
			productSupply = ProductSupply.builder()
					.bpartner(bpartner)
					.contractLine(contractLine)
					.product(product)
					.day(date)
					.build();
		}

		productSupply.setQtyUserEntered(qty);
		if (request.isQtyConfirmedByUser())
		{
			productSupply.setQty(qty);
		}

		saveAndPush(productSupply);
	}

	@Override
	public List<ProductSupply> getProductSupplies(final BPartner bpartner, final LocalDate date)
	{
		return productSupplyRepository.findByBpartnerAndDay(bpartner, DateUtils.toSqlDate(date));
	}

	@Override
	public List<ProductSupply> getProductSupplies(
			final long bpartner_id,
			final long product_id,
			@NonNull final LocalDate dayFrom,
			@NonNull final LocalDate dayTo)
	{
		final BPartner bpartner = bpartner_id > 0 ? bpartnersRepository.getOne(bpartner_id) : null;
		final Product product = product_id > 0 ? productRepository.getOne(product_id) : null;
		logger.debug("Querying product supplies for: bpartner={}, product={}, day={}->{}", bpartner, product, dayFrom, dayTo);

		final List<ProductSupply> productSupplies = productSupplyRepository.findBySelector(
				bpartner,
				product,
				DateUtils.toSqlDate(dayFrom),
				DateUtils.toSqlDate(dayTo));
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
	public Trend getNextWeekTrend(final BPartner bpartner, final Product product, final YearWeek week)
	{
		final LocalDate weekDay = week.plusWeeks(1).atDay(DayOfWeek.MONDAY);
		final WeekSupply weekSupply = weekSupplyRepository.findByProductAndBpartnerAndDay(
				product,
				bpartner,
				DateUtils.toSqlDate(weekDay));

		return weekSupply != null ? weekSupply.getTrend() : null;
	}

	@Override
	@Transactional
	public WeekSupply setNextWeekTrend(final BPartner bpartner, final Product product, final YearWeek week, final Trend trend)
	{
		final LocalDate weekDay = week.plusWeeks(1).atDay(DayOfWeek.MONDAY);

		WeekSupply weeklySupply = weekSupplyRepository.findByProductAndBpartnerAndDay(
				product,
				bpartner,
				DateUtils.toSqlDate(weekDay));
		if (weeklySupply == null)
		{
			weeklySupply = WeekSupply.builder()
					.bpartner(bpartner)
					.product(product)
					.day(weekDay)
					.build();
		}

		weeklySupply.setTrend(trend);

		saveAndPush(weeklySupply);

		return weeklySupply;
	}

	@Override
	public List<WeekSupply> getWeeklySupplies(
			final long bpartner_id,
			final long product_id,
			@NonNull final LocalDate dayFrom,
			@NonNull final LocalDate dayTo)
	{
		final BPartner bpartner = bpartner_id > 0 ? bpartnersRepository.getOne(bpartner_id) : null;
		final Product product = product_id > 0 ? productRepository.getOne(product_id) : null;
		logger.debug("Querying weekly supplies for: bpartner={}, product={}, day={}->{}", bpartner, product, dayFrom, dayTo);

		final List<WeekSupply> weeklySupplies = weekSupplyRepository.findBySelector(
				bpartner,
				product,
				DateUtils.toSqlDate(dayFrom),
				DateUtils.toSqlDate(dayTo));
		logger.debug("Got {} weekly supplies", weeklySupplies.size());

		return weeklySupplies;
	}

	@Override
	public List<WeekSupply> getWeeklySupplies(
			@NonNull final BPartner bpartner,
			@Nullable final Product product,
			@NonNull final YearWeek week)
	{
		final LocalDate weekDay = week.atDay(DayOfWeek.MONDAY);

		return weekSupplyRepository.findBySelector(
				bpartner,
				product,
				DateUtils.toSqlDate(weekDay),
				DateUtils.toSqlDate(weekDay));
	}

	private void saveAndPush(@NonNull final ProductSupply productSupply)
	{
		productSupplyRepository.save(productSupply);
		senderToMetasfreshService.syncAfterCommit().add(productSupply);
	}

	private void saveAndPush(@NonNull final WeekSupply weeklySupply)
	{
		weekSupplyRepository.save(weeklySupply);
		senderToMetasfreshService.syncAfterCommit().add(weeklySupply);
	}

	@Override
	public Product getProductById(final long productId)
	{
		return productRepository.getOne(productId);
	}

	@Override
	public void importPlanningSupply(@NonNull final ImportPlanningSupplyRequest request)
	{
		final BPartner bpartner = request.getBpartner();
		final ContractLine contractLine = request.getContractLine();
		final Product product = productRepository.findByUuid(request.getProduct_uuid());
		final LocalDate day = request.getDate();
		final BigDecimal qty = request.getQty();

		ProductSupply productSupply = productSupplyRepository.findByProductAndBpartnerAndDay(product, bpartner, DateUtils.toSqlDate(day));
		final boolean isNew;
		if (productSupply == null)
		{
			isNew = true;
			productSupply = ProductSupply.builder()
					.bpartner(bpartner)
					.contractLine(contractLine)
					.product(product)
					.day(day)
					.build();
		}
		else
		{
			isNew = false;
		}

		//
		// Contract line
		if (!isNew)
		{
			final ContractLine contractLineOld = productSupply.getContractLine();
			if (!Objects.equals(contractLine, contractLineOld))
			{
				logger.warn("Changing contract line {}->{} for {} because of planning supply: {}", contractLineOld, contractLine, productSupply, request);
			}
			productSupply.setContractLine(contractLine);
		}

		//
		// Quantity
		if (!isNew)
		{
			final BigDecimal qtyOld = productSupply.getQty();
			if (qty.compareTo(qtyOld) != 0)
			{
				logger.warn("Changing quantity {}->{} for {} because of planning supply: {}", qtyOld, qty, productSupply, request);
			}
		}

		productSupply.setQtyUserEntered(qty);
		productSupply.setQty(qty);

		//
		// Save the product supply
		saveAndPush(productSupply);
	}

	@Override
	@Transactional
	public void confirmUserEntries(@NonNull final User user)
	{
		final BPartner bpartner = user.getBpartner();
		final List<ProductSupply> productSupplies = productSupplyRepository.findUnconfirmed(bpartner);
		for (final ProductSupply productSupply : productSupplies)
		{
			productSupply.setQty(productSupply.getQtyUserEntered());
			saveAndPush(productSupply);
		}
	}

	@Override
	public long getCountUnconfirmed(@NonNull final User user)
	{
		return productSupplyRepository.countUnconfirmed(user.getBpartner());
	}
}
