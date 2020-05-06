package de.metas.edi.esb.bean.invoic;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.CamelContext;

import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.pojo.common.MeasurementUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-edi
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

@Value
@Builder
public class StepComInvoicSettings
{
	private static final String ANY_MEASUREMENTUNIT = "<ANY>";

	public enum InvoicLineQuantityInUOM
	{
		InvoicedUOM,

		OrderedUOM;
	}

	public static StepComInvoicSettings forReceiverGLN(
			@NonNull final CamelContext context,
			@NonNull final String recipientGLN)
	{
		return StepComInvoicSettings
				.builder()
				.applicationRef(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.applicationRef", "INVOIC"))
				.partnerId(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.partnerId"))
				.fileNamePrefix(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.fileNamePrefix"))
				.testIndicator(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.testIndicator", "T"))

				.invoicORSE(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.ORSE", "true"))
				.invoicBUYRAddressName1Required(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.BUYR.AddressName1.required", "true"))
				.invoicBUYRGLNRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.BUYR.GLN.required", "true"))
				.invoicBUYRStreet1Required(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.BUYR.Street1.required", "false"))
				.invoicBUYRCityRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.BUYR.City.required", "false"))
				.invoicBUYRPostaCodeRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.BUYR.PostalCode.required", "false"))

				.invoicIVCEAddressName1Required(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.IVCE.AddressName1.required", "true"))
				.invoicIVCEGLNRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.IVCE.GLN.required", "true"))
				.invoicIVCEStreet1Required(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.IVCE.Street1.required", "false"))
				.invoicIVCECityRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.IVCE.City.required", "false"))
				.invoicIVCEPostaCodeRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.IVCE.PostalCode.required", "false"))

				.invoicLineBUYRRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.BUYR.required", "false"))
				.invoicLineGTINRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.GTIN.required", "false"))
				.invoicLineEANCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.EANC.required", "false"))
				.invoicLineEANTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.EANT.required", "false"))
				.invoicLineUPCCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.UPCC.required", "false"))
				.invoicLineUPCTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.UPCT.required", "false"))

				.invoicLineQuantityInUOM(InvoicLineQuantityInUOM.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.QuantityInUOM", "InvoicedUOM")))
				.invoicLineRequiredMEASUREMENTUNIT(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.MEASUREMENTUNIT.required", ANY_MEASUREMENTUNIT))
				.build();
	}

	String applicationRef;

	String partnerId;

	String fileNamePrefix;

	String testIndicator;

	boolean invoicORSE;

	boolean invoicBUYRAddressName1Required;
	boolean invoicBUYRGLNRequired;
	boolean invoicBUYRStreet1Required;
	boolean invoicBUYRCityRequired;
	boolean invoicBUYRPostaCodeRequired;

	boolean invoicIVCEAddressName1Required;
	boolean invoicIVCEGLNRequired;
	boolean invoicIVCEStreet1Required;
	boolean invoicIVCECityRequired;
	boolean invoicIVCEPostaCodeRequired;

	boolean invoicLineBUYRRequired;

	boolean invoicLineGTINRequired;

	boolean invoicLineEANCRequired;
	boolean invoicLineEANTRequired;

	boolean invoicLineUPCCRequired;
	boolean invoicLineUPCTRequired;

	InvoicLineQuantityInUOM invoicLineQuantityInUOM;

	String invoicLineRequiredMEASUREMENTUNIT;

	public boolean isInvoicLineMEASUREMENTUNITRequired()
	{
		return !Util.isEmpty(invoicLineRequiredMEASUREMENTUNIT);
	}

	public boolean isMeasurementUnitAllowed(@NonNull final MeasurementUnit measurementUnit)
	{
		final List<String> allowedMeasurementUnits = Arrays.asList(invoicLineRequiredMEASUREMENTUNIT.split("\\s*,\\s*"));

		return Util.isEmpty(invoicLineRequiredMEASUREMENTUNIT)
				|| ANY_MEASUREMENTUNIT.equals(invoicLineRequiredMEASUREMENTUNIT)
				|| allowedMeasurementUnits.contains(measurementUnit.name());
	}
}
