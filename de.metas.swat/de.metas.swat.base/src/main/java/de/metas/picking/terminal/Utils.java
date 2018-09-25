/**
 *
 */
package de.metas.picking.terminal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.awt.Color;
import java.math.BigDecimal;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.UOMConversionContext;

import de.metas.picking.legacy.form.IPackingItem;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class Utils
{
	public static enum PackingStates
	{
		packed(Color.GREEN), //
		partiallypacked(Color.YELLOW), //
		unpacked(Color.RED), //
		overlapped(new Color(255, 165, 79)), //
		ready(new Color(34, 139, 34)), //
		open(new Color(255, 102, 0)), //
		closed(new Color(0, 153, 51)); // dark green

		@Getter
		private final Color color;

		PackingStates(Color color)
		{
			this.color = color;
		}
	};

	private static final String buttonSize = "h 80:80:80, w 80:80:80,";

	public static String getButtonSize()
	{
		return buttonSize;
	}

	public static final BigDecimal convertToItemUOM(
			@NonNull final IPackingItem packingItem,
			@NonNull final Quantity quantity)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		return uomConversionBL.convertQty(
				UOMConversionContext.of(packingItem.getProductId()),
				quantity.getAsBigDecimal(),
				quantity.getUOM(),
				packingItem.getC_UOM());
	}
}
