/**
 *
 */
package de.metas.process.ui;

import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.grid.ed.VButton;
import org.compiere.model.I_AD_Process;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

/**
 * Additional record processes
 *
 * @author tsa
 *
 */
public class AProcess
{
	private final AProcessModel model = new AProcessModel();
	private final APanel parent;
	private final AppsAction action;

	/**
	 *
	 * @param parent
	 * @param small if <code>true</code> then use a small icon
	 * @return
	 */
	public static AppsAction createAppsAction(final APanel parent, final boolean small)
	{
		final AProcess app = new AProcess(parent, small);
		return app.action;
	}

	private AProcess(final APanel parent, final boolean small)
	{
		super();

		this.parent = parent;
		action = AppsAction.builder()
				.setAction(model.getActionName())
				.setSmallSize(small)
				.build();
		action.setDelegate(event -> showPopup());
	}

	private JPopupMenu getPopupMenu()
	{
		final List<I_AD_Process> processes = model.fetchProcesses(Env.getCtx(), parent.getCurrentTab());
		if (processes.isEmpty())
		{
			return null;
		}

		final JPopupMenu popup = new JPopupMenu("ProcessMenu");
		processes.stream()
				.map(process -> createProcessMenuItem(process))
				.sorted(Comparator.comparing(CMenuItem::getText))
				.forEach(mi -> popup.add(mi));

		return popup;
	}

	public void showPopup()
	{
		final JPopupMenu popup = getPopupMenu();
		if (popup == null)
		{
			return;
		}

		final AbstractButton button = action.getButton();
		if (button.isShowing())
		{
			popup.show(button, 0, button.getHeight()); // below button
		}
	}

	private CMenuItem createProcessMenuItem(final I_AD_Process process)
	{
		final CMenuItem mi = new CMenuItem(model.getDisplayName(process));
		mi.setIcon(model.getIcon(process));
		mi.setToolTipText(model.getDescription(process));
		mi.addActionListener(event -> startProcess(process));
		return mi;
	}

	private void startProcess(final I_AD_Process process)
	{
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);
		final VButton button = new VButton(
				"StartProcess",    // columnName,
				false,    // mandatory,
				false,    // isReadOnly,
				true,    // isUpdateable,
				processTrl.getName(),
				processTrl.getDescription(),
				processTrl.getHelp(),
				process.getAD_Process_ID());
		parent.actionButton(button);
	}
}
