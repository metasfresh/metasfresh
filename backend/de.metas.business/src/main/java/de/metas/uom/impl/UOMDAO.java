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

package de.metas.uom.impl;

import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UOMType;
import de.metas.uom.UOMUtil;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class UOMDAO implements IUOMDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<X12DE355, Optional<UomId>> uomIdsByX12DE355 = CCache.<X12DE355, Optional<UomId>>builder()
			.tableName(I_C_UOM.Table_Name)
			.build();

	@Override
	public I_C_UOM getById(final int uomId)
	{
		Check.assumeGreaterThanZero(uomId, "uomId");
		return loadOutOfTrx(uomId, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public I_C_UOM getByIdOrNull(final int uomId)
	{
		if (uomId <= 0)
		{
			return null;
		}

		return loadOutOfTrx(uomId, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public I_C_UOM getById(@NonNull final UomId uomId)
	{
		return loadOutOfTrx(uomId, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public ITranslatableString getName(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return InterfaceWrapperHelper.getModelTranslationMap(uom).getColumnTrl(I_C_UOM.COLUMNNAME_Name, uom.getUOMSymbol());
	}

	@Override
	public List<I_C_UOM> getByIds(@NonNull final Collection<UomId> uomIds)
	{
		return loadByRepoIdAwaresOutOfTrx(uomIds, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public UomId getUomIdByX12DE355(@NonNull final X12DE355 x12de355)
	{
		return getUomIdByX12DE355IfExists(x12de355)
				.orElseThrow(() -> new AdempiereException("No UOM found for X12DE355=" + x12de355));
	}

	private Optional<UomId> getUomIdByX12DE355IfExists(@NonNull final X12DE355 x12de355)
	{
		return uomIdsByX12DE355.getOrLoad(x12de355, this::retrieveUomIdByX12DE355);
	}

	private Optional<UomId> retrieveUomIdByX12DE355(@NonNull final X12DE355 x12de355)
	{
		final UomId uomId = queryBL
				.createQueryBuilderOutOfTrx(I_C_UOM.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, x12de355.getCode())
				.orderByDescending(I_C_UOM.COLUMNNAME_AD_Client_ID)
				.orderByDescending(I_C_UOM.COLUMNNAME_IsDefault)
				.create()
				.firstId(UomId::ofRepoIdOrNull);
		return Optional.ofNullable(uomId);
	}

	@Override
	public X12DE355 getX12DE355ById(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return X12DE355.ofCode(uom.getX12DE355());
	}

	@Override
	public @NonNull I_C_UOM getByX12DE355(@NonNull final X12DE355 x12de355)
	{
		return getByX12DE355IfExists(x12de355)
				.orElseThrow(() -> new AdempiereException("No UOM found for X12DE355=" + x12de355));
	}

	@Override
	public Optional<I_C_UOM> getByX12DE355IfExists(@NonNull final X12DE355 x12de355)
	{
		return getUomIdByX12DE355IfExists(x12de355)
				.map(this::getById);
	}

	@Override
	public I_C_UOM getEachUOM()
	{
		return getByX12DE355(X12DE355.EACH);
	}

	@Override
	public TemporalUnit getTemporalUnitByUomId(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return UOMUtil.toTemporalUnit(uom);
	}

	@Override
	public I_C_UOM getByTemporalUnit(@NonNull final TemporalUnit temporalUnit)
	{
		final UomId uomId = getUomIdByTemporalUnit(temporalUnit);
		return getById(uomId);
	}

	@Override
	public UomId getUomIdByTemporalUnit(@NonNull final TemporalUnit temporalUnit)
	{
		final X12DE355 x12de355 = X12DE355.ofTemporalUnit(temporalUnit);
		try
		{
			return getUomIdByX12DE355(x12de355);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("temporalUnit", temporalUnit)
					.setParameter("x12de355", x12de355)
					.setParameter("Suggestion", "Create an UOM for that X12DE355 code or activate it if already exists.")
					.appendParametersToMessage();
		}
	}

	@Override
	public UOMPrecision getStandardPrecision(final UomId uomId)
	{
		if (uomId == null)
		{
			// NOTE: if there is no UOM specified, we assume UOM is Each => precision=0
			return UOMPrecision.ZERO;
		}

		final I_C_UOM uom = getById(uomId);
		return UOMPrecision.ofInt(uom.getStdPrecision());
	}

	@Override
	public boolean isUOMForTUs(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return isUOMForTUs(uom);
	}

	public static boolean isUOMForTUs(@NonNull final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(uom.getX12DE355());
		return X12DE355.COLI.equals(x12de355) || X12DE355.TU.equals(x12de355);
	}

	@Override
	public boolean isUOMEach(@NonNull final UomId uomId)
	{
		final X12DE355 x12de355 = getX12DE355ById(uomId);
		return X12DE355.EACH.equals(x12de355);
	}

	@Override
	public @NonNull UOMType getUOMTypeById(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return UOMType.ofNullableCodeOrOther(uom.getUOMType());
	}

	@Override
	public @NonNull Optional<I_C_UOM> getBySymbol(@NonNull final String uomSymbol)
	{
		return Optional.ofNullable(queryBL.createQueryBuilder(I_C_UOM.class)
										   .addOnlyActiveRecordsFilter()
										   .addEqualsFilter(I_C_UOM.COLUMNNAME_UOMSymbol, uomSymbol)
										   .create()
										   .firstOnlyOrNull(I_C_UOM.class));
	}

	@Override
	public ITranslatableString getUOMSymbolById(@NonNull final UomId uomId)
	{
		final I_C_UOM uom = getById(uomId);
		return InterfaceWrapperHelper.getModelTranslationMap(uom).getColumnTrl(I_C_UOM.COLUMNNAME_UOMSymbol, uom.getUOMSymbol());
	}
}
