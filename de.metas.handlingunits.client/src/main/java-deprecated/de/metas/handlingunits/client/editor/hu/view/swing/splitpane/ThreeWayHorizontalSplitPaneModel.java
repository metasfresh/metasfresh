package de.metas.handlingunits.client.editor.hu.view.swing.splitpane;

import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;

/**
 * Used <a href="http://stackoverflow.com/questions/6117826/jxmultisplitpane-how-to-use">this source</a> of inspiration...
 * 
 * @author al
 */
public class ThreeWayHorizontalSplitPaneModel extends Split
{
	public static final String LEFT = "LEFT";
	public static final String CENTER = "CENTER";
	public static final String RIGHT = "RIGHT";

	/**
	 * Create a three-way horizontal split model with no specific weight (0.0) between panes.
	 */
	public ThreeWayHorizontalSplitPaneModel()
	{
		this(false);
	}

	/**
	 * Create a three-way horizontal split model with default evenly distributed weight (0.33) between all 3 panes.
	 */
	public ThreeWayHorizontalSplitPaneModel(final boolean isEqualyWeighed)
	{
		super();

		setRowLayout(true);

		final Leaf left = new Leaf(ThreeWayHorizontalSplitPaneModel.LEFT);
		final Leaf center = new Leaf(ThreeWayHorizontalSplitPaneModel.CENTER);
		final Leaf right = new Leaf(ThreeWayHorizontalSplitPaneModel.RIGHT);
		if (isEqualyWeighed)
		{
			left.setWeight(0.33);
			center.setWeight(0.33);
			right.setWeight(0.33);
		}

		setChildren(left, new Divider(), center, new Divider(), right);
	}
}
