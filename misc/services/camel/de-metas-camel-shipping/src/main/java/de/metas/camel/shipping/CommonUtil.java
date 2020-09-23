/*
 * #%L
 * de-metas-camel-shipping
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

package de.metas.camel.shipping;

import de.metas.common.filemaker.DATABASE;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.FMPXMLRESULT.FMPXMLRESULTBuilder;
import de.metas.common.filemaker.PRODUCT;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.shipping.JsonProduct;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.PropertiesComponent;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

@UtilityClass
public class CommonUtil
{
	private final static String PRODUCT_AND_ORG_PREFIX_PATTERN = "([^-]*-)?(.*)";

	public FMPXMLRESULTBuilder createFmpxmlresultBuilder(
			final String databaseName,
			final int numberOfItems)
	{
		return FMPXMLRESULT.builder()
				.errorCode("0")
				.product(new PRODUCT())
				.database(DATABASE.builder()
						.name(databaseName)
						.records(Integer.toString(numberOfItems))
						.build());
	}

	@NonNull
	public String extractWarehouseValue(
			@NonNull final PropertiesComponent propertiesComponent,
			@NonNull final String _siro_kunden_id)
	{
		final var propertyKey = "_siro_kunden_id." + _siro_kunden_id + ".warehouseValue";
		return propertiesComponent
				.resolveProperty(propertyKey)
				.orElseThrow(() -> new RuntimeCamelException("Unexpected _siro_kunden_id=" + _siro_kunden_id + "; fix it in metasfresh or add '" + propertyKey + "' to the properties file"));
	}

	@NonNull
	public String extractProductNo(
			@NonNull final PropertiesComponent propertiesComponent,
			@NonNull final JsonProduct product,
			@NonNull final String orgCode)
	{
		final var artikelNummerPrefix = CommonUtil.extractOrgPrefix(propertiesComponent, orgCode);
		return artikelNummerPrefix + product.getProductNo();
	}

	@NonNull
	private String extractOrgPrefix(
			@NonNull final PropertiesComponent propertiesComponent,
			@NonNull final String orgCode)
	{
		final var propertyKey = "_artikel_nummer.orgValue." + orgCode + ".prefix";

		final var propertyValue = propertiesComponent
				.resolveProperty(propertyKey)
				.orElseThrow(() -> new RuntimeCamelException("Unexpected orgCode=" + orgCode + "; fix it in metasfresh or add '" + propertyKey + "' to the properties file"));

		return propertyValue + "-";
	}

	@Nullable
	public String removeOrgPrefix(@Nullable final String productValueWithOrgCode)
	{
		if (productValueWithOrgCode == null || productValueWithOrgCode.isBlank())
		{
			return productValueWithOrgCode;
		}

		final var prefix = Pattern.compile(PRODUCT_AND_ORG_PREFIX_PATTERN);
		final var matcher = prefix.matcher(productValueWithOrgCode);
		if (!matcher.matches())
		{
			return productValueWithOrgCode;
		}
		return matcher.group(2);
	}

	@Nullable
	public String extractOrgPrefixFromProduct(@Nullable final String productValueWithOrgCode)
	{
		if (productValueWithOrgCode == null || productValueWithOrgCode.isBlank())
		{
			return null;
		}

		final var prefix = Pattern.compile(PRODUCT_AND_ORG_PREFIX_PATTERN);
		final var matcher = prefix.matcher(productValueWithOrgCode);
		if (!matcher.matches())
		{
			return null;
		}
		return matcher.group(1).replaceAll("-", "");
	}

	public JsonError createJsonError(@NonNull final Throwable throwable)
	{
		final StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		final String stackTrace = sw.toString();

		return JsonError.ofSingleItem(
				JsonErrorItem.builder()
						.message(throwable.getMessage())
						.detail("Exception-Class=" + throwable.getClass().getName())
						.stackTrace(stackTrace)
						.build());
	}

}
