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


import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Ignore;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.allocation.source.impl.TestDataSource;
import de.metas.handlingunits.api.IHandlingUnitsBL;
import de.metas.handlingunits.api.IHandlingUnitsDAO;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorPanel;
import de.metas.handlingunits.client.form.HUEditorHelper;
import de.metas.handlingunits.document.AbstractTestDataSourceBuilder;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.MInOutTestDataSourceBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

@Ignore
public class HUEditorManualTestFrame
{
	//
	// Setup
	static
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IClientUI.class, new SwingClientUI());
		POJOWrapper.setPrintReferencedModels(false);

		new de.metas.handlingunits.model.validator.Main().registerFactories();
	}

	public static void main(final String[] args)
	{
		new HUEditorManualTestFrame().start();
	}

	private AbstractTestDataSourceBuilder<?> dataSourceBuilder;

	protected HUEditorManualTestFrame()
	{
		super();
	}

	protected AbstractTestDataSourceBuilder<?> createDataSourceBuilder()
	{
		final IDataSource parentDataSource = new TestDataSource();
		POJOLookupMap.get().dumpStatus();

		final AbstractTestDataSourceBuilder<?> dataSourceBuilder = new MInOutTestDataSourceBuilder(parentDataSource);
		// final AbstractTestDataSourceBuilder<?> dataSourceBuilder = new ReceiptScheduleTestDataSourceBuilder(parentDataSource);
		// final AbstractTestDataSourceBuilder<?> dataSourceBuilder = new HandlingUnitTestDataSourceBuilder(parentDataSource);
		dataSourceBuilder.setContext(new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));

		return dataSourceBuilder;
	}

	public IDataSource createDataSource()
	{
		if (dataSourceBuilder == null)
		{
			dataSourceBuilder = createDataSourceBuilder();
		}
		return dataSourceBuilder.createDataSource();
	}

	public void start()
	{
		while (true)
		{
			final IDataSource dataSource = createDataSource();
			showHUEditor(dataSource);

			validateData();
		}
	}

	private static void showHUEditor(final IDataSource dataSource)
	{
		final HUEditorPanel huEditorPanel = new HUEditorPanel();
		huEditorPanel.setDataDource(dataSource);

		final JDialog frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("HU Editor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 800));
		frame.add(huEditorPanel);

		frame.setJMenuBar(new JMenuBar());
		HUEditorHelper.createMenu(frame.getJMenuBar(), huEditorPanel);

		frame.pack();
		frame.setVisible(true);
	}

	private static void validateData()
	{
		try
		{
			validateData0();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void validateData0()
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU hu : POJOLookupMap.get().getRecords(I_M_HU.class))
		{
			for (final I_M_HU_Item huItem : Services.get(IHandlingUnitsDAO.class).retrieveItems(hu))
			{
				final IHUItemStorage itemStorage = storageFactory.getStorage(huItem);
				HUAssert.assertStorageValid(itemStorage);
			}
		}
	}
}
