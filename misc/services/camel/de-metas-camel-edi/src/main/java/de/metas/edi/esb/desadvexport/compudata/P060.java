/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport.compudata;

import lombok.Data;

import java.io.Serializable;

@Data
public class P060 implements Serializable
{
	private static final long serialVersionUID = 372101200931588674L;

	private String record;
	private String partner;
	private String messageNo;
	private String cPScounter;
	private String levelID;
	private String palettQTY;
	private String innerOuterCode;
	private String palettTyp;
	private String hoehe;
	private String laenge;
	private String breite;
	private String volumen;
	private String bruttogewicht;
	private String hierachSSCC;
	private String normalSSCC;
	private String grainNummer;
}
