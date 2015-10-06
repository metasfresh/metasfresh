package de.metas.handlingunits.client.editor.allocation.view.swing.selectionmodel;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 * Inspired from <a href="http://www.coderanch.com/t/346552/GUI/java/Disable-Selection-JTable">http://www.coderanch.com/t/346552/GUI/java/Disable-Selection-JTable</a>.<br>
 * <br>
 * Used to prevent user's ability to select any element from a JTable.
 * 
 * @author al
 */
public final class NullSelectionModel implements ListSelectionModel
{
	public static final NullSelectionModel instance = new NullSelectionModel();

	private NullSelectionModel()
	{
		super();
	}

	@Override
	public boolean isSelectionEmpty()
	{
		return true;
	}

	@Override
	public boolean isSelectedIndex(final int index)
	{
		return false;
	}

	@Override
	public int getMinSelectionIndex()
	{
		return -1;
	}

	@Override
	public int getMaxSelectionIndex()
	{
		return -1;
	}

	@Override
	public int getLeadSelectionIndex()
	{
		return -1;
	}

	@Override
	public int getAnchorSelectionIndex()
	{
		return -1;
	}

	@Override
	public void setSelectionInterval(final int index0, final int index1)
	{
		// nothing
	}

	@Override
	public void setLeadSelectionIndex(final int index)
	{
		// nothing
	}

	@Override
	public void setAnchorSelectionIndex(final int index)
	{
		// nothing
	}

	@Override
	public void addSelectionInterval(final int index0, final int index1)
	{
		// nothing
	}

	@Override
	public void insertIndexInterval(final int index, final int length, final boolean before)
	{
		// nothing
	}

	@Override
	public void clearSelection()
	{
		// nothing
	}

	@Override
	public void removeSelectionInterval(final int index0, final int index1)
	{
		// nothing
	}

	@Override
	public void removeIndexInterval(final int index0, final int index1)
	{
		// nothing
	}

	@Override
	public void setSelectionMode(final int selectionMode)
	{
		// nothing
	}

	@Override
	public int getSelectionMode()
	{
		return ListSelectionModel.SINGLE_SELECTION;
	}

	@Override
	public void addListSelectionListener(final ListSelectionListener lsl)
	{
		// nothing
	}

	@Override
	public void removeListSelectionListener(final ListSelectionListener lsl)
	{
		// nothing
	}

	@Override
	public void setValueIsAdjusting(final boolean valueIsAdjusting)
	{
		// nothing
	}

	@Override
	public boolean getValueIsAdjusting() // NOPMD: method implementation from given interface
	{
		return false;
	}
}