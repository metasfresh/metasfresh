package org.adempiere.pipo.exception;

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


public class POSaveFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8325708851137221459L;

	public POSaveFailedException() {
		super();
	}

	public POSaveFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public POSaveFailedException(String message) {
		super(message);
	}

	public POSaveFailedException(Throwable cause) {
		super(cause);
	}
}
