package de.metas.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;

import com.google.common.base.Predicates;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.ISingletonService;
import lombok.Builder.Default;
import lombok.NonNull;

public interface IHandlingUnitsBL extends ISingletonService
{
	/**
	 * @return default storage factory
	 */
	IHUStorageFactory getStorageFactory();

	/**
	 * @param contextProvider
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContext(IContextAware contextProvider);

	/**
	 * Create HU Context.
	 *
	 * Compared with {@link #createMutableHUContext(IContextAware)} this method optimizes the HU Context for HU generation/processing (i.e. enable caching etc etc).
	 *
	 * @param contextProvider
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContextForProcessing(IContextAware contextProvider);

	/**
	 * Creates a mutable context with the given <code>ctx</code> (may not be <code>null</code>) and <code>trxName=ITrx.TRXNAME_None</code>.
	 *
	 * @param ctx
	 * @return
	 */
	IMutableHUContext createMutableHUContext(Properties ctx);

	/**
	 * Creates a mutable context with the given <code>ctx</code> and <code>trxName</code>.
	 *
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	IMutableHUContext createMutableHUContext(Properties ctx, String trxName);

	I_C_UOM getHandlingUOM(I_M_Product product);

	I_C_UOM getC_UOM(I_M_Transaction mtrx);

	/**
	 * Method iterated the given version and checks if one of them has {@link I_M_HU_PI_Version#isCurrent()} set to <code>true</code>.
	 *
	 * @param versions
	 * @return
	 */
	boolean isListContainsCurrentVersion(List<I_M_HU_PI_Version> versions);

	/**
	 * @param hu
	 * @return true if HU was destroyed
	 */
	boolean isDestroyed(I_M_HU hu);

	/**
	 * Refresh HU first before checking if it's destroyed
	 *
	 * @param hu
	 * @return true if HU was destroyed
	 *
	 * @throws AdempiereException if hu has local changes
	 */
	boolean isDestroyedRefreshFirst(I_M_HU hu) throws AdempiereException;

	/**
	 * Build a user friendly display name of given HU.
	 *
	 * If u want more options, please use {@link #buildDisplayName(I_M_HU)}.
	 *
	 * @param hu
	 * @return display name (user friendly) of given HU
	 */
	String getDisplayName(I_M_HU hu);

	IHUDisplayNameBuilder buildDisplayName(I_M_HU hu);

	/**
	 *
	 * @param hu may be {@code null}
	 * @return true if this is a virtual HU
	 */
	boolean isVirtual(I_M_HU hu);

	/**
	 *
	 * @param huItem
	 * @return {@code true} if the given {@code huItems}'s {@code M_HU_PI_Item_ID} is the "virtual" one, see {@link IHandlingUnitsDAO#getVirtual_HU_PI_Item_ID()}.
	 */
	boolean isVirtual(I_M_HU_Item huItem);

	/**
	 * Checks if given <code>huItem</code> is part of a pure virtual HU.
	 *
	 * A HU is considered pure virtual when:
	 * <ul>
	 * <li>{@link #isVirtual(I_M_HU)}
	 * <li>and its parent HU Item ({@link I_M_HU#getM_HU_Item_Parent()}) is a material line (i.e. {@link #getItemType(I_M_HU_Item)} is {@link X_M_HU_PI_Item#ITEMTYPE_Material})
	 * </ul>
	 *
	 * e.g.
	 * <ul>
	 * <li>a VHU on a palet is virtual but it's NOT PURE virtual (i.e. it's parent HU Item is an item of type {@link X_M_HU_PI_Item#ITEMTYPE_HandlingUnit})
	 * <li>a VHU, top level, is virtual but it's NOT PURE virtual
	 * <li>a VHU linked to an material HU Item IS PURE virtual
	 * </ul>
	 *
	 * @param huItem
	 * @return true if this item is part of a pure virtual HU.
	 */
	boolean isPureVirtual(I_M_HU_Item huItem);

	/**
	 * A pure virtual HU will have {@link IHandlingUnitsBL#isPureVirtual(I_M_HU_Item)} true for all of the HU's items.
	 *
	 * @param hu
	 * @return true if the HU is considered a "pure virtual" HU
	 */
	boolean isPureVirtual(I_M_HU hu);

