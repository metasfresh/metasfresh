package de.metas.commission.install;

/*
 * #%L
 * de.metas.commission.client
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


import java.util.Properties;

import org.compiere.model.PO;

public abstract class Installer {

	protected final String trxName;

	protected final Properties ctx;

	private boolean isSaved;

	protected Installer(final Properties ctx, final String trxName) {

		this.trxName = trxName;
		this.ctx = ctx;

		isSaved = false;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public void save(PO po) {

		if (isSaved()) {
			return;
		}

		po.saveEx();
		setSaved(true);
	}
	
	protected void checkSaved() {
		if (!isSaved()) {
			throw new IllegalStateException("Call 'save' first");
		}
	}
}
