package de.metas.procurement.webui.ui.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationListener;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.util.JavascriptUtils;

/*
 * #%L
 * metasfresh-procurement-webui
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

@SuppressWarnings("serial")
public class NumberEditorView<BT> extends MFProcurementNavigationView
{
	private static final String STYLE = DailyProductQtyReportView.STYLE; // FIXME: refactor to a common style

	// private static final Logger logger = LoggerFactory.getLogger(NumberEditorView.class);

	private final String numberPropertyId;

	@Autowired
	private I18N i18n;

	private BeanItem<BT> item;

	private Form form;

	private final NavigationListener navigationListener = new NavigationListener()
	{

		@Override
		public void navigate(final NavigationEvent event)
		{
			final NavigationManager navigationManager = getNavigationManager();
			if (navigationManager != null && navigationManager.getCurrentComponent() != NumberEditorView.this)
			{
				commit();
				navigationManager.removeListener(this);
			}
		}
	};

	private Converter<String, BigDecimal> numberConverter = new StringToBigDecimalConverter()
	{
		@Override
		protected NumberFormat getFormat(final Locale locale)
		{
			final NumberFormat format = super.getFormat(locale);
			format.setGroupingUsed(false); // FRESH-126
			return format;
		}
	};

	public NumberEditorView(final String numberPropertyId)
	{
		super();
		addStyleName(STYLE);

		this.numberPropertyId = numberPropertyId;
		setCaptionAndTranslate(numberPropertyId);
	}

	public NumberEditorView<BT> setNumberConverter(final Converter<String, BigDecimal> numberConverter)
	{
		this.numberConverter = Preconditions.checkNotNull(numberConverter, "numberConverter");
		return this;
	}

	public NumberEditorView<BT> setCaptionAndTranslate(final String captionMessage, final Object... arguments)
	{
		final String captionTrl = i18n.getWithDefault(captionMessage, captionMessage, arguments);
		setCaption(captionTrl);
		return this;
	}

	@Override
	protected void onBecomingVisible()
	{
		super.onBecomingVisible();

		if (item == null)
		{
			return;
		}

		//
		// Content
		{
			final VerticalLayout content = new VerticalLayout();

			//
			// Quantity input
			{
				form = new Form(item);
				form.focus();
				content.addComponent(form);
			}

			setContent(content);
		}

		getNavigationManager().addNavigationListener(navigationListener);
	}

	@Override
	public void detach()
	{
		super.detach();
	}

	public void setItem(final BeanItem<BT> itemNew)
	{
		item = itemNew;
	}

	/**
	 * @return true if all fields are valid and commit was successful
	 */
	private boolean commit()
	{
		if (form != null)
		{
			form.commit();

			if (!form.isValid())
			{
				return false;
			}

			form = null;
		}

		return true;
	}

	private final void commitAndGoBack()
	{
		if (!commit())
		{
			// if commit was not successful, stop here, don't go back
			return;
		}
		getNavigationManager().navigateBack();
	}

	private final class Form extends CssLayout
	{
		private final TextField qty;
		private final FieldGroup binder;

		public Form(final BeanItem<BT> item)
		{
			super();
			addStyleName("input-form");

			//
			// Qty
			qty = new NumberField();
			qty.setConverter(numberConverter);
			qty.setConversionError(i18n.get("DailyProductQtyReportView.error.InvalidValue"));
			qty.addValidator(new BigDecimalRangeValidator(i18n.get("DailyProductQtyReportView.error.InvalidValue"), BigDecimal.ZERO, null)); // FRESH-144
			qty.setImmediate(true);

			// NOTE: because "qty.selectAll" seems to not work, we are doing this job directly in javascript.
			qty.setId(DailyProductQtyReportView.class.getSimpleName() + "_qty");
			JavascriptUtils.enableSelectAllOnFocus(qty);

			qty.focus();
			addComponent(qty);

			//
			// Because on Android, in numeric soft keyboard, when u press OK it actually navigates to next component,
			// and if there is next component it will actually select the URL omnibar (!?!?),
			// we do following workaround.
			// see: http://stackoverflow.com/questions/6545086/html-why-does-android-browser-show-go-instead-of-next-in-keyboard
			final TextField nextCapturer = new TextField();
			nextCapturer.setStyleName("nextEventCapturer");
			nextCapturer.addFocusListener(new FocusListener()
			{

				@Override
				public void focus(final FocusEvent event)
				{
					commitAndGoBack();
				}
			});
			addComponent(nextCapturer);

			//
			// Bind the form
			binder = new FieldGroup();
			binder.setBuffered(false); // update the fields immediately
			binder.setItemDataSource(item);
			binder.bind(qty, numberPropertyId);

			addShortcutListener(new ShortcutListener("submit", KeyCode.ENTER, null)
			{
				@Override
				public void handleAction(final Object sender, final Object target)
				{
					commitAndGoBack();
				}
			});

			qty.selectAll();
			qty.focus();

			// Listen value change on qty field, just to catch events which are coming after user navigated outside of this view.
			// This is a workaround to cover following case:
			// * write something in Qty box
			// * press Back button (DO NOT press enter, OK, tab or something else)
			qty.addValueChangeListener(new Property.ValueChangeListener()
			{
				@Override
				public void valueChange(final ValueChangeEvent event)
				{
					updateSentStatus();
				}
			});
		}

		private final void updateSentStatus()
		{
			// nothing
		}

		public boolean isValid()
		{
			return binder.isValid();
		}

		public final void commit()
		{
			try
			{
				// Validate all fields
				qty.validate(); // throws InvalidValueException which will be handled on upper level

				binder.commit();

				updateSentStatus();
			}
			catch (final CommitException e)
			{
				Notification.show(i18n.get("DailyProductQtyReportView.error.InvalidValue"));
			}
		}

		@Override
		protected void focus()
		{
			qty.focus();
		}
	}
}
