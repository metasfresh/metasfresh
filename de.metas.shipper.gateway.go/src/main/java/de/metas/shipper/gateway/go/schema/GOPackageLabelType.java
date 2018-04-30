package de.metas.shipper.gateway.go.schema;

import de.metas.shipper.gateway.spi.model.PackageLabelType;

/*
 * #%L
 * de.metas.shipper.gateway.go
 * %%
 * Copyright (C) 2017 metas GmbH
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

public enum GOPackageLabelType implements PackageLabelType
{
	/** DIN A4 HWB (Tag Frachtbrief) */
	DIN_A4_HWB,
	/** DIN A6 routerlabel, optimized for Citizen and Zebra labelprinters (Tag RouterlabelZebra) */
	DIN_A6_ROUTER_LABEL,
	/** DIN A6 routerlabel for any other labelprinters (Tag Routerlabel) */
	DIN_A6_ROUTER_LABEL_ZEBRA,
}
