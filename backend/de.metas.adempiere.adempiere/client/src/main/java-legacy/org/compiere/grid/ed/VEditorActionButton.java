package org.compiere.grid.ed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.adempiere.images.Images;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * The action button which is displayed on {@link VEditor}'s corner.
 * 
 * @author tsa
 *
 */
class VEditorActionButton extends JButton
{
	/**
	 * @param iconName icon name to be displayed, without size suffix and without extension suffix.
	 * @param textComponent
	 * @return editor's action button
	 */
	/* package */static VEditorActionButton build(final String iconName, final JComponent textComponent)
	{
		return new VEditorActionButton(iconName, textComponent);
	}

	private static final long serialVersionUID = -7658517666763337529L;

	private static final transient Logger logger = LogManager.getLogger(VEditorActionButton.class);

	private TextComponent2ButtonUISynchronizer textComponentRef = null;

	private VEditorActionButton(final String iconName, final JComponent textComponent)
	{
		super();
		setIconByIconName(iconName);
		setTextComponent(textComponent);

		//
		// update UI
		updateUICustomProperties();
	}

	/**
	 * @param iconName icon name to be displayed, without size suffix and without extension suffix.
	 */
	private void setIconByIconName(final String iconName)
	{
		//
		// Get and set the icon
		String iconNameAndSize = iconName + "16";
		ImageIcon icon = Images.getImageIcon2(iconNameAndSize);
		if (icon == null)
		{
			logger.debug("No image icon was found for {}", iconNameAndSize);
			iconNameAndSize = iconName + "10";
			icon = Images.getImageIcon2(iconNameAndSize);
		}
		setIcon(icon);
	}

	@Override
	public final void updateUI()
	{
		super.updateUI();
		updateUICustomProperties();
	}

	private final void updateUICustomProperties()
	{

		// Don't focus when tabbing
		// Also, in grid mode if button is focusable it is causing focus issues and it will trigger javax.swing.JTable.CellEditorRemover.propertyChange(PropertyChangeEvent).
		setFocusable(false);

		setMargin(new Insets(0, 0, 0, 0));
		setBorder(BorderFactory.createEmptyBorder());
		setOpaque(true);

		//
		// Update the button UI from text component if any
		if (textComponentRef != null)
		{
			textComponentRef.updateActionButtonUI();
		}
	}

	public void setReadWrite(final boolean readWrite)
	{
		if (isEnabled() != readWrite)
			setEnabled(readWrite);

		// Don't show button if not ReadWrite
		if (isVisible() != readWrite)
			setVisible(readWrite);
	}

	/**
	 * Temporary disable this button and return an {@link IAutoCloseable} to enable it back.
	 * 
	 * If the button was already disabled, this method does nothing.
	 * 
	 * @return {@link IAutoCloseable} to enable the button.
	 */
	public IAutoCloseable temporaryDisable()
	{
		final boolean enabledOld = isEnabled();
		if (!enabledOld)
		{
			return NullAutoCloseable.instance;
		}

		setEnabled(false);
		return new IAutoCloseable()
		{
			private volatile boolean closed = false;

			@Override
			public void close()
			{
				if (closed)
				{
					return;
				}
				this.closed = true;

				setEnabled(enabledOld);
			}
		};
	}

	public void setTextComponent(final JComponent textComponent)
	{
		if (textComponentRef != null)
		{
			textComponentRef.dispose();
			textComponentRef = null;
		}

		if (textComponent == null)
		{
			return;
		}

		textComponentRef = TextComponent2ButtonUISynchronizer.bind(this, textComponent);
	}

	private static final class TextComponent2ButtonUISynchronizer implements PropertyChangeListener
	{
		public static final TextComponent2ButtonUISynchronizer bind(final VEditorActionButton actionButton, final JComponent textComponent)
		{
			return new TextComponent2ButtonUISynchronizer(actionButton, textComponent);
		}

		private static final String PROPERTY_PreferredSize = "preferredSize";
		private static final String PROPERTY_Background = "background";

		private final WeakReference<VEditorActionButton> actionButtonRef;
		private final WeakReference<JComponent> textComponentRef;
		private boolean disposed = false;

		private TextComponent2ButtonUISynchronizer(final VEditorActionButton actionButton, final JComponent textComponent)
		{
			super();
			this.actionButtonRef = new WeakReference<>(actionButton);
			this.textComponentRef = new WeakReference<>(textComponent);

			//
			// Update the action button
			updateActionButtonUI();

			// Bind
			textComponent.addPropertyChangeListener(PROPERTY_PreferredSize, this);
			textComponent.addPropertyChangeListener(PROPERTY_Background, this);
		}

		public void dispose()
		{
			if (disposed)
			{
				return;
			}
			this.disposed = true;

			final JComponent textComponent = textComponentRef.get();
			if (textComponent != null)
			{
				textComponent.removePropertyChangeListener(PROPERTY_PreferredSize, this);
				textComponent.removePropertyChangeListener(PROPERTY_Background, this);
			}
			textComponentRef.clear();

			actionButtonRef.clear();
		}

		private final boolean isDisposed()
		{
			return disposed;
		}

		private VEditorActionButton getActionButton()
		{
			return actionButtonRef.get();
		}

		private JComponent getTextComponent()
		{
			return textComponentRef.get();
		}

		public void updateActionButtonUI()
		{
			if (isDisposed())
			{
				return;
			}

			final JComponent textComponent = getTextComponent();
			final VEditorActionButton actionButton = getActionButton();
			if (textComponent == null || actionButton == null)
			{
				dispose();
				return;
			}

			updateActionButtonUI_PreferredSize(actionButton, textComponent);
			updateActionButtonUI_Background(actionButton, textComponent);
		}

		private static void updateActionButtonUI_PreferredSize(final VEditorActionButton actionButton, final JComponent textComponent)
		{
			final Dimension textCompSize = textComponent.getPreferredSize();
			final int textCompHeight = textCompSize.height;

			final Dimension buttonSize = new Dimension(textCompHeight, textCompHeight);
			actionButton.setPreferredSize(buttonSize);
		}

		private static void updateActionButtonUI_Background(final VEditorActionButton actionButton, final JComponent textComponent)
		{
			final Color textCompBackground = textComponent.getBackground();
			actionButton.setBackground(textCompBackground);
		}

		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (isDisposed())
			{
				return;
			}

			final JComponent textComponent = (JComponent)evt.getSource();
			if (textComponent == null)
			{
				return; // shall not happen
			}

			final VEditorActionButton actionButton = getActionButton();
			if (actionButton == null)
			{
				dispose();
				return;
			}

			final String propertyName = evt.getPropertyName();
			if (PROPERTY_PreferredSize.equals(propertyName))
			{
				updateActionButtonUI_PreferredSize(actionButton, textComponent);
			}
			else if (PROPERTY_Background.equals(propertyName))
			{
				updateActionButtonUI_Background(actionButton, textComponent);
			}
		}
	}
}
