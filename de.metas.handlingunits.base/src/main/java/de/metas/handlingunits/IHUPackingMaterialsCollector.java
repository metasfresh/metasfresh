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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialsCollector;

/**
 * The implementation iterates HUs (and their children) and collect the packing material products from those HUs.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T> type of the optional "source" (inoutLine, oderLine or other) the respective HU belongs to.
 *            In the case of M_InOutLine, this allows the implementation to later on set the <code>M_PackingMaterial_InOutLine_ID</code> of the source-iol.
 */
public interface IHUPackingMaterialsCollector<T>
{

	/**
	 * Iterates given <code>hu</code> and it's included HUs and collects all packing materials.
	 *
	 * NOTE: this method makes sure that an HU is considered only once, so it's safe to call it as many times as you want.
	 *
	 * @param hu
	 * @param source optional, may be <code>null</code>. Allows the implementation to later on update the given source, as needed.
	 */
	void addHURecursively(I_M_HU hu,
			T source);

	/**
	 * Recursivelly adds all the HUs from given collection.
	 *
	 * NOTE: this method makes sure that an HU is considered only once, so it's safe to call it as many times as you want.
	 *
	 * @param hus
	 * @param source optional, may be <code>null</code>. Allows the implementation to later on update the given source, as needed.
	 * 
	 * @see #addHURecursively(I_M_HU)
	 */
	void addHURecursively(Collection<I_M_HU> hus,
			T source);

	/**
	 * Retrieves suitable HUs from given {@link I_M_HU_Assignment}s query and add them recursively (see {@link #addHURecursively(Collection)}).
	 *
	 * NOTE: this method makes sure that an HU is considered only once, so it's safe to call it as many times as you want.
	 *
	 * @param huAssignments
	 */
	void addHURecursively(IQueryBuilder<I_M_HU_Assignment> huAssignments);

	/**
	 * Retrieves suitable TUs from {@link I_M_HU_Assignment}s query and add them recursively.
	 *
	 * NOTE: In case there is an LU on that assignment, the LU will also be collected.
	 *
	 * @param tuAssignmentsQuery
	 */
	void addTUHUsRecursively(IQueryBuilder<I_M_HU_Assignment> tuAssignmentsQuery);

	List<HUPackingMaterialDocumentLineCandidate> getAndClearCandidates();

	/**
	 * Iterates given <code>hu</code> and it's included HUs and collects all packing materials. The counter is decreased with the number of HUs.
	 *
	 * NOTE: this method makes sure that an HU is considered only once, so it's safe to call it as many times as you want.
	 *
	 * @param hu
	 */
	void removeHURecursively(I_M_HU hu);

	/**
	 * Add a trading unit packing materials
	 *
	 * @param tuHU
	 * @param source optional, may be <code>null</code>. Allows the implementation to later on update the given source, as needed.
	 */
	void addTU(I_M_HU tuHU, T source);

	/**
	 * Collect (extract) trading unit packing materials
	 *
	 * @param tuHU
	 */
	void removeTU(I_M_HU tuHU);

	/**
	 * Add a loading unit packing materials
	 *
	 * @param luHU
	 * @param source optional, may be <code>null</code>. Allows the implementation to later on update the given source, as needed.
	 */
	void addLU(I_M_HU luHU,
			T source);

	/**
	 * Collect (extract) loading unit packing materials
	 *
	 * @param luHU
	 */
	void removeLU(I_M_HU luHU);

	/**
	 * Sets a shared "seen list" for added HUs.
	 *
	 * To be used in case you have multiple {@link HUPackingMaterialsCollector}s and you want if an HU is added to one of them then don't add it to the others.
	 *
	 * Please call this method before you add HUs.
	 *
	 * @param seenM_HU_IDs_ToAdd
	 */
	void setSeenM_HU_IDs_ToAdd(Set<Integer> seenM_HU_IDs_ToAdd);

	/**
	 * Sets if we shall collect packing materials only if {@link I_M_HU#isHUPlanningReceiptOwnerPM()} is set.
	 *
	 * @param collectIfOwnPackingMaterialsOnly
	 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
	 */
	void setCollectIfOwnPackingMaterialsOnly(boolean collectIfOwnPackingMaterialsOnly);

	/**
	 * Sets comparator to be used by {@link #getAndClearCandidates()} to sort the candidates
	 *
	 * @param productIdsSortComparator
	 */
	IHUPackingMaterialsCollector<T> setProductIdSortComparator(Comparator<Integer> productIdsSortComparator);

}
