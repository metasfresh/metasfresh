package de.metas.adempiere.docline.sort.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_DocLine_Sort;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort_Item;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.Before;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.adempiere.model.I_M_Product;
import de.metas.testsupport.AbstractTestSupport;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 *
 * @author al
 *
 */
public abstract class AbstractDocLineSortItemFinderTest extends AbstractTestSupport
{
	//
	// Services
	protected final transient IDocLineSortDAO docLineSortDAO = Services.get(IDocLineSortDAO.class);

	protected final Properties ctx;

	protected I_C_DocType docTypeSOO;
	protected I_C_DocType docTypeMMS;
	protected I_C_DocType docTypePOO;
	protected I_C_DocType docTypeMMR;

	protected I_C_BPartner bpartner1;
	protected I_C_BPartner bpartner2;
	protected I_C_BPartner bpartner3;
	protected I_C_BPartner bpartner4;
	protected I_C_BPartner bpartner5;

	protected I_M_Product product1;
	protected I_M_Product product2;
	protected I_M_Product product3;
	protected I_M_Product product4;
	protected I_M_Product product5;

	public AbstractDocLineSortItemFinderTest()
	{
		super();

		ctx = Env.getCtx();
	}

	@Before
	public final void init()
	{
		AdempiereTestHelper.get().init();

		setupData();
	}

	private final void setupData()
	{
		// C_DocTypes
		{
			// Sales
			docTypeSOO = docType(X_C_DocType.DOCBASETYPE_SalesOrder, X_C_DocType.DOCSUBTYPE_Produktauslieferung);
			docTypeMMS = docType(X_C_DocType.DOCBASETYPE_MaterialDelivery, X_C_DocType.DOCSUBTYPE_Produktauslieferung);
			// Purchase
			docTypePOO = docType(X_C_DocType.DOCBASETYPE_PurchaseOrder, X_C_DocType.DOCSUBTYPE_Produktauslieferung);
			docTypeMMR = docType(X_C_DocType.DOCBASETYPE_MaterialReceipt, X_C_DocType.DOCSUBTYPE_Produktanlieferung);
		}

		bpartner1 = bpartner("G000");
		bpartner2 = bpartner("G001");
		bpartner3 = bpartner("G002");
		bpartner4 = bpartner("G003");
		bpartner5 = bpartner("G004");

		product1 = product("product1", 100000);
		product2 = product("product2", 100001);
		product3 = product("product3", 100002);
		product4 = product("product4", 100003);
		product5 = product("product5", 100004);

		setupAdditionalData();
	}

	/**
	 * Called after {@link #setupData()}.
	 */
	protected abstract void setupAdditionalData();

	protected final I_C_DocLine_Sort docLineSort(final String docLineSortName, final String docBaseType, final boolean isDefault)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_DocLine_Sort docLineSort = db.getFirstOnly(I_C_DocLine_Sort.class, new IQueryFilter<I_C_DocLine_Sort>()
		{
			@Override
			public boolean accept(final I_C_DocLine_Sort pojo)
			{
				final boolean equalsName = Check.equals(pojo.getName(), docLineSortName);
				final boolean equalsDocBaseType = Check.equals(pojo.getDocBaseType(), docBaseType);
				final boolean equalsDefault = Check.equals(pojo.isDefault(), isDefault);
				return equalsName
						&& equalsDocBaseType
						&& equalsDefault;
			}
		});

		if (docLineSort == null)
		{
			docLineSort = db.newInstance(Env.getCtx(), I_C_DocLine_Sort.class);
			docLineSort.setName(docLineSortName);
			docLineSort.setDocBaseType(docBaseType);
			docLineSort.setIsDefault(isDefault);
			InterfaceWrapperHelper.save(docLineSort);
		}
		return docLineSort;
	}

	protected final I_C_DocLine_Sort_Item docLineSortItem(final I_C_DocLine_Sort docLineSort, final I_M_Product product, final int seqNo)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_DocLine_Sort_Item docLineSortItem = db.getFirstOnly(I_C_DocLine_Sort_Item.class, new IQueryFilter<I_C_DocLine_Sort_Item>()
		{
			@Override
			public boolean accept(final I_C_DocLine_Sort_Item pojo)
			{
				final boolean equalsDocLineSort = pojo.getC_DocLine_Sort_ID() == docLineSort.getC_DocLine_Sort_ID();
				final boolean equalsProduct = pojo.getM_Product_ID() == product.getM_Product_ID();
				final boolean equalsSeqNo = pojo.getSeqNo() == seqNo;
				return equalsDocLineSort
						&& equalsProduct
						&& equalsSeqNo;
			}
		});

		if (docLineSortItem == null)
		{
			docLineSortItem = db.newInstance(Env.getCtx(), I_C_DocLine_Sort_Item.class);
			docLineSortItem.setC_DocLine_Sort(docLineSort);
			docLineSortItem.setM_Product(product);
			docLineSortItem.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(docLineSortItem);
		}
		return docLineSortItem;
	}

	protected final I_C_BP_DocLine_Sort docLineSortBP(final I_C_DocLine_Sort docLineSort, final I_C_BPartner bpartner)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_BP_DocLine_Sort bpDocLineSort = db.getFirstOnly(I_C_BP_DocLine_Sort.class, new IQueryFilter<I_C_BP_DocLine_Sort>()
		{
			@Override
			public boolean accept(final I_C_BP_DocLine_Sort pojo)
			{
				final boolean equalsDocLineSort = pojo.getC_DocLine_Sort_ID() == docLineSort.getC_DocLine_Sort_ID();
				final boolean equalsBPartner = pojo.getC_BPartner_ID() == bpartner.getC_BPartner_ID();
				return equalsDocLineSort
						&& equalsBPartner;
			}
		});

		if (bpDocLineSort == null)
		{
			bpDocLineSort = db.newInstance(Env.getCtx(), I_C_BP_DocLine_Sort.class);
			bpDocLineSort.setC_DocLine_Sort(docLineSort);
			bpDocLineSort.setC_BPartner(bpartner);
			InterfaceWrapperHelper.save(bpDocLineSort);
		}
		return bpDocLineSort;
	}
}
