package org.compiere.apps.search;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IADInfoWindowBL;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_A_Asset;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_ResourceAssignment;
import org.compiere.util.Env;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
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
	//
	// Actions for standard/hardcoded Info windows
	// NOTE: this action name will be also used for finding the window icon.
	public static final String ACTION_InfoBPartner = "InfoBPartner";
	public static final String ACTION_InfoProduct = "InfoProduct";
	public static final String ACTION_InfoAsset = "InfoAsset";
	//public static final String ACTION_InfoAccount = "InfoAccount"; // no longer supported
	public static final String ACTION_InfoSchedule = "InfoSchedule";
	public static final String ACTION_InfoMRP = "InfoMRP";
	public static final String ACTION_InfoCRP = "InfoCRP";
	public static final String ACTION_InfoOrder = "InfoOrder";
	public static final String ACTION_InfoInvoice = "InfoInvoice";
	public static final String ACTION_InfoInOut = "InfoInOut";
	public static final String ACTION_InfoPayment = "InfoPayment";
	public static final String ACTION_InfoCashLine = "InfoCashLine";
	public static final String ACTION_InfoAssignment = "InfoAssignment";

	/** Map of TableName to IconName to be used */
	public static final ImmutableMap<String, String> tableName2iconName = ImmutableMap.<String, String> builder()
			.put(I_C_BPartner.Table_Name, ACTION_InfoBPartner)
			.put(I_M_Product.Table_Name, ACTION_InfoProduct)
			.put(I_A_Asset.Table_Name, ACTION_InfoAsset)
			// .put(I_.Table_Name, ACTION_InfoAccount)
			// .put(I_.Table_Name, ACTION_InfoSchedule)
			// .put(I_.Table_Name, ACTION_InfoMRP)
			// .put(I_.Table_Name, ACTION_InfoCRP)
			.put(I_C_Order.Table_Name, ACTION_InfoOrder)
			.put(I_C_Invoice.Table_Name, ACTION_InfoInvoice)
			.put(I_M_InOut.Table_Name, ACTION_InfoInOut)
			.put(I_C_Payment.Table_Name, ACTION_InfoPayment)
			.put(I_C_CashLine.Table_Name, ACTION_InfoCashLine)
			.put(I_S_ResourceAssignment.Table_Name, ACTION_InfoAssignment)
			.build();

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

	/**
	 * Show BPartner Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 */
	static void showBPartner(final Frame frame, final int WindowNo)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_C_BPartner.Table_Name)
				.buildAndShow();
	} // showBPartner

	/**
	 * Show Asset Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 */
	static void showAsset(final Frame frame, final int WindowNo)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_A_Asset.Table_Name)
				.buildAndShow();
	} // showBPartner

	/**
	 * Show Product Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 */
	static void showProduct(final Frame frame, final int WindowNo)
	{
		final Properties ctx = Env.getCtx();
		final Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("M_Warehouse_ID", Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID"));
		attributes.put("M_PriceList_ID", Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID"));

		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_M_Product.Table_Name)
				.setAttributes(attributes)
				.buildAndShow();
	} // showProduct

	/**
	 * Show Order Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showOrder(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_C_Order.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showOrder

	/**
	 * Show Invoice Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showInvoice(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_C_Invoice.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showInvoice

	/**
	 * Show Shipment Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showInOut(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_M_InOut.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showInOut

	/**
	 * Show Payment Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showPayment(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_C_Payment.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showPayment

	/**
	 * Show Cash Line Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showCashLine(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_C_CashLine.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showCashLine

	/**
	 * Show Assignment Info (non modal)
	 *
	 * @param frame Parent Frame
	 * @param WindowNo window no
	 * @param value query value
	 */
	static void showAssignment(final Frame frame, final int WindowNo, final String value)
	{
		newBuilder()
				.setParentFrame(frame)
				.setWindowNo(WindowNo)
				.setModal(false)
				.setTableName(I_S_ResourceAssignment.Table_Name)
				.setSearchValue(value)
				.buildAndShow();
	} // showAssignment

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

		final Info info;
		if (tableName.equals("C_BPartner"))
		{
			final boolean isSOTrx = Env.isSOTrx(getCtx(), windowNo);
			info = new InfoBPartner(parentFrame, modal, windowNo, value, isSOTrx, multiSelection, whereClause);
		}
		else if (tableName.equals("M_Product"))
		{
			final int m_Warehouse_ID = 0;
			final int m_PriceList_ID = 0;
			@SuppressWarnings("deprecation")
			final InfoProduct infoProduct = new InfoProduct(parentFrame, modal, windowNo, m_Warehouse_ID, m_PriceList_ID, value, multiSelection, whereClause);
			info = infoProduct;
		}
		else if (tableName.equals("C_Invoice"))
		{
			info = new InfoInvoice(parentFrame, modal, windowNo, value,
					multiSelection, whereClause);
		}
		else if (tableName.equals("A_Asset"))
		{
			final int assetId = 0;
			info = new InfoAsset(parentFrame, modal, windowNo, assetId, value, multiSelection, whereClause);
		}
		else if (tableName.equals("C_Order"))
		{
			info = new InfoOrder(parentFrame, modal, windowNo, value, multiSelection, whereClause);
		}
		else if (tableName.equals("M_InOut"))
		{
			info = new InfoInOut(parentFrame, modal, windowNo, value, multiSelection, whereClause);
		}
		else if (tableName.equals("C_Payment"))
		{
			info = new InfoPayment(parentFrame, modal, windowNo, value, multiSelection, whereClause);
		}
		else if (tableName.equals("C_CashLine"))
		{
			info = new InfoCashLine(parentFrame, modal, windowNo, value, multiSelection, whereClause);
		}
		else if (tableName.equals("S_ResourceAssigment"))
		{
			info = new InfoAssignment(parentFrame, modal, windowNo, value, multiSelection, whereClause);
		}
		else
		{
			info = new InfoGeneral(parentFrame, modal, windowNo, value,
					tableName, keyColumn,
					multiSelection, whereClause);
		}
		//
		Check.assumeNotNull(info, "info not null");

		//
		// Set icon if any
		final Image icon = getIconImage();
		if (icon != null)
		{
			info.getWindow().setIconImage(icon);
		}

		return info;
	}

	private final Properties getCtx()
	{
		return Env.getCtx();
	}

	/**
	 * Sets the table name. It is also calling the {@link #setIconName(String)}.
	 * 
	 * @param tableName
	 */
	public InfoBuilder setTableName(final String tableName)
	{
		_tableName = tableName;

		//
		// Also set the icon name if available
		if (_tableName != null)
		{
			final String iconName = tableName2iconName.get(_tableName);
			if (iconName != null)
			{
				setIconName(iconName);
			}
		}
		return this;
	}

	private final String getTableName()
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

		final String keyColumn = getTableName() + "_ID";
		return keyColumn;
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

	private final I_AD_InfoWindow getInfoWindow()
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
			return setIcon((Image)null);
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

	private final Image getIconImage()
	{
		return _iconImage;
	}

}
