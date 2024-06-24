/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.uom;

import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUOMDAO extends ISingletonService
{

	I_C_UOM getById(int uomId);

	@Nullable
	I_C_UOM getByIdOrNull(int uomId);

	I_C_UOM getById(UomId uomId);

	ITranslatableString getName(@NonNull UomId uomId);

	List<I_C_UOM> getByIds(Collection<UomId> uomIds);

	UomId getUomIdByX12DE355(X12DE355 x12de355);

	X12DE355 getX12DE355ById(UomId uomId);

	@NonNull
	I_C_UOM getByX12DE355(X12DE355 x12de355);

	Optional<I_C_UOM> getByX12DE355IfExists(X12DE355 x12de355);

	/**
	 * Gets UOM for Each/Stuck.
	 */
	I_C_UOM getEachUOM();

	TemporalUnit getTemporalUnitByUomId(UomId uomId);

	I_C_UOM getByTemporalUnit(@NonNull TemporalUnit temporalUnit);

	UomId getUomIdByTemporalUnit(@NonNull TemporalUnit temporalUnit);

	UOMPrecision getStandardPrecision(UomId uomId);

	/**
	 * Tells if the given UOM is used for TUs.
	 * This is significant because in order to convert from the TU to a CU-quantity,
	 * we need to look at the respective PIIP or {@code QtyItemCapacity} and not at any UOM-conversion-rate.
	 */
	boolean isUOMForTUs(UomId uomId);

	@NonNull
	UOMType getUOMTypeById(UomId uomId);

	ITranslatableString getUOMSymbolById(@NonNull UomId uomId);
}
