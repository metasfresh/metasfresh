package org.compiere.report.email.service;

import org.compiere.model.I_AD_User;

import de.metas.letters.model.MADBoilerPlate;

public interface IEmailParameters {

	String getAttachmentPrefix(final String defaultValue);

	I_AD_User getFrom();

	String getMessage();

	String getSubject();

	String getTitle();

	String getTo();

	String getExportFilePrefix();

	MADBoilerPlate getDefaultTextPreset();

}
