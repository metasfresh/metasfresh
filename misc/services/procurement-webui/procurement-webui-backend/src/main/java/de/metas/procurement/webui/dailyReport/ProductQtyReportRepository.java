package de.metas.procurement.webui.dailyReport;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Contracts;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.I18N;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.ISendService;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.NonNull;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
	private static final Logger logger = LoggerFactory.getLogger(ProductQtyReportRepository.class);

	private final I18N i18n;
	private final IProductSuppliesService productSuppliesRepository;
	// @Autowired private MFEventBus applicationEventBus;

	private final User user;
	private final Contracts contracts;
	private final Locale locale;

	//
	// Cached products lists
	private Collection<Product> _productsContracted;
	private List<Product> _productsNotContracted;
	private List<Product> _productsFavorite;

	private final LoadingCache<LocalDate, ProductQtyReportContainer> day2productQtyReportContainer = CacheBuilder.newBuilder()
			.build(new CacheLoader<LocalDate, ProductQtyReportContainer>()
			{

				@Override
				public ProductQtyReportContainer load(final LocalDate day) throws Exception
				{
					return createDailyProductQtyReportContainer(day);
				}

			});

	private final HashMap<DateRange, WeekProductQtyReportContainer> week2productQtyContainer = new HashMap<>();

	@Builder
	public ProductQtyReportRepository(
			@NonNull final I18N i18n,
			@NonNull final IProductSuppliesService productSuppliesRepository,
			@NonNull final User user,
			@NonNull final Contracts contracts)
	{
		this.i18n = i18n;
		this.productSuppliesRepository = productSuppliesRepository;

		this.user = user;
		this.contracts = contracts;
		this.locale = user.getLanguageKeyOrDefault().toLocale();

		// applicationEventBus.register(new UIApplicationEventListenerAdapter()
		// {
		// 	@Override
		// 	public void onContractChanged(final ContractChangedEvent event)
		// 	{
		// 		resetCacheOnContractChanged(event);
		// 	}
		//
		// 	@Override
		// 	public void onProductSupplyChanged(final ProductSupplyChangedEvent event)
		// 	{
		// 		updateOnProductSupplyChanged(event);
		// 	}
		// });

	}

	private final User getUser()
	{
		return user;
	}

	private Locale getLocale()
	{
		return locale;
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
					Collections.sort(products, Product.comparatorByName(getLocale()));
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
		Collections.sort(productsNotSelected, Product.comparatorByName(getLocale()));
		return productsNotSelected;
	}

	public void addFavoriteProduct(final Product product)
	{
		productSuppliesRepository.addUserFavoriteProduct(getUser(), product);
		resetProductsFavorite(); // FIXME: optimization: i think it would be better to just add it to internal list

		//
		// Migrate existing daily report containers
		for (final Map.Entry<LocalDate, ProductQtyReportContainer> e : day2productQtyReportContainer.asMap().entrySet())
		{
			final LocalDate day = e.getKey();
			final ProductQtyReportContainer dayContainer = e.getValue();

			// Do nothing if the day already has this product
			if (dayContainer.hasProduct(product))
			{
				continue;
			}

			final ProductQtyReport dayItem = dayContainer.addBean(ProductQtyReport.of(product, day));
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
					Collections.sort(productsNotContracted, Product.comparatorByName(getLocale()));
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

	public final ProductQtyReportContainer getDailyProductQtyReportContainer(@NonNull final LocalDate day)
	{
		try
		{
			return day2productQtyReportContainer.get(day);
		}
		catch (final ExecutionException e)
		{
			throw new RuntimeException(e.getCause()); // shall not happen
		}
	}

	private final ProductQtyReportContainer createDailyProductQtyReportContainer(final LocalDate day)
	{
		final List<Product> productsWithoutReportings = new ArrayList<>(getProductsFavorite());

		//
		// Retrieve all daily reporting records and convert them to ProductQtyReport entries
		final BPartner bpartner = getBPartner();
		final List<ProductSupply> dailyProductSupplies = productSuppliesRepository.getProductSupplies(bpartner, DateUtils.toDate(day));
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

	// public WeekProductQtyReportContainer getWeek(final Date aDayInWeek)
	// {
	// 	final DateRange week = DateRange.createWeek(aDayInWeek);
	//
	// 	WeekProductQtyReportContainer weekContainer = week2productQtyContainer.get(week);
	// 	if (weekContainer == null)
	// 	{
	// 		weekContainer = new WeekProductQtyReportContainer(getBPartner(), week);
	// 		week2productQtyContainer.put(week, weekContainer);
	// 	}
	//
	// 	//
	// 	// Update quantities
	// 	weekContainer.resetAllDailyReportings();
	// 	for (final LocalDate dayOfWeek : week.daysIterable())
	// 	{
	// 		final ProductQtyReportContainer dayContainer = getDailyProductQtyReportContainer(dayOfWeek);
	// 		for (final Object itemId : dayContainer.getItemIds())
	// 		{
	// 			final BeanItem<ProductQtyReport> item = dayContainer.getItem(itemId);
	// 			weekContainer.addDailyQtyReport(item);
	// 		}
	// 	}
	//
	// 	return weekContainer;
	// }
}
