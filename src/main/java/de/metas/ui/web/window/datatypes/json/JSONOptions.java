package de.metas.ui.web.window.datatypes.json;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ILanguageDAO;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.model.DocumentFieldChange;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * JSON context: provide different options and filters to be used when the API responses are converted to/from JSON.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class JSONOptions
{
	public static final Builder builder(final UserSession userSession)
	{
		return new Builder(userSession);
	}

	public static final JSONOptions of(final UserSession userSession)
	{
		return new Builder(userSession).build();
	}

	public static final String DEBUG_ATTRNAME = "json-options";

	private final String adLanguage;
	private final boolean showAdvancedFields;
	private final String dataFieldsListStr;
	private final boolean debugShowColumnNamesForCaption;

	private static final transient Splitter FIELDS_LIST_SPLITTER = Splitter.on(",")
			.trimResults()
			.omitEmptyStrings();

	private Predicate<DocumentLayoutElementDescriptor> _documentLayoutElementFilter; // lazy
	private Predicate<IDocumentFieldView> _documentFieldFilter; // lazy
	private Predicate<DocumentFieldChange> _documentFieldChangeFilter; // lazy

	private final NewRecordDescriptorsProvider newRecordDescriptorsProvider;

	private final Supplier<JSONDocumentPermissions> documentPermissionsSupplier;

	private JSONOptions(final Builder builder)
	{
		adLanguage = builder.getAD_Language();
		showAdvancedFields = builder.isShowAdvancedFields();
		dataFieldsListStr = Strings.emptyToNull(builder.dataFieldsListStr);
		debugShowColumnNamesForCaption = builder.isShowColumnNamesForCaption(false);

		newRecordDescriptorsProvider = builder.getNewRecordDescriptorsProvider();

		documentPermissionsSupplier = builder.getPermissionsSupplier();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("showAdvancedFields", showAdvancedFields)
				.add("dataFieldsListStr", dataFieldsListStr)
				.add("debugShowColumnNamesForCaption", debugShowColumnNamesForCaption)
				.toString();
	}

	public String getAD_Language()
	{
		return adLanguage;
	}

	public boolean isShowAdvancedFields()
	{
		return showAdvancedFields;
	}

	public boolean isDebugShowColumnNamesForCaption()
	{
		return debugShowColumnNamesForCaption;
	}

	public boolean isProtocolDebugging()
	{
		return WindowConstants.isProtocolDebugging();
	}

	public Predicate<DocumentLayoutElementDescriptor> documentLayoutElementFilter()
	{
		if (_documentLayoutElementFilter == null)
		{
			_documentLayoutElementFilter = showAdvancedFields ? FILTER_DocumentLayoutElementDescriptor_ALL : FILTER_DocumentLayoutElementDescriptor_BASIC;
		}
		return _documentLayoutElementFilter;
	}

	public Predicate<IDocumentFieldView> documentFieldFilter()
	{
		if (_documentFieldFilter == null)
		{
			_documentFieldFilter = createDocumentFieldFilter();
		}
		return _documentFieldFilter;
	}

	private Predicate<IDocumentFieldView> createDocumentFieldFilter()
	{
		final Predicate<IDocumentFieldView> filter = showAdvancedFields ? FILTER_DocumentFieldView_ALL_PUBLIC_FIELDS : FILTER_DocumentFieldView_BASIC_PUBLIC_FIELDS;

		final Set<String> dataFieldNamesSet = Check.isEmpty(dataFieldsListStr, true) ? ImmutableSet.of() : ImmutableSet.copyOf(FIELDS_LIST_SPLITTER.splitToList(dataFieldsListStr));
		if (dataFieldNamesSet.isEmpty() || dataFieldNamesSet.contains("*"))
		{
			return filter;
		}

		return new FILTER_DocumentFieldView_ByFieldNamesSet(dataFieldNamesSet, filter);
	}

	public Predicate<DocumentFieldChange> documentFieldChangeFilter()
	{
		if (_documentFieldChangeFilter == null)
		{
			_documentFieldChangeFilter = createDocumentFieldChangeFilter();
		}

		return _documentFieldChangeFilter;
	}

	private Predicate<DocumentFieldChange> createDocumentFieldChangeFilter()
	{
		final Predicate<DocumentFieldChange> filter = showAdvancedFields ? FILTER_DocumentFieldChange_ALL_PUBLIC_FIELDS : FILTER_DocumentFieldChange_BASIC_PUBLIC_FIELDS;

		final Set<String> dataFieldNamesSet = Check.isEmpty(dataFieldsListStr, true) ? ImmutableSet.of() : ImmutableSet.copyOf(FIELDS_LIST_SPLITTER.splitToList(dataFieldsListStr));
		if (dataFieldNamesSet.isEmpty() || dataFieldNamesSet.contains("*"))
		{
			return filter;
		}

		return new FILTER_DocumentFieldChange_ByFieldNamesSet(dataFieldNamesSet, filter);
	}

	/**
	 * @return NewRecordDescriptorsProvider or null
	 */
	public NewRecordDescriptorsProvider getNewRecordDescriptorsProvider()
	{
		return newRecordDescriptorsProvider;
	}

	public JSONDocumentPermissions getDocumentPermissions()
	{
		return documentPermissionsSupplier.get();
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

	private static final Predicate<IDocumentFieldView> FILTER_DocumentFieldView_BASIC_PUBLIC_FIELDS = new Predicate<IDocumentFieldView>()
	{
		@Override
		public String toString()
		{
			return "basic public fields";
		};

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			return field.isPublicField() && !field.isAdvancedField();
		}
	};

	private static final Predicate<DocumentFieldChange> FILTER_DocumentFieldChange_BASIC_PUBLIC_FIELDS = new Predicate<DocumentFieldChange>()
	{
		@Override
		public String toString()
		{
			return "basic public fields";
		};

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			return field.isPublicField() && !field.isAdvancedField();
		}
	};

	private static final Predicate<IDocumentFieldView> FILTER_DocumentFieldView_ALL_PUBLIC_FIELDS = new Predicate<IDocumentFieldView>()
	{
		@Override
		public String toString()
		{
			return "all public fields";
		};

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			return field.isPublicField();
		}
	};

	private static final Predicate<DocumentFieldChange> FILTER_DocumentFieldChange_ALL_PUBLIC_FIELDS = new Predicate<DocumentFieldChange>()
	{
		@Override
		public String toString()
		{
			return "all public fields";
		};

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			return field.isPublicField();
		}
	};

	private static final class FILTER_DocumentFieldView_ByFieldNamesSet implements Predicate<IDocumentFieldView>
	{
		private final Set<String> fieldNamesSet;
		private final Predicate<IDocumentFieldView> parentFilter;

		private FILTER_DocumentFieldView_ByFieldNamesSet(final Set<String> fieldNamesSet, final Predicate<IDocumentFieldView> parentFilter)
		{
			super();
			this.fieldNamesSet = fieldNamesSet;
			this.parentFilter = parentFilter;
		}

		@Override
		public String toString()
		{
			return "field name in " + fieldNamesSet + " and " + parentFilter;
		};

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			if (!fieldNamesSet.contains(field.getFieldName()))
			{
				return false;
			}

			return parentFilter.test(field);
		}
	};

	private static final class FILTER_DocumentFieldChange_ByFieldNamesSet implements Predicate<DocumentFieldChange>
	{
		private final Set<String> fieldNamesSet;
		private final Predicate<DocumentFieldChange> parentFilter;

		private FILTER_DocumentFieldChange_ByFieldNamesSet(final Set<String> fieldNamesSet, final Predicate<DocumentFieldChange> parentFilter)
		{
			super();
			this.fieldNamesSet = fieldNamesSet;
			this.parentFilter = parentFilter;
		}

		@Override
		public String toString()
		{
			return "field name in " + fieldNamesSet + " and " + parentFilter;
		};

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			if (!fieldNamesSet.contains(field.getFieldName()))
			{
				return false;
			}

			return parentFilter.test(field);
		}
	};

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final UserSession _userSession;
		private boolean showAdvancedFields = false;
		private String dataFieldsListStr = null;
		private String adLanguage;
		private final String sessionADLanguage;
		private NewRecordDescriptorsProvider newRecordDescriptorsProvider;

		private Builder(final UserSession userSession)
		{
			super();
			_userSession = userSession;
			sessionADLanguage = userSession != null ? userSession.getAD_Language() : null;
		}

		public JSONOptions build()
		{
			return new JSONOptions(this);
		}

		public Builder setAD_LanguageIfNotEmpty(final String adLanguage)
		{
			if (Check.isEmpty(adLanguage, true))
			{
				return this;
			}

			this.adLanguage = adLanguage.trim();
			return this;
		}

		private String getAD_Language()
		{
			if (adLanguage != null)
			{
				return adLanguage;
			}

			//
			// Try fetching the AD_Language from "Accept-Language" HTTP header
			if(_userSession != null && _userSession.isUseHttpAcceptLanguage())
			{
				HttpServletRequest httpServletRequest = getHttpServletRequest();
				if (httpServletRequest != null)
				{
					final String httpAcceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
					if (!Check.isEmpty(httpAcceptLanguage, true))
					{
						final String requestLanguage = Services.get(ILanguageDAO.class).retrieveAvailableLanguages()
								.getAD_LanguageFromHttpAcceptLanguage(httpAcceptLanguage, sessionADLanguage);
						if (requestLanguage != null)
						{
							return requestLanguage;
						}
					}
				}
			}

			//
			// Use session language
			if (sessionADLanguage != null)
			{
				return sessionADLanguage;
			}

			throw new IllegalStateException("Cannot detect the AD_Language");
		}

		private static final HttpServletRequest getHttpServletRequest()
		{
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (requestAttributes == null)
			{
				return null;
			}
			if (!(requestAttributes instanceof ServletRequestAttributes))
			{
				return null;
			}

			final HttpServletRequest servletRequest = ((ServletRequestAttributes)requestAttributes).getRequest();
			return servletRequest;
		}

		private Supplier<JSONDocumentPermissions> getPermissionsSupplier()
		{
			return createPermissionsSupplier(_userSession);
		}

		private static final Supplier<JSONDocumentPermissions> createPermissionsSupplier(final UserSession userSession)
		{
			if (userSession == null)
			{
				return () -> null;
			}

			return ExtendedMemorizingSupplier.of(() -> {
				final IUserRolePermissions userRolePermissions = userSession.getUserRolePermissions();
				return new JSONDocumentPermissions(userRolePermissions);
			});
		}

		public Builder setShowAdvancedFields(final boolean showAdvancedFields)
		{
			this.showAdvancedFields = showAdvancedFields;
			return this;
		}

		private boolean isShowAdvancedFields()
		{
			return showAdvancedFields;
		}

		public Builder setDataFieldsList(final String dataFieldsListStr)
		{
			this.dataFieldsListStr = dataFieldsListStr;
			return this;
		}

		private boolean isShowColumnNamesForCaption(final boolean defaultValue)
		{
			if (_userSession != null)
			{
				return _userSession.isShowColumnNamesForCaption();
			}

			return defaultValue;
		}

		private NewRecordDescriptorsProvider getNewRecordDescriptorsProvider()
		{
			return newRecordDescriptorsProvider;
		}

		public Builder setNewRecordDescriptorsProvider(final NewRecordDescriptorsProvider newRecordDescriptorsProvider)
		{
			this.newRecordDescriptorsProvider = newRecordDescriptorsProvider;
			return this;
		}
	}
}
