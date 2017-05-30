package de.metas.inoutcandidate.api;

import java.util.List;

import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.util.ISingletonService;

/**
 * Packaging related DAO
 * 
 * @author ts
 * @author tsa
 * 
 * @see <a href="http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29">(2009_0022_G61)</a>
 */
public interface IPackagingDAO extends ISingletonService
{
	@Deprecated
	default List<I_M_PackagingContainer> retrieveContainers(int wareHouseId, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates an empty {@link IPackageableQuery}.
	 * 
	 * @return
	 */
	IPackageableQuery createPackageableQuery();

	/**
	 * Retrieve all {@link IPackageable} items for given <code>query</code>
	 * 
	 * @param ctx
	 * @param query
	 * @return
	 */
	List<IPackageable> retrievePackableLines(IPackageableQuery query);
}
