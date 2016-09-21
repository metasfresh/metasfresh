/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Mutable Tree Node (not a PO).
 *
 * @author Jorg Janke
 * @version $Id: MTreeNode.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public final class MTreeNode extends DefaultMutableTreeNode
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6046137020835035816L;

	/**
	 * Construct Model TreeNode
	 *
	 * @param node_ID node
	 * @param seqNo sequence
	 * @param name name
	 * @param description description
	 * @param parent_ID parent
	 * @param isSummary summary
	 * @param imageIndicator image indicator
	 * @param onBar on bar
	 * @param color color
	 */
	public MTreeNode(
			final int node_ID,
			final int seqNo,
			final String name,
			final String description,
			final int parent_ID,
			final boolean isSummary,
			final String imageIndicator,
			final boolean onBar,
			final Color color)
	{
		super();
		// log.debug( "MTreeNode Node_ID=" + node_ID + ", Parent_ID=" + parent_ID + " - " + name);
		m_node_ID = node_ID;
		m_seqNo = seqNo;
		m_name = name;
		m_description = description;
		if (m_description == null)
		{
			m_description = "";
		}
		m_parent_ID = parent_ID;
		setSummary(isSummary);
		setImageIndicator(imageIndicator);
		m_onBar = onBar;
		m_color = color;
	}   // MTreeNode

	/** Node ID */
	private final int m_node_ID;
	/** SeqNo */
	private int m_seqNo;
	/** Name */
	private String m_name;
	/** Description */
	private String m_description;
	/** Parent ID */
	private int m_parent_ID;
	/** Summary */
	private boolean m_isSummary;
	/** Image Indicator */
	private String m_imageIndicator;
	/** Index to Icon */
	private int m_imageIndex = 0;
	/** On Bar */
	private boolean m_onBar;
	/** Color */
	private Color m_color;
	/** AD_Tree_ID */
	private int m_AD_Tree_ID = -1;
	/** Table */
	private int m_AD_Table_ID = -1;

	/** Logger */
	private static Logger log = LogManager.getLogger(MTreeNode.class);

	/*************************************************************************/

	/** Window - 1 */
	public static int TYPE_WINDOW = 1;
	/** Report - 2 */
	public static int TYPE_REPORT = 2;
	/** Process - 3 */
	public static int TYPE_PROCESS = 3;
	/** Workflow - 4 */
	public static int TYPE_WORKFLOW = 4;
	/** Workbench - 5 */
	public static int TYPE_WORKBENCH = 5;
	/** Variable - 6 */
	public static int TYPE_SETVARIABLE = 6;
	/** Choice - 7 */
	public static int TYPE_USERCHOICE = 7;
	/** Action - 8 */
	public static int TYPE_DOCACTION = 8;

	/** 16* 16 Icons */
	private static String[] IMAGES = new String[]
			{
		null,
		"mWindow",
		"mReport",
		"mProcess",
		"mWorkFlow",
		"mWorkbench",
		"mSetVariable",
		"mUserChoice",
		"mDocAction"
			};

	/**************************************************************************
	 * Get Node ID
	 *
	 * @return node id (e.g. AD_Menu_ID)
	 */
	public int getNode_ID()
	{
		return m_node_ID;
	}   // getID

	/**
	 * Set Name
	 *
	 * @param name name
	 */
	public void setName(final String name)
	{
		if (name == null)
		{
			m_name = "";
		}
		else
		{
			m_name = name;
		}
	}   // setName

	/**
	 * Get Name
	 *
	 * @return name
	 */
	public String getName()
	{
		return m_name;
	}   // setName

	/**
	 * Get SeqNo (Index) as formatted String 0000 for sorting
	 *
	 * @return SeqNo as String
	 */
	public String getSeqNo()
	{
		String retValue = "0000" + m_seqNo;	// not more than 100,000 nodes
		if (m_seqNo > 99999)
		{
			log.error("MTreeNode.getIndex - TreeNode Index is higher than 99999");
		}
		if (retValue.length() > 5)
		{
			retValue = retValue.substring(retValue.length() - 5);	// last 5
		}
		return retValue;
	}	// getSeqNo

	public void setSeqNo(final int seqNo)
	{
		m_seqNo = seqNo;
	}

	/**
	 * Return parent
	 *
	 * @return Parent_ID (e.g. AD_Menu_ID)
	 */
	public int getParent_ID()
	{
		return m_parent_ID;
	}	// getParent

	public void setParent_ID(final int parent_ID)
	{
		m_parent_ID = parent_ID;
	}

	/**
	 * Print Name
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		// !! don't change for improved debugging etc !!
		// the toString() return value will be shown in the swing tree
		return // m_node_ID + "/" + m_parent_ID + " " + m_seqNo + " - " +
				m_name;
	}   // toString

	/**
	 * Get Description
	 *
	 * @return description
	 */
	public String getDescription()
	{
		return m_description;
	}	// getDescription

	/**************************************************************************
	 * Set Summary (allow children)
	 *
	 * @param isSummary summary node
	 */
	public void setSummary(final boolean isSummary)
	{
		m_isSummary = isSummary;
		super.setAllowsChildren(isSummary);
	}   // setSummary

	/**
	 * Set Summary (allow children)
	 *
	 * @param isSummary true if summary
	 */
	@Override
	public void setAllowsChildren(final boolean isSummary)
	{
		m_isSummary = isSummary;
		super.setAllowsChildren(isSummary);
	}   // setAllowsChildren

	/**
	 * Allow children to be added to this node
	 *
	 * @return true if summary node
	 */
	public boolean isSummary()
	{
		return m_isSummary;
	}   // isSummary

	/**************************************************************************
	 * Get Image Indicator/Index
	 *
	 * @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 * @return index of image
	 */
	public static int getImageIndex(final String imageIndicator)
	{
		int imageIndex = 0;
		if (imageIndicator == null)
		{
			;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_UserWindow)		// Window
				|| imageIndicator.equals(X_AD_WF_Node.ACTION_UserForm))
		{
			imageIndex = TYPE_WINDOW;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_AppsReport))
		{
			imageIndex = TYPE_REPORT;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_AppsProcess)		// Process
				|| imageIndicator.equals(X_AD_WF_Node.ACTION_AppsTask))
		{
			imageIndex = TYPE_PROCESS;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_SubWorkflow))
		{
			imageIndex = TYPE_WORKFLOW;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_SetVariable))
		{
			imageIndex = TYPE_SETVARIABLE;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_UserChoice))
		{
			imageIndex = TYPE_USERCHOICE;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_DocumentAction))
		{
			imageIndex = TYPE_DOCACTION;
		}
		else if (imageIndicator.equals(X_AD_WF_Node.ACTION_WaitSleep))
		{
			;
		}
		return imageIndex;
	}   // getImageIndex

	/**
	 * Set Image Indicator and Index
	 *
	 * @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 */
	public void setImageIndicator(final String imageIndicator)
	{
		if (imageIndicator != null)
		{
			m_imageIndicator = imageIndicator;
			m_imageIndex = getImageIndex(m_imageIndicator);
		}
	}   // setImageIndicator

	/**
	 * Get Image Indicator
	 *
	 * @return image indicator
	 */
	public String getImageIndiactor()
	{
		return m_imageIndicator;
	}   // getImageIndiactor

	/**
	 * Get Image Icon
	 *
	 * @param index image index
	 * @return Icon
	 */
	public static String getIconName(final int index)
	{
		if (index == 0 || IMAGES == null || index > IMAGES.length)
		{
			return null;
		}
		return IMAGES[index];
	}	// getIcon

	public static final boolean isValidIconType(final int type)
	{
		return type > 0 && type < IMAGES.length;
	}

	/**
	 * Get Image Icon
	 *
	 * @return Icon
	 */
	public String getIconName()
	{
		return getIconName(m_imageIndex);
	}	// getIcon

	/**
	 * Get Shortcut Bar info
	 *
	 * @return true if node on bar
	 */
	public boolean isOnBar()
	{
		return m_onBar;
	}   // isOnBar

	/**
	 * Is Process
	 *
	 * @return true if Process
	 */
	public boolean isProcess()
	{
		return X_AD_Menu.ACTION_Process.equals(m_imageIndicator);
	}	// isProcess

	/**
	 * Is Report
	 *
	 * @return true if report
	 */
	public boolean isReport()
	{
		return X_AD_Menu.ACTION_Report.equals(m_imageIndicator);
	}	// isReport

	/**
	 * Is Window
	 *
	 * @return true if Window
	 */
	public boolean isWindow()
	{
		return X_AD_Menu.ACTION_Window.equals(m_imageIndicator);
	}	// isWindow

	/**
	 * Is Workbench
	 *
	 * @return true if Workbench
	 */
	public boolean isWorkbench()
	{
		return X_AD_Menu.ACTION_Workbench.equals(m_imageIndicator);
	}	// isWorkbench

	/**
	 * Is Workflow
	 *
	 * @return true if Workflow
	 */
	public boolean isWorkFlow()
	{
		return X_AD_Menu.ACTION_WorkFlow.equals(m_imageIndicator);
	}	// isWorkFlow

	/**
	 * Is Form
	 *
	 * @return true if Form
	 */
	public boolean isForm()
	{
		return X_AD_Menu.ACTION_Form.equals(m_imageIndicator);
	}	// isForm

	/**
	 * Is Task
	 *
	 * @return true if Task
	 */
	public boolean isTask()
	{
		return X_AD_Menu.ACTION_Task.equals(m_imageIndicator);
	}	// isTask

	/**
	 * Get Color
	 *
	 * @return color or black if not set
	 */
	public Color getColor()
	{
		if (m_color != null)
		{
			return m_color;
		}
		return Color.black;
	}	// getColor

	/*************************************************************************/

	/** Last found ID */
	private int m_lastID = -1;
	/** Last found Node */
	private MTreeNode m_lastNode = null;

	/**
	 * Return the Node with ID in list of children
	 *
	 * @param ID id
	 * @return VTreeNode with ID or null
	 */
	public MTreeNode findNode(final int ID)
	{
		if (m_node_ID == ID)
		{
			return this;
		}
		//
		if (ID == m_lastID && m_lastNode != null)
		{
			return m_lastNode;
		}
		//
		final Enumeration<?> en = preorderEnumeration();
		while (en.hasMoreElements())
		{
			final MTreeNode nd = (MTreeNode)en.nextElement();
			if (ID == nd.getNode_ID())
			{
				m_lastID = ID;
				m_lastNode = nd;
				return nd;
			}
		}
		return null;
	}   // findNode

	public MTreeNode getChildById(final int id)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			final MTreeNode n = (MTreeNode)getChildAt(i);
			if (n.getNode_ID() == id)
			{
				return n;
			}
		}
		return null;
	}

	/**
	 * @return children list; please note that this is a clone of actual children list, so any changes made to this list are not effective to original children structure
	 */
	public List<MTreeNode> getChildrenList()
	{
		final List<MTreeNode> children = new ArrayList<MTreeNode>();
		final Enumeration<?> en = children();
		while (en.hasMoreElements())
		{
			children.add((MTreeNode)en.nextElement());
		}
		return children;
	}

	public void setAD_Table_ID(final int AD_Table_ID)
	{
		m_AD_Table_ID = AD_Table_ID;
	}

	public int getAD_Table_ID()
	{
		return m_AD_Table_ID;
	}

	public void setAD_Tree_ID(final int AD_Tree_ID)
	{
		m_AD_Tree_ID = AD_Tree_ID;
	}

	public int getAD_Tree_ID()
	{
		return m_AD_Tree_ID;
	}

	public void setOnBar(final boolean onBar)
	{
		m_onBar = onBar;
	}

	// metas: begin
	private final List<MTreeNode> childrenAll = new ArrayList<MTreeNode>();
	private boolean m_isDisplayed = true;

	public void setDisplayed(final boolean displayed)
	{
		final boolean changed = m_isDisplayed != displayed;
		m_isDisplayed = displayed;
		if (changed)
		{
			final MTreeNode parentNode = (MTreeNode)getParent();
			if (parentNode != null)
			{
				parentNode.rebuildChildrenList();
			}
		}
	}

	public boolean isDisplayed()
	{
		return m_isDisplayed;
	}

	@Override
	public void insert(final MutableTreeNode newChild, final int childIndex)
	{
		super.insert(newChild, childIndex);

		int countBefore = 0;
		for (int i = 0; i < childIndex; i++)
		{
			final MTreeNode c = (MTreeNode)children.get(i);
			if (c.isPlaceholder())
			{
				countBefore += c.getPlaceholderNodes().size();
			}
			else
			{
				countBefore++;
			}
		}

		final MTreeNode tn = (MTreeNode)newChild;
		if (tn.isPlaceholder())
		{
			for (final MTreeNode n : tn.getPlaceholderNodes())
			{
				childrenAll.add(countBefore++, n);
				log.debug("Added " + n + " from placeholder " + newChild);
			}
		}
		else
		{
			childrenAll.add(countBefore, (MTreeNode)newChild);
			log.debug("Added " + newChild);
		}
	}

	@Override
	public void remove(final int childIndex)
	{
		final MTreeNode child = (MTreeNode)getChildAt(childIndex);
		super.remove(childIndex);
		if (child != null)
		{
			// removing a placeholder node:
			if (child.isPlaceholder())
			{
				for (final MTreeNode n : child.getPlaceholderNodes())
				{
					childrenAll.remove(n);
				}
				return;
			}

			// removing a normal node
			if (childrenAll.remove(child))
			{
				return;
			}
			for (final MTreeNode n : childrenAll)
			{
				if (n.isPlaceholder())
				{
					if (n.getPlaceholderNodes().remove(child))
					{
						return;
					}
				}
			}
		}
		log.warn("Child node " + child + " not found in all children array");
	}

	@SuppressWarnings("unchecked")
	protected void rebuildChildrenList()
	{
		children.clear();
		MTreeNode lastAdded = null;
		for (int i = 0; i < childrenAll.size(); i++)
		{
			final MTreeNode child = childrenAll.get(i);
			if (child.isDisplayed())
			{
				children.add(child);
				child.setSeqNo(i);
				child.setParent(this);
				lastAdded = child;
			}
			else
			{
				MTreeNode ph;
				if (lastAdded != null && lastAdded.isPlaceholder())
				{
					ph = lastAdded;
				}
				else
				{
					ph = createPlaceholder(i);
					children.add(ph);
				}
				final List<MTreeNode> phNodes = ph.getPlaceholderNodes();
				phNodes.add(child);
				final String itemsText = phNodes.size() > 1 ? "items" : "item"; // TODO: translate
				ph.setName("(" + phNodes.size() + " " + itemsText + ")");
				lastAdded = ph;
			}
		}
		log.debug("" + this + " children #" + children.size() + ", all #" + childrenAll.size());
	}

	public List<MTreeNode> getChildrenAll()
	{
		return Collections.unmodifiableList(childrenAll);
	}

	private MTreeNode createPlaceholder(final int seqNo)
	{
		final MTreeNode ph = new MTreeNode(
				-1, // node_ID,
				seqNo,
				"...", // name,
				"", // description,
				getNode_ID(), // parent_ID,
				false, // isSummary,
				null, // imageIndicator,
				false, // onBar,
				null // color
				);
		ph.setIsPlaceholder(true);
		ph.setParent(this);
		return ph;
	}

	private boolean m_isPlaceholder = false;
	private List<MTreeNode> m_placeholderNodes = null;

	public boolean isPlaceholder()
	{
		return m_isPlaceholder;
	}

	private void setIsPlaceholder(final boolean isPlaceholder)
	{
		m_isPlaceholder = isPlaceholder;
		if (m_placeholderNodes == null)
		{
			m_placeholderNodes = new ArrayList<MTreeNode>();
		}
	}

	public List<MTreeNode> getPlaceholderNodes()
	{
		return m_placeholderNodes;
	}

	public boolean isEmpty()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			final MTreeNode c = (MTreeNode)getChildAt(i);
			if (!c.isPlaceholder())
			{
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @return <code>true</code> if at least one of this node's children is not displayed. If the given node has no children at all, then the method returns the result of {@link #isDisplayed()}.
	 *
	 * @see #isDisplayed()
	 */
	public boolean isFiltered()
	{
		boolean noChildDisplayed = true;
		for (final MTreeNode n : childrenAll)
		{
			if (n.isDisplayed())
			{
				noChildDisplayed = false; // at least one child is displayed
				break;
			}
		}
		return noChildDisplayed;
	}

	public void setColor(final Color color)
	{
		m_color = color;
	}

	public Color getBackgroundColor()
	{
		return m_colorBg;
	}

	public void setBackgroundColor(final Color color)
	{
		m_colorBg = color;
	}

	private Color m_colorBg = null;

	public void copyInfoFrom(final MTreeNode node)
	{
		// note: we use the setters (where available), because they can contain further BL (see setSummary()).
		setName(node.getName());
		m_description = node.getDescription();
		setImageIndicator(node.getImageIndiactor());
		setSummary(node.isSummary());
		setColor(node.getColor());
		setBackgroundColor(node.getBackgroundColor());
		setInternalName(node.getInternalName());
	}

	// metas: end

	private String internalName;

	public String getInternalName()
	{
		if (internalName == null)
		{
			return Integer.toString(getNode_ID()); // fallback to avoid NPE.
		}
		return internalName;
	}

	public void setInternalName(final String internalName)
	{
		this.internalName = internalName;
	}

	private int AD_Window_ID = -1;
	private int AD_Process_ID = -1;
	private int AD_Form_ID = -1;
	private int AD_Workflow_ID = -1;
	private int AD_Task_ID = -1;
	private boolean isCreateNewRecord = false;
	private String webuiNameBrowse;
	private String webuiNameNew;
	private String webuiNameNewBreadcrumb;

	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	public void setAD_Window_ID(int aD_Window_ID)
	{
		AD_Window_ID = aD_Window_ID;
	}

	public int getAD_Process_ID()
	{
		return AD_Process_ID;
	}

	public void setAD_Process_ID(int aD_Process_ID)
	{
		AD_Process_ID = aD_Process_ID;
	}

	public int getAD_Form_ID()
	{
		return AD_Form_ID;
	}

	public void setAD_Form_ID(int aD_Form_ID)
	{
		AD_Form_ID = aD_Form_ID;
	}

	public int getAD_Workflow_ID()
	{
		return AD_Workflow_ID;
	}

	public void setAD_Workflow_ID(int aD_Workflow_ID)
	{
		AD_Workflow_ID = aD_Workflow_ID;
	}

	public int getAD_Task_ID()
	{
		return AD_Task_ID;
	}

	public void setAD_Task_ID(int aD_Task_ID)
	{
		AD_Task_ID = aD_Task_ID;
	}

	public void setIsCreateNewRecord(final boolean isCreateNewRecord)
	{
		this.isCreateNewRecord = isCreateNewRecord;
	}
	
	public boolean isCreateNewRecord()
	{
		return isCreateNewRecord;
	}
	
	public void setWEBUI_NameBrowse (final String webuiNameBrowse)
	{
		this.webuiNameBrowse = webuiNameBrowse;
	}

	public String getWEBUI_NameBrowse() 
	{
		return webuiNameBrowse;
	}
	
	public void setWEBUI_NameNew (final String webuiNameNew)
	{
		this.webuiNameNew = webuiNameNew;
	}

	public String getWEBUI_NameNew() 
	{
		return webuiNameNew;
	}

	public void setWEBUI_NameNewBreadcrumb (final String webuiNameNewBreadcrumb)
	{
		this.webuiNameNewBreadcrumb = webuiNameNewBreadcrumb;
	}

	public String getWEBUI_NameNewBreadcrumb() 
	{
		return webuiNameNewBreadcrumb;
	}


}   // MTreeNode
