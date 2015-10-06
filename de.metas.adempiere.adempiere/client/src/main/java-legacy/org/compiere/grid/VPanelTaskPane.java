package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

import org.adempiere.plaf.AdempiereTaskPaneUI;
import org.adempiere.plaf.IUISubClassIDAware;
import org.jdesktop.swingx.JXTaskPane;

/**
 * A collapsible panel used to group the fields in a {@link VPanel}.
 * 
 * @author tsa
 *
 */
class VPanelTaskPane extends JXTaskPane implements IUISubClassIDAware
{
	private static final long serialVersionUID = 1054350358867570709L;

	public VPanelTaskPane()
	{
		super();
	}

	@Override
	public String getUISubClassID()
	{
		return AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FieldGroup;
	}

	public void setContentPanel(final JPanel contentPanel)
	{
		final Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(contentPanel, BorderLayout.CENTER);
	}

	public void setCollapsible(boolean collapsible)
	{
		if (!collapsible)
		{
			setCollapsed(false);
		}
	}
}
