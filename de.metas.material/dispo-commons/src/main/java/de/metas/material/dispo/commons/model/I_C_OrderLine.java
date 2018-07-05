/**
 *
 */
package de.metas.material.dispo.commons.model;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine
{
    public static final String COLUMNNAME_Qty_AvailableToPromise = "Qty_AvailableToPromise";
	public void setQty_AvailableToPromise (java.math.BigDecimal Qty_AvailableToPromise);
	public java.math.BigDecimal getQty_AvailableToPromise();
}
