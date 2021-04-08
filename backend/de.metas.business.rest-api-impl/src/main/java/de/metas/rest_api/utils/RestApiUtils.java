package de.metas.rest_api.utils;

import de.metas.common.rest_api.v1.JsonAttributeInstance;
import de.metas.common.rest_api.v1.JsonAttributeSetInstance;
import de.metas.common.rest_api.v1.JsonQuantity;
import de.metas.i18n.ILanguageDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.order.JsonPurchaseCandidateCreateItem;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_M_Attribute;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public class RestApiUtils
{
	public String getAdLanguage()
	{
		final ILanguageDAO languagesRepo = Services.get(ILanguageDAO.class);

		final HttpServletRequest httpServletRequest = getHttpServletRequest();
		if (httpServletRequest != null)
		{
			final String httpAcceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
			if (!Check.isEmpty(httpAcceptLanguage, true))
			{
				final String requestLanguage = languagesRepo.retrieveAvailableLanguages()
						.getAD_LanguageFromHttpAcceptLanguage(httpAcceptLanguage, null);
				if (requestLanguage != null)
				{
					return requestLanguage;
				}
			}
		}

		// Fallback to base language
		return languagesRepo.retrieveBaseLanguage();
	}

	@Nullable
	public HttpServletRequest getHttpServletRequest()
	{
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null)
		{
			return null;
		}
		if (!(requestAttributes instanceof ServletRequestAttributes))
		{
			return null;
		}

		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}

	public JsonAttributeSetInstance extractJsonAttributeSetInstance(
			@NonNull final ImmutableAttributeSet attributeSet,
			@NonNull final OrgId orgId)
	{
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		final JsonAttributeSetInstance.JsonAttributeSetInstanceBuilder jsonAttributeSetInstance = JsonAttributeSetInstance.builder();
		for (final AttributeId attributeId : attributeSet.getAttributeIds())
		{
			final AttributeCode attributeCode = attributeSet.getAttributeCode(attributeId);

			final JsonAttributeInstance.JsonAttributeInstanceBuilder instanceBuilder = JsonAttributeInstance.builder()
					.attributeName(attributeSet.getAttributeName(attributeId))
					.attributeCode(attributeCode.getCode());
			final String attributeValueType = attributeSet.getAttributeValueType(attributeId);
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
			{
				final Date valueAsDate = attributeSet.getValueAsDate(attributeCode);
				if (valueAsDate != null)
				{
					final ZoneId timeZone = orgDAO.getTimeZone(orgId);
					final LocalDate localDate = valueAsDate.toInstant().atZone(timeZone).toLocalDate();
					instanceBuilder.valueDate(localDate);
				}
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
			{
				instanceBuilder.valueStr(attributeSet.getValueAsString(attributeCode));
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
			{
				instanceBuilder.valueStr(attributeSet.getValueAsString(attributeCode));
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
			{
				instanceBuilder.valueNumber(attributeSet.getValueAsBigDecimal(attributeCode));
			}

			jsonAttributeSetInstance.attributeInstance(instanceBuilder.build());
		}
		return jsonAttributeSetInstance.build();
	}

	@NonNull
	public Quantity getQuantity(final JsonPurchaseCandidateCreateItem request)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final JsonQuantity jsonQuantity = request.getQty();
		final String uomCode = jsonQuantity.getUomCode();
		final Optional<I_C_UOM> uom = uomDAO.getByX12DE355IfExists(X12DE355.ofCode(uomCode));
		if (!uom.isPresent())
		{
			throw MissingResourceException.builder().resourceIdentifier("quantity.uomCode").resourceIdentifier(uomCode).build();
		}
		return Quantity.of(jsonQuantity.getQty(), uom.get());
	}
}
