package org.adempiere.plaf;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.RowFilter;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.AEnv;
import org.compiere.swing.FontChooser;
import org.compiere.util.ValueNamePair;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.sort.TableSortController;

import com.google.common.collect.Ordering;

import de.metas.util.Check;
import de.metas.util.collections.IdentityHashSet;

/**
 * Dialog used to edit and persist {@link UIDefaults}.
 *
 * @author tsa
 *
 */
public class UIDefaultsEditorDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates and shows the {@link UIDefaultsEditorDialog}.
	 *
	 * @param parent parent component (used to get the parent frame)
	 */
	public static final void createAndShow(final Component parent)
	{
		final UIDefaultsEditorDialog frame = new UIDefaultsEditorDialog(parent);
		frame.setLookAndFeel(UIManager.getLookAndFeel());
		frame.pack();
		AEnv.showCenterScreen(frame);
	}

	//
	// UI
	private final JTextField textSearch = new JTextField();
	private final JXTable uiDefaultsTable = new JXTable();

	private UIDefaultsEditorDialog(final Component parent)
	{
		super(AEnv.getWindow(parent));
		setTitle("UI Editor");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		final Dimension dim = new Dimension(1000, 800);
		setSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		getContentPane().setSize(dim);

		final JPopupMenu tablePopup = new JPopupMenu();
		tablePopup.add(new AbstractAction("Show similar")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				setTextSearchFromCurrentRow(UIDefaultsTableModel.COLUMNINDEX_Value);
			}
		});
		tablePopup.add(new AbstractAction("Show same")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				setTextSearchFromCurrentRow(UIDefaultsTableModel.COLUMNINDEX_ID);
			}
		});

		textSearch.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				final String text = textSearch.getText();
				uiDefaultsTable.setRowFilter(FullTextSearchRowFilter.ofText(text));
			}
		});

		setLayout(new BorderLayout());

		//
		// Top
		{
			final JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(textSearch);
			add(panel, BorderLayout.NORTH);
		}

		// Center
		{
			uiDefaultsTable.setDefaultRenderer(Object.class, new UIDefaultsValueCellRenderer());
			uiDefaultsTable.setRowHeight(60);
			uiDefaultsTable.setRowSorter(new TableSortController<>());
			uiDefaultsTable.addMouseListener(new EditRowMouseListener());
			uiDefaultsTable.setComponentPopupMenu(tablePopup);
			uiDefaultsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			add(new JScrollPane(uiDefaultsTable), BorderLayout.CENTER);
		}

		//
		// Bottom
		{
			final JPanel bottomPanel = new JPanel();
			bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
			add(bottomPanel, BorderLayout.SOUTH);

			final JButton btnReapply = new JButton("Reapply");
			bottomPanel.add(btnReapply);
			btnReapply.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
					final ValueNamePair lookAndFeelVNP = ValueNamePair.of(lookAndFeel.getClass().getName(), lookAndFeel.getName());
					//
					final MetalTheme theme = MetalLookAndFeel.getCurrentTheme();
					final ValueNamePair themeVNP = ValueNamePair.of(theme.getClass().getName(), theme.getName());
					//
					AdempierePLAF.setPLAF(lookAndFeelVNP, themeVNP, false); // updateIni=false
					AEnv.updateUI();
				}
			});
		}
	}

	private final void setTextSearchFromCurrentRow(final int columnIndexModel)
	{
		final int rowIndexView = uiDefaultsTable.getSelectedRow();
		if (rowIndexView < 0)
		{
			return;
		}
		final int rowIndexModel = uiDefaultsTable.convertRowIndexToModel(rowIndexView);
		final UIDefaultsTableModel tableModel = (UIDefaultsTableModel)uiDefaultsTable.getModel();
		final Object value = tableModel.getValueAt(rowIndexModel, columnIndexModel);
		if (value == null)
		{
			return;
		}
		textSearch.setText(value.toString());
		textSearch.postActionEvent(); // fire action performed

	}

	public void setLookAndFeel(final LookAndFeel lookAndFeel)
	{
		Check.assumeNotNull(lookAndFeel, "lookAndFeel not null");

		setTitle("UI Editor: " + lookAndFeel.getName() + " (" + lookAndFeel.getClass() + ")");

		uiDefaultsTable.setModel(new UIDefaultsTableModel(lookAndFeel));
	}

	/**
	 * {@link UIDefaults} table model.
	 *
	 * @author tsa
	 *
	 */
	static class UIDefaultsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;

		private final LookAndFeel lookAndFeel;
		private final UIDefaults uiDefaults;
		private final List<Object> keys;

		public static final int COLUMNINDEX_Key = 0;
		public static final int COLUMNINDEX_Value = 1;
		public static final int COLUMNINDEX_ID = 2;

		public UIDefaultsTableModel(final LookAndFeel lookAndFeel)
		{
			super();
			this.lookAndFeel = lookAndFeel;
			uiDefaults = UIManager.getDefaults();

			keys = new ArrayList<>();
			for (final Map.Entry<Object, Object> e : uiDefaults.entrySet())
			{
				keys.add(e.getKey());
			}

			Collections.sort(keys, Ordering.usingToString());

		}

		@Override
		public int getRowCount()
		{
			return keys.size();
		}

		@Override
		public int getColumnCount()
		{
			return 3;
		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex)
		{
			if (COLUMNINDEX_Key == columnIndex)
			{
				return String.valueOf(keys.get(rowIndex));
			}
			else if (COLUMNINDEX_Value == columnIndex)
			{
				final Object key = keys.get(rowIndex);
				return uiDefaults.get(key);
			}
			else if (COLUMNINDEX_ID == columnIndex)
			{
				final Object key = keys.get(rowIndex);
				final Object value = uiDefaults.get(key);
				return value == null ? null : System.identityHashCode(value);
			}
			else
			{
				return null;
			}
		}

		@Override
		public boolean isCellEditable(final int rowIndex, final int columnIndex)
		{
			// NOTE: not supported because we don't have cell editors defined. We will set the values directly to model.
			// if (columnIndex == COLUMNINDEX_Value)
			// {
			// return true;
			// }
			return false;
		}

		@Override
		public void setValueAt(final Object valueNew, final int rowIndex, final int columnIndex)
		{
			if (columnIndex == COLUMNINDEX_Value)
			{
				final Object key = keys.get(rowIndex);
				uiDefaults.put(key, valueNew);
				fireTableCellUpdated(rowIndex, columnIndex);

				//
				// Save value to sysconfigs repository:
				SysConfigUIDefaultsRepository
						.ofLookAndFeelId(lookAndFeel.getID())
						.setValue(key, valueNew);
			}
		}

		@Override
		public String getColumnName(final int columnIndex)
		{
			if (COLUMNINDEX_Key == columnIndex)
			{
				return "Key";
			}
			else if (COLUMNINDEX_Value == columnIndex)
			{
				return "Value";
			}
			else if (COLUMNINDEX_ID == columnIndex)
			{
				return "ID";
			}
			else
			{
				return null;
			}
		}

		public Set<Object> getKeysWithSameValue(final Object value, final int excludeRowIndex)
		{
			final Set<Object> foundKeys = new LinkedHashSet<>();
			if (value == null)
			{
				return foundKeys;
			}

			final int rowCount = getRowCount();
			for (int row = 0; row < rowCount; row++)
			{
				if (row == excludeRowIndex)
				{
					continue;
				}

				final Object key = keys.get(row);
				final Object rowValue = uiDefaults.get(key);
				if (Check.equals(value, rowValue))
				{
					foundKeys.add(key);
				}
			}

			return foundKeys;
		}

		public void setValue(final Object key, final Object value)
		{
			final int row = keys.indexOf(key);
			if (row < 0)
			{
				return;
			}

			setValueAt(value, row, COLUMNINDEX_Value);
		}
	}

	static class UIDefaultsValueCellRenderer implements TableCellRenderer
	{
		private final TableCellRenderer colorRenderer = new ColorUIResourceCellRenderer();
		private final FontUIResourceCellRenderer fontRenderer = new FontUIResourceCellRenderer();
		private final BorderUIResourceCellRenderer borderRenderer = new BorderUIResourceCellRenderer();
		private final IconUIResourceCellRenderer iconRenderer = new IconUIResourceCellRenderer();
		private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
		{
			final TableCellRenderer renderer;
			if (value instanceof java.awt.Color)
			{
				renderer = colorRenderer;
			}
			else if (value instanceof java.awt.Font)
			{
				renderer = fontRenderer;
			}
			else if (value instanceof Border)
			{
				renderer = borderRenderer;
			}
			else if (value instanceof Icon)
			{
				renderer = iconRenderer;
			}
			else
			{
				renderer = defaultRenderer;
			}
			//
			return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	static class ColorUIResourceCellRenderer implements TableCellRenderer
	{
		private final JLabel comp;

		public ColorUIResourceCellRenderer()
		{
			super();

			comp = new JLabel();
			final Dimension dim = new Dimension(100, 50);
			comp.setMinimumSize(dim);
			comp.setMaximumSize(dim);
			comp.setPreferredSize(dim);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
		{
			final java.awt.Color color;
			final String text;
			if (value instanceof java.awt.Color)
			{
				color = (java.awt.Color)value;
				text = String.valueOf(color);
			}
			else
			{
				color = Color.GRAY;
				text = "Uknown color: " + color;
			}

			comp.setBackground(color);
			comp.setOpaque(true);
			comp.setForeground(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
			comp.setText(text);
			return comp;
		}
	}

	static class FontUIResourceCellRenderer implements TableCellRenderer
	{
		private final JLabel comp;

		public FontUIResourceCellRenderer()
		{
			super();

			comp = new JLabel();
			comp.setText("unknown font");
			comp.setForeground(Color.BLACK);
			comp.setBackground(Color.WHITE);

			final Dimension dim = new Dimension(100, 50);
			comp.setMinimumSize(dim);
			comp.setMaximumSize(dim);
			comp.setPreferredSize(dim);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
		{
			final java.awt.Font font;
			if (value instanceof java.awt.Font)
			{
				font = (java.awt.Font)value;
			}
			else
			{
				font = null;
			}

			comp.setText("Font: " + font);
			comp.setFont(font);
			return comp;
		}
	}

	static class BorderUIResourceCellRenderer implements TableCellRenderer
	{
		private final JButton button;

		private final IdentityHashSet<Object> notSupportedValues = new IdentityHashSet<>();

		public BorderUIResourceCellRenderer()
		{
			super();
			button = new JButton();
			button.setForeground(Color.BLACK);
			button.setBackground(Color.WHITE);
			final Dimension dim = new Dimension(100, 50);
			button.setMinimumSize(dim);
			button.setMaximumSize(dim);
			button.setPreferredSize(dim);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
		{
			final String text;
			final String tooltip;
			final Border border;
			if (value == null)
			{
				border = null;
				text = "null border";
				tooltip = null;
			}
			else if (notSupportedValues.contains(value))
			{
				text = "not supported: " + value.toString();
				tooltip = null;
				border = null;
			}
			else if (value instanceof Border)
			{
				border = (Border)value;
				text = border.toString();
				tooltip = ObjectUtils.toString(border);
			}
			else
			{
				text = "unknown: " + value;
				tooltip = null;
				border = null;
			}

			try
			{
				button.setText(text);
				button.setToolTipText(tooltip);
				button.setBorder(border == null ? null : new SafeBorderUIResource(border));
			}
			catch (final Exception e)
			{
				button.setBorder(null);
				button.setText("Error displaying: " + value);
				button.setToolTipText(null);
				// e.printStackTrace();
				notSupportedValues.add(value);
			}

			return button;
		}

		class SafeBorderUIResource extends BorderUIResource
		{
			private static final long serialVersionUID = 1L;
			private final Border delegate;

			public SafeBorderUIResource(final Border delegate)
			{
				super(delegate);
				this.delegate = delegate;
			}

			@Override
			public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height)
			{
				try
				{
					super.paintBorder(c, g, x, y, width, height);
				}
				catch (final Exception e)
				{
					notSupportedValues.add(delegate);
				}
			}

		}
	}

	static class IconUIResourceCellRenderer implements TableCellRenderer
	{
		private final JLabel comp;

		private final IdentityHashSet<Object> notSupportedValues = new IdentityHashSet<>();

		public IconUIResourceCellRenderer()
		{
			super();

			comp = new JLabel();
			comp.setText("");
			comp.setForeground(Color.BLACK);
			comp.setBackground(Color.WHITE);

			final Dimension dim = new Dimension(100, 50);
			comp.setMinimumSize(dim);
			comp.setMaximumSize(dim);
			comp.setPreferredSize(dim);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
		{
			final Icon icon;
			final String text;
			if (value == null)
			{
				icon = null;
				text = "null icon";
			}
			else if (value instanceof Icon)
			{
				icon = (Icon)value;
				text = "Icon: " + icon;
			}
			else if (notSupportedValues.contains(value))
			{
				icon = null;
				text = "Not supported: " + icon;
			}
			else
			{
				icon = null;
				text = "Unknown: " + icon;
			}

			try
			{
				comp.setText(text);
				comp.setIcon(icon == null ? null : new SafeIconUIResource(icon));
			}
			catch (final Exception e)
			{
				comp.setText("Not supported: " + icon);
				comp.setIcon(null);
				notSupportedValues.add(value);
			}
			return comp;
		}

		class SafeIconUIResource extends IconUIResource
		{
			private static final long serialVersionUID = 1L;
			private final Icon delegate;

			public SafeIconUIResource(final Icon delegate)
			{
				super(delegate);
				this.delegate = delegate;
			}

			@Override
			public void paintIcon(final Component c, final Graphics g, final int x, final int y)
			{
				try
				{
					super.paintIcon(c, g, x, y);
				}
				catch (final Exception e)
				{
					notSupportedValues.add(delegate);
				}
			}

		}
	}

	/**
	 * <ul>
	 * <li>select row on mouse click (any click)
	 * <li>Start editing current row (if possible), on double clicking it.
	 * </ul>
	 *
	 * @author tsa
	 *
	 */
	public static class EditRowMouseListener extends MouseAdapter
	{
		@Override
		public void mousePressed(final MouseEvent me)
		{
			final JTable table = (JTable)me.getSource();
			final Point p = me.getPoint();
			final int rowIndexView = table.rowAtPoint(p);

			// Make sure current row is selected, no matter what mouse button we press
			table.getSelectionModel().setSelectionInterval(rowIndexView, rowIndexView);

			// Edit row on double-click.
			if (me.getClickCount() >= 2)
			{
				editRow(table, rowIndexView);
			}
		}

		private void editRow(final JTable table, final int rowIndexView)
		{
			final Object value = getValue(table, rowIndexView);
			if (value == null)
			{
				return;
			}

			if (value instanceof Color)
			{
				final Color color = (Color)value;
				editColor(table, rowIndexView, color);
			}
			else if (value instanceof Font)
			{
				final Font font = (Font)value;
				editFont(table, rowIndexView, font);
			}
			else if (value instanceof VEditorDialogButtonAlign)
			{
				final VEditorDialogButtonAlign buttonAlign = (VEditorDialogButtonAlign)value;
				editVEditorDialogButtonAlign(table, rowIndexView, buttonAlign);
			}
			else if (value instanceof Integer)
			{
				final Integer valueInt = (Integer)value;
				editInteger(table, rowIndexView, valueInt);
			}
			else if (value instanceof Boolean)
			{
				final Boolean valueBoolean = (Boolean)value;
				editBoolean(table, rowIndexView, valueBoolean);
			}
		}

		private void editColor(final JTable table, final int rowIndexView, final Color color)
		{
			final String key = getKey(table, rowIndexView);
			Color colorNew = JColorChooser.showDialog(table, "Editing: " + key, color);
			if (colorNew == null || colorNew.equals(color))
			{
				return;
			}

			if (!(colorNew instanceof ColorUIResource))
			{
				colorNew = new ColorUIResource(colorNew);
			}
			if (colorNew.equals(color))
			{
				return;
			}

			setValue(table, rowIndexView, colorNew, color);
		}

		private void editFont(final JTable table, final int rowIndexView, final Font font)
		{
			final Dialog owner = AEnv.getDialog(table);
			final String key = getKey(table, rowIndexView);
			Font fontNew = FontChooser.showDialog(owner, "Editing: " + key, font);

			if (fontNew == null || fontNew.equals(font))
			{
				return;
			}

			if (!(fontNew instanceof FontUIResource))
			{
				fontNew = new FontUIResource(fontNew);
			}
			if (fontNew.equals(font))
			{
				return;
			}

			setValue(table, rowIndexView, fontNew, font);

		}

		private void editVEditorDialogButtonAlign(JTable table, int rowIndexView, VEditorDialogButtonAlign buttonAlign)
		{
			final Dialog owner = AEnv.getDialog(table);
			final String key = getKey(table, rowIndexView);
			final String title = "Editing: "+key;
			final String message = "";

			final Object[] options = VEditorDialogButtonAlign.values();

			final int optionIndex = JOptionPane.showOptionDialog(owner,
					message,
					title,	// title
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, // icon
					options,
					buttonAlign);

			if (optionIndex == JOptionPane.CLOSED_OPTION)
			{
				return;
			}

			final VEditorDialogButtonAlign buttonAlignNew = (VEditorDialogButtonAlign)options[optionIndex];
			if (Check.equals(buttonAlign, buttonAlignNew))
			{
				return; // nothing changed
			}

			setValue(table, rowIndexView, buttonAlignNew, buttonAlign);
		}

		private void editInteger(final JTable table, final int rowIndexView, final Integer valueInt)
		{
			final Dialog owner = AEnv.getDialog(table);
			final String key = getKey(table, rowIndexView);
			final String title = "Editing: "+key;

			final String valueStrNew = JOptionPane.showInputDialog(owner, title, valueInt);
			if(Check.isEmpty(valueStrNew, true))
			{
				return; // cancel
			}

			final Integer valueIntNew = Integer.parseInt(valueStrNew.trim());

			if (Check.equals(valueInt, valueIntNew))
			{
				return; // nothing changed
			}

			setValue(table, rowIndexView, valueIntNew, valueInt);
		}

		private void editBoolean(final JTable table, final int rowIndexView, Boolean valueBoolean)
		{
			final Dialog owner = AEnv.getDialog(table);
			final String key = getKey(table, rowIndexView);
			final String title = "Editing: "+key;

			if (valueBoolean == null)
			{
				valueBoolean = false;
			}

			final String valueStrNew = JOptionPane.showInputDialog(owner, title, valueBoolean.toString());
			if(Check.isEmpty(valueStrNew, true))
			{
				return; // cancel
			}

			final Boolean valueBooleanNew = Boolean.parseBoolean(valueStrNew);

			if (Check.equals(valueBoolean, valueBooleanNew))
			{
				return; // nothing changed
			}

			setValue(table, rowIndexView, valueBooleanNew, valueBoolean);
		}



		private final String getKey(final JTable table, final int rowIndexView)
		{
			final Object value = table.getValueAt(rowIndexView, table.convertColumnIndexToView(UIDefaultsTableModel.COLUMNINDEX_Key));
			return String.valueOf(value);
		}

		private Object getValue(final JTable table, final int rowIndexView)
		{
			final int columnIndexView_Value = table.convertColumnIndexToView(UIDefaultsTableModel.COLUMNINDEX_Value);
			final Object value = table.getValueAt(rowIndexView, columnIndexView_Value);
			return value;
		}

		private final void setValue(final JTable table, final int rowIndexView, final Object value, final Object valueOld)
		{
			final UIDefaultsTableModel tableModel = (UIDefaultsTableModel)table.getModel();
			final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
			final Set<Object> similarKeysToUpdate = getSimilarKeysToUpdate(table, valueOld, rowIndexModel);

			tableModel.setValueAt(value, rowIndexModel, UIDefaultsTableModel.COLUMNINDEX_Value);
			for (final Object key : similarKeysToUpdate)
			{
				tableModel.setValue(key, value);
			}
		}

		private Set<Object> getSimilarKeysToUpdate(final JTable table, final Object valueOld, final int rowIndexModel)
		{
			final UIDefaultsTableModel tableModel = (UIDefaultsTableModel)table.getModel();
			final Set<Object> keysWithSameValue = tableModel.getKeysWithSameValue(valueOld, rowIndexModel);
			if (keysWithSameValue.isEmpty())
			{
				return Collections.emptySet();
			}

			final JXList list = new JXList(keysWithSameValue.toArray());
			list.setVisibleRowCount(20);
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			final Object[] params = new Object[] {
					"Following keys have the same value. Do you want to change them too?",
					new JScrollPane(list) };

			final int answer = JOptionPane.showConfirmDialog(
					table, // parentComponent
					params, // message
					"Change similar keys?", // title
					JOptionPane.YES_NO_OPTION // messageType
					);
			if (answer != JOptionPane.YES_OPTION)
			{
				return Collections.emptySet();
			}

			final Set<Object> keysToUpdate = new LinkedHashSet<>(Arrays.asList(list.getSelectedValues()));
			return keysToUpdate;
		}
	}

	/**
	 * {@link RowFilter} which includes only rows which contains a given <code>text</code>.
	 *
	 * @author tsa
	 *
	 */
	static class FullTextSearchRowFilter extends RowFilter<TableModel, Integer>
	{
		public static FullTextSearchRowFilter ofText(final String text)
		{
			if (Check.isEmpty(text, true))
			{
				return null;
			}
			else
			{
				return new FullTextSearchRowFilter(text);
			}
		}

		private final String textUC;

		private FullTextSearchRowFilter(final String text)
		{
			super();
			textUC = text.toUpperCase();
		}

		@Override
		public boolean include(final javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> entry)
		{
			for (int i = entry.getValueCount() - 1; i >= 0; i--)
			{
				String entryValue = entry.getStringValue(i);
				if (entryValue == null || entryValue.isEmpty())
				{
					continue;
				}

				entryValue = entryValue.toUpperCase();

				if (entryValue.indexOf(textUC) >= 0)
				{
					return true;
				}
			}

			return false;
		}

	}

}
