/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.util;

import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.ui.web.WebuiURLs.SYSCONFIG_FRONTEND_URL;

@UtilityClass
public class TableRecordUtil
{
	private static final String WINDOW = "window";

	@Nullable
	public static String getMetasfreshUrl(@NonNull final TableRecordReference recordReference)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String webUIFrontendUrl = sysConfigBL.getValue(SYSCONFIG_FRONTEND_URL);

		final Optional<AdWindowId> windowId = RecordWindowFinder.findAdWindowId(recordReference);

		if (!windowId.isPresent())
		{
			return null;
		}

		return webUIFrontendUrl + "/" + WINDOW + "/" + windowId.get().getRepoId() + "/" + recordReference.getRecord_ID();
	}
}
