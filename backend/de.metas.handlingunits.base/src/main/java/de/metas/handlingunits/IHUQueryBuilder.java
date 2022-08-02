package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.product.ProductId;
import de.metas.storage.IStorageQuery;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.ModelColumn;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Developer friendly Query Builder which is oriented on Handling Units concerns.
 *
 * To create a new instance, please use {@link IHandlingUnitsDAO#createHUQueryBuilder()}.
 *
 * Defaults:
 * <ul>
 * <li>only top level HUs are matched by default
 * </ul>
 *
 * @author tsa
 *
 */
@SuppressWarnings("UnusedReturnValue")
public interface IHUQueryBuilder
{
	/**
	 * @return user readable string about attributes filters
	 */
	String getAttributesSummary();

	/** Creates a copy of this object */
	IHUQueryBuilder copy();

	/** Creates {@link IQuery} object from this builder */
	IQuery<I_M_HU> createQuery();

	/** Creates {@link IQueryBuilder} object from this builder */
	IQueryBuilder<I_M_HU> createQueryBuilder();

	/**
	 * Create an {@link IQueryFilter} based on what we set in our builder.
	 *
	 * NOTE: Using directly {@link IQueryFilter#accept(Object)} won't work in all cases (e.g. when matching attributes because other tables are involved)
	 *
	 * @return created query filter
	 */
	IQueryFilter<I_M_HU> createQueryFilter();

	/** Retrieves all HUs which are matching our criteria */
	List<I_M_HU> list();

	ImmutableSet<HuId> listIds();

	/** Retrieves all HUs which are matching our criteria, but no more than <code>limit</code> number. */
	List<I_M_HU> list(final int limit);

	/**
	 * Retrieves first {@link I_M_HU}.
	 *
	 * If there are more HUs an exception will be thrown.
	 *
	 * @return HU or <code>null</code>
	 */
	I_M_HU firstOnly();

	/** Retrieves first {@link I_M_HU} */
	I_M_HU first();

	/** Counts how many {@link I_M_HU}s are matched by our criteria */
	int count();

	/**
	 * Collects a unique list of models from on of {@link I_M_HU}'s columns.
	 *
	 * e.g. Collect all business partners from matched HUs
	 *
	 * <pre>
	 * List&lt;I_C_BPartner&gt; bpartners = myHUQueryBuilder.collect(I_M_HU.COLUMN_C_BPartner_ID);
	 * </pre>
	 *
	 * @return list of collected models
	 */
	<T> List<T> collect(ModelColumn<I_M_HU, T> huColumn);

	<T> List<T> collect(String columnName, Class<T> modelType);

	/**
	 * Set the context in which the query will run. Optional, see {@link #setContext(Object)}.
	 */
	IHUQueryBuilder setContext(final Properties ctx, final String trxName);

	/**
	 * Optionally set the context in which the query will run.<br>
	 * If omitted, then {@link org.adempiere.model.PlainContextAware#newWithThreadInheritedTrx()} is used.
	 *
	 * @param contextProvider an instance that can be used as parameter for {@link org.adempiere.model.InterfaceWrapperHelper#getContextAware(Object)}.
	 * @return this
	 */
	IHUQueryBuilder setContext(final Object contextProvider);

	IHUQueryBuilder onlyContextClient(boolean onlyContextClient);

	/**
	 * Filter only those HUs which have any of the given product(s) in their storages.
	 *
	 * NOTEs:
	 * <ul>
	 * <li>given product(s) are appended to the list of previously specified ones</li>
	 * <li>by default, HUs with an <b>empty</b> storage for the given products are <b>not</b> returned. To return those HUs as well, also call {@link #setAllowEmptyStorage()}</li>
	 * </ul>
	 */
	IHUQueryBuilder addOnlyWithProductIds(final Collection<Integer> productIds);

	/**
	 * See {@link #addOnlyWithProductIds(Collection)}.
	 */
	IHUQueryBuilder addOnlyWithProduct(org.compiere.model.I_M_Product product);

	IHUQueryBuilder addOnlyWithProductId(ProductId productId);

	/**
	 * This is only relevant {@link #addOnlyWithProductIds(Collection)} or one of its siblings is used. The default is {@code false}.
	 */
	IHUQueryBuilder setAllowEmptyStorage();

	/**
	 * Filter only those HUs which are in any of the given warehouse(s).
	 *
	 * NOTE: given warehouse(s) are appended to the list of previously specified ones
	 *
	 * @return this
	 */
	IHUQueryBuilder addOnlyInWarehouseIds(final Collection<WarehouseId> warehouseIds);

	/**
	 * Filter only those HUs which are in any of the given warehouse(s).
	 *
	 * NOTE: given warehouse(s) are appended to the list of previously specified ones
	 */
	IHUQueryBuilder addOnlyInWarehouseId(final WarehouseId warehouseId);

	/**
	 *
	 * @return an unmodifiable set containing the <code>M_Warehouse_ID</code>s that were previously specified by invocations of {@link #addOnlyInWarehouseId(WarehouseId)},
	 *         {@link #addOnlyInWarehouseIds(Collection)} and {@link #addOnlyInWarehouseIds(Collection)}.
	 */
	Set<WarehouseId> getOnlyInWarehouseIds();

	IHUQueryBuilder addOnlyInLocatorId(int locatorId);

	/**
	 * See {@link IStorageQuery#setExcludeAfterPickingLocator(boolean)}.
	 */
	IHUQueryBuilder setExcludeAfterPickingLocator(boolean excludeAfterPickingLocator);

	/**
	 * Filter only those HUs which are assigned to any of the given bpartner(s).
	 *
	 * NOTE: given bpartner(s) are appended to the list of previously specified ones.
	 *
	 * @param bPartnerId HU's BPartner that can be accepted. If it's <code>null</code> we will search for HU's without BPartner too.
	 * @return this
	 */
	IHUQueryBuilder addOnlyInBPartnerId(final BPartnerId bPartnerId);

	/**
	 * @return an unmodifiable set containing the <code>C_BPartner_ID</code>s that were previously specified by invocations of {@link #addOnlyInBPartnerId(BPartnerId)}.
	 */
	Set<BPartnerId> getOnlyInBPartnerIds();

	Set<ProductId> getOnlyWithProductIds();

	/**
	 * Filter only those HUs which have M_HU.C_BPartner_ID set.
	 */
	IHUQueryBuilder setOnlyIfAssignedToBPartner(final boolean onlyIfAssignedToBPartner);

	/**
	 * Filter only those HUs which are assigned to any of the given bpartner location(s).
	 *
	 * NOTE: given bpartner location(s) are appended to the list of previously specified ones.
	 *
	 * @param bpartnerLocationId HU's BPartner Location that can be accepted. If it's <code>null</code> we will search for HU's without BPartner Location too.
	 * @return this
	 */
	IHUQueryBuilder addOnlyWithBPartnerLocationId(final Integer bpartnerLocationId);

	/**
	 * Match only top level HUs.
	 *
	 * It's same as calling {@link #setOnlyTopLevelHUs(boolean)} with <code>true</code>.
	 *
	 * @return this
	 * @see #setOnlyTopLevelHUs(boolean)
	 */
	IHUQueryBuilder setOnlyTopLevelHUs();

	/**
	 * Sets if we shall match only top level HUs or not.
	 *
	 * A HU is considered top level when it does not have any parent.
	 *
	 * NOTE: in case you are matching only top level HUs, then {@link #setM_HU_Item_Parent_ID(int)} will be ignored
	 *
	 * @param onlyTopLevelHUs true if only top level HUs shall be matched; false if any HU shall be matched
	 * @return this
	 */
	IHUQueryBuilder setOnlyTopLevelHUs(boolean onlyTopLevelHUs);

	/**
	 * Sets parent HU Item that our HUs needs to have.
	 *
	 * In case we are matching only top level HUs, this parameter is ignored.
	 */
	IHUQueryBuilder setM_HU_Item_Parent_ID(final int huItemId);

	IHUQueryBuilder setM_HU_Parent_ID(final int parentHUId);

	/**
	 * If <code>true</code> then only active HUs will be matched (i.e. IsActive='Y').
	 * By default, this is true.
	 */
	IHUQueryBuilder setOnlyActiveHUs(boolean onlyActiveHUs);

	/**
	 * Sets HU's HUStatus to be matched.
	 *
	 * If <code>null</code> then all HU statuses are matched.
	 *
	 * NOTE: this is a short version for clearing all HU Statuses to be included and then if not null, adding this HUStatus.
	 */
	IHUQueryBuilder setHUStatus(String huStatus);

	/**
	 * Adds an HU Status that shall be included. So ONLY those HUs which have a status that was added by this method will be included.
	 */
	IHUQueryBuilder addHUStatusToInclude(String huStatus);

	IHUQueryBuilder addHUStatusesToInclude(Collection<String> huStatuses);

	/**
	 * Adds an HU Status that shall be excluded. So all HUs which have that status will be excluded.
	 * <p>
	 * <b>NOTE:</b> even if you specifically set this status to the list of statuses to include (e.g. {@link #setHUStatus(String)}): if the status is present in exclude list then it will be excluded
	 * no matter what.
	 *
	 * @param huStatus HUStatus to exclude (not null)
	 * @return this
	 */
	IHUQueryBuilder addHUStatusToExclude(String huStatus);

	IHUQueryBuilder addHUStatusesToExclude(Collection<String> huStatuses);

	/**
	 * Filter only those HUs which have <code>attribute</code> with given <code>value</code>.
	 */
	IHUQueryBuilder addOnlyWithAttribute(I_M_Attribute attribute, Object value);

	/** Convenience method that works {@link #addOnlyWithAttribute(I_M_Attribute, Object)} for all attributes from the given {@code attributeSet}. */
	IHUQueryBuilder addOnlyWithAttributes(ImmutableAttributeSet attributeSet);

	IHUQueryBuilder addOnlyWithAttributeValuesMatchingPartnerAndProduct(BPartnerId bpartnerId, ProductId productId, ImmutableAttributeSet attributeSet);

	/**
	 * Filter only those HUs which have attribute with given <code>value</code>.
	 *
	 * @see #addOnlyWithAttribute(I_M_Attribute, Object)
	 */
	IHUQueryBuilder addOnlyWithAttribute(AttributeCode attributeCode, Object value);

	IHUQueryBuilder addOnlyWithAttribute(AttributeId attributeId, Object value);

	/**
	 * Filter only those HUs which have <code>attribute</code> with any of the given <code>values</code>.
	 *
	 * <p>
	 * <b>IMPORTANT:</b> other than e.g. in {@link #addOnlyInWarehouseIds(Collection)}, the conditions specified by successive method invocations are <b>AND</b>ed. So, only HUs that have <b>all</b> (as
	 * opposed to any) of the specified attributes and values will match the query.
	 */
	@SuppressWarnings("UnusedReturnValue")
	IHUQueryBuilder addOnlyWithAttributeInList(I_M_Attribute attribute, String attributeValueType, List<?> values);

	/**
	 * Filter only those HUs which have <code>attributeCode</code> with any of the given <code>values</code>.
	 *
	 * <p>
	 * <b>IMPORTANT:</b> other than e.g. in {@link #addOnlyInWarehouseIds(Collection)}, the conditions specified by successive method invocations are <b>AND</b>ed. So, only HUs that have <b>all</b> (as
	 * opposed to any) of the specified attributes and values will match the query.
	 */
	IHUQueryBuilder addOnlyWithAttributeInList(AttributeCode attributeCode, Object... values);

	/**
	 * Filter only those HUs which have <code>attributeCode</code> and it's value is not null.
	 */
	IHUQueryBuilder addOnlyWithAttributeNotNull(AttributeCode attributeCode);

	/**
	 * Filter only those HUs which does not have <code>attributeCode</code> or it's value is null.
	 */
	IHUQueryBuilder addOnlyWithAttributeMissingOrNull(AttributeCode attributeCode);

	@SuppressWarnings("UnusedReturnValue")
	IHUQueryBuilder allowSqlWhenFilteringAttributes(boolean allow);

	/**
	 * Filter the HUs by barcode.
	 * HU identification by barcode can be either of the 2 below:
	 * <li>the M_HU.Value equals the inserted barcode (like before)</li>
	 * <li>the HU has a linked M_HU_Attribute entry with M_Attribute of type Barcode (defined in the DIM_Barcode_Attributes dimension spec) and with the value that equals the inserted barcode</li>
	 */
	IHUQueryBuilder setOnlyWithBarcode(String barcode);

	/**
	 * Add miscelanous filter to be applied.
	 *
	 * NOTE: use this method when none of other filters from here applies.
	 *
	 * @param filter filter to be applied
	 * @return this
	 */
	IHUQueryBuilder addFilter(IQueryFilter<I_M_HU> filter);

	/**
	 * Set sub-query filter. It will be bound on M_HU_ID.
	 *
	 * @param huSubQueryFilter {@link I_M_HU}s subquery or null
	 * @return this
	 */
	IHUQueryBuilder setInSubQueryFilter(IQuery<I_M_HU> huSubQueryFilter);

	/**
	 * Filter only those HUs which are locked (by any lock owner)
	 */
	IHUQueryBuilder onlyLocked();

	/**
	 * Filter only those HUs which are NOT locked (by any lock owner)
	 */
	IHUQueryBuilder onlyNotLocked();

	/**
	 * Ask this builder to throw an error if there were no HUs retrieved.
	 *
	 * @param errorADMessage error AD_Message to be used in exception; if <code>null</code> then a standard error message will be used
	 * @return this
	 * @see #setErrorIfNoHUs(boolean, String)
	 */
	IHUQueryBuilder setErrorIfNoHUs(String errorADMessage);

	/**
	 * Ask this builder to throw or to not throw an error if there were no HUs retrieved.
	 *
	 * Applies only if we call methods like {@link #list()}, {@link #firstOnly()}, {@link #first()} etc.
	 *
	 * Does not apply to {@link #collect(ModelColumn)}.
	 *
	 * @param errorIfNoHUs if true, an exception need to be thrown when there were no HUs retrieved.
	 * @param errorADMessage error AD_Message to be used in exception
	 * @return this
	 */
	IHUQueryBuilder setErrorIfNoHUs(boolean errorIfNoHUs, String errorADMessage);

	/**
	 *
	 * @return true if we shall throw an exception there were no HUs found
	 * @see #setErrorIfNoHUs(boolean, String)
	 * @see #setErrorIfNoHUs(String)
	 */
	boolean isErrorIfNoItems();

	IHUQueryBuilder addOnlyHUValue(@NonNull String huValue);

	/**
	 * Adds HUs which shall be selected. No other HUs, beside those ones will be considered.
	 *
	 * If the given list {@code null} this method will do nothing. If it is empty, no HUs will be considered.
	 */
	IHUQueryBuilder addOnlyHUIds(Collection<HuId> onlyHUIds);

	/**
	 * Adds HUs which needs to be excluded when we retrieve them.
	 */
	IHUQueryBuilder addHUIdsToExclude(Collection<HuId> huIdsToExclude);

	IHUQueryBuilder addHUIdsToAlwaysInclude(Collection<HuId> huIdsToAlwaysInclude);

	/**
	 * Adds the {@link I_M_HU_PI_Version} to include.
	 */
	IHUQueryBuilder addPIVersionToInclude(HuPackingInstructionsVersionId huPIVersionId);

	/**
	 * Check if given {@link IAttributeSet} is matching the attributes from our query.
	 */
	boolean matches(IAttributeSet attributes);

	/**
	 * Sets if the HUs which are currently open or are in a picking slot queue, shall be excluded.
	 */
	IHUQueryBuilder setExcludeHUsOnPickingSlot(boolean excludeHUsOnPickingSlot);

	/**
	 * Set if the Locator of the HU shall be AfterPicking
	 */
	IHUQueryBuilder setIncludeAfterPickingLocator(boolean includeAfterPickingLocator);

	/**
	 * Entries with empty storage will be the only ones retrieved
	 */
	IHUQueryBuilder setEmptyStorageOnly();

	/**
	 * Entries with not-empty storage will be the only ones retrieved.
	 */
	IHUQueryBuilder setNotEmptyStorageOnly();

	/**
	 * Entries that are reserved to the given document or are not reserved at all will be the only ones retrieved.
	 */
	IHUQueryBuilder setExcludeReservedToOtherThan(HUReservationDocRef documentRef);

	/**
	 * Ignored if also {@link #setExcludeReservedToOtherThan(HUReservationDocRef)} was called.
	 */
	IHUQueryBuilder setExcludeReserved();

	IHUQueryBuilder addOnlyInLocatorId(@NonNull LocatorId locatorId);

	IHUQueryBuilder addOnlyInLocatorRepoIds(Collection<Integer> locatorIds);

	IHUQueryBuilder addOnlyInLocatorIds(Collection<LocatorId> locatorIds);

	/**
	 * If true = include only the HUs which have a product with {@link org.compiere.model.I_M_Product#COLUMNNAME_IsStocked} = true
	 * If false or not set = all HUs are included (the field {@link org.compiere.model.I_M_Product#COLUMNNAME_IsStocked} is ignored when filtering the HUs)
	 */
	IHUQueryBuilder setOnlyStockedProducts(final boolean onlyStockedProducts);


}