	/**
	 * Destroy given HU or some of it's children which are empty.
	 *
	 * <b>NOTE: for a full description of everything this method does, consult the javadoc of {@link #destroyIfEmptyStorage(IHUContext, I_M_HU)}.</b>
	 *
	 * @param hu
	 * @return true if given HU was fully destroyed now or it was already destroyed
	 * @see #destroyIfEmptyStorage(IHUContext, I_M_HU)
	 */
	boolean destroyIfEmptyStorage(I_M_HU hu);

	/**
	 * Destroy given HU or some of it's children which are empty.
	 * <p>
	 * NOTE 1: if you are not calling this method from a code chunk which is injected to be executed inside HU API (which could be your case!) please consider using
	 * {@link #destroyIfEmptyStorage(I_M_HU)}.
	 * <p>
	 * NOTE 2: the destroyed HUs are also saved.
	 * Additionally, this method does:
	 * <ul>
	 * <li>destroys any of it's HU children which have empty storage
	 * <li>if HU's parent remains empty after this HU is destroyed, it will destroy the parent also (recursively to the top).
	 * </ul>
	 *
	 * @param huContext
	 * @param hu
	 * @return true if given HU was fully destroyed now or it was already destroyed
	 */
	boolean destroyIfEmptyStorage(IHUContext huContext, I_M_HU hu);

	/**
	 * Gets HU Item Type.
	 * <p>
	 * <b>Important:</b> HU items that were created prior to https://github.com/metasfresh/metasfresh/issues/460 might have an empty
	 * {@link I_M_HU_Item#COLUMN_ItemType}. So unless you know what you do, please use this method rather than {@link I_M_HU_Item#getItemType()}, because otherwise you might stumble over an old/pre-existing item and get wrong results.
	 *
	 * @param huItem
	 * @return HU Item Type
	 * @see I_M_HU_PI_Item#getItemType()
	 */
	String getItemType(I_M_HU_Item huItem);

	/**
	 * Gets top level parent of given HU (i.e. the top of hierarchy) or given HU if that HU does not have a parent
	 *
	 * @param hu
	 * @return top level parent; never return null
	 */
	I_M_HU getTopLevelParent(I_M_HU hu);

	/**
	 * Gets top level HUs of given HUs (i.e. the top of hierarchy).
	 *
	 * @param query see {@link TopLevelHusQuery}.
	 *
	 * @return top level HUs; never return {@code null}
	 */
	List<I_M_HU> getTopLevelHUs(TopLevelHusQuery query);

	@lombok.Builder
	@lombok.Value
	final static class TopLevelHusQuery
	{
		/**
		 * May be empty, but not {@code null}
		 */
		@NonNull
		Collection<I_M_HU> hus;

		/**
		 * If {@code true} then e.g. for a CU, not only the LU will be returned, but also the intermediate TU and the CU itself.<br>
		 * If {@code false}, then any hu will only be returned if it is a top level HU itself.
		 */
		boolean includeAll;

		/**
		 * Optional; if not provided, then every hu passes the default filter.<br>
		 * If the filter returns {@code false} for a given HU, then neither that HU or its parents will be added to the result.
		 */
		@Default
		Predicate<I_M_HU> filter = Predicates.alwaysTrue();
	}

	/**
	 * Gets top level parent of given HU (i.e. the top of hierarchy) or given HU if that HU does not have a parent.
	 *
	 * @param hu
	 * @return top level parent; never return null; more preciselly:
	 *         <ul>
	 *         <li>if given HU is a VHU, then returned LUTUCU pair will have: VHU=given HU, TU=parent TU, LU=parent LU(top level)
	 *         <li>if given HU is a TU, then returned LUTUCU pair will have: VHU=null, TU=given HU, LU=parent LU(top level)
	 *         <li>if given HU is a LU, then returned LUTUCU pair will have: VHU=null, TU=null, LU=given HU(top level)
	 *         </ul>
	 */
	LUTUCUPair getTopLevelParentAsLUTUCUPair(I_M_HU hu);

	/**
	 * Determines if the handling unit is a loading unit (type {@link X_M_HU_PI_Version#HU_UNITTYPE_LoadLogistiqueUnit} )
	 *
	 * @param hu maybe be {@code null}. In that case, {@code false} is returned.
	 * @return true if loading unit (LU)
	 */
	boolean isLoadingUnit(I_M_HU hu);

	/**
	 * Asserts given HU is a Loading Unit(LU).
	 *
	 * @param hu
	 * @throws HUException in case given LU is not a loading unit
	 */
	void assertLoadingUnit(I_M_HU hu);

