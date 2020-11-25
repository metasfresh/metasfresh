/*
 * #%L
 * de-metas-edi-esb-camel
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

package de.metas.edi.esb.commons;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.CamelContext;

import de.metas.edi.esb.invoicexport.stepcom.qualifier.DocumentType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InvoicSettings
{
	private static final String ANY_MEASUREMENTUNIT = "<ANY>";

	public enum InvoicLineQuantityInUOM
	{
		InvoicedUOM,
		OrderedUOM
	}

	public static InvoicSettings forReceiverGLN(
			@NonNull final CamelContext context,
			@NonNull final String recipientGLN)
	{
		final String clearingCenterProperty = "edi.recipientGLN." + recipientGLN + ".clearingCenter";
		final ClearingCenter clearingCenter = ClearingCenter.valueOf(Util.resolveProperty(context, clearingCenterProperty, "ecosio"));

		final InvoicSettingsBuilder settings = InvoicSettings
				.builder()
				.clearingCenter(clearingCenter);

		switch (clearingCenter)
		{
			case ecosio:
				return settings.build();
			case STEPcom:
				return settings
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

						.invoicDocumentTypeCMIVAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CMIV.alias", "CMIV")))
						.invoicDocumentTypeCRNOAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CRNO.alias", "CRNO")))
						.invoicDocumentTypeDBNOAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.DBNO.alias", "DBNO")))
						.invoicDocumentTypeCRNFAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CRNF.alias", "CRNF")))
						.invoicDocumentTypeDBNFAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.DBNF.alias", "DBNF")))
						.invoicDocumentTypeCSIVAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CSIV.alias", "CSIV")))
						.invoicDocumentTypeCSCNAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CSCN.alias", "CSCN")))
						.invoicDocumentTypePFIVAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.PFIV.alias", "PFIV")))
						.invoicDocumentTypeCRIVAlias(DocumentType.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.DocumentType.CRIV.alias", "CRIV")))

						.invoicLineBUYRRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.BUYR.required", "false"))
						.invoicLineGTINRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.GTIN.required", "false"))
						.invoicLineEANCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.EANC.required", "false"))
						.invoicLineEANTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.EANT.required", "false"))
						.invoicLineUPCCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.UPCC.required", "false"))
						.invoicLineUPCTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.UPCT.required", "false"))

						.invoicLineQuantityInUOM(InvoicLineQuantityInUOM.valueOf(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.QuantityInUOM", "InvoicedUOM")))
						.invoicLineRequiredMEASUREMENTUNIT(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".invoic.line.MEASUREMENTUNIT.required", ANY_MEASUREMENTUNIT))
						.build();
			case CompuData:
				return settings.build();
			default:
				throw new RuntimeException("Unsupporded clearing center property " + clearingCenterProperty);
		}

	}

	ClearingCenter clearingCenter;

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

	DocumentType invoicDocumentTypeCMIVAlias;
	DocumentType invoicDocumentTypeCRNOAlias;
	DocumentType invoicDocumentTypeDBNOAlias;
	DocumentType invoicDocumentTypeCRNFAlias;
	DocumentType invoicDocumentTypeDBNFAlias;
	DocumentType invoicDocumentTypeCSIVAlias;
	DocumentType invoicDocumentTypeCSCNAlias;
	DocumentType invoicDocumentTypePFIVAlias;
	DocumentType invoicDocumentTypeCRIVAlias;

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
