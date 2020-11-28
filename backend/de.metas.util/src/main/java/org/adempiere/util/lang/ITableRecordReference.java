package org.adempiere.util.lang;

/**
 * Table record reference.
 * <p>
 * Used to store references to models, without having to load them from the start (depends on implementation of this interface).
 * <p>
 * In case the underlying model is needed, the developer can call {@link #getModel(IContextAware)} which will return the underlying model.
 *
 * @author tsa
 */
public interface ITableRecordReference
{
	String COLUMNNAME_Record_ID = "Record_ID";

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * @return referenced model's TableName
	 */
	String getTableName();

	/**
	 * @return referenced model's AD_Table_ID
	 */
	int getAD_Table_ID();

	/**
	 * @return referenced model's ID
	 */
	int getRecord_ID();

	/**
	 * Gets referenced/underlying model using given context.
	 * <p>
	 * NOTE: implementations of this method can cache the model but they always need to check if the cached model is valid in given context (e.g. has the same transaction etc).
	 *
	 * @return referenced model
	 * @deprecated It's a bad habit to use this method. Please use the appropriate repository or DAO instead.
	 */
	@Deprecated
	Object getModel(IContextAware context);

	/**
	 * @deprecated It's a bad habit to use this method. Please use the appropriate repository or DAO instead.
	 */
	@Deprecated
	Object getModel();

	/**
	 * Gets referenced/underlying model using given context and wraps it to provided <code>modelClass</code> interface.
	 *
	 * @return referenced model wrapped to given model interface
	 * @see #getModel(IContextAware)
	 */
	<T> T getModel(IContextAware context, Class<T> modelClass);

	<T> T getModel(final Class<T> modelClass);

	/**
	 * Use case: you updated records via directUpdate.
	 */
	void notifyModelStaled();
}
