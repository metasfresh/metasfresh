package org.adempiere.misc.service;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import de.metas.util.ISingletonService;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated this service is deprecated <b>big time</b>. Please use {@link InterfaceWrapperHelper} instead.
 */
@Deprecated
public interface IPOService extends ISingletonService
{
	/**
	 * Tries to save the given PO with the given transaction. Throws a
	 * {@link RuntimeException}, if {@link PO#save(String)} returns
	 * <code>false</code>.
	 * 
	 * @param po
	 * @param trxName
	 * @throws RuntimeException
	 *             if the po's save fails
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 * 
	 */
	void save(Object po, String trxName);

	/**
	 * If the table of the given PO has a column with the given name, the PO's
	 * value is returned.
	 * 
	 * This method can be used to access non-standard columns that are not
	 * present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @return the PO's value of the given column or <code>null</code> if the PO
	 *         doesn't have a column with the given name.
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	Object getValue(Object po, String columnName);

	/**
	 * If the table of the given PO has a column with the given name, the PO's
	 * value is set to the given value.
	 * 
	 * This method can be used to access non-standard columns that are not
	 * present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @param value
	 *            may be <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void setValue(Object po, String columnName, Object value);

	/**
	 * Convenience method. Invokes {@link #getValue(Object, String)} and
	 * {@link #setValue(Object, String, Object)}.
	 * 
	 * @param source
	 * @param dest
	 * @param columnName
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void copyValue(Object source, Object dest, String columnName);
}
