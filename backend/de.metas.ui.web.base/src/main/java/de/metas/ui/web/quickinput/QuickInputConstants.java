package de.metas.ui.web.quickinput;

import de.metas.util.Services;
import lombok.experimental.UtilityClass;
import org.adempiere.service.ISysConfigBL;

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
	private static final String SYSCONFIG_EnableBestBeforePolicy = "webui.quickinput.EnableBestBeforePolicy";
	private static final String SYSCONFIG_EnableVatCodeField = "webui.quickinput.EnableVatCodeField";

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

	public static boolean isEnableBestBeforePolicy()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableBestBeforePolicy, true);
	}

	public static boolean isEnableVatCodeField()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnableVatCodeField, false);
	}

}
