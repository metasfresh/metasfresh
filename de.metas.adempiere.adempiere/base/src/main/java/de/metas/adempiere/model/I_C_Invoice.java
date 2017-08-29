package de.metas.adempiere.model;

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

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_C_DocType;

import de.metas.document.model.IDocumentLocation;

public interface I_C_Invoice extends org.compiere.model.I_C_Invoice, IDocumentLocation
{
	/**
	 * Doctype for adjustment charges (invoices) resulting from delivery differences.
	 */
	String DOC_SUBTYPE_ARI_AQ = "AQ";

	/**
	 * Doctype for adjustment charges (invoices) resulting from price differences.
	 */
	String DOC_SUBTYPE_ARI_AP = "AP";

	/**
	 * Doctype for credit memos resulting from delivery differences or RMAs.
	 */
	String DOC_SUBTYPE_ARC_CQ = "CQ";

	/**
	 * Doctype for credit memos resulting from price differences.
	 */
	String DOC_SUBTYPE_ARC_CR = "CR";

	/**
	 * Doctype for credit memos resulting from returned material
	 */
	String DOC_SUBTYPE_ARC_CS = X_C_DocType.DOCSUBTYPE_GS_Retoure;

	// 04258

	public static String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	public String getDescriptionBottom();

	public void setDescriptionBottom(String DescriptionBottom);

	public static final String COLUMNNAME_Incoterm = "Incoterm";

	public String getIncoterm();

	public void setIncoterm(String Incoterm);

	public static final String COLUMNNAME_IncotermLocation = "IncotermLocation";

	public String getIncotermLocation();

	public void setIncotermLocation(String IncotermLocation);

	public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	public boolean isUseBPartnerAddress();

	public void setIsUseBPartnerAddress(boolean IsUseBPartnerAddress);

	// 02527
	/** Column name M_AttributeSetInstance_ID */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance */
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

	// 02527 end

	/**
	 * @task http://dewiki908/mediawiki/index.php/08927_Add_feature_Gutgeschriebener_Betrag_erneut_abrechenbar_%28101267285473%29
	 */
	// @formatter:off
	public static final String COLUMNNAME_IsCreditedInvoiceReinvoicable = "IsCreditedInvoiceReinvoicable";
	public void setIsCreditedInvoiceReinvoicable(boolean IsCreditedInvoiceReinvoicable);
	public boolean isCreditedInvoiceReinvoicable();
	// @formatter:on
}
