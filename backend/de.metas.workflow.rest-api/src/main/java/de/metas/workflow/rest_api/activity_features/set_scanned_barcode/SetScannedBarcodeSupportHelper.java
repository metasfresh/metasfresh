/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.workflow.rest_api.activity_features.set_scanned_barcode;

import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import lombok.experimental.UtilityClass;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;

@UtilityClass
public class SetScannedBarcodeSupportHelper
{
	public static UIComponent createUIComponent(
			@Nullable final String barcodeCaption)
	{
		return UIComponent.builder()
				.type(UIComponentType.SCAN_BARCODE)
				.properties(Params.builder()
						.value("barcodeCaption", barcodeCaption)
						.build())
				.build();
	}
}
