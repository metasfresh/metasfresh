package org.adempiere.plaf;

/**
 * VPanel UI.
 * 
 * @author tsa
 *
 */
public class VPanelUI
{
	public static final String KEY_IncludedTabHeight = "VPanel.IncludedTab.Height";
	public static final int DEFAULT_IncludedTabHeight = 150;

	public static final String KEY_StandardWindow_FieldColumns = "VPanel.StandardWindow.FieldColumns";
	public static final int DEFAULT_StandardWindow_FieldColumns = 3;

	public static final String KEY_StandardWindow_LabelMinWidth = "VPanel.StandardWindow.LabelMinWidth";
	public static final int DEFAULT_StandardWindow_LabelMinWidth = 160;

	public static final String KEY_StandardWindow_LabelMaxWidth = "VPanel.StandardWindow.LabelMaxWidth";
	public static final int DEFAULT_StandardWindow_LabelMaxWidth = 160;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_StandardWindow_FieldColumns, DEFAULT_StandardWindow_FieldColumns
				, KEY_StandardWindow_LabelMinWidth, DEFAULT_StandardWindow_LabelMinWidth
				, KEY_StandardWindow_LabelMaxWidth, DEFAULT_StandardWindow_LabelMaxWidth
				, KEY_IncludedTabHeight, DEFAULT_IncludedTabHeight
		};
	}
}
