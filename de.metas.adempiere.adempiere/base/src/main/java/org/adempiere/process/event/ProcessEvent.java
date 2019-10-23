package org.adempiere.process.event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


public final class ProcessEvent {

	private final EventType type;

	private final Object source;

	private final String trxName;

	private final Object relatedSource;

	public EventType getType() {
		return type;
	}

	public enum EventType {
		recordCreated, recordChanged, recordDeleted, trxFinished
	};

	/**
	 * 
	 * @param myType
	 *            type of event
	 * @param mySource
	 * @param myRelatedSource
	 *            Optional, may be <code>null</code>.
	 * @param myTrxName
	 *            Name of the transaction, the source PO was changed in (if not
	 *            yet committed). Optional, may be <code>null</code>.
	 */
	public ProcessEvent(final EventType myType, final Object mySource,
			final Object myRelatedSource, final String myTrxName) {

		if (myType == null) {
			throw new IllegalArgumentException("EventType may not be null");
		}
		if (mySource == null) {
			throw new IllegalArgumentException("Event source may not be null");
		}

		type = myType;
		source = mySource;
		relatedSource = myRelatedSource;
		trxName = myTrxName;
	}

	public Object getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "ProcessEvent; source: " + source + "; type: " + type;
	}

	public String getTrxName() {
		return trxName;
	}

	public Object getRelatedSource() {
		return relatedSource;
	}
}