	/**
	 * Determines if the handling unit is strictly a transport unit (a.k.a. trade unit, type {@link X_M_HU_PI_Version#HU_UNITTYPE_TransportUnit} ).
	 *
	 * @param hu
	 * @return true if transport unit (TU)
	 */
	boolean isTransportUnit(I_M_HU hu);

	/**
	 * Determines if the handling unit is a transport unit (a.k.a. trade unit, type {@link X_M_HU_PI_Version#HU_UNITTYPE_TransportUnit} ) or is a virtual Handling Unit which can be stored on a LU.
	 *
	 * @param hu
	 * @return true if transport unit (TU) or virtual handling unit
	 */
	boolean isTransportUnitOrVirtual(I_M_HU hu);

	/**
	 * @return true if the HU is a TU or an aggregated TU
	 */
	boolean isTransportUnitOrAggregate(I_M_HU hu);

	/**
	 * Checks if given handling unit is top level (i.e. it has no parents)
	 *
	 * @param hu
	 * @return true if is a top level handling unit
	 */
	boolean isTopLevel(I_M_HU hu);

	/**
	 * Gets top level LU of given HU.
	 *
	 * If given HU is a loading unit then given HU will be returned.
	 *
	 * @param hu
	 * @return LU handling unit or null.
	 */
	I_M_HU getLoadingUnitHU(I_M_HU hu);

	/**
	 * Gets the TU of given HU.
	 *
	 * @param hu
	 * @return TU handling unit or null.
	 */
	I_M_HU getTransportUnitHU(I_M_HU hu);

	/**
	 * Returns the {@link I_M_HU_PI_Version#COLUMNNAME_HU_UnitType} value of the given <code>pi</code>'s version.
	 *
	 * @param pi
	 * @return unit type
	 */
	String getHU_UnitType(I_M_HU_PI pi);

	/**
	 * Returns the {@link I_M_HU_PI_Version#COLUMNNAME_HU_UnitType} value of the given <code>hu</code>'s.
	 *
	 * @param hu
	 * @return unit type
	 */
	String getHU_UnitType(I_M_HU hu);

	/**
	 *
	 * @param piItem
	 * @return true if given <code>piItem</code> is null or is NoPI
	 */
	boolean isNoPI(I_M_HU_PI_Item piItem);

	/**
	 *
	 * @param huPI
	 * @return true if given HU PI is not null and is Virtual PI
	 */
	boolean isVirtual(I_M_HU_PI huPI);

	/**
	 * Marks the hu as destroyed, but doesn't handle the storages
	 *
	 * @param huContext
	 * @param hu
	 */
	void markDestroyed(IHUContext huContext, I_M_HU hu);

	/**
	 * Marks all HUs as destroyed, but doesn't handle the storages.
	 *
	 * @param huContext
	 * @param hus HUs to mark as destroyed
	 */
	void markDestroyed(IHUContext huContext, Collection<I_M_HU> hus);

	/**
	 * Checks if the given {@code hu} is a aggregate HU.<br>
	 * An aggregate HU is on a virtual HU located below an LU that represents a whole number of TUs at once.
	 *
	 * @param hu optional, may be {@code null}.
	 * @return {@code true} if the given {@code hu} is not {@code null} and if it also has a {@code M_HU_Item_Parent} with {@code ItemType} being {@link X_M_HU_Item#ITEMTYPE_HUAggregate}.
	 */
	boolean isAggregateHU(I_M_HU hu);

	I_M_HU_PI getPI(I_M_HU hu);

	I_M_HU_PI_Version getPIVersion(I_M_HU hu);

	I_M_HU_PI_Item getPIItem(I_M_HU_Item huItem);

	HuPackingInstructionsVersionId getEffectivePIVersionId(I_M_HU hu);

	/**
	 * If the given {@code hu} is a aggregate HU, return the PI version of the HUs that are <i>represented</i> within the aggregate HU.<br>
	 * Otherwise, return the given {@code hu}'s own/direct PI version.
	 *
	 * @param hu
	 * @return
	 */
	I_M_HU_PI_Version getEffectivePIVersion(I_M_HU hu);

	/**
	 * If the given {@code hu} is a aggregate HU, return the PI of the HUs that are <i>represented</i> within the aggregate HU.<br>
	 * Otherwise, return the given {@code hu}'s own/direct PI.
	 *
	 * @param hu
	 * @return
	 */
	I_M_HU_PI getEffectivePI(I_M_HU hu);

	I_M_HU_PackingMaterial getHUPackingMaterial(I_M_HU_Item huItem);

}
