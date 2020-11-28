package de.metas.handlingunits;

import de.metas.util.ISingletonService;

/**
 * Used to register and create handlers for custom handling unit changes on documents.
 *
 * @author ad
 *
 */
public interface IHUDocumentHandlerFactory extends ISingletonService
{
	/**
	 * Creates an instance of the class that was previously registered for the given <code>tableName</code> and returns it. If no class was registered, the method returns <code>null</code>.
	 *
	 * @param tableName
	 * @return
	 */
	IHUDocumentHandler createHandler(String tableName);

	/**
	 * Registers the given <code>handler</code> class for the given <code>tableName</code>.
	 * <p>
	 * <b>IMPORTANT: the clas needs to have a default constructor.<b>
	 *
	 * @param tableName
	 * @param handler
	 */
	void registerHandler(String tableName, Class<? extends IHUDocumentHandler> handler);
}
