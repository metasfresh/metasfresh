package de.metas.ui.web.window.datatypes.json;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;

import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class JSONDocumentLayoutOptions
{
	public static JSONDocumentLayoutOptionsBuilder prepareFrom(@NonNull final UserSession userSession)
	{
		return _builder()
				.jsonOpts(JSONOptions.of(userSession))
				.debugShowColumnNamesForCaption(userSession.isShowColumnNamesForCaption())
				.defaultLookupSearchStartDelaySupplier(ExtendedMemorizingSupplier.of(userSession.getDefaultLookupSearchStartDelay()));
	}

	public static JSONDocumentLayoutOptions of(@NonNull final UserSession userSession)
	{
		return prepareFrom(userSession).build();
	}

	@VisibleForTesting
	@Deprecated
	public static JSONDocumentLayoutOptions ofAdLanguage(@NonNull final String adLanguage)
	{
		final JSONOptions jsonOpts = JSONOptions.ofAdLanguage(adLanguage);
		return _builder().jsonOpts(jsonOpts).build();
	}

	@Getter
	private final JSONOptions jsonOpts;
	@Getter
	private final boolean showAdvancedFields;
	@Getter
	private final boolean debugShowColumnNamesForCaption;
	@Getter
	private final NewRecordDescriptorsProvider newRecordDescriptorsProvider;
	private final Supplier<Duration> defaultLookupSearchStartDelaySupplier;
	private static final Supplier<Duration> ZERO_DURATION_SUPPLIER = Suppliers.ofInstance(Duration.ZERO);

	private Predicate<DocumentLayoutElementDescriptor> _documentLayoutElementFilter; // lazy

	@Builder(builderMethodName = "_builder")
	private JSONDocumentLayoutOptions(
			@NonNull final JSONOptions jsonOpts,
			final boolean showAdvancedFields,
			final boolean debugShowColumnNamesForCaption,
			@Nullable final NewRecordDescriptorsProvider newRecordDescriptorsProvider,
			@Nullable final Supplier<Duration> defaultLookupSearchStartDelaySupplier)
	{
		this.jsonOpts = jsonOpts;
		this.showAdvancedFields = showAdvancedFields;
		this.debugShowColumnNamesForCaption = debugShowColumnNamesForCaption;
		this.newRecordDescriptorsProvider = newRecordDescriptorsProvider;
		this.defaultLookupSearchStartDelaySupplier = defaultLookupSearchStartDelaySupplier != null
				? defaultLookupSearchStartDelaySupplier
				: ZERO_DURATION_SUPPLIER;
	}

	public Duration getDefaultLookupSearchStartDelay()
	{
		final Duration duration = defaultLookupSearchStartDelaySupplier.get();
		return duration != null ? duration : Duration.ZERO;
	}

	public Predicate<DocumentLayoutElementDescriptor> documentLayoutElementFilter()
	{
		if (_documentLayoutElementFilter == null)
		{
			_documentLayoutElementFilter = isShowAdvancedFields() ? FILTER_DocumentLayoutElementDescriptor_ALL : FILTER_DocumentLayoutElementDescriptor_BASIC;
		}
		return _documentLayoutElementFilter;
	}

	private static final Predicate<DocumentLayoutElementDescriptor> FILTER_DocumentLayoutElementDescriptor_BASIC = new Predicate<DocumentLayoutElementDescriptor>()
	{
		@Override
		public String toString()
		{
			return "basic layout elements";
		};

		@Override
		public boolean test(final DocumentLayoutElementDescriptor layoutElement)
		{
			return !layoutElement.isAdvancedField();
		};
	};

	private static final Predicate<DocumentLayoutElementDescriptor> FILTER_DocumentLayoutElementDescriptor_ALL = new Predicate<DocumentLayoutElementDescriptor>()
	{
		@Override
		public String toString()
		{
			return "all layout elements";
		};

		@Override
		public boolean test(final DocumentLayoutElementDescriptor layoutElement)
		{
			return true;
		};
	};

	public String getAdLanguage()
	{
		return getJsonOpts().getAdLanguage();
	}
}
