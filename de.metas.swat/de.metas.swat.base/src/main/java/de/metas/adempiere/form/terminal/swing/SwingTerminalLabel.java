package de.metas.adempiere.form.terminal.swing;

import java.awt.Component;

import org.adempiere.util.Services;
import org.compiere.swing.CLabel;

import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.i18n.IMsgBL;

/* package */class SwingTerminalLabel implements ITerminalLabel, IComponentSwing
{
	private final ITerminalContext tc;
	private final CLabel label;

	private boolean disposed = false;

	/**
	 * @param tc
	 * @param label
	 * @param translate true if label needs to be translated (so it's not already translated)
	 */
	protected SwingTerminalLabel(final ITerminalContext tc, final String label, final boolean translate)
	{
		this.tc = tc;

		final String labelTrl = translate ? Services.get(IMsgBL.class).translate(tc.getCtx(), label) : label;
		this.label = new CLabel(labelTrl);

		// override the font size
		final float fontSize = tc.getDefaultFontSize();
		if (fontSize != 0)
		{
			this.label.setFont(this.label.getFont().deriveFont(fontSize));
		}

		tc.addToDisposableComponents(this);
	}

	@Override
	public Component getComponent()
	{
		return label;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public String getLabel()
	{
		return label.getText();
	}

	@Override
	public void setLabel(final String label)
	{
		this.label.setText(label);
	}

	@Override
	public void setFont(final float size)
	{
		label.setFont(label.getFont().deriveFont(size));
	}

	/**
	 * Does nothing, only sets our internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}
}
