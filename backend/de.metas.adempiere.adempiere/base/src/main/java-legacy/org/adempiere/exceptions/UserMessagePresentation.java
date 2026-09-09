package org.adempiere.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * How the WebUI shall present a user-friendly {@link AdempiereException} to the user.
 * Carried on the exception (see {@link AdempiereException#getUserMessagePresentation()}) and emitted
 * next to the {@code userFriendlyError} flag in the WebUI error JSON, so the frontend knows whether to
 * render an auto-dismissing toast or an acknowledgeable OK-dialog.
 * <p>
 * {@code ACKNOWLEDGE_DIALOG} also gets a translated {@code userMessageTitle} in the same JSON, emitted by
 * {@code de.metas.ui.web.config.WebuiExceptionHandler}. That title is currently a single shared
 * "Information" AD_Message for every {@code ACKNOWLEDGE_DIALOG} throw site — the simpler option, and
 * correct as long as every such dialog is genuinely informational. If a future throw site needs a
 * different title (e.g. a warning-flavoured acknowledge dialog), the title resolution would need to move
 * from presentation-mode-level to per-throw-site (e.g. an optional title {@code de.metas.i18n.AdMessageKey}
 * carried on the exception itself, mirroring the message) — {@code WebuiExceptionHandler} is the only
 * place that would need to change.
 *
 * @implNote If you want to add a new presentation mode, e.g. MY_MODE, then you shall
 * <ul>
 * <li>add the MY_MODE enum member here</li>
 * <li>handle the new {@code userMessagePresentation} JSON value on the frontend (the enum name is serialized as-is; no AD_Message involved)</li>
 * </ul>
 */
public enum UserMessagePresentation
{
	TOAST,
	ACKNOWLEDGE_DIALOG,
	;
}
