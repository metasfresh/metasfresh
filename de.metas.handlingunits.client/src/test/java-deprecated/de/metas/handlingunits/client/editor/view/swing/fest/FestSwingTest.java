package de.metas.handlingunits.client.editor.view.swing.fest;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Util;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.hamcrest.CoreMatchers;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.handlingunits.allocation.source.impl.TestDataSource;
import de.metas.handlingunits.client.editor.allocation.model.HUDocumentsModel;
import de.metas.handlingunits.client.editor.allocation.view.swing.HUDocumentsPanel;
import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.attr.view.swing.HUAttributeSetEditor;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorFrame;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorPanel;
import de.metas.handlingunits.client.editor.view.swing.fest.helper.TableCellHelper;
import de.metas.handlingunits.client.editor.view.swing.fest.vnumber.VNumberCellWriter;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Swing Integration Test
 * 
 * @author al
 */
public class FestSwingTest
{
	static
	{
		// // Set system property.
		// // Call this BEFORE the toolkit has been initialized, that is,
		// // before Toolkit.getDefaultToolkit() has been called.
		// System.setProperty("java.awt.headless", "true");
	}
	private final GenericTypeMatcher<JXTreeTable> EDITOR_TABLE_MATCHER = new GenericTypeMatcher<JXTreeTable>(JXTreeTable.class)
	{
		@Override
		protected boolean isMatching(final JXTreeTable component)
		{
			return Util.same(component, editorTreeTable);
		}
	};

	private final GenericTypeMatcher<JXTreeTable> ALLOCATION_TABLE_MATCHER = new GenericTypeMatcher<JXTreeTable>(JXTreeTable.class)
	{
		@Override
		protected boolean isMatching(final JXTreeTable component)
		{
			return Util.same(component, documentsTreeTable);
		}
	};

	private FrameFixture window;

	private JXTreeTable editorTreeTable;
	private HUEditorModel editorModel;

	private JXTreeTable documentsTreeTable;
	private HUDocumentsModel documentsModel;

	private TestDataSource dataSource;

	private HUEditorPanel editorPanel;

