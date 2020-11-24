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

package de.metas.edi.esb.excelimport;

import java.math.BigInteger;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.language.HeaderExpression;

import de.metas.edi.esb.commons.Util;

/**
 * Immutable Excel OLCand import configuration properties.
 *
 * @author tsa
 * @task 08839
 */
// @Immutable
public final class ExcelConfigurationContext
{
	/**
	 * Creates the configuration by extracting the values from given {@link Exchange}'s properties
	 *
	 * NOTE: the properties were set Exchange by {@link #asSetPropertiesToExchangeProcessor()}.
	 */
	public static ExcelConfigurationContext createFromExchange(final Exchange exchange)
	{
		return new ExcelConfigurationContext(exchange);
	}

	/**
	 * Creates the configuration by extracting the values from given {@link CamelContext}'s properties
	 */
	public static ExcelConfigurationContext createFromCamelContext(final CamelContext camelContext)
	{
		return new ExcelConfigurationContext(camelContext);
	}

	/** Config properties context root */
	private static final String CONTEXT_XLS_ROOT = "excel.order.";

	private final String CamelFileName;
	//
	// private static final String CONTEXT_EDIMessageDatePattern = CONTEXT_EDI_ROOT + "edi_message_date_pattern";
	// private final String EDIMessageDatePattern;
	private static final String CONTEXT_AD_Client_Value = CONTEXT_XLS_ROOT + "ad_client_value";
	private final String AD_Client_Value;
	private static final String CONTEXT_AD_Org_ID = CONTEXT_XLS_ROOT + "ad_org_id";
	private final int AD_Org_ID;
	private static final String CONTEXT_ADInputDataDestination_InternalName = CONTEXT_XLS_ROOT + "ad_input_datadestination_internalname";
	private final String ADInputDataDestination_InternalName;
	private static final String CONTEXT_ADInputDataSourceID = CONTEXT_XLS_ROOT + "ad_input_datasource_id";
	private final BigInteger ADInputDataSourceID;
	private static final String CONTEXT_ADUserEnteredByID = CONTEXT_XLS_ROOT + "ad_user_enteredby_id";
	private final BigInteger ADUserEnteredByID;
	private static final String CONTEXT_DELIVERY_RULE = CONTEXT_XLS_ROOT + "deliveryrule";
	private final String DeliveryRule;
	private static final String CONTEXT_DELIVERY_VIA_RULE = CONTEXT_XLS_ROOT + "deliveryviarule";
	private final String DeliveryViaRule;
	//
	private static final String CONTEXT_CurrencyISOCode = CONTEXT_XLS_ROOT + "currency";
	private final String currencyISOCode;

	/** Constructs the configuration by fetching the values from given {@link Exchange} properties */
	private ExcelConfigurationContext(final Exchange exchange)
	{
		this.CamelFileName = exchange.getProperty(Exchange.FILE_NAME, String.class);
		// this.EDIMessageDatePattern = exchange.getProperty(CONTEXT_EDIMessageDatePattern, String.class);
		this.AD_Client_Value = exchange.getProperty(CONTEXT_AD_Client_Value, String.class);
		this.AD_Org_ID = exchange.getProperty(CONTEXT_AD_Org_ID, Integer.class);
		this.ADInputDataDestination_InternalName = exchange.getProperty(CONTEXT_ADInputDataDestination_InternalName, String.class);
		this.ADInputDataSourceID = exchange.getProperty(CONTEXT_ADInputDataSourceID, BigInteger.class);
		this.ADUserEnteredByID = exchange.getProperty(CONTEXT_ADUserEnteredByID, BigInteger.class);
		this.DeliveryRule = exchange.getProperty(CONTEXT_DELIVERY_RULE, String.class);
		this.DeliveryViaRule = exchange.getProperty(CONTEXT_DELIVERY_VIA_RULE, String.class);
		//
		this.currencyISOCode = exchange.getProperty(CONTEXT_CurrencyISOCode, String.class);
	}

