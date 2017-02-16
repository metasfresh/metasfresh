package de.metas.testsupport;

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

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_M_Product;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.impl.PlainCurrencyBL;
import de.metas.interfaces.I_C_BPartner_Product;

public class AbstractTestSupport
{
	/**
	 * Gets/creates an {@link I_M_Product} with given value.
	 *
	 * @param productValue
	 * @param productId TODO
	 * @return product
	 */
	public I_M_Product product(final String productValue, final int productId)
	{
		return product(productValue, productId, 0);
	}
	
	public I_M_Product product(final String productValue, final int productId, final int orgId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_Product product = db.getFirstOnly(I_M_Product.class, new IQueryFilter<I_M_Product>()
		{
			@Override
			public boolean accept(final I_M_Product pojo)
			{
				return Objects.equals(pojo.getValue(), productValue) && pojo.getM_Product_ID() == productId;
			}
		});

		if (product == null)
		{
			product = db.newInstance(Env.getCtx(), I_M_Product.class);
			product.setAD_Org_ID(orgId);
			product.setValue(productValue);
			product.setName(productValue);
			product.setM_Product_ID(productId);
			InterfaceWrapperHelper.save(product);
		}

		return product;
	}

	/**
	 * Create an organization with a given name
	 * 
	 * @param name
	 * @return
	 */
	public I_AD_Org org(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);

		org.setName(name);

		InterfaceWrapperHelper.save(org);

