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

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.cache.CacheBuilder;
import com.google.gwt.thirdparty.guava.common.cache.CacheLoader;
import com.google.gwt.thirdparty.guava.common.cache.LoadingCache;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IProductSuppliesService;
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

public class ProductQtyReportRepository
{
	@Autowired
    private I18N i18n;

	@Autowired(required = true)
	IProductSuppliesService productSuppliesRepository;

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
	}

	private final ObjectProperty<Integer> notSentCounterProperty = new ObjectProperty<>(0);

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
			List<Product> products = getContracts().getProducts();
			Collections.sort(products, Product.comparatorByName(i18n.getLocale()));
			_productsContracted = ImmutableList.copyOf(products);
		}
		return _productsContracted;
	}

	public List<Product> getProductsFavorite()
	{
		if (_productsFavorite == null)
		{
			final User user = getUser();
			_productsFavorite = ImmutableList.copyOf(productSuppliesRepository.getUserFavoriteProducts(user));
		}
		return _productsFavorite;
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
		_productsFavorite = null; // FIXME: optimization: i think it would be better to just add it to internal list

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
		_productsFavorite = null; // FIXME: optimization: i think it would be better to just add it to internal list

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
			final List<Product> productsNotContracted = new ArrayList<>(productSuppliesRepository.getAllSharedProducts());
			final Collection<Product> contractedProducts = getProductsContracted();
			productsNotContracted.removeAll(contractedProducts);
			Collections.sort(productsNotContracted, Product.comparatorByName(i18n.getLocale()));
			this._productsNotContracted = ImmutableList.copyOf(productsNotContracted);
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
				if(item == null)
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
		productQtyReport.setQtySent(qty);
		updateSentStatus(item);
	}

	public void updateSentStatus(final BeanItem<ProductQtyReport> item)
	{
		final ProductQtyReport bean = item.getBean();
		final boolean sent = bean.getQty().compareTo(bean.getQtySent()) == 0;
		
		@SuppressWarnings("unchecked")
		final Property<Boolean> sentProperty = item.getItemProperty(ProductQtyReport.PROPERY_Sent);
		if (sentProperty.getValue() == sent)
		{
			return;
		}

		sentProperty.setValue(sent);

		//
		// Adjust the not-sent counter
		if (sent)
		{
			final int counter = notSentCounterProperty.getValue();
			notSentCounterProperty.setValue(counter > 0 ? counter - 1: 0); // guard against negative counters (i.e. some bug)
		}
		else
		{
			int counter = notSentCounterProperty.getValue();
			notSentCounterProperty.setValue(counter + 1);
		}
	}
	
	public Property<Integer> getNotSentCounterProperty()
	{
		return notSentCounterProperty;
	}

	public int getNotSentCounter()
	{
		return notSentCounterProperty.getValue();
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

}