	private ExcelConfigurationContext(final CamelContext camelContext)
	{
		this.CamelFileName = null; // NOT available
		// this.EDIMessageDatePattern = Util.resolvePropertyPlaceholders(camelContext, CONTEXT_EDIMessageDatePattern);
		this.AD_Client_Value = Util.resolveProperty(camelContext, CONTEXT_AD_Client_Value);
		this.AD_Org_ID = Util.resolvePropertyPlaceholdersAsInt(camelContext, CONTEXT_AD_Org_ID);
		this.ADInputDataDestination_InternalName = Util.resolveProperty(camelContext, CONTEXT_ADInputDataDestination_InternalName);
		this.ADInputDataSourceID = new BigInteger(Util.resolveProperty(camelContext, CONTEXT_ADInputDataSourceID));
		this.ADUserEnteredByID = new BigInteger(Util.resolveProperty(camelContext, CONTEXT_ADUserEnteredByID));
		this.DeliveryRule = Util.resolveProperty(camelContext, CONTEXT_DELIVERY_RULE);
		this.DeliveryViaRule = Util.resolveProperty(camelContext, CONTEXT_DELIVERY_VIA_RULE);
		//
		this.currencyISOCode = Util.resolveProperty(camelContext, CONTEXT_CurrencyISOCode);
	}

	@Override
	public String toString()
	{
		return "XLSConfigurationContext ["
				+ "CamelFileName=" + CamelFileName
				// + ", EDIMessageDatePattern=" + EDIMessageDatePattern
				+ ", AD_Client_Value=" + AD_Client_Value
				+ ", AD_Org_ID=" + AD_Org_ID
				+ ", ADInputDataDestination_InternalName=" + ADInputDataDestination_InternalName
				+ ", ADInputDataSourceID=" + ADInputDataSourceID
				+ ", ADUserEnteredByID=" + ADUserEnteredByID
				+ ", DeliveryRule=" + DeliveryRule
				+ ", DeliveryViaRule=" + DeliveryViaRule
				+ ", currencyISOCode=" + currencyISOCode
				+ "]";
	}

	/**
	 * @return processor which sets the properties of this configuration as exchange properties.
	 */
	public Processor asSetPropertiesToExchangeProcessor()
	{
		return new Processor()
		{
			private final HeaderExpression headerFileName = new HeaderExpression(Exchange.FILE_NAME);

			@Override
			public void process(Exchange exchange) throws Exception
			{
				// NOTE: use the Header FILE_NAME instead of our context property
				// because that's dynamic and also the filename is not set when creating the context from CamelContext
				exchange.setProperty(Exchange.FILE_NAME, headerFileName.evaluate(exchange, String.class));

				// exchange.setProperty(CONTEXT_EDIMessageDatePattern, EDIMessageDatePattern);
				exchange.setProperty(CONTEXT_AD_Client_Value, AD_Client_Value);
				exchange.setProperty(CONTEXT_AD_Org_ID, AD_Org_ID);
				exchange.setProperty(CONTEXT_ADInputDataDestination_InternalName, ADInputDataDestination_InternalName);
				exchange.setProperty(CONTEXT_ADInputDataSourceID, ADInputDataSourceID);
				exchange.setProperty(CONTEXT_ADUserEnteredByID, ADUserEnteredByID);
				exchange.setProperty(CONTEXT_DELIVERY_RULE, DeliveryRule);
				exchange.setProperty(CONTEXT_DELIVERY_VIA_RULE, DeliveryViaRule);
				//
				exchange.setProperty(CONTEXT_CurrencyISOCode, currencyISOCode);
			}
		};
	}

	/** @return Excel filename which is currently imported */
	public String getCamelFileName()
	{
		return CamelFileName;
	}

	// public String getEDIMessageDatePattern()
	// {
	// return EDIMessageDatePattern;
	// }

	public String getAD_Client_Value()
	{
		return AD_Client_Value;
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public String getADInputDataDestination_InternalName()
	{
		return ADInputDataDestination_InternalName;
	}

	public BigInteger getADInputDataSourceID()
	{
		return ADInputDataSourceID;
	}

	public BigInteger getADUserEnteredByID()
	{
		return ADUserEnteredByID;
	}

	public String getDeliveryRule()
	{
		return DeliveryRule;
	}

	public String getDeliveryViaRule()
	{
		return DeliveryViaRule;
	}

	public String getCurrencyISOCode()
	{
		return currencyISOCode;
	}

}
