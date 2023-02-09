package org.compiere.apps.search;

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IADInfoWindowBL;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.util.Env;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Info window (see {@link Info}) builder.
 * 
 * @author tsa
 *
 */
public class InfoBuilder
{
	public static InfoBuilder newBuilder()
	{
		return new InfoBuilder();
	}

	@Deprecated
	public static Info create(final Frame frame, final boolean modal, final int WindowNo,
			final String tableName, final String keyColumn, final String value,
			final boolean multiSelection, final String whereClause)
	{
		return newBuilder()
				.setParentFrame(frame)
				.setModal(modal)
				.setWindowNo(WindowNo)
				.setTableName(tableName)
				.setKeyColumn(keyColumn)
				.setSearchValue(value)
				.setMultiSelection(multiSelection)
				.setWhereClause(whereClause)
				.build();
	}

	@Deprecated
	public static Info create(final Frame frame, final boolean modal, final int WindowNo,
			final String tableName, final String keyColumn, final String value,
			final boolean multiSelection, final String whereClause, final Map<String, Object> attributes)
	{
		return newBuilder()
				.setParentFrame(frame)
				.setModal(modal)
				.setWindowNo(WindowNo)
				.setTableName(tableName)
				.setKeyColumn(keyColumn)
				.setSearchValue(value)
				.setMultiSelection(multiSelection)
				.setWhereClause(whereClause)
				.setAttributes(attributes)
				.build();
	}

	@Deprecated
	public static Info create(final int windowNo, final I_AD_InfoWindow infoWindow)
	{
		final JFrame parentFrame = Env.getWindow(windowNo);
		return newBuilder()
				.setParentFrame(parentFrame)
				.setWindowNo(windowNo)
				.setInfoWindow(infoWindow)
				.build();
	}

	@Deprecated
	public static Info create(final JFrame parentFrame, final int windowNo, final I_AD_InfoWindow infoWindow)
	{
		return newBuilder()
				.setParentFrame(parentFrame)
				.setWindowNo(windowNo)
				.setInfoWindow(infoWindow)
				.build();
	}

	//
	//
	// --------------------------------------------------------------------------------------------------------------------
	//
	//

	private Frame _parentFrame;
	private Image _iconImage;
	private Boolean _modal;
	private Integer _windowNo;
	private String _tableName;
	private String _keyColumn;
	private String _searchValue = "";
	private boolean _multiSelection = false;
	private String _whereClause = "";
	private Map<String, Object> _attributes = null;

	private I_AD_InfoWindow _infoWindowDef;

	private InfoBuilder()
	{
		super();
	}

	public final Info build()
	{
		final Info info;

		final I_AD_InfoWindow infoWindow = getInfoWindow();
		if (infoWindow != null)
		{
			info = newGenericInfoWindow(infoWindow);
		}
		else
		{
			info = newStandardInfoWindow();
		}

		AEnv.positionCenterWindow(getParentFrame(), info.getWindow());
		return info;
	}

	public final Info buildAndShow()
	{
		final Info info = build();
		info.showWindow();
		return info;
	}

