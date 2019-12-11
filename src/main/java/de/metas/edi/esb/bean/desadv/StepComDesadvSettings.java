package de.metas.edi.esb.bean.desadv;

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
public class StepComDesadvSettings
{
	private static final String ANY_MEASUREMENTUNIT = "<ANY>";

	public static StepComDesadvSettings forReceiverGLN(
			@NonNull final CamelContext context,
			@NonNull final String recipientGLN)
	{
		return StepComDesadvSettings
				.builder()
				.applicationRef(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.applicationRef", "DESADV"))
				.partnerId(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.partnerId"))
				.testIndicator(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.testIndicator", "T"))
				.desadvHeaderORIGReference(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.header.ORIG.reference", ""))

				.desadvLinePackagingCodeLURequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.packagingCodeLU.required", "true"))
				.desadvLinePackagingCodeTURequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.packagingCodeTU.required", "false"))

				.desadvLineSSCCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.SSCC.required", "false"))
				.desadvLineCUTURequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.CUTU.required", "true"))

				.desadvLineBUYRRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.BUYR.required", "true"))
				.desadvLineGTINRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.GTIN.required", "false"))
				.desadvLineEANCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.EANC.required", "false"))
				.desadvLineEANTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.EANT.required", "false"))
				.desadvLineUPCCRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.UPCC.required", "false"))
				.desadvLineUPCTRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.UPCT.required", "false"))

				.desadvLineORBUOrderReference(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.ORBU.orderReference", "false"))
				.desadvLineORBUOrderLineReference(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.ORBU.orderLineReference", "true"))
				.desadvLineLIRN(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.LIRN", "true"))
				.desadvLinePRICRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.PRIC.required", "false"))
				.desadvLineDMARK1BestBeforeDateRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.DMARK1.bestBeforeDate.required", "false"))
				.desadvLineDMARK1BestBeforeDateAsBatchNoRequired(Util.resolvePropertyAsBool(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.DMARK1.bestBeforeDateAsBatchNo.required", "false"))

				.desadvLineRequiredMEASUREMENTUNIT(Util.resolveProperty(context, "edi.stepcom.recipientGLN." + recipientGLN + ".desadv.line.MEASUREMENTUNIT.required", ANY_MEASUREMENTUNIT))
				.build();
	}

	String applicationRef;

	String partnerId;

	String testIndicator;

	String desadvHeaderORIGReference;

	boolean desadvLinePackagingCodeLURequired;

	boolean desadvLinePackagingCodeTURequired;

	boolean desadvLineSSCCRequired;

	boolean desadvLineBUYRRequired;

	boolean desadvLineGTINRequired;

	boolean desadvLineEANCRequired;
	boolean desadvLineEANTRequired;

	boolean desadvLineUPCCRequired;
	boolean desadvLineUPCTRequired;

	boolean desadvLineCUTURequired;

	boolean desadvLineORBUOrderReference;

	boolean desadvLineORBUOrderLineReference;

	boolean desadvLineLIRN;

	boolean desadvLinePRICRequired;

	boolean desadvLineDMARK1BestBeforeDateRequired;

	boolean desadvLineDMARK1BestBeforeDateAsBatchNoRequired;

	String desadvLineRequiredMEASUREMENTUNIT;

	public boolean isDesadvLineMEASUREMENTUNITRequired()
	{
		return !Util.isEmpty(desadvLineRequiredMEASUREMENTUNIT);
	}

	public boolean isMeasurementUnitAllowed(@NonNull final MeasurementUnit measurementUnit)
	{
		final List<String> allowedMeasurementUnits = Arrays.asList(desadvLineRequiredMEASUREMENTUNIT.split("\\s*,\\s*"));

		return Util.isEmpty(desadvLineRequiredMEASUREMENTUNIT)
				|| ANY_MEASUREMENTUNIT.equals(desadvLineRequiredMEASUREMENTUNIT)
				|| allowedMeasurementUnits.contains(measurementUnit.name());
	}
}
