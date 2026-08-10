package de.metas.mforecast;

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

import com.google.common.collect.ImmutableSet;
import de.metas.mforecast.ForecastRequest.ForecastLineRequest;
import de.metas.mforecast.impl.ForecastId;
import de.metas.mforecast.impl.ForecastQuery;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface IForecastDAO extends ISingletonService
{
	@NonNull
	List<I_M_ForecastLine> retrieveLinesByForecastId(@NonNull ForecastId forecastId);

	I_M_ForecastLine getForecastLineById(int forecastLineRecordId);

	@NonNull
	ForecastId createForecast(@NonNull ForecastRequest request);

	@NonNull
	Stream<I_M_Forecast> streamRecordsByIds(@NonNull ImmutableSet<ForecastId> ids);

	void addForecastLine(@NonNull ForecastId forecastId, @NonNull ForecastLineRequest request);

	I_M_Forecast getById(@NonNull ForecastId forecastId);

	void save(@NonNull I_M_Forecast forecastRecord);

	List<ForecastId> listIdsByQuery(@NonNull final ForecastQuery forecastQuery);

	/**
	 * Sums, per matching forecast document, the quantity of that document's forecast lines that match the given query
	 * (product / ASI / warehouse / org, honouring {@code onlyNonZeroQty}).
	 * <p>
	 * Each line's {@code Qty} is converted to the query product's stock UOM before summing, so a document whose matching
	 * lines are stored in different UOMs still yields a correct, single-UOM total.
	 *
	 * @return one entry per forecast document that has at least one matching line; the {@link Quantity} value is expressed
	 * in the product's stock UOM. Documents without a matching line are absent from the map.
	 */
	@NonNull
	Map<ForecastId, Quantity> sumQtyByForecastId(@NonNull ForecastQuery forecastQuery);
}