		return org;
	}

	protected I_M_ProductPrice productPrice(final int productPriceId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_ProductPrice productPrice = db.getFirstOnly(I_M_ProductPrice.class, new IQueryFilter<I_M_ProductPrice>()
		{

			@Override
			public boolean accept(final I_M_ProductPrice pojo)
			{
				return Objects.equals(pojo.getM_ProductPrice_ID(), productPriceId);
			}
		});

		if (productPrice == null)
		{
			productPrice = db.newInstance(Env.getCtx(), I_M_ProductPrice.class);
			productPrice.setM_ProductPrice_ID(productPriceId);
			InterfaceWrapperHelper.save(productPrice);
		}

		return productPrice;

	}

	protected I_M_DiscountSchemaLine discountSchemaLine(final int discountSchemaLineId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_DiscountSchemaLine discountSchemaLine = db.getFirstOnly(I_M_DiscountSchemaLine.class, new IQueryFilter<I_M_DiscountSchemaLine>()
		{

			@Override
			public boolean accept(final I_M_DiscountSchemaLine pojo)
			{
				return Objects.equals(pojo.getM_DiscountSchemaLine_ID(), discountSchemaLineId);
			}
		});

		if (discountSchemaLine == null)
		{
			discountSchemaLine = db.newInstance(Env.getCtx(), I_M_DiscountSchemaLine.class);
			discountSchemaLine.setM_DiscountSchemaLine_ID(discountSchemaLineId);
			InterfaceWrapperHelper.save(discountSchemaLine);
		}

		return discountSchemaLine;

	}

	protected I_M_PriceList priceList(final int priceListId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_PriceList priceList = db.getFirstOnly(I_M_PriceList.class, new IQueryFilter<I_M_PriceList>()
		{

			@Override
			public boolean accept(final I_M_PriceList pojo)
			{
				return Objects.equals(pojo.getM_PriceList_ID(), priceListId);
			}
		});

		if (priceList == null)
		{
			priceList = db.newInstance(Env.getCtx(), I_M_PriceList.class);
			priceList.setM_PriceList_ID(priceListId);
			InterfaceWrapperHelper.save(priceList);
		}

		return priceList;

	}

	protected I_M_PriceList_Version priceListVersion(final int priceListVersionId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_PriceList_Version priceListVersion = db.getFirstOnly(I_M_PriceList_Version.class, new IQueryFilter<I_M_PriceList_Version>()
		{

			@Override
			public boolean accept(final I_M_PriceList_Version pojo)
			{
				return Objects.equals(pojo.getM_PriceList_Version_ID(), priceListVersionId);
			}
		});

		if (priceListVersion == null)
		{
			priceListVersion = db.newInstance(Env.getCtx(), I_M_PriceList_Version.class);
			priceListVersion.setM_PriceList_ID(priceListVersionId);
			InterfaceWrapperHelper.save(priceListVersion);
		}

		return priceListVersion;

	}

	protected I_M_AttributeSetInstance attributeSetInstance(final int id)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_AttributeSetInstance attributeSetInstance = db.getFirstOnly(I_M_AttributeSetInstance.class, new IQueryFilter<I_M_AttributeSetInstance>()
		{

			@Override
			public boolean accept(final I_M_AttributeSetInstance pojo)
			{
				return Objects.equals(pojo.getM_AttributeSetInstance_ID(), id);
			}
		});

		if (attributeSetInstance == null)
		{
			attributeSetInstance = db.newInstance(Env.getCtx(), I_M_AttributeSetInstance.class);
			attributeSetInstance.setM_AttributeSetInstance_ID(id);
			InterfaceWrapperHelper.save(attributeSetInstance);
		}

		return attributeSetInstance;
	}

	protected I_M_AttributeInstance attributeInstance(final int setId, final String value)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_AttributeInstance attributeInstance = db.getFirstOnly(I_M_AttributeInstance.class, new IQueryFilter<I_M_AttributeInstance>()
		{

			@Override
			public boolean accept(final I_M_AttributeInstance pojo)
			{
				return Objects.equals(pojo.getM_AttributeValue(), value) && Objects.equals(pojo.getM_AttributeSetInstance_ID(), setId);
			}
		});

		if (attributeInstance == null)
		{
			attributeInstance = db.newInstance(Env.getCtx(), I_M_AttributeInstance.class);
			attributeInstance.setM_AttributeSetInstance_ID(setId);
			attributeInstance.setValue(value);
			InterfaceWrapperHelper.save(attributeInstance);
		}

		return attributeInstance;
	}

	/**
	 * Get/Create {@link I_M_AttributeValue} which has given <code>value</code>.
	 *
	 * @param value
	 * @return
	 */
	protected I_M_AttributeValue attributeValue(final String value)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_AttributeValue attributeValue = db.getFirstOnly(I_M_AttributeValue.class, new IQueryFilter<I_M_AttributeValue>()
		{

			@Override
			public boolean accept(final I_M_AttributeValue pojo)
			{
				return Objects.equals(pojo.getValue(), value);
			}
		});

		if (attributeValue == null)
		{
			attributeValue = db.newInstance(Env.getCtx(), I_M_AttributeValue.class);
			attributeValue.setValue(value);
			InterfaceWrapperHelper.save(attributeValue);
		}

		return attributeValue;
	}

	protected I_C_DocType docType(final String baseType, final String subType)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_DocType docType = db.getFirstOnly(I_C_DocType.class, new IQueryFilter<I_C_DocType>()
		{

			@Override
			public boolean accept(final I_C_DocType pojo)
			{
				return Objects.equals(pojo.getDocBaseType(), baseType) && Objects.equals(pojo.getDocSubType(), baseType);
			}
		});

		if (docType == null)
		{
			docType = db.newInstance(Env.getCtx(), I_C_DocType.class);
			docType.setDocBaseType(baseType);
			docType.setDocSubType(subType);
			InterfaceWrapperHelper.save(docType);
		}

		return docType;
	}

	protected I_C_Tax tax(final BigDecimal rate)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_Tax tax = db.getFirstOnly(I_C_Tax.class, new IQueryFilter<I_C_Tax>()
		{

			@Override
			public boolean accept(final I_C_Tax pojo)
			{
				return Objects.equals(pojo.getRate(), rate);
			}
		});

		if (tax == null)
		{
			tax = db.newInstance(Env.getCtx(), I_C_Tax.class);
			tax.setRate(rate);
			InterfaceWrapperHelper.save(tax);
		}

		return tax;
	}

	/**
	 * Gets/creates a BPartner with given Value
	 *
	 * @param bpValue
	 * @return bpartner
	 */
	public I_C_BPartner bpartner(final String bpValue)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_BPartner bpartner = db.getFirstOnly(I_C_BPartner.class, new IQueryFilter<I_C_BPartner>()
		{
			@Override
			public boolean accept(final I_C_BPartner pojo)
			{
				return Objects.equals(pojo.getValue(), bpValue);
			}
		});

		if (bpartner == null)
		{
			bpartner = db.newInstance(Env.getCtx(), I_C_BPartner.class);
			bpartner.setValue(bpValue);
			bpartner.setName(bpValue);

			bpartner.setAD_Org_ID(0);
			InterfaceWrapperHelper.save(bpartner);
		}

		return bpartner;
	}

	public I_C_BPartner_Product bpartnerProduct(final I_C_BPartner bpartner, final I_M_Product product, final I_AD_Org org)
	{

		final I_C_BPartner_Product bpProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		
		bpProduct.setC_BPartner(bpartner);
		bpProduct.setM_Product(product);
		bpProduct.setAD_Org(org);
		
		InterfaceWrapperHelper.save(bpProduct);
		
		return bpProduct;
	}

	public I_C_Order order(final String orderDocNo)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_Order order = db.getFirstOnly(I_C_Order.class, new IQueryFilter<I_C_Order>()
		{

			@Override
			public boolean accept(final I_C_Order pojo)
			{
				return Objects.equals(pojo.getDocumentNo(), orderDocNo);
			}
		});

		if (order == null)
		{
			order = db.newInstance(Env.getCtx(), I_C_Order.class);
			order.setDocumentNo(orderDocNo);
			InterfaceWrapperHelper.save(order);
		}

		return order;
	}

	protected I_AD_User user(final String userName)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_AD_User user = db.getFirstOnly(I_AD_User.class, new IQueryFilter<I_AD_User>()
		{
			@Override
			public boolean accept(final I_AD_User pojo)
			{
				return Objects.equals(pojo.getName(), userName);
			}
		});

		if (user == null)
		{
			user = db.newInstance(Env.getCtx(), I_AD_User.class);
			user.setName(userName);
			InterfaceWrapperHelper.save(user);
		}

		return user;
	}

	protected I_C_OrderLine orderLine(final String orderLineDescription)
	{
		return orderLine(orderLineDescription, I_C_OrderLine.class);
	}

	public <T> T orderLine(final String orderLineDescription, final Class<T> clazz)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_OrderLine orderLine = db.getFirstOnly(I_C_OrderLine.class, new IQueryFilter<I_C_OrderLine>()
		{
			@Override
			public boolean accept(final I_C_OrderLine pojo)
			{
				return Objects.equals(pojo.getDescription(), orderLineDescription);
			}
		});

		if (orderLine == null)
		{
			orderLine = db.newInstance(Env.getCtx(), I_C_OrderLine.class);
			orderLine.setDescription(orderLineDescription);

			final PlainCurrencyBL currencyConversionBL = (PlainCurrencyBL)Services.get(ICurrencyBL.class);
			orderLine.setC_Currency(currencyConversionBL.getBaseCurrency(Env.getCtx()));
			InterfaceWrapperHelper.save(orderLine);
		}

		return InterfaceWrapperHelper.create(orderLine, clazz);
	}

	protected I_C_InvoiceSchedule schedule(final String scheduleName, final String scheduleFrequency)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_InvoiceSchedule schedule = db.getFirstOnly(I_C_InvoiceSchedule.class, new IQueryFilter<I_C_InvoiceSchedule>()
		{

			@Override
			public boolean accept(final I_C_InvoiceSchedule pojo)
			{
				return Objects.equals(pojo.getName(), scheduleName);
			}
		});

		if (schedule == null)
		{
			schedule = db.newInstance(Env.getCtx(), I_C_InvoiceSchedule.class);
			schedule.setName(scheduleName);
			schedule.setInvoiceFrequency(scheduleFrequency);
			InterfaceWrapperHelper.save(schedule);
		}

		return schedule;
	}

	protected I_C_Charge charge(final String chargeName)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_Charge charge = db.getFirstOnly(I_C_Charge.class, new IQueryFilter<I_C_Charge>()
		{

			@Override
			public boolean accept(final I_C_Charge pojo)
			{
				return Objects.equals(pojo.getName(), chargeName);
			}
		});

		if (charge == null)
		{
			charge = db.newInstance(Env.getCtx(), I_C_Charge.class);
			charge.setName(chargeName);
			InterfaceWrapperHelper.save(charge);
		}

		return charge;
	}

	protected I_M_InOut inOut(final String inoutDocumentNo)
	{
		return inOut(inoutDocumentNo, I_M_InOut.class);
	}

	protected <T extends I_M_InOut> T inOut(final String inoutDocumentNo, final Class<T> clazz)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_InOut inOut = db.getFirstOnly(I_M_InOut.class, new IQueryFilter<I_M_InOut>()
		{
			@Override
			public boolean accept(final I_M_InOut pojo)
			{
				return Objects.equals(pojo.getDocumentNo(), inoutDocumentNo);
			}
		});

		if (inOut == null)
		{
			inOut = db.newInstance(Env.getCtx(), I_M_InOut.class);
			inOut.setDocumentNo(inoutDocumentNo);
			InterfaceWrapperHelper.save(inOut);
		}

		return InterfaceWrapperHelper.create(inOut, clazz);
	}

	protected I_M_InOutLine inOutLine(final String inOutLineDescription)
	{
		final boolean assumeNew = false;
		return inOutLine(inOutLineDescription, assumeNew);
	}

	protected I_M_InOutLine inOutLine(final String inOutLineDescription, final boolean assumeNew)
	{
		return inOutLine(inOutLineDescription, assumeNew, I_M_InOutLine.class);
	}

	protected <T extends I_M_InOutLine> T inOutLine(
			final String inOutLineDescription,
			final boolean assumeNew,
			final Class<T> clazz)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_InOutLine inOutLine = db.getFirstOnly(I_M_InOutLine.class, new IQueryFilter<I_M_InOutLine>()
		{
			@Override
			public boolean accept(final I_M_InOutLine pojo)
			{
				return Objects.equals(pojo.getDescription(), inOutLineDescription);
			}
		});

		Check.errorIf(assumeNew && inOutLine != null,
				"Param 'assumeNew'==true, still there is an existing inOutline with description '{}: {}", inOutLineDescription, inOutLine);

		if (inOutLine == null)
		{
			inOutLine = db.newInstance(Env.getCtx(), I_M_InOutLine.class);
			inOutLine.setDescription(inOutLineDescription);
			InterfaceWrapperHelper.save(inOutLine);
		}

		return InterfaceWrapperHelper.create(inOutLine, clazz);
	}
}