	private Info newGenericInfoWindow(final I_AD_InfoWindow infoWindow)
	{
		String className = infoWindow.getClassname();
		if (Check.isEmpty(className, true))
		{
			className = InfoSimple.class.getCanonicalName();
		}

		try
		{
			final boolean modal = isModal();

			@SuppressWarnings("unchecked")
			final Class<InfoSimple> clazz = (Class<InfoSimple>)Info.class.getClassLoader().loadClass(className);
			final java.lang.reflect.Constructor<? extends Info> ctor = clazz.getConstructor(Frame.class, Boolean.class);
			if (!ctor.isAccessible())
			{
				ctor.setAccessible(true);
			}
			final InfoSimple infoSimple = (InfoSimple)ctor.newInstance(getParentFrame(), modal);
			final Map<String, Object> attributes = getAttributes();
			if (attributes != null)
			{
				for (final Entry<String, Object> e : attributes.entrySet())
				{
					infoSimple.setCtxAttribute(e.getKey(), e.getValue());
				}
			}
			infoSimple.init(modal, getWindowNo(), infoWindow, getKeyColumn(), getSearchValue(), isMultiSelection(), getWhereClause());

			// Set the window icon if any
			final Image icon = getIconImage();
			if (icon != null)
			{
				infoSimple.getWindow().setIconImage(icon);
			}

			return infoSimple;
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	private Info newStandardInfoWindow()
	{
		final String tableName = getTableName();
		final String keyColumn = getKeyColumn();
		final Frame parentFrame = getParentFrame();
		final boolean modal = isModal();
		final int windowNo = getWindowNo();
		final String value = getSearchValue();
		final boolean multiSelection = isMultiSelection();
		final String whereClause = getWhereClause();

		final Info info = new InfoGeneral(parentFrame, modal, windowNo, value,
					tableName, keyColumn,
					multiSelection, whereClause);

		//
		// Set icon if any
		final Image icon = getIconImage();
		if (icon != null)
		{
			info.getWindow().setIconImage(icon);
		}

		return info;
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	/**
	 * Sets the table name. It is also calling the {@link #setIconName(String)}.
	 */
	public InfoBuilder setTableName(final String tableName)
	{
		_tableName = tableName;
		return this;
	}

	private String getTableName()
	{
		return _tableName;
	}

	public InfoBuilder setParentFrame(final Frame parentFrame)
	{
		_parentFrame = parentFrame;
		return this;
	}

	public Frame getParentFrame()
	{
		return _parentFrame;
	}

	public InfoBuilder setModal(final boolean modal)
	{
		_modal = modal;
		return this;
	}

	private boolean isModal()
	{
		if (_modal != null)
		{
			return _modal;
		}

		//
		// Checks if we shall display the info window as modal.
		// We shall make the info window modal if the given windowNo is for a regular window, because else we would have a leak in managed windows (so this window will not be closed on logout).
		// This is because in case a windowNo is provided, no new windowNo will be created so the window will not be added to window manager.
		// Usually, the case when a windowNo is provided is the case when the info window is opened from a window component (like VEditor action button).
		return Env.isRegularWindowNo(getWindowNo());
	}

	public InfoBuilder setWindowNo(final int windowNo)
	{
		_windowNo = windowNo;
		return this;
	}

	private int getWindowNo()
	{
		if (_windowNo != null)
		{
			return _windowNo;
		}

		return Env.WINDOW_None;
	}

	public InfoBuilder setKeyColumn(final String keyColumn)
	{
		_keyColumn = keyColumn;
		return this;
	}

	private String getKeyColumn()
	{
		if (_keyColumn != null)
		{
			return _keyColumn;
		}

		return getTableName() + "_ID";
	}

	public InfoBuilder setSearchValue(final String value)
	{
		_searchValue = value;
		return this;
	}

	public String getSearchValue()
	{
		return _searchValue;
	}

	public InfoBuilder setMultiSelection(final boolean multiSelection)
	{
		_multiSelection = multiSelection;
		return this;
	}

	private boolean isMultiSelection()
	{
		return _multiSelection;
	}

	public InfoBuilder setWhereClause(final String whereClause)
	{
		_whereClause = whereClause;
		return this;
	}

	public String getWhereClause()
	{
		return _whereClause;
	}

	public InfoBuilder setAttributes(final Map<String, Object> attributes)
	{
		_attributes = attributes;
		return this;
	}

	private Map<String, Object> getAttributes()
	{
		return _attributes;
	}

	public InfoBuilder setInfoWindow(final I_AD_InfoWindow infoWindow)
	{
		_infoWindowDef = infoWindow;

		if (infoWindow != null)
		{
			final String tableName = Services.get(IADInfoWindowBL.class).getTableName(infoWindow);
			setTableName(tableName);
		}

		return this;
	}

	private I_AD_InfoWindow getInfoWindow()
	{
		if (_infoWindowDef != null)
		{
			return _infoWindowDef;
		}

		final String tableName = getTableName();
		final I_AD_InfoWindow infoWindowFound = Services.get(IADInfoWindowDAO.class).retrieveInfoWindowByTableName(getCtx(), tableName);
		if (infoWindowFound != null && infoWindowFound.isActive())
		{
			return infoWindowFound;
		}

		return null;
	}

	/**
	 * @param iconName icon name (without size and without file extension).
	 */
	public InfoBuilder setIconName(final String iconName)
	{
		if (Check.isEmpty(iconName, true))
		{
			return setIcon(null);
		}
		else
		{
			final Image icon = Images.getImage2(iconName + "16");
			return setIcon(icon);
		}
	}

	public InfoBuilder setIcon(final Image icon)
	{
		this._iconImage = icon;
		return this;
	}

	private Image getIconImage()
	{
		return _iconImage;
	}

}
