package org.compiere.report.email.service.impl;

import org.compiere.model.I_AD_User;
import org.compiere.report.email.service.IEmailParameters;

import de.metas.letters.model.MADBoilerPlate;

/**
 * Default implementation. Always returns <code>null</code>.
 * 
 * @author ts
 * 
 */
public final class EmptyEmailParams implements IEmailParameters {

	@Override
	public String getAttachmentPrefix(String defaultValue) {
		return null;
	}

	@Override
	public MADBoilerPlate getDefaultTextPreset() {
		return null;
	}

	@Override
	public String getExportFilePrefix() {
		return null;
	}

	@Override
	public I_AD_User getFrom() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getSubject() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public String getTo() {
		return null;
	}
}
