package de.metas.ui.web.window.descriptor;

import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptorProviderBuilder;
import de.metas.util.Functions;
import de.metas.util.Functions.MemoizingFunction;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

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

@Component
public class LookupDescriptorProviders
{
	public static LookupDescriptorProviders sharedInstance()
	{
		return SpringContextHolder.instance.getBeanOr(LookupDescriptorProviders.class, _sharedInstance);
	}

	private static final LookupDescriptorProviders _sharedInstance = new LookupDescriptorProviders(null);

	/**
	 * Provider which returns <code>Optional.empty()</code> for any scope)
	 */
	public static final LookupDescriptorProvider NULL = new NullLookupDescriptorProvider();

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private ADReferenceService _adReferenceService;

	private static final String SYSCONFIG_PageLength = "webui.lookup.pageLength";
	private static final int DEFAULT_PageLength = 10;

	public LookupDescriptorProviders(
			@Nullable final ADReferenceService adReferenceService)
	{
		this._adReferenceService = adReferenceService;
	}

	public ADReferenceService getAdReferenceService()
	{
		ADReferenceService adReferenceService = this._adReferenceService;
		if (adReferenceService == null)
		{
			adReferenceService = this._adReferenceService = ADReferenceService.get();
		}
		return adReferenceService;
	}

	public SqlLookupDescriptorProviderBuilder sql()
	{
		return new SqlLookupDescriptorProviderBuilder(getAdReferenceService())
				.setPageLength(sysConfigBL.getIntValue(SYSCONFIG_PageLength, DEFAULT_PageLength));
	}

	public LookupDescriptorProvider searchByAD_Val_Rule_ID(
			@NonNull final ReferenceId AD_Reference_Value_ID,
			@Nullable final AdValRuleId AD_Val_Rule_ID)
	{
		return sql()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(null)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setAD_Val_Rule_ID(AD_Val_Rule_ID)
				.setDisplayType(DisplayType.Search)
				.setReadOnlyAccess()
				.build();
	}

	public LookupDescriptorProvider searchInTable(@NonNull final String lookupTableName)
	{
		return prepareSearchInTable(lookupTableName).build();
	}

	@NonNull
	public LookupDescriptorProvider searchInTable(@NonNull final String lookupTableName, @Nullable final AdValRuleId ruleId)
	{
		return prepareSearchInTable(lookupTableName).setAD_Val_Rule_ID(ruleId).build();
	}

	@NonNull
	public LookupDescriptorProvider searchInTable(@NonNull final String lookupTableName, @Nullable final IValidationRule rule)
	{
		return prepareSearchInTable(lookupTableName).addValidationRule(rule).build();
	}

	private SqlLookupDescriptorProviderBuilder prepareSearchInTable(@NonNull final String lookupTableName)
	{
		return sql()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(InterfaceWrapperHelper.getKeyColumnName(lookupTableName))
				.setDisplayType(DisplayType.Search)
				.setReadOnlyAccess();
	}

	public LookupDescriptorProvider listByAD_Reference_Value_ID(@NonNull final ReferenceId AD_Reference_Value_ID)
	{
		return sql()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(null)
				.setDisplayType(DisplayType.List)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setReadOnlyAccess()
				.build();
	}

	public LookupDescriptor productAttributes()
	{
		return sql()
				.setCtxTableName(null) // tableName
				.setCtxColumnName(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID)
				.setDisplayType(DisplayType.PAttribute)
				.setReadOnlyAccess()
				.build()
				.provide()
				.orElseThrow(() -> new AdempiereException("No lookup descriptor found for Product Attributes"));

	}

	/**
	 * @return provider which returns given {@link LookupDescriptor} for any scope
	 */
	public static LookupDescriptorProvider singleton(@NonNull final LookupDescriptor lookupDescriptor)
	{
		return new SingletonLookupDescriptorProvider(lookupDescriptor);
	}

	public static LookupDescriptorProvider ofNullableInstance(@Nullable final LookupDescriptor lookupDescriptor)
	{
		return lookupDescriptor != null ? singleton(lookupDescriptor) : NULL;
	}

	/**
	 * @return provider which calls the given function (memoized)
	 */
	public static LookupDescriptorProvider fromMemoizingFunction(final Function<LookupScope, LookupDescriptor> providerFunction)
	{
		return new MemoizingFunctionLookupDescriptorProvider(providerFunction);
	}

	//
	//
	//

	private static class NullLookupDescriptorProvider implements LookupDescriptorProvider
	{
		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return Optional.empty();
		}
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@ToString
	private static class SingletonLookupDescriptorProvider implements LookupDescriptorProvider
	{
		private final Optional<LookupDescriptor> lookupDescriptor;

		private SingletonLookupDescriptorProvider(@NonNull final LookupDescriptor lookupDescriptor)
		{
			this.lookupDescriptor = Optional.of(lookupDescriptor);
		}

		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return lookupDescriptor;
		}
	}

	@ToString
	private static class MemoizingFunctionLookupDescriptorProvider implements LookupDescriptorProvider
	{
		private final MemoizingFunction<LookupScope, LookupDescriptor> providerFunctionMemoized;

		private MemoizingFunctionLookupDescriptorProvider(@NonNull final Function<LookupScope, LookupDescriptor> providerFunction)
		{
			providerFunctionMemoized = Functions.memoizing(providerFunction);
		}

		@Override
		public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
		{
			return Optional.ofNullable(providerFunctionMemoized.apply(scope));
		}
	}
}
