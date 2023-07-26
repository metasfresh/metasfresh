/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.WarehouseAndLocatorValue;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/hu" })
@RestController
@Profile(Profiles.PROFILE_App)
public class HandlingUnitsRestController
{
	final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	final IProductBL productBL = Services.get(IProductBL.class);

	@GetMapping("/bySerialNo/{serialNo}")
	public ResponseEntity<JsonGetSingleHUResponse> getBySerialNo(
			@PathVariable("serialNo") @NonNull final String serialNo)
	{
		try
		{
			final I_M_HU hu = retrieveActiveHUIdBySerialNo(serialNo);

			return ResponseEntity.ok(JsonGetSingleHUResponse.builder()
					.result(toJson(hu))
					.build());
		}
		catch (final Exception ex)
		{
			return ResponseEntity.badRequest().body(JsonGetSingleHUResponse.builder()
					.error(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()))
					.build());
		}
	}

	private I_M_HU retrieveActiveHUIdBySerialNo(final @NonNull String serialNo)
	{
		final List<I_M_HU> hus = handlingUnitsDAO
				.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.addOnlyWithAttribute(AttributeConstants.ATTR_SerialNo, serialNo)
				.list();
		if (hus.isEmpty())
		{
			throw new AdempiereException("No active HU found for serialNo=`" + serialNo + "`");
		}
		else if (hus.size() == 1)
		{
			return hus.get(0);
		}
		else
		{
			throw new AdempiereException("More than one active HUs found for serialNo=`" + serialNo + "`:" + hus);
		}
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<JsonGetSingleHUResponse> getById(
			@PathVariable("id") final int huRepoId)
	{
		try
		{
			final I_M_HU hu = handlingUnitsBL.getById(HuId.ofRepoId(huRepoId));
			if (hu == null)
			{
				throw new AdempiereException("No HU found for ID=" + huRepoId);
			}

			return ResponseEntity.ok(JsonGetSingleHUResponse.builder()
					.result(toJson(hu))
					.build());
		}
		catch (final Exception ex)
		{
			return ResponseEntity.badRequest().body(JsonGetSingleHUResponse.builder()
					.error(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()))
					.build());
		}
	}

	private JsonHU toJson(final I_M_HU hu)
	{
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();

		final ImmutableList<JsonHUProduct> jsonHUProducts = huContext.getHUStorageFactory()
				.getStorage(hu)
				.streamProductStorages()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		final ImmutableAttributeSet huAttributes = huContext.getHUAttributeStorageFactory()
				.getImmutableAttributeSet(hu);
		final JsonHUAttributes jsonHUAttributes = toJson(huAttributes);

		for (final ExtractCounterAttributesCommand.CounterAttribute counterAttribute : extractCounterAttributes(huAttributes))
		{
			jsonHUAttributes.putAttribute(counterAttribute.getAttributeCode(), counterAttribute.getCounter());
		}

		final WarehouseAndLocatorValue warehouseAndLocatorValue = hu.getM_Locator_ID() > 0
				? warehouseDAO.retrieveWarehouseAndLocatorValueByLocatorRepoId(hu.getM_Locator_ID())
				: null;

		return JsonHU.builder()
				.id(String.valueOf(hu.getM_HU_ID()))
				.huStatus(hu.getHUStatus())
				.warehouseValue(warehouseAndLocatorValue != null ? warehouseAndLocatorValue.getWarehouseValue() : null)
				.locatorValue(warehouseAndLocatorValue != null ? warehouseAndLocatorValue.getLocatorValue() : null)
				.products(jsonHUProducts)
				.attributes(jsonHUAttributes)
				.build();
	}

	private List<ExtractCounterAttributesCommand.CounterAttribute> extractCounterAttributes(@NonNull final ImmutableAttributeSet huAttributes)
	{
		return new ExtractCounterAttributesCommand(huAttributes).execute();
	}

	private JsonHUProduct toJson(@NonNull final IHUProductStorage productStorage)
	{
		final I_M_Product product = productBL.getById(productStorage.getProductId());
		final Quantity qty = productStorage.getQty();

		return JsonHUProduct.builder()
				.productValue(product.getValue())
				.productName(product.getName())
				.qty(qty.toBigDecimal().toString())
				.uom(qty.getX12DE355().getCode())
				.build();
	}

	private JsonHUAttributes toJson(final ImmutableAttributeSet huAttributes)
	{
		final JsonHUAttributes json = new JsonHUAttributes();
		for (final AttributeCode attributeCode : huAttributes.getAttributeCodes())
		{
			final Object value = huAttributes.getValue(attributeCode);
			json.putAttribute(attributeCode.getCode(), value);
		}

		return json;
	}
}
