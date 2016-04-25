package de.metas.handlingunits.client.editor.view.manual;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.document.AbstractTestDataSourceBuilder;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.document.impl.HUDocumentListDataSource;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

public class HUEditorFromHUsManualTestFrame extends HUEditorManualTestFrame
{
	private final HUTestHelper helper;
	private final I_M_HU_PI huDefPalet;

	public HUEditorFromHUsManualTestFrame()
	{
		helper = new HUTestHelper();
		helper.init();

		// make sure SwingClientUI is used
		Services.registerService(IClientUI.class, new SwingClientUI());

		final I_M_HU_PI huDefBlister = helper.createHuDefinition(HUTestHelper.NAME_Bag_Product);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefBlister);
			helper.assignProduct(itemMA, helper.pTomato, new BigDecimal("20"), helper.uomEach);

			// helper.createHU_PI_Item_PackingMaterial(huDefBlister, helper.pmBlister);
		}

		final I_M_HU_PI huDefIFCO = helper.createHuDefinition(HUTestHelper.NAME_IFCO_Product);
		{
			helper.createHU_PI_Item_IncludedHU(huDefIFCO, huDefBlister, new BigDecimal(10));
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, helper.pmIFCO);
		}

		this.huDefPalet = helper.createHuDefinition(HUTestHelper.NAME_Palet_Product);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal(2));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, helper.pmPalet);
		}

		helper.createHUs(huDefPalet,
				helper.pTomato,
				new BigDecimal("1"),
				helper.uomEach);
	}

	@Override
	protected AbstractTestDataSourceBuilder<?> createDataSourceBuilder()
	{
		throw new AdempiereException("Shall not be called");
	}

	@Override
	public IDataSource createDataSource()
	{
		final List<I_M_HU> hus = helper.retrieveAllHandlingUnits();
		for (Iterator<I_M_HU> it = hus.iterator(); it.hasNext();)
		{
			final I_M_HU hu = it.next();
			if (hu.getM_HU_Item_Parent_ID() > 0)
			{
				it.remove();
			}
		}

		final List<IHUDocument> documents = Services.get(IHUDocumentFactoryService.class).createHUDocuments(
				helper.ctx,
				I_M_HU.class,
				hus.iterator());

		final HUDocumentListDataSource dataSource = new HUDocumentListDataSource(documents);
		return dataSource;
	}

	public static void main(final String[] args)
	{
		new HUEditorFromHUsManualTestFrame().start();
	}

}
