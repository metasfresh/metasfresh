package org.adempiere.inout.shipment;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.Timestamp;
import java.util.Set;

/**
 * A simple bean that is used to pass parameters to
 * {@link ShipmentGeneratorService#computeShipmentCandidates(String, ShipmentgenerateParams)}.
 * 
 * @author ts
 * 
 */
public final class ShipmentParams {

	public final static String IGNORE_POSTAGEFREEAMT = "ignorePostageFreeamount";

	/**
	 * If set, only create shipments for the included order line ids. Don't
	 * prefer them, however.
	 */
	private Set<Integer> selectedOrderLineIds;

	/** Warehouse */
	private int warehouseId = 0;

	/** BPartner */
	private int bPartnerId = 0;

	/** Promise Date */
	private Timestamp datePromised = null;

	/** Include order lines that already have unconfirmed Shipments? */
	private boolean isIncludeWhenIncompleteInOutExists = false;

	/** Consolidate */
	private boolean consolidateDocument = true;

	/** Shipment Date */
	private Timestamp movementDate = null;

	/**
	 * Create a shipment, even if the total price is below the bPartner's
	 * postage free amount.
	 */
	private boolean ignorePostageFreeamount = false;

	/**
	 * If if a bPartner has been selected, create a shipment for that bPArtner,
	 * even if there are other bPartners that are actually further up in the
	 * queue. That is the old behavior of this process.
	 */
	private boolean preferBPartner = false;

	private int adUserId;
	
	private int adPInstanceId;
	
	public int getAdUserId()
	{
		return adUserId;
	}

	public void setAdUserId(int adUserId)
	{
		this.adUserId = adUserId;
	}

	public int getAdPInstanceId()
	{
		return adPInstanceId;
	}

	public void setAdPInstanceId(int adPInstanceId)
	{
		this.adPInstanceId = adPInstanceId;
	}
	
	public final int getWarehouseId() {
		return warehouseId;
	}

	public final void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public final int getBPartnerId() {
		return bPartnerId;
	}

	public final void setBPartnerId(int partnerId) {
		bPartnerId = partnerId;
	}

	public final Timestamp getDatePromised() {
		return datePromised;
	}

	public final void setDatePromised(Timestamp datePromised) {
		this.datePromised = datePromised;
	}

	public final boolean isIncludeWhenIncompleteInOutExists() {
		return isIncludeWhenIncompleteInOutExists;
	}

	public final void setIncludeWhenIncompleteInOutExists(boolean isUnconfirmedInOut) {
		this.isIncludeWhenIncompleteInOutExists = isUnconfirmedInOut;
	}

	public final boolean isConsolidateDocument() {
		return consolidateDocument;
	}

	public final void setConsolidateDocument(boolean consolidateDocument) {
		this.consolidateDocument = consolidateDocument;
	}

	public final Timestamp getMovementDate() {
		return movementDate;
	}

	public final void setMovementDate(Timestamp dateShipped) {
		this.movementDate = dateShipped;
	}

	public final boolean isPreferBPartner() {
		return preferBPartner;
	}

	public final void setPreferBPartner(boolean preferBPartner) {
		this.preferBPartner = preferBPartner;
	}

	public final boolean isIgnorePostageFreeamount() {
		return ignorePostageFreeamount;
	}

	public final void setIgnorePostageFreeamount(boolean ignorePostageFreeamount) {
		this.ignorePostageFreeamount = ignorePostageFreeamount;
	}

	public Set<Integer> getSelectedOrderLineIds() {
		return selectedOrderLineIds;
	}

	public void setSelectedOrderLineIds(Set<Integer> selectedOrderLineIds) {
		this.selectedOrderLineIds = selectedOrderLineIds;
	}

}
