package org.compiere.grid;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.adempiere.util.Check;
import org.compiere.model.FieldGroupVO;
import org.compiere.model.FieldGroupVO.FieldGroupType;

/**
 * {@link VPanelFieldGroup} factory.
 * 
 * @author tsa
 *
 */
final class VPanelFieldGroupFactory
{
	private VPanelLayoutFactory layoutFactory;

	public final VPanelFieldGroup newEmptyPanelForIncludedTab(final int adTabId)
	{
		final boolean createCollapsibleContainer = true;
		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, createCollapsibleContainer);
		panel.setFieldGroupName("IncludedTab#" + adTabId);
		panel.setTitle(""); // to be set later
		panel.setCollapsible(true);
		panel.setCollapsed(false);
		panel.setIncludedTab(true);
		return panel;
	}

	public final VPanelFieldGroup newCollapsibleFieldGroupPanel(final FieldGroupVO fieldGroup)
	{
		final boolean createCollapsibleContainer = fieldGroup != FieldGroupVO.NULL;
		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, createCollapsibleContainer);
		panel.setFieldGroupName(fieldGroup.getFieldGroupName());
		panel.setCollapsed(fieldGroup.isCollapsedByDefault());
		panel.setCollapsible(fieldGroup.getFieldGroupType() == FieldGroupType.Collapsible);
		
		// Set content pane border.
		// top/bottom=0 because it seems the JXTaskPane is already adding those
		panel.getContentPane().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // top, left, bottom, right

		return panel;
	}

	public final VPanelFieldGroup newHorizontalFieldGroupPanel(final String fieldGroupName)
	{
		final JPanel contentPanel = layoutFactory.createFieldsPanel();
		contentPanel.setLayout(layoutFactory.createFieldsPanelLayout(false));
		contentPanel.setName(fieldGroupName);

		final VPanelLayoutCallback layoutCallback = layoutFactory.createFieldsPanelLayoutCallback();
		VPanelLayoutCallback.setup(contentPanel, layoutCallback);

		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, contentPanel);
		panel.setFieldGroupName(fieldGroupName);
		return panel;
	}
	
	public VPanelFieldGroupFactory setLayoutFactory(final VPanelLayoutFactory layoutFactory)
	{
		Check.assumeNotNull(layoutFactory, "layoutFactory not null");
		this.layoutFactory = layoutFactory;
		return this;
	}
}
