package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.math.BigDecimal;
import java.util.Date;

import de.metas.material.planning.event.MaterialDemandEventListener;

/**
 * Instances of this interface specify a material demand. Currently there is no "real" service from which an instance can be obtained.
 * <p>
 * See {@link MaterialDemandEventListener}.
 *
 * Note that there is a sub interface in libero which contains additional fields and it therefore more tightly coupled to libero.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IMaterialRequest
{
	/** @return context */
	IMaterialPlanningContext getMrpContext();

	/** @return how much quantity is needed to supply */
	BigDecimal getQtyToSupply();

	/** @return demand date; i.e. the date when quantity to supply is really needed */
	Date getDemandDate();

	/**
	 * @return C_BPartner_ID or -1
	 */
	int getMrpDemandBPartnerId();

	/**
	 * @return sales C_OrderLine_ID or -1
	 */
	int getMrpDemandOrderLineSOId();
}
