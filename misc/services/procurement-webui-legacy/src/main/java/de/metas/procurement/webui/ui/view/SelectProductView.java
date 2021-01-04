package de.metas.procurement.webui.ui.view;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.ui.component.GenericProductButton;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;

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

@SuppressWarnings("serial")
public class SelectProductView extends MFProcurementNavigationView
{
	@Autowired
    private I18N i18n;
	
	private final ProductQtyReportRepository productQtyReportRepository;
	
	private Component previousNavigationView;
	private VerticalComponentGroup productsNotContractedNorFavoritePanel;

	public SelectProductView(final ProductQtyReportRepository productQtyReportRepository)
	{
		super();
		this.productQtyReportRepository = productQtyReportRepository;

		setCaption(i18n.get("SelectProductView.caption"));
	}

	@Override
	public void attach()
	{
		super.attach();

		this.previousNavigationView = getNavigationManager().getCurrentComponent(); // i.e. our current component

		final VerticalLayout content = new VerticalLayout();

		//
		// Products: contracted but not favorite
		{
			final VerticalComponentGroup productsPanel = new VerticalComponentGroup();
			for (final Product product : productQtyReportRepository.getProductsContractedButNotFavorite())
			{
				final ProductButton button = createProductButton(product);
				productsPanel.addComponent(button);
			}
			content.addComponent(productsPanel);
		}

		//
		// Products: additional not contracted products
		if (productQtyReportRepository.hasProductsNotContractedNorFavorite())
		{
			this.productsNotContractedNorFavoritePanel = new VerticalComponentGroup();
			
			final ProductButton showNotContractedButton = new ProductButton();
			showNotContractedButton.setCaption(i18n.get("SelectProductView.showMeNotContractedButton"));
			showNotContractedButton.setTargetView(this);
			showNotContractedButton.addClickListener(new NavigationButtonClickListener()
			{
				
				@Override
				public void buttonClick(NavigationButtonClickEvent event)
				{
					onShowNotContractedProducts();
				}
			});
			productsNotContractedNorFavoritePanel.addComponent(showNotContractedButton);
			
			content.addComponent(productsNotContractedNorFavoritePanel);
		}

		setContent(content);
	}
	
	private final ProductButton createProductButton(final Product product)
	{
		final ProductButton button = new ProductButton();
		button.setProduct(product);
		button.setTargetView(previousNavigationView);
		button.addClickListener(new NavigationButtonClickListener()
		{

			@Override
			public void buttonClick(final NavigationButtonClickEvent event)
			{
				onProductSelected(product);
			}
		});
		
		return button;
	}

	private void onProductSelected(final Product product)
	{
		productQtyReportRepository.addFavoriteProduct(product);
	}
	
	private void onShowNotContractedProducts()
	{
		productsNotContractedNorFavoritePanel.removeAllComponents();
		
		final List<Product> productsNotContractedNorFavorite = productQtyReportRepository.getProductsNotContractedNorFavorite();
		for (final Product product : productsNotContractedNorFavorite)
		{
			final ProductButton button = createProductButton(product);
			productsNotContractedNorFavoritePanel.addComponent(button);
		}
	}


	private static final class ProductButton extends GenericProductButton<Product>
	{
		public void setProduct(final Product product)
		{
			updateUI(product);
		}

		@Override
		protected String extractCaption(final Product bean)
		{
			if (bean == null)
			{
				return null;
			}
			
			final Locale locale = UI.getCurrent().getLocale();
			return buildCaptionFromProductName(bean.getName(locale), bean.getPackingInfo(locale));
		}

		@Override
		protected String extractDescription(final Product bean)
		{
			return null;
		}
	}
}
