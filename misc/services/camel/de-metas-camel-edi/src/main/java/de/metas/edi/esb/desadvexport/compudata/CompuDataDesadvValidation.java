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

package de.metas.edi.esb.desadvexport.compudata;

import de.metas.edi.esb.desadvexport.helper.DesadvLines;
import de.metas.edi.esb.desadvexport.helper.DesadvParser;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;

import java.util.List;

import static de.metas.edi.esb.commons.ValidationHelper.validateObject;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;

public class CompuDataDesadvValidation
{
	public EDIExpDesadvType validateExchange(final Exchange exchange)
	{
		final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class);
		validateObject(xmlDesadv, "@EDI.DESADV.XmlNotNull@"); // DESADV body shall not be null

		// validate mandatory types (not null)
		validateObject(xmlDesadv.getCBPartnerID(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @C_BPartner_ID@");
		validateObject(xmlDesadv.getBillLocationID(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @Bill_Location_ID@");
		validateObject(xmlDesadv.getSequenceNoAttr(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @SequenceNoAttr@");
		validateObject(xmlDesadv.getMovementDate(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @MovementDate@");
		validateObject(xmlDesadv.getDateOrdered(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @DateOrdered@");
		validateObject(xmlDesadv.getCBPartnerLocationID(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @C_BPartner_Location_ID@");
		validateObject(xmlDesadv.getPOReference(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @POReference@");
		validateObject(xmlDesadv.getCCurrencyID(), "@FillMandatory@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @C_Currency_ID@");

		// validate strings (not null or empty)
		validateString(xmlDesadv.getCBPartnerID().getEdiRecipientGLN(), "@FillMandatory@ @C_BPartner_ID@=" + xmlDesadv.getCBPartnerID().getValue() + " @EdiRecipientGLN@");
		validateString(xmlDesadv.getCBPartnerLocationID().getGLN(), "@FillMandatory@ @C_BPartner_Location_ID@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @GLN@");
		validateObject(xmlDesadv.getBillLocationID().getGLN(), "@FillMandatory@ @Bill_Location_ID@ @EDI_DESADV_ID@=" + xmlDesadv.getDocumentNo() + " @GLN@");

		// Evaluate EDI_DesadvLines
		final DesadvLines desadvLines = DesadvParser.getDesadvLinesEnforcingSinglePacks(xmlDesadv);
		final List<EDIExpDesadvLineType> ediExpDesadvLines = desadvLines.getAllLines();
		if (ediExpDesadvLines.isEmpty())
		{
			throw new RuntimeCamelException("@EDI.DESADV.ContainsDesadvLines@");
		}

		for (final EDIExpDesadvLineType xmlDesadvLine : ediExpDesadvLines)
		{
			// validate mandatory types (not null)
			validateObject(xmlDesadvLine.getLine(), "@FillMandatory@  @EDI_DesadvLine_ID@ @Line@");

			validateObject(xmlDesadvLine.getCUOMID(), "@FillMandatory@ @EDI_DesadvLine_ID@=" + xmlDesadvLine.getLine() + " @C_UOM_ID@");
			validateObject(xmlDesadvLine.getMProductID(), "@FillMandatory@ @EDI_DesadvLine_ID@=" + xmlDesadvLine.getLine() + " @M_Product_ID@");

			validateObject(xmlDesadvLine.getQtyEntered(), "@FillMandatory@ @EDI_DesadvLine_ID@=" + xmlDesadvLine.getLine() + " @QtyEntered@");

			validateString(xmlDesadvLine.getProductNo(), "@FillMandatory@ ProductNo in @EDI_DesadvLine_ID@ " + xmlDesadvLine.getLine());
			validateString(xmlDesadvLine.getEANCU(), "@FillMandatory@ EAN_CU in @EDI_DesadvLine_ID@ " + xmlDesadvLine.getLine());
			validateString(xmlDesadvLine.getProductDescription(), "@FillMandatory@ ProductDescription in @EDI_DesadvLine_ID@ " + xmlDesadvLine.getLine());
		}

		return xmlDesadv;
	}
}
