package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



public class AggregationBL_IsAllowConsolidateInvoiceEffective_Test
{
//	@Mocked
//	I_C_BPartner partner;
//
//	@Mocked
//	ISysConfigBL sysConfigBL;
//
//	/**
//	 * Register our mocked service implementation
//	 */
//	@Before
//	public void init()
//	{
//		Services.registerService(ISysConfigBL.class, sysConfigBL);
//	}
//
//	@Test
//	public void test_isAllowConsolidateInvoiceEffective()
//	{
//		boolean allowsConsolidate = true;
//		boolean sysConfigValue = true;
//
//		allowsConsolidate = false;
//		{
//			sysConfigValue = false;
//			{
//				// vendor = false
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, false, sysConfigValue, false);
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, true, sysConfigValue, false);
//
//				// vendor = true
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, false, sysConfigValue, false);
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, true, sysConfigValue, false);
//			}
//
//			sysConfigValue = true;
//			{
//				// vendor = false
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, false, sysConfigValue, false);
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, true, sysConfigValue, false);
//
//				// vendor=true, soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, false, sysConfigValue, true);
//
//				// vendor = true
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, true, sysConfigValue, false);
//			}
//		}
//
//		allowsConsolidate = true;
//		final boolean expectedResultInthisWholeBlock = true;
//		{
//			sysConfigValue = false;
//			{
//				// vendor = false
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
//
//				// vendor = true
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
//			}
//
//			sysConfigValue = true;
//			{
//				// vendor = false
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
//				verifyIsAllowConsolidateInvoiceEffective(false, allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
//
//				// vendor=true, soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
//
//				// vendor = true
//				verifyIsAllowConsolidateInvoiceEffective(true, allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
//			}
//		}
//	}
//
//	protected void verifyIsAllowConsolidateInvoiceEffective(final boolean partnerIsVendor,
//			final boolean partnerAllowsConsolidateInvoice,
//			final boolean isSOTrx,
//			final boolean sysConfigValue,
//			final boolean expectedResult)
//	{
//		partnerAllowsConsolidateInvoice(partnerAllowsConsolidateInvoice);
//		partnerIsVendor(partnerIsVendor);
//		sysConfig_AllowConsolidateInvoice_ReturnsTrue(sysConfigValue);
//
//		final AggregationBL testee = new AggregationBL();
//		assertSame(expectedResult, testee.isAllowConsolidateInvoiceEffective(partner, isSOTrx));
//	}
//
//	private void partnerAllowsConsolidateInvoice(final boolean partnerAllowsConsolidateInvoice)
//	{
//		// @formatter:off
//		new NonStrictExpectations()
//		{{
//				partner.isAllowConsolidateInvoice();
//				result = partnerAllowsConsolidateInvoice;
//		}};
//		// @formatter:on
//	}
//
//	private void partnerIsVendor(final boolean partnerIsVendor)
//	{
//		// @formatter:off
//		new NonStrictExpectations()
//		{{
//				partner.isVendor();
//				result = partnerIsVendor;
//		}};
//		// @formatter:on
//	}
//
//	private void sysConfig_AllowConsolidateInvoice_ReturnsTrue(final boolean sysConfigReturnsTrue)
//	{
//		// @formatter:off
//		new NonStrictExpectations()
//		{{
//				sysConfigBL.getBooleanValue(AggregationBL.SYSCONFIG_C_BPartner_Vendor_AllowConsolidateInvoice_Override, false);
//				result = sysConfigReturnsTrue;
//		}};
//		// @formatter:on
//	}
}
