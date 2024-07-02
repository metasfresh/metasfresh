/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.rest_api.v2.uomconversion;

import de.metas.common.product.v2.request.JsonRequestUOMConversionUpsert;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UomId;
import de.metas.uom.UpdateUOMConversionRequest;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UomConversionRestService
{
	private static final Logger logger = LogManager.getLogger(UomConversionRestService.class);

	private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public void createOrUpdateUOMConversions(
			@Nullable final List<JsonRequestUOMConversionUpsert> uomConversions,
			@NonNull final ProductId productId,
			@NonNull final SyncAdvise syncAdvise)
	{
		final UOMConversionsMap uomConversionsMap = uomConversionDAO.getProductConversions(productId);
		if (uomConversions != null)
		{
			uomConversions.forEach(uomConversion ->
										   createOrUpdateUOMConversion(uomConversion,
																	   productId,
																	   syncAdvise,
																	   uomConversionsMap));
		}
	}

	private void createOrUpdateUOMConversion(
			@NonNull final JsonRequestUOMConversionUpsert jsonRequestUOMConversionUpsert,
			@NonNull final ProductId productId,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final UOMConversionsMap uomConversionsMap)
	{
		validateJsonRequestUOMConversionUpsert(jsonRequestUOMConversionUpsert);
		final UomId fromUomId = getUomId(X12DE355.ofCode(jsonRequestUOMConversionUpsert.getFromUomCode()));
		final UomId toUomId = getUomId(X12DE355.ofCode(jsonRequestUOMConversionUpsert.getToUomCode()));

		final Optional<UOMConversionRate> existingUomConversionRate = uomConversionsMap.getRateIfExists(fromUomId, toUomId);

		if (existingUomConversionRate.isPresent())
		{
			if (syncAdvise.getIfExists().isUpdate())
			{
				final UpdateUOMConversionRequest updateUOMConversionRequest = syncUpdateUOMConversionRequestWithJson(productId,
																													 fromUomId,
																													 toUomId,
																													 jsonRequestUOMConversionUpsert,
																													 existingUomConversionRate.get());

				uomConversionDAO.updateUOMConversion(updateUOMConversionRequest);
			}
		}
		else if (syncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder()
					.resourceName("C_UOM_Conversion")
					.detail(TranslatableStrings.constant("{ M_Product_ID:" + productId.getRepoId() + ", C_UOM_ID: " + fromUomId + ", C_UOM_To_ID: " + toUomId))
					.build()
					.setParameter("effectiveSyncAdvise", syncAdvise);
		}
		else
		{
			final BigDecimal fromToMultiplier = Optional.ofNullable(jsonRequestUOMConversionUpsert.getFromToMultiplier())
					.orElseThrow(() -> new MissingPropertyException("fromToMultiplier", jsonRequestUOMConversionUpsert));

			final CreateUOMConversionRequest createUOMConversionRequest = CreateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(fromUomId)
					.toUomId(toUomId)
					.fromToMultiplier(fromToMultiplier)
					.catchUOMForProduct(BooleanUtils.isTrue(jsonRequestUOMConversionUpsert.getCatchUOMForProduct()))
					.build();

			uomConversionDAO.createUOMConversion(createUOMConversionRequest);
		}
	}

	@NonNull
	private UomId getUomId(@NonNull final X12DE355 x12de355)
	{
		return uomDAO.getIdByX12DE355IfExists(x12de355)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("uomCode")
						.resourceIdentifier(x12de355.getCode())
						.build());
	}

	@NonNull
	private UpdateUOMConversionRequest syncUpdateUOMConversionRequestWithJson(
			@NonNull final ProductId productId,
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId,
			@NonNull final JsonRequestUOMConversionUpsert jsonRequestUOMConversionUpsert,
			@NonNull final UOMConversionRate existingUomConversionRate)
	{
		final UpdateUOMConversionRequest.UpdateUOMConversionRequestBuilder updateUOMConversionRequestBuilder = UpdateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(fromUomId)
				.toUomId(toUomId);

		if (jsonRequestUOMConversionUpsert.isFromToMultiplierSet())
		{
			if (jsonRequestUOMConversionUpsert.getFromToMultiplier() == null)
			{
				logger.debug("Ignoring property \"fromToMultiplier\" : null ");
				updateUOMConversionRequestBuilder.fromToMultiplier(existingUomConversionRate.getFromToMultiplier());
			}
			else
			{
				updateUOMConversionRequestBuilder.fromToMultiplier(jsonRequestUOMConversionUpsert.getFromToMultiplier());
			}
		}
		else
		{
			updateUOMConversionRequestBuilder.fromToMultiplier(existingUomConversionRate.getFromToMultiplier());
		}

		if (jsonRequestUOMConversionUpsert.isCatchUOMForProductSet())
		{
			if (jsonRequestUOMConversionUpsert.getCatchUOMForProduct() == null)
			{
				logger.debug("Ignoring property \"catchUOMForProduct\" : null ");
			}
			else
			{
				updateUOMConversionRequestBuilder.catchUOMForProduct(jsonRequestUOMConversionUpsert.getCatchUOMForProduct());
			}
		}
		else
		{
			updateUOMConversionRequestBuilder.catchUOMForProduct(existingUomConversionRate.isCatchUOMForProduct());
		}

		return updateUOMConversionRequestBuilder.build();
	}

	private static void validateJsonRequestUOMConversionUpsert(@NonNull final JsonRequestUOMConversionUpsert jsonRequestUOMConversionUpsert)
	{
		if (jsonRequestUOMConversionUpsert.getFromUomCode() == null)
		{
			throw new MissingPropertyException("fromUomCode", jsonRequestUOMConversionUpsert);
		}

		if (jsonRequestUOMConversionUpsert.getToUomCode() == null)
		{
			throw new MissingPropertyException("toUomCode", jsonRequestUOMConversionUpsert);
		}
	}
}
