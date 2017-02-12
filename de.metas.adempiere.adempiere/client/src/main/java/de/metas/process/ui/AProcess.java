/**
 *
 */
package de.metas.process.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.grid.ed.VButton;
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

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private JPopupMenu getPopupMenu()
	{
		final Properties ctx = getCtx();
		final List<SwingRelatedProcessDescriptor> processes = model.fetchProcesses(ctx, parent.getCurrentTab());
		if (processes.isEmpty())
		{
			return null;
		}

		final String adLanguage = Env.getAD_Language(ctx);

		final JPopupMenu popup = new JPopupMenu("ProcessMenu");
		processes.stream()
				.map(process -> createProcessMenuItem(process, adLanguage))
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

	private CMenuItem createProcessMenuItem(final SwingRelatedProcessDescriptor process, final String adLanguage)
	{
		final CMenuItem mi = new CMenuItem(process.getCaption(adLanguage));
		mi.setIcon(process.getIcon());
		mi.setToolTipText(process.getDescription(adLanguage));
		
		if (process.isEnabled())
		{
			mi.setEnabled(true);
			mi.addActionListener(event -> startProcess(process));
		}
		else
		{
			mi.setEnabled(false);
			mi.setToolTipText(process.getDisabledReason(adLanguage));
		}
		
		return mi;
	}

	private void startProcess(final SwingRelatedProcessDescriptor process)
	{
		final String adLanguage = Env.getAD_Language(getCtx());

		final VButton button = new VButton(
				"StartProcess", // columnName,
				false,        // mandatory,
				false,        // isReadOnly,
				true,        // isUpdateable,
				process.getCaption(adLanguage),
				process.getDescription(adLanguage),
				process.getHelp(adLanguage),
				process.getAD_Process_ID());
		
		// Invoke action
		parent.actionButton(button);
	}
}
