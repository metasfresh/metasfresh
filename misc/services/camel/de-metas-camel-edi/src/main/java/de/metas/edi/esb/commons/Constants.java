package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
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

import de.metas.edi.esb.commons.api.ILookupTemplate;
import de.metas.edi.esb.commons.api.impl.LookupTemplate;
import de.metas.edi.esb.jaxb.metasfresh.EDICBPartnerLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;

public final class Constants
{
	/**
	 * Date pattern that should be used by the metasfresh replication interface.
	 */
	public static final String METASFRESH_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS"; // TODO extract pattern to props file

	public static final String EP_AMQP_FROM_MF = "{{ep.rabbitmq.from.mf}}";
	public static final String EP_AMQP_TO_MF = "ep.rabbitmq.to.mf";
	public static final String EP_AMQP_TO_MF_DURABLE_ROUTING_KEY = "{{rabbitmq.out.exchange.durable}}";

	public static final String JAXB_ContextPath = ObjectFactory.class.getPackage().getName();
	public static final ObjectFactory JAXB_ObjectFactory = new ObjectFactory();

	public static final String EXP_FORMAT_GENERIC_VERSION = "*";

	public static final String METHOD_SET_Name = "setName";
	public static final String METHOD_SET_Value = "setValue";
	public static final String METHOD_SET_GLN = "setGLN";
	public static final String METHOD_SET_StoreGLN = "setStoreGLN";
	public static final String METHOD_SET_CountryCode = "setCountryCode";
	public static final String METHOD_SET_X12DE355 = "setX12DE355";
	public static final String METHOD_SET_EdiRecipientGLN = "setEdiRecipientGLN";
	public static final String METHOD_SET_UPC = "setUPC";
	public static final String METHOD_SET_ISOCode = "setISOCode";
	public static final String METHOD_SET_InternalName = "setInternalName";
	public static final String METHOD_SET_C_BPartner_ID = "setCBPartnerID";

	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_Name = new LookupTemplate<String>(String.class, Constants.METHOD_SET_Name);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_Value = new LookupTemplate<String>(String.class, Constants.METHOD_SET_Value);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_GLN = new LookupTemplate<String>(String.class, Constants.METHOD_SET_GLN);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_StoreGLN = new LookupTemplate<String>(String.class, Constants.METHOD_SET_StoreGLN);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_CountryCode = new LookupTemplate<String>(String.class, Constants.METHOD_SET_CountryCode);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_X12DE355 = new LookupTemplate<String>(String.class, Constants.METHOD_SET_X12DE355);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_EdiRecipientGLN = new LookupTemplate<String>(String.class, Constants.METHOD_SET_EdiRecipientGLN);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_UPC = new LookupTemplate<String>(String.class, Constants.METHOD_SET_UPC);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_ISOCode = new LookupTemplate<String>(String.class, Constants.METHOD_SET_ISOCode);
	public static final ILookupTemplate<String> LOOKUP_TEMPLATE_InternalName = new LookupTemplate<String>(String.class, Constants.METHOD_SET_InternalName);
	public static final ILookupTemplate<EDICBPartnerLookupBPLGLNVType> LOOKUP_TEMPLATE_C_BPartner_ID = new LookupTemplate<EDICBPartnerLookupBPLGLNVType>(EDICBPartnerLookupBPLGLNVType.class, Constants.METHOD_SET_C_BPartner_ID);

	public static final String DECIMAL_FORMAT = "EDIDecimalFormat";

	private Constants()
	{
		super();
	}
}
