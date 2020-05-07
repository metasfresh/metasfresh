package de.metas.handlingunits.client.terminal.form;

import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;

import de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectFrame;
import de.metas.util.Check;

/**
 * Template for all Terminal POS forms.
 *
 * NOTE to developer:
 * <ul>
 * <li>This is the point from where you start when you want to create another Terminal POS
 * </ul>
 *
 */
public abstract class AbstractHUSelectForm<MT extends AbstractHUSelectModel> implements FormPanel
{
	private FormFrame frame;
	private AbstractHUSelectFrame<MT> framePanel;

	@Override
	public final void init(final int WindowNo, final FormFrame frame)
	{
		this.frame = frame;

		framePanel = createFramePanel(frame);
		Check.assumeNotNull(framePanel, "framePanel not null");
	}

	/**
	 * Creates and adds actual frame panel.
	 *
	 * To be overriden by extension classes.
	 *
	 * @param frame outer frame to decorate
	 * @return inner frame component
	 */
	protected abstract AbstractHUSelectFrame<MT> createFramePanel(final FormFrame frame);

	public final AbstractHUSelectFrame<MT> getFramePanel()
	{
		return framePanel;
	}

	@Override
	public final void dispose()
	{
		if (framePanel != null)
		{
			framePanel.dispose();
			framePanel = null;

		}

		if (frame != null)
		{
			frame.dispose();
			frame = null;
		}

	}

	public final void show()
	{
		if (frame == null)
		{
			// not initialized or already disposed
			return;
		}

		frame.setVisible(true);
	}

}
