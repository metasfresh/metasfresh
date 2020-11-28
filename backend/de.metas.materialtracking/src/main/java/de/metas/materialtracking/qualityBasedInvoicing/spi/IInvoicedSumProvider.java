package de.metas.materialtracking.qualityBasedInvoicing.spi;

import java.math.BigDecimal;

import de.metas.materialtracking.model.I_M_Material_Tracking;

/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * This interface returns the amount of money that was already invoiced for a given material tracking.<br>
 * We encapsulated the code like this so that we can provide a mocked implementation when doing module
 * tests.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IInvoicedSumProvider
{
	BigDecimal getAlreadyInvoicedNetSum(I_M_Material_Tracking materialTracking);
}
