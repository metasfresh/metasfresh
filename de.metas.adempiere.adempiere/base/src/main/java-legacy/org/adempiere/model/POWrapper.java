/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2010 Teo Sarca, teo.sarca@gmail.com *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.IModelClassInfo;
import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.persistence.ModelClassIntrospector;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.persistence.exceptions.ModelClassNotSupportedException;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.wrapper.IInterfaceWrapper;
import org.adempiere.ad.wrapper.POModelInternalAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.ProxyMethodsCache;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.IModelTranslationMap;
import de.metas.logging.LogManager;

/**
 * Wrap a PO object to a given bean interface. Example
 *
 * <pre>
 * public interface I_C_Invoice_Customized
 * {
 * public int getCustomValue1();
 * public void setCustomValue1(int customValue1);
 * public String getCustomString1();
 * public void setCustomString1(String customString1);
 * }
 * ....
 * MInvoice invoice = ......;
 * I_C_Invoice_Customized invoiceCustomized = POWrapper.create(invoice, I_C_Invoice_Customized.class);
 * invoiceCustomized.setCustomValue1(12345);
 * invoiceCustomized.setCustomString1("my test string");
 * invoice.saveEx();
 * </pre>
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class POWrapper implements InvocationHandler, IInterfaceWrapper
{
	public static <T> T create(final Object po, final Class<T> cl)
	{
		final Boolean useOldValues = null; // take it from wrapped object if any, else "false"
		final String trlAdLanguage = null; // take it from wrapped object if any, else fallback to std way of getting it
		return create(po, cl, useOldValues, trlAdLanguage);
	}

	public static <T> T create(final Object po, final Class<T> cl, final boolean useOldValues)
	{
		final String trlAdLanguage = null;
		return create(po, cl, useOldValues, trlAdLanguage);
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(final Object obj, final Class<T> cl, final Boolean useOldValues, final String trlAdLanguage)
	{
		if (obj == null)
		{
			return null;
		}

		boolean useOldValuesEffective = useOldValues == null ? false : useOldValues;
		String trlAdLanguageEffective = trlAdLanguage;

		if (cl.isInstance(obj) && !useOldValuesEffective && trlAdLanguageEffective == null)
		{
			return (T)obj;
		}

		//
		// Get the PO
		PO po = null;
		// Check if is a direct instance
		if (obj instanceof PO)
		{
			po = (PO)obj;
		}
		// Check if it's a POWrapper
		if (po == null)
		{
			final POWrapper poWrapper = getPOWrapperOrNull(obj);
			if (poWrapper != null)
			{
				po = poWrapper.getPO();
				// Preserve settings from old wrapped object, if they were not explicitelly specified
				if (useOldValues == null)
				{
					useOldValuesEffective = poWrapper.useOldValues;
				}
				if (trlAdLanguage == null)
				{
					trlAdLanguageEffective = poWrapper.trlAdLanguage;
				}
			}
		}
		// Try getting the PO using other wrappers too
		if (po == null)
		{
			po = getPO(obj);
		}

		if (!(po instanceof PO))
		{
			throw new AdempiereException("Not a PO object - " + obj);
		}

		//
		// Check TableName
		final String classTableName = InterfaceWrapperHelper.getTableNameOrNull(cl);
		if (classTableName != null)
		{
			final String poTableName = po.get_TableName();
			if (!poTableName.equals(classTableName))
			{
				throw new IllegalArgumentException("PO " + po + " (TableName:" + poTableName + ") and class " + cl + " (TableName:" + classTableName + ") are not compatible");
			}
		}

		return (T)Proxy.newProxyInstance(cl.getClassLoader(),
				new Class<?>[] { cl },
				new POWrapper(cl, po, useOldValuesEffective, trlAdLanguageEffective));
	}

	/**
	 * Similar to {@link #create(Properties, String, int, Class, String)}, but attempts to get the table name from the given <code>modelClass</code>.
	 *
	 * @param <T> model interface
	 * @param ctx context
	 * @param id record id
	 * @param modelClass model interface class
	 * @param trxName db transaction name
	 * @return
	 */
	public static <T> T create(final Properties ctx, final int id, final Class<T> modelClass, final String trxName)
	{
		final String tableName = getTableName(modelClass); // won't return null
		return create(ctx, tableName, id, modelClass, trxName);
	}

	/**
	 * Loads the PO with the given <code>id</code> and returns an isntance.
	 *
	 * @param <T> model interface
	 * @param ctx context
	 * @param tableName table name to be queried
	 * @param id record id
	 * @param modelClass model interface class
	 * @param trxName db transaction name
	 * @return new instance or <code>null</code> if not found.
	 */
	public static <T> T create(final Properties ctx, final String tableName, final int id, final Class<T> modelClass, final String trxName)
	{
		Check.assumeNotNull(tableName, "tableName not null");

		if (id < getFirstValidIdByColumnName(tableName + "_ID"))
		{
			return null;
		}

		final PO po = tableModelLoader.getPO(ctx, tableName, id, trxName);

		if (po == null || po.get_ID() != id)
		{
			// throw new AdempiereException("@PONotFound@ @" + tableName + "@ (ID=" + record_id + ")");
			return null;
		}
		return create(po, modelClass);
	}

	public static <T> T create(final Properties ctx, final Class<T> cl, final String trxName)
	{
		final String tableName = getTableName(cl);
		final PO po = tableModelLoader.newPO(ctx, tableName, trxName);
		if (po == null)
		{
			// throw new AdempiereException("@PONotFound@ @" + tableName + "@ (ID=" + record_id + ")");
			return null;
		}
		return create(po, cl);
	}

	public static <T> T translate(final T model, final Class<T> cl)
	{
		final String trlAdLanguage = null; // autodetect from context
		return translate(model, cl, trlAdLanguage);
	}

	public static <T> T translate(final T model, final Class<T> cl, String trlAdLanguage)
	{
		final PO po = getStrictPO(model);

		if (trlAdLanguage == null)
		{
			final Properties ctx = getCtx(model);
			trlAdLanguage = Env.getAD_Language(ctx);
		}
		if (Env.isBaseLanguage(trlAdLanguage, po.get_TableName()))
		{
			// no need to translate because context language is same as base language
			trlAdLanguage = null;
		}

		final boolean useOldValues = false;
		return create(po, cl, useOldValues, trlAdLanguage);
	}

	public static IModelTranslationMap getModelTranslationMap(final Object model)
	{
		final PO po = getStrictPO(model);
		Check.assumeNotNull(po, "po not null for {}", model);

		return po.get_ModelTranslationMap();
	}

	/**
	 * Method calls {@link #getPO(Object, boolean)} with <code>checkOtherWrapper=true</code>.
	 *
	 * @param <T>
	 * @param model
	 * @return underlying {@link PO} or null
	 */
	/*package*/ static <T extends PO> T getPO(final Object model)
	{
		final boolean checkOtherWrapper = true;
		final T po = getPO(model, checkOtherWrapper);
		return po;
	}

	/**
	 * Method strictly gets the underlying PO if the wrapper supports it.
	 *
	 * Compared with {@link #getPO()}, this method will NOT try to load the PO from other wrappers.
	 *
	 * @param model
	 * @return
	 */
	public static <T extends PO> T getStrictPO(final Object model)
	{
		final boolean checkOtherWrapper = false;
		final T po = getPO(model, checkOtherWrapper);
		return po;
	}

	/**
	 *
	 * @param model
	 * @param checkOtherWrapper if the given <code>model</code> is handled by a {@link GridTabWrapper} and this param is <code>true</code>, then this method <b>loads a new PO from DB</b>, only using
	 *            the given <code>model</code>'s table name and record ID. If this param is <code>false</code> and <code>model</code> is not handled by <code>POWrapper</code>, then this method returns
	 *            <code>null</code>.
	 * @return <ul>
	 * <li>PO
	 * <li>null if model is null
	 * <li>null if {@link POWrapper} does not support it and <code>checkOtherWrapper</code> is <code>false</code>.
	 * </ul>
	 */
	@SuppressWarnings("unchecked")
	private static <T extends PO> T getPO(final Object model, final boolean checkOtherWrapper)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof PO)
		{
			return (T)model;
		}

		if (Proxy.isProxyClass(model.getClass()))
		{
			final InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof POWrapper)
			{
				final POWrapper wrapper = (POWrapper)ih;
				return (T)wrapper.getPO();
			}
			if (checkOtherWrapper && ih instanceof GridTabWrapper)
			{
				final GridTabWrapper wrapper = (GridTabWrapper)ih;
				return wrapper.getPO();
			}
		}

		return null;
	}

	private static final POWrapper getPOWrapperOrNull(final Object model)
	{
		if (Proxy.isProxyClass(model.getClass()))
		{
			final InvocationHandler ih = Proxy.getInvocationHandler(model);
			if (ih instanceof POWrapper)
			{
				final POWrapper wrapper = (POWrapper)ih;
				return wrapper;
			}
		}

		return null;
	}

	/**
	 * Get model context.
	 *
	 * @param model
	 * @return
	 */
	public static Properties getCtx(final Object model)
	{
		final boolean useClientOrgFromModel = false;
		return getCtx(model, useClientOrgFromModel);
	}

	/**
	 * Get context form model.
	 * If <code>useClientOrgFromModel</code> is true, then the returned context will contain the given <code>model</code>'s AD_Client_ID and AD_Org_ID in its <code>#AD_client_ID</code> and
	 * <code>#AD_Org_ID</code> properties.
	 *
	 * @param model
	 * @param useClientOrgFromModel
	 * @return
	 */
	public static Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		final PO po = getStrictPO(model);

		if (po != null)
		{
			final Properties ctxPO = po.getCtx();
			final Properties ctx;
			if (useClientOrgFromModel)
			{
				final int adClientId = po.getAD_Client_ID();
				final int adOrgId = po.getAD_Org_ID();

				if (Env.getAD_Client_ID(ctxPO) != adClientId
						|| Env.getAD_Org_ID(ctxPO) != adOrgId)
				{
					ctx = Env.deriveCtx(ctxPO); // won't change the original.
					Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
					Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
				}
				else
				{
					// we have the same AD_Client_ID and AD_Org_ID
					// => we can use PO's context directly, no need to create a new instance
					ctx = ctxPO;
				}
			}
			else
			{
				ctx = ctxPO;
			}
			return ctx;
		}

		// Notify developer that (s)he is using wrong models
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException e = new AdempiereException("Cannot get context from model " + model + " because is not supported. Returning global context.");
			log.warn(e.getLocalizedMessage(), e);
		}

		return Env.getCtx();
	}

	public static String getTrxName(final Object model)
	{
		final PO po = getStrictPO(model);
		if (po != null)
		{
			return po.get_TrxName();
		}

		// Notify developer that (s)he is using wrong models
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException e = new AdempiereException("Cannot get trxName from model " + model + " because is not supported. Returning 'null'.");
			log.warn(e.getLocalizedMessage(), e);
		}

		return null;
	}

	/**
	 *
	 * @param clazz
	 * @return
	 * @throws AdempiereException
	 * @see {@link InterfaceWrapperHelper#getTableName(Class)}
	 */
	public static final String getTableName(final Class<?> clazz) throws AdempiereException
	{
		return InterfaceWrapperHelper.getTableName(clazz);
	}

	private static final transient Logger log = LogManager.getLogger(POWrapper.class);
	private static final transient TableModelLoader tableModelLoader = TableModelLoader.instance;
	private final Class<?> interfaceClass;
	private final PO po;
	private final boolean useOldValues;
	private final String trlAdLanguage;
	private final IModelClassInfo modelClassInfo;

	private POWrapper(final Class<?> interfaceClass, final PO po, final boolean useOldValues, final String trlAdLanguage)
	{
		super();
		this.po = po;
		this.useOldValues = useOldValues;
		this.interfaceClass = interfaceClass;

		// FIXME: handle the case when interfaceClass is not an model interface
		modelClassInfo = ModelClassIntrospector.getInstance().getModelClassInfo(this.interfaceClass);

		this.trlAdLanguage = trlAdLanguage;
	}

	public Properties getCtx()
	{
		return po.getCtx();
	}

	public String getTrxName()
	{
		return po.get_TrxName();
	}

	public String getTableName()
	{
		return po.get_TableName();
	}

	/**
	 * Gets model's implementation class
	 *
	 * @return model's implementation class; never return null
	 */
	private final Class<?> getModelClassImpl()
	{
		return po.getClass();
	}

	protected Set<String> getColumnNames()
	{
		return po.getPOInfo().getColumnNames();
	}

	protected int getColumnIndex(final String name)
	{
		return po.get_ColumnIndex(name);
	}

	public boolean isVirtualColumn(final String columnName)
	{
		return po.getPOInfo().isVirtualColumn(columnName);
	}

	public boolean isKeyColumnName(final String columnName)
	{
		return po.getPOInfo().isKey(columnName);
	}

	public boolean isCalculated(final String columnName)
	{
		return po.getPOInfo().isCalculated(columnName);
	};

	protected Object getValue(final String columnName, final int index, final Class<?> returnType)
	{
		if (trlAdLanguage != null && String.class.equals(returnType))
		{
			return po.get_Translation(columnName, trlAdLanguage);
		}

		final Object value;
		if (useOldValues)
		{
			value = po.get_ValueOld(index);
		}
		else
		{
			value = po.get_Value(index);
		}

		if (boolean.class.equals(returnType))
		{
			if (value == null)
			{
				return false;
			}
			else
			{
				return value instanceof Boolean ? value : "Y".equals(value);
			}
		}
		else
		{
			return value;
		}

	}

	protected int getValueAsInt(final int index)
	{
		if (useOldValues)
		{
			return po.get_ValueOldAsInt(index);
		}
		else
		{
			return po.get_ValueAsInt(index);
		}
	}

	protected boolean setValue(final String name, Object value)
	{
		if (useOldValues)
		{
			throw new AdempiereException("Setting values in an old object is not allowed");
		}
		else
		{
			value = checkZeroIdValue(name, value);

			final POInfo poInfo = po.getPOInfo();
			if (!poInfo.isColumnUpdateable(name))
			{
				// If the column is not updateable we need to use set_ValueNoCheck
				// because else is not consistent with how the generated classes were created
				// see org.adempiere.util.ModelClassGenerator.createColumnMethods
				return po.set_ValueNoCheck(name, value);
			}
			else
			{
				return po.set_ValueOfColumnReturningBoolean(name, value);
			}
		}
	}

	protected final boolean setValueNoCheck(final String columnName, final Object value)
	{
		if (useOldValues)
		{
			throw new AdempiereException("Setting values in an old object is not allowed");
		}
		else
		{
			final Object valueToSet = checkZeroIdValue(columnName, value);
			return po.set_ValueNoCheck(columnName, valueToSet);
		}
	}

	protected void setValueFromPO(final String name, final Class<?> clazz, final Object value)
	{
		if (useOldValues)
		{
			throw new AdempiereException("Setting values in an old object is not allowed");
		}
		else
		{
			po.set_ValueFromPO(name, clazz, value);
		}
	}

	// NOTE: public until we move everything to "org.adempiere.ad.model.util" package.
	public static final Object checkZeroIdValue(final String columnName, final Object value)
	{
		if (!(value instanceof Integer))
		{
			return value;
		}

		if (!columnName.endsWith("_ID"))
		{
			return value;
		}

		final int id = (Integer)value;
		if (id > 0)
		{
			return id;
		}

		final int firstValidId = getFirstValidIdByColumnName(columnName);
		if (id < firstValidId)
		{
			return null;
		}

		return id;
	}

	/**
	 * Returns 0 if there is (or could be) a valid record with ID=0), like for example <code>AD_User_ID</code>.
	 *
	 * @param columnName
	 * @return
	 */
	public static final int getFirstValidIdByColumnName(final String columnName)
	{
		if (_ColumnNamesWithFirstValidIdZERO.contains(columnName))
		{
			return 0;
		}
		return 1;
	}

	/**
	 * A set of ColumNames on which ID=0 is accepted
	 * FIXME: get rid of this hardcoded list
	 */
	private static final Set<String> _ColumnNamesWithFirstValidIdZERO = ImmutableSet.<String> builder()
			.add("AD_Client_ID")
			.add("AD_Org_ID")
			.add("Record_ID")
			.add("C_DocType_ID")
			.add("Node_ID")
			.add("AD_User_ID")
			.add("AD_Role_ID")
			.add("M_AttributeSet_ID")
			.add("M_AttributeSetInstance_ID")
			.build();

	protected Object invokeParent(final Method method, final Object[] args) throws Exception
	{
		return method.invoke(po, args);
	}

	private final boolean invokeEquals(final Object[] args)
	{
		final Object otherModel = args[0];
		final PO otherPO = getPO(otherModel);
		return po == otherPO || po.equals(otherPO);
	}

	private Method findPOMethod(final Method interfaceMethod)
	{
		final Class<?> implClass = getModelClassImpl();
		final Method implMethod = ProxyMethodsCache.getInstance().getMethodImplementation(interfaceMethod, implClass);
		return implMethod;
	}

	public PO getPO()
	{
		return po;
	}

	public Class<?> getInterfaceClass()
	{
		return interfaceClass;
	}

	@Override
	public Object invoke(final Object proxy_NOTUSED, final Method method, final Object[] args) throws Throwable
	{
		return modelClassInfo.getMethodInfo(method).invoke(modelInternalAccessor, args);
	}

	/**
	 * Load object that is referenced by given property. Example: getReferencedObject("M_Product_ID", method) should load the M_Product record with ID given by M_Product_ID property name;
	 *
	 * @param columnName value column name (e.g. M_Product_ID, AD_Client_ID etc)
	 * @param method
	 * @return model
	 */
	private final Object getReferencedObject(final String columnName, final Method interfaceMethod) throws Exception
	{
		final Class<?> columnModelType = interfaceMethod.getReturnType();

		if (useOldValues)
		{
			final POWrapperCacheLocal poCache = get_POCacheLocal(columnName, columnModelType);
			return poCache.get(columnModelType);
		}

		final Method poMethod = interfaceMethod == null ? null : findPOMethod(interfaceMethod);
		return getModelValue(po, columnName, columnModelType, poMethod);
	}

	private final static <ModelType> ModelType getModelValue(final PO model, final String columnName, final Class<ModelType> columnModelType, final Method poMethod) throws Exception
	{
		if (poMethod != null)
		{
			final Object[] poMethodArgs = null;
			final Object retValueObj = poMethod.invoke(model, poMethodArgs);
			if (retValueObj == null)
			{
				return null;
			}
			else if (columnModelType.isAssignableFrom(retValueObj.getClass()))
			{
				@SuppressWarnings("unchecked")
				final ModelType retValue = (ModelType)retValueObj;
				return retValue;
			}
			else
			{
				return create(retValueObj, columnModelType);
			}
		}

		final ModelType retValue = model.get_ValueAsPO(columnName, columnModelType);
		return retValue;

	}

	public static <ModelType> ModelType getModelValue(final Object model, final String columnName, final Class<ModelType> columnModelType)
	{
		final PO po = getStrictPO(model);
		Check.assumeNotNull(po, "po not null");

		final Method poMethod = null; // N/A
		try
		{
			return getModelValue(po, columnName, columnModelType, poMethod);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed while retrieving model from " + columnName + " of " + model, e);
		}
	}

	private transient Map<String, POWrapperCacheLocal> poCacheLocals;

	private POWrapperCacheLocal get_POCacheLocal(final String columnName, final Class<?> refModelClass)
	{
		if (poCacheLocals == null)
		{
			poCacheLocals = new HashMap<>();
		}

		final String refTableName = InterfaceWrapperHelper.getTableName(refModelClass);

		POWrapperCacheLocal poCache = poCacheLocals.get(columnName);
		if (poCache != null && !refTableName.equals(poCache.getTableName()))
		{
			log.warn("POCache does not have tableName=" + refTableName + " -- " + poCache, new Exception());
			poCache = null;
		}
		if (poCache == null)
		{
			poCache = new POWrapperCacheLocal(this, columnName, refTableName);
			poCacheLocals.put(columnName, poCache);
		}

		return poCache;
	}

	public static void save(final Object o)
	{
		if (o == null)
		{
			throw new IllegalArgumentException("model is null");
		}

		final PO po = getStrictPO(o);
		if (po != null)
		{
			po.saveEx();
		}
		else
		{
			throw new ModelClassNotSupportedException(o);
		}
	}

	public static void delete(final Object o)
	{
		if (o == null)
		{
			throw new IllegalArgumentException("model is null");
		}

		final PO po = getPO(o);
		if (po != null)
		{
			boolean force = false;

			// If the Processed column is a virtual column then we have to NOT check and enforce it,
			// because the caller has no option to unset it.
			if (po.getPOInfo().isVirtualColumn("Processed"))
			{
				force = true;
			}

			po.deleteEx(force);
		}
		else
		{
			throw new ModelClassNotSupportedException(o);
		}
	}

	public static boolean isHandled(final Object model)
	{
		return getStrictPO(model) != null;
	}

	/**
	 * Reload underlying PO object in same transaction as it was
	 *
	 * @param model
	 * @see #refresh(Object, String)
	 * @throws IllegalArgumentException if model is null
	 * @throws IllegalArgumentException if there is no underlying PO object (i.e. getPO(model) return null)
	 */
	public static void refresh(final Object model)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("model is null");
		}
		final PO po = getStrictPO(model);
		if (po == null)
		{
			throw new ModelClassNotSupportedException(model);
		}

		po.load(po.get_TrxName());
	}

	/**
	 * Reload underlying PO object
	 *
	 * @param model
	 * @param trxName transaction to be used for reloading
	 * @throws IllegalArgumentException if model is null
	 * @throws IllegalArgumentException if there is no underlying PO object (i.e. getPO(model) return null)
	 */
	public static void refresh(final Object model, final String trxName)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("model is null");
		}
		final PO po = getStrictPO(model);
		if (po == null)
		{
			throw new ModelClassNotSupportedException(model);
		}

		po.load(trxName);
	}

	public static void setTrxName(final Object model, final String trxName)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("model is null");
		}
		final PO po = getStrictPO(model);
		if (po == null)
		{
			throw new ModelClassNotSupportedException(model);
		}

		po.set_TrxName(trxName);

	}

	/**
	 * Check if given columnName's value is null
	 *
	 * @param model
	 * @param columnName
	 * @return true if columnName's value is null
	 */
	public static boolean isNull(final Object model, final String columnName)
	{
		final PO po = getStrictPO(model);
		if (po == null)
		{
			return true;
		}

		final Object value = po.get_Value(columnName);
		return value == null;
	}

	public static Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		final Object valueOld = getStrictPO(model).setDynAttribute(attributeName, value);
		return valueOld;
	}

	public static <T> T getDynAttribute(final Object model, final String attributeName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)getStrictPO(model).getDynAttribute(attributeName);
		return value;
	}

	public static boolean hasModelColumnName(final Object model, final String columnName)
	{
		final PO po = getStrictPO(model);
		if (po == null)
		{
			return false;
		}

		final int idx = po.get_ColumnIndex(columnName);
		return idx >= 0;
	}

	public static boolean hasColumnName(final Class<?> modelClass, final String columnName)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (poInfo == null)
		{
			throw new AdempiereException("No POInfo found for " + modelClass + " (tableName=" + tableName + ")");
		}

		return poInfo.hasColumnName(columnName);
	}

	public boolean hasColumnName(final String columnName)
	{
		return po.getPOInfo().hasColumnName(columnName);
	}

	public static boolean isNew(final Object model)
	{
		return getStrictPO(model).is_new();
	}

	public static boolean isUIAction(final Object model)
	{
		return getStrictPO(model).is_ManualUserAction();
	}

	public static boolean isRecordChanged(final Object model)
	{
		final PO po = getStrictPO(model);
		Check.assumeNotNull(po, "po not null for {}", model);

		return po.is_Changed();
	}

	public static boolean isValueChanged(final Object model, final String columnName)
	{
		final PO po = getStrictPO(model);
		Check.assumeNotNull(po, "po not null for {}", model);
		return isPOValueChanged(po, columnName);
	}

	/**
	 * @param model
	 * @param columnNames
	 * @return true if any of given column names where changed
	 */
	public static boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		final PO po = getStrictPO(model);
		Check.assumeNotNull(po, "po not null for {}", model);
		for (final String columnName : columnNames)
		{
			if (isPOValueChanged(po, columnName))
			{
				return true;
			}
		}

		return false;
	}

	private static final boolean isPOValueChanged(final PO po, final String columnName)
	{
		final int idx = po.get_ColumnIndex(columnName);
		if (idx < 0)
		{
			log.warn("Column " + columnName + " not found for " + po + ". Considering it as not changed");
			return false;
		}

		final boolean changed = po.is_ValueChanged(idx);
		return changed;
	}

	public static boolean hasChanges(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		final PO po = getStrictPO(model);
		return po.is_Changed();
	}

	public static final boolean isOldValues(final Object model)
	{
		final POWrapper wrapper = getPOWrapperOrNull(model);
		return wrapper == null ? false : wrapper.useOldValues;
	}

	public static IModelInternalAccessor getModelInternalAccessor(final Object model)
	{
		Check.assumeNotNull(model, "model not null");

		if (model instanceof PO)
		{
			final PO po = (PO)model;
			return new POModelInternalAccessor(po);
		}

		final POWrapper wrapper = getPOWrapperOrNull(model);
		if (wrapper != null)
		{
			return wrapper.modelInternalAccessor;
		}

		return null;
	}

	/** {@link POWrapper} internal accessor implementation */
	private final IModelInternalAccessor modelInternalAccessor = new IModelInternalAccessor()
	{
		@Override
		public void setValueFromPO(final String idColumnName, final Class<?> parameterType, final Object value)
		{
			POWrapper.this.setValueFromPO(idColumnName, parameterType, value);
		}

		@Override
		public boolean setValue(final String columnName, final Object value)
		{
			return POWrapper.this.setValue(columnName, value);
		}

		@Override
		public boolean setValueNoCheck(final String columnName, final Object value)
		{
			return POWrapper.this.setValueNoCheck(columnName, value);
		};

		@Override
		public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
		{
			return POWrapper.this.invokeParent(method, methodArgs);
		}

		@Override
		public boolean invokeEquals(final Object[] methodArgs)
		{
			return POWrapper.this.invokeEquals(methodArgs);
		}

		@Override
		public Object getValue(final String columnName, final int idx, final Class<?> returnType)
		{
			return POWrapper.this.getValue(columnName, idx, returnType);
		}

		@Override
		public Object getValue(final String columnName, final Class<?> returnType)
		{
			final int columnIndex = POWrapper.this.getColumnIndex(columnName);
			return POWrapper.this.getValue(columnName, columnIndex, returnType);
		}

		@Override
		public Object getReferencedObject(final String columnName, final Method interfaceMethod) throws Exception
		{
			return POWrapper.this.getReferencedObject(columnName, interfaceMethod);
		}

		@Override
		public Set<String> getColumnNames()
		{
			return POWrapper.this.getColumnNames();
		}

		@Override
		public int getColumnIndex(final String columnName)
		{
			return POWrapper.this.getColumnIndex(columnName);
		}

		@Override
		public boolean isVirtualColumn(final String columnName)
		{
			return POWrapper.this.isVirtualColumn(columnName);
		}

		@Override
		public boolean isKeyColumnName(final String columnName)
		{
			return POWrapper.this.isKeyColumnName(columnName);
		};

		@Override
		public boolean isCalculated(final String columnName)
		{
			return POWrapper.this.isCalculated(columnName);
		}

		@Override
		public boolean hasColumnName(final String columnName)
		{
			return POWrapper.this.hasColumnName(columnName);
		}
	};
}
