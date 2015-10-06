package de.metas.handlingunits.client.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.adempiere.util.Check;
import org.adempiere.util.collections.IdentityHashSet;
import org.adempiere.util.collections.Predicate;
import org.compiere.util.CLogger;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;
import org.jdesktop.swingx.tree.TreeModelSupport;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.tree.node.ITreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public abstract class AbstractHUTreeTableModel<T extends ITreeNode<T>> extends AbstractTreeTableModel
{
	protected final CLogger logger = CLogger.getCLogger(getClass());

	private final List<ITreeTableColumn<T>> columns = new ArrayList<ITreeTableColumn<T>>();
	private final List<ITreeTableColumn<T>> columnsRO = Collections.unmodifiableList(this.columns);
	private int hierarchicalColumnIndex = -1;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private class NodeColumn
	{
		private final T node;
		private final int column;

		public NodeColumn(final T node, final int column)
		{
			this.node = node;
			this.column = column;
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(column)
					// .append(node) // don't add the node because is mutable
					.toHashcode();
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			final NodeColumn other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}

			return new EqualsBuilder()
					.append(this.column, other.column)
					.appendByRef(this.node, other.node) // append by reference because node is mutable
					.isEqual();
		}

	}

	private TreeSelectionModel selectionModel;

	public AbstractHUTreeTableModel()
	{
		super();
	}
	
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	
	protected void addColumnAdapter(final ITreeTableColumn<T> column)
	{
		this.columns.add(column);
	}

	public List<ITreeTableColumn<T>> getColumnAdapters()
	{
		return this.columnsRO;
	}

	@Override
	public int getHierarchicalColumn()
	{
		if (hierarchicalColumnIndex >= 0)
		{
			return hierarchicalColumnIndex;
		}
		return super.getHierarchicalColumn();
	}

	protected void setHierarchicalColumn(final ITreeTableColumn<T> column)
	{
		final int index = this.columns.indexOf(column);
		if (index < 0)
		{
			throw new IllegalArgumentException("Column " + column + " not found");
		}

		this.hierarchicalColumnIndex = index;
	}

	public void setSelectionModel(final TreeSelectionModel treeSelectionModel)
	{
		this.selectionModel = treeSelectionModel;
	}

	public TreeSelectionModel getSelectionModel()
	{
		return this.selectionModel;
	}

	@Override
	public int getColumnCount()
	{
		return this.columns.size();
	}

	@Override
	public Object getValueAt(final Object nodeObj, final int column)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final T node = (T)nodeObj;
			return this.columns.get(column).getValue(node);
		}
		catch (Exception e)
		{
			logger.log(Level.WARNING, "Error while getting value for column=" + column + ", node=" + nodeObj + ". Assuming NULL.", e);
			return null;
		}
	}

	@Override
	public Object getChild(final Object parent, final int index)
	{
		@SuppressWarnings("unchecked")
		final T node = (T)parent;
		return node.getChildren().get(index);
	}

	@Override
	public int getChildCount(final Object parent)
	{
		@SuppressWarnings("unchecked")
		final T node = (T)parent;
		return node.getChildren().size();
	}

	@Override
	public int getIndexOfChild(final Object parentObj, final Object childObj)
	{
		@SuppressWarnings("unchecked")
		final T parent = (T)parentObj;
		return parent.getChildren().indexOf(childObj);
	}

	@Override
	public String getColumnName(final int column)
	{
		return this.columns.get(column).getColumnHeader();
	}

	@Override
	public Class<?> getColumnClass(int column)
	{
		return this.columns.get(column).getColumnType();
	}

	public boolean isCellVisible(final Object nodeObj, final int column)
	{
		@SuppressWarnings("unchecked")
		final T node = (T)nodeObj;
		return this.columns.get(column).isVisible(node);
	}

	@Override
	public boolean isCellEditable(final Object nodeObj, final int column)
	{
		// If cell is not visible, of-course is not editable
		if (!isCellVisible(nodeObj, column))
		{
			return false;
		}

		@SuppressWarnings("unchecked")
		final T node = (T)nodeObj;
		return this.columns.get(column).isEditable(node);
	}

	public List<Object> getAvailableValuesList(final Object nodeObj, final int column)
	{
		@SuppressWarnings("unchecked")
		final T node = (T)nodeObj;
		return this.columns.get(column).getAvailableValuesList(node);
	}

	private final List<NodeColumn> setValueTrace = new ArrayList<NodeColumn>();

	@Override
	public void setValueAt(final Object value, final Object nodeObj, final int column)
	{
		@SuppressWarnings("unchecked")
		final T node = (T)nodeObj;

		final NodeColumn nc = new NodeColumn(node, column);
		if (setValueTrace.contains(nc))
		{
			// value was already set, avoid recursions
			return;
		}

		try
		{
			setValueTrace.add(nc);
			this.columns.get(column).setValue(node, value);
		}
		finally
		{
			setValueTrace.remove(nc);
		}
	}

	/**
	 * @param column
	 * @param value
	 * @return display name
	 */
	public String getDisplayName(final int column, final Object value)
	{
		return this.columns.get(column).getDisplayName(value);
	}

	/**
	 * @see #insertNodeInto(IHUTreeNode, IHUTreeNode, int)
	 * 
	 * @param newChild
	 * @param parent
	 */
	public void addNode(final T newChild, final T parent)
	{
		insertNodeInto(newChild, parent, parent.getChildren().size());
	}

	/**
	 * Invoked this to insert newChild at location index in parents children.
	 * 
	 * This will then message nodesWereInserted to create the appropriate event. This is the preferred way to add children as it will create the appropriate event.
	 * 
	 * @param newChild
	 * @param parent
	 * @param index
	 */
	public void insertNodeInto(final T newChild, final T parent, final int index)
	{
		parent.insertChild(newChild, index);
		treeNodeInserted(newChild, parent, index);

		modelSupport.fireChildAdded(new TreePath(getPathToRoot(parent)), index, newChild);

		// trigger event to expand and select node after it was added
		modelSupport.fireTreeStructureChanged(new TreePath(getPathToRoot(newChild)));
	}

	/**
	 * Called by {@link #insertNodeInto(ITreeNode, ITreeNode, int)} after node was inserted but before any events were fired.
	 * 
	 * This is a good place for extending classes to plug-in custom code.
	 * 
	 * @param newChild
	 * @param parent
	 * @param index
	 */
	protected void treeNodeInserted(T newChild, T parent, int index)
	{
		// nothing at this level
	}

	/**
	 * Message this to remove node from its parent.<br />
	 * This will message nodesWereRemoved to create the appropriate event.<br />
	 * It is the preferred way to remove a node, as it handles the event creation for you.
	 */
	public void removeNodeFromParent(final T node)
	{
		final T parent = node.getParent();

		if (parent == null)
		{
			throw new IllegalArgumentException("Node does not have a parent.");
		}

		final int index = parent.getChildIndex(node);
		parent.removeChild(node);

		modelSupport.fireChildRemoved(new TreePath(getPathToRoot(parent)), index, node);
	}

	public void removeChildren(final T node)
	{
		if (node == null)
		{
			return;
		}

		final List<T> children = node.getChildren();
		if (children == null || children.isEmpty())
		{
			return;
		}

		for (final T child : new ArrayList<T>(children))
		{
			removeNodeFromParent(child);
		}
	}

	/**
	 * Gets the path from the root to the specified node.
	 * 
	 * @param aNode
	 * @return {@link ITreeNode<T> }[]
	 */
	public ITreeNode<T>[] getPathToRoot(final ITreeNode<T> aNode)
	{
		if (aNode == null)
		{
			throw new IllegalArgumentException("aNode is null");
		}
		if (root == null)
		{
			throw new IllegalStateException("Tree has no root");
		}

		final List<ITreeNode<T>> path = new ArrayList<ITreeNode<T>>();
		ITreeNode<T> node = aNode;

		while (node != null && node != root)
		{
			path.add(0, node);

			node = node.getParent();
		}

		if (node == null)
		{
			throw new IllegalArgumentException("Node " + aNode + " does not have the root " + root);
		}

		if (node == root)
		{
			path.add(0, node);
		}

		@SuppressWarnings("unchecked")
		final ITreeNode<T>[] pathArr = path.toArray(new ITreeNode[path.size()]);
		return pathArr;
	}

	/**
	 * Note: Called by API. Never call directly!
	 * 
	 * @return {@link TreeModelSupport} modelSupport
	 */
	public TreeModelSupport getModelSupport()
	{
		return modelSupport;
	}

	public void setRoot(final T root)
	{
		this.root = root;
		modelSupport.fireNewRoot();
	}

	@Override
	public T getRoot()
	{
		@SuppressWarnings("unchecked")
		final T root = (T)super.getRoot();
		return root;
	}

	/**
	 * Navigate tree in pre-order (i.e. visit children, visit node).
	 * 
	 * @param node
	 * @param predicate
	 * @return
	 */
	public List<T> navigatePreOrder(final T node, final Predicate<T> predicate)
	{
		final IdentityHashSet<T> trace = new IdentityHashSet<T>();
		return navigatePreOrder(node, predicate, trace);
	}

	public List<T> navigatePreOrder(final T node, final Predicate<T> predicate, final IdentityHashSet<T> trace)
	{
		if (!trace.add(node))
		{
			// node was already iterated
			throw new IllegalStateException("Recursion detected: " + trace);
		}

		final List<T> result = new ArrayList<T>();

		final boolean accept = predicate == null || predicate.evaluate(node);
		if (accept)
		{
			result.add(node);
		}

		final List<T> children = node.getChildren();
		if (children != null)
		{
			for (final T child : children)
			{
				final List<T> childResult = navigatePreOrder(child, predicate);
				result.addAll(childResult);
			}
		}

		return result;
	}

	/**
	 * Navigate tree in post-order (i.e. visit children, visit node).
	 * 
	 * @param node
	 * @param predicate
	 * @return
	 */
	public List<T> navigatePostOrder(final T node, final Predicate<T> predicate)
	{
		final List<T> result = new ArrayList<T>();

		final List<T> children = node.getChildren();
		if (children != null)
		{
			for (final T child : children)
			{
				final List<T> childResult = navigatePostOrder(child, predicate);
				result.addAll(childResult);
			}
		}

		final boolean accept = predicate == null || predicate.evaluate(node);
		if (accept)
		{
			result.add(node);
		}

		return result;
	}

	/**
	 * Notify tree that model was changed (refresh)
	 * 
	 * @param node
	 */
	public void fireNodeChanged(final T node)
	{
		if (node == null)
		{
			return;
		}

		final T parent = node.getParent();
		if (parent == null)
		{
			return;
		}

		this.getModelSupport().fireChildChanged(
				new TreePath(getPathToRoot(parent)),
				getIndexOfChild(parent, node),
				node);

		// Do it recursively to the top
		// ... because in most of the cases, if a node changes also it's parent node changes
		fireNodeChanged(parent);
	}

	/**
	 * Refresh current selection
	 */
	public void refreshCurrentSelection()
	{
		Check.assumeNotNull(selectionModel, "selectionModel not null");

		final TreePath[] currentSelection = selectionModel.getSelectionPaths();
		selectionModel.clearSelection();
		selectionModel.addSelectionPaths(currentSelection);
	}

}
