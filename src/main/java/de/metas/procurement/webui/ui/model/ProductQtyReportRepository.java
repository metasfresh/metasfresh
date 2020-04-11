package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.cache.CacheBuilder;
import com.google.gwt.thirdparty.guava.common.cache.CacheLoader;
import com.google.gwt.thirdparty.guava.common.cache.LoadingCache;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.event.ContractChangedEvent;
import de.metas.procurement.webui.event.MFEventBus;
import de.metas.procurement.webui.event.ProductSupplyChangedEvent;
import de.metas.procurement.webui.event.UIApplicationEventListenerAdapter;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.ISendService;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductQtyReportRepository
{
	private static final Logger logger = LoggerFactory.getLogger(ProductQtyReportContainer.class);
	
	@Autowired
	private I18N i18n;

	@Autowired
	private IProductSuppliesService productSuppliesRepository;

	@Autowired
	private MFEventBus applicationEventBus;

	private final User user;
	private final Contracts contracts;

	//
	// Cached products lists
	private Collection<Product> _productsContracted;
	private List<Product> _productsNotContracted;
	private List<Product> _productsFavorite;

	private final LoadingCache<Date, ProductQtyReportContainer> day2productQtyReportContainer = CacheBuilder.newBuilder()
			.build(new CacheLoader<Date, ProductQtyReportContainer>()
			{

				@Override
				public ProductQtyReportContainer load(final Date day) throws Exception
				{
					return createDailyProductQtyReportContainer(day);
				}

			});

	private final Map<DateRange, WeekProductQtyReportContainer> week2productQtyContainer = new HashMap<>();

	public ProductQtyReportRepository(final User user, final Contracts contracts)
	{
		super();
		Application.autowire(this);

		this.user = user;
		this.contracts = contracts;

		applicationEventBus.register(new UIApplicationEventListenerAdapter()
		{
			@Override
			public void onContractChanged(final ContractChangedEvent event)
			{
				resetCacheOnContractChanged(event);
			}

			@Override
			public void onProductSupplyChanged(final ProductSupplyChangedEvent event)
			{
				updateOnProductSupplyChanged(event);
			}
		});

	}

	private final User getUser()
	{
		return user;
	}

	private final Contracts getContracts()
	{
		return contracts;
	}

	private final BPartner getBPartner()
	{
		return user.getBpartner();
	}

	private Collection<Product> getProductsContracted()
	{
		if (_productsContracted == null)
		{
			synchronized (this)
			{
				if (_productsContracted == null)
				{
					final List<Product> products = getContracts().getProducts();
					Collections.sort(products, Product.comparatorByName(i18n.getLocale()));
					_productsContracted = ImmutableList.copyOf(products);
				}
			}
		}
		return _productsContracted;
	}

	public List<Product> getProductsFavorite()
	{
		if (_productsFavorite == null)
		{
			synchronized (this)
			{
				if (_productsFavorite == null)
				{
					final User user = getUser();
					_productsFavorite = ImmutableList.copyOf(productSuppliesRepository.getUserFavoriteProducts(user));
				}
			}
		}
		return _productsFavorite;
	}

	private void resetProductsFavorite()
	{
		synchronized (this)
		{
			_productsFavorite = null;
		}
	}

	public List<Product> getProductsContractedButNotFavorite()
	{
		final List<Product> productsNotSelected = new ArrayList<>(getProductsContracted());
		productsNotSelected.removeAll(getProductsFavorite());
		Collections.sort(productsNotSelected, Product.comparatorByName(i18n.getLocale()));
		return productsNotSelected;
	}

	public void addFavoriteProduct(final Product product)
	{
		productSuppliesRepository.addUserFavoriteProduct(getUser(), product);
		resetProductsFavorite(); // FIXME: optimization: i think it would be better to just add it to internal list

		//
		// Migrate existing daily report containers
		for (final Map.Entry<Date, ProductQtyReportContainer> e : day2productQtyReportContainer.asMap().entrySet())
		{
			final Date day = e.getKey();
			final ProductQtyReportContainer dayContainer = e.getValue();

			// Do nothing if the day already has this product
			if (dayContainer.hasProduct(product))
			{
				continue;
			}

			final BeanItem<ProductQtyReport> dayItem = dayContainer.addBean(ProductQtyReport.of(product, day));
			dayContainer.sort();

			//
			// Migrate the week container
			final DateRange week = DateRange.createWeek(day);
			final WeekProductQtyReportContainer weekContainer = week2productQtyContainer.get(week);
			if (weekContainer != null)
			{
				weekContainer.addDailyQtyReport(dayItem);
			}
		}
	}

	public void removeFavoriteProduct(final Product product)
	{
		if (!productSuppliesRepository.removeUserFavoriteProduct(getUser(), product))
		{
			return; // already deleted
		}
		resetProductsFavorite(); // FIXME: optimization: i think it would be better to just add it to internal list

		//
		// Migrate existing daily report containers
		final List<BeanItem<ProductQtyReport>> deletedItems = new ArrayList<>();
		for (final ProductQtyReportContainer dayContainer : day2productQtyReportContainer.asMap().values())
		{
			final BeanItem<ProductQtyReport> deletedItem = dayContainer.removeProductIfZeroQty(product);
			if (deletedItem != null)
			{
				deletedItems.add(deletedItem);
			}
		}

		// make sure the ZERO quantities were reported
		send(deletedItems);

		//
		// Migrate the week containers
		for (final WeekProductQtyReportContainer weekContainer : week2productQtyContainer.values())
		{
			weekContainer.removeProductIfZeroQty(product);
		}
	}

	public List<Product> getProductsNotContracted()
	{
		if (_productsNotContracted == null)
		{
			synchronized (this)
			{
				if (_productsNotContracted == null)
				{
					final List<Product> productsNotContracted = new ArrayList<>(productSuppliesRepository.getAllSharedProducts());
					final Collection<Product> contractedProducts = getProductsContracted();
					productsNotContracted.removeAll(contractedProducts);
					Collections.sort(productsNotContracted, Product.comparatorByName(i18n.getLocale()));
					_productsNotContracted = ImmutableList.copyOf(productsNotContracted);
				}
			}
		}
		return _productsNotContracted;
	}

	public List<Product> getProductsNotContractedNorFavorite()
	{
		final List<Product> result = new ArrayList<>(getProductsNotContracted());
		result.removeAll(getProductsFavorite());
		return result;
	}

	public boolean hasProductsNotContractedNorFavorite()
	{
		return !getProductsNotContractedNorFavorite().isEmpty();
	}

	public final ProductQtyReportContainer getDailyProductQtyReportContainer(Date day)
	{
		day = DateUtils.truncToDay(day);
		try
		{
			return day2productQtyReportContainer.get(day);
		}
		catch (final ExecutionException e)
		{
			throw new RuntimeException(e.getCause()); // shall not happen
		}
	}

	private final ProductQtyReportContainer createDailyProductQtyReportContainer(final Date day)
	{
		final List<Product> productsWithoutReportings = new ArrayList<>(getProductsFavorite());

		//
		// Retrieve all daily reporting records and convert them to ProductQtyReport entries
		final BPartner bpartner = getBPartner();
		final List<ProductSupply> dailyProductSupplies = productSuppliesRepository.getProductSupplies(bpartner, day);
		final List<ProductQtyReport> productQtyReports = new ArrayList<>(dailyProductSupplies.size());
		for (final ProductSupply productSupply : dailyProductSupplies)
		{
			final ProductQtyReport productQtyReport = ProductQtyReport.of(productSupply);
			productQtyReports.add(productQtyReport);

			productsWithoutReportings.remove(productQtyReport.getProduct());
		}

		//
		// Add reporting for those products which were flagged as favorite but don't have a reporting
		for (final Product product : productsWithoutReportings)
		{
			final ProductQtyReport productQtyReport = ProductQtyReport.of(product, day);
			productQtyReports.add(productQtyReport);
		}

		//
		// Create the container and add all daily reporting records to it
		final ProductQtyReportContainer productsContainer = new ProductQtyReportContainer();
		productsContainer.setAutoSortByPropertyId(ProductQtyReport.PROPERY_ProductName);
		productsContainer.addAll(productQtyReports);

		return productsContainer;
	}

	public final void sendAll()
	{
		final List<BeanItem<ProductQtyReport>> items = new ArrayList<>();
		for (final Map.Entry<Date, ProductQtyReportContainer> dayAndproductQtyReportContainer : day2productQtyReportContainer.asMap().entrySet())
		{
			final ProductQtyReportContainer productQtyReportContainer = dayAndproductQtyReportContainer.getValue();
			for (final Object itemId : productQtyReportContainer.getItemIds())
			{
				final BeanItem<ProductQtyReport> item = productQtyReportContainer.getItem(itemId);
				if (item == null)
				{
					continue;
				}
				items.add(item);
			}
		}

		send(items);
	}

	@Transactional
	public void send(final List<BeanItem<ProductQtyReport>> items)
	{
		final Contracts contracts = getContracts();

		for (final BeanItem<ProductQtyReport> item : items)
		{
			send(contracts, item);
		}
	}

	private final void send(final Contracts contracts, final BeanItem<ProductQtyReport> item)
	{
		final ProductQtyReport productQtyReport = item.getBean();

		// Do nothing if already sent
		if (productQtyReport.isSent())
		{
			return;
		}

		final BPartner bpartner = getBPartner();
		final Product product = productQtyReport.getProduct();
		final BigDecimal qty = productQtyReport.getQty();
		final Date day = productQtyReport.getDay();
		final ContractLine contractLine = contracts.getContractLineOrNull(product, day);
		productSuppliesRepository.reportSupply(bpartner, product, contractLine, day, qty);
		// TODO: handle exceptions

		//
		// Flag as sent
		productQtyReport.setSentFieldsFromActualFields();
		updateSentStatus(item);
	}

	public void updateSentStatus(final BeanItem<ProductQtyReport> item)
	{
		final ISendService sendService = MFProcurementUI.getCurrentMFSession().getSendService();
		sendService.updateSentStatus(item);
	}

	public WeekProductQtyReportContainer getWeek(final Date aDayInWeek)
	{
		final DateRange week = DateRange.createWeek(aDayInWeek);

		WeekProductQtyReportContainer weekContainer = week2productQtyContainer.get(week);
		if (weekContainer == null)
		{
			weekContainer = new WeekProductQtyReportContainer(getBPartner(), week);
			week2productQtyContainer.put(week, weekContainer);
		}

		//
		// Update quantities
		weekContainer.resetAllDailyReportings();
		for (final Date dayOfWeek : week.daysIterable())
		{
			final ProductQtyReportContainer dayContainer = getDailyProductQtyReportContainer(dayOfWeek);
			for (final Object itemId : dayContainer.getItemIds())
			{
				final BeanItem<ProductQtyReport> item = dayContainer.getItem(itemId);
				weekContainer.addDailyQtyReport(item);
			}
		}

		return weekContainer;
	}

	protected void resetCacheOnContractChanged(final ContractChangedEvent event)
	{
		logger.debug("Reseting cache because of {}", event);
		
		synchronized (this)
		{
			contracts.resetContractsCache();
			_productsContracted = null;
			_productsNotContracted = null;
			resetProductsFavorite();
		}
	}

	private void updateOnProductSupplyChanged(final ProductSupplyChangedEvent event)
	{
		logger.debug("Updating product supplies for {}", event);
		
		final Date day = event.getDay();
		final ProductQtyReportContainer dailySupplies = day2productQtyReportContainer.getIfPresent(day);
		if (dailySupplies == null)
		{
			return;
		}

		final Product product = event.getProduct();
		if (product == null)
		{
			return;
		}

		//
		// Get/create product qty report entry
		BeanItem<ProductQtyReport> item = dailySupplies.getItemByProduct(product);
		if (item == null)
		{
			final ProductQtyReport productQtyReport = ProductQtyReport.of(product, day);
			item = dailySupplies.addBean(productQtyReport);
		}

		//
		// Set quantity
		setQty(item, event.getQty());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final void setQty(final BeanItem<ProductQtyReport> item, final BigDecimal qty)
	{
		final Property qtyProperty = item.getItemProperty(ProductQtyReport.PROPERTY_Qty);
		qtyProperty.setValue(qty);
	}

}
