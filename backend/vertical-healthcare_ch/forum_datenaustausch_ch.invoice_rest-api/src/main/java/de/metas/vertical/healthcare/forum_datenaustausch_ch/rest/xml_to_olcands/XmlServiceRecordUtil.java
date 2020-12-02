/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
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

package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands;

import com.google.common.collect.ImmutableList;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.util.Check;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDRGType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordDrugType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordLabType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordMigelType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordOtherType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordParamedType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request.RecordTarmedType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.assumeNotEmpty;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * By ServiceRecord we need the XML nodes named {@code <service>}, that correspond to invoice-lines in metasfresh
 */
@UtilityClass
public class XmlServiceRecordUtil
{
	private static final String CURRENCY_CODE = "CHF";

	private static final String UOM_CODE = "MJ";  // minute; HUR would be hour

	public ImmutableList<JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder> insertServiceRecordIntoBuilder(
			@NonNull final JsonOLCandCreateRequest preliminaryRequest,
			@NonNull final Object record,
			@NonNull final XmlToOLCandsService.HighLevelContext context)
	{
		final ImmutableList.Builder<JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder> result = ImmutableList.builder();

		final String externalLineId;
		final JsonProductInfo product;
		final BigDecimal price;
		final BigDecimal quantity;
		if (record instanceof RecordTarmedType)
		{
			final RecordTarmedType recordTarmedType = (RecordTarmedType)record;

			externalLineId = createExternalId(preliminaryRequest, recordTarmedType.getRecordId());
			product = createProduct(recordTarmedType.getCode(), recordTarmedType.getName(), context);
			price = recordTarmedType.getAmount();
			quantity = ONE;
		}
		else if (record instanceof RecordDRGType)
		{
			final RecordDRGType recordDRGType = (RecordDRGType)record;

			externalLineId = createExternalId(preliminaryRequest, recordDRGType.getRecordId());
			product = createProduct(recordDRGType.getCode(), recordDRGType.getName(), context);
			price = createPrice(recordDRGType.getUnit(), recordDRGType.getUnitFactor(), recordDRGType.getExternalFactor());
			quantity = recordDRGType.getQuantity();
		}
		else if (record instanceof RecordLabType)
		{
			final RecordLabType recordLabType = (RecordLabType)record;

			externalLineId = createExternalId(preliminaryRequest, recordLabType.getRecordId());
			product = createProduct(recordLabType.getCode(), recordLabType.getName(), context);
			price = createPrice(recordLabType.getUnit(), recordLabType.getUnitFactor(), recordLabType.getExternalFactor());
			quantity = recordLabType.getQuantity();
		}
		else if (record instanceof RecordMigelType)
		{
			final RecordMigelType recordMigelType = (RecordMigelType)record;

			externalLineId = createExternalId(preliminaryRequest, recordMigelType.getRecordId());
			product = createProduct(recordMigelType.getCode(), recordMigelType.getName(), context);
			price = createPrice(recordMigelType.getUnit(), recordMigelType.getUnitFactor(), recordMigelType.getExternalFactor());
			quantity = recordMigelType.getQuantity();
		}
		else if (record instanceof RecordParamedType)
		{
			final RecordParamedType recordParamedOtherType = (RecordParamedType)record;

			externalLineId = createExternalId(preliminaryRequest, recordParamedOtherType.getRecordId());
			product = createProduct(recordParamedOtherType.getCode(), recordParamedOtherType.getName(), context);
			price = createPrice(recordParamedOtherType.getUnit(), recordParamedOtherType.getUnitFactor(), recordParamedOtherType.getExternalFactor());
			quantity = recordParamedOtherType.getQuantity();
		}
		else if (record instanceof RecordDrugType)
		{
			final RecordDrugType recordDrugType = (RecordDrugType)record;

			externalLineId = createExternalId(preliminaryRequest, recordDrugType.getRecordId());
			product = createProduct(recordDrugType.getCode(), recordDrugType.getName(), context);
			price = createPrice(recordDrugType.getUnit(), recordDrugType.getUnitFactor(), recordDrugType.getExternalFactor());
			quantity = recordDrugType.getQuantity();
		}
		else if (record instanceof RecordOtherType)
		{
			final RecordOtherType recordOtherType = (RecordOtherType)record;

			externalLineId = createExternalId(preliminaryRequest, recordOtherType.getRecordId());
			product = createProduct(recordOtherType.getCode(), recordOtherType.getName(), context);
			price = createPrice(recordOtherType);
			quantity = recordOtherType.getQuantity();

		}
		else
		{
			Check.fail("Unexpected record type={}", record);
			return null;
		}
		final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder serviceRecordBuilder = preliminaryRequest.toBuilder()
				.externalLineId(externalLineId)
				.product(product)
				.price(price)
				.currencyCode(CURRENCY_CODE)
				// the UOM shall be taken from the product-masterdata, because we don't really know it from the XML file
				// .uomCode(UOM_CODE)
				.discount(ZERO)
				.qty(quantity);

		result.add(serviceRecordBuilder);

		return result.build();
	}

	private String createExternalId(
			@NonNull final JsonOLCandCreateRequest request,
			final int recordId)
	{
		final String externalHeaderId = assumeNotEmpty(request.getExternalHeaderId(), "request.externalHeaderId may not be empty; request={}", request);
		return ""
				+ externalHeaderId
				+ "_"
				+ recordId;
	}

	private JsonProductInfo createProduct(
			@NonNull final String productCode,
			@NonNull final String productName,
			@NonNull final XmlToOLCandsService.HighLevelContext context)
	{
		return JsonProductInfo.builder()
				.syncAdvise(context.getProductsSyncAdvise())
				.code(productCode)
				.name(productName)
				.type(JsonProductInfo.Type.SERVICE)
				.uomCode(UOM_CODE)
				.build();
	}

	private BigDecimal createPrice(@NonNull final RecordOtherType recordOtherType)
	{
		return createPrice(
				recordOtherType.getUnit(),
				recordOtherType.getUnitFactor(),
				recordOtherType.getExternalFactor());
	}

	private BigDecimal createPrice(
			@Nullable final BigDecimal unit,
			@Nullable final BigDecimal unitFactor,
			@Nullable final BigDecimal externalFactor)
	{
		final BigDecimal unitToUse = coalesce(unit, ONE); // tax point (TP) of the applied service
		final BigDecimal unitFactorToUse = coalesce(unitFactor, ONE); // tax point value (TPV) of the applied service
		final BigDecimal externalFactorToUse = coalesce(externalFactor, ONE);

		return unitToUse.multiply(unitFactorToUse).multiply(externalFactorToUse);
	}
}
