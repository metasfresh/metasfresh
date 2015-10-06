/**
 * Module for subsequent processing of POs
 * 
 * To use, register an implementation of {@link de.de.metas.processor.spi.IPOProcessor} in the Table <code>AD_POProcessor</code>
 * 
 * <b>Background Info</b>
 *     
 * Adding new "Type" ModelValidator.TYPE_SUBSEQUENT to the modelvalidator to allow a modelvalidator to be invoked not during the saving of a PO (before/after and so on), but subsequently
 * <p>     
 * "Susequently" can mean right after the saving (invoked from within PO.saveFinish) or later on (invoked from a process). 
 * This can be configured, e.g. by the validator itself (initialize() method)
 * The "subsequent" type invocation can't prohibit saving the PO, but it can raise errors which will be recorded and can thus be dealt with
 * <p>
 * Purposes:
 * <ul>
 *  <li>Decoupling modules (example: a bug in module XY's model validator should not prohibit users from creating generic sales orders)</li>
 *  <li>Performance: Some stuff can be done asynchronously on the server, which makes the user interaction faster</li> 
 *</ul>
 *
 * <b>How to use</b>
 * <p>
 * To use a model validator with subsequent processing, you need to
 * <ul>
 *   <li>invoke ModelValidationEngine.enableModelValidatorSubsequentProcessing()</li>
 *   <li>Add code to your modelChange() method that handles ModelValidator.TYPE_SUBSEQUENT</li> 
 * </ul>
 *
 */
package de.metas.processor;

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
