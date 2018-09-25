/**
 * 
 */
package de.metas.picking.legacy.form;

import java.util.Collections;
import java.util.List;

import org.compiere.model.PackagingTreeItemComparable;
import org.compiere.model.X_M_PackagingTreeItemSched;

/**
 * @author cg
 * 
 */
@Deprecated
class PackingTreeBL
{
	public static List<PackagingTreeItemComparable> getItems(int tree_id, String type)
	{
		// cg : task 05659 Picking Terminal: Disable Persistency
		return Collections.emptyList();
	}

	public static List<X_M_PackagingTreeItemSched> getNonItems(int tree_id)
	{
		// cg : task 05659 Picking Terminal: Disable Persistency
		return Collections.emptyList();
	}

	public static List<X_M_PackagingTreeItemSched> getSchedforItem(int item_id)
	{
		// cg : task 05659 Picking Terminal: Disable Persistency
		return Collections.emptyList();
	}

	/**
	 * fetch open boxes by partener's location
	 * 
	 * @param C_BPartner_Location_ID
	 * @return
	 */
	public static List<PackagingTreeItemComparable> getOpenBoxes(int C_BPartner_Location_ID, int M_Warehouse_Dest_ID, boolean isGrouppingByWarehouse)
	{
		// cg : task 05659 Picking Terminal: Disable Persistency
		return Collections.emptyList();
	}
}
