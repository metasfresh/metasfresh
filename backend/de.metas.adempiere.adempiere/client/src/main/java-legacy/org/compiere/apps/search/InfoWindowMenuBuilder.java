package org.compiere.apps.search;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import de.metas.adempiere.form.IClientUI;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.images.Images;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

import javax.swing.*;
import java.util.Properties;

/**
 * Helper class used to create, add and handle the Info windows menu items.
 *
 * The standard way of using it is:
 * <ul>
 * <li>create the builder instance: {@link #newBuilder()}
 * <li>configure it, i.e. {@link #setMenu(JMenu)}, {@link #setCtx(Properties)} etc
 * <li>call {@link #build()} to create all info window menu items. They will be automatically handled.
 * </ul>
 *
 * @author tsa
 *
 */
public final class InfoWindowMenuBuilder
{
	/** @return new builder instance */
	public static final InfoWindowMenuBuilder newBuilder()
	{
		return new InfoWindowMenuBuilder();
	}

	// services
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	// parameters
	private JMenu _menu;
	private Properties _ctx;
	private Supplier<Integer> _parentWindowNoSupplier = Suppliers.ofInstance(Env.WINDOW_None);

	private boolean _built = false;

	private InfoWindowMenuBuilder()
	{
		super();
	}

	/** Creates, configures and handles all info window menu items */
	public void build()
	{
		markAsBuilt();

		final Properties ctx = getCtx();
		final JMenu menu = getMenu();
		// NOTE: avoid using getWindowNo() here because in some cases it might not be available

		//
		// Add all custom windows to menu
		for (final I_AD_InfoWindow infoWindow : Services.get(IADInfoWindowDAO.class).retrieveInfoWindowsInMenu(ctx))
		{
			final CMenuItem mi = new CMenuItem(infoWindow.getName(), Images.getImageIcon2(Info.DEFAULT_IconName));
			mi.setToolTipText(infoWindow.getDescription());
			menu.add(mi);
			mi.addActionListener(e -> {
				try
				{
					openGenericInfoWindow(infoWindow);
				}
				catch (final Exception ex)
				{
					clientUI.error(getParentWindowNo(), ex);
				}
			});
		}
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Info window menu items not already built");
	}

	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	/** Open generic info window */
	private final void openGenericInfoWindow(final I_AD_InfoWindow infoWindow)
	{
		InfoBuilder.newBuilder()
				.setParentFrame(getParentFrame())
				.setWindowNo(getParentWindowNo())
				.setInfoWindow(infoWindow)
				.buildAndShow();
	}

	public InfoWindowMenuBuilder setCtx(final Properties ctx)
	{
		assertNotBuilt();

		Check.assumeNotNull(ctx, "ctx not null");
		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		return _ctx == null ? Env.getCtx() : _ctx;
	}

	private final IUserRolePermissions getUserRolePermissions()
	{
		final Properties ctx = getCtx();
		final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
		return role;
	}

	public InfoWindowMenuBuilder setParentWindowNo(final int windowNo)
	{
		return setParentWindowNo(Suppliers.ofInstance(windowNo));
	}

	public InfoWindowMenuBuilder setParentWindowNo(final Supplier<Integer> windowNoSupplier)
	{
		Check.assumeNotNull(windowNoSupplier, "windowNoSupplier not null");
		_parentWindowNoSupplier = windowNoSupplier;
		return this;
	}

	private final int getParentWindowNo()
	{
		final Integer parentWindowNo = _parentWindowNoSupplier.get();
		return parentWindowNo == null ? Env.WINDOW_MAIN : parentWindowNo;
	}

	private final JFrame getParentFrame()
	{
		final int parentWindowNo = getParentWindowNo();

		// No windowNo => use main window
		if (parentWindowNo < 0 || parentWindowNo == Env.WINDOW_None)
		{
			return Env.getWindow(Env.WINDOW_MAIN);
		}

		// Use particular window
		final JFrame frame = Env.getWindow(parentWindowNo);
		if (frame != null)
		{
			return frame;
		}

		// Fallback to main window (shall not happen)
		return Env.getWindow(Env.WINDOW_MAIN);
	}

	/** Sets the menu where we shall add the info windows menu items */
	public InfoWindowMenuBuilder setMenu(final JMenu menu)
	{
		assertNotBuilt();

		Check.assumeNotNull(menu, "menu not null");
		_menu = menu;
		return this;
	}

	private final JMenu getMenu()
	{
		Check.assumeNotNull(_menu, "menu not null");
		return _menu;
	}
}
