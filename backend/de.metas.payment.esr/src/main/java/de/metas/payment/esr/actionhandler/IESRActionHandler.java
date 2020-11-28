package de.metas.payment.esr.actionhandler;

/*
 * #%L
 * de.metas.payment.esr
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


import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * Every implementor handles a specific {@link I_ESR_ImportLine#COLUMNNAME_ESR_Payment_Action}.
 * 
 */
public interface IESRActionHandler
{

	/**
	 * Processes the ESR import line according to its assigned payment action.
	 * 
	 * @param line The line to process. The handlers don't save by design, it has to be done after the call.
	 * @param message Stores information about the process calling the handler. Currently used when writing off.
	 * @return if the line shall be flagged as processed after the handler has been invoked.
	 */
	boolean process(I_ESR_ImportLine line, String message);
}