	/**
	 * @see <a href="http://docs.codehaus.org/display/FEST/Testing+that+access+to+Swing+components+is+done+in+the+EDT">this</a>
	 */
	@BeforeClass
	public static void setUpOnce()
	{
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp()
	{
		AdempiereTestHelper.get().init();

		dataSource = new TestDataSource();

		final HUEditorFrame frame = GuiActionRunner.execute(new GuiQuery<HUEditorFrame>()
		{
			@Override
			protected HUEditorFrame executeInEDT()
			{
				final HUEditorFrame frame = new HUEditorFrame();
				frame.setDataDource(dataSource);
				return frame;
			}
		});
		window = new FrameFixture(frame.getFrame());

		editorPanel = frame.getHUEditorPanel();

		editorTreeTable = editorPanel.getTreeTable();
		editorModel = editorPanel.getModel();

		final HUDocumentsPanel allocationPanel = editorPanel.getAllocationPanel();
		documentsTreeTable = allocationPanel.getTreeTable();
		documentsModel = allocationPanel.getModel();

		window.show(new Dimension(1200, 900)); // shows the frame to test
	}

	@After
	public void tearDown()
	{
		window.cleanUp();
	}

	@Test
	public void testHUEditorFrame()
	{
		// our current Xvfb on dejen901 does not support this, resulting in
		// org.fest.swing.exception.ActionFailedException: Platform does not support maximizing frames
		// window.maximize();

		// create a blister
		final IHUTreeNode rootEditor = editorModel.getRoot();
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, rootEditor, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Add Blister")
				.click();

		// undo/redo create
		undo();
		redo();

		// remove the blister
		final IHUTreeNode mixedPalet = rootEditor.getChildAt(0);
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPalet, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Remove")
				.click();

		// undo/redo remove
		undo();
		redo();

		// create a mixed palet
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, rootEditor, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Add Mixed Palet")
				.click();

		// create an IFCO in the first PI (this one allows for it)
		final IHUTreeNode mixedPaletItem0 = rootEditor.getChildAt(0).getChildAt(0);
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletItem0, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Add IFCO")
				.click();

		// create a blister in the IFCO's Item
		final IHUTreeNode mixedPaletIFCOItem = mixedPaletItem0.getChildAt(0).getChildAt(0);
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCOItem, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Add Blister")
				.click();

		// set the allocation source for the blister first Item
		final IHUTreeNode mixedPaletIFCOBlisterItem0 = mixedPaletIFCOItem.getChildAt(0).getChildAt(0);
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCOBlisterItem0, HUEditorModel.COLUMNNAME_M_HU_DocumentLine))
				.select().select() // FIXME do not need to select twice when moving on to a combo box
				.enterValue("Tomato-Product#10010");

		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCOBlisterItem0, HUEditorModel.COLUMNNAME_M_Product_ID))
				.enterValue("Tomato-Product");

		final IHUDocumentTreeNode rootAllocation = documentsModel.getRoot();
		// assert proper allocation when introducing quantity that exceeds the maximum allocation size
		{
			final String qtyExceeded = "20";
			// VNumber seems to be annoying FEST, so we set up a cellWriter
			// see http://docs.codehaus.org/display/FEST/Custom+Cell+Editors
			final String editorResultInsert1 = window.table(EDITOR_TABLE_MATCHER)
					.cellWriter(new VNumberCellWriter(window.robot))
					.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCOBlisterItem0, HUEditorModel.COLUMNNAME_Qty))
					.enterValue(qtyExceeded)
					.value();
			// maximum allocation capacity is 10 for [[L1: Tomato-Product#1001]], Tomato-Product
			assertEquals("Invalid edited quantity", "10", editorResultInsert1);

			final IHUDocumentTreeNode lineSource1TomatoProduct = rootAllocation.getChildAt(0).getChildAt(0);
			final String allocationResultInsert1 = window.table(ALLOCATION_TABLE_MATCHER)
					.cell(TableCellHelper.findCell(documentsTreeTable, documentsModel, lineSource1TomatoProduct, HUDocumentsModel.COLUMNNAME_QtyAllocated))
					.value();
			// allocatedQty = insertedQty
			assertThat(qtyExceeded, CoreMatchers.not(allocationResultInsert1));
			assertEquals("Invalid allocated quantity", editorResultInsert1, allocationResultInsert1);
		}

		final BigDecimal volumeEntered = new BigDecimal("230");
		final BigDecimal volumeExpected = new BigDecimal("230.00");
		// enter attribute volume value
		{
			final IHUTreeNode mixedPaletIFCOBlister = mixedPaletIFCOItem.getChildAt(0);
			window.table(EDITOR_TABLE_MATCHER)
					.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCOBlister, HUEditorModel.COLUMNNAME_M_HU_Item))
					.click();

			final AttributeSetContext context = getAttributeSetContext();
			final String mixedPaletIFCOBlisterVolumeInsert1 = window.table(context.getAttributeSetTableMatcher())
					.cellWriter(new VNumberCellWriter(window.robot))
					.cell(TableCellHelper.findAttributeCell(context.getAttributeSetTable(), context.getAttributeSetModel(), dataSource.attr_Volume.getName()))
					.enterValue(volumeEntered.toString())
					.value();
			assertEquals("Volume not " + volumeExpected, volumeExpected, new BigDecimal(mixedPaletIFCOBlisterVolumeInsert1));
		}

		// check propagated volume value
		{
			final IHUTreeNode mixedPaletIFCO = mixedPaletItem0.getChildAt(0);
			window.table(EDITOR_TABLE_MATCHER)
					.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCO, HUEditorModel.COLUMNNAME_M_HU_Item))
					.click();

			final AttributeSetContext context = getAttributeSetContext();
			final String mixedPaletIFCOVolumeInsert1 = window.table(context.getAttributeSetTableMatcher())
					.cellWriter(new VNumberCellWriter(window.robot))
					.cell(TableCellHelper.findAttributeCell(context.getAttributeSetTable(), context.getAttributeSetModel(), dataSource.attr_Volume.getName()))
					.value();
			assertEquals("Volume not " + volumeExpected, volumeExpected, new BigDecimal(mixedPaletIFCOVolumeInsert1));
		}

		undo();

		// check propagated volume value is ZERO after undo
		{
			final IHUTreeNode mixedPaletIFCO = mixedPaletItem0.getChildAt(0);
			window.table(EDITOR_TABLE_MATCHER)
					.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletIFCO, HUEditorModel.COLUMNNAME_M_HU_Item))
					.click();

			final AttributeSetContext context = getAttributeSetContext();
			final String mixedPaletIFCOVolumeInsert1 = window.table(context.getAttributeSetTableMatcher())
					.cellWriter(new VNumberCellWriter(window.robot))
					.cell(TableCellHelper.findAttributeCell(context.getAttributeSetTable(), context.getAttributeSetModel(), dataSource.attr_Volume.getName()))
					.value();
			assertEquals("Volume not " + BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(mixedPaletIFCOVolumeInsert1));
		}

		// create a blister in the second PI (this one also allows for it)
		final IHUTreeNode mixedPaletItem1 = rootEditor.getChildAt(0).getChildAt(1);
		window.table(EDITOR_TABLE_MATCHER)
				.cell(TableCellHelper.findCell(editorTreeTable, editorModel, mixedPaletItem1, HUEditorModel.COLUMNNAME_M_HU_Item))
				// .rightClick() // will already be called in showPopupMenu()
				.showPopupMenu()
				.menuItem("Add Blister")
				.click();

		undo();

		// Samples:
		// this.window.textBox("textToCopy").enterText("Some random text");
		// this.window.button("copyButton").click();
		// this.window.label("copiedText").requireText("Some random text");
	}

	private void undo()
	{
		window.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_Z).modifiers(InputEvent.CTRL_MASK));
	}

	private void redo()
	{
		window.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_Y).modifiers(InputEvent.CTRL_MASK));
	}

	private AttributeSetContext getAttributeSetContext()
	{
		return new AttributeSetContext(editorPanel);
	}

	private static class AttributeSetContext
	{
		private final JXTable attributeSetTable;
		private final HUAttributeSetModel attributeSetModel;

		private final GenericTypeMatcher<JXTable> attributeSetTableMatcher;

		public AttributeSetContext(final HUEditorPanel panel)
		{
			final HUAttributeSetEditor attributeSetEditor = panel.getAttributeSetEditor();
			attributeSetTable = attributeSetEditor.getTable();
			attributeSetModel = (HUAttributeSetModel)attributeSetEditor.getModel();
			attributeSetTableMatcher = new GenericTypeMatcher<JXTable>(JXTable.class)
			{
				@Override
				protected boolean isMatching(final JXTable component)
				{
					return Util.same(component, attributeSetTable);
				}
			};
		}

		public JXTable getAttributeSetTable()
		{
			return attributeSetTable;
		}

		public HUAttributeSetModel getAttributeSetModel()
		{
			return attributeSetModel;
		}

		public GenericTypeMatcher<JXTable> getAttributeSetTableMatcher()
		{
			return attributeSetTableMatcher;
		}
	}
}
