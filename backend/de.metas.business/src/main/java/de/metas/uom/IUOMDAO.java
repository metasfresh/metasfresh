package de.metas.uom;

import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import org.compiere.model.I_C_UOM;

import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IUOMDAO extends ISingletonService
{
	String X12DE355_Each = "PCE";
	int C_UOM_ID_Each = 100;

	String X12DE355_Kilogram = "KGM";
	String X12DE355_TU = "TU";
	String X12DE355_COLI = "COLI";

	I_C_UOM getById(int uomId);

	I_C_UOM getByIdOrNull(int uomId);

	I_C_UOM getById(UomId uomId);

	List<I_C_UOM> getByIds(Collection<UomId> uomIds);

	UomId getUomIdByX12DE355(String x12de355);

	String getX12DE355ById(UomId uomId);

	/**
	 * @return uom; never return null
	 */
	I_C_UOM retrieveByX12DE355(Properties ctx, String x12de355);

	/**
	 * @param throwExIfNull if <code>false</code> and there is no UOM with the given <code>x12de355</code>, then we return <code>null</code>.
	 * @return uom; never return null
	 */
	I_C_UOM retrieveByX12DE355(Properties ctx, String x12de355, boolean throwExIfNull);

	/**
	 * Gets UOM for Each/Stuck.
	 */
	I_C_UOM retrieveEachUOM(Properties ctx);

	TemporalUnit getTemporalUnitByUomId(UomId uomId);

	UOMPrecision getStandardPrecision(UomId uomId);

	/**
	 * Tells if the given UOM is used for TUs.
	 * This is significant because in order to convert from the TU to a CU-quantity,
	 * we need to look at the respective PIIP or {@code QtyItemCapacity} and not at any UOM-conversion-rate.
	 */
	boolean isUOMForTUs(UomId uomId);

	@NonNull
	UOMType getUOMTypeById(UomId uomId);
}
