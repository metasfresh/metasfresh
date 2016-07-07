package org.compiere.swing.autocomplete;

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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import com.jgoodies.looks.Options;

/**
 * Auto-complete support for a {@link JTextComponent}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public class JTextComponentAutoCompleter
{
	// services
	protected final transient Logger logger = LogManager.getLogger(getClass());

	// config:
	private static final int DEFAULT_PopupDelayMillis = 500;
	private static final int DEFAULT_MaxResults = 10;
	private int maxResults = 0; // to be set

	// constants
	private static final KeyStroke KEY_Enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

	// state
	private ResultItem currentItem = null;

	// UI
	private final JList<ResultItem> listBox = new JList<>();
	private final JTextComponent textBox;
	private final JPopupMenu popup = new JPopupMenu();

	private boolean popupTriggerEnabled = true;
	/** Triggers the popup showing after {@value #DEFAULT_PopupDelayMillis} millis */
	private final Timer popupTrigger = new Timer(DEFAULT_PopupDelayMillis, new ActionListener()
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			showPopupNow();
		}
	});

	/** Listens when popup is displayed/hidden */
	private final PopupMenuListener popupMenuListener = new PopupMenuListener()
	{
		@Override
		public void popupMenuWillBecomeVisible(final PopupMenuEvent e)
		{
		}

		@Override
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent e)
		{
			onPopupHiding();
		}

		@Override
		public void popupMenuCanceled(final PopupMenuEvent e)
		{
		}
	};

	private final MouseListener listBoxMouseListener = new MouseAdapter()
	{
		@Override
		public void mouseClicked(final java.awt.event.MouseEvent e)
		{
			final ResultItem selectedItem = listBox.getSelectedValue();
			acceptedListItem(selectedItem);
		};
	};

	private final DocumentListener textBoxDocumentListener = new DocumentListener()
	{
		@Override
		public void insertUpdate(final DocumentEvent e)
		{
			showPopupDelayed();
		}

		@Override
		public void removeUpdate(final DocumentEvent e)
		{
			showPopupDelayed();
		}

		@Override
		public void changedUpdate(final DocumentEvent e)
		{
		}
	};

	/**
	 * Action triggered when user presses Enter in text box, but the popup is not visible.
	 * 
	 * It will fire {@link #fireCurrentItemChanged(ResultItem, ResultItem)} if the current item is not null.
	 */
	private final Action textBoxEnterAction = new AbstractAction()
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			final ResultItem currentItem = getCurrentItem();

			// Force firing the change event
			// NOTE: in some cases the BL will decide to re-run an action for given item, when user presses enter
			if (currentItem != null)
			{
				fireCurrentItemChanged(currentItem, currentItem);
			}
		}
	};

	/** Action triggered to accept selected item of the popup's results list */
	private final Action popupAcceptItemAction = new AbstractAction()
	{
		private static final long serialVersionUID = -3950389799318995148L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			// Hide the popup
			popup.setVisible(false);

			// Item to accept: the selected item
			ResultItem itemToAccept = listBox.getSelectedValue();

			//
			// Case: user pressed Enter in text box but there is no selected item in the popup list
			// => search for first item that it's matching
			if (itemToAccept == null)
			{
				itemToAccept = findFirstMatchingItem();
			}

			// Ask the completer to accept found item
			if (itemToAccept != null)
			{
				acceptedListItem(itemToAccept);
			}
		}

		/** @return first matching item or null */
		private final ResultItem findFirstMatchingItem()
		{
			final String searchText = textBox.getText();
			for (int index = 0; index < resultsListModel.getSize(); index++)
			{
				final ResultItem resultItem = resultsListModel.getElementAt(index);

				// Ignore the "more..." marker
				if (resultItem == MORE_Marker)
				{
					continue;
				}

				if (itemMatches(resultItem, searchText, MatchScope.FindingFirstItemToAccept))
				{
					return resultItem;
				}
			}

			return null; // no matching item found
		}
	};

	/**
	 * Action fired to select next item from results list. If the popup is not visible, this action will display the popup first.
	 */
	private final Action popupSelectNextResultItemAction = new AbstractAction()
	{
		private static final long serialVersionUID = 8868536979000734628L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final JComponent tf = (JComponent)e.getSource();
			if (!tf.isEnabled())
			{
				return;
			}

			// If popup is not visible, show the popup first.
			if (!popup.isVisible())
			{
				showPopupNow();
				return;
			}

			selectNextPossibleValue();
		}
	};

	/** Action fired to select previous item from results list */
	private final Action popupSelectPreviousResultItemAction = new AbstractAction()
	{
		private static final long serialVersionUID = 2200136359410394434L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final JComponent tf = (JComponent)e.getSource();
			if (!tf.isEnabled())
			{
				return;
			}

			// If popup is not visible, this action shall not be executed.
			if (!popup.isVisible())
			{
				return;
			}

			selectPreviousPossibleValue();
		}
	};

	/** Action fired to hide the popup */
	private final Action popupHideAction = new AbstractAction()
	{
		private static final long serialVersionUID = -5683983067872135654L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			popup.setVisible(false);
		}
	};

	protected static final ResultItem MORE_Marker = new ResultItem()
	{

		@Override
		public String getText()
		{
			return "";
		}

		@Override
		public String toString()
		{
			// needed for DefaultCellRenderer
			return "...";
		};
	};

	private final class ResultsListModel extends AbstractListModel<ResultItem>
	{
		private static final long serialVersionUID = 1L;

		private ResultItemSource itemsSource = ResultItemSource.NULL;
		private List<ResultItem> resultItemsList = null;
		private boolean truncated = false;

		public void setSource(final ResultItemSource itemsSource)
		{
			this.itemsSource = itemsSource == null ? ResultItemSource.NULL : itemsSource;
			reset();
		}

		@Override
		public int getSize()
		{
			return resultItemsList == null ? 0 : resultItemsList.size();
		}

		public final boolean isEmpty()
		{
			return resultItemsList == null || resultItemsList.isEmpty();
		}

		public final boolean isTruncated()
		{
			return truncated;
		}

		@Override
		public ResultItem getElementAt(final int index)
		{
			return resultItemsList.get(index);
		}

		private final void reset()
		{
			this.truncated = false;

			final int size = getSize();
			this.resultItemsList = null;
			if (size > 0)
			{
				fireIntervalRemoved(this, 0, size);
			}
		}

		public void filter(final String searchText)
		{
			final int maxResults = getMaxResults();

			boolean truncated = false; // was the list of matching items truncated to maximum allowed results
			final List<ResultItem> resultItemsListNew = new ArrayList<>();

			for (final ResultItem item : itemsSource.query(searchText, maxResults))
			{
				if (itemMatches(item, searchText, MatchScope.Filtering))
				{
					if (maxResults > 0 && resultItemsListNew.size() >= maxResults)
					{
						resultItemsListNew.add(MORE_Marker);
						truncated = true;
						break;
					}

					resultItemsListNew.add(item);
				}
			}

			this.truncated = truncated;
			this.resultItemsList = resultItemsListNew;
			fireContentsChanged(this, 0, getSize() - 1);
		}
	}

	private final ResultsListModel resultsListModel = new ResultsListModel();

	public JTextComponentAutoCompleter(final JTextComponent comp)
	{
		super();

		Check.assumeNotNull(comp, "comp not null");
		textBox = comp;

		// Make sure the text is not automatically selected when the textbox got focus.
		// Reason: in some circumstances the text will be selected while the user is typing (because while typing there was some focus lost and then gain),
		// so basically when the user will type the next char, what he/she typed until now will be lost => very annoying and makes the component useless. 
		textBox.putClientProperty(Options.SELECT_ON_FOCUS_GAIN_CLIENT_KEY, Boolean.FALSE);

		textBox.getDocument().addDocumentListener(textBoxDocumentListener);
		textBox.registerKeyboardAction(textBoxEnterAction, KEY_Enter, JComponent.WHEN_FOCUSED);
		textBox.registerKeyboardAction(popupSelectNextResultItemAction, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_FOCUSED);
		textBox.registerKeyboardAction(popupSelectPreviousResultItemAction, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_FOCUSED);
		textBox.registerKeyboardAction(popupHideAction, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);

		listBox.setFocusable(false);
		listBox.addMouseListener(listBoxMouseListener);
		listBox.setRequestFocusEnabled(false);
		listBox.setDragEnabled(false);
		listBox.setTransferHandler(null);
		final ListCellRenderer<ResultItem> listBoxRenderer = createListCellRenderer();
		if (listBoxRenderer != null)
		{
			listBox.setCellRenderer(listBoxRenderer);
		}
		listBox.setModel(resultsListModel);

		final JScrollPane listBoxScroll = new JScrollPane(listBox);
		listBoxScroll.setBorder(BorderFactory.createEmptyBorder());
		listBoxScroll.getVerticalScrollBar().setFocusable(false);
		listBoxScroll.getHorizontalScrollBar().setFocusable(false);

		popup.setBorder(BorderFactory.createLineBorder(Color.black));
		popup.add(listBoxScroll);
		popup.addPopupMenuListener(popupMenuListener);

		setMaxResults(DEFAULT_MaxResults);
	}

	protected ListCellRenderer<ResultItem> createListCellRenderer()
	{
		return null; // i.e. use default
	}

	public void setSource(final ResultItemSource source)
	{
		resultsListModel.setSource(source);
	}

	public final void setMaxResults(final int maxResults)
	{
		if (this.maxResults == maxResults)
		{
			return;
		}

		Check.assume(maxResults > 0, "maxResults > 0");
		this.maxResults = maxResults;
	}

	public final int getMaxResults()
	{
		return maxResults;
	}

	private final void showPopupDelayed()
	{
		if (!popupTriggerEnabled)
		{
			return;
		}

		logger.trace("showPopupDelayed..");
		popupTrigger.setRepeats(false);
		popupTrigger.start();
	}

	private final void showPopupNow()
	{
		logger.trace("showPopup");
		popup.setVisible(false);

		if (textBox.isEnabled() && updateListData() && !resultsListModel.isEmpty())
		{
			//
			// Register listeners
			textBox.registerKeyboardAction(popupAcceptItemAction, KEY_Enter, JComponent.WHEN_FOCUSED);

			//
			// Make sure we see all items, without the need of scrolling.
			final int listBoxSize = resultsListModel.getSize();
			final int maxResults = getMaxResults() + 1; // +1 for more indicator
			listBox.setVisibleRowCount(listBoxSize < maxResults ? listBoxSize : maxResults);

			//
			// Show to popup right under the textbox
			popup.show(textBox, 0, textBox.getHeight());
		}
		else
		{
			popup.setVisible(false);
		}

		textBox.requestFocus();
	}

	private final void onPopupHiding()
	{
		textBox.registerKeyboardAction(textBoxEnterAction, KEY_Enter, JComponent.WHEN_FOCUSED);
	}

	/**
	 * Selects the next item in the list. It won't change the selection if the currently selected item is already the last item.
	 */
	private final void selectNextPossibleValue()
	{
		selectRelativeItem(+1);
	}

	/**
	 * Selects the previous item in the list. It won't change the selection if the currently selected item is already the first item.
	 */
	private final void selectPreviousPossibleValue()
	{
		selectRelativeItem(-1);
	}

	/**
	 * Selects next/previous item.
	 * 
	 * @param increment +1 for next, -1 for previous
	 */
	private final void selectRelativeItem(final int increment)
	{
		final int itemsCount = resultsListModel.getSize();
		if (itemsCount <= 0)
		{
			return;
		}

		Check.assume(increment == 1 || increment == -1, "Invalid increment: {}", increment);

		//
		// Initialize index to select
		int indexToSelect;
		final int selectedIndex = listBox.getSelectedIndex();
		if (selectedIndex >= 0)
		{
			indexToSelect = selectedIndex;
		}
		else
		{
			indexToSelect = 0;
		}

		//
		// Do maximum items count iterations and try to find an item which is selectable and select it.
		for (int iteration = 0; iteration < itemsCount; iteration++)
		{
			// Increment/Decrement the index to select
			indexToSelect += increment;
			
			// If we reached the beginning => go to the end
			if (indexToSelect < 0)
			{
				indexToSelect = itemsCount - 1;
			}
			// If we reached the end => go to the begining
			if (indexToSelect >= itemsCount)
			{
				indexToSelect = 0;
			}

			//
			// If our item is the Marker, skip it.
			if (resultsListModel.getElementAt(indexToSelect) == MORE_Marker)
			{
				// if we have only one item, for sure we have only the More marker in our list, so stop here
				// (this shall not happen)
				if (itemsCount == 1)
				{
					return;
				}
				continue;
			}

			//
			// We found a selectable item
			// => select it and stop here.
			listBox.setSelectedIndex(indexToSelect);
			listBox.ensureIndexIsVisible(indexToSelect);
			break;
		}
	}

	/** Matching scope */
	protected static enum MatchScope
	{
		/** Exact (Perfect) match */
		Exact,
		/** Exact match, but ignore the small differences */
		EqualsIgnoreCase,
		/**
		 * Match used to find out which is the first item that could match when user is pressing enter in search box but no current item defined or there is no perfect match. Usually this is the same
		 * as {@link #Filtering}.
		 */
		FindingFirstItemToAccept,
		/** Match used when filtering the results, to decide if the item is matching our search text */
		Filtering,
	};

	/**
	 * Check if given item it's matching the search string, given the matching scope.
	 * 
	 * @param item item to be matched
	 * @param searchText search string
	 * @param scope matching scope
	 * @return true if matching
	 */
	private final boolean itemMatches(final ResultItem item, final String searchText, final MatchScope scope)
	{
		if (Check.isEmpty(searchText, false))
		{
			return false;
		}

		if (item == null)
		{
			return false;
		}

		final String itemText = item.getText();
		if (itemText == null || itemText.isEmpty())
		{
			return false;
		}

		if (scope == MatchScope.Exact)
		{
			return itemText.compareTo(searchText) == 0;
		}
		else if (scope == MatchScope.EqualsIgnoreCase)
		{
			final String itemTextNorm = normalizeSearchString(itemText);
			final String searchTextNorm = normalizeSearchString(searchText);
			return itemTextNorm.compareTo(searchTextNorm) == 0;
		}
		else if (scope == MatchScope.FindingFirstItemToAccept)
		{
			return itemMatches_FindingFirstItemToAccept(itemText, searchText);
		}
		else if (scope == MatchScope.Filtering)
		{
			return itemMatches_Filtering(itemText, searchText);
		}
		else
		{
			throw new IllegalArgumentException("Unknown scope: " + scope);
		}
	}

	protected boolean itemMatches_Filtering(final String itemText, final String searchText)
	{
		return startsWithIgnoreCase(itemText, searchText);
	}

	protected boolean itemMatches_FindingFirstItemToAccept(final String itemText, final String searchText)
	{
		return itemMatches_Filtering(itemText, searchText);
	}

	/**
	 * Helper method to checks if the normalized str1 starts with normalized str2.
	 *
	 * @param str1
	 * @param str2
	 * @return true if str1 starts with str2
	 * @see #normalizeSearchString(String)
	 */
	protected boolean startsWithIgnoreCase(final String str1, final String str2)
	{
		final String str1Normalized = normalizeSearchString(str1);
		final String str2Normalized = normalizeSearchString(str2);
		return str1Normalized.startsWith(str2Normalized);
	}

	/** @return normalized string used for matching */
	protected final String normalizeSearchString(final String str)
	{
		return str == null ? "" : org.adempiere.util.StringUtils.stripDiacritics(str.toUpperCase()).trim();
	}

	public final void setTextNoPopup(final String text)
	{
		final boolean popupTriggerEnabledOld = this.popupTriggerEnabled;
		popupTriggerEnabled = false;
		try
		{
			textBox.setText(text);
		}
		finally
		{
			popupTriggerEnabled = popupTriggerEnabledOld;
		}
	}

	public final void setItemNoPopup(final ResultItem item)
	{
		acceptedListItem(item);
	}

	/**
	 * User has selected some item in the list. Update textfield accordingly...
	 *
	 * @param resultItem
	 */
	private final void acceptedListItem(final ResultItem resultItem)
	{
		final boolean popupTriggerEnabledOld = this.popupTriggerEnabled;
		popupTriggerEnabled = false; // don't trigger popup while we are setting back the values
		try
		{
			if (resultItem == null || resultItem == MORE_Marker)
			{
				setCurrentItem(null);
				return;
			}

			final String text = resultItem.getText();
			textBox.setText(text);

			setCurrentItem(resultItem);
		}
		finally
		{
			popupTriggerEnabled = popupTriggerEnabledOld;
		}
	}

	protected final ResultItem getCurrentItem()
	{
		return currentItem;
	}

	protected final void setCurrentItem(final ResultItem item)
	{
		final ResultItem currentItemOld = this.currentItem;
		this.currentItem = item;

		//
		// Set text box color
		{
			final boolean mandatory = false;
			final boolean hasText = !Check.isEmpty(textBox.getText());
			final boolean hasItem = item != null;
			final boolean error = hasText && !hasItem;
			if (error)
			{
				textBox.setBackground(AdempierePLAF.getFieldBackground_Error());
			}
			else if (!textBox.isEditable())
			{
				textBox.setBackground(AdempierePLAF.getFieldBackground_Inactive());
			}
			else if (mandatory)
			{
				textBox.setBackground(AdempierePLAF.getFieldBackground_Mandatory());
			}
			else
			{
				textBox.setBackground(AdempierePLAF.getFieldBackground_Normal());
			}
		}

		// Notify that current item was changed
		if (!Check.equals(currentItem, currentItemOld))
		{
			fireCurrentItemChanged(item, currentItemOld);
		}
	}

	/**
	 * Update list model depending on the data in text field
	 *
	 * @return true if popup has to be displayed after this update
	 */
	protected final boolean updateListData()
	{
		final String searchText = textBox.getText();

		//
		// Filter results list model
		resultsListModel.filter(searchText);

		//
		// If there is no item on the list return false, to not show the popup
		if (resultsListModel.isEmpty())
		{
			setCurrentItem(null); // reset the current item because we have no suggestions
			return false;
		}

		//
		// If first item of the resulting list is matching our search text,
		// then select it.
		{
			final ResultItem resultItem = resultsListModel.getElementAt(0);
			if (itemMatches(resultItem, searchText, MatchScope.EqualsIgnoreCase))
			{
				setCurrentItem(resultItem);
				return true;
			}
		}

		// if the list has only one item,
		// and that item equals with current item
		// return false to not show any popup
		{
			final ResultItem currentItem = getCurrentItem();
			if (!resultsListModel.isTruncated()
					&& resultsListModel.getSize() == 1
					&& currentItem != null
					&& currentItem.equals(resultsListModel.getElementAt(0)))
			{
				logger.trace("nothing to do 1");
				return false;
			}
		}

		//
		// If current item is not precisely matching our search text then reset the current item.
		// We will set it later in this method, if we found a match.
		{
			final ResultItem currentItem = getCurrentItem();
			if (currentItem != null)
			{
				if (!itemMatches(currentItem, searchText, MatchScope.Exact))
				{
					setCurrentItem(null);
				}
			}
		}

		return true;
	}
	
	private final void fireCurrentItemChanged(final ResultItem currentItem, final ResultItem currentItemOld)
	{
		try
		{
			onCurrentItemChanged(currentItem, currentItemOld);
		}
		catch (Exception e)
		{
			logger.warn("Failed while firing current item changed", e);
		}
	}

	protected void onCurrentItemChanged(final ResultItem currentItem, final ResultItem currentItemOld)
	{
		// nothing on this level
	}

}
