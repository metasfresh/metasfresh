package de.metas.ui.web.quickinput;

import com.google.common.annotations.VisibleForTesting;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class QuickInputConstants
{
	private static final String SYSCONFIG_EnablePackingInstructionsField = "webui.quickinput.EnablePackingInstructionsField";
	private static final String SYSCONFIG_EnableLUFields = "webui.quickinput.EnableLUFields";
	private static final String SYSCONFIG_EnableBestBeforePolicy = "webui.quickinput.EnableBestBeforePolicy";
	private static final String SYSCONFIG_EnableVatCodeField = "webui.quickinput.EnableVatCodeField";
	private static final String SYSCONFIG_EnableContractConditionsField = "webui.quickinput.EnableContractConditionsField";
	private static final String SYSCONFIG_IsContractConditionsFieldMandatory = "webui.quickinput.IsContractConditionsFieldMandatory";
	private static final String SYSCONFIG_ProductFieldWidgetSize = "webui.quickinput.ProductFieldWidgetSize";

	private static final Logger logger = LogManager.getLogger(QuickInputConstants.class);

	/**
	 * Created for https://github.com/metasfresh/metasfresh/issues/14009 where we want batch entry dropdown to contain "ALL" potential matches,
	 * not just the first 10 (see de.metas.ui.web.window.model.lookup.LookupDataSource#DEFAULT_PageLength).
	 * Because "ALL" is a recipe for OOMs and stalled requests, we're using this constant instead as pageLength.
	 */
	public static final Integer BIG_ENOUGH_PAGE_LENGTH = 200;

	public static boolean isEnablePackingInstructionsField()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnablePackingInstructionsField, true);
	}

	public static boolean isLUFieldsEnabled(@NonNull final SOTrx soTrx)
	{
		return soTrx.isPurchase() && Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableLUFields, false);
	}

	public static boolean isEnableBestBeforePolicy()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableBestBeforePolicy, true);
	}

	public static boolean isEnableVatCodeField()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableVatCodeField, false);
	}

	public static boolean isEnableContractConditionsField()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableContractConditionsField, false);
	}

	public static boolean isContractConditionsFieldMandatory()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_IsContractConditionsFieldMandatory, false);
	}

	/**
	 * Widget size (width) of the Product field in the order-line quick-input panel.
	 * blank / unset / "-" => null (Default width, unchanged). See webui.quickinput.ProductFieldWidgetSize.
	 */
	@Nullable
	public static WidgetSize getProductFieldWidgetSize()
	{
		final String value = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ProductFieldWidgetSize, (String)null);
		return parseProductFieldWidgetSize(value);
	}

	@Nullable
	@VisibleForTesting
	static WidgetSize parseProductFieldWidgetSize(@Nullable final String value)
	{
		if (Check.isBlank(value))
		{
			return null;
		}
		final String trimmed = value.trim();
		if ("-".equals(trimmed))
		{
			return null; // empty-sentinel: Check.isBlank("-") is false, so map it explicitly
		}
		try
		{
			return WidgetSize.fromNullableADRefListValue(trimmed); // S/M/L/XL/XXL
		}
		catch (final NoSuchElementException e)
		{
			// This is a cosmetic, default-off setting: a bad value (typo, wrong case e.g. "l", "Large", "30em")
			// must NEVER break order-line batch entry. The descriptor is memoized per node (QuickInputDescriptors
			// CCache) with no client/org key, so a throw here would brick the batch-entry panel for every user on
			// that node until the value is fixed and caches are reset. Degrade to Default width and warn instead.
			logger.warn("Ignoring invalid {}=\"{}\" (expected one of S/M/L/XL/XXL); using Default width",
					SYSCONFIG_ProductFieldWidgetSize, trimmed, e);
			return null;
		}
	}
}
