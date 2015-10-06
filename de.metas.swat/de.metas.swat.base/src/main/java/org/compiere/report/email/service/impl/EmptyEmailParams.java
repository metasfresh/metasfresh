package org.compiere.report.email.service.impl;

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


import org.compiere.model.MUser;
import org.compiere.report.email.service.IEmailParameters;

import de.metas.letters.model.MADBoilerPlate;

/**
 * Default implementation. Always returns <code>null</code>.
 * 
 * @author ts
 * 
 */
public final class EmptyEmailParams implements IEmailParameters {

	public String getAttachmentPrefix(String defaultValue) {
		return null;
	}

	public MADBoilerPlate getDefaultTextPreset() {
		return null;
	}

	public String getExportFilePrefix() {
		return null;
	}

	public MUser getFrom() {
		return null;
	}

	public String getMessage() {
		return null;
	}

	public String getSubject() {
		return null;
	}

	public String getTitle() {
		return null;
	}

	public String getTo() {
		return null;
	}
}
